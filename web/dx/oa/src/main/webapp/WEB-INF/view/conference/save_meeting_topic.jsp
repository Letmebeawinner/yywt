<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>新建议题</title>
    <script type="text/javascript">
        function addTeacherLibrary() {
        if (jQuery("input[name='meetingTopic.name']").val() == '') {
            alert("议题名称不能为空");
            return;
        }

        if (jQuery("input[name='meetingTopic.emergencyDegree']").val() == '') {
            alert("紧急程度不能为空");
            return;
        }

        if (jQuery("input[name='meetingTopic.reporter']").val() == '') {
            alert("汇报人不能为空");
            return;
        }
        if (jQuery("input[name='meetingTopic.attendPeople']").val() == '') {
            alert("列席人不能为空");
            return;
        }


        if (jQuery("textarea[name='meetingTopic.subjectContent']").val() == '') {
            alert("议题内容不能为空");
            return;
        }

        var params = jQuery("#saveForm").serialize();
        jQuery.ajax({
            url: '/admin/oa/conference/meetingTopic/save.json',
            data: params,
            type: 'post',
            dataType: 'json',
            success: function (result) {
                if (result.code == "0") {
                    window.location.href = "/admin/oa/conference/meetingTopic/list.json";
                } else {

                }
            }
        })
        }
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">新建议题</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来新建议题<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="saveForm">
                <p>
                    <label><em style="color: red;">*</em>议题名称</label>
                    <span class="field">
                        <input type="text" name="meetingTopic.name" class="longinput" maxlength="48">
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>紧急程度</label>
                    <span class="field">
                        <input type="text" name="meetingTopic.emergencyDegree" class="longinput" maxlength="48">
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>汇报人</label>
                    <span class="field">
                        <input type="text" name="meetingTopic.reporter" class="longinput" maxlength="48">
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>列席人</label>
                    <span class="field">
                        <input type="text" name="meetingTopic.attendPeople" class="longinput" maxlength="48">
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>议题内容</label>
                    <span class="field">
                        <textarea name="meetingTopic.subjectContent" cols="30" rows="10" class="longinput"></textarea>
                    </span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addTeacherLibrary();return false;">添 加</button>
                </p>
            </form>
            <br/>
        </div>
    </div>
</div>
</body>
</html>