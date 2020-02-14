<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>管理员用户列表</title>
	</head>
	<body>
    <c:forEach items="${userList}" var="sysuser">
    ${sysuser.userName}<br/>
    </c:forEach>

	</body>
</html>