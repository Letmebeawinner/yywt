<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/base.jsp" %>
<html>
<head>
    <title>访问路径错误-404</title>
</head>
<body>
    <div class="contentwrapper padding10">
        <div class="errorwrapper error403">
            <div class="errorcontent">
                <h1>访问路径不正�?</h1>

                <p>找不到页面，您访问的链接不存�?</p>
                <br />
                <button class="stdbtn btn_black" onclick="history.back()">返回之前的页�?</button> &nbsp;
                <button onclick="location.href='${basePath}/admin/index.json'" class="stdbtn btn_orange">系统首页</button>
            </div><!--errorcontent-->
        </div><!--errorwrapper-->
    </div>
</body>
</html>
