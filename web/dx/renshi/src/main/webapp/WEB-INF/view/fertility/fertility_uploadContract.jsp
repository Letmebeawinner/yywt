<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>合同上传</title>
    <link type="text/css" rel="stylesheet" href='/static/uploadify/uploadify.css'/>
    <script type="text/javascript" src="/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="/static/uploadify/upload-file.js"></script>
    <script type="text/javascript" src="${ctx}/static/admin/js/fertility.js"></script>
    <script type="text/javascript">
        var fileServicePath='${fileServicePath}';
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">合同上传</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来进行计生合同的上传<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="updatefertility">
                <p>
                    <input type="hidden" name="fertility.id" id="id" value="${fertility.id}"/>
                    <label><em style="color: red;">*</em>上传合同:</label>
					 <span class="field">
						 <input type="hidden" name="fertility.contractUrl"   id="fileUrl"/>
						 <input type="button" id="uploadFile" value="上传文件"/><a onclick="upFile()" href="javascript:void(0)">上传</a>
						 <tt  id="file" style="color: red"></tt>
					</span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="updatefertility();return false;">上 传</button>
                    <input type="reset" class="reset radius2" value="返 回" onclick="javascript:history.go(-1);"/>
                </p>
            </form>
            <br/>
        </div><!--subcontent-->
    </div>
</div>

</body>
</html>