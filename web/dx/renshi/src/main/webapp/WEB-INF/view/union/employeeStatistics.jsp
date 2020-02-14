<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>处室人员数据统计</title>
    <script type="text/javascript">
        /**
         * 执行查询
         */
        function searchForm() {
            jQuery("#employeeStatistics").submit();
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
        <h1 class="pagetitle">处室人员数据统计</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来展示各工会处室人员数据信息。
        </span>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">


        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="employeeStatistics" action="${ctx}/admin/rs/employeeStatistics.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">处室名称 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" placeholder="输入处室名称" name="union.name" value="${union.name}">
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
                    <th class="head0 center">处室名称</th>
                    <th class="head1">总人数</th>
                    <th class="head1">男性人数</th>
                    <th class="head1">女性人数</th>
                    <th class="head1">平均年龄</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${employeeStatisticsList}" var="employeeStatistics">
                    <tr>
                        <td>${employeeStatistics.unionName}</td>
                        <td>${employeeStatistics.empNumber}</td>
                        <td>${employeeStatistics.manNumber}</td>
                        <td>${employeeStatistics.womanNumber}</td>
                        <td>${employeeStatistics.ageAvg}</td>
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