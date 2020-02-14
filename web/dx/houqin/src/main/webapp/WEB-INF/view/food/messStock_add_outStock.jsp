<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>食材库存添加出库</title>
    <script type="text/javascript">
        function addFormSubmit() {
            var name = jQuery("#name").val();
            var code = jQuery("#code").val();
            var price = jQuery("#price").val();
            var num = jQuery("#num").val();
            var number=jQuery("#number").val();
            var ids=jQuery("#ids").val();
            if (num == null || num == '') {
                alert(" 数量不能为空!");
                return;
            }

            if(num>number){
                alert("出库数量不能大于总库存量!");
                return;
            }
            var context = jQuery("#context").val();
            var typeId = jQuery("#typeId").val();
            var unitId = jQuery("#unitId").val();
            var source = jQuery("#source").val();
            jQuery.ajax({
                url: "${ctx}/admin/houqin/addSaveOutStockPlus.json",
                data: {
                    "outStock.typeId": typeId,
                    "outStock.unitId": unitId,
                    "outStock.name": name,
                    "outStock.code": code,
                    "outStock.price": price,
                    "outStock.num": num,
                    "outStock.source": source,
                    "outStock.context": context,
                    "id":ids
                },
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "/admin/houqin/queryOutStock.json";
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
                <input type="hidden" name="outStock.typeId" value="${messStock.id}" id="typeId">
                <input type="hidden" name="" value="${id}" id="ids">
                <p>
                    <label><em style="color: red;">*</em>食材名称</label>
                    <span class="field">
                        <input type="text" name="outStock.name" class="longinput" id="name" value="${messStock.name}" readonly="readonly"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>出库数量</label>
                    <span class="field">
                        <input type="text" name="outStock.num" class="longinput" id="num"/>库存剩余:<font style="color: red">${messStock.count}</font><input type="hidden" id="number"  value="${messStock.count}"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>作用于</label>
                    <span class="field">
                        <input type="text" name="outStock.source" class="longinput" id="source"/>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>备注</label>
                    <span class="field">
                      <textarea cols="80" rows="5" name="outStock.context" id="context" class="longinput">${messStock.content}</textarea>
                    </span>
                </p>

            </form>
            <p class="stdformbutton">
                <button class="submit radius2" onclick="addFormSubmit()">提交保存</button>
            </p>
        </div>
    </div>
</div>
</body>
</html>