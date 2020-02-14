<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>发送邮件</title>
    <style type="text/css">
        textarea {
            resize: none;
        }
    </style>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 30px">
        <h1 class="pagetitle">发送邮件</h1>
        <span>
            <span style="color: red;">说明</span><br>
            1. 本页面用来发送邮件，其中邮件标题、正文和收件人为必填项；<br>
            2. 选择收件人时有两种方式：<br>
            2.1. 自定义：手动输入收件人的邮箱，并使用英文状态下的","(逗号)分割；<br>
            2.2. 选择学员：根据用户选择收件人。<br>
            <span style="color: red">
            注意：<br>
                1. 选择完用户点击确定按钮后，被选择用户的邮箱会显示在页面上。
            点击邮箱右侧的<a class="close"><i class="fa fa-w c-999 fa-close"></i></a>可以删除该收件人；<br>
                2. 收件人接收邮件视网络状况等不可控因素影响可能会有延迟
            </span>。
        </span>
    </div>

    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="${ctx}/admin/sms/email/sendEmail.json" onsubmit="return false;" id="sendEmail" enctype="multipart/form-data">
                <p>
                    <label for="emailSubject"><em style="color: red;">*</em>邮件主题</label>
                    <span class="field">
                        <input type="text" name="email.subject" id="emailSubject" class="longinput" style="width: 60%" placeholder="邮件主题"/>
                    </span>
                </p>
                <p>
                    <label for="emailContent"><em style="color: red;">*</em>邮件正文</label>
                    <span class="field">
                        <textarea name="email.content" id="emailContent" rows="8" cols="7" class="mediuminput" placeholder="邮件正文"></textarea>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>收件人添加方式</label>
                    <span class="field" style="width: 15%">
                        <select name="email.custom" onchange="queryReceivers(this)">
                            <option value="1" selected="selected">自定义</option>
                            <option value="2">选择用户</option>
                        </select>
                    </span>
                </p>
                <p>
                    <label for="receivers"><em style="color: red;">*</em>收件人邮箱</label>
                    <span class="field">
                        <textarea name="email.receivers" id="receivers" rows="8" cols="7" class="mediuminput" placeholder="自定义添加收件人，需手动输入收件人邮箱"></textarea>
                        <span id="tempShow"></span>
                    </span>
                    <span class="field" id="showUser" style="display: none">
                        <button class="stdbtn btn_red" onclick="showUsers()">选择用户</button>
                    </span>
                </p>
                <p class="stdformbutton">
                    <button type="button" class="submit radius2" onclick="sendEmail()">发送邮件</button>
                    <button class="stdbtn" type="button" onclick="comeback()">返回</button>
                </p>
            </form>
        </div>
    </div>
</div>
<script charset="utf-8" type="text/javascript" src="${ctx}/static/admin/js/email/email.js"></script>
<script charset="utf-8" type="text/javascript" src="${basePath}/static/admin/js/permission/utils.js"></script>
</body>
</html>