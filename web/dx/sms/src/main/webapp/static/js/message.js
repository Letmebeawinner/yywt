/**
 * 删除短信记录
 * @param id 记录id
 * @param obj 当前记录的js对象
 */
function deleteMsgRecode(id, obj) {
    if (window.confirm("确定删除吗？")) {
        jQuery.ajax({
            url: '/admin/sms/message/deleteMsgRecode.json',
            type: 'GET',
            data: {"id": id},
            dataType: 'json',
            success: function (result) {
                if (result.code == '0') {
                    if (jQuery('tr').length == 2) {
                        var param = currentPage <= 1 ? "" : ("?pagination.currentPage=" + --currentPage);
                        window.location.href = "/admin/sms/message/messageList.json" + param;
                    } else {
                        if (totalPageSize != 1 && currentPage != totalPageSize) {
                            window.location.reload();
                        } else {
                            jQuery(obj).parent().parent().remove();
                        }
                    }
                }
            }
        });
    }
}

/**
 * 短信记录列表 点击搜索
 */
function searchForm() {
    jQuery("#searchForm").submit();
}
/**
 * 短信记录列表 点击重置
 */
function emptyForm() {
    jQuery("#searchForm input").val("");
    jQuery("#searchForm select").val(0);
}


/**
 * 发送短信页面
 * 点击发送按钮 发送短信
 */
function send() {
    var mobiles = "";
    var receiveUserIds = "";
    var type = jQuery('#type').val();
    if (type == 0) {
        mobiles = jQuery("#tel").val();//手机号
        receiveUserIds = jQuery("#receiveUserId").val();//接收人ids
    }else if (type == 1) {
        jQuery(".close").each(function(){
            receiveUserIds+=jQuery(this).attr("id")+",";
            mobiles+=jQuery(this).find("i").eq(0).text()+",";
        });
    }
    mobiles = mobiles.replaceAll("，", ",");//替换中文逗号
    if (mobiles.substring(mobiles.length - 1, mobiles.length) == ',') {//去掉最后的逗号
        mobiles = mobiles.substring(0, mobiles.length - 1);
    }
    var context = jQuery("#context").val();//短信内容
    jQuery.ajax({
        url: '/admin/sms/message/addMsg.json',
        type: 'POST',
        data: {"mobiles": mobiles, "context": context, "receiveUserIds": receiveUserIds},
        dataType: 'json',
        success: function (result) {
            if (result.code == '0') {
                if (confirm("发送成功，点击确定返回列表页面，点击取消继续发送短信")) {
                    window.location.href = "/admin/sms/message/messageList.json";
                } else {
                    jQuery("#addUser textarea").val("");
                }
            } else {
                alert(result.message);
            }
        }
    });
}
/**
 * 发送短信页面
 * 点击选择学员
 */
function chooseUser() {
    var mobiles = jQuery('#mobiles');
    var len = mobiles.children('a').size() - 1;
    jQuery.ajax({
        url: '/admin/sms/info/queryReceivers.json',
        type: 'POST',
        data: {'from': 2},
        dataType: 'html',
        success: function (result) {
            jQuery.alerts._show("选择用户", result, null, 'dialog', function (data) {
                if (!data) {
                    if (len < 0) mobiles.children('a').remove();
                    mobiles.children("a:gt(" + len + ")").remove();
                }
            });
        }
    });
}
/**
 * 发送短信页面  选择添加联系人方式
 * @param obj
 */
function chooseType(obj) {
    if (jQuery(obj).val() == 0) {//自定义
        jQuery("#choose").hide();
        jQuery(".close").remove();//选择自定义 移除一选择的学员
        jQuery('#tel').attr({
            disabled: false,
            placeholder: '自定义添加联系人，需手动输入联系人手机号'
        }).show();
    } else {//选择列表
        jQuery("#tel").hide();
        jQuery("#choose").show();
    }
}
/**
 * 重置按钮  清空表单的同时 联系人编辑框可编辑
 * 添加学员按钮隐藏
 */
function resetForm() {
    jQuery('#mobiles').children('a').remove();
    jQuery("#choose").hide();
    jQuery('#tel').attr({
        disabled: false,
        placeholder: '自定义添加联系人，需手动输入联系人手机号'
    }).show();
}

/**
 * 点击x号  移除联系人
 * @param obj 点击的js对象
 */
function removeUser(obj) {
    jQuery(obj).parent().remove();
}


/**
 * 用户列表  点击多选框
 */
function checkuser(obj) {
    //判断是否是全部选中了
    var bn = true;
    jQuery(".users").each(function () {
        if (!this.checked) {
            bn = false;
        }
    });
    if (bn) {
        jQuery(":checkbox").attr("checked", true);
    } else {
        jQuery("#checkAll").attr("checked", false);
    }

    var userId = jQuery(obj).val();
    var mobile = jQuery("#mobile_" + userId).text().trim();
    //添加联系人
    if(obj.checked){//选中的添加
        if(!jQuery("#"+userId).text()){
            var element=' <a class="close" id="'+userId+'"> <i>'+mobile+'</i><i class="fa fa-w c-999 fa-close" onclick="removeUser(this)"></i> </a>';
            jQuery("#mobiles").append(element);
        }
    } else {//取消选中的移除
        var id = "#" + userId;
        jQuery(id).remove();
    }
}

function checkAll(obj) {
    var a = obj.checked;
    jQuery(":checkbox").attr("checked", a);
    if (a) {
        jQuery("#chooseText").text("取消全选");
        jQuery(".users").each(function () {
            checkuser(this);
        });
    } else {
        jQuery("#chooseText").text("全部选中");
        jQuery(".users").each(function () {
            checkuser(this);
        });
    }

}