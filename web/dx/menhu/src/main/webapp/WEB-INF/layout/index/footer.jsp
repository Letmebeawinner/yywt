<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title><sitemesh:write property='title'/></title>
	<sitemesh:write property='head'/>
</head>

<body class="">
<sitemesh:write property='body'/>
	<footer>
		<div class="footerLine"></div>
		<div class="w1000">
			<section class="f-link clearfix">
				<div class="fIcon"><img src="${ctx}/static/images/blue.png" /></div>
				<ul>
					<li><a href="">网站地图</a> 丨 <a href="">联系我们</a> 丨 <a href="">版权信息</a> 丨 <a href="">隐私声明</a>  </li>
					<li>版权所有：中共贵阳市委党校</li>
					<%--<li>技术支持：艾瑞科技网络</li>--%>
				</ul>
			</section>
		</div>
	</footer>
</body>
</html>
