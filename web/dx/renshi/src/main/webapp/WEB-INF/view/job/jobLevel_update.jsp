<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>岗位级别修改</title>
    <script type="text/javascript">
        function addFormSubmit() {
            var id = jQuery("#id").val();
            var name = jQuery("#name").val();
            var jobOrderId = jQuery("#jobOrderId").val();
            if (jQuery("#name").val().trim() == "") {
                alert("请填写岗位级别名称。");
                return false
            }
            jQuery.ajax({
                type: "POST",
                dataType: "json",
                url: "/admin/ganbu/jobLevel/updateJobLevel.json",
                data: {
                    "jobLevel.id": id,
                    "jobLevel.jobOrderId": jobOrderId,
                    "jobLevel.name": name
                },
                async: false,
                success: function (result) {
                    if (result.code == 0) {
                        window.location.href = "/admin/ganbu/jobLevel/queryJobLevel.json";
                    } else {
                        alert("网络异常，请稍后再试");
                    }
                }
            });
        }
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">岗位级别修改</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <form id="jobLevelform" class="stdform stdform2" method="post">
            <p>
                <label>岗位系列:</label>
                <span class="field">
						<select name="jobLevel.jobOrderId" id="jobOrderId">
							<c:forEach items="${jobOrderList}" var="jobOrder">
                                <option  <c:if test="${jobLevel.jobOrderId==jobOrder.id}"> selected="selected" </c:if>
                                        value="${jobOrder.id}">${jobOrder.name}</option>
                            </c:forEach>
						</select>
					</span>
            </p>
            <p>
                <input type="hidden" value="${jobLevel.id}" name="jobLevel.id" id="id">
                <label>岗位级别名称:</label>
                <span class="field">
                    <input type="text" id="name" value="${jobLevel.name}" name="jobLevel.name" class="longinput"/></span>
            </p>

            <p class="stdformbutton" style="text-align: center">
                <button class="submit radius2" onclick="addFormSubmit();return false;">提交保存</button>
            </p>
        </form>
    </div>
</div>
</body>
</html>