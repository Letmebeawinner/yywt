<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>流程详情</title>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">流程详情</h1>
    </div><!--pageheader-->
    <div style="margin-left: 20px;">
        <span style="color:red">流程列表</span><br>
            1.本页面显示流程信息详情；<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <c:forEach items="${processDetail.variableMaps}" var="processList">
            <c:forEach items="${processList}" var="process">
                <p>
                    <label>${process.key}</label>
                    <span class="field"><label>${process.value}</label></span>
                </p>
            </c:forEach>
        </c:forEach>
        <c:forEach items="${processDetail.taskMaps}" var="tasks">
            <c:forEach items="${tasks}" var="task">
                <p>
                    <label>${task.key}</label>
                    <span class="field"><label>${task.value}</label></span>
                </p>
            </c:forEach>
        </c:forEach>
    </div>
</div>
<script type="text/javascript">
    /**
     * 开启流程
     * @param id
     */
    function startProcess(id) {
        jQuery.ajax({
            url: "${ctx}/admin/oa/process/form/find/start.json",
            type: "post",
            dataType: "json",
            cache: false,
            data: {
                "processDefinitionId": id
            },
            success: function(result) {
                if (result.code=="0") {

                } else {
                    alert(result.message);
                    return;
                }
            }
        });
    }
    function delProcess(id) {
        if (confirm("你确定要删除该流程")) {
            jQuery.ajax({
                url: "${ctx}/admin/oa/process/del.json",
                type: "post",
                dataType: "json",
                cache: false,
                data: {
                    "deploymentId": id
                },
                success: function(result) {
                    if (result.code=="0") {
                        window.location.reload();
                    } else {
                        alert(result.message);
                        return;
                    }
                }
            });
        }
    }
    //激活或者挂起，0挂起，1激活
    function updateProcessState(id, flag) {
        if (flag == 'active') {
            var message = "你确定要激活该流程吗?";
        }
        if (flag == 'suspend') {
            var message = "你确定要挂起该流程吗?"
        }
        if (confirm(message)) {
            jQuery.ajax({
                url: "${ctx}/admin/oa/process/update.json",
                type: "post",
                dataType: "json",
                cache: false,
                data: {
                    "processDefinitionId": id,
                    "status": flag
                },
                success: function(result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.reload();
//                        if (flag == 'active') {
//                            console.log("suspend");
//                            jQuery("#update_state_" + id).attr("title", "挂起");
//                            jQuery("#update_state_" + id).attr("onclick", "updateProcessState(" + id + ", 'suspend')");
//                            jQuery("#update_state_" + id).html("挂起");
//                        }
//                        if (flag == 'suspend') {
//                            console.log("suspend");
//                            jQuery("#update_state_" + id).attr("title", "激活");
//                            jQuery("#update_state_" + id).attr("onclick", "updateProcessState(" + id + ", 'active')");
//                            jQuery("#update_state_" + id).html("激活");
//                        }
                    }
                }
            });
        }
    }
</script>
</body>
</html>