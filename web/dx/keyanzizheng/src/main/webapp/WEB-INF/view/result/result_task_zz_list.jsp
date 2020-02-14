<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>课题列表</title>
    <script type="text/javascript">
        /**
         * 执行查询
         */
        function searchForm() {
            jQuery("#getResultList").submit();
        }

        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        /**
         * 删除成果
         * @param resultId
         */
        function delResult(resultId) {
            if (confirm("删除后将无法恢复，是否继续")) {
                jQuery.ajax({
                    url: "/admin/ky/deleteResult.json?id=" + resultId,
                    data: {},
                    type: "post",
                    dataType: "json",
                    async: false,
                    success: function (result) {
                        if (result.code == "0") {
                            alert(result.message);
                            window.location.reload();
                        } else {
                            alert(result.message);
                            return;
                        }
                    }
                });
            }
        }

        function resultExport() {
            jQuery("#getResultList").prop("action", "${ctx}/admin/ky/resultTaskExport.json");
            jQuery("#getResultList").submit();
            jQuery("#getResultList").prop("action", "${ctx}/admin/ky/getTaskResultList.json");
        }

        function resultImport() {
            jQuery("#myFile").click();
            jQuery("#myFile").change(function () {
                jQuery("#importExcel").submit();
            });
        }

        jQuery(function () {
            jQuery("select[id='yearOrMonthly']").val(jQuery("#hideYom").val())
        })
    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab" style="margin-left: 10px">
        <h1 class="pagetitle">课题列表</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来查看审批文件，查看课题组成员信息，查看课题变更记录<br>
                2. 变更记录：点击操作列中的<span style="color:red">变更记录</span>查看课题变更记录；<br>
                <%--3. 批量导入：通过点击搜索列中的<span style="color:red">课题模板</span>下载课题模板，根据格式题写后点击搜索列中的<span style="color:red">批量导入</span>选择文件导入。--%>
        </span>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form action="${ctx}/admin/ky/resultImport.json" method="post" id="importExcel"
                      enctype="multipart/form-data">
                    <input id="myFile" type="file" name="myFile" style="display: none" onchange="fileChange()">
                </form>
                <form class="disIb" id="getResultList" action="${ctx}/admin/ky/getTaskResultList.json" method="post">
                    <input type="hidden" name="queryResult.resultType" value="${queryResult.resultType}">
                    <div class="disIb ml20 mb10">
                        <span class="vam" id="resultName">课题名称 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" placeholder="输入成果名称" name="queryResult.name"
                                   value="${queryResult.name}">
                        </label>
                    </div>

                    <c:if test="${queryResult.resultType== 2}">
                        <div class="disIb ml20 mb10">
                            <span class="vam">课题类型&nbsp;</span>
                            <label class="vam">
                                <input type="hidden" id="hideYom" value="${queryResult.yearOrMonthly}">
                                <select name="queryResult.yearOrMonthly" id="yearOrMonthly">
                                    <option value="">未选择</option>
                                    <option value="1">年度</option>
                                    <option value="2">月度</option>
                                </select>
                            </label>
                        </div>
                    </c:if>
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
                    <th class="head0 center" width="5%">序号</th>
                    <th class="head1" width="30%">课题名称</th>
                    <th class="head1" width="10%">课题负责人</th>
                    <th class="head1">字数</th>
                    <th class="head1" width="10%">审批状态</th>
                    <th class="head1" width="10%">入库状态</th>
                    <th class="head1" width="10%">归档状态</th>
                    <th class="head1" width="5%">课题类型</th>
                    <th class="head1">开始时间</th>
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
                                <%--生态文明所的审批--%>
                            <c:if test="${result.resultType == 2}">
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
                            </c:if>
                        </td>

                        <td>
                            <c:if test="${result.intoStorage=='1'}">未入库</c:if>
                            <c:if test="${result.intoStorage=='2'}">已入库</c:if>
                        </td>
                        <td>
                            <c:if test="${result.ifFile==0}">未归档</c:if>
                            <c:if test="${result.ifFile==1}">已归档</c:if>
                        </td>
                        <td>
                            <c:if test="${result.yearOrMonthly==1}">年度</c:if>
                            <c:if test="${result.yearOrMonthly==2}">月度</c:if>
                        </td>

                        <td><fmt:formatDate value="${result.addTime}" pattern="yyyy-MM-dd"/></td>
                        <td class="center" width="25%">
                            <a href="${ctx}/admin/ky/getResultInfo.json?id=${result.id}" class="stdbtn"
                               title="查看">查看</a>
                            <a href="${ctx}/admin/ky/getTaskChangeList.json?id=${result.id}" class="stdbtn"
                               title="变更记录">变更记录</a>
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