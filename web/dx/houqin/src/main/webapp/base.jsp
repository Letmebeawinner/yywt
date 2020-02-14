<%@ page import="com.houqin.common.CommonConstants"%>
<%@ page import="com.a_268.base.constants.BaseCommonConstants"%>
<%@ page import="java.util.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="smsPath" value="<%=CommonConstants.smsPath%>" />
<c:set var="ctx" value="<%=CommonConstants.contextPath%>"/>
<c:set var="basePath" value="<%=BaseCommonConstants.basePath%>"/>
<script type="text/javascript">
    var imagePath = "<%=CommonConstants.imgPath%>";
</script>
<%
    Calendar calendar = GregorianCalendar.getInstance();
    calendar.setTime(new Date());
    int endYear = calendar.get(GregorianCalendar.YEAR);
    List<Integer> monthList = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12);
%>
<c:set var="beginYear" value="2015"/>
<c:set var="endYear" value="<%= endYear%>"/>
<c:set var="monthList" value="<%= monthList%>"/>



