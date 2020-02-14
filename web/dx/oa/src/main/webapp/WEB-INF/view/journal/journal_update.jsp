<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>添加日志</title>
    <script type="text/javascript">


//        jQuery(function () {
//            laydate.skin('molv');
//            laydate({
//                elem: '#startTime',
//                format: 'YYYY-MM-DD hh:mm:ss'
//            });
//        });
//
//        jQuery(function () {
//            laydate.skin('molv');
//            laydate({
//                elem: '#updateTime',
//                format: 'YYYY-MM-DD hh:mm:ss'
//            });
//        });

        function addFormSubmit() {
//            var startTime = jQuery("#startTime").val();
//            if (startTime == "" || startTime == null) {
//                alert("请添加开始时间");
//                return;
//            }
            jQuery.ajax({
                url: "${ctx}/admin/oa/updateJournal.json",
                data: jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                async: false,
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "${ctx}/admin/oa/queryAllJournal.json";
                    } else {
                        alert(result.message);
                    }
                }
            });
        }

        function delEmployee(id) {
            var userId = jQuery("#userId").val();
            var pattern = id + "";
            userId = userId.replace(new RegExp(pattern), "");
            userId = userId.split(",").unique();
            jQuery("#userId").val(userId);
            queryEmployee(jQuery("#userId").val());
        }

    </script>
</head>

<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">修改日志</h1>
    </div>
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于日志信息添加；<br>
        3.<em style="color: red;">*</em>代表必填项；<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">

            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">

                <p>
                    <label><em style="color: red;">*</em>标题</label>
                    <span class="field">
                        <input type="hidden" name="journal.id" value="${journal.id}">
                        <input type="text" name="journal.journal_name" id="" class="longinput"
                               value="${journal.journal_name}"/></span>
                </p>
                <%--<p>--%>
                    <%--<label><em style="color: red;">*</em>开始时间</label>--%>
                    <%--<span class="field"><input type="text" name="journal.updateTime" id="startTime" class="longinput"--%>
                                               <%--value="${journal.createTime}"/></span>--%>
                <%--</p>--%>
                <%--<p>--%>
                    <%--<label><em style="color: red;">*</em>更新时间</label>--%>
                    <%--<span class="field"><input type="text" name="journal.updateTime" id="updateTime" class="longinput"--%>
                                               <%--value="${journal.updateTime}"--%>
                    <%--/></span>--%>
                <%--</p>--%>
                <p>
                    <label>日志内容</label>
                    <span class="field">
                        <textarea rows="10" cols="5" name="journal.journal_content"
                                  id="context">${journal.journal_content}</textarea>
                    </span>
                </p>
            </form>
            <p class="stdformbutton" style="text-align: center">
                <button class="submit radius2" onclick="addFormSubmit()">提交修改</button>
            </p>
            <br/>
        </div>
    </div>
</div>

</body>
</html>