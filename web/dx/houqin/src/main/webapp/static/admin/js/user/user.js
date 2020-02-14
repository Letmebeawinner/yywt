/**
 * Created by yzl on 2017-01-18.
 */
/**
 * 桌面选择
 */
var data;
var studentIds;
function choiceUser() {
    jQuery.ajax({
        type:"post",
        url:"/admin/houqin/ajax/selectUserList.json",
        data:data,
        dataType:"text",
        async: false,
        cache:false,
        success:function (result) {
            jQuery.alerts._show('选择学员列表',null, null,'addCont',function (confirm) {
                if(confirm){
                    if(studentIds){
                        jQuery("#userId").val(studentIds);
                        queryStudent(studentIds);
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



function queryStudent(ids){
    jQuery.ajax({
        url: "/admin/houqin/getSysUserByIds.json",
        data: {"ids": ids},
        type: "post",
        dataType: "json",
        success: function (result) {
            if (result.code=="0") {
                var userList = result.data;
                var html = '';
                for (var i = 0; i < userList.length; i++) {
                    html += '<p style="margin-left:250px;width: 300px;height: 40px;border: 0px;margin-top: 10px;">' + userList[i].name  + '&nbsp;&nbsp;<a onclick="delUser(' + userList[i].id  + ')" class="stdbtn btn_red" style="" href="javascript:void(0)"> 删除</a></p>';
                }
                jQuery("#users").html(html);

            } else {
                jQuery("#users").html("");
            }
        }
    });
}

/**
 * 得到教职工id串
 */
function userClick() {
    studentIds="";
    jQuery('.user:checked').each(function () {
        studentIds+=jQuery(this).val()+',';
    });
    studentIds=studentIds.substring(0,studentIds.length-1);
}