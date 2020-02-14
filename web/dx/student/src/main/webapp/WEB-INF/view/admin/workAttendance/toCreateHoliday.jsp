<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>请假</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">

        jQuery(function () {
            laydate.skin('molv');
            laydate({
                elem: '#startTime',
                format: 'YYYY-MM-DD'
                // istime: true
            });
            laydate({
                elem: '#endTime',
                format: 'YYYY-MM-DD'
                // istime: true
            });
        });

        function addHoliday() {
            var studentId = jQuery("#studentId").val();
            if (studentId == '') {
                jAlert('请选择学员', '提示', function () {
                });
                return;
            }
            var startTimeValue = jQuery("#startTime").val();
            if (startTimeValue == '') {
                jAlert('请填写开始时间', '提示', function () {
                });
                return;
            }
            var endTimeValue = jQuery("#endTime").val();
            if (endTimeValue == '') {
                jAlert('请填写结束时间', '提示', function () {
                });
                return;
            }
            var startTime = new Date(startTimeValue);
            var endTime = new Date(endTimeValue);

            if (startTime.getTime() == endTime.getTime()) {
                if (jQuery("#beginLastAfternoon").val() == 1 && jQuery("#endLastAfternoon").val() == 0) {
                    jAlert('结束时间不能大于开始时间', '提示', function () {
                    });
                }
            }

            if ((parseInt(endTime - startTime) / (1000 * 60 * 60 * 24)) >= 2) {
                jConfirm('该学员请假时间超过了2天，请确认是否提交该申请？', '确认', function (r) {
                    if (r) {
                        var params = jQuery("#form1").serialize();
                        jQuery.ajax({
                            url: '${ctx}/admin/jiaowu/holiday/createHoliday.json',
                            data: params,
                            type: 'post',
                            dataType: 'json',
                            success: function (result) {
                                jAlert(result.message, '提示', function () {
                                });
                                if (result.code == "0") {
                                    jQuery("#note").val("");
                                    jQuery("#startTime").val("");
                                    jQuery("#endTime").val("");
                                }

                            },
                            error: function (e) {
                                jAlert('添加失败', '提示', function () {
                                });
                            }
                        });
                    }
                });
            } else {
                var params = jQuery("#form1").serialize();
                console.log(params);
                jQuery.ajax({
                    url: '${ctx}/admin/jiaowu/holiday/createHoliday.json',
                    data: params,
                    type: 'post',
                    dataType: 'json',
                    success: function (result) {
                        jAlert(result.message, '提示', function () {
                        });
                        if (result.code == "0") {
                            jQuery("#note").val("");
                            jQuery("#startTime").val("");
                            jQuery("#endTime").val("");
                        }

                    },
                    error: function (e) {
                        jAlert('添加失败', '提示', function () {
                        });
                    }
                });
            }

        }

        function selectUser() {
            window.open('${ctx}/jiaowu/user/userListForSelect.json', 'newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=1000,height=800');
        }

        function addUser(userIdAndName) {
            jQuery("#studentId").val(userIdAndName[0]);
            jQuery("#studentName").val(userIdAndName[1]);
            jQuery("#userspan").html(userIdAndName[1]);
        }

        function beginAfternoon(obj) {

        }

        function endAfternoon(obj) {
            var startTimeValue = jQuery("#startTime").val();
            if (startTimeValue == null) {
                jAlert('请填写开始时间', '提示', function () {
                });
            }
            var endTimeValue = jQuery("#endTime").val();
            if (endTimeValue == null) {
                jAlert('请填写结束时间', '提示', function () {
                });
            }
            var startTime = new Date(startTimeValue).getTime();
            var endTime = new Date(endTimeValue).getTime();
            if (startTime == endTime) {
                if (jQuery("#beginLastAfternoon").val() == 1 && jQuery("#endLastAfternoon").val() == 0) {
                    jAlert('结束时间不能大于开始时间', '提示', function () {
                    });
                }
            }
        }
    </script>

</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">请假</h1>
        <span>
            <span style="color:red">说明</span><br>
            1.本页面用于请假<br/>
            2.按要求填写相关信息,点击"提交"按钮,请假.<br/>
            3.带有红色<span style="color: red">*</span>标记的内容为必填部分。
    </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="updates" class="subcontent">
            <!-- 主要内容开始 -->
            <div id="validation" class="subcontent">

                <form id="form1" class="stdform stdform2" method="post" action="">

                    <p>
                        <input type="hidden" name="holiday.userId" id="studentId" value="${user.id}"/>
                        <input type="hidden" name="holiday.userName" id="studentName" value="${user.name}"/>
                        <label><em style="color: red;">*</em>学员</label>
                        <span class="field">
                            <c:if test="${userType==2 || userType==1}">
                                <span id="userspan"></span>
                                <a href="javascript:selectUser()" class="stdbtn btn_orange">选择学员</a>
                            </c:if>
                            <c:if test="${userType==3}">
                                <span id="userspan1">${user.name}</span>
                            </c:if>
                        </span>
                    </p>

                    <p>
                        <label><em style="color: red;">*</em>开始时间</label>
                        <span class="field">
                            <input id="startTime" type="text" class="longinput" name="holiday.beginTime" readonly/>
                            <span class="n_file_sel">
                                <select id="beginLastAfternoon" name="holiday.beginLastAfternoon"
                                        onchange="beginAfternoon(this)">
                                    <option value="0">上午</option>
                                    <option value="1">下午</option>
                                </select>
                            </span>
                         </span>


                    </p>
                    <p>
                        <label><em style="color: red;">*</em>结束时间</label>
                        <span class="field">
                            <input id="endTime" type="text" class="longinput" name="holiday.endTime" readonly/>
                            <span class="n_file_sel">
                                <select id="endLastAfternoon" name="holiday.endLastAfternoon"
                                        onchange="endAfternoon(this)">
                                    <option value="0">上午</option>
                                    <option value="1">下午</option>
                                </select>
                            </span>
                         </span>
                    </p>
                    <%--<p>--%>
                    <%--<label>请假时长类型</label>--%>
                    <%--<span class="field">--%>
                    <%--<select name="holiday.length">--%>
                    <%--<option value="">请选择</option>--%>
                    <%--<option value="上午">上午</option>--%>
                    <%--<option value="下午">下午</option>--%>
                    <%--<option value="1天">1天</option>--%>
                    <%--</select>--%>
                    <%--</span>--%>
                    <%--</p>--%>
                    <p>
                        <label><em style="color: red;">*</em>请假类型</label>
                        <span class="field">
                            <select name="holiday.leaType">
                                <option value="">请选择</option>
                                <option value="事假">事假</option>
                                <option value="病假">病假</option>
                                <option value="公假">公假</option>
                            </select>
                       </span>
                    </p>
                    <p>
                        <label>原因</label>
                        <span class="field"><textarea cols="80" rows="5" name="holiday.reason" class="mediuminput"
                                                      id="note"></textarea></span>
                    </p>
                    <p class="stdformbutton" style="text-align: center">
                        <button class="radius2" onclick="addHoliday();return false;" id="submitButton">提交</button>
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