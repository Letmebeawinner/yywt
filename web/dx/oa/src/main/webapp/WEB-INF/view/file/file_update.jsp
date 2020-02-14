<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>修改文件</title>
    <link type="text/css" rel="stylesheet" href='/static/uploadify/uploadify.css'/>
    <script type="text/javascript" src="/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="/static/uploadify/upload-file.js"></script>
    <script type="text/javascript">
        jQuery(function () {
            laydate.skin('molv');
            uploadFile("uploadFile", true, "myFile", imagePath, callbackFile);
        });

        function callbackFile(data) {
            data = data.substr(2);
            data = data.substr(0, data.length - 2);
            jQuery("#fileUrl").val(data);
            jQuery("#file").html('已上传：' + jQuery(".fileName").html());
        }

        function addFormSubmit() {
            var title = jQuery("#title").val();
            if (title == "" || title == null) {
                alert("请添加文件名称");
                return;
            }
            jQuery.ajax({
                url: "${ctx}/admin/oa/updateFile.json",
                data:jQuery('#addFormSubmit').serialize(),
                type: "post",
                dataType: "json",
                async: false,
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "${ctx}/admin/oa/queryAllFile.json";
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
        <h1 class="pagetitle">修改文件</h1>
    </div>
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于文件信息修改；<br>
        2.<em style="color: red;">*</em>代表必填项；<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                <input type="hidden" name="file.id" value="${file.id}">
                    <label><em style="color: red;">*</em>文件名称</label>
                    <span class="field">
                        <input type="text" name="file.title" id="title" class="longinput" value="${file.title}"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>文件地址</label>
               	 <span class="field">
						 <input type="hidden" name="file.fileUrl" value="${file.fileUrl}" id="fileUrl"/>
						 <input type="button" id="uploadFile" value="上传文件"/>
						 <center><h4 id="file">${file.fileUrl}</h4></center>
					</span>
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