<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/ztree/css/zTreeStyle/zTreeStyle.css">
    <title>角色列表</title>
</head>
<body>
<div class="centercontent tables">
    <!--  class notab -->
    <div class="pageheader" style="padding-left: 30px">
        <h1 class="pagetitle">角色列表</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面包括角色的增加、修改、删除、修改权限等相关的操作；<br>
                2. 增加角色：点击搜索部分最右侧的<span style="color:red">添加</span>按钮添加新的角色；<br>
                3. 更新角色：点击角色列表操作列中的<span style="color:red">编辑</span>按钮编辑角色的信息；<br>
                4. 删除角色：点击角色列表操作列中的<span style="color:red">删除</span>按钮编辑角色的信息；<br>
                5. 禁用/恢复角色：如果某个角色为禁用状态，则角色列表中的状态为<span style="color:red">禁用</span>，且操作列中有<span style="color:green">恢复</span>按钮。
                如果某个角色为正常状态，则角色列表中的状态为<span style="color:green">正常</span>，且操作列中有<span style="color:red">禁用</span>按钮；<br>
                6. 修改角色的权限：点击角色列表操作列中的<span style="color:red">修改权限</span>按钮编辑角色的权限。
                在弹出的窗口中，角色拥有的权限会被勾选。勾选需要的权限和取消勾选不需要的权限，然后点击确定即可。<br>
        </span>
    </div>

    <div id="contentwrapper" class="contentwrapper">
        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <!-- 搜索form表单 -->
                <form class="disIb" id="searchForm" action="${ctx}/admin/base/role/queryAllRoleList.json" method="get">
                    <div class="tableoptions disIb mb10">
                        <span class="vam">角色 &nbsp;</span>
                        <label class="vam" for="roleDesc"></label>
                        <input id="roleDesc" style="width: auto;" type="text" class="hasDatepicker" name="role.roleDesc" value="${role.roleDesc}" placeholder="角色名或角色描述">
                    </div>
                    <div class="tableoptions disIb mb10">
                        <span class="vam">状态 &nbsp;</span>
                        <label class="vam" for="roleStatus"></label>
                        <select name="role.status" id="roleStatus">
                            <option value="1" <c:if test="${role.status == 1}">selected="selected"</c:if>>正常</option>
                            <option value="0" <c:if test="${role.status == 0}">selected="selected"</c:if>>禁用</option>
                        </select>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript:" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript:" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="${ctx}/admin/base/role/getRole.json" class="stdbtn ml10">添加</a>
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
                    <th class="head1 center">角色id</th>
                    <th class="head1 center">角色名</th>
                    <th class="head0 center">角色描述</th>
                    <th class="head1 center">状态</th>
                    <th class="head1 center">创建时间</th>
                    <th class="head0 center">更新时间</th>
                    <th class="head1 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody id="roleData">
                <c:forEach items="${roles}" var="role">
                    <tr id="role_${role.id}">
                        <td class="center">${role.id}</td>
                        <td class="center">${role.roleName}</td>
                        <td style="width: 30%">${role.roleDesc}</td>
                        <td class="center" id="roleStatus_${role.id}">
                            <c:if test="${role.status==1}">正常</c:if>
                            <c:if test="${role.status==0}">禁用</c:if>
                        </td>
                        <td class="center">
                            <fmt:formatDate value="${role.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td class="center">
                            <fmt:formatDate value="${role.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td class="center">
                            <a class="stdbtn" href="javascript:" onclick="showRoleResources('${role.roleName}', '${role.id}')" title="修改权限">修改权限</a>
                            <a class="stdbtn" title="编辑" href="${ctx}/admin/base/role/getRole.json?roleId=${role.id}">编辑</a>
                            <c:if test="${role.status == 0}">
                                <a id="roleStatusOp_${role.id}" href="javascript:" class="stdbtn" onclick="saveRole(2, '${role.id}', this, '${role.roleName}')" title="恢复">恢复</a>
                            </c:if>
                            <c:if test="${role.status == 1}">
                                <a id="roleStatusOp_${role.id}" href="javascript:" class="stdbtn" onclick="saveRole(2, '${role.id}', this, '${role.roleName}')" title="禁用">禁用</a>
                            </c:if>
                            <a href="javascript:" class="stdbtn" onclick="saveRole(3, '${role.id}', this, '${role.roleName}')" title="删除">删除</a>
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
<script charset="utf-8" type="text/javascript" src="${ctx}/static/ztree/jquery.ztree.all-3.5.min.js"></script>
<script charset="utf-8" type="text/javascript" src="${ctx}/static/admin/js/permission/permission.js"></script>
<script charset="utf-8" type="text/javascript" src="${ctx}/static/admin/js/permission/utils.js"></script>
<script charset="utf-8" type="text/javascript" src="${ctx}/static/admin/js/role/role.js"></script>
</body>
</html>