<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>智慧校园管理平台登录页面</title>
<link rel="stylesheet" href="${ctx}/static/css/style.default.css" type="text/css" />
<!--[if IE 9]>
    <link rel="stylesheet" media="screen" href="${ctx}/static/css/style.ie9.css"/>
<![endif]-->
<!--[if IE 8]>
    <link rel="stylesheet" media="screen" href="${ctx}/static/css/style.ie8.css"/>
<![endif]-->
<!--[if lt IE 9]>
	<script src="${ctx}/static/js/plugins/css3-mediaqueries.js"></script>
<![endif]-->
	<style type="text/css">
		html,body{height: 100%;}
	</style>
</head>

<body class="loginpage">
	<div class="loginbox-wrap">
	<div class="loginbox">
    	<div class="loginboxinner">

            <div class="logo">
				<c:if test="${source==null||source==''}">
            	<h1 class="logo">
					智慧校园管理平台
				</h1>
					<h3 style="margin-top: 10px;font-size: 14px">
						<a href="http://10.100.101.1:6695/upload/贵阳市委党校智慧校园使用手册.zip" target="_blank">(贵阳市委党校智慧校园使用手册)</a>
					</h3>
				</c:if>
				<c:if test="${source!=null&&source!=''}">
					<h1 class="logo" style="color:red">
						学员报名平台
					</h1>
				</c:if>
            </div><!--logo-->

            <%--<br clear="all" />--%>
			<div style="height:33px;">
				<div class="nousername">
					<div class="loginmsg">
						<img src="/static/images/tc.2.png" width="14" height="14" style="vertical-align: middle;" />
						&nbsp;<b id="login-error-msg"></b>
					</div>
				</div>
			</div>

            <form id="login" method="post">
				<input type="hidden" name="codeName" id="codeName"/>
                <div class="username">
                	<div class="usernameinner">
                    	<input onkeyup="enterSubmit(event)" type="text" name="userName" id="username" placeholder="输入登陆用户名/手机号/用户别名" />
                    </div>
                </div>

                <div class="password">
                	<div class="passwordinner">
                    	<input onkeyup="enterSubmit(event)" type="password" name="password" id="password" placeholder="输入登录密码" />
                    </div>
                </div>

				<div class="password">
                	<div class="passwordinner pw-yzm">
						<img id="ranCode" width="95" height="40" onclick="getCode()" class="pw-yzmPic" />
                    	<input onkeyup="enterSubmit(event)" type="text" name="ranCode" id="ranCodeValue" placeholder="输入验证码" />
                    </div>
                </div>
            </form>
			<button style="color: #fff;" onclick="login()">登 录</button>
			<%--<div class="keep">Powered By <a href="http://www.268xue.com" target="_blank">268教育软件</a></div>--%>

        </div><!--loginboxinner-->
    </div><!--loginbox-->
	</div>

	<div class="onlineConsult">
		<dl>
			<dd class="pr onlineC-item1">
				<a href="JavaScript:void(0)" class="smgz-a">
					<%--<em class="ocgld-em smgz-btn">&nbsp;</em>--%>
					<span class="fb-hover-text">下载APP</span>
					<div class="smgz-pic" id="scanAtten">
						<img src="${ctx}/static/images/Android_logo.png" id="ewm_index" width="150" height="150" class="dis"/>
						<p class=" fsize14 f-fM c-666 tac">Android下载</p>
						<%--<img src="${ctx}/static/images/IOS_logo.png" width="150" height="150" class="dis mt10"/>--%>
						<%--<p class=" fsize14 f-fM c-666 tac">IOS下载</p>--%>
						<span class="ewm-jiao">
							<img src="/static/images/ewm-y-icon.png" width="7" height="14">
						</span>
					</div>
				</a>
			</dd>
		</dl>
	</div>

	<script type="text/javascript" src="${ctx}/static/js/plugins/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/custom/placeholder.js"></script>
	<script type="text/javascript" src="${ctx}/static/admin/main/login.js"></script>
	<script type="text/javascript">
		placeholderFun(); //适配 placeholder;
	</script>
</body>
</html>
