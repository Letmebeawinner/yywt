<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>
        <c:if test="${role.id > 0}">
            修改角色信息
        </c:if>
        <c:if test="${role.id == null || role.id == 0}">
            添加角色信息
        </c:if>
    </title>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <c:if test="${role.id > 0}">
            <h1 class="pagetitle">修改角色信息</h1>
        </c:if>
        <c:if test="${role.id == null || role.id == 0}">
            <h1 class="pagetitle">添加角色信息</h1>
        </c:if>
        <span>
                <span style="color:red">说明</span><br>
            <c:if test="${role.id > 0}">
                1. 本页面用来修改角色信息；<br>
            </c:if>
            <c:if test="${role.id == null || role.id == 0}">
                1. 本页面用来添加新的角色信息；<br>
            </c:if>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" action="#" id="form">
                <p>
                    <label for="roleName"><em style="color: red;">*</em>角色名</label>
                    <span class="field">
                        <input type="text" name="role.roleName" id="roleName" class="longinput" value="${role.roleName}" />
                    </span>
                </p>
                <p>
                    <label for="roleStatus"><em style="color: red;">*</em>状态</label>
                    <span class="field">
                        <select id="roleStatus" name="role.status">
                            <option value="1" <c:if test="${role.status == 1}">selected="selected"</c:if>>正常</option>
                            <option value="0" <c:if test="${role.status == 0}">selected="selected"</c:if>>禁用</option>
                        </select>
                    </span>
                </p>
                <p>
                    <label for="roleDesc"><em style="color: red;">*</em>角色描述</label>
                    <span class="field">
                        <textarea style="resize: none;" cols="80" rows="5" name="role.roleDesc" id="roleDesc" class="longinput">${role.roleDesc}</textarea>
                    </span>
                </p>
                <p hidden="hidden">
                    <label for="roleId"><em style="color: red;">*</em>角色ID</label>
                    <span class="field">
                        <input type="text" name="role.id" id="roleId" class="longinput" value="${role.id}"/>
                    </span>
                </p>
                <p>
                    <label for="roleId">排序值</label>
                    <span class="field">
                        <input type="text" name="role.sort" id="sort" class="longinput" value="${role.sort}"/>
                    </span>
                </p>
                <p class="stdformbutton">
                    <button type="button" class="submit radius2" onclick="saveRole(1, 0)">提交保存</button>
                    <button class="stdbtn" type="button" onclick="comeback()">返回</button>
                </p>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctx}/static/admin/js/permission/utils.js"></script>
<script type="text/javascript" src="${ctx}/static/admin/js/role/role.js"></script>
</body>
</html>