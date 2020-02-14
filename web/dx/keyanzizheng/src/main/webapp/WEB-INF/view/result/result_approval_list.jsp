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
         *科研和咨政处审核
         * @param resultId
         */
        function approvalResult(resultId) {
            if (confirm("是否通过 ${resultTypeName}审批?")) {
                jQuery.ajax({
                    url: "/admin/ky/updateResult.json",
                    data: {
                        "result.id": resultId,
                        "result.passStatus": 4
                    },
                    type: "post",
                    dataType: "json",
                    async: false,
                    success: function (result) {
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
            <c:if test="${queryResult.passStatus==1}">
                部门审批
            </c:if>
            <c:if test="${queryResult.passStatus==2}">
                课题审批
            </c:if>
            <c:if test="${queryResult.passStatus==4}">
                领导审核
            </c:if>
            </h1>
        <span>
                <span style="color:red">说明</span><br>
            <c:if test="${queryResult.passStatus==1}">
                1. 本页面用来进行课题信息的审批：部门审批<br>
                2. 部门审批：点击<span style="color:red">部门审批</span>按钮将进入部门审批页面<br>
            </c:if>
            <c:if test="${queryResult.passStatus==2}">
                1. 本页面用来进行课题信息的审批：课题审批<br>
                2. 审批：点击<span style="color:red">审批</span>进行审核<br>
            </c:if>
            <c:if test="${queryResult.passStatus==4}">
                1. 本页面用来进行课题信息的审批：审批<br>
                2. 领导审核：点击<span style="color:red"> 领导审核</span>按钮将进入审核入库页面<br>
            </c:if>
        </span>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="getResultList" action="${ctx}/admin/ky/toResultApprovalList.json" method="post">
                    <input type="hidden" name="queryResult.passStatus" id="passStatus"  value="${queryResult.passStatus}"/>
                    <input type="hidden" name="queryResult.resultType" value="${queryResult.resultType}">
                    <div class="disIb ml20 mb10">
                        <span class="vam" id="resultName">课题名称 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" placeholder="输入课题名称" name="queryResult.name" value="${queryResult.name}">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchForm()">搜 索</a>
                    <a href="javascript: void(0)" class="stdbtn ml10" onclick="emptyForm()">重 置</a>
                </div>
            </div>
        </div>

        <div class="pr" >
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
                    <th class="head1">字数</th>
                    <th class="head1">课题组成员</th>
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
                            ${result.taskForceMembers}
                        </td>
                        <td><fmt:formatDate value="${result.addTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                        <td class="center">
                            <c:if test="${result.passStatus==1}">
                                <a href="${ctx}/admin/ky/toAddApproveBill.json?approveBill.resultId=${result.id}&resultType=${queryResult.resultType}" class="stdbtn" title="部门审核">部门审批</a>
                            </c:if>
                            <c:if test="${result.passStatus==2}">
                                <%--通过部门审批之后, 由科研处审批--%>
                                <a href="${ctx}/admin/ky/officeApproval.json?approveBill.resultId=${result.id}&type=${queryResult.resultType}"
                                   class="stdbtn" id="keyanshenhe"
                                   title="审批">审批</a>

                            </c:if>
                            <c:if test="${result.passStatus==4}">
                                <a href="${ctx}/admin/ky/toApprovalResult.json?id=${result.id}" class="stdbtn"
                                   title="领导审批">领导审批</a>
                            </c:if>
                            <a href="${ctx}/admin/ky/getResultInfo.json?id=${result.id}" class="stdbtn" title="查看">查看</a>                       </td>
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