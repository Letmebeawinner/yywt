/**
 * 添加选择的用户名
 *
 * @param obj 某用户前的复选框对象。根据该对象定位用户的用户名
 * @author sk
 * @since 2017-01-03
 */
function appendUsername(obj) {
    var username = jQuery(obj).parent('td').siblings('td[id^=username]')[0].innerText;
    if (document.getElementById('user' + obj.value) == null) {
        var element = '<a style="font-size: 0.75rem" id="user' + obj.value + '" class="close" onclick="removeSelect(this)">' + username + '<i class="fa fa-w c-999 fa-close"></i>;</a>';
        jQuery('#tempShow').append(element);
    }
}

function deleteInfoRecord(recordId) {
    if (confirm('删除后数据不可恢复，确定继续吗?')) {
        var url = "/admin/sms/info/deleteInfoSendRecord";
        var param = "recordId=" + recordId;
        crud(url, param, 'GET', function () {
            var roleData = document.getElementById('infoSendRecordData');
            /**
             * 当该页面角色数据只有一条时，如果currentPage＞1时，加载其上一页数据；
             * 否则当该页面角色数据不止一条时，如果totalPageSize＞1且currentPage!=totalPageSize时，加载其下一页数据；
             * 否则当该页面角色数据不止一条时，如果totalPageSize＝currentPage时，直接移除数据节点，不加载新数据
             */
            if (jQuery('tr[id^=infoSendRecord_]').length == 1) {
                var param = currentPage <= 1 ? "" : ("?pagination.currentPage=" + --currentPage);
                window.location.href = "/admin/sms/info/queryInfoSendRecord.json" + param;
            } else {
                if (totalPageSize != 1 && currentPage != totalPageSize)
                    window.location.reload();
                else {
                    var deleteRole = document.getElementById('infoSendRecord_' + recordId);
                    roleData.removeChild(deleteRole);
                }
            }
        });
    }
}


function deleteInfoReceiveRecord(recordId) {
    if (confirm('删除后数据不可恢复，确定继续吗?')) {
        var url = "/admin/sms/info/deleteInfoReceiveRecord";
        var param = "id=" + recordId;
        crud(url, param, 'GET', function () {
            var roleData = document.getElementById('infoSendRecordData');
            /**
             * 当该页面角色数据只有一条时，如果currentPage＞1时，加载其上一页数据；
             * 否则当该页面角色数据不止一条时，如果totalPageSize＞1且currentPage!=totalPageSize时，加载其下一页数据；
             * 否则当该页面角色数据不止一条时，如果totalPageSize＝currentPage时，直接移除数据节点，不加载新数据
             */
            if (jQuery('tr[id^=infoSendRecord_]').length == 1) {
                var param = currentPage <= 1 ? "" : ("?pagination.currentPage=" + --currentPage);
                window.location.href = "/admin/sms/info/queryInfoList.json" + param;
            } else {
                if (totalPageSize != 1 && currentPage != totalPageSize)
                    window.location.reload();
                else {
                    var deleteRole = document.getElementById('infoSendRecord_' + recordId);
                    roleData.removeChild(deleteRole);
                }
            }
        });
    }
}

/**
 * 发送消息
 *
 * @author sk
 * @since 2017-01-20
 */
function sendInfo() {
    var url = '/admin/sms/info/sendInfo.json';
    var param = jQuery('#sendInfo').serialize();
    jQuery.ajax({
        data: param,
        dataType: 'json',
        url: url,
        type: 'GET',
        success: function (result) {
            if (result.code == '0') {
                if (confirm('发送成功，点击确定继续发送')) {
                    window.location.reload();
                    jQuery('#infoReceiver').hide();
                    jQuery('#tempShow').val('');
                    jQuery('[type=reset]').click();
                } else {
                    location.href = result.data;
                }
            } else {
                alert(result.message);
            }
        }
    });
}

/**
 * 展示用户列表弹窗
 *
 * @author sk
 * @since 2017-01-04
 */
function showReceivers() {
    var span = jQuery('#tempShow');
    var size = span.children('a').size() - 1;
    jQuery.ajax({
        url: '/admin/sms/info/queryReceivers.json',
        data: {'from': 3},
        dataType: 'html',
        success: function (result) {
            jQuery.alerts._show('选择用户', result, null, 'dialog', function (confirm) {
                if (confirm) {
                    span.show();
                    document.getElementById('receivers').innerText = selectedUserIds();
                    document.getElementById('num').innerHTML =document.getElementById('receivers').innerText.split(",").length;
                } else {
                    if (size < 0) span.children('a').remove();
                    span.children('a:gt(' + size + ')').remove();
                    document.getElementById('num').innerHTML =document.getElementById('receivers').innerText.split(",").length;
                }
            });
        }
    })
}