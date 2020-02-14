<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>办公室领导审批</title>
    <script type = "text/javascript" src = "${ctx}/static/js/oa_select_options.js"></script>
    <script type="text/javascript">
        var topicId = '${agenda.topicId}';
        jQuery(function () {
            queryDateNameById(topicId, "agendaName", 4);
        });

        function addFormSubmit(flag) {
            jQuery("#audit").val(flag);
            var data = jQuery("#saveForm").serialize();
            jQuery.ajax({
                url: "${ctx}/admin/oa/agendaApplyAudit.json",
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
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">会议管理员调整</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来会议管理员调整相关内容<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="saveForm">
                <p>
                    <label><em style="color: red;">*</em>议题</label>
                    <span class="field">
                        <select name="agenda.topicId" class="longinput" id = "agendaName">
                        </select>
                    </span>
                </p>
                <input type = "hidden" name = "taskId" value = "${task.id}" id = "taskId">
                <input type = "hidden" name = "agenda.processInstanceId" value = "${agenda.processInstanceId}" id = "processInstanceId">
                <input type="hidden" name="agenda.audit" id="audit">
                <p>
                    <label><em style="color: red;">*</em>时间</label>
                    <span class="field">
                        <input type="text" id="time" class="longinput" readonly value = "<fmt:formatDate value="${agenda.time}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>">
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>地点</label>
                    <span class="field">
                        <input type="text" readonly="readonly" readonly class="longinput" value = "${agenda.location}">
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>主持人</label>
                    <span class="field">
                        <input type="text" readonly class="longinput" value = "${agenda.compere}">
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>出席</label>
                    <span class="field">
                        <input type="text" readonly class="longinput" value = "${agenda.bePresent}">
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>缺席</label>
                    <span class="field">
                        <input type="text" name="agenda.absent" class="longinput" value = "${agenda.absent}">
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>列席</label>
                    <span class="field">
                        <input type="text" readonly class="longinput" value = "${agenda.attend}">
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>记录</label>
                    <span class="field">
                        <textarea readonly rows="10" cols="5" class="longinput" style="text-align: left">${agenda.record}</textarea>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>意见</label>
                    <span class="field">
                    <textarea cols="80" rows="5" maxlength="398" id="refuseReason" name="comment"
                              class="longinput"></textarea>
                </span>
                </p>

                <p>
                    <label>审核状态</label>
                    <span class="field">
                    <c:if test = "${agenda.audit == 1}">
                        审批已通过
                    </c:if>
                    <c:if test = "${agenda.audit == 0}">
                        审核中
                    </c:if>
                    <c:if test = "${agenda.audit == 2}">
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