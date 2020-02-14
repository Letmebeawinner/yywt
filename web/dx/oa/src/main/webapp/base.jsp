<%@ page import="com.oa.common.CommonConstants"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="smsPath" value="<%=CommonConstants.smsPath%>" />
<c:set var="ctx" value="<%=CommonConstants.contextPath%>"/>
<c:set var="imagePath" value="<%=CommonConstants.imagePath%>"/>
<c:set var="basePath" value="<%=CommonConstants.basePath%>"/>
<script type="text/javascript">
    var imagePath = "<%=CommonConstants.imagePath%>";
    var informationPath = "<%=CommonConstants.informationPath%>";
    var contextPath = '${ctx}';
    var basePath = "<%=CommonConstants.basePath%>"
    document.domain='127.0.0.1';
</script>


