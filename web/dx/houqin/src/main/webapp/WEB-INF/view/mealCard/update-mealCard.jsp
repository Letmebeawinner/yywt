<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>修改饭卡</title>
    <script type="text/javascript">
        function addFormSubmit() {
            jQuery("#addFormSubmit").submit();
        }

    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">修改饭卡</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="${ctx}/admin/houqin/updateMealCard.json" id="addFormSubmit">
                <input type="hidden" value="${mealcard.id}" name="mealcard.id">
                <p>
                    <label><em style="color: red;">*</em>姓名</label>
                    <span class="field">
                        <input type="text" name="mealcard.name"  class="longinput" id="name" value="${mealcard.name}"/>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>卡号</label>
                    <span class="field">
                        <input type="text" name="mealcard.cardNumber"  class="longinput" id="cardNumber" value="${mealcard.cardNumber}"/>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>类型金额</label>
                    <span class="field">
                        <input type="text" name="mealcard.money"  class="longinput"  id="money" value="${mealcard.money}"/>
                    </span>
                </p>
                <p class="stdformbutton">
                    <button class="submit radius2" onclick="addFormSubmit()">提交保存</button>
                    <input type="reset" class="reset radius2" value="重置表单"/>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
</html>