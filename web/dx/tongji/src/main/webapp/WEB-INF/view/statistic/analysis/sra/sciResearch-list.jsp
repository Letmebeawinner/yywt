<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>科研咨政统计</title>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">科研列表</h1>
        <span>
            <span style="color: red;">说明</span><br>
            本页面用来展示科研的统计情况。
        </span>
    </div>

    <div id="contentwrapper" class="contentwrapper">
        <!-- 搜索开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/statistic/analysis/sra/listSciResearch.json">
                    <div class="tableoptions disIb mb10">
                        <span class="vam">科研类型</span>
                        <label class="vam">
                            <select name="type" class="vam" id="type">
                                <option value="0" <c:if test="${type == 0}">selected="selected"</c:if>>全部</option>
                                <option value="1" <c:if test="${type == 1}">selected="selected"</c:if>>科研</option>
                                <option value="2" <c:if test="${type == 2}">selected="selected"</c:if>>咨政</option>
                            </select>
                        </label>
                    </div>
                    <div class="tableoptions disIb mb10">
                        <span class="vam"></span>
                        <label class="vam">
                            <select name="startYear" class="vam" id="startYear">
                                <c:forEach begin="1900" end="${year}" varStatus="index">
                                    <option value="${year - index.index + 1900}">${year - index.index + 1900}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                    <div class="tableoptions disIb mb10">
                        <span class="vam"></span>
                        <label class="vam">
                            <select name="startMonth" class="vam" id="startMonth">
                                <option value="0">月份</option>
                                <c:forEach begin="1" end="12" var="m">
                                    <option value="${m}">${m}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                </div>
            </div>
        </div>
        <!-- 搜索结束 -->

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
                    <col class="con0"/>
                    <col class="con1"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head1 center">科研类型</th>
                    <th class="head0 center">申报数量</th>
                    <th class="head1 center">入库数量</th>
                    <th class="head1 center">未入库数量</th>
                    <th class="head1 center">结项数量</th>
                    <th class="head1 center">未结项数量</th>
                    <th class="head1 center">入库比例(%)</th>
                    <th class="head1 center">结项比例(%)</th>
                    <th class="head1 center">年月</th>
                </tr>
                </thead>
                <tbody id="emailSendRecordData">
                <c:forEach items="${resultStatisticList}" var="result">
                    <tr>
                        <td class="center">
                            <c:choose>
                                <c:when test="${result.resultForm == 1}">论文</c:when>
                                <c:when test="${result.resultForm == 2}">著作</c:when>
                                <c:when test="${result.resultForm == 3}">课题</c:when>
                            </c:choose>
                        </td>
                        <td class="center">${result.declareCount}</td>
                        <td class="center">${result.approvalCount}</td>
                        <td class="center">${result.disApprovalCount}</td>
                        <td class="center">${result.endCount}</td>
                        <td class="center">${result.disEndCount}</td>
                        <td class="center">
                            <c:choose>
                                <c:when test="${result.declareCount > 0}">
                                    <fmt:formatNumber value="${result.approvalCount / result.declareCount * 100}"
                                                      pattern="0.00"/>
                                </c:when>
                                <c:otherwise>0.00</c:otherwise>
                            </c:choose>
                        </td>
                        <td class="center">
                            <c:choose>
                                <c:when test="${result.declareCount > 0}">
                                    <fmt:formatNumber value="${result.endCount / result.declareCount * 100}"
                                                      pattern="0.00"/>
                                </c:when>
                                <c:otherwise>0.00</c:otherwise>
                            </c:choose>
                        </td>
                        <td class="center">${result.date}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <!-- 数据显示列表，结束 -->
    </div>
</div>
<script type="text/javascript" src="${ctx}/static/admin/js/statistic.js"></script>
<script type="text/javascript">
    var startYear = parseInt(${startYear});
    var startMonth = parseInt(${startMonth});
    jQuery('#startYear').val(startYear);
    jQuery('#startMonth').val(startMonth);
</script>
</body>
</html>