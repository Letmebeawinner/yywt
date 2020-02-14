<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>成果形式列表</title>
    <script type="text/javascript">
        /**
         * 执行查询
         */
        function searchForm() {
            jQuery("#getResultFormList").submit();
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
        <h1 class="pagetitle">成果形式列表</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来展示成果形式列表、编辑成果形式、<br>
                2. 编辑成果形式信息：点击操作列中的<span style="color:red">编辑</span>按钮编辑成果形式信息；<br>
        </span>
    </div>

    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="getResultFormList" action="${ctx}/admin/ky/getResultFormList.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam" id="resultName">成果形式名称 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" placeholder="输入成果形式名称" name="resultForm.name" value="${resultForm.name}">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchForm()">搜 索</a>
                    <a href="javascript: void(0)" class="stdbtn ml10" onclick="emptyForm()">重 置</a>
                    <a href="${ctx}/admin/ky/toAddResultForm.json" class="stdbtn ml10">新建形式</a>
                </div>
            </div>
        </div>
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable" id="resultForm">
                <thead>
                <tr>
                    <td class="center">ID</td>
                    <td class="center">成果形式名称</td>
                    <td class="center">添加时间</td>
                    <td class="center">修改时间</td>
                    <td class="center">
                        操作
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${resultFormList}" var="resultForm">
                    <tr>
                        <td class="center">${resultForm.id}</td>
                        <td class="center">${resultForm.name}</td>
                        <td class="center"><fmt:formatDate value="${resultForm.createTime}" pattern="yyyy-MM-dd"/></td>
                        <td class="center"><fmt:formatDate value="${resultForm.updateTime}" pattern="yyyy-MM-dd"/></td>
                        <td class="center">
                            <a href="${ctx}/admin/ky/toUpdateResultForm.json?id=${resultForm.id}" class="stdbtn"
                               title="编辑">编辑</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>