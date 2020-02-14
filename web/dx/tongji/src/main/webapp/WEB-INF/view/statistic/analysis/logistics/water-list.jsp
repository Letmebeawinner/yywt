<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>能源统计</title>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">用水统计</h1>
        <span>
            <span style="color: red;">说明</span><br>
            本页面用来展示用水的统计情况。
        </span>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <!-- 搜索开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/statistic/analysis/logistics/listWaterFee.json">
                    <div class="tableoptions disIb mb10">
                        <span class="field">开始时间 &nbsp;</span>
                        <label class="vam"></label>
                        <span class="field">
                            	<input value="${startTime}" name="startTime" id="startTimePicker" type="text" class="width100">
                        </span>
                    </div>
                    <div class="tableoptions disIb mb10">
                        <span class="field">结束时间 &nbsp;</span>
                        <label class="vam"></label>
                        <span class="field">
                            	<input value="${endTime}" name="endTime" id="endTimePicker" type="text" class="width100">
                        </span>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                </div>
            </div>
        </div>
        <!-- 搜索结束 -->
        <span>
            <c:if test="${startTime != null}">
                <fmt:parseDate value="${startTime}" var="start" />
                <fmt:formatDate value="${start}" pattern="yyyy-MM-dd" /> ~
                <c:choose>
                    <c:when test="${endTime != null}">
                        <fmt:parseDate value="${endTime}" var="end" />
                        <fmt:formatDate value="${end}" pattern="yyyy-MM-dd" />
                    </c:when>
                    <c:otherwise>至今，</c:otherwise>
                </c:choose>
            </c:if>
            共用水${totalConsume}吨，支出${totalExpense}元。
        </span>
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
                    <col class="con0"/>
                    <col class="con1"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head1 center">用水量(t)</th>
                    <th class="head0 center">用水支出(￥)</th>
                    <th class="head0 center">日期</th>
                </tr>
                </thead>
                <tbody id="emailSendRecordData">
                <c:forEach items="${perMonthList}" var="perMonth">
                    <tr>
                        <c:forEach items="${perMonth}" var="per">
                            <td class="center">${per.value[0]}</td>
                            <td class="center">${per.value[1]}</td>
                            <td class="center">${per.key}</td>
                        </c:forEach>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- 分页，开始 -->
            <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
            <!-- 分页，结束 -->
        </div>
        <!-- 数据显示列表，结束 -->
    </div>
</div>
<script type="text/javascript" src="${ctx}/static/admin/js/statistic.js"></script>
</body>
</html>