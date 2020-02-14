<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>申请会议室</title>
    <script type = "text/javascript" src = "${ctx}/static/js/oa_select_options.js"></script>
    <script type="text/javascript">
        jQuery(function() {
            queryListInfo("meetId", 1);
            laydate.skin('molv');
            laydate({
                elem: '#applyDate',
                format:'YYYY-MM-DD hh:mm:ss',
                istime: true
            });
            laydate({
                elem: '#applyEndDate',
                format:'YYYY-MM-DD hh:mm:ss',
                istime: true
            });

        });

        function addFormSubmit() {
            var meetId = jQuery("#meetId").val();
            if (meetId == "" || meetId == null) {
                alert("请添加会议室名称");
                return;
            }
            var name = jQuery("#name").val();
            if (name == "" || name == null) {
                alert("请添加会议名称");
                return;
            }
            var applyDate = jQuery("#applyDate").val();
            if (applyDate == "" || applyDate == null) {
                alert("请添加申请开始时间");
                return;
            }
            var applyEndDate = jQuery("#applyEndDate").val();
            if (applyEndDate == "" || applyEndDate == null) {
                alert("请添加申请结束时间");
                return;
            }
            var reason = jQuery("#reason").val();
            if (reason == "" || reason == null) {
                alert("请添加申请理由");
                return;
            }
            var data = jQuery("#addFormSubmit").serialize();
            jQuery.ajax({
                url: "${ctx}/admin/oa/meeting/process/start.json",
                data: data,
                type: "post",
                dataType: "json",
                async: false,
                cache : false,
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "${ctx}/admin/oa/task/history/mine.json";
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
        <h1 class="pagetitle">申请会议室</h1>
    </div>
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于会议申请；<br>
        2.<em style="color: red;">*</em>代表必填项；<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <input type = "hidden" name = "processDefinitionId" value = "${processDefinition.id}">
                <p>
                    <label><em style="color:red">*&nbsp;</em>会议室名称</label>
                    <span class="field">
                        <select name="oaMeeting.meetId" id="meetId">
                        </select>
                    </span>
                </p>
                <p>
                    <label><em style="color:red">*&nbsp;</em>会议名称</label>
                    <span class="field"><input type="text" name="oaMeeting.name" id="name" class="longinput" /></span>
                </p>
                <p>
                    <label><em style="color:red">*&nbsp;</em>申请开始日期</label>
                    <span class="field"><input type="text" name="oaMeeting.startTime" id="applyDate" class="longinput"  readonly="readonly"/></span>
                </p>
                <p>
                    <label><em style="color:red">*&nbsp;</em>申请结束时间</label>
                    <span class="field"><input type="text" name="oaMeeting.endTime" id="applyEndDate" class="longinput"  readonly="readonly"/></span>
                </p>
                <p>
                    <label><em style="color:red">*&nbsp;</em>申请理由</label>
                    <span class="field"><textarea cols="80" rows="5" name="oaMeeting.reason" id="reason" class="longinput"></textarea></span>
                </p>
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