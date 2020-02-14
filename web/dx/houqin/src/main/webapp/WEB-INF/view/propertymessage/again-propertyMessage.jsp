<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>添加入库</title>
    <script type="text/javascript">
        function addFormSubmit() {

            var amount = jQuery("#amount").val();
            if (amount == null || amount == 0) {
                alert("请填写数量!");
                return;
            }
            var price = jQuery("#price").val();
            if (price == null || price == 0) {
                alert("请填写总价!");
                return;
            }

            jQuery.ajax({
                url: "${ctx}/admin/houqin/againPropertyMessage.json",
                data: jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "/admin/houqin/queryAllPropertyMessage.json";
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
        <h1 class="pagetitle">添加入库</h1>
        <div style="margin-left: 20px;">
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">

                <p>
                    <input type="hidden" name="propertyMessage.id" value="${propertyMessage.id}">
                    <label><em style="color: red;">*</em>数量</label>
                    <span class="field">
                         <input type="text" name="propertyMessage.amount" class="longinput" id="amount"
                                onkeyup="if(/\D/.test(this.value)){this.value='';}"
                                maxlength="8"
                         /> 库存剩余:<font style="color: red">${propertyMessage.amount}</font>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>金额</label>
                    <span class="field">
                        <input type="text" name="propertyMessage.price" class="longinput" id="price" maxlength="8"/>
                    </span>
                </p>
                <p class="stdformbutton" style="text-align: center;">
                    <button class="submit radius2" onclick="addFormSubmit(); return false;">提交保存</button>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
</html>