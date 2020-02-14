<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>更新菜单</title>
    <script type="text/javascript">
        function addFormSubmit() {
            jQuery("#addFormSubmit").submit();
        }

    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">更新菜单</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="${ctx}/admin/houqin/updateMenus.json" id="addFormSubmit">
                <input type="hidden" name="menus.id" value="${menus.id}">
                <p>
                    <label>所属分类</label>
                    <span class="field">
                        <select name="menus.typeId" id="typeId">
                            <c:forEach var="menu" items="${menuList}">
                                <option value="${menu.id}" <c:if test="${menu.id==menus.typeId}">selected="selected"</c:if>>${menu.name}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>名称</label>
                    <span class="field">
                        <input type="text" name="menus.title"  class="longinput" value="${menus.title}"/>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>价格</label>
                    <span class="field">
                        <input type="text" name="menus.price"  class="longinput" value="${menus.price}"/>单价元
                    </span>
                </p>
                <p class="stdformbutton">
                    <button class="submit radius2" onclick="addFormSubmit()">提交保存</button>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
</html>