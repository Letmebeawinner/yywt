<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>家庭成员列表</title>
    <script type="text/javascript">
        function delFamily(id) {
            if (confirm("删除后将无法恢复，是否继续")) {
                jQuery.ajax({
                    url: "${ctx}/admin/rs/deleteFamily.json",
                    data: {"id":id},
                    type: "post",
                    dataType: "json",
                    async: false,
                    success: function (result) {
                        if (result.code=="0") {
                            window.location.reload();
                        } else {
                            alert(result.message);
                            return;
                        }
                    }
                });
            }
        }
    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab"  style="margin-left: 10px">
        <h1 class="pagetitle">家庭成员列表</h1>
    </div>

    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="getEmployeeList" action="${ctx}/admin/rs/getEmployeeList.json" method="post">
                </form>
                <div class="disIb ml20 mb10">
                    <a href="${ctx}/admin/rs/toAddFamily.json?id=${employeeId}" class="stdbtn ml10 btn_orange">新 建</a>
                    <a href="javascript:void(0)" class="stdbtn ml10" onclick="exportExcel()">导出excel</a>
                </div>
            </div>
        </div>

        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <thead>
                <tr>
                    <th class="head1">序号</th>
                    <th class="head1">称谓</th>
                    <th class="head1">姓名</th>
                    <th class="head1">出生年月</th>
                    <th class="head1">政治面貌</th>
                    <th class="head1">工作单位及职务</th>
                    <td class="head0 center">
                        操作
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${familyList}" var="family" varStatus="index">
                    <tr>
                        <td>${index.count}</td>
                        <td>${family.title}</td>
                        <td>${family.name}</td>
                        <td><fmt:formatDate value="${family.birthday}" pattern="yyyy-MM-dd"/></td>
                        <td>${family.aspect}</td>
                        <td>${family.unit}</td>
                        <td class="center">
                            <a href="${ctx}/admin/rs/toUpdateFamily.json?id=${family.id}" class="stdbtn" title="编辑">编辑</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delFamily(${employee.id})">删除</a>
                        </td>
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