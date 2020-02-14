<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>教务统计</title>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">培训期次</h1>
        <span>
            <span style="color: red;">说明</span><br>
            本页面用来展示培训的期次。
        </span>
    </div>

    <div id="contentwrapper" class="contentwrapper">
        <!-- 搜索开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/statistic/analysis/ea/listTrainBatches.json">
                    <div class="tableoptions disIb mb10">
                        <span class="vam">班级类型 &nbsp;</span>
                        <label class="vam">
                            <select name="classType" class="vam" id="classType">
                                <option value="0">请选择</option>
                                <c:forEach items="${classTypeMap}" var="classType">
                                    <option value="${classType.key}">${classType.value}</option>
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
                    <th class="head1 center">班级类型</th>
                    <th class="head1 center">培训期次</th>
                    <th class="head1 center">班次名称</th>
                    <th class="head0 center">教师姓名</th>
                    <th class="head1 center">学生人数</th>
                    <th class="head1 center">开课时间</th>
                    <th class="head1 center">结课时间</th>
                </tr>
                </thead>
                <tbody id="emailSendRecordData">
                <c:forEach items="${classesList}" var="classes">
                    <tr>
                        <td>${classes.classType}</td>
                        <td class="center">第&nbsp;${classes.id}&nbsp;期</td>
                        <td class="center">${classes.name}</td>
                        <td class="center">${classes.teacherName}</td>
                        <td class="center">${classes.studentTotalNum}</td>
                        <td class="center">
                            <fmt:parseDate value="${classes.startTime}" var="startTime"/>
                            <fmt:formatDate value="${startTime}" pattern="yyyy-MM-dd"/>
                        </td>
                        <td class="center">
                            <fmt:parseDate value="${classes.endTime}" var="endTime"/>
                            <fmt:formatDate value="${endTime}" pattern="yyyy-MM-dd"/>
                        </td>
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
</div>
<script type="text/javascript" src="${ctx}/static/admin/js/statistic.js"></script>
<script type="text/javascript">
    var classType = parseInt(${classType});
    $('#classType').val(classType);
</script>
</body>
</html>