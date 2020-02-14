<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>添加投稿</title>
    <link type="text/css" rel="stylesheet" href='${ctx}/static/uploadify/uploadify.css'/>
    <script type="text/javascript" src="${ctx}/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/uploadify/upload-file.js"></script>
    <script type="text/javascript">
        var fileServicePath='${fileServicePath}';
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">投稿</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来进行投稿申请<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addContribute">
                <p>
                    <label><em style="color: red;">*</em>投稿名称</label>
                    <span class="field"><input type="text" name="contribute.name" id="name" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>上传申请表:</label>
					 <span class="field">
						 <input type="hidden" name="contribute.formUrl"   id="fileUrl" />
						 <input type="button" id="uploadFile" value="上传文件"/>
						 <%--<tt  id="file" style="color: red"></tt>--%>
                         <center><h4 id="file"></h4></center>
					</span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addContributeFormSubmit();return false;">添 加</button>
                    <input type="reset" class="reset radius2" value="重 置" onclick="resetData()"/>
                </p>
            </form>
            <br/>
        </div><!--subcontent-->
    </div><!--contentwrapper-->
</div><!-- centercontent -->
<script type="text/javascript" src="${ctx}/static/admin/js/contribute.js"></script>
<script type="text/javascript" >

</script>
</body>
</html>