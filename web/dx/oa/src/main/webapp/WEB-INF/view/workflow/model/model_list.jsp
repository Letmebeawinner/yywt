<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>模型列表</title>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">模型列表</h1>
    </div><!--pageheader-->
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
            1.本页面用于模型信息查看；<br>
            2.编辑：点击<span style="color:red">编辑</span>，进入流程设计页面；<br>
            3.部署：点击<span style="color:red">部署</span>，部署当前模型的流程；<br>
            4.部署：删除<span style="color:red">删除</span>，删除当前模型的流程；<br>
            5.导出：导出<span style="color:red">导出</span>，导出当前模型的流程；<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <colgroup>
                    <col class="con0"/>
                    <col class="con1"/>
                    <col class="con0"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">ID</th>
                    <th class="head0 center">名称</th>
                    <th class="head1">KEY</th>
                    <th class="head1">模型信息</th>
                    <th class="head1">创建时间</th>
                    <th class="head1">最后修改时间</th>
                    <th class="head1">版本</th>
                    <th class="head0 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${models}" var="model">
                    <tr>
                        <td>${model.id}</td>
                        <td>${model.name}</td>
                        <td>${model.key}</td>
                        <td>${model.metaInfo}</td>
                        <td><fmt:formatDate value="${model.createTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                        <td><fmt:formatDate value="${model.lastUpdateTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                        <td>${model.version}</td>
                        <td class="center">
                            <a href="${ctx}/modeler.html?modelId=${model.id}" class="stdbtn" title="编辑">编辑</a>
                            <a href="javascript:void(0)" class="stdbtn" title="部署" onclick="deploy(${model.id})">部署</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delModel(${model.id})">删除</a>
                            <a href="${ctx}/admin/oa/model/export.json?modelId=${model.id}" class="stdbtn" title="导出">导出</a>
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
    function deploy(modelId) {
        jQuery.ajax({
            url: "${ctx}/admin/oa/model/deploy.json",
            type: "post",
            dataType: "json",
            cache: false,
            data: {
                "modelId" : modelId
            },
            success: function(result) {
               alert(result.message);
                return;
            }
        });
    }
    function delModel(modelId) {
        if (confirm("你确定要删除该模型?")) {
            jQuery.ajax({
                url: "${ctx}/admin/oa/model/delete.json",
                type: "post",
                dataType: "json",
                cache: false,
                data: {
                    "modelId" : modelId
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

    function resetData(){
        jQuery(".longinput").val("");
    }
</script>
</body>
</html>