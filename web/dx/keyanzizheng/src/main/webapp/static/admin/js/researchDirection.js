/**
 * 表单验证
 */
function checkInput() {
    if (jQuery("#departmentName").val() == "") {
        alert("请添加部门");
        return false;
    }
    if (jQuery("#name").val() == "") {
        alert("请输入方向名称");
        return false;
    }
    if (jQuery("#info").val() == "") {
        alert("请输入简介");
        return false;
    }
    return true;
}

/**
 * 添加调研方向
 * @returns {boolean}
 */
function addResearchDirectionFormSubmit() {
    if(!checkInput()){
        return;
    }
    var date = jQuery("#addResearchDirection").serialize();
    jQuery.ajax({
        url: "/admin/ky/addResearchDirection.json",
        data: date,
        type: "post",
        dataType: "json",
        async: false,
        success: function (result) {
            if (result.code == "0") {
                alert(result.message);
                window.location.href = "/admin/ky/getResearchDirectionList.json";
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
    jQuery("#departmentName").html('');
    jQuery("#deleteDepartment").hide();
}

/**
 * 修改调研方向
 * @returns {boolean}
 */
function updateResearchDirection() {
    if(!checkInput()){
        return;
    }
    var date = jQuery("#updateResearchDirection").serialize();
    jQuery.ajax({
        url: "/admin/ky/updateResearchDirection.json",
        data: date,
        type: "post",
        dataType: "json",
        async: false,
        success: function (result) {
            if (result.code == "0") {
                alert(result.message);
                window.location.href = "/admin/ky/getResearchDirectionList.json";
            } else {
                alert(result.message);
            }
        }
    });
}

/**
 * 删除调研方向
 * @param researchDirectionId
 */
function delResearchDirection(researchDirectionId) {
    if (confirm("删除后将无法恢复，是否继续")) {
        jQuery.ajax({
            url: "/admin/ky/deleteResearchDirection.json?id="+researchDirectionId,
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
