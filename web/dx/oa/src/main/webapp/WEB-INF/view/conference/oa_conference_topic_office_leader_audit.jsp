<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>办公室确认</title>
    <script type="text/javascript" src="/pageoffice.js" id="po_js_main"></script>
    <script type="text/javascript">

        //汇报人
        var userIds = '${oaMeetingTopic.reporter}';
        if(userIds.length>1){
            userIds = userIds.substring(1,userIds.length-1);
            if(userIds!=''){
                jQuery.ajax({
                    url: '/admin/oa/conference/agenda/userList.json',
                    data: {"userIds":userIds},
                    type: 'post',
                    dataType: 'json',
                    success: function (result) {
                        if (result.code == "0") {
                            var userList = result.data;
                            if(userList!=null && userList.length!=0){
                                for(var i=0;i<userList.length;i++){
                                    if(i==userList.length-1){
                                        var element='<i>'+userList[i].userName+'</i>';
                                        jQuery("#issue").append(element);
                                    }else {
                                        var element='<i>'+userList[i].userName+'、'+'</i>';
                                        jQuery("#issue").append(element);
                                    }
                                }
                            }
                        } else {
                            jAlert(result.message, '提示', function () {
                            });
                        }
                    },
                    error: function (e) {
                        jAlert('添加失败', '提示', function () {
                        });
                    }
                });
            }
        }

        //列席人
        userIds = '${oaMeetingTopic.attendPeople}';
        if(userIds.length>1){
            userIds = userIds.substring(1,userIds.length-1);
            if(userIds!=''){
                jQuery.ajax({
                    url: '/admin/oa/conference/agenda/userList.json',
                    data: {"userIds":userIds},
                    type: 'post',
                    dataType: 'json',
                    success: function (result) {
                        if (result.code == "0") {
                            var userList = result.data;
                            if(userList!=null && userList.length!=0){
                                for(var i=0;i<userList.length;i++){
                                    if(i==userList.length-1){
                                        var element='<i>'+userList[i].userName+'</i>';
                                        jQuery("#attend").append(element);
                                    }else {
                                        var element='<i>'+userList[i].userName+'、'+'</i>';
                                        jQuery("#attend").append(element);
                                    }
                                }
                            }
                        } else {
                            jAlert(result.message, '提示', function () {
                            });
                        }
                    },
                    error: function (e) {
                        jAlert('添加失败', '提示', function () {
                        });
                    }
                });
            }
        }


        function addFormSubmit(flag) {

            jQuery("#audit").val(flag);
            var data = jQuery("#saveForm").serialize();
            jQuery.ajax({
                url: "${ctx}/admin/oa/meetingTopicAudit.json",
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

        function openPageOffice(timeStamp) {
            var url = "${ctx}/open/oaIssuesWord.json?timeStamp=" + timeStamp + "&type=2"
            window.location.href = "javascript:POBrowser.openWindow('" + url + "','width=1200px;height=800px;')";
        }

        jQuery(function(){
            jQuery("#pobmodal-dialog").hide()
        })
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">办公室确认</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来办办公室确认<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="saveForm">
                <p>
                    <label>议题名称</label>
                    <span class="field">
                        ${oaMeetingTopic.name}&nbsp;
                    </span>
                </p>
                <p>
                    <label>紧急程度</label>
                    <span class="field">
                        ${oaMeetingTopic.emergencyDegree}&nbsp;
                    </span>
                </p>
                <p>
                    <label>汇报人</label>
                    <span class="field">
                        <span id="issue">&nbsp;</span>
                        <%--<input type="text" name="oaMeetingTopic.reporter" class="longinput" maxlength="48">--%>
                        <%--<button class="radius2" onclick = "chooseUser(2);return false;">选择用户</button>--%>
                    </span>
                </p>
                <p>
                    <label>列席人</label>
                    <span class="field">
                        <span id="attend">&nbsp;</span>
                        <%--<button class="radius2" onclick = "chooseUser(1);return false;">选择用户</button>--%>
                        <%--<input type="text" name="oaMeetingTopic.attendPeople" class="longinput" maxlength="48">--%>
                    </span>
                </p>
                <p>
                    <label>议题内容</label>
                    <span class="field">
                        ${oaMeetingTopic.subjectContent} &nbsp;
                    </span>
                </p>
                <c:if test = "${not empty oaMeetingTopic.fileUrl}">
                    <p>
                        <label>议题文件(在线Word)</label>
                        <span class="field">
                             <a href="javascript:void(0)" onclick="openPageOffice('${oaMeetingTopic.timeStamp}')" class="ZL-generate">查看文件</a>
                            </span>
                    </p>
                </c:if>
                <p>
                    <label>审核状态</label>
                    <span class="field">
                        <c:if test = "${oaMeetingTopic.audit == 1}">
                            审批已通过
                        </c:if>
                        <c:if test = "${oaMeetingTopic.audit == 0}">
                            审核中
                        </c:if>
                        <c:if test = "${oaMeetingTopic.audit == 2}">
                            已拒绝
                        </c:if>
                    </span>

                </p>
                <p class="stdformbutton" style="text-align: center">
                    <%--<c:if test = "${processDefKey == 'oa_simple_conference_apply'}">--%>
                        <button class="submit radius2" onclick="addFormSubmit(1);return false;">确认</button>
                    <%--</c:if>--%>
                    <%--<c:if test = "${processDefKey == 'oa_conference_topic_apply'}">--%>
                        <%--<button class="submit radius2" onclick="addFormSubmit(0);return false;">同意</button>--%>
                    <%--</c:if>--%>
                    <%--<button class="submit radius2" onclick="addFormSubmit(2);return false;">驳回</button>--%>
                    <button class="submit radius2" onclick="history.go(-1);return false;">返回</button>
                </p>
                <input type = "hidden" name = "taskId" value = "${task.id}" id = "taskId">
                <input type = "hidden" name = "oaMeetingTopic.processInstanceId" value = "${oaMeetingTopic.processInstanceId}" id = "processInstanceId">
                <input type = "hidden" name = "oaMeetingTopic.audit" id = "audit">
                <input type = "hidden" name = "oaMeetingTopic.state" id = "state" value="1">
                <input type = "hidden" name = "comment" id = "comment" value="确认">
            </form>
            <br/>
            <jsp:include page="/WEB-INF/view/common/process_transfer.jsp"></jsp:include>
        </div>
    </div>
</div>
</body>
</html>