<%@ page import="com.a_268.base.constants.BaseCommonConstants" %>
<%@ page import="com.tongji.common.CommonConstants" %>
<%@ page import="java.time.LocalDate" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="ctx" value="<%=CommonConstants.contextPath%>"/>
<c:set var="basePath" value="<%=BaseCommonConstants.basePath%>"/>
<c:set var="year" value="<%=LocalDate.now().getYear()%>"/>
<c:set var="month" value="<%=LocalDate.now().getMonth()%>"/>

