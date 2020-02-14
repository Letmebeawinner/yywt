<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>新建调研报告类型</title>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
<%-- 		<script type="text/javascript" src="${ctx}/static/js/custom/elements.js"></script> --%>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/datetimepicker/jquery.datetimepicker.css"/>
		<script src="${ctx}/static/datetimepicker/jquery.js"></script>
		<script src="${ctx}/static/datetimepicker/build/jquery.datetimepicker.full.js"></script>
        <script type="text/javascript">
       /*  (function($){
        	datepicker("#datepicker");
        })(jQuery); */        
        jQuery(function(){
            /* laydate.skin('molv');
            laydate({
                elem: '#startTime',
                format:'YYYY-MM-DD hh:mm:ss'
            });
            laydate({
                elem: '#endTime',
                format:'YYYY-MM-DD hh:mm:ss'
            }); */
        	jQuery('.some_class').datetimepicker();
        });
        
        function addResearch(){
        	/* var title=jQuery("#title").val();
         	var startTime=jQuery("#startTime").val();
         	var endTime=jQuery("#endTime").val();
        	var note=jQuery("#note").val(); */
        	var params=jQuery("#form1").serialize();
        	jQuery.ajax({
	    		url:'${ctx}/admin/jiaowu/research/createResearch.json',
	    		/* data:{
	    			"research.title":title,
 	    			"research.startTime":startTime,
 	    			"research.endTime":endTime,
	    			"research.note":note}, */
	    		data:params,
	    		type:'post',
	    		dataType:'json',		
	    		success:function (result){
	    			if(result.code=="0"){
	    				window.location.href="${ctx}/admin/jiaowu/research/researchList.json";
	    			}else{
	    				jAlert(result.message,'提示',function() {});
	    			}		    			
	    		} ,
	    		error:function(e){
	    			jAlert('添加失败','提示',function() {});
	    		}
	    	});
        }
    </script>

        </script>
	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">新建调研报告类型</h1>
                <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于新建调研报告类型<br />
                    2.按要求填写相关信息,点击"提交"按钮,新建调研报告类型.<br />
                    3.带有红色<span style="color: red">*</span>标记的内容为必填部分。
				</span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">
                <div id="updates" class="subcontent">
        		<!-- 主要内容开始 -->
				<div id="validation" class="subcontent">
            	
                    <form id="form1" class="stdform" method="post" action="">
                        <p>
                        	<label><em style="color: red;">*</em>名称</label>
                            <span class="field"><input type="text" name="research.title" id="title" class="longinput" /></span>
                        </p>
                        <p>
                        	<label><em style="color: red;">*</em>开始时间</label>
                            <span class="field">
                            	<input type="text" class="some_class" value="" id="some_class_1" name="research.startTime"/>
<!--                             	<input id="startTime" type="text" class="width100 laydate-icon" style="width: 120px;" name="research.startTime" readonly="readonly"/>  -->
                            </span>
                        </p> 
                        <p>
                        	<label><em style="color: red;">*</em>结束时间</label>
                            <span class="field">
                            	<input type="text" class="some_class" value="" id="some_class_2" name="research.endTime"/>
<!--                             	<input id="endTime" type="text" class="width100 laydate-icon" style="width: 120px;" name="research.endTime" readonly="readonly"/>  -->
                            </span>
                        </p>
                        <p>
                        	<label>备注</label>
                            <span class="field"><textarea cols="80" rows="5" name="research.note" class="mediuminput" id="note"></textarea></span> 
                        </p>
                        <br />
                        
                        </form>
                        <p class="stdformbutton">
                        	<button class="radius2" onclick="addResearch()" id="submitButton" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">
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