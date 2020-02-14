<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>新建心得</title>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
		<link type="text/css" rel="stylesheet" href='${ctx }/static/uploadify/uploadify.css'/>
		<script type="text/javascript" src="${ctx }/static/uploadify/jquery.uploadify.min.js"></script>
		<script type="text/javascript" src="${ctx }/static/uploadify/upload-file.js"></script>
<%-- 		<script type="text/javascript" src="${ctx}/static/js/custom/elements.js"></script> --%>
        <script type="text/javascript">
       /*  (function($){
        	datepicker("#datepicker");
        })(jQuery); */        
        jQuery(function(){
        	jQuery("#type").change(function(){
       		 var selectedType = jQuery(this).children('option:selected').val();
       		 if(selectedType=="会议心得"){
       			jQuery("#meetingp").show();
       		 }else{
       			jQuery("#meetingp").hide();
       		 }
       	 });
			uploadFile("uploadFile",false,"myFile",'http://10.100.101.1:6694',callbackFile);
        });

	   function callbackFile(data){
		   data=data.substr(2);
		   data=data.substr(0,data.length-2);
		   jQuery("#fileUrl").val(data);

	   }
	   function upFile(){
		   jQuery("#file").html( '已上传：'+jQuery(".fileName").html());
		   jQuery("#uploadFile").uploadify('upload');
	   }
        
        function addXinDe(){
        	/* var meetingId=jQuery("#meetingId").val();
        	var meetingName=jQuery("#meetingName").val();
        	var type=jQuery("#type").val();
        	var content=jQuery("#content").val();
        	var note=jQuery("#note").val(); */
        	var params=jQuery("#form1").serialize();
        	jQuery.ajax({
	    		url:'${ctx}/admin/jiaowu/xinDe/createXinDe.json',
	    		/* data:{"xinDe.meetingId":meetingId,
	    			"xinDe.meetingName":meetingName,
	    			"xinDe.type":type,
	    			"xinDe.content":content,
	    			"xinDe.note":note}, */
	    		data:params,
	    		type:'post',
	    		dataType:'json',		
	    		success:function (result){
	    			if(result.code=="0"){
                        jAlert('提交成功','提示',function() {});
	    			}else{
	    				jAlert(result.message,'提示',function() {});
	    			}		    			
	    		} ,
	    		error:function(e){
	    			jAlert('添加失败','提示',function() {});
	    		}
	    	});
        }
        function selectMeeting(){
        	window.open('${ctx}/jiaowu/meeting/meetingListForSelect.json','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=1200,height=800');
        }
        
        function addMeetingIdAndName(meetingIdAndName){
        	jQuery("#meetingId").val(meetingIdAndName.split("-")[0]);
        	jQuery("#meetingspan").html(meetingIdAndName.split("-")[1]);
        	jQuery("#meetingName").val(meetingIdAndName.split("-")[1]);
        }
    </script>

	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">新建心得</h1>
                <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于新建心得<br />
                    2.按要求填写相关信息,点击"提交"按钮,新建心得.<br />
                    3.带有红色<span style="color: red">*</span>标记的内容为必填部分。
				</span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">
                <div id="updates" class="subcontent">
        		<!-- 主要内容开始 -->
				<div id="validation" class="subcontent">
            	
                    <form id="form1" class="stdform" method="post" action="">
                    <input type="hidden" id="meetingId" name="xinDe.meetingId" />
                    <input type="hidden" id="meetingName" name="xinDe.meetingName" />
                        <p>
                            <label><em style="color: red;">*</em>标题:</label>
                            <span class="field">
							    <input type="text" name="xinDe.title" id="title" />
                            </span>
                        </p>
                    	<p>
                        	<label><em style="color: red;">*</em>类型</label>
                            <span class="field">
                            <select name="xinDe.type" id="type">
                               	<option value="培训心得">培训心得</option>
                                <option value="会议心得">会议心得</option>
								<option value="培训总结">培训总结</option>
                            </select>
                            </span>
                        </p>
                        
                        <p id="meetingp" style="display: none;">
                        	<label><em style="color: red;">*</em>会议</label>
                            <span class="field">
                            <span id="meetingspan"></span>
                            <a href="javascript:selectMeeting()" class="stdbtn btn_orange">选择会议</a>
                            </span>
                        </p>
                        
                        <%--<p>
                        	<label><em style="color: red;">*</em>内容</label>
                            <span class="field"><textarea cols="80" rows="5" name="xinDe.content" class="mediuminput" id="content"></textarea></span> 
                        </p>--%>
						<p>
							<label><em style="color: red;">*</em>文件地址:</label>
					 		<span class="field">
							 <input type="text" name="xinDe.fileUrl"   id="fileUrl" />
						 	<input type="button" id="uploadFile" value="上传文件"/>
						 	<a onclick="upFile()" href="javascript:void(0)">上传</a>
						 	<center><h4  id="file"></h4></center>
							</span>
						</p>
                        <p>
                        	<label>备注</label>
                            <span class="field"><textarea cols="80" rows="5" name="xinDe.note" class="mediuminput" id="note"></textarea></span> 
                        </p>
                        <br />
                        
                        </form>
                        <p class="stdformbutton">
                        	<button class="radius2" onclick="addXinDe()" id="submitButton" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">
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