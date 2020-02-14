<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>库存量</title>
    <script type="text/javascript" src="${ctx}/static/js/highcharts.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/highcharts-zh_CN.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/exporting.js"></script>
    <script type="text/javascript">
        jQuery(function () {
            jQuery("#queryYear").val('${year}');

            jQuery(".highcharts-credits").hide();
            jQuery('#container').highcharts({
                chart: {
                    type: 'line'
                },
                title: {
                    text: '${year}年用水量(单位吨)'
                },
                subtitle: {
                    text: '贵阳市委党校'
                },
                xAxis: {
                    categories: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
                },
                yAxis: {
                    title: {
                        text: ''
                    }
                },
                plotOptions: {
                    line: {
                        dataLabels: {
                            enabled: true          // 开启数据标签
                        },
                        enableMouseTracking: true // 关闭鼠标跟踪，对应的提示框、点击事件会失效
                    }
                },
                series: [{
                    name: '用水量',
                    data: [${data}]
                }]
            });

            jQuery('#container1').highcharts({
                chart: {
                    type: 'line'
                },
                title: {
                    text: '近三年用水量比较'
                },
                xAxis: {
                    categories: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
                },
                yAxis: {
                    title: {
                        text: '吨'
                    }
                },
                plotOptions: {
                    line: {
                        dataLabels: {
                            enabled: true          // 开启数据标签
                        },
                        enableMouseTracking: true // 关闭鼠标跟踪，对应的提示框、点击事件会失效
                    }
                },
                series: [
                    {
                        name: '${beforeyear}年',
                        data: [${beforeData}]
                    },
                    {name: '${lastyear}年',
                        data: [${lastData}]
                    },
                    {
                        name: '${years}年',
                        data: [${currentData}]
                    }]
            });



        });


        function searchForm() {
            jQuery("#searchForm").submit();
        }

    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">用水量统计</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <!-- 数据显示列表，开始 -->
        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/statisticWater.json" method="post">

                    <div class="disIb ml20 mb10">
                        <span class="vam">年份 &nbsp;</span>
                        <label class="vam">
                            <select name="year" id="queryYear">
                                <c:forEach begin="2011" end="2017" var="year">
                                    <option value="${year}">${year}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                </div>
                <div class="disIb ml20 mb10">
                    <span>总数：${sum}吨</span>
                </div>
            </div>
        </div>
        <div id="container" style="width:100%;height: 400px"></div>

        <!-- 搜索条件，结束 -->
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
                    <th class="head0 center">月份</th>
                    <th class="head0 center">用量(吨)</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${waterStatisticList}" var="statistic">
                    <tr>
                        <td><b>${statistic.month}月</b></td>
                        <td><b>${statistic.amount}</b></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- 分页，开始 -->
            <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
            <!-- 分页，结束 -->
        </div>
        <!-- 数据显示列表，结束 -->
        <div id="container1" style="width:100%;height: 400px;margin-top: 50px"></div>
        <!-- 数据显示列表，结束 -->
    </div>
</div>
</body>
</html>