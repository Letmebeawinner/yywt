<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<%@ page import="com.a_268.base.constants.BaseCommonConstants" %>
<c:set var="basePath" value="<%=BaseCommonConstants.basePath%>"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <script type="text/javascript">

        jQuery("#waitNum").html('${waitNum}');

        function claimTask(taskId) {
            jQuery.ajax({
                url: "${ctx}/admin/task/claim.json",
                type: "get",
                dataType: "json",
                cache: false,
                data: {"taskId" : taskId},
                success: function(result) {
                    if (result.code=="0") {
                        var url = "${oaPath}/admin/oa/task/to/complete.json?taskId=" + taskId;
                        ajaxSwitchSystem('OA',url);
                    } else {
                        alert(result.message);
                        return;
                    }
                }
            });
        }

        function ajaxSwitchSystem(sysKey,url) {
            jQuery.ajax({
                url: "${ctx}/admin/ajax/switchSystem.json",
                type: "get",
                dataType: "json",
                cache: false,
                data: {
                    "sysKey" : sysKey
                },
                success: function(result) {
                    if (result.code=="0") {
                        window.location.href = url;
                    } else {
                        alert(result.message);
                        return;
                    }
                }
            });
        }


        function approvalResult(resultId,type) {
            if(type==7){
                var url="${kyPath}/admin/ky/finish.json?id="+resultId;
                ajaxSwitchSystem("KY",url);
            }else {
                var url="${kyPath}/admin/ky/toApprovalResult.json?id="+resultId;
                ajaxSwitchSystem("KY",url);
            }
        }

        function approvalProject(projectId,type) {
            if(type==1){
                var url="${zzPath}/admin/zz/projectEstablishment/approvalProject.json?resultId="+projectId;
                ajaxSwitchSystem("ZZ",url);
            }else if(type==2){
                var url="${zzPath}/admin/zz/projectEstablishment/knotProject.json?resultId="+projectId;
                ajaxSwitchSystem("ZZ",url);
            }
        }

        function infoRecord(id){
            jQuery.ajax({
                data: {"id":id},
                dataType: 'json',
                url: "${ctx}/admin/ajax/updateInfoReceiveRecord.json",
                type: 'post',
                success: function (result) {
                    if (result.code == '0') {
                        var url = "${smsPath}/admin/sms/info/getInfoReceiveRecord.json?id=" + id;
                        ajaxSwitchSystem('SMS',url);
                    } else {
                        alert(result.message);
                    }
                }
            });
        }

        function changeType(e,type) {
            jQuery(e).siblings().removeClass("current");
            jQuery(e).addClass("current");
            // jQuery("#type").val(type);

            jQuery("#taskList").hide();
            jQuery("#resultList").hide();
            jQuery("#projectList").hide();
            jQuery("#letterList").hide();
            jQuery("#"+type).show();

            if(type=="infoRecordList"){
                jQuery("#goToBtn").html("我的消息(发文) >");
            }else{
                jQuery("#goToBtn").html("我已审批 >");
            }
        }

        function openPageOffice(processDefinitionId) {
            console.log(processDefinitionId + "====")
            var url = "${oaPath}/open/oaHistoryWord.json?processDefinitionId=" + processDefinitionId + "&type=2"
            window.location.href = "javascript:POBrowser.openWindow('" + url + "','width=1200px;height=800px;')";
        }

        function openLetterInfo(OpenWordControllerprocessInstanceId,letterId) {
            jQuery.ajax({
                type:"post",
                url:"${oaPath}/admin/oa/ajax/setRead.json",
                data:{"letterId":letterId},
                dataType:"json",
                xhrFields: {
                    withCredentials: true // 这里设置了withCredentials
                },
                async: false,
                success:function (result) {
                    if (result.code=="0") {
                        var url = "${oaPath}/admin/oa/history/process/info.json?processInstanceId="+ processInstanceId +"&processDefinitionKey=oa_inner_letter";
                        ajaxSwitchSystem('OA',url)
                    } else {
                        alert(result.message);
                    }
                }
            });
        }
        
        
        function alreadyProcess() {
            var e=jQuery("dl").find(".current")[0];
            var type=jQuery(e).index();
            if(type==1){
                ajaxSwitchSystem('OA',"${oaPath}/admin/oa/task/me/audit.json");
            }else if(type==2){
                ajaxSwitchSystem('KY',"${kyPath}/admin/ky/getAlreadyProcessList.json?resultType=1");
            }else if(type==3){
                ajaxSwitchSystem('ZZ',"${zzPath}/admin/ky/getAlreadyProcessList.json?resultType=2");
            }else if(type==4){
                ajaxSwitchSystem('OA',"${oaPath}/admin/oa/process/approvalList.json");
            }else if(type==5){
                ajaxSwitchSystem('SMS',"${smsPath}/admin/sms/info/queryInfoList.json");
            }
        }
    </script>
