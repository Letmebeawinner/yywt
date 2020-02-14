<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>分管校领导领导审批</title>
    <link rel="stylesheet" href="${ctx}/kinggrid/dialog/artDialog/ui-dialog.css">
    <link rel="stylesheet" href="${ctx}/kinggrid/core/kinggrid.plus.css">
    <style>
        .laydate_table {
            display: none;
        }

        #laydate_hms {
            display: none !important;
        }
    </style>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="background: #fff;">
        <h1 class="page-list" style="text-align: center;">
            校（院）教职工请销假审批和备案表
        </h1>
    </div><!--pageheader-->
    <div>
        <input type="button" class="test" style = "margin-left: 21px;cursor: pointer" value="电子签章" onclick = "clickSignature(this, 'pos1')">
    </div>
    <div id="contentwrapper" class="contentwrapper users-car">
        <form method="post" action="" id="submitForm">
            <input type = "hidden" id = "isSignature">
            <input type="hidden" value="${task.id}" id="taskId" name="taskId">
            <input type="hidden" value="${oaLeave.processInstanceId}" name="processInstanceId">
            <input type="hidden" name="oaLeave.audit" id="audit">
            <input type="hidden" name="oaLeave.schoolAuditTime" value="<fmt:formatDate value="${nowDate}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>">
            <input type="hidden" name="comment" id="comment">
            <div class="testtle-tables">
                <table border="1">
                    <tr>
                        <td class="pt"><small class="c-red">*</small>姓名</td>
                        <td><span>${applyName}</span></td>
                        <td class="pt"><small class="c-red">*</small>参加工作时间</td>
                        <td><span>${oaLeave.workTime}</span></td>
                    </tr>

                    <tr>
                        <td class="pt"><small class="c-red">*</small>所在部门</td>
                        <td><span>${departmentName}</span></td>
                        <td class="pt"><small class="c-red">*</small>现任职务</td>
                        <td><span>${oaLeave.position}</span></td>
                    </tr>
                    <tr>
                        <td class="pt"><small class="c-red">*</small>请假种类</td>
                        <td colspan="3" style="text-align: left;">
                            <span> <input type="radio" name="oaLeave.leaveType"  class="checkinput" value="0" <c:if test = "${oaLeave.leaveType == 0}">checked</c:if> disabled/>&nbsp;公务假&nbsp;</span>
                            <span><input type="radio" name="oaLeave.leaveType"  class="checkinput" value="1" <c:if test = "${oaLeave.leaveType == 1}">checked</c:if> disabled/>&nbsp;事假</span>
                            <span><input type="radio" name="oaLeave.leaveType"  class="checkinput" value="2" <c:if test = "${oaLeave.leaveType == 2}">checked</c:if> disabled/>&nbsp;婚假</span>
                            <span><input type="radio" name="oaLeave.leaveType" class="checkinput" value="3" <c:if test = "${oaLeave.leaveType == 3}">checked</c:if> disabled/>&nbsp;病假</span>
                            <span><input type="radio" name="oaLeave.leaveType"  class="checkinput" value="4" <c:if test = "${oaLeave.leaveType == 4}">checked</c:if> disabled/>&nbsp;产假</span>
                            <span><input type="radio" name="oaLeave.leaveType"  class="checkinput" value="5" <c:if test = "${oaLeave.leaveType == 5}">checked</c:if> disabled/>&nbsp;丧假</span>
                            <span><input type="radio" name="oaLeave.leaveType"  class="checkinput" value="6" <c:if test = "${oaLeave.leaveType == 6}">checked</c:if> disabled/>&nbsp;探亲假</span>

                        </td>
                    </tr>
                    <tr>
                        <td class="pt"><small class="c-red">*</small>请假时间</td>
                        <td colspan="3" width="50%">
                            <span>
                                <font style = "width:30%;height:42px;text-align: center;"><fmt:formatDate value="${oaLeave.startTime}" pattern="yyyy年MM月dd日"></fmt:formatDate></font>
                                <font style = "width:30%;height:42px;text-align: center;">到 <fmt:formatDate value="${oaLeave.endTime}" pattern="yyyy年MM月dd日"></fmt:formatDate></font>
                                <font style = "width:30%;height:42px;text-align: center;">共${oaLeave.leaveDays}天</font>
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <td class="pt"><small class="c-red">*</small>请假事由</td>
                        <td colspan="3"><span>${oaLeave.reason}</span></td>
                    </tr>
                    <tr>
                    <tr>
                        <td class="pt" rowspan="2">部门负责人审批意见</td>
                        <td class="pt" rowspan="2" style="border-right: none;color:#666;"><textarea cols="30" rows="3" id = "departmentOption" disabled>${oaLeave.departmentOption}</textarea></td>
                        <td colspan="2" style="border: none;border-right: 1px solid #ddd; "></td>
                    </tr>
                    <tr>
                        <td class="pt" colspan="" style="border:none;border-bottom: 1px solid #ddd; color:#666;">签字：</td>
                        <div id="img-box">
                            <div id="pos0"  style="position:absolute;top: 350px;left: 64%;"bgcolor=#ffffff></div>
                        </div>
                        <td class="pt" colspan="1" style="color:#666;border-left:none;border-top:none"><span><fmt:formatDate value="${oaLeave.departAuditTime}" pattern="yyyy年MM月dd日"></fmt:formatDate></span></td>
                    </tr>
                    <tr>
                        <td class="pt" rowspan="2"><small class="c-red">*</small>分管校（院）领导审批意见</td>
                        <td class="pt" rowspan="2" style="border-right: none;color:#666;"><textarea cols="30" rows="3" name="oaLeave.schoolOption" id = "schoolOption">${oaLeave.schoolOption}</textarea></td>
                        <td colspan="2" style="border: none;border-right: 1px solid #ddd; "></td>
                    </tr>
                    <tr>
                        <td class="pt" colspan="" style="border:none;border-bottom: 1px solid #ddd; color:#666;">签字：</td>
                        <div id="pos1"  style="position:absolute;top: 450px;left: 64%;"bgcolor=#ffffff></div>
                        <td class="pt" colspan="1" style="color:#666;border-left:none;border-top:none"><span>年 月 日</span></td>
                    </tr>
                    <tr>
                        <td class="pt" rowspan="2">常务副校长/副校长（主持工作）审批意见</td>
                        <td class="pt" rowspan="2" style="border-right: none; color:#666;"><textarea cols="30" rows="3" id = "schoolLeaderOption" disabled>${oaLeave.schoolLeaderOption}</textarea></td>
                        <td colspan="2" style="border: none;border-right: 1px solid #ddd; "></td>
                    </tr>
                    <tr>
                        <td class="pt" colspan="" style="border:none;border-bottom: 1px solid #ddd; color:#666;">签字：</td>
                        <div id="pos2"  style="position:absolute;top: 550px;left: 64%;"bgcolor=#ffffff></div>
                        <td class="pt" colspan="1" style="color:#666;border-left:none;border-top:none"><span>年 月 日</span></td>
                    </tr>
                    <tr>
                        <td class="pt" rowspan="3">请假备案情况</td>
                        <td class="pt" rowspan="3" style="border-right: none; color:#666;"><textarea cols="30" rows="3" id = "leaveRecordSituation" disabled>${oaLeave.leaveRecordSituation}</textarea></td>
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
                <a class="submit radius2" style="cursor: pointer" onclick="addFormSubmit(0);return false;">同意</a>
                <a class="submit radius2" style="cursor: pointer" onclick="addFormSubmit(2);return false;">驳回</a>
                <a class="submit radius2" style="cursor: pointer" onclick="history.go(-1);return false;">返回</a>
            </div>
            </div>
        </form>
        <jsp:include page="/WEB-INF/view/common/process_transfer.jsp"></jsp:include>
    </div>


