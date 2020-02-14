<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>成果统计</title>
    <script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
    <script src="http://code.highcharts.com/highcharts.js"></script>
</head>
<body>
<form action="${ctx}/hessian/admin/ky/resultYearStatistics.json" method="post">
    <span> 年份：<input type="text" value="${year}" onkeyup='this.value=this.value.replace(/\D/gi,"")' name="year"
                     size="8"></span>
    <input type="hidden" name="type" value="${type}">
    <input type="submit" value="确定">
</form>

<div id="container" style="width: 500px; height: 400px;margin: 0 auto"></div>

<script language="JavaScript">
    jQuery(document).ready(function () {
        var chart = {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        };
        var title = {
            text: '${year}' + '年教师科研成果统计'
        };
        var tooltip = {
            pointFormat: '{series.name}: <b>{point.y}</b>'
        };
        var plotOptions = {
            pie: {
                size: 200,
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}%</b>: {point.percentage:.1f} %',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    }
                }
            }
        };
        var series = [{
            type: 'pie',
            name: '数量',
            data: [
                ['课题', ${resultStatistics3.declareCount}],
                ['论文', ${resultStatistics1.declareCount}],
                ['著作', ${resultStatistics2.declareCount}],
                ['内刊', ${resultStatistics4.declareCount}]
            ]
        }];

        var json = {};
        json.chart = chart;
        json.title = title;
        json.tooltip = tooltip;
        json.series = series;
        json.plotOptions = plotOptions;
        jQuery('#container').highcharts(json);
        jQuery('.highcharts-credits').hide();
    });
</script>
</body>
</html>