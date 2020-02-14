<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>添加功能菜单</title>
    <script type="text/javascript">
        function addFormSubmit() {
            var name = jQuery("#name").val();
            if (name == "" || name == null) {
                alert("请添加功能名称");
                return;
            }
            var data = jQuery("#addFormSubmit").serialize();
            jQuery.ajax({
                url: "${ctx}/admin/oa/addSaveFunction.json",
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
        <h1 class="pagetitle">添加功能菜单</h1>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <div class="contenttitle2">
                <h3>添加功能</h3>
            </div>
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label>功能名称</label>
                    <span class="field"><input type="text" name="function.name" id="name" class="longinput"/></span>
                </p>
                <p>
                    <label>功能链接</label>
                    <span class="field"><input type="text" name="function.link" id="link" class="longinput"/></span>
                </p>
                <p>
                    <label>排序</label>
                    <span class="field"><input type="text" name="function.sort" id="sort" class="longinput" onkeyup="value=value.replace(/[^\d]/g,'')" value="0"/></span>
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