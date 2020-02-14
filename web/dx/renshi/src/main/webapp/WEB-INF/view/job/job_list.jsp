<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>岗位</title>
    <script type="text/javascript">
        function clearVal() {
            jQuery(".selectTab").val("");
        }

        function submit() {
            jQuery("#from").submit();
        }

        function delJob(id) {
            if (confirm("删除后将无法恢复，是否继续")) {
                jQuery.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "/admin/ganbu/job/delJob.json",
                    data: {"id": id},
                    cache: false,
                    async: false,
                    error: function (request) {
                        alert("网络异常，请稍后再试");
                    },
                    success: function (result) {
                        if (result.code == 0) {
                            window.location.reload();
                        } else {
                            alert("网络异常，请稍后再试");
                        }
                    }
                });
            }
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">岗位列表</h1>
        <span>
                    <span style="color:red">说明</span><br>
                    1. 本页面包括岗位的增加、修改、删除等相关的操作；<br>
                    2. 增加岗位：点击搜索部分最右侧的<span style="color:red">添加</span>按钮添加新的岗位；<br>
                    3. 更新岗位：点击岗位列表操作列中的<span style="color:red">编辑</span>按钮编辑岗位的信息；<br>
                    4. 删除岗位：点击岗位列表操作列中的<span style="color:red">删除</span>按钮删除岗位的信息；<br>
            </span>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <%--<form class="disIb" id="from" method="post" action="${ctx}/admin/ganbu/job/queryJob.json">--%>
                    <%--<div class="tableoptions disIb mb10">--%>
                        <%--<span class="vam">岗位系列： &nbsp;</span>--%>
                        <%--<label class="vam" for="jobOrderId"></label>--%>
                        <%--<select name="jobOrderId" id="jobOrderId" class="selectTab">--%>
                            <%--<option value="">-请选择-</option>--%>
                            <%--<c:forEach items="${jobOrderList}" var="jobOrder">--%>
                                <%--<option <c:if test="${jobOrder.id==jobOrderId}"> selected =selected</c:if>--%>
                                        <%--value="${jobOrder.id}">${jobOrder.name}</option>--%>
                            <%--</c:forEach>--%>
                        <%--</select>--%>
                    <%--</div>--%>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: submit()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: clearVal()" class="stdbtn ml10">重 置</a>
                    &nbsp;&nbsp;
                    <a href="${ctx}/admin/ganbu/job/toAddJob.json" class="stdbtn btn_orange" title="添加">添加</a>
                </div>
            </div>
        </div>
        <!-- 搜索条件，结束 -->
        <!-- 数据显示列表，开始 -->
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <thead>
                <tr>
                    <th class="head0 center">序号</th>
                    <th class="head0 center">岗位名称</th>
                    <th class="head1 center">岗位编号</th>
                    <th class="head1 center">所属部门</th>
                    <th class="head1 center">所辖人数</th>
                    <th class="head1 center">直接上级</th>
                    <th class="head1 center">本岗位人数</th>
                    <th class="head1 center">直接下级</th>
                    <th class="head1 center">岗位分析时间</th>
                    <th class="head1 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${jobList}" var="obj">
                    <tr>
                        <td>${obj.id}</td>
                        <td>${obj.name}</td>
                        <td>${obj.number}</td>
                        <td>${obj.departmentName}</td>
                        <td>${obj.manageNumber}</td>
                        <td>${obj.leader}</td>
                        <td>${obj.thisJobNumber}</td>
                        <td>${obj.junior}</td>
                        <td><fmt:formatDate value="${obj.analyzeTime}" pattern="yyyy-MM"/> </td>
                        <td class="center">
                            <a class="stdbtn" title="编辑" href="${ctx}/admin/ganbu/job/toUpdateJob.json?id=${obj.id}"><span>编辑</span></a>
                            <a href="javascript:delJob('${obj.id}')" class="stdbtn" title="删除">删除</a>
                            <a class="stdbtn" title="查看详情" href="${ctx}/admin/ganbu/job/toDetailJob.json?id=${obj.id}"><span>查看详情</span></a>
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
</body>
</html>