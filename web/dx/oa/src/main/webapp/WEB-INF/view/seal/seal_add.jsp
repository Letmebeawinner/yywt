<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>添加印章</title>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">添加印章</h1>
    </div>
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于印章信息添加；<br>
        2.<em style="color: red;">*</em>代表必填项；<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addSeal">
                <p>
                    <label><em style="color: red;">*</em>印章名称</label>
                    <span class="field"><input type="text" name="seal.name" id="name" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>印章用途</label>
                    <span class="field">
                        <select name = "seal.sealFunctionId" id = "sealFunctionId">
                            <option value = "">请选择印章用途</option>
                            <c:forEach items = "${sealFunctions}" var = "sealFunction">
                                <option value = "${sealFunction.id}">${sealFunction.name}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>印章类别</label>
                    <span class="field">
                        <select name = "seal.sealTypeId" id = "sealTypeId">
                            <option value = "">请选择印章类别</option>
                            <c:forEach items = "${sealTypes}" var = "sealType">
                                <option value = "${sealType.id}">${sealType.name}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>
                <p>
                    <label>存放位置</label>
                    <span class="field"> <input type="text"  name="seal.address" class="longinput"/></span>
                </p>
                <p>
                    <label>备注</label>
                    <span class="field"><textarea cols="80" rows="5" name="seal.note"  class="longinput"></textarea></span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit();return false;">保 存</button>
                    <input type="reset" class="reset radius2" value="重 置" onclick="resetData()"/>
                </p>
            </form>
            <br/>
        </div>
    </div>
</div>
<script type="text/javascript">
    function addFormSubmit() {
        if(!jQuery("#name").val()){
            alert("请添加印章名");
            return;
        }
        if(!jQuery("#sealFunctionId").val()){
            alert("请选择印章用途");
            return;
        }
        if(!jQuery("#sealTypeId").val()){
            alert("请选择印章类别");
            return;
        }
        var data = jQuery("#addSeal").serialize();
        jQuery.ajax({
            url: "${ctx}/admin/oa/addSeal.json",
            data: data,
            type: "post",
            dataType: "json",
            async: false,
            cache : false,
            success: function (result) {
                if (result.code == "0") {
                    alert(result.message);
                    window.location.href = "${ctx}/admin/oa/queryAllSeal.json";
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