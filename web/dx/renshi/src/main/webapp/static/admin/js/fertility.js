/**
 * 表单验证
 */
function checkInput() {
    if(jQuery("#fileUrl").val()==""){
        alert("请添加相关文件");
        return false;
    }
    if(jQuery("#name").val()==""){
        alert("请添加申请人姓名");
        return false;
    }
    return true;
}

/**
 * 添加计生申请
 */
function addfertilityFormSubmit() {
    if(!checkInput()){
        return;
    }
    var date = jQuery("#addfertility").serialize();
    jQuery.ajax({
        url: "/admin/rs/addFertility.json",
        data: date,
        type: "post",
        dataType: "json",
        async: false,
        success: function (result) {
            if (result.code=="0") {
                alert(result.message);
                window.location.href = "/admin/rs/getFertilityList.json";
            } else {
                alert(result.message);
            }
        }
    });
}
/**
 * 修改计生申请
 */
function updatefertility() {
    if(!checkInput()){
        return;
    }
    var date = jQuery("#updatefertility").serialize();
    jQuery.ajax({
        url: "/admin/rs/updateFertility.json",
        data: date,
        type: "post",
        dataType: "json",
        async: false,
        success: function (result) {
            if (result.code=="0") {
                alert(result.message);
                window.location.href = "/admin/rs/getFertilityList.json";
            } else {
                alert(result.message);
            }
        }
    });
}
/**
 * 修改计生申请
 */
function passFertility(id) {
    if (confirm("确定此申请通过审核？")) {
        jQuery.ajax({
            url: "/admin/rs/updateFertility.json",
            data: {
                "fertility.id": id,
                "fertility.ifPass": 2
            },
            type: "post",
            dataType: "json",
            async: false,
            success: function (result) {
                if (result.code == "0") {
                    alert("操作成功！");
                    window.location.href = "/admin/rs/getFertilityList.json";
                } else {
                    alert(result.message);
                }
            }
        });
    }
}
/**
 * 清空表单
 */
function resetData(){
    jQuery(".longinput").val("");
}



/**
 * 执行查询
 */
function searchForm() {
    jQuery("#getFertilityList").submit();
}
/**
 * 清空搜索条件
 */
function emptyForm() {
    jQuery("input:text").val('');
    jQuery("select").val(0);
}
/**
 * 删除计生申请
 */
function delFertility(id) {
    if (confirm("删除后将无法恢复，是否继续")) {
        jQuery.ajax({
            url: "/admin/rs/deleteFertility.json?id="+id,
            data: {},
            type: "post",
            dataType: "json",
            async: false,
            success: function (result) {
                if (result.code=="0") {
                    window.location.reload();
                } else {
                    alert(result.message);
                    return;
                }
            }
        });
    }
}

var passReason="";
/**
 * 成果审批未通过
 * @param resultId
 */
function notPassFertility(id){
    jQuery.alerts._show('输入原因',null, null,'addCont',function (confirm) {
        if(confirm){
            if(passReason!="" && passReason!=null ){
                jQuery.ajax({
                    url:"/admin/rs/updateFertility.json",
                    data: { "fertility.id": id,
                        "fertility.ifPass": 3
                    },
                    type: "post",
                    dataType: "json",
                    async: false,
                    success: function (result) {
                        if (result.code == "0") {
                            sendInfo(result);
                        } else {
                            alert(result.message);
                            return;
                        }
                    }
                });
            }else{
                alert("请输入原因!");
                notPassFertility(id);
            }
        }
    });
    jQuery('#popup_message').html("<textarea cols='120' rows='8' id='passReason' onchange='setPassReason()'></textarea>");
    // 修改弹窗的位置。距窗口上边距150px，左边距30%.
    jQuery('#popup_container').css({
        top: 250,
        left: '30%',
        overflow:'hidden'
    });
    jQuery('#popup_container').css("max-height","800px");
    jQuery('#popup_message').css("max-height","600px");
    jQuery('#popup_message').css('overflow-y','scroll');
}
/**
 * 发送站内信
 * @param taskId
 */
function sendInfo(result){
    jQuery.ajax({
        url: "/admin/rs/sendInfo.json" ,
        data: {"content":"你提交的"+result.data.name+"的计生申请未通过审核，原因："+passReason,
            "id":result.data.employeeId
        },
        type: "post",
        dataType: "json",
        async: false,
        success: function (result) {
            if (result.code == "0") {
                alert(result.message);
                window.location.href = "/admin/rs/toApprovalFertilityList.json";
            } else {
                alert(result.message);
                return;
            }
        }
    });
}
function setPassReason(){
    passReason=jQuery("#passReason").val()
}