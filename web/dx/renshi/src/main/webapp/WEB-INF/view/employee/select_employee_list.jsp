<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<div id="contentwrapper" class="contentwrapper">
    <div class="overviewhead clearfix mb10">
        <div class="fl mt5">
            <form class="disIb" id="getEmployeeList" action="${ctx}/admin/rs/getEmployeeList.json" method="post">
                <div class="disIb ml20 mb10">
                    <span class="vam">用户名 &nbsp;</span>
                    <label class="vam">
                        <input type="text" class="hasDatepicker" placeholder="输入用户名称" name="employee.name"
                               value="${employee.name}">
                    </label>
                </div>
            </form>
            <div class="disIb ml20 mb10">
                <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchForm()">搜 索</a>
                <a href="javascript: void(0)" class="stdbtn ml10" onclick="emptyForm()">重 置</a>
            </div>
        </div>
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
                <th class="head0 center">ID</th>
                <th class="head0 center">姓名</th>
                <th class="head1">性别</th>
                <th class="head1">民族</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${employeeList}" var="employee">
                <tr>
                    <td><input type="checkbox" name="employeeId" value="${employee.id}" onclick="radioClick()">${employee.id}</td>
                    <td id="employeeName${employee.id}">${employee.name}</td>
                    <td>
                        <c:if test="${employee.sex==0}">男</c:if>
                        <c:if test="${employee.sex==1}">女</c:if>
                    </td>
                    <td>${employee.nationality}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
