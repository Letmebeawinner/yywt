<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>区域用电占比</title>
    <script type="text/javascript" src="${ctx}/static/js/highcharts.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/highcharts-zh_CN.js"></script>
    <script type="text/javascript">
        $(function () {
            //判断宽度后赋值
            jQuery('#searchForm').width(jQuery('#contentwrapper').width()-300);
            jQuery('div[name="contentParent"]').width(jQuery('#searchForm').width()-200);
            jQuery('label[name="contentR"]').width(jQuery('div[name="contentParent"]').width()-100);

            jQuery("#queryYear").val('${year}');
            jQuery('#container').highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false
                },
                title: {
                    text: '区域用电占比'
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
                    name: '区域用电占比',
                    data: [
                        <c:forEach items="${typeVOS}" var="typeVOS" varStatus="status">
                        ['${typeVOS.name}', ${typeVOS.num}]
                        <c:if test="${!status.last}">, </c:if>
                        </c:forEach>
                    ]
                }]
            });


            jQuery('#containerCompare').highcharts({
                chart: {
                    type: 'column'
                },
                title: {
                    text: '用电量统计列表'
                },
                subtitle: {
                    text: '各区域用电量统计'
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
                        text: '度'
                    }
                },
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y:.1f}度</b></td></tr>',
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
                    name: '用电量',
                    data: [${number}]
                }]
            });
            
            
            
            

        });


        function searchForm() {
            jQuery("#searchForm").submit();
        }





        function compareResult() {
            var valueA= parseFloat(jQuery("#compareA").val());
            var valueB= parseFloat(jQuery("#compareB").val());
            if (valueA == null  || valueB == null ){
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
                    text: '用电量统计列表'
                },
                subtitle: {
                    text: '各区域用电量统计'
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
                        text: '度'
                    }
                },
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y:.1f}度</b></td></tr>',
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
                    name: '用电量',
                    data: [ valueA,valueB]
                }]
            });
        }


        
        
        

    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">区域用电占比</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <!-- 数据显示列表，开始 -->
        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5" style="float: left">
                <form class="disIb" id="searchForm"
                      action="${ctx}/admin/houqin/regionalElectricityConsumptionRatio.json" method="post">

                    <div class="disIb ml20 mb10">
                        <span class="vam">年份 &nbsp;</span>
                        <label class="vam">
                            <select name="queryYear" id="queryYear" autocomplete="off">
                                <c:forEach begin="${beginYear}" end="${endYear}" var="year" varStatus="index">
                                    <option <c:if test="${queryYear == year}"> selected="selected"</c:if> value="${year}">${year}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                    <div class="disIb ml20 mb10" name="contentParent">
                        <span class="vam" style="width: 40px;display: inline-block">区域 &nbsp;</span>
                        <label class="vam disIb" name="contentR">
                            <ul>
                                <c:forEach items="${electricityTypeList}" var="type">
                                    <c:if test="${ids==null}">
                                        <li style="float:left;margin-right: 5px;"><input name="ids" value="${type.id}" checked="checked" type="checkbox"/>${type.type}</li>
                                    </c:if>
                                    <c:if test="${ids!=null}">
                                        <li style="float:left;margin-right: 5px"><input name="ids" value="${type.id}"
                                                                       <c:forEach items="${ids}" var="id">
                                                                           <c:if test="${id==type.id}"> checked="checked" </c:if>
                                                                       </c:forEach>
                                                                       type="checkbox"/>${type.type}</li>
                                    </c:if>
                                </c:forEach>


                            </ul>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10"  style="float: right">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    
                </div>
            </div>
            <%--<div class="fl mt5" style="width: 45%; float: right">--%>
                <%--<div class="disIb ml20 mb10 queryType querySeason" >--%>
                    <%--<span class="vam">对比区域一 &nbsp;</span>--%>
                    <%--<label class="vam">--%>
                        <%--<select name="month" id="compareA">--%>
                            <%--<option value="">请选择</option>--%>
                            <%--<c:forEach items="${typeVOS}" var="vo">--%>
                                <%--<option value="${vo.num}" data-id="${vo.name}">${vo.name}</option>--%>
                            <%--</c:forEach>--%>
                        <%--</select>--%>
                    <%--</label>--%>
                <%--</div>--%>
                <%--<div class="disIb ml20 mb10 queryType querySeason" >--%>
                    <%--<span class="vam">对比区域二 &nbsp;</span>--%>
                    <%--<label class="vam">--%>
                        <%--<select name="month" id="compareB">--%>
                       <%--<option value="">请选择</option>--%>
                            <%--<c:forEach items="${typeVOS}" var="vo">--%>
                                <%--<option value="${vo.num}" data-id="${vo.name}">${vo.name}</option>--%>
                            <%--</c:forEach>--%>
                        <%--</select>--%>
                    <%--</label>--%>
                <%--</div>--%>
                <%--<div class="disIb ml20 mb10">--%>
                    <%--<a href="javascript: void(0)" onclick="compareResult()" class="stdbtn btn_orange">对 比</a>--%>
                <%--</div>--%>

            <%--</div>--%>
            
            
        </div>
        <div id="container" style="width:50%;height: 400px;float: left"></div>
        <div id="containerCompare" style="width:50%;height: 400px;float: right"></div>

    </div>
</div>
</body>
</html>