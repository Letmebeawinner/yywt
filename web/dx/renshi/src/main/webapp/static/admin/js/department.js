var data
/**
 * 部门选择
 */
function selectDepartment() {
    jQuery.ajax({
        type:"post",
        url:"/admin/rs/ajax/selectDepartMentList.json",
        data:data,
        dataType:"text",
        async: false,
        success:function (result) {
            jQuery.alerts._show('选择部门',null, null,'addCont',function (confirm) {
                if(!confirm){
                    deleteDepartment();
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
function searchForm() {
    data=jQuery("#getDepartMentList").serialize();
    selectDepartment();
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
function _radioClick() {
    var departmentId=jQuery('input[name="departmentId"]:checked').val();
    jQuery("#departmentId").val(departmentId);
    jQuery("#departmentName").html(jQuery("#departmentName"+departmentId).html());
    jQuery("#deleteDepartment").show();
}
/**
 * 清除部门
 */
function deleteDepartment() {
    jQuery("#departmentId").val('');
    jQuery("#departmentName").html('');
    jQuery("#deleteDepartment").hide();
}