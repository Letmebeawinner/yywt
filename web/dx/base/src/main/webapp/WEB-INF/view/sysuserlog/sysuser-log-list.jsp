<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>管理员用户列表</title>
    <script type="text/javascript">
        function deleteLog(id) {
            if (confirm("确认删除吗？")) {
                jQuery.ajax({
                    url: "/admin/base/deleteLogById.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code == 0) {
                            alert(result.message);
                            window.location.href = "/admin/base/querySysUserlogList.json";
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
    <div class="pageheader" style="margin-left: 30px">
        <h1 class="pagetitle">登录日志列表</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

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
                    <th class="head1 center">序号</th>
                    <th class="head1 center">用户名</th>
                    <th class="head0 center">登录ip</th>
                    <th class="head1 center">登录日期</th>
                    <th class="head1 center">登录系统</th>
                    <th class="head0 center">浏览器</th>
                    <th class="head1 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${sysUserLogList}" var="sysuser" varStatus="index">
                    <tr>
                        <td>${index.count}</td>
                        <td>${sysuser.userName}</td>
                        <td>${sysuser.loginIp}</td>
                        <td>
                            <fmt:formatDate value="${sysuser.loginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td>${sysuser.osName}</td>
                        <td>${sysuser.userAgent}</td>
                        <td class="center">
                            <a href="javascript:void(0)" class="stdbtn" title="编辑"
                               onclick="deleteLog(${sysuser.id})">删除</a>
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