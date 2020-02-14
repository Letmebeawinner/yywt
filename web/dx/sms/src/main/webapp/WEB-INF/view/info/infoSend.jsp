<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>发送消息</title>
    <style type="text/css">
        textarea {
            resize: none;
        }
    </style>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 30px">
        <h1 class="pagetitle">发送消息</h1>
        <span>
            <span style="color: red;">说明</span><br>
            1. 本页面用来发送消息，其中消息内容和消息类型为必填项；<br>
            2. 选择接收人时有两种方式：<br>
            2.1. 发送所有人：该消息将会发送给所有用户；<br>
            2.2. 发送部分人：根据用户选择接收人，此时接收人为必填项；<br>
            3. 选择完用户点击确定按钮后，被选择用户会显示在页面上。
            点击用户右侧的<a class="close"><i class="fa fa-w c-999 fa-close"></i></a>可以删除该接收人。<br>
        </span>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" onsubmit="return false;" id="sendInfo">
                <p>
                    <label for="infoContent"><em style="color: red;">*</em>消息正文</label>
                    <span class="field">
                        <textarea name="infoRecord.content" id="infoContent" rows="8" cols="7" class="mediuminput" placeholder="消息正文"></textarea>
                    </span>
                </p>

                <p>
                    <label for="infoType"><em style="color: red;">*</em>消息类型</label>
                    <span class="field" id="infoType">
                        <select name="infoRecord.infoType" onchange="queryReceivers(this)">
                            <option value="3" selected="selected">发送所有人</option>
                            <option value="2">发送部分人</option>
                        </select>
                    </span>
                </p>
                <p style="display: none" id="infoReceiver">
                    <label>接收人</label>
                    <span class="field" style="height: 20px">
                        <textarea name="receiverIds" id="receivers" rows="8" cols="7" class="mediuminput" style="display: none"></textarea>
                        <span id="tempShow"></span>
                    </span>
                    <span class="field" id="showUser" style="display: none">
                        <button class="stdbtn btn_red" onclick="showReceivers()">选择用户</button>&nbsp;&nbsp;&nbsp;&nbsp;<span>共选择<span id="num">0</span>人</span>
                    </span>
                </p>
                <p class="stdformbutton">
                    <button type="button" class="submit radius2" onclick="sendInfo()">发送消息</button>
                    <button class="stdbtn" type="button" onclick="comeback()">返回</button>
                    <button type="reset" style="display: none">重置</button>
                </p>
            </form>
        </div>
    </div>
</div>
<script charset="utf-8" type="text/javascript" src="${ctx}/static/admin/js/email/email.js"></script>
<script charset="utf-8" type="text/javascript" src="${ctx}/static/admin/js/info/info.js"></script>
<script charset="utf-8" type="text/javascript" src="${basePath}/static/admin/js/permission/utils.js"></script>
</body>
</html>