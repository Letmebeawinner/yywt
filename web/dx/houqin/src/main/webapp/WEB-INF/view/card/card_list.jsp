<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>开通一卡通</title>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">开通一卡通</h1>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <!-- 数据显示列表，开始 -->
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <thead>
                <tr>
                    <th class="head0 center">班级ID</th>
                    <th class="head0 center">班级名称</th>
                    <th class="head0 center">班级人数</th>
                    <th class="head0 center">班主任</th>
                    <th class="head0 center">开始时间</th>
                    <th class="head0 center">结束时间</th>
                    <th class="head0 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${classesList}" var="classes" varStatus="index">
                    <tr>
                        <td>${classes.id}</td>
                        <td>${classes.name}</td>
                        <td>${classes.studentTotalNum}</td>
                        <td>${classes.teacherName}</td>
                        <td>${classes.startTime}</td>
                        <td>${classes.endTime}</td>
                        <td class="center">
                            <a href="${ctx}/admin/houqin/openCardById/${classes.id}.json" class="stdbtn" title="编辑">去开通</a>

                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- 分页，结束 -->
        </div>
        <!-- 数据显示列表，结束 -->
    </div>
</div>
</body>
</html>