function selectSysUser(name) {
    jQuery.ajax({
        type:"post",
        url:"/admin/oa/ajax/selectSysUserList.json",
        data:{"username":name},
        dataType:"text",
        async: false,
        success:function (result) {
            jQuery.alerts._show('选择用户',null, null,'addCont',function (confirm) {
                if(!confirm){
                    var obj = jQuery("#userName").html();
                    removeUserAll(obj);
                    // addApproval(1)
                }
            });
            jQuery('#popup_message').html(result);
            // 修改弹窗的位置。距窗口上边距150px，左边距30%.
            jQuery('#popup_container').css({
                top: 50,
                left: '20%',
                overflow:'hidden'
            });
            // jQuery('#popup_container').css("max-height","600px");
            // jQuery('#popup_message').css("max-height","800px");
            // jQuery('#popup_message').css('overflow-y','scroll');
        }
    });
}

function radioClick() {
    var sysUserIds= "";
    var userName = "";
    jQuery('input[name="sysUserId"]:checked').each(function(){
        sysUserIds+=jQuery(this).val()+",";
        // userName+=jQuery("#userName"+jQuery(this).val()).html()+",";
        userName+=' <a class="close" id="'+jQuery(this).val()+'"> <i>'+jQuery("#userName"+jQuery(this).val()).html()+'</i><i class="fa fa-w c-999 fa-close" onclick="removeUser(this,jQuery(this).val())"></i> </a>';
        // jQuery("#mobiles").append(element);
    });
    if(sysUserIds!=""){
        sysUserIds=sysUserIds.substring(0,sysUserIds.length-1);
        userName=userName.substring(0,userName.length-1);
        jQuery("#sysUserIds").val(sysUserIds);
        jQuery("#userName").html(userName);
    }
}

function searchForm() {
    var username = jQuery('input[name="username"]').val();
    selectSysUser(username);
}

function removeUser(obj) {
    jQuery(obj).parent().next().remove();
    jQuery(obj).parent().remove();

}

function removeUserAll(obj) {
    jQuery(obj).remove();

}

function addApproval(type) {
    var obj = jQuery("#userName").children();
    var userIds = "";
    if (obj.length == 0) {
        alert("请选择发送领导");
        return false;
    }

    if(!jQuery("#effectiveTime").val()){
        alert("请选择时效截止时间");
        return;
    }
    jQuery.each(obj, function(index , value) {
        userIds += jQuery(value).attr("id") + ",";
    });

    // var ids = jQuery("#sysUserIds").val();
    var ids = userIds.substring(0,userIds.length-1);;
    var PrimaryKey = jQuery("#PrimaryKey").val();
    var timeStamp = jQuery("#timeStamp").val();
    var effectiveTime = jQuery("#effectiveTime").val();
    var flag=false;
    if(ids!=""){
        jQuery.ajax({
            type:"post",
            url:"/admin/oa/ajax/addApproval.json",
            data:{"id":ids,"type":type,"letterId":PrimaryKey,"timeStamp":timeStamp,"effectiveTime":effectiveTime},
            dataType:"json",
            async: false,
            success:function (result) {
                if (result.code=="0") {
                    //window.location.reload();
                    flag= true;
                } else {
                    alert(result.message);
                }
            }
        });
    }
    return flag;
}