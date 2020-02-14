jQuery(function () {
    chooseResultForm();
});

var nowDate = new Date();

/**
 * 选择成果形式
 */
function chooseResultForm() {
    if (jQuery("#resultForm").val() == 1) {
        jQuery(".choose1").show();
        jQuery(".resultSwitch").show();
        jQuery(".choose2").hide();
        jQuery(".reportingTime").hide();
        jQuery("#addTime").val(nowDate);
        jQuery("#endTime").val(nowDate);
        jQuery(".publish1").show();
        jQuery(".publishTime1").show();
        jQuery(".name").html("<em >*</em>论文名称");
        jQuery(".publish").html("发表刊物");
        jQuery(".publishTime").html("<em >*</em>发表时间");
        jQuery(".workName").html("作者名称");
        jQuery(".wordsNumber").html("<em >*</em>字数");
        jQuery("#taskForceMembers").hide()
    } else if (jQuery("#resultForm").val() == 2) {
        jQuery(".choose2").show()
        jQuery(".choose1").hide();
        jQuery(".reportingTime").hide();
        jQuery("#addTime").val(nowDate);
        jQuery("#endTime").val(nowDate);
        jQuery(".resultSwitch").hide();
        jQuery("#resultSwitch").val("")
        jQuery(".publish1").show();
        jQuery(".publishTime1").show();
        jQuery(".name").html("<em>*</em>著作名称");
        jQuery(".publish").html("出版社");
        jQuery(".publishTime").html("<em >*</em>出版时间");
        jQuery(".workName").html("主编");
        jQuery(".wordsNumber").html("主编字数");
        jQuery("#taskForceMembers").hide()
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
        jQuery(".workName").html("课题负责人");
        jQuery(".wordsNumber").html("<em style=\"color: red;\">*</em> 字数");
        jQuery("#taskForceMembers").hide()
    }

    if (jQuery("#resultForm").val() == 3) {
        // 课题组成员输入框
        jQuery("#taskForceMembers").show()
    }

    // 隐藏获奖
    jQuery("#awards").hide()
}

/**
 }
 * 表单验证
 */
function checkInput() {

    if (jQuery("#name").val() == "") {
        if (jQuery("#resultForm").val() == 3) {
            alert("请输入课题名称");
        } else if (jQuery("#resultForm").val() == 2) {
            alert("请输入著作名称");
        } else {
            alert("请输入成果名称");
        }
        return false;
    }
    if (jQuery("#publishTime").val() == "") {
        if (jQuery("#resultForm").val() == 1) {
            alert("选择发表时间");
        } else {
            alert("选择出版时间");
        }
        return false;
    }
    if (jQuery("#resultForm").val() == 3) {
        if (jQuery("#workName").val().length < 1) {
            alert("请输入课题负责人")
            return false;
        }

        if (jQuery("#addTime").val() == "") {
            alert("请输入开始时间");
            return false;
        }

        if (jQuery("input[name='result.resultType']").val() == 1) {
            if (jQuery("#endTime").val() == "") {
                alert("请输入结束时间");
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
    }
    if (jQuery("#wordsNumber").val() == "") {
        if (jQuery("#resultForm").val() == 3) {
            alert("请输入课题字数");
            return false;
        }
        if (jQuery("#resultForm").val() == 1) {
            alert("请输入论文字数");
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
    if (jQuery("#teacherResearch").val() == 0) {
        alert("请选择所属处室")
        return false
    }

    // 附件必选
    if (jQuery("#fileUrl").val().length < 1) {
        alert("请上传申请书")
        return false
    }
    if (jQuery("#fileUrlTheory").val().length < 1) {
        alert("请上传论证活页")
        return false
    }


    if (!checkInput()) {
        return false;
    }
    var date = jQuery("#addResult").serialize();
    jQuery.ajax({
        url: "/admin/jiaowu/ky/addResult.json",
        data: date,
        type: "post",
        dataType: "json",
        async: false,
        success: function (result) {
            if (result.code == "0") {
                alert(result.message);
                if (jQuery("#resultForm").val() == 3) {
                    var urlParam = jQuery("input[name='result.resultType']").val()
                    window.location.href = "/admin/jiaowu/ky/myTaskResultList.json?resultType=" + urlParam + "&result.resultForm=" + jQuery("#resultForm").val();
                }
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
