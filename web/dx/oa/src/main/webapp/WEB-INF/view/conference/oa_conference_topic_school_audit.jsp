<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>分管校领导审批</title>
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
                                    var element=' <a class="close" id="issue_'+userList[i].id+'"> <i>'+userList[i].userName+'</i><i class="fa fa-w c-999 fa-close" onclick="removeUser(this)"></i> </a>';
                                    jQuery("#issue").append(element);
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
                                    var element=' <a class="close" id="attend_'+userList[i].id+'"> <i>'+userList[i].userName+'</i><i class="fa fa-w c-999 fa-close" onclick="removeUser(this)"></i> </a>';
                                    jQuery("#attend").append(element);
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


        /**
         * 点击x号  移除联系人
         * @param obj 点击的js对象
         */
        function removeUser(obj) {
            jQuery(obj).parent().remove();
        }


        /**
         * 用户列表  点击多选框
         */
        function checkuser(obj) {
            //判断是否是全部选中了
            var bn = true;
            jQuery(".users").each(function () {
                if (!this.checked) {
                    bn = false;
                }
            });
            if (bn) {
                jQuery(":checkbox").attr("checked", true);
            } else {
                jQuery("#checkAll").attr("checked", false);
            }

            var userId = jQuery(obj).val();
            var userName = jQuery("#username_" + userId).text().trim();
            //添加联系人
            if(obj.checked){//选中的添加
                if(jQuery("#type").val()==2){
                    if(!jQuery("#issue_"+userId).text()){
                        var element=' <a class="close" id="issue_'+userId+'"> <i>'+userName+'</i><i class="fa fa-w c-999 fa-close" onclick="removeUser(this)"></i> </a>';
                        jQuery("#issue").append(element);
                    }
                }
                if(jQuery("#type").val()==1){
                    if(!jQuery("#attend_"+userId).text()){
                        var element=' <a class="close" id="attend_'+userId+'"> <i>'+userName+'</i><i class="fa fa-w c-999 fa-close" onclick="removeUser(this)"></i> </a>';
                        jQuery("#attend").append(element);
                    }
                }
            } else {//取消选中的移除
                if(jQuery("#type").val()==2){
                    var id = "#issue_" + userId;
                    jQuery(id).remove();
                }
                if(jQuery("#type").val()==1){
                    var id = "#attend_" + userId;
                    jQuery(id).remove();
                }
            }
        }
        function checkAll(obj) {
            var a = obj.checked;
            jQuery(":checkbox").attr("checked", a);
            if (a) {
                jQuery("#chooseText").text("取消全选");
                jQuery(".users").each(function () {
                    checkuser(this);
                });
            } else {
                jQuery("#chooseText").text("全部选中");
                jQuery(".users").each(function () {
                    checkuser(this);
                });
            }
        }

        /**
         * 发送短信页面
         * 点击选择学员
         */
        function chooseUser(type) {
            jQuery("#type").val(type);

            var attend = jQuery('#attend');
            var len1 = attend.children('a').size() - 1;

            var issue = jQuery('#issue');
            var len2 = issue.children('a').size() - 1;
            jQuery.ajax({
                url: '${ctx}/admin/oa/ajax/queryReceivers.json',
                type: 'POST',
                data: {'from': 2},
                dataType: 'html',
                success: function (result) {
                    jQuery.alerts._show("添加用户", result, null, 'dialog', function (data) {
                        if (!data) {
                            if(type==1){
                                if (len1 < 0) attend.children('a').remove();
                                attend.children("a:gt(" + len1 + ")").remove();
                            }
                            if(type==2){
                                if (len2 < 0) issue.children('a').remove();
                                issue.children("a:gt(" + len2 + ")").remove();
                            }
                        }
                    });
                }
            });
        }


        function addFormSubmit(flag) {

            var issue = jQuery("#issue").children();
            var reporter = ",";

            jQuery.each(issue, function() {
                console.log(jQuery(this).attr("id"));
                reporter += jQuery(this).attr("id").replace("issue_","") + ",";
            });
            jQuery("#reporter").val(reporter);

            var attend = jQuery("#attend").children();
            var attendPeople = ",";
            jQuery.each(attend, function() {
                attendPeople += jQuery(this).attr("id").replace("attend_","") + ",";
            });
            jQuery("#attendPeople").val(attendPeople);

            jQuery("#audit").val(flag);
            // if (!jQuery("#comment").val()) {
            //     alert("请输入意见");
            //     return;
            // }
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
            var url = "${ctx}/open/oaIssuesWord.json?timeStamp=" + timeStamp
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
        <h1 class="pagetitle">部门领导审批</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来议题申请<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="saveForm">
                <p>
                    <label><em style="color: red;">*</em>议题名称</label>
                    <span class="field">
                        <%--<input type="text" name="oaMeetingTopic.name" class="longinput" maxlength="48" value = "${oaMeetingTopic.name}">--%>
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
                    <label><em style="color: red;">*</em>汇报人</label>
                    <span class="field">
                            <span id="issue">&nbsp;</span>
                        <%--<input type="text" name="oaMeetingTopic.reporter" class="longinput" maxlength="48">--%>
                        <button class="radius2" onclick = "chooseUser(2);return false;">选择用户</button>
                    </span>
                </p>
                <p>
                    <label>列席人</label>
                    <span class="field">
                        <span id="attend">&nbsp;</span>
                        <button class="radius2" onclick = "chooseUser(1);return false;">选择用户</button>
                        <%--<input type="text" name="oaMeetingTopic.attendPeople" class="longinput" maxlength="48">--%>
                    </span>
                </p>
                <p>
                    <label>议题内容</label>
                    <span class="field">
                        ${oaMeetingTopic.subjectContent} &nbsp;
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>分管校领导意见</label>
                    <span class="field">
                        <textarea cols="10" rows="5" id = "comment" name = "comment" class="longinput"></textarea>
                    </span>
                </p>
                <c:if test = "${not empty oaMeetingTopic.fileUrl}">
                    <p>
                        <label>议题文件(在线Word)</label>
                        <span class="field">
                             <a href="javascript:void(0)" onclick="openPageOffice('${oaMeetingTopic.timeStamp}')" class="ZL-generate">编辑文件</a>
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
                    <button class="submit radius2" onclick="addFormSubmit(1);return false;">同意</button>
                    <button class="submit radius2" onclick="addFormSubmit(2);return false;">驳回</button>
                    <button class="submit radius2" onclick="history.go(-1);return false;">返回</button>
                </p>
                <input type = "hidden" name = "taskId" value = "${task.id}" id = "taskId">
                <input type = "hidden" name = "oaMeetingTopic.processInstanceId" value = "${oaMeetingTopic.processInstanceId}" id = "processInstanceId">
                <input type = "hidden" name = "oaMeetingTopic.audit" id = "audit">
                <input type="hidden" name="oaMeetingTopic.reporter" id="reporter">
                <input type="hidden" name="oaMeetingTopic.attendPeople" id="attendPeople">
                <input type = "hidden" name = "oaMeetingTopic.state" id = "state" value="0">
                <input type="hidden" name="type" id="type">
            </form>
            <br/>
            <jsp:include page="/WEB-INF/view/common/process_transfer.jsp"></jsp:include>
        </div>
    </div>
</div>
</body>
</html>