if (!Array.prototype.indexOf) {
    Array.prototype.indexOf = function (elt /*, from*/) {
        var len = this.length >>> 0;

        var from = Number(arguments[1]) || 0;
        from = (from < 0)
            ? Math.ceil(from)
            : Math.floor(from);
        if (from < 0)
            from += len;

        for (; from < len; from++) {
            if (from in this &&
                this[from] === elt)
                return from;
        }
        return -1;
    };
}

/**
 * 获取Cookies方法
 * @param cookieName
 * @returns
 */
function getCookie(cookieName) {

    var cookieString = document.cookie;
    var start = cookieString.indexOf(cookieName + '=');
    // 加上等号的原因是避免在某些 Cookie 的值里有
    // 与 cookieName 一样的字符串。
    if (start == -1) // 找不到
        return null;
    start += cookieName.length + 1;
    var end = cookieString.indexOf(';', start);
    if (end == -1) {
        return unescape(cookieString.substring(start));
    } else {
        return unescape(cookieString.substring(start, end));
    }
}

/**
 * 获取Cookies方法,解决中文乱码
 * @param cookieName
 * @returns
 */
function getCookieFromServer(cookieName) {
    var cookieString = document.cookie;
    var start = cookieString.indexOf(cookieName + '=');
    // 加上等号的原因是避免在某些 Cookie 的值里有
    // 与 cookieName 一样的字符串。
    if (start == -1) // 找不到
        return null;
    start += cookieName.length + 1;
    var end = cookieString.indexOf(';', start);
    if (end == -1) {
        return Url.decode(cookieString.substring(start));
    } else {
        return Url.decode(cookieString.substring(start, end));
    }
}

/**
 * 删除Cookies
 * @param name
 */
function DeleteCookie(name) {
    DeleteCookieDomain(name, mydomain);
}

/**创建Cookies
 * @param name
 * @param value
 */
function SetCookie(name, value) {
    SetCookieDomain(name, value, mydomain);
}

//自定义cookies失效时间 s指秒 h指天数 d指天数 如s40代表40秒
function SetCookieOutTime(name, value, outTime) {
    var strsec = getsec(outTime);
    var exp = new Date();
    exp.setTime(exp.getTime() + strsec * 1);
    if (isNotEmpty(mydomain)) {
        document.cookie = name + "=" + escape(value) + ";expires="
            + exp.toGMTString() + ";path=/" + ";domain=" + mydomain;
    } else {
        document.cookie = name + "=" + escape(value) + ";expires="
            + exp.toGMTString() + ";path=/";
    }
}

// 转换cookies时间
function getsec(str) {
    var str1 = str.substring(1, str.length) * 1;
    var str2 = str.substring(0, 1);
    if (str2 == "s") {
        return str1 * 1000;
    } else if (str2 == "h") {
        return str1 * 60 * 60 * 1000;
    } else if (str2 == "d") {
        return str1 * 24 * 60 * 60 * 1000;
    }
}

/**
 * 删除指定域名下的共享cookie.二级域名可用
 * @param name
 * @param domain
 */
function DeleteCookieDomain(name, domain) {
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval = getCookie(name);
    if (isNotEmpty(domain)) {
        document.cookie = name + "=" + escape(cval) + ";expires="
            + exp.toGMTString() + ";path=/" + ";domain=" + domain;
    } else {
        document.cookie = name + "=" + escape(cval) + ";expires="
            + exp.toGMTString() + ";path=/";
    }
}

/**
 * 创建Cookies 可设置域名
 * @param name
 * @param value
 * @param domain
 */
function SetCookieDomain(name, value, domain) {
    var Days = 2;
    var exp = new Date();
    exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
    if (isNotEmpty(domain)) {
        document.cookie = name + "=" + escape(value) + ";expires="
            + exp.toGMTString() + ";path=/" + ";domain=" + domain;
    } else {
        document.cookie = name + "=" + escape(value) + ";expires="
            + exp.toGMTString() + ";path=/";
    }
}

/**
 * 删除所有的cookie
 */
function clearCookie() {
    var strCookie = document.cookie;
    var arrCookie = strCookie.split("; "); // 将多cookie切割为多个名/值对
    for (var i = 0; i < arrCookie.length; i++) { // 遍历cookie数组，处理每个cookie对
        var arr = arrCookie[i].split("=");
        if (arr.length > 0)
            DeleteCookieDomain(arr[0], mydomain);
    }
}
/**
 * 获取URL中的参数
 * @param val
 * @returns
 */
