<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>上班考勤统计列表</title>
    <script type="text/javascript">
        /**
         * 执行查询
         */
        function searchForm() {
            jQuery("#getWorkStatisticsList").submit();
        }
        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }
    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab" style="margin-left: 10px">
        <h1 class="pagetitle">上班考勤统计列表</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用查看教职工的上班考勤信息<br>
        </span>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="getWorkStatisticsList" action="${ctx}/admin/rs/getWorkStatisticsList.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">年 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" onkeyup='this.value=this.value.replace(/\D/gi,"")' placeholder="输入年份" name="year" value="${year}">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">月 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" onkeyup='this.value=this.value.replace(/\D/gi,"")' placeholder="输入月份" name="month" value="${month}">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">日 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" onkeyup='this.value=this.value.replace(/\D/gi,"")' placeholder="输入日期" name="day" value="${day}">
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
                    <th class="head0 center">日期</th>
                    <th class="head0 center">迟到人数</th>
                    <th class="head0 center">早退人数</th>
                    <th class="head1">旷工人数</th>
                    <th class="head1">请假人数</th>
                    <th class="head1">出勤人数</th>
                    <th class="head1">加班人数</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${workStatisticsList}" var="workStatistics">
                    <tr>
                        <td>${workStatistics.workDate}</td>
                        <td>${workStatistics.lateCount}</td>
                        <td>${workStatistics.earlyCount}</td>
                        <td>${workStatistics.absentCount}</td>
                        <td>${workStatistics.leaveCount}</td>
                        <td>${workStatistics.wkCount}</td>
                        <td>${workStatistics.overCount}</td>
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