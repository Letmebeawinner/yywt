<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>修改会场</title>
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
            jQuery.ajax({
                url: "${ctx}/admin/houqin/updateMeeting.json",
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
        <h1 class="pagetitle">修改会场</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <input type="hidden" name="meeting.id" value="${meeting.id}">
                    <label><em style="color: red;">*</em>会场名称</label>
                    <span class="field">
                        <input type="text" name="meeting.name" class="longinput" id="name" value="${meeting.name}"/>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>移交时间</label>
                    <span class="field">
                        <input type="text" name="meeting.returnTime" class="longinput" id="returnTime"
                               value="<fmt:formatDate value="${meeting.turnTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>投入时间</label>
                    <span class="field">
                        <input type="text" name="meeting.useTime" class="longinput" id="useTime"
                               value="<fmt:formatDate value="${meeting.useTime}" pattern="yyyy-MM-dd HH:mm:ss "/> "/>
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