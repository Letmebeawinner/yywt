<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>新建班次</title>
<%-- 		<script type="text/javascript" src="${ctx}/static/js/custom/elements.js"></script> --%>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
        <script type="text/javascript">
           
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
        
        function addClass(){
         	var params=jQuery("#form1").serialize();
        	jQuery.ajax({
	    		url:'${ctx}/admin/jiaowu/class/doCreateClass.json',
	    		data:params,
	    		type:'post',
	    		dataType:'json',		
	    		success:function (result){
	    			if(result.code=="0"){
	    				window.location.href="${ctx}/admin/jiaowu/class/classList.json";
	    			}else{
	    				jAlert(result.message,'提示',function() {});
	    			}		    			
	    		} ,
	    		error:function(e){
	    			jAlert('添加失败','提示',function() {});
	    		}
	    	});
        }
        
        function selectTeacher(){
        	window.open('${ctx}/jiaowu/teacher/teacherListForSelect.json','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=1200,height=800');
        }
        
        function addToPeopleId(teacherIdAndName){
			jQuery("#teacherId").val(teacherIdAndName[0]);
        	jQuery("#teacherName").val(teacherIdAndName[1]);
        	jQuery("#teacherspan").html(teacherIdAndName[1]);
		}
    </script>

	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">新建班次</h1>
                <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于新建班次<br />
                    2.按要求填写相关信息,点击"提交"按钮,新建班次.<br />
                    3.带有红色<span style="color: red">*</span>标记的内容为必填部分。
				</span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">
                <div id="updates" class="subcontent">
        		<!-- 主要内容开始 -->
				<div id="validation" class="subcontent">
            	
                    <form id="form1" class="stdform" method="post" action="">
                    <input type="hidden" id="teacherId" name="classes.teacherId" />
                    <input type="hidden" id="teacherName" name="classes.teacherName" />
                    	<p>
                        	<label><em style="color: red;">*</em>班型</label>
                            <span class="field">
                            <select name="classes.classTypeId" id="classTypeId">
                                <c:if test="${classTypeList!=null&&classTypeList.size()>0}">
                                <c:forEach items="${classTypeList }" var="classType">
                                	<option value="${classType.id }">${classType.name}</option>
                                </c:forEach>
                                </c:if>
                            </select>
                            </span>
                        </p>
                        
                       <!--  <p>
                        	<label><em style="color: red;">*</em>班次编号</label>
                            <span class="field"><input type="text" name="classes.classNumber" id="classNumber" class="longinput" /></span>
                        </p> -->
                        
                        <p>
                        	<label><em style="color: red;">*</em>名称</label>
                            <span class="field"><input type="text" name="classes.name" id="name" class="longinput" /></span>
                        </p>
                        
                        <p>
                        	<label><em style="color: red;">*</em>讲师</label>
                            <%-- <span class="field">
                            <select name="classes.teacherId" id="teacherId">
                            	<option value="">请选择</option>
                                <c:if test="${teacherList!=null&&teacherList.size()>0}">
                                	<c:forEach items="${teacherList}" var="teacher">
                                		<option value="${teacher.id}">${teacher.name }</option>
                                	</c:forEach>
                                </c:if>
                                
                            </select>
                            </span> --%>
                            <span class="field">
                            <span id="teacherspan"></span>
                            <a href="javascript:selectTeacher()" class="stdbtn btn_orange">选择讲师</a>
                            </span>
                        </p>
                        <p>
                        	<label><em style="color: red;">*</em>开始时间</label>
                            <span class="field">
                            	<input id="startTime" type="text" class="width100 laydate-icon" style="width: 120px;" name="classes.startTime"/> 
                            </span>
                        </p> 
                        <p>
                        	<label><em style="color: red;">*</em>结束时间</label>
                            <span class="field">
                            	<input id="endTime" type="text" class="width100 laydate-icon" style="width: 120px;" name="classes.endTime"/> 
                            </span>
                        </p>
                        <p>
                        <label><em style="color: red;">*</em>最大人数</label>
                        <span class="field"><input type="text" name="classes.maxNum" id="maxNum" class="longinput" onchange="if(/\D/.test(this.value)){alert('只能输入数字','提示',null);this.value='';}"/></span>
                        </p>
                        <p>
                        	<label>备注</label>
                            <span class="field"><textarea cols="80" rows="5" name="classes.note" class="mediuminput" id="note"></textarea></span> 
                        </p>
                        <br />
                        
                        </form>
                        <p class="stdformbutton">
                        	<button class="radius2" onclick="addClass()" id="submitButton" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">
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