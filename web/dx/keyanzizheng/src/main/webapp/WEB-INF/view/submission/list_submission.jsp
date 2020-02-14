<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>投稿申请列表</title>
    <script type="text/javascript" src="${ctx}/static/admin/js/submission.js"></script>
    <script type="text/javascript">
        jQuery(function () {
            jQuery("select[name='submission.typeId']").val("${submission.typeId}")
        })
    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab" style="margin-left: 10px">
        <h1 class="pagetitle">投稿申请列表</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来展示投稿申请列表、编辑投稿申请。<br>
                2. 审批投稿申请信息：点击操作列中的<span style="color:red">审批</span>按钮, 审批投稿申请。<br>
                3. 删除投稿申请信息：点击操作列中的<span style="color:red">删除</span>按钮, 删除投稿申请。<br>
        </span>
    </div>

    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="listCategory" action="${ctx}/admin/ky/listSubmission.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">投稿名称 &nbsp;</span>
                        <label class="vam">
                            <input type="text" placeholder="输入投稿名称" name="submission.name"
                                   value="${submission.name}">
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">形式 &nbsp;</span>
                        <label class="vam">
                            <select name="submission.typeId">
                                <option value="">未选择</option>
                                <option value="1">贵阳决策咨询</option>
                                <option value="2">生态文明参阅</option>
                            </select>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchForm()">搜 索</a>
                    <a href="javascript: void(0)" class="stdbtn ml10" onclick="emptyForm()">重 置</a>
                    <%--<a href="${ctx}/admin/ky/saveSubmission.json" class="stdbtn ml10">申请投稿</a>--%>
                </div>
            </div>
        </div>
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <thead>
                <tr>
                    <td class="center">序号</td>
                    <td class="center">投稿人</td>
                    <td class="center">投稿名称</td>
                    <td class="center">投稿类型</td>
                    <td class="center">审批状态</td>
                    <td class="center">添加时间</td>
                    <td class="center">修改时间</td>
                    <td class="center">
                        操作
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${submissionList}" var="c" varStatus="status">
                    <tr>
                        <td class="center">${status.count}</td>
                        <td class="center">${c.applicantName}</td>
                        <td class="center">${c.name}</td>
                        <td class="center">${c.typeName}</td>
                        <td class="center">
                            <c:if test="${c.audit == 0}">
                                未审批
                            </c:if>
                            <c:if test="${c.audit == 1}">
                                已通过审批
                            </c:if>
                            <c:if test="${c.audit == 2}">
                                未通过审批
                            </c:if>
                        </td>
                        <td class="center"><fmt:formatDate value="${c.createTime}" pattern="yyyy-MM-dd"/></td>
                        <td class="center"><fmt:formatDate value="${c.updateTime}" pattern="yyyy-MM-dd"/></td>
                        <td class="center">
                            <a href="${ctx}/admin/ky/detailSubmission.json?id=${c.id}" class="stdbtn"
                               title="查看">查看</a>
                            <c:if test="${c.audit == 0}">
                                <a href="${ctx}/admin/ky/auditSubmission.json?id=${c.id}" class="stdbtn"
                                   title="审批">审批</a>
                            </c:if>
                            <a class="stdbtn" title="删除" onclick="remove('${c.id}')">删除</a>
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