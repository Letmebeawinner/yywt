$(function () {
   getCode();
    hideErrorMsg();
});

/**
 * 登录入口
 */
function login() {
    // var params = jQuery("#login").serialize();
    
    var userName=jQuery("#username").val();
    if(userName==null||userName==""){
        showErrorMsg('请输入用户名')
        getCode()
        return false;
    }
    var password=jQuery("#password").val();
    if(password==null||password==""){
        showErrorMsg('请输入密码')
        getCode()
        return false;
    }
    var ranCode=jQuery("#ranCodeValue").val();
    if(ranCode==null||ranCode==""){
        showErrorMsg('请输入验证码')
        getCode()
        return false;
    }
    var codeName=jQuery("#codeName").val();

    // password=base64encode(userName+password);
    jQuery.ajax({
        url:'/admin/login.json',
        type:'post',
        dataType:'json',
        // data:params,
        data:{"userName":userName,"password":password,"ranCode":ranCode,"codeName":codeName},
        success:function (data) {
            if(data.code=='0'){
                //如果该用户所在部门为"单位管理员"、"组织部管理员"、"报名",则直接跳转到在线报名页面.
                /*if(data.data!=null&&data.data!=""){
                    if(data.data=="baoming"){
                        window.location.href="http://10.100.101.1:6682/admin/jiaowu/user/toCreateUser.json";
                    }else{
                        window.location.href="http://10.100.101.1:6682/admin/jiaowu/user/directToNewUserList.json";
                    }
                }else{
                    window.location.href='/admin/index.json';
                }*/
                window.location.href='/admin/index.json?sysKey='+data.data;
            }else{
                jQuery("#password").val("");
                jQuery("#ranCodeValue").val("");
                showErrorMsg(data.message);
            }
        },
        error:function (error) {
            showErrorMsg('系统错误，请稍后再操作')
        }
    });
}

/**
 * base64加密
 * @param str
 * @returns {string}
 */
function base64encode(str) {
    var base64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    var out, i, len;
    var c1, c2, c3;

    len = str.length;
    i = 0;
    out = "";
    while(i < len) {
        c1 = str.charCodeAt(i++) & 0xff;
        if(i == len)
        {
            out += base64EncodeChars.charAt(c1 >> 2);
            out += base64EncodeChars.charAt((c1 & 0x3) << 4);
            out += "==";
            break;
        }
        c2 = str.charCodeAt(i++);
        if(i == len)
        {
            out += base64EncodeChars.charAt(c1 >> 2);
            out += base64EncodeChars.charAt(((c1 & 0x3)<< 4) | ((c2 & 0xF0) >> 4));
            out += base64EncodeChars.charAt((c2 & 0xF) << 2);
            out += "=";
            break;
        }
        c3 = str.charCodeAt(i++);
        out += base64EncodeChars.charAt(c1 >> 2);
        out += base64EncodeChars.charAt(((c1 & 0x3)<< 4) | ((c2 & 0xF0) >> 4));
        out += base64EncodeChars.charAt(((c2 & 0xF) << 2) | ((c3 & 0xC0) >>6));
        out += base64EncodeChars.charAt(c3 & 0x3F);
    }
    return out;
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
// function getCode(){
//     jQuery("#ranCode").prop("src", "/kaptcha?random=" + Math.random());
// }

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