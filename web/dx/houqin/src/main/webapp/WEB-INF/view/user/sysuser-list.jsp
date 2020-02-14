<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>物业人员列表</title>
    <script type="text/javascript">
        /**
         * 执行查询
         */
        function searchForm() {
            jQuery("#searchForm").submit();
        }

        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        function deleteSysUserById(id) {
            if (window.confirm("确认要删除该用户吗？")) {
                jQuery.ajax({
                    url: "/admin/houqin/delSysUserById.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code == 0) {
                            alert(result.message);
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
    <div class="pageheader" style="margin-left: 30px">
        <h1 class="pagetitle">物业人员列表</h1>
        <span>
                    <span style="color:red">说明</span><br>
                    1. 本页面用于物业人员的管理；<br>
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/queryUserList.json" method="get">
                    <div class="disIb ml20 mb10">
                        <span class="vam">查询条件 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" type="text" class="hasDatepicker" name="check"
                                   placeholder="用户名称/邮箱/手机号" value="${check}">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="${ctx}/admin/houqin/initAddUser.json" class="stdbtn ml10">添加</a>
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
                    <col class="con0"/>
                    <col class="con1"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">用户编号</th>
                    <th class="head1 center">用户名</th>
                    <th class="head0 center">用户类型</th>
                    <th class="head1 center">手机</th>
                    <th class="head1 center">邮箱</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${userList}" var="sysuser">
                    <tr>
                        <td>${sysuser.userNo}</td>
                        <td>${sysuser.userName}</td>
                        <td>
                            <c:if test="${sysuser.userType==1}">管理员</c:if>
                            <c:if test="${sysuser.userType==2}">教职工</c:if>
                            <c:if test="${sysuser.userType==3}">学员</c:if>
                            <c:if test="${sysuser.userType==4}">驾驶员</c:if>
                            <c:if test="${sysuser.userType==5}">单位报名</c:if>
                            <c:if test="${sysuser.userType==6}">物业公司</c:if>
                        </td>
                        <td>${sysuser.mobile}</td>
                        <td>${sysuser.email}</td>
                        <td class="center">
                                <%--<a class="stdbtn" title="编辑" href="/admin/houqin/toUpdateSysUser.json?id=${sysuser.id}"><span>编辑</span></a>--%>
                            <a class="stdbtn" title="删除" href="javascript:void(0);"
                               onclick="deleteSysUserById('${sysuser.id}')"><span>删除</span></a>
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