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

        jQuery(function () {
            jQuery("select[id='yearOrMonthly']").val(jQuery("#hideYom").val())
        })
    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab" style="margin-left: 10px">
        <h1 class="pagetitle">
            <c:choose>
                <c:when test="${queryResult.passStatus == 1}">立项审批</c:when>
                <c:when test="${queryResult.passStatus == 2}">结项审批</c:when>
                <c:otherwise>课题审批</c:otherwise>
            </c:choose>
        </h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来进行课题的审批<br>
                2. 课题立项：点击<span style="color:red">审批</span>按钮将进入课题审批页面<br>
        </span>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="getResultList" action="${ctx}/admin/zz/projectEstablishment/list.json"
                      method="post">
                    <%--接收URL的参数--%>
                    <input type="hidden" name="queryResult.passStatus" id="passStatus"
                           value="${queryResult.passStatus}"/>
                    <div class="disIb ml20 mb10">
                        <span class="vam">课题名称 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" placeholder="输入课题名称" name="queryResult.name"
                                   value="${queryResult.name}">
                        </label>
                    </div>

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
                    <th class="head1" width="20%">课题名称</th>
                    <th class="head0" width="20%">课题负责人</th>
                    <%--<th class="head1">字数</th>--%>
                    <%--<th class="head1">课题组成员</th>--%>
                    <th class="head1">类型</th>
                    <th class="head0">状态</th>
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
                        <td>
                                ${result.name}
                        </td>
                        <td>
                                ${result.workName}
                        </td>
                            <%--<td>${result.wordsNumber}</td>--%>
                            <%--<td>${result.taskForceMembers}</td>--%>
                        <td>
                            <c:if test="${result.yearOrMonthly == 1}">
                                年度
                            </c:if>
                            <c:if test="${result.yearOrMonthly == 2}">
                                月度
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${result.passStatus==1}"><span>未审批</span></c:if>
                            <c:if test="${result.passStatus==2 and empty result.fileUrlDeclaration}">
                                已立项, 暂未提交相关结项文件
                            </c:if>
                            <c:if test="${result.passStatus==2 and not empty result.fileUrlDeclaration}">
                                已提交相关结项文件, 待审批
                            </c:if>
                        </td>
                        <td><fmt:formatDate value="${result.createTime}" pattern="yyyy-MM-dd"/></td>
                        <td class="center">
                            <c:if test="${result.passStatus==1}">
                                <%--提交申请后, 由生态文明所确认立项--%>
                                <a href="${ctx}/admin/zz/projectEstablishment/approvalProject.json?resultId=${result.id}"
                                   class="stdbtn" title="审批立项">审批立项</a>
                            </c:if>
                            <c:if test="${result.passStatus==2 and not empty result.fileUrlDeclaration}">
                                <%--确认立项后, 由科研/咨政处审批文件--%>
                                <a href="${ctx}/admin/zz/projectEstablishment/knotProject.json?resultId=${result.id}"
                                   class="stdbtn" title="结项审批">结项审批</a>
                            </c:if>
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