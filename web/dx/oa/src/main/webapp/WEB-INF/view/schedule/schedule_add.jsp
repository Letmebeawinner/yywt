<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>添加日程</title>
    <script type="text/javascript" src="${ctx}/static/js/schedule/selectemployee.js"></script>
    <script type="text/javascript">


        jQuery(function(){
            laydate.skin('molv');
            laydate({
                elem: '#startTime',
                format:'YYYY-MM-DD hh:mm:ss'
            });
        });

        function addFormSubmit() {
            var startTime = jQuery("#startTime").val();
            if (startTime == "" || startTime == null) {
                alert("请添加开始时间");
                return;
            }
            jQuery.ajax({
                url: "${ctx}/admin/oa/addSaveSchedule.json",
                data:jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                async: false,
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "${ctx}/admin/oa/queryAllSchedule.json";
                    } else {
                        alert(result.message);
                    }
                }
            });
        }

    </script>
</head>

<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">添加日程</h1>
    </div>
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于日程信息添加；<br>
        2.新建分类：点击<span style="color:red">新建分类</span>，修改安排我的日程信息；<br>
        3.<em style="color: red;">*</em>代表必填项；<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">

            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label><em style="color: red;">*</em>开始时间</label>
                    <span class="field"><input type="text" name="schedule.startTime" id="startTime" class="longinput" readonly="readonly"/></span>
                </p>
                <p>
                    <label>事务类型</label>
                    <span class="field">
                        <input type="radio" name="schedule.type"  class="longinput"  value="0" style="width: 30px;" checked="checked"/>工作事务
                        <input type="radio" name="schedule.type" class="longinput"  value="1" style="width: 30px;"/>个人事务
                    </span>
                </p>
                <p>
                    <label>发送给</label>
                     <span class="field">
                        <input type="hidden" name="employeeIds" id="userId">
                         <a class="stdbtn btn_red" onclick="addEmployee()" >选择教职工</a>
                        <div id="employees"></div>
                    </span>
                </p>
                <p>
                    <label>日程内容</label>
                    <span class="field">
                        <textarea rows="10" cols="5" name="schedule.context" id="context"></textarea>
                    </span>

                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit();return false;">提交保存</button>
                </p>
            </form>
            <br/>
        </div>
    </div>
</div>

</body>
</html>