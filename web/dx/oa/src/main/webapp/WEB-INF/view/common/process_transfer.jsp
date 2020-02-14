<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<div class="pr" style="margin-bottom: 50px;">
    <table cellpadding="0" cellspacing="0" border="0" class="stdtable printHide">
        <colgroup>
            <col class="con0" style="width:10%;"/>
            <col class="con1"/>
            <col class="con0" style="width:20%;"/>
        </colgroup>
        <thead>
        <tr>
            <th class="head0 center" width="20%">流转过程</th>
            <th class="head0 center" width="15%">开始时间</th>
            <th class="head0">结束时间</th>
            <th class="head0">办理人</th>
            <th class="head0" width="20%">意见</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${hisProcessEntities}" var="hisProcessEntity" varStatus="index">
            <tr>
                <td>${index.index + 1}、${hisProcessEntity.activitiName}</td>
                <td width="10%"><fmt:formatDate value="${hisProcessEntity.acitivitiStartTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                <td width="10%"><fmt:formatDate value="${hisProcessEntity.acitivitiEndTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                <td width="17%">${hisProcessEntity.assignee}</td>
                <td>${hisProcessEntity.option}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>