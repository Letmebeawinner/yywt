
/**
 * 获取登录用户信息
 */
function querySysUserInfo(){
    var url=basePath+"/admin/base/querySysUserInfo.json?callback=?";
    jQuery.getJSON(url,function(data){
        if(data!=null){
            //user-name-h
            //email
            jQuery(".user-name-h").text(data.userName);
            jQuery("#user-email").text(data.email);

        }
    });
}

//跳转模块菜单
function goToMessage(baseUrl,smsUrl) {
    jQuery.ajax({
        url: baseUrl+"/admin/ajax/switchSystem.json",
        type: "post",
        dataType: "json",
        xhrFields: {
            withCredentials: true // 这里设置了withCredentials
        },
        data: {
            "sysKey" : "SMS"
        },
        success: function(result) {
            if (result.code=="0") {
                window.location.href = smsUrl+"/admin/sms/info/queryInfoList.json";
            } else {
                alert(result.message);
                return;
            }
        }
    });
}
