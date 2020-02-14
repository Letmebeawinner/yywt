<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>课题操作记录列表</title>
    <script type="text/javascript">
    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab" style="margin-left: 10px">
        <h1 class="pagetitle">课题操作记录列表</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来查看课题的操作、变更情况。
        </span>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <colgroup>
                    <col class="con0" style="width:10%;"/>
                    <col class="con1"/>
                    <col class="con0" style="width:60%;"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">序号</th>
                    <th class="head0">时间</th>
                    <th class="head1 center">操作内容</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${taskChangeList}" var="taskChange" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td><fmt:formatDate value="${taskChange.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>${taskChange.operate}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <form class="stdform stdform2" method="post" action="" id="">
                <p class="stdformbutton" style="text-align: center">
                    <input type="reset" class="reset radius2" value="返 回" onclick="javascript:history.go(-1);"/>
                </p>
            </form>
            <!-- 分页，开始 -->
            <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
            <!-- 分页，结束 -->
        </div><!-- centercontent -->
    </div>
</div>
</body>
</html>