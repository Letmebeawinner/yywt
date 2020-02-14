<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>申请档案</title>
    <script type="text/javascript">


        function addFormSubmit() {
            var name = jQuery("#name").val();
            if (name == "" || name == null) {
                alert("请填写姓名");
                return;
            }
            var context = jQuery("#context").val();
            if (context == "" || context == null) {
                alert("请填写主要内容和目的");
                return;
            }
            jQuery.ajax({
                url: "${ctx}/admin/oa/addSearchArchive.json",
                data: jQuery('#addFormSubmit').serialize(),
                type: "post",
                dataType: "json",
                async: false,
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "${ctx}/admin/oa/searchArchiveList.json";
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
        <h1 class="pagetitle">申请档案查询</h1>
    </div>
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于档案添加；<br>
        2.<em style="color: red;">*</em>代表必填项；<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label><em style="color: red;">*</em>调阅人姓名</label>
                    <span class="field">
                        <input type="text" name="archiveSearch.name" id="name" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>主要内容和目的</label>
                    <span class="field">
                        <textarea rows="10" cols="80" class="longinput" name="archiveSearch.context" id="context"></textarea>
                    </span>
                </p>

                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit();return false;">提交保存</button>
                </p>
            </form>
            <br/>
        </div>
    </div>
</div>

</body>
</html>