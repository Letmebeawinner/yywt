<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<%@ page import="com.a_268.base.constants.BaseCommonConstants"%>
<c:set var="basePath" value="<%=BaseCommonConstants.basePath%>"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title><sitemesh:write property='title'/>-智慧校园管理平台</title>
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
    <!--[if lte IE 8]>
    <script src="http://cdn.bootcss.com/jquery/1.9.0/jquery.min.js"></script>
    <![endif]-->
<link rel="stylesheet" href="${basePath}/static/css/style.contrast.css" type="text/css"/>
<script type="text/javascript" src="${basePath}/static/js/plugins/jquery-1.7.min.js"></script>
<sitemesh:write property='head'/>
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
    
    <div class="vernav2 iconmenu" style="display: none;">
        <a  style="display: none;" id="show_authority_list" class="togglemenu"></a>
        <br /><br />
    </div><!--leftmenu-->

    <sitemesh:write property='body'/>

</div>
<!--bodywrapper-->
<script type="text/javascript" src="${basePath}/static/js/plugins/jquery.alerts.js"></script>
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
    var basePath = '${basePath}';
    var mydomain = "";
    querySysUserInfo();
</script>
</body>
</html>
