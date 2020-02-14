<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>修改食材分类</title>
    <script type="text/javascript">
        function addFormSubmit() {
            var content = jQuery("#content").val();
            if (content == null || content == '') {
                alert("类别名称不能为空!");
                return;
            }
            jQuery.ajax({
                url: "${ctx}/admin/houqin/updateFoodType.json",
                data: jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "${ctx}/admin/houqin/selectAllFoodType.json";
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
        <h1 class="pagetitle">修改进货名称</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于修改进货名称；<br>
            2.修改食材类型：按要求填写相关信息,点击<span style="color:red">提交保存</span>按钮；修改进货名称<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label><em style="color: red;">*</em>进货名称</label>
                    <span class="field">
                        <input type="text" name="foodType.content" value="${foodType.content}" class="longinput"
                               id="content"/>
                        <input type="hidden" name="foodType.id" value="${foodType.id}" class="longinput"/>
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