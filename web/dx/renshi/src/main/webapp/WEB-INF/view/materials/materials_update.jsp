<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>修改物资信息</title>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">物资信息修改</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来修改入库工会物资的信息<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="updatematerials">
                <p>
                    <input type="hidden" name="materials.id" id="id" value="${materials.id}"/>
                    <label><em style="color: red;">*</em>物资名称</label>
                    <span class="field"><input type="text"  value="${materials.material}" name="materials.material" id="material" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>入库数量</label>
                    <span class="field"><input type="text" onkeyup='this.value=this.value.replace(/\D/gi,"")' value="${materials.number}" name="materials.number" id="number" class="longinput"/></span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="updatematerials();return false;">修 改</button>
                    <input type="reset" class="reset radius2" value="返 回" onclick="javascript:history.go(-1);"/>
                </p>
            </form>
            <br/>
        </div><!--subcontent-->
    </div>
</div>
<script type="text/javascript">
    function updatematerials() {
        if(jQuery("#material").val()==""){
            alert("请添加物资名");
            return;
        }
        if(jQuery("#number").val()==""){
            alert("请添加入库数量");
            return;
        }
        var date = jQuery("#updatematerials").serialize();
        jQuery.ajax({
            url: "${ctx}/admin/rs/updateMaterials.json",
            data: date,
            type: "post",
            dataType: "json",
            async: false,
            success: function (result) {
                if (result.code=="0") {
                    alert(result.message);
                    window.location.href = "${ctx}/admin/rs/getMaterialsList.json";
                } else {
                    alert(result.message);
                }
            }
        });
    }
</script>
</body>
</html>