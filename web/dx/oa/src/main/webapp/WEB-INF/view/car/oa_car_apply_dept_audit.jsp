<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>用车申请审核</title>
</head>
<body>
<div class="centercontent user-car">
    <div class="pageheader">
        <h1 class="pagetitle">用车申请审核</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于用车申请审核；<br>
            2.<span style="color:red">*</span>标记为必填项；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
                <div class="testtle-tables">
                    <form id = "carApplyDeptAudit" method="post">
                        <table border="1">
                            <caption align="bottom">备注：如需使用公务用车，原则上提前一天填写该表，经同意后方可使用。该审批单由出车驾驶员、办公室各留存一份，办公室每月底对驾驶员出车情况进行汇总统计</caption>
                            <tr>
                                <td class="pt" style="width:20%;">申请人</td>
                                <td class="pt" style="width:15%;"><span>${applyName}</span></td>
                                <td class="pt" style="width:15%;">用车时间</td>
                                <td style="width:50%;">
                                    <span><fmt:formatDate value="${carApply.startTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate> 到 <fmt:formatDate value="${carApply.endTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></span>
                                </td>
                            </tr>
                            <tr>
                                <td class="pt" >用车事由</td>
                                <td colspan="3"><span>${carApply.reason}</span></td>
                            </tr>
                            <tr>
                                <td class="pt" >线路、公里数</td>
                                <td colspan="3"><span>${carApply.distance}</span></td>
                            </tr>
                            <tr>
                                <td class="pt" ><small class="c-red">*</small>处室领导意见</td>
                                <td colspan="3"><span><input type = "text" name = "carApply.departmentOption" id = "departmentOption"></span></td>
                            </tr>
                            <tr>
                                <td class="pt" >办公室管理员意见</td>
                                <td colspan="3"><span></span></td>
                            </tr>
                            <tr>
                                <td class="pt" >所用车辆驾驶员</td>
                                <td colspan="3"><span>${carApply.driver}</span></td>
                            </tr>
                            <tr>
                                <td class="pt">所用车辆车牌号</td>
                                <td colspan="3"><span></span></td>
                            </tr>
                            <tr>
                                <td class="pt">审核状态</td>
                                <td colspan="3">
                                    <span>
                                        <c:if test = "${carApply.audit == 1}">
                                            审批已通过
                                        </c:if>
                                        <c:if test = "${carApply.audit == 0}">
                                            审核中
                                        </c:if>
                                        <c:if test = "${carApply.audit == 2}">
                                            已拒绝
                                        </c:if>
                                    </span>
                                </td>
                            </tr>
                        </table>
                </div>

                <input type="hidden" value="${task.id}" id="taskId" name="taskId">
                <input type="hidden" value="${carApply.processInstanceId}" name="processInstanceId">
                <input type="hidden" name="carApply.audit" id="audit">
                <input type="hidden" name="comment" id="comment">
            </form>
            <div class="buttons" style="text-align: center;margin-top:40px;">
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit(0);return false;">同意</button>
                    <button class="submit radius2" onclick="addFormSubmit(2);return false;">驳回</button>
                    <button class="submit radius2" onclick="history.go(-1);return false;">返回</button>
                </p>
            </div>
            <br/>
            <jsp:include page="/WEB-INF/view/common/process_transfer.jsp"></jsp:include>

        </div><!--subcontent-->
    </div>
</div>

<script type="text/javascript">
    function addFormSubmit(flag) {
        if (!jQuery("#departmentOption").val()) {
            alert("处室领导意见不能为空");
            return;
        }
        jQuery("#audit").val(flag);
        jQuery("#comment").val(jQuery("#departmentOption").val());
        var data = jQuery("#carApplyDeptAudit").serialize();
        jQuery.ajax({
            url: "${ctx}/admin/oa/carApplyDeptAudit.json",
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