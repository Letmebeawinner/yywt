<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>校委会会议议程</title>

    <script type="text/javascript" src="${ctx}/static/js/oa_select_options.js"></script>
    <script type="text/javascript">
        var userIds = '${oaMeetingAgenda.bePresent}';
        if (userIds.length > 1) {
            userIds = userIds.substring(1, userIds.length - 1);
            if (userIds != '') {
                jQuery.ajax({
                    url: '/admin/oa/conference/agenda/userList.json',
                    data: {"userIds": userIds},
                    type: 'post',
                    dataType: 'json',
                    success: function (result) {
                        if (result.code == "0") {
                            var userList = result.data;
                            if (userList != null && userList.length != 0) {
                                for (var i = 0; i < userList.length; i++) {
                                    if (i == userList.length - 1) {
                                        var element = userList[i].userName;
                                        jQuery("#bePresentNames").append(element);
                                    } else {
                                        var element = userList[i].userName + '、';
                                        jQuery("#bePresentNames").append(element);
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
        userIds = '${oaMeetingAgenda.attend}';
        if (userIds.length > 1) {
            userIds = userIds.substring(1, userIds.length - 1);
            if (userIds != '') {
                jQuery.ajax({
                    url: '/admin/oa/conference/agenda/userList.json',
                    data: {"userIds": userIds},
                    type: 'post',
                    dataType: 'json',
                    success: function (result) {
                        if (result.code == "0") {
                            var userList = result.data;
                            if (userList != null && userList.length != 0) {
                                for (var i = 0; i < userList.length; i++) {
                                    if (i == userList.length - 1) {
                                        var element = userList[i].userName;
                                        jQuery("#attendNames").append(element);
                                    } else {
                                        var element = userList[i].userName + '、';
                                        jQuery("#attendNames").append(element);
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
        userIds = '${oaMeetingAgenda.absent}';
        if (userIds.length > 1) {
            userIds = userIds.substring(1, userIds.length - 1);
            if (userIds != '') {
                jQuery.ajax({
                    url: '/admin/oa/conference/agenda/userList.json',
                    data: {"userIds": userIds},
                    type: 'post',
                    dataType: 'json',
                    success: function (result) {
                        if (result.code == "0") {
                            var userList = result.data;
                            if (userList != null && userList.length != 0) {
                                for (var i = 0; i < userList.length; i++) {
                                    if (i == userList.length - 1) {
                                        var element = userList[i].userName;
                                        jQuery("#absentNames").append(element);
                                    } else {
                                        var element = userList[i].userName + '、';
                                        jQuery("#absentNames").append(element);
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
        userIds = '${oaMeetingAgenda.compere}';
        if (userIds.length > 1) {
            userIds = userIds.substring(1, userIds.length - 1);
            if (userIds != '') {
                jQuery.ajax({
                    url: '/admin/oa/conference/agenda/userList.json',
                    data: {"userIds": userIds},
                    type: 'post',
                    dataType: 'json',
                    success: function (result) {
                        if (result.code == "0") {
                            var userList = result.data;
                            if (userList != null && userList.length != 0) {
                                for (var i = 0; i < userList.length; i++) {
                                    if (i == userList.length - 1) {
                                        var element = userList[i].userName;
                                        jQuery("#compereNames").append(element);
                                    } else {
                                        var element = userList[i].userName + '、';
                                        jQuery("#compereNames").append(element);
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
        userIds = '${oaMeetingAgenda.record}';
        if (userIds.length > 1) {
            userIds = userIds.substring(1, userIds.length - 1);
            if (userIds != '') {
                jQuery.ajax({
                    url: '/admin/oa/conference/agenda/userList.json',
                    data: {"userIds": userIds},
                    type: 'post',
                    dataType: 'json',
                    success: function (result) {
                        if (result.code == "0") {
                            var userList = result.data;
                            if (userList != null && userList.length != 0) {
                                for (var i = 0; i < userList.length; i++) {
                                    if (i == userList.length - 1) {
                                        var element = userList[i].userName;
                                        jQuery("#recordNames").append(element);
                                    } else {
                                        var element = userList[i].userName + '、';
                                        jQuery("#recordNames").append(element);
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

        var topicIds = '${oaMeetingAgenda.topicIds}';
        var agendaId = '${oaMeetingAgenda.id}';
        if (topicIds.length > 1) {
            topicIds = topicIds.substring(1, topicIds.length - 1);
            if (topicIds != '') {
                // jQuery.ajax({
                //     url: '/admin/oa/conference/agenda/topicList.json',
                //     data: {"topicIds": topicIds},
                //     type: 'post',
                //     dataType: 'json',
                //     success: function (result) {
                //         if (result.code == "0") {
                //             var topicList = result.data;
                //             if (topicList != null && topicList.length != 0) {
                //                 for (var i = 0; i < topicList.length; i++) {
                //                     //if(i==topicList.length-1){
                //                     var element = '<button class="radius2" onclick = "topicInfo(' + topicList[i].id + ');return false;">' + topicList[i].name + '</button>&nbsp;&nbsp;&nbsp;&nbsp;'
                //                     //var element=topicList[i].name;
                //                     //jQuery("#topicNames").append(element);
                //                     //}else {
                //                     //var element=topicList[i].name+"、";
                //                     //jQuery("#topicNames").append(element);
                //                     //}
                //                 }
                //             }
                //         } else {
                //             jAlert(result.message, '提示', function () {
                //             });
                //         }
                //     },
                //     error: function (e) {
                //         jAlert('添加失败', '提示', function () {
                //         });
                //     }
                // });
                showSelectedTopicListForPrint(topicIds,agendaId);
            }
        }

        function showSelectedTopicListForPrint(topicIds,agendaId) {
            jQuery.ajax({
                url: "${ctx}/admin/oa/ajax/showSelectedTopicListForPrint.json",
                type: 'POST',
                data: {'topicIds': topicIds,"agendaId":agendaId},
                dataType: 'html',
                success: function (result) {
                    jQuery("#topicNames").html(result);
                }
            });
        }

        jQuery(function () {
            var time = jQuery("#time").val();
            jQuery.ajax({
                url: "${ctx}/admin/oa/ajax/getWeekOfDate.json",
                type: 'POST',
                data: {'time': time},
                dataType: 'json',
                success: function (result) {
                    if(result.code=="0"){
                        jQuery("#fmortTime").html(result.data);
                    }
                }
            });
        });

        function topicInfo(id) {
            jQuery.ajax({
                url: "${ctx}/admin/oa/ajax/queryMeetingTopic/info.json",
                type: 'POST',
                data: {'id': id},
                dataType: 'html',
                success: function (result) {
                    jQuery.alerts._show("查看议题", result, null, 'dialog', function (data) {
                    });
                    jQuery('#popup_container').css('width', '600px');
                }
            });
        }

        function printDocument() {
            jQuery("#print").hide();
            jQuery("#auditStatus").hide();
            jQuery(".printHide").hide();
            jQuery(".header").hide();
            jQuery(".iconmenu").hide();
            jQuery(".centercontent").css("marginLeft", "0");
            jQuery("textarea").css("min-height", "102px");
            jQuery("td").css("border", "1px solid #999");
            // return;
            window.print();
            jQuery("#print").show();
            jQuery("#auditStatus").show();
            jQuery(".printHide").show();
            jQuery(".header").show();
            jQuery(".iconmenu").show();
            jQuery(".centercontent").css("marginLeft", "181px");
            jQuery("td").css("border", "1px solid #ddd");
            jQuery("textarea").css("height", "100%");
        }
    </script>
    <style>
        .pageheader h1.page-list{font-weight: bold;color: #333;font-size: 30px;}
        .pageheader h2.page-list{font-size: 20px;}
        .stdform2 span.field,.stdform2 p,.stdform2 p:first-child{border: none;}
        .stdform label{width: 140px;letter-spacing: 10px;font-size: 20px;text-align: center;padding-right: 0;color: #333;font-weight: 600;}
        .stdform2 span.field{margin-left: 140px;font-size: 18px;padding-left: 0;}
        .stdform2 p{background: transparent;}
        .stdform2 p.yt_wrap{padding-left: 50px;}
        .stdform2 .yt_box p{padding: 10px 0;font-size: 18px;}
    </style>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="background: #fff;">
        <h1 class="page-list tac">
            校委会会议议程
        </h1>
    </div>
    <div class="pageheader" style="background: #fff;">
        <h2 class="page-list tac">
            （${oaMeetingAgenda.year}年第${oaMeetingAgenda.frequency}次）
        </h2>
    </div>
    <div id="print">
        <button type="button" onclick="printDocument()" style="margin-left: 21px;cursor: pointer" class="printHide">打印
        </button>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="saveForm">
                <%--<p>
                    <label>年次</label>
                    <span class="field">
                        ${oaMeetingAgenda.year}年第${oaMeetingAgenda.frequency}次
                    </span>
                </p>--%>

                <p>
                    <input type="hidden" id="time" value="<fmt:formatDate value="${oaMeetingAgenda.time}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>">
                    <label>时&nbsp;&nbsp;间：</label>
                    <span class="field" id="fmortTime">
                        <fmt:formatDate value="${oaMeetingAgenda.time}" pattern="yyyy年MM月dd日"></fmt:formatDate>&nbsp;&nbsp;&nbsp;&nbsp;
                        <fmt:formatDate value="${oaMeetingAgenda.time}" pattern="ahh:mm"></fmt:formatDate>
                    </span>
                </p>
                <p>
                    <label>地&nbsp;&nbsp;点：</label>
                    <span class="field">
                        ${oaMeetingAgenda.location}&nbsp;
                    </span>
                </p>
                <p>
                    <label>主持人：</label>
                    <span class="field">
                        <%--${oaMeetingAgenda.compere}&nbsp;--%>
                        <span id="compereNames">&nbsp;</span>
                    </span>
                </p>
                <p>
                    <label>出&nbsp;&nbsp;席：</label>
                    <span class="field">
                        <span id="bePresentNames">&nbsp;</span>
                    </span>
                </p>
                <p>
                    <label>缺&nbsp;&nbsp;席：</label>
                    <span class="field">
                        <span id="absentNames">&nbsp;</span>
                    </span>
                </p>
                <p>
                    <label>列&nbsp;&nbsp;席：</label>
                    <span class="field">
                        <span id="attendNames">&nbsp;</span>
                    </span>
                </p>
                <p>
                    <label>记&nbsp;&nbsp;录：</label>
                    <span class="field">
                        <%--${oaMeetingAgenda.record}&nbsp;--%>
                        <span id="recordNames">&nbsp;</span>
                    </span>
                </p>
                <p>
                    <label>议&nbsp;&nbsp;题：</label>
                    <span class="field">&nbsp;</span>
                </p>
                <p class="yt_wrap">
                    <span id="topicNames">&nbsp;</span>
                </p>
                <p id="auditStatus">
                    <label>审核状态</label>
                    <span class="field">
                        <c:if test="${oaMeetingAgenda.audit == 1}">
                            审批已通过
                        </c:if>
                        <c:if test="${oaMeetingAgenda.audit == 0}">
                            审核中
                        </c:if>
                        <c:if test="${oaMeetingAgenda.audit == 2}">
                            已拒绝
                        </c:if>
                    </span>
                </p>
                <p class="stdformbutton printHide" style="text-align: center">
                    <button class="submit radius2" onclick="history.go(-1);return false;">返回</button>
                </p>
                <input type="hidden" name="taskId" value="${task.id}" id="taskId">
            </form>
            <br/>
        </div>
    </div>
    <c:if test="${hisProcessEntities!=null}">
        <jsp:include page="/WEB-INF/view/common/process_transfer.jsp"></jsp:include>
    </c:if>
</div>
</body>
</html>