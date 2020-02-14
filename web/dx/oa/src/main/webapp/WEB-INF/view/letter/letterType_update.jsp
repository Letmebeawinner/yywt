<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>修改类型</title>
    <script type="text/javascript">
        function addFormSubmit() {
            var name = jQuery("#name").val();
            if (name == "" || name == null) {
                alert("请添加类型名称");
                return;
            }
            jQuery.ajax({
                url: "${ctx}/admin/oa/updateLetterType.json",
                data: jQuery('#addFormSubmit').serialize(),
                type: "post",
                dataType: "json",
                async: false,
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "${ctx}/admin/oa/queryAllLetterType.json";
                    } else {
                        alert(result.message);
                    }
                }
            });
        }
    </script>
</head>

<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">修改类型</h1>
    </div>
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于公文类型修改；<br>
        2.<em style="color: red;">*</em>代表必填项；<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <input type="hidden" name="letterType.id" value="${letterType.id}">
                <p>
                    <c:choose>
                        <c:when test="${letterType.parentId!=null && letterType.parentId>0}">
                            <label>父级专业</label>
                            <span class="field">
                                <select name="letterType.parentId">
                                    <c:forEach items="${letterTypeList}" var="letter">
                                        <option value="${letter.id}"
                                                <c:if test="${letterType.id==letter.id}">selected="selected"</c:if>>${letter.name}</option>
                                    </c:forEach>
                                </select>
                            </span>
                        </c:when>
                        <c:otherwise>
                            <input name="letterType.parentId" value="0" style="display: none"/>
                        </c:otherwise>
                    </c:choose>
                </p>
                <p>
                    <label><em style="color: red;">*</em>类型名称</label>
                    <span class="field"><input type="text" name="letterType.name" id="name" class="longinput"
                                               value="${letterType.name}"/></span>
                </p>
                <p>
                    <label>排序</label>
                    <span class="field"><input type="text" name="letterType.sort" id="sort" class="longinput"
                                               onkeyup="value=value.replace(/[^\d]/g,'')"
                                               value="${letterType.sort}"/></span>
                </p>
            </form>
            <p class="stdformbutton" style="text-align: center">
                <button class="submit radius2" onclick="addFormSubmit()">提交保存</button>
            </p>
            <br/>
        </div>
    </div>
</div>


</body>
</html>