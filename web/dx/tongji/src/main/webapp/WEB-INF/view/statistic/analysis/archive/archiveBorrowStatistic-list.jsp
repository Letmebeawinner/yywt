<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>档案统计</title>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">档案借阅统计</h1>
        <span>
            <span style="color: red;">说明</span><br>
            本页面用来展示档案的借阅统计。
        </span>
    </div>

    <div id="contentwrapper" class="contentwrapper">
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
                    <th class="head0 center">序号</th>
                    <th class="head0 center">档案类型</th>
                    <th class="head0 center">借阅次数</th>
                    <th class="head0 center">比例(%)</th>
                </tr>
                </thead>
                <tbody id="emailSendRecordData">
                <c:forEach items="${borrowList}" var="borrow" varStatus="index">
                    <tr>
                        <td class="center">${index.index + 1}</td>
                        <td class="center">${borrow.name}</td>
                        <td class="center">${borrow.count}</td>
                        <td class="center">
                            <fmt:formatNumber value="${borrow.count / total * 100}" pattern="0.00" />
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