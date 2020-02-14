<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>任务待处理列表</title>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">任务待处理列表</h1>
    </div><!--pageheader-->
    <div style="margin-left: 20px;">
        <span style="color:red">任务待处理列表</span><br>
            1.本页面用于模型信息查看；<br>
            2.办理：点击<span style="color:red">办理</span>，办理该申请；<br>

    </div>
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
                    <th class="head0 center">ID</th>
                    <th class="head0 center">任务名</th>
                    <th class="head0 center">任务定义key</th>
                    <th class="head1">创建时间</th>
                    <th class="head1">流程定义名</th>
                    <th class="head1">流程定义版本</th>
                    <th class="head1">流程定义id</th>
                    <th class="head1">申請人</th>
                    <th class="head0 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${tasks}" var="task">
                    <tr>
                        <td>${task.id}</td>
                        <td>${task.name}</td>
                        <td>${task.taskDefKey}</td>
                        <td>${task.createTime}</td>
                        <td>${task.pdname}</td>
                        <td>${task.pdversion}</td>
                        <td>${task.proDefId}</td>
                        <td>${task.applyName}</td>
                        <td class="center">
                            <a href="${ctx}/admin/oa/task/to/complete.json?taskId=${task.id}" class="stdbtn" title="">办理</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- 分页，开始 -->
            <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
            <!-- 分页，结束 -->
        </div>
    </div>
</div>
</body>
</html>