var data
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
 * 执行查询
 */
function searchForm() {
    data=jQuery("#getEmployeeList").serialize();
    selectEmployee();
}
/**
 * 清空搜索条件
 */
function emptyForm() {
    jQuery(".hasDatepicker").val('');
}
/**
 * 页面显示姓名
 */
function radioClick() {
    var employeeId= "";
    var employeeName = "";
    jQuery('input[name="employeeId"]:checked').each(function(){
        employeeId+=jQuery(this).val()+",";
        employeeName+=jQuery("#employeeName"+jQuery(this).val()).html()+","
    });
    if(employeeId!=""){
        employeeId=employeeId.substring(0,employeeId.length-1);
    }
    if(employeeName!=""){
        employeeName=employeeName.substring(0,employeeName.length-1);
    }
    jQuery("#employeeId").val(employeeId);
    jQuery("#employeeName").html(employeeName);
    jQuery("#deleteEmployee").show();
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
 * 表单验证
 */
function checkInput() {
    if(jQuery("#employeeType").val()==null ||jQuery("#employeeType").val()=='' ){
        alert("请选择教职工类别");
        return false;
    }
    if(jQuery("#category").val()==null ||jQuery("#category").val()=='' ){
        alert("请选择职工类别");
        return false;
    }
    if(jQuery("#name").val()==""){
        alert("请添加姓名");
        return false;
    }
    if(jQuery("#position").val()==""){
        alert("请添加职务");
        return false;
    }
    if(jQuery("#birthDay").val()==""){
        alert("请添加出生日期");
        return false;
    }
    if(jQuery("#age").val()==""){
        alert("请添加年龄");
        return false;
    }
    if(jQuery("#workTime").val()==""){
        alert("请添加工作时间");
        return false;
    }
    return true;
}
/**
 * 添加教职工
 */
function addEmployeeFormSubmit() {
    if(!checkInput()){
        return;
    }
    var date = jQuery("#addEmployee").serialize();
    jQuery.ajax({
        url: "/admin/rs/addEmployee.json",
        data: date,
        type: "post",
        dataType: "json",
        async: false,
        success: function (result) {
            if (result.code=="0") {
                alert(result.message);
                window.location.href = "/admin/rs/getEmployeeList.json";
            } else {
                alert(result.message);
            }
        }
    });
}
/**
 * 修改教职工
 */
function updateEmployee() {
    if(!checkInput()){
        return;
    }
    var date = jQuery("#updateEmployee").serialize();
    jQuery.ajax({
        url: "/admin/rs/updateEmployee.json",
        data: date,
        type: "post",
        dataType: "json",
        async: false,
        success: function (result) {
            if (result.code=="0") {
                alert(result.message);
                window.location.href = "/admin/rs/getEmployeeList.json";
            } else {
                alert(result.message);
            }
        }
    });
}


/**
 * 修改教职工
 */
function updateSelfEmployee() {
    if(!checkInput()){
        return;
    }
    var date = jQuery("#updateEmployee").serialize();
    jQuery.ajax({
        url: "/admin/rs/updateEmployeeInfo.json",
        data: date,
        type: "post",
        dataType: "json",
        async: false,
        success: function (result) {
            if (result.code=="0") {
                alert(result.message);
                window.location.href = "/admin/rs/employeeInfo.json";
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
function selectBirthDay() {
     var birthDay= new Date(jQuery("#birthDay").val()).getFullYear();
     var date=new Date().getFullYear();
     var a=date -birthDay;
    if(a >0){
        jQuery("#age").val(a)
    }else{
        jQuery("#age").val(0)
    }
}
jQuery("button").click(function(){
   alert(0)
});