/**
 * 提交form表单
 * @returns {boolean} {@code true}允许提交
 */
function commit() {
    if (!verify()) {
        return false;
    }
    jQuery.ajax({
        url: '/admin/app/update/saveAppUpdateInfo.json',
        data: jQuery('#saveAppUpdate').serialize(),
        dataType: 'JSON',
        success: function (result) {
            alert(result.message);
        }
    })
}

/**
 * 判断指定的文本是否为空
 * @param text
 * @returns {boolean}
 */
function isEmpty(text) {
    return text == '' || text.replace('/[\s]/g', '') == '';
}

/**
 * 验证字段
 * @returns {boolean}
 */
function verify() {
    if (isEmpty(document.getElementById('app.version').value)) {
        alert('app版本不能为空');
        return false;
    }
    var updateLink = document.getElementById('app.updateUrl').value;
    if (isEmpty(updateLink)) {
        alert('app更新下载链接不能为空');
        return false;
    }
    return true;
}