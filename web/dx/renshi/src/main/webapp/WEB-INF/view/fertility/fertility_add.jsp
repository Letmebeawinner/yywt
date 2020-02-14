<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>计生管理</title>
    <link type="text/css" rel="stylesheet" href='/static/uploadify/uploadify.css'/>
    <script type="text/javascript" src="/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="/static/uploadify/upload-file.js"></script>
    <script type="text/javascript" src="${ctx}/static/admin/js/fertility.js"></script>
    <script type="text/javascript">
        var fileServicePath='${fileServicePath}';
        function callbackFile(data){
            data=data.substr(2);
            data=data.substr(0,data.length-2);
            jQuery("#fileUrl").val(data);
            jQuery("#file").html( '已上传：'+jQuery(".fileName").html());
        }
        function upFile(){
            jQuery("#uploadFile").uploadify('upload');
        }
        jQuery(function() {
            uploadFile("uploadFile", false, "myFile", fileServicePath, callbackFile);
        });
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">计生申请</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来进行计生申请<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addfertility">
                <p>
                    <label><em style="color: red;">*</em>相关文件:</label>
					 <span class="field">
						 <input type="hidden" name="fertility.url"   id="fileUrl" />
						 <input type="button" id="uploadFile" value="上传文件"/><a onclick="upFile()" href="javascript:void(0)">上传</a>
						 <tt  id="file" style="color: red"></tt>
					</span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>申请人姓名</label>
                    <span class="field"><input type="text"  name="fertility.name" id="name" class="longinput"/></span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addfertilityFormSubmit();return false;">添 加</button>
                    <input type="reset" class="reset radius2" value="重 置" onclick="resetData()"/>
                </p>
            </form>
            <br/>
        </div>
    </div>
</div>

</body>
</html>