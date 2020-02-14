<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>修改班型</title>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
        <script type="text/javascript">
			
			function updateClassType(){
	        	var params=jQuery("#form1").serialize();
	        	jQuery.ajax({
		    		url:'${ctx}/admin/jiaowu/classType/updateClassType.json',
		    		data:params,
		    		type:'post',
		    		dataType:'json',		
		    		success:function (result){
		    			if(result.code=="0"){
		    				window.location.href="${ctx}/admin/jiaowu/classType/classTypeList.json";
		    			}else{
		    				jAlert(result.message,'提示',function() {});
		    			}		    			
		    		},
		    		error:function(e){
		    			jAlert('更新失败','提示',function() {});
		    		}
		    	});
			}
        </script>
	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">修改班型</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面用于修改班型.<br />
					2.可修改相关信息,点击"提交"按钮,保存修改信息.<br />
					3.带有红色<span style="color: red">*</span>标记的内容为必填部分。
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">
                <div id="updates" class="subcontent">
        		<!-- 主要内容开始 -->
				<div id="validation" class="subcontent">
            	
                    <form id="form1" class="stdform" method="post" action="">
                    <input type="hidden" value="${classType.id}" id="classTypeId" name="classType.id"/>
                        <p>
                        	<label><em style="color: red;">*</em>名称</label>
                            <span class="field"><input type="text" name="classType.name" id="classTypeName" class="longinput" value="${classType.name}"/></span>
                        </p>
                        
                        <p>
                        	<label>描述</label>
                            <span class="field"><textarea cols="80" rows="5" name="classType.description" class="mediuminput" id="description">${classType.description}</textarea></span> 
                        </p>
                        
                        <p>
                        	<label>备注</label>
                            <span class="field"><textarea cols="80" rows="5" name="classType.note" class="mediuminput" id="note">${classType.note}</textarea></span> 
                        </p>
                        <br />
                        
                        
                    </form>
					<p class="stdformbutton">
                        	<button class="radius2" onclick="updateClassType()" id="submitButton" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">提交</button>
                        </p>
            </div>
            <!-- 主要内容结束 -->
				<div class="clear"></div>
            </div><!-- #updates -->
	    </div>
	    </div>
	</body>
</html>