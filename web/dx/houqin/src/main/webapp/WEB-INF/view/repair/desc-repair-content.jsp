<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>报修详细统计</title>
    <script type="text/javascript">
        function searchForm() {
            jQuery("#searchForm").submit();
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">报修详细统计</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/repairStatisticInfo.json" method="post">

                    <div class="disIb ml20 mb10">
                        <span class="vam">年份 &nbsp;</span>
                        <label class="vam">
                            <select name="year">
                                <c:forEach begin="${beginYear}" end="${endYear}" var="years">
                                    <option value="${years}"
                                            <c:if test="${years==year}">selected="selected"</c:if>
                                    >${years}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">月份 &nbsp;</span>
                        <label class="vam">
                            <select name="month">
                                <c:forEach begin="1" end="12" var="months">
                                    <option value="${months}" <c:if test="${month==months}">selected</c:if>>${months}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
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
                    <th class="head0 center">类型名称</th>
                    <th class="head0 center">维修总数</th>
                    <th class="head0 center">未维修数</th>
                    <th class="head0 center">已维修数</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${repairStatisticsList}" var="statis" varStatus="index">
                    <tr>
                        <td>${index.count}</td>
                        <td>${statis.name}</td>
                        <td>${statis.count}</td>
                        <td>${statis.count}</td>
                        <td>${statis.count}</td>
                    </tr>
                </c:forEach>
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