<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>修改教学计划课程</title>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
<%-- 		<script type="text/javascript" src="${ctx}/static/admin/js/classes.js"></script> --%>
<%-- 		<script type="text/javascript" src="${ctx}/static/admin/js/jquery-1.7.min.js"></script> --%>
        <script type="text/javascript">
			
			function updateTeachingProgramCourse(){
	        	var params=jQuery("#form1").serialize();
	        	jQuery.ajax({
		    		url:'${ctx}/admin/jiaowu/teach/doUpdateTeachingProgramCourse.json',
		    		data:params,
		    		type:'post',
		    		dataType:'json',		
		    		success:function (result){
		    			if(result.code=="0"){
		    				window.location.href="${ctx}/admin/jiaowu/teach/teachingProgramCourseList.json";
		    			}else{
		    				jAlert(result.message,'提示',function() {});
		    			}		    			
		    		},
		    		error:function(e){
		    			jAlert('更新失败','提示',function() {});
		    		}
		    	});
			}
			
			function selectClassroom(){
	        	window.open('${ctx}/jiaowu/classroom/classroomListForSelect.json','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=1200,height=800');
	        }
	        
	        function addClassroomIdAndPosition(classroomIdAndPosition){
	        	jQuery("#classroomId").val(classroomIdAndPosition.split("-")[0]);
	        	jQuery("#classroomspan").html(classroomIdAndPosition.split("-")[1]);
	        	jQuery("#classroomName").val(classroomIdAndPosition.split("-")[1]);
	        }
	        
	        function selectCourse(){
	            window.open('${ctx}/jiaowu/course/courseListForSelect.json','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=1200,height=800');
	        }

	        function addCourse(courseArray){
	            jQuery("#coursespan").html(courseArray[1]);
	            jQuery("#courseId").val(courseArray[0]);
	            jQuery("#courseName").val(courseArray[1]);
	        }
	        
	        function selectClass(){
	        	window.open('${ctx}/jiaowu/class/classListForSelect.json','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=1200,height=800');
	        }
	        
	        function addClass(classArray){
	        	jQuery("#classspan").html(classArray[1]);
	            jQuery("#classId").val(classArray[0]);
	            jQuery("#className").val(classArray[1]);
	        }
	        
	        function selectTeacher(){
	            window.open('${ctx}/jiaowu/teacher/teacherListForSelect.json','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=1200,height=800');
	        }

	        function addToPeopleId(teacherArray){
	            jQuery("#teacherspan").html(teacherArray[1]);
	            jQuery("#teacherId").val(teacherArray[0]);
	            jQuery("#teacherName").val(teacherArray[1]);
	        }
        </script>
	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">修改教学计划课程</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面用于修改教学计划课程.<br />
					2.可修改相关信息,点击"提交"按钮,保存修改信息.<br />
					3.带有红色<span style="color: red">*</span>标记的内容为必填部分。
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">
                <div id="updates" class="subcontent">
        		<!-- 主要内容开始 -->
				<div id="validation" class="subcontent">
            	
                    <form id="form1" class="stdform" method="post" action="">
                    <input type="hidden" value="${teachingProgramCourse.id}" id="teachingProgramCourseId" />
                    	<input type="hidden" name="teachingProgramCourse.classroomId" id="classroomId" value="${teachingProgramCourse.classroomId }"/>
                    	<input type="hidden" name="teachingProgramCourse.classroomName" id="classroomName" value="${teachingProgramCourse.classroomName }"/>
                    	<input type="hidden" name="teachingProgramCourse.courseId" id="courseId" value="${teachingProgramCourse.courseId }"/>
                    	<input type="hidden" name="teachingProgramCourse.courseName" id="courseName" value="${teachingProgramCourse.courseName }"/>
                    	<input type="hidden" name="teachingProgramCourse.classId" id="classId" value="${teachingProgramCourse.classId}"/>
                    	<input type="hidden" name="teachingProgramCourse.className" id="className" value="${teachingProgramCourse.className}"/>
                    	<%-- <input type="hidden" name="teachingProgramCourse.teacherId" id="teacherId" value="${teachingProgramCourse.teacherId}"/>
                    	<input type="hidden" name="teachingProgramCourse.teacherName" id="teacherName" value="${teachingProgramCourse.teacherName }"/> --%>
                    	<p>
                            <label><em style="color: red;">*</em>课程</label>
                            <span class="field">
                            	<span id="coursespan">${teachingProgramCourse.courseName}</span>
                                <a href="javascript:selectCourse()" class="stdbtn btn_orange">选择课程</a>
                            </span>
                        </p>
                        <p>
                            <label><em style="color: red;">*</em>班次</label>
                            <span class="field">
                            	<span id="classspan">${teachingProgramCourse.className}</span>
                                <a href="javascript:selectClass()" class="stdbtn btn_orange">选择班次</a>
                            </span>
                        </p>
                        <%-- <p>
                            <label><em style="color: red;">*</em>讲师</label>
                            <span class="field">
                            	<span id="teacherspan">${teachingProgramCourse.teacherName}</span>
                                <a href="javascript:selectTeacher()" class="stdbtn btn_orange">选择讲师</a>
                            </span>
                        </p> --%>
						<p id="classroomp">
                        	<label><em style="color: red;">*</em>教室</label>
                            <span class="field">
                            	<span id="classroomspan">${teachingProgramCourse.classroomName}</span>
                            	<a href="javascript:selectClassroom()" class="stdbtn btn_orange">选择教室</a>
                            </span>
                        </p>

                        <p>
                            <label>备注</label>
                            <span class="field"><textarea cols="80" rows="5" name="teachingProgramCourse.note" class="mediuminput" id="note">${teachingProgramCourse.note }</textarea></span> 
                        </p>
                        <br />
                        
                       
                    </form>
					 <p class="stdformbutton">
                        	<button class="radius2" onclick="updateTeachingProgramCourse()" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">提交</button>
                        </p>
            </div>
            <!-- 主要内容结束 -->
				<div class="clear"></div>
            </div><!-- #updates -->
	    </div>
	    </div>
	</body>
</html>