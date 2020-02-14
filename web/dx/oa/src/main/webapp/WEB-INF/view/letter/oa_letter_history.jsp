<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>公文申请详情</title>
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
    <script type="text/javascript" src = "${ctx}/static/js/signature/signature.js"></script>
    <script type="text/javascript" src = "${ctx}/static/js/official/official.js"></script>
    <script>
        var id = "${oaLetter.id}";
        var type = 1;

        function openPageOffice(processDefinitionId) {
            var url = "${ctx}/open/oaHistoryWord.json?processDefinitionId=" + processDefinitionId + "&type=1"
            window.location.href = "javascript:POBrowser.openWindow('" + url + "','width=1200px;height=800px;')";

        }
        function openPageOfficeOa(timeStamp) {
            var url = "${ctx}/open/internalOfficialDocument.json?timeStamp=" + timeStamp  + "&type=2"
            window.location.href = "javascript:POBrowser.openWindow('" + url + "','width=1200px;height=800px;')";

        }

        jQuery(function(){
            jQuery("#pobmodal-dialog").hide()
        })
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="background: #fff;">
        <h1 class="page-list " style="text-align: center;">
            公文申请详情
        </h1>
    </div>
    <div id="print">
        <button type = "button" onclick = "printDocument()" style = "margin-left: 21px;cursor: pointer" class = "printHide">打印</button>
    </div>
    <div id="contentwrapper" class="contentwrapper user-car" style="overflow-x:hidden;">
        <div id="basicform" class="subcontent">
            <div class="testtle-tables">
                <form id = "addLetter" method="post" class="stdform stdform2">
                    <c:if test="${oaLetter.type==2}">
                        <table border="1" class="tables">
                            <tr>
                                <td class="pt text_controller_center" style="width:15%;">发文号</td>
                                <td style="width:35%;"><span>${oaLetter.letterNo}</span></td>
                                <td class="pt" style="width:5%;">密级</td>
                                <td style="width:45%;"><span>${oaLetter.secretLevel}</span></td>
                            </tr>
                            <tr>
                                <td class="pt" colspan="4" style="text-align: left;padding-left: 40px; color:#666;"><tt>文件标题：</tt><textarea  id="reason" style="width:88%;" cols="20" rows="5" disabled>${oaLetter.reason}</textarea></td>
                            </tr>
                            <tr>
                                <td colspan="2" class="text_controller_center">主办单位：<span>${departmentName}</span></td>
                                <td rowspan="2" colspan="2"  class="text_controller_center">时&nbsp;&nbsp;&nbsp;&nbsp;间：<span><fmt:formatDate value = "${oaLetter.startTime}" pattern="yyyy 年 MM 月dd 日 HH:mm"></fmt:formatDate></span></td>
                            </tr>
                            <tr>
                                <td colspan="2" class="text_controller_center">拟&nbsp;&nbsp;稿&nbsp;&nbsp;人：<span>${applyName}</span></td>
                            </tr>
                            <tr>
                                <div id="pos0"  style="position:absolute;    top: 208px;left: 129px;"  bgcolor=#ffffff></div>
                                <td colspan="2" class="text_controller_center">核&nbsp;&nbsp;&nbsp;&nbsp;稿：<span>${oaLetter.confirmOption}</span><input disabled style="width:85%;height:110px;"/></td>
                                <div id="pos1"  style="position:absolute;    top: 208px;left: 55%;"  bgcolor=#ffffff></div>
                                <div id="pos2"  style="position:absolute;    top: 208px;left: 75%;"  bgcolor=#ffffff></div>
                                <td colspan="2" class="text_controller_center">会&nbsp;&nbsp;&nbsp;&nbsp;签：<span>${oaLetter.countersignOption}</span></td>
                            </tr>
                            <tr>
                                <div id="pos3"  style="position:absolute;    top: 365px;left: 15%;"  bgcolor=#ffffff></div>
                                <div id="pos4"  style="position:absolute;    top: 365px;left: 35%;"  bgcolor=#ffffff></div>
                                <td class="pt text_controller_center" colspan="4" style="text-align: left;padding-left: 40px; ">签&nbsp;&nbsp;&nbsp;&nbsp;发：<span>${oaLetter.confirmToSendOption}</span><input disabled style="width:85%;height:110px;"/></td>
                            </tr>
                            <tr>
                                <td class="pt" colspan="4" style="text-align: left;padding-left: 40px; color:#666;"><tt>发送单位：</tt><textarea  id="sendToUnit" style="width:88%;" cols="10" rows="5" readonly>${oaLetter.sendToUnit}</textarea></td>
                            </tr>
                            <tr>
                                <td class="pt" colspan="4" style="text-align: left;padding-left: 40px; color:#666;"><tt>内容摘要：</tt><textarea  id="context" style="width:88%;" cols="10" rows="5" readonly>${oaLetter.context}</textarea></td>
                            </tr>
                            <c:if test = "${not empty oaLetter.fileUrl}">
                                <tr>
                                    <td class="pt"><small class="c-red"></small>公文内容(在线Word)</td>
                                    <td colspan="3">
                                            <%--<span class="field">--%>
                                        <a href="javascript:void(0)" onclick="openPageOfficeOa('${oaLetter.timeStamp}')" class="ZL-generate">查看公文内容</a>
                                            <%--</span>--%>
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
                    </c:if>
                    <c:if test="${oaLetter.type!=2}">
                        <table border="1" id="approval-table">
                            <tr>
                                <td class="pt text_controller_center" style="width:12%;">发文号</td>
                                <td style="width:38%;"><input type="text" readonly name="oaLetter.letterNo" value="${oaLetter.letterNo}"></td>
                                <td class="pt" style="width:5%;">密级</td>
                                <td wstyle="width:45%;"><input type="text" readonly name="oaLetter.secretLevel" value="${oaLetter.secretLevel}"></td>
                            </tr>
                            <tr>
                                <td class="pt" colspan="4" style="text-align: left;padding-left: 40px; color:#666;">
                                    <tt>标题：</tt><textarea id="reason" style="width:88%;" cols="20" readonly
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
                                    公文内容(在线Word)：</tt><span>
                                     <div class="text-center m-t-sm">
                                             <a href="javascript:void(0)"
                                                onclick="openPageOffice('${oaLetter.timeStamp}')" class="ZL-generate">查看公文内容</a>
                                      </div>
                                  </span></td>
                            </tr>
                            <tr>
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
                    </c:if>
                    <input type = "hidden" name = "taskId" value = "${task.id}" id = "taskId">
                    <input type = "hidden" name = "oaLetter.processInstanceId" value = "${oaLetter.processInstanceId}" id = "processInstanceId">
                    <input type = "hidden" name = "oaLetter.audit" id = "audit">
                    <input type = "hidden" name = "comment" id = "comment">
                    <input type = "hidden" name = "sysUserIds" id = "sysUserIds" value="">
                    <input type = "hidden" id = "PrimaryKey" value="${oaLetter.id}">
                    <span id="userName"></span>
                    <input type = "hidden" id = "timeStamp" value="${oaLetter.timeStamp}">
                </form>
            </div>
            <div class="buttons printHide" style="text-align: center;margin-top:20px;margin-bottom: 20px">
                <a class="submit radius2" onclick = "history.go(-1)" style="cursor: pointer">返回</a>
                <%--<a class="submit radius2" href="${ctx}/admin/oa/task/approvalList.json?taskId=${oaLetter.id}" style="cursor: pointer">发布记录</a>--%>
                <%--<a class="submit radius2" onclick = "selectSysUser(null)" style="cursor: pointer">选择联系人</a>--%>
                <%--<a class="submit radius2" onclick = "addApproval(1)" style="cursor: pointer">发布</a>--%>
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
        jQuery("textarea").css("min-height","80px");
        jQuery(".pt textarea").css({
            "min-height": "70px",
            "height": "70px"
        })
        jQuery("td input[type=\"text\"][readonly]").css("height","90%");
        jQuery('body').css({
            width: '210mm',
            height: '297mm',
            margin: 'auto',
            overflowX: 'hidden'
        }).removeClass('withvernav')

       jQuery(".contentwrapper").css("overflow-x",'hidden');
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
                top: top + 60,
                left: left
            }).find('.kg-img').css({
                width: '200px',
                height: '94px'
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