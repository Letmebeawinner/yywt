/**
 * Created by yzl on 2017-01-18.
 */
/**
 * 桌面选择
 */
var data=data;
var functionsIds;
function addFunction() {
    jQuery.ajax({
        type:"post",
        url:"/admin/oa/ajax/toAddFunction.json",
        data:data,
        dataType:"text",
        async: false,
        success:function (result) {
            jQuery.alerts._show('选择功能列表',null, null,'addCont',function (confirm) {
                if(confirm){
                    if(functionsIds){
                        jQuery.ajax({
                            type:"post",
                            url:"/admin/oa/updateDesk.json",
                            data:{'functionsIds': functionsIds},
                            dataType:"json",
                            success:function (result) {
                                if(result.code=="0"){
                                    alert("操作成功!");
                                    window.location.reload();
                                }else {
                                    alert(result.message);
                                }
                            }
                        });
                    }else{
                        alert("没有选择！");
                    }

                }
            });
            jQuery('#popup_message').html(result);
            // 修改弹窗的位置。距窗口上边距150px，左边距30%.
            jQuery('#popup_container').css({
                top: 50,
                left: '30%',
                overflow:'hidden'
            });
            jQuery('#popup_container').css("max-height","800px");
            jQuery('#popup_message').css("max-height","600px");
            jQuery('#popup_message').css('overflow-y','scroll');
        }
    });
}



/**
 * 得到教职工id串
 */
function functionClick() {
    functionsIds="";
    jQuery('.function:checked').each(function () {
        functionsIds+=jQuery(this).val()+',';
    });
}