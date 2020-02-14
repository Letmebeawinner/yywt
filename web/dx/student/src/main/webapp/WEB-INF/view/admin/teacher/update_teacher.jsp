<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>修改讲师</title>
<script type="text/javascript"
	src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
<script type="text/javascript">
	jQuery(function() {
        	laydate.skin('molv');
        	laydate({
            	elem: '#birthDay',
            	format:'YYYY-MM-DD hh:mm:ss'
        	});
		var sex="${teacher.sex}";
		jQuery("#sex option[value='"+sex+"']").attr("selected",true);
		var politicalStatus="${teacher.politicalStatus}";
		jQuery("#politicalStatus option[value='"+politicalStatus+"']").attr("selected",true);



		//获取管理类型，只有身份是管理员才能查看身份证
		var uT = ${userType};
		if(uT==1){
			jQuery(".identityCardForTeacher").css('display','block');
		}else{
			jQuery(".identityCardForTeacher").css('display','none');
		}





	});
	function updateTeacher(){
		var params = jQuery("#form1").serialize();
		jQuery
				.ajax({
					url : '${ctx}/admin/jiaowu/teacher/updateTeacher.json',
					data : params,
					type : 'post',
					dataType : 'json',
					success : function(result) {
						if (result.code == "0") {
							window.location.href = "${ctx}/admin/jiaowu/teacher/teacherList.json";
						} else {
							jAlert(result.message, '提示', function() {
							});
						}
					},
					error : function(e) {
						jAlert('更新失败', '提示', function() {
						});
					}
				});
	}
</script>
</head>
<body>
	<div class="centercontent tables">
		<div class="pageheader notab" style="margin-left: 10px">
			<h1 class="pagetitle">修改讲师</h1>
			<span>
                <span style="color:red">说明</span><br>
                1. 本页面用来修改讲师<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
		</div>
		<!--pageheader-->
		<div id="contentwrapper" class="contentwrapper">
			<div id="updates" class="subcontent">
				<!-- 主要内容开始 -->
				<div id="validation" class="subcontent">

					<form id="form1" class="stdform" method="post" action="">
						<input type="hidden" value="${teacher.id}" id="teacherId"
							name="teacher.id" />
						<p>
							<label><em style="color: red;">*</em>姓名</label> <span
								class="field"><input type="text" name="teacher.name"
								id="name" class="longinput" value="${teacher.name}"/></span>
						</p>
						<%-- <p>
                    		<label><em style="color: red;">*</em>生日</label>
                    		<span class="field">
                        		<input id="birthDay" type="text" class="width100 laydate-icon" style="width: 120px;" name="teacher.birthDay" readonly="readonly" value="<fmt:formatDate type='both' value='${teacher.birthDay}' pattern='yyyy-MM-dd HH:mm'/>"/> 
                    		</span>
                		</p> --%>
                		<p>
                        	<label><em style="color: red;">*</em>出生日期</label>
                            <span class="field">
                            	<input style="width: 200px;" id="birthDay" type="text" class="width100" name="teacher.birthDay" value="${teacher.birthDay}"/> 
                            </span>
                        </p> 
                		
						<p class="identityCardForTeacher" style="display: none">
							<label><em style="color: red;">*</em>身份证</label> <span
								class="field"><input type="text"
								name="teacher.identityCard" id="identityCard" class="longinput" value="${teacher.identityCard}"/></span>
						</p>
						<p>
							<label><em style="color: red;">*</em>电话</label> <span
								class="field"><input type="text" name="userInfo.mobile"
								id="mobile" class="longinput" value="${mobile}"/></span>
						</p>
						<p>
							<label><em style="color: red;">*</em>邮箱</label> <span
								class="field"><input type="text" name="userInfo.email"
								id="email" class="longinput" value="${email}"/></span>
						</p>
						<p>
							<label><em style="color: red;">*</em>年龄</label> <span
								class="field"><input type="text" name="teacher.age"
								id="age" class="longinput" value="${teacher.age}"/></span>
						</p>
						<p>
							<label><em style="color: red;">*</em>性别</label> <span class="field"> <select
								name="teacher.sex" id="sex">
									<option value="1">男</option>
									<option value="0">女</option>
							</select>
							</span>
						</p>
						<%--<p>
							<label><em style="color: red;">*</em>是否残疾</label> <span class="field"> <select
								name="teacher.ifCripple" id="ifCripple">
									<option value="1">是</option>
									<option value="0" selected="selected">否</option>
							</select>
							</span>
						</p>--%>

						<p>
							<label><%--<em style="color: red;">*</em>--%>政治面貌</label>
							<span class="field">
								<select name="teacher.politicalStatus" id="politicalStatus">
									<option value="">请选择</option>
									<option value="0">中共党员</option>
									<option value="1" >民主党派</option>
									<option value="2" >无党派人士</option>
									<option value="3" >群众</option>
								</select>
							</span>
						</p>

						<p>
							<label><em style="color: red;">*</em>民族</label> <span class="field"><input type="text"
								name="teacher.nationality" id="nationality" class="longinput" value="${teacher.nationality}"/></span>
						</p>
						<p>
							<label><em style="color: red;">*</em>学历</label> <span class="field"><input type="text"
								name="teacher.education" id="education" class="longinput" value="${teacher.nationality}"/></span>
						</p>
						<p>
							<label><em style="color: red;">*</em>专业</label> <span class="field"><input type="text"
								name="teacher.profession" id="profession" class="longinput" value="${teacher.profession}"/></span>
						</p>
						<p>
							<label><em style="color: red;">*</em>职务</label> <span
								class="field"><input type="text" name="teacher.position"
								id="position" class="longinput" value="${teacher.position}"/></span>
						</p>
						<%--<p>
							<label><em style="color: red;">*</em>基本工资</label> <span
								class="field"><input type="text" name="teacher.baseMoney"
								id="baseMoney" class="longinput" value="${teacher.baseMoney}"/></span>
						</p>--%>
						<p>
							<label><em style="color: red;">*</em>履历信息 <small></small>
							</label> <span class="field"><textarea cols="80" rows="5"
									name="teacher.resumeInfo" id="resumeInfo" class="longinput">${teacher.resumeInfo}</textarea></span>
						</p>
						<br />
					</form>
					<p class="stdformbutton">
						<button class="radius2" onclick="updateTeacher()"
							id="submitButton"
							style="background-color: #d20009; border-color: #de4204; cursor: pointer; font-weight: bold; padding: 7px 10px; color: #fff; margin-left: 220px;">提交</button>
					</p>
				</div>
				<!-- 主要内容结束 -->
				<div class="clear"></div>
			</div>
			<!-- #updates -->
		</div>
	</div>
</body>
</html>