<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>增加模型</title>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">增加模型</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于模型信息查看；<br>
            2.<em style="color: red;">*</em>代表必填项；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="${ctx}/admin/oa/addModel.json" id="addModel">
                <p>
                    <label><em style="color: red;">*</em>名称</label>
                    <span class="field"><input type="text" name="name" id="name" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>KEY</label>
                    <span class="field"><input type = "text" name="key" class="longinput" id = "key"/></span>
                </p>
                <p>
                    <label>描述</label>
                    <span class="field"><textarea cols="80" rows="5" name="description"  class="longinput"></textarea></span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit();return false;">添 加</button>
                    <input type="reset" class="reset radius2" value="重 置" onclick="resetData()"/>
                </p>
            </form>
            <br/>
        </div><!--subcontent-->
    </div>
</div>
<script type="text/javascript">
    function addFormSubmit() {
        if(!jQuery("#name").val()){
            alert("请添加模型名");
            return;
        }
        if(!jQuery("#key").val()){
            alert("请添加关键词");
            return;
        }
        jQuery('#addModel').submit();
    }

    function resetData(){
        jQuery(".longinput").val("");
    }
</script>
</body>
</html>