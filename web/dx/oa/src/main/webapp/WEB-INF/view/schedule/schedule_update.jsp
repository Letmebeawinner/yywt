<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>修改公告类型</title>
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
                url: "${ctx}/admin/oa/updateSchedule.json",
                data:jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                async: false,
                success: function (result) {
                    if (result.code=="0") {
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
        <h1 class="pagetitle">修改日程</h1>
    </div>
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于日程信息修改；<br>
        2.<em style="color: red;">*</em>代表必填项；<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <input type="hidden" name="schedule.id" value="${schedule.id}" >
                <p>
                    <label><em style="color: red;">*</em>开始时间</label>
                    <span class="field"><input type="text" name="schedule.startTime" id="startTime" class="longinput" value="<fmt:formatDate value="${schedule.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly"/></span>
                </p>
                <p>
                    <label>事务类型</label>
                    <span class="field">
                        <input type="radio" name="schedule.type" id="type" class="longinput"  value="0" style="width: 30px;" <c:if test="${schedule.type==0}">checked</c:if>/>工作事务
                        <input type="radio" name="schedule.type" id="type" class="longinput"  value="1" style="width: 30px;" <c:if test="${schedule.type==1}">checked</c:if>/>个人事务
                    </span>
                </p>
                <p>
                    <label>日程内容</label>
                    <span class="field">
                        <textarea rows="10" cols="5" name="schedule.context" id="context">${schedule.context}</textarea>
                    </span>
                </p>
            </form>
            <p class="stdformbutton" style="text-align: center">
                <button class="submit radius2" onclick="addFormSubmit()">提交保存</button>
            </p>
            <br/>
        </div>
    </div>
</div>


</body>
</html>