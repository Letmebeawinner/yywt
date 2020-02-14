<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>排课详情</title>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
        <script type="text/javascript">
			jQuery(function(){
			
            
        });
			function updateCourseArrange(){
				var classIds=document.getElementsByName("classId");
				var str="";
				jQuery(classIds).each(function(){
					if(jQuery(this).prop("checked")){
						str+=this.value+",";
					}
				});
				
				var classId="${courseArrange.classId}";
				var id=jQuery("#courseArrangeId").val();
				var classroomId=jQuery("#classroomId").val();
				var classroomName=jQuery("#classroomName").val();	 
				var teacherId=jQuery("#teacherId").val();
				var teacherName=jQuery("#teacherName").val();
	        	jQuery.ajax({
		    		url:'${ctx}/admin/jiaowu/courseArrange/updateOneCourseArrange.json',
		    		data:{"courseArrange.id":id,
		    			"courseArrange.classroomId":classroomId,
		    			"courseArrange.classroomName":classroomName,
		    			"courseArrange.teacherId":teacherId,
		    			"courseArrange.teacherName":teacherName,
		    			"otherClassIds":str},
		    		type:'post',
		    		dataType:'json',		
		    		success:function (result){
		    			if(result.code=="0"){
		    				window.location.href="${ctx}/admin/jiaowu/courseArrange/toUpdateCourseArrange.json?classId="+classId;
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
                <h1 class="pagetitle">排课详情</h1>
                <span>
                    <span style="color:red">说明</span><br>
					<%--1.本页面用来修改一条排课记录的讲师和教室,也可以选择将该课程分享给其他符合条件的班次.<br>--%>
					1.本页面用来查看一条排课记录的讲师、教室和班次.<br>
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">
                <div id="updates" class="subcontent">
        		<!-- 主要内容开始 -->
				<div id="validation" class="subcontent">
            	
                    <form id="form1" class="stdform" method="post" action="">
                    <input type="hidden" value="${courseArrange.id}" id="courseArrangeId" />
                    <input type="hidden" name="courseArrange.classroomId" id="classroomId" value="${courseArrange.classroomId }"/>
                    <input type="hidden" name="courseArrange.classroomName" id="classroomName" value="${courseArrange.classroomName}"/>	  
                    <input type="hidden" name="courseArrange.teacherId" id="teacherId" value="${courseArrange.teacherId }" />
                    <input type="hidden" name="courseArrange.teacherName" id="teacherName" value="${courseArrange.teacherName }" />                                                                    
                        <p>
                        	<label>课程名称</label>
                            <span class="field"><input type="text" name="classes.name" id="name" class="longinput" value="${courseArrange.courseName}" readonly="readonly"/></span>
                        </p>
                        <p>
                        	<label>班次名称</label>
                            <span class="field"><input type="text" name="classes.name" id="name" class="longinput" value="${courseArrange.className}" readonly="readonly"/></span>
                        </p>
                        <p>
                        	<label>上课开始时间</label>
<%--                             <span class="field"><input type="text" name="classes.name" id="name" class="longinput" value='<fmt:formatDate type="both" value="${classes.startTime}" pattern="yyyy-MM-dd HH:mm"/>'/></span> --%>
                        	<span class="field"><input type="text" name="classes.name" id="name" class="longinput" value="${courseArrange.startTimeForJs}" readonly="readonly"/></span>
                        </p>
                        <p>
                        	<label>上课结束时间</label>
<%--                             <span class="field"><input type="text" name="classes.name" id="name" class="longinput" value='<fmt:formatDate type="both" value="${classes.startTime}" pattern="yyyy-MM-dd HH:mm"/>'/></span> --%>
                        	<span class="field"><input type="text" name="classes.name" id="name" class="longinput" value="${courseArrange.endTimeForJs}" readonly="readonly"/></span>
                        </p>
                        <%-- <p>
                        	<label>讲师</label>
                            <span class="field"><input type="text" name="classes.name" id="name" class="longinput" value="${courseArrange.teacherName}" readonly="readonly"/></span>
                        </p> --%>
                        <p>
                        	<label>讲师</label>
                            <span class="field">
                            <span id="teacherspan">${courseArrange.teacherName}</span>
                            <%--<a href="javascript:selectTeacher()" class="stdbtn btn_orange">选择讲师</a>--%>
                            </span>
                        </p>
                        <p>
                        	<label>教室</label>
                            <span class="field">
                            <span id="classroomspan">${courseArrange.classroomName}</span>
                            <%--<a href="javascript:selectClassroom()" class="stdbtn btn_orange">选择教室</a>--%>
                            </span>
                        </p>
                         <%--<p>
                        	<label>其他班次</label>
                            <span class="field">
                            <c:if test="${classList!=null&&classList.size()>0 }">
                            	<c:forEach items="${classList}" var="classes">
                            		<input type="checkbox" name="classId" value="${classes.id}" <c:if test="${classes.hasShareCourse==1}">checked="checked"</c:if> />${classes.classNumber}
                            	</c:forEach>
                            </c:if>
                            </span>
                        </p>--%>
                        <br />
                        
                        
                    </form>
					<p class="stdformbutton">
                        	<%--<button class="radius2" onclick="updateCourseArrange()" id="submitButton" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">提交</button>--%>
                    		<button class="radius2" onclick="javascript :history.back(-1);" id="submitButton" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">返回</button>
                    </p>
            </div>
            <!-- 主要内容结束 -->
				<div class="clear"></div>
            </div><!-- #updates -->
	    </div>
	    </div>
	</body>
</html>