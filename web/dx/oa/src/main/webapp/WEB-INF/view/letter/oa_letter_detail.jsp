<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>查看详情</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/kindeditor/themes/default/default.css"/>
    <script type="text/javascript" src="${ctx}/kindeditor/kindeditor-all.js"></script>
    <script type="text/javascript">
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">修改公文信息</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于修改公文信息；<br>
            2.<em style="color: red;">*</em>代表必填项；<br>
        </div>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="updateLetter">
                <p>
                    <input type="hidden" name="letter.id" value="${letter.id}">
                    <label><em style="color: red;">*</em>公文名称</label>
                    <span class="field"><input type="text" name="letter.title" id="name" class="longinput"
                                               value="${letter.title}"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>公文模版类型</label>
                    <span class="field">
                        <select name="letter.typeId" id="typeId">
                            <option value="1" <c:if test="${letter.typeId==1}">selected</c:if>>中共贵阳市委党校文件</option>
                            <option value="2" <c:if test="${letter.typeId==2}">selected</c:if>>贵阳社会主义学院</option>
                            <option value="3" <c:if test="${letter.typeId==3}">selected</c:if>>校委会议纪要</option>
                            <option value="4" <c:if test="${letter.typeId==4}">selected</c:if>>工作简报</option>
                        </select>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>公文内容</label>
                    <span class="field">
                        <textarea cols="10" rows="50" id="content" name="letter.context">
                            ${letter.context}
                        </textarea>
                    </span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit();return false;">保 存</button>
                </p>
            </form>
            <br/>
        </div>
    </div>
</div>

</body>
</html>