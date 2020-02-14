<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>用油量占比统计</title>
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
            jQuery('#container').highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false
                },
                title: {
                    text: '${year} 用油量的占比'
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
                    name: '用油量占比',
                    data: [
                        ['绿化', ${lvhuaCount}],
                        ['发电', ${fadianCount}],
                        ['其他', ${othserCount}]
                    ]
                }]
            });


            jQuery('#container1').highcharts({
                chart: {
                    type: 'line'
                },
                title: {
                    text: '近三年用油量比较'
                },
                xAxis: {
                    categories: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
                },
                yAxis: {
                    title: {
                        text: '升'
                    }
                },
                plotOptions: {
                    line: {
                        dataLabels: {
                            enabled: true          // 开启数据标签
                        },
                        enableMouseTracking: false // 关闭鼠标跟踪，对应的提示框、点击事件会失效
                    }
                },
                series: [
                    {
                        name: '${beforeyear}年',
                        data: [${beforeData}]
                    },
                    {
                        name: '${lastyear}年',
                        data: [${lastData}]
                    },
                    {
                        name: '${year}年',
                        data: [${currentData}]
                    }]
            });

            jQuery('#containerCompare').highcharts({
                chart: {
                    type: 'column'
                },
                title: {
                    text: '用油量统计列表'
                },
                subtitle: {
                    text: '各区域用油量统计'
                },
                xAxis: {
                    categories: [
                        "绿化总量","发电总量","其他用量"
                    ],
                    crosshair: true
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: '升'
                    }
                },
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y:.1f}升</b></td></tr>',
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
                    name: '用油量',
                    data: [${lvhuaCount},${fadianCount},${othserCount}]
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
            if (valueA == null || valueB == null ){
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
                    text: '用油量统计列表'
                },
                subtitle: {
                    text: '用油量统计对比'
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
                        text: 'L'
                    }
                },
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y:.1f}升</b></td></tr>',
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
                    name: '用油量',
                    data: [ valueA,valueB]
                }]
            });
        }
        
        
        
        
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">用油占比统计</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <!-- 数据显示列表，开始 -->
        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5" style="width: 45%; float: left">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/statisticByOilPurpose.json"
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
                    总量：${count} &nbsp;&nbsp;&nbsp;绿化总量：${lvhuaCount}升 &nbsp;&nbsp;&nbsp;发电总量：${fadianCount}升 &nbsp;&nbsp;&nbsp;其他：${othserCount}升
                </div>
            </div>
            <div class="fl mt5" style="width: 45%; float: right">
                <div class="disIb ml20 mb10 queryType querySeason" >
                    <span class="vam">对比区域一 &nbsp;</span>
                    <label class="vam">
                        <select name="month" id="compareA">
                            <option value="">请选择</option>
                                <option value="${lvhuaCount}" data-id="绿化用油">绿化用油</option>
                                <option value="${fadianCount}" data-id="发电用油">发电用油</option>
                                <option value="${othserCount}" data-id="其他用油">其他用油</option>
                        </select>
                    </label>
                </div>
                <div class="disIb ml20 mb10 queryType querySeason" >
                    <span class="vam">对比区域二 &nbsp;</span>
                    <label class="vam">
                        <select name="month" id="compareB">
                            <option value="">请选择</option>
                            <option value="${lvhuaCount}" data-id="绿化用油">绿化用油</option>
                            <option value="${fadianCount}" data-id="发电用油">发电用油</option>
                            <option value="${othserCount}" data-id="其他用油">其他用油</option>
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

        <div id="container1" style="width:100%;height: 400px;margin-top: 50px"></div>

    </div>
</div>
</body>
</html>