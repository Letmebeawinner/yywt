﻿/*! v1.2 | pageoffice.js | (c) 2016, 2018 Beijing zhuozheng zhiyuan software, Inc.*/
function PO_checkPageOffice() {
    var bodyHtml = document.body.innerHTML;
    if (bodyHtml.indexOf("EC852C85-C2FC-4c86-8D6B-E4E97C92F821") < 0) {
        var poObjectStr = "";
        var explorer = window.navigator.userAgent;
        if (explorer.indexOf("MSIE") >= 0) {
            poObjectStr = "<div style=\"background-color:green;width:1px; height:1px;\">" + "\r\n" + "<object id=\"PageOfficeCtrl1\" height=\"100%\" width=\"100%\" classid=\"clsid:EC852C85-C2FC-4c86-8D6B-E4E97C92F821\">" + "</object></div>"
        } else {
            poObjectStr = "<div style=\"background-color:green;width:1px; height:1px;\">" + "\r\n" + "<object id=\"PageOfficeCtrl1\" height=\"100%\" width=\"100%\" type=\"application/x-pageoffice-plugin\" clsid=\"{EC852C85-C2FC-4c86-8D6B-E4E97C92F821}\">" + "</object></div>"
        }
        jQuery(document.body).append(poObjectStr)
    }
    try {
        var sCap = document.getElementById("PageOfficeCtrl1").Caption;
        if (sCap == null) {
            return false
        } else {
            return true
        }
    } catch (e) {
        return false
    }
}

