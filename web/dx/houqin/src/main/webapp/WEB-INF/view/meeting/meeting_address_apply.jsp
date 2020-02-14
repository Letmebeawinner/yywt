<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>会场记录图</title>
    <script type="text/javascript">


    </script>
    <style type="text/css">
        div#tip {
            position: absolute;
            width: 100px;
            height: auto;
            border: 1px solid #00CC66;
        }
    </style>
</head>
<body>

<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle"><b>${meeting.name}:</b>会场记录图</h1>
        <span>
            <span style="color:red">说明</span><br>
            1.本页面用于为某会场的排期显示，可以以月视图、周视图、日视图</font>三中形式查看.<br>
        </span>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <!-- 数据显示列表，开始 -->
        <div class="pr">
            <div id="calendar"></div>
        </div>
    </div>
</div>
</div>

<input type="hidden" value="${meetingId}" id="meetingId">
<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.cookie.js"></script>
<script type='text/javascript' src='${ctx}/static/js/plugins/fullcalendar.min.js'></script>
<script type="text/javascript" src="${ctx}/static/js/custom/calendar.js"></script>
<script type="text/javascript" src="${ctx}/static/js/plugins/kill-ie6.js"></script>
<script type="text/javascript">

</script>
</body>
</html>