<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>班次课时百分比统计</title>
    <script src="${ctx}/static/js/echarts.js"></script>
    <script type="text/javascript">


        jQuery(function(){
            laydate.skin('molv');
            laydate({
                elem: '#startTime',
                format:'YYYY-MM-DD hh:mm:ss'
            });
            laydate({
                elem: '#endTime',
                format:'YYYY-MM-DD hh:mm:ss'
            });
        });

        function searchForm(){
            jQuery("#searchForm").submit();
        }

        function emptyForm(){
            jQuery("input:text").val('');
            jQuery("select").val(0);
            jQuery("#teacherId").val(0);
            jQuery("#teacherName").val("");
            jQuery("#teacherspan").html("");
        }

        function excel(){
            jQuery("#searchForm").attr("action","${ctx}/admin/jiaowu/courseArrange/courseArrangePercentOfClassExcel.json");
            jQuery("#searchForm").submit();
            jQuery("#searchForm").attr("action","${ctx}/admin/jiaowu/courseArrange/courseArrangePercentOfClass.json");
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">班次课时百分比统计</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/courseArrange/courseArrangePercentOfClass.json" method="get">
                    <div class="disIb ml20 mb10">
                        <span class="vam">开始时间 &nbsp;</span>
                        <label class="vam">
                            <input id="startTime" style="width: auto;height:35px;" name="startTime" type="text" class="hasDatepicker laydate-icon" value="${startTime}" placeholder="请输入开始时间">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">结束时间 &nbsp;</span>
                        <label class="vam">
                            <input id="endTime" style="width: auto;height:35px;" name="endTime" type="text" class="hasDatepicker laydate-icon" value="${endTime}" placeholder="请输入结束时间">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="javascript: void(0)" onclick="excel()" class="stdbtn ml10">导出</a>
                </div>
            </div>
        </div>
        <!-- 搜索条件，结束 -->

        <!-- 数据显示列表，开始 -->
        <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
        <div id="main" style="width: 600px;height:400px;"></div>
        <script type="text/javascript">
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));

            option = {
                title : {
                    text: '班次课时百分比',
                    subtext: '',
                    x:'center'
                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                /*legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: ['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
                },*/
                series : [
                    {
                        name: '访问来源',
                        type: 'pie',
                        radius : '55%',
                        center: ['50%', '60%'],
                        data:[
                                <c:if test="${list!=null&&list.size()>0}">
                                    <c:forEach items="${list}" var="map">
                                        {value:${map.num}, name:'${map.className}'},
                                    </c:forEach>
                                </c:if>
                            /*{value:335, name:'直接访问'},
                            {value:310, name:'邮件营销'},
                            {value:234, name:'联盟广告'},
                            {value:135, name:'视频广告'},
                            {value:1548, name:'搜索引擎'}*/
                        ],
                        itemStyle: {
                            emphasis: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        </script>
    </div>
</div>
</body>

</html>