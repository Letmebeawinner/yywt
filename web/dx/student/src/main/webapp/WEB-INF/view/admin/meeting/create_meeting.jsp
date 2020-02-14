<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>新建会议</title>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
<%-- 		<script type="text/javascript" src="${ctx}/static/js/custom/elements.js"></script> --%>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/datetimepicker/jquery.datetimepicker.css"/>
		<script src="${ctx}/static/datetimepicker/jquery.js"></script>
		<script src="${ctx}/static/datetimepicker/build/jquery.datetimepicker.full.js"></script>
        <script type="text/javascript">
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
        
        function addMeeting(){
//         	 var regTime = new RegExp("^(\d{4})/(0\d{1}|1[0-2])/(0\d{1}|[12]\d{1}|3[01])(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})$");
//          	var regTime =/^(\d{4})/(0\d{1}|1[0-2])/(0\d{1}|[12]\d{1}|3[01])(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})$/;
			/* var startTime=jQuery("#some_class_1").val();
        	 if(!regTime.test(startTime)){
        		jAlert('开始时间格式错误','提示',function() {});
        		return;
        	}
        	var endTime=jQuery("#some_class_2").val();
        	if(!regTime.test(endTime)){
        		jAlert('结束时间格式错误','提示',function() {});
        		return;
        	}   */
        	var params=jQuery("#form1").serialize();
        	jQuery.ajax({
	    		url:'${ctx}/admin/jiaowu/meeting/createMeeting.json',
	    		data:params,
	    		type:'post',
	    		dataType:'json',		
	    		success:function (result){
	    			if(result.code=="0"){
	    				window.location.href="${ctx}/admin/jiaowu/meeting/meetingList.json";
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

	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">新建会议</h1>
                <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于新建会议<br />
                    2.按要求填写相关信息,点击"提交"按钮,新建会议.<br />
                    3.带有红色<span style="color: red">*</span>标记的内容为必填部分。
				</span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">
                <div id="updates" class="subcontent">
        		<!-- 主要内容开始 -->
				<div id="validation" class="subcontent">
            	
                    <form id="form1" class="stdform" method="post" action="">
                    	
                        
                        <p>
                        	<label><em style="color: red;">*</em>会议名称</label>
                            <span class="field"><input type="text" name="meeting.name" id="name" class="longinput" /></span>
                        </p>
                        
                        
                        <p>
                        	<label><em style="color: red;">*</em>开始时间</label>
                            <span class="field">
                            		<input type="text" class="some_class" value="" id="some_class_1" name="meeting.startTime"/>
<!--                             	<input id="startTime" type="text" class="width100 laydate-icon" style="width: 120px;" name="meeting.startTime" readonly="readonly"/>  -->
                            </span>
                        </p> 
                        <p>
                        	<label><em style="color: red;">*</em>结束时间</label>
                            <span class="field">
                            		<input type="text" class="some_class" value="" id="some_class_2" name="meeting.endTime"/>
<!--                             	<input id="endTime" type="text" class="width100 laydate-icon" style="width: 120px;" name="meeting.endTime" readonly="readonly"/>  -->
                            </span>
                        </p>
                        <p>
                        	<label>备注</label>
                            <span class="field"><textarea cols="80" rows="5" name="meeting.note" class="mediuminput" id="note"></textarea></span> 
                        </p>
                        <br />
                        
                        </form>
                        <p class="stdformbutton">
                        	<button class="radius2" onclick="addMeeting()" id="submitButton" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">
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