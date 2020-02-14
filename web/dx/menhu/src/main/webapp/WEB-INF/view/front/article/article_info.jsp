<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>${article.title}</title>
    <link rel="stylesheet" href="/static/css/style.default.css" type="text/css"/>
    <link rel="stylesheet" media="screen" href="/static/css/style.ie9.css"/>
    <link rel="stylesheet" media="screen" href="/static/css/style.ie8.css"/>
    <script src="/static/js/plugins/css3-mediaqueries.js"></script>
    <script type="text/javascript" src="/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="/static/js/html5.js"></script>
</head>

<body class="">
<div class="mb50">
    <section class="w1000">
        <div class="mt20">
            <section class="clearfix">
                <aside class="fr w300">

                </aside>
                <article class="fl w650">
                    <div class="">
                        <div class="pathwray">
                            <ol class="clearfix c-master f-fM fsize14">
                                <li><a href="${ctx}">首页</a>&gt;</li>
                                <li class="action">
                                    <a href="${ctx}/front/menhu/article/queryArticleList.json?typeId=${article.typeId}">
                                        <c:if test="${article.typeId==1}">通知公告</c:if>
                                        <c:if test="${article.typeId==2}">公文处理</c:if>
                                        <c:if test="${article.typeId==3}">科研咨政</c:if>
                                        <c:if test="${article.typeId==4}">新闻资讯</c:if>
                                        <c:if test="${article.typeId==5}">信息公开</c:if>
                                        <c:if test="${article.typeId==6}">政策法规</c:if>
                                        <c:if test="${article.typeId==7}">教学动态</c:if>
                                        <c:if test="${article.typeId==8}">在线交流</c:if>
                                    </a>
                                    &gt;
                                </li>
                                <li class="action">${article.title}</li>
                            </ol>
                        </div>
                    </div>

                    <section class="title_one">
                        <h2 class="tac f-fM">${article.title}</h2>
                        <div class="title_two">
                            <span>来源：${article.source}</span>
                            <span>作者：${article.author}</span>
                            <span>浏览次数：${article.clickTimes}次</span>
                            <span>时间：
                                    <fmt:formatDate value="${article.createTime}" pattern="yyyy-MM-dd"/>
                            </span>
                        </div>
                    </section>


                    <div class="articleText ">
                        <div class="dan-nr">
                            ${article.content}
                            <%--<c:if test="${article.fileUrl!=null && article.fileUrl!=''}">--%>
                                <%--<br/>--%>
                                <%--<a href="${article.fileUrl}">点击下载附件内容</a>--%>
                            <%--</c:if>--%>
                        </div>
                    </div>
                    <div class="artBottom">
                        <c:choose>
                            <c:when test="${article.typeId==1||article.typeId==6||article.typeId==7}">
                            </c:when>
                            <c:otherwise>
                                <span class="mr20 c-333 fsize14">编辑：${article.author}</span>
                                <span class="c-333 fsize14">责任编辑：${article.author}</span>
                            </c:otherwise>
                        </c:choose>

                        <ul class="clearfix printBtn">
                            <li><a href="javascript:window.close()"><em class="icon12 close"></em>关闭本页</a></li>
                            <li style="margin-right: 12px;"><a href="javascript:window.print()"><em class="icon12"></em>打印本页</a>
                            </li>
                        </ul>
                    </div>
                </article>
                <article class="w300 fr">
                    <div class="imgShow">
                        <img src="/static/images/imgShow.jpg" alt="点击进入在线学习">
                    </div>
                    <div class="sli_r_title cmsInfor mb20 mt20">
                        <article>热门点击</article>
                    </div>
                    <ul class="c-c-list">
                        <c:if test="${hotArticle!=null && hotArticle.size() > 0}">
                            <c:forEach items="${hotArticle }" var="obj" varStatus="status">
                                <li>
                                    <span class="order nb-${status.count}">${status.count}</span>
                                    <a title="${obj.title}" href="${ctx}/front/menhu/article/queryArticleInfo.json?id=${obj.id}&typeId=${article.typeId}">
                                        <p>${obj.title}</p>
                                    </a>
                                </li>
                            </c:forEach>
                        </c:if>
                    </ul>
                </article>
            </section>
        </div>
    </section>
</div>
<script type="text/javascript">
    jQuery(function () {
        jQuery.ajax({
            url: "${ctx}/front/menhu/article/updateClickTimes.json",
            data: {"id":${article.id}},
            type: "post",
            dataType: "json",
            success: function (result) {
                if (result.code == "0") {
                } else {
                }
            }
        });
    });
</script>
</body>
</html>

