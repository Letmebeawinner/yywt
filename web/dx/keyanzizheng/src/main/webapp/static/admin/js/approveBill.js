/**
 * 添加审批
 * @returns {boolean}
 */
function addapproveBillFormSubmit() {
    /*if (jQuery("#fileUrl").val() == "") {
        alert("请上传审批文件");
        return false;
    }*/
    var date = jQuery("#addapproveBill").serialize();
    jQuery.ajax({
        url: "/admin/ky/addApproveBill.json",
        data: date,
        type: "post",
        dataType: "json",
        async: false,
        success: function (result) {
            if (result.code == "0") {
                alert(result.message);
                window.location.href = "/admin/ky/toResultApprovalList.json?queryResult.passStatus="+ 1
                    + "&queryResult.resultType=" + result.data;
            } else {
                alert(result.message);
            }
        }
    });
}


/**
 * 科研/咨政处审批
 * @returns {boolean}
 */
function saveOfficeForm() {
    /*if (jQuery("#fileUrl").val() == "") {
        alert("请上传审批文件");
        return false;
    }*/
    var date = jQuery("#saveForm").serialize();
    jQuery.ajax({
        url: "/admin/ky/saveOfficeApproval.json",
        data: date,
        type: "post",
        dataType: "json",
        async: false,
        success: function (result) {
            if (result.code == "0") {
                alert(result.message);
                window.location.href = "/admin/ky/toResultApprovalList.json?queryResult.passStatus=" + 2
                    + "&queryResult.resultType=" + result.data;
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
 * 检测电脑是否安装了flash
 * @returns {___anonymous44316_44376}
 */
function flashChecker() {
    var hasFlash = 0; //是否安装了flash
    var flashVersion = 0; //flash版本
    if (document.all) {
        var swf = new ActiveXObject('ShockwaveFlash.ShockwaveFlash');
        if (swf) {
            hasFlash = 1;
            VSwf = swf.GetVariable("$version");
            flashVersion = parseInt(VSwf.split(" ")[1].split(",")[0]);
        }
    } else {
        if (navigator.plugins && navigator.plugins.length > 0) {
            var swf = navigator.plugins["Shockwave Flash"];
            if (swf) {
                hasFlash = 1;
                var words = swf.description.split(" ");
                for (var i = 0; i < words.length; ++i) {
                    if (isNaN(parseInt(words[i]))) continue;
                    flashVersion = parseInt(words[i]);
                }
            }
        }
    }
    return {
        f: hasFlash,
        v: flashVersion
    };
}
/**
 * 是否安装了Flash
 * @returns {Boolean}
 */
function testingFlash() {
    var fls = flashChecker();
    if (fls.f) {
        return true;
    } else {
        return false;
    }
}
