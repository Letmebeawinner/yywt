<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>用气量占比统计</title>
    <script type="text/javascript" src="${ctx}/static/js/highcharts.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/highcharts-zh_CN.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/exporting.js"></script>
    <script type="text/javascript">
        $(function () {
            var type ='${queryType}';
            jQuery('#courseYear').val('${years}');
            jQuery('#month').val('${month}');
            jQuery('#season').val('${season}');
            query(type);
            jQuery(function () {
                jQuery('#container').highcharts({
                    chart: {
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false
                    },
                    title: {
                        text: '${describe}用气量的占比'
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
                    series: [{
                        type: 'pie',
                        name: '用气量占比',
                        data: [
                            <c:forEach items="${naturalStatisticList}" var="natural" varStatus="index">
                            <c:if test="${!index.last}">
                            ['${natural.type}', ${natural.amount}],
                            </c:if>
                            <c:if test="${index.last}">
                            ['${natural.type}', ${natural.amount}]
                            </c:if>
                            </c:forEach>
                        ]
                    }]
                });
            });
            jQuery('#containerCompare').highcharts({
                chart: {
                    type: 'column'
                },
                title: {
                    text: '用气量统计列表'
                },
                subtitle: {
                    text: '各区域用气量统计'
                },
                xAxis: {
                    categories: [
                        ${name}
                    ],
                    crosshair: true
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: 'm³'
                    }
                },
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y:.1f}立方米</b></td></tr>',
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
                    name: '用气量',
                    data: [${number}]
                }]
            });

        });
        function searchForm() {
            jQuery("#searchForm").submit();
        }
        function query(type) {
            var queryType = jQuery("#queryType").val();
            if ("" == queryType) {
                queryType = type;
            }
            if (queryType == 1) {
                jQuery(".queryType").hide();
                jQuery(".querySeason").show();
                jQuery("#queryMonth").val(0);
            }
            if (queryType == 2) {
                jQuery(".queryType").hide();
                jQuery(".queryMonth").show();
                jQuery("#querySeason").val(0);
            }
        }
        function compareResult() {
            var valueA= parseFloat(jQuery("#compareA").val());
            var valueB= parseFloat(jQuery("#compareB").val());
            if (valueA == null || valueA == 0 || valueB == null || valueB == 0){
                alert("请选择对比区域");
                return;
            }
            var typeA = jQuery("#compareA option:selected").attr("data-id");
            var typeB = jQuery("#compareB option:selected").attr("data-id");
            jQuery('#containerCompare').highcharts({
                chart: {
                    type: 'column'
                },
                title: {
                    text: '用气量统计列表'
                },
                subtitle: {
                    text: '各区域用气量统计'
                },
                xAxis: {
                    categories: [
                        typeA,typeB
                    ],
                    crosshair: true
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: 'm³'
                    }
                },
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y:.1f}立方米</b></td></tr>',
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
                    name: '用气量',
                    data: [ valueA,valueB]
                }]
            });
        }

    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">用气量占比统计</h1>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <!-- 数据显示列表，开始 -->
        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5" style="width: 45%; float: left">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/statisticByNaturalPurpose.json"
                      method="post">

                    <div class="disIb ml20 mb10">
                        <span class="vam">年份 &nbsp;</span>
                        <label class="vam">
                            <select name="year" id="queryYear">
                                <c:forEach begin="${beginYear}" end="${endYear}" var="year">
                                    <option value="${year}"
                                            <c:if test="${years == year}">selected</c:if>>${year}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>

                    <div class="disIb ml20 mb10 ">
                        <span class="vam">选择类型 &nbsp;</span>
                        <label class="vam">
                            <select name="queryType" id="queryType" onchange="query()">
                                <option value="">请选择</option>
                                <option value="1" <c:if test="${queryType == 1}">selected =selected</c:if>>按季度</option>
                                <option value="2" <c:if test="${queryType == 2}">selected =selected</c:if>>按月份</option>
                            </select>
                        </label>
                    </div>

                    <div class="disIb ml20 mb10 queryType querySeason" style="display: none">
                        <span class="vam">季度 &nbsp;</span>
                        <label class="vam">
                            <select name="season" id="querySeason">
                                <option value="">请选择</option>
                                <option value="1"
                                        <c:if test="${season == 1}">selected</c:if>  >第一季度
                                </option>
                                <option value="2"
                                        <c:if test="${season == 2}">selected</c:if> >第二季度
                                </option>
                                <option value="3"
                                        <c:if test="${season == 3}">selected</c:if> >第三季度
                                </option>
                                <option value="4"
                                        <c:if test="${season == 4}">selected</c:if> >第四季度
                                </option>
                            </select>
                        </label>
                    </div>
                    <div class="disIb ml20 mb10 queryType queryMonth" style="display: none">
                        <span class="vam">月份 &nbsp;</span>
                        <label class="vam">
                            <select name="month" id="queryMonth">
                                <option value="">请选择</option>
                                <c:forEach items="${monthList}" var="month">
                                    <option value="${month}" <c:if test="${months == month}">selected</c:if>>${month}月
                                    </option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                </div>
                <div class="disIb ml20 mb10">
                    总量：${count} &nbsp;&nbsp;
                </div>
            </div>
            <div class="fl mt5" style="width: 45%; float: right">
                <div class="disIb ml20 mb10 queryType querySeason" >
                    <span class="vam">对比区域一 &nbsp;</span>
                    <label class="vam">
                        <select name="month" id="compareA">
                            <option value="">请选择</option>
                            <c:forEach items="${naturalStatisticList}" var="water">
                                <option value="${water.amount}" data-id="${water.type}">${water.type}</option>
                            </c:forEach>
                        </select>
                    </label>
                </div>
                <div class="disIb ml20 mb10 queryType querySeason" >
                    <span class="vam">对比区域二 &nbsp;</span>
                    <label class="vam">
                        <select name="month" id="compareB">
                            <option value="">请选择</option>
                            <c:forEach items="${naturalStatisticList}" var="water">
                                <option value="${water.amount}" data-id="${water.type}">${water.type}</option>
                            </c:forEach>
                        </select>
                    </label>
                </div>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="compareResult()" class="stdbtn btn_orange">对 比</a>
                </div>

            </div>
        </div>
        <div id="container" style="width:50%;height: 400px;float: left"></div>
        <div id="containerCompare" style="width:50%;height: 400px;float: right"></div>

    </div>
</div>
</body>
</html>