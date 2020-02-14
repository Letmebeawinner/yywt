<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>资讯类型列表</title>
	</head>
	<body>
    <div class="">
        <div id="contentwrapper" class="contentwrapper">
            <div class="pr">
                <form id = "searchForm" action="/admin/oa/ajax/news/type.json">
                    <input type = "hidden" name = "pagination.currentPage" value = "${pagination.currentPage}" id = "currentPage">
                    <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                        <thead>
                        <tr>
                            <th class="head0 center">类型编号</th>
                            <th class="head0 center">类型名</th>
                            <th class="head0 center">状态</th>

                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${typeList}" var="typeList" varStatus="index">
                            <tr>
                                <td><input type = "radio" name = "typeId" value = "${typeList.id}" onclick="getTypeId()">${index.count}</td>
                                <td>${typeList.name}</td>
                                <td>
                                    <c:if test="${typeList.status==0}">正常</c:if>
                                    <c:if test="${typeList.status==1}">锁定</c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </form>
                <!-- 分页，开始 -->
                <jsp:include page="/WEB-INF/view/common/alert_adminPage.jsp"/>
                <!-- 分页，结束 -->
            </div>
            <!-- 数据显示列表，结束 -->
        </div>
    </div>

    </body>
</html>