<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>资讯类型添加</title>
    <script type="text/javascript">
        function addFormSubmit() {
            var sort = jQuery("#sort").val();
            var name = jQuery("#name").val();
            if (name == null || name == undefined || name == '') {
                alert("请填写类型名称");
                return;
            }
            if (!Number(sort)) {
                alert("排序必须为数字");
                return;
            }
            jQuery.ajax({
                url: '${ctx}/admin/menhu/articleType/addArticleType.json',
                data: jQuery('#archivesTypeForm').serialize(),
                type: 'post',
                dataType: 'json',
                success: function (result) {
                    if (result.code == "0") {
                        window.location.href = "${ctx}/admin/menhu/articleType/queryArticleType.json";
                    }
                },
            });
        }

    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">资讯类型添加</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <form id="archivesTypeform" class="stdform stdform2" method="post" action="">
            <p>
                <input type="hidden" value="${articleType.id}" name="articleType.id">
                <label>名称:</label>
                <span class="field"><input type="text" id="name" name="articleType.name" class="longinput"/></span>
            </p>
            <p>
                <label>排序:</label>
                <span class="field"><input type="text" onkeyup="value=value.replace(/[^\d]/g,'')" id="sort"
                                           name="articleType.sort" class="longinput"/></span>
            </p>
            <p>
                <button onclick="addFormSubmit();return false" class="stdbtn">保存</button>
            </p>
        </form>

    </div>
</div>
</body>
</html>
