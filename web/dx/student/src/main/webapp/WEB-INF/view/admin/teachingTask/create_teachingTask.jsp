<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>新建教学任务</title>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
<%-- 		<script type="text/javascript" src="${ctx}/static/js/custom/elements.js"></script> --%>
        <script type="text/javascript">
        function addTeachingTask(){
        	var params=jQuery("#form1").serialize();
        	jQuery.ajax({
	    		url:'${ctx}/admin/jiaowu/teachingTask/doCreateTeachingTask.json',
	    			data:params,
	    		type:'post',
	    		dataType:'json',		
	    		success:function (result){
	    			if(result.code=="0"){
	    				window.location.href="${ctx}/admin/jiaowu/teachingTask/teachingTaskList.json";
	    			}else{
	    				jAlert(result.message,'提示',function() {});
	    			}		    			
	    		},
	    		error:function(e){
	    			jAlert('添加失败','提示',function() {});
	    		}
	    	});
        }
        
        function selectClass(){
        	window.open('${ctx}/jiaowu/class/classListForSelect.json','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=1200,height=800');
        }
        
        function addClass(classArray){
        	jQuery("#classspan").html(classArray[1]);
            jQuery("#classId").val(classArray[0]);
            jQuery("#className").val(classArray[1]);
        }
    </script>


	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">新建教学任务</h1>
                <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于新建教学任务<br />
                    2.按要求填写相关信息,点击"提交"按钮,新建教学任务.<br />
                    3.带有红色<span style="color: red">*</span>标记的内容为必填部分。
				</span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">
                <div id="updates" class="subcontent">
        		<!-- 主要内容开始 -->
				<div id="validation" class="subcontent">
            	
                    <form id="form1" class="stdform" method="post" action="">
                    	<input type="hidden" name="teachingTask.classId" id="classId" />
                    	<input type="hidden" name="teachingTask.className" id="className" />
                        <p>
                            <label><em style="color: red;">*</em>班次</label>
                            <span class="field">
                            	<span id="classspan"></span>
                                <a href="javascript:selectClass()" class="stdbtn btn_orange">选择班次</a>
                            </span>
                        </p>
                        <p>
                        	<label><em style="color: red;">*</em>内容</label>
                            <span class="field"><textarea cols="80" rows="5" name="teachingTask.content" class="mediuminput" id="content"></textarea></span> 
                        </p>
                        <p>
                        	<label>备注</label>
                            <span class="field"><textarea cols="80" rows="5" name="teachingTask.note" class="mediuminput" id="note"></textarea></span> 
                        </p>
                        <br />
                        
                        
                    </form>
					<p class="stdformbutton">
                        	<button class="radius2" onclick="addTeachingTask()" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">提交</button>
                        </p>
            </div>
            <!-- 主要内容结束 -->
				<div class="clear"></div>
            </div><!-- #updates -->
	    </div>
	    </div>
	</body>
</html>