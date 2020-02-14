<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>打卡记录</title>
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
        });
        function searchForm(){
            jQuery("#searchForm").submit();
        }

        function emptyForm(){
            jQuery("input:text").val('');
            jQuery("select").val(0);
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
                        $("#searchForm").prop("action", "${ctx}/admin/jiaowu/attendRecord.json");
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
        <h1 class="pagetitle">打卡记录</h1>
                <span>
                    <span style="color:red">说明</span><br>
					 1.本页面展示打卡记录.<br/>
                     <%--2.没有打卡记录的教师不会被导出.<br/>--%>
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/rs/queryAllTeacherAttData.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">姓名 &nbsp;</span>
                        <label class="vam">
                            <input id="userName" style="width: auto;" name="userCondition.userName" type="text" class="" value="${userCondition.userName}">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">开始日期 &nbsp;</span>
                        <label class="vam">
                            <input id="startTime" type="text" readonly  name="userCondition.startTime" value="<fmt:formatDate value="${userCondition.startTime}" pattern="yyyy-MM-dd"/>"/>
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">截止日期 &nbsp;</span>
                        <label class="vam">
                            <input id="endTime" type="text" readonly  name="userCondition.endTime" value="<fmt:formatDate value="${userCondition.endTime}" pattern="yyyy-MM-dd"/>"/>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                   <%-- <a href="javascript: void(0)" onclick="exportExcel()" class="stdbtn ml10">导 出</a>--%>
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
                    <th class="head0 center">姓名</th>
                    <th class="head0 center">上午考勤状态</th>
                    <th class="head0 center">下午考勤状态</th>
                    <th class="head1 center">打卡日期</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${userWorkDayDataList!=null&&userWorkDayDataList.size()>0 }">
                    <c:forEach items="${userWorkDayDataList}" var="userWorkDayData" varStatus="index">
                        <tr>
                            <td>${index.index+1}</td>
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