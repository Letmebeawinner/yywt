function selectUser(processInstanceId,effectiveTime) {
    jQuery("#processInstanceId").val(processInstanceId);
    jQuery.ajax({
        type:"post",
        url:"/admin/oa/ajax/selectDepartmentList.json",
        dataType:"text",
        async: false,
        success:function (result) {
            jQuery.alerts._show('选择用户',null, null,'addCont',function (confirm) {
                if(confirm){
                    addApproval(2,effectiveTime);
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

function radioClick() {
    var sysUserIds= "";
    jQuery('input[name="sysUserId"]:checked').each(function(){
        sysUserIds+=jQuery(this).val()+",";
    });
    if(sysUserIds!=""){
        sysUserIds=sysUserIds.substring(0,sysUserIds.length-1);
        jQuery("#sysUserIds").val(sysUserIds);
    }
}

function addApproval(type,effectiveTime) {
    var ids = jQuery("#sysUserIds").val();
    var processInstanceId = jQuery("#processInstanceId").val();
    if(ids!=""){
        jQuery.ajax({
            type:"post",
            url:"/admin/oa/ajax/addApproval.json",
            data:{"id":ids,"type":type,"letterId":processInstanceId,"effectiveTime":effectiveTime},
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
}

/**
 * 我发出去的记录
 * @param processInstanceId
 */
function senderList(processInstanceId) {
    window.location.href = "/admin/oa/process/senderList.json?processInstanceId="+processInstanceId;
}