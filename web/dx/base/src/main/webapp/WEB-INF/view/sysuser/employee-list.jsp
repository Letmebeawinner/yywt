<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/ztree/css/zTreeStyle/zTreeStyle.css">
    <title>教职工列表</title>
    <script type="text/javascript" src="${ctx}/static/ztree/jquery.ztree.all-3.5.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/admin/js/department/department-list.js"></script>
    <script type="text/javascript" src="${ctx}/static/admin/js/permission/utils.js"></script>
    <script type="text/javascript" src="${ctx}/static/admin/js/sysuser/sysuser.js"></script>
    <script type="text/javascript" src="${ctx}/static/admin/js/role/sysuserrole.js"></script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader" style="margin-left: 30px">
        <h1 class="pagetitle">教职工列表</h1>
        <span>
                    <span style="color:red">说明</span><br>
                    1. 本页面用于管理员用户的管理；<br>
                    2. 添加用户：点击<span style="color:red">添加</span>按钮，跳转添加用户页面，添加相关信息；<br>
                    3. 更新用户：点击<span style="color:red">编辑</span>按钮，跳转编辑用户页面，修改相关信息；<br>
                    4. 锁定、恢复：点击<span style="color:red">锁定</span>按钮，锁定用户,锁定的用户将不能进行登录等操作，点击<span
                style="color:red">恢复</span>恢复用户， 用户恢复正常<br>
                    5. 设置部门：点击<span style="color:red">设置部门</span>在弹窗中选中部门，点击确定，给用户添加部门信息；<br>
                    6.查询：输入查询条件，点击<span style="color:red">搜索</span>
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/base/sysuser/queryEmployeeList.json"
                      method="get">
                    <div class="tableoptions disIb mb10">
                        <span class="vam">状态 &nbsp;</span>
                        <label class="vam">
                            <select name="status" class="vam" id="status">
                                <option value="0" <c:if test="${status==0}">selected="selected"</c:if>>正常</option>
                                <option value="1" <c:if test="${status==1}">selected="selected"</c:if>>锁定</option>
                            </select>
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">查询条件 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" type="text" class="hasDatepicker" name="check"
                                   placeholder="用户名称/邮箱/手机号" value="${check}">
                        </label>
                    </div>
                    <div class="tableoptions disIb mb10">
                        <span class="vam">部门 &nbsp;</span>
                        <label class="vam">
                            <select name="department" class="vam">
                                <option value="">请选择</option>
                                <c:forEach items="${departmentList}" var="department">
                                    <option value="${department.id}" <c:if test="${departmentId==department.id}">selected</c:if>>${department.departmentName}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="${ctx}/admin/base/sysuser/initAddUser.json" class="stdbtn ml10">添加</a>
                    <a href="${ctx}/admin/base/sysuser/toAddBatchSysUser.json" class="stdbtn ml10">批量导入</a>
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
                    <th class="head1 center">所属部门</th>
                    <th class="head0 center">状态</th>
                    <th class="head1 center">创建时间</th>
                    <th class="head1 center">
                        操作
                    </th>
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
                        <td>${sysuser.departmentName}</td>
                        <td id="status_${sysuser.id}">
                            <c:if test="${sysuser.status==0}">正常</c:if>
                            <c:if test="${sysuser.status==1}">锁定</c:if>
                        </td>
                        <td>
                            <fmt:formatDate value="${sysuser.createTime}" pattern="yyyy-MM-dd"/>
                        </td>

                        <td class="center">
                            <a class="stdbtn" title="编辑"
                               href="/admin/base/sysuser/toUpdateSysUser.json?id=${sysuser.id}"><span>编辑</span></a>
                            <c:if test="${sysuser.userType!=3}">
                                <a href="javascript:void(0)" class="stdbtn" title="设置角色"
                                   onclick="showSysUserRole(${sysuser.id})">设置角色</a>
                                <a href="javascript:void(0)" class="stdbtn" title="设置部门"
                                   onclick="showSysUserDepartment(${sysuser.id})">设置部门</a>
                            </c:if>
                            <a href="javascript:void(0)" class="stdbtn" title="删除"
                               onclick="deleteSysUser(${sysuser.id})">删除</a>
                            <c:if test="${sysuser.status==1}">
                                <a href="javascript:void(0)" class="stdbtn" title="恢复"
                                   onclick="updateStatus(${sysuser.id},this)">恢复</a>
                            </c:if>
                            <c:if test="${sysuser.status==0}">
                                <a href="javascript:void(0)" class="stdbtn" title="锁定"
                                   onclick="updateStatus(${sysuser.id},this)">锁定</a>
                            </c:if>

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