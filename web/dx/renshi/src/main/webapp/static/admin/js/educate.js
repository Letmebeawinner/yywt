var data
var employeeIds;
var educateId;
/**
 * 教职工选择
 */
function addEmployee(id) {
    educateId=id;
    jQuery.ajax({
        type:"post",
        url:"/admin/rs/ajax/union/selectEmployeeList.json",
        data:data,
        dataType:"text",
        async: false,
        success:function (result) {
            jQuery.alerts._show('选择教职工',null, null,'addCont',function (confirm) {
                if(confirm){
                    if(employeeIds!=""){
                        jQuery.ajax({
                            type:"post",
                            url:"/admin/rs/addEducateEmployee.json",
                            data:{"educateEmployee.educateId":educateId,
                                "employeeIds":employeeIds
                            },
                            dataType:"json",
                            success:function (result) {
                                if(result.code=="0"){
                                    alert("操作成功!");
                                    window.location.reload();
                                }else {
                                    alert(result.message);
                                }
                            }
                        });
                    }else{
                        alert("没有选择任何教职工！");
                    }
                }
            });
            jQuery('#popup_message').html(result);
            // 修改弹窗的位置。距窗口上边距150px，左边距30%.
            jQuery('#popup_container').css({
                top: 50,
                left: '30%',
                overflow:'hidden'
            });
            jQuery('#popup_container').css("max-height","800px");
            jQuery('#popup_message').css("max-height","600px");
            jQuery('#popup_message').css('overflow-y','scroll');
        }
    });
}
/**
 * 执行查询
 */
function _searchForm() {
    employeeIds="";
    data=jQuery("#employeeList").serialize();
    addEmployee(educateId);
}
/**
 * 清空搜索条件
 */
function _emptyForm() {
    jQuery(".hasDatepicker").val('');
}
/**
 * 得到教职工id串
 */
function checkboxClick() {
    employeeIds="";
    jQuery('.employeeId:checked').each(function () {
        employeeIds+=jQuery(this).val()+' ';
    });
}
/**
 * 表单验证
 */
function checkInput() {
    if(jQuery("#technical").val()==""){
        alert("请添加职称");
        return false;
    }
    if(jQuery("#name").val()==""){
        alert("请添加培训名称");
        return false;
    }
    if(jQuery("#purpose").val()==""){
        alert("请添加培训目的");
        return false;
    }
    if(jQuery("#beginTime").val()==""){
        alert("请添加培训开始时间");
        return false;
    }
    if(jQuery("#endTime").val()==""){
        alert("请添加培训结束时间");
        return false;
    }
    if(jQuery("#endTime").val() < jQuery("#beginTime").val()){
        alert("结束时间不能小于开始时间");
        return false;
    }
    if(jQuery("#teacher").val()==""){
        alert("请添加培训讲师");
        return false;
    }
    if(jQuery("#diaoXunUnit").val()==""){
        alert("请添加调训单位");
        return false;
    }
    if(jQuery("#trainingUnit").val()==""){
        alert("请添加培训单位");
        return false;
    }
    if(jQuery("#trainingLocation").val()==""){
        alert("请添加培训地点");
        return false;
    }
    return true;
}

/**
 * 添加培训
 */
function addEducateFormSubmit() {
    if(!checkInput()){
        return;
    }
    if(jQuery("#fileUrl").val()==""){
        alert("请上传培训总结");
        return false;
    }
    var date = jQuery("#addEducate").serialize();
    jQuery.ajax({
        url: "/admin/rs/addEducate.json",
        data: date,
        type: "post",
        dataType: "json",
        async: false,
        success: function (result) {
            if (result.code=="0") {
                alert(result.message);
                window.location.href = "/admin/rs/getEducateList.json";
            } else {
                alert(result.message);
            }
        }
    });
}

/**
 * 添加培训
 */
function addSelfEducateFormSubmit() {
    if(!checkInput()){
        return;
    }
    jQuery.ajax({
        url: "/admin/rs/addEducate.json",
        data: jQuery("#addEducate").serialize(),
        type: "post",
        dataType: "json",
        async: false,
        success: function (result) {
            if (result.code=="0") {
                alert(result.message);
                window.location.href = "/admin/rs/getSelfEducateList.json";
            } else {
                alert(result.message);
            }
        }
    });
}

/**
 * 修改培训
 */
function updateEducate() {
    if(!checkInput()){
        return;
    }
    jQuery.ajax({
        url: "/admin/rs/updateEducate.json",
        data: jQuery("#updateEducate").serialize(),
        type: "post",
        dataType: "json",
        async: false,
        success: function (result) {
            if (result.code=="0") {
                alert(result.message);
                window.location.href = "/admin/rs/getEducateList.json";
            } else {
                alert(result.message);
            }
        }
    });
}

/**
 * 修改培训
 */
function updateSelfEducate() {
    if(!checkInput()){
        return;
    }
    jQuery.ajax({
        url: "/admin/rs/updateEducate.json",
        data: jQuery("#updateEducate").serialize(),
        type: "post",
        dataType: "json",
        async: false,
        success: function (result) {
            if (result.code=="0") {
                alert(result.message);
                window.location.href = "/admin/rs/getSelfEducateList.json";
            } else {
                alert(result.message);
            }
        }
    });
}
/**
 * 清空表单
 */
function resetData(){
    jQuery(".longinput").val("");
}



/**
 * 执行查询
 */
function searchForm() {
    jQuery("#getEducateList").submit();
}
/**
 * 清空搜索条件
 */
function emptyForm() {
    jQuery("input:text").val('');
    jQuery("select").val(0);
}
/**
 * 删除培训
 */
function delEducate(educateId) {
    if (confirm("删除后将无法恢复，是否继续")) {
        jQuery.ajax({
            url: "/admin/rs/deleteEducate.json",
            data: {"id":educateId},
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
 * 教职工选择
 */
function selectEmployee() {
    jQuery.ajax({
        type:"post",
        url:"/admin/rs/ajax/selectEmployeeList.json",
        data:data,
        dataType:"text",
        async: false,
        success:function (result) {
            jQuery.alerts._show('选择教职工',null, null,'addCont',function (confirm) {
                if(!confirm){
                    deleteEmployee();
                }
            });
            jQuery('#popup_message').html(result);
            // 修改弹窗的位置。距窗口上边距150px，左边距30%.
            jQuery('#popup_container').css({
                top: 50,
                left: '20%',
                overflow:'hidden'
            });
            jQuery('#popup_container').css("max-height","600px");
            jQuery('#popup_message').css("max-height","400px");
            jQuery('#popup_message').css('overflow-y','scroll');
        }
    });
}

/**
 * 清除教职工
 */
function deleteEmployee() {
    jQuery("#employeeId").val('');
    jQuery("#employeeName").html('');
    jQuery("#deleteEmployee").hide();
}

/**
 * 页面显示姓名
 */
function radioClick() {
    //var employeeId=jQuery('input[name="employeeId"]:checked').val();
    var chkId ="";
    var chkName ="";
    jQuery('input[name="employeeId"]:checked').each(function(){
        chkId+=jQuery(this).val()+",";
        chkName+=jQuery("#employeeName"+jQuery(this).val()).html()+"、";

    });
    if(chkId!=""){
        chkId=chkId.substring(0,chkId.length-1);
    }
    if(chkName!=""){
        jQuery("#deleteEmployee").show();
        chkName=chkName.substring(0,chkName.length-1);
    }
    jQuery("#employeeId").val(chkId);
    jQuery("#employeeName").html(chkName);
    jQuery("#deleteEmployee").show();
}