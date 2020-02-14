<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>我发起的</title>
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
        <h1 class="pagetitle">我发起的申请列表</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于我发起的流程信息查看；<br>
            2.查看详情:<span style="color:red">查看详情</span>页，点击直接进入详情页；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="auditSearchForm" action="${ctx}/admin/oa/task/history/mine.json" method="post">
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
                    <th class="head0 center">请求类型</th>
                    <th class="head0 center">事由</th>
                    <th class="head0 center">状态</th>
                    <th class="head0 center">流程进度</th>
                    <th class="head1">流程开始时间</th>
                    <th class="head1">流程结束时间</th>
                    <th class="head1">发起人</th>
                    <th class="head1">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${maps}" var="map">
                    <tr>
                        <td>${map.pId}</td>
                        <td>${map.pDefName}</td>
                        <td>${map.reason}</td>
                        <td>${map.currActivityName}</td>
                        <td>
                            <c:if test="${map.pEndTime==null || map.pEndTime==''}">
                                <a href = "${ctx}/admin/oa/process/info.json?processInstanceId=${map.pId}" target="_blank" title = "${map.pDefName}">${map.pDefName}${map.pDefKey}</a>
                            </c:if>
                            <c:if test="${map.pEndTime!=null && map.pEndTime!=''}">
                                已结束
                            </c:if>
                        </td>
                        <td><fmt:formatDate value='${map.pStartTime}' pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
                        <td><fmt:formatDate value='${map.pEndTime}' pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
                        <td>${map.pStartName}</td>
                        <td>
                            <a href="${ctx}/admin/oa/history/process/info.json?processInstanceId=${map.pId}&processDefinitionKey=${map.pDefKey}" class="stdbtn" title="查看详情">查看详情</a>
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

<!-- 办理任务对话框 -->
<div id="handleTemplate" class="template"></div>
</body>
</html>