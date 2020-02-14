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
                    <th class="head0 center" rowspan="2">部门名称</th>
                    <th class="head0 center" colspan="2">性别</th>
                    <th class="head0 center" colspan="3">政治面貌</th>
                    <th class="head0 center" colspan="2">干部</th>
                    <th class="head0 center" rowspan="2">总人数</th>
                </tr>
                <tr>
                    <th class="head0 center">男性</th>
                    <th class="head0 center">女性</th>
                    <th class="head0 center">党员</th>
                    <th class="head0 center">无党派</th>
                    <th class="head0 center">群众</th>
                    <th class="head0 center">现任</th>
                    <th class="head0 center">后备</th>
                </tr>
                </thead>
                <tbody id="emailSendRecordData">
                <c:forEach items="${statisticList}" var="cadre" varStatus="">
                    <tr>
                        <td class="center">${cadre.departName}</td>
                        <td class="center">${cadre.male}</td>
                        <td class="center">${cadre.female}</td>
                        <td class="center">${cadre.communist}</td>
                        <td class="center">${cadre.community}</td>
                        <td class="center">${cadre.masses}</td>
                        <td class="center">${cadre.incumbent}</td>
                        <td class="center">${cadre.reserve}</td>
                        <td class="center">${cadre.total}</td>
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