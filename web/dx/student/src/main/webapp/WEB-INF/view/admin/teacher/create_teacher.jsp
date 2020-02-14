<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>创建班主任</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">
    
    	jQuery(function(){
        	laydate.skin('molv');
        	laydate({
            	elem: '#birthDay',
            	format:'YYYY-MM-DD hh:mm:ss'
        	});
    	});
    
        function addTeacher(){
         //性别校验
            if(jQuery("#sex").val()==''){
                jAlert("性别不能为空","提示",function() {});

            }

        	var params=jQuery("#addTeacher").serialize();
        	jQuery.ajax({
	    		url:'${ctx}/admin/jiaowu/teacher/createTeacher.json',
	    		data:params,
	    		type:'post',
	    		dataType:'json',		
	    		success:function (result){
	    			if(result.code=="0"){
	    				window.location.href="${ctx}/admin/jiaowu/teacher/teacherList.json";
	    			}else{
	    				jAlert(result.message,'提示',function() {});
	    			}		    			
	    		} ,
	    		error:function(e){
	    			jAlert('添加失败','提示',function() {});
	    		}
	    	});
        }
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">创建讲师</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来创建讲师<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addTeacher">
                <p>
                    <label><em style="color: red;">*</em>姓名</label>
                    <span class="field"><input type="text" name="teacher.name" id="name" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>出生日期</label>
                    <span class="field">
                        <input id="birthDay" type="text" class="width100 laydate-icon" style="width: 120px;" name="teacher.birthDay" readonly="readonly"/> 
                    </span>
                </p>

                <p class="identityCardForTeacher" >
                    <label><em style="color: red;">*</em>身份证</label>
                    <span class="field"><input type="text" name="teacher.identityCard" id="identityCard" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>电话</label>
                    <span class="field"><input type="text" name="userInfo.mobile" id="mobile" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>邮箱</label>
                    <span class="field"><input type="text" name="userInfo.email" id="email" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>密码</label>
                    <span class="field"><input type="password" name="userInfo.password" id="password" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>请再次输入密码</label>
                    <span class="field"><input type="password" name="userInfo.confirmPassword" id="confirmPassword" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>年龄</label>
                    <span class="field"><input type="text" name="teacher.age" id="age" class="longinput" onchange="if(/\D/.test(this.value)){jAlert('只能输入数字','提示',null);this.value='';}"/></span>
                </p>


                <p>
                    <label><em style="color: red;">*</em>性别</label>
                    <span class="field">
                        <select name="teacher.sex" id="sex" >
                             <option value="">请选择</option>
                            <option value="0">男</option>
                            <option value="1">女</option>
                        </select>
                    </span>
                </p>
               <%-- <p>
                    <label><em style="color: red;">*</em>是否残疾</label>
                    <span class="field">
                        <select name="teacher.ifCripple" id="ifCripple">
                            <option value="1">是</option>
                            <option value="0" selected="selected">否</option>
                        </select>
                    </span>
                </p>--%>

                <p>
                    <label><em style="color: red;">*</em>政治面貌</label>
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
                    <label><%--<em style="color: red;">*</em>--%>民族</label>
                    <span class="field"><input type="text" name="teacher.nationality" id="nationality" class="longinput"/></span>
                </p>
                <p>
                    <label><%--<em style="color: red;">*</em>--%>学历</label>
                    <span class="field"><input type="text" name="teacher.education" id="education" class="longinput"/></span>
                </p>
                <p>
                    <label><%--<em style="color: red;">*</em>--%>专业</label>
                    <span class="field"><input type="text" name="teacher.profession" id="profession"
                                               class="longinput"/></span>
                </p>
                <p>
                    <label><%--<em style="color: red;">*</em>--%>职务</label>
                    <span class="field"><input type="text" name="teacher.position" id="position" class="longinput"/></span>
                </p>
               <%-- <p>
                    <label><em style="color: red;">*</em>基本工资</label>
                    <span class="field"><input type="text" name="teacher.baseMoney" id="baseMoney" class="longinput" onchange="if(/\D\./.test(this.value)){jAlert('只能输入数字','提示',null);this.value='';}"/></span>
                </p>--%>
                <p>
                    <label><%--<em style="color: red;">*</em>--%>履历信息
                        <small></small>
                    </label>
                    <span class="field"><textarea cols="80" rows="5" name="teacher.resumeInfo" id="resumeInfo" class="longinput"></textarea></span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addTeacher();return false;">添 加</button>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
</html>