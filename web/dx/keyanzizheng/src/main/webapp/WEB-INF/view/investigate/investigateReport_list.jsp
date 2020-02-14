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
        <h1 class="pagetitle">调研报告列表</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来编辑、删除调研报告信息，下载查看申报文件<br>
                2. 编辑调研报告信息：点击操作列中的<span style="color:red">编辑</span>按钮编辑调研报告信息；<br>
                3. 删除调研报告信息：点击操作列中的<span style="color:red">删除</span>按钮删除调研报告信息；<br>
                4. 下载申报文件：点击操作列中的<span style="color:red">文件下载</span>按钮下载申报文件。
        </span>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="getInvestigateReportList" action="${ctx}/admin/ky/getInvestigateReportList.json" method="post">
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
                    <th class="head0 ">调研方向名称</th>
                    <th class="head1">调研报告名称</th>
                    <th class="head1">调研部门名称</th>
                    <th class="head1">相关资料地址</th>
                    <th class="head1">相关介绍</th>
                    <th class="head1">审核状态</th>
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
                        <td>
                        <c:if test="${investigateReport.ifPass==1}">未审核</c:if>
                        <c:if test="${investigateReport.ifPass==2}">已通过审核</c:if>
                        <c:if test="${investigateReport.ifPass==3}">未通过审核</c:if>
                        </td>
                        <td class="center">
                            <a href="${investigateReport.relatedUrl}" class="stdbtn" target="_blank" title="文件下载">文件下载</a>
                            <a href="${ctx}/admin/ky/toUpdateInvestigateReport.json?id=${investigateReport.id}" class="stdbtn" title="编辑">编辑</a>
                            <a href="${ctx}/admin/ky/getInvestigateReportInfo.json?queryInvestigateReport.id=${investigateReport.id}" class="stdbtn" title="查看">查看</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delInvestigateReport(${investigateReport.id})">删除</a>
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