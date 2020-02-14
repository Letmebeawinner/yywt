<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>资产统计</title>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">年度收支统计</h1>
        <span>
            <span style="color: red;">说明</span><br>
            本页面用来展示部门的年度收支统计。
        </span>
    </div>

    <div id="contentwrapper" class="contentwrapper">
        <!-- 数据显示列表，开始 -->
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <thead>
                <tr>
                    <th class="head0 center" rowspan="3">年份</th>
                    <th class="head0 center" rowspan="3">总收入</th>
                    <th class="head0 center" rowspan="3">总支出</th>
                    <th class="head0 center" rowspan="3">结余</th>
                    <th class="head0 center" rowspan="3">重审计结余</th>
                    <th class="head0 center" colspan="3">详情</th>
                    <th class="head0 center" rowspan="3">操作</th>
                </tr>
                <tr>
                    <th class="head0 center" colspan="1">收入</th>
                    <th class="head0 center" colspan="2">支出</th>
                </tr>
                <tr>
                    <th class="head0 center">宿舍</th>
                    <th class="head0 center">能源</th>
                    <th class="head0 center">薪资</th>
                </tr>
                </thead>
                <tbody id="emailSendRecordData">
                <c:forEach items="${budgetList}" var="budget">
                    <tr>
                        <td class="center">${budget.year}</td>
                        <td class="center">${budget.earning}</td>
                        <td class="center">${budget.expense}</td>
                        <td class="center">
                            <c:if test="${budget.balanceType == 1}">${budget.balance}</c:if>
                            <c:if test="${budget.balanceType == 2}">-${budget.balance}</c:if>
                        </td>
                        <td class="center" id="redoneBalance">
                            <c:if test="${budget.redoneBalanceType == 1}">${budget.redoneBalance}</c:if>
                            <c:if test="${budget.redoneBalanceType == 2}">-${budget.redoneBalance}</c:if>
                        </td>
                        <td class="center">${budget.dorm}</td>
                        <td class="center">${budget.energy}</td>
                        <td class="center">${budget.salary}</td>
                        <td class="center">
                            <a class="stdbtn" href="javascript:" onclick="redoneBalance(${budget.year})" title="重新审计">重新审计</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <!-- 数据显示列表，结束 -->
    </div>
</div>
<script type="text/javascript">
    var ctx = '${ctx}';
    function redoneBalance(year) {
        jQuery.ajax({
            url: ctx + '/admin/statistic/analysis/asset/redoneBalance.json',
            data: {'year': year},
            dataType: 'json',
            success: function (result) {
                if (result.code == '0')
                    window.location.reload(true);
            }
        })
    }

</script>
</body>
</html>