<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>干部统计</title>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">部门干部统计</h1>
        <span>
            <span style="color: red;">说明</span><br>
            本页面用来展示部门的干部人数统计。
        </span>
    </div>

    <div id="contentwrapper" class="contentwrapper">
        <!-- 数据显示列表，开始 -->
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <thead>
                <tr>
                    <th class="head0 center">序号</th>
                    <th class="head0 center">课题名称</th>
                    <th class="head0 center">培训人数</th>
                    <th class="head0 center">培训时间</th>
                </tr>
                </thead>
                <tbody id="emailSendRecordData">
                <c:forEach items="${trainStatisticList}" var="train" varStatus="index">
                    <tr>
                        <td class="center">${index.index + 1}</td>
                        <td class="center">${train.trainName}</td>
                        <td class="center">${train.trainCount}</td>
                        <td class="center">
                            <fmt:formatDate value="${train.trainTime}" pattern="yyyy-MM" />
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <!-- 数据显示列表，结束 -->
    </div>
</div>
</body>
</html>