<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>修改会议室</title>
    <script type="text/javascript">
        function addFormSubmit() {
            var meetId=jQuery("#meetId").val();
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
            var context = jQuery("#context").val();
            if (context == "" || context == null) {
                alert("请添加申请理由");
                return;
            }
            jQuery.ajax({
                url: "${ctx}/admin/oa/updateMeetApply.json",
                data:jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                async: false,
                success: function (result) {
                    if (result.code=="0") {
                        alert(result.message);
                        window.location.href = "${ctx}/admin/oa/queryAllMeetApply.json";
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
        <h1 class="pagetitle">修改会议室</h1>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <input type="hidden" name="meetApply.id" value="${meetApply.id}" >
                    <label><em style="color:red">*&nbsp;</em>会议室名称</label>
                    <span class="field">
                        <select name="meetApply.meetId" id="meetId">
                            <c:forEach var="meet" items="${meetingList}">
                                <option value="${meet.id}" <c:if test="${meetApply.meetId==meet.id}">selected="selected"</c:if>>${meet.name}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>
                <p>
                    <label><em style="color:red">*&nbsp;</em>会议名称</label>
                    <span class="field"><input type="text" name="meetApply.name" id="name" class="longinput"  value="${meetApply.name}"/></span>
                </p>
                <p>
                    <label><em style="color:red">*&nbsp;</em>申请开始日期</label>
                    <span class="field"><input type="text" name="meetApply.applyStartDate" id="applyDate" class="longinput"  value="<fmt:formatDate value="${meetApply.applyStartDate}"/>" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" readonly="readonly"/></span>
                </p>
                <p>
                    <label><em style="color:red">*&nbsp;</em>申请结束时间</label>
                    <span class="field"><input type="text" name="meetApply.applyendDate" id="applyEndDate" class="longinput" value="<fmt:formatDate value="${meetApply.applyendDate}"/>" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" readonly="readonly"/></span>
                </p>
                <p>
                    <label><em style="color:red">*&nbsp;</em>申请理由</label>
                    <span class="field"><textarea cols="80" rows="5" name="meetApply.context" id="context" class="longinput">${meetApply.context}</textarea></span>
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