<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>调研方向列表</title>
    <script type="text/javascript">
        /**
         * 执行查询
         */
        function searchForm() {
            jQuery("#getResearchDirectionList").submit();
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
        <h1 class="pagetitle">调研方向列表</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来新增、编辑、删除调研方向，申报、查看调研报告信息<br>
                2. 新建调研方向：点击搜索框中最后的<span style="color:red">新建</span>按钮添加删除调研方向；<br>
                3. 编辑调研方向：点击操作列中的<span style="color:red">编辑</span>按钮编辑调研方向信息；<br>
                4. 删除调研方向：点击操作列中的<span style="color:red">删除</span>按钮删除调研方向信息；<br>
                5. 提交调研报告：未提交调研报告的调研方向，点击操作列中的<span style="color:red">提交调研报告</span>按钮提交调研报告；<br>
                6. 查看调研报告：已提交调研报告的调研方向，点击操作列中的<span style="color:red">查看调研报告</span>按钮查看调研报告。
        </span>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="getResearchDirectionList" action="${ctx}/admin/ky/getResearchDirectionList.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">调研方向名称 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" placeholder="输入调研方向名称" name="queryResearchDirection.name" value="${queryResearchDirection.name}">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">部门名称 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" placeholder="部门名称" name="queryResearchDirection.departmentName" value="${queryResearchDirection.departmentName}">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchForm()">搜 索</a>
                    <a href="javascript: void(0)" class="stdbtn ml10" onclick="emptyForm()">重 置</a>
                    <a href="${ctx}/admin/ky/toAddResearchDirection.json" class="stdbtn ml10">新 建</a>
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
                    <th class="head0 center">部门名称</th>
                    <th class="head0 center">申报人</th>
                    <th class="head0 center">调研方向名称</th>
                    <th class="head1">相关简介</th>
                    <td class="head0 center">
                        操作
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${researchDirectionList}" var="researchDirection">
                    <tr>
                        <td>${researchDirection.id}</td>
                        <td>${researchDirection.departmentName}</td>
                        <td>${researchDirection.employeeName}</td>
                        <td>${researchDirection.name}</td>
                        <td>${researchDirection.info}</td>
                        <td class="center">
                            <c:if test="${researchDirection.ifReport==1}">
                            <a href="${ctx}/admin/ky/toAddInvestigateReport.json?investigateReport.researchId=${researchDirection.id}" class="stdbtn" title="提交调研报告">提交调研报告</a>
                            </c:if>
                            <c:if test="${researchDirection.ifReport==2}">
                                <a href="${ctx}/admin/ky/getInvestigateReportInfo.json?queryInvestigateReport.researchId=${researchDirection.id}" class="stdbtn" title="查看调研报告">查看调研报告</a>
                            </c:if>
                            <a href="${ctx}/admin/ky/toUpdateResearchDirection.json?id=${researchDirection.id}" class="stdbtn" title="编辑">编辑</a>
                            <a href="${ctx}/admin/ky/getResearchDirectionInfo.json?id=${researchDirection.id}" class="stdbtn" title="查看">查看</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delResearchDirection(${researchDirection.id})">删除</a>
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
<script type="text/javascript" src="${ctx}/static/admin/js/researchDirection.js"></script>
</body>
</html>