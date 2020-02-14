<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>维修记录统计</title>

    <script type="text/javascript">
        function searchForm() {
            jQuery("#searchForm").submit();
        }

    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">维修详细记录统计</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <!-- 数据显示列表，开始 -->
        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/maintenanceStatistics.json"
                      method="post">

                    <div class="disIb ml20 mb10">
                        <span class="vam">年份 &nbsp;</span>
                        <label class="vam">
                            <select name="year" id="courseYear">
                                <c:forEach begin="${beginYear}" end="${endYear}" var="years">
                                    <option value="${years}"
                                            <c:if test="${years==year}">selected="selected"</c:if>
                                    >${years}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="exportExcel()" class="stdbtn btn_orange">导出EXCEL</a>
                </div>
                <div class="disIb ml20 mb10">
                    总数：${sum}条维修记录
                </div>
            </div>
        </div>
        <!-- 搜索条件，结束 -->
        <div class="pr">
            <table id="datatable" cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <thead>
                <tr>
                    <th class="center">月份</th>
                    <th>已维修</th>
                    <th>维修总数</th>
                    <th>未维修</th>
                    <th>详情</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${result.size()==0}">
                    <c:forEach begin="1" end="12" var="result">
                        <tr>
                            <th>${result}</th>
                            <td>${0}</td>
                            <td>${0}</td>
                            <td>${0}</td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:forEach items="${result}" var="result">
                    <tr>
                        <th style="font-weight: 400;border: 1px solid #eee;color: #666">${result.month}月</th>
                        <td>${result.yiMaintenance}</td>
                        <td>${result.count}</td>
                        <td>${result.weiMaintenance}</td>
                        <td class="center">
                            <a href="${ctx}/admin/houqin/repairStatisticInfo.json?month=${result.month}" class="stdbtn"
                               title="详情">详情</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <!-- 数据显示列表，结束 -->
    </div>
</div>
</body>
</html>