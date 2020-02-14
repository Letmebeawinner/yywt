<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>公文发送</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

    <link rel="stylesheet" href="${ctx}/kinggrid/dialog/artDialog/ui-dialog.css">
    <link rel="stylesheet" href="${ctx}/kinggrid/core/kinggrid.plus.css">

    <style type="text/css">
        @media print{
            #in{ display:none}
        }

        table{margin: 0 auto;}
    </style>

    <script type = "text/javascript" src="${ctx}/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="/pageoffice.js" id="po_js_main"></script>
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
    <script type="text/javascript" src = "${ctx}/static/js/official/official.js"></script>

    <script type="text/javascript">
        function addFormSubmit(flag) {
            var res=addApproval(1);
            if(res==true){
                jQuery("#audit").val(flag);
                jQuery("#comment").val("发布");
                var data = jQuery("#addLetter").serialize();
                jQuery.ajax({
                    url: "${ctx}/admin/oa/inner/letterApplyAudit.json",
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


        }

        function resetData(){
            jQuery(".longinput").val("");
        }
        jQuery(function(){
            jQuery("#pobmodal-dialog").hide();
            laydate.skin('molv');
            laydate({
                elem: '#effectiveTime',
                format:'YYYY-MM-DD hh:mm:ss',
                istoday: false,
                istime: true,
                choose: function() {
                    calcLeaveDays();
                }
            });

        })
        function openPageOffice(timeStamp) {
            var url = "${ctx}/open/internalOfficialDocument.json?timeStamp=" + timeStamp
            window.location.href = "javascript:POBrowser.openWindow('" + url + "','width=1200px;height=800px;')";
        }

        jQuery(function(){
            jQuery("#pobmodal-dialog").hide()
        })
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">公文发送</h1>
    </div>
    <div id="contentwrapper" class="contentwrapper user-car">
        <div id="basicform" class="subcontent">
            <div class="testtle-tables">
                <form id = "addLetter" method="post" class="stdform stdform2">
                    <table border="1">
                        <tr>
                        <tr>
                            <td class="pt text_controller_center" style="width:12%;">发文号</td>
                            <td colspan="1" style="width:38%;"><input type = "text" name = "oaLetter.letterNo" value="${oaLetter.letterNo}" id = "letterNo"></td>
                            <td class="pt" style="width:5%;">密级</td>
                            <td wstyle="width:45%;"><input type = "text" name = "oaLetter.secretLevel" value="${oaLetter.secretLevel}" id = "secretLevel"></td>
                        </tr>
                        </tr>
                        <tr>
                            <td class="pt" colspan="4" style="text-align: left;padding-left: 40px; color:#666;"><tt>文件标题：</tt><textarea  id="reason" style="width:88%;" cols="20" rows="5">${oaLetter.reason}</textarea></td>
                        </tr>
                        <tr>
                            <td colspan="2" class="text_controller_center">主办单位：<span>${departmentName}</span></td>
                            <td rowspan="2" colspan="2"  class="text_controller_center">时&nbsp;&nbsp;&nbsp;&nbsp;间：<span><fmt:formatDate value = "${oaLetter.startTime}" pattern="yyyy 年 MM 月dd 日 HH:mm"></fmt:formatDate></span></td>
                        </tr>
                        <tr>
                            <td colspan="2" class="text_controller_center">拟&nbsp;&nbsp;稿&nbsp;&nbsp;人：<span>${applyName}</span></td>
                        </tr>
                        <tr>
                            <div id="pos0"  style="position:absolute;    top: 256px;left: 129px;"  bgcolor=#ffffff></div>
                            <td colspan="2" class="text_controller_center">核&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;稿：<span>${oaLetter.confirmOption}</span></td>
                            <div id="pos1"  style="position:absolute;    top: 208px;left: 964px;"  bgcolor=#ffffff></div>
                            <td colspan="2" class="text_controller_center">会&nbsp;&nbsp;&nbsp;&nbsp;签：<span>${oaLetter.countersignOption}</span></td>
                        </tr>
                        <tr>
                            <div id="pos2"  style="position:absolute;    top: 282px;left: 143px;"  bgcolor=#ffffff></div>
                            <td class="pt" colspan="4" style="text-align: left;padding-left: 40px; color:#666;">签发：</tt><span>${oaLetter.confirmToSendOption}</span></td>
                        </tr>
                        <tr>
                            <td class="pt" colspan="4" style="text-align: left;padding-left: 40px; color:#666;"><tt>发送单位：</tt><textarea  id="sendToUnit" style="width:88%;" cols="10" rows="5" readonly>${oaLetter.sendToUnit}</textarea></td>
                        </tr>
                        <tr>
                            <td class="pt" colspan="4" style="text-align: left;padding-left: 40px; color:#666;"><tt>内容摘要：</tt><textarea  id="context" style="width:88%;" cols="10" rows="5" readonly>${oaLetter.context}</textarea></td>
                        </tr>
                        <c:if test = "${not empty oaLetter.fileUrl}">
                            <tr>
                                <td class="pt">公文内容(在线Word)</td>
                                <td colspan="3">
                                <span class="field">
                                        <a href="javascript:void(0)" onclick="openPageOffice('${oaLetter.timeStamp}')" class="ZL-generate">查看公文内容</a>
                               </span>
                                </td>
                            </tr>
                        </c:if>
                        <tr>
                            <td class="pt">审核状态</td>
                            <td colspan="3" align="left">
                                <span>
                                    <font color="green">审批已通过</font>
                                </span>
                            </td>
                        </tr>
                        <tr>
                            <td class="pt">发送人</td>
                            <td colspan="3">
                                <span id="userName"></span>
                            </td>
                        </tr>
                        <tr>
                            <td class="pt">时效截止</td>
                            <td colspan="3">
                                <span>
                                    <input type="text" style="width:90%;" id = "effectiveTime" readonly>
                                </span>
                            </td>
                        </tr>
                    </table>
                    <input type = "hidden" name = "taskId" value = "${task.id}" id = "taskId">
                    <input type = "hidden" name = "oaLetter.processInstanceId" value = "${oaLetter.processInstanceId}" id = "processInstanceId">
                    <input type = "hidden" name = "oaLetter.audit" id = "audit">
                    <input type = "hidden" name = "comment" id = "comment">
                    <input type = "hidden" name = "sysUserIds" id = "sysUserIds" value="">
                    <input type = "hidden" id = "PrimaryKey" value="${oaLetter.id}">
                    <input type = "hidden" id = "timeStamp" value="${oaLetter.timeStamp}">
                </form>
            </div>
            <div class="buttons" style="text-align: center;margin-top:20px;margin-bottom: 20px">
                <a class="submit radius2" onclick = "addFormSubmit(1)" style="cursor: pointer">提交</a>
                <a class="submit radius2" onclick = "history.go(-1)" style="cursor: pointer">返回</a>
                <a class="submit radius2" onclick = "selectSysUser(null)" style="cursor: pointer">选择发送领导</a>
            </div>
            <br/>
            <jsp:include page="/WEB-INF/view/common/process_transfer.jsp"></jsp:include>
        </div>
    </div>
</div>


</body>
</html>