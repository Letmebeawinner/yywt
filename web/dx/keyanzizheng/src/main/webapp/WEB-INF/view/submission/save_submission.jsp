<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>投稿申请</title>
    <style>
        em {
            color: red;
        }
    </style>
    <link type="text/css" rel="stylesheet" href='${ctx}/static/uploadify/uploadify.css'/>
    <script type="text/javascript" src="${ctx}/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/uploadify/upload-file.js"></script>
    <script type="text/javascript" src="${ctx}/static/admin/js/submission.js"></script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">投稿申请</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来进行申请投稿。<br>
                2. 带有红色 <span style="color: red">*</span> 标记的内容为必填部分。<br>
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="saveForm">

                <p>
                    <label><em>*</em> 投稿名称</label>
                    <span class="field"><input type="text" name="submission.name" maxlength="225"
                                               class="longinput"/></span>
                </p>

                <p>
                    <label> <em>*</em> 类型</label>
                    <span class="field">
                        <input type="radio" value="1" name="submission.typeId" checked/><span>贵阳决策咨询</span> &nbsp;
                        <input type="radio" value="2" name="submission.typeId"/><span>生态文明参阅</span>
                    </span>
                </p>

                <p>
                    <label><em>*</em>上传申请表:</label>
                    <span class="field">
						 <input type="hidden" name="submission.url" id="fileUrl"/>
						 <input type="button" id="uploadFile" value="上传文件"/>
                         <center><h4 id="file"></h4></center>
					</span>
                </p>

                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="saveForm();return false;">添 加</button>
                    <input type="reset" class="reset radius2" value="重 置"/>
                </p>

                <input type="hidden" name="submission.typeName" id="typeName">
            </form>
            <br/>
        </div><!--subcontent-->
    </div><!--contentwrapper-->
</div><!-- centercontent -->
</body>
</html>