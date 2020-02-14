<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>生态文明所课题列表</title>
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

        jQuery(function () {
            jQuery("select[name='yearOrMonthly']").val(jQuery("#hideYom").val())
        })
    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab" style="margin-left: 10px">
        <h1 class="pagetitle">生态文明所课题列表</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来查看课题列表<br>
        </span>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <%--<form action="${ctx}/admin/ky/resultImport.json" method="post" id="importExcel" enctype="multipart/form-data">
                    <input id="myFile" type="file"  name="myFile" style="display: none" onchange="fileChange()">
                </form>
                --%>
                <form class="disIb" id="postForm" action="${ctx}/admin/jiaowu/ky/myTaskResultList.json?resultType=2"
                      method="post">

                    <div class="disIb ml20 mb10">
                        <span class="vam">课题名称&nbsp;</span>
                        <label class="vam">
                            <input type="text" value="${resultName}" name="resultName">
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">课题类型&nbsp;</span>
                        <label class="vam">
                            <input type="hidden" id="hideYom" value="${yom}">
                            <select name="yearOrMonthly" id="yearOrMonthly">
                                <option value="">未选择</option>
                                <option value="1">年度</option>
                                <option value="2">月度</option>
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
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable" id="resultForm3">
                <thead>
                <tr>
                    <th class="head0 center">序号</th>
                    <th class="head1" width="10%">课题名称</th>
                    <th class="head1" width="10%">课题负责人</th>
                    <th class="head1">字数</th>
                    <th class="head1">课题组成员</th>
                    <th class="head1">审批状态</th>
                    <th class="head1">入库状态</th>
                    <th class="head1">课题类型</th>
                    <th class="head1">开始时间</th>
                    <th class="head1">结束时间</th>
                    <th class="head1">入库时间</th>
                    <td class="head0 center">
                        操作
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${resultList}" var="result" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${result.name}</td>
                        <td>${result.workName}</td>
                        <td>${result.wordsNumber}</td>
                        <td>
                                ${fn:substring(result.taskForceMembers,0,8)}
                            <c:if test="${result.taskForceMembers.length()>8}">...</c:if>
                        </td>
                        <td>
                            <c:if test="${result.passStatus==1}"><span>未审批</span></c:if>
                            <c:if test="${result.passStatus==2}"><span>课题立项已通过</span></c:if>
                            <c:if test="${result.passStatus==3}"><span>课题立项已拒绝</span></c:if>
                            <c:if test="${result.passStatus==4}">
                                <span>已通过评审,
                                     <c:if test="${result.assessmentLevel == 1}">课题等级为: 优</c:if>
                                     <c:if test="${result.assessmentLevel == 2}"> 课题等级为: 良</c:if>
                                     <c:if test="${result.assessmentLevel == 3}">课题等级为: 合格</c:if>
                                     <c:if test="${result.assessmentLevel == 4}">课题等级为: 不合格</c:if>
                                </span>
                            </c:if>
                            <c:if test="${result.passStatus==5}"><span>未通过评审</span></c:if>
                        </td>
                        <td>
                            <c:if test="${result.yearOrMonthly == 1}">年度</c:if>
                            <c:if test="${result.yearOrMonthly == 2}">月度</c:if>
                        </td>
                        <td>
                            <c:if test="${result.intoStorage=='1'}">未入库</c:if>
                            <c:if test="${result.intoStorage=='2'}">已入库</c:if>
                        </td>
                        <td><fmt:formatDate value="${result.addTime}" pattern="yyyy-MM-dd"/></td>
                        <td><fmt:formatDate value="${result.endTime}" pattern="yyyy-MM-dd"/></td>
                        <td><fmt:formatDate value="${result.storageTime}" pattern="yyyy-MM-dd"/></td>
                        <td class="center">
                                <%--课题立项通过后 上传相关文件--%>
                            <c:if test="${result.passStatus==2}">
                                <c:if test="${empty result.fileUrlDeclaration}">
                                <a href="${ctx}/admin/jiaowu/ky/problemStatementDeclaration.json?resultId=${result.id}"
                                   class="stdbtn" title="提交课题相关文件">提交课题相关文件</a>
                                </c:if>
                                <c:if test="${not empty result.fileUrlDeclaration}">
                                    <a href="javascript:;"
                                       class="stdbtn" title="课题结项文件已提交">课题结项文件已提交</a>
                                </c:if>
                            </c:if>
                            <a href="${ctx}/admin/jiaowu/ky/getResultInfo.json?id=${result.id}" class="stdbtn"
                               title="查看">查看</a>
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