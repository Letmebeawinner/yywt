<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>新闻列表</title>
    <script type="text/javascript">
        /**
         * 执行查询
         */
        function searchForm() {
            jQuery("#newsForm").submit();
        }
        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val("");
        }

        /**
         * 删除新闻
         */
        function delNews(newsId) {
            if (confirm("删除后将无法恢复，是否继续")) {
                jQuery.ajax({
                    url: "${ctx}/admin/oa/deleteNews.json",
                    data: {
                        "id" : newsId
                    },
                    type: "POST",
                    dataType: "JSON",
                    async: false,
                    cache : false,
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
        <h1 class="pagetitle">新闻列表</h1>

    </div><!--pageheader-->
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于新闻列表查看；<br>
        2.查询：输入查询条件，点击<span style="color:red">搜索</span>；<br>
        3.编辑：点击<span style="color:red">编辑</span>，修改用印章信息；<br>
        4.查看：点击<span style="color:red">查看</span>，查看用印章信息；<br>
    </div>

    <div id="contentwrapper" class="contentwrapper">

        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="newsForm" action="${ctx}/admin/oa//queryAllNews.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">新闻标题 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="longinput" style="width: auto" placeholder="输入新闻标题" name="news.title" value="${news.title}">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">新闻类别 &nbsp;</span>
                        <label class="vam">
                            <select name = "news.newsTypeId">
                                <option value = "">--请选择--</option>
                                <c:forEach items = "${newsTypes}" var = "newsType">
                                    <option value = "${newsType.id}" <c:if test = "${news.newsTypeId == newsType.id}">selected = "selected"</c:if>>${newsType.name}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>

                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchForm()">搜 索</a>
                    <a href="javascript: void(0)" class="stdbtn ml10" onclick="emptyForm()">重 置</a>
                    <a href="${ctx}/admin/oa/toAddNews.json" class="stdbtn ml10" onclick="emptyForm()">添 加</a>
                </div>
            </div>
        </div>

        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <colgroup>
                    <col class="con0"/>
                    <col class="con1"/>
                    <col class="con0"/>
                    <col class="con1"/>
                    <col class="con0"/>
                    <col class="con1"/>
                    <col class="con0"/>
                    <col class="con1"/>
                </colgroup>
                <thead>
                <tr>
                        <th class="head1">id</th>
                        <th class="head1">新闻类别</th>
                        <th class="head0 center">标题</th>
                        <th class="head0 center">副标题</th>
                        <th class="head0 center">来源</th>
                        <th class="head0 center">作者</th>
                        <th class="head0 center">外部链接</th>
                        <%--<th class="head0 center">简介</th>--%>
                        <th class="head0 center">是否可评论</th>
                        <th class="head0 center">属性</th>
                        <%--<th class="head1">发布状态</th>--%>
                        <th class="head0 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${newsDtos}" var="newsDto">
                    <tr>
                        <td>${newsDto.id}</td>
                        <td>${newsDto.newsTypeName}</td>
                        <td>${newsDto.title}</td>
                        <td>${newsDto.subTitle}</td>
                        <td>${newsDto.source}</td>
                        <td>${newsDto.author}</td>
                        <td>${newsDto.link}</td>
                        <%--<td>${newsDto.brief}</td>--%>
                        <td>
                            <c:if test="${newsDto.canComment == 0}">不可评论</c:if>
                            <c:if test="${newsDto.canComment == 1}">可评论</c:if>
                        </td>
                        <td>
                            <c:if test="${newsDto.hot == 1}">热点</c:if>
                            <c:if test="${newsDto.hot == 1 and newsDto.recommend == 1}">,</c:if>
                            <c:if test="${newsDto.recommend == 1}">推荐</c:if>
                        </td>
                        <td class="center">
                            <a href="${ctx}/admin/oa/toUpdateNews.json?id=${newsDto.id}&flag=0" class="stdbtn" title="编辑">编辑</a>
                            <a href="${ctx}/admin/oa/toUpdateNews.json?id=${newsDto.id}&flag=1" class="stdbtn" title="查看">查看</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delNews(${newsDto.id})">删除</a>
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