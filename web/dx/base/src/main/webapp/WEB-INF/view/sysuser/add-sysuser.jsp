<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>添加用户</title>
	<script type="text/javascript" src="${ctx}/static/admin/js/sysuser/sysuser.js"></script>
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
				<p class="bindingpeople" style="display: none;">
					<label><em style="color: red;">*</em>添加绑定人</label>
					<span class="field">
					<a id="deletebinding" onclick="deletequeryAdministrator()">
						<span id="addUserName"></span>&nbsp;&nbsp;<i class="fa fa-w c-999 fa-close"></i>
					</a>
					</span>
					<span class="field">
						<button id="teacherList" class="stdbtn btn_red" onclick="getTeacherStudentList(2)">选择教职工</button>
						<button id="studentList" class="stdbtn btn_red" onclick="getTeacherStudentList(3)">选择学员</button>
					</span>
				</p>
				<p>
					<label><em style="color: red;">*</em>选择用户类型</label>
					<span class="field">
						<select class="query"  id="userType" name = "userType">
							<option value="1">管理员</option>
							<option value="2">教职工</option>
							<option value="3">学员</option>
							<option value="4">驾驶员</option>
							<option value="5">单位报名</option>
							<option value="6">物业公司</option>
						</select>
					</span>
				</p>
				<p id="unitIdp" style="display: none">
					<label><em style="color: red;">*</em>单位ID</label>
					<span class="field"><input type="text" name="unitId" id="unitId" class="longinput" placeholder="" onkeyup="if(/\D/.test(this.value)){alert('只能输入数字');this.value='';}"/><span style="color: red;">您可以到学员系统-字典管理-单位列表查看单位ID</span></span>
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