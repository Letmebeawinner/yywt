<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>办公室领导审批</title>
    <script type = "text/javascript" src = "${ctx}/static/js/oa_select_options.js"></script>
    <script type="text/javascript">
        var meetingId = '${oaMeeting.meetId}'
        jQuery(function() {
            queryDateNameById(meetingId, "meetId", 1);
        });

        function addFormSubmit(flag) {
            jQuery("#audit").val(flag);
            var data = jQuery("#addFormSubmit").serialize();
            jQuery.ajax({
                url: "${ctx}/admin/oa/meetingApplyAudit.json",
                data: data,
                type: "post",
                dataType: "json",
                async: false,
                cache : false,
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "${ctx}/admin/oa/task/to/claim/list.json";
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
        <h1 class="pagetitle">办公室领导审批</h1>
    </div>
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于办公室领导审批；<br>
        2.<em style="color: red;">*</em>代表必填项；<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <input type = "hidden" name = "taskId" value = "${task.id}" id = "taskId">
                <input type = "hidden" name = "oaMeeting.processInstanceId" value = "${oaMeeting.processInstanceId}" id = "processInstanceId">
                <input type = "hidden" value = "${oaMeeting.meetId}" id = "meetingId">
                <input type = "hidden" name = "oaMeeting.audit" id = "audit">
                <p>
                    <label><em style="color:red">*&nbsp;</em>会议室名称</label>
                    <span class="field">
                        <select name="oaMeeting.meetId" id="meetId">
                        </select>
                    </span>
                </p>
                <p>
                    <label><em style="color:red">*&nbsp;</em>会议名称</label>
                    <span class="field"><input type="text" value = "${oaMeeting.name}" id="name" class="longinput" readonly = "true"/></span>
                </p>
                <p>
                    <label><em style="color:red">*&nbsp;</em>申请开始日期</label>
                    <span class="field"><input type="text" value = "<fmt:formatDate value="${oaMeeting.startTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>" id="applyDate" class="longinput"readonly="readonly"/></span>
                </p>
                <p>
                    <label><em style="color:red">*&nbsp;</em>申请结束时间</label>
                    <span class="field"><input type="text" value = "<fmt:formatDate value="${oaMeeting.endTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>" id="applyEndDate" class="longinput" readonly="readonly"/></span>
                </p>
                <p>
                    <label><em style="color:red">*&nbsp;</em>申请理由</label>
                    <span class="field"><textarea cols="80" rows="5" id="reason" class="longinput" readonly = "true">${oaMeeting.reason}</textarea></span>
                </p>
                <p>
                    <label>办公室人员意见</label>
                    <span class="field">
                        <textarea cols="10" rows="5" id = "comment" name = "comment"></textarea>
                    </span>
                </p>
                <p>
                    <label>审核状态</label>
                    <span class="field">
                        <c:if test = "${oaNews.audit == 1}">
                            审批已通过
                        </c:if>
                        <c:if test = "${oaNews.audit == 0}">
                            审核中
                        </c:if>
                        <c:if test = "${oaNews.audit == 2}">
                            已拒绝
                        </c:if>
                    </span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit(1);return false;">同意</button>
                    <button class="submit radius2" onclick="addFormSubmit(2);return false;">驳回</button>
                    <button class="submit radius2" onclick="history.go(-1);return false;">返回</button>
                </p>
            </form>

            <br/>
        </div>
    </div>
    <jsp:include page="/WEB-INF/view/common/process_transfer.jsp"></jsp:include>
</div>
</body>
</html>