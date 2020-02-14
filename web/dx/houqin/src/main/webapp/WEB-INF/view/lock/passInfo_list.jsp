<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>房间列表</title>
    <script type="text/javascript">
        jQuery(function () {
            jQuery("select[name='searchFloor']").val(jQuery("input[name='searchOpt']").val())
        })

        function searchForm() {
            jQuery("#searchForm").submit();
        }

        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">房间使用记录</h1>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <%--<input type="hidden" value="${searchRoom}" name="searchOpt">--%>
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/passInfoList.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">房间 &nbsp;</span>
                        <label class="vam">
                            <select name="searchRoom">
                                <option value="">--请选择--</option>
                                <c:forEach items="${roomList}" var="room">
                                    <option value="${room.id}" <c:if test="${searchRoom==room.id}">selected</c:if>>${room.name}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                </div>
            </div>
        </div>
        <!-- 数据显示列表，开始 -->
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <thead>
                <tr>
                    <th class="head0 center">ID</th>
                    <%--<th class="head0 center">房间ID</th>--%>
                    <th class="head0 center">房间名称</th>
                    <th class="head0 center">用户名</th>
                    <th class="head0 center">卡号</th>
                    <th class="head0 center">刷卡时间</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${passInfoList}" var="passInfo" varStatus="index">
                    <tr>
                        <td>${index.count}</td>
                        <%--<td>${passInfo.bedroomid}</td>--%>
                        <td>${passInfo.bedRoomName}</td>
                        <td>${passInfo.userName}</td>
                        <td>${passInfo.cardId}</td>
                        <td>${fn:substring(passInfo.passTime, 0, 19)}</td>
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
</body>
</html>