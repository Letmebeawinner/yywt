<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>用印申请审核</title>
    <link rel="stylesheet" href="${ctx}/kinggrid/dialog/artDialog/ui-dialog.css">
    <link rel="stylesheet" href="${ctx}/kinggrid/core/kinggrid.plus.css">
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="background: #fff;">
        <h1 class="pagetitle tac">印章使用审批表</h1>
    </div><!--pageheader-->
    <div>
        <input type="button" class="test" style = "margin-left: 21px;cursor: pointer" value="电子签章" onclick = "clickSignature(this, 'pos1')">
    </div>
    <div id="contentwrapper" class="contentwrapper user-car">
        <div id="basicform" class="subcontent">
            <div class="testtle-tables">
                <form id = "sealApplyDeptAudit" method="post">
                    <table border="1">
                        <tr>
                            <td class="pt text_controller_center" style="width:15%;">用印部门</td>
                            <td class="pt" style="width:15%;"><span style="padding: 0;"><textarea name="sealApply.applyCompany" style="width:95%;" cols="20" rows="1" disabled>${departmentName}</textarea></span></td>
                            <td class="pt text_controller_center" style="width:15%;">用印人</td>
                            <td class="pt" style="width:15%;"><span style="padding: 0;"><textarea style="width:95%;" cols="20" rows="1" disabled>${applyName}</textarea></span></td>
                            <td class="pt text_controller_center" style="width:15%;">用印数量</td>
                            <td class="pt" style="width:15%;"><span style="padding: 0;"><textarea name="sealApply.useSealNum" style="width:95%;" cols="20" rows="1" disabled>${oaSeal.useSealNum}</textarea></span></td>
                        </tr>
                        <tr>
                            <td class="pt text_controller_center">用印事由</td>
                            <td class="pt" colspan="3" style="text-align: left; color:#666;">
                                <span style="padding: 0;"><textarea name="sealApply.reason" id="reason" style="width:95%;" cols="20" rows="5" disabled>${oaSeal.reason}</textarea></span>
                            </td>
                            <td class="pt text_controller_center">印章类别</td>
                            <td class="pt">
                                <span style="padding: 0;">
                                    <%--<textarea style="width:95%;" cols="20" rows="5" disabled>--%>
                                        <c:if test="${fn:contains(oaSeal.sealType,'1')}">
                                            中共贵阳市委党校<br/>
                                        </c:if>
                                        <c:if test="${fn:contains(oaSeal.sealType,'2')}">
                                            法人章<br/>
                                        </c:if>
                                        <c:if test="${fn:contains(oaSeal.sealType,'3')}">
                                            贵阳社会主义学院<br/>
                                        </c:if>
                                        <c:if test="${fn:contains(oaSeal.sealType,'4')}">
                                            贵阳行政学院<br/>
                                        </c:if>
                                        <c:if test="${fn:contains(oaSeal.sealType,'5')}">
                                            中共贵阳市委党校机关委员会<br/>
                                        </c:if>
                                        <c:if test="${fn:contains(oaSeal.sealType,'6')}">
                                            正校长章<br/>
                                        </c:if>
                                        <c:if test="${fn:contains(oaSeal.sealType,'7')}">
                                            中共贵阳市委党校纪律监察委员会<br/>
                                        </c:if>
                                        <c:if test="${fn:contains(oaSeal.sealType,'8')}">
                                            贵阳市县级党校体制改革推荐委员会<br/>
                                        </c:if>
                                        <c:if test="${fn:contains(oaSeal.sealType,'9')}">
                                            贵阳市县级党校体制改革推荐委员会办公室<br/>
                                        </c:if>
                                    <%--</textarea>--%>
                                </span>
                            </td>
                        </tr>
                        <tr>
                            <td class="pt text_controller_center">部门领导意见</td>
                            <td style="text-align: left; color:#666;" class="pt">
                                <span style="padding: 0;"><textarea name="sealApply.departAuditOption" style="width:95%;" cols="20" rows="5" disabled>${oaSeal.departAuditOption}</textarea></span>
                                <div id="pos0"  style="position:absolute;top: 150px;left: 15%;"bgcolor=#ffffff></div>
                            </td>
                            <td class="pt text_controller_center"><small class="c-red" style = "display: inline-block; vertical-align: top">*</small>分管校领导意见</td>
                            <td class="pt">
                                <span style="padding: 0;"><textarea id="schoolOption" name="sealApply.schoolOption" style="width:95%;" cols="20" rows="5">${oaSeal.schoolOption}</textarea></span>
                                <div id="pos1"  style="position:absolute;top: 150px;left: 48%;"bgcolor=#ffffff></div>
                            </td>
                            <td class="pt text_controller_center">常务副校长/副校长（主持工作）意见</td>
                            <td class="pt">
                                <span style="padding: 0;"><textarea name="sealApply.schoolLeaderOption" style="width:95%;" cols="20" rows="5" disabled>${oaSeal.schoolLeaderOption}</textarea></span>
                                <div id="pos2"  style="position:absolute;top: 150px;left: 82%;"bgcolor=#ffffff></div>
                            </td>
                        </tr>
                        <tr>
                            <td class="pt text_controller_center" rowspan="2">办公室主任意见</td>
                            <td colspan="2" rowspan="2" style="text-align: left; color:#666;" class="pt">
                                <span style="padding: 0;"><textarea name="sealApply.officeLeaderOption" style="width:95%;" cols="20" rows="5" disabled>${oaSeal.officeLeaderOption}</textarea></span>
                                <div id="pos3"  style="position:absolute;top: 320px;left: 20%;"bgcolor=#ffffff></div>
                            </td>
                            <td class="pt text_controller_center">盖章人</td>
                            <div id="pos4"  style="position:absolute;top: 300px;left: 71%;"bgcolor=#ffffff></div>
                            <td colspan="2" class="pt"><span style="padding: 0;"><textarea style="width:95%;" cols="20" rows="5" disabled></textarea></span></td>
                        </tr>
                        <tr>
                            <td class="pt text_controller_center">用印时间</td>
                            <td colspan="2" class="pt"><span style="padding: 0;"><input type="text" name = "sealApply.useSealTime" value="<fmt:formatDate value="${oaSeal.useSealTime}" pattern="yyyy-MM-dd"></fmt:formatDate>" style="width:90%;" id = "useSealTime" disabled></span></td>
                        </tr>
                        <c:if test = "${not empty oaSeal.fileUrl}">
                            <tr>
                                <td class="pt"><small class="c-red"></small>附件</td>
                                <td colspan="5">
                                <span>
                                     <a href="${ctx}/admin/oa/file/load.json?fileUrl=${oaSeal.fileUrl}&fileName=${fn:split(oaSeal.fileName, "(")[0]}" title="下载附件">${oaSeal.fileName}</a>
                                </span>
                                </td>
                            </tr>
                        </c:if>
                    </table>
                    <input type = "hidden" name = "taskId" value = "${task.id}" id = "taskId">
                    <input type = "hidden" name = "sealApply.processInstanceId" value = "${oaSeal.processInstanceId}" id = "processInstanceId">
                    <input type = "hidden" name = "comment" id = "comment">
                    <input type="hidden" name="sealApply.audit" id="audit">
                </form>
                <div class="buttons" style="text-align: center;margin-top:20px;margin-bottom: 20px">
                    <a class="submit radius2" onclick = "sealFormSubmit(0)" style="cursor: pointer">同意</a>
                    <a class="submit radius2" onclick = "sealFormSubmit(2)" style="cursor: pointer">驳回</a>
                    <a class="submit radius2" onclick = "history.go(-1)" style="cursor: pointer">返回</a>
                </div>
            </div>
            <br/>
        </div>
    </div>
    <jsp:include page="/WEB-INF/view/common/process_transfer.jsp"></jsp:include>
</div>
<script type="text/javascript" src="${ctx}/jquery-1.8.3.min.js"></script>
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
<script type="text/javascript">

    var id = '${oaSeal.id}';
    var type = 3;
    initSignature(id, "分管领导盖章");
    function sealFormSubmit(flag) {
        var list = Signature.list;
        var i = 0;
        for ( var key in list) {
            ++ i;
            saveSignature(key, list[key].getSignatureData());
        }
        if (i < 2) {
            alert("签字/盖章后才能提交");
            return;
        }
        // if (!jQuery("#schoolOption").val()) {
        //     alert('分管校领导意见不能为空');
        //     return;
        // }
        jQuery("#audit").val(flag);
        jQuery("#comment").val(jQuery("#schoolOption").val());
        var data = jQuery("#sealApplyDeptAudit").serialize();
        jQuery.ajax({
            url: "${ctx}/admin/oa/sealApplyDeptAudit.json",
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

    function changeOption(flag) {
        console.log(flag);
        if (flag == 0) {
            jQuery("#unPassReason").hide();
        } else {
            jQuery("#unPassReason").show();
        }
    }

    function resetData(){
        jQuery(".longinput").val("");
    }

</script>
</body>
</html>