<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<%@ page import="com.a_268.base.constants.BaseCommonConstants"%>
<c:set var="basePath" value="<%=BaseCommonConstants.basePath%>"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>中共贵阳市委党校</title>
    <link rel="stylesheet" href="/static/css/style.default.css" type="text/css" />
    <link rel="stylesheet" media="screen" href="/static/css/style.ie9.css"/>
    <link rel="stylesheet" media="screen" href="/static/css/style.ie8.css"/>
    <script src="js/plugins/css3-mediaqueries.js"></script>
    <script type="text/javascript" src="/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="/static/js/html5.js"></script>
    <script type="text/javascript" src="/static/js/jquery-1.7.2.min.js"></script>
</head>
<body>
<div class="w1000">
    <section class="clearfix mt40 mb30">
        <div class="oSlide fl">
            <section class="oSlide-P" id="oSlideFun">
                <ul>
                    <c:if test="${articleBannerList!=null && articleBannerList.size() > 0}">
                        <c:forEach items="${articleBannerList }" var="article" varStatus="index">
                            <li class="oShow" style="display:  ${index.first?'block':'none'};">
                                <a target="_blank" title="${article.title}" href="${ctx}/front/menhu/article/queryArticleInfo.json?id=${article.id}&typeId=${article.typeId}">
                                    <c:if test="${article.pictureList!=null && article.pictureList.size() > 0}">
                                        <img src="${article.pictureList[0]}" alt="${article.title}" style="width: 700px;padding-left: 610px" >
                                    </c:if>
                                </a>
                            </li>
                        </c:forEach>
                    </c:if>
                </ul>
			<span id="oSbtn" class="oSbtn">
                <c:if test="${articleBannerList!=null && articleBannerList.size() > 0}">
                    <c:forEach items="${articleBannerList }" var="article" varStatus="index">
                        <a href="javascript:void(0)" class="${index.first?'on':''}">&nbsp;</a>
                    </c:forEach>
                </c:if>
			</span>
            </section>
        </div>
        <div class="slideRight fr" style="height: 340px;">
            <div class="sli_r_title">
                <article>通知公告    <small>NOTICE</small></article>
                <span> <a href="javascript:void(0)"  onclick="openArticleList(1)">MORE > ></a></span>
            </div>
            <ul class="newsLi">
                <c:if test="${noticeList!=null && noticeList.size() > 0}">
                    <c:forEach items="${noticeList }" var="article">
                        <li>
                            <em class="icon18"></em>
                            <div class="n_Content "> <a title="${article.title}" href="${ctx}/front/menhu/article/queryArticleInfo.json?id=${article.id}&typeId=1">${article.title}</a></div>
                            <span class="n_Time">${fn:substring(article.createTime, 0, 10)}</span>
                        </li>
                    </c:forEach>
                </c:if>
            </ul>
        </div>
    </section>
    <section class="clearfix" >
        <div class="fl">
            <div class="slideLeft mb30"style="height: 312px;" >
                <div class="sli_r_title">
                    <article>新闻资讯    <small>NEWS</small></article>
                    <span> <a  href="javascript:void(0)"  onclick="openArticleList(4)">MORE > ></a></span>
                </div>
                <ul class="newsLi_l">
                    <c:if test="${newsList!=null && newsList.size() > 0}">
                        <c:forEach items="${newsList }" var="article">
                            <li>
                                <em class="icon18"></em>
                                <div class="n_Content "> <a title="${article.title}" href="${ctx}/front/menhu/article/queryArticleInfo.json?id=${article.id}&typeId=4">${article.title}</a></div>
                                <span class="n_Time"><fmt:formatDate value="${article.createTime}" pattern="yyyy-MM-dd"/>  </span>
                            </li>
                        </c:forEach>
                    </c:if>
                </ul>
            </div>
        </div>
        <div class="fr">
            <div class="slideRight" style="height: 312px;">
                <div class="sli_r_title">
                    <article>教学动态    <small>NOTICE</small></article>
                    <span> <a href="javascript:void(0)"  onclick="openArticleList(7)">MORE > ></a></span>
                </div>
                <ul class="newsLi">
                <c:if test="${teachingList!=null && teachingList.size() > 0}">
                    <c:forEach items="${teachingList }" var="article">
                        <li>
                            <em class="icon18"></em>
                            <div class="n_Content"> <a  title="${article.title}" href="${ctx}/front/menhu/article/queryArticleInfo.json?id=${article.id}&typeId=7">${article.title}</a></div>
                            <span class="n_Time">${fn:substring(article.createTime, 0, 10)}</span>
                        </li>
                    </c:forEach>
                </c:if>
                </ul>
            </div>
        </div>
    </section>
    <section class="sc-banner"><img src="/static/images/sc-banner.jpg" /></section>
    <section class="clearfix" style="padding-bottom: 68px;">
        <div class="fl">
            <div class="slideLeft">
                <div class="sli_r_title sli_tab">
                    <%--<article class="current" onclick="doChange('KYAab','teachingAab')" >教学动态</article>--%>
                    <article class="current">科研咨政</article>
                    <span> <a href="javascript:void(0)" id="_openArticleList" onclick="openArticleList(3)">MORE > ></a></span>
                </div>
                <ul class="newsLi_l" id="KYAab">
                    <c:if test="${KYList!=null && KYList.size() > 0}">
                        <c:forEach items="${KYList }" var="article">
                            <li>
                                <div class="n_Content "> <a title="${article.title}" href="${ctx}/front/menhu/article/queryArticleInfo.json?id=${article.id}&typeId=3">${article.title}</a></div>
                                <span class="n_Time"><fmt:formatDate value="${article.createTime}" pattern="yyyy-MM-dd"/>  </span>
                            </li>
                        </c:forEach>
                    </c:if>
                </ul>
            </div>
        </div>
        <div class="fr">
            <div class="online">
                <a href="http://10.100.20.200">
                    <img src="/static/images/online.jpg" title="点击进入在线学习"/>
                </a>
            </div>
            <div class="online">
                <a href="http://10.100.120.251:8080/ziyuanmulu/publicMulu_get.do">
                    <img src="/static/images/zyml.jpg" title="党校资源目录"/>
                </a>
            </div>
        </div>
    </section>
