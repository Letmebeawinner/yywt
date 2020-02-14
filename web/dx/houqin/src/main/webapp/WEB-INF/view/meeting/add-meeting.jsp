<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>添加会场</title>
    <script type="text/javascript">
        jQuery(function () {
            laydate.skin('molv');
            laydate({
                elem: '#returnTime',
                format: 'YYYY-MM-DD hh:mm:ss'
            });
            laydate({
                elem: '#useTime',
                format: 'YYYY-MM-DD hh:mm:ss'
            });
        });

        function addFormSubmit() {
            var sort = jQuery("#sort").val();
            jQuery.ajax({
                url: "${ctx}/admin/houqin/addSaveMeeting.json",
                data: jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "/admin/houqin/queryAllMeeting.json";
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
        <h1 class="pagetitle">添加会场</h1>
    </div><!--pageheader-->
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于添加会场辆信息；<br>
        2.<em style="color: red;">*</em>代表必填项；<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label><em style="color: red;">*</em>会场名称</label>
                    <span class="field">
                        <input type="text" name="meeting.name" class="longinput" id="name"/>
                    </span>
                </p>
                <p>
                    <label>可容纳人数</label>
                    <span class="field">
                        <input type="text" name="meeting.peopleNo" class="longinput" id="peopleNo"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>移交时间</label>
                    <span class="field">
                        <input type="text" name="meeting.turnTime" class="longinput" id="returnTime"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>投入时间</label>
                    <span class="field">
                        <input type="text" name="meeting.useTime" class="longinput"  id="useTime"/>
                    </span>
                </p>
                <p class="stdformbutton center">
                    <button class="submit radius2" onclick="addFormSubmit();return false;">提交保存</button>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
</html>