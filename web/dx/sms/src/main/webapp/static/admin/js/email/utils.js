/**
 * 返回、后退
 */
function comeback() {
    window.location.href = document.referrer;
}

/**
 * 操作数据(增删改查) 1.9版本之前不支持{@code method}属性
 * @param url 操作数据的请求路径
 * @param param 请求参数
 * @param method 请求方式. GET or POST
 * @param callback 回调
 */
function crud(url, param, method, callback) {
    if (!url.endWith('.json')) {
        url += '.json';
    }
    jQuery.ajax({
        url: url,
        data: param,
        dataType: 'json',
        type: method,
        success: function (result) {
            if (result.code == "0") {
                if (!!callback) {
                    callback(result.data, result.message);
                } else {
                    window.location.reload();
                }
            } else {
                alert(result.message);
            }
        }
    })
}

/**
 * 文本是否以指定的字符串结尾
 * @param pattern   结尾的字符串
 * @return {boolean} {@code true}结尾
 */
String.prototype.endWith = function (pattern) {
    return this.slice(-pattern.length) == pattern;
};