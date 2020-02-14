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
                alert("柴油用量错误,请输入十位以内的浮点类型");
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
                url: "${ctx}/admin/houqin/updateOil.json",
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
        <h1 class="pagetitle">更新柴油用量</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于更新柴油用量；<br>
            2.更新柴油用量：按要求填写相关信息,点击<span style="color:red">提交保存</span>按钮；更新柴油用量<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <input type="hidden" name="oil.id" value="${oil.id}">
                    <label><em style="color: red;">*</em>单位(升)</label>
                    <span class="field">
                        <input type="text" name="oil.litre" value="${oil.litre}" class="longinput" id="litre"
                               maxlength="10"/>
                    </span>
                </p>

                <p>
                    <label><em style="color: red">*</em>油单价(升)</label>
                    <span class="field">
                        <input type="text" name="oil.price" class="longinput" id="price" value="${oil.price}">
                    </span>
                </p>
                <p>
                    <label><em style="color: red">*</em>油类型</label>
                    <span class="field">
                        <select name="oil.oilType" id="oilType">
                              <c:forEach items="${oilTypeList}" var="oilType">
                                  <option value="${oilType.id}"
                                          <c:if test="${oil.oilType==oilType.id}">selected="selected"</c:if>>${oilType.name}</option>
                              </c:forEach>
                        </select>
                    </span>
                </p>

                <p>
                    <label><em style="color: red">*</em>油用途</label>
                    <span class="field">
                        <select name="oil.purpose" id="purpose">
                            <c:forEach items="${oilUseList}" var="oilUse">
                                <option value="${oilUse.id}"
                                        <c:if test="${oil.purpose==oilUse.id}">selected="selected"</c:if>>${oilUse.name}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>外界用能类型</label>
                    <span class="field">
                        <select name="oil.type" id="type" class="longinput">
                            <option value="OTHER" <c:if test="${'OTHER' eq oil.type}">selected</c:if>>未选择</option>
                            <option value="IRON"
                                    <c:if test="${'IRON' eq oil.type}">selected</c:if> >铁塔公司</option>
                            <option value="BUFFET"
                                    <c:if test="${'BUFFET' eq oil.type}">selected</c:if> >小卖部</option>
                            <option value="PARK"
                                    <c:if test="${'PARK' eq oil.type}">selected</c:if> >森林公园</option>
                        </select>
                    </span>
                </p>
                <p>
                    <label>备注</label>
                    <span class="field">
                        <textarea cols="80" rows="5" name="oil.context" class="longinput">${oil.context}</textarea>
                    </span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit()">提交保存</button>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
</html>