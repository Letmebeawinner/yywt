<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<%-- <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>议题列表</title>
</head>
<body>
<!-- 搜索条件，开始 -->
<div class="overviewhead clearfix mb10">
    <div class="fl mt5">
        <form class="disIb" id="searchForm"  action="${ctx}/admin/oa/ajax/queryMeetingTopics.json"
              method="post">
            <div class="disIb ml20 mb10">
                <span class="vam">议题名称 &nbsp;</span>
                <label class="vam">
                    <input style="width: auto;" name="oaMeetingTopic.name" type="text"
                           value="${oaMeetingTopic.name}" placeholder="请输入议题名称">
                </label>
            </div>
        </form>
        <div class="disIb ml20 mb10">
            <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
            <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
        </div>
    </div>
</div>
<!-- 搜索条件，结束 -->

<!-- 数据显示列表，开始 -->
<div class="pr sbClass">
    <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
        <colgroup>
            <col class="con0"/>
            <col class="con1"/>
            <col class="con0"/>
            <col class="con1"/>
            <col class="con0"/>
            <col class="con1"/>
        </colgroup>
        <thead>
        <tr>
            <th class="head0 center"><input id="checkAllTopic" type="checkbox" onclick="checkAllTopic(this)"/></th>
            <th class="head0 center">序号</th>
            <th class="head0 center">议题名称</th>
            <th class="head0 center">紧急程度</th>
            <%--<th class="head0 center">汇报人</th>--%>
            <%--<th class="head0 center">列席人</th>--%>
            <th class="head0 center">议题内容</th>
            <%--<th class="head0 center">状态</th>--%>
        </tr>
        </thead>
        <tbody>
        <c:if test="${oaMeetingTopicList !=null && oaMeetingTopicList.size()>0 }">
            <c:forEach items="${oaMeetingTopicList}" var="mt" varStatus="status">
                <tr>
                    <td style="width: 50px">
                        <input type="checkbox" value="${mt.id}" onclick="checkTopic(this)" class="topics">
                    </td>
                    <td>${status.count}</td>
                    <td id="topicName_${mt.id}">${mt.name}</td>
                    <td>${mt.emergencyDegree}</td>
                    <%--<td>${mt.reporter}</td>--%>
                    <%--<td>${mt.attendPeople}</td>--%>
                    <td>${mt.subjectContent}</td>
                    <%--<td>--%>
                        <%--<c:if test = "${mt.state == 0}">正常</c:if>--%>
                        <%--<c:if test = "${mt.status == 2}">已申请议程</c:if>--%>
                    <%--</td>--%>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>
    <!-- 分页，开始 -->
    <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
    <!-- 分页，结束 -->
    <script>
        function searchForm() {
            var param = jQuery('#searchForm').serialize();
            jQuery.ajax({
                url: '/admin/oa/ajax/queryMeetingTopics.json',
                data: param,
                type: 'POST',
                dataType: 'html',
                success: function (result) {
                    jQuery("#popup_message").html(result);
                }
            });
        }
    </script>
</body>
</html>