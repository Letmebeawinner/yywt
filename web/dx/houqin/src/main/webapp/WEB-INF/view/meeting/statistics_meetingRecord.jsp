<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>会场使用统计</title>
    <script type="text/javascript" src="${ctx}/static/js/highcharts.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/highcharts-zh_CN.js"></script>
    <script>
        jQuery(function () {
            jQuery('#container').highcharts({
                chart: {
                    type: 'line'
                },
                title: {
                    text: '${queryYear}会场情况'
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
                        name: '会场使用次数',
                        data: [${pictureData}]
                    }
                ]
            });
        });

    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">会场使用统计</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于会场使用情况统计；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <form action="${ctx}/admin/houqin/to/meetingRecord/statistics.json" id="submitForm" class="disIb"
                  method="post">
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

        <div class="pr">
            <div id="container" style="width:100%;height: 400px"></div>
            <table id="datatable" class="stdtable" cellspacing="0" cellpadding="0" border="0">
                <thead>
                <tr>
                    <th class="center">月份</th>
                    <th>使用数</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${data}" var="entry" varStatus="index">
                    <tr>
                        <td>${entry.key}</td>
                        <td>
                            <a href="javascript:void(0)" onclick="toStatisticsDetail(${entry.key})">
                                <c:if test="${empty entry.value}">0</c:if>${entry.value}
                            </a>
                        </td>

                    </tr>
                </c:forEach>
                <tr>
                </tbody>
            </table>
        </div>
        <div class="pr">
            <div style="height: 30px;font-weight: bold;margin-top: 20px">班次使用的次数</div>
            <table class="stdtable" cellspacing="0" cellpadding="0" border="0">
                <thead>
                <tr>
                    <c:forEach items="${meetingRecordsGroupByClass.get(0)}" var="map" varStatus="index">
                        <c:choose>
                            <c:when test="${map.key == 0}">
                                <th>班次名称</th>
                            </c:when>
                            <c:otherwise>
                                <th>${map.key} 月</th>
                            </c:otherwise>
                        </c:choose>

                    </c:forEach>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${meetingRecordsGroupByClass}" var="map">
                    <tr>
                        <c:forEach items="${map}" var="entry" varStatus="index">

                            <td <c:if test="${index.count == 1}">style="width:20%;text-align: center"</c:if>>
                                <c:if test="${empty entry.value and index.count != 1}">0</c:if>${entry.value}
                            </td>
                        </c:forEach>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

</div>
<script>
    function searchForm() {
        jQuery("#submitForm").submit();
    }

    function toStatisticsDetail(month) {
        var year = jQuery("#queryYear").val();
        window.location.href = "${ctx}/admin/houqin/to/meetingRecord/statistics/detail.json?year=" + year + "&month=" + month;
    }
</script>
</body>
</html>