<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>科研课题列表</title>
    <script type="text/javascript">
        /**
         * 执行查询
         */
        function searchForm() {
            jQuery("#postForm").submit();
        }

        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val("");
            jQuery("select").val("");
        }

    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab" style="margin-left: 10px">
        <h1 class="pagetitle">科研课题列表</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来查看课题列表<br>
        </span>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="postForm" action="${ctx}/admin/ky/myTaskResultList.json?resultType=1"
                      method="post">

                    <div class="disIb ml20 mb10">
                        <span class="vam">课题名称&nbsp;</span>
                        <label class="vam">
                            <input type="text" value="${resultName}" name="resultName">
                        </label>
                    </div>
                </form>

                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchForm()">搜 索</a>
                    <a href="javascript: void(0)" class="stdbtn ml10" onclick="emptyForm()">重 置</a>

                </div>

                <div class="disIb ml20 mb10">
                    <a href="${ctx}/admin/ky/awardDeclaration.json"
                       class="stdbtn ml10 btn_orange">新建获奖申报</a>
                    <a href="${ctx}/admin/ky/myAwardDeclaration.json"
                       class="stdbtn ml10 btn_orange">我的获奖申报</a>
                </div>
            </div>
        </div>
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable" id="resultForm3">
                <thead>
                <tr>
                    <th class="head0 center">序号</th>
                    <th class="head1" width="20%">课题名称</th>
                    <th class="head1" width="20%">课题负责人</th>
                    <th class="head1">字数</th>
                    <th class="head1">审批状态</th>
                    <th class="head1">入库状态</th>
                    <th class="head1">开始时间</th>
                    <th class="head1">结束时间</th>
                    <td class="head0 center">
                        操作
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${resultList}" var="result" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>
                                ${result.name}
                        </td>
                        <td>
                                ${result.workName}
                        </td>
                        <td>${result.wordsNumber}</td>
                        <td>
                            <c:if test="${result.passStatus==1}"><span>未审批</span></c:if>
                            <c:if test="${result.passStatus==2}"><span>通过部门审批</span></c:if>
                            <c:if test="${result.passStatus==3}"><span>未通过部门审批</span></c:if>
                            <c:if test="${result.passStatus==4}"><span>通过科研处审核</span></c:if>
                            <c:if test="${result.passStatus==5}"><span>未通过科研处审核</span></c:if>
                            <c:if test="${result.passStatus==6}"><span>未通过科研处领导审核</span></c:if>
                            <c:if test="${result.passStatus==7 and empty result.fileUrlDeclaration}">
                                <span>通过科研处领导审核</span></c:if>
                            <c:if test="${result.passStatus==7 and not empty result.fileUrlDeclaration}">
                                <span>课题结项已申报, 请等待审核</span></c:if>
                            <c:if test="${result.passStatus==8}"><span>课题已结项</span></c:if>
                            <c:if test="${result.passStatus==9}"><span>课题结项未通过</span></c:if>
                        </td>
                        <td>
                            <c:if test="${result.intoStorage=='1'}">未入库</c:if>
                            <c:if test="${result.intoStorage=='2'}">已入库</c:if>
                        </td>
                        <td><fmt:formatDate value="${result.addTime}" pattern="yyyy-MM-dd"/></td>
                        <td><fmt:formatDate value="${result.endTime}" pattern="yyyy-MM-dd"/></td>
                        <td class="center">
                                <%--领导审批未通过, 课题结项未通过 从头提交--%>
                            <c:if test="${result.passStatus==6 or result.passStatus==9}">
                                <a href="${ctx}/admin/ky/toAddResult.json?result.resultForm=3&result.resultType=${resultType}"
                                   class="stdbtn" title="重新提交">重新提交</a>
                            </c:if>
                                <%--领导审批通过后,可以申报结项--%>
                            <c:if test="${result.passStatus==7 and empty result.fileUrlDeclaration}">
                                <a href="${ctx}/admin/ky/problemStatementDeclaration.json?resultId=${result.id}"
                                   class="stdbtn" title="课题结项申报">课题结项申报</a>
                            </c:if>
                                <a href="${ctx}/admin/ky/getResultInfo.json?id=${result.id}" class="stdbtn" title="查看">查看</a>
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
</body>
</html>