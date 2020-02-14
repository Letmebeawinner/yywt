<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title><sitemesh:write property='title'/></title>
	<sitemesh:write property='head'/>
</head>

<body class="">
<header class="topBarWrap">
	<div class="w1000">
		<section class="cleafix">
			<span class="fl welcome">您好，欢迎访问中共贵阳市委党校网站！</span>
			<ul class="t-link c-ccc fr">
				<li class="outLi pr header_list">
					<a class="topAPPLink" href="/static/app/dangxiao.apk" title="APP下载">APP下载</a>
					<section class="topAPPLinkBox">
						<img src="${ctx}/static/images/nxandroid.jpg" width="120" height="120"
							 alt="">
						<p class="hLh30 tac"><span class="c-999 fsize12">扫一扫,下载APP</span></p>
					</section>
					|
				</li>
				<li><a href="javascript:addFavorite();" title="收藏本站" rel="sidebar" class="collect">收藏本站</a></li>
			</ul>
		</section>
	</div>
</header>
<section class="headerBg">
	<div class="w1000">
		<div class="banner" style=" width:1080px; height:180px; margin:auto;">
			<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="1080" height="180" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0">
				<param name="movie" value="banner.swf">
				<param name="quality" value="high">
				<param name="wmode" value="transparent">
				<embed src="/static/swf/banner.swf" width="1080" height="180" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" wmode="transparent">
			</object>
		</div>
	</div>
</section>

<div class="hNavWrap">
	<section class="w1000 clearfix">
		<ul class="hNav fl pr navCon_on" id="guideInfo"><!-- current -->
			<li class="current">
				<a href="/"  title="首页">&nbsp;&nbsp;首页&nbsp;&nbsp;</a>
			</li>
			<li><a href="javaScript:void('')" onclick="toSkip(4)" title="新闻资讯">新闻资讯</a></li>
			<%--<li><a href="javaScript:void('')" onclick="toSkip(5)"  title="信息公开">信息公开</a></li>--%>
			<li><a href="http://10.100.101.1:6681/admin/toLogin.json?source=front"  title="通知公告">在线报名</a></li>
			<li><a href="http://10.100.20.200"  title="在线学习">在线学习</a></li>
			<li><a href="javaScript:void('')" onclick="toSkip(7)"  title="教学动态">教学动态</a></li>
			<li><a href="javaScript:void('')" onclick="toSkip(3)"  title="科研咨政">科研咨政</a></li>
			<li><a href="javaScript:void('')" onclick="toSkip(1)"  title="通知公告">通知公告</a></li>
			<li class="bbs_i1 js_pub_tab" tab="js_navjlq">
				<a href="${ctx}/digitalLibrary.json"   title="数字图书馆">数字图书馆</a>
				<%--<div class="bbs_subnavbox1 js_navjlq" style="display: none">
					<div class="bbs_content">
						<div class="bbs_subnav_yc">
							<h1><a  href="javascript:;">馆藏图书</a></h1>
							<div class="bbs_subnav_yc" id="js_producyList" >
								<ul class="bbs_subnav bbs_subnav01 clearfix">
									<li class="">
										<a href="javascript:;"><span>图书借阅指南</span></a>
									</li>
									<li class="">
										<a href="http://10.100.101.2"><span>图书借阅</span></a>
									</li>
								</ul>
								<h1><a  href="javascript:;">电子图书</a></h1>
								<ul class="bbs_subnav bbs_subnav02 clearfix">
									<li class="">
										<a href="http://10.100.101.3:8080"><span>超星电子图书</span></a>
									</li>
									<li class="">
									<a href="javascript:;"><span>APP用户指南</span></a>
								    </li>
									<li class="">
									<a href="javascript:;"><span>图书借阅机</span></a>
								    </li>
								</ul>
								<h1><a  href="javascript:;">CNKI电子期刊</a></h1>
								<ul class="bbs_subnav bbs_subnav03 clearfix">
									<li class="">
										<a href="http://cpc.cnki.net/gydx"><span>外网资源</span></a>
									</li>
									<li class="">
									    <a href="http://10.100.101.4"><span>内网资源</span></a>
									</li>
								</ul>
								<h1><a  href="javascript:;">其他</a></h1>
								<ul class="bbs_subnav bbs_subnav03 clearfix">
									<li class="">
										<a href="http://www.duxiu.com"><span>超星读秀检索平台</span></a>
									</li>
									<li class="">
									    <a href="javascript:void(0)"><span>图书馆介绍</span></a>
									</li>
                                    <li class="">
                                        <a href="http://www.sslibrary.com"><span>远程包库</span></a>
                                    </li>
								</ul>
								<h1><a  href="javascript:;">内网资源检索</a></h1>
								<ul class="bbs_subnav bbs_subnav03 clearfix">
									<li class="">
										<a href="/static/index.html"><span>内网资源检索</span></a>
									</li>
								</ul>
							</div>
						</div>
					</div>
				</div>--%>

			</li>
		</ul>
	</section>
</div>


<script type="text/javascript" src="/static/js/jquery.min.js"></script>
<script type="text/javascript">
    jQuery(".sli_tab article").click(function(){
        jQuery(this).addClass("current").siblings().removeClass("current");
    });
    function toSkip(typeId){
        window.location.href='${ctx}/front/menhu/article/queryArticleList.json?article.typeId='+typeId;
    }
    /*jQuery(".js_pub_tab").hover(function () {
        var tabclass = jQuery(this).attr('tab');
        jQuery(this).addClass('checked');
        jQuery('.bbs_subnavbox1').hide();
        jQuery('.' + tabclass).show();
    }, function () {
        var tabclass = jQuery(this).attr('tab');
        jQuery('.bbs_subnavbox1').hide();
        jQuery('.' + tabclass).hide();
        jQuery(this).removeClass('checked');
    });*/

    $(function () {
        $(".topAPPLink").hover(function () {
            $(".topAPPLinkBox").slideDown(100);
        }, function () {
        });
        $(".header_list").hover(function () {
        }, function () {
            $(".topAPPLinkBox").stop(false, true).slideUp(100);
        })

    })
</script>

<sitemesh:write property='body'/>
</body>
</html>
