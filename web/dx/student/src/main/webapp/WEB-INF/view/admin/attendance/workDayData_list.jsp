<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>考勤列表</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
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
                            jQuery("#classId").val("${userCondition.classId}");
                        }else{
                            jAlert(result.message,'提示',function() {});
                        }
                    } ,
                    error:function(e){
                        jAlert('添加失败','提示',function() {});
                    }
                });
            });
            jQuery("#classTypeId").val("${userCondition.classTypeId}");
            jQuery("#classTypeId").trigger("change");
            jQuery("#morningAttendanceStatus").val("${userCondition.morningAttendanceStatus}");
            jQuery("#afternoonAttendanceStatus").val("${userCondition.afternoonAttendanceStatus}");

        });
        function searchForm(){
            jQuery("#searchForm").submit();
        }

        function emptyForm(){
            jQuery("#classTypeId").val('');
            jQuery("#classId").val('');
            jQuery("#userName").val('');
            jQuery("#morningAttendanceStatus").val('');
            jQuery("#afternoonAttendanceStatus").val('');
            jQuery("#startTime").val('');
            jQuery("#endTime").val('');
        }

        //导出
        function exportExcel() {
            jQuery.ajax({
                url:'${ctx}/admin/jiaowu/attendance/checkAttendanceStatistics.json',
                data:jQuery("#searchForm").serialize(),
                type:'post',
                dataType:'json',
                success:function (result){
                    if(result.code=="0"){
                        $("#searchForm").prop("action", "${ctx}/admin/jiaowu/attendance/attendanceStatistics.json");
                        $("#searchForm").submit();
                        $("#searchForm").prop("action", "${ctx}/admin/jiaowu/attendance/workDayDataList.json");
                    }else{
                        jAlert(result.message,'提示',function() {});
                    }
                } ,
                error:function(e){
                    jAlert('导出失败','提示',function() {});
                }
            });

        }

    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">考勤列表</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示考勤列表.<br />
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/attendance/workDayDataList.json" method="post">

                    <div class="disIb ml20 mb10">
                        <span class="vam">班型 &nbsp;</span>
                        <label class="vam">
                            <select name="userCondition.classTypeId" class="vam" id="classTypeId">
                                <option value="0">请选择</option>
                                <c:if test="${classTypeList!=null&&classTypeList.size()>0}">
                                    <c:forEach items="${classTypeList }" var="classType">
                                        <option value="${classType.id }">${classType.name}</option>
                                    </c:forEach>
                                </c:if>
                            </select>
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">班次 &nbsp;</span>
                        <label class="vam">
                            <select name="userCondition.classId" class="vam" id="classId" >
                                <option value="0">请选择</option>
                            </select>
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">姓名 &nbsp;</span>
                        <label class="vam">
                            <input id="userName" style="width: auto;" name="userCondition.userName" type="text" class="" value="${userCondition.userName}">
                        </label>
                    </div>

                        <%--<div class="disIb ml20 mb10">--%>
                            <%--<span class="vam">第一时间段 &nbsp;</span>--%>
                            <%--<label class="vam">--%>
                                <%--<select id="morningAttendanceStatus" class="vam" name="userCondition.morningAttendanceStatus" style="width:150px">--%>
                                    <%--<option value="">--请选择--</option>--%>
                                    <%--<option value="1">正常</option>--%>
                                    <%--<option value="2">迟到</option>--%>
                                    <%--<option value="3">早退</option>--%>
                                    <%--<option value="4">旷课</option>--%>
                                <%--</select>--%>
                            <%--</label>--%>
                        <%--</div>--%>

                        <%--<div class="disIb ml20 mb10">--%>
                            <%--<span class="vam">第二时间段 &nbsp;</span>--%>
                            <%--<label class="vam">--%>
                                <%--<select id="afternoonAttendanceStatus" name="userCondition.afternoonAttendanceStatus" style="width:150px">--%>
                                    <%--<option value="">--请选择--</option>--%>
                                    <%--<option value="1">正常</option>--%>
                                    <%--<option value="2">迟到</option>--%>
                                    <%--<option value="3">早退</option>--%>
                                    <%--<option value="4">旷课</option>--%>
                                <%--</select>--%>
                            <%--</label>--%>
                        <%--</div>--%>
                    <div class="disIb ml20 mb10">
                        <span class="vam">开始日期 &nbsp;</span>
                        <label class="vam">
                            <input id="startTime" type="text"  name="userCondition.startTime" value="<fmt:formatDate value="${userCondition.startTime}" pattern="yyyy-MM-dd"/>"/>
                        </label>
                    </div>
                       <div class="disIb ml20 mb10">
                            <span class="vam">截止日期 &nbsp;</span>
                            <label class="vam">
                                <input id="endTime" type="text"  name="userCondition.endTime" value="<fmt:formatDate value="${userCondition.endTime}" pattern="yyyy-MM-dd"/>"/>
                            </label>
                        </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="javascript: void(0)" onclick="exportExcel()" class="stdbtn ml10">导 出</a>
                </div>
            </div>
        </div>
        <!-- 搜索条件，结束 -->

        <!-- 数据显示列表，开始 -->
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <colgroup>
                    <col class="con0"/>
                    <col class="con1" />
                    <col class="con0"/>
                    <col class="con1" />
                    <col class="con0"/>
                    <col class="con1" />
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">序号</th>
                    <th class="head0 center">一卡通编号</th>
                    <th class="head0 center">班型</th>
                    <th class="head0 center">班次</th>
                    <th class="head0 center">姓名</th>
                    <th class="head0 center">上午考勤状态</th>
                    <th class="head0 center">下午考勤状态</th>
                    <%--<th class="head0 center">流水号</th>--%>
                    <th class="head1 center">打卡日期</th>
                    <%--<th class="head1 center">设备编号</th>--%>
                </tr>
                </thead>
                <tbody>
                <c:if test="${userWorkDayDataList!=null&&userWorkDayDataList.size()>0 }">
                    <c:forEach items="${userWorkDayDataList}" var="userWorkDayData" varStatus="index">
                        <tr>
                            <td>${index.index+1}</td>
                            <td>${userWorkDayData.timeCardNo}</td>
                            <td>${userWorkDayData.classTypeName}</td>
                            <td>${userWorkDayData.className}</td>
                            <td>${userWorkDayData.userName}</td>
                            <td>
                                <c:if test="${userWorkDayData.morningAttendanceStatus==1}">
                                    正常
                                </c:if>
                                <c:if test="${userWorkDayData.morningAttendanceStatus==2}">
                                    迟到
                                </c:if>
                                <c:if test="${userWorkDayData.morningAttendanceStatus==3}">
                                    早退
                                </c:if>
                                <c:if test="${userWorkDayData.morningAttendanceStatus==4}">
                                    旷工
                                </c:if>
                                <c:if test="${userWorkDayData.morningAttendanceStatus==5}">
                                    加班
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${userWorkDayData.afternoonAttendanceStatus==1}">
                                    正常
                                </c:if>
                                <c:if test="${userWorkDayData.afternoonAttendanceStatus==2}">
                                    迟到
                                </c:if>
                                <c:if test="${userWorkDayData.afternoonAttendanceStatus==3}">
                                    早退
                                </c:if>
                                <c:if test="${userWorkDayData.afternoonAttendanceStatus==4}">
                                    旷工
                                </c:if>
                                <c:if test="${userWorkDayData.afternoonAttendanceStatus==5}">
                                    加班
                                </c:if>
                            </td>
                            <td>${userWorkDayData.workDate}</td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
            <!-- 分页，开始 -->
            <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
            <!-- 分页，结束 -->
        </div>
        <!-- 数据显示列表，结束 -->
    </div>
</body>
</html>