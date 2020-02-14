<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>议题申请</title>
    <script type="text/javascript" src="/pageoffice.js" id="po_js_main"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/kindeditor/themes/default/default.css"/>
    <script type="text/javascript" src="${ctx}/kindeditor/kindeditor-all.js"></script>
    <script type="text/javascript" charset="utf-8" src="/static/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="/static/ueditor/ueditor.all.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="/static/ueditor/lang/zh-cn/zh-cn.js"></script>
    <link type="text/css" rel="stylesheet" href='${ctx}/static/uploadify/uploadify.css'/>
    <script type="text/javascript" src="${ctx}/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/uploadify/upload-file.js"></script>
    <script type="text/javascript">
        var timeStamp = new Date().getTime();
        jQuery(function (){
            uploadFile("uploadFile", false, "myFile", imagePath, callbackFile);
        });

        function callbackFile(data){
            data=data.substr(2);
            data=data.substr(0,data.length-2);
            jQuery("#fileUrl").val(data).show();
            jQuery("#file").html('已上传：' + jQuery(".fileName").html());
            jQuery("#fileName").val(jQuery(".fileName").html());

        }

        function upFile(){
            jQuery("#file").html( '已上传：'+jQuery(".fileName").html());
            jQuery("fileName").val(jQuery(".fileName").html());
            jQuery("#uploadFile").uploadify('upload');
        }

        function addFormSubmit() {
            jQuery("#timeStamp").val(timeStamp);
            var issue = jQuery("#issue").children();
            var reporter = ",";
            jQuery.each(issue, function() {
                reporter += jQuery(this).attr("id").replace("issue_","") + ",";
            });
            jQuery("#reporter").val(reporter);

            var attend = jQuery("#attend").children();
            var attendPeople = ",";
            jQuery.each(attend, function() {
                attendPeople += jQuery(this).attr("id").replace("attend_","") + ",";
            });
            jQuery("#attendPeople").val(attendPeople);


            // if (jQuery("input[name='oaMeetingTopic.name']").val() == '') {
            //     alert("议题名称不能为空");
            //     return;
            // }
    //        var message = "您好，请您参加"+jQuery("input[name='oaMeetingTopic.name']").val()+"议题申请";
    //        if (jQuery("input[name='oaMeetingTopic.emergencyDegree']").val() == '') {
    //            alert("紧急程度不能为空");
    //            return;
    //        }
           /* if (jQuery("#reporter").val() == ',') {
                alert("汇报人不能为空");
                return;
            }*/
            if (jQuery("input[name='oaMeetingTopic.reporter']").val() == ',') {
                alert("汇报人不能为空");
                return;
            }

            if (jQuery("textarea[name='oaMeetingTopic.subjectContent']").val() == '') {
                alert("议题内容不能为空");
                return;
            }


            var obj = jQuery("#mobiles").children();
            var userIds = "";
            if (obj.length == 0) {
                alert("请选择分管校领导");
                return;
            }
            jQuery.each(obj, function(index , value) {
                userIds += jQuery(value).attr("id") + ",";
            });
            var params = jQuery("#saveForm").serialize();
            jQuery.ajax({
                url: '${ctx}/admin/oa/topic/process/start.json',
                data: params+ "&userIds=" + userIds.substr(0, userIds.length),
                type: 'post',
                dataType: 'json',
                success: function (result) {
                    if (result.code == "0") {
    //                    if(jQuery("#reporter").val()!=','){
    //                        sendMobileMessage(mobileNumbers, userNames, message);
    //                    }
    //                    sendMobileMessage(mobileNumbers1, userNames1, message);
                        alert(result.message);
                        window.location.href = "${ctx}/admin/oa/task/history/mine.json";
                    } else {

                    }
                }
            })
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
        function openPageOffice() {
            var url = "${ctx}/open/oaIssuesWord.json?timeStamp=" + timeStamp
            window.location.href = "javascript:POBrowser.openWindow('" + url + "','width=1200px;height=800px;')";
        }

        jQuery(function(){
            jQuery("#pobmodal-dialog").hide()
        })


        /**
         * 发送短信页面
         * 点击选择学员
         */
        function chooseNext() {
            var mobiles = jQuery('#mobiles');
            var len = mobiles.children('a').size() - 1;
            jQuery.ajax({
                url: '${ctx}/admin/oa/ajax/queryReceivers/next.json',
                type: 'POST',
                data: {'from': 2},
                dataType: 'html',
                success: function (result) {
                    jQuery.alerts._show("添加用户", result, null, 'dialog', function (data) {
                        if (!data) {
                            if (len < 0) mobiles.children('a').remove();
                            mobiles.children("a:gt(" + len + ")").remove();
                        }
                    });
                }
            });
        }

        /**
         * 点击x号  移除联系人
         * @param obj 点击的js对象
         */
        function removeNext(obj) {
            jQuery(obj).parent().remove();
        }


        /**
         * 用户列表  点击多选框
         */
        function checkNext(obj) {
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
                if(!jQuery("#"+userId).text()){
                    var element=' <a class="close" id="'+userId+'"> <i>'+userName+'</i><i class="fa fa-w c-999 fa-close" onclick="removeNext(this)"></i> </a>';
                    jQuery("#mobiles").append(element);
                }
            } else {//取消选中的移除
                var id = "#" + userId;
                jQuery(id).next().remove();
                jQuery(id).remove();
            }
        }

        function checkAllNext(obj) {
            var a = obj.checked;
            jQuery(":checkbox").attr("checked", a);
            if (a) {
                jQuery("#chooseText").text("取消全选");
                jQuery(".users").each(function () {
                    checkNext(this);
                });
            } else {
                jQuery("#chooseText").text("全部选中");
                jQuery(".users").each(function () {
                    checkNext(this);
                });
            }
        }
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">议题申请</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来议题申请<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="saveForm">
                <input type="hidden" name="oaMeetingTopic.reporter" id="reporter">
                <input type="hidden" name="oaMeetingTopic.attendPeople" id="attendPeople">
                <input type="hidden" name="oaMeetingTopic.timeStamp" id="timeStamp">
                <input type="hidden" name="type" id="type">
                <p>
                    <label><em style="color: red;">*</em>议题名称</label>
                    <span class="field">
                        <input type="text" name="oaMeetingTopic.name" class="longinput" maxlength="100">
                    </span>
                </p>
                <p>
                    <label>紧急程度</label>
                    <span class="field">
                        <input type="text" name="oaMeetingTopic.emergencyDegree" class="longinput" maxlength="48">
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
                        <textarea name="oaMeetingTopic.subjectContent" cols="30" rows="10" class="longinput"></textarea>
                    </span>
                </p>
                <%--<p>
                    <label>附件上传</label>
                    <span class="field">
                        <input type="hidden" name="oaMeetingTopic.fileUrl"  id="fileUrl" />
                                     <input type="hidden" name="oaMeetingTopic.fileName" id="fileName" />
                                     <input type="button" id="uploadFile" value="上传附件"/>
                                     <button onclick="upFile();return false;" href="javascript:void(0)" class="upload_sate submit radius2" id="upload_sate">确认上传</button>
                                     <center><h4  id="file"></h4></center>
                    </span>
                </p>--%>
                <p>
                    <label>议题文件(在线Word)</label>
                    <span class="field">
                        <a href="javascript:void(0)" onclick="openPageOffice()" class="ZL-generate">编辑文件</a>
                    </span>
                </p>
                <p class="stdformbutton buttons" style="text-align: center">
                    <span id = "mobiles"></span>
                    <button class="submit radius2" onclick="addFormSubmit();return false;" style="font-weight: 400">提交</button>
                    <a class="submit radius2" onclick = "chooseNext()" style="cursor: pointer;vertical-align: bottom;">选择下一步审批人</a>
                </p>
                <input type="hidden" name="processDefinitionId" value="${processDefinition.id}">
                <input type = "hidden" name = "oaMeetingTopic.state" id = "state" value="0">
                <input type="hidden" value="<fmt:formatDate value="${nowDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" name="oaMeetingTopic.startTime"/>
            </form>
            <br/>
        </div>
    </div>
</div>
</body>
</html>