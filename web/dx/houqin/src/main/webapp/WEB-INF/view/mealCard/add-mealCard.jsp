<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>添加饭卡</title>
    <script type="text/javascript">
        function addFormSubmit() {
            var name=jQuery("#name").val();
            if(name==null || name==''){
                alert("类型名称不能为空!");
                return;
            }
            var cardNumber=jQuery("#cardNumber").val();
            var password=jQuery("#password").val();
            var money=jQuery("#money").val();
            jQuery.ajax({
                url: "${ctx}/admin/houqin/addSaveMealCard.json",
                data: {
                    "mealCard.name": name,
                    "mealCard.cardNumber": cardNumber,
                    "mealCard.password": password,
                    "mealCard.money": money
                },
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code=="0") {
                        alert(result.message);
                        window.location.href = "/admin/houqin/queryAllMealCard.json";
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
        <h1 class="pagetitle">添加饭卡</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label><em style="color: red;">*</em>姓名</label>
                    <span class="field">
                        <input type="text" name="mealcard.name"  class="longinput" id="name"/>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>卡号</label>
                    <span class="field">
                        <input type="text" name="mealcard.cardNumber"  class="longinput" id="cardNumber"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>饭卡密码</label>
                    <span class="field">
                        <input type="password" name="mealcard.password"  class="longinput" id="password"/>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>类型金额</label>
                    <span class="field">
                        <input type="text" name="mealcard.money"  class="longinput"  id="money"/>
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