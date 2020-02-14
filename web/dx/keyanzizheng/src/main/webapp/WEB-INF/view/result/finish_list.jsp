<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>${resultTypeName}课题结项审批列表</title>
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
         *科研和咨政处审核
         * @param resultId
         */
        function approvalResult(resultId) {
            if (confirm("是否通过审批?")) {
                jQuery.ajax({
                    url: "/admin/ky/updateResult.json",
                    data: {
                        "result.id": resultId,
                        "result.passStatus": 4
                    },
                    type: "post",
                    dataType: "json",
                    async: false,
                    success: function () {
                        window.location.reload();
                    }
                });
            }
        }
    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab" style="margin-left: 10px">
        <h1 class="pagetitle">
            课题结项审批
        </h1>
        <span>
            <span style="color:red">说明</span><br>
            1. 本页面用来进行课题结项的审批：审批，下载课题结项文件<br>
            2. 结项审批：点击<span style="color:red">结项审批</span>按钮将进入审核课题结项页面<br>
            3. 教职工未提交结项申请附件时, 没有<span style="color:red">结项审批</span>按钮<br>
        </span>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="getResultList" action="${ctx}/admin/ky/toResultApprovalList.json" method="post">
                    <%--通过状态--%>
                    <input type="hidden" name="queryResult.passStatus" id="passStatus"
                           value="${queryResult.passStatus}"/>
                    <%--课题类型 科研/咨政--%>
                    <input type="hidden" name="queryResult.resultType" value="${queryResult.resultType}">
                    <%--<div class="disIb ml20 mb10">
                        <span class="vam">ID &nbsp;</span>
                        <label class="vam">
                            <input type="text" onkeyup='this.value=this.value.replace(/\D/gi,"")' class="hasDatepicker" placeholder="输入ID" name="queryResult.id" value="${queryResult.id}">
                        </label>
                    </div>--%>
                    <div class="disIb ml20 mb10">
                        <span class="vam" id="resultName">课题名称 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" placeholder="输入课题名称" name="queryResult.name"
                                   value="${queryResult.name}">
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
                <colgroup>
                    <col class="con0" style="width:10%;"/>
                    <col class="con1"/>
                    <col class="con0"/>
                    <col class="con1"/>
                    <col class="con0"/>
                    <col class="con1"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">序号</th>
                    <th class="head1">课题名称</th>
                    <th class="head0">课题负责人</th>
                    <%--<th class="head1">结项单位</th>--%>
                    <th class="head1">字数</th>
                    <th class="head1">课题组成员</th>
                    <th class="head1">状态</th>
                    <th class="head1">创建时间</th>
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
                            <%--<td>${result.resultDepartment}</td>--%>
                        <td>${result.wordsNumber}</td>
                        <td>
                                <%--<c:forEach items="${result.employeeList}" var="employee">
                                    ${employee.name} ,
                                </c:forEach>--%>
                                ${result.taskForceMembers}
                        </td>
                        <td>
                            <c:if test="${empty result.fileUrlDeclaration}">
                                未提交课题结项申请
                            </c:if>
                            <c:if test="${not empty result.fileUrlDeclaration}">
                                课题结项申请已提交
                            </c:if>
                        </td>
                        <td><fmt:formatDate value="${result.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                        <td class="center">
                            <c:if test="${not empty result.fileUrlDeclaration}">
                                <a href="${ctx}/admin/ky/finish.json?id=${result.id}" class="stdbtn"
                                   title="结项审批">结项审批</a>
                            </c:if>
                                <%-- <c:if test="${result.passStatus!=1}">
                                     <a href="${result.approveBill.fileUrl}" class="stdbtn" title="下载审批文件" download="">下载审批文件</a>
                                 </c:if>--%>
                            <a href="${ctx}/admin/ky/getResultInfo.json?id=${result.id}" class="stdbtn"
                               title="查看">查看</a></td>
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