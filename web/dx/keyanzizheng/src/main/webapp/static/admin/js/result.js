jQuery(function () {
    chooseResultForm();
});

Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

var nowDate = new Date().Format("yyyy-MM-dd hh:mm:ss");

// 1论文 2著作 3课题 4内刊 5 其他
function chooseResultForm() {
    var resultFormVal = jQuery("#resultForm").val()
    if (resultFormVal == 1 || resultFormVal == 4 || resultFormVal == 5) {
        jQuery(".choose1").show();
        jQuery(".resultSwitch").show();
        jQuery(".choose2").hide();
        jQuery(".reportingTime").hide();
        jQuery("#addTime").val(nowDate);
        jQuery("#endTime").val(nowDate);
        jQuery(".publish1").show();
        jQuery(".publishTime1").show();
        jQuery(".name").html("<em style=\"color: red;\">*</em> 论文名称");
        jQuery(".publish").html("发表刊物");
        jQuery(".publishTime").html("<em style=\"color: red;\">*</em> 发表时间");
        jQuery(".workName").html("作者名称");
        jQuery(".wordsNumber").html("<em style=\"color: red;\">*</em> 字数");
        jQuery("#kindNature").html("<em style=\"color: red;\">*</em> 刊物性质");
        jQuery("#natureOne").html("核心期刊");
        jQuery("#natureTwo").html("内刊");
        jQuery("#natureThree").html("一般期刊");
        jQuery("#natureFour").html("报纸");
        jQuery("#taskForceMembers").hide()
        if (resultFormVal == 4) {
            jQuery(".name").html("<em style=\"color: red;\">*</em> 内刊名称");
        }
        if (resultFormVal == 5) {
            jQuery(".name").html("<em style=\"color: red;\">*</em> 成果名称");
        }
    } else if (resultFormVal == 2) {
        jQuery(".choose2").show()
        jQuery(".choose1").hide();
        jQuery(".reportingTime").hide();
        jQuery("#addTime").val(nowDate);
        jQuery("#endTime").val(nowDate);
        jQuery(".resultSwitch").hide();
        jQuery("#resultSwitch").val("")
        jQuery(".publish1").show();
        jQuery(".publishTime1").show();
        jQuery(".name").html("<em style=\"color: red;\">*</em> 著作名称");
        jQuery(".publish").html("<em style='color: red;'>*</em>" + " 出版社");
        jQuery(".publishTime").html("<em style=\"color: red;\">*</em> 出版时间");
        jQuery(".workName").html("主编");
        jQuery(".wordsNumber").html("主编字数");
        jQuery("#kindNature").html("类别");
        jQuery("#natureOne").html("教材");
        jQuery("#natureTwo").html("教研报告");
        jQuery("#natureThree").html("书");
        jQuery("#natureFour").hide();
        jQuery("#natureFour").prev().hide();
        jQuery("#participationGroupMembers").html("参编组成员");
    } else {
        jQuery(".choose2").hide();
        jQuery(".reportingTime").show();
        jQuery(".resultSwitch").hide();
        jQuery("#resultSwitch").val("")
        jQuery(".choose1").hide();
        jQuery(".name").html("<em style=\"color: red;\">*</em> 课题名称");
        jQuery(".publish1").hide();
        jQuery("#publishTime").val(nowDate);
        jQuery(".publishTime1").hide();
        jQuery(".workName").html("<em style=\"color: red;\">*</em> 课题负责人");
        jQuery(".wordsNumber").html("<em style=\"color: red;\">*</em> 字数");
    }

    if (resultFormVal == 3) {
        jQuery("#taskForceMembers").show();
        jQuery("#uploadArgumentTheory").show()
        jQuery("#radioKindNature").hide();
        jQuery(".publish1").show();
        jQuery(".publish1 > label").html("课题发布单位")
    } else {
        jQuery("#taskForceMembers").show()
        jQuery("#uploadArgumentTheory").hide()
        jQuery("#radioKindNature").show();
    }

    if (resultFormVal == 1) {
        jQuery("#natureFour").nextAll().show()
    } else {
        jQuery("#natureFour").nextAll().css("display", "none")
    }

    if (resultFormVal === "3" || resultFormVal === "5") {
        jQuery("#radioKindNature").hide()
    } else {
        jQuery("#radioKindNature").show()
    }


    if (resultFormVal === "3" || resultFormVal === "4" || resultFormVal === "5") {
        jQuery("#awardTitle").show()
    } else {
        jQuery("#awardTitle").hide()
    }

}
/**
 }
 * 表单验证
 */
