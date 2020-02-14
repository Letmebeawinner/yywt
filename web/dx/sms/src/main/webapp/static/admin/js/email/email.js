/**
 * 添加选择的邮箱
 *
 * @param obj 某用户前的复选框对象。根据该对象定位用户的邮箱
 * @author sk
 * @since 2017-01-03
 */
function appendEmail(obj) {
    var email = jQuery(obj).parent('td').siblings('td[id^=email]')[0].innerText;
    if (document.getElementById('user' + obj.value) == null) {
        var element = '<a style="font-size: 0.75rem" id="user' + obj.value + '" class="close" onclick="removeSelect(this)">' + email + '<i class="fa fa-w c-999 fa-close"></i>;</a>';
        jQuery('#tempShow').append(element);
    }
}

/**
 * 全选与取消全选
 * @param obj 全选按钮的选择框
 * @author sk
 * @since 2017-01-03
 */
function checkAll(obj) {
    jQuery(':checkbox').prop('checked', obj.checked);
    var inputs = jQuery('td > :checkbox');
    if (obj.checked) {
        if (window.location.href.endWith('toSendEmail.json')) {
            inputs.each(function () {
                appendEmail(this);
            });
        } else {
            inputs.each(function () {
                appendUsername(this);
            });
        }
    } else {
        inputs.each(function () {
            removeSelected(this);
        });
    }
}

/**
 * 选择与取消选择用户。
 * 当点击某用户所在行的复选框时，判断当前页的所有用户是否都已被选中。
 * 如果是，选中全选按钮，否则如果全选按钮是选中的，取消全选状态。
 * 其次，如果是选择用户，则显示选中用户的邮箱；
 * 否则如果是取消选择，则移除用户的邮箱。
 *
 * @param obj 某用户前的复选框对象。
 * @author sk
 * @since 2017-01-03
 */
function checkuser(obj) {
    var inputs = jQuery('td > :checkbox');
    var checked = inputs.filter(':checked').length == inputs.size();
    jQuery('#checkAll').prop('checked', checked);
    if (obj.checked) {
        if (window.location.href.endWith('toSendEmail.json')) {
            appendEmail(obj);
        } else {
            appendUsername(obj);
        }
    } else {
        removeSelected(obj);
    }
}

/**
 * 删除邮件记录
 *
 * @param recordId 待删除的邮件记录的id
 * @author sk
 * @since 2016-12-30
 */
function deleteEmailRecord(recordId) {
    if (confirm('删除后数据不可恢复，确定继续吗?')) {
        var url = "/admin/sms/email/deleteEmailSendRecord";
        var param = "recordIds=" + recordId;
        crud(url, param, 'GET', function () {
            var roleData = document.getElementById('emailSendRecordData');
            /**
             * 当该页面角色数据只有一条时，如果currentPage＞1时，加载其上一页数据；
             * 否则当该页面角色数据不止一条时，如果totalPageSize＞1且currentPage!=totalPageSize时，加载其下一页数据；
             * 否则当该页面角色数据不止一条时，如果totalPageSize＝currentPage时，直接移除数据节点，不加载新数据
             */
            if (jQuery('tr[id^=emailSendRecord_]').length == 1) {
                var param = currentPage <= 1 ? "" : ("?pagination.currentPage=" + --currentPage);
                window.location.href = "/admin/sms/email/queryEmailSendRecord.json" + param;
            } else {
                if (totalPageSize != 1 && currentPage != totalPageSize)
                    window.location.reload();
                else {
                    var deleteRole = document.getElementById('emailSendRecord_' + recordId);
                    roleData.removeChild(deleteRole);
                }
            }
        });
    }
}

/**
 * 邮件发送记录列表 点击重置
 */
function emptyForm() {
    jQuery("#searchForm select").val(0);
    jQuery('#infoType').val(-1);
    jQuery("#searchForm input").val("");
}

/**
 * 选择收件人时不同的操作。
 * 如果是手动输入收件人邮箱，显示一个{@code textarea};
 * 如果是根据用户选择收件人
 *
 * @param obj 选择收件人类型选项
 */
function queryReceivers(obj) {
    document.getElementById('receivers').innerHTML = '';
    document.getElementById('tempShow').innerHTML = '';
    if (obj.value == 1) {
        jQuery('#receivers').show();
        jQuery('#showUser').hide();
        jQuery('#infoReceiver').hide();
    }

    if (obj.value == 2) {
        jQuery('#receivers').hide();
        jQuery('#infoReceiver').show();
        jQuery('#showUser').show();
    }
}

/**
 * 邮件发送记录列表 点击搜索
 */
function searchForm() {
    jQuery("#searchForm").submit();
}

/**
 * 展示用户列表弹窗
 *
 * @author sk
 * @since 2017-01-04
 */
function showUsers() {
    var span = jQuery('#tempShow');
    var size = span.children('a').size() - 1;
    jQuery.ajax({
        url: '/admin/sms/info/queryReceivers.json',
        data: {'from': 1},
        dataType: 'html',
        success: function (result) {
            jQuery.alerts._show('选择用户', result, null, 'dialog', function (confirm) {
                if (confirm) {
                    span.show();
                    document.getElementById('receivers').innerText = selectedUserIds();
                } else {
                    if (size < 0) span.children('a').remove();
                    else span.children('a:gt(' + size + ')').remove();
                }
            });
        }
    })
}

/**
 * 移除选中的邮箱(发送邮件页面的移除)
 */
function removeSelect(obj) {
    jQuery(obj).remove();
    document.getElementById('receivers').innerText = selectedUserIds();
    document.getElementById('num').innerHTML =document.getElementById('receivers').innerText.split(",").length;
}

/**
 * 得到用户选择的所有用户的id
 * @returns {string} 用户的id串，使用英文状态","分割
 */
function selectedUserIds() {
    return jQuery('a[id^=user]').map(function () {
        return this.id.substr(4)
    }).get().join(',');
}

var clicked = false;

/**
 * 发送邮件
 *
 * @returns {boolean} false.防止表单自动提交
 */
function sendEmail() {
    if (!clicked) {
        clicked = true;
        var url = "/admin/sms/email/sendEmail.json";
        var param = jQuery('#sendEmail').serialize();
        jQuery.ajax({
            data: param,
            dataType: 'json',
            url: url,
            type: 'POST',
            success: function (result) {
                if (result.code == "0") {
                    if (confirm('发送成功，点击确定返回列表页面，取消继续发送邮件')) {
                        window.location.href = result.data;
                    }
                } else {
                    alert(result.message);
                    clicked = false;
                }
            }
        });
    }
    return false;
}


/**
 * 移除选择的邮箱、手机号(选择用户对话框使用)
 * @param obj 待移除的用户前的复选框
 */
function removeSelected(obj) {
    jQuery('#user' + obj.value).remove();
}