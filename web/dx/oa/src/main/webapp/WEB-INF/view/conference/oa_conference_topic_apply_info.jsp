<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>议题申请详情</title>
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
                                        var element=userList[i].userName;
                                        jQuery("#issue").append(element);
                                    }else {
                                        var element=userList[i].userName+'、';
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
                                        var element=userList[i].userName;
                                        jQuery("#attend").append(element);
                                    }else {
                                        var element=userList[i].userName+'、';
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


        function printDocument() {
            jQuery("#print").hide();
            jQuery(".printHide").hide();
            jQuery(".header").hide();
            jQuery(".iconmenu").hide();
            jQuery(".centercontent").css("marginLeft","0");
            jQuery("textarea").css("min-height","102px");
            jQuery("td").css("border","1px solid #999");


            // return;
            window.print();
            jQuery("#print").show();
            jQuery(".printHide").show();
            jQuery(".header").show();
            jQuery(".iconmenu").show();
            jQuery(".centercontent").css("marginLeft","181px");
            jQuery("td").css("border","1px solid #ddd");
            jQuery("textarea").css("height", "100%");

        }
        function openPageOffice(timeStamp) {
            var url = "${ctx}/open/oaIssuesWord.json?timeStamp=" + timeStamp +"&type=2"
            window.location.href = "javascript:POBrowser.openWindow('" + url + "','width=1200px;height=800px;')";
        }

        jQuery(function(){
            jQuery("#pobmodal-dialog").hide()
        })
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="background: #fff;">
        <h1 class="page-list " style="text-align: center;">
            议题申请详情
        </h1>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="saveForm">
                <p>
                    <label>议题名称</label>
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
                <%--<p>--%>
                <%--<label><em style="color: red;">*</em>议题内容</label>--%>
                <%--<span class="field">--%>
                <%--${oaMeetingTopic.subjectContent}--%>
                <%--</span>--%>
                <%--</p>--%>
                <p class = "printHide">
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
                <p class="stdformbutton printHide" style="text-align: center">
                    <button class="submit radius2" onclick="window.opener=null;window.open('','_self');window.close();">关闭</button>
                </p>
                <input type = "hidden" name = "taskId" value = "${task.id}" id = "taskId">
                <input type = "hidden" name = "oaMeetingTopic.processInstanceId" value = "${oaMeetingTopic.processInstanceId}" id = "processInstanceId">
                <input type = "hidden" name = "oaMeetingTopic.audit" id = "audit">
            </form>
            <br/>
            <c:if test="${hisProcessEntities!=null}">
                <jsp:include page="/WEB-INF/view/common/process_transfer.jsp"></jsp:include>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>