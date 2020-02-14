<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>公文发布</title>
    <script type="text/javascript">

    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">公文发布</h1>
    </div>
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于公文发布；<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form id = "letterPublish" method="post" class="stdform stdform2">
                <input type = "hidden" name = "taskId" value = "${task.id}" id = "taskId">
                <input id = "userId" name="userIds" type="hidden">
                <input name = "letterId" type="hidden" value = "${oaLetter.id}">
                <p>
                    <label>来文单位</label>
                    <span class="field">
                        <input type = "text" value = "${oaLetter.applyCompany}" class="longinput" readonly="true">
                    </span>
                </p>
                <p>
                    <label>文号</label>
                    <span class="field">
                        <input type = "text" value = "${oaLetter.letterNo}" class="longinput" readonly = "true">
                    </span>
                </p>
                <p>
                    <label>序号</label>
                    <span class="field">
                        <input type = "text" value = "${oaLetter.orderNo}" class="longinput" readonly = "true">
                    </span>
                </p>
                <p>
                    <label>密级</label>
                    <span class="field">
                        <input type = "text" class="longinput" value = "${oaLetter.secretLevel}" readonly = "true">
                    </span>
                </p>
                <p>
                    <label>紧急程度</label>
                    <span class="field">
                        <input type = "text"  class="longinput" value = "${oaLetter.urgentLevel}" readonly = "true">
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>公文名称</label>
                    <span class="field"><input type="text" value = "${oaLetter.title}" id="name" class="longinput" readonly = "true"/></span>
                </p>
                <p>
                    <label>公文内容</label>
                    <span class="field">
                        <textarea cols="10" rows="5" readonly class="longinput">${oaLetter.context}</textarea>
                    </span>
                </p>
                <p>
                    <label>审核状态</label>
                    <span class="field">
                        <c:if test = "${oaLetter.audit == 1}">
                            审批已通过
                        </c:if>
                        <c:if test = "${oaLetter.audit == 0}">
                            审核中
                        </c:if>
                        <c:if test = "${oaLetter.audit == 2}">
                            已拒绝
                        </c:if>
                    </span>

                </p>

            <p>
                <label>发送给</label>
                <span class="field">
                    <a class="stdbtn btn_red" onclick="addEmployee()">选择教职工</a>
                    <div id="employees"></div>
                </span>
            </p>
            <p class="stdformbutton" style="text-align: center">
                <button class="submit radius2" onclick="publishLetter();return false;">提交保存</button>
            </p>
            </form>
            <br/>

        </div>
    </div>
    <jsp:include page="/WEB-INF/view/common/process_transfer.jsp"></jsp:include>
</div>

<script type="text/javascript" src="${ctx}/static/js/schedule/selectemployee.js"></script>
<script>
    function publishLetter() {
        var data = jQuery("#letterPublish").serialize();
        jQuery.ajax({
            url: "${ctx}/admin/oa/letter/publish.json",
            data: data,
            type: "post",
            dataType: "json",
            success: function(result) {
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