<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>编辑议程</title>
    <script type="text/javascript" src="${ctx}/static/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">

        jQuery(function () {
            laydate.skin('molv');
            laydate({
                elem: '#time',
                format: 'YYYY-MM-DD hh:mm:ss'
            });

            var bePresent = '${agenda.bePresent}';
            if(bePresent.length>1){
                bePresent = bePresent.substring(1,bePresent.length-1);
            }
            if(bePresent!=','){
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
                                    var element=' <a class="close" id="'+userList[i].id+'"> <i>'+userList[i].userName+'</i><i class="fa fa-w c-999 fa-close" onclick="removeUser(this)"></i> </a>';
                                    element += "<input name = 'mobile' type = 'hidden' value = " + userList[i].mobile + ">"
                                    jQuery("#mobiles").append(element);
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
            var attend = '${agenda.attend}';
            if(attend.length>1){
                attend = attend.substring(1,attend.length-1);
            }
            if(attend!=","){
                jQuery.ajax({
                    url: '/admin/oa/conference/agenda/userById.json',
                    data: {"bePresent":bePresent,"attend":attend},
                    type: 'post',
                    dataType: 'json',
                    success: function (result) {
                        if (result.code == "0") {
                            var subList1 = result.data.bePresent;
                            var subList2 = result.data.attend;
                            if(subList1!=null && subList1.length!=0){
                                for(var i=0;i<subList1.length;i++){
                                    var element=' <a class="close" id="'+subList1[i].id+'"> <i>'+subList1[i].userName+'</i><i class="fa fa-w c-999 fa-close" onclick="removeUser(this)"></i> </a>';
                                    element += "<input name = 'mobile' type = 'hidden' value = " + subList1[i].mobile + ">"
                                    jQuery("#mobiles").append(element);
                                }
                            }
                            if(subList2!=null && subList2.length!=0){
                                for(var i=0;i<subList2.length;i++){
                                    var element1=' <a class="close" id="'+subList2[i].id+'"> <i>'+subList2[i].userName+'</i><i class="fa fa-w c-999 fa-close" onclick="removeUser(this)"></i> </a>';
                                    element1 += "<input name = 'mobile1' type = 'hidden' value = " + subList2[i].mobile + ">"
                                    jQuery("#liexi").append(element1);
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
        });

        function addAgenda() {
            if (!jQuery("#topicId").val()) {
                alert("议题不能为空");
                return false
            }

            if (jQuery("input[name='agenda.time']").val() == '') {
                alert("时间不能为空");
                return false
            }

            if (jQuery("input[name='agenda.location']").val() == '') {
                alert("地点不能为空");
                return false;
            }

            if (jQuery("input[name='agenda.compere']").val() == '') {
                alert("主持人不能为空");
                return false
            }

            var mobiles = jQuery("input[name='mobile']");
            var mobileNumbers = [];
            var userNames = [];
            var bePresent = ",";
            jQuery.each(mobiles, function() {
                mobileNumbers.push(jQuery(this).val());
                bePresent += jQuery(this).prev().attr("id") + ",";
                userNames.push(jQuery(this).prev().children().text());
            });
            jQuery("#bePresent").val(bePresent);

            var mobiles1 = jQuery("input[name='mobile1']");
            var mobileNumbers1 = [];
            var userNames1 = [];
            var attend = ",";
            jQuery.each(mobiles1, function() {
                mobileNumbers1.push(jQuery(this).val());
                attend += jQuery(this).prev().attr("id") + ",";
                userNames1.push(jQuery(this).prev().children().text());
            });
            jQuery("#attend").val(attend);

            if (jQuery("input[name='agenda.bePresent']").val() == ',') {
                alert("出席不能为空");
                return false
            }
            if (jQuery("input[name='agenda.attend']").val() == ',') {
                jAlert("列席不能为空", "提示", function () {
                });
                return false
            }

            if (jQuery("input[name='agenda.record']").val() == '') {
                jAlert("记录不能为空", "提示", function () {
                });
                return false
            }

            var params = jQuery("#saveForm").serialize();
            jQuery.ajax({
                url: '/admin/oa/conference/agenda/update.json',
                data: params,
                type: 'post',
                dataType: 'json',
                success: function (result) {
                    if (result.code == "0") {
                        window.location.href = "/admin/oa/conference/agenda/list.json";
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



        function chooseUser(type) {
            var url = "";
            jQuery("#type").val(type);
            if(type==1){
                url = "${ctx}/admin/oa/ajax/queryReceivers.json?type=1";
            }else{
                url = "${ctx}/admin/oa/ajax/queryReceivers.json";
            }
            var mobiles = jQuery('#mobiles');
            var len = mobiles.children('a').size() - 1;

            var mobiles1 = jQuery('#mobiles');
            var len1 = mobiles1.children('a').size() - 1;
            jQuery.ajax({
                url: url,
                type: 'POST',
                data: {'from': 2},
                dataType: 'html',
                success: function (result) {
                    jQuery.alerts._show("添加用户", result, null, 'dialog', function (data) {
                        if (!data) {
                            if(type==1){
                                if (len1 < 0) mobiles1.children('a').remove();
                                mobiles1.children("a:gt(" + len1 + ")").remove();
                            }
                            if(type==2){
                                if (len < 0) mobiles.children('a').remove();
                                mobiles.children("a:gt(" + len + ")").remove();
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
            jQuery(obj).parent().next().remove();
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
            var mobile = jQuery("#mobile_" + userId).text().trim();
            var userName = jQuery("#username_" + userId).text().trim();
            //添加联系人
            if(obj.checked){//选中的添加
                if(!jQuery("#"+userId).text()){
                    if(jQuery("#type").val()==2){
                        var element=' <a class="close" id="'+userId+'"> <i>'+userName+'</i><i class="fa fa-w c-999 fa-close" onclick="removeUser(this)"></i> </a>';
                        element += "<input name = 'mobile' type = 'hidden' value = " + mobile + ">"
                        jQuery("#mobiles").append(element);
                    }
                    if(jQuery("#type").val()==1){
                        var element=' <a class="close" id="'+userId+'"> <i>'+userName+'</i><i class="fa fa-w c-999 fa-close" onclick="removeUser(this)"></i> </a>';
                        element += "<input name = 'mobile1' type = 'hidden' value = " + mobile + ">"
                        jQuery("#liexi").append(element);
                    }
                }
            } else {//取消选中的移除
                var id = "#" + userId;
                jQuery(id).next().remove();
                jQuery(id).remove();
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
         * 手机号，发送消息
         * @param mobile
         * @param message
         */
        function sendMobileMessage(mobile, userNames, message) {
            console.log(userNames);
            console.log(mobile);
            jQuery.ajax({
                url: "${ctx}/admin/oa/ajax/mobile/sendMessages.json",
                data: {
                    "mobiles": mobile,
                    "message": message,
                    "userNames": userNames
                },
                dataType: "json",
                type: "post",
                cache: false,
                success: function(result) {

                }

            });
        }
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">编辑议程</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来编辑议程<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="saveForm">
                <p>
                    <label><em style="color: red;">*</em>议题</label>
                    <input type="hidden" name="oldTopicId" value="${agenda.topicId}">
                    <span class="field">
                        <select name = "agenda.topicId" id = "topicId">
                            <option value = "">没有选择议题</option>
                            <c:forEach items="${oaMeetingTopics}" var="oaMeetingTopic">
                                <option value = "${oaMeetingTopic.id}" <c:if test = "${oaMeetingTopic.id == agenda.topicId}"> selected</c:if>>${oaMeetingTopic.name}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>时间</label>
                    <span class="field">
                        <input type="text" name="agenda.time" value="<fmt:formatDate type="both" value="${agenda.time}" pattern="yyyy-MM-dd HH:mm:ss"/>" id="time" class="longinput">
                    </span>
                </p>
                <input type="hidden" name="agenda.id" value="${agenda.id}">
                <p>
                    <label><em style="color: red;">*</em>地点</label>
                    <span class="field">
                        <input type="text" name="agenda.location" value="${agenda.location}" class="longinput">
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>主持人</label>
                    <span class="field">
                        <input type="text" name="agenda.compere" value="${agenda.compere}" class="longinput">
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>出席</label>
                    <span class="field">
                        <span id = "mobiles"></span>
                        <button class="radius2" onclick = "chooseUser(2);return false;">选择用户</button>
                    </span>
                </p>
                <p>
                    <label>缺席</label>
                    <span class="field">
                        <input type="text" name="agenda.absent" value="${agenda.absent}" class="longinput">
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>列席</label>
                    <span class="field">
                        <span id = "liexi"></span>
                        <button class="radius2" onclick = "chooseUser(1);return false;">选择用户</button>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>记录</label>
                    <span class="field">
                        <textarea name="agenda.record" rows="10" cols="5" class="longinput">${agenda.record}</textarea>
                    </span>
                </p>
                <input type = "hidden" name = "agenda.bePresent" id = "bePresent" value="${agenda.bePresent}">
                <input type="hidden" name="agenda.attend" id = "attend" value="${agenda.attend}">
                <input type="hidden" name="type" id="type">
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addAgenda();return false;">保 存</button>
                </p>
            </form>
            <br/>
        </div>
    </div>
</div>
</body>
</html>