<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>修改食材</title>
    <script type="text/javascript">
        function addFormSubmit() {
            var content = jQuery("#content").val();
            if (content == null || content == '') {
                alert("类别名称不能为空!");
                return;
            }
            var count = jQuery("#count").val();
            if (count == null || count == '') {
                alert("数量不能为空!");
                return;
            }
            var unit = jQuery("#unit").val();
            if (unit == null || unit == '') {
                alert("单位不能为空!");
                return;
            }
            var expirationDate = jQuery("#expirationDate").val();
            if (expirationDate == null || expirationDate == '') {
                alert("保质期不能为空!");
                return;
            }
            var inventory = jQuery("#inventory").val();
            if (inventory == null || inventory == '') {
                alert("库存不能为空!");
                return;
            }
            jQuery.ajax({
                url: "${ctx}/admin/houqin/updateFood.json",
                data: jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "${ctx}/admin/houqin/selectAllFood.json";
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
        <h1 class="pagetitle">修改食材</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label><em style="color: red;">*</em>名称</label>
                    <span class="field">
                        <input type="text" name="food.name" value="${food.name}" class="longinput" id="content"/>
                        <input type="hidden" name="food.id" value="${food.id}" class="longinput" id="id"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>数量</label>
                    <span class="field">
                        <input type="text" name="food.cont" value="${food.cont}" class="longinput" id="count"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>单位</label>
                    <span class="field">
                        <input type="text" name="food.unit" value="${food.unit}" class="longinput" id="unit"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>保质期</label>
                    <span class="field">
                        <input type="text" name="food.expirationDate" value="${food.expirationDate}" class="longinput"
                               id="expirationDate"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>库存</label>
                    <span class="field">
                        <input type="text" name="food.inven" value="${food.inven}" class="longinput"
                               id="inventory"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>食堂</label>
                    <span class="field">
                        <select id="messId" name="food.messId">
                            <c:forEach items="${messTypeList}" var="mess">
                                <option value="${mess.id}"
                                        <c:if test="${food.messId==mess.id}">selected="selected"</c:if>
                                >${mess.name}</option>
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