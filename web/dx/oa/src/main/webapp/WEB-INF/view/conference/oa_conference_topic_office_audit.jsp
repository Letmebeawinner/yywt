<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>办公室审批</title>
    <script type="text/javascript">
        function addFormSubmit(flag) {
        jQuery("#audit").val(flag);
        if (!jQuery("#comment").val()) {
            alert("请输入意见");
            return;
        }
        var data = jQuery("#saveForm").serialize();
        jQuery.ajax({
            url: "${ctx}/admin/oa/meetingTopicAudit.json",
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

        jQuery(function(){
            jQuery("#pobmodal-dialog").hide()
        })
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">办公室审批</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来办公室审批<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="saveForm">
                <p>
                    <label><em style="color: red;">*</em>议题名称</label>
                    <span class="field">
                        <%--<input type="text" name="oaMeetingTopic.name" class="longinput" maxlength="48" value = "${oaMeetingTopic.name}">--%>
                        ${oaMeetingTopic.name}&nbsp;
                    </span>
                </p>
                <p>
                    <label>紧急程度</label>
                    <span class="field">
                        ${oaMeetingTopic.emergencyDegree}&nbsp;
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>汇报人</label>
                    <span class="field">
                       ${oaMeetingTopic.reporter} &nbsp;
                    </span>
                </p>
                <p>
                    <label>列席人</label>
                    <span class="field">
                        ${oaMeetingTopic.attendPeople} &nbsp;
                    </span>
                </p>
                <p>
                    <label>议题内容</label>
                    <span class="field">
                        ${oaMeetingTopic.subjectContent} &nbsp;
                    </span>
                </p>
                <%--<p>--%>
                    <%--<label><em style="color: red;">*</em>议题内容</label>--%>
                    <%--<span class="field">--%>
                        <%--${oaMeetingTopic.subjectContent}--%>
                    <%--</span>--%>
                <%--</p>--%>
                <p>
                    <label><em style="color: red;">*</em>领导意见</label>
                    <span class="field">
                        <textarea cols="10" rows="5" id = "comment" name = "comment" class="longinput"></textarea>
                    </span>
                </p>
                <p>
                    <label>审核状态</label>
                    <span class="field">
                        <c:if test = "${oaMeetingTopic.audit == 1}">
                            审批已通过
                        </c:if>
                        <c:if test = "${oaMeetingTopic.audit == 0}">
                            审核中
                        </c:if>
                        <c:if test = "${oaMeetingTopic.audit == 2}">
                            已拒绝
                        </c:if>
                    </span>

                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit(0);return false;">同意</button>
                    <button class="submit radius2" onclick="addFormSubmit(2);return false;">驳回</button>
                    <button class="submit radius2" onclick="history.go(-1);return false;">返回</button>
                </p>
                <input type = "hidden" name = "taskId" value = "${task.id}" id = "taskId">
                <input type = "hidden" name = "oaMeetingTopic.processInstanceId" value = "${oaMeetingTopic.processInstanceId}" id = "processInstanceId">
                <input type = "hidden" name = "oaMeetingTopic.audit" id = "audit">
            </form>
            <br/>
            <jsp:include page="/WEB-INF/view/common/process_transfer.jsp"></jsp:include>
        </div>
    </div>
</div>
</body>
</html>