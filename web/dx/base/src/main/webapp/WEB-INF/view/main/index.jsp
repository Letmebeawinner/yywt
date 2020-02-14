<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<%@ page import="com.a_268.base.constants.BaseCommonConstants" %>
<c:set var="basePath" value="<%=BaseCommonConstants.basePath%>"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <title>智慧校园管理平台中心</title>
    <link rel="stylesheet" href="${ctx}/static/fonts/css/font-awesome.min.css">
    <link rel="stylesheet" href="${ctx}/static/fonts/css/font-awesome-ie7.min.css">
    <link rel="stylesheet" href="${ctx}/static/css/style.default.css" type="text/css"/>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery-1.7.min.js"></script>
    <script type="text/javascript" src="/pageoffice.js" id="po_js_main"></script>
    <!--[if lte IE 8]>
    <script language="javascript" type="text/javascript" src="${ctx}/static/js/plugins/excanvas.min.js"></script>
    <![endif]-->
    <!--[if IE 9]>
    <link rel="stylesheet" media="screen" href="${ctx}/static/css/style.ie9.css"/>
    <![endif]-->
    <!--[if IE 8]>
    <link rel="stylesheet" media="screen" href="${ctx}/static/css/style.ie8.css"/>
    <![endif]-->
    <link rel="stylesheet" href="${ctx}/static/css/style.contrast.css" type="text/css">
    <style>
        html,body{overflow: hidden;}
        .shortcuts-bx .shortcuts li{min-width: 180px;}
        html,body,.bodywrapper{height: 100%;}
    </style>
</head>

