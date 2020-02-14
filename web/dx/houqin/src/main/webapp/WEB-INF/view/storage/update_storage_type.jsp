<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>添加出库类型</title>
    <script type="text/javascript">
        function addFormSubmit() {
            var name = jQuery("#name").val();
            var sort = jQuery("#sort").val();
            if (!name) {
                alert("出库类型不能为空，请填写");
                return;
            }
            if (!sort) {
                alert("排序值不可以为空，请填写");
                return;
            }
            jQuery.ajax({
                url: "${ctx}/admin/houqin/updateStorageType.json",
                data: jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                async: false,
                success: function (result) {
                    if (result.code == 0) {
                        alert("修改成功！");
                        window.location.href = "${ctx}/admin/houqin/storageTypeList.json";
                    }
                }
            });
        }

    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">添加出库类型</h1>
    </div>
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <input type="hidden" name="storageType.id" class="longinput"  value="${storageType.id}"/>
                    <label><em style="color: red;">*</em>名称</label>
                    <span class="field">
                        <input type="text" name="storageType.name" class="longinput" id="name" value="${storageType.name}"/>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>排序</label>
                    <span class="field">
                         <input type="text" name="storageType.sort" class="longinput" id="sort" value="${storageType.sort}"/>
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