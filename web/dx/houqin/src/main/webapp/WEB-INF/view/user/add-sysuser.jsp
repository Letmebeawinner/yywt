<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>添加物业人员</title>
	<script type="text/javascript">
		$(function(){
			jQuery("#userType").change(function(){
				var ut=jQuery(this).val();
				if(ut=="5"){
					jQuery("#unitIdp").show();
				}else{
					jQuery("#unitIdp").hide();
					jQuery("#unitId").val("");
				}
			});
		});

		/**
		 *重置表单
		 */
		function formResetAddSysuser() {
			jQuery('.bindingpeople').hide();
		}

		/**
		 * 添加系统=用户
		 */
		function save() {
			var sysUser = jQuery("#addUser").serialize();
			jQuery.ajax({
				url:  '/admin/houqin/addSysUser.json',
				type: 'POST',
				data: sysUser,
				dataType: 'json',
				success: function (result) {
					if (result.code == 0) {
						alert(result.message);
						window.location.href = '/admin/houqin/queryUserList.json';
						jQuery(".reset.radius2").click();
					} else {
						alert(result.message);
					}
				}
			});
		}
	</script>
</head>
<body>
<div class="centercontent">
	<div class="pageheader notab" style="margin-left: 30px">
		<h1 class="pagetitle">添加用户</h1>
		<span>
                    <span style="color:red">说明</span><br>
                    1. 本页面用于添加用户；<br>
                    2. 添加用户：按要求填写相关信息,点击<span style="color:red">提交保存</span>按钮；添加用户<br>
                    3. 重置表单：点击<span style="color:red">重置表单</span>按钮，将清空以填写的用户信息
		</span>
	</div><!--pageheader-->
	<div id="contentwrapper" class="contentwrapper">

		<div id="basicform" class="subcontent">
			<form class="stdform stdform2" method="post" onsubmit="return false;" id="addUser">
				<p>
					<label><em style="color: red;">*</em>用户名</label>
					<span class="field"><input type="text" name="userName" id="userName2" class="longinput"  placeholder="用户名不能为空，且大于6位小于20位"/></span>
				</p>
				<p>
					<label><em style="color: red;">*</em>邮箱</label>
					<span class="field"><input type="text" name="email" id="email2" class="longinput" placeholder="邮箱不能为空" /></span>
				</p>
				<p>
					<label><em style="color: red;">*</em>手机号</label>
					<span class="field"><input type="text" name="mobile" id="mobile2" class="longinput" placeholder="手机号不能为空"/></span>
				</p>
				<p>
					<label><em style="color: red;">*</em>密码</label>
					<span class="field"><input type="password" name="password" id="password2" class="longinput" placeholder="密码不能为空" /></span>
				</p>
				<p>
					<label><em style="color: red;">*</em>确认密码</label>
					<span class="field"><input type="password"  class="longinput" id="rpassword" name="rpassword" placeholder="两次输入的密码必须相同"/></span>
				</p>
				<p>
					<label><em style="color: red;">*</em>选择用户类型</label>
					<span class="field">
						<select class="query"  id="userType" name = "userType">
							<option value="6">物业公司</option>
						</select>
					</span>
				</p>
                <input type="hidden" name="linkId" id="linkId" value=""/>
                <%--<input type="hidden" name="userType" id="queryuserType"  value=""/>--%>
				<p class="stdformbutton">
					<button class="submit radius2" onclick="save()">提交保存</button>
					<input type="reset" class="reset radius2" value="重置表单" onclick="formResetAddSysuser()">
					<%--//<input type="reset" class="reset radius2" value="重置表单" />--%>
				</p>
			</form>
		</div>
	</div>
</div>
</body>
</html>