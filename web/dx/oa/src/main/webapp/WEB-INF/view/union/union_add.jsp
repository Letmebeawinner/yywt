<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>添加工会</title>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">新建工会处室</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来添加新的工会处室信息<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addunion">
                <p>
                    <label><em style="color: red;">*</em>处室名称</label>
                    <span class="field"><input type="text" name="union.name" id="name" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>联系人</label>
                    <span class="field"><input type="text"  name="union.contacts" id="contacts" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>联系方式</label>
                    <span class="field"><input type="text" name="union.mobile" id="mobile" class="longinput"/></span>
                </p>
                <p>
                    <label>处室简介</label>
                    <span class="field"><textarea cols="80" rows="5" name="union.info" id="info" class="longinput"></textarea></span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addunionFormSubmit();return false;">添 加</button>
                    <input type="reset" class="reset radius2" value="重 置" onclick="resetData()"/>
                </p>
            </form>
            <br/>
        </div><!--subcontent-->
    </div>
</div>
<script type="text/javascript">
    function addunionFormSubmit() {
        if(jQuery("#name").val()==""){
            alert("请添加处室名");
            return;
        }
        if(jQuery("#contacts").val()==""){
            alert("请添加联系人");
            return;
        }
        var date = jQuery("#addunion").serialize();
        jQuery.ajax({
            url: "${ctx}/admin/oa/addUnion.json",
            data: date,
            type: "post",
            dataType: "json",
            async: false,
            success: function (result) {
                if (result.code=="0") {
                    alert(result.message);
                    window.location.href = "${ctx}/admin/oa/getUnionList.json";
                } else {
                    alert(result.message);
                }
            }
        });
    }

    function resetData(){
        jQuery(".longinput").val("");
    }
</script>
</body>
</html>