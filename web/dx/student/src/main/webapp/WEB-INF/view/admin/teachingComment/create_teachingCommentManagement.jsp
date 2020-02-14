<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>教学工作评价</title>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
<%-- 		<script type="text/javascript" src="${ctx}/static/js/custom/elements.js"></script> --%>
        <script type="text/javascript">
       /*  (function($){
        	datepicker("#datepicker");
        })(jQuery); */        
        
        
        function addTeachingCommentManagement(){
        	/* var teachingCommentId=jQuery("#teachingCommentId").val();
         	var toPeopleId=jQuery("#toPeopleId").val();
         	var toPeopleName=jQuery("#toPeopleName").val();
         	var content=jQuery("#content").val();
        	var note=jQuery("#note").val(); */
        	var params=jQuery("#form1").serialize();
        	jQuery.ajax({
	    		url:'${ctx}/admin/jiaowu/teachingComment/createTeachingCommentManagement.json',
	    		/* data:{"teachingCommentManagement.teachingCommentId":teachingCommentId,	    			
 	    			"teachingCommentManagement.toPeopleId":toPeopleId,
 	    			"teachingCommentManagement.toPeopleName":toPeopleName,
 	    			"teachingCommentManagement.content":content,
	    			"teachingCommentManagement.note":note}, */
	    		data:params,
	    		type:'post',
	    		dataType:'json',		
	    		success:function (result){
	    			if(result.code=="0"){
	    				window.location.href="${ctx}/admin/jiaowu/teachingComment/teachingCommentManagementList.json";
	    			}else{
	    				jAlert(result.message,'提示',function() {});
	    			}		    			
	    		} ,
	    		error:function(e){
	    			jAlert('添加失败','提示',function() {});
	    		}
	    	});
        }
        
        function selectTeachingComment(){
        	window.open('${ctx}/jiaowu/teachingComment/teachingCommentListForSelect.json','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=1200,height=800');
        }
        
		function addTeachingCommentType(teachingCommentIdAndCourseName){
        	jQuery("#teachingCommentId").val(teachingCommentIdAndCourseName[0]);
        	jQuery("#teachingCommentIdp").html(teachingCommentIdAndCourseName[1]);
        }
		
		function selectToPeople(){
			window.open('${ctx}/jiaowu/teacher/teacherListForSelect.json','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=1200,height=800');
		}
		
		function addToPeopleId(teacherIdAndName){
			jQuery("#toPeopleId").val(teacherIdAndName[0]);
        	jQuery("#toPeopleIdp").html(teacherIdAndName[1]);
        	jQuery("#toPeopleName").val(teacherIdAndName[1]);
		}
    </script>

        </script>
	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">教学工作评价</h1>
                <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于新建教学工作评价<br />
                    2.按要求填写相关信息,点击"提交"按钮,新建教学工作评价.<br />
                    3.带有红色<span style="color: red">*</span>标记的内容为必填部分。
				</span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">
                <div id="updates" class="subcontent">
        		<!-- 主要内容开始 -->
				<div id="validation" class="subcontent">
            	
                    <form id="form1" class="stdform" method="post" action="">     
                    <input type="hidden" id="teachingCommentId" name="teachingCommentManagement.teachingCommentId" />
                    <input type="hidden" id="toPeopleId" name="teachingCommentManagement.toPeopleId" />  
                    <input type="hidden" id="toPeopleName" name="teachingCommentManagement.toPeopleName" />            
                    	<!-- <p>
                        	<label>类型</label>
                            <span class="field">
                            <select name="classes.classTypeId" id="type">
                            	<option value="">请选择</option>
                                <option value="student_to_teacher">学生对老师做出评价</option>
                                <option value="teacher_to_teacher">讲师对讲师做出评价</option>
                                <option value="leader_to_teacher">领导对讲师做出评价</option>
                            </select>
                            </span>
                        </p> -->
                    	<p>
                        	<label><em style="color: red;">*</em>教学工作评价类别</label>
                            <span class="field">
                            <span id="teachingCommentIdp"></span>
                            <a href="javascript:selectTeachingComment()" class="stdbtn btn_orange">选择教学工作类别</a>
                            </span>
                        </p>
                        
                        <p>
                        	<label><em style="color: red;">*</em>被评价人</label>
                            <span class="field">
                            <span id="toPeopleIdp"></span>
                            <a href="javascript:selectToPeople()" class="stdbtn btn_orange">选择被评价人</a>
                            </span>
                        </p>
                        
                       
                        <p>
                        	<label><em style="color: red;">*</em>内容</label>
                            <span class="field"><textarea cols="80" rows="5" name="teachingCommentManagement.content" class="mediuminput" id="content"></textarea></span> 
                        </p>
                       
                        <p>
                        	<label>备注</label>
                            <span class="field"><textarea cols="80" rows="5" name="teachingCommentManagement.note" class="mediuminput" id="note"></textarea></span> 
                        </p>
                        <br />
                        
                        </form>
                        <p class="stdformbutton">
                        	<button class="radius2" onclick="addTeachingCommentManagement()" id="submitButton" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">
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