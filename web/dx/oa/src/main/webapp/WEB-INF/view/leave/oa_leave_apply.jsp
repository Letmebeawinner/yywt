<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>请假申请</title>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="background: #fff;">
        <h1 class="page-list" style="text-align: center;">
            校（院）教职工请销假审批和备案表
        </h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper users-car">
        <form method="post" action="" id="submitForm">
            <div class="testtle-tables">
                <input type="hidden" name="processDefinitionId" value="${processDefinition.id}">
                <table border="1">
                    <tr>
                        <td class="pt"><small class="c-red">*</small>姓名</td>
                        <td><span>${applyName}</span></td>
                        <td class="pt"><small class="c-red">*</small>参加工作时间</td>
                        <td>
                            <span>
                            <input type="text" name = "oaLeave.workTime"  style="width:90%;"
                                   id = "workTime" value="<fmt:formatDate value="${workTime}" pattern="yyyy-MM"></fmt:formatDate>">
                            </span>
                        </td>
                    </tr>

                    <tr>
                        <td class="pt"><small class="c-red">*</small>所在部门</td>
                        <td><span>${departmentName}</span></td>
                        <td class="pt"><small class="c-red">*</small>现任职务</td>
                        <td><span><input type="text" name="oaLeave.position" style="width:90%;" id = "position" value="${position}"></span></td>
                    </tr>
                    <tr>
                        <td class="pt"><small class="c-red">*</small>请假种类</td>
                        <td colspan="3" style="text-align: left;">
                            <span> <input type="radio" name="oaLeave.leaveType"  class="checkinput" value="0"/>&nbsp;公务假&nbsp;</span>
                            <span><input type="radio" name="oaLeave.leaveType"  class="checkinput" value="1"/>&nbsp;事假</span>
                            <span><input type="radio" name="oaLeave.leaveType"  class="checkinput" value="2"/>&nbsp;婚假</span>
                            <span><input type="radio" name="oaLeave.leaveType" class="checkinput" value="3"/>&nbsp;病假</span>
                            <span><input type="radio" name="oaLeave.leaveType"  class="checkinput" value="4"/>&nbsp;产假</span>
                            <span><input type="radio" name="oaLeave.leaveType"  class="checkinput" value="5"/>&nbsp;丧假</span>
                            <span><input type="radio" name="oaLeave.leaveType"  class="checkinput" value="6"/>&nbsp;探亲假   </span>

                        </td>
                    </tr>
                    <tr>
                        <td class="pt"><small class="c-red">*</small>请假时间</td>
                        <td colspan="3">

                               <span>
                                  <input type="text" name = "oaLeave.startTime" id = "startTime" readonly style = "width:30%;height:42px;text-align: center;">
                                  到<input type="text" name = "oaLeave.endTime" id = "endTime" readonly style = "width:30%;height:42px;text-align: center;">
                                  共<input type = "text" name = "oaLeave.leaveDays" id = "leaveDays" style = "width:30%;height:42px;text-align: center;">天
                               </span>
                        </td>
                    </tr>
                    <tr>
                        <td class="pt"><small class="c-red">*</small>请假事由</td>
                        <td colspan="3"><textarea name="oaLeave.reason"  cols="30" rows="2" id = "reason"></textarea></td>
                    </tr>
                    <tr>
                    <tr>
                        <td class="pt" rowspan="2">部门负责人审批意见</td>
                        <td class="pt" rowspan="2" style="border-right: none;color:#666;"><textarea disabled  cols="30" rows="3" id = "departmentOption" disabled>${oaLeave.departmentOption}</textarea></td>
                        <td colspan="2" style="border: none;border-right: 1px solid #ddd; "></td>
                    </tr>
                    <tr>
                        <td class="pt" colspan="" style="border:none;border-bottom: 1px solid #ddd; color:#666;">签字：</td>
                        <div id="img-box">
                            <div id="pos0"  style="position:absolute;top: 350px;left: 64%;"bgcolor=#ffffff></div>
                        </div>
                        <td class="pt" colspan="1" style="color:#666;border-left:none;border-top:none"><span>年 月 日</span></td>
                    </tr>
                    <tr>
                        <td class="pt" rowspan="2">分管校（院）领导审批意见</td>
                        <td class="pt" rowspan="2" style="border-right: none;color:#666;"><textarea cols="30" rows="3" id = "schoolOption" disabled>${oaLeave.schoolOption}</textarea></td>
                        <td colspan="2" style="border: none;border-right: 1px solid #ddd; "></td>
                    </tr>
                    <tr>
                        <td class="pt" colspan="" style="border:none;border-bottom: 1px solid #ddd; color:#666;">签字：</td>
                        <div id="pos1"  style="position:absolute;top: 450px;left: 64%;"bgcolor=#ffffff></div>
                        <td class="pt" colspan="1" style="color:#666;border-left:none;border-top:none"><span>年 月 日</span></td>
                    </tr>
                    <tr>
                        <td class="pt" rowspan="2">常务副校长/副校长（主持工作）审批意见</td>
                        <td class="pt" rowspan="2" style="border-right: none; color:#666;"><textarea  cols="30" rows="3" id = "schoolLeaderOption" disabled>${oaLeave.schoolLeaderOption}</textarea></td>
                        <td colspan="2" style="border: none;border-right: 1px solid #ddd; "></td>
                    </tr>
                    <tr>
                        <td class="pt" colspan="" style="border:none;border-bottom: 1px solid #ddd; color:#666;">签字：</td>
                        <div id="pos2"  style="position:absolute;top: 550px;left: 64%;"bgcolor=#ffffff></div>
                        <td class="pt" colspan="1" style="color:#666;border-left:none;border-top:none"><span>年 月 日</span></td>
                    </tr>
                    <tr>
                        <td class="pt" rowspan="3">请假备案情况</td>
                        <td class="pt" rowspan="3" style="border-right: none; color:#666;"><textarea  cols="30" rows="3" id = "leaveRecordSituation" disabled>${oaLeave.leaveRecordSituation}</textarea></td>
                        <td colspan="2" style="border: none;border-right: 1px solid #ddd; "></td>
                    </tr>
                    <tr>
                        <td class="pt" colspan="2" style="border:none; color:#666;border-right: 1px solid #ddd;">
                            <p class="testtle-txt">请假人已于年 月 日进行请假备案</p>
                        </td>
                    </tr>
                    <tr>
                        <td class="pt" colspan="2" style="border:none; color:#666;border-right: 1px solid #ddd;border-bottom: 1px solid #ddd;" >
                            <p class="testtle-txt">组织人事处备案人（签字）：${oaLeave.leaveRecordSign}</p>
                        </td>
                    </tr>
                    <tr>
                        <td class="pt" rowspan="4">销假备案情况</td>
                        <td class="pt" rowspan="4" style="border-right: none; color:#666;"><textarea cols="30" rows="3" id = "cancelLeaveRecordSituation" disabled>${oaLeave.cancelLeaveRecordSituation}</textarea></td>
                        <td colspan="2" style="border: none;border-right: 1px solid #ddd; "></td>
                    </tr>
                    <tr>
                        <td class="pt" colspan="2" style="border:none; color:#666;border-right: 1px solid #ddd;">
                            <p class="testtle-txt">请假人已于年 月 日进行销假备案</p>
                        </td>
                    </tr>
                    <tr>
                        <td class="pt" colspan="2" style="border:none; color:#666;border-right: 1px solid #ddd;" >
                            <p class="testtle-txt">请假人（签字）：${oaLeave.applySign}</p>
                        </td>
                    </tr>
                    <tr>
                        <td class="pt" colspan="2" style="border:none; color:#666;border-right: 1px solid #ddd;border-bottom: 1px solid #ddd;" >
                            <p class="testtle-txt">组织人事处备案人（签字）：${oaLeave.cancelLeaveRecordSign}</p>
                        </td>
                    </tr>

                </table>
                <div class="pt10">注：请假2天以内（含 2天）的，部门副职或其他教职工由部门正职（或主持工作的副职）批准，部门正职（或主持工作的副职）由分管校（院）领导批准；请假2天以上的，部门副职或其他教职工经部门正职（或主持工作的副职）同意，报分管校（院）领导批准，部门正职（或主持工作的副职）经分管校（院）领导同意，报常务副校（院）长批准。</div>
                <div class="buttons" style="text-align: center;margin-top:30px;margin-bottom: 20px">
                <a id="chooseBtn" class="submit radius2" onclick = "chooseUser()" style="display: none; cursor: pointer">选择分管副校长审批</a>
                <input type="hidden" value="" name="userIds" id="userIds"/>
                <a class="submit radius2" style="cursor: pointer" onclick="addFormSubmit();return false;">提交</a>
            </div>
            </div>
        </form>
    </div>


