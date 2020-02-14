<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>已审核的任务</title>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">已审批任务列表</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于查看已审批任务；<br>
            2.查看详情：点击<span style="color:red">查看详情</span>，查看该任务详情；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="auditSearchForm" action="${ctx}/admin/oa/task/me/audit.json" method="post">
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
                        <span class="vam">审批时间 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" type="text"  readonly class="longinput"  name="taskSearch.auditStartTime" value="<fmt:formatDate value="${taskSearch.auditStartTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>" id = "auditStartTime">
                        </label>
                        <span class="vam">- &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" type="text" readonly class="longinput" name="taskSearch.auditEndTime" value="<fmt:formatDate value="${taskSearch.auditEndTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>" id = "auditEndTime">
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
                    <th class="head0 center">事由</th>
                    <th class="head0 center">状态</th>
                    <th class="head0 center">流程进度</th>
                    <th class="head1">申请人</th>
                    <th class="head1">申请时间</th>
                    <th class="head1">审批时间</th>
                    <th class="head0 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${tasks}" var="task">
                    <tr>
                        <td <c:if test="${task.red}">style="color: red"</c:if>>${task.id}</td>
                        <td <c:if test="${task.red}">style="color: red"</c:if>>${task.pdname}</td>
                        <td <c:if test="${task.red}">style="color: red"</c:if>>${task.reason}</td>
                        <td <c:if test="${task.red}">style="color: red"</c:if>>${task.currActivityName}</td>
                        <td <c:if test="${task.red}">style="color: red"</c:if>>
                            <c:if test="${task.endTime==null || task.endTime==''}">
                                <a <c:if test="${task.red}">style="color: red"</c:if> href="${ctx}/admin/oa/process/info.json?processInstanceId=${task.pid}" target="_blank" title="${task.pdname}">${task.pdname}${task.pdfKey}</a>
                            </c:if>
                            <c:if test="${task.endTime!=null && task.endTime!=''}">
                                已结束
                            </c:if>

                        </td>
                        <td <c:if test="${task.red}">style="color: red"</c:if>>${task.applyName}</td>
                        <td <c:if test="${task.red}">style="color: red"</c:if>>${task.createTime}</td>
                        <td <c:if test="${task.red}">style="color: red"</c:if>>${task.auditTime}</td>
                        <td class="center">
                            <a class="stdbtn" href="${ctx}/admin/oa/history/process/info.json?processInstanceId=${task.pid}&processDefinitionKey=${task.pdfKey}">查看详情</a>
                            <%--<c:if test="${isAuthority && task.pdname eq '公文(红头)'}">
                                <a class="stdbtn" href="${ctx}/admin/oa/task/approvalList.json?taskId=${task.id}">发布记录</a>
                            </c:if>--%>
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
</body>
</html>