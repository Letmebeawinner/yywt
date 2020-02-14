<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>部门领导审批</title>
</head>

<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">部门领导审批</h1>
    </div>
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于部门领导审批；<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label><em style="color: red;">*</em>调阅人姓名</label>
                    <span class="field">
                        <input type="text"  value = "${applyName}" class="longinput" readonly/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>主要内容和目的</label>
                    <span class="field">
                        <textarea rows="10" cols="80" class="longinput" id="content">${oaArchive.content}</textarea>
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
                    <c:if test = "${oaArchive.audit == 1}">
                        审批已通过
                    </c:if>
                    <c:if test = "${oaArchive.audit == 0}">
                        审核中
                    </c:if>
                    <c:if test = "${oaArchive.audit == 2}">
                        已拒绝
                    </c:if>
                </span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit(0);return false;">同意</button>
                    <button class="submit radius2" onclick="addFormSubmit(2);return false;">驳回</button>
                    <button class="submit radius2" onclick="history.go(-1);return false;">返回</button>
                </p>
                <input type="hidden" value="${task.id}" id="taskId" name="taskId">
                <input type="hidden" value="${oaArchive.processInstanceId}" name="processInstanceId">
                <input type="hidden" name="oaArchive.audit" id="audit">
            </form>
            <br/>
        </div>
    </div>
    <jsp:include page="/WEB-INF/view/common/process_transfer.jsp"></jsp:include>
</div>
<script>
    function addFormSubmit(flag) {
        jQuery("#audit").val(flag);
        var data = jQuery("#addFormSubmit").serialize();
        jQuery.ajax({
            url: "${ctx}/admin/oa/archiveApplyAudit.json",
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
</body>
</html>