<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>岗位系列修改</title>
    <script type="text/javascript">
        function addFormSubmit() {
            if (jQuery("#name").val().trim() == "") {
                alert("请填写岗位系列名称。");
                return;
            }
            jQuery.ajax({
                type: "post",
                dataType: "json",
                url: "/admin/ganbu/jobOrder/updateJobOrder.json",
                data: {
                    "jobOrder.name": jQuery('#name').val(),
                    "jobOrder.id": jQuery("#jobOrderId").val()
                },
                success: function (result) {
                    if (result.code == 0) {
                        window.location.href = result.data;
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
        <h1 class="pagetitle">岗位系列修改</h1>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <form id="jobOrderFrom" class="stdform stdform2" method="post">
            <p>
                <input type="hidden" value="${jobOrder.id}" name="jobOrder.id" id="jobOrderId">
                <label>岗位系列名称:</label>
                <span class="field">
                    <input type="text"  value="${jobOrder.name}" name="jobOrder.name" class="longinput" id="name"/>
                </span>
            </p>
            <p class="stdformbutton" style="text-align: center">
                <button class="submit radius2" onclick="addFormSubmit();return false;">保 存</button>
                <input type="reset" class="reset radius2" value="重 置" onclick="resetData()"/>
            </p>
        </form>
    </div>
</div>
</body>
</html>