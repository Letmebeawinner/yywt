<%@ page import="com.keyanzizheng.common.CommonConstants" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="ctx" value="<%=CommonConstants.contextPath%>"/>
<c:set var="imgPath" value="<%=CommonConstants.imageServicePath%>"/>
<c:set var="fileServicePath" value="<%=CommonConstants.fileServicePath%>"/>
<c:set var="smsPath" value="<%=CommonConstants.smsPath%>" />
<c:set var="basePath" value="<%=CommonConstants.basePath%>"/>

<script type="text/javascript">
    var imagePath = "<%=CommonConstants.imageServicePath%>";
</script>
<%
    Calendar calendar = GregorianCalendar.getInstance();
    calendar.setTime(new Date());
    int endYear = calendar.get(GregorianCalendar.YEAR);
%>
<c:set var="beginYear" value="2015" />
<c:set var="endYear" value="<%= endYear%>" />