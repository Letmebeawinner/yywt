<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>选择教室</title>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
<%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
        <script type="text/javascript">
        jQuery(function(){
			var id= "${courseArrange.id}";
			jQuery("#courseArrangeId").val(id);
			var classroomId= "${courseArrange.classroomId}";
			jQuery("#classroomId").val(classroomId);
            var classroomName="${courseArrange.classroomName}";
            jQuery("#classroomName").val(classroomName);
            jQuery("#classroomspan").html(classroomName);
        });
        
		function updateClassroom(){
			/* var id=jQuery("#courseArrangeId").val();
			var classroomId=jQuery("#classroomId").val();
			var classroomName=jQuery("#classroomName").val(); */
			var params=jQuery("#form1").serialize();
			jQuery.ajax({
	    		url:'${ctx}/admin/jiaowu/teach/updateClassroom.json',
	    		/* data:{"courseArrange.id":id,
	    			"courseArrange.classroomId":classroomId,
	    			"courseArrange.classroomName":classroomName
	    			}, */
	    		data:params,
	    		type:'post',
	    		dataType:'json',		
	    		success:function (result){
	    			if(result.code=="0"){
	    				window.location.href="${ctx}/admin/jiaowu/courseArrange/toUpdateCourseArrange.json?classId=${courseArrange.classId}";
	    			}else{
	    				jAlert(result.message,'提示',function() {});
	    			}		    			
	    		},
	    		error:function(e){
	    			jAlert('添加失败','提示',function() {});
	    		}
	    	});
		}
				
		
		function selectClassroom(){
        	window.open('${ctx}/jiaowu/classroom/classroomListForSelect.json','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=1200,height=800');
        }
        
        function addClassroomIdAndPosition(classroomIdAndPosition){
        	jQuery("#classroomId").val(classroomIdAndPosition.split("-")[0]);
        	jQuery("#classroomspan").html(classroomIdAndPosition.split("-")[1]);
        	jQuery("#classroomName").val(classroomIdAndPosition.split("-")[1]);
        }
        </script>
	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">选择教室</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面用于修改某排课记录的教室.<br />
					2.可修改该排课记录的教室,点击"提交"按钮,保存修改信息.<br />
					3.带有红色<span style="color: red">*</span>标记的内容为必填部分。
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">

            <div id="updates" class="subcontent">
                <!-- 主要内容开始 -->
                <div id="validation" class="subcontent">

                    <form id="form1" class="stdform" method="post" action="">
                    	<input type="hidden" name="courseArrange.classroomId" id="classroomId" />
                    	<input type="hidden" name="courseArrange.classroomName" id="classroomName" />
                    	<input type="hidden" id="courseArrangeId" name="courseArrange.id" />
                       
                        <p id="classroomp">
                        	<label><em style="color: red;">*</em>教室</label>
                            <span class="field">
                            <span id="classroomspan"></span>
                            <a href="javascript:selectClassroom()" class="stdbtn btn_orange">选择教室</a>
                            </span>
                        </p>


                       
                        <br />

                        
                    </form>
					<p class="stdformbutton">
                            <button class="radius2" onclick="updateClassroom()" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">提交</button>
                        </p>
            </div>
            <!-- 主要内容结束 -->
                <div class="clear"></div>
            </div><!-- #updates -->

        </div>
	    </div>
	</body>
</html>