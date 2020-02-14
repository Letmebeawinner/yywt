<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>添加新闻类型</title>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">添加新闻类型</h1>
    </div><!--pageheader-->
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于新闻类型信息添加；<br>
        2.<em style="color: red;">*</em>代表必填项；<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">

            <form class="stdform stdform2" method="post" action="" id="addNewsType">
                <p>
                    <label><em style="color: red;">*</em>新闻类型名称</label>
                    <span class="field"><input type="text" name="newsType.name" id="name" class="longinput"/></span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit();return false;">保 存</button>
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
            alert("请添加新闻类型名称");
            return;
        }

        var data = jQuery("#addNewsType").serialize();
        jQuery.ajax({
            url: "${ctx}/admin/oa/addNewsType.json",
            data: data,
            type: "post",
            dataType: "json",
            async: false,
            success: function (result) {
                if (result.code == "0") {
                    alert(result.message);
                    window.location.href = "${ctx}/admin/oa/queryAllNewsType.json";
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