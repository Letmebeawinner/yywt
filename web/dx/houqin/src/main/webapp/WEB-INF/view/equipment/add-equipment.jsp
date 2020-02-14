<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>添加消防器材</title>
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
                url: "${ctx}/admin/houqin/addSaveEquipment.json",
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
        <h1 class="pagetitle">添加消防器材</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于添加消防器材；<br>
            2.添加消防器材：按要求填写相关信息,点击<span style="color:red">提交保存</span>按钮；添加消防器材<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label><em style="color: red;">*</em>器材名称</label>
                    <span class="field">
                        <input type="text" name="equipment.name" class="longinput" id="name"/>
                    </span>
                </p>
                <p>
                    <label>消防器材位置</label>
                    <span class="field">
                        <input type="text" name="equipment.location" class="longinput" id="location"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>器材维护信息</label>
                    <span class="field">
                        <input type="text" name="equipment.context" class="longinput" id="context"/>
                    </span>
                </p>
                <p>
                    <label>数量</label>
                    <span class="field">
                        <input type="text" name="equipment.num" class="longinput" id="num" value="0"/>
                    </span>
                </p>
                <p>
                    <label>单位</label>
                    <span class="field">
                       <select name="equipment.unitId" id="unitId">
                           <option value="0">--请选择--</option>
                           <c:forEach items="${goodsunitList}" var="unit">
                               <option value="${unit.id}">${unit.name}</option>
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