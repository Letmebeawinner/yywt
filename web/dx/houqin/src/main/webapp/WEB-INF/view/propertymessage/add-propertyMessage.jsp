<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>添加资产信息</title>
    <script type="text/javascript">

        jQuery(function () {
            laydate.skin('molv');
            laydate({
                elem: '#liftTime',
                format: 'YYYY-MM-DD'
            });
            laydate({
                elem: '#buyTime',
                format: 'YYYY-MM-DD'
            });
        });

        function clearNoNum(obj) {
            obj.value = obj.value.replace(/[^\d.]/g, "");  //清除“数字”和“.”以外的字符
            obj.value = obj.value.replace(/\.{2,}/g, "."); //只保留第一个. 清除多余的
            obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
            obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3');//只能输入两个小数
            if (obj.value.indexOf(".") < 0 && obj.value != "") {//以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额
                obj.value = parseFloat(obj.value);
            }
        }

        function addFormSubmit() {
            var storageId = jQuery("#storageId").val();
            if (storageId == null || storageId == 0) {
                alert("库房不能为空!");
                return;
            }
            var name = jQuery("#name").val();
            if (name == null || name == '') {
                alert("资产名称不能为空!");
                return;
            }

            var propertyId=jQuery("#propertyId").val();
            if(propertyId==null||propertyId==0){
                alert("请选择资产分类!");
                return;
            }


            var amount=jQuery("#amount").val();
            if(amount==null||amount==0){
                alert("请填写数量!");
                return;
            }

            var buyTime=jQuery("#buyTime").val();
            if(buyTime==null||buyTime==''){
                alert("请填写购入时间");
                return;
            }

            var price=jQuery("#price").val();
            if(price==null||price==''){
                alert("请填写购入金额");
                return;
            }

            var property=jQuery("#property").val();
            if(property==0){
                alert("请选择资产来源");
                return;
            }

            jQuery.ajax({
                url: "${ctx}/admin/houqin/addSavePropertyMessage.json",
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
        <h1 class="pagetitle">添加资产信息</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于添加资产信息；<br>
            2.添加资产信息：按要求填写相关信息,点击<span style="color:red">提交保存</span>按钮；添加资产<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">

                <p>
                    <label><em style="color: red;">*</em>库房名称</label>
                    <span class="field">
                       <select name="propertyMessage.storageId" id="storageId">
                            <option value="0">--请选择--</option>
                            <c:forEach var="waterHouse" items="${wareHouseList}">
                                <option value="${waterHouse.id}">${waterHouse.name}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>资产名称</label>
                    <span class="field">
                        <input type="text" name="propertyMessage.name" class="longinput" id="name"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>资产分类</label>
                    <span class="field">
                        <select name="propertyMessage.propertyId" id="propertyId">
                            <option value="0">--请选择--</option>
                            <c:forEach var="property" items="${propertyList}">
                                <option value="${property.id}">${property.typeName}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>
                <p>
                    <label>规格型号</label>
                    <span class="field">
                        <input type="text" name="propertyMessage.product" class="longinput" id="product"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>数量</label>
                    <span class="field">
                        <input type="text" name="propertyMessage.amount"  class="longinput" id="amount"
                               onkeyup="if(/\D/.test(this.value)){alert('只能输入数字');this.value='';}"
                        />
                    </span>
                </p>
                <p>
                    <label>计量单位</label>
                    <span class="field">
                        <input type="text" name="propertyMessage.unit" class="longinput" id="unit"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>金额</label>
                    <span class="field">
                        <input type="text" name="propertyMessage.price" onkeyup="clearNoNum(this)" class="longinput"
                               id="price" maxlength="8"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>购入时间</label>
                    <span class="field">
                        <input type="text" name="propertyMessage.buyTime" class="longinput" id="buyTime" readonly=readonly/>
                    </span>
                </p>
                <p>
                    <label>管理员</label>
                    <span class="field">
                        <input type="text"  class="longinput" value=""/>
                    </span>
                </p>
                <p>
                    <label>使用期限</label>
                    <span class="field">
                        <input type="text" name="propertyMessage.liftTime" class="longinput" id="liftTime" readonly=readonly/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>来源</label>
                    <span class="field">
                        <select name="propertyMessage.source" id="property">
                            <option value="0">--请选择--</option>
                            <c:forEach items="${propertySourceList}" var="property">
                                <option value="${property.id}">${property.name}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>
                <p>
                    <label>备注</label>
                    <span class="field">
                        <textarea cols="5" rows="10" name="propertyMessage.context"  id="context" style="width: 50%"></textarea>
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