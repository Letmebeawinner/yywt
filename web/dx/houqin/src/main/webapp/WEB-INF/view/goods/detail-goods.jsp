<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>物品详情</title>
</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">查看物品详情</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于查看物品信息；<br>
        </div>
    </div>
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="${ctx}/admin/houqin/updateGoods.json" id="addFormSubmit">

                <p>
                    <label><em style="color: red;">*</em>库房名称</label>
                    <span class="field">
                        <select name="goods.storageId" disabled="disabled">
                            <option value="0">--请选择--</option>
                            <c:forEach items="${storageList}" var="storage">
                                <option value="${storage.id}"
                                        <c:if test="${goods.storageId==storage.id}">selected="selected"</c:if>
                                >${storage.name}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>

                <p>
                    <label>分类名称</label>
                    <span class="field">
                        <select name="goods.typeId" id="typeId" disabled="disabled">
                            <c:forEach items="${goodTypeList}" var="goodstype">
                                <option value="${goodstype.id}" <c:if test="${goods.typeId==goodstype.id}">selected="selected"</c:if>>${goodstype.typeName}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>

                <p>
                    <label>入库人</label>
                    <span class="field">
                        <span>${sysUser.userName}</span>&nbsp;&nbsp;
                    </span>
                </p>
                <p>
                    <label>商品名称</label>
                    <span class="field">
                        <span>${goods.name}</span>&nbsp;&nbsp;
                    </span>
                </p>
                <p>
                    <label>商品编号</label>
                    <span class="field">
                       ${goods.code} &nbsp;&nbsp;
                    </span>
                </p>
                <p>
                    <label>规格型号</label>
                    <span class="field">
                      ${goods.model}&nbsp;&nbsp;
                    </span>
                </p>
                <p>
                    <label>数量</label>
                    <span class="field">
                    ${goods.num}&nbsp;&nbsp;
                    </span>
                </p>
                <p>
                    <label>单位</label>
                    <span class="field">
                       <select name="goods.unitId" id="unitId" disabled="disabled">
                           <c:forEach items="${goodsUnitList}" var="unit">
                               <option value="${unit.id}"<c:if test="${goods.unitId==unit.id}">selected="selected"</c:if>>${unit.name}</option>
                           </c:forEach>
                       </select>
                    </span>
                </p>
                <p>
                    <label>价格</label>
                    <span class="field">
                       ${goods.price}元 &nbsp;&nbsp;
                    </span>
                </p>
                <p>
                    <label>添加时间</label>
                    <span class="field">
                      <fmt:formatDate value="${goods.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </span>
                </p>
                <p>
                    <label>备注</label>
                    <span class="field">
                      ${goods.describes}&nbsp;
                    </span>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
</html>