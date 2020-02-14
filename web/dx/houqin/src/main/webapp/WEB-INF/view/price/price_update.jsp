<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>修改单价名称</title>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">修改单价名称</h1>
    </div><!--pageheader-->
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
            1.本页面用于修改单价名称信息；<br>
            2.<em style="color: red;">*</em>代表必填项；<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addPrice">
                <p>
                    <label><em style="color: red;">*</em>单价名称</label>
                    <span class="field"><input type="text" name="price.name" id="name" class="longinput" value = "${price.name}"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>类型</label>
                    <span class="field"><select name = "price.type" id = "type" class = "longinput">

                            <option value = "">未选择</option>
                            <option value = "WATER"  <c:if test="${price.type == 'WATER'}">selected = selected</c:if>>水费</option>
                            <option value = "ELECTRICITY"  <c:if test="${price.type == 'ELECTRICITY'}">selected = selected</c:if>>电费</option>
                            <option value = "NATURAL" <c:if test="${price.type == 'NATURAL'}">selected = selected</c:if>>天然气费</option>
                        </select>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>内部单价</label>
                    <span class="field"><input type = "text" name="price.inwardPrice" value="${price.inwardPrice}" class="longinput" id = "inwardPrice"/></span>
                </p>
                <p>
                    <label>外部单价</label>
                    <span class="field"><input type = "text" name="price.exteriorPrice" value="${price.exteriorPrice}" class="longinput" id = "exteriorPrice"/></span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit();return false;">修 改</button>
                    <input type="reset" class="reset radius2" value="重 置" onclick="resetData()"/>
                </p>
            </form>
            <br/>
        </div>
    </div>
</div>
<script type="text/javascript">
    function addFormSubmit() {
        if (!jQuery('#name').val()) {
            alert("名字不能为空");
            return;
        }
        if (!jQuery("#type").val()) {
            alert("类型不能为空");
            return;
        }
        var inwardPrice = jQuery('#inwardPrice').val();
        var reg = /^\d+(\.\d+)?$/;
        if (!reg.test(inwardPrice)) {
            alert("请输入正确的内部水电单价");
            return;
        }

        var data = jQuery("#addPrice").serialize();
        jQuery.ajax({
            url: "${ctx}/admin/houqin/ajax/price/update.json",
            data: data,
            type: "post",
            dataType: "json",
            async: false,
            cache : false,
            success: function (result) {
                if (result.code == "0") {
                    alert(result.message);
                    window.location.href = "${ctx}/admin/houqin/price/list.json";
                } else {
                    alert(result.message);
                }
            }
        });
    }

    function resetData(){
        jQuery(".longinput").val("");
        jQuery(".hasDatepicker").val("");
    }
</script>
</body>
</html>