<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<script type="text/javascript" src="${ctx}/static/js/schedule/telephone.js"></script>
<div id="contentwrapper" class="contentwrapper">
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.教职工人员列表；<br>
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
                <th class="head0 center">编号</th>
                <th class="head0 center">姓名</th>
                <th class="head0 center">手机号</th>
                <th class="head0 center">邮箱号码</th>
            </tr>
            </thead>
            <tbody>

                <c:forEach items="${employeeList}" var="employee">
                    <tr>
                        <td>
                            <input type="checkbox" name="telephoneIds" value="${employee.id}" onclick="checkClick()" class="telephone" <c:if test="${employee.flag==1}">checked="checked"</c:if>>${employee.id}
                        </td>
                        <td>${employee.employeeNo}</td>
                        <td>${employee.name}</td>
                        <td>${employee.mobile}</td>
                        <td>${employee.email}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <!-- 分页，开始 -->
        <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
        <!-- 分页，结束 -->
    </div>
</div>
