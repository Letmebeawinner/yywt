<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>邮件发送详情</title>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 30px">
        <h1 class="pagetitle">邮件发送详情</h1>
        <span>
            <span style="color: red;">说明</span><br>
            本页面用来展示系统或管理员发送过的邮件详情。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="#" onsubmit="return false;" id="sendEmail">
                <p>
                    <label for="emailSubject">邮件主题</label>
                    <span class="field">
                        <input readonly="readonly" type="text" id="emailSubject" class="longinput" value="${email.subject}" style="width: 60%"/>
                    </span>
                </p>
                <p>
                    <label for="emailContent">邮件正文</label>
                    <span class="field">
                        <textarea readonly="readonly"  id="emailContent" rows="8" cols="5" style="resize: none;" class="mediuminput">${email.content}</textarea>
                    </span>
                </p>
                <p id="sender">
                    <label for="emailSender">发件人</label>
                    <span class="field">
                        <input readonly="readonly" type="text" id="emailSender" class="longinput" value="${sysUser.userName}" style="width: 60%"/>
                    </span>
                </p>
                <p id="receivers">
                    <label for="emailReceivers">收件人</label>
                    <span class="field">
                        <textarea readonly="readonly" id="emailReceivers" rows="5" cols="5" style="resize: none;" class="mediuminput">${email.receivers}</textarea>
                    </span>
                </p>
                <p id="createTime">
                    <label>发送时间</label>
                    <span class="field">
                        <fmt:formatDate value="${email.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
                    </span>
                </p>
                <p class="stdformbutton">
                    <a  class="stdbtn ml10" href="javascript:comeback();">返回</a>
                </p>
            </form>
        </div>
    </div>
</div>
<script charset="utf-8" type="text/javascript" src="${ctx}/static/admin/js/email/email.js"></script>
<script charset="utf-8" type="text/javascript" src="${ctx}/static/admin/js/email/utils.js"></script>
</body>
</html>