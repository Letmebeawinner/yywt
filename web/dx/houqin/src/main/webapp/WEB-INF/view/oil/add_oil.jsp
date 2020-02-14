<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>添加柴油用量</title>
    <script type="text/javascript">
        function addFormSubmit() {
            var litre = jQuery.trim(jQuery("#litre").val());
            var reg = new RegExp("^[1-9]\d*\.\d*|0\.\d*[1-9]\d*$");
            if (!reg.test(litre)) {
                alert("柴油用量只能为数字,请重新填写");
                return;
            }

            var price = jQuery("#price").val();
            if (price == null || price == "") {
                alert("请填写油单价");
            }

            var oilType = jQuery("#oilType").val();
            if (oilType == 0) {
                alert("请选择油类型");
                return;
            }

            var purpose = jQuery("#purpose").val();
            if (purpose == 0) {
                alert("请选择有用途");
                return;
            }

            jQuery.ajax({
                url: "${ctx}/admin/houqin/addOil.json",
                data: jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "/admin/houqin/queryAllOil.json";
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
        <h1 class="pagetitle">添加油用量</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于添加油用量；<br>
            2.添加油用量：按要求填写相关信息,点击<span style="color:red">提交保存</span>按钮；添加油用量<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label><em style="color: red;">*</em>单位(升)</label>
                    <span class="field">
                        <input type="text" name="oil.litre" class="longinput" id="litre" maxlength="10"/>
                    </span>
                </p>

                <p>
                    <label><em style="color: red">*</em>油单价(升)</label>
                    <span class="field">
                        <input type="text" name="oil.price" class="longinput" id="price">
                    </span>
                </p>
                <p>
                    <label><em style="color: red">*</em>油类型</label>
                    <span class="field">
                        <select name="oil.oilType" id="oilType">
                           <option value="0">请选择</option>
                            <c:forEach items="${oilTypeList}" var="oilType">
                                <option value="${oilType.id}">${oilType.name}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>

                <p>
                    <label><em style="color: red">*</em>油用途</label>
                    <span class="field">
                        <select name="oil.purpose" id="purpose">
                            <option value="0">请选择</option>
                            <c:forEach items="${oilUseList}" var="oilUse">
                                <option value="${oilUse.id}">${oilUse.name}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>外界用能类型</label>
                    <span class="field">
                        <select name="oil.type" id="type" class="longinput">
                            <option value="OTHER">请选择</option>
                            <option value="IRON">铁塔公司</option>
                            <option value="BUFFET">小卖部</option>
                            <option value="PARK">森林公园</option>
                        </select>
                    </span>
                </p>
                <p>
                    <label>备注</label>
                    <span class="field">
                        <textarea cols="80" rows="5" name="oil.context" class="longinput"></textarea>
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