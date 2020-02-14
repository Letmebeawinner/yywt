/**
 * 表单验证
 */
function checkInput() {
    if (jQuery("#name").val() == "") {
        alert("请输入报告名称");
        return false;
    }
    if (jQuery("#fileUrl").val() == "") {
        alert("请上传相关资料");
        return false;
    }
    if (jQuery("#info").val() == "") {
        alert("请添加相关简介");
        return false;
    }
    return true;
}
/**
 * 添加调研报告
 * @returns {boolean}
 */
function addInvestigateReportFormSubmit() {
    if(!checkInput()){
        return;
    }
    var date = jQuery("#addinvestigateReport").serialize();
    jQuery.ajax({
        url: "/admin/ky/addInvestigateReport.json",
        data: date,
        type: "post",
        dataType: "json",
        async: false,
        success: function (result) {
            if (result.code == "0") {
                alert(result.message);
                window.location.href = "/admin/ky/getInvestigateReportList.json";
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
 * 修改调研报告
 * @returns {boolean}
 */
function updateInvestigateReport() {
    if(!checkInput()){
        return;
    }
    var date = jQuery("#upInvestigateReport").serialize();
    console.log(date);
    jQuery.ajax({
        url: "/admin/ky/updateInvestigateReport.json",
        data: date,
        type: "post",
        dataType: "json",
        async: false,
        success: function (result) {
            if (result.code == "0") {
                alert(result.message);
                window.location.href = "/admin/ky/getInvestigateReportList.json";
            } else {
                alert(result.message);
            }
        }
    });
}
/**
 * 删除调研报告
 * @param InvestigateReportId
 */
function delInvestigateReport(investigateReportId) {
    if (confirm("删除后将无法恢复，是否继续")) {
        jQuery.ajax({
            url: "/admin/ky/deleteInvestigateReport.json?id="+investigateReportId,
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
 * 调研报告审核
 * @returns {boolean}
 */
function passInvestigateReport(id,ifpass) {
    if (confirm("确定进行该不可恢复操作！")) {
        jQuery.ajax({
            url: "/admin/ky/updateInvestigateReport.json",
            data: {
                "investigateReport.id":id,
                "investigateReport.ifPass":ifpass,
            },
            type: "post",
            dataType: "json",
            async: false,
            success: function (result) {
                if (result.code == "0") {
                    alert("操作成功!");
                    window.location.href = "/admin/ky/toApproveInvestigateReport.json";
                } else {
                    alert(result.message);
                }
            }
        });
    }
}