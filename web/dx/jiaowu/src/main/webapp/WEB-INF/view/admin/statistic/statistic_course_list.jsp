<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>讲师课时统计</title>
    <script type="text/javascript" src="${ctx}/static/js/highcharts.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/highcharts-zh_CN.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/data.js"></script>
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
            jQuery('#container').highcharts({
                chart: {
                    type: 'column'
                },
                title: {
                    text: '讲师课时统计列表'
                },
                subtitle: {
                    text: '各个讲师课时数统计'
                },
                xAxis: {
                    categories: [
                        ${teacherNames}
                    ],
                    crosshair: true
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: '课时'
                    }
                },
                tooltip: {
                    headerFormat: '<span style="font-size:20px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y:.1f}时</b></td></tr>',
                    footerFormat: '</table>',
                    shared: true,
                    useHTML: true
                },
                plotOptions: {
                    column: {
                        borderWidth: 0
                    }
                },
                series: [{
                    name: '课时',
                    data: [${hour}]
                }]
            });
        });
        function excel(){
            jQuery("#searchForm").attr("action","${ctx}/admin/jiaowu/statistic/statisticCourseLessonExcel.json");
            jQuery("#searchForm").submit();
            jQuery("#searchForm").attr("action","${ctx}/admin/jiaowu/statistic/statisticCourseLesson.json");
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">讲师课时统计</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/statistic/statisticCourseLesson.json"
                      method="get">
                    <div class="disIb ml20 mb10">
                        <span class="vam">讲师姓名&nbsp;</span>
                        <label class="vam">
                            <input id="name" style="width: auto;" name="teacherName" type="text" class="hasDatepicker" value="${teacherName}" placeholder="请输入讲师名称">
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
                        <th class="head0 center">讲师id</th>
                        <th class="head0 center">教研部</th>
                        <th class="head0 center">讲师姓名</th>
                        <th class="head0 center">课时数量</th>
                        <th class="head0 center">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${courseArrangeNumList!=null&&courseArrangeNumList.size()>0}">
                        <c:forEach items="${courseArrangeNumList}" var="course">
                            <tr>
                                <td>${course.teacherId}</td>
                                <td>
                                    <c:if test="${course.teacherResearchId==1}">党史</c:if>
                                    <c:if test="${course.teacherResearchId==2}">公管</c:if>
                                    <c:if test="${course.teacherResearchId==3}">经济学</c:if>
                                    <c:if test="${course.teacherResearchId==4}">法学</c:if>
                                    <c:if test="${course.teacherResearchId==5}">文化与社会发展</c:if>
                                    <c:if test="${course.teacherResearchId==6}">马列</c:if>
                                    <c:if test="${course.teacherResearchId==7}">统一战线</c:if>
                                </td>
                                <td>${course.teacherName}</td>
                                <td>${course.sum}</td>
                                <td>
                                    <a href="${ctx}/admin/jiaowu/statistic/queryCourseInfo/${course.teacherId}.json"
                                       class="stdbtn ml10">详情</a>

                                    <a href="${ctx}/admin/jiaowu/statistic/chgThqHour/${course.teacherId}.json"
                                       class="stdbtn ml10">
                                        更改教师课时</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    </tbody>
                </table>
        </div>
        <!-- 搜索条件，结束 -->
    </div>
    <div id="container" style="width: 100%;height: 400px;"></div>
</div>
</body>

</html>