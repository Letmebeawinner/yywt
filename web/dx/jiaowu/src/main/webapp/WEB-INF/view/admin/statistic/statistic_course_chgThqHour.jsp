<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>更改教师课时</title>
    <script type="text/javascript">
        function addFormSubmit() {
            var schHour = jQuery("input[name='schHour']").val()

            jQuery.ajax({
                url: "${ctx}/admin/jiaowu/statistic/saveOrUpdateThqHour.json",
                data: jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                success: function (result) {
                    alert(result.message);
                    if (result.code === "0") {
                        window.location.href = "${ctx}/admin/jiaowu/statistic/statisticCourseLesson.json";
                    }
                }
            });
        }

    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">更改教师课时</h1>
        <span>
                <span style="color:red">说明</span><br>
                1.本页面用于更改教师课时.<br />
                2.在统计时, 这里输入的课时, <span style="color:red">将于排课产生的课时相加</span>.<br />
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label><em style="color: red;">*</em>教师课时</label>
                    <span class="field">
                        <input type="text" name="hour" value="${entity.hour}" class="longinput" maxlength="8"
                               onchange="if(/\D/.test(this.value)){alert('只能输入数字','提示',null);this.value='';}"
                        />
                    </span>
                </p>

                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit();return false;">提交保存</button>
                </p>

                <%--隐藏域--%>
                <input type="hidden" name="thqId" value="${thqId}" class="longinput"/>
                <input type="hidden" name="id" value="${entity.id}" class="longinput"/>
            </form>
        </div>
    </div>
</div>
</body>
</html>