</div>
<script type = "text/javascript">
    var leaderLevel='${leaderLevel}';

    /**
     * 点击选择审批人
     */
    function chooseUser() {
        jQuery.ajax({
            url: '${ctx}/admin/oa/ajax/querySchoolRole.json',
            type: 'POST',
            data: {},
            dataType: 'html',
            success: function (result) {
                jQuery.alerts._show("选择审批领导", result, null, 'dialog', function (data) {
                    if (data) {
                        var userId = jQuery("input[name='user']:checked").val();
                        console.log(userId);
                        var userName = jQuery("#username_" + userId).text().trim();
                        jQuery("#chooseBtn").html(userName+"审批");
                        jQuery("#userIds").val(userId);
                    }
                });
            }
        });
    }


    jQuery(function(){
        laydate.skin('molv');
//        laydate({
//            elem: '#workTime',
//            istoday: false,
//            format: 'YYYY-MM-DD hh:mm:ss'
//        });
        laydate({
            elem: '#startTime',
            format:'YYYY-MM-DD hh:mm:ss',
            istoday: false,
            istime: true,
            choose: function() {
                calcLeaveDays();
            }
        });

        laydate({
            elem: '#endTime',
            format:'YYYY-MM-DD hh:mm:ss',
            istoday: false,
            istime: true,
            choose: function() {
                calcLeaveDays();
            }
        });

        jQuery('#startTime, #endTime').keyup(function(){
            calcLeaveDays();
        })

    });

    function addFormSubmit() {
        if (!jQuery("#workTime").val()) {
            alert("工作时间不能为空");
            return;
        }

        if (!jQuery("#position").val()) {
            alert("现任职务不能为空");
            return;
        }

        if (!jQuery("input[name='oaLeave.leaveType']:checked").val()) {
            alert("请假种类不能为空");
            return;
        }

        var startTimeVal = jQuery("#startTime").val()
        var endTimeVal = jQuery("#endTime").val()

        if (startTimeVal.length < 1) {
            alert("开始时间不能为空");
            return;
        }
        if (endTimeVal.length < 1) {
            alert("结束时间不能为空");
            return;
        }
        // 开始时间不能大于结束时间
        var startTimeDate = new Date(startTimeVal.replace(/-/g, '/'));
        var endTimeDate = new Date(endTimeVal.replace(/-/g, '/'));
        if (endTimeDate < startTimeDate) {
            alert('结束时间必须大于开始时间');
            return;
        }

        if (!jQuery("#reason").val()) {
            alert('请假事由不能为空');
            return;
        }
        if (jQuery("#leaveDays").val()>2  && leaderLevel=='1' && !jQuery("#userIds").val()) {
            alert('请选择分管副校长审批');
            return;
        }

        var params = jQuery("#submitForm").serialize();
        jQuery.ajax({
            url: '${ctx}/admin/oa/leave/process/start.json',
            data: params,
            type: 'post',
            dataType: 'json',
            success: function (result) {
                if (result.code == "0") {
                    alert(result.message);
                    window.location.href = "${ctx}/admin/oa/task/history/mine.json";
                } else {

                }
            }
        })
    }

    function calcLeaveDays() {
        var startTimeVal = jQuery("#startTime").val();
        var endTimeVal = jQuery("#endTime").val();
        if (!startTimeVal) {
           return;
        }
        if (!endTimeVal) {
           return;
        }
        var dates = getDateDiff(startTimeVal, endTimeVal);
        if(dates>2 && leaderLevel=='1'){
            jQuery("#chooseBtn").show();
        }else {
            jQuery("#chooseBtn").hide();
        }
        jQuery("#leaveDays").val(dates);
    }

    function getDateDiff(startDate,endDate) {

        var startTime = new Date(Date.parse(startDate.replace(/-/g,   "/"))).getTime();
        var endTime = new Date(Date.parse(endDate.replace(/-/g,   "/"))).getTime();

        var dates = Math.floor(Math.abs((subDate(endTime) - subDate(startTime))/(1000*60*60*24)));


        return dates;
    }

    function subDate(date) {
        var nowTimeDate = new Date(date);
        nowTimeDate.setHours(0, 0, 0, 0);//设为当天0点0分0秒0毫秒。
        return nowTimeDate.getTime();//把nowTime的日期加到startTime上
    }

    function getHour(date) {
        if (new Date(date).getHours() < 14) {
            return "上午"
        } else {
            return "下午"
        }
    }

    function getHourAft(date) {
        var hour =new Date(date).getHours()
        if (0===hour) {
            return "零点"
        } else if (0 < hour && hour < 14) {
            return "上午"
        } else {
            return "下午"
        }
    }

    //取整
    function getResult(num){
        return parseInt(num);
    }

    //四舍五入到num后面的n位
    function getResult(num,n){
        return Math.round(num*Math.pow(10,n))/Math.pow(10,n);
    }

    //截取n位
    function getresult(num,n){
        return num.toString().replace(new RegExp("^(\\-?\\d*\\.?\\d{0,"+n+"})(\\d*)$"),"$1")+0;
    }
</script>
</body>
</html>