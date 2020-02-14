<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>使用会场</title>
    <script type="text/javascript">
        jQuery(function () {
            laydate.skin('molv');
            laydate({
                elem: '#turnTime',
                format: 'YYYY-MM-DD hh:mm:ss',
                istime: true
            });
            laydate({
                elem: '#useTime',
                format: 'YYYY-MM-DD hh:mm:ss',
                istime: true
            });


            jQuery("#classTypeId").change(function () {
                var selectedClassTypeId = jQuery(this).children('option:selected').val();
                jQuery("#classId").html("");
                jQuery.ajax({
                    url: '${ctx}/admin/houqin/getClassListByClassType.json',
                    data: {
                        "classTypeId": selectedClassTypeId
                    },
                    type: 'post',
                    dataType: 'json',
                    success: function (result) {
                        if (result.code == "0") {
                            var list = result.data;
                            if (list != null && list.length > 0) {
                                var classstr = "";
                                for (var i = 0; i < list.length; i++) {
                                    classstr += "<option value='" + list[i].id + "'>" + list[i].name + "</option>";
                                }
                                jQuery("#classId").html(classstr);
                            }else{
                                jQuery("#classId").html("<option value='0'>请选择</option>");
                            }
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
            });
        });

        function addFormSubmit() {
            var caption = jQuery("#caption").val();
            var startTime = jQuery("#useTime").val();
            var endTime = jQuery("#turnTime").val();

            var nowDate = Date.parse(new Date());

            if (!jQuery("#caption").val()) {
                alert("标题不能为空");
                return;
            }
            if (!jQuery("#meetingId").val()) {
                alert("会场不能为空");
                return;
            }
            if (!startTime) {
                alert("开始时间不能为空");
                return;
            }
            var _startTime = Date.parse(new Date(Date.parse(startTime.replace(/-/g,"/"))));

            if (_startTime <= nowDate) {
                alert("开始时间必须大于现在");
                return;
            }
            if (!endTime) {
                alert("结束时间不能为空");
                return;
            }
            var _endTime = Date.parse(new Date(Date.parse(endTime.replace(/-/g,"/"))));
            if (_endTime <= nowDate) {
                alert("结束时间必须大于现在");
                return;
            }
            if (_endTime <= _startTime) {
                alert("结束时间必须大于开始时间");
                return;
            }
            jQuery.ajax({
                url: "${ctx}/admin/houqin/applyMeeting.json",
                data: jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == "0") {
                        if (result.data == 0) {
                            alert(result.message);
                            window.location.href = "/admin/houqin/queryAllMeeting.json";
                        } else if (result.data == 1) {
                            alert("此时间段的某一时间，有会场正在使用");
                            return;
                        }

                    } else {
                        alert(result.message);
                        return;
                    }
                }
            });
        }

        function meetingRecord() {
            var meetingId=jQuery("#meetingId").val();
            window.location.href="/admin/houqin/meetingApplyRecordList.json?meetingId="+meetingId;
        }

        var data = data;
        var functionsIds;

        function addMeetingRecord() {
            jQuery.ajax({
                type: "post",
                url: "/admin/houqin/selectMeeting.json",
                data: data,
                dataType: "text",
                async: false,
                success: function (result) {
                    jQuery.alerts._show('选择会场列表', null, null, 'addCont', function (confirm) {
                        if (confirm) {
                            if (functionsIds) {
                                jQuery.ajax({
                                    type: "post",
                                    url: "/admin/houqin/searchMeeting.json",
                                    data: {'id': functionsIds},
                                    dataType: "json",
                                    success: function (result) {
                                        if (result.code == "0") {
                                            var name = result.data.name;
                                            var id = result.data.id;
                                            jQuery("#meetingName").html(name);
                                            jQuery("#meetingId").val(id);
//                                            jQuery("#meetingRecord").show();
                                        } else {
                                            alert(result.message);
                                        }
                                    }
                                });
                            } else {
                                alert("没有选择！");
                            }

                        }
                    });
                    jQuery('#popup_message').html(result);
                    // 修改弹窗的位置。距窗口上边距150px，左边距30%.
                    jQuery('#popup_container').css({
                        top: 50,
                        left: '20%',
                        overflow: 'hidden'
                    });
                    jQuery('#popup_container').css("max-height", "600px");
                    jQuery('#popup_message').css("max-height", "450px");
                    jQuery('#popup_message').css('overflow-y', 'scroll');
                }
            });
        }
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">使用会场</h1>
    </div><!--pageheader-->
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于会场申请<br>
        2.<em style="color: red;">*</em>代表必填项；<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label><em style="color: red;">*</em>标题</label>
                    <span class="field">
                        <input type="text" name="meetingRecord.caption" class="longinput" id="caption"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>选择会场</label>
                <td>
                    <input type="hidden" value="" id="meetingId" name="meetingRecord.meetingId">
                    <span class="field">
                        <a href="javascript:addMeetingRecord()" class="stdbtn btn_orange">选择会场</a><font id="meetingName"></font>
                    </span>

                </td>
                </p>
                <%--<p>
                    <label>班型</label>
                    <span class="field">
                        <select id="classTypeId" name="plan.classType">
                            <option value="0">请选择</option>
                             <c:forEach items="${classTypeMap}" var="classType">
                                 <option value="${classType.key}">${classType.value}</option>
                             </c:forEach>
                        </select>
                    </span>
                </p>--%>
                <%--<p>
                    <label>班次</label>
                    <span class="field">
                       <select name="meetingRecord.classesId" class="vam" id="classId" >
								<option value="0">请选择</option>
							</select>
                    </span>
                </p>--%>
                <p>
                    <label><em style="color: red;">*</em>使用开始时间</label>
                    <span class="field">
                        <input type="text" name="meetingRecord.useTime" class="longinput" id="useTime"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>使用结束时间</label>
                    <span class="field">
                        <input type="text" name="meetingRecord.turnTime" class="longinput" id="turnTime"/>
                    </span>
                </p>
                <p>
                    <label>描述</label>
                    <span class="field">
                    <textarea rows="10" cols="10" name="meetingRecord.description" class="longinput"></textarea>
                    </span>
                </p>
                <p class="stdformbutton center">
                    <button class="submit radius2" onclick="addFormSubmit();return false;">提交</button>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
</html>