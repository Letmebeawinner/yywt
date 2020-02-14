<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>资讯列表</title>
    <script type="text/javascript">
        function clearVal() {
            jQuery(".hasDatepicker").val("");
            jQuery("#typeId,#isIndex").val("");
        }

        function submit() {
            jQuery("#from").submit();
        }

        function updStatus(id, status) {
            jQuery.ajax({
                type: "POST",
                dataType: "json",
                url: "/admin/menhu/article/updStatus.json",
                data: {"id": id, "status": status},
                cache: false,
                async: false,
                error: function (request) {
                    alert("网络异常，请稍后再试");
                },
                success: function (result) {
                    if (result.code == 0) {
                        window.location.reload();
                    } else {
                        alert("网络异常，请稍后再试");
                    }
                }
            });
        }


        function deleteArticle(id) {
            if (confirm("确认删除吗？")) {
                jQuery.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "/admin/menhu/article/deleteArticleById.json",
                    data: {"id": id, "status": status},
                    cache: false,
                    async: false,
                    error: function (request) {
                        alert("网络异常，请稍后再试");
                    },
                    success: function (result) {
                        if (result.code == 0) {
                            window.location.reload();
                        } else {
                            alert("网络异常，请稍后再试");
                        }
                    }
                });
            }

        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">资讯列表</h1>
        <span>
                    <span style="color:red">说明</span><br>
                    1. 本页面包括资讯的增加、修改、删除等相关的操作；<br>
                    2. 增加资讯：点击搜索部分最右侧的<span style="color:red">添加</span>按钮添加新的资讯；<br>
                    3. 更新资讯：点击资讯列表操作列中的<span style="color:red">编辑</span>按钮编辑资讯的信息；<br>
                    4. 禁用/恢复资讯：如果某个资讯为禁用状态，则资讯列表中的状态为<span style="color:red">禁用</span>，且操作列中有<span
                style="color:red">恢复</span>按钮。
                    如果某个资讯为正常状态，则资讯列表中的状态为<span style="color:red">正常</span>，且操作列中有<span
                style="color:red">禁用</span>按钮；<br>
            </span>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="from" method="post" action="${ctx}/admin/menhu/article/index.json">

                    <div class="tableoptions disIb mb10">
                        <span class="vam">标题：&nbsp;</span>
                        <label class="vam" for="title"></label>
                        <input id="title" style="width: 250px;" type="text" class="hasDatepicker" name="article.title" value="${article.title}" placeholder="标题">
                    </div>
                    <div class="tableoptions disIb mb10">
                        <span class="vam">类型：&nbsp;</span>
                        <label class="vam" for="typeId"></label>
                        <select name="article.typeId" id="typeId">
                            <option value="" selected="selected">--请选择--</option>
                            <c:forEach items="${articleTypeList}" var="list">
                                <option value="${list.id}" <c:if
                                        test="${list.id==article.typeId}"> selected="selected" </c:if>>${list.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: submit()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: clearVal()" class="stdbtn ml10">重 置</a>
                    <a href="${ctx}/admin/menhu/article/toAddArticle.json" class="stdbtn ml10">添 加</a>
                    <a href="${ctx}" class="stdbtn ml10" target="_blank">校园内网前台</a>
                </div>
            </div>
        </div>
        <!-- 搜索条件，结束 -->

        <!-- 数据显示列表，开始 -->
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">

                <thead>
                <tr>
                    <th class="head0 center">序号</th>
                    <th class="head1 center" width="15%">标题</th>
                    <th class="head1 center">作者</th>
                    <th class="head1 center">点击数量</th>
                    <th class="head1 center">文章类别</th>
                    <th class="head1 center">状态</th>
                    <th class="head1 center">创建时间</th>
                    <th class="head0 center">最后更新时间</th>
                    <th class="head1 center" style="width: 200px">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${articleList}" var="article" varStatus="index">
                    <tr>
                        <td>${index.count}</td>
                        <td>${article.title}</td>
                        <td>${article.author}</td>
                        <td>${article.clickTimes}</td>
                        <td>
                            <c:forEach items="${articleTypeList}" var="list">
                                <c:if test="${list.id==article.typeId}"> ${list.name}</c:if>
                            </c:forEach>
                        </td>
                        <td>
                            <c:if test="${article.status==0}">正常</c:if>
                            <c:if test="${article.status==1}">禁用</c:if>
                        </td>
                        <td>
                            <fmt:formatDate value="${article.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td>
                            <fmt:formatDate value="${article.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td class="center">
                            <a href="${ctx}/front/menhu/article/queryArticleInfo.json?id=${article.id}&typeId=${article.typeId}" class="stdbtn" title="预览" target="_blank">预览</a>
                            <c:if test="${article.status==0}">
                                <a class="stdbtn" title="编辑"
                                   href="${ctx}/admin/menhu/article/toUpdateArticle.json?id=${article.id}"><span>编辑</span></a>
                                <a href="javascript:updStatus('${article.id}','1')" class="stdbtn" title="禁用">禁用</a>
                            </c:if>
                            <c:if test="${article.status==1}"> <a href="javascript:updStatus('${article.id}','0')"
                                                                  class="stdbtn" title="恢复">恢复</a></c:if>
                            <a href="javascript:deleteArticle('${article.id}')" class="stdbtn" title="删除">删除</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- 分页，开始 -->
            <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
            <!-- 分页，结束 -->
        </div>
        <!-- 数据显示列表，结束 -->
    </div>
</div>
</body>
</html>