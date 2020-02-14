var data
var positionId
var employeeId
/**
 * 职位选择
 */
function selectPosition(id) {
    employeeId=id;
    jQuery.ajax({
        type:"post",
        url:"/admin/rs/ajax/selectPositionList.json",
        data:data,
        dataType:"text",
        async: false,
        success:function (result) {
            jQuery.alerts._show('选择职位',null, null,'addCont',function (confirm) {
                if(confirm){
                    jQuery.ajax({
                        type:"post",
                        url:"/admin/rs/updateEmployeePosition.json",
                        data:{"unionEmployee.unionId":jQuery("#unionId").val(),
                            "unionEmployee.employeeId":employeeId,
                            "unionEmployee.position":positionId
                        },
                        dataType:"json",
                        async: false,
                        success:function (result) {
                            if (result.code=="0") {
                                window.location.reload();
                            } else {
                                alert(result.message);
                                return;
                            }
                        }
                    });
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
function searchForm_() {
    data=jQuery("#getPositionList").serialize();
    selectPosition(employeeId);
}
/**
 * 清空搜索条件
 */
function emptyForm_() {
    jQuery(".hasDatepicker").val('');
}
/**
 * 页面显示姓名
 */
function radioClick() {
    positionId=jQuery('input[name="positionId"]:checked').val();
}