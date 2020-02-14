<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>查询教学任务</title>
<%-- 		<script type="text/javascript" src="${ctx}/static/admin/js/classes.js"></script> --%>
<%-- 		<script type="text/javascript" src="${ctx}/static/admin/js/jquery-1.7.min.js"></script> --%>
        <script type="text/javascript">
//         (function($){
        	/* var classTypeId= "${classes.classTypeId}";
        	alert(classTypeId);
       	    jQuery("#classTypeId option[value='"+classTypeId+"']").attr("selected",true);
       	    jQuery("#classTypeId").val(classTypeId);
       	 	var teacherId= "${classes.teacherId}";
    	    jQuery("#teacherId option[value='"+teacherId+"']").attr("selected",true); */
    	   
//         })(jQuery);
			
       
			function goBack(){
				window.location.href="${ctx}/admin/jiaowu/teachingTask/teachingTaskList.json";
			}
        </script>
	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">查询教学任务</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面用于展示某教学任务的具体内容.<br />
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">
                <div id="updates" class="subcontent">
        		<!-- 主要内容开始 -->
				<div id="validation" class="subcontent">
            	
                    <form id="form1" class="stdform" method="post" action="">
                    <input type="hidden" value="${teachingTask.id}" id="teachingTaskId" />
                    	<p>
                        	<label>班型</label>
                            <span class="field">
                            <input type="text" name="teachingTask.content" id="classNumber" class="longinput" value="${teachingTask.classTypeName}" readonly="readonly"/>
                            </span> 
                        </p>
                    
                    	<p>
                        	<label>班次</label>
                            <span class="field">
                            <input type="text" name="classes.classNumber" id="classNumber" class="longinput" value="${teachingTask.className}" readonly="readonly"/>
                            </span> 
                        </p>
                        
                        <p>
                        	<label>内容</label>
                            <span class="field"><textarea cols="80" rows="5" name="teachingTask.content" class="mediuminput" id="content" readonly="readonly">${teachingTask.content}</textarea></span> 
                        </p>
                        <p>
                        	<label>备注</label>
                            <span class="field"><textarea cols="80" rows="5" name="teachingTask.note" class="mediuminput" id="note" readonly="readonly">${teachingTask.note}</textarea></span> 
                        </p>
                        <br />
                        
                        
                    </form>
                    	<p class="stdformbutton">
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