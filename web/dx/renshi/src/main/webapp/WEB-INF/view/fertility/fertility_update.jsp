<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>修改计生信息</title>
    <link type="text/css" rel="stylesheet" href='/static/uploadify/uploadify.css'/>
    <script type="text/javascript" src="/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="/static/uploadify/upload-file.js"></script>
    <script type="text/javascript" src="${ctx}/static/admin/js/fertility.js"></script>
    <script type="text/javascript">
        var fileServicePath = '${fileServicePath}';

        function callbackFile(data) {
            data = data.substr(2);
            data = data.substr(0, data.length - 2);
            jQuery("#fileUrl").val(data);
            jQuery("#file").html('已上传：' + jQuery(".fileName").html());
        }

        function upFile() {
            jQuery("#uploadFile").uploadify('upload');
        }

        jQuery(function () {
            uploadFile("uploadFile", false, "myFile", fileServicePath, callbackFile);
        });
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">计生信息修改</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来修改计生申请信息<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="updatefertility">
                <p>
                    <input type="hidden" name="fertility.id" id="id" value="${fertility.id}"/>
                    <label><em style="color: red;">*</em>相关文件:</label>
                    <span class="field">
						 <input type="hidden" name="fertility.url" id="fileUrl" value="${fertility.url}"/>
						 <input type="button" id="uploadFile" value="上传文件"/><a onclick="upFile()"
                                                                               href="javascript:void(0)">上传</a>
						 <tt id="file">${fertility.url}</tt>
					</span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>申请人姓名</label>
                    <span class="field"><input type="text" value="${fertility.name}" name="fertility.name" id="name"
                                               class="longinput"/></span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="updatefertility();return false;">修 改</button>
                    <input type="reset" class="reset radius2" value="返 回" onclick="javascript:history.go(-1);"/>
                </p>
            </form>
            <br/>
        </div><!--subcontent-->
    </div>
</div>

</body>
</html>