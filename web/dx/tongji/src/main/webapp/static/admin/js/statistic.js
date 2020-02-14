/**
 * 提交表单
 *
 * @author sk
 * @since 2017-02-28
 */
function searchForm() {
    jQuery('#searchForm').submit();
}

function emptyForm() {
    jQuery('select').val(0);
    var year = new Date().getFullYear();
    jQuery('#startYear').val(year);
    jQuery('#startTimePicker').val('');
    jQuery('#endTimePicker').val('');
}

var options = {
    changeYear: true,
    changeMonth: true,
    dateFormat: 'yy-mm-dd',
    yearRange: '1900:' + new Date().getFullYear()
};

jQuery(document).ready(function () {
    jQuery("#startTimePicker").datepicker(options);
    jQuery('#endTimePicker').datepicker(options);
});