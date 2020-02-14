<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/base.jsp" %>
<html>
<head>
    <title>没有操作权限</title>
</head>
<body>
    <div class="centercontent">
        <div id="contentwrapper" class="contentwrapper">
            <div style="height: 350px;margin: 0 auto;position: relative;top: 115%;width: 250px;text-align: center;">
                <span style="top: 50%;text-align: center;color: red;">
                   <h3>对不起，您没有操作权限,只有教师才有权限!</h3>
                </span>
                <button onclick="location.href='${basePath}/admin/index.json'" class="stdbtn btn_orange">系统首页</button>

            </div>
        </div>
    </div>
</body>
</html>
