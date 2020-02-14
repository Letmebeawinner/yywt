<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <c:if test = "${flag == 0}">
        <title>修改印章信息</title>
    </c:if>
    <c:if test = "${flag == 1}">
        <title>查看印章信息</title>
    </c:if>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <c:if test = "${flag == 0}">
            <h1 class="pagetitle">修改印章信息</h1>
        </c:if>
        <c:if test = "${flag == 1}">
            <h1 class="pagetitle">查看印章信息</h1>
        </c:if>
    </div><!--pageheader-->
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        <c:if test = "${flag == 0}">
            1.本页面用于印章信息修改；<br>
            2.<em style="color: red;">*</em>代表必填项；<br>
        </c:if>
        <c:if test = "${flag == 1}">
            1.本页面用于印章信息查看；<br>
            2.<em style="color: red;">*</em>代表必填项；<br>
        </c:if>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="updateSeal">
                <p>
                    <label><em style="color: red;">*</em>印章名称</label>
                    <span class="field"><input type="text" name="seal.name" id="name" class="longinput" value = "${seal.name}"/></span>
                </p>
                <input type="hidden" name="seal.id" value="${seal.id}">
                <p>
                    <label><em style="color: red;">*</em>印章用途</label>
                    <span class="field">
                        <select name = "seal.sealFunctionId" id = "sealFunctionId" class = "longinput">
                              <option value = "">请选择印章用途</option>
                            <c:forEach items = "${sealFunctions}" var = "sealFunction">
                                <option value = "${sealFunction.id}" <c:if test = "${seal.sealFunctionId == sealFunction.id}">selected = "selected"</c:if>>${sealFunction.name}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>印章类别</label>
                    <span class="field">
                        <select name = "seal.sealTypeId" id = "sealTypeId" class = "longinput">
                              <option value = "">请选择印章类别</option>
                            <c:forEach items = "${sealTypes}" var = "sealType">
                                <option value = "${sealType.id}" <c:if test = "${seal.sealTypeId == sealType.id}">selected = "selected"</c:if>>${sealType.name}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>
                <p>
                    <label>存放位置</label>
                    <span class="field"> <input type="text"  name="seal.address" class="longinput" value = "${seal.address}"/></span>
                </p>
                <p>
                    <label>备注</label>
                    <span class="field"><textarea cols="80" rows="5" name="seal.note"  class="longinput">${seal.note}</textarea></span>
                </p>
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
            alert("请添加印章名");
            return;
        }
        if(!jQuery("#sealTypeId").val()){
            alert("请选择印章类别");
            return;
        }
        if(!jQuery("#sealFunctionId").val()){
            alert("请选择印章用途");
            return;
        }
        var data = jQuery("#updateSeal").serialize();
        jQuery.ajax({
            url: "${ctx}/admin/oa/updateSeal.json",
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
    jQuery(function(){
        if (flag != 0) {
            jQuery(".longinput").attr("disabled", "disabled");
        }
    });
</script>
</body>
</html>