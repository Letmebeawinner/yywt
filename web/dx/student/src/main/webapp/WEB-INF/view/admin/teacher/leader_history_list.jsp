<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>历任班主任列表</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">
        function searchForm() {
            jQuery("#searchForm").submit();
        }

        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }
        <%--function delTeacher(id) {--%>
            <%--if (confirm("您确定要删除吗?")) {--%>
                <%--jQuery.ajax({--%>
                    <%--url: '${ctx}/admin/jiaowu/teacher/delTeacher.json?id=' + id,--%>
                    <%--type: 'post',--%>
                    <%--dataType: 'json',--%>
                    <%--success: function (result) {--%>
                        <%--if (result.code == "0") {--%>
                            <%--window.location.reload();--%>
                        <%--} else {--%>
                            <%--jAlert(result.message, '提示', function () {--%>
                            <%--});--%>
                        <%--}--%>
                    <%--},--%>
                    <%--error: function (e) {--%>
                        <%--jAlert('删除失败', '提示', function () {--%>
                        <%--});--%>
                    <%--}--%>
                <%--});--%>
            <%--}--%>
        <%--}--%>
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">历任班主任列表</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/teacher/leaderHistoryList.json" method="get">

                    <div class="disIb ml20 mb10">
                        <span class="vam">班主任姓名 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="classesTeacherRecord.teacherName" type="text" class="hasDatepicker"
                                   value="${classesTeacherRecord.teacherName}" placeholder="请输入班主任姓名">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">班次名称 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="classesTeacherRecord.classesName" type="text" class="hasDatepicker"
                                   value="${classesTeacherRecord.classesName}" placeholder="请输入班次名称">
                        </label>
                    </div>
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
                    <th class="head0 center">姓名</th>
                    <th class="head0 center">班次</th>
                    <th class="head0 center">类别</th>
                    <th class="head0 center">开班时间</th>
                    <th class="head0 center">结束时间</th>
                    <%--<th class="head1">出生日期</th>--%>
                    <%--<th class="head1">电话</th>--%>
                    <%--<th class="head1">邮箱</th>--%>
                    <%--<th class="head1">年龄</th>--%>
                    <%--<th class="head1">性别</th>--%>
                    <%--<th class="head1">民族</th>--%>
                    <%--<th class="head1">学历</th>--%>
                    <%--<th class="head1">专业</th>--%>
                    <%--<th class="head1">职务</th>--%>
                    <%--<td class="head0 center">操作</td>--%>
                </tr>
                </thead>
                <tbody>
                <c:if test="${classesTeacherRecordList!=null&&classesTeacherRecordList.size()>0 }">
                    <c:forEach items="${classesTeacherRecordList}" var="teacher" varStatus="index">
                        <tr>
                            <td>${index.index+1}</td>
                            <td>${teacher.teacherName}</td>
                            <td>${teacher.classesName}</td>
                            <td>
                                <c:if test="${teacher.type==1}">班主任</c:if>
                                <c:if test="${teacher.type==2}">副班主任</c:if>
                            </td>
                           
                            <td> <fmt:formatDate value="${teacher.startTime}" pattern="yyyy-MM-dd"/></td>
                            <td> <fmt:formatDate value="${teacher.endTime}" pattern="yyyy-MM-dd"/></td>
                            <%--<td>${fn:substring(teacher.birthDay, 0, 10)}</td>--%>
                            <%--<td>${teacher.mobile}</td>--%>
                            <%--<td>${teacher.email}</td>--%>
                            <%--<td>${teacher.age}</td>--%>
                            <%--<td>--%>
                                <%--<c:if test="${teacher.sex==0}">男</c:if>--%>
                                <%--<c:if test="${teacher.sex==1}">女</c:if>--%>
                            <%--</td>--%>
                            <%--<td>${teacher.nationality}</td>--%>
                            <%--<td>${teacher.education}</td>--%>
                            <%--<td>${teacher.profession}</td>--%>
                            <%--<td>${teacher.position}</td>--%>
                            <%--<td class="center">--%>
                                    <%--<a href="javascript:cancelClassLeader(${teacher.id})" class="stdbtn">取消班主任</a>--%>
                            <%--</td>--%>
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