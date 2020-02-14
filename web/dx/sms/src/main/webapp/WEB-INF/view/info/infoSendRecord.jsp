<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>消息发送详情</title>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 30px">
        <h1 class="pagetitle">消息发送详情</h1>
        <span>
            <span style="color: red;">说明</span><br>
            1. 本页面用来展示系统或管理员发送过的消息详情；<br>
            2. 如果消息是向所有用户发送的，接收人处将显示<strong>此消息发送给所有用户</strong>；
                否则显示所有接收人的姓名。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="#" onsubmit="return false;" id="sendEmail">
                <p>
                    <label>消息正文</label>
                    <span class="field">
                        ${infoRecord.content}&nbsp;
                    </span>
                </p>
                <p id="sender">
                    <label>发送人</label>
                    <span class="field">
                        ${sysUser==null?'系统发送':sysUser.userName}&nbsp;
                    </span>
                </p>
                <p id="receivers">
                    <label>接收人</label>
                    <span class="field">
                        <c:choose>
                            <c:when test="${infoRecord.infoType == 1}">此消息发送给所有用户</c:when>
                            <c:otherwise>
                                ${receivers}&nbsp;
                            </c:otherwise>
                        </c:choose>
                    </span>
                </p>
                <p id="createTime">
                    <label>发送时间</label>
                    <span class="field">
                        <fmt:formatDate value="${infoRecord.sendTime}" pattern="yyyy-MM-dd HH:mm:ss" />&nbsp;
                    </span>
                </p>
                <c:if test="${infoRecord.fileUrl!=null && infoRecord.fileUrl!=''}" >
                    <p id="file">
                        <label>查看附件</label>
                        <span class="field">
                            <%--<c:if test="${infoRecord.createTime<=sevenDaysAgo}">--%>
                                <%--<a href="javascript:alert('附件已过期');" title="点击下载">${infoRecord.fileName}</a>--%>
                            <%--</c:if>--%>
                            <%--<c:if test="${infoRecord.createTime>sevenDaysAgo}">--%>
                                <a href="javascript:openPageOffice('${infoRecord.id}')">${infoRecord.fileName}</a>
                            <%--</c:if>--%>
                    </span>
                    </p>
                    <p>
                        <label>下载附件</label>
                        <span class="field">
                            <a href="${infoRecord.fileUrl}" title="下载附件" download="${infoRecord.fileName}">${infoRecord.fileName}</a>
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
        var url = "${ctx}/admin/sms/open/infoRecordOfficialDocument.json?infoRecordId=" + infoRecordId + "&type=1"
        window.location.href = "javascript:POBrowser.openWindow('" + url + "','width=1200px;height=800px;')";
    }

    jQuery(function(){
        jQuery("#pobmodal-dialog").hide()
    })
</script>
</body>
</html>