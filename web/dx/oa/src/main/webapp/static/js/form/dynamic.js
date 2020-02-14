/**
 * 动态Form办理功能
 */
jQuery(function() {

    jQuery('.handle').click(handle);

});

/**
 * 打开办理对话框
 */
function handle() {
    var jQueryele = jQuery(this);

    // 当前节点的英文名称
    var tkey = jQuery(this).attr('tkey');

    // 当前节点的中文名称
    var tname = jQuery(this).attr('tname');

    // 任务ID
    var taskId = jQuery(this).attr('tid');

    jQuery('#handleTemplate').html('').dialog({
        modal: true,
        width: jQuery.common.window.getClientWidth() * 0.8,
        height: jQuery.common.window.getClientHeight() * 0.9,
        title: '办理任务[' + tname + ']',
        open: function() {
            readForm.call(this, taskId);
        },
        buttons: [{
            text: '提交',
            click: function() {
                jQuery('.formkey-form').submit();
            }
        }, {
            text: '关闭',
            click: function() {
                jQuery(this).dialog('close');
            }
        }]
    });
}


/**
 * 读取任务表单
 */
function readForm(taskId) {
    var dialog = this;

    // 读取启动时的表单
    jQuery.get(ctx + '/task/form/' + taskId, function(form) {
        // 获取的form是字符行，html格式直接显示在对话框内就可以了，然后用form包裹起来
        jQuery(dialog).html(form).wrap("<form class='formkey-form' method='post' />");

        var jQueryform = jQuery('.formkey-form');

        // 设置表单action
        jQueryform.attr('action', ctx + '/form/formkey/task/complete/' + taskId);

        // 初始化日期组件
        jQueryform.find('.datetime').datetimepicker({
            stepMinute: 5
        });
        jQueryform.find('.date').datepicker();

        // 表单验证
        jQueryform.validate(jQuery.extend({}, jQuery.common.plugin.validator));
    });
}