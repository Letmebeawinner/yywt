jQuery(function () {
    initAuthority();
})

/**
 * 初始化权限
 */
function initAuthority(){
    var url=basePath+"/admin/base/queryChildSysAuthority.json?callback=?";
    jQuery.getJSON(url,function(data){
        var context='<ul>';
        if(data!=null && data.length>0){
            context+='';
            jQuery.each(data,function(index,em){
                var styleName = em.styleName;
                if(styleName==null || jQuery.trim(styleName)==''){
                    styleName = 'fa-folder';
                }
                if(em.childList==null || em.childList.length==0){
                    context+='<li><a href="'+em.resourcePath+'" class="elements"><i class="fa '+styleName+'"></i>&nbsp; '+em.resourceName+'</a>';
                }else{
                    context+='<li><a href="#childs_a_'+em.id+'" class="editor"><i class="fa '+styleName+'"></i>&nbsp; '+em.resourceName+'</a>';
                    context+='<span class="arrow"><i class="fa-caret-down fa fa-fw"></i></span>';
                    context+='<ul id="childs_a_'+em.id+'">';
                    jQuery.each(em.childList,function(index,emc){
                        context+='<li><a href="'+emc.resourcePath+'">'+emc.resourceName+'</a></li>';
                    });
                    context+='</ul></li>';
                }
            });
            context+='</ul>';
            jQuery("body").addClass("withvernav");
            jQuery("div>.vernav2").show();
            //jQuery(".centercontent").css("margin-left",'181px');
        }else{
            jQuery("body").removeClass("withvernav");
            jQuery("div>.vernav2").hide();
            //jQuery(".centercontent").css("margin-left",'0');
        }
        jQuery(context).insertBefore("#show_authority_list");
        initAuthorityClick();
        exShowM();
        jQuery('.vernav > ul li a, .vernav2 > ul li a').each(function(){
            var url = jQuery(this).attr('href');
            jQuery(this).click(function(){
                if(jQuery(url).length > 0) {
                    if(jQuery(url).is(':visible')) {
                        if(!jQuery(this).parents('div').hasClass('menucoll') &&
                            !jQuery(this).parents('div').hasClass('menucoll2'))
                            jQuery(url).slideUp();
                    } else {
                        jQuery('.vernav ul ul, .vernav2 ul ul').each(function(){
                            jQuery(this).slideUp();
                        });
                        if(!jQuery(this).parents('div').hasClass('menucoll') &&
                            !jQuery(this).parents('div').hasClass('menucoll2'))
                            jQuery(url).slideDown();
                    }
                    return false;
                }
            });
        });
    });
}


var ckCookieName="sys_click_cookie_name";
function initAuthorityClick() {
    jQuery(".iconmenu>ul>li>a").click(function () {
        if(jQuery(this).hasClass('editor')){
            var _href = this.href;
            var ck = _href.split("#")[1];
            SetCookie(ckCookieName,ck);
        }else{
            DeleteCookie(ckCookieName);
        }
    });
}
/**
 * 展开点击过的权限
 */
function exShowM() {
    var _v = getCookie(ckCookieName);
    if(isEmpty(_v)){
        return;
    }
    var aem = jQuery("#"+_v).prev('span').prev('a');

    var url = jQuery(aem).attr('href');
    if(jQuery(url).length > 0) {
        if(jQuery(url).is(':visible')) {
            if(!jQuery(this).parents('div').hasClass('menucoll') &&
                !jQuery(this).parents('div').hasClass('menucoll2'))
                jQuery(url).slideUp();
        } else {
            jQuery('.vernav ul ul, .vernav2 ul ul').each(function(){
                jQuery(this).slideUp();
            });
            if(!jQuery(this).parents('div').hasClass('menucoll') &&
                !jQuery(this).parents('div').hasClass('menucoll2'))
                jQuery(url).slideDown();
        }
        return false;
    }
}
