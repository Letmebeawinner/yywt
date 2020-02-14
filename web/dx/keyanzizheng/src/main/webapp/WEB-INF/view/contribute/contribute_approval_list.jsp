<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>投稿审核列表</title>
    <script type="text/javascript">
        /**
         * 执行查询
         */
        function searchForm() {
            jQuery("#getContributeList").submit();
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

    <div class="pageheader notab" style="margin-left: 10px">
        <h1 class="pagetitle">投稿审核列表</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用管理员对未审核过的投稿进行审核<br>
                2. 通过点击操作列中的<span style="color:red">下载申请表</span>按钮查看申请信息<br>
                3. 通过申请表点击操作列中的<span style="color:red">录用</span>或<span style="color:red">不录用</span>按钮对稿件进行删选<br>
        </span>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">

        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="getContributeList" action="${ctx}/admin/ky/toContributeApprovalList.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">稿件名称 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" placeholder="输入稿件名称" name="queryContribute.name" value="${queryContribute.name}">
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
                    <th class="head0 center">序号</th>
                    <th class="head1">投稿人名称</th>
                    <th class="head1">稿件名称</th>
                    <th class="head1">投稿时间</th>
                    <th class="head1">审批状态</th>
                    <td class="head0 center">
                        操作
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${contributeList}" var="contribute" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${contribute.employeeName}</td>
                        <td>${contribute.name}</td>
                        <td><fmt:formatDate value="${contribute.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>
                            <c:if test="${contribute.ifPass==1}">未审批</c:if>
                            <c:if test="${contribute.ifPass==2}">通过</c:if>
                            <c:if test="${contribute.ifPass==3}">未通过</c:if>
                        </td>
                        <td class="center">
                            <a href="${contribute.formUrl}" class="stdbtn" title="下载申请表" download="">下载申请表</a>
                            <a href="javascript:void(0)" class="stdbtn" title="录用" onclick="passContribute(${contribute.id},2)">录用</a>
                            <a href="javascript:void(0)" class="stdbtn" title="不录用" onclick="passContribute(${contribute.id},3)">不录用</a>
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
<script type="text/javascript" src="${ctx}/static/admin/js/contribute.js"></script>
</body>
</html>