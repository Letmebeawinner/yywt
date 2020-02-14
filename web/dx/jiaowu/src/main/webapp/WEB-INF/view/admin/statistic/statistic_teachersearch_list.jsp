<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>各个教研部的课时</title>
    <script type="text/javascript" src="${ctx}/static/js/highcharts.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/highcharts-zh_CN.js"></script>
    <script type="text/javascript">
        jQuery(function () {
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

        jQuery(function(){
            jQuery("#container").highcharts({    //图表展示容器，与div的id保持一致
                chart: {
                    type:'column'    //指定图表的类型，默认是折线图（line）
                },
                title: {
                    text:'各个教研部的课时' //指定图表标题
                },
                xAxis: {
                    categories: ['党史', '公管', '经济学', '法学', '文化与社会发展', '马列', '统一战线'] //指定X分组
                },
                yAxis: {
                    title: {
                        text:'数量' //指定Y轴的标题
                    },
                },
                series: [ //指定数据列，数据列里的数据是可以随业务的需求改变的
                    {
                        name:'教研部', //数据列名
                        data:[${dangshiCount},  ${gongguanCount},
                            ${jingjiCount}, ${faxueCount}, ${wenhuaCount},
                            ${malieCount}, ${tongyiCount}] //数据
                    }
                ]
            });
        });


        function searchForm(){
            jQuery("#searchForm").submit();
        }

        function emptyForm(){
            jQuery("#startTime").val('');
            jQuery("#endTime").val('');
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">各个教研部的课时占比</h1>
    </div><!--pageheader-->
    <div class="overviewhead clearfix mb10">
        <div class="fl mt5">
            <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/statistic/statisticCourseByTeacherResearch.json" method="get">
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
            </div>
        </div>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <!-- 数据显示列表，开始 -->
        <div id="container" style="width:100%;height: 400px"></div>

        <div class="head0" style="margin: 5px;"><b>课时总数：${total}&nbsp;&nbsp;&nbsp;&nbsp;主体班总课时：${zhutiTotalCount}&nbsp;&nbsp;&nbsp;&nbsp;专职讲师总数：${teacherCount}
            &nbsp;&nbsp;&nbsp;&nbsp;专职讲师主体班总课时平均数：${teacherZhuTiAverage}&nbsp;&nbsp;&nbsp;&nbsp;各教研部主体班平均课时数：${jiaoyanZhuTiAverage}</b></div>
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
                    <th class="head0 center">教研部名称</th>
                    <th class="head0 center">课时量</th>
                </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><b>党史</b></td>
                        <td><b>${dangshiCount}</b></td>
                    </tr>
                    <tr>
                        <td><b>公管</b></td>
                        <td><b>${gongguanCount}</b></td>
                    </tr>
                    <tr>
                        <td><b>经济学</b></td>
                        <td><b>${jingjiCount}</b></td>
                    </tr>
                    <tr>
                        <td><b>法学</b></td>
                        <td><b>${faxueCount}</b></td>
                    </tr>
                    <tr>
                        <td><b>文化与社会发展</b></td>
                        <td><b>${wenhuaCount}</b></td>
                    </tr>
                    <tr>
                        <td><b>马列</b></td>
                        <td><b>${malieCount}</b></td>
                    </tr>
                    <tr>
                        <td><b>统一战线</b></td>
                        <td><b>${tongyiCount}</b></td>
                    </tr>
                </tbody>
            </table>

        </div>

    </div>
</div>
</body>
</html>