<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>修改教室</title>
<%-- 		<script type="text/javascript" src="${ctx}/static/js/custom/elements.js"></script> --%>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
        <script type="text/javascript">
           
        jQuery(function(){
            jQuery("#type").val("${classroom.type}");
        });
        
        function updateClassroom(){
        	var reg = new RegExp("^[0-9]*$");
        	var num=jQuery("#num").val();
        	if(num==""){
        		jAlert("座位数不能为空",'提示',function() {});
        		return;
        	}
        	if(!reg.test(num)){
        		jAlert("座位数必须为数字",'提示',function() {});
        		return;
        	}
        	var params=jQuery("#form1").serialize();
        	jQuery.ajax({
	    		url:'${ctx}/admin/jiaowu/classroom/updateClassroom.json',
	    		data:params,
	    		type:'post',
	    		dataType:'json',		
	    		success:function (result){
	    			if(result.code=="0"){
	    				window.location.href="${ctx}/admin/jiaowu/classroom/classroomList.json";
	    			}else{
	    				jAlert(result.message,'提示',function() {});
	    			}		    			
	    		},
	    		error:function(e){
	    			jAlert('添加失败','提示',function() {});
	    		}
	    	});
        }

		function selectUser(){
			window.open('${ctx}/jiaowu/user/userListForSelect.json','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=1200,height=800');
		}

		function addUser(userIdAndName){
			jQuery("#compereId").val(userIdAndName[0]);
			jQuery("#comperespan").html(userIdAndName[1]);
		}
    </script>

	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">修改教室</h1>
                <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于修改教室<br />
                    2.按要求填写相关信息,点击"提交"按钮,修改教室.<br />
                    3.带有红色<span style="color: red">*</span>标记的内容为必填部分。
				</span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">
                <div id="updates" class="subcontent">
        		<!-- 主要内容开始 -->
				<div id="validation" class="subcontent">
            	
                    <form id="form1" class="stdform" method="post" action="">
                    	<input type="hidden" name="classroom.id" value="${classroom.id}" />
						<input type="hidden" name="classroom.compereId" id="compereId" value="${classroom.compereId}"/>
						<p>
							<label><em style="color: red;">*</em>座区图类型</label>
							<span class="field">
								<select id="type" name="classroom.type">
									<option value="1">大礼堂一楼(478座)</option>
									<option value="2">100座</option>
									<option value="3">大礼堂一二楼(736座)</option>
									<option value="4">200座</option>
									<option value="5">二楼会议室(158座)</option>
									<option value="6">新大楼4楼环形教室(80座)</option>
									<option value="7">新大楼报告厅(312座)</option>
									<option value="8">演播厅(207座)</option>
									<option value="9">综合楼4楼演播厅(100座)</option>
								</select>
							</span>
						</p>
                        <p>
                        	<label><em style="color: red;">*</em>位置</label>
                            <span class="field"><input type="text" name="classroom.position" id="position" class="longinput" value="${classroom.position}"/></span>
                        </p>
                        <p>
                        	<label><em style="color: red;">*</em>座位数</label>
                            <span class="field"><input type="text" name="classroom.num" id="num" class="longinput" value="${classroom.num}"/></span>
                        </p>
						<p>
							<label><em style="color: red;">*</em>主持人</label>
                        	<span class="field">
                                <span id="comperespan">${compereName}</span>
                                <a href="javascript:selectUser()" class="stdbtn btn_orange">选择主持人</a>
                            </span>
						</p>
                        <p>
                        	<label>备注</label>
                            <span class="field"><textarea cols="80" rows="5" name="classroom.note" class="mediuminput" id="note">${classroom.note}</textarea></span> 
                        </p>
                        <br />
                        
                        </form>
                        <p class="stdformbutton">
                        	<button class="radius2" onclick="updateClassroom()" id="submitButton" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">
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