<body class="">
<div class="bodywrapper">
    <div class="header">
        <div class="left">
            <h1 class="logo">
                <a href="">&nbsp;</a>
            </h1>
        </div><!--left-->
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
                <div class="userdata" style="margin-left:0px;">
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
    <%--<div>--%>
        <div class="pageheader indexHeader">
            <h1 class="pagetitle"><span>您好，</span>欢迎来到中共贵阳市委党校管理平台</h1>
            <div style="font-size: 16px;margin-right: 10px  ;width: 20%;float: right; text-align: right">
                <div>
                    <a href="http://10.100.101.1:6695/upload/贵阳市委党校智慧校园使用手册.zip" target="_blank">(贵阳市委党校智慧校园使用手册)</a>
                </div>

            </div>
            <div class="nei-wang-tips">
                <a href="http://10.100.101.1:6690" target="_blank" style="color: #c80000;">中共贵阳市委党校内网门户网站</a>
            </div>
            <%--<div style="clear: both"></div>--%>
            <ul class="hornav">
                <li class="current"><a href="#updates">各子系统快捷入口面板</a></li>
                <c:if test="${sysUser.userType==1 || sysUser.userType==2 || sysUser.userType==3}">
                    <li><a href="#process">待办事项<span id="waitNum" class="nav-hint"></span></a></li>
                </c:if>
            </ul>
        </div><!--pageheader-->
        <div class="panel-bg">
            <div id="contentwrapper" class="contentwrapper index_content">
                <section class="index-content__wrapper">
                    <div id="updates" class="subcontent">
                        <div class="shortcuts-bx">
                            <div class="clear"></div>
                            <ul class="shortcuts">

                                <li class="base" style="display: none">
                                    <a href="javascript:void(0);" onclick="switchSystem('BASE')" >
                                        <i class="fa fa-th-list fa-fw fl"></i>
                                        <div class="shortcut-number">
                                            <big class="hLh20">基础管理系统</big>
                                            <small class="hLh20">实现账号的创建、角色分配及权限管控管理</small>
                                        </div>
                                    </a>
                                </li>
                                <li  class="users" style="display: none">
                                    <a href="javascript:void(0);" onclick="switchSystem('XY')">
                                        <i class="fa fa-user fa-fw fl"></i>
                                        <div class="shortcut-number">
                                            <big>学员管理系统</big>
                                            <small class="hLh20">主要对在线学员报名信息的管理</small>
                                        </div>
                                    </a>
                                </li>
                                <li class="jiaowu" style="display: none">
                                    <a href="javascript:void(0);" onclick="switchSystem('JW')" >
                                        <i class="fa fa-wrench fa-fw fl"></i>
                                        <div class="shortcut-number">
                                            <big>教务管理系统</big>
                                            <small class="hLh20">进行开班、排课等教务工作管理</small>
                                        </div>
                                    </a>
                                </li>
                                <li class="keyan" style="display: none">
                                    <a href="javascript:void(0);" onclick="switchSystem('KY')" >
                                        <i class="fa fa-flask fa-fw fl"></i>
                                        <div class="shortcut-number">
                                            <big>科研管理系统</big>
                                            <small class="hLh20">主要进行科研课题的申报，成果信息登记</small>
                                        </div>
                                    </a>
                                </li>
                                <li class="shengtai" style="display: none">
                                    <a href="javascript:void(0);" onclick="switchSystem('ZZ')" >
                                        <i class="fa fa-tree fa-fw fl"></i>
                                        <div class="shortcut-number">
                                            <big>生态文明研究所</big>
                                            <small class="hLh20">主要进行月度/年度课题申报处理等管理</small>
                                        </div>
                                    </a>
                                </li>
                                <li class="renshi" style="display: none">
                                    <a href="javascript:void(0);" onclick="switchSystem('HR')" >
                                        <i class="fa fa-group fa-fw fl"></i>
                                        <div class="shortcut-number">
                                            <big>人事管理系统</big>
                                            <small class="hLh20">便于部门组织架构维护，快速管理人员信息</small>
                                        </div>
                                    </a>
                                </li>
                                <li class="houqin" style="display: none">
                                    <a href="javascript:void(0);" onclick="switchSystem('HQ')" >
                                        <i class="fa fa-cog fa-fw fl"></i>
                                        <div class="shortcut-number">
                                            <big>后勤管理系统</big>
                                            <small class="hLh20">对日常后勤的数字化管理，可进行多维度数据对比</small>
                                        </div>
                                    </a>
                                </li>
                                <li class="bangong" style="display: none">
                                    <a href="javascript:void(0);" onclick="switchSystem('OA')" >
                                        <i class="fa fa-filter fa-fw fl"></i>
                                        <div class="shortcut-number">
                                            <big>办公OA系统</big>
                                            <small class="hLh20">无纸化办公，提高工作效率，痕迹保留</small>
                                        </div>
                                    </a>
                                </li>
                                <li class="zaixianxuexi" style="display: none">
                                    <a href="javascript:void(0)" onclick="switchSystem('ZXXY')" >
                                        <i class="fa fa-book fa-fw fl"></i>
                                        <div class="shortcut-number">
                                            <big>在线学习系统</big>
                                            <small class="hLh20">通过网络学习， 掌握学习进度</small>
                                        </div>
                                    </a>
                                </li>
                                <li class="menhu" style="display: none">
                                    <a href="javascript:void(0);" onclick="switchSystem('MH')" >
                                        <i class="fa fa-road fa-fw fl"></i>
                                        <div class="shortcut-number">
                                            <big>校园内网门户系统</big>
                                            <small class="hLh20">发布校园信息，快速浏览</small>
                                        </div>
                                    </a>
                                </li>
                                <li class="information" style="display: none">
                                    <a href="javascript:void(0);" onclick="switchSystem('SMS')" >
                                        <i class="fa fa-envelope-o fa-fw fl"></i>
                                        <div class="shortcut-number">
                                            <big>短信平台/消息系统</big>
                                            <small class="hLh20">发送短信/消息，实时保持消息互通</small>
                                        </div>
                                    </a>
                                </li>
                                <li class="statistic" style="display: none">
                                    <a href="javascript:void(0);" onclick="switchSystem('TJFX')" >
                                        <i class="fa fa-bar-chart fa-fw fl"></i>
                                        <div class="shortcut-number">
                                            <big>统计分析系统</big>
                                            <small class="hLh20">多维度分析各数据，生成报表</small>
                                        </div>
                                    </a>
                                </li>
                                <li style="display: none" class="phone" >
                                    <a href="javascript:void(0);" onclick="switchSystem('APP')" >
                                        <i class="fa fa-phone-square fa-fw fl"></i>
                                        <div class="shortcut-number">
                                            <big>掌上校园移动平台</big>
                                            <small class="hLh20">随时随地掌握最新动态，便捷查看</small>
                                        </div>
                                    </a>
                                </li>
                            </ul>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div id="process" class="to-do" >

                    </div>
                </section>
            </div>
        </div>
    <%--</div>--%>