function checkInput() {
    var rfVal = jQuery("#resultForm").val()

    if (jQuery("#teacherResearch").val() == 0) {
        alert("请选择所属处室")
        return false
    }

    /*if (jQuery("#fileUrl").val().length < 1) {
        alert("请上传申请书")
        return false
    }*/

    if (rfVal == 2) {
        var publishVal = jQuery("#publish").val()
        if (publishVal.length == 0) {
            alert("出版社不能为空")
            return false
        }
        var chapterVal = jQuery("#chapter").val()
        if (chapterVal.length == 0) {
            alert("参编章节不能为空")
            return false
        }
    }
    if (jQuery("#name").val() == "") {
        if (rfVal == 3) {
            alert("请输入课题名称");
        } else if (rfVal == 2) {
            alert("请输入著作名称");
        } else if (rfVal == 1) {
            alert("请输入论文名称");
        } else if (rfVal == 4) {
            alert("请输入内刊名称");
        } else (
            alert("请输入成果名称")
        )
        return false;
    }
    if (jQuery("#publishTime").val() == "" && rfVal !== "3") {
        if (rfVal == 1 || rfVal == 4) {
            alert("选择发表时间");
        } else {
            alert("选择出版时间");
        }
        return false;
    }
    if (rfVal == 3) {
        if (jQuery("#addTime").val() == "") {
            alert("输入开始时间");
            return false;
        }

        if (jQuery("#workName").val().length < 1) {
            alert("请输入课题负责人")
            return false;
        }

        if (jQuery("input[name='result.resultType']").val() == 1) {
            if (jQuery("#endTime").val() == "") {
                alert("输入结束时间");
                return false;
            }
        }

        if (jQuery("#endTime").val().length > 0) {
            var addTime = jQuery("#addTime").val()
            var endTime = jQuery("#endTime").val()
            var _addTime = new Date(addTime.replace(/-/g, '/'));
            var _endTime = new Date(endTime.replace(/-/g, '/'));
            if (_addTime > _endTime) {
                alert('审报开始时间不能大于结束时间');
                return false;
            }
        }

        /*if (jQuery("#fileUrlTheory").val().length < 1) {
            alert("请上传论证活页")
            return false
        }*/
    }
    if (jQuery("#wordsNumber").val() == "") {
        if (rfVal == 3) {
            alert("请输入课题字数");
            return false;
        }
        if (rfVal == 1) {
            alert("请输入论文字数");
            return false;
        }
        if (rfVal == 4) {
            alert("请输入内刊字数");
            return false;
        }
    }
    return true;
}
/**
 * 添加成果
 * @returns {boolean}
 */
function addResultFormSubmit() {

    if (!checkInput()) {
        return false;
    }

    var regVal = jQuery("#regTime").val()
    //debugger
    if (regVal === "") {
        alert("请选择登记时间")
        return false
    }

    // 给隐藏域赋值
    jQuery("#journalNatureName").val(jQuery("#selectJournalNature").find("option:selected").text())

    var date = jQuery("#addResult").serialize();
    jQuery.ajax({
        url: "/admin/ky/addResult.json",
        data: date,
        type: "post",
        dataType: "json",
        async: false,
        success: function (result) {
            if (result.code == "0") {
                alert(result.message);
                if(resultForm==null || resultForm == ''){
                    window.location.href = "/admin/ky/getResultList.json?queryResult.resultType=" + result.data.resultType;
                } else if (jQuery("#resultForm").val() == 3) {
                    window.location.href = "/admin/ky/getTaskResultList.json?queryResult.resultType=" + result.data.resultType;
                } else {
                    window.location.href = "/admin/ky/getResultList.json?queryResult.resultType=" + result.data.resultType;
                }

            } else {
                alert(result.message);
            }
        }
    });
}
/**
 * 添加类型
 * @returns {boolean}
 */
