<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>编辑食堂库存信息</title>
    <script type="text/javascript">
        jQuery(function () {
            laydate.skin('molv');
            laydate({
                elem: '#time',
                format: 'YYYY-MM-DD hh:mm:ss'
            });
        });
        function addFormSubmit() {
            var name = jQuery("#name").val();
            var unitId = jQuery("#unitId").val();

            if (name == null || name == '') {
                alert("名称不能为空");
                return false;
            }

            if (unitId == 0) {
                alert("请选择单位");
                return false;
            }
            jQuery("#updateFormSubmit").submit();
        }
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">添加食堂库存信息</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于添加食堂物品入库；<br>
            2.添加食堂物品入库：按要求填写相关信息,点击<span style="color:red">提交保存</span>按钮；添加食堂物品入库<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="${ctx}/admin/houqin/updateMessStock.json"
                  id="updateFormSubmit">

                <p>
                    <label><em style="color: red;">*</em>商品名称</label>
                    <span class="field">
                        <input type="text" name="messStock.name" value="${messStock.name}" class="longinput" id="name"/>
                    </span>
                </p>
                <input type="hidden" name="messStock.id" value="${messStock.id}">
                <p>
                    <label><em style="color: red;">*</em>单位</label>
                    <span class="field">
                       <select name="messStock.units" id="unitId">
                           <option value="0">--请选择--</option>
                           <c:forEach items="${goodsunitList}" var="unit">
                               <option value="${unit.name}" <c:if test="${messStock.units==unit.name}">selected="selected"</c:if> >${unit.name}</option>
                           </c:forEach>
                       </select>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>进货名称</label>
                    <span class="field">
                       <select name="messStock.foodTypeContent">
                           <c:forEach items="${foodTypeList}" var="fl">
                               <option value="${fl.content}"
                                       <c:if test="${fl.content eq messStock.foodTypeContent}">selected="selected"</c:if>
                               >${fl.content}</option>
                           </c:forEach>
                       </select>
                    </span>
                </p>
                <p>
                    <label>过期时间</label>
                    <span class="field">
                       <input type="text" name="messStock.expirationTime"
                              value="<fmt:formatDate value="${messStock.expirationTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                              id="time" class="longinput">
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>备注</label>
                    <span class="field">
                      <textarea cols="80" rows="5" name="messStock.content" id="content" class="longinput">${messStock.content}</textarea>
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