<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>添加维护记录</title>
    <script type="text/javascript">
        jQuery(function () {
            laydate.skin('molv');
            laydate({
                elem: '#useTime',
                format: 'YYYY-MM-DD hh:mm:ss',
                istime: true
            });

        });

        function addFormSubmit() {
            var name=jQuery("#name").val();
            var repairTime = jQuery("#useTime").val();
            var description=jQuery("#description").val();

            if(!name){
                alert("维护人不能为空");
                return;
            }
            if (!jQuery("#meetingId").val()) {
                alert("会场不能为空");
                return;
            }
            if (!repairTime) {
                alert("维护时间不能为空");
                return;
            }
            if (!description) {
                alert("设备情况不能为空");
                return;
            }

            jQuery.ajax({
                url: "${ctx}/admin/houqin/addMeetingRepairRecord.json",
                data: jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == "0") {
                            alert(result.message);
                            window.location.href = "/admin/houqin/queryRepairRecordList.json";
                        } else {
                        alert(result.message);
                        return;
                    }
                }
            });
        }

    </script>

    <script type="text/javascript">
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
        <h1 class="pagetitle">添加维护记录</h1>
    </div><!--pageheader-->
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.<em style="color: red;">*</em>代表必填项；<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label><em style="color: red;">*</em>维护人</label>
                    <span class="field">
                        <input type="text" name="meetingRepairRecord.name" class="longinput" id="name"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>选择会场</label>
                <td>
                    <input type="hidden" value="" id="meetingId" name="meetingRepairRecord.meetingId">
                    <span class="field">
                        <a href="javascript:addMeetingRecord()" class="stdbtn btn_orange">选择会场</a><font id="meetingName"></font>
                    </span>

                </td>
                </p>

                <p>
                    <label><em style="color: red;">*</em>维护时间</label>
                    <span class="field">
                        <input type="text" name="meetingRepairRecord.repairTime" class="longinput" id="useTime"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>设备情况</label>
                    <span class="field">
                        <textarea rows="10" cols="10" name="meetingRepairRecord.description" id="description" class="longinput"></textarea>
                    </span>
                </p>
                <p>
                    <label>修改的状态</label>
                    <span class="field">
                    <select name="meetingRepairRecord.status">
                        <option value="0">在维修</option>
                        <option value="1">已正常</option>
                    </select>
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