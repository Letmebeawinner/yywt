<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>修改物品信息</title>
    <script type="text/javascript">
        function addFormSubmit() {

            var params = jQuery("#addFormSubmit").serialize();
            jQuery.ajax({
                url: "${ctx}/admin/houqin/addSaveGoods.json",
                data: params,
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "/admin/houqin/queryAllGoods.json";
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
        <h1 class="pagetitle">修改物品信息</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于修改物品信息；<br>
            2.修改物品信息：按要求修改相关信息,点击<span style="color:red">提交保存</span>按钮；修改物品信息<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="${ctx}/admin/houqin/updateGoods.json" id="addFormSubmit">

                <p>
                    <input type="hidden" name="goods.id" value="${goods.id}">
                    <input type="hidden" name="goods.userId" value="${goods.userId}">
                    <label><em style="color: red;">*</em>分类名称</label>
                    <span class="field">
                        <select name="goods.typeId" id="typeId">
                            <c:forEach items="${goodTypeList}" var="goodstype">
                                <option value="${goodstype.id}" <c:if test="${goods.typeId==goodstype.id}">selected="selected"</c:if>>${goodstype.typeName}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>商品名称</label>
                    <span class="field">
                        <input type="text" name="goods.name"  class="longinput"  id="name" value="${goods.name}"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>商品编号</label>
                    <span class="field">
                        <input type="text" name="goods.code"  class="longinput"   id="code" value="${goods.code}" disabled/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>规格型号</label>
                    <span class="field">
                        <input type="text" name="goods.model"  class="longinput"   id="model" value="${goods.model}"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>数量</label>
                    <span class="field">
                        <input type="text" name="goods.num"  class="longinput"  id="num" value="${goods.num}"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>单位</label>
                    <span class="field">
                       <select name="goods.unitId" id="unitId">
                           <c:forEach items="${goodsunitList}" var="unit">
                               <option value="${unit.id}"<c:if test="${goods.unitId==unit.id}">selected="selected"</c:if>>${unit.name}</option>
                           </c:forEach>
                       </select>
                    </span>
                </p>
                <p>
                    <label>价格</label>
                    <span class="field">
                        <input type="text" name="goods.price"  class="longinput"  id="price" value="${goods.price}"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>备注</label>
                    <span class="field">
                      <textarea cols="80" rows="5" name="goods.describes" id="desc" class="longinput">${goods.describes}</textarea>
                    </span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit();return false;">提交保存</button>
                    <button class="reset radius2" onclick = "clearForm()">重置表单</button>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
</html>