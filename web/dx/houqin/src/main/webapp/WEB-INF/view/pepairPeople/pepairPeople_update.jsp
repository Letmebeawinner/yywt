<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>修改维修员用户</title>
    <script type="text/javascript" src="${ctx}/static/admin/js/sysuser/sysuser.js"></script>
    <script type="text/javascript">
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 30px">
        <h1 class="pagetitle">修改维修员用户</h1>
        <span>
                    <span style="color:red">说明</span><br>
                    1. 本页面用于修改用户；<br>
                    2. 修改信息：修改用户信息，点击<span style="color:red">提交保存</span>按钮修改；点击<span style="color:red">重置表单</span>按钮，将重新显示用户修改前的信息；<br>
                    3. 修改密码：添加新密码并确认，点击<span style="color:red">提交保存</span>按钮，点击<span style="color:red">重置表单</span>按钮，将清空新密码以及确认密码
		</span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="${ctx}/admin/base/sysuser/updateSysUser.json"
                  onsubmit="return false;" id="updateUser">
                <p>
                    <label><em style="color: red;">*</em>用户名</label>
                    <span class="field"><input type="text" name="userName" id="userName2" class="longinput"
                                               value="${sysUser.userName}"/><em style="color: red;"
                                                                                 id="usna"></em></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>邮箱</label>
                    <span class="field"><input type="text" name="email" id="email2" class="longinput"
                                               value="${sysUser.email}"/><em style="color: red;" id="mail"></em></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>手机号</label>
                    <span class="field"><input type="text" name="mobile" id="mobile2" class="longinput"
                                               value="${sysUser.mobile}"/><em style="color: red;" id="mob"></em></span>
                </p>
                <input type="hidden" name="id" id="linkId" value="${sysUser.id}"/>
                <p class="stdformbutton">
                    <button class="submit radius2" onclick="update()">提交修改</button>
                </p>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    jQuery(document).ready(function () {
        judgeSelection();
    });

    /**
     * 修改基本信息
     */
    function update() {
        var arr = jQuery('#updateUser').serialize();
        jQuery.ajax({
            url: '${ctx}/admin/base/sysuser/updateSysUser.json',
            type: 'post',
            data: arr,
            dataType: 'json',
            async: false,
            success: function (result) {
                if (result.code == 0) {
                    if (window.confirm("修改成功，是否返回列表页面？")) {
                        window.location = "/admin/base/sysuser/queryUserList.json"
                    }
                } else {
                    alert(result.message);
                }
            }
        })
    }
    /**
     * 修改密码
     */
    function savePsw() {
        var str = jQuery('#updatePsw').serialize();
        jQuery.ajax({
            url: '${ctx}/admin/base/sysuser/updateSysUserPsw.json',
            type: 'post',
            data: str,
            dataType: 'json',
            async: false,
            success: function (result) {
                if (result.code == 0) {
                    if (window.confirm("修改成功，是否返回列表页面？")) {
                        window.location = "/admin/base/sysuser/queryUserList.json"
                    }
                } else {
                    alert(result.message);
                }
            }
        })
    }
</script>
</body>
</html>