<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>在线报名</title>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
<%-- 		<script type="text/javascript" src="${ctx}/static/js/custom/elements.js"></script> --%>
        <script type="text/javascript">
           
        jQuery(function(){
            
        });
        
        function addSign(){
        	/* var name=jQuery("#name").val();
        	var sex=jQuery("#sex").val();
        	var companyName=jQuery("#companyName").val();
        	var mobile=jQuery("#mobile").val();
        	var info=jQuery("#info").val(); */
        	var params=jQuery("#form1").serialize();
        	jQuery.ajax({
	    		url:'${ctx}/admin/jiaowu/sign/createSign.json',
	    		/* data:{"sign.name":name,
	    			"sign.sex":sex,
	    			"sign.companyName":companyName,
	    			"sign.mobile":mobile,
	    			"sign.info":info}, */
	    		data:params,	
	    		type:'post',
	    		dataType:'json',		
	    		success:function (result){
	    			if(result.code=="0"){
	    				window.location.href="${ctx}/admin/jiaowu/sign/signList.json";
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
                <h1 class="pagetitle">在线报名</h1>
                <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于在线报名<br />
                    2.按要求填写相关信息,点击"提交"按钮,在线报名.<br />
					3.带有红色<span style="color: red">*</span>标记的内容为必填部分。
				</span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">
                <div id="updates" class="subcontent">
        		<!-- 主要内容开始 -->
				<div id="validation" class="subcontent">
            	
                    <form id="form1" class="stdform" method="post" action="">
                    <input type="hidden" id="teacherId" name="classes.teacherId" />
                    <input type="hidden" id="teacherName" name="classes.teacherName" />
                        <p>
                        	<label><em style="color: red;">*</em>名称</label>
                            <span class="field"><input type="text" name="sign.name" id="name" class="longinput" /></span>
                        </p>
                        <p>
                        	<label><em style="color: red;">*</em>性别</label>
                            <span class="field"><input type="text" name="sign.sex" id="sex" class="longinput" /></span>
                        </p>
                        <p>
                        	<label><em style="color: red;">*</em>单位名称</label>
                            <span class="field"><input type="text" name="sign.companyName" id="companyName" class="longinput" /></span>
                        </p>
                        <p>
                        	<label><em style="color: red;">*</em>电话</label>
                            <span class="field"><input type="text" name="sign.mobile" id="mobile" class="longinput" /></span>
                        </p>
                        <p>
                        	<label><em style="color: red;">*</em>科目信息</label>
                            <span class="field"><input type="text" name="sign.info" id="info" class="longinput" /></span>
                        </p>                        
                        <br />
                        
                        </form>
                        <p class="stdformbutton">
                        	<button class="radius2" onclick="addSign()" id="submitButton" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">
                        	提交
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