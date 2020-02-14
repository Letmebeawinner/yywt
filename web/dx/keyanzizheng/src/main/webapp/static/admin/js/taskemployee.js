var data
var employeeIds="";
var resultId;
/**
 * 教职工选择
 */
function selectEmployee(id) {
    resultId=id;
    jQuery.ajax({
        type:"post",
        url:"/admin/ky/ajax/result/selectEmployeeList.json",
        data:data,
        dataType:"text",
        async: false,
        success:function (result) {
            jQuery.alerts._show('选择教职工',null, null,'addCont',function (confirm) {
                if(confirm){
                    if(employeeIds!=""){
                        jQuery.ajax({
                            type:"post",
                            url:"/admin/ky/addTaskEmployee.json",
                            data:{"taskEmployee.taskId":resultId,
                                "employeeIds":employeeIds
                            },
                            dataType:"json",
                            success:function (result) {
                                if(result.code=="0"){
                                    alert("操作成功！");
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
    selectEmployee(resultId);
}
/**
 * 清空搜索条件
 */
function _emptyForm() {
    jQuery(".hasDatepicker").val('');
}
/**
 * 页面显示姓名
 */
function checkboxClick() {
    employeeIds="";
    jQuery('.employeeId:checked').each(function () {
        employeeIds+=jQuery(this).val()+' ';
    });
}

/**
 * 移除教职工
 */
function delEmployee(id) {
    if (confirm("确定将该教职工从该课题中移除？")) {
        jQuery.ajax({
            url: "/admin/ky/deleteTaskEmployee.json?",
            data: {'taskEmployee.taskId':jQuery("#resultId").val(),
                'taskEmployee.employeeId':id
            },
            type: "post",
            dataType: "json",
            async: false,
            success: function (result) {
                if (result.code=="0") {
                    window.location.reload();
                } else {
                    alert(result.message);
                    return;
                }
            }
        });
    }
}