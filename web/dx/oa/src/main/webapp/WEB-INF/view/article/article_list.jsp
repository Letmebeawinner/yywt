<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>文章列表</title>
    <script type="text/javascript">
        /**
         * 执行查询
         */
        function searchForm() {
            jQuery("#sealForm").submit();
        }
        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        function delArticle(articleId) {
            if (confirm("删除后将无法恢复，是否继续")) {
                jQuery.ajax({
                    url: "${ctx}/admin/oa/deleteArticle.json",
                    data: {
                        "id": articleId
                    },
                    type: "POST",
                    dataType: "JSON",
                    async: false,
                    cache: false,
                    success: function (result) {
                        if (result.code == "0") {
                            window.location.reload();
                        } else {
                            alert(result.message);
                            return;
                        }
                    }
                });
            }
        }
    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab">
        <h1 class="pagetitle">文章列表</h1>
    </div>
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于文章信息列表查看；<br>
        2.查询：输入查询条件，点击<span style="color:red">搜索</span>；<br>
        3.编辑：点击<span style="color:red">编辑</span>，修改用文章信息；<br>
        4.查看：点击<span style="color:red">查看</span>，查看用文章信息；<br>
        5.删除：点击<span style="color:red">删除</span>，删除文章信息；<br>
    </div>

    <div id="contentwrapper" class="contentwrapper">

        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="sealForm" action="${ctx}/admin/oa/queryAllArticle.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">ID &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="longinput" placeholder="输入ID" style="width: auto"
                                   name="article.id" value="${article.id}">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">文章名称 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="longinput" placeholder="输入文章名称" style="width: auto"
                                   name="article.name" value="${article.name}">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">文章类别 &nbsp;</span>
                        <label class="vam">
                            <select name="article.type_id" style="width: 150px">
                                <option value="">--请选择--</option>
                                <c:forEach items="${articleTypeList}" var="articleType">
                                    <option value="${articleType.id}"
                                            <c:if test="${article.type_id== articleType.id}">selected="selected"</c:if>>${articleType.type_name}</option>
                                </c:forEach>
                            </select>
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
                    <th class="head0 center">ID</th>
                    <th class="head1">文章类型</th>
                    <th class="head0 center">文章名称</th>
                    <th class="head1">文章内容</th>
                    <th class="head0 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${articleDtoList}" var="article">
                    <tr>
                        <td>${article.id}</td>
                        <td><%--<c:forEach items="${articleTypeList}" var="articleType">
                            <c:if test="${articleType.id==article.type_id}">${articleType.type_name}</c:if>
                        </c:forEach>--%>
                        ${article.newArticleTypeName}
                        </td>
                        <td>${article.name}</td>
                        <td>${article.content}</td>
                        <td class="center">
                            <a href="${ctx}/admin/oa/toUpdateArticle.json?id=${article.id}&flag=0" class="stdbtn"
                               title="编辑">编辑</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delArticle(${article.id})">删除</a>
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