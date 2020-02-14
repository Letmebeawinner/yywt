<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>修改账号信息</title>
    <script type="text/javascript">
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 30px">
        <h1 class="pagetitle">修改管理员用户</h1>
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
                                               value="${_sysUser.userName}"/><em style="color: red;"
                                                                                 id="usna"></em></span>
                </p>
                <p>
                    <label>用户别名</label>
                    <span class="field">
                        <input type="text" name="anotherName" id="anotherUserName" class="longinput" value="${_sysUser.anotherName}"/>
                        <em style="color: red;" id="usanother"></em></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>邮箱</label>
                    <span class="field"><input type="text" name="email" id="email2" class="longinput"
                                               value="${_sysUser.email}"/><em style="color: red;" id="mail"></em></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>手机号</label>
                    <span class="field"><input type="text" name="mobile" id="mobile2" class="longinput"
                                               value="${_sysUser.mobile}"/><em style="color: red;" id="mob"></em></span>
                </p>

                <input type="hidden" name="linkId" id="linkId" value="${_sysUser.linkId}"/>
                <input type="hidden" name="userType" id="queryuserType" value="${_sysUser.userType}"/>
                <p class="stdformbutton">
                    <button class="submit radius2" onclick="update()">提交保存</button>
                    <input type="reset" class="reset radius2" value="重置表单" onclick="formReset()"/>
                </p>
            </form>
            <form action="${ctx}/admin/base/sysuser/updateSysUserPsw.json" method="post" class="stdform stdform2"
                  id="updatePsw" onsubmit="return false;">
                <div class="pageheader notab">
                    <h1 class="pagetitle">修改管理员用户密码</h1>
                </div><!--pageheader-->
                <p>
                    <label><em style="color: red;">*</em>原密码</label>
                    <span class="field"><input type="password" name="nowPassword" id="nowPassword" class="longinput"
                                               placeholder="原密码不能为空"/><em style="color: red;" id="nowPsw"></em></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>新密码</label>
                    <span class="field"><input type="password" name="newPassword" id="newPassword2" class="longinput"
                                               placeholder="新密码不能为空"/><em style="color: red;" id="newPsw"></em></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>确认密码</label>
                    <span class="field"><input type="password" class="longinput" id="rpassword" name="rnewPassword"
                                               placeholder="两次输入密码必须相同"/><em style="color: red;"
                                                                             id="rnewPsw"></em></span>
                </p>
                <p class="stdformbutton">
                    <button class="submit radius2" onclick="savePsw()">提交保存</button>
                    <input type="reset" class="reset radius2" value="重置表单"/>
                </p>
            </form>

            <%--<form action="${ctx}/admin/base/sysuser/updateAuditPassword.json" method="post" class="stdform stdform2"--%>
                  <%--id="updateAuditPassword" onsubmit="return false;">--%>
                <%--<div class="pageheader notab">--%>
                    <%--<h1 class="pagetitle">修改用户审批密码</h1>--%>
                <%--</div><!--pageheader-->--%>
                <%--<p>--%>
                    <%--<label><em style="color: red;">*</em>账号密码</label>--%>
                    <%--<span class="field"><input type="password" name="password" id="password" class="longinput"--%>
                                               <%--placeholder="账号密码不能为空"/><em style="color: red;"></em></span>--%>
                <%--</p>--%>
                <%--<p>--%>
                    <%--<label><em style="color: red;">*</em>审批密码</label>--%>
                    <%--<span class="field"><input type="password" name="auditPassword" id="auditPassword" class="longinput"--%>
                                               <%--placeholder="新密码不能为空"/><em style="color: red;"></em></span>--%>
                <%--</p>--%>
                <%--<p>--%>
                    <%--<label><em style="color: red;">*</em>确认审批密码</label>--%>
                    <%--<span class="field"><input type="password" class="longinput" id="confirmAuditPassword"--%>
                                               <%--placeholder="两次输入密码必须相同"/><em style="color: red;"></em></span>--%>
                <%--</p>--%>
                <%--<p class="stdformbutton">--%>
                    <%--<button class="submit radius2" onclick="saveAuditPassword()">提交保存</button>--%>
                    <%--<input type="reset" class="reset radius2" value="重置表单"/>--%>
                <%--</p>--%>
                <%--<input type = "hidden" name = "id" value = "${_sysUser.id}">--%>
            <%--</form>--%>
        </div>
    </div>
</div>
<script type="text/javascript">
    /**
     * 修改基本信息
     */
    function update() {
        var userName=jQuery("#userName2").val();
        if(userName==""||userName==null){
            alert("请完善您的用户名");
            return;
        }
        var email=jQuery("#email2").val();
        if(email==""||email==null){
            alert("请完善您的邮箱");
            return;
        }
        var mobile=jQuery("#mobile2").val();
        if(mobile==""||mobile==null){
            alert("请完善您的手机号");
            return;
        }

        var arr = jQuery('#updateUser').serialize();
        jQuery.ajax({
            url: '${ctx}/admin/base/sysuser/updateThisSysUser.json',
            type: 'post',
            data: arr,
            dataType: 'json',
            async: false,
            success: function (result) {
                if (result.code == 0) {
                    window.alert("保存成功")
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
        var data = jQuery('#updatePsw').serialize();
        jQuery.ajax({
            url: '${ctx}/admin/base/sysuser/updateThisSysUserPsw.json',
            type: 'post',
            data: data,
            dataType: 'json',
            async: false,
            success: function (result) {
                if (result.code == 0) {
                    window.alert("修改密码成功");
                    jQuery("#nowPassword").val('');
                    jQuery("#newPassword2").val('');
                    jQuery("#rpassword").val('');
                } else {
                    alert(result.message);
                }
            }
        })
    }

    //修改审核密码
    function saveAuditPassword() {
        if (!jQuery("#password").val()) {
            alert("密码不能为空");
            return;
        }
        if (!jQuery("#auditPassword").val()) {
            alert("审核密码不能为空");
            return;
        }
        if (jQuery("#auditPassword").val() != jQuery("#confirmAuditPassword").val()) {
            alert("确认密码和审核密码不一致");
            return;
        }
        var str = jQuery('#updateAuditPassword').serialize();
        jQuery.ajax({
            url: '${ctx}/admin/base/sysuser/updateAuditPassword.json',
            type: 'post',
            data: str,
            dataType: 'json',
            async: false,
            success: function (result) {
                if (result.code == 0) {
                    alert(result.message);
                    jQuery("#password").val('');
                    jQuery("#auditPassword").val('');
                    jQuery("#confirmAuditPassword").val('');
                } else {
                    alert(result.message);
                }
            }
        })
    }
    /**
     * 重置表单
     */
    var condition =${_sysUser.userType};
    function formReset() {
        jQuery('.bindingpeople').html(bind).show();
        if (condition == 1 || condition == 0) {
            directAddition();
        }
        if (condition == 2) {
            jQuery(".bindingpeople").show();
            jQuery("#teacherList").show();
            jQuery("#studentList").hide();
            jQuery("#deletebinding").show();
        }
        if (condition == 3) {
            jQuery(".bindingpeople").show();
            jQuery("#teacherList").hide();
            jQuery("#studentList").show();
            jQuery("#deletebinding").show();
        }
    }
</script>
</body>
</html>