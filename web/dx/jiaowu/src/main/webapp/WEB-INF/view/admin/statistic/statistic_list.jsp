<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>班次详细信息统计</title>
    <script type="text/javascript">
        function searchForm() {
            jQuery("#searchForm").submit();
        }

        function emptyForm(){
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">班次详细信息统计</h1>
    </div>
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/statistic/statisticList.json"
                      method="post">
                    <input type="hidden" name="classes.teacherId" id="teacherId" value="${classes.teacherId }"/>
                    <input type="hidden" name="classes.teacherName" id="teacherName" value="${classes.teacherName}"/>

                    <div class="disIb ml20 mb10">
                        <span class="vam">班次名称： &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="longinput" name="classes.name" value="${classes.name}" style="width: 200px;"/>
                        </label>
                    </div>
                </form>
                <div class="disIb mb10 ml20">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                </div>
            </div>
        </div>
        <!-- 搜索条件，结束 -->

        <!-- 数据显示列表，开始 -->
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <colgroup>
                    <col class="con0"/>
                    <col class="con1"/>
                    <col class="con0"/>
                    <col class="con1"/>
                    <col class="con0"/>
                    <col class="con1"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">班次名称</th>
                    <th class="head0 center">班主任</th>
                    <%--<th class="head0 center">已报到人数</th>--%>
                    <%--<th class="head0 center">未报到人数</th>--%>
                    <th class="head0 center">班次总人数</th>
                    <th class="head0 center">开始时间</th>
                    <th class="head0 center">结束时间</th>
                    <th class="head0 center">天数</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${classList!=null&&classList.size()>0 }">
                    <c:forEach items="${classList}" var="classes">
                        <tr>
                            <td>${classes.name}</td>
                            <td>${classes.teacherName}</td>
                            <%--<td>${classes.studentTotalNum}</td>--%>
                            <%--<td>${classes.studentNotReportNum}</td>--%>
                            <td>${classes.maxNum}</td>
                            <td><fmt:formatDate value="${classes.startTime}" pattern="yyyy-MM-dd"/> </td>
                            <td><fmt:formatDate value="${classes.endTime}" pattern="yyyy-MM-dd"/></td>
                            <td>${classes.comparDays}</td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
            <!-- 分页，开始 -->
            <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
            <%--<!-- 分页，结束 -->--%>
    </div>
</div>
</div>
</body>

</html>