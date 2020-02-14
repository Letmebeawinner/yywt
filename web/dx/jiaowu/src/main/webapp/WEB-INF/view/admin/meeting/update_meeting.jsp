<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>修改会议</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">
        jQuery(function () {
            laydate.skin('molv');
            laydate({
                elem: '#startTime',
                format: 'YYYY-MM-DD hh:mm:ss'
            });
            laydate({
                elem: '#endTime',
                format: 'YYYY-MM-DD hh:mm:ss'
            });
        });

        function updateMeeting() {
            var params = jQuery("#form1").serialize();
            jQuery.ajax({
                url: '${ctx}/admin/jiaowu/meeting/updateMeeting.json',
                data: params,
                type: 'post',
                dataType: 'json',
                success: function (result) {
                    if (result.code == "0") {
                        window.location.href = "${ctx}/admin/jiaowu/meeting/meetingList.json";
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
    </script>

</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">修改会议</h1>
        <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于修改会议<br/>
                    2.按要求填写相关信息,点击"提交"按钮,修改会议.<br/>
                    3.带有红色<span style="color: red">*</span>标记的内容为必填部分。
				</span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="updates" class="subcontent">
            <!-- 主要内容开始 -->
            <div id="validation" class="subcontent">

                <form id="form1" class="stdform stdform2" method="post" action="">

                    <p>
                        <input type="hidden" name="meeting.id" value="${meeting.id}"/>
                        <label><em style="color: red;">*</em>会议名称</label>
                        <span class="field"><input type="text" name="meeting.name" id="name" class="longinput"
                                                   value="${meeting.name}"/></span>
                    </p>


                    <p>
                        <label><em style="color: red;">*</em>开始时间</label>
                        <span class="field">
                            	<input id="startTime" type="text" class="longinput" name="meeting.startTime" readonly="readonly"
                                       value="<fmt:formatDate type='both' value='${meeting.startTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
                            </span>
                    </p>
                    <p>
                        <label><em style="color: red;">*</em>结束时间</label>
                        <span class="field">
                            	<input id="endTime" type="text" class="longinput" name="meeting.endTime" readonly="readonly"
                                       value="<fmt:formatDate type='both' value='${meeting.endTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
                            </span>
                    </p>
                    <p>
                        <label>备注</label>
                        <span class="field"><textarea cols="80" rows="5" name="meeting.note" class="mediuminput"
                                                      id="note">${meeting.note}</textarea></span>
                    </p>
                    <p class="stdformbutton" style="text-align: center">
                        <button class="radius2" onclick="updateMeeting(); return false" id="submitButton">提交</button>
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