</div>
<script type = "text/javascript" src="${ctx}/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${ctx}/kinggrid/core/kinggrid.min.js"></script>
<script type="text/javascript" src="${ctx}/kinggrid/core/kinggrid.plus.min.js"></script>
<script type="text/javascript" src="${ctx}/kinggrid/dialog/artDialog/dialog-min.js"></script>
<script type="text/javascript" src="${ctx}/kinggrid/signature.min.js"></script><!-- html签章核心JS -->
<script type="text/javascript" src="${ctx}/kinggrid/signature.pc.min.js"></script><!-- PC端附加功能 -->
<script type="text/javascript" src="${ctx}/kinggrid/password.min.js"></script>
<script type="text/javascript" src="${ctx}/kinggrid/signature_pad.min.js"></script>
<script type="text/javascript" src="${ctx}/kinggrid/jquery.timer.dev.js"></script>
<script type="text/javascript" src="${ctx}/kinggrid/jquery.qrcode.min.js"></script>
<script type="text/javascript" src="${ctx}/kinggrid/qrcode.min.js"></script>
<script type="text/javascript" src="${ctx}/kinggrid/jsQR.js"></script>
<script type="text/javascript" src = "${ctx}/static/js/signature/signature.js"></script>
<script type = "text/javascript">
    var id = '${oaLeave.id}';
    var type = 2;
    var departAuditTime='${oaLeave.departAuditTime}';
    function addFormSubmit(flag) {
        if (!jQuery("#schoolOption").val()) {
            alert("分管校领导意见不能为空");
            return;
        }
        var list = Signature.list;
        var i = 0;
        for ( var key in list) {
            ++ i;
            saveSignature(key, list[key].getSignatureData());
        }
        if(departAuditTime==null || departAuditTime==""){
            if (i < 1) {
                alert("审批必须签字");
                return;
            }
        }else {
            if (i < 2) {
                alert("审批必须签字");
                return;
            }
        }
        jQuery("#audit").val(flag);
        jQuery("#comment").val(jQuery("#schoolOption").val());
        var data = jQuery("#submitForm").serialize();
        jQuery.ajax({
            url: "${ctx}/admin/oa/leaveApplyAudit.json",
            data: data,
            type: "post",
            dataType: "json",
            async: false,
            cache : false,
            success: function (result) {
                if (result.code == "0") {
                    alert(result.message);
                    window.location.href = "${ctx}/admin/oa/task/to/claim/list.json";
                } else {
                    alert(result.message);
                }
            }
        });
    }
</script>
</body>
</html>