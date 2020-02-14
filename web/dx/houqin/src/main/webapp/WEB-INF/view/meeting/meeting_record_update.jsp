<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>修改会场</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="${basePath}/static/fonts/css/font-awesome.min.css">
    <link rel="stylesheet" href="${basePath}/static/fonts/css/font-awesome-ie7.min.css">
    <link rel="stylesheet" href="${basePath}/static/css/style.default.css" type="text/css"/>
    <!--[if lte IE 8]><script language="javascript" type="text/javascript" src="${basePath}/static/js/plugins/excanvas.min.js"></script><![endif]-->
    <!--[if IE 9]>
    <link rel="stylesheet" media="screen" href="${basePath}/static/css/style.ie9.css"/>
    <![endif]-->
    <!--[if IE 8]>
    <link rel="stylesheet" media="screen" href="${basePath}/static/css/style.ie8.css"/>
    <![endif]-->
    <link rel="stylesheet" href="${basePath}/static/css/style.contrast.css" type="text/css"/>
    <script type="text/javascript" src="${basePath}/static/js/plugins/jquery-1.7.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/js/plugins/jquery-ui-1.8.16.custom.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/js/plugins/jquery.cookie.js"></script>
    <script type="text/javascript" src="${basePath}/static/js/plugins/jquery.uniform.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/js/plugins/jquery.slimscroll.js"></script>
    <script type="text/javascript" src="${basePath}/static/js/custom/general.js"></script>
    <script type="text/javascript" src="${basePath}/static/js/plugins/headNav.js"></script>
    <script type="text/javascript" src="${basePath}/static/js/plugins/kill-ie6.js"></script>
    <script type="text/javascript" src="${basePath}/static/laydate/laydate.js"></script>
    <script type="text/javascript" src="${basePath}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript" src="${basePath}/static/js/common/base-utils.js"></script>
    <script type="text/javascript" src="${basePath}/static/js/common/authority.js"></script>
    <script type="text/javascript" src="${basePath}/static/js/common/utils.js"></script>
    <!--[if lt IE 9]>
    <script src="${basePath}/static/js/plugins/css3-mediaqueries.js"></script>
    <![endif]-->
    <script type="text/javascript">
        jQuery(function () {
            laydate.skin('molv');
            laydate({
                elem: '#turnTime',
                format: 'YYYY-MM-DD hh:mm:ss',
                istime: true
            });
            laydate({
                elem: '#useTime',
                format: 'YYYY-MM-DD hh:mm:ss',
                istime: true
            });
        });

        function addFormSubmit() {
            var startTime = jQuery("#useTime").val();
            var endTime = jQuery("#turnTime").val();
            if (!startTime) {
                alert("开始时间不能为空");
                return;
            }
            if (!endTime) {
                alert("结束时间不能为空");
                return;
            }
            if (startTime && endTime) {
                var startTimeMill = Date.parse(new Date(startTime));
                var endTimeMill = Date.parse(new Date(endTime));
                if (endTimeMill <= startTimeMill) {
                    alert("开始时间必须大于当前时间");
                    return;
                }
            }
            if(endTime){
                var now =Date.parse(new Date());
                var endTimeMill = Date.parse(new Date(endTime));
                if(endTimeMill<now){
                    alert("结束时间必须大于当前时间");
                    return;
                }
            }
            jQuery.ajax({
                url: "${ctx}/admin/houqin/updateApplyMeeting.json",
                data: jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == "0") {
                        if (result.data == 0) {
                            alert(result.message);
                            window.location.href = "${ctx}/admin/houqin/toApplyMeeting.json";
                        } else if (result.data == 1) {
                            alert("会议时间和之前安排过的会议时间冲突");
                            return;
                        }
                    } else {
                        alert(result.message);
                        return;
                    }
                }
            });
        }

    </script>
</head>
<body>
<div class="header">
    <%--<div class="left">--%>
        <%--<h1 class="logo">--%>
            <%--<a href="">&nbsp;</a>--%>
        <%--</h1>--%>
    <%--</div><!--left-->--%>
    <div class="header-nav">
        <div class="top-nav-big">
            <div class="top-nav-list"></div>
        </div>
    </div>
    <div class="right">
        <div class="userinfo">
            <span class="vam user-name-h"></span>
            <i class="fa fa-angle-double-down fa-fw vam"></i>
        </div><!--userinfo-->
        <div class="userinfodrop">
            <div class="userdata" style="margin-left: 0px;">
                <h4 class="user-name-h"></h4>
                <span class="email" id="user-email"></span>
                <ul>
                    <li><a href="${basePath}/admin/index.json">返回切换子系统</a></li>
                    <li><a href="javascript:goToMessage('${basePath}','${smsPath}');">消息</a></li>
                    <li><a href="${basePath}/admin/base/sysuser/toUpdateThisSysUser.json">账号设置</a></li>
                    <li><a href="${basePath}/admin/outLogin.json">退出</a></li>
                </ul>
            </div><!--userdata-->
        </div><!--userinfodrop-->
    </div><!--right-->
    <div class="clear"></div>
</div><!--header-->

<%--<div class="vernav2 iconmenu" style="display: none;">--%>
    <%--<a  style="display: none;" id="show_authority_list" class="togglemenu"></a>--%>
    <%--<br /><br />--%>
<%--</div><!--leftmenu-->--%>

<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">修改会场</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <input type="hidden" name="meetingRecord.id" value="${meetingRecord.id}">
                    <input type="hidden" name="meetingRecord.meetingId" value="${meetingRecord.meetingId}">
                    <label><em style="color: red;">*</em>会场名称</label>
                    <span class="field">
                        <input type="text" name="meeting.name" class="longinput" id="name" value="${meeting.name}" readonly/>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>会议开始时间</label>
                    <span class="field">
                        <input type="text" name="meetingRecord.useTime" class="longinput" id="useTime" readonly
                               value='<fmt:formatDate value="${meetingRecord.useTime}" pattern="yyyy-MM-dd HH:mm:ss"/>'/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>会议结束时间</label>
                    <span class="field">
                        <input type="text" name="meetingRecord.turnTime" class="longinput" id="turnTime" readonly
                               value='<fmt:formatDate value="${meetingRecord.turnTime}" pattern="yyyy-MM-dd HH:mm:ss "/>'/>
                    </span>
                </p>
                <p class="stdformbutton center">
                    <button class="submit radius2" onclick="addFormSubmit();return false;">提交保存</button>
                </p>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    var basePath = '${basePath}';
    var mydomain = "";
    querySysUserInfo();
</script>
</body>
</html>