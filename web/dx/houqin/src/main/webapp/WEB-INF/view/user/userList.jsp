<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<div id="contentwrapper" class="contentwrapper">
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.用于显示所有学员<br>
    </div>
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
                <th class="head0 center">姓名</th>
                <th class="head0 center">学员学号</th>
            </tr>
            </thead>
            <tbody>

            <c:forEach items="${StudentList}" var="student">
                <tr>
                    <td>
                        <input type="checkbox" name="studentIds" value="${student.id}" onclick="userClick()" class="user">${employee.id}
                    </td>
                    <td>${student.name}</td>
                    <td>${student.studentId}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <!-- 分页，开始 -->
        <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
        <!-- 分页，结束 -->
    </div>
</div>
