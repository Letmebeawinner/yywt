<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>查询调研</title>
        <script type="text/javascript">
			jQuery(function(){
			var classTypeId= "${classes.classTypeId}";
			jQuery("#classTypeId option[value='"+classTypeId+"']").attr("selected",true);
			var teacherId= "${classes.teacherId}";
			jQuery("#teacherId option[value='"+teacherId+"']").attr("selected",true);
           
        });
			
        </script>
	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">查询调研</h1>
                <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于查询某调研的具体信息<br />
				</span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">
                <div id="updates" class="subcontent">
        		<!-- 主要内容开始 -->
				<div id="validation" class="subcontent">
            	
                    <form id="form1" class="stdform" method="post" action="">
                    	
                        
                        <p>
                        	<label>标题</label>
                            <span class="field"><input type="text" name="classes.classNumber" id="classNumber" class="longinput" value="${research.title}" readonly="readonly"/></span>
                        </p>
                        
                        <p>
                        	<label>班级</label>
                            <span class="field">
                                
                            </span>
                        </p>
                        <p>
                        	<label>开始时间</label>
                            <span class="field">
                            	<input style="width: 200px;" readonly="readonly" id="startTime" type="text" class="width100" name="classes.startTime" value="<fmt:formatDate type='both' value='${research.createTime}' pattern='yyyy-MM-dd HH:mm'/>"/> 
                            </span>
                        </p> 
                        <p>
                        	<label>内容</label>
                            <span class="field"><textarea cols="80" rows="5" name="classes.note" class="mediuminput" id="note" readonly="readonly">${research.content}</textarea></span> 
                        </p>
                        <p>
                        	<label>备注</label>
                            <span class="field"><textarea cols="80" rows="5" name="classes.note" class="mediuminput" id="note" readonly="readonly">${research.note}</textarea></span> 
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