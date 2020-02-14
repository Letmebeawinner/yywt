<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<body>
<div class="yt_box">
    <c:if test="${oaMeetingTopicList !=null && oaMeetingTopicList.size()>0 }">
        <c:forEach items="${oaMeetingTopicList}" var="oaMeetingTopic" varStatus="status">
            <p>${oaMeetingTopic.numStr}、${oaMeetingTopic.name} <c:if test="${oaMeetingTopic.isLook}"><button class="radius2 printHide" onclick = "window.open('${ctx}/admin/oa/queryMeetingTopic/info.json?id=${oaMeetingTopic.id}');return false;"> 查看</button></c:if></p>
            <p class="tD30">汇报：${oaMeetingTopic.reporter}</p>
            <p class="tD30">列席：${oaMeetingTopic.attendPeople}</p>
        </c:forEach>
    </c:if>
</div>
</body>
</html>