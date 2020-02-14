<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <c:if test = "${flag == 0}">
        <title>修改印章用途</title>
    </c:if>
    <c:if test = "${flag == 1}">
        <title>查看印章用途</title>
    </c:if>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <c:if test = "${flag == 0}">
            <h1 class="pagetitle">修改印章用途</h1>
        </c:if>
        <c:if test = "${flag == 1}">
            <h1 class="pagetitle">查看印章用途</h1>
        </c:if>
    </div>
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        <c:if test = "${flag == 0}">
            1.本页面用于印章信息用途修改；<br>
            2.<em style="color: red;">*</em>代表必填项；<br>
        </c:if>
        <c:if test = "${flag == 1}">
            1.本页面用于印章信息用途查看；<br>
            2.<em style="color: red;">*</em>代表必填项；<br>
        </c:if>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="updateSealFunction">
                <p>
                    <label><em style="color: red;">*</em>印章用途名称</label>
                    <span class="field"><input type="text" name="sealFunction.name" id="name" class="longinput" value = "${sealFunction.name}"/></span>
                </p>
                <input type="hidden" name="sealFunction.id" value="${sealFunction.id}">
                <c:if test="${flag == 0}">
                    <p class="stdformbutton" style="text-align: center">
                        <button class="submit radius2" onclick="addFormSubmit();return false;">保 存</button>
                        <input type="reset" class="reset radius2" value="重 置" onclick="resetData()"/>
                    </p>
                </c:if>
            </form>

            <br/>
        </div>
    </div>
</div>
<script type="text/javascript">
    var flag = '${flag}';
    function addFormSubmit() {
        if(!jQuery("#name").val()){
            alert("请添加印章用途名");
            return;
        }
        var data = jQuery("#updateSealFunction").serialize();
        jQuery.ajax({
            url: "${ctx}/admin/oa/updateSealFunction.json",
            data: data,
            type: "post",
            dataType: "json",
            async: false,
            cache : false,
            success: function (result) {
                if (result.code == "0") {
                    alert(result.message);
                    window.location.href = "${ctx}/admin/oa/queryAllSealFunction.json";
                } else {
                    alert(result.message);
                }
            }
        });
    }

    function resetData(){
        jQuery(".longinput").val("");
    }
    jQuery(function(){
        if (flag != 0) {
            jQuery(".longinput").attr("disabled", "disabled");
        }
    });
</script>
</body>
</html>