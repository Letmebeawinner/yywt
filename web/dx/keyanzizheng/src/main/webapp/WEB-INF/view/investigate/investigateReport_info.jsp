<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>调研报告详情</title>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">调研报告</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addinvestigateReport">
                <p>
                    <label>调研报告名称</label>
                    <span class="field"><input type="text" name="investigateReport.researchName" id="researchName" class="longinput" value="${investigateReport.researchName}"/></span>
                </p>
                <p>
                    <label>调研报告名称</label>
                    <span class="field"><input type="text" name="investigateReport.name" id="name" class="longinput" value="${investigateReport.name}"/></span>
                </p>
                <p>
                    <label>相关资料上传</label>
                    <span class="field"><input type="text" value="${investigateReport.relatedUrl}" name="investigateReport.relatedUrl" id="relatedUrl" class="longinput"/></span>
                </p>
                <p>
                    <label>简介
                        <small></small>
                    </label>
                    <span class="field"><textarea cols="80" rows="5" name="investigateReport.info" id="info"
                                                  class="longinput">${investigateReport.info}</textarea></span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <input type="reset" class="reset radius2" value="返 回" onclick="javascript:history.go(-1);"/>
                </p>
            </form>
            <br/>
        </div><!--subcontent-->
    </div><!--contentwrapper-->
</div><!-- centercontent -->
</body>
</html>