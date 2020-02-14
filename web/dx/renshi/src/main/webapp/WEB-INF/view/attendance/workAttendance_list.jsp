<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>上班考勤列表</title>
    <script type="text/javascript">
        /**
         * 执行查询
         */
        function searchForm() {
            jQuery("#getWorkAttendanceList").submit();
        }
        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }
        jQuery(function(){
            laydate.skin('molv');
            laydate({
                elem: '#signInTime',
                format:'YYYY-MM-DD'
            });
        });
    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab" style="margin-left: 10px">
        <h1 class="pagetitle">上班考勤列表</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用查看教职工的上班考勤信息<br>
        </span>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="getWorkAttendanceList" action="${ctx}/admin/rs/getWorkAttendanceList.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">ID &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" onkeyup='this.value=this.value.replace(/\D/gi,"")' placeholder="输入ID" name="queryWorkAttendance.id" value="${queryWorkAttendance.id}">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">教职工姓名 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" placeholder="输入教职工姓名" name="queryWorkAttendance.employeeName" value="${queryWorkAttendance.employeeName}">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">考勤时间 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" readonly placeholder="选择考勤时间" name="queryCreateTime" id="signInTime" value="${queryWorkAttendance.signInTime}">
                        </label>

                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchForm()">搜 索</a>
                    <a href="javascript: void(0)" class="stdbtn ml10" onclick="emptyForm()">重 置</a>
                </div>
            </div>
        </div>

        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <thead>
                <tr>
                    <th class="head0 center">id</th>
                    <th class="head0 center">考勤时间</th>
                    <th class="head0 center">教职工编号</th>
                    <th class="head0 center">教职工姓名</th>
                    <th class="head1">签到时间</th>
                    <th class="head1">签退时间</th>
                    <th class="head1">上班状态</th>
                    <th class="head1">类型</th>
                    <th class="head1">天数</th>
                    <th class="head1">小时数</th>
                    <th class="head1">分钟数</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${workAttendanceList}" var="workAttendance">
                    <tr>
                        <td>${workAttendance.id}</td>
                        <td>${workAttendance.workDate}</td>
                        <td>${workAttendance.employeeId}</td>
                        <td>${workAttendance.employeeName}</td>
                        <td>${workAttendance.signInTime}</td>
                        <td>${workAttendance.signOutTime}</td>
                            <c:if test="${workAttendance.workStatus==0}">
                                <td>正常</td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </c:if>
                            <c:if test="${workAttendance.workStatus==1}">
                                <td>缺勤</td>
                                <c:if test="${workAttendance.workStatusType==1}"><td>迟到</td></c:if>
                                <c:if test="${workAttendance.workStatusType==2}"><td>早退</td></c:if>
                                <c:if test="${workAttendance.workStatusType==3}"><td>旷工</td></c:if>
                                <c:if test="${workAttendance.workStatusType==4}"><td>${workAttendance.min}</td></c:if>
                                <c:if test="${workAttendance.workStatusType==5}"><td>出勤</td></c:if>
                                <c:if test="${workAttendance.workStatusType==6}"><td>${workAttendance.min}</td></c:if>
                                <td>${workAttendance.day}</td>
                                <td>${workAttendance.hours}</td>
                                <c:if test="${workAttendance.workStatusType==6||workAttendance.workStatusType==4}"><td></td></c:if>
                                <c:if test="${workAttendance.workStatusType!=6 && workAttendance.workStatusType!=4}"><td>${workAttendance.min}</td></c:if>
                            </c:if>
                            <c:if test="${workAttendance.workStatus==2}">
                                <td>加班</td>
                                <c:if test="${workAttendance.workStatusType==1}"><td>正常加班</td></c:if>
                                <c:if test="${workAttendance.workStatusType==2}"><td>周末加班</td></c:if>
                                <c:if test="${workAttendance.workStatusType==3}"><td>节假日加班</td></c:if>
                                <td>${workAttendance.day}</td>
                                <td>${workAttendance.hours}</td>
                                <td>${workAttendance.min}</td>
                            </c:if>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- 分页，开始 -->
            <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
            <!-- 分页，结束 -->
        </div>
    </div>
</div>
</body>
</html>