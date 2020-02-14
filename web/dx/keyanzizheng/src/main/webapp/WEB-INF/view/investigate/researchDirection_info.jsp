<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>调研方向详情</title>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">调研方向</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addresearchDirection">
                <p>
                    <label>部门</label>
                    <span class="field"><input type="text" name="researchDirection.departmentName" id="departmentName" class="longinput" value="${researchDirection.departmentName}"/></span>
                </p>
                <p>
                    <label>调研方向名称</label>
                    <span class="field"><input type="text" name="researchDirection.name" id="name" class="longinput" value="${researchDirection.name}"/></span>
                </p>
                <p>
                    <label>调研方向简介
                        <small></small>
                    </label>
                    <span class="field"><textarea cols="80" rows="5" name="researchDirection.info" id="info"
                                                  class="longinput">${researchDirection.info}</textarea></span>
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