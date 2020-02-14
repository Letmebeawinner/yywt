<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>更新消防器材</title>
    <script type="text/javascript">
        function addFormSubmit() {
            var name = jQuery("#name").val();
            if (name == null || name == '') {
                alert("器材名称不能为空!");
                return;
            }

            var context = jQuery("#context").val();
            if (context == null || context == '') {
                alert("器材维护信息不能为空!");
                return;
            }
            jQuery.ajax({
                url: "${ctx}/admin/houqin/updateEquipment.json",
                data:jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "/admin/houqin/queryAllEquipment.json";
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
        <h1 class="pagetitle">更新消防器材</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于消防器材信息修改；<br>
            2.修改消防器材信息：按要求修改相关信息,点击<span style="color:red">提交保存</span>按钮；修改消防器材信息<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <input type="hidden" name="equipment.id" value="${equipment.id}">
                    <label><em style="color: red;">*</em>器材名称</label>
                    <span class="field">
                        <input type="text" name="equipment.name"  class="longinput" value="${equipment.name}" id="name"/>
                    </span>
                </p>
                <p>
                    <label>消防器材位置</label>
                    <span class="field">
                        <input type="text" name="equipment.location"  class="longinput" value="${equipment.location}"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>器材维护信息</label>
                    <span class="field">
                        <input type="text" name="equipment.context"  class="longinput" value="${equipment.context}" id="context"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>使用情况</label>
                    <span class="field">
                            <select name="equipment.status">
                                <option value="0" <c:if test="${equipment.status==0}">selected="selected"</c:if>>未使用</option>
                                <option value="1" <c:if test="${equipment.status==1}">selected="selected"</c:if>>已使用</option>
                            </select>
                    </span>
                </p>
                <p>
                    <label>数量</label>
                    <span class="field">
                        <input type="text" name="equipment.num" value="${equipment.num}" class="longinput" id="num"/>
                    </span>
                </p>
                <p>
                    <label>单位</label>
                    <span class="field">
                       <select name="equipment.unitId" id="unitId">
                           <option value="0">--请选择--</option>
                           <c:forEach items="${goodsunitList}" var="unit">
                               <option value="${unit.id}" <c:if test="${unit.id==equipment.unitId}">selected</c:if>>${unit.name}</option>
                           </c:forEach>
                       </select>
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