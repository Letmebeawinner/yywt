<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/base.jsp" %>
<html>
<head>
    <title>系统繁忙</title>
</head>
<body>
<div class="centercontent padding10">
    <div class="errorwrapper error403">
        <div class="errorcontent">
            <h1>系统繁忙</h1>

            <p>系统出现�?些小问题了！</p>
            <br />
            <button class="stdbtn btn_black" onclick="history.back()">返回之前的页�?</button> &nbsp;
            <button onclick="location.href='${basePath}/admin/index.json'" class="stdbtn btn_orange">系统首页</button>
        </div><!--errorcontent-->
    </div><!--errorwrapper-->
</div>
</body>
</html>
