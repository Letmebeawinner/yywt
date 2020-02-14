<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>核对新生信息</title>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
<%-- 		<script type="text/javascript" src="${ctx}/static/js/custom/elements.js"></script> --%>
        <script type="text/javascript">        
        jQuery(function(){
        	var id="${user.id}";
        	jQuery("#userId").val(id);
        	var year="${year}";
        	jQuery("#year").val(year);
        	var month="${month}";
        	jQuery("#month").val(month);
        	var classTypeId="${user.classTypeId}";
        	jQuery("#classTypeId").val(classTypeId);
        	var classId="${user.classId}";
        	jQuery("#classId").val(classId);
        	var className="${className}"
        	var serialNumber="${user.serialNumber}";
        	jQuery("#serialNumber").val(serialNumber);
        	var name="${user.name}";
        	jQuery("#name").val(name);
        	var idNumber="${user.idNumber}";
        	jQuery("#idNumber").val(idNumber);
        	var mobile="${mobile}";
        	jQuery("#mobile").val(mobile);
        	var email="${email}";
        	jQuery("#email").val(email);        	
        	var sex="${user.sex}";
        	jQuery("#sex").val(sex);
        	var age="${user.age}";
        	jQuery("#age").val(age);
        	var note="${user.note}";
        	jQuery("#note").val(note);        	        	        	
        });
        
        function checkNewUser(){
        	var id=jQuery("#userId").val();        	
        	jQuery.ajax({
	    		url:'${ctx}/admin/jiaowu/user/checkNewUser.json',
	    		data:{"id":id},
	    		type:'post',
	    		dataType:'json',		
	    		success:function (result){
	    			if(result.code=="0"){
	    				window.location.href="${ctx}/admin/jiaowu/user/newUserList.json";
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

        </script>
	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">核对新生信息</h1>
                <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于核对新生信息<br />
                    2.可查看某位学员的信息,点击"核对无误"按钮,表示该新生信息无误.或点击"返回"按钮,返回上一页面.
				</span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">
                <div id="updates" class="subcontent">
        		<!-- 主要内容开始 -->
				<div id="validation" class="subcontent">
            	
                    <form id="form1" class="stdform" method="post" action="">
                    <input type="hidden" id="userId" name="user.id" />
                    	<p>
                        	<label>年份</label>
                            <span class="field"><input type="text" name="year" id="year" class="longinput" readonly="readonly"/></span>
                        </p>
                        <p>
                        	<label>月份</label>
                            <span class="field"><input type="text" name="month" id="month" class="longinput" readonly="readonly"/></span>
                        </p>
                    	 <p>
                        	<label>班型</label>
                            <span class="field"><input type="text" name="classTypeId" id="classTypeId" class="longinput" readonly="readonly"/></span>
                        </p>
                         <p>
                        	<label>班次</label>
                            <span class="field"><input type="text" name="classId" id="classId" class="longinput" readonly="readonly"/></span>
                        </p>
                        
                        <p>
                        	<label>学员序号</label>
                            <span class="field"><input type="text" name="user.serialNumber" id="serialNumber" class="longinput" readonly="readonly"/></span>
                        </p>
                        
                        <p>
                        	<label>名称</label>
                            <span class="field"><input type="text" name="user.name" id="name" class="longinput" readonly="readonly"/></span>
                        </p>
                        <p>
                        	<label>身份证号</label>
                            <span class="field"><input type="text" name="user.idNumber" id="idNumber" class="longinput" readonly="readonly"/></span>
                        </p>
                        <p>
                        	<label>手机号</label>
                            <span class="field"><input type="text" name="user.mobile" id="mobile" class="longinput" readonly="readonly"/></span>
                        </p>
                        <p>
                        	<label>邮箱</label>
                            <span class="field"><input type="text" name="user.email" id="email" class="longinput" readonly="readonly"/></span>
                        </p>                        
                        <p>
                        	<label>性别</label>
                            <span class="field">
                            <select name="user.sex" id="sex" readonly="readonly">
                            	<option value="">请选择</option>
                                <option value="male">男</option>
                                <option value="female">女</option>
                            </select>
                            </span>
                        </p>
                        <p>
                        	<label>年龄</label>
                            <span class="field"><input type="text" name="user.age" id="age" class="longinput" readonly="readonly"/></span>
                        </p>
                        
                        <p>
                        	<label>备注</label>
                            <span class="field"><textarea cols="80" rows="5" name="user.note" class="mediuminput" id="note" readonly="readonly"></textarea></span> 
                        </p>
                        <br />
                        
                        </form>
                        <p class="stdformbutton">
                        	<button class="radius2" onclick="checkNewUser()" id="submitButton" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">
                        	核对无误
                        	</button>
                        	<button class="radius2" onclick="javascript :history.back(-1);" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">
                        	返回
                        	</button>
                        </p>
                    
					
            </div>
            <!-- 主要内容结束 -->
				<div class="clear"></div>
            </div><!-- #updates -->
	    </div>
	    </div>
	</body>
</html>