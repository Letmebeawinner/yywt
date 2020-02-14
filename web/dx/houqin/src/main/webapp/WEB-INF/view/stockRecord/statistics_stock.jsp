<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>出入库统计</title>
    <script type="text/javascript" src="${ctx}/static/js/highcharts.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/highcharts-zh_CN.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/exporting.js"></script>
    <script>
        jQuery(function () {
            jQuery('#container').highcharts({
                chart: {
                    type: 'line'
                },
                title: {
                    text: '${queryYear}出入库情况'
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
                series: [
                    {
                        name: '入库',
                        data: [${pictureJoinData}]
                    },
                    {
                        name: '出库',
                        data: [${pictureOutData}]
                    }
                ]
            });
        });

    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">出入库统计</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于入库管理列表查看；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <form action="${ctx}/admin/houqin/to/stock/statistics.json" id="submitForm" class="disIb" method="post">
                <select name="year" id="queryYear">
                    <c:forEach begin="${beginYear}" end="${endYear}" var="year">
                        <option value="${year}" <c:if test="${queryYear == year}">selected</c:if>>${year}</option>
                    </c:forEach>
                </select>
            </form>
            <div class="disIb ml20 mb10">
                <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
            </div>
        </div>

        <div id="container" style="width:100%;height: 400px"></div>

        <table id="datatable" class="stdtable" cellspacing="0" cellpadding="0" border="0">
            <thead>
            <tr>
                <th class="center">月份</th>
                <th>入库数</th>
                <th>出库数</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${data}" var="entry" varStatus="index">
                <tr>
                    <td>${entry.key}</td>
                    <td>
                        <a href="javascript:void(0)" onclick="toStatisticsDetail(${entry.key}, 'join')">
                            <c:if test="${empty entry.value.join}">0</c:if>${entry.value.join}
                        </a>
                    </td>

                    <td>
                        <a href="javascript:void(0)" onclick="toStatisticsDetail(${entry.key}, 'out')">
                            <c:if test="${empty entry.value.join}">0</c:if>
                            <c:if test="${empty entry.value.out}">0</c:if>${entry.value.out}
                        </a>
                    </td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
    </div>

</div>
</div>
<script>
    function searchForm() {
        jQuery("#submitForm").submit();
    }

    function toStatisticsDetail(month, type) {
        var year = jQuery("#queryYear").val();
        window.location.href = "${ctx}/admin/houqin/to/stock/statistics/detail.json?type=" + type + "&year=" + year + "&month=" + month;
    }
</script>
</body>
</html>