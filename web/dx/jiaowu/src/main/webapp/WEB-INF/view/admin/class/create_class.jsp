<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>新建班次</title>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
        <script type="text/javascript">
           
        jQuery(function(){
            jQuery("#registration").change();

            laydate.skin('molv');
            laydate({
                elem: '#startTime',
                format:'YYYY-MM-DD hh:mm:ss',
                istime: true
            });
            laydate({
                elem: '#endTime',
                format:'YYYY-MM-DD hh:mm:ss',
                istime: true
            });
            laydate({
                elem: '#signEndTime',
                format:'YYYY-MM-DD hh:mm:ss',
                istime: true
            });
            jQuery("#jiezhiDate").hide();
        });
        
        function addClass(){
            var classId = jQuery("#classId").val();
            if(classId == null || classId.trim()==''){
                jAlert('请选择教室','提示',function() {});
                return;
            }
            var registration = jQuery("#registration").val();
            if(registration==2){
                var maxNum = jQuery("#maxNum").val();
                var peopleNumber = jQuery("#peopleNumber").val();
                if(peopleNumber == null || peopleNumber.trim()==''){
                    jAlert('请填写报名人数','提示',function() {});
                    return;
                }
                if(parseInt(peopleNumber)>parseInt(maxNum)){
                    jAlert('报名人数不能大于最大人数','提示',function() {});
                    return;
                }
            }
         	var params=jQuery("#form1").serialize();
         	console.log(params)
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

        //增加教室
		function selectClass() {
            var startTime = jQuery("#startTime").val();
            /*if(startTime == null || startTime.trim()==''){
                jAlert('请填写开始时间','提示',function() {});
                return;
            }*/
            var endTime = jQuery("#endTime").val();
            /*if(endTime == null || endTime.trim()==''){
                jAlert('请填写结束时间','提示',function() {});
                return;
            }*/
            window.open('${ctx}/jiaowu/class/meetList.json?startTime='+startTime+"&endTime="+endTime,'newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=1000,height=800');
        }
        function addClassId(classId,className){
            jQuery("#classId").val(classId);
            jQuery("#className").val(className);
            jQuery("#classspan").html(className+"<a href='javascript:void(0);' onclick='quxiao(1)' style='color: red;'>取消</a>");
        }

        function quxiao(type) {
            if(type==1){
                jQuery("#classId").val('');
                jQuery("#className").val('');
                jQuery("#classspan").html('');
            }else{
                jQuery("#discussId").val('');
                jQuery("#discussName").val('');
                jQuery("#discussspan").html('');
            }
        }
        //增加讨论室
        function selectDiscuss() {
            window.open('${ctx}/jiaowu/class/discussList.json','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=1000,height=800');
        }
        function addDiscussId(discussIds,discussNames){
            jQuery("#discussId").val(discussIds);
            jQuery("#discussName").val(discussNames);
            jQuery("#discussspan").html(discussNames+"<a href='javascript:void(0);' onclick='quxiao(2)' style='color: red;'>取消</a>");
        }
        
        function queryRegistration(val) {
            if(val==1){
                jQuery("#peopleNumberStatus").hide();
                jQuery("#jiezhiDate").show();
                jQuery("#maxNum").show();
            }else{
                jQuery("#peopleNumberStatus").show();
                jQuery("#jiezhiDate").hide();
                jQuery("#maxNum").hide();
            }
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

                    <form id="form1" class="stdform stdform2" method="post" action="">


                        <%--隐藏域放这里会影响样式--%>
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
                        

                        <p>
                        	<label><em style="color: red;">*</em>名称</label>
                            <span class="field"><input type="text" name="classes.name" id="name" class="longinput" /></span>
                        </p>
                            <p>
                                <label><em style="color: red;">*</em>开始时间</label>
                                <span class="field">
                            	<input id="startTime" type="text" class="longinput"  name="classes.startTime" readonly/>
                            </span>
                            </p>
                            <p>
                                <label><em style="color: red;">*</em>结束时间</label>
                                <span class="field">
                            	<input id="endTime" type="text" class="longinput"  name="classes.endTime" readonly/>
                            </span>
                            </p>
                        <p>
                            <label><em style="color: red;">*</em>在线报名</label>
                            <span class="field">
                            <select id="registration" name="classes.registration" onchange="queryRegistration(this.options[this.options.selectedIndex].value)">
                                <option value="2">否</option>
                                <option value="1">是</option>
                            </select><span style="color: red">说明：选择“是”则按照主体班的模式执行，如果选择“否”，则跳过报名、分配一卡通、考勤等步骤</span>
                        </span>
                        </p>
                        <p id="peopleNumberStatus">
                            <label><em style="color: red;">*</em>报名人数</label>
                            <span class="field"><input type="text" name="classes.peopleNumber" id="peopleNumber" class="longinput" onchange="if(/\D/.test(this.value)){alert('只能输入数字','提示',null);this.value='';}"/></span>
                        </p>
                        <%--<p>
                        	<label><em style="color: red;">*</em>班主任</label>
                            <span class="field">
                            <span id="teacherspan"></span>
                            <a href="javascript:selectTeacher()" class="stdbtn btn_orange">选择班主任</a>
                            </span>
                        </p>--%>
                        <p>
                            <label><em style="color: red;">*</em>教室</label>
                            <span class="field">
                            <span id="classspan"></span>
                                <a href="javascript:selectClass()" class="stdbtn btn_orange">选择教室</a>
                            </span>
                        </p>
                        <p>
                            <label>讨论室</label>
                            <span class="field">
                            <span id="discussspan"></span>
                                <a href="javascript:selectDiscuss()" class="stdbtn btn_orange">选择讨论室</a>
                            </span>
                        </p>
                        <p id="jiezhiDate">
                            <label><em style="color: red;">*</em>报名截止时间</label>
                            <span class="field">
                            	<input id="signEndTime" type="text" class="longinput"  name="classes.signEndTime" readonly/>
                            </span>
                        </p>
                        <p id="maxNum">
                        <label><em style="color: red;">*</em>最大人数</label>
                        <span class="field"><input type="text" name="classes.maxNum" id="maxNum" class="longinput" onchange="if(/\D/.test(this.value)){alert('只能输入数字','提示',null);this.value='';}"/></span>
                        </p>
                        <p>
                        	<label>备注</label>
                            <span class="field"><textarea cols="80" rows="5" name="classes.note" class="mediuminput" id="note"></textarea></span> 
                        </p>
                        <br />

                        <input type="hidden" id="classId" name="classes.classId"/>
                        <input type="hidden" id="className" name="classes.className"/>
                        <input type="hidden" id="discussId" name="classes.discussId"/>
                        <input type="hidden" id="discussName" name="classes.discussName"/>
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