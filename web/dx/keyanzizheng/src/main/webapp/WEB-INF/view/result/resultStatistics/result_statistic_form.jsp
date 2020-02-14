<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>档案类型的占比</title>
    <script type="text/javascript" src="${ctx}/static/js/highcharts.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/highcharts-zh_CN.js"></script>
    <script type="text/javascript">
        jQuery(function () {
            jQuery('#container').highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false
                },
                title: {
                    text: '各个档案类型的占比'
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
                    name: '各个档案类型的占比',
                    data: [
                        <c:forEach items="${resultFormStatisticList}" var="statistic" varStatus="index">
                        <c:if test="${!index.last}">
                        ['${statistic.name}', ${statistic.num}],
                        </c:if>
                        <c:if test="${index.last}">
                        ['${statistic.name}', ${statistic.num}]
                        </c:if>
                        </c:forEach>
                    ]
                }]
            });


            // 回显下拉列表
            jQuery("select[name='year']").val(jQuery("input[name='selectYear']").val())
        });


        /**
         * 执行查询
         */
        function searchForm() {
            jQuery("#searchForm").submit();
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">档案类型的占比</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <!-- 数据显示列表，开始 -->
        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/ky/getEmployeeResultStatistic.json"
                      method="post">
                    <input type="hidden" name="id" value="${id}">
                    <input type="hidden" value="${selectYear}" name="selectYear">
                    <div class="disIb ml20 mb10">
                        <span class="vam">年份 &nbsp;</span>
                        <label class="vam">
                            <select name="year">
                                <c:forEach begin="2011" end="${currentYear}" var="year">
                                    <option value="${year}">${year}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                </div>
            </div>
        </div>
        <div id="container" style="width:100%;height: 400px;margin-bottom: 30px;"></div>

        <!-- 数据显示列表，开始 -->
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <thead>
                <tr>
                    <th class="head0 center">类型名称</th>
                    <th class="head0 center">数量</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${not empty resultFormStatisticList}">
                    <c:forEach items="${resultFormStatisticList}" var="result">
                        <tr>
                            <td>${result.name}</td>
                            <td>${result.num}</td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
        </div>
        <!-- 数据显示列表，结束 -->
    </div>
</div>
</body>
</html>