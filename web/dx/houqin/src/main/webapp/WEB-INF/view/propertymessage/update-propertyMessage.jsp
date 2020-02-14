<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>修改资产信息</title>
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
        })
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

            jQuery.ajax({
                url: "${ctx}/admin/houqin/updatePropertyMessage.json",
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
        <h1 class="pagetitle">修改资产信息</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于修改资产信息；<br>
            2.修改资产信息：按要求修改相关信息,点击<span style="color:red">提交保存</span>按钮；修改资产信息<br>
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
                                <option value="${waterHouse.id}" <c:if test="${propertyMessage.storageId==waterHouse.id}">selected="selected"</c:if>>${waterHouse.name}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>
                <p>
                    <input type="hidden" name="propertyMessage.id" value="${propertyMessage.id}">
                    <label><em style="color: red;">*</em>资产名称</label>
                    <span class="field">
                        <input type="text" name="propertyMessage.name" class="longinput" value="${propertyMessage.name}"
                               id="name"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>资产分类</label>
                    <span class="field">
                        <select name="propertyMessage.propertyId" id="propertyId">
                            <option value="0">--请选择--</option>
                            <c:forEach var="property" items="${propertyList}">
                                <option value="${property.id}" <c:if test="${propertyMessage.propertyId==property.id}">selected="selected"</c:if>>${property.typeName}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>
                <p>
                    <label>规格型号</label>
                    <span class="field">
                        <input type="text" name="propertyMessage.product" value="${propertyMessage.product}" class="longinput" id="product"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>数量</label>
                    <span class="field">
                         <input type="text" name="propertyMessage.amount"  class="longinput" id="amount" value="${propertyMessage.amount}"
                                onkeyup="if(/\D/.test(this.value)){alert('只能输入数字');this.value='';}"
                         />
                    </span>
                </p>
                <p>
                    <label>计量单位</label>
                    <span class="field">
                        <input type="text" name="propertyMessage.unit" value="${propertyMessage.unit}" class="longinput" id="unit"/>
                    </span>
                </p>
                <p>
                    <label>金额</label>
                    <span class="field">
                        <input type="text" name="propertyMessage.price" class="longinput" id="price" value="${propertyMessage.price}"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>购入时间</label>
                    <span class="field">
                        <input type="text" name="propertyMessage.buyTime" class="longinput" readonly=readonly id="buyTime" value="<fmt:formatDate type='both' value='${propertyMessage.buyTime}' pattern='yyyy-MM-dd'/>"/>
                    </span>
                </p>
                <p>
                    <label>使用期限</label>
                    <span class="field">
                        <input type="text" name="propertyMessage.liftTime"  readonly=readonly class="longinput" id="liftTime" value="<fmt:formatDate type='both' value='${propertyMessage.liftTime}' pattern='yyyy-MM-dd'/>"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>来源</label>
                    <span class="field">
                        <select name="propertyMessage.source" id="property">
                            <option value="0">--请选择--</option>
                            <c:forEach items="${propertySourceList}" var="property">
                                <option value="${property.id}" <c:if test="${property.id==propertyMessage.source}">selected="selected"</c:if>>${property.name}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>
                <p>
                    <label>备注</label>
                    <span class="field">
                        <textarea cols="5" rows="10" name="propertyMessage.context"  id="context" style="width: 50%">${propertyMessage.context}</textarea>
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