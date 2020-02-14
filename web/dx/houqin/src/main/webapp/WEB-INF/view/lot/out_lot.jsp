<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>出库</title>
    <script type="text/javascript">
        function addFormSubmit() {
            var amount = jQuery("input[name='amount']").val()
            if (!Number(amount)) {
                alert("请输入正确的数量")
                return false
            }
            var original = parseInt(jQuery("input[name='original']").val())
            if (parseInt(amount) > original) {
                alert("出库数量不能大于总库存量")
                return false
            }

            var manager = jQuery("input[name='lotRecord.manager']").val()
            if (manager.length < 1) {
                alert("经办人不能为空")
                return false
            }
            var outboundPerson = jQuery("input[name='lotRecord.outboundPerson']").val()
            if (outboundPerson.length < 1) {
                alert("出库人不能为空")
                return false
            }
            var receiver = jQuery("input[name='lotRecord.receiver']").val()
            if (receiver.length < 1) {
                alert("接受人不能为空")
                return false
            }
            jQuery("input[name='lot.lotAmount']").val(original - parseInt(amount));

            var messStockId = jQuery("input[name='lot.messStockId']").val();

            var params = jQuery("#saveForm").serialize();
            jQuery.ajax({
                url: "${ctx}/admin/houqin/lot/outbound.json",
                data: params,
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "/admin/houqin/lot/list/" + messStockId + ".json";
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
        <h1 class="pagetitle">出库</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于出库；<br>
            2.根据要求填写出库信息；<br>
            3.提交保存：点击<span style="color:red">提交保存</span>修改库存信息；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="saveForm">
                <p>
                    <label><em style="color: red;">*</em>出库数量</label>
                    <span class="field">
                        <input type="text" name="amount" class="longinput"/>
                        库存剩余:<font style="color: red">${lot.lotAmount}</font>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>经办人</label>
                    <span class="field">
                        <input type="text" name="lotRecord.manager" class="longinput"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>出库人</label>
                    <span class="field">
                        <input type="text" name="lotRecord.outboundPerson" class="longinput"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>接受人</label>
                    <span class="field">
                        <input type="text" name="lotRecord.receiver" class="longinput"/>
                    </span>
                </p>
                <input type="hidden" name="lot.id" value="${lot.id}">
                <input type="hidden" name="lot.messStockId" value="${lot.messStockId}">
                <input type="hidden" name="original" value="${lot.lotAmount}">
                <input type="hidden" name="lot.lotAmount" value="">
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit(); return false">提交保存</button>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
</html>