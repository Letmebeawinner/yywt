<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>添加红头公文模板</title>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">添加红头公文模板</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于添加红头公文模板；<br>
            2.<em style="color: red;">*</em>代表必填项；<br>
        </div>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" enctype="multipart/form-data" action="" id="addLetterModel">
                <p>
                    <label><em style="color: red;">*</em>模板名称</label>
                    <span class="field"><input type="text" name="letterModel.modelName" id="modelName" class="longinput"/></span>
                </p>
                <p>
                    <label>排序值</label>
                    <span class="field"><input type = "text" value="0" name="letterModel.sort" class="longinput" id = "sort"/></span>
                </p> 
                <p>
                    <label>文件</label>
                    <span class="field"><input type = "file" value="0" name="letterModel.fileUrl" class="longinput" id = "fileUrl"/></span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit();return false;">添 加</button>
                </p>
            </form>
            <br/>
        </div>
    </div>
</div>
<script type="text/javascript">
    function addFormSubmit() {
      
        if(!jQuery("#modelName").val()){
            alert("请填写模板名称");
            return;
        }
        var fileUrl = jQuery("#fileUrl")[0].files[0];
        if (!fileUrl) {
            alert("请上传文件");
            return;
        }
        var formData = new FormData();
        formData.append("file", fileUrl);
        formData.append("modelName", jQuery("#modelName").val());
        formData.append("sort", jQuery("#sort").val());
        jQuery.ajax({
            url: "${ctx}/admin/oa/addLetterModel.json",
            data: formData,
            type: "post",
            dataType: "json",
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (result) {
                if (result.code == "0") {
                    alert(result.message);
                    window.location.href = "${ctx}/admin/oa/letterModelList.json";
                } else {
                    alert(result.message);
                }
            }
        });
    }
</script>
</body>
</html>