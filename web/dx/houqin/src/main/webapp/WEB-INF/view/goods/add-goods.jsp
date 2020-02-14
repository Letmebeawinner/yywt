<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>添加物品记录</title>
    <script type="text/javascript">
        function addFormSubmit() {
            var storageVal = jQuery("select[name='goods.storage']").val();
            if (storageVal < 1) {
                alert("请选择库存!");
                return;
            }

            var typeIdVal = jQuery("select[name='goods.typeId']").val();
            if (typeIdVal < 1) {
                alert("请选择分类!");
                return;
            }


            var name = jQuery("#name").val();
            if (name == null || name == '') {
                alert("商品名称不能为空!");
                return;
            }

            var num = jQuery("#num").val();
            if (num == null || num == '') {
                alert(" 数量不能为空!");
                return;
            }


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

        function clearNoNum(obj) {
            //修复第一个字符是小数点 的情况.
            if (obj.value != '' && obj.value.substr(0, 1) == '.') {
                obj.value = "";
            }
            obj.value = obj.value.replace(/^0*(0\.|[1-9])/, '$1');//解决 粘贴不生效
            obj.value = obj.value.replace(/[^\d.]/g, "");  //清除“数字”和“.”以外的字符
            obj.value = obj.value.replace(/\.{2,}/g, "."); //只保留第一个. 清除多余的
            obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
            obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3');//只能输入两个小数
            if (obj.value.indexOf(".") < 0 && obj.value != "") {//以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额
                if (obj.value.substr(0, 1) == '0' && obj.value.length == 2) {
                    obj.value = obj.value.substr(1, obj.value.length);
                }
            }
        }
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">添加物品记录</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于添加物品入库；<br>
            2.添加物品入库：按要求填写相关信息,点击<span style="color:red">提交保存</span>按钮；添加物品入库<br>
        </div>
    </div>
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">

                <p>
                    <label><em style="color: red;">*</em>库房名称</label>
                    <span class="field">
                        <select name="goods.storageId">
                            <option value="0">--请选择--</option>
                            <c:forEach items="${storageList}" var="storage">
                                <option value="${storage.id}">${storage.name}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>分类名称</label>
                    <span class="field">
                        <select name="goods.typeId" id="typeId">
                            <option value="0">--请选择--</option>
                            <c:forEach items="${goodTypeList}" var="goodstype">
                                <option value="${goodstype.id}">${goodstype.typeName}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>商品名称</label>
                    <span class="field">
                        <input type="text" name="goods.name" class="longinput" id="name"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>数量</label>
                    <span class="field">
                        <input type="text" name="goods.num" class="longinput" id="num"
                               maxlength="8"
                               onkeyup="value=this.value.replace(/\D+/g,'')"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>单位</label>
                    <span class="field">
                       <select name="goods.unitId" id="unitId">
                           <option value="0">--请选择--</option>
                           <c:forEach items="${goodsunitList}" var="unit">
                               <option value="${unit.id}">${unit.name}</option>
                           </c:forEach>
                       </select>
                    </span>
                </p>
                <p>
                <label>规格型号</label>
                <span class="field">
                <input type="text" name="goods.model" class="longinput" id="model"/>
                </span>
                </p>
                <p>
                    <label>价格</label>
                    <span class="field">
                        <input type="text" name="goods.price" class="longinput" id="price"
                               onkeyup="clearNoNum(this)" maxlength="8"/>
                    </span>
                </p>
                <p>
                    <label>备注</label>
                    <span class="field">
                      <textarea cols="80" rows="5" name="goods.describes" id="desc" class="longinput"></textarea>
                    </span>
                </p>
                <p>
                    <label>入库人</label>
                    <span class="field">
                        ${user.userName}
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