</div>

<script type="text/javascript" src="${ctx}/static/js/plugins/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.uniform.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.slimscroll.js"></script>
<script type="text/javascript" src="${ctx}/static/js/custom/general.js"></script>
<script type="text/javascript" src="${ctx}/static/js/plugins/headNav.js"></script>
<script type="text/javascript" src="${ctx}/static/js/plugins/kill-ie6.js"></script>
<script type="text/javascript" src="${basePath}/static/js/common/base-utils.js"></script>
<script type="text/javascript" src="${basePath}/static/js/common/utils.js"></script>
<!--[if lt IE 9]>
<script src="${ctx}/static/js/plugins/css3-mediaqueries.js"></script>
<![endif]-->

<script type="text/javascript" src="${basePath}/static/js/plugins/jquery.slimscroll.js"></script>

<script type="text/javascript">
    jQuery(function(){
        var resource;
        resource=eval('${resourceSites}').toString();
        if(resource.indexOf("BASE")!=-1){
            jQuery(".base").css("display","block");
        }
        if(resource.indexOf("XY")!=-1){
            jQuery(".users").css("display","block");
        }
        if(resource.indexOf("JW")!=-1){
            jQuery(".jiaowu").css("display","block");
        }
        if(resource.indexOf("KY")!=-1){
            jQuery(".keyan").css("display","block");
        }
        if(resource.indexOf("ZZ")!=-1){
            jQuery(".shengtai").css("display","block");
        }
        if(resource.indexOf("HR")!=-1){
            jQuery(".renshi").css("display","block");
        }
        if(resource.indexOf("HQ")!=-1){
            jQuery(".houqin").css("display","block");
        }
        if(resource.indexOf("OA")!=-1){
            jQuery(".bangong").css("display","block");
        }
        if(resource.indexOf("ZXXY")!=-1){
            jQuery(".zaixianxuexi").css("display","block");
        }
        if(resource.indexOf("MH")!=-1){
            jQuery(".menhu").css("display","block");
        }
        if(resource.indexOf("SMS")!=-1){
            jQuery(".information").css("display","block");
        }
        if(resource.indexOf("TJFX")!=-1){
            jQuery(".statistic").css("display","block");
        }
        if(resource.indexOf("APP")!=-1){
            jQuery(".phone").css("display","block");
        }


    });
    jQuery('.index_content').slimScroll({
        height: '100%',
        railOpacity: 0.9,
        alwaysVisible: false
    });
</script>


<script type="text/javascript">
    var basePath = '${basePath}';

    function switchSystem(sysKey) {
        if (sysKey != null && jQuery.trim(sysKey) != '') {
            window.open('${basePath}/admin/switchSystem.json?sysKey=' + sysKey, '_self');
        }
    }

    querySysUserInfo();
    queryWaitProcess();

    function queryWaitProcess(){
        jQuery.ajax({
            url:'/admin/waitProcess.json',
            type:'post',
            data:{},
            dataType:'text',
            success:function(result){
                jQuery("#process").html(result);
            },
            error:function () {},
        });
    }

</script>
</body>
</html>
