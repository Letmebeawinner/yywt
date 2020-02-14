<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>会议管理员审批</title>
    <script type = "text/javascript" src = "${ctx}/static/js/oa_select_options.js"></script>
    <script type="text/javascript">

        jQuery(function () {
            laydate.skin('molv');
            laydate({
                elem: '#time',
                istime: true,
                format: 'YYYY-MM-DD hh:mm:ss'
            });
            queryListInfo("agendaName", 4);
        });

        function addAgenda() {
            if (!jQuery("#agendaName").val()) {
                alert("议题不能为空");
                return;
            }
            if (jQuery("input[name='agenda.time']").val() == '') {
                alert("时间不能为空");
                return;
            }

            if (jQuery("input[name='agenda.location']").val() == '') {
                alert("地点不能为空");
                return;
            }

            if (jQuery("input[name='agenda.compere']").val() == '') {
                alert("主持人不能为空");
            }
            if (jQuery("input[name='agenda.bePresent']").val() == '') {
                alert("出席不能为空");
                return;
            }


            if (jQuery("input[name='agenda.absent']").val() == '') {
                alert("缺席不能为空");
                return;
            }

            if (jQuery("input[name='agenda.attend']").val() == '') {
                alert("列席不能为空");
                return;
            }

            if (jQuery("input[name='agenda.record']").val() == '') {
                alert("记录不能为空");
                return;
            }

            if (jQuery("input[name='agenda.agendaName']").val() == '') {
                alert("议题不能为空");
                return;
            }

            var data = jQuery("#saveForm").serialize();
            jQuery.ajax({
                url: "${ctx}/admin/oa/agenda/process/start.json",
                data: data,
                type: "post",
                dataType: "json",
                async: false,
                cache : false,
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "${ctx}/admin/oa/task/history/mine.json";
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
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">新建议程</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来新建议程<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="saveForm">
                <p>
                    <label><em style="color: red;">*</em>议题</label>
                    <span class="field">
                        <select name="agenda.topicId" class="longinput" id = "agendaName">
                        </select>
                    </span>
                </p>
                <input type="hidden" name="processDefinitionId" value="${processDefinition.id}">
                <p>
                    <label><em style="color: red;">*</em>时间</label>
                    <span class="field">
                        <input type="text" name="agenda.time" id="time" class="longinput" readonly="readonly">
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>地点</label>
                    <span class="field">
                        <input type="text" name="agenda.location" class="longinput">
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>主持人</label>
                    <span class="field">
                        <input type="text" name="agenda.compere" class="longinput">
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>出席</label>
                    <span class="field">
                        <input type="text" name="agenda.bePresent" class="longinput">
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>缺席</label>
                    <span class="field">
                        <input type="text" name="agenda.absent" class="longinput">
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>列席</label>
                    <span class="field">
                        <input type="text" name="agenda.attend" class="longinput">
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>记录</label>
                    <span class="field">
                        <textarea name="agenda.record" rows="10" cols="5" class="longinput" style="text-align: left"></textarea>
                    </span>
                </p>

                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addAgenda();return false;">提 交</button>
                </p>
            </form>
            <br/>
        </div>
    </div>
</div>
</body>
</html>