function getParameter(val) {
    var uri = window.location.search;
    var re = new RegExp("" + val + "=([^&?]*)", "ig");
    return ((uri.match(re)) ? (uri.match(re)[0].substr(val.length + 1)) : null);
}

var Url = {
    encode: function (string) {
        return escape(this._utf8_encode(string));
    },
    decode: function (string) {
        return this._utf8_decode(unescape(string));
    },
    _utf8_encode: function (string) {
        string = string.replace(/\r\n/g, "\n");
        var utftext = "";

        for (var n = 0; n < string.length; n++) {

            var c = string.charCodeAt(n);

            if (c < 128) {
                utftext += String.fromCharCode(c);
            } else if ((c > 127) && (c < 2048)) {
                utftext += String.fromCharCode((c >> 6) | 192);
                utftext += String.fromCharCode((c & 63) | 128);
            } else {
                utftext += String.fromCharCode((c >> 12) | 224);
                utftext += String.fromCharCode(((c >> 6) & 63) | 128);
                utftext += String.fromCharCode((c & 63) | 128);
            }

        }

        return utftext;
    },

    // private method for UTF-8 decoding
    _utf8_decode: function (utftext) {
        var string = "";
        var i = 0;
        var c = c1 = c2 = 0;

        while (i < utftext.length) {

            c = utftext.charCodeAt(i);

            if (c < 128) {
                string += String.fromCharCode(c);
                i++;
            } else if ((c > 191) && (c < 224)) {
                c2 = utftext.charCodeAt(i + 1);
                string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
                i += 2;
            } else {
                c2 = utftext.charCodeAt(i + 1);
                c3 = utftext.charCodeAt(i + 2);
                string += String.fromCharCode(((c & 15) << 12)
                    | ((c2 & 63) << 6) | (c3 & 63));
                i += 3;
            }

        }
        return string;
    }
};


/**
 * String去空格函数
 * @returns
 */
String.prototype.trim = function () {
    return this.replace(/(^\s*)|(\s*$)/g, "");
};
String.prototype.ltrim = function () {
    return this.replace(/(^\s*)/g, "");
};
String.prototype.rtrim = function () {
    return this.replace(/(\s*$)/g, "");
};

/**
 * 去掉< >代码
 * @param data
 * @returns
 */
function ReplaceTagHTML(data) {
    var r = /\<(.+?)\>/gi;
    data = data.replace(r, "");
    return data;
}

/**
 * 去掉< >代码 然后截取指定长度
 * @param data
 * @returns
 */
function ReplaceTagHTMLSubLength(data, length) {
    var r = /\<(.+?)\>/gi;
    data = data.replace(r, "");
    if (data == null || data == '') {
        return null;
    } else {
        if (data.length <= length) {
            return data;
        } else {
            data = data.substring(0, length) + "...";
            return data;
        }
    }
    return data;
}

/**
 * 自定义JS.StringBuffer
 * @returns
 */
function StringBuffer() {
    this.data = [];
}
StringBuffer.prototype.append = function () {
    this.data.push(arguments[0]);
    return this;
};
StringBuffer.prototype.toString = function () {
    return this.data.join("");
};
String.prototype.replaceAll = function (reallyDo, replaceWith, ignoreCase) {
    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {
        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi" : "g")), replaceWith);
    } else {
        return this.replace(reallyDo, replaceWith);
    }
};

String.prototype.trim = function () {
    return this.replace(/(^\s*)|(\s*$)/g, "");
};
String.prototype.ltrim = function () {
    return this.replace(/(^\s*)/g, "");
};
String.prototype.rtrim = function () {
    return this.replace(/(\s*$)/g, "");
};

/**
 * 判断字符串是否为空
 * @param str
 * @returns {Boolean}
 */
function isNotEmpty(str) {
    if (str == null || str == "" || str.trim() == '') {
        return false;
    }
    return true;
}

function isEmpty(str) {
    if (str == null || str == "" || str.trim() == '') {
        return true;
    }
    return false;
}


