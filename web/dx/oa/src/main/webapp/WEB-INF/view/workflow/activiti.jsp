<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/2/6
  Time: 16:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello,World</title>
</head>
<body>
    <a href="/admin/oa/activiti.json">activiti</a>
    <form action="/admin/oa/create.json" method="get">
        <input type="text" id="name" name="name"/>
        <input type="text" id="key" name="key"/>
        <input type="text" id="description" name="description"/>
        <input type="submit" value="提交">
    </form>
</body>
</html>
