<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>我的考勤</title>
    <%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">
        jQuery(function () {
            laydate.skin('molv');
            laydate({
                elem: '#workDate',
                format: 'YYYY-MM-DD'
            });
            laydate({
                elem: '#classStartTime',
                format: 'YYYY-MM-DD'
            });
            laydate({
                elem: '#classEndTime',
                format: 'YYYY-MM-DD'
            });
            jQuery("#morningAttendanceStatus").val("${userCondition.morningAttendanceStatus}");
            jQuery("#afternoonAttendanceStatus").val("${userCondition.afternoonAttendanceStatus}");

        });

        function searchForm() {
            jQuery("#searchForm").submit();
        }

        function emptyForm() {
            jQuery("#morningAttendanceStatus").val('');
            jQuery("#afternoonAttendanceStatus").val('');
            jQuery("#classStartTime").val('');
            jQuery("#classEndTime").val('');
        }

    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">我的考勤</h1>
        <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示我的考勤.<br/>
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/attendance/studentWorkDayDataList.json" method="get">

                    <div class="disIb ml20 mb10">
                        <span class="vam" >第一时间段 &nbsp;</span>
                        <label class="vam" >
                            <select id="morningAttendanceStatus" class="vam" name="userCondition.morningAttendanceStatus" style="width: 150px">
                                <option value="">--请选择--</option>
                                <option value="1">正常</option>
                                <option value="2">迟到</option>
                                <option value="3">早退</option>
                                <option value="4">旷课</option>
                            </select>
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">第二时间段 &nbsp;</span>
                        <label class="vam">
                            <select id="afternoonAttendanceStatus" name="userCondition.afternoonAttendanceStatus" style="width: 150px">
                                <option value="">--请选择--</option>
                                <option value="1">正常</option>
                                <option value="2">迟到</option>
                                <option value="3">早退</option>
                                <option value="4">旷课</option>
                            </select>
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">日期 &nbsp;</span>
                        <label class="vam">
                            <input id="workDate" type="text" class="hasDatepicker" name="userCondition.workDate" value="${userCondition.workDate}" readonly/>
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">班级开班开始时间 &nbsp;</span>
                        <label class="vam">
                            <input id="classStartTime" type="text" class="hasDatepicker" name="userCondition.classStartTime" value="${userCondition.classStartTime}" readonly/>
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">班级开班结束时间 &nbsp;</span>
                        <label class="vam">
                            <input id="classEndTime" type="text" class="hasDatepicker" name="userCondition.classEndTime" value="${userCondition.classEndTime}" readonly/>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                </div>
            </div>
        </div>
        <!-- 搜索条件，结束 -->

        <!-- 数据显示列表，开始 -->
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
                    <th class="head0 center">序号</th>
                    <th class="head0 center">一卡通编号</th>
                    <th class="head0 center">班型</th>
                    <th class="head0 center">班次</th>
                    <th class="head0 center">姓名</th>
                    <th class="head0 center">班级开班开始时间</th>
                    <th class="head0 center">班级开班结束时间</th>
                    <th class="head0 center">第一时间段考勤状态</th>
                    <th class="head0 center">第二时间段考勤状态</th>
                    <%--<th class="head0 center">流水号</th>--%>
                    <th class="head1 center">日期</th>
                    <%--<th class="head1 center">设备编号</th>--%>
                </tr>
                </thead>
                <tbody>
                <c:if test="${userWorkDayDataList!=null&&userWorkDayDataList.size()>0 }">
                    <c:forEach items="${userWorkDayDataList}" var="userWorkDayData" varStatus="index">
                        <tr>
                            <td>${index.index+1}</td>
                            <td>${userWorkDayData.perId}</td>
                            <td>${userWorkDayData.classTypeName}</td>
                            <td>${userWorkDayData.className}</td>
                            <td>${userWorkDayData.userName}</td>
                            <td><fmt:formatDate value="${userWorkDayData.startTime}" pattern="yyyy-MM-dd"/></td>
                            <td><fmt:formatDate value="${userWorkDayData.endTime}" pattern="yyyy-MM-dd"/></td>
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
                                    旷课
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
                                    旷课
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