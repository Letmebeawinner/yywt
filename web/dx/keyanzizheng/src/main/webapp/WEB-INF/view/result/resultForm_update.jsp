<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>编辑成果形式</title>
    <script type="text/javascript" src="${ctx}/static/admin/js/result.js"></script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">成果形式</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来进行添加成果形式<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addResultForm">
                <p>
                    <input type="hidden" name="resultForm.id" id="id" value="${resultForm.id}"/>
                    <label><em style="color: red;">*</em>成果形式名称</label>
                    <span class="field"><input type="text" name="resultForm.name" value="${resultForm.name}" id="name"/></span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="resultFormFromSubmit();return false;">修 改</button>
                    <input type="reset" class="reset radius2" value="重 置" onclick="resetData()"/>
                </p>
            </form>
            <br/>
        </div>
    </div>
</div>
</body>
</html>