//BASE64 encode and decode
var BASE64 = {
    enKey: 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/',
    deKey: new Array(
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,
        52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1,
        -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
        15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,
        -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
        41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1
    ),
    encode: function (src) {
        var str = new Array();
        var ch1, ch2, ch3;
        var pos = 0;
        while (pos + 3 <= src.length) {
            ch1 = src.charCodeAt(pos++);
            ch2 = src.charCodeAt(pos++);
            ch3 = src.charCodeAt(pos++);
            str.push(this.enKey.charAt(ch1 >> 2), this.enKey.charAt(((ch1 << 4) + (ch2 >> 4)) & 0x3f));
            str.push(this.enKey.charAt(((ch2 << 2) + (ch3 >> 6)) & 0x3f), this.enKey.charAt(ch3 & 0x3f));
        }
        if (pos < src.length) {
            ch1 = src.charCodeAt(pos++);
            str.push(this.enKey.charAt(ch1 >> 2));
            if (pos < src.length) {
                ch2 = src.charCodeAt(pos);
                str.push(this.enKey.charAt(((ch1 << 4) + (ch2 >> 4)) & 0x3f));
                str.push(this.enKey.charAt(ch2 << 2 & 0x3f), '=');
            } else {
                str.push(this.enKey.charAt(ch1 << 4 & 0x3f), '==');
            }
        }
        return str.join('');
    },
    decode: function (src) {
        var str = new Array();
        var ch1, ch2, ch3, ch4;
        var pos = 0;
        src = src.replace(/[^A-Za-z0-9\+\/]/g, '');
        while (pos + 4 <= src.length) {
            ch1 = this.deKey[src.charCodeAt(pos++)];
            ch2 = this.deKey[src.charCodeAt(pos++)];
            ch3 = this.deKey[src.charCodeAt(pos++)];
            ch4 = this.deKey[src.charCodeAt(pos++)];
            str.push(String.fromCharCode(
                (ch1 << 2 & 0xff) + (ch2 >> 4), (ch2 << 4 & 0xff) + (ch3 >> 2), (ch3 << 6 & 0xff) + ch4));
        }
        if (pos + 1 < src.length) {
            ch1 = this.deKey[src.charCodeAt(pos++)];
            ch2 = this.deKey[src.charCodeAt(pos++)];
            if (pos < src.length) {
                ch3 = this.deKey[src.charCodeAt(pos)];
                str.push(String.fromCharCode((ch1 << 2 & 0xff) + (ch2 >> 4), (ch2 << 4 & 0xff) + (ch3 >> 2)));
            } else {
                str.push(String.fromCharCode((ch1 << 2 & 0xff) + (ch2 >> 4)));
            }
        }
        return str.join('');
    }
};

function isEmail(str) {//email格式验证
    var email = /^[a-z]([a-z0-9]*[-_]?[a-z0-9]+)*@([a-z0-9]*[-_]?[a-z0-9]+)+[\.][a-z]{2,3}([\.][a-z]{2})?$/i;
    if (email.test(str)) {
        return true;
    }
    return false;
}
function isMobile(str) {//mobile格式验证
    //var mobile =/^(13[0-9]{9}|15[0-9]{9}|[0-9]{7,8}|0[0-9]{2,3}\-[0-9]{7,8}(\-[0-9]{0-4})?)$/;
    var mobile = /^(13[0-9]|14[5,7]|15[^4]|17[0,3,6-8]|18[0-9])[0-9]{8}$/;
    if (mobile.test(str)) {
        return true;
    }
    return false;
}
function isNumber(str) {//正整数验证
    var number = /^[0-9]+$/;
    if (number.test(str)) {
        return true;
    }
    return false;
}

function isNull(object) {
    if (typeof(object) == "undefined" || object == null || object == '') {
        return true;
    }
    return false;
}


function isNotNull(object) {
    return !isNull(object);
}

//自定义方法-IE下不支持indexOf方法
if (!Array.indexOf) {
    Array.prototype.indexOf = function (obj) {
        for (var i = 0; i < this.length; i++) {
            if (this[i] == obj) {
                return i;
            }
        }
        return -1;
    };
}
//自定义方法-数组去重复
Array.prototype.unique = function () {
    var newArr = []; //一个新的临时数组
    for (var i = 0; i < this.length; i++) { //遍历当前数组
        if (this[i] == "") {
            continue;
        }
        //如果当前数组的第i已经保存进了临时数组，那么跳过，否则把当前项push到临时数组里面
        if (newArr.indexOf(this[i]) == -1) {
            newArr.push(this[i]);
        }
    }
    return newArr;
}

//格式化时间
function formatDate(date, format) {
    var o = {
        "M+": date.getMonth() + 1, //month
        "d+": date.getDate(), //day
        "h+": date.getHours(), //hour
        "m+": date.getMinutes(), //minute
        "s+": date.getSeconds(), //second
        "q+": Math.floor((date.getMonth() + 3) / 3), //quarter
        "S": date.getMilliseconds() //millisecond
    }

    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
    }

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}

