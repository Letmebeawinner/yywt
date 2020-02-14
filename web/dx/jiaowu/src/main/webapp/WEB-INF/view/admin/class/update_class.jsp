<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>修改班次</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">
        jQuery(function () {
            jQuery("#registration").change();
            var classTypeId = "${classes.classTypeId}";
            jQuery("#classTypeId option[value='" + classTypeId + "']").attr("selected", true);
            var teacherName = "${classes.teacherName}";
            jQuery("#teacherspan").html(teacherName);

            //下拉框是否选中
            var registration = '${classes.registration}';
            jQuery("#registration").val(registration);
            if(registration==1){
                jQuery("#peopleNumberStatus").hide();
                jQuery("#jiezhiDate").show();
            }else{
                jQuery("#peopleNumberStatus").show();
                jQuery("#jiezhiDate").hide();
            }

            laydate.skin('molv');
            laydate({
                elem: '#startTime',
                format: 'YYYY-MM-DD hh:mm:ss',
                istime: true
            });
            laydate({
                elem: '#endTime',
                format: 'YYYY-MM-DD hh:mm:ss',
                istime: true
            });
            laydate({
                elem: '#signEndTime',
                format: 'YYYY-MM-DD hh:mm:ss',
                istime: true
            });

        });

        //修改前的数据
        var classIdOld = '${classes.classId}';

        function updateClass() {
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

            var type = 1;
            if(classIdOld==classId && jQuery("#startTimeOld").val() == jQuery("#startTime").val() && jQuery("#endTimeOld").val() == jQuery("#endTime").val()){
                type = 2
            }

            var params = jQuery("#form1").serialize()+"&type="+type;
            jQuery.ajax({
                url: '${ctx}/admin/jiaowu/class/doUpdateClass.json',
                data: params,
                type: 'post',
                dataType: 'json',
                success: function (result) {
                    if (result.code == 0) {
                        alert(result.message);
                        window.location.href = "${ctx}/admin/jiaowu/class/classList.json";
                    } else {
                        alert(result.message);
                        return;
                    }
                }
            });
        }

        function selectTeacher() {
            window.open('${ctx}/jiaowu/teacher/teacherListForSelect.json', 'newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=1000,height=800');
        }

        function addToPeopleId(teacherArray) {
            jQuery("#teacherspan").html(teacherArray[1]);
            jQuery("#teacherId").val(teacherArray[0]);
            jQuery("#teacherName").val(teacherArray[1]);
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
    <div class="pageheader notab" >
        <h1 class="pagetitle">修改班次</h1>
        <div style="margin-left: 30px">
                 <span>
                    <span style="color:red">说明</span><br>
					1.本页面用于修改班次.<br/>
					2.可修改相关信息,点击"提交"按钮,保存修改信息.<br/>
					3.带有红色<span style="color: red">*</span>标记的内容为必填部分。
                </span>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="updates" class="subcontent">
            <!-- 主要内容开始 -->
            <div id="validation" class="subcontent">

                <form id="form1" class="stdform stdform2" method="post" action="">

                    <p>
                        <input type="hidden" value="${classes.id}" id="" name="classes.id"/>
                        <input type="hidden" value="${classes.classTypeId}" name="classes.classTypeId"/>
                        <input type="hidden" value="${classes.teacherId}" id="teacherId" name="classes.teacherId"/>
                        <input type="hidden" value="${classes.teacherName}" id="teacherName" name="classes.teacherName"/>
                        <input type="hidden" value="${classes.recordId}" name="classes.recordId"/>
                        <input type="hidden" value="<fmt:formatDate type='both' value='${classes.startTime}' pattern='yyyy-MM-dd HH:mm:ss'/>" id="startTimeOld"/>
                        <input type="hidden" value="<fmt:formatDate type='both' value='${classes.endTime}' pattern='yyyy-MM-dd HH:mm:ss'/>" id="endTimeOld"/>
                        <label><em style="color: red;">*</em>名称</label>
                        <span class="field"><input type="text" name="classes.name" id="name" class="longinput"
                                                   value="${classes.name}"/></span>
                    </p>
                    <p>
                        <label><em style="color: red;">*</em>开班时间</label>
                        <span class="field">
                            	<input  id="startTime" type="text" class="longinput"
                                        name="classes.startTime"
                                        value="<fmt:formatDate type='both' value='${classes.startTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
                            </span>
                    </p>
                    <p>
                        <label><em style="color: red;">*</em>结束时间</label>
                        <span class="field">
                            	<input  id="endTime" type="text" class="longinput"
                                        name="classes.endTime"
                                        value="<fmt:formatDate type='both' value='${classes.endTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
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
                        <span class="field"><input type="text" name="classes.peopleNumber" id="peopleNumber" value="${classes.peopleNumber}" class="longinput" onchange="if(/\D/.test(this.value)){alert('只能输入数字','提示',null);this.value='';}"/></span>
                    </p>
                    <%--<p>
                        <label><em style="color: red;">*</em>讲师</label>
                        <span class="field">
                            <span id="teacherspan">${classes.teacherName}</span>
                            <a href="javascript:void(0)" onclick="selectTeacher()" class="stdbtn btn_orange">选择讲师</a>
                            </span>
                    </p>--%>
                    <p>
                        <label><em style="color: red;">*</em>教室</label>
                        <span class="field">
                            <span id="classspan">${classes.className}<c:if test="${classes.className!=null && classes.className!=''}"><a href='javascript:void(0);' onclick='quxiao(1)' style='color: red;'>取消</a></c:if></span>
                                <a href="javascript:selectClass()" class="stdbtn btn_orange">选择教室</a>
                            </span>
                    </p>
                    <p>
                        <label>讨论室</label>
                        <span class="field">
                            <span id="discussspan">${classes.discussName}<c:if test="${classes.discussName!=null && classes.className!=''}"><a href='javascript:void(0);' onclick='quxiao(2)' style='color: red;'>取消</a></c:if></span>
                                <a href="javascript:selectDiscuss()" class="stdbtn btn_orange">选择讨论室</a>
                            </span>
                    </p>
                    <p id="jiezhiDate">
                        <label><em style="color: red;">*</em>报名截止时间</label>
                        <span class="field">
                            	<input id="signEndTime" type="text" class="longinput"
                                       name="classes.signEndTime"
                                       value="<fmt:formatDate type='both' value='${classes.signEndTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
                            </span>
                    </p>
                    <p id="maxNum" style="display: none">
                        <label><em style="color: red;">*</em>最大人数</label>
                        <span class="field"><input type="text" name="classes.maxNum" id="maxNum" class="longinput"
                                                   onchange="if(/\D/.test(this.value)){alert('只能输入数字','提示',null);this.value='';}"
                                                   value="${classes.maxNum}"/></span>
                    </p>
                    <p>
                        <label>备注</label>
                        <span class="field"><textarea cols="80" rows="5" name="classes.note" class="mediuminput"
                                                      id="note">${classes.note}</textarea></span>
                    </p>
                    <p class="stdformbutton" style="text-align: center">
                        <button class="radius2" onclick="updateClass();return false;" id="submitButton">提交</button>
                    </p>
                    <input type="hidden" id="classId" name="classes.classId" value="${classes.classId}"/>
                    <input type="hidden" id="className" name="classes.className" value="${classes.className}"/>
                    <input type="hidden" id="discussId" name="classes.discussId" value="${classes.discussId}"/>
                    <input type="hidden" id="discussName" name="classes.discussName" value="${classes.discussName}"/>
                </form>

            </div>
            <!-- 主要内容结束 -->
            <div class="clear"></div>
        </div><!-- #updates -->
    </div>
</div>
</body>
</html>