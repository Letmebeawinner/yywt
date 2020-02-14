<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>公文申请详情(非管理员)</title>
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
    <script type="text/javascript" src = "${ctx}/static/js/official/officialTwo.js"></script>
    <script>
        var id = "${oaLetter.id}";
        var type = 1;
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="background: #fff;">
        <h1 class="page-list " style="text-align: center;">
            公文申请详情(非管理员)
        </h1>
    </div>
    <div id="print">
        <button type = "button" onclick = "printDocument()" style = "margin-left: 21px;cursor: pointer" class = "printHide">打印</button>
    </div>
    <div id="contentwrapper" class="contentwrapper user-car">
        <div id="basicform" class="subcontent">
            <div class="testtle-tables">
                <form id = "addLetter" method="post" class="stdform stdform2">
                    <table border="1" class="tables">
                        <tr>
                            <td class="pt text_controller_center" style="width:15%;">发文号</td>
                            <td style="width:35%;"><span>${oaLetter.letterNo}</span></td>
                            <td class="pt" style="width:5%;">密级</td>
                            <td style="width:45%;"><span>${oaLetter.secretLevel}</span></td>
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
                                <td class="pt"><small class="c-red"></small>附件</td>
                                <td colspan="3">
                                <span>
                                     <a href="${ctx}/admin/oa/file/load.json?fileUrl=${oaLetter.fileUrl}&fileName=${oaLetter.fileName}" title="下载附件">${oaLetter.fileName}</a>
                                </span>
                                </td>
                            </tr>
                        </c:if>
                        <tr class = "printHide">
                            <td class="pt">审核状态</td>
                            <td colspan="3">
                                    <span>
                                        <c:if test = "${oaLetter.audit == 1}">
                                            <font color="green">审批已通过</font>
                                        </c:if>
                                        <c:if test = "${oaLetter.audit == 0}">
                                            审核中
                                        </c:if>
                                        <c:if test = "${oaLetter.audit == 2}">
                                            已拒绝
                                        </c:if>
                                    </span>
                            </td>
                        </tr>
                    </table>
                    <input type = "hidden" name = "taskId" value = "${task.id}" id = "taskId">
                    <input type = "hidden" name = "oaLetter.processInstanceId" value = "${oaLetter.processInstanceId}" id = "processInstanceId">
                    <input type = "hidden" name = "oaLetter.audit" id = "audit">
                    <input type = "hidden" name = "comment" id = "comment">
                    <input type = "hidden" name = "sysUserIds" id = "sysUserIds" value="">
                </form>
            </div>
            <div class="buttons printHide" style="text-align: center;margin-top:20px;margin-bottom: 20px">
                <a class="submit radius2" onclick = "history.go(-1)" style="cursor: pointer">返回</a>
                <a class="submit radius2" onclick = "selectSysUser(null)" style="cursor: pointer">发布</a>
            </div>
            <br/>
            <jsp:include page="/WEB-INF/view/common/process_transfer.jsp"></jsp:include>
        </div>
    </div>
</div>
<script type = "text/javascript">
    function printDocument() {
        jQuery("#print").hide();
        jQuery(".printHide").hide();
        jQuery(".header").hide();
        jQuery(".iconmenu").hide();
        jQuery(".centercontent").css("marginLeft","0");
        jQuery("textarea").css("min-height","85px");
//        jQuery("#pos0").css("marginLeft","-181px");
        jQuery(".kg-img-div").css({
            "left": "calc(64% - 0px)",
            transform: "translateY(-50%)"
        });
        //jQuery("td").css("border","1px solid #999");
        // return;
        window.print();
        jQuery("#print").show();
        jQuery(".printHide").show();
        jQuery(".header").show();
        jQuery(".iconmenu").show();
        jQuery(".centercontent").css("marginLeft","181px");
        //jQuery("#pos0").css("left","64%");
        //jQuery("td").css("border","1px solid #ddd");
        jQuery("textarea").css("height", "100%");
    }
</script>

</body>
</html>