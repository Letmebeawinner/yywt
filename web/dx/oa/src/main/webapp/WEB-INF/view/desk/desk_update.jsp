<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>修改功能菜单</title>
    <script type="text/javascript">
        function addFormSubmit() {
            var name = jQuery("#name").val();
            if (name == "" || name == null) {
                alert("请添加功能名称");
                return;
            }
            var data = jQuery("#addFormSubmit").serialize();
            jQuery.ajax({
                url: "${ctx}/admin/oa/updateFunction.json",
                data: data,
                type: "post",
                dataType: "json",
                async: false,
                cache : false,
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "${ctx}/admin/oa/queryAllFunction.json";
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
        <h1 class="pagetitle">修改功能菜单</h1>
    </div>
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于功能菜单修改；<br>
        2.<em style="color: red;">*</em>代表必填项；<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <input type="hidden" name="function.id" value="${function.id}">
                <p>
                    <label><em style="color: red;">*</em>功能名称</label>
                    <span class="field"><input type="text" name="function.name" id="name" class="longinput" value="${function.name}"/></span>
                </p>
                <p>
                    <label>功能链接</label>
                    <span class="field"><input type="text" name="function.link" id="link" class="longinput" value="${function.link}"/></span>
                </p>
                <p>
                    <label>排序</label>
                    <span class="field"><input type="text" name="function.sort" id="sort" class="longinput" onkeyup="value=value.replace(/[^\d]/g,'')" value="${function.sort}"/></span>
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