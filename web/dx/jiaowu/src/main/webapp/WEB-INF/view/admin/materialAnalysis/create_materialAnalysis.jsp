<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>新建党性材料分析</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">

        jQuery(function () {
            jQuery("#type").change(function () {
                var selectedType = jQuery(this).children('option:selected').val();
                if (selectedType == "meeting") {
                    jQuery("#meetingp").show();
                } else {
                    jQuery("#meetingp").hide();
                }
            });
        });

        function addMaterialAnalysis() {
            var params = jQuery("#form1").serialize();
            jQuery.ajax({
                url: '${ctx}/admin/jiaowu/materialAnalysis/createMaterialAnalysis.json',
                data: params,
                type: 'post',
                dataType: 'json',
                success: function (result) {
                    if (result.code == "0") {
                        window.location.href = "${ctx}/admin/jiaowu/materialAnalysis/materialAnalysisList.json";
                    } else {
                        jAlert(result.message, '提示', function () {
                        });
                    }
                },
                error: function (e) {
                    jAlert('添加失败', '提示', function () {
                    });
                }
            });
        }

        function selectMeeting() {
            window.open('${ctx}/jiaowu/meeting/meetingListForSelect.json', 'newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=800,height=600');
        }

        function addMeetingIdAndName(meetingIdAndName) {
            jQuery("#meetingId").val(meetingIdAndName.split("-")[0]);
            jQuery("#meetingspan").html(meetingIdAndName.split("-")[1]);
            jQuery("#meetingName").val(meetingIdAndName.split("-")[1]);
        }

    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">新建党性材料分析</h1>
        <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于新建党性材料分析<br/>
                    2.按要求填写相关信息,点击"提交"按钮,新建党性材料分析.<br/>
                    3.带有红色<span style="color: red">*</span>标记的内容为必填部分。
				</span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="updates" class="subcontent">
            <!-- 主要内容开始 -->
            <div id="validation" class="subcontent">

                <form id="form1" class="stdform stdform2" method="post" action="">
                    <p>
                        <input type="hidden" id="meetingId" name="materialAnalysis.meetingId"/>
                        <input type="hidden" id="meetingName" name="materialAnalysis.meetingName"/>
                        <label><em style="color: red;">*</em>类型</label>
                        <span class="field">
                            <select name="materialAnalysis.type" id="type">
                               	<option value="train">培训党性材料分析</option>
                                <option value="meeting">会议党性材料分析</option>
                            </select>
                            </span>
                    </p>

                    <p id="meetingp" style="display: none;">
                        <label><em style="color: red;">*</em>会议</label>
                        <span class="field">
                            <span id="meetingspan"></span>
                            <a href="javascript:selectMeeting()" class="stdbtn btn_orange">选择会议</a>
                            </span>
                    </p>

                    <p>
                        <label><em style="color: red;">*</em>内容</label>
                        <span class="field"><textarea cols="80" rows="5" name="materialAnalysis.content" class="mediuminput" id="content"></textarea></span>
                    </p>
                    <p>
                        <label>备注</label>
                        <span class="field"><textarea cols="80" rows="5" name="materialAnalysis.note" class="mediuminput" id="note"></textarea></span>
                    </p>
                    <p class="stdformbutton" style="text-align: center">
                        <button class="radius2" onclick="addMaterialAnalysis();return false;" id="submitButton">提交</button>
                    </p>
                </form>
            </div>
            <!-- 主要内容结束 -->
            <div class="clear"></div>
        </div><!-- #updates -->
    </div>
</div>
</body>
</html>