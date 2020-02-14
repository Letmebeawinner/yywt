<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>新建议程</title>
    <script type="text/javascript" src="${ctx}/static/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">

        jQuery(function () {
            laydate.skin('molv');
            laydate({
                elem: '#time',
                format: 'YYYY-MM-DD hh:mm:ss',
                istime: true
            });
        });

        function addAgenda() {
            if (!jQuery("#topicId").val()) {
                jAlert("议题不能为空", "提示", function () {
                });
                return false;
            }
            var time = jQuery("input[name='agenda.time']").val();
            if (!jQuery(time)) {
                jAlert("时间不能为空", "提示", function () {
                });
                return false
            }
            var address = jQuery("input[name='agenda.location']").val();
            if (!address) {
                jAlert("地点不能为空", "提示", function () {
                });
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
            var message = "您好，请在" + time + "在" + address + "参加会议";
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

            var params = jQuery("#saveForm").serialize();
            if (jQuery("input[name='agenda.compere']").val() == '') {
                jAlert("主持人不能为空", "提示", function () {
                });
                return false
            }
            if (jQuery("input[name='agenda.bePresent']").val() == ',') {
                jAlert("出席不能为空", "提示", function () {
                });
                return false
            }

            if (jQuery("input[name='agenda.absent']").val() == '') {
                jAlert("缺席不能为空", "提示", function () {
                });
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

            if (jQuery("input[name='agenda.agendaName']").val() == '') {
                jAlert("议题不能为空", "提示", function () {
                });
                return false
            }

            jQuery.ajax({
                url: '/admin/oa/conference/agenda/save.json',
                data: params,
                type: 'post',
                dataType: 'json',
                async: false,
                success: function (result) {
                    if (result.code == "0") {
                        sendMobileMessage(mobileNumbers, userNames, message);
                        sendMobileMessage(mobileNumbers1, userNames1, message);
                        alert("操作成功");
                        window.location.href = "${ctx}/admin/oa/conference/agenda/list.json";
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

        /**
         * 发送短信页面
         * 点击选择学员
         */
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
        <h1 class="pagetitle">新建议程</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来新建议程<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="saveForm">
                <p>
                    <label><em style="color: red;">*</em>议题</label>
                    <span class="field">
                        <select name = "agenda.topicId" id = "topicId">
                            <c:forEach items="${oaMeetingTopics}" var = "oaMeetingTopic">
                                <option value = "${oaMeetingTopic.id}">${oaMeetingTopic.name}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>时间</label>
                    <span class="field">
                        <input type="text" name="agenda.time" id="time" class="longinput" readonly="readonly">
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>地点</label>
                    <span class="field">
                        <input type="text" name="agenda.location" class="longinput">
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>主持人</label>
                    <span class="field">
                        <input type="text" name="agenda.compere" class="longinput">
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
                        <input type="text" name="agenda.absent" class="longinput">
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
                        <textarea name="agenda.record" rows="10" cols="5" class="longinput"
                                  style="text-align: left"></textarea>
                    </span>
                </p>
                <input type = "hidden" name = "agenda.bePresent" id = "bePresent">
                <input type="hidden" name="agenda.attend" id = "attend">
                <input type="hidden" name="type" id="type">
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addAgenda();return false;">提 交</button>
                </p>
            </form>
            <br/>
        </div>
    </div>
</div>
</body>
</html>