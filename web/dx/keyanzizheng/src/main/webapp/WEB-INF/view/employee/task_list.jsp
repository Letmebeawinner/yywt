<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>课题列表</title>
    <script type="text/javascript">
        /**
         * 执行查询
         */
        function searchForm() {
            jQuery("#gettaskList").submit();
        }
        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }
    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab">
        <h1 class="pagetitle">课题列表</h1>

    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="gettaskList" action="${ctx}/admin/ky/getEmployeeTaskList.json" method="post">
                    <input type="hidden" name="queryTask.employeeId" value="${queryTask.employeeId}">
                    <div class="disIb ml20 mb10">
                        <span class="vam">ID &nbsp;</span>
                        <label class="vam">
                            <input type="text" onkeyup='this.value=this.value.replace(/\D/gi,"")' class="hasDatepicker" placeholder="输入ID" name="queryTask.id" value="${queryTask.id}">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">课题名称 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" placeholder="输入名称" name="queryTask.name" value="${queryTask.name}">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">关键字 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" placeholder="输入关键字" name="queryTask.keyword"
                                   value="${queryTask.keyword}">
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
                    <th class="head0 center">id</th>
                    <th class="head1">课题名称</th>
                    <th class="head1">立项时间</th>
                    <th class="head1">是否入库</th>
                    <td class="head0 center">
                        操作
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${taskList}" var="task">
                    <tr>
                        <td>${task.id}</td>
                        <td>${task.name}</td>
                        <td><fmt:formatDate value="${task.projectDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>
                            <c:if test="${task.intoStorage=='1'}"> 否 </c:if>
                            <c:if test="${task.intoStorage=='2'}"> 是 </c:if>
                        </td>
                        <td class="center">
                            <a href="${ctx}/admin/ky/getTaskInfo.json?id=${task.id}" class="stdbtn" title="查看">查看</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- 分页，开始 -->
            <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
            <!-- 分页，结束 -->
        </div><!-- centercontent -->
    </div>
</div>
<script type="text/javascript" src="${ctx}/static/admin/js/task.js"></script>
</body>
</html>