<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>消息接收详情</title>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 30px">
        <h1 class="pagetitle">消息接收详情</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="#" onsubmit="return false;" id="sendEmail">
                <p>
                    <label>消息正文</label>
                    <span class="field">
                        ${info.content}&nbsp;
                    </span>
                </p>
                <p id="sender">
                    <label>发送人</label>
                    <span class="field">
                        <c:if test="${sender==null || sender==''}">系统发送</c:if>
                        <c:if test="${sender!=null && sender!=''}">${sender}&nbsp;</c:if>
                    </span>
                </p>
                <p id="deliverTime">
                    <label>接收时间</label>
                    <span class="field">
                        <fmt:formatDate value="${info.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
                    </span>
                </p>
                <c:if test="${info.fileUrl!=null && info.fileUrl!=''}" >
                    <p id="file">
                        <label>查看文件</label>
                        <span class="field">
                            <%--<c:if test="${info.createTime<=sevenDaysAgo}">--%>
                                <%--<a href="javascript:alert('附件已过期');" title="点击查看">${info.fileName}</a>--%>
                            <%--</c:if>--%>
                            <%--<c:if test="${info.createTime>sevenDaysAgo}">--%>
                                <a href="javascript:openPageOffice('${info.id}')" title="点击查看">${info.fileName}</a>
                            <%--</c:if>--%>
                    </span>
                    </p>
                    <p>
                        <label>下载附件</label>
                        <span class="field">
                            <a href="${info.fileUrl}" title="下载附件" download="${info.fileName}">${info.fileName}</a>
                        </span>
                    </p>
                </c:if>
                <p class="stdformbutton" style="text-align: center">
                    <a  class="stdbtn ml10" href="javascript:comeback();">返回</a>
                </p>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript" src="/pageoffice.js" id="po_js_main"></script>
<script charset="utf-8" type="text/javascript" src="${ctx}/static/admin/js/email/email.js"></script>
<script charset="utf-8" type="text/javascript" src="${ctx}/static/admin/js/email/utils.js"></script>
<script type="text/javascript">
    jQuery(function(){
        jQuery("#pobmodal-dialog").hide()
    })
    function openPageOffice(infoRecordId) {
        var url = "${ctx}/admin/sms/open/infoRecordOfficialDocument.json?infoRecordId=" + infoRecordId
        window.location.href = "javascript:POBrowser.openWindow('" + url + "','width=1200px;height=800px;')";
    }

    jQuery(function(){
        jQuery("#pobmodal-dialog").hide()
    })
</script>
</body>
</html>