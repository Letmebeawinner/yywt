<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>待审批任务列表</title>
    <script type="text/javascript">
        function searchForm() {
            jQuery("#auditSearchForm").submit();
        }

        //清空搜索栏
        function emptyForm() {
            jQuery("#processDefName").val("");
            jQuery("#applyName").val("");
            jQuery("#auditStartTime").val("");
            jQuery("#auditEndTime").val("");
            jQuery("#processDefKey").val("");
        }

        jQuery(function(){
            laydate.skin('molv');
//        laydate({
//            elem: '#applyStartTime',
//            format:'YYYY-MM-DD hh:mm:ss',
//            istime: true
//        });
//        laydate({
//            elem: '#applyEndTime',
//            format:'YYYY-MM-DD hh:mm:ss',
//            istime: true
//        });
            laydate({
                elem: '#auditStartTime',
                format:'YYYY-MM-DD hh:mm:ss',
                istime: true
            });
            laydate({
                elem: '#auditEndTime',
                format:'YYYY-MM-DD hh:mm:ss',
                istime: true
            });
        });
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">待审批任务列表</h1>
        <div style="margin-left: 20px;">
            1.本页面用于查看待审批任务；<br>
            2.签收：点击<span style="color:red">签收</span>，签收该任务；<br>
            3.查看详情：点击<span style="color:red">查看详情</span>，查看该任务详情；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="auditSearchForm" action="${ctx}/admin/oa/task/to/claim/list.json" method="post">
                    <%--<div class="disIb ml20 mb10">--%>
                        <%--<span class="vam">请求类型 &nbsp;</span>--%>
                        <%--<label class="vam">--%>
                            <%--<input style="width: auto;" type="text" class="longinput" placeholder="输入请求类型" name="taskSearch.processDefName" value="${taskSearch.processDefName}" id = "processDefName"/>--%>
                        <%--</label>--%>
                    <%--</div>--%>
                    <div class="disIb ml20 mb10">
                        <span class="vam">请求类型 &nbsp;</span>
                        <label class="vam">
                            <select style="width: auto;" name="taskSearch.processDefKey" id = "processDefKey">
                                <option value="">请选择</option>
                                <option <c:if test="${taskSearch.processDefKey=='oa_car_apply'}">selected="selected"</c:if> value="oa_car_apply">用车申请</option>
                                <option <c:if test="${taskSearch.processDefKey=='oa_conference_topic_apply'}">selected="selected"</c:if> value="oa_conference_topic_apply">议题申请</option>
                                <option <c:if test="${taskSearch.processDefKey=='oa_agenda_apply'}">selected="selected"</c:if> value="oa_agenda_apply">议程申请</option>
                                <option <c:if test="${taskSearch.processDefKey=='oa_letter_apply'}">selected="selected"</c:if> value="oa_letter_apply">公文(红头)</option>
                                <option <c:if test="${taskSearch.processDefKey=='oa_inner_letter'}">selected="selected"</c:if> value="oa_inner_letter">公文(内部)</option>
                                <option <c:if test="${taskSearch.processDefKey=='oa_archive_apply'}">selected="selected"</c:if> value="oa_archive_apply">档案查询申请</option>
                                <option <c:if test="${taskSearch.processDefKey=='oa_leave_apply'}">selected="selected"</c:if> value="oa_leave_apply">请假申请</option>
                                <option <c:if test="${taskSearch.processDefKey=='oa_news_apply'}">selected="selected"</c:if> value="oa_news_apply">信息发布</option>
                                <option <c:if test="${taskSearch.processDefKey=='oa_seal_apply'}">selected="selected"</c:if> value="oa_seal_apply">用章申请</option>
                            </select>
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">申请人 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" type="text" class="longinput" placeholder="输入申请人" name="taskSearch.applyName" value="${taskSearch.applyName}" id = "applyName">
                        </label>
                    </div>
                    <%--<div class="disIb ml20 mb10">--%>
                    <%--<span class="vam">申请时间 &nbsp;</span>--%>
                    <%--<label class="vam">--%>
                    <%--<input style="width: auto;" type="text" class="longinput"  name="taskSearch.applyStartTime" value="<fmt:formatDate value="${taskSearch.applyStartTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>" id = "applyStartTime">--%>
                    <%--</label>--%>
                    <%--<span class="vam">- &nbsp;</span>--%>
                    <%--<label class="vam">--%>
                    <%--<input style="width: auto;" type="text" class="longinput"  name="taskSearch.applyEndTime" value="<fmt:formatDate value="${taskSearch.applyEndTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>" id = "applyEndTime">--%>
                    <%--</label>--%>
                    <%--</div>--%>
                    <div class="disIb ml20 mb10">
                        <span class="vam">申请时间 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" type="text"  readonly class="longinput"  name="taskSearch.applyStartTime" value="<fmt:formatDate value="${taskSearch.auditStartTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>" id = "auditStartTime">
                        </label>
                        <span class="vam">- &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" type="text" readonly class="longinput" name="taskSearch.applyEndTime" value="<fmt:formatDate value="${taskSearch.auditEndTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>" id = "auditEndTime">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchForm()">搜 索</a>
                    <a href="javascript: void(0)" class="stdbtn ml10" onclick="emptyForm()">重 置</a>
                </div>
            </div>
        </div>
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <colgroup>
                    <col class="con0" style="width:10%;"/>
                    <col class="con1"/>
                    <col class="con0" style="width:20%;"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">序号</th>
                    <th class="head1">请求类型</th>
                    <th class="head1">事由</th>
                    <th class="head0 center">状态</th>
                    <th class="head0 center">流程进度</th>
                    <th class="head1">申请人</th>
                    <th class="head1">申请时间</th>
                    <th class="head0 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${tasks}" var="task">
                    <tr>
                        <td>${task.id}</td>
                        <td>${task.pdname}</td>
                        <td>${task.reason}</td>
                        <td>${task.name}</td>
                        <td><a href="${ctx}/admin/oa/process/info.json?processInstanceId=${task.pid}" target="_blank" title="${task.pdname}">${task.pdname}${task.pdfKey}</a></td>
                        <td>${task.applyName}</td>
                        <td>${task.createTime}</td>
                        <td class="center">
                            <a href="javascript:void(0)" class="stdbtn" title="签收" onclick='claimTask(${task.id})'>签收</a>
                            <a class="stdbtn" href="${ctx}/admin/oa/history/process/info.json?processInstanceId=${task.pid}&processDefinitionKey=${task.pdfKey}">查看详情</a>
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
    function claimTask(taskId) {
        jQuery.ajax({
            url: "${ctx}/admin/oa/task/claim.json",
            type: "post",
            dataType: "json",
            cache: false,
            data: {
                "taskId" : taskId
            },
            success: function(result) {
                if (result.code=="0") {
                    window.location.href = "${ctx}/admin/oa/task/to/complete.json?taskId=" + taskId;
                } else {
                    alert(result.message);
                    return;
                }
            }
        });
    }

</script>
</body>
</html>