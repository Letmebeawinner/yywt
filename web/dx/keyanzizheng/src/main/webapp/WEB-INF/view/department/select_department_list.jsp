<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<div id="contentwrapper" class="contentwrapper">
    <div class="contenttitle2">
        <h3>教职工列表</h3>
    </div><!--contenttitle-->
    <div class="overviewhead clearfix mb10">
        <div class="fl mt5">
            <form class="disIb" id="getDepartMentList" action="${ctx}/admin/rs/getEmployeeList.json" method="post">
                <div class="disIb ml20 mb10">
                    <span class="vam">部门名称 &nbsp;</span>
                    <label class="vam">
                        <input type="text" class="hasDatepicker" placeholder="输入部门名称" name="departMent.departmentName"
                               value="${departMent.departmentName}">
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
                <th class="head0 center">部门名称</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${departMentList}" var="departMent">
                <tr>
                    <td><input type="radio" name="departmentId" value="${departMent.id}" onclick="_radioClick()">${departMent.id}</td>
                    <td id="departmentName${departMent.id}">${departMent.departmentName}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div><!-- centercontent -->
</div>
