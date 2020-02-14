<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>档案列表</title>
</head>
<body>
<div class="tables">

    <div class="pageheader notab">
        <h1 class="pagetitle">档案列表</h1>

        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于档案信息列表查看；<br>
            <%--2.查询：输入查询条件，点击<span style="color:red">搜索</span>；<br>--%>
        </div>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">

        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <colgroup>
                    <col class="con0"/>
                    <col class="con1"/>
                    <col class="con0"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">&nbsp;ID</th>
                    <th class="head0 center">序号</th>
                    <th class="head0 center">部门名称</th>
                    <th class="head0 center">档案分类</th>
                    <th class="head0 center">档号</th>
                    <th class="head0 center">件号</th>
                    <th class="head0 center">责任者</th>
                    <th class="head0 center">文号</th>
                    <th class="head0 center">题名</th>
                    <th class="head0 center">日期</th>
                    <%--<th class="head0 center">页数</th>--%>
                    <%--<th class="head0 center">机构或问题</th>--%>
                    <%--<th class="head0 center">是否入库</th>--%>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${archiveList}" var="archive" varStatus="status">
                    <tr>
                        <td>
                            <input type="checkbox" value="${archive.id}" onclick="checkClick()" class = "telephone">
                        </td>
                        <td>${status.count}</td>
                        <td>${archive.departmentName}</td>
                        <td>${archive.typeName}</td>
                        <td>${archive.danghao}</td>
                        <td>${archive.jianhao}</td>
                        <td>${archive.author}</td>
                        <td>${archive.wenhao}</td>
                        <td>${archive.autograph}</td>
                        <td><fmt:formatDate value="${archive.archivedate}" pattern="yyyyMMdd"/></td>
                        <%--<td>${archive.pages}</td>--%>
                        <%--<td>${archive.orginzation}</td>--%>
                        <%--<td>--%>
                            <%--<c:if test = "${archive.stockFlag == 0}">--%>
                                <%--未入库--%>
                            <%--</c:if>--%>
                            <%--<c:if test = "${archive.stockFlag == 1}">--%>
                                <%--已入库--%>
                            <%--</c:if>--%>
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
<script>
    /**
     * 执行查询
     */
    function searchForm() {
        jQuery("#searchForm").submit();
    }
</script>
</body>
</html>