<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>讲师课时详情</title>
    <script type="text/javascript">
        function searchForm() {
            jQuery("#searchForm").submit();
        }

        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        jQuery(function(){
            laydate.skin('molv');
            laydate({
                elem: '#startTime',
                format:'YYYY-MM-DD hh:mm:ss'
            });
            laydate({
                elem: '#endTime',
                format:'YYYY-MM-DD hh:mm:ss'
            });
        });

        function excel(){
            jQuery("#searchForm").attr("action","${ctx}/admin/jiaowu/statistic/queryCourseInfoExcel/${id}.json");
            jQuery("#searchForm").submit();
            jQuery("#searchForm").attr("action","${ctx}/admin/jiaowu/statistic/queryCourseInfo/${id}.json");
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">讲师课时详情</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/statistic/queryCourseInfo/${id}.json" method="get">
                    <div class="disIb ml20 mb10">
                        <span class="vam">班次名称&nbsp;</span>
                        <label class="vam">
                            <input id="name" style="width: auto;" name="className" type="text" class="hasDatepicker" value="${className}" placeholder="请输入班次名称">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">开始时间 &nbsp;</span>
                        <label class="vam">
                            <input id="startTime" style="width: auto;height:35px;" name="startTime" type="text" class="hasDatepicker laydate-icon" value="${startTime}" placeholder="请输入开始时间">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">结束时间 &nbsp;</span>
                        <label class="vam">
                            <input id="endTime" style="width: auto;height:35px;" name="endTime" type="text" class="hasDatepicker laydate-icon" value="${endTime}" placeholder="请输入结束时间">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="javascript: void(0)" onclick="excel()" class="stdbtn btn_orange">导出Excel</a>
                </div>
            </div>
        </div>
        <!-- 搜索条件，结束 -->

        <!-- 数据显示列表，开始 -->
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <!-- 数据显示列表，开始 -->
                    <thead>
                    <tr>
                        <th class="head0 center">讲师姓名</th>
                        <th class="head0 center">班次名称</th>
                        <th class="head0 center">课程名称</th>
                        <th class="head0 center">教室名称</th>
                        <th class="head0 center">班级人数</th>
                        <th class="head0 center">开始时间</th>
                        <th class="head0 center">结束时间</th>
                        <th class="head0 center">天数</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${courseArrangeList!=null&&courseArrangeList.size()>0}">
                        <c:forEach items="${courseArrangeList}" var="course">
                            <tr>
                                <td>${course.teacherName}</td>
                                <td>${course.className}</td>
                                <td>${course.courseName}</td>
                                <td>${course.classroomName}</td>
                                <td>${course.totalNum}</td>
                                <td><fmt:formatDate value="${course.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                <td><fmt:formatDate value="${course.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
                                <td>
                                    <c:set var="interval" value="${course.endTime.time - course.startTime.time}"/>
                                    <fmt:formatNumber value="${interval/1000/60/60/24}" pattern="#0.0"/>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
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