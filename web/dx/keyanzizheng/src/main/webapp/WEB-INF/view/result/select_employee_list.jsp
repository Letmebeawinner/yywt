<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<div id="contentwrapper" class="contentwrapper">
    <div class="contenttitle2">
        <h3>教职工列表</h3>
    </div><!--contenttitle-->
    <div class="overviewhead clearfix mb10">
        <div class="fl mt5">
            <form class="disIb" id="employeeList" action="${ctx}/admin/ky/ajax/task/selectEmployeeList.json" method="post">
                <div class="disIb ml20 mb10">
                    <span class="vam">ID &nbsp;</span>
                    <label class="vam">
                        <input type="text" onkeyup='this.value=this.value.replace(/\D/gi,"")' class="hasDatepicker" placeholder="输入用户ID" name="employee.id" value="${employee.id}">
                    </label>
                </div>
                <div class="disIb ml20 mb10">
                    <span class="vam">姓名 &nbsp;</span>
                    <label class="vam">
                        <input type="text" class="hasDatepicker" placeholder="输入用户名称" name="employee.name"
                               value="${employee.name}">
                    </label>
                </div>
            </form>
            <div class="disIb ml20 mb10">
                <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="_searchForm()">搜 索</a>
                <a href="javascript: void(0)" class="stdbtn ml10" onclick="_emptyForm()">重 置</a>
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
                    <td><input type="checkbox" name="employeeId" class="employeeId" value="${employee.id}" onclick="checkboxClick()">${employee.id}</td>
                    <td id="employeeName${employee.id}">${employee.name}</td>
                    <td>
                        <c:if test="${employee.sex==1}">男</c:if>
                        <c:if test="${employee.sex==2}">女</c:if>
                    </td>
                    <td>${employee.nationality}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div><!-- centercontent -->
</div>
