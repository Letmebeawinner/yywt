<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>维修员用户列表</title>
    <script type="text/javascript" src="${ctx}/static/js/function/function.js"></script>
    <script type="text/javascript" src="${ctx}/static/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">
        var data = data;
        var functionsIds;
        function addSystemUsers() {
            jQuery.ajax({
                type: "post",
                url: "/admin/houqin/getSystemUserList.json",
                data: data,
                dataType: "text",
                async: false,
                success: function (result) {
                    jQuery.alerts._show('选择人员列表', null, null, 'addCont', function (confirm) {
                        if (confirm) {
                            if (functionsIds) {
                                jQuery.ajax({
                                    type: "post",
                                    url: "/admin/houqin/addPeopaorPeople.json",
                                    data: {'functionsIds': functionsIds},
                                    dataType: "json",
                                    success: function (result) {
                                        if (result.code == "0") {
                                            alert("操作成功!");
                                            window.location.reload();
                                        } else {
                                            alert(result.message);
                                        }
                                    }
                                });
                            } else {
                                alert("没有选择！");
                            }
                        }
                    });
                    jQuery('#popup_message').html(result);
                    // 修改弹窗的位置。距窗口上边距150px，左边距30%.
                    jQuery('#popup_container').css({
                        top: 50,
                        left: '20%',
                        overflow: 'hidden'
                    });
                    jQuery('#popup_container').css("max-height", "650px");
                    jQuery('#popup_container').css("width", "902px");
                    jQuery('#popup_message').css("max-height", "600px");
                    jQuery('#popup_message').css('overflow-y', 'scroll');
                    jQuery('#popup_panel').css('margin', '-27px 0 10px 0');
                }
            });
        }
        function delPepeirPeople(id) {
            jQuery.ajax({
                type: "post",
                url: "/admin/houqin/deletePeopaorPeople.json",
                data: {'id': id},
                dataType: "json",
                success: function (result) {
                    if (result.code == "0") {
                        alert("操作成功!");
                        window.location.reload();
                    } else {
                        alert(result.message);
                    }
                }
            });
        }
    </script>
</head>
<body>
<form action="${ctx}/admin/houqin/queryPeopairPeopleList.json" method="post" id="searchForm">
    <div class="centercontent tables">
        <div class="pageheader" style="margin-left: 30px">
            <h1 class="pagetitle">维修员用户列表</h1>
            <span>
                    <span style="color:red">说明</span><br>
                    1. 本页面用于维修员用户的管理；<br>
                    2. 添加用户：点击<span style="color:red">添加</span>按钮，跳转添加用户页面，添加相关信息；<br>
                    3. 更新用户：点击<span style="color:red">编辑</span>按钮，跳转编辑用户页面，修改相关信息；<br>
                    4. 锁定、恢复：点击<span style="color:red">锁定</span>按钮，锁定用户,锁定的用户将不能进行登录等操作，点击<span
                    style="color:red">恢复</span>恢复用户， 用户恢复正常<br>
                    5. 设置部门：点击<span style="color:red">设置部门</span>在弹窗中选中部门，点击确定，给用户添加部门信息；<br>
                    6.查询：输入查询条件，点击<span style="color:red">搜索</span>
                </span>
        </div><!--pageheader-->
        <div id="contentwrapper" class="contentwrapper">

            <div class="overviewhead clearfix mb10">
                <div class="fl mt5">
                    <div class="disIb ml20 mb10">
                        <a href="javascript: void(0)" class="stdbtn m80" onclick="addSystemUsers()">添加维修人员</a>
                    </div>
                </div>
            </div>
            <!-- 数据显示列表，开始 -->
            <div class="pr">
                <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                    <colgroup>
                        <col class="con0"/>
                        <col class="con1"/>
                        <col class="con0"/>
                        <col class="con1"/>
                    </colgroup>
                    <thead>
                    <tr>
                        <th class="head0 center">用户编号</th>
                        <th class="head1 center">用户名</th>
                        <th class="head0 center">邮箱</th>
                        <th class="head1 center">手机</th>
                        <th class="head0 center">角色类型</th>
                        <th class="head0 center">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${sysUserList}" var="sysuser">
                        <tr>
                            <td>${sysuser.userNo}</td>
                            <td>${sysuser.userName}</td>
                            <td>${sysuser.email}</td>
                            <td>${sysuser.mobile}</td>
                            <td>${sysuser.roleName}</td>
                            <td style="text-align: center"><a href="javascript:void(0)" onclick="delPepeirPeople(${sysuser.id})" class="stdbtn">删除</a></td>
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
</form>
</body>
</html>