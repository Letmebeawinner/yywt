<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>档案查询申请</title>
</head>

<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">添加档案查询申请</h1>
    </div>
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于档案查询申请；<br>
        2.<em style="color: red;">*</em>代表必填项；<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label><em style="color: red;">*</em>调阅人姓名</label>
                    <span class="field">
                        <input type="text"  value = "${applyName}" class="longinput" readonly/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>主要内容和目的</label>
                    <span class="field">
                        <textarea rows="10" cols="80" class="longinput" name="oaArchive.content" id="content"></textarea>
                    </span>
                </p>

                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit();return false;">提交保存</button>
                </p>
                <input type = "hidden" name = "processDefinitionId" value = "${processDefinition.id}">
            </form>
            <br/>
        </div>
    </div>
</div>
<script>
    function addFormSubmit() {
        if(!jQuery("#content").val()){
            alert("请填写申请内容和目的");
            return;
        }

        var data = jQuery("#addFormSubmit").serialize();
        jQuery.ajax({
            url: "${ctx}/admin/oa/archive/process/start.json",
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
</body>
</html>