<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>学员统计列表</title>
    <script type="text/javascript" src="${ctx}/static/js/highcarts/exporting.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/highcarts/highcharts.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/highcarts/highcharts-zh_CN.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/highcarts/data.js"></script>
    <script type="text/javascript">
        jQuery(function(){

            laydate.skin('molv');
            laydate({
                elem: '#startTime',
                format:'YYYY-MM-DD'
            });
            laydate.skin('molv');
            laydate({
                elem: '#endTime',
                format:'YYYY-MM-DD'
            });

            jQuery('#container').highcharts({
                    chart: {
                        type: 'column'
                    },
                    title: {
                        text: '学员统计列表'
                    },
                    subtitle: {
                        text: '各个班报名人数统计'
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
                            text: '人数'
                        }
                    },
                    tooltip: {
                        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                        '<td style="padding:0"><b>{point.y:.1f}人</b></td></tr>',
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
                        name: '人数',
                        data: [${number}]
                    }]
                });

            jQuery("#classTypeId").change(function(){
                var selectedClassTypeId = jQuery(this).children('option:selected').val();
                jQuery.ajax({
                    url:'${ctx}/admin/jiaowu/class/getClassListByClassType.json',
                    data:{"classTypeId":selectedClassTypeId
                    },
                    type:'post',
                    dataType:'json',
                    success:function (result){
                        if(result.code=="0"){
                            var list=result.data;
                            var classstr="<option value=0>请选择</option>";
                            if(list!=null&&list.length>0){

                                for(var i=0;i<list.length;i++){
                                    classstr+="<option value='"+list[i].id+"'>"+list[i].name+"</option>";
                                }

                            }
                            jQuery("#classId").html(classstr);
                            jQuery("#classId").val("${userExcelRecord.classId}");
                        }else{
                            jAlert(result.message,'提示',function() {});
                        }
                    } ,
                    error:function(e){
                        jAlert('添加失败','提示',function() {});
                    }
                });
            });
        });


        function searchForm(){
            jQuery("#searchForm").submit();
        }

        function emptyForm(){
            jQuery("#classTypeId").val("");
            jQuery("#classId").val("");
            jQuery("#age").val("");
            jQuery("#ageOne").val("");
            jQuery("#business").val("");
            jQuery("#startTime").val("");
            jQuery("#endTime").val("");
        }

    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">学员统计列表</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示学员统计列表.<br />
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/user/userStatisticList.json" method="post">
        <!-- 搜索条件，开始 -->
        <div class="tableoptions disIb mb10">
            <span class="vam">班型 &nbsp;</span>
            <label class="vam">
                <select name="classTypeStatistic.classTypeId" class="vam" id="classTypeId">
                    <option value="0">请选择</option>
                    <c:if test="${classTypeList!=null&&classTypeList.size()>0}">
                        <c:forEach items="${classTypeList }" var="classType">
                            <option <c:if test="${classType.id==classTypeStatistic.classTypeId }"> selected="selected"</c:if> value="${classType.id }">${classType.name}</option>
                        </c:forEach>
                    </c:if>
                </select>
            </label>
        </div>

        <div class="tableoptions disIb mb10">
            <span class="vam">是否党员 &nbsp;</span>
            <label class="vam">
                <select name="classTypeStatistic.politicalStatus" class="vam" id="politicalStatus">
                    <option value="-2">请选择</option>
                    <option value="1" <c:if test="${classTypeStatistic.politicalStatus==1}">selected="selected"</c:if>>是</option>
                    <option value="2" <c:if test="${classTypeStatistic.politicalStatus==2}">selected="selected"</c:if>>否</option>
                </select>
            </label>
        </div>

        <div class="tableoptions disIb mb10">
            <span class="vam">班次 &nbsp;</span>
            <label class="vam">
                <select name="classTypeStatistic.classId" class="vam" id="classId" >
                    <option value="0">请选择</option>
                    <c:if test="${classesList!=null&&classesList.size()>0}">
                        <c:forEach items="${classesList }" var="classes">
                            <option <c:if test="${classes.id==classTypeStatistic.classId }"> selected="selected"</c:if> value="${classes.id }">${classes.name}</option>
                        </c:forEach>
                    </c:if>
                </select>
            </label>
        </div>

        <div class="disIb ml20 mb10">
            <span class="vam">年龄 &nbsp;</span>
            <label class="vam">
                <input id="age" style="width: auto;" name="classTypeStatistic.age" type="text" class="hasDatepicker" placeholder="请输入年龄" value="${classTypeStatistic.age}">&nbsp;-&nbsp;
                <input id="ageOne" style="width: auto;" name="classTypeStatistic.ageOne" type="text" class="hasDatepicker" placeholder="请输入年龄" value="${classTypeStatistic.ageOne}">
            </label>
        </div>

        <div class="disIb ml20 mb10">
            <span class="vam">级别 &nbsp;</span>
            <label class="vam">
                <select name="classTypeStatistic.business" id="business">
                    <option value="">请选择</option>
                    <option <c:if test="${classTypeStatistic.business==1 }"> selected="selected"</c:if> value="1">正厅</option>
                    <option <c:if test="${classTypeStatistic.business==2 }"> selected="selected"</c:if> value="2">巡视员</option>
                    <option <c:if test="${classTypeStatistic.business==3 }"> selected="selected"</c:if> value="3">副厅</option>
                    <option <c:if test="${classTypeStatistic.business==4 }"> selected="selected"</c:if> value="4">副巡视员</option>
                    <option <c:if test="${classTypeStatistic.business==5 }"> selected="selected"</c:if> value="5">正县</option>
                    <option <c:if test="${classTypeStatistic.business==6 }"> selected="selected"</c:if> value="6">副县</option>
                    <option <c:if test="${classTypeStatistic.business==7 }"> selected="selected"</c:if> value="7">调研员</option>
                    <option <c:if test="${classTypeStatistic.business==8 }"> selected="selected"</c:if> value="8">副调研员</option>
                    <option <c:if test="${classTypeStatistic.business==9 }"> selected="selected"</c:if> value="9">正科</option>
                    <option <c:if test="${classTypeStatistic.business==10}"> selected="selected"</c:if> value="10">副科</option>
                    <option <c:if test="${classTypeStatistic.business==11}"> selected="selected"</c:if> value="11">工作人员</option>
                </select>
            </label>
        </div>

        <div class="disIb ml20 mb10">
            <span class="vam">开始时间 &nbsp;</span>
            <label class="vam">
                <input id="startTime" type="text" class="width100 laydate-icon" name="classTypeStatistic.startTime"  readonly value="<fmt:formatDate value='${classTypeStatistic.startTime}' pattern='yyyy-MM-dd'/>" style="height: 30px;width: 150px;"/>
            </label>
        </div>

            <div class="disIb ml20 mb10">
                <span class="vam">结束时间 &nbsp;</span>
                <label class="vam">
                    <input id="endTime" type="text" class="width100 laydate-icon" name="classTypeStatistic.endTime"  readonly value="<fmt:formatDate value='${classTypeStatistic.endTime}' pattern='yyyy-MM-dd'/>" style="height: 30px;width: 150px;"/>
                </label>
            </div>

        </form>
        <div class="disIb ml20 mb10">
            <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
            <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
            <%--<a href="javascript: void(0)" onclick="useExcel()" class="stdbtn btn_orange">导出Excel</a>--%>
        </div>
        <!-- 搜索条件，结束 -->
        <div id="container" style="width: 100%;height: 400px;"></div>
    </div>
</div>
</body>
</html>