<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>修改班次</title>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
        <script type="text/javascript">
			jQuery(function(){
			var classTypeId= "${classes.classTypeId}";
			jQuery("#classTypeId option[value='"+classTypeId+"']").attr("selected",true);
			var teacherName= "${classes.teacherName}";
			jQuery("#teacherspan").html(teacherName);
			
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
			function updateClass(){
				/* var classId=jQuery("#classId").val();
				var classTypeId=jQuery("#classTypeId").val();
	        	var classNumber=jQuery("#classNumber").val();
	        	var name=jQuery("#name").val();
	        	var teacherId=jQuery("#teacherId").val();
	        	var teacherName=jQuery("#teacherName").val();
	        	var startTime=jQuery("#startTime").val();
	        	startTime=new Date(startTime.replace(/-/g,"/") );  
	        	var endTime=jQuery("#endTime").val();
	        	endTime=new Date(endTime.replace(/-/g,"/")); 
	        	var note=jQuery("#note").val(); */
	        	var params=jQuery("#form1").serialize();
	        	jQuery.ajax({
		    		url:'${ctx}/admin/jiaowu/class/doUpdateClass.json',
		    		/* data:{"classes.id":classId,
		    			"classes.classTypeId":classTypeId,
		    			"classes.classNumber":classNumber,
		    			"classes.name":name,
		    			"classes.teacherId":teacherId,
		    			"classes.teacherName":teacherName,
		    			 "classes.startTime":startTime,
		    			"classes.endTime":endTime,
		    			"classes.note":note}, */
		    		data:params,
		    		type:'post',
		    		dataType:'json',		
		    		success:function (result){
		    			if(result.code=="0"){
		    				window.location.href="${ctx}/admin/jiaowu/class/classList.json";
		    			}else{
		    				jAlert(result.message,'提示',function() {});
		    			}		    			
		    		},
		    		error:function(e){
		    			jAlert('更新失败','提示',function() {});
		    		}
		    	});
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
                <h1 class="pagetitle">修改班次</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面用于修改班次.<br />
					2.可修改相关信息,点击"提交"按钮,保存修改信息.<br />
					3.带有红色<span style="color: red">*</span>标记的内容为必填部分。
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">
                <div id="updates" class="subcontent">
        		<!-- 主要内容开始 -->
				<div id="validation" class="subcontent">
            	
                    <form id="form1" class="stdform" method="post" action="">
                    <input type="hidden" value="${classes.id}" id="classId" name="classes.id"/>
                    <input type="hidden" value="${classes.classTypeId}" name="classes.classTypeId"/>
                    <input type="hidden" value="${classes.teacherId}" id="teacherId" name="classes.teacherId"/>
                    <input type="hidden" value="${classes.teacherName}" id="teacherName" name="classes.teacherName"/>
                    	<%-- <p>
                        	<label><em style="color: red;">*</em>班型</label>
                            <span class="field">
                            <select name="classes.classTypeId" id="classTypeId">
                            	<option value="">请选择</option>
                                <c:if test="${classTypeList!=null&&classTypeList.size()>0}">
                                <c:forEach items="${classTypeList }" var="classType">
                                    <option value="${classType.id }">${classType.name}</option>
                                </c:forEach>
                                </c:if>
                               
                            </select>
                             </span>
                        </p> --%>
                        
                        <%-- <p>
                        	<label><em style="color: red;">*</em>班次编号</label>
                            <span class="field"><input type="text" name="classes.classNumber" id="classNumber" class="longinput" value="${classes.classNumber}"/></span>
                        </p> --%>
                        
                        <p>
                        	<label><em style="color: red;">*</em>名称</label>
                            <span class="field"><input type="text" name="classes.name" id="name" class="longinput" value="${classes.name}"/></span>
                        </p>
                        
                         <p>
                            <label><em style="color: red;">*</em>讲师</label>
                            <span class="field">
                            <span id="teacherspan">${classes.teacherName}</span>
                            <a href="javascript:void(0)" onclick="selectTeacher()" class="stdbtn btn_orange">选择讲师</a>
                            </span>
                        </p>
                        <p>
                        	<label><em style="color: red;">*</em>开班时间</label>
                            <span class="field">
                            	<input style="width: 200px;" id="startTime" type="text" class="width100" name="classes.startTime" value="<fmt:formatDate type='both' value='${classes.startTime}' pattern='yyyy-MM-dd HH:mm'/>"/> 
                            </span>
                        </p> 
                        <p>
                        	<label><em style="color: red;">*</em>结束时间</label>
                            <span class="field">
                            	<input style="width: 200px;" id="endTime" type="text" class="width100" name="classes.endTime" value="<fmt:formatDate type='both' value='${classes.endTime}' pattern='yyyy-MM-dd HH:mm'/>"/> 
                            </span>
                        </p>
						<p>
							<label><em style="color: red;">*</em>最大人数</label>
							<span class="field"><input type="text" name="classes.maxNum" id="maxNum" class="longinput" onchange="if(/\D/.test(this.value)){alert('只能输入数字','提示',null);this.value='';}" value="${classes.maxNum}"/></span>
						</p>
                        <p>
                        	<label>备注</label>
                            <span class="field"><textarea cols="80" rows="5" name="classes.note" class="mediuminput" id="note">${classes.note}</textarea></span> 
                        </p>
                        <br />
                        
                        
                    </form>
					<p class="stdformbutton">
                        	<button class="radius2" onclick="updateClass()" id="submitButton" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">提交</button>
                        </p>
            </div>
            <!-- 主要内容结束 -->
				<div class="clear"></div>
            </div><!-- #updates -->
	    </div>
	    </div>
	</body>
</html>