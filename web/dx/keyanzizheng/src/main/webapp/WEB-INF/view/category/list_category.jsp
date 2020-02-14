<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>成果类型列表</title>
    <script type="text/javascript" src="${ctx}/static/admin/js/category.js"></script>
    <script type="text/javascript">
        jQuery(function () {
            jQuery("#resultFormId").val("${category.resultFormId}")
        })
    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab" style="margin-left: 10px">
        <h1 class="pagetitle">成果类型列表</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来展示成果类型列表、编辑成果类型。<br>
                2. 编辑成果类型信息：点击操作列中的<span style="color:red">编辑</span>按钮编辑成果类型信息。<br>
        </span>
    </div>

    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="listCategory" action="${ctx}/admin/ky/listCategory.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">成果类型名称 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" placeholder="输入成果类型名称" name="category.name"
                                   value="${category.name}">
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">成果形式 &nbsp;</span>
                        <label class="vam">
                            <select name="category.resultFormId" id="resultFormId">
                                <option value="">未选择</option>
                                <c:forEach items="${resultForms}" var="r">
                                    <option value="${r.id}">${r.name}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchForm()">搜 索</a>
                    <a href="javascript: void(0)" class="stdbtn ml10" onclick="emptyForm()">重 置</a>
                    <a href="${ctx}/admin/ky/saveCategory.json" class="stdbtn ml10">添加类型</a>
                </div>
            </div>
        </div>
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <thead>
                <tr>
                    <td class="center">标识</td>
                    <td class="center">成果形式名称</td>
                    <td class="center">成果类型名称</td>
                    <td class="center">添加时间</td>
                    <td class="center">修改时间</td>
                    <td class="center">
                        操作
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${categoryDTOList}" var="c" varStatus="status">
                    <tr>
                        <td class="center">${c.id}</td>
                        <td class="center">${c.resultFormName}</td>
                        <td class="center">${c.name}</td>
                        <td class="center"><fmt:formatDate value="${c.createTime}" pattern="yyyy-MM-dd"/></td>
                        <td class="center"><fmt:formatDate value="${c.updateTime}" pattern="yyyy-MM-dd"/></td>
                        <td class="center">
                            <a href="${ctx}/admin/ky/updateCategory.json?id=${c.id}" class="stdbtn"
                               title="编辑">编辑</a>
                            <%--<a class="stdbtn" title="删除" onclick="remove('${c.id}')">删除</a>--%>
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