<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>资讯列表</title>
    <link rel="stylesheet" href="/static/css/style.default.css" type="text/css" />
    <link rel="stylesheet" media="screen" href="/static/css/style.ie9.css"/>
    <link rel="stylesheet" media="screen" href="/static/css/style.ie8.css"/>
    <script src="/static/js/plugins/css3-mediaqueries.js"></script>
    <script type="text/javascript" src="/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="/static/js/html5.js"></script>
</head>

<body>
<div class="mb50">
    <section class="w1000">
        <div class="mt20">
            <section class="clearfix">
                <aside class="fr w300">

                </aside>
                <article class="fl w650">
                    <div class="">
                        <div class="pathwray" >
                            <ol class="clearfix c-master f-fM fsize14">
                                <li><a href="${ctx}">首页</a>&gt;</li>
                                <li class="action">
                                    <a href="${ctx}/front/menhu/article/queryArticleList.json?article.typeId=${article.typeId}">
                                        <c:if test="${article.typeId==1}">通知公告</c:if>
                                        <c:if test="${article.typeId==2}">公文处理</c:if>
                                        <c:if test="${article.typeId==3}">科研咨政</c:if>
                                        <c:if test="${article.typeId==4}">新闻资讯</c:if>
                                        <c:if test="${article.typeId==5}">信息公开</c:if>
                                        <c:if test="${article.typeId==6}">政策法规</c:if>
                                        <c:if test="${article.typeId==7}">教学动态</c:if>
                                        <c:if test="${article.typeId==8}">在线交流</c:if>
                                    </a>
                                </li>
                            </ol>
                        </div>
                    </div>
                    <div class="sli_r_title cmsInfor mb20">
                        <article>
                            <c:if test="${article.typeId==1}">通知公告</c:if>
                            <c:if test="${article.typeId==2}">公文处理</c:if>
                            <c:if test="${article.typeId==3}">科研咨政</c:if>
                            <c:if test="${article.typeId==4}">新闻资讯</c:if>
                            <c:if test="${article.typeId==5}">信息公开</c:if>
                            <c:if test="${article.typeId==6}">政策法规</c:if>
                            <c:if test="${article.typeId==7}">教学动态</c:if>
                            <c:if test="${article.typeId==8}">在线交流</c:if>
                        </article>
                        <aside class="fr">
                            <form style="padding-top: 4px" action="${ctx}/front/menhu/article/queryArticleList.json">
                                <input type="text"  name="article.title" value="${article.title}"
                                       style="background: #fcfcfc none repeat scroll 0 0; border: 1px solid #ccc; border-radius: 2px;
                                       box-shadow: 0 1px 3px #ddd inset; color: #666; padding: 8px 5px;vertical-align: middle;width: 200px" placeholder="标题" />
                                <input type="hidden" name="article.typeId"  value="${article.typeId}"  />
                                <input type="submit"
                                       style="background: rgba(0, 0, 0, 0) -moz-linear-gradient(center top , #f7f6f6, #eeeeee) repeat scroll 0 0;
                                        border: 1px solid #ccc;  border-radius: 2px; color: #333; cursor: pointer; font-weight: bold;opacity: 0.8; padding: 7px 10px;" value="搜索"/>
                            </form>
                        </aside>
                    </div>
                    <ul class="inforList">
                        <c:if test="${articleList!=null && articleList.size() > 0}">
                            <c:forEach items="${articleList }" var="article" varStatus="status">
                                <li>
                                    <c:choose>
                                        <c:when test="${typeId==6}">
                                            <a title="${article.name}" href="${ctx}/front/menhu/article/queryArticleInfo.json?id=${article.id}&typeId=${article.typeId}">${article.name}</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a title="${article.title}" href="${ctx}/front/menhu/article/queryArticleInfo.json?id=${article.id}&typeId=${article.typeId}">${article.title}</a>
                                        </c:otherwise>
                                    </c:choose>

                                    <span class="inforTime">
                                        <c:choose>
                                            <c:when test="${article.typeId==1||article.typeId==6||article.typeId==7}">
                                                ${fn:substring(article.createTime, 0, 10)}
                                            </c:when>
                                            <c:otherwise>
                                                <fmt:formatDate value="${article.createTime}" pattern="yyyy-MM-dd"/></span>
                                            </c:otherwise>
                                        </c:choose>
                                    </span>
                                </li>
                            </c:forEach>
                        </c:if>
                    </ul>
                </article>
                <article class="w300 fr">
                    <div class="imgShow">
                        <img src="/static/images/imgShow.jpg" alt="点击进入在线学习">
                    </div>
                    <div class="sli_r_title cmsInfor mb20 mt20">
                        <article>热门点击 </article>
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
        <div class="mt20">
            <jsp:include page="/WEB-INF/view/common/frontPage.jsp"/>
        </div>
    </section>
</div>
</body>
</html>
