
/**
 * 表单验证
 */
function checkInput() {
    if (jQuery("#name").val() == "") {
        alert("请输入你的课题名称");
        return false;
    }
    if (jQuery("#theme").val() == "") {
        alert("请输入申报项目的主题词");
        return false;
    }
    if (jQuery("#keyword").val() == "") {
        alert("请输入申报的关键字");
        return false;
    }
    if (jQuery("#projectDate").val() == "") {
        alert("请输入你的立项时间");
        return false;
    }
    if (jQuery("#finishDate").val() == "") {
        alert("请输入你的预完成时间");
        return false;
    }
    if (jQuery("#presenter").val() == "") {
        alert("请输入课题主持人的姓名");
        return false;
    }
    if (jQuery("#digest").val() == "") {
        alert("请输入本课题的内容摘要");
        return false;
    }
    return true;
}
/**
 * 添加课题
 * @returns {boolean}
 */
function addTaskFormSubmit() {
    if(!checkInput()){
        return;
    }
    var date = jQuery("#addTask").serialize();
    jQuery.ajax({
        url: "/admin/ky/addTask.json",
        data: date,
        type: "post",
        dataType: "json",
        async: false,
        success: function (result) {
            if (result.code == "0") {
                alert(result.message);
                window.location.href = "/admin/ky/getTaskList.json?queryTask.taskType="+jQuery("#taskType").val();
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
 * 修改课题
 * @returns {boolean}
 */
function updateEmployee() {
    if(!checkInput()){
        return;
    }
    var date = jQuery("#updateEmployee").serialize();
    jQuery.ajax({
        url: "/admin/ky/updateTask.json",
        data: date,
        type: "post",
        dataType: "json",
        async: false,
        success: function (task) {
            if (task.code == "0") {
                alert(task.message);
                window.location.href = "/admin/ky/getTaskList.json?queryTask.taskType="+jQuery("#taskType").val();
            } else {
                alert(task.message);
            }
        }
    });
}



