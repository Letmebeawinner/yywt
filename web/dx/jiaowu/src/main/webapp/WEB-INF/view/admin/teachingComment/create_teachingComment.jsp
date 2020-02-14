<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>新建教学工作评价类别</title>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
<%-- 		<script type="text/javascript" src="${ctx}/static/js/custom/elements.js"></script> --%>
        <script type="text/javascript">
       /*  (function($){
        	datepicker("#datepicker");
        })(jQuery); */        
        jQuery(function(){
            laydate.skin('molv');
            laydate({
                elem: '#startTime',
                format:'YYYY-MM-DD hh:mm:ss'
            });
            laydate({
                elem: '#endTime',
                format:'YYYY-MM-DD hh:mm:ss'
            });
        });
        
        function addTeachingComment(){
        	/* var courseId=jQuery("#courseId").val();
        	var courseName=jQuery("#courseName").val();
         	var startTime=jQuery("#startTime").val();
         	var endTime=jQuery("#endTime").val();
        	var note=jQuery("#note").val(); */
        	var params=jQuery("#form1").serialize();
        	jQuery.ajax({
	    		url:'${ctx}/admin/jiaowu/teachingComment/createTeachingComment.json',
	    		/* data:{"teachingComment.courseId":courseId,	    	
	    			"teachingComment.courseName":courseName,
 	    			"teachingComment.startTime":startTime,
 	    			"teachingComment.endTime":endTime,
	    			"teachingComment.note":note}, */
	    		data:params,
	    		type:'post',
	    		dataType:'json',		
	    		success:function (result){
	    			if(result.code=="0"){
	    				window.location.href="${ctx}/admin/jiaowu/teachingComment/teachingCommentList.json";
	    			}else{
	    				jAlert(result.message,'提示',function() {});
	    			}		    			
	    		} ,
	    		error:function(e){
	    			jAlert('添加失败','提示',function() {});
	    		}
	    	});
        }
        
        function selectCourse(){
            window.open('${ctx}/jiaowu/course/courseListForSelect.json','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=1200,height=800');
        }

        function addCourse(courseArray){
            jQuery("#coursespan").html(courseArray[1]);
            jQuery("#courseId").val(courseArray[0]);
            jQuery("#courseName").val(courseArray[1]);
        }
    </script>

	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">新建教学工作评价类别</h1>
                <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于新建教学工作评价类别<br />
                    2.按要求填写相关信息,点击"提交"按钮,新建教学工作评价类别.<br />
                    3.带有红色<span style="color: red">*</span>标记的内容为必填部分。
				</span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">
                <div id="updates" class="subcontent">
        		<!-- 主要内容开始 -->
				<div id="validation" class="subcontent">
            	
                    <form id="form1" class="stdform" method="post" action="">
                    	<input type="hidden" name="teachingProgramCourse.courseId" id="courseId" />
                    	<input type="hidden" name="teachingProgramCourse.courseName" id="courseName" />
                    	<p>
                        	<label><em style="color: red;">*</em>课程</label>
                            <span class="field">
                            <%-- <select name="teachingComment.courseId" id="courseId">
                            	<option value="">请选择</option>
                                <c:if test="${courseList!=null&&courseList.size()>0}">
                                <c:forEach items="${courseList }" var="course">
                                	<option value="${course.id }">${course.name}</option>
                                </c:forEach>
                                </c:if>
                               
                            </select> --%>
                            	<span id="coursespan">${teachingProgramCourse.courseName}</span>
                                <a href="javascript:selectCourse()" class="stdbtn btn_orange">选择课程</a>
                            </span>
                        </p>
                        
                       
                        <p>
                        	<label><em style="color: red;">*</em>评价开始时间</label>
                            <span class="field">
                            	<input id="startTime" type="text" class="width100 laydate-icon" name="teachingComment.startTime"/> 
                            </span>
                        </p> 
                        <p>
                        	<label><em style="color: red;">*</em>评价结束时间</label>
                            <span class="field">
                            	<input id="endTime" type="text" class="width100 laydate-icon" name="teachingComment.endTime"/> 
                            </span>
                        </p>
                        <p>
                        	<label>备注</label>
                            <span class="field"><textarea cols="80" rows="5" name="teachingComment.note" class="mediuminput" id="note"></textarea></span> 
                        </p>
                        <br />
                        
                        </form>
                        <p class="stdformbutton">
                        	<button class="radius2" onclick="addTeachingComment()" id="submitButton" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">
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