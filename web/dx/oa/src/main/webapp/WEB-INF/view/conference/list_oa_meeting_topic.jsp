<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<%-- <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>我的议题列表</title>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader">
        <h1 class="pagetitle">我的议题列表</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br/>
            1.本页面展示议题。<br/>
            2.可通过议题名称查询对应的议题。<br/>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="/admin/oa/meetingTopic/list.json"
                      method="get">
                    <div class="disIb ml20 mb10">
                        <span class="vam">议题名称 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="oaMeetingTopic.name" type="text"
                                   value="${oaMeetingTopic.name}" placeholder="请输入议题名称">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">议题内容 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="oaMeetingTopic.subjectContent" type="text"
                                   value="${oaMeetingTopic.subjectContent}" placeholder="请输入议题内容">
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
        <div class="pr">
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
                    <th class="head0 center">序号</th>
                    <th class="head0 center">议题名称</th>
                    <th class="head0 center">议程年次</th>
                    <th class="head0 center">紧急程度</th>
                    <%--<th class="head0 center">汇报人</th>--%>
                    <%--<th class="head0 center">列席人</th>--%>
                    <th class="head0 center">议题内容</th>
                    <th class="head0 center">状态</th>
                    <th class="head0 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${oaMeetingTopicList !=null && oaMeetingTopicList.size()>0 }">
                    <c:forEach items="${oaMeetingTopicList}" var="mt" varStatus="status">
                        <tr>
                            <td>${status.count}</td>
                            <td>${mt.name}</td>
                            <td>
                                <c:if test="${mt.oaMeetingAgenda!=null && mt.oaMeetingAgenda!=''}">
                                    ${mt.oaMeetingAgenda.year}年第${mt.oaMeetingAgenda.frequency}次
                                </c:if>
                                <c:if test="${mt.oaMeetingAgenda==null || mt.oaMeetingAgenda==''}">
                                    --
                                </c:if>
                            </td>
                            <td>${mt.emergencyDegree}</td>
                            <%--<td>${mt.reporter}</td>--%>
                            <%--<td>${mt.attendPeople}</td>--%>
                            <td>${mt.subjectContent}</td>
                            <td>
                                <c:if test = "${mt.state == 2}">已申请议程</c:if>
                                <c:if test = "${mt.state != 2}">
                                    <c:if test = "${mt.audit == 0}">
                                        审核中
                                    </c:if>
                                    <c:if test = "${mt.audit == 1}">
                                        已通过
                                    </c:if>
                                    <c:if test = "${mt.audit == 2}">
                                        已拒绝
                                    </c:if>
                                </c:if>
                            </td>
                            <td class="center">
                                <a href="/admin/oa/queryMeetingTopic.json?id=${mt.id}" class="stdbtn" title="查看议题详情">查看议题详情</a>
                                <c:if test="${mt.oaMeetingAgenda!=null && mt.oaMeetingAgenda!=''}">
                                    <a href="/admin/oa/queryMeetingAgenda.json?id=${mt.oaMeetingAgenda.id}" class="stdbtn" title="查看议程详情">查看议程详情</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
            <!-- 分页，开始 -->
            <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
            <!-- 分页，结束 -->
        </div>
        <!-- 数据显示列表，结束 -->
    </div>
    <script>
        function searchForm() {
            jQuery("#searchForm").submit();
        }

        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }
    </script>
</body>
</html>