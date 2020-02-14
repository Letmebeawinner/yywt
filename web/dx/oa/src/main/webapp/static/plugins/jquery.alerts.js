// jQuery Alert Dialogs Plugin
//
// Version 1.1
//
// Cory S.N. LaViska
// A Beautiful Site (http://abeautifulsite.net/)
// 14 May 2009
//
// Visit http://abeautifulsite.net/notebook/87 for more information
//
// Usage:
//		jAlert( message, [title, callback] )
//		jConfirm( message, [title, callback] )
//		jPrompt( message, [value, title, callback] )
// 
// History:
//
//		1.00 - Released (29 December 2008)
//
//		1.01 - Fixed bug where unbinding would destroy all resize events
//
// License:
// 
// This plugin is dual-licensed under the GNU General Public License and the MIT License and
// is copyright 2008 A Beautiful Site, LLC. 
//
(function ($) {

    $.alerts = {

        // These properties can be read/written by accessing $.alerts.propertyName from your scripts at any time

        verticalOffset: -75,                // vertical offset of the dialog from center screen, in pixels
        horizontalOffset: 0,                // horizontal offset of the dialog from center screen, in pixels/
        repositionOnResize: true,           // re-centers the dialog on window resize
        overlayOpacity: .01,                // transparency level of overlay
        overlayColor: '#FFF',               // base color of overlay
        draggable: true,                    // make the dialogs draggable (requires UI Draggables plugin)
        okButton: '&nbsp;确定&nbsp;',         // text for the OK button
        cancelButton: '&nbsp;取消&nbsp;', // text for the Cancel button
        dialogClass: null,                  // if specified, this class will be applied to all dialogs

        // Public methods

        alert: function (message, title, callback) {
            if (title == null) title = 'Alert';
            $.alerts._show(title, message, null, 'alert', function (result) {
                if (callback) callback(result);
            });
        },

        confirm: function (message, title, callback) {
            if (title == null) title = 'Confirm';
            $.alerts._show(title, message, null, 'confirm', function (result) {
                if (callback) callback(result);
            });
        },

        prompt: function (message, value, title, callback) {
            if (title == null) title = 'Prompt';
            $.alerts._show(title, message, value, 'prompt', function (result) {
                if (callback) callback(result);
            });
        },

        addCont: function (message, value, title, callback) {
            if (title == null) title = 'addCont';
            $.alerts._show(title, message, value, 'addCont', function (result) {
                if (callback) callback(result);
            });
        },

        // Private methods

        _show: function (title, msg, value, type, callback) {

            $.alerts._hide();
            $.alerts._overlay('show');

            $("BODY").append(
                '<div id="popup_container">' +
                '<h1 id="popup_title"></h1>' +
                '<div id="popup_content">' +
                '<div id="popup_message"></div>' +
                '</div>' +
                '</div>');

            if ($.alerts.dialogClass) $("#popup_container").addClass($.alerts.dialogClass);

            // IE6 Fix
            var pos = ($.browser.msie && parseInt($.browser.version) <= 6 ) ? 'absolute' : 'fixed';

            $("#popup_container").css({
                position: pos,
                zIndex: 99999,
                padding: 0,
                margin: 0,
            });

            $("#popup_title").text(title);
            $("#popup_content").addClass(type);
            $("#popup_message").text(msg);
            $("#popup_message").html($("#popup_message").text().replace(/\n/g, '<br />'));

            switch (type) {
                case 'alert':
                    $("#popup_message").after('<div id="popup_panel"><input type="button" value="' + $.alerts.okButton + '" id="popup_ok" /></div>');
                    $("#popup_ok").click(function () {
                        $.alerts._hide();
                        callback(true);
                    });
                    $("#popup_ok").focus().keypress(function (e) {
                        if (e.keyCode == 13 || e.keyCode == 27) $("#popup_ok").trigger('click');
                    });
                    break;
                case 'confirm':
                    $("#popup_message").after('<div id="popup_panel"><input type="button" value="' + $.alerts.okButton + '" id="popup_ok" /> <input type="button" value="' + $.alerts.cancelButton + '" id="popup_cancel" /></div>');
                    $("#popup_ok").click(function () {
                        $.alerts._hide();
                        if (callback) callback(true);
                    });
                    $("#popup_cancel").click(function () {
                        $.alerts._hide();
                        if (callback) callback(false);
                    });
                    $("#popup_ok").focus();
                    $("#popup_ok, #popup_cancel").keypress(function (e) {
                        if (e.keyCode == 13) $("#popup_ok").trigger('click');
                        if (e.keyCode == 27) $("#popup_cancel").trigger('click');
                    });
                    break;
                case 'prompt':
                    $("#popup_message").append('<br /><input type="text" size="30" id="popup_prompt" />').after('<div id="popup_panel"><input type="button" value="' + $.alerts.okButton + '" id="popup_ok" /> <input type="button" value="' + $.alerts.cancelButton + '" id="popup_cancel" /></div>');
                    $("#popup_prompt").width($("#popup_message").width());
                    $("#popup_ok").click(function () {
                        var val = $("#popup_prompt").val();
                        $.alerts._hide();
                        if (callback) callback(val);
                    });
                    $("#popup_cancel").click(function () {
                        $.alerts._hide();
                        if (callback) callback(null);
                    });
                    $("#popup_prompt, #popup_ok, #popup_cancel").keypress(function (e) {
                        if (e.keyCode == 13) $("#popup_ok").trigger('click');
                        if (e.keyCode == 27) $("#popup_cancel").trigger('click');
                    });
                    if (value) $("#popup_prompt").val(value);
                    $("#popup_prompt").focus().select();
                    break;
                case 'addCont':
                    var addContEle = '<div class="addContBox" id="addContBox"><ul class="hornav">';
                    addContEle += '<li class="current"><a href="#updates">基本信息</a></li><li class=""><a href="#activities">封面图片</a></li></ul>';
                    addContEle += '<div id="updates" class="subcontent"><form class="stdform" method="post" action="">';
                    addContEle += '<div class="par"><label>Small Input</label><span class="field"><input type="text" class="smallinput" name="input1"><font class="c-red ml5">校验位置</font></span><small class="desc">Small description of this field.</small></div>';
                    addContEle += '<div class="par"><label>Small Input</label><span class="field"><input type="text" class="mediuminput" name="input2"></span><small class="desc">Small description of this field.</small></div>';
                    addContEle += '<div class="par"><label>Small Input</label><span class="field"><input type="text" class="longinput" name="input3"></span><small class="desc">Small description of this field.</small></div>';
                    addContEle += '<div class="par"><label>Small Input</label><div class="selector"><span style="-moz-user-select: none;">Selection Four</span><select class="uniformselect" name="select" style="opacity: 0;"><option value="">Choose One</option></select></div></div>';
                    addContEle += '<div class="par"><label>Small Input</label><div class="selector disabled"><span style="-moz-user-select: none;">Selection Four</span><select class="uniformselect" disabled="disabled" name="select" style="opacity: 0;"><option value="">Choose One</option></select></div></div>';
                    addContEle += '<div class="par"><label>Small Input</label><div class="selector"><span style="-moz-user-select: none;">Selection Four</span><select class="uniformselect" name="select" style="opacity: 0;"><option value="">Choose One</option></select></div></div>';
                    addContEle += '<div class="par"><label>Small Input</label><span class="formwrapper"><div class="radio" id="uniform-undefined"><span><input type="radio" name="radiofield" style="opacity: 0;"></span></div> Unchecked Radio &nbsp; &nbsp;<div class="radio" id="uniform-undefined"><span><input type="radio" checked="checked" name="radiofield" style="opacity: 0;"></span></div> Checked Radio &nbsp; &nbsp;<div class="radio disabled" id="uniform-undefined"><span><input type="radio" disabled="disabled" name="radiofield" style="opacity: 0;"></span></div> Disabled Radio  &nbsp; &nbsp;<div class="radio disabled" id="uniform-undefined"><span class="checked"><input type="radio" disabled="disabled" checked="checked" name="radiofield" style="opacity: 0;"></span></div> Disabled Radio </span></div>';
                    addContEle += '<div class="par"><label>Small Input</label><span class="field"><textarea class="longinput" rows="5" cols="80"></textarea></div>';
                    addContEle += '</form></div>';

                    addContEle += '<div id="activities" class="subcontent">封面图片</div>';
                    addContEle += '</div>';
                    $("#popup_message").append(addContEle).after('<div id="popup_panel"><input type="button" value="' + $.alerts.okButton + '" id="popup_ok" /><input class="ml10" type="button" value="' + $.alerts.cancelButton + '" id="popup_cancel" /></div>');
                    $("#popup_ok").click(function () {
                        $.alerts._hide();
                        callback(true);
                    });

                    $("#popup_cancel").click(function () {
                        $.alerts._hide();
                        if (callback) callback(false);
                    });
                    $("#popup_ok").focus();
                    $("#popup_ok, #popup_cancel").keypress(function (e) {
                        if (e.keyCode == 13) $("#popup_ok").trigger('click');
                        if (e.keyCode == 27) $("#popup_cancel").trigger('click');
                    });

                    $.alerts._hnavFun();
                    break;
                /** 自定义树状结构 **/
                case 'zTree' :
                    $("#popup_message").append(msg).after('<div id="popup_panel"><input type="button" value="' + $.alerts.okButton + '" id="popup_ok" /><input class="ml10" type="button" value="' + $.alerts.cancelButton + '" id="popup_cancel" /></div>');
                    $("#popup_ok").click(function () {
                        $.alerts._hide();
                        callback(true);
                    });

                    $("#popup_cancel").click(function () {
                        $.alerts._hide();
                        if (callback) callback(false);
                    });
                    $("#popup_ok").focus();
                    $("#popup_ok, #popup_cancel").keypress(function (e) {
                        if (e.keyCode == 13) $("#popup_ok").trigger('click');
                        if (e.keyCode == 27) $("#popup_cancel").trigger('click');
                    });

                    $.alerts._hnavFun();
                    break
            }

            $("#popup_container").css({
                minWidth: $("#popup_container").outerWidth(),
                width: $("#popup_container").outerWidth()
            });

            $.alerts._reposition();
            $.alerts._maintainPosition(true);

            // Make draggable
            if ($.alerts.draggable) {
                try {
                    $("#popup_container").draggable({handle: $("#popup_title")});
                    $("#popup_title").css({cursor: 'move'});
                } catch (e) { /* requires jQuery UI draggables */
                }
            }
        },

        _hide: function () {
            $("#popup_container").remove();
            $.alerts._overlay('hide');
            $.alerts._maintainPosition(false);
        },

        _overlay: function (status) {
            switch (status) {
                case 'show':
                    $.alerts._overlay('hide');
                    $("BODY").append('<div id="popup_overlay"></div>');
                    $("#popup_overlay").css({
                        position: 'absolute',
                        zIndex: 99998,
                        top: '0px',
                        left: '0px',
                        width: '100%',
                        height: $(document).height(),
                        background: $.alerts.overlayColor,
                        opacity: $.alerts.overlayOpacity
                    });
                    break;
                case 'hide':
                    $("#popup_overlay").remove();
                    break;
            }
        },

        _reposition: function () {
            var top = (($(window).height() / 2) - ($("#popup_container").outerHeight() / 2)) + $.alerts.verticalOffset;
            var left = (($(window).width() / 2) - ($("#popup_container").outerWidth() / 2)) + $.alerts.horizontalOffset;
            if (top < 0) top = 0;
            if (left < 0) left = 0;

            // IE6 fix
            if ($.browser.msie && parseInt($.browser.version) <= 6) top = top + $(window).scrollTop();
            $("#popup_container").css({
                top: top + 'px',
                left: left + 'px'
            });
            $("#popup_overlay").height($(document).height());
        },

        _maintainPosition: function (status) {
            if ($.alerts.repositionOnResize) {
                switch (status) {
                    case true:
                        $(window).bind('resize', $.alerts._reposition);
                        break;
                    case false:
                        $(window).unbind('resize', $.alerts._reposition);
                        break;
                }
            }
        },

        _hnavFun: function () {
            jQuery('.addContBox .subcontent').eq(0).show();
            jQuery('.hornav a').click(function () {

                //this is only applicable when window size below 450px
                if (jQuery(this).parents('.more').length == 0)
                    jQuery('.hornav li.more ul').hide();

                //remove current menu
                jQuery('.hornav li').each(function () {
                    jQuery(this).removeClass('current');
                });

                jQuery(this).parent().addClass('current');	// set as current menu

                var url = jQuery(this).attr('href');
                if (jQuery(url).length > 0) {
                    jQuery('.addContBox .subcontent').hide();
                    jQuery(url).show();
                } else {
                    jQuery.post(url, function (data) {
                        jQuery('#contentwrapper').html(data);
                        jQuery('.stdtable input:checkbox').uniform();	//restyling checkbox
                    });
                }
                return false;
            });
        }


    }

    // Shortuct functions
    jAlert = function (message, title, callback) {
        $.alerts.alert(message, title, callback);
    }

    jConfirm = function (message, title, callback) {
        $.alerts.confirm(message, title, callback);
    };

    jPrompt = function (message, value, title, callback) {
        $.alerts.prompt(message, value, title, callback);
    };

    jAddCont = function (message, value, title, callback) {
        $.alerts.addCont(message, value, title, callback);
    };

})(jQuery);