<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>新建课程类别</title>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
<%-- 		<script type="text/javascript" src="${ctx}/static/js/custom/elements.js"></script> --%>
        <script type="text/javascript">
           
        jQuery(function(){           
        });
        
        function addCourseType(){        	
         	var params=jQuery("#form1").serialize();
         	console.info(params);
        	jQuery.ajax({
	    		url:'${ctx}/admin/jiaowu/courseType/createCourseType.json',
	    		data:params,
	    		type:'post',
	    		dataType:'json',		
	    		success:function (result){
	    			if(result.code=="0"){
	    				window.location.href="${ctx}/admin/jiaowu/courseType/courseTypeList.json";
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
                <h1 class="pagetitle">新建课程类别</h1>
                <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于新建课程类别<br />
                    2.按要求填写相关信息,点击"提交"按钮,新建课程类别.<br />
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
                            <span class="field"><input type="text" name="courseType.name" id="name" class="longinput" /></span>
                        </p>
                        <br />
                        </form>
                        <p class="stdformbutton">
                        	<button class="radius2" onclick="addCourseType()" id="submitButton" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">
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