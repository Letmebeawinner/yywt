<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>修改调研报告类型</title>
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
        
        function updateResearch(){
        	var params=jQuery("#form1").serialize();
        	jQuery.ajax({
	    		url:'${ctx}/admin/jiaowu/research/updateResearch.json',
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
                <h1 class="pagetitle">修改调研报告类型</h1>
                <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于修改调研报告类型<br />
                    2.按要求填写相关信息,点击"提交"按钮,修改调研报告类型.<br />
                    3.带有红色<span style="color: red">*</span>标记的内容为必填部分。
				</span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">
                <div id="updates" class="subcontent">
        		<!-- 主要内容开始 -->
				<div id="validation" class="subcontent">
            	
                    <form id="form1" class="stdform" method="post" action="">
                    	<input type="hidden" name="research.id" value="${research.id}" />
                        <p>
                        	<label><em style="color: red;">*</em>名称</label>
                            <span class="field"><input type="text" name="research.title" id="title" class="longinput" value="${research.title}"/></span>
                        </p>
                        <p>
                        	<label><em style="color: red;">*</em>开始时间</label>
                            <span class="field">
                            	<input id="startTime" type="text" class="width100 laydate-icon" style="width: 120px;" name="research.startTime" readonly="readonly" value="<fmt:formatDate type='both' value='${research.startTime}' pattern='yyyy-MM-dd HH:mm'/>"/> 
                            </span>
                        </p> 
                        <p>
                        	<label><em style="color: red;">*</em>结束时间</label>
                            <span class="field">
                            	<input id="endTime" type="text" class="width100 laydate-icon" style="width: 120px;" name="research.endTime" readonly="readonly" value="<fmt:formatDate type='both' value='${research.endTime}' pattern='yyyy-MM-dd HH:mm'/>"/> 
                            </span>
                        </p>
                        <p>
                        	<label>备注</label>
                            <span class="field"><textarea cols="80" rows="5" name="research.note" class="mediuminput" id="note">${research.note }</textarea></span> 
                        </p>
                        <br />
                        
                        </form>
                        <p class="stdformbutton">
                        	<button class="radius2" onclick="updateResearch()" id="submitButton" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">
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