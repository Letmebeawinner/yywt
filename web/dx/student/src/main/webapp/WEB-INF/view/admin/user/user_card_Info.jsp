<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>查看房间信息</title>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">查看房间信息</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div class="pr" style="width: 300px;margin-top: 50px;font-size: 20px;margin-left: 20px">
            <p>您的所在的房间号:<b>${user.roomInformation}</b></p>
            <p>您的所在的住房卡号:<b>${user.cardNo}</b></p>
            <p>您的所在的考勤卡号:<b>${user.timeCardNo}</b></p>
        </div>
        <!-- 数据显示列表，结束 -->
    </div>
</div>
</body>
</html>