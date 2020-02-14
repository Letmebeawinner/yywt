<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>发送消息</title>
    <script type="text/javascript" src="${basePath}/static/js/plugins/jquery-1.7.min.js"></script>
    <style type="text/css">
        textarea {
            resize: none;
        }

        .junbao{
            background: #da251d;
            color: #fff;
            font-size: 16px;
            display: inline-block;
            padding: 7px 25px;
        }
    </style>
    <script type="text/javascript">

        jQuery(function (){
            uploadFile("uploadFile", false, "myFile", imagePath, callbackFile);
        });

        function callbackFile(data){
            data=data.substr(2);
            data=data.substr(0,data.length-2);
            jQuery("#fileUrl").val(data).show();
            jQuery("#file").html('已上传：' + jQuery(".fileName").html());
            jQuery("#fileName").val(jQuery(".fileName").html());

        }

        function upFile(){
            jQuery("#file").html( '已上传：'+jQuery(".fileName").html());
            jQuery("fileName").val(jQuery(".fileName").html());
            jQuery("#uploadFile").uploadify('upload');
        }


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
                            jQuery('#tempShow').val('');
                            jQuery('#file').html('');
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
    </script>
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
                <p id="infoReceiver">
                    <label>接收人</label>
                    <span class="field" style="height: 20px">
                        <textarea name="receiverIds" id="receivers" rows="8" cols="7" class="mediuminput" style="display: none"></textarea>
                        <span id="tempShow"></span>
                    </span>
                    <span class="field" id="showUser">
                        <button class="stdbtn btn_red" onclick="showReceivers()">选择用户</button>&nbsp;&nbsp;&nbsp;&nbsp;<span >共选择<span id="num">0</span>人</span>
                    </span>
                </p>
                <p>
                    <label for="infoContent"><em style="color: red;">*</em>消息正文</label>
                    <span class="field">
                        <textarea name="infoRecord.content" id="infoContent" rows="8" cols="7" class="mediuminput" placeholder="消息正文"></textarea>
                    </span>
                </p>
                <p>
                    <label for="infoContent">上传附件 <br/><em style="color: red;">*</em>选择完成后，请点击确认上传</label>
                    <span class="field">
                        <input type="hidden" name="infoRecord.fileUrl"  id="fileUrl" />
                         <input type="hidden" name="infoRecord.fileName"   id="fileName" />
                         <input type="button" id="uploadFile" value="上传附件"/>
                         <a onclick="upFile()" href="javascript:void(0)" class="junbao upload_sate submit radius2">确认上传</a>
                         <center><h4  id="file"></h4></center>
                    </span>
                </p>
                <%--<p>--%>
                    <%--<label for="infoType"><em style="color: red;">*</em>消息类型</label>--%>
                    <%--<span class="field" id="infoType">--%>
                        <%--<select name="infoRecord.infoType" onchange="queryReceivers(this)">--%>
                            <%--<option value="3" selected="selected">发送所有人</option>--%>
                            <%--<option value="2">发送部分人</option>--%>
                        <%--</select>--%>
                    <%--</span>--%>
                <%--</p>--%>

                <p class="stdformbutton">
                    <input type="hidden" name="infoRecord.infoType" value="4"/>
                    <button type="button" class="submit radius2" onclick="sendInfo()">发送消息</button>
                    <button class="stdbtn" type="button" onclick="comeback()">返回</button>
                    <button type="reset" style="display: none">重置</button>
                </p>
            </form>
        </div>
    </div>
</div>
<script charset="utf-8" type="text/javascript" src="${ctx}/static/admin/js/email/email.js"></script>
<script charset="utf-8" type="text/javascript" src="${basePath}/static/admin/js/permission/utils.js"></script>
<link type="text/css" rel="stylesheet" href='${ctx}/static/uploadify/uploadify.css'/>
<script type="text/javascript" src="${ctx}/static/uploadify/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="${ctx}/static/uploadify/upload-file.js"></script>
</body>
</html>