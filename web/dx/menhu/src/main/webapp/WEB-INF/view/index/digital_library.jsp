<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>数字图书馆</title>
    <link rel="stylesheet" href="/static/css/style.default.css" type="text/css" />
    <link rel="stylesheet" media="screen" href="/static/css/style.ie9.css"/>
    <link rel="stylesheet" media="screen" href="/static/css/style.ie8.css"/>
    <script src="/static/js/plugins/css3-mediaqueries.js"></script>
    <script type="text/javascript" src="/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="/static/js/html5.js"></script>
</head>
<body>
<div class="mb50">
    <section class="w1000">
        <div class="mt20">
            <section class="clearfix">
                <div class="bbs_subnavbox1 js_navjlq">
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
                                        <a href="${ctx}/static/贵阳市委党校-移动图书馆使用说明.pdf"><span>APP用户指南</span></a>
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
                </div>
            </section>
        </div>
    </section>
</div>
</body>
</html>
