<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/base.jsp" %>
<html>
<head>
    <title>科研咨政管理系统</title>
    <style>
        html,body,.bodywrapper{height: 100%;}
    </style>
    <script type="text/javascript">
        jQuery(function () {
            // TODO 绑定域名后做修正

            // 当前URL的端口号
            var port = window.location.port
            // 线上
            if (port === "6684") {
                jQuery("#welcomeText").html("欢迎来到科研处管理系统！")
                jQuery("title").html("科研处管理系统")
            }
            if (port === "6699") {
                jQuery("#welcomeText").html("欢迎来到生态文明所管理系统！")
                jQuery("title").html("生态文明所管理系统")
            }

            // 线下
            if (port === "8084") {
                jQuery("#welcomeText").html("欢迎来到科研处管理系统！")
                jQuery("title").html("科研处管理系统")
            }

            if (port === "8094") {
                jQuery("#welcomeText").html("欢迎来到生态文明所管理系统！")
                jQuery("title").html("生态文明所管理系统")
            }
        })
    </script>
</head>
<body>
    <div class="centercontent tables rightFrame">
        <span class="dis tac" style="padding-top: 160px;"><img src="${basePath}/static/images/badge.png"></span>
        <p id="welcomeText" style="text-align: center;margin-top: 60px;font-size: 36px;color: #D20009;line-height: 40px;letter-spacing: 12px;">欢迎来到科研咨政管理系统!</p>
    </div>
</body>
</html>

