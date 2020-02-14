<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>修改投稿</title>
    <link type="text/css" rel="stylesheet" href='/static/uploadify/uploadify.css'/>
    <script type="text/javascript" src="/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="/static/uploadify/upload-file.js"></script>
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
                1. 本页面用来修改投稿申请信息<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="updateContribute">
                <input type="hidden" name="contribute.id" id="id" class="longinput" value="${contribute.id}"/>
                <p>
                    <label><em style="color: red;">*</em>投稿名称</label>
                    <span class="field"><input type="text" name="contribute.name" value="${contribute.name}" id="name" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>上传申请表:</label>
					 <span class="field">
						 <input type="hidden" name="contribute.formUrl"   id="fileUrl" value="${contribute.formUrl}"/>
						 <input type="button" id="uploadFile" value="上传文件"/>
                         <center><h4 id="file"></h4></center>
					</span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="updateContribute();return false;">修 改</button>
                    <input type="reset" class="reset radius2" value="返 回" onclick="javascript:history.go(-1);"/>
                </p>
            </form>
            <br/>
        </div><!--subcontent-->
    </div><!--contentwrapper-->
</div><!-- centercontent -->
<script type="text/javascript" src="${ctx}/static/admin/js/contribute.js"></script>
<script type="text/javascript" >
    /**
     * 时间控件
     */
    jQuery(function () {
        laydate.skin('molv');
        laydate({
            elem: '#time',
            format: 'YYYY-MM-DD hh:mm:ss'
        });
    });
</script>
</body>
</html>