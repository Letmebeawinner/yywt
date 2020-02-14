<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>调研报告列表</title>
    <script type="text/javascript">
        /**
         * 执行查询
         */
        function searchForm() {
            jQuery("#getInvestigateReportList").submit();
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
        <h1 class="pagetitle">调研报告审核</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来进行调研方向的调研报告申报信息进行审核<br>
                2. 点击操作列中的<span style="color:red">文件下载</span>按钮下载查看申报文件，
            再点击<span style="color:red">审核通过</span>或<span style="color:red">审核不通过</span>按钮进行审核反馈。
        </span>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="getInvestigateReportList" action="${ctx}/admin/ky/toApproveInvestigateReport.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">调研报告名称 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" placeholder="输入调研报告名称" name="queryInvestigateReport.name" value="${queryInvestigateReport.name}">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">调研部门名称 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" placeholder="部门名称" name="queryInvestigateReport.departmentName" value="${queryInvestigateReport.departmentName}">
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
                    <th class="head0 ">调用方向名称</th>
                    <th class="head1">调研报告名称</th>
                    <th class="head1">调研部门名称</th>
                    <th class="head1">相关资料地址</th>
                    <th class="head1">相关介绍</th>
                    <td class="head0 center">
                        操作
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${investigateReportList}" var="investigateReport">
                    <tr>
                        <td>${investigateReport.id}</td>
                        <td>${investigateReport.researchName}</td>
                        <td>${investigateReport.name}</td>
                        <td>${investigateReport.departmentName}</td>
                        <td>${investigateReport.relatedUrl}</td>
                        <td>${investigateReport.info}</td>
                        <td class="center">
                            <a href="${investigateReport.relatedUrl}" class="stdbtn" title="文件下载">文件下载</a>
                            <a href="javascript:void(0)" class="stdbtn" title="审核通过" onclick="passInvestigateReport(${investigateReport.id},2)">审核通过</a>
                            <a href="javascript:void(0)" class="stdbtn" title="审核不通过" onclick="passInvestigateReport(${investigateReport.id},3)">审核不通过</a>
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
<script type="text/javascript" src="${ctx}/static/admin/js/investigate.js"></script>
</body>
</html>