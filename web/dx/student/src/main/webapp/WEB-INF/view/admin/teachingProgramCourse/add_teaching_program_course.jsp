<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>新建教学计划课程</title>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
<%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
        <script type="text/javascript">

		function addTeachingProgramCourse(){
			var params=jQuery("#form1").serialize();
			jQuery.ajax({
	    		url:'${ctx}/admin/jiaowu/teach/doCreateTeachingProgramCourse.json',
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
	    			jAlert('添加失败','提示',function() {});
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
                <h1 class="pagetitle">新建教学计划课程</h1>
                <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于新建教学计划课程.<br />
                    2.按要求填写相关信息,点击"提交"按钮,新建教学计划课程.<br />
                    3.带有红色<span style="color: red">*</span>标记的内容为必填部分。
				</span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">

            <div id="updates" class="subcontent">
                <!-- 主要内容开始 -->
                <div id="validation" class="subcontent">

                    <form id="form1" class="stdform" method="post" action="">
                    	<input type="hidden" name="teachingProgramCourse.classroomId" id="classroomId" />
                    	<input type="hidden" name="teachingProgramCourse.classroomName" id="classroomName" />
                    	<input type="hidden" name="teachingProgramCourse.courseId" id="courseId" />
                    	<input type="hidden" name="teachingProgramCourse.courseName" id="courseName" />
                    	<input type="hidden" name="teachingProgramCourse.classId" id="classId" />
                    	<input type="hidden" name="teachingProgramCourse.className" id="className" />
                    	<!-- <input type="hidden" name="teachingProgramCourse.teacherId" id="teacherId" />
                    	<input type="hidden" name="teachingProgramCourse.teacherName" id="teacherName" /> -->
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
                            <span id="classroomspan"></span>
                            <a href="javascript:selectClassroom()" class="stdbtn btn_orange">选择教室</a>
                            </span>
                        </p>


                        <p>
                            <label>备注</label>
                            <span class="field"><textarea cols="80" rows="5" name="teachingProgramCourse.note" class="mediuminput" id="note"></textarea></span> 
                        </p>
                        <br />

                        
                    </form>
					<p class="stdformbutton">
                            <button class="radius2" onclick="addTeachingProgramCourse()" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">提交</button>
                    </p> 
            </div>
            <!-- 主要内容结束 -->
                <div class="clear"></div>
            </div><!-- #updates -->

        </div>
	    </div>
	</body>
</html>