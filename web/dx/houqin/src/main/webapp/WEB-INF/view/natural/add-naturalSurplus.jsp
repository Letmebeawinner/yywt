<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>添加用天燃气量</title>
    <script type="text/javascript">
        function addFormSubmit() {
            var amount = jQuery("#amount").val();
            if (amount == null || amount == '') {
                alert("天燃气总量!");
                return;
            }
            jQuery.ajax({
                url: "${ctx}/admin/houqin/addSaveNaturalSurplus.json",
                data: jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "/admin/houqin/queryAllNaturalSurplus.json";
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
        <h1 class="pagetitle">添加用天燃气量</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label><em style="color: red;">*</em>天燃气总量</label>
                    <span class="field">
                        <input type="text" name="naturalSurplus.amount" class="longinput" id="amount"/>
                    </span>
                </p>

                <p>
                    <label>选择年份</label>
                    <span class="field">
                       <input type="text" name="naturalSurplus.year" class="longinput">
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