<%@ page import="com.jiaowu.common.CommonConstants"%>
<%@ page import="com.a_268.base.constants.BaseCommonConstants"%>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="java.util.Date" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%--
<c:set var="smsPath" value="<%=CommonConstants.smsPath%>" />
--%>
<c:set var="ctx" value="<%=CommonConstants.contextPath%>"/>
<c:set var="imgPath" value="<%=CommonConstants.imageServicePath%>"/>
<c:set var="basePath" value="<%=CommonConstants.BASE_PATH%>"/>
<c:set var="smsPath" value="<%=CommonConstants.smsPath%>" />
<%--
班主任角色ID
--%>
<c:set var="teacherLeaderRoleId" value="<%=CommonConstants.CLASS_LEADER_ROLE_ID%>"/>
<%
    Calendar calendar = GregorianCalendar.getInstance();
    calendar.setTime(new Date());
    int endYear = calendar.get(GregorianCalendar.YEAR);
%>
<script type="text/javascript">
    var imagePath = "<%=CommonConstants.imageServicePath%>";
    <c:set var="beginYear" value="2011"/>
    <c:set var="endYear" value="<%= endYear%>"/>
</script>

