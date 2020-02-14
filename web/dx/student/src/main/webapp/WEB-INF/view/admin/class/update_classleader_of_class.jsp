<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>设置班主任</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">
        jQuery(function(){
            var teacherName= "${classes.teacherName}";
            jQuery("#teacherspan").html(teacherName);
        });
        function updateClass(){
            var params=jQuery("#form1").serialize();
            jQuery.ajax({
                url:'${ctx}/admin/jiaowu/class/updateClassLeaderOfClass.json',
                data:params,
                type:'post',
                dataType:'json',
                success:function (result){
                    if(result.code=="0"){
                        window.location.href="${ctx}/admin/jiaowu/class/classList.json";
                    }else{
                        jAlert(result.message,'提示',function() {});
                    }
                },
                error:function(e){
                    jAlert('更新失败','提示',function() {});
                }
            });
        }
        //表明是点击的“选择班主任”按钮(1)，还是点击"选择副班主任"按钮(2)
        var selectTeacherTag;
        function selectTeacher(){
            selectTeacherTag=1;
            window.open('${ctx}/jiaowu/teacher/classLeaderListForSelect.json','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=800,height=600');
        }

        function addToPeopleId(teacherArray){
            if(selectTeacherTag==1) {
                jQuery("#teacherspan").html(teacherArray[1]);
                jQuery("#teacherId").val(teacherArray[0]);
                jQuery("#teacherName").val(teacherArray[1]);
            }else{
                jQuery("#deputyteacherspan").html(teacherArray[1]);
                jQuery("#deputyTeacherId").val(teacherArray[0]);
                jQuery("#deputyTeacherName").val(teacherArray[1]);
            }
        }

        function selectDeputyTeacher(){
            selectTeacherTag=2;
            window.open('${ctx}/jiaowu/teacher/classLeaderListForSelect.json','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=800,height=600');
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">设置班主任</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面用于设置班主任.<br />
					2.可修改相关信息,点击"提交"按钮,保存修改信息.<br />
					3.带有红色<span style="color: red">*</span>标记的内容为必填部分。
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="updates" class="subcontent">
            <!-- 主要内容开始 -->
            <div id="validation" class="subcontent">

                <form id="form1" class="stdform stdform2" method="post" action="">
                    <p>
                        <input type="hidden" value="${classes.id}" id="classId" name="classes.id"/>
                        <input type="hidden" value="${classes.classTypeId}" name="classes.classTypeId"/>
                        <input type="hidden" value="${classes.teacherId}" id="teacherId" name="classes.teacherId"/>
                        <input type="hidden" value="${classes.teacherName}" id="teacherName" name="classes.teacherName"/>
                        <input type="hidden" value="${classes.deputyTeacherId}" id="deputyTeacherId" name="classes.deputyTeacherId"/>
                        <input type="hidden" value="${classes.deputyTeacherName}" id="deputyTeacherName" name="classes.deputyTeacherName"/>
                        <label><em style="color: red;">*</em>名称</label>
                        <span class="field"><input type="text" name="classes.name" id="name" class="longinput" value="${classes.name}" readonly="readonly"/></span>
                    </p>

                    <p>
                        <label><em style="color: red;">*</em>班主任</label>
                            <span class="field">
                            <span id="teacherspan">${classes.teacherName}</span>
                            <a href="javascript:void(0)" onclick="selectTeacher()" class="stdbtn btn_orange">选择班主任</a>
                            </span>
                    </p>
                    <p>
                        <label>副班主任</label>
                            <span class="field">
                            <span id="deputyteacherspan">${classes.deputyTeacherName}</span>
                            <a href="javascript:void(0)" onclick="selectDeputyTeacher()" class="stdbtn btn_orange">选择副班主任</a>
                            </span>
                    </p>
                    <p>
                        <label><em style="color: red;">*</em>开班时间</label>
                            <span class="field">
                            	<input style="width: 200px;" id="startTime" type="text" class="width100" name="classes.startTime" value="<fmt:formatDate type='both' value='${classes.startTime}' pattern='yyyy-MM-dd HH:mm:ss'/>" readonly="readonly"/>
                            </span>
                    </p>
                    <p>
                        <label><em style="color: red;">*</em>结束时间</label>
                            <span class="field">
                            	<input style="width: 200px;" id="endTime" type="text" class="width100" name="classes.endTime" value="<fmt:formatDate type='both' value='${classes.endTime}' pattern='yyyy-MM-dd HH:mm:ss'/>" readonly="readonly"/>
                            </span>
                    </p>
                    <p>
                        <label><em style="color: red;">*</em>最大人数</label>
                        <span class="field"><input type="text" name="classes.maxNum" id="maxNum" class="longinput" onchange="if(/\D/.test(this.value)){alert('只能输入数字','提示',null);this.value='';}" value="${classes.maxNum}" readonly="readonly"/></span>
                    </p>
                    <p>
                        <label>备注</label>
                        <span class="field"><textarea cols="80" rows="5" name="classes.note" class="mediuminput" id="note" readonly="readonly">${classes.note}</textarea></span>
                    </p>
                    <p class="stdformbutton" style="text-align: center">
                        <button class="radius2" onclick="updateClass()" id="submitButton" type="button">提交</button>
                    </p>
                </form>
            </div>
            <div class="clear"></div>
        </div><!-- #updates -->
    </div>
</div>
</body>
</html>