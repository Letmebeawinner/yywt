
/**
 * 表单验证
 */
function checkInput() {
    if (jQuery("#name").val() == "") {
        alert("请输入稿件名称");
        return false;
    }
    if (jQuery("#fileUrl").val() == "") {
        alert("请上传申请表");
        return false;
    }
    return true;
}
/**
 * 添加投稿
 * @returns {boolean}
 */
function addContributeFormSubmit() {
    if(!checkInput()){
        return;
    }
    var date = jQuery("#addContribute").serialize();
    jQuery.ajax({
        url: "/admin/ky/addContribute.json",
        data: date,
        type: "post",
        dataType: "json",
        async: false,
        success: function (result) {
            if (result.code == "0") {
                alert(result.message);
                window.location.href = "/admin/ky/getContributeList.json";
            } else {
                alert(result.message);
            }
        }
    });
}
/**
 * 重置
 */
function resetData() {
    jQuery(".longinput").val("");
}
/**
 * 添加投稿
 * @returns {boolean}
 */
function updateContribute() {
    if(!checkInput()){
        return;
    }
    var date = jQuery("#updateContribute").serialize();
    jQuery.ajax({
        url: "/admin/ky/updateContribute.json",
        data: date,
        type: "post",
        dataType: "json",
        async: false,
        success: function (result) {
            if (result.code == "0") {
                alert(result.message);
                window.location.href = "/admin/ky/getContributeList.json";
            } else {
                alert(result.message);
            }
        }
    });
}
/**
 * 删除投稿
 * @param contributeId
 */
function delContribute(contributeId) {
    if (confirm("删除后将无法恢复，是否继续")) {
        jQuery.ajax({
            url: "/admin/ky/deleteContribute.json?id="+contributeId,
            data: {},
            type: "post",
            dataType: "json",
            async: false,
            success: function (result) {
                if (result.code=="0") {
                    alert(result.message);
                    window.location.reload();
                } else {
                    alert(result.message);
                    return;
                }
            }
        });
    }
}

/**
 * 投稿审核
 * @param contributeId
 */
function passContribute(contributeId,passStatus) {
    if (confirm("此操作无法回复，确定操作？")) {
        jQuery.ajax({
            url: "/admin/ky/updateContribute.json",
            data: {"contribute.id":contributeId,
                "contribute.ifPass":passStatus
            },
            type: "post",
            dataType: "json",
            async: false,
            success: function (result) {
                if (result.code=="0") {
                    alert("操作成功!");
                    window.location.reload();
                } else {
                    alert(result.message);
                    return;
                }
            }
        });
    }
}