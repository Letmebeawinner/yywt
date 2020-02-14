<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>新建课程</title>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
<%-- 		<script type="text/javascript" src="${ctx}/static/js/custom/elements.js"></script> --%>
        <script type="text/javascript">
       /*  (function($){
        	datepicker("#datepicker");
        })(jQuery); */        
       
        jQuery(function(){
        	/* jQuery("#teacherId").change(function(){
       		 var selectedTeacherName = jQuery(this).children('option:selected').text();
       		 jQuery("#teacherName").val(selectedTeacherName);
       	 }); */
        });
        function addCourse(){
        	/* var name=jQuery("#name").val();
        	var teacherId=jQuery("#teacherId").val();     
        	var teacherName=jQuery("#teacherName").val();
        	var note=jQuery("#note").val(); */
        	var params=jQuery("#form1").serialize();
        	jQuery.ajax({
	    		url:'${ctx}/admin/jiaowu/course/createCourse.json',
	    		/* data:{"course.name":name,
	    			"course.teacherId":teacherId,	
	    			"course.teacherName":teacherName,
	    			"course.note":note}, */
	    		data:params,
	    		type:'post',
	    		dataType:'json',		
	    		success:function (result){
	    			if(result.code=="0"){
	    				window.location.href="${ctx}/admin/jiaowu/course/courseList.json";
	    			}else{
	    				jAlert(result.message,'提示',function() {});
	    			}		    			
	    		},
	    		error:function(e){
	    			jAlert('添加失败','提示',function() {});
	    		}
	    	});
        }
        
        function selectTeacher(){
//             window.open('${ctx}/jiaowu/teacher/teacherListForSelect.json','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=1200,height=800');
        	window.open('${ctx}/jiaowu/teacher/teacherListForSelect.json','newwindow', 'toolbar=yes,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=1200,height=800');
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
                <h1 class="pagetitle">新建课程</h1>
                <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于新建课程<br />
                    2.按要求填写相关信息,点击"提交"按钮,新建课程.<br />
                    3. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
				</span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">
                <div id="updates" class="subcontent">
        		<!-- 主要内容开始 -->
				<div id="validation" class="subcontent">
            	
                    <form id="form1" class="stdform" method="post" action="">
                    	<input type="hidden" name="course.teacherId" id="teacherId" />
                    	<input type="hidden" name="course.teacherName" id="teacherName" />
                    	<p>
                        	<label><em style="color: red;">*</em>名称</label>
                            <span class="field"><input type="text" name="course.name" id="name" class="longinput" /></span>
                        </p>
                         <p>
                        	<label><em style="color: red;">*</em>课程类别</label>
                            <span class="field">
                            <select id="courseTypeId" name="course.courseTypeId">
                            	<option value="0">请选择</option>
                            	<c:if test="${courseTypeList!=null&&courseTypeList.size()>0}">
                            		<c:forEach items="${courseTypeList}" var="courseType">
                            			<option value="${courseType.id}">${courseType.name}</option>
                            		</c:forEach>
                            	</c:if>
                            </select>
                            </span>
                         </p>
                         <p>
                        	<label><em style="color: red;">*</em>学时</label>
                            <span class="field"><input type="text" name="course.hour" id="hour" class="longinput" onchange="if(/\D/.test(this.value)){jAlert('只能输入数字','提示',null);this.value='';}"/></span>
                        </p>
                    	 <p>
                            <label><em style="color: red;">*</em>讲师</label>
                            <span class="field">
                            <span id="teacherspan"></span>
                            <a href="javascript:selectTeacher()" class="stdbtn btn_orange">选择讲师</a>
                            </span>
                        </p>
                       
                        
                        <p>
                        	<label>备注</label>
                            <span class="field"><textarea cols="80" rows="5" name="course.note" class="mediuminput" id="note"></textarea></span> 
                        </p>
                        <br />
                        
                        
                    </form>
					<p class="stdformbutton">
                        	<button class="radius2" onclick="addCourse()" id="submitButton" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">提交</button>
                    </p>
            </div>
            <!-- 主要内容结束 -->
				<div class="clear"></div>
            </div><!-- #updates -->
	    </div>
	    </div>
	</body>
</html>