<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>添加菜单</title>
    <script type="text/javascript">
        function addFormSubmit() {
            var name=jQuery("#name").val();
            if(name==null || name==''){
                alert("名称不能为空!");
                return;
            }
            var price=jQuery("#price").val();
            var typeId=jQuery("#typeId").val();
            jQuery.ajax({
                url: "${ctx}/admin/houqin/addSaveMenus.json",
                data: {"menus.title": name,
                    "menus.price": price,
                    "menus.typeId": typeId},
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code=="0") {
                        alert(result.message);
                        window.location.href = "/admin/houqin/queryAllMenus.json";
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
        <h1 class="pagetitle">添加菜单</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label>所属分类</label>
                    <span class="field">
                        <select name="menus.typeId" id="typeId">
                            <c:forEach var="menu" items="${menuList}">
                                <option value="${menu.id}">${menu.name}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>菜单名称</label>
                    <span class="field">
                        <input type="text" name="menus.title"  class="longinput" id="name"/>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>菜单价格</label>
                    <span class="field">
                        <input type="text" name="menus.price"  class="longinput"  id="price"/>单位元
                    </span>
                </p>
            </form>
            <p class="stdformbutton">
                <button class="submit radius2" onclick="addFormSubmit()">提交保存</button>
            </p>
        </div>
    </div>
</div>
</body>
</html>