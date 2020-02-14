<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>修改动态教学文件</title>
    <link type="text/css" rel="stylesheet" href='/static/uploadify/uploadify.css'/>
    <script type="text/javascript" src="/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="/static/uploadify/upload-file.js"></script>
    <script type="text/javascript">
        jQuery(function () {
            laydate.skin('molv');
            uploadFile("uploadFile", false, "myFile", imagePath, callbackFile);
        });

        function callbackFile(data) {
            data = data.substr(2);
            data = data.substr(0, data.length - 2);
            jQuery("#fileUrl").val(data);

        }
        function upFile() {
            jQuery("#file").html('已上传：' + jQuery(".fileName").html());
            jQuery("#fileName").val(jQuery(".fileName").html());
            jQuery("#uploadFile").uploadify('upload');
        }

        function addFormSubmit() {
            var title = jQuery("#title").val();
            if (title == null || title.trim() == '') {
                alert("请填写文件标题");
                return;
            }
            var fileUrl = jQuery("#fileUrl").val();
            if (fileUrl == null || fileUrl.trim() == '') {
                alert("请上传文件");
                return;
            }
            var index = fileUrl.lastIndexOf(".");
            var suffix = fileUrl.substr(index+1);
            if(suffix!='xlsx' && suffix!='xls'){
                alert("请上传Excel文件");
                return;
            }
            jQuery.ajax({
                url: "${ctx}/admin/jiaowu/excel/update.json",
                data:jQuery('#updateFormSubmit').serialize(),
                type: "post",
                dataType: "json",
                async: false,
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "${ctx}/admin/jiaowu/excel/uploadExcelHistoryList.json";
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
        <h1 class="pagetitle">修改上传文件</h1>
    </div>
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于动态教学文件修改；<br>
        2.<em style="color: red;">*</em>代表必填项；<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">

            <form class="stdform stdform2" method="post" action="" id="updateFormSubmit">
                <p>
                    <input type="hidden" name="uploadExcelHistory.id" value="${uploadExcelHistory.id}">
                    <input type="hidden" name="uploadExcelHistory.fileName" id="fileName" value="${uploadExcelHistory.fileName}"/>
                    <label><em style="color: red;">*</em>文件标题</label>
                    <span class="field">
                        <input type="text" name="uploadExcelHistory.title" id="title" class="longinput" value="${uploadExcelHistory.title}"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>上传Excel:</label>
                    <span class="field">
                             <input type="hidden" name="uploadExcelHistory.fileUrl" value="${uploadExcelHistory.fileUrl}" id="fileUrl"/>
                             <input type="button" id="uploadFile" value="上传Excel"/>
                             <a onclick="upFile()" href="javascript:void(0)">上传</a>
                             <center><h4  id="file">${uploadExcelHistory.fileUrl}</h4></center>
                        </span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit(); return false">提交保存</button>
                </p>
            </form>
            <br/>
        </div>
    </div>
</div>
</body>
</html>