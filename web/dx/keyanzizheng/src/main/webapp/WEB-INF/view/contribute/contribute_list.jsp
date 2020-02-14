<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>投稿列表</title>
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
        <h1 class="pagetitle">投稿列表</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来新建、编辑、删除投稿<br>
                2. 新建投稿：点击搜索框中最后的<span style="color:red">投稿</span>按钮添加投稿；<br>
                3. 编辑投稿：点击操作列中的<span style="color:red">编辑</span>按钮编辑投稿的信息；<br>
                4. 删除投稿：点击操作列中的<span style="color:red">删除</span>按钮删除投稿的信息；<br>
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="getContributeList" action="${ctx}/admin/ky/getContributeList.json" method="post">
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
                    <a href="${ctx}/admin/ky/toAddContribute.json" class="stdbtn ml10">投 稿</a>
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
                            <a href="${ctx}/admin/ky/toUpdateContribute.json?id=${contribute.id}" class="stdbtn" title="编辑">编辑</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delContribute(${contribute.id})">删除</a>
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