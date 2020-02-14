<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<div id="contentwrapper" class="contentwrapper">
    <div class="pr">
        <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
            <colgroup>
                <col class="con0" style="width:10%;"/>
                <col class="con1"/>
                <col class="con0" style="width:20%;"/>
            </colgroup>
            <thead>
            <tr>
                <th class="head0 center">&nbsp;ID</th>
                <th class="head0 center">功能名称</th>
                <th class="head0 center" style="width: 30%">功能链接</th>
            </tr>
            </thead>
            <tbody>

                <c:forEach items="${functionList}" var="function">
                    <tr>
                        <td>
                            <input type="checkbox" name="telephoneIds" value="${function.id}" onclick="functionClick()" class="function"<c:if test="${function.flag==1}">checked="checked"</c:if>>${function.id}
                        </td>
                        <td>${function.name}</td>
                        <td>${function.link}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <!-- 分页，开始 -->
        <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
        <!-- 分页，结束 -->
    </div>
</div>