</div>
<script type="text/javascript" src="${ctx}/static/js/placeholder.js"></script>
<script type="text/javascript">
    function doChange(type1,type2) {
        jQuery("#"+type1).hide();
        jQuery("#"+type2).show();
        if(type1=='KYAab'){
            jQuery("#_openArticleList").attr("onclick","openArticleList(7)");
        }else{
            jQuery("#_openArticleList").attr("onclick","openArticleList(3)");
        }
    }

    function openArticleList(type) {
        window.location.href='${ctx}/front/menhu/article/queryArticleList.json?typeId='+type;
    }

    jQuery(".sli_tab article").click(function(){
        jQuery(this).addClass("current").siblings().removeClass("current");
    });
    //首页全屏适应幻灯片
    function oSlider() {
        var Wind = {};
        Wind.Focus = {
            init:function (args){
                this.id = $(args.id);
                this.aBtn = $(args.aBtn);
                this.prev = $(args.prev);
                this.next = $(args.next);
                this.li = $(args.li);
                this.ms = 4000;
                this.auto = args.auto?args.auto:false;
                this.on = args.on;
                this.nextTarget = 0;
                this.autoTimer = null;
                this.start();
            },
            start:function (){
                var _this = this;
                var color = this.li.eq(0).find("a").attr("name");
                this.id.siblings(".slideColorBg").show().css("background-color", color);
                if(this.li.length === 1) {
                    this.showSlides(this.li.length);
                    return;
                } else {
                    this.aBtn.each(function(){
                        var index = _this.aBtn.index(this);
                        if(!_this.li.is(':animated')) {
                            $(this).hover(function(){
                                _this.showSlides(index);
                            });
                        }
                    });
                    this.autoTimer = setInterval(function(){
                        _this.autoPlay();
                    },_this.ms);
                    this.id.hover(function(){
                        clearInterval(_this.autoTimer);
                        _this.prev.fadeIn(200);
                        _this.next.fadeIn(200);
                    },function(){
                        _this.autoTimer = setInterval(function(){
                            _this.autoPlay();
                        },_this.ms);
                        _this.prev.fadeOut(200);
                        _this.next.fadeOut(200);
                    });
                    this.prev.click(function() {
                        _this.nextTarget++;
                        if(_this.nextTarget > _this.li.length - 1){
                            _this.nextTarget = 0;
                        }
                        if(!_this.li.is(':animated')) {_this.showSlides(_this.nextTarget);}
                    });
                    this.next.click(function() {
                        _this.nextTarget--;
                        if(_this.nextTarget <0) {
                            _this.nextTarget = _this.li.length - 1;
                        }
                        if(!_this.li.is(':animated')) {_this.showSlides(_this.nextTarget);}
                    })
                }
            },
            showSlides:function (index){
                this.nextTarget = index;
                var color = this.li.eq(index).find("a").attr("name");
                var _thisId = this.id;
                if(!this.li.is(":animated")) {
                    this.aBtn.eq(index).addClass(this.on).siblings().removeClass(this.on);
                    this.li.eq(index).fadeIn(500).siblings().hide();
                    _thisId.siblings(".slideColorBg").fadeIn("500", function(){$(this).css("background-color", color)});
                };
            },
            autoPlay:function (){
                this.nextTarget++;
                if(this.nextTarget > this.li.length - 1){
                    this.nextTarget = 0;
                }
                this.showSlides(this.nextTarget);
            }
        };

        Wind.Focus.init({
            //幻灯片id
            id: "#oSlideFun",
            //按钮
            aBtn: "#oSbtn a",
            //左右
            prev: "#oSlideFun .prev",
            next: "#oSlideFun .next",
            //大图li
            li: "#oSlideFun li",
            //按钮鼠标放上时
            on: "on",
            //自动播放的时间
            ms: 2000,
            //是否自动播放
            auto: true
        });
    }
    jQuery(function(){
        oSlider(); //首页幻灯片
    });

    /*漂浮广告*/
    //定义全局变量
    var moveX = 0;       //X轴方向移动的距离
    var moveY = 0;      //Y轴方向移动的距离
    var step = 1;      //图片移动的速度
    var directionY = 0;   //设置图片在Y轴的移动方向
    var directionX = 0;   //设置图片在X轴的移动方向

    function changePos(){
        var img = document.getElementById("float_icon");  //图片所在层ID
        var width = document.documentElement.clientWidth;       //浏览器宽度
        var height = document.body.clientHeight;    //浏览器高度
        var imgHeight=document.getElementById("floatImg").height;   //漂浮图片高度
        var imgWidth=document.getElementById("floatImg").width;     //漂浮图片宽度
        img.style.left =parseInt(moveX + document.documentElement.scrollLeft)+"px";     //漂浮图片距浏览器左侧位置
        img.style.top = parseInt(moveY + document.documentElement.scrollTop)+"px";      //漂浮图片距浏览器顶端位置
//alert(img.style.left);
        if (directionY==0){
            moveY = moveY + step;   //漂浮图片在Y轴方向上向下移动
        }
        else{
            moveY = moveY - step;   //漂浮图片在Y轴方向上向上移动
        }
        if (moveY < 0) {     //如果漂浮图片漂到浏览器顶端时，设置图片在Y轴方向上向下移动
            directionY = 0;
            moveY = 0;
        }
        if (moveY >= (height - imgHeight)) {   //如果漂浮图片漂到浏览器底端时，设置图片在Y轴方向上向上移动
            directionY = 1;
            moveY = (height - imgHeight);
        }
        if (directionX==0){
            moveX = moveX + step;     //漂浮图片在X轴方向上向右移动
        }
        else {
            moveX = moveX - step;     //漂浮图片在X轴方向上向左移动
        }
        if (moveX < 0) {     //如果漂浮图片漂到浏览器左侧时，设置图片在X轴方向上向右移动
            directionX = 0;
            moveX = 0;
        }
        if (moveX >= (width - imgWidth)) {     //如果漂浮图片漂到浏览器右侧时，设置图片在X轴方向上向左移动
            directionX = 1;
            moveX = (width - imgWidth);
        }
        // setTimeout("changePos()",30);
    }
    setInterval("changePos()",30);
</script>
</body>
</html>
