<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>流程定义列表</title>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">流程定义列表</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于流程信息查看；<br>
            2.编辑：点击<span style="color:red">编辑</span>，进入流程设计页面；<br>
            3.挂起：点击<span style="color:red">挂起</span>，挂起当前流程的流程；反之点击激活，激活当前流程<br>
            3.删除：点击<span style="color:red">删除</span>，删除当前流程的流程；<br>
            3.强制删除：点击<span style="color:red">强制删除</span>，删除当前流程的流程；(会删除该流程下的所有数据(谨慎使用!!!))<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div class="pr">
            <c:if test="${message != null}">
                <div class="ui-widget">
                    <div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
                        <p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
                            <strong>提示：</strong>${message}</p>
                    </div>
                </div>
            </c:if>
            <form action="${ctx}/admin/oa/model/form/deploy.json" method="post" enctype="multipart/form-data">
                <input type="file" name="file" />
                <input type="submit" value="部署新流程" />
            </form>
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <colgroup>
                    <col class="con0" style="width:10%;"/>
                    <col class="con1"/>
                    <col class="con0" style="width:20%;"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">分类</th>
                    <th class="head0 center">ID</th>
                    <th class="head0 center">名字</th>
                    <th class="head0 center">版本</th>
                    <th class="head1">图片</th>
                    <th class="head0 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${processDefinitions}" var="processDefinition">
                    <tr>
                        <td>${processDefinition.category}</td>
                        <td>${processDefinition.id}</td>
                        <td>${processDefinition.name}</td>
                        <td>${processDefinition.version}</td>
                        <td><a target="_blank" href='${ctx}/admin/oa/process/resource/read.json?processDefinitionId=${processDefinition.id}&resourceType=image'>${processDefinition.diagramResourceName }</a></td>
                        <td>
                            <c:if test = "${processDefinition.suspensionState == 1}">
                                <a href = "javascript:void(0)" onclick = "updateProcessState('${processDefinition.id}', 'suspend')" id = "update_state_${processDefinition.id}" title = "挂起" class="stdbtn">挂起</a>
                            </c:if>
                            <c:if test = "${processDefinition.suspensionState == 2}">
                                <a href = "javascript:void(0)" onclick = "updateProcessState('${processDefinition.id}', 'active')" id = "update_state_${processDefinition.id}" title = "激活" class="stdbtn">激活</a>
                            </c:if>
                            <a href = "${ctx}/admin/oa/to/dynamic/page.json?processDefinitionId=${processDefinition.id}" title = "启动" class="stdbtn">启动</a>
                            <a href = "${ctx}/admin/oa/to/category/set.json?processDefinitionId=${processDefinition.id}" title = "设置分类" class="stdbtn">设置分类</a>
                            <a href = "javascript:void(0)" onclick = "delProcess(${processDefinition.deploymentId}, 0)" title = "删除" class="stdbtn">删除</a>
                            <a href = "javascript:void(0)" onclick = "delProcess(${processDefinition.deploymentId}, 1)" title = "强制删除" class="stdbtn">强制删除</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- 分页，开始 -->
            <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
            <!-- 分页，结束 -->
        </div>
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
    function delProcess(id, flag) {
        if (confirm("你确定要删除该流程")) {
            jQuery.ajax({
                url: "${ctx}/admin/oa/process/del.json",
                type: "post",
                dataType: "json",
                cache: false,
                data: {
                    "deploymentId": id,
                    "flag": flag
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