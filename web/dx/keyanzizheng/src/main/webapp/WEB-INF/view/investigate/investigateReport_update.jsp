<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>修改调研报告</title>
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
        <h1 class="pagetitle">调研报告</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来进行调研方向的调研报告申报信息修改<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="upInvestigateReport">
                <input type="hidden" name="investigateReport.id" id="id" class="longinput" value="${investigateReport.id}"/>
                <p>
                    <label><em style="color: red;">*</em>调研报告名称</label>
                    <span class="field"><input type="text" name="investigateReport.name" id="name" class="longinput" value="${investigateReport.name}"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>相关资料上传:</label>
					 <span class="field">
						 <input type="hidden" name="investigateReport.relatedUrl"   id="fileUrl" value="${investigateReport.relatedUrl}"/>
						 <input type="button" id="uploadFile" value="上传文件"/><a onclick="upFile()" href="javascript:void(0)">上传</a>
						 <tt  id="file" style="color: red">${investigateReport.relatedUrl}</tt>
					</span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>简介
                        <small></small>
                    </label>
                    <span class="field"><textarea cols="80" rows="5" name="investigateReport.info" id="info"
                                                  class="longinput">${investigateReport.info}</textarea></span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="updateInvestigateReport();return false;">修 改</button>
                    <input type="reset" class="reset radius2" value="返 回" onclick="javascript:history.go(-1);"/>
                </p>
            </form>
            <br/>
        </div><!--subcontent-->
    </div><!--contentwrapper-->
</div><!-- centercontent -->
<script type="text/javascript" src="${ctx}/static/admin/js/investigate.js"></script>
</body>
</html>