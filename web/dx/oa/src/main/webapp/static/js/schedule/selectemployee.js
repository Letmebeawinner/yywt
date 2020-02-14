/**
 * Created by yzl on 2017-01-18.
 */
/**
 * 通讯录选择
 */
var data=data;
var employeesIds;
function addEmployee() {
    jQuery.ajax({
        type:"post",
        url:"/admin/oa/ajax/selectEmployee.json",
        data:{},
        dataType:"text",
        async: false,
        success:function (result) {
            jQuery.alerts._show('选择教职工人员',null, null,'addCont',function (confirm) {
                if(confirm){
                    if(employeesIds){
                        jQuery("#userId").val(employeesIds);
                        queryEmployee(employeesIds);
                    }else{
                        alert("没有选择！");
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


function queryEmployee(ids){
    jQuery.ajax({
        url: "/admin/oa/getEmployeeByIds.json",
        data: {"ids": ids},
        type: "post",
        dataType: "json",
        success: function (result) {
            if (result.code=="0") {
                var userList = result.data;
                if(userList!=null){
                    var html = '';
                    for (var i = 0; i < userList.length; i++) {
                        html += '<p style="margin-left:250px;width: 300px;height: 40px;border: 0px;margin-top: 10px;">' + userList[i].name  + '&nbsp;&nbsp;<a onclick="delEmployee(' + userList[i].id  + ')" class="stdbtn btn_red" style="" href="javascript:void(0)"> 删除</a></p>';
                    }
                    jQuery("#employees").html(html);
                }else{
                    jQuery("#employees").html("");
                }
            } else {
                jQuery("#employees").html("");
            }
        }
    });
}


/**
 * 得到教职工id串
 */
function checkClick() {
    employeesIds="";
    jQuery('.telephone:checked').each(function () {
        employeesIds+=jQuery(this).val()+',';
    });
    employeesIds=employeesIds.substring(0,employeesIds.length-1);
}

function delEmployee(id) {
    var userId = jQuery("#userId").val();
    var pattern = id + "";
    userId = userId.replace(new RegExp(pattern), "");
    userId = userId.split(",").unique();
    jQuery("#userId").val(userId);
    queryEmployee(jQuery("#userId").val());
}