function resultFormFromSubmit(){
    var name= jQuery("#name").val();
    if(name==""){
        alert("请填写类型名称")
        return;
    }
    var date = jQuery("#addResultForm").serialize();
    jQuery.ajax({
        url: "/admin/ky/addResultForm.json",
        data: date,
        type: "post",
        dataType: "json",
        async: false,
        success: function (result) {
            if (result.code == "0") {
                alert(result.message);
                window.location.href ="/admin/ky/getResultFormList.json";
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
 * 修改成果
 * @returns {boolean}
 */
function updateResult() {
    if (!checkInput()) {
        return false;
    }

    var regVal = jQuery("#regTime").val()
    //debugger
    if (regVal === "") {
        alert("请选择登记时间")
        return false
    }

    // 给隐藏域赋值
    jQuery("#journalNatureName").val(jQuery("#selectJournalNature").find("option:selected").text())

    var date = jQuery("#updateResult").serialize();
    jQuery.ajax({
        url: "/admin/ky/updateResult.json",
        data: date,
        type: "post",
        dataType: "json",
        async: false,
        success: function (result) {
            if (result.code == "0") {
                alert(result.message);
                if (jQuery("#resultForm").val() == 3) {
                    if (result.data.passStatus == 8) {
                        window.location.href = "/admin/ky/getResultList.json?queryResult.resultType=" + result.data.resultType;
                    } else {
                        window.location.href = "/admin/ky/getTaskResultList.json?queryResult.resultType=" + result.data.resultType;
                    }
                } else {
                    if (result.data.intoStorage == 1) {
                        window.location.href = "/admin/ky/getResultList.json?queryResult.resultType=" + result.data.resultType;
                    } else {
                        window.location.href = "/admin/ky/getResultStorageList.json?queryResult.resultType=" + result.data.resultType;
                    }
                }
            } else {
                alert(result.message);
            }
        }
    });
}

/**
 * 删除成果
 * @param resultId
 */
function delResult(resultId) {
    if (confirm("删除后将无法恢复，是否继续")) {
        jQuery.ajax({
            url: "/admin/ky/deleteResult.json?id=" + resultId,
            data: {},
            type: "post",
            dataType: "json",
            async: false,
            success: function (result) {
                if (result.code == "0") {
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
 * 成果入库
 * @param resultId 成果id
 * @param resultForm 成果类型
 */
function intoStorageResult(resultId, resultForm) {
    if (confirm("是否入库？")) {
        jQuery.ajax({
            url: "/admin/ky/saveArchiveResult.json",
            data: {
                "result.id": resultId,
                "result.intoStorage": 2
            },
            type: "post",
            dataType: "json",
            async: false,
            success: function (result) {
                if (result.code === "0") {
                    alert("操作成功！");
                    window.location.href = "/admin/ky/getResultList.json?queryResult.resultType=" + result.data
                        + "&queryResult.resultForm=" + resultForm;
                } else {
                    alert(result.message);
                    return;
                }
            }
        });
    }
}
/**
 * 成果审核
 * @param resultId
 */
function auditingResult(resultId) {
    if (confirm("是否通审核?")) {
        jQuery.ajax({
            url: "/admin/ky/updateResult.json",
            data: {
                "result.id": resultId,
                "result.passStatus": 6
            },
            type: "post",
            dataType: "json",
            async: false,
            success: function (result) {
                var resultForm = result.data.resultForm;
                var resultType = result.data.resultType;
                window.location.href = "/admin/ky/getResultList.json?queryResult.resultType=" + resultType + "&queryResult.resultForm=" + resultForm;
            }
        });
    }
}
/**
 * 科研成果归档
 */
function fileResult(resultId) {
    window.location.href = "/admin/ky/archiveResult.json?resultId=" + resultId
}

/**
 * 咨政成果归档
 * @param flag 2:短期归档 3:长期归档
 * @param resultId 成果id
 */
function archive(flag, resultId) {
    /*var msg;
    if (flag === 2) {
        msg = "是否确定短期归档"
    }
    if (flag === 3) {
        msg = "是否确定长期归档"
     }*/
    if (confirm("是否确定归档")) {
        jQuery.ajax({
            url: "/admin/ky/doFileArchive.json",
            data: {
                "result.id": resultId,
                "result.ifFile": 1,
                "flag": flag
            },
            type: "post",
            dataType: "json",
            async: false,
            success: function (result) {
                if (result.code === "0") {
                    alert(result.message);
                    window.location.reload();
                } else {
                    alert(result.message);
                }
            }
        });
    }

}
