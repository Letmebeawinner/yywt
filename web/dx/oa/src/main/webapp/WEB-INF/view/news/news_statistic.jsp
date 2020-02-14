<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>新闻发布统计</title>
    <script type="text/javascript" src="${ctx}/static/js/highcarts/highcharts.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/highcarts/highcharts-zh_CN.js"></script>
    <script type="text/javascript">
        jQuery(function () {
            jQuery('#container').highcharts({
                chart: {
                    type: 'column'
                },
                title: {
                    text: '新闻发布统计'
                },
                xAxis: {
                    categories: [
                       ${xAxis}
                    ],
                    crosshair: true
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: '新闻发布数量'
                    }
                },
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}条</span><table>',
                    footerFormat: '</table>',
                    shared: true,
                    useHTML: true
                },
                plotOptions: {
                    column: {
                        pointPadding: 0.2,
                        borderWidth: 0
                    }
                },
                series: [{
                    name: '发布数量',
                    data: [${yAxis}]
                }]
            });
        });

    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab">
        <h1 class="pagetitle">新闻发布统计</h1>
    </div>


    <div id="container" style="width:1200px;height: 400px"></div>

    <div id="contentwrapper" class="contentwrapper">

        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">

                <thead>
                <tr>
                        <th class="head1">id</th>
                        <th class="head1">姓名</th>
                        <th class="head1">数量</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${newsList}" var="news" varStatus="index">
                    <tr>
                        <td>${index.index+1}</td>
                        <td>${news.userName}</td>
                        <td>${news.num}</td>
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