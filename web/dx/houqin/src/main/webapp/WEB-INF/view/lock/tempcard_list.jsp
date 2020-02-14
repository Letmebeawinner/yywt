<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>临时卡开卡记录</title>
    <script type="text/javascript">
        function searchForm() {
            jQuery("#searchForm").submit();
        }
        function emptyForm() {
            jQuery("input:text").val('');
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">临时卡开卡记录</h1>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <!-- 数据显示列表，开始 -->
        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/tempOpenCardList.json" method="post">

                    <div class="disIb ml20 mb10">
                        <span class="vam">姓名 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="tempCard.userName" type="text" class="hasDatepicker" value="${tempCard.userName}" placeholder="姓名">
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
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <thead>
                <tr>
                    <th class="head0 center">序号</th>
                    <th class="head0 center">姓名</th>
                    <th class="head0 center">房间号</th>
                    <th class="head0 center">住房卡号</th>
                    <th class="head0 center">考勤卡号</th>
                    <th class="head0 center">开始时间</th>
                    <th class="head0 center">结束时间</th>
                    <th class="head0 center">开房时间</th>
                    <th class="head0 center">操作</th>
                </tr>
                </thead>
                <tbody>

                <c:forEach items="${temCardList}" var="tem" varStatus="index">
                    <tr>
                        <td>${index.count}</td>
                        <td>${tem.userName}</td>
                        <td>${tem.roomNo}</td>
                        <td>${tem.cardNo}</td>
                        <td>${tem.timeCardNo}</td>
                        <td><fmt:formatDate value="${tem.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td><fmt:formatDate value="${tem.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td><fmt:formatDate value="${tem.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>
                            <a href="${ctx}/admin/houqin/queryRoomStatus.json?id=${tem.id}&roomId=${tem.roomId}" class="stdbtn" title="更换房间">更换房间</a>
                        </td>
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
</body>
</html>