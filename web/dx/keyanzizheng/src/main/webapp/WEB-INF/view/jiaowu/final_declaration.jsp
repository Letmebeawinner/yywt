<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>结项申报</title>
    <link type="text/css" rel="stylesheet" href='/static/uploadify/uploadify.css'/>
    <script type="text/javascript" src="/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="/static/uploadify/upload-file.js"></script>

    <script type="text/javascript">
        jQuery(function () {
            uploadFile("uploadFile", true, "myFile", imagePath, callbackFile);
        });

        function callbackFile(data){
            data=data.substr(2);
            data=data.substr(0,data.length-2);
            jQuery("#fileUrl").val(data);
            jQuery("#file").html( '已上传：'+jQuery(".fileName").html());
        }

        function problemStatementDeclaration() {
            var  param = jQuery("#bill").serialize()
            jQuery.ajax({
                url: '/admin/ky/problemStatementDeclaration/add.json',
                type: 'post',
                data: param,
                dataType: 'json',
                success: function (result) {
                    alert(result.message)
                    location.href = document.referrer
                }
            });
        }
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">课题结项申报</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来部门课题结项申报<%--，其中审批文件为保留部门批示笔记的文件（照片等形式）--%><br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="bill">
                <p>
                    <label><em style="color: red;">*</em>课题结项申报:</label>
                    <span class="field">
						 <input type="hidden" name="fileUrlDeclaration"   id="fileUrl" />
						 <input type="button" id="uploadFile" value="上传课题结项申报"/>
						 <center><h4  id="file"></h4></center>
					</span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="problemStatementDeclaration();return false;">添 加</button>
                </p>
                <input type="hidden" id="resultId" name="resultId" value="${resultId}">
            </form>
            <br/>
        </div><!--subcontent-->
    </div><!--contentwrapper-->
</div><!-- centercontent -->
</body>
</html>