$(function () {
   getCode();
    hideErrorMsg();
});
/**
 * 登录入口
 */
function login() {
    var params = jQuery("#login").serialize();
    jQuery.ajax({
        url:'/admin/login.json',
        type:'post',
        dataType:'json',
        data:params,
        success:function (data) {
            if(data.code=='0'){
                window.location.href='/admin/index.json';
            }else{
                showErrorMsg(data.message)
            }
        },
        error:function (error) {
            showErrorMsg('系统错误，请稍后再操作')
        }
    });
}
/**
 * 显示登录错误信息
 * @param message
 */
function showErrorMsg(message) {
    jQuery("#login-error-msg").text(message);
    jQuery("#login-error-msg").parent().parent().show();
}

/**
 * 隐藏登录错误信息
 */
function hideErrorMsg() {

    jQuery("input:text,input:password").keyup(function(){
        jQuery("#login-error-msg").parent().parent().hide();
    });
}
/**
 * 获取验证码
 */
function getCode(){
    var codeName = "codeName_"+new Date().getTime();
    $("#codeName").val(codeName);
    $("#ranCode").attr("src","/ran/random.json?codeName="+codeName);
}

/**
 * 检测回车
 * @param event
 */
function  enterSubmit(event){
    var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
    if (keyCode == 13) {
        login();
    }
}