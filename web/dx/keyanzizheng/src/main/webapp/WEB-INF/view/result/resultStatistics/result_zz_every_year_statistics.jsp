<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>历年成果统计</title>
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
    </style>

    <script src="${ctx}/static/js/highchartsLibs/http_code.highcharts.com_highcharts.js" ></script>
    <script src="${ctx}/static/js/highchartsLibs/http_cdn.hcharts.cn_highcharts_modules_exporting.js" ></script>
    <script type="text/javascript">
        function searchForm() {
            jQuery("#searchForm").submit();
        }

        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("#queryYear").val(2017);
        }

        jQuery(function () {
            Highcharts.setOptions({
                lang: {
                    printChart: "打印图表",
                    downloadJPEG: "下载JPEG图片",
                    downloadPDF: "下载PDF文档",
                    downloadPNG: "下载PNG图片",
                    downloadSVG: "下载SVG矢量图"
                }
            });
        })
    </script>

</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 10px">
        <h1 class="pagetitle">历年成果数量统计</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来查看历年成果数量统计<br>
        </span>
    </div><!--pageheader-->
    <%--table统计成果开始--%>
    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm"
                      action="${ctx}/admin/ky/ecologicalCivilizationResultsStatistics.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">年份 &nbsp;</span>
                        <label class="vam">
                            <select name="year" id="queryYear">
                                <c:forEach begin="2011" end="${currentYear}" var="y">
                                    <option value="${y}">${y}</option>
                                </c:forEach>
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
        <div id="pieChart" style="width:100%;height: 400px"></div>
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
                    <th class="head0 center">成果类型</th>
                    <th class="head0 center">个数</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>课题</td>
                    <td>${resultCount} <c:if test="${empty resultCount}">0</c:if></td>
                </tr>
                <tr>
                    <td>调研</td>
                    <td>${researchReport} <c:if test="${empty researchReport}">0</c:if></td>
                </tr>
                </tbody>
            </table>
        </div>
        <%--table统计成果结束--%>
        <script type="text/javascript">
            jQuery(document).ready(function () {
                jQuery("#queryYear").val('${year}');

                var chart = {
                    type: 'column'
                };
                var title = {
                    text: '历年成果数量统计'
                };
                var xAxis = {
                    categories: [${year}],
                    crosshair: true
                };
                var yAxis = {
                    min: 0,
                    title: {
                        text: '成果数（条）'
                    }
                };
                var tooltip = {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y:.1f} 条</b></td></tr>',
                    footerFormat: '</table>',
                    shared: false,
                    useHTML: true
                };
                var plotOptions = {
                    column: {
                        pointPadding: 0.2,
                        borderWidth: 0
                    }
                };
                var credits = {
                    enabled: false
                };

                var series = [{
                    name: '成果课题',
                    data: [${resultCount}],
                    color: "#90ed7d"
                }, {
                    name: '调研报告',
                    data: [${researchReport}],
                    color: "#f7a35c"
                }];

                var exporting = {
                    enabled: true,
                    buttons: {
                        exportButton: {
                            menuItems: [{
                                text: '导出PNG图片(宽度为250px)',
                                onclick: function () {
                                    this.exportChart({
                                        width: 200 //导出报表的宽度
                                    });
                                }
                            }, {
                                text: '导出PNG图片(宽度为800px)',
                                onclick: function () {
                                    this.exportChart();// 800px by default
                                }
                            },
                                null,
                                null
                            ]
                        },
                        printButton: {
                            enabled: false
                        }
                    },
                    filename: '历年成果数量统计'
                };

                var json = {};
                json.chart = chart;
                json.title = title;
                json.tooltip = tooltip;
                json.xAxis = xAxis;
                json.yAxis = yAxis;
                json.series = series;
                json.plotOptions = plotOptions;
                json.credits = credits;
                json.exporting = exporting;
                jQuery('#container').highcharts(json);
                jQuery('.highcharts-credits').hide();


                // 饼状图
                jQuery('#pieChart').highcharts({
                    chart: {
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false
                    },
                    colors: ['#90ed7d', '#f7a35c', '#8085e9',
                        '#f15c80', '#e4d354', '#8085e8', '#8d4653', '#91e8e1'],
                    title: {
                        text: '成果类型占比'
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
                    exporting: {
                        enabled: true,
                        buttons: {
                            exportButton: {
                                menuItems: [{
                                    text: '导出PNG图片(宽度为250px)',
                                    onclick: function () {
                                        this.exportChart({
                                            width: 200 //导出报表的宽度
                                        });
                                    }
                                }, {
                                    text: '导出PNG图片(宽度为800px)',
                                    onclick: function () {
                                        this.exportChart();// 800px by default
                                    }
                                },
                                    null,
                                    null
                                ]
                            },
                            printButton: {
                                enabled: false
                            }
                        },
                        filename: '成果类型占比'
                    },
                    credits: {
                        enabled: false
                    },
                    series: [{
                        type: 'pie',
                        name: '成果类型占比',
                        data: [
                            ['课题', ${resultCount} <c:if test="${empty resultCount}">0</c:if>],
                            ['调研', ${researchReport} <c:if test="${empty researchReport}">0</c:if>]
                        ]
                    }]
                });
            });
        </script>
    </div>

</div>
</body>
</html>
