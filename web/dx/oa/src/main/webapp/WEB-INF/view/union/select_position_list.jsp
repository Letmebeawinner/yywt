<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<div id="contentwrapper" class="contentwrapper">
    <div class="overviewhead clearfix mb10">
        <div class="fl mt5">
            <form class="disIb" id="getPositionList" action="${ctx}/admin/rs/getPositionList.json" method="post">
                <div class="disIb ml20 mb10">
                    <span class="vam">ID &nbsp;</span>
                    <label class="vam">
                        <input type="text" onkeyup='this.value=this.value.replace(/\D/gi,"")' class="hasDatepicker" placeholder="输入职位ID" name="position.id" value="${position.id}">
                    </label>
                </div>
                <div class="disIb ml20 mb10">
                    <span class="vam">职位名 &nbsp;</span>
                    <label class="vam">
                        <input type="text" class="hasDatepicker" placeholder="输入职位名称" name="position.name"
                               value="${position.name}">
                    </label>
                </div>
            </form>
            <div class="disIb ml20 mb10">
                <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchForm_()">搜 索</a>
                <a href="javascript: void(0)" class="stdbtn ml10" onclick="emptyForm_()">重 置</a>
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
                <th class="head0 center">职位ID</th>
                <th class="head0 center">职位名称</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${positionList}" var="position">
                <tr>
                    <td><input type="radio" name="positionId" class="positionId" value="${position.id}" onclick="radioClick()">${position.id}</td>
                    <td id="positionName${position.id}">${position.name}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
