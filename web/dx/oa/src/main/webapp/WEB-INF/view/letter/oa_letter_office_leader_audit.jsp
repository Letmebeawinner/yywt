<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>办公室主任审批</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

    <link rel="stylesheet" href="${ctx}/kinggrid/dialog/artDialog/ui-dialog.css">
    <link rel="stylesheet" href="${ctx}/kinggrid/core/kinggrid.plus.css">

    <style type="text/css">
        @media print {
            #in {
                display: none
            }
        }

        table {
            margin: 0 auto;
        }
        /*#approval-table{margin-top: -61px;}*/
    </style>

    <script type="text/javascript" src="${ctx}/jquery-1.8.3.min.js"></script>
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
    <script type="text/javascript" src="${ctx}/static/js/signature/signature.js"></script>
    <script type="text/javascript">

        var id = '${oaLetter.id}';
        var type = 1;
        initSignature(id, "办公室主任审批");

        function addFormSubmit(flag) {
            var list = Signature.list;
            var i = 0;
            for (var key in list) {
                ++i;
                saveSignature(key, list[key].getSignatureData());
            }
            if (i < 4) {
                alert("核稿必须签字");
                return;
            }
            var confirmOption = jQuery("#confirmOption").val();
//            if (!confirmOption) {
//                alert("核稿信息必填");
//                return;
//            }

            jQuery("#audit").val(flag);
            jQuery("#comment").val(confirmOption);
            var data = jQuery("#addLetter").serialize();
            jQuery.ajax({
                url: "${ctx}/admin/oa/letterApplyAudit.json",
                data: data,
                type: "post",
                dataType: "json",
                async: false,
                cache: false,
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

        function resetData() {
            jQuery(".longinput").val("");
        }

        function openPageOffice(processDefinitionId) {
            var url = "${ctx}/open/oaWord.json?processDefinitionId=" + processDefinitionId
            window.location.href = "javascript:POBrowser.openWindow('" + url + "','width=1200px;height=800px;')";

        }

        jQuery(function () {
            jQuery("#pobmodal-dialog").hide()
        })


    </script>

</head>
<body id="">
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle printHide">办公室主任审批</h1>
    </div>
    <div>
        <input type="button" class="test" style="margin: 0 0 20px 21px;cursor: pointer" value="电子签章" onclick="clickSignature(this, 'pos3')">
    </div>
    <div id="contentwrapper" class="contentwrapper user-car">
        <div id="basicform" class="subcontent">
            <div class="testtle-tables">
                <form id = "addLetter" method="post" class="stdform stdform2">
                    <table border="1" id="approval-table">
                        <tr>
                            <td class="pt text_controller_center" style="width:12%;">发文号</td>
                            <td style="width:38%;"><input type="text" name="oaLetter.letterNo" value="${oaLetter.letterNo}"></td>
                            <td class="pt" style="width:5%;">密级</td>
                            <td wstyle="width:45%;"><input type="text" name="oaLetter.secretLevel" value="${oaLetter.secretLevel}"></td>
                        </tr>
                        <tr>
                            <td class="pt" colspan="4" style="text-align: left;padding-left: 40px; color:#666;">
                                <tt>标题：</tt><textarea id="reason" style="width:88%;" cols="20"
                                                      rows="5">${oaLetter.reason}</textarea></td>
                        </tr>
                        <tr>
                            <td colspan="2" class="text_controller_center">主办单位：<span>${departmentName}</span></td>
                            <td rowspan="2" colspan="2" class="text_controller_center">时&nbsp;&nbsp;&nbsp;&nbsp;间：<span><fmt:formatDate
                                    value="${oaLetter.startTime}" pattern="yyyy 年 MM 月dd 日 HH:mm"></fmt:formatDate></span></td>
                        </tr>
                        <tr>
                            <td colspan="2" class="text_controller_center">
                                拟&nbsp;&nbsp;稿&nbsp;&nbsp;人：<span>${applyName}</span></td>
                        </tr>
                        <%--<tr>
                            <div id="pos0" style="position:absolute;    top: 208px;left: 129px;" bgcolor=#ffffff></div>
                            <td colspan="2" class="text_controller_center">
                                <small class="c-red">*</small>
                                核&nbsp;&nbsp;&nbsp;&nbsp;稿：<input style="width:85%;height:110px;"
                                                                                    type="text"
                                                                                    name="oaLetter.confirmOption"
                                                                                    id="confirmOption"/></td>
                            <td colspan="2" class="text_controller_center">会&nbsp;&nbsp;&nbsp;&nbsp;签：</td>
                        </tr>--%>
                        <tr>
                            <td class="pt text_controller_center" colspan="2">部门领导核稿：
                                <div id="pos0" style="position:absolute;    top: 21%;left: 15%;" bgcolor=#ffffff></div>
                                <textarea style="width:88%;" cols="20" rows="5" readonly></textarea>
                            </td>
                            <td class="pt text_controller_center" colspan="2">分管校领导意见：
                                <div id="pos1" style="position:absolute;    top: 21%;left: 65%;" bgcolor=#ffffff></div>
                                <textarea  style="width:88%;" cols="20" rows="5" readonly></textarea>
                            </td>
                        </tr>
                        <tr>
                            <td class="pt text_controller_center" colspan="2">会&nbsp;&nbsp;&nbsp;&nbsp;签：
                                <div id="pos2" style="position:absolute;    top: 32%;left: 15%;" bgcolor=#ffffff></div>
                                <textarea  style="width:95%;" cols="20" rows="5" readonly></textarea>
                            </td>
                            <td class="pt text_controller_center" colspan="2">办公室主任：
                                <div id="pos3" style="position:absolute;    top: 32%;left: 65%;" bgcolor=#ffffff></div>
                                <textarea  style="width:88%;" cols="20" rows="5" readonly></textarea>
                            </td>
                        </tr>
                        <tr>
                            <td class="pt" colspan="4" style="text-align: left;padding-left: 40px; color:#666;">
                                <tt>签&nbsp;&nbsp;&nbsp;&nbsp;发：</tt>
                                <div id="pos4" style="position:absolute;    top: 43%;left: 15%;" bgcolor=#ffffff></div>
                                <textarea style="width:88%;" cols="20" rows="5" readonly></textarea>
                            </td>
                        </tr>
                        <tr>
                            <td class="pt" colspan="4" style="text-align: left;padding-left: 40px; color:#666;"><tt>发送单位：</tt><textarea
                                    id="sendToUnit" style="width:88%;" cols="10" rows="5"
                                    readonly>${oaLetter.sendToUnit}</textarea></td>
                        </tr>
                        <tr>
                            <td class="pt" colspan="4" style="text-align: left;padding-left: 40px; color:#666;"><tt>内容摘要：</tt><textarea
                                    id="context" style="width:88%;" cols="10" rows="5"
                                    readonly>${oaLetter.context}</textarea></td>
                        </tr>
                        <tr>
                            <td class="pt" colspan="4" style="text-align: left;padding-left: 40px; color:#666;"><tt>
                                <%--<small class="c-red" style="display: inline-block; vertical-align: top">*</small>--%>
                                公文内容(在线Word)：</tt><span>
                                 <div class="text-center m-t-sm">
                                         <a href="javascript:void(0)"
                                            onclick="openPageOffice('${oaLetter.timeStamp}')"
                                            class="ZL-generate">编辑公文内容</a>
                                  </div>
                              </span></td>
                        </tr>
                        <tr class="printHide">
                            <td class="pt">审核状态</td>
                            <td colspan="3">
                                    <span>
                                        <c:if test="${oaLetter.audit == 1}">
                                            <font color="green">审批已通过</font>
                                        </c:if>
                                        <c:if test="${oaLetter.audit == 0}">
                                            审核中
                                        </c:if>
                                        <c:if test="${oaLetter.audit == 2}">
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
                </form>
            </div>
            <div class="buttons" style="text-align: center;margin-top:20px;margin-bottom: 20px">
                <a class="submit radius2" onclick = "addFormSubmit(0)" style="cursor: pointer">同意</a>
                <a class="submit radius2" onclick = "addFormSubmit(2)" style="cursor: pointer">驳回</a>
                <a class="submit radius2" onclick = "history.go(-1)" style="cursor: pointer">返回</a>
            </div>
            <br/>
            <jsp:include page="/WEB-INF/view/common/process_transfer.jsp"></jsp:include>
        </div>
    </div>
</div>
</body>
</html>