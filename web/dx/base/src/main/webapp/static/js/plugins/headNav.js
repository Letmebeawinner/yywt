 jQuery(function(){
	slideScroll();//头部导航菜单滚动
	navFun();
 })
jQuery(window).resize(function() {
	navFun();
});

function slideScroll() {    //头部导航菜单滚动
	var prev = jQuery(".prev"),
		next = jQuery(".next"),
		oUl = jQuery(".top-nav-list>ul"),
		w = oUl.find("li").outerWidth(true),
		l = oUl.find("li").length;
		oUl.css("width" , w * l + 3 +"px");
	//click left
	prev.click(function() {
		if(!oUl.is(":animated")) {
			oUl.animate({"margin-left" : -w}, function() {
				oUl.find("li").eq(0).appendTo(oUl);
				oUl.css("margin-left" , 0);
			});
		};
	});
	//click right
	next.click(function() {
		if(!oUl.is(":animated")) {
			oUl.find("li:last").prependTo(oUl);
			oUl.css("margin-left" , -w);
			oUl.animate({"margin-left" : 0});
		};
	});
}
function navFun() {
	var winW = parseInt(document.documentElement.clientWidth, 10) + parseInt(document.documentElement.scrollLeft || document.body.scrollLeft, 10),
		nlW = winW - 418,
		ulW = jQuery(".top-nav-list>ul").width(),
		oPN = jQuery('<a class="prev" title="左" href="javascript: void(0)"><i class="fa fa-chevron-left fa-fw fa-2x c-fff"></i></a><a class="next" title="右" href="javascript: void(0)"><span class="navMoreBx"><big class="navMoreBtn"><div class="more"><i class="fa-th fa fa-fw"></i></div><p class="hLh30 tac c-fff fsize12">更多</p></big></span><i class="fa fa-chevron-right fa-fw fa-2x c-fff"></i></a>');
	
	if (nlW > ulW) {
		jQuery(".top-nav-list").css("width" , nlW);
		jQuery(".prev,.next").remove();
	} else {
		jQuery(".top-nav-list").css("width" , nlW-96);
		oPN.appendTo(".top-nav-big");
		slideScroll();
	}
}