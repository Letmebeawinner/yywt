<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>新建调研报告</title>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
<%-- 		<script type="text/javascript" src="${ctx}/static/js/custom/elements.js"></script> --%>
		<link type="text/css" rel="stylesheet" href='${ctx }/static/uploadify/uploadify.css'/>
		<script type="text/javascript" src="${ctx }/static/uploadify/jquery.uploadify.min.js"></script>
		<script type="text/javascript" src="${ctx }/static/uploadify/upload-file.js"></script>
        <style type="text/css">
            .uploadify {
                display: inline-block;
                margin-left: 15px;
            }
        </style>
        <script type="text/javascript">
       /*  (function($){
        	datepicker("#datepicker");
        })(jQuery); */        
        jQuery(function(){
        	jQuery("#type").change(function(){
       		 var selectedType = jQuery(this).children('option:selected').val();
       		 if(selectedType=="teacher"){
       			 jQuery("#teacherp").show();
       			 jQuery("#studentp").hide();
       			 jQuery("#peopleId").val("");
       			 jQuery("#teacherspan").html("");
       			 jQuery("#studentspan").html("");
       		 }else{
       			 jQuery("#teacherp").hide();
       			 jQuery("#studentp").show();
       			 jQuery("#peopleId").val("");
       			 jQuery("#teacherspan").html("");
      			 jQuery("#studentspan").html("");
       		 }
       	 });
            laydate.skin('molv');
            uploadFile("uploadFile", true, "myFile", imagePath, callbackFile);
        });

       function callbackFile(data){
           data=data.substr(2);
           data=data.substr(0,data.length-2);
           jQuery("#fileUrl").val(data);
           jQuery("#file").html('已上传：' + jQuery(".fileName").html());
       }


       function addResearchReport(){
        	/* var researchId=jQuery("#researchId").val();
        	var researchName=jQuery("#researchspan").html();        	
        	var content=jQuery("#content").val();         	
        	var note=jQuery("#note").val(); */
        	var params=jQuery("#form1").serialize();
        	jQuery.ajax({
	    		url:'${ctx}/admin/jiaowu/researchReport/createResearchReport.json',
	    		/* data:{"researchReport.researchId":researchId,
	    			"researchReport.researchName":researchName,	    			
 	    			"researchReport.content":content,
	    			"researchReport.note":note}, */
	    		data:params,
	    		type:'post',
	    		dataType:'json',		
	    		success:function (result){
	    			if(result.code=="0"){
	    				window.location.href="${ctx}/admin/jiaowu/researchReport/researchReportList.json";
	    			}else{
	    				jAlert(result.message,'提示',function() {});
	    			}		    			
	    		} ,
	    		error:function(e){
	    			jAlert('添加失败','提示',function() {});
	    		}
	    	});
        }
        
        /* function selectTeacher(){
        	window.open('${ctx}/admin/jiaowu/teachingComment/teacherListForSelect.json','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=1200,height=800');
        }
        
        function addToPeopleId(teacherIdAndName){
			jQuery("#peopleId").val(teacherIdAndName[0]);
        	jQuery("#teacherspan").val(teacherIdAndName[1]);
		} */
        
        function selectResearch(){
        	window.open('${ctx}/jiaowu/research/researchListForSelect.json','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=1200,height=800');
        }
        
        function addResearchId(researchIdAndName){
        	jQuery("#researchId").val(researchIdAndName[0]);
        	jQuery("#researchspan").html(researchIdAndName[1]);
        	jQuery("#researchName").val(researchIdAndName[1]);
        }
        
        /* function selectStudent(){
        	window.open('${ctx}/jiaowu/user/userListForSelect.json','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=1200,height=800');
        }
        
        function addStudentId(studentIdAndName){
        	jQuery("#peopleId").val(studentIdAndName[0]);
        	jQuery("#studentspan").html(studentIdAndName[1]);
        } */
    </script> 

	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">新建调研报告</h1>
                <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于新建调研报告<br />
                    2.按要求填写相关信息,点击"提交"按钮,新建调研报告.<br />
					3.带有红色<span style="color: red">*</span>标记的内容为必填部分。
				</span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">
                <div id="updates" class="subcontent">
        		<!-- 主要内容开始 -->
				<div id="validation" class="subcontent">
            	
                    <form id="form1" class="stdform" method="post" action="">
                    <input type="hidden" name="researchReport.researchId" id="researchId" />
                    <input type="hidden" name="researchReport.researchName" id="researchName" />
<!--                     <input type="hidden" name="researchReport.peopleId" id="peopleId" /> -->
                    	<p>
                        	<label><em style="color: red;">*</em>调研报告类型</label>
                            <span class="field">
                            <span id="researchspan"></span>
                            <a href="javascript:selectResearch()" class="stdbtn btn_orange">选择调研报告类型</a>
                            </span>
                        </p>
                        <!-- <p>
                        	<label>类别</label>
                            <span class="field">
                            <select name="researchReport.type" id="type">
                            	<option value="teacher">讲师</option>
                                <option value="student">学员</option>                                
                            </select>
                            </span>
                        </p>
                        <p id="teacherp" style="display: none;">
                        	<label>讲师</label>
                            <span class="field">
                            <span id="teacherspan"></span>
                            <a href="javascript:selectTeacher()" class="stdbtn btn_orange">选择讲师</a>
                            </span>
                        </p> -->                        
                        
                        <p>
                        	<label><em style="color: red;">*</em>内容</label>
                            <span class="field"><textarea cols="80" rows="5" name="researchReport.content" class="mediuminput" id="content"></textarea></span> 
                        </p>
						<p>
							<label><em style="color: red;">*</em>文件地址:</label>
							<span class="field">
								<input type="hidden" name="researchReport.fileUrl" id="fileUrl"/>
								<input type="button" id="uploadFile" value="上传文件"/>
								<center><h4 id="file"></h4></center>
							</span>
						</p>
                        <p>
                        	<label>备注</label>
                            <span class="field"><textarea cols="80" rows="5" name="researchReport.note" class="mediuminput" id="note"></textarea></span> 
                        </p>
                        <br />
                        
                        </form>
                        <p class="stdformbutton">
                        	<button class="radius2" onclick="addResearchReport()" id="submitButton" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">
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