</head>
<body>
    <aside class="to-do_left">
        <dl>
            <dt>全部事项</dt>
            <dd onclick="changeType(this,'taskList');false;" class="current">
                <i class="fa fa-book"></i>
                <span>文件审批</span>
                <c:if test="${taskList!=null && taskList.size()>0}"><em>${taskList.size()}</em></c:if>
            </dd>
            <dd onclick="changeType(this,'resultList');false;">
                <i class="fa fa-film"></i>
                <span>课题审批</span>
                <c:if test="${resultList!=null && resultList.size()>0}"><em>${resultList.size()}</em></c:if>
            </dd>
            <dd onclick="changeType(this,'projectList');false;">
                <i class="fa fa-shopping-cart"></i>
                <span>立项审批</span>
                <c:if test="${projectList!=null && projectList.size()>0}"><em>${projectList.size()}</em></c:if>
            </dd>
            <dd onclick="changeType(this,'letterList');false;">
                <i class="fa fa-book"></i>
                <span>未读公文</span>
                <c:if test="${letterList!=null && letterList.size()>0}"><em>${letterList.size()}</em></c:if>
            </dd>
            <dd onclick="changeType(this,'infoRecordList');false;">
                <i class="fa fa-book"></i>
                <span>未读消息</span>
                <c:if test="${infoRecordList!=null && infoRecordList.size()>0}"><em>${infoRecordList.size()}</em></c:if>
            </dd>
        </dl>
    </aside>
    <form id="searchForm" action="${ctx}/admin/waitProcess.json" method="post">
        <input type="hidden" name="type" value="${type}"/>
    </form>
    <section class="to-do_right">
        <header class="fsize16" style="color: #333">待办清单 |
            <a id="goToBtn" href="javascript:void(0);" onclick="alreadyProcess();" style="color: #aaa" class="fsize16">我已审批 ></a>
        </header>
        <div class="toDo_bx">
            <section class="to-do_list" id="taskList">
                <ul class="toDo-list">
                    <c:forEach items="${taskList}" var="task">
                        <li  onclick='claimTask(${task.id})'>
                            <div class="pull-left t-list-left">
                                <i class="fa fa-file-text-o"></i>
                                <a href="javascript:;" style="text-decoration: underline;">${task.pdname}-${task.reason}</a>
                            </div>
                            <div class="pull-right">
                                <span>${task.applyName}</span>
                                <span class="t-list-time">${task.createTime}</span>
                            </div>
                            <div class="clear"></div>
                        </li>
                    </c:forEach>
                </ul>
            </section>
            <section class="to-do_list" id="resultList" style="display: none">
                <ul class="toDo-list">
                    <c:forEach items="${resultList}" var="result">
                        <li  onclick='approvalResult(${result.id},${result.passStatus})'>
                            <div class="pull-left t-list-left">
                                <i class="fa fa-file-text-o"></i>
                                <a href="javascript:;" style="text-decoration: underline;">${result.name}</a>
                            </div>
                            <div class="pull-right">
                                <span>${result.workName}</span>
                                <span class="t-list-time">${fn:substring(result.addTime,0,16)}</span>
                            </div>
                            <div class="clear"></div>
                        </li>
                    </c:forEach>
                </ul>
            </section>
            <section class="to-do_list" id="projectList" style="display: none">
                <ul class="toDo-list">
                    <c:forEach items="${projectList}" var="project">
                        <li  onclick='approvalProject(${project.id},${project.passStatus})'>
                            <div class="pull-left t-list-left">
                                <i class="fa fa-file-text-o"></i>
                                <a href="javascript:;" style="text-decoration: underline;">${project.name}</a>
                            </div>
                            <div class="pull-right">
                                <span>${project.workName}</span>
                                <span class="t-list-time">${fn:substring(project.addTime,0,16)}</span>
                            </div>
                            <div class="clear"></div>
                        </li>
                    </c:forEach>
                </ul>
            </section>
            <section class="to-do_list" id="letterList" style="display: none">
                <ul class="toDo-list">
                    <c:forEach items="${letterList}" var="letter">
                        <li
                            <c:if test="${letter.letterType=='1'}">onclick="openPageOffice('${letter.timeStamp}')"</c:if>
                            <c:if test="${letter.letterType=='2'}">onclick="openLetterInfo('${letter.processInstanceId}','${letter.letterId}')"</c:if>
                        >
                            <div class="pull-left t-list-left">
                                <i class="fa fa-file-text-o"></i>
                                <a href="javascript:;" style="text-decoration: underline;">${letter.title}</a>
                            </div>
                            <div class="pull-right">
                                <span>${letter.senderName}</span>
                                <span class="t-list-time">${fn:substring(letter.updateTime,0,16)}</span>
                            </div>
                            <div class="clear"></div>
                        </li>
                    </c:forEach>
                </ul>
            </section>
            <section class="to-do_list" id="infoRecordList" style="display: none">
                <ul class="toDo-list">
                    <c:forEach items="${infoRecordList}" var="infoRecord">
                        <li  onclick='infoRecord(${infoRecord.id})'>
                            <div class="pull-left t-list-left">
                                <i class="fa fa-file-text-o"></i>
                                <a href="javascript:;" style="text-decoration: underline;">${infoRecord.content}</a>
                            </div>
                            <div class="pull-right">
                                <span>
                                    <c:choose>
                                        <c:when test="${info.senderId == 0}">系统发送</c:when>
                                        <c:otherwise>${senders.get(info.senderId.toString())}</c:otherwise>
                                    </c:choose>
                                </span>
                                <span class="t-list-time">${fn:substring(infoRecord.createTime,0,16)}</span>
                            </div>
                            <div class="clear"></div>
                        </li>
                    </c:forEach>
                </ul>
            </section>
        </div>
    </section>
    <%--<jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>--%>
</body>
</html>
