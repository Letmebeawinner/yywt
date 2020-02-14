<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>排班</title>
    <script type="text/javascript">
        var duties = ${duties};
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
    <div class="pageheader notab">
        <h1 class="pagetitle">排班</h1>
    </div><!--pageheader-->
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于排班信息展示，修改，删除；<br>
        2.拖动人名放到对应的时间上，即是安排那人在那天进行值班；<br>
        3.点击日历里面的人名可删除对应值班人员的值班信息<br>
        4.日历可分为月周日进行显示，即mongth，week，day，右边<< >>可进行时间的选择<br>
    </div>
    <div id="contentwrapper" class="contentwrapper withrightpanel">

        <div id="calendar"></div>
        <div id="tip" style="display: none">
            <p>用户编号:<span id="courseNameP"></span></p>
        </div>
    </div>

    <div class="rightpanel">
        <div class="rightpanelinner">
            <div class="widgetbox">
                <div class="title"><h4>待选用户</h4></div>
                <div class="widgetcontent">
                    <div id="external-events">
                        <c:forEach items = "${sysUsers}" var = "sysUser">
                            <div class="external-event" id="${sysUser.id}">${sysUser.userName}</div>
                        </c:forEach>
                    </div>

                </div><!--widgetcontent-->


            </div><!--widgetbox-->
        </div><!--rightpanelinner-->
    </div>
</div>
<form method="post" action="">
</form>
<script type="text/javascript" src="${ctx}/static/plugins/jquery-1.5.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/plugins/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/static/plugins/jquery.cookie.js"></script>
<script type='text/javascript' src='${ctx}/static/plugins/fullcalendar.min.js'></script>
<script type="text/javascript" src="${ctx}/static/custom/general.js"></script>
<script type="text/javascript" src="${ctx}/static/custom/calendar.js"></script>
<script type="text/javascript" src="${ctx}/static/plugins/kill-ie6.js"></script>
</body>
</html>