<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>会议考勤列表</title>
    <script type="text/javascript">
        /**
         * 执行查询
         */
        function searchForm() {
            jQuery("#getMeetAttendanceList").submit();
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
                elem: '#createTime',
                format:'YYYY-MM-DD'
            });
        });
    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab" style="margin-left: 10px">
        <h1 class="pagetitle">会议考勤列表</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用查看教职工的会议考勤信息<br>
        </span>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="getMeetAttendanceList" action="${ctx}/admin/rs/getMeetAttendanceList.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">ID &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" onkeyup='this.value=this.value.replace(/\D/gi,"")' placeholder="输入ID" name="queryMeetAttendance.id" value="${queryMeetAttendance.id}">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">教职工姓名 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" placeholder="输入教职工姓名" name="queryMeetAttendance.employeeName" value="${queryMeetAttendance.employeeName}">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">考勤时间 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" readonly placeholder="选择考勤时间" name="queryCreateTime" id="createTime" value="<fmt:formatDate value="${queryMeetAttendance.signInTime}" pattern="yyyy-MM-dd"></fmt:formatDate>">
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
                <colgroup>
                    <col class="con0" style="width:10%;"/>
                    <col class="con1"/>
                    <col class="con0" style="width:20%;"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">id</th>
                    <th class="head0 center">教职工id</th>
                    <th class="head0 center">教职工姓名</th>
                    <th class="head0 center">会议名称</th>
                    <th class="head0 center">会议地点</th>
                    <th class="head1">签到时间</th>
                    <th class="head1">签退时间</th>
                    <th class="head1">备注</th>
                    <td class="head0 center">
                        操作
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${meetAttendanceList}" var="meetAttendance">
                    <tr>
                        <td>${meetAttendance.id}</td>
                        <td>${meetAttendance.employeeId}</td>
                        <td>${meetAttendance.employeeName}</td>
                        <td>${meetAttendance.meetName}</td>
                        <td>${meetAttendance.meetPlace}</td>
                        <td><fmt:formatDate value="${meetAttendance.signInTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                        <td><fmt:formatDate value="${meetAttendance.signOutTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                        <td>${meetAttendance.remark}</td>
                        <td class="center">

                        </td>
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