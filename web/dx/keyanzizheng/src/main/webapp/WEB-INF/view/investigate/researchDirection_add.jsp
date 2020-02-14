<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>添加调研方向</title>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">调研方向</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来新建调研方向信息<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>

    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addResearchDirection">

                <p>
                    <label> <em style="color: red;">*</em>申报部门</label>
                    <span class="field">
                    <input type="text" name="researchDirection.departmentName" id="departmentName" class="longinput"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>调研方向名称</label>
                    <span class="field"><input type="text" name="researchDirection.name" id="name" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>调研方向简介
                        <small></small>
                    </label>
                    <span class="field"><textarea cols="80" rows="5" name="researchDirection.info" id="info"
                                                  class="longinput"></textarea></span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addResearchDirectionFormSubmit();return false;">添 加</button>
                    <input type="reset" class="reset radius2" value="重 置" onclick="resetData()"/>
                </p>
            </form>
            <br/>
        </div><!--subcontent-->
    </div><!--contentwrapper-->
</div><!-- centercontent -->
<script type="text/javascript" src="${ctx}/static/admin/js/researchDirection.js"></script>
</body>
</html>