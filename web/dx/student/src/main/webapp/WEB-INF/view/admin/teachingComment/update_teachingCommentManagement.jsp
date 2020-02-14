<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>完成教学评价</title>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
        <script type="text/javascript">
			jQuery(function(){
			
        });
			function updateTeachingCommentManagement(){
	        	var params=jQuery("#form1").serialize();
	        	jQuery.ajax({
		    		url:'${ctx}/admin/jiaowu/teachingComment/updateTeachingCommentManagement.json',
		    		data:params,
		    		type:'post',
		    		dataType:'json',		
		    		success:function (result){
		    			if(result.code=="0"){
		    				window.location.href="${ctx}/admin/jiaowu/teachingComment/toTeachingCommentListByRole.json";
		    			}else{
		    				jAlert(result.message,'提示',function() {});
		    			}		    			
		    		},
		    		error:function(e){
		    			jAlert('更新失败','提示',function() {});
		    		}
		    	});
			}
			
        </script>
	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">完成教学评价</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面用于完成教学评价.<br />
					2.可填写相关信息,点击"提交"按钮,完成教学评价.<br />
					3.带有红色<span style="color: red">*</span>标记的内容为必填部分。
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">
                <div id="updates" class="subcontent">
        		<!-- 主要内容开始 -->
				<div id="validation" class="subcontent">
            	
                    <form id="form1" class="stdform" method="post" action="">
                    <input type="hidden" value="${teachingCommentManagement.id}" name="teachingCommentManagement.id" />
                        
                        <p>
                        	<label><em style="color: red;">*</em>课程</label>
                            <span class="field"><input type="text" name="teachingCommentManagement.courseName" class="longinput" value="${teachingCommentManagement.courseName}" readonly="readonly"/></span>
                        </p>
                        
                        <p>
                        	<label><em style="color: red;">*</em>讲师</label>
                            <span class="field"><input type="text" name="teachingCommentManagement.toPeopleName" class="longinput" value="${teachingCommentManagement.toPeopleName}" readonly="readonly"/></span>
                        </p>
                        
                        <p>
                        	<label><em style="color: red;">*</em>内容</label>
                            <span class="field"><textarea cols="80" rows="5" name="teachingCommentManagement.content" class="mediuminput" id="note">${teachingCommentManagement.content}</textarea></span> 
                        </p>
                        <br />
                        
                        
                    </form>
					<p class="stdformbutton">
                        	<button class="radius2" onclick="updateTeachingCommentManagement()" id="submitButton" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">提交</button>
                        </p>
            </div>
            <!-- 主要内容结束 -->
				<div class="clear"></div>
            </div><!-- #updates -->
	    </div>
	    </div>
	</body>
</html>