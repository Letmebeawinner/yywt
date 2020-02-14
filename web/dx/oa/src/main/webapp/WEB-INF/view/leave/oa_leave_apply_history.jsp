<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>校（院）教职工请销假审批和备案表</title>
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
        <h1 class="page-list " style="text-align: center;">
            校（院）教职工请销假审批和备案表
        </h1>
    </div><!--pageheader-->
    <div id="print">
        <button type = "button" onclick = "printDocument()" style = "margin-left: 21px;cursor: pointer" class = "printHide">打印</button>
    </div>
    <div id="contentwrapper" class="contentwrapper users-car">
        <form method="post" action="" id="submitForm">
            <div class="testtle-tables">
                <table border="1">
                    <tr>
                        <td class="pt">姓名</td>
                        <td><span>${applyName}</span></td>
                        <td class="pt">参加工作时间</td>
                        <td><span>${oaLeave.workTime}</span></td>
                    </tr>

                    <tr>
                        <td class="pt">所在部门</td>
                        <td><span>${departmentName}</span></td>
                        <td class="pt">现任职务</td>
                        <td><span>${oaLeave.position}</span></td>
                    </tr>
                    <tr>
                        <td class="pt">请假种类</td>
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
                        <td class="pt">请假时间</td>
                        <td colspan="3" width="50%">
                            <span>
                                <font style = "width:30%;height:42px;text-align: center;"><fmt:formatDate value="${oaLeave.startTime}" pattern="yyyy年MM月dd日 HH:mm"></fmt:formatDate></font>
                                <font style = "width:30%;height:42px;text-align: center;">到 <fmt:formatDate value="${oaLeave.endTime}" pattern="yyyy年MM月dd日 HH:mm"></fmt:formatDate></font>
                                <font style = "width:30%;height:42px;text-align: center;">共${oaLeave.leaveDays}天</font>
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <td class="pt">请假事由</td>
                        <td colspan="3"><span>${oaLeave.reason}</span></td>
                    </tr>
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
                        <td class="pt" rowspan="2">分管校（院）领导审批意见</td>
                        <td class="pt" rowspan="2" style="border-right: none;color:#666;"><textarea cols="30" rows="3" id = "schoolOption" disabled>${oaLeave.schoolOption}</textarea></td>
                        <td class="pt" colspan="2" style="border: none;border-right: 1px solid #ddd; "></td>
                    </tr>
                    <tr>
                        <td class="pt" colspan="" style="border:none;border-bottom: 1px solid #ddd; color:#666;">签字：</td>
                        <div id="pos1"  style="position:absolute;top: 450px;left: 64%;"bgcolor=#ffffff></div>
                        <td class="pt" colspan="1" style="color:#666;border-left:none;border-top:none"><span><fmt:formatDate value="${oaLeave.schoolAuditTime}" pattern="yyyy年MM月dd日"></fmt:formatDate></span></td>
                    </tr>
                    <tr>
                        <td class="pt" rowspan="2">常务副校长/副校长（主持工作）审批意见</td>
                        <td class="pt" rowspan="2" style="border-right: none; color:#666;"><textarea cols="30" rows="3" id = "schoolLeaderOption" disabled>${oaLeave.schoolLeaderOption}</textarea></td>
                        <td colspan="2" style="border: none;border-right: 1px solid #ddd; "></td>
                    </tr>
                    <tr>
                        <td class="pt" colspan="" style="border:none;border-bottom: 1px solid #ddd; color:#666;">签字：</td>
                        <div id="pos2"  style="position:absolute;top: 550px;left: 64%;"bgcolor=#ffffff></div>
                        <td class="pt" colspan="1" style="color:#666;border-left:none;border-top:none"><span><fmt:formatDate value="${oaLeave.schoolLeaderAuditTime}" pattern="yyyy年MM月dd日"></fmt:formatDate></span></td>
                    </tr>
                    <tr>
                        <td class="pt" rowspan="2">请假备案情况</td>
                        <td class="pt" rowspan="2" style="border-right: none; color:#666;"><textarea cols="30" rows="3" id = "leaveRecordSituation" disabled>${oaLeave.leaveRecordSituation}</textarea></td>
                        <%--<td colspan="2" style="border: none;border-right: 1px solid #ddd; "></td>--%>
                        <td class="pt" colspan="2" style="border:none; color:#666;border-right: 1px solid #ddd;">
                            <p class="testtle-txt">请假人已于<fmt:formatDate value="${oaLeave.leaveRecordTime}" pattern="yyyy年MM月dd日"></fmt:formatDate>进行请假备案</p>
                        </td>
                    </tr>
                    <%--<tr>
                        <td class="pt" colspan="2" style="border:none; color:#666;border-right: 1px solid #ddd;">
                            <p class="testtle-txt">请假人已于<fmt:formatDate value="${oaLeave.leaveRecordTime}" pattern="yyyy年MM月dd日"></fmt:formatDate>进行请假备案</p>
                        </td>
                    </tr>--%>
                    <tr>
                        <td class="pt" colspan="2" style="border:none; color:#666;border-right: 1px solid #ddd;border-bottom: 1px solid #ddd;" >
                            <p class="testtle-txt">组织人事处备案人（签字）：${oaLeave.leaveRecordSign}</p>
                        </td>
                    </tr>
                    <tr>
                        <td class="pt" rowspan="3">销假备案情况</td>
                        <td class="pt" rowspan="3" style="border-right: none; color:#666;"><textarea disabled  cols="30" rows="3" id = "cancelLeaveRecordSituation" disabled>${oaLeave.cancelLeaveRecordSituation}</textarea></td>
                        <td class="pt" colspan="2" style="border:none; color:#666;border-right: 1px solid #ddd;">
                            <p class="testtle-txt">请假人已于<fmt:formatDate value="${oaLeave.cancelLeaveRecordTime}" pattern="yyyy年MM月dd日"></fmt:formatDate>进行销假备案</p>
                        </td>
                    </tr>
                    <%--<tr>
                        <td class="pt" colspan="2" style="border:none; color:#666;border-right: 1px solid #ddd;">
                            <p class="testtle-txt">请假人已于<fmt:formatDate value="${oaLeave.cancelLeaveRecordTime}" pattern="yyyy年MM月dd日"></fmt:formatDate>进行销假备案</p>
                        </td>
                    </tr>--%>
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

                    <tr class = "printHide">
                        <td class="pt">审核状态</td>
                        <td colspan="3">
                                <span>
                                    <c:if test = "${oaLeave.audit == 1}">
                                        审批已通过
                                    </c:if>
                                    <c:if test = "${oaLeave.audit == 0}">
                                        审核中
                                    </c:if>
                                    <c:if test = "${oaLeave.audit == 2}">
                                        已拒绝
                                    </c:if>
                                </span>
                        </td>
                    </tr>

                </table>
                <div class="pt10">注：请假2天以内（含 2天）的，部门副职或其他教职工由部门正职（或主持工作的副职）批准，部门正职（或主持工作的副职）由分管校（院）领导批准；请假2天以上的，部门副职或其他教职工经部门正职（或主持工作的副职）同意，报分管校（院）领导批准，部门正职（或主持工作的副职）经分管校（院）领导同意，报常务副校（院）长批准。</div>
                <div class="buttons printHide" style="text-align: center;margin-top:30px;margin-bottom: 20px">
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

    function addFormSubmit(flag) {
        if (!jQuery("#schoolOption").val()) {
            alert("分管校领导意见不能为空");
            return;
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

    function printDocument() {
        jQuery("#print").hide();
        jQuery(".printHide").hide();
        jQuery(".header").hide();
        jQuery(".iconmenu").hide();
        jQuery(".centercontent").css("marginLeft","0");
        jQuery("textarea").css("min-height","80px");
        jQuery(".pt textarea").css({
            "min-height": "70px",
            "height": "70px"
        })
        jQuery("td input[type=\"text\"][readonly]").css("height","90%");
        jQuery('body').css({
            width: '210mm',
            height: '297mm',
            margin: 'auto'
        }).removeClass('withvernav')

        // jQuery("#pos0").css("marginLeft","-181px");
        jQuery(".kg-img-div").css({
            // "left": "calc(64% - 0px)",
            // transform: "translateY(-50%)"
            // transform: 'translateX(-50%)'
        });
        var kgImgDivs = jQuery('.kg-img-div[elemid*="pos"]')
        kgImgDivs.each(function(index, item) {
            var top = parseFloat($('#' + $(item).attr('elemid'))[0].offsetTop)
            var left = parseFloat($('#' + $(item).attr('elemid'))[0].offsetLeft)
            $(this).css({
                top: top + 10,
                left: left
            }).find('.kg-img').css({
                width: '150px',
                height: '74px'
            })
        })

        // return;
        window.print();
        jQuery('body').css({
            width: '',
            height: ''
        }).addClass('withvernav')
        jQuery("#print").show();
        jQuery(".printHide").show();
        jQuery(".header").show();
        jQuery(".iconmenu").show();
        jQuery(".centercontent").css("marginLeft","181px");
        // jQuery(".pt textarea").css('height', '65px')
        //jQuery("#pos0").css("left","64%");
        //jQuery("td").css("border","1px solid #ddd");
        jQuery("textarea").css("height", "100%");

    }



</script>
</body>
</html>