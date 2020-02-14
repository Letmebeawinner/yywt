<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>消费列表</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <%--<style>--%>
    <%--.laydate_table {--%>
    <%--display: none;--%>
    <%--}--%>

    <%--#laydate_hms {--%>
    <%--display: none !important;--%>
    <%--}--%>
    <%--</style>--%>
    <script type="text/javascript">
        // jQuery(function () {
        //     laydate.skin('molv');
        //     laydate({
        //         elem: '#searchTime',
        //         format:'YYYY-MM'
        //     });
        // });

        function searchForm() {
            jQuery("#searchForm").submit();
        }

        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        // function selectYear() {
        //     for () {
        //
        //     }
        // }

    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">消费列表</h1>
        <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示消费列表.<br/>
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/consumption/list.json" method="post">

                    <div class="disIb ml20 mb10">
                        <span class="vam">员工编号 &nbsp;</span>
                        <label class="vam">
                            <input id="perId" style="width: auto;" name="searchOpt" type="text" class="" value="${searchOpt}">
                        </label>
                    </div>
                    <%--<div class="disIb ml20 mb10">--%>
                        <%--<span class="vam">&nbsp;年份 &nbsp;</span>--%>
                        <%--<label class="vam">--%>
                            <%--<select name="yearTime" id="yearTime" class="longinput" style="width: 150px;" onclick="selectYear()">--%>
                                <%--<option value="">请选择年份</option>--%>
                            <%--</select>--%>
                        <%--</label>--%>
                    <%--</div>--%>
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
                    <th class="head1 center">人员编号</th>
                    <th class="head1 center">人员姓名</th>
                    <th class="head1 center">卡号</th>
                    <th class="head0 center">日期</th>
                    <th class="head0 center">消费记录数</th>
                    <th class="head0 center">消费累计额</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${resultList!=null&&resultList.size()>0 }">
                    <c:forEach items="${resultList}" var="rl" varStatus="status">
                        <tr>
                            <td>${status.count}</td>
                            <td>${rl.Base_PerID}</td>
                            <td>${rl.Base_PerName}</td>
                            <td>${rl.Base_CardNo}</td>
                            <td>${rl.Cost_Date}</td>
                            <td>${rl.Cost_Count}</td>
                            <td>${rl.Cost_SumMoney}</td>
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
</div>
</body>
</html>