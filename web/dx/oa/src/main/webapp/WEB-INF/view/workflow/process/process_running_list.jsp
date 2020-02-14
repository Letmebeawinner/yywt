<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>正在运行的流程</title>
    <style type="text/css">
        .underline a{text-decoration: underline;}
    </style>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">正在运行的流程</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于正在运行的流程信息查看；<br>
            2.查看详情:<span style="color:red">查看详情</span>页，点击直接进入详情页；<br>
        </div>
    </div><!--pageheader-->
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
                    <th class="head0 center">序号</th>
                    <th class="head0 center">请求标题</th>
                    <th class="head0 center">事由</th>
                    <th class="head0 center">状态</th>
                    <th class="head1">流程运行图</th>
                    <th class="head1">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${processInstances}" var="processInstance">
                    <tr>
                        <td>${processInstance.id}</td>
                        <td>${processInstance.processDefinitionName}</td>
                        <td>${processInstance.reason}</td>
                        <td>${processInstance.currActivityName}</td>
                        <td class = "underline"><a href = "${ctx}/admin/oa/process/info.json?processInstanceId=${processInstance.id}" target="_blank" title = "${processInstance.processDefinitionName}">${processInstance.processDefinitionName}${processInstance.processDefinitionId}</a></td>
                        <td><a class="stdbtn"
                               href="${ctx}/admin/oa/history/process/info.json?processInstanceId=${processInstance.id}&processDefinitionKey=${processInstance.processDefinitionKey}">查看详情</a>
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