<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>教职工考勤表</title>
    <style>
        .laydate_table {
            display: none;
        }

        #laydate_hms {
            display: none !important;
        }
    </style>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle tac">教职工考勤表</h1>
        <div class="work-title">
            <form action="${ctx}/admin/oa/queryAttendanceList.json" method="post" id="attendanceFrom">
                <table width="100%" cellspacing="0" cellpadding="0" border="0">
                   
                    <tr>
                        <c:if test="${checkWorkAttendance.roleId ==46}">
                            <td width="10%">部&nbsp;门:</td>
                            <td width="20%">
                                <div class="tableoptions disIb mb10">
                                    <label class="vam">
                                        <select name="departmentId" class="vam">
                                            <option value="">请选择</option>
                                            <c:forEach items="${departmentList}" var="department">
                                                <option value="${department.id}"
                                                        <c:if test="${checkWorkAttendance.departmentId==department.id}">selected</c:if>>${department.departmentName}</option>
                                            </c:forEach>
                                        </select>
                                    </label>
                                </div>
                            </td>
                        </c:if>
                        <td width="10%">考勤月份:</td>
                        <td width="20%">
                            <input type="text" name="checkWorkAttendance.addTime" value="${checkWorkAttendance.addTime}"
                                   id="addTime" readonly style="text-align: center;">
                        </td>
                        <td width="10%">考勤人(签名):</td>
                        <td width="20%">
                            <input type="text" value="${checkWorkAttendance.attendanceName}"
                                   name="checkWorkAttendance.attendanceName">
                        </td>
                        <td width="10%"><input type="button" href="javascript:void(0)" onclick="attendanceSubmit()"
                                               value="搜索"/></td>

                    </tr>
                </table>
            </form>
        </div>

        <div class="work-title">
            <table width="100%" cellspacing="0" cellpadding="0" border="0">
                <tr>
                    <td width="10%">部&nbsp;门:</td>
                    <td width="23.4%">
                        <c:if test="${checkWorkAttendance.departmentId!=null}">
                            ${checkWorkAttendances[0].departmentName}
                        </c:if>
                    </td>
                    <td width="10%">考勤月份:</td>
                    <td width="23.4%">
                        <c:if test="${checkWorkAttendance.addTime!=null &&''!=checkWorkAttendance.addTime}">
                            <span id="time"></span>
                        </c:if>
                    </td>
                    <td width="10%">考勤人(签名):</td>
                    <td width="23.4%">
                        <c:if test="${checkWorkAttendance.attendanceName!=null && ''!=checkWorkAttendance.attendanceName}">
                            ${checkWorkAttendances[0].attendanceName}
                        </c:if>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent work-attendance">
            <table width="100%" cellspacing="0" cellpadding="0" border="0">
                <tr>
                    <td rowspan="2" align="center" width="6%">姓&nbsp;名:</td>
                    <td rowspan="2" align="center">状态</td>
                    <td rowspan="2" align="center">1</td>
                    <td rowspan="2" align="center">2</td>
                    <td rowspan="2" align="center">3</td>
                    <td rowspan="2" align="center">4</td>
                    <td rowspan="2" align="center">5</td>
                    <td rowspan="2" align="center">6</td>
                    <td rowspan="2" align="center">7</td>
                    <td rowspan="2" align="center">8</td>
                    <td rowspan="2" align="center">9</td>
                    <td rowspan="2" align="center">10</td>
                    <td rowspan="2" align="center">11</td>
                    <td rowspan="2" align="center">12</td>
                    <td rowspan="2" align="center">13</td>
                    <td rowspan="2" align="center">14</td>
                    <td rowspan="2" align="center">15</td>
                    <td rowspan="2" align="center">16</td>
                    <td rowspan="2" align="center">17</td>
                    <td rowspan="2" align="center">18</td>
                    <td rowspan="2" align="center">19</td>
                    <td rowspan="2" align="center">20</td>
                    <td rowspan="2" align="center">21</td>
                    <td rowspan="2" align="center">22</td>
                    <td rowspan="2" align="center">23</td>
                    <td rowspan="2" align="center">24</td>
                    <td rowspan="2" align="center">25</td>
                    <td rowspan="2" align="center">26</td>
                    <td rowspan="2" align="center">27</td>
                    <td rowspan="2" align="center">28</td>
                    <td rowspan="2" align="center">29</td>
                    <td rowspan="2" align="center">30</td>
                    <td rowspan="2" align="center">31</td>
                    <td colspan="4" align="center">月出勤情况统计</td>
                </tr>
                <tr>
                    <td align="center">全勤(天)</td>
                    <td align="center">缺勤(天)</td>
                    <td align="center">迟到早退(次)</td>
                    <td align="center">加班（天）</td>
                </tr>
                <c:forEach items="${checkWorkAttendances}" var="checkWorkAttendance">
                    <tr>
                        <td rowspan="2" align="center">${checkWorkAttendance.userName}</td>
                        <c:set value="${checkWorkAttendance.userName}am" var="am"></c:set>
                        <td align="center">上午</td>
                        <c:forEach items="${dataMap.get(am)}" var="amCkad">
                            <c:set value="${amCkad.amOutTime}" var="amOutTime"></c:set>
                            <c:set value="${amCkad.amRetreat}" var="amRetreat"></c:set>
                            <c:set value="${amCkad.amLeave}" var="amLeave"></c:set>
                            <c:set value="${amCkad.amAbsenteeism}" var="amAbsenteeism"></c:set>
                            <td align="center">
                                <c:choose>
                                    <c:when test="${amCkad.amAttendanceStatus==1}">
                                        √
                                    </c:when>
                                    <c:when test="${amCkad.amAttendanceStatus==2}">
                                        ×
                                    </c:when>
                                    <c:when test="${amCkad.amAttendanceStatus==3}">
                                        ▲
                                    </c:when>
                                    <c:when test="${amCkad.amAttendanceStatus==4}">
                                        ○
                                    </c:when>
                                    <c:when test="${amCkad.amAttendanceStatus==5}">
                                        ☆
                                    </c:when>
                                    <c:when test="${amCkad.amAttendanceStatus==6}">
                                        ◇
                                    </c:when>
                                    <c:when test="${amCkad.amAttendanceStatus==7}">
                                        ∣
                                    </c:when>
                                    <c:when test="${amCkad.amAttendanceStatus==8}">
                                        ☺
                                    </c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </td>
                        </c:forEach>
                        <td align="center">${amOutTime}</td>
                        <td align="center">${amLeave}</td>
                        <td align="center">${amRetreat}</td>
                        <td align="center">${amAbsenteeism}</td>
                    </tr>
                    <tr>
                        <c:set value="${checkWorkAttendance.userName}pm" var="pm"></c:set>
                        <td align="center">下午</td>
                        <c:forEach items="${dataMap.get(pm)}" var="pmCkad">
                            <td align="center">
                                <c:choose>
                                    <c:when test="${pmCkad.pmAttendanceStatus==1}">
                                        √
                                    </c:when>
                                    <c:when test="${pmCkad.pmAttendanceStatus==2}">
                                        ×
                                    </c:when>
                                    <c:when test="${pmCkad.pmAttendanceStatus==3}">
                                        ▲
                                    </c:when>
                                    <c:when test="${pmCkad.pmAttendanceStatus==4}">
                                        ○
                                    </c:when>
                                    <c:when test="${pmCkad.pmAttendanceStatus==5}">
                                        ☆
                                    </c:when>
                                    <c:when test="${pmCkad.pmAttendanceStatus==6}">
                                        ◇
                                    </c:when>
                                    <c:when test="${pmCkad.pmAttendanceStatus==7}">
                                        ∣
                                    </c:when>
                                    <c:when test="${pmCkad.pmAttendanceStatus==8}">
                                        ☺
                                    </c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </td>
                            <c:set value="${pmCkad.pmOutTime}" var="pmOutTime"></c:set>
                            <c:set value="${pmCkad.pmRetreat}" var="pmRetreat"></c:set>
                            <c:set value="${pmCkad.pmLeave}" var="pmLeave"></c:set>
                            <c:set value="${pmCkad.pmAbsenteeism}" var="pmAbsenteeism"></c:set>
                        </c:forEach>
                        <td align="center">${pmOutTime}</td>
                        <td align="center">${pmLeave}</td>
                        <td align="center">${pmRetreat}</td>
                        <td align="center">${pmAbsenteeism}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctx}/jquery-1.8.3.min.js"></script>
<script type="application/javascript">
    $(function () {
        laydate.skin('molv');
        laydate({
            elem: '#addTime',
            istoday: false,
            format: 'YYYY年MM月'
        });
        var name = $("#addTime").val();
        $("#time").html(name)
    })
    function attendanceSubmit() {
        $('#attendanceFrom').submit()
    }
</script>
</body>
</html>
 