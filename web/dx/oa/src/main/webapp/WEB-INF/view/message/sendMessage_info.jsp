<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>详情</title>
</head>

<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">发件详情</h1>
    </div>
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于显示收件详情；<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label>接收人</label>
                    <span class="field">${sendInfo.receiverNames}</span>
                </p>
                <p>
                    <label>发送时间</label>
                    <span class="field">${sendInfo.sendTime}</span>
                </p>
                <p>
                    <label>发送内容</label>
                    <span class="field">
                            ${sendInfo.content}
                    </span>
                </p>

            </form>
            <br/>
        </div>
    </div>
</div>

</body>
</html>