function po_uuid(len, radix) {
    var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
    var uuid = [], i;
    radix = radix || chars.length;
    if (len) {
        for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random() * radix]
    } else {
        var r;
        uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
        uuid[14] = '4';
        for (i = 0; i < 36; i++) {
            if (!uuid[i]) {
                r = 0 | Math.random() * 16;
                uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r]
            }
        }
    }
    return uuid.join('')
}
var bPOIsInstalled = false;
var bPOBrowserOpened = false;
var POParent = po_uuid(8, 16);
var PO_code = "\150\164\164\160\72\57\57\61\62\67\56\60\56\60\56\61\72\65\67\60\67\60\57";
var PO_code2 = "\150\164\164\160\163\72\57\57\61\62\67\56\60\56\60\56\61\72\65\67\60\67\61\57";
var PO_datas;
var POBrowser = {
    isChromeAndGreaterThan42: function () {
        var e = "42";
        return this.getChromeVersion() >= e ? !0 : !1
    }, getChromeVersion: function () {
        var e, t = navigator.userAgent.toLowerCase(), n = /chrome/, o = /safari\/\d{3}\.\d{2}$/, i = /chrome\/(\S+)/;
        return n.test(t) && o.test(t) && i.test(t) ? e = RegExp.$1 : 0
    }, isChrome: function () {
        var e = navigator.userAgent.toLowerCase(), t = /chrome/;
        return t.test(e) ? !0 : !1
    }, isEdge: function () {
        var e = navigator.userAgent.toLowerCase(), t = /edge/;
        return t.test(e) ? !0 : !1
    }, isOldIE: function () {
        var e = navigator.userAgent.toLowerCase();
        return /msie/.test(e)
    }, getBrowserVer: function () {
        var e = navigator.userAgent.toLowerCase();
        return (e.match(/.+(?:rv|it|ra|ie)[\/: ]([\d.]+)/) || [])[1]
    }, checkPOBrowserSate: function () {
    }, strToHexCharCode: function (str) {
        if (str === "") return "";
        var hexCharCode = [];
        for (var i = 0; i < str.length; i++) {
            hexCharCode.push((str.charCodeAt(i)).toString(16))
        }
        return hexCharCode.join("").toUpperCase()
    }, checkSSL: function () {
        var strhref = window.location.href;
        strhref = strhref.toLowerCase();
        if (strhref.substr(0, 8) == "https://") {
            PO_code = PO_code2
        }
        return true
    }, getRootPath: function () {
        var pathName = document.getElementById('po_js_main').src;
        var index = pathName.indexOf("/pageoffice.js");
        return pathName.substr(0, index)
    }, showInstallDlg: function () {
        if (confirm("您需要安装PageOffice来打开文档。现在立即安装PageOffice吗？\r\n注意：安装完成后，请重新访问当前页面。")) {
            window.location.href = this.getRootPath() + "/posetup.exe"
        }
    }, openWindow: function (strURL, strOptions, strArgument) {
        if ((strURL == null) || (strURL == "")) {
            alert("The parameter strURL of openWindow() cannot be null or empty.");
            return
        }
        if (!bPOIsInstalled) {
            this.showInstallDlg();
            return;
        }
        if (strURL.charAt(0) != '/') {
            var strLower = strURL.toLowerCase();
            if ((strLower.substr(0, 7) == "http://") || (strLower.substr(0, 8) == "https://")) {
            } else {
                var pathName = window.location.href;
                var index = pathName.lastIndexOf("/");
                strURL = pathName.substr(0, index + 1) + strURL
            }
        } else {
            var pathName = window.location.href;
            var index = pathName.indexOf(window.location.pathname);
            strURL = pathName.substr(0, index) + strURL
        }
        jQuery.post(this.getRootPath() + "/poserver.zz", {
            Info: "PageOfficeLink",
            pageurl: strURL,
            options: strOptions,
            params: strArgument
        }, function (data) {
            PO_datas = data.split("\r\n");
            setTimeout("POBrowser.sendUserdata()", 50);
            location.href = PO_datas[0] + POParent;
            jQuery().showPobDlg();
            if (!bPOBrowserOpened) {
                bPOBrowserOpened = true;
                if (POBrowser.isOldIE() && (parseInt(POBrowser.getBrowserVer(), 10) >= 8) && window.XDomainRequest) setTimeout("POBrowser.callback3()", 300); else setTimeout("POBrowser.callback2()", 300)
            }
        })
    }, openWindowModeless: function (strURL, strOptions, strArgument) {
        if ((strURL == null) || (strURL == "")) {
            alert("The parameter strURL of openWindow() cannot be null or empty.");
            return
        }
        if (!bPOIsInstalled) {
            this.showInstallDlg();
            return;
        }
        if (strURL.charAt(0) != '/') {
            var strLower = strURL.toLowerCase();
            if ((strLower.substr(0, 7) == "http://") || (strLower.substr(0, 8) == "https://")) {
            } else {
                var pathName = window.location.href;
                var index = pathName.lastIndexOf("/");
                strURL = pathName.substr(0, index + 1) + strURL
            }
        } else {
            var pathName = window.location.href;
            var index = pathName.indexOf(window.location.pathname);
            strURL = pathName.substr(0, index) + strURL
        }
        jQuery.post(this.getRootPath() + "/poserver.zz", {
            Info: "PageOfficeLink",
            pageurl: strURL,
            options: strOptions + "IsModal=false;",
            params: strArgument
        }, function (data) {
            PO_datas = data.split("\r\n");
            var strToken = POBrowser.strToHexCharCode(PO_datas[0] + POParent);
            if (POBrowser.isOldIE() && (parseInt(POBrowser.getBrowserVer(), 10) >= 8) && window.XDomainRequest) {
                var xdr = new XDomainRequest();
                if (xdr) {
                    xdr.onload = function () {
                        var data = xdr.responseText;
                        if (data == "") {
                            setTimeout("POBrowser.sendUserdata()", 50);
                            location.href = PO_datas[0] + POParent;
                            if (!bPOBrowserOpened) {
                                bPOBrowserOpened = true;
                                setTimeout("POBrowser.callback3()", 300)
                            }
                        } else if (data == "false") {
                            setTimeout("POBrowser.sendUserdata()", 50);
                            location.href = PO_datas[0] + POParent + "|" + strToken;
                            if (!bPOBrowserOpened) {
                                bPOBrowserOpened = true;
                                setTimeout("POBrowser.callback3()", 300)
                            }
                        }
                    };
                    xdr.onerror = function (e) {
                    };
                    xdr.ontimeout = function () {
                    };
                    xdr.open("POST", PO_code + "checkopened.htm?x=" + po_uuid(8, 16));
                    xdr.send("token=" + strToken)
                } else {
                }
            } else {
                jQuery.ajax({
                    url: PO_code + "checkopened.htm?x=" + po_uuid(8, 16),
                    data: {"token": strToken},
                    dataType: "text",
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if (textStatus == "timeout") {
                        } else {
                        }
                    },
                    success: function (data, textStatus) {
                        if (textStatus == "success") {
                            if (data == "") {
                                setTimeout("POBrowser.sendUserdata()", 50);
                                location.href = PO_datas[0] + POParent;
                                if (!bPOBrowserOpened) {
                                    bPOBrowserOpened = true;
                                    setTimeout("POBrowser.callback2()", 300)
                                }
                            } else if (data == "false") {
                                setTimeout("POBrowser.sendUserdata()", 50);
                                location.href = PO_datas[0] + POParent + "|" + strToken;
                                if (!bPOBrowserOpened) {
                                    bPOBrowserOpened = true;
                                    setTimeout("POBrowser.callback2()", 300)
                                }
                            }
                        }
                    }
                })
            }
        })
    }, sendUserdata: function () {
        if (POBrowser.isOldIE() && (parseInt(POBrowser.getBrowserVer(), 10) >= 8) && window.XDomainRequest) {
            var xdr = new XDomainRequest();
            if (xdr) {
                xdr.onload = function () {
                };
                xdr.onerror = function (e) {
                };
                xdr.open("POST", PO_code + "userdata.htm");
                xdr.send("parent=" + POParent + "&Info=" + PO_datas[1])
            } else {
            }
        } else {
            jQuery.post(PO_code + "userdata.htm", {parent: POParent, Info: PO_datas[1]}, function (data) {
            })
        }
    }, getArgument: function () {
        try {
            return window.external.UserParams
        } catch (e) {
            alert(e.message + ' Please ensure that you call it in POBrowser.')
        }
    }, callParentJs: function (strFunc) {
        if ((strFunc == null) || (strFunc == "")) {
            alert("The parameter strFunc of callParentJs() cannot be null or empty.");
            return
        }
        try {
            strFunc = strFunc.replace(/&/g, '');
            var strRet = window.external.CallParentFunc(strFunc, POParent);
            if (strRet == "po_timeout5618_") {
                alert("The " + strFunc + " function is timeout.");
                strRet = ""
            }
            return strRet
        } catch (e) {
            alert(e.message + ' Please ensure that you call it in POBrowser.')
        }
    }, closeWindow: function () {
        try {
            return window.external.Close()
        } catch (e) {
            alert(e.message + ' Please ensure that you call it in POBrowser.')
        }
    }, callback2: function () {
        jQuery.ajax({
            url: PO_code + "func2.htm?x=" + po_uuid(8, 16),
            data: {"parent": POParent},
            dataType: "text",
            timeout: 180000,
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (textStatus == "timeout") {
                    setTimeout("POBrowser.callback2()", 300)
                } else {
                    setTimeout("POBrowser.callback2()", 300)
                }
            },
            success: function (data, textStatus) {
                if ((textStatus == "success") && (data != "null")) {
                    var parsedData = jQuery.parseJSON(data);
                    var vRet;
                    try {
                        vRet = eval(parsedData[0].name);
                    } catch (e) {
                        alert(e.message)
                    }
                    if (typeof(vRet) != "string") {
                        vRet = "undefined"
                    }
                    jQuery.post(PO_code + "funcret.htm", {id: parsedData[0].id, ret: vRet}, function (data) {
                    })
                }
                setTimeout("POBrowser.callback2()", 300)
            }
        })
    }, callback3: function () {
        var xdr = new XDomainRequest();
        if (xdr) {
            xdr.onload = function () {
                var data = xdr.responseText;
                if (data != "null") {
                    var parsedData = jQuery.parseJSON(data);
                    var vRet;
                    try {
                        vRet = eval(parsedData[0].name);
                    } catch (e) {
                        alert(e.message)
                    }
                    if (typeof(vRet) != "string") {
                        vRet = "undefined"
                    }
                    var xdr2 = new XDomainRequest();
                    if (xdr2) {
                        xdr2.onload = function () {
                        };
                        xdr2.onerror = function () {
                        };
                        xdr2.open("POST", PO_code + "funcret.htm");
                        xdr2.send("id=" + parsedData[0].id + "&ret=" + vRet)
                    } else {
                    }
                }
                setTimeout("POBrowser.callback3()", 300)
            };
            xdr.onerror = function (e) {
            };
            xdr.timeout = 180000;
            xdr.ontimeout = function () {
                setTimeout("POBrowser.callback3()", 300)
            };
            xdr.open("POST", PO_code + "func2.htm?x=" + po_uuid(8, 16));
            xdr.send("parent=" + POParent)
        } else {
        }
    }, addCssByLink: function (url) {
        var doc = document;
        var link = doc.createElement("link");
        link.setAttribute("rel", "stylesheet");
        link.setAttribute("type", "text/css");
        link.setAttribute("href", url);
        var heads = doc.getElementsByTagName("head");
        if (heads.length) heads[0].appendChild(link); else doc.documentElement.appendChild(link)
    }, includeJS: function (path) {
        var a = document.createElement("script");
        a.type = "text/javascript";
        a.src = path;
        var head = document.getElementsByTagName("head")[0];
        head.appendChild(a)
    }, resumePO: function () {
        jQuery.getJSON(PO_code + "resume.htm?parent=" + POParent + "&callback=?", function (data) {
        })
    }
};
var ZDFAPP = {
    openWindow: function (strURL, strOptions) {
        if ((strURL == null) || (strURL == "")) {
            alert("The parameter strURL of openWindow() cannot be null or empty.");
            return
        }
        jQuery.post(this.getRootPath() + "/poserver.zz", {Info: "ZDFAPPLink", pageurl: strURL}, function (data) {
            location.href = data
        })
    }
};
jQuery(function () {
    jQuery("body").append("<div class=\"pobmodal-overlay\"><div id=\"pobmodal-dialog\"><h2>提示</h2>当前文档正处于打开状态，请点击<a style='color:red;' href=\"javascript:POBrowser.resumePO();\" > 这里 </a>切换PageOffice窗口继续查看或编辑文档。<div class=\"button-holder\"></br></br><a class=\"button blue\" href=\"javascript:POBrowser.resumePO();\" > 立即切换 PageOffice 窗口</a></div></div></div>");
    var dialogPob = jQuery('#pobmodal-dialog').parent();
    dialogPob.find('.close').click(function () {
        dialogPob.trigger('hide')
    });
    dialogPob.click(function (e) {
    });
    dialogPob.on('hide', function () {
        dialogPob.fadeOut()
    }).on('show', function () {
        dialogPob.fadeIn()
    });
    jQuery.fn.showPobDlg = function () {
        dialogPob.trigger('show')
    };
    jQuery.fn.hidePobDlg = function () {
        dialogPob.trigger('hide')
    }
});
POBrowser.addCssByLink(POBrowser.getRootPath() + "/pobstyle.css");
POBrowser.checkSSL();
jQuery.getJSON(PO_code + "json.htm?callback=?", function (data) {
    jQuery.each(data, function (i, value) {
        if (value.name == "jsonx") {
            bPOIsInstalled = true;
            return false
        }
    })
});