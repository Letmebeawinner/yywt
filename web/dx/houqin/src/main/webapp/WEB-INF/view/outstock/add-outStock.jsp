<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>添加出库</title>
    <script type="text/javascript">
        function addFormSubmit() {

            var receiver=jQuery("#receiver").val();
            if(receiver==null||receiver==''){
                alert("领用人不能为空");
                return;
            }

            var num = jQuery("#num").val();
            var number = jQuery("#number").val();
            if (num == null || num == '') {
                alert(" 数量不能为空!");
                return;
            }

            if (parseInt(num) > parseInt(number)) {
                alert("出库数量不能大于总库存量!");
                return;
            }
            jQuery.ajax({
                url: "${ctx}/admin/houqin/addSaveOutStock.json",
                data: jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "/admin/houqin/queryStorehouse.json";
                    } else {
                        alert(result.message);
                        return;
                    }
                }
            });
        }

    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">物品出库</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于物品出库信息；<br>
            2.根据要求填写出库信息；<br>
            3.提交保存：点击<span style="color:red">提交保存</span>修改库存信息；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <input type = "hidden" value = "${storeHouse.id}" name = "storeId">
                <p>
                    <label>库房名称</label>
                    <span class="field">
                        ${storage.name}
                        <input type="hidden" name="outStock.storageId" class="longinput" value="${storage.id}" readonly="readonly"/>
                    </span>
                </p>



                <input type="hidden" name="outStock.typeId" value="${storeHouse.typeId}" id="typeId">
                <input type="hidden" name="outStock.unitId" class="longinput" id="unitId" value="${storeHouse.unitId}"/>
                <p>
                    <label><em style="color: red;">*</em>商品编号</label>
                    <span class="field">
                       ${storeHouse.code}
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>领用人</label>
                    <span class="field">
                        <input type="text" name="outStock.receiver" class="longinput" id="receiver"/>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>商品名称</label>
                    <span class="field">
                        ${storeHouse.name}
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>出库数量</label>
                    <span class="field">
                        <input type="number" name="outStock.num" min="1" step="1" max = "${storeHouse.num}" id="num" onkeyup='this.value=this.value.replace(/\D/gi,"")'>
                        库存剩余:<font style="color: red">${storeHouse.num}</font>
                        <input type="hidden" id="number" value="${storeHouse.num}"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>出库类型</label>
                    <span class="field">
                       <select name="outStock.outStockType" id="outStockType">
                           <option value="0">--请选择--</option>
                           <c:forEach items="${storageTypeList}" var="type">
                               <option value="${type.id}">--${type.name}--</option>
                           </c:forEach>
                       </select>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>价格</label>
                    <span class="field">
                        <input type="text" name="outStock.price" class="longinput" id="price"
                               value="${storeHouse.price}"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>用途</label>
                    <span class="field">
                        <input type="text" name="outStock.source" class="longinput" id="source"/>
                    </span>
                </p>

                <p>
                    <label>备注</label>
                    <span class="field">
                      <textarea cols="80" rows="5" name="outStock.context" id="context" class="longinput"></textarea>
                    </span>
                </p>

                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit();return false;">提交保存</button>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
</html>