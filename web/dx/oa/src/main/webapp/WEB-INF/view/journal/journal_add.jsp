<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>添加日志</title>
    <script type="text/javascript">
        function checkJournalByName() {
            jQuery.ajax({
                url: "${ctx}/admin/oa/checkJournalByName.json",
                data: jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                async: false,
                success: function (result) {
                    if (result.code == "0") {
                        addFormSubmit();
                    } else {
                        alert(result.message);
                    }
                }
            });
        }

        function addFormSubmit() {
            jQuery.ajax({
                url: "${ctx}/admin/oa/addSaveJournal.json",
                data: jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                async: false,
                success: function (results) {
                    if (results.code == "0") {
                        alert(results.message);
                        window.location.href = "${ctx}/admin/oa/queryAllJournal.json";
                    } else {
                        alert(results.message);
                    }
                }
            });
        }

       /* function delEmployee(id) {
            var userId = jQuery("#userId").val();
            var pattern = id + "";
            userId = userId.replace(new RegExp(pattern), "");
            userId = userId.split(",").unique();
            jQuery("#userId").val(userId);
            queryEmployee(jQuery("#userId").val());
        }*/

    </script>
</head>

<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">添加日志</h1>
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
                    <span class="field"><input type="text" name="journal.journal_name" id="" class="longinput"
                                              /></span>
                </p>
                <p>
                    <label>日志内容</label>
                    <span class="field">
                        <textarea rows="10" cols="5" name="journal.journal_content" id="context"></textarea>
                    </span>
                </p>
            </form>
            <p class="stdformbutton" style="text-align: center">
                <button class="submit radius2" onclick="checkJournalByName()">提交保存</button>
            </p>
            <br/>
        </div>
    </div>
</div>

</body>
</html>