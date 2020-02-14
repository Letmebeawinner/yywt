<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>修改用气区域</title>
    <script type="text/javascript">
        function addFormSubmit() {
            var type = jQuery("#type").val();
            if (type == null || type == '') {
                alert("区域不能为空!");
                return;
            }
            jQuery.ajax({
                url: "${ctx}/admin/houqin/updateNaturalType.json",
                data: jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "/admin/houqin/queryAllNaturalType.json";
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
        <h1 class="pagetitle">修改用电区域</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于更新用电区域；<br>
            2.更新用电区域信息：按要求修改相关信息,点击<span style="color:red">提交保存</span>按钮；更新用电区域信息<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">

                <p>
                    <input type="hidden" name="naturalType.id" value="${naturalType.id}">
                    <label><em style="color: red;">*</em>用气区域</label>
                    <span class="field">
                        <input type="text" name="naturalType.type" class="longinput" value="${naturalType.type}"
                               id="type"/>
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