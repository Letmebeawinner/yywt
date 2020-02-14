<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>每月新建班次数量统计</title>
    <script type="text/javascript" src="${ctx}/static/js/highcharts.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/highcharts-zh_CN.js"></script>
    <script type="text/javascript">
        jQuery(function () {
            jQuery("#queryYear").val('${year}');

            jQuery(".highcharts-credits").hide();
            jQuery('#container').highcharts({
                chart: {
                    type: 'line'
                },
                title: {
                    text: '${year}年班次数量'
                },
                subtitle: {
                    text: '贵阳市委党校'
                },
                xAxis: {
                    categories: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12']
                },
                yAxis: {
                    title: {
                        text: ''
                    }
                },
                series: [{
                    name: '班次数量',
                    data: [${data}]
                }]
            });
        });


        function searchForm() {
            jQuery("#searchForm").submit();
        }

        function excel(){
            jQuery("#searchForm").attr("action","${ctx}/admin/jiaowu/statistic/statisticClassNumExcel.json");
            jQuery("#searchForm").submit();
            jQuery("#searchForm").attr("action","${ctx}/admin/jiaowu/statistic/statisticClassNum.json");
        }

    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">每月新建班次数量统计</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <!-- 数据显示列表，开始 -->
        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/statistic/statisticClassNum.json" method="post">

                    <div class="disIb ml20 mb10">
                        <span class="vam">年份 &nbsp;</span>
                        <label class="vam">
                            <select name="year" id="queryYear">
                                <c:forEach begin="${beginYear}" end="${endYear}" var="year">
                                    <option value="${year}">${year}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="excel()" class="stdbtn btn_orange">导出Excel</a>
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
                    <th class="head0 center">数量</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${classesStatisticList}" var="statistic">
                    <tr>
                        <td>${statistic.month}</td>
                        <td>${statistic.amount}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- 分页，开始 -->
            <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
            <!-- 分页，结束 -->
        </div>
        <!-- 数据显示列表，结束 -->
    </div>
</div>
</body>
</html>