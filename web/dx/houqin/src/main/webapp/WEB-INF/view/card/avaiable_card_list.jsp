<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>剩余可用房间</title>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">剩余可用房间</h1>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <!-- 数据显示列表，开始 -->
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <thead>
                <tr>
                    <th class="head0 center">序号</th>
                    <th class="head0 center">用户id</th>
                    <th class="head0 center">卡号</th>
                    <th class="head0 center">用户名</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${roomList}" var="room" varStatus="index">
                    <tr>
                        <td>${index.count}</td>
                        <td>${room.userId}</td>
                        <td>${room.cridentialId}</td>
                        <td>${room.userName}</td>
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