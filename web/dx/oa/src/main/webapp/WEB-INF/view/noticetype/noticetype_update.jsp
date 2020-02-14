<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>修改公告类型</title>
    <script type="text/javascript">
        function addFormSubmit() {
            var id=jQuery("#noticeId").val();
            var name = jQuery("#name").val();
            if (name == "" || name == null) {
                alert("请添加类型名称");
                return;
            }
            var sort = jQuery("#sort").val();
            jQuery.ajax({
                url: "${ctx}/admin/oa/updateNoticeType.json",
                data: {
                    "noticeType.id":id,
                    "noticeType.name": name,
                    "noticeType.sort": sort
                },
                type: "post",
                dataType: "json",
                async: false,
                success: function (result) {
                    if (result.code=="0") {
                        alert(result.message);
                        window.location.href = "${ctx}/admin/oa/queryAllNoticeType.json";
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
        <h1 class="pagetitle">修改新闻信息</h1>
    </div><!--pageheader-->
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于新闻信息修改；<br>
        2.<em style="color: red;">*</em>代表必填项；<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label><em style="color: red;">*</em>类型名称</label>
                    <span class="field"><input type="text" name="noticeType.name" id="name" class="longinput" value="${noticeType.name}"/></span>
                </p>
                <input type="hidden" name="noticeType.id" value="${noticeType.id}" id="noticeId">
                <p>
                    <label>排序</label>
                    <span class="field"><input type="text" name="noticeType.sort" id="sort" class="longinput" onkeyup="value=value.replace(/[^\d]/g,'')" value="${noticeType.sort}"/></span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit(); return false">提交保存</button>
                </p>
            </form>
            <br/>
        </div>
    </div>
</div>


</body>
</html>