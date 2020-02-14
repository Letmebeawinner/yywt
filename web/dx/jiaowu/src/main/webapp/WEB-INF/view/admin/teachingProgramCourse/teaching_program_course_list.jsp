<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>教学计划课程列表</title>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
        <script type="text/javascript">
        jQuery(function(){
			var teacherId= "${teachingProgramCourse.teacherId}";
			var teacherName="${teachingProgramCourse.teacherName}";
			var classId= "${teachingProgramCourse.classId}";
			var className="${teachingProgramCourse.className}";
			var courseId= "${teachingProgramCourse.courseId}";
			var courseName="${teachingProgramCourse.courseName}";
			jQuery("#teacherId").val(teacherId);
			jQuery("#teacherName").val(teacherName);
			jQuery("#teacherspan").html(teacherName);
			jQuery("#classId").val(classId);
			jQuery("#className").val(className);
			jQuery("#classspan").html(className);
			jQuery("#courseId").val(courseId);
			jQuery("#courseName").val(courseName);
			jQuery("#coursespan").html(courseName);
        });
        function searchForm(){
            jQuery("#searchForm").submit();
        }
        function emptyForm(){
            jQuery("input:text").val('');
            jQuery("select").val(0);
            jQuery("#teacherId").val(0);
			jQuery("#teacherName").val("");
			jQuery("#teacherspan").html("");
			jQuery("#classId").val(0);
			jQuery("#className").val("");
			jQuery("#classspan").html("");
			jQuery("#courseId").val(0);
			jQuery("#courseName").val("");
			jQuery("#coursespan").html("");
        }
        function deleteTeachingProgramCourse(id){
        	jConfirm('您确定要删除吗?','确认',function(r){
        	    if(r){
        	    	jQuery.ajax({
        	    		url:'${ctx}/admin/jiaowu/teach/deleteTeachingProgramCourse.json?id='+id,
        	    		type:'post',
        	    		dataType:'json',		    		
        	    		success:function (result){
        	    			if(result.code=="0"){
        	    				window.location.reload();
        	    			}else{
        	    				jAlert(result.message,'提示',function() {});
        	    			}		    			
        	    		},
        	    		error:function(e){
        	    			jAlert('删除失败','提示',function() {});
        	    		}
        	    	});
        	    }
        	});
		}
        
        function selectCourse(){
            window.open('${ctx}/jiaowu/course/courseListForSelect.json','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=800,height=600');
        }

        function addCourse(courseArray){
            jQuery("#coursespan").html(courseArray[1]);
            jQuery("#courseId").val(courseArray[0]);
            jQuery("#courseName").val(courseArray[1]);
        }
        
        function selectClass(){
        	window.open('${ctx}/jiaowu/class/classListForSelect.json','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=800,height=600');
        }
        
        function addClass(classArray){
        	jQuery("#classspan").html(classArray[1]);
            jQuery("#classId").val(classArray[0]);
            jQuery("#className").val(classArray[1]);
        }
        
        function selectTeacher(){
            window.open('${ctx}/jiaowu/teacher/teacherListForSelect.json','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=800,height=600');
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
                <h1 class="pagetitle">教学计划课程列表</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示教学计划课程的列表.<br />
					2.可通过课程、班级查询对应的教学计划课程.<br />
					<%--3.如果某教学计划课程所属的教学计划未确认,可点击"修改"按钮,修改教学计划课程.<br />--%>
					3.如果某教学计划课程所属的教学计划未确认,可点击"删除"按钮,删除教学计划课程.<br />
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">

                <!-- 搜索条件，开始 -->
                <div class="overviewhead clearfix mb10">
                    <div class="fl mt5">
                        <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/teach/teachingProgramCourseList.json" method="get">
                    		<input type="hidden" name="courseId" id="courseId" />
                    		<input type="hidden" name="courseName" id="courseName" />
                    		<input type="hidden" name="classId" id="classId" />
                    		<input type="hidden" name="className" id="className" />
                    		<input type="hidden" name="teacherId" id="teacherId" />
                    		<input type="hidden" name="teacherName" id="teacherName" />
                            <div class="tableoptions disIb mb10">
                                <span class="vam">专题 &nbsp;</span>
                                <label class="vam">                                
                                <span id="coursespan">${courseArrange.courseName}</span>
                                <a href="javascript:void(0)" onclick="selectCourse()" class="stdbtn btn_orange">选择专题</a>
                                </label>
                            </div>
                           
                            <div class="disIb ml20 mb10">
                               <span class="vam">班级 &nbsp;</span>
                               <label class="vam">
                               	   <span id="classspan">${courseArrange.className}</span>
                                   <a href="javascript:void(0)" onclick="selectClass()" class="stdbtn btn_orange">选择班次</a>
                               </label>
                           </div>

                           <%--<div class="disIb ml20 mb10">
                               <span class="vam">讲师 &nbsp;</span>
                               <label class="vam">
	                                <span id="teacherspan">${teachingProgramCourse.teacherName}</span>
                                	<a href="javascript:void(0)" onclick="selectTeacher()" class="stdbtn btn_orange">选择讲师</a>
                               </label>
                           </div>--%>
                        </form>
                        <div class="disIb ml20 mb10">
                            <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                            <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                            <%--<a href="${ctx}/admin/jiaowu/teach/toBatchImport.json" class="stdbtn btn_orange">批量导入</a>--%>
                            <%--<a href="${ctx}/admin/jiaowu/teach/createTeachingProgramCourse.json" class="stdbtn btn_orange">新建</a>--%>
                        </div>
                    </div>
                </div>
                <!-- 搜索条件，结束 -->

                <!-- 数据显示列表，开始 -->
                <div class="pr">
                    <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                        <colgroup>
                            <col class="con0"/>
                            <col class="con1" />
                            <col class="con0"/>
                            <col class="con1" />
                            <col class="con0"/>
                            <col class="con1" />
                        </colgroup>
                        <thead>
                            <tr>
                                <th class="head0 center">序号</th>
                                <th class="head0 center">专题</th>
                                <th class="head1 center">班次</th>
                                <th class="head0 center">教师</th>
                                <th class="head0 center">教室</th>
<!--                                 <th class="head1 center">状态</th> -->
                                <th class="head0 center">开始时间</th>
                                <th class="head0 center">结束时间</th>
                                <th class="head1 center">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<c:if test="${courseArrangeList!=null&&courseArrangeList.size()>0}">
                            <c:forEach items="${courseArrangeList}" var="courseArrange" varStatus="index">
                            <tr>
                                <td>${index.index+1}</td>
                                <td>${courseArrange.courseName}</td>
                                <td>${courseArrange.className}</td>
                                <td>${courseArrange.teacherName}</td>
                                <td>${courseArrange.classroomName}</td>
<%--                                 <td>${teachingProgramCourse.status}</td> --%>
                                <td><fmt:formatDate type="both" value="${courseArrange.startTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td><fmt:formatDate type="both" value="${courseArrange.endTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td class="center">
                                	<%--<c:if test="${teachingProgramCourse.confirmTeachingProgram==0}">
                                    <a href="${ctx}/admin/jiaowu/teach/updateTeachingProgramCourse.json?id=${teachingProgramCourse.id}" class="stdbtn" title="修改">修改</a>
                                    <a href="javascript:void(0)" onclick="deleteTeachingProgramCourse(${courseArrange.id})" class="stdbtn" title="删除">删除</a>
                                	</c:if>--%>
                                        <a href="${ctx}/admin/jiaowu/courseArrange/toUpdateOneCourseArrange.json?id=${courseArrange.id}&type=1" class="stdbtn" title="修改">修改</a>
                                        <a href="javascript:void(0)" onclick="deleteTeachingProgramCourse(${courseArrange.id})" class="stdbtn" title="删除">删除</a>
                                </td>
                            </tr>
                            </c:forEach>
                            </c:if>
                        </tbody>
                    </table>
                    <!-- 分页，开始 -->
                    <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
                    <!-- 分页，结束 -->
                </div>
                <!-- 数据显示列表，结束 -->
	    </div>
	    </div>
	</body>
</html>