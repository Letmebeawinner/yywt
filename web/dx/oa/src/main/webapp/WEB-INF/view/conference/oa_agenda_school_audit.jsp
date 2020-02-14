<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>常务副校长/副校长（主持工作）审批</title>
    <script type="text/javascript" src="${ctx}/static/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">

        jQuery(function () {
            laydate.skin('molv');
            laydate({
                elem: '#time',
                format: 'YYYY-MM-DD hh:mm:ss'
            });

            var userIds = '${oaMeetingAgenda.bePresent}';
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
                                        var element=' <a class="close" id="bePresent_'+userList[i].id+'"> <i>'+userList[i].userName+'</i><i class="fa fa-w c-999 fa-close" onclick="removeUser(this)"></i> </a>';
                                        jQuery("#bePresentNames").append(element);
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
            userIds = '${oaMeetingAgenda.attend}';
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
                                        jQuery("#attendNames").append(element);
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
            userIds = '${oaMeetingAgenda.absent}';
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
                                        var element=' <a class="close" id="absent_'+userList[i].id+'"> <i>'+userList[i].userName+'</i><i class="fa fa-w c-999 fa-close" onclick="removeUser(this)"></i> </a>';
                                        jQuery("#absentNames").append(element);
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
            userIds = '${oaMeetingAgenda.compere}';
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
                                        var element=' <a class="close" id="compere_'+userList[i].id+'"> <i>'+userList[i].userName+'</i><i class="fa fa-w c-999 fa-close" onclick="removeUser(this)"></i> </a>';
                                        jQuery("#compereNames").append(element);
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

            userIds = '${oaMeetingAgenda.record}';
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
                                        var element=' <a class="close" id="record_'+userList[i].id+'"> <i>'+userList[i].userName+'</i><i class="fa fa-w c-999 fa-close" onclick="removeUser(this)"></i> </a>';
                                        jQuery("#recordNames").append(element);
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

            var topicIds = '${oaMeetingAgenda.topicIds}';
            if(topicIds.length>1){
                topicIds = topicIds.substring(1,topicIds.length-1);
                if(topicIds!=''){
                    jQuery.ajax({
                        url: '/admin/oa/conference/agenda/topicList.json',
                        data: {"topicIds":topicIds},
                        type: 'post',
                        dataType: 'json',
                        success: function (result) {
                            if (result.code == "0") {
                                var topicList = result.data;
                                if(topicList!=null && topicList.length!=0){
                                    for(var i=0;i<topicList.length;i++){
                                        var element=' <button class="radius2" onclick = "topicInfo('+topicList[i].id+');return false;" id="topic_'+topicList[i].id+'"> <i>'+topicList[i].name+'</i></button><a href="javascript:void(0);"> <i class="fa fa-w c-999 fa-close" onclick="removeTopic(this)"></i> </a>';
                                        jQuery("#topicNames").append(element);
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
        });

        function addFormSubmit(flag) {
            jQuery("#audit").val(flag);
//            if (!jQuery("#topicId").val()) {
//                alert("议题不能为空");
//                return false
//            }
            if(flag==0 || flag==1){
                jQuery("#comment").val("同意");
            }else if(flag==2){
                jQuery("#comment").val("拒绝");
            }
            var year = jQuery("input[name='agenda.year']").val();
            var frequency = jQuery("input[name='agenda.frequency']").val();
            if (!jQuery(year) || !jQuery(frequency)) {
                jAlert("年次不能为空", "提示", function () {
                });
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

            var bePresentNames = jQuery("#bePresentNames").children();
            var bePresent = ",";
            jQuery.each(bePresentNames, function() {
                bePresent += jQuery(this).attr("id").replace("bePresent_","") + ",";
            });
            jQuery("#bePresent").val(bePresent);

            var attendNames = jQuery("#attendNames").children();
            var attend = ",";
            jQuery.each(attendNames, function() {
                attend += jQuery(this).attr("id").replace("attend_","") + ",";
            });
            jQuery("#attend").val(attend);

            var absentNames = jQuery("#absentNames").children();
            var absent = ",";
            jQuery.each(absentNames, function() {
                absent += jQuery(this).attr("id").replace("absent_","") + ",";
            });
            jQuery("#absent").val(absent);

            var compereNames = jQuery("#compereNames").children();
            var compere = ",";
            jQuery.each(compereNames, function () {
                compere += jQuery(this).attr("id").replace("compere_", "") + ",";
            });
            jQuery("#compere").val(compere);

            var recordNames = jQuery("#recordNames").children();
            var record = ",";
            jQuery.each(recordNames, function () {
                record += jQuery(this).attr("id").replace("record_", "") + ",";
            });
            jQuery("#record").val(record);

            var topicNames = jQuery("#topicNames button");
            var topicIds = ",";
            jQuery.each(topicNames, function() {
                topicIds += jQuery(this).attr("id").replace("topic_","") + ",";
            });
            if (topicIds == ',') {
                alert("请选择议题");
                return false
            }
            jQuery("#topicIds").val(topicIds);

            if (jQuery("input[name='agenda.compere']").val() == ',') {
                alert("主持人不能为空");
                return false
            }

            if (jQuery("input[name='agenda.bePresent']").val() == ',') {
                alert("出席不能为空");
                return false
            }
            if (jQuery("input[name='agenda.attend']").val() == ',') {
                jAlert("列席不能为空", "提示", function () {
                });
                return false
            }

            if (jQuery("input[name='agenda.record']").val() == ',') {
                jAlert("记录不能为空", "提示", function () {
                });
                return false
            }

            var params = jQuery("#saveForm").serialize();
            jQuery.ajax({
                url: '/admin/oa/agendaApplyAudit.json',
                data: params,
                type: 'post',
                dataType: 'json',
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "/admin/oa/task/to/claim/list.json";
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
            jQuery("#type").val(type);

            var bePresentNames = jQuery('#bePresentNames');
            var len2 = bePresentNames.children('a').size() - 1;

            var attendNames = jQuery('#attendNames');
            var len1 = attendNames.children('a').size() - 1;

            var absentNames = jQuery('#absentNames');
            var len3 = absentNames.children('a').size() - 1;

            var compereNames = jQuery('#compereNames');
            var len4 = compereNames.children('a').size() - 1;

            var recordNames = jQuery('#recordNames');
            var len5 = recordNames.children('a').size() - 1;
            jQuery.ajax({
                url: "${ctx}/admin/oa/ajax/queryReceivers.json",
                type: 'POST',
                data: {'from': 2},
                dataType: 'html',
                success: function (result) {
                    jQuery.alerts._show("添加用户", result, null, 'dialog', function (data) {
                        if (!data) {
                            if(type==1){
                                if (len1 < 0) attendNames.children('a').remove();
                                attendNames.children("a:gt(" + len1 + ")").remove();
                            }else if(type==2){
                                if (len2 < 0) bePresentNames.children('a').remove();
                                bePresentNames.children("a:gt(" + len2 + ")").remove();
                            }else if(type==3){
                                if (len3 < 0) absentNames.children('a').remove();
                                absentNames.children("a:gt(" + len3 + ")").remove();
                            } else if (type == 4) {
                                if (len4 < 0) compereNames.children('a').remove();
                                compereNames.children("a:gt(" + len4 + ")").remove();
                            } else if (type == 5) {
                                if (len5 < 0) recordNames.children('a').remove();
                                recordNames.children("a:gt(" + len5 + ")").remove();
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
         * 点击x号  移除议题
         * @param obj 点击的js对象
         */
        function removeTopic(obj) {
            jQuery(obj).parent().prev().remove();
            jQuery(obj).parent().remove();
//            jQuery(obj).stopPropagation()
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
                    if(!jQuery("#bePresent_"+userId).text()){
                        var element=' <a class="close" id="bePresent_'+userId+'"> <i>'+userName+'</i><i class="fa fa-w c-999 fa-close" onclick="removeUser(this)"></i> </a>';
                        jQuery("#bePresentNames").append(element);
                    }
                }else if(jQuery("#type").val()==1){
                    if(!jQuery("#attend_"+userId).text()){
                        var element=' <a class="close" id="attend_'+userId+'"> <i>'+userName+'</i><i class="fa fa-w c-999 fa-close" onclick="removeUser(this)"></i> </a>';
                        jQuery("#attendNames").append(element);
                    }
                }else if(jQuery("#type").val()==3){
                    if(!jQuery("#absent_"+userId).text()){
                        var element=' <a class="close" id="absent_'+userId+'"> <i>'+userName+'</i><i class="fa fa-w c-999 fa-close" onclick="removeUser(this)"></i> </a>';
                        jQuery("#absentNames").append(element);
                    }
                } else if (jQuery("#type").val() == 4) {
                    if (!jQuery("#compere_" + userId).text()) {
                        var element = ' <a class="close" id="compere_' + userId + '"> <i>' + userName + '</i><i class="fa fa-w c-999 fa-close" onclick="removeUser(this)"></i> </a>';
                        jQuery("#compereNames").append(element);
                    }
                } else if (jQuery("#type").val() == 5) {
                    if (!jQuery("#record_" + userId).text()) {
                        var element = ' <a class="close" id="record_' + userId + '"> <i>' + userName + '</i><i class="fa fa-w c-999 fa-close" onclick="removeUser(this)"></i> </a>';
                        jQuery("#recordNames").append(element);
                    }
                }
            } else {//取消选中的移除
                if(jQuery("#type").val()==2){
                    var id = "#bePresent_" + userId;
                    jQuery(id).remove();
                }else if(jQuery("#type").val()==1){
                    var id = "#attend_" + userId;
                    jQuery(id).remove();
                }else if(jQuery("#type").val()==3){
                    var id = "#absent_" + userId;
                    jQuery(id).remove();
                } else if (jQuery("#type").val() == 4) {
                    var id = "#compere_" + userId;
                    jQuery(id).remove();
                } else if (jQuery("#type").val() == 5) {
                    var id = "#record_" + userId;
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
         * 点击选择议题
         */
        function chooseTopic() {
            var topicNames = jQuery('#topicNames');
            var len = topicNames.children('button').size() - 1;
            jQuery.ajax({
                url: "${ctx}/admin/oa/ajax/queryMeetingTopics.json",
                type: 'POST',
                data: {},
                dataType: 'html',
                success: function (result) {
                    jQuery.alerts._show("添加议题", result, null, 'dialog', function (data) {
                        if (!data) {
                            if (len < 0){
                                topicNames.children('button').remove();
                                topicNames.children('a').remove();
                            }
                            topicNames.children("button:gt(" + len + ")").remove();
                            topicNames.children("a:gt(" + len + ")").remove();
                        }
                    });
                }
            });
        }

        /**
         * 用户列表  点击多选框
         */
        function checkTopic(obj) {
            //判断是否是全部选中了
            var bn = true;
            jQuery(".topics").each(function () {
                if (!this.checked) {
                    bn = false;
                }
            });
            if (bn) {
                jQuery(":checkbox").attr("checked", true);
            } else {
                jQuery("#checkAll").attr("checked", false);
            }

            var topicId = jQuery(obj).val();
            var topicName = jQuery("#topicName_" + topicId).text().trim();
            //添加联系人
            if(obj.checked){//选中的添加
                if(!jQuery("#topic_"+topicId).text()){
                    var element=' <button class="radius2" onclick = "topicInfo('+topicId+');return false;" id="topic_'+topicId+'"> <i>'+topicName+'</i></button><a href="javascript:void(0);"> <i class="fa fa-w c-999 fa-close" onclick="removeTopic(this)"></i> </a>';
                    jQuery("#topicNames").append(element);
                }
            } else {//取消选中的移除
                var id = "#topic_" + topicId;
                jQuery(id).next().remove();
                jQuery(id).remove();
            }
        }

        function checkAllTopic(obj) {
            var a = obj.checked;
            jQuery(":checkbox").attr("checked", a);
            if (a) {
                jQuery("#chooseText").text("取消全选");
                jQuery(".topics").each(function () {
                    checkTopic(this);
                });
            } else {
                jQuery("#chooseText").text("全部选中");
                jQuery(".topics").each(function () {
                    checkTopic(this);
                });
            }
        }

        function topicInfo(id){
            window.open("${ctx}/admin/oa/queryMeetingTopic/info.json?id="+id);
            <%--jQuery.ajax({--%>
            <%--url: "${ctx}/admin/oa/ajax/queryMeetingTopic/info.json",--%>
            <%--type: 'POST',--%>
            <%--data: {'id':id},--%>
            <%--dataType: 'html',--%>
            <%--success: function (result) {--%>
            <%--jQuery.alerts._show("查看议题", result, null, 'dialog', function (data) {--%>
            <%--});--%>
            <%--jQuery('#popup_container').css('width','600px');--%>
            <%--}--%>
            <%--});--%>
        }



        function chooseMeeting() {
            var location = jQuery('#location');
            var len = location.children('button').size() - 1;
            jQuery.ajax({
                url: "${ctx}/admin/oa/ajax/queryHqAllMeeting.json",
                type: 'POST',
                data: {},
                dataType: 'html',
                success: function (result) {
                    //console.log(result);
                    jQuery.alerts._show("会场列表", result, null, 'dialog', function (data) {
                        if (!data) {
                            if (len < 0) location.children('a').remove();
                            location.children("a:gt(" + len + ")").remove();
                        }
                    });
                }
            });
        }

        // queryHqAllMeeting.jsp
        function checkMeeting(obj) {
            var meetingId = jQuery(obj).val();
            var meetingName = jQuery("#meetingName_" + meetingId).text().trim();
            //添加联系人
            if (obj.checked) {//选中的添加
                var element = ' <a class="close" id="mePresent_' + meetingId + '"> <i>' + meetingName + '</i><i class="fa fa-w c-999 fa-close" onclick="removeMeeting(this)"></i> </a>';
                jQuery("#location").html(element);
                jQuery("#locationId").val(meetingId);
                jQuery("#locationName").val(meetingName);
                getMeetingRecordForOAById(meetingId);
            }
        }

        //查看最新会场记录
        function getMeetingRecordForOAById(id) {
            jQuery.ajax({
                url: "${ctx}/admin/oa/ajax/getMeetingRecordForOAById.json",
                type: 'POST',
                data: {'meetingId':id},
                dataType: 'json',
                success: function (result) {
                    if (result.code == "0") {
                        jQuery("#useTime").val(result.data.useTime);
                        jQuery("#turnTime").val(result.data.turnTime);
                    } else {
                        jQuery("#useTime").val("");
                        jQuery("#turnTime").val("");
                    }
                }
            });
        }

        /**
         * 点击x号  移除会场
         * @param obj 点击的js对象
         */
        function removeMeeting(obj) {
            jQuery(obj).parent().remove();
            jQuery("#locationId").val(0);
            jQuery("#locationName").val("");
        }

        jQuery(function () {
            jQuery("#locationId").val(0);
            jQuery("#locationName").val("");
        });

        /**
         * 判断日期是否在区间内，在区间内返回true，否返回false
         * @param dateString 日期字符串
         * @param startDateString 区间开始日期字符串
         * @param endDateString 区间结束日期字符串
         * @returns {Number}
         */
        function isDateBetween(dateString, startDateString, endDateString) {
            if (isEmpty(dateString)) {
                alert("dateString不能为空");
                return;
            }
            if (isEmpty(startDateString)) {
                alert("startDateString不能为空");
                return;
            }
            if (isEmpty(endDateString)) {
                alert("endDateString不能为空");
                return;
            }
            var flag = false;
            var startFlag = (dateCompare(dateString, startDateString) < 1);
            var endFlag = (dateCompare(dateString, endDateString) > -1);
            if (startFlag && endFlag) {
                flag = true;
            }
            return flag;
        };

        /**
         * 日期比较大小
         * compareDateString大于dateString，返回1；
         * 等于返回0；
         * compareDateString小于dateString，返回-1
         * @param dateString 日期
         * @param compareDateString 比较的日期
         */
        function dateCompare(dateString, compareDateString) {
            if (isEmpty(dateString)) {
                alert("dateString不能为空");
                return;
            }
            if (isEmpty(compareDateString)) {
                alert("compareDateString不能为空");
                return;
            }
            var dateTime = dateParse(dateString).getTime();
            var compareDateTime = dateParse(compareDateString).getTime();
            if (compareDateTime > dateTime) {
                return 1;
            } else if (compareDateTime == dateTime) {
                return 0;
            } else {
                return -1;
            }
        };

        /**
         * 日期解析，字符串转日期
         * @param dateString 可以为2017-02-16，2017/02/16，2017.02.16
         * @returns {Date} 返回对应的日期对象
         */
        function dateParse(dateString) {
            var SEPARATOR_BAR = "-";
            var SEPARATOR_SLASH = "/";
            var SEPARATOR_DOT = ".";
            var dateArray;
            if (dateString.indexOf(SEPARATOR_BAR) > -1) {
                dateArray = dateString.split(SEPARATOR_BAR);
            } else if (dateString.indexOf(SEPARATOR_SLASH) > -1) {
                dateArray = dateString.split(SEPARATOR_SLASH);
            } else {
                dateArray = dateString.split(SEPARATOR_DOT);
            }
            return new Date(dateArray[0], dateArray[1] - 1, dateArray[2]);
        };


    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">常务副校长/副校长（主持工作）审批</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来常务副校长/副校长（主持工作）审批<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="saveForm">
                <p>
                    <label><em style="color: red;">*</em>年次</label>
                    <span class="field">
                        <input style="width:60px;" type="text" name="agenda.year" class="longinput" value="${oaMeetingAgenda.year}"> 年第
                        <input style="width:40px;" type="text" name="agenda.frequency" class="longinput" value="${oaMeetingAgenda.frequency}"> 次
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>时间</label>
                    <span class="field">
                        <input type="text" name="agenda.time" value="<fmt:formatDate type="both" value="${oaMeetingAgenda.time}" pattern="yyyy-MM-dd HH:mm:ss"/>" id="time" class="longinput">
                    </span>
                </p>
                <input type="hidden" name="agenda.id" value="${oaMeetingAgenda.id}">
                <p>
                    <label><em style="color: red;">*</em>地点</label>
                    <span class="field">
                        <input type="text" readonly="readonly" name="agenda.location" value="${oaMeetingAgenda.location}" class="longinput">
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>主持人</label>
                    <span class="field">
                        <span id = "compereNames">&nbsp;</span>
                        <button class="radius2" onclick = "chooseUser(4);return false;">选择用户</button>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>出席</label>
                    <span class="field">
                        <span id = "bePresentNames">&nbsp;</span>
                        <button class="radius2" onclick = "chooseUser(2);return false;">选择用户</button>
                    </span>
                </p>
                <p>
                    <label>缺席</label>
                    <span class="field">
                        <span id = "absentNames">&nbsp;</span>
                        <button class="radius2" onclick = "chooseUser(3);return false;">选择用户</button>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>列席</label>
                    <span class="field">
                        <span id = "attendNames">&nbsp;</span>
                        <button class="radius2" onclick = "chooseUser(1);return false;">选择用户</button>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>记录</label>
                    <span class="field">
                       <span id = "recordNames">&nbsp;</span>
                        <button class="radius2" onclick = "chooseUser(5);return false;">选择用户</button>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>议题</label>
                    <span class="field">
                        <span id = "topicNames">&nbsp;</span>
                        <button class="radius2" onclick = "chooseTopic();return false;">选择议题</button>
                    </span>
                </p>
                <input type = "hidden" name = "agenda.topicIds" id = "topicIds" value="${oaMeetingAgenda.topicIds}">
                <input type = "hidden" name = "agenda.bePresent" id = "bePresent" value="${oaMeetingAgenda.bePresent}">
                <input type="hidden" name="agenda.attend" id = "attend" value="${oaMeetingAgenda.attend}">
                <input type="hidden" name="agenda.compere" id = "compere" value="${oaMeetingAgenda.compere}">
                <input type="hidden" name="agenda.record" id = "record" value="${oaMeetingAgenda.record}">
                <input type="hidden" name="type" id="type">
                <input type = "hidden" name = "agenda.audit" id = "audit">
                <input type = "hidden" name = "taskId" value = "${task.id}" id = "taskId">
                <input type = "hidden" name = "comment" id = "comment">
                <input type="hidden" name="agenda.location" id="locationName" value="${oaMeetingAgenda.location}">
                <input type="hidden" name="agenda.locationId" id="locationId" value="${oaMeetingAgenda.locationId}">
                <input type = "hidden" name = "agenda.processInstanceId" value = "${oaMeetingAgenda.processInstanceId}" id = "processInstanceId">
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit(1);return false;">同意</button>
                    <button class="submit radius2" onclick="addFormSubmit(2);return false;">驳回</button>
                    <button class="submit radius2" onclick="history.go(-1);return false;">返回</button>
                </p>
            </form>
            <br/>
            <jsp:include page="/WEB-INF/view/common/process_transfer.jsp"></jsp:include>
        </div>
    </div>
</div>
</body>
</html>