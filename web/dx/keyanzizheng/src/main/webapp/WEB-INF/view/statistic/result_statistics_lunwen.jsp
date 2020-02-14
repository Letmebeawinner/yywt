<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>论文统计</title>
    <style>
        .stdtable {
            width: 100%;
        }

        .stdtable .con0 {
            background: #fff;
        }

        .stdtable .con1 {
            background: #fcfcfc;
        }

        .stdtable th, .stdtable td {
            line-height: 21px;
            vertical-align: middle;
            color: #333;
        }

        .stdtable thead th, .stdtable thead td {
            padding: 7px 10px;
            border: 1px solid #ddd;
            border-left: 0;
            text-align: left;
        }

        .stdtable tfoot th, .stdtable tfoot td {
            font-weight: normal;
            font-size: 14px;
            padding: 7px 10px;
            border-right: 1px solid #ddd;
            border-bottom: 1px solid #ddd;
            text-align: left;
        }

        .stdtable thead th:first-child, .stdtable tfoot th:first-child,
        .stdtable thead td:first-child, .stdtable tfoot td:first-child {
            border-left: 1px solid #ddd;
        }

        .stdtable thead th.head0, .stdtable tfoot th.head0, .stdtable thead td.head0, .stdtable tfoot td.head0 {
            font-size: 14px;
            background-color: #fcfcfc;
        }

        /*.stdtable thead td { font-weight: bold;}*/
        .stdtable tbody tr td {
            font-size: 12px;
            padding: 8px 10px;
            border-right: 1px solid #eee;
            border-bottom: 1px solid #eee;
            color: #666;
        }

        .stdtable tbody tr:last-child td {
            border-bottom: 1px solid #ddd;
        }

        .stdtable tbody tr td:first-child {
            border-left: 1px solid #ddd;
        }

        .stdtable tbody tr td:last-child {
            border-right: 1px solid #ddd;
        }

        .stdtable tbody tr.togglerow td {
            background: #fff;
            padding: 15px;
        }

        .stdtable tbody tr.togglerow:hover td {
            background: #fff;
        }

        .stdtable .actions a {
            display: inline-block;
            margin-left: 5px;
            border-left: 1px solid #ccc;
            padding-left: 5px;
        }

        .stdtable .actions a:first-child {
            border-left: 0;
            margin-left: 0;
        }

        .stdtable .actions a:hover {
            color: #D20009;
        }

        td:hover{
            text-decoration:underline;
            color: #C20C0C;
        }
    </style>

    <script src="${ctx}/static/js/highchartsLibs/http_code.highcharts.com_highcharts.js"></script>
    <script src="${ctx}/static/js/highchartsLibs/http_cdn.hcharts.cn_highcharts_modules_exporting.js"></script>
    <script src="${ctx}/static/js/highchartsLibs/http_cdn.hcharts.cn_highcharts_modules_no-data-to-display.js"></script>
    <script type="text/javascript">
        jQuery(function () {
            Highcharts.setOptions({
                lang: {
                    noData: "暂无数据",
                    printChart: "打印图表",
                    downloadJPEG: "下载JPEG图片",
                    downloadPDF: "下载PDF文档",
                    downloadPNG: "下载PNG图片",
                    downloadSVG: "下载SVG矢量图"
                }
            });


            // 绑定时间插件
            laydate.skin('molv');
            laydate({
                elem: '#searchAddTime',
                format: 'YYYY-MM-DD hh:mm:ss',
                istime: true
            });
            laydate({
                elem: '#searchEndTime',
                format: 'YYYY-MM-DD hh:mm:ss',
                istime: true
            });
        })

        function searchForm() {
            // 开始时间不能晚于结束时间
            // 开始时间不能大于结束时间
            // 开始时间不能>结束时间
            var searchAddTime = jQuery("#searchAddTime").val()
            var searchEndTime = jQuery("#searchEndTime").val()
            if (searchAddTime.length > 0 &&
                searchEndTime.length > 0) {

                var _addTime = new Date(searchAddTime.replace(/-/g, '/'));
                var _endTime = new Date(searchEndTime.replace(/-/g, '/'));
                if (_addTime > _endTime) {
                    alert('开始时间不能晚于结束时间, 请重新选择');
                    return false;
                }
            }
            jQuery("#searchForm").submit();
        }

        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("#queryYear").val(2017);
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 10px">
        <h1 class="pagetitle">论文统计</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来查看论文统计<br>
                2. 只输入开始时间, 将只查询此时间之后的数据<br>
                2. 只输入结束时间, 将只查询此时间之前的数据<br>
        </span>
    </div><!--pageheader-->
    <%--table统计成果开始--%>
    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/ky/lunwenDetailsStatistics.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">开始时间 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" placeholder="输入时间段" name="startTime"
                                   id="searchAddTime" readonly
                                   value="${startTime}">
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">结束时间 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" placeholder="输入时间段" name="endTime"
                                   id="searchEndTime" readonly
                                   value="${endTime}">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchForm()">搜 索</a>
                    <a href="javascript: void(0)" class="stdbtn ml10" onclick="emptyForm()">重 置</a>
                </div>
            </div>
        </div>
        <div id="containerPie" style="width: 100%; height: 400px;"></div>
        <div id="container" style="width: 100%; height: 400px;"></div>
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
                    <th class="head0 center">刊物性质 (点击可跳转到成果列表)</th>
                    <th class="head0 center">个数 (点击可跳转到成果列表)</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${series}" varStatus="status" var="s">
                    <tr onclick="location.href='${ctx}/admin/ky/getResultList.json?queryResult.resultForm=1&queryResult.resultType=1&queryResult.journalNature=${s.secId}'">
                        <td>${s.name}</td>
                        <td>${s.data}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <%--table统计成果结束--%>
        <script type="text/javascript">
            jQuery(function () {
                jQuery('#containerPie').highcharts({
                    chart: {
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false
                    },
                    title: {
                        text: '刊物性质占比'
                    },
                    tooltip: {
                        headerFormat: '{series.name}<br>',
                        pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
                    },
                    plotOptions: {
                        pie: {
                            allowPointSelect: true,
                            cursor: 'pointer',
                            dataLabels: {
                                enabled: true,
                                format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                                style: {
                                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                                }
                            }
                        }
                    },
                    credits: {
                        enabled: false
                    },
                    series: [{
                        type: 'pie',
                        name: '刊物性质占比',
                        data: [
                            <c:if test="${nodata != 1}">
                            <c:forEach items="${series}" var="s" varStatus="status" >
                            ["${s.name}", ${s.data}]
                            <c:if test="${!status.last}">, </c:if>
                            </c:forEach>
                            </c:if>
                        ]
                    }]
                });

                jQuery('#container').highcharts({
                    chart: {
                        type: 'column'
                    },
                    title: {
                        text: '刊物性质统计'
                    },
                    xAxis: {
                        categories: [
                            <c:forEach items="${series}" var="s" varStatus="status" >
                            "${s.name}"
                            <c:if test="${!status.last}">, </c:if>
                            </c:forEach>
                        ],
                        crosshair: true
                    },
                    yAxis: {
                        min: 0,
                        title: {
                            text: '个'
                        }
                    },
                    credits: {
                        enabled: false
                    },
                    tooltip: {
                        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                        '<td style="padding:0"><b>{point.y:.1f} 个</b></td></tr>',
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
                    series: [
                        <c:if test="${nodata != 1}">
                        <c:forEach items="${series}" var="s" varStatus="status" >
                        {
                            name: "${s.name}",
                            data: [${s.data}]
                        }<c:if test="${!status.last}">, </c:if>
                        </c:forEach>
                        </c:if>
                    ]
                });
            });
        </script>
    </div>

</div>
</body>
</html>
