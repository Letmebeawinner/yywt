var data
var employeeIds;
var countrysideId;
/**
 * 教职工选择
 */
function addEmployee(id) {
    countrysideId=id;
    jQuery.ajax({
        type:"post",
        url:"/admin/oa/ajax/union/selectEmployeeList.json",
        data:data,
        dataType:"text",
        async: false,
        success:function (result) {
            jQuery.alerts._show('选择教职工',null, null,'addCont',function (confirm) {
                if(confirm){
                    if(employeeIds!=""){
                        jQuery.ajax({
                            type:"post",
                            url:"/admin/oa/addCountrysideEmployee.json",
                            data:{"countrysideEmp.countrysideId":id,
                                "employeeIds":employeeIds
                            },
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
                        alert("没有选择任何教职工！");
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
            jQuery('#popup_container').css("max-width","600px");
            jQuery('#popup_message').css("max-height","400px");
            jQuery('#popup_message').css('overflow-y','scroll');
        }
    });
}
/**
 * 执行教职工查询
 */
function _searchForm() {
    employeeIds="";
    data=jQuery("#employeeList").serialize();
    addEmployee(countrysideId);
}
/**
 * 清空教职工搜索条件
 */
function _emptyForm() {
    jQuery(".hasDatepicker").val('');
}
/**
 * 得到教职工id串
 */
function checkboxClick() {
    employeeIds="";
    jQuery('.employeeId:checked').each(function () {
        employeeIds+=jQuery(this).val()+' ';
    });
}

/**
 * 表单验证
 */
function checkInput() {
    if(jQuery("#name").val()==""){
        alert("请添加姓名");
        return false;
    }
    if(jQuery("#place").val()==""){
        alert("请添加下乡地点");
        return false;
    }
    if(jQuery("#content").val()==""){
        alert("请添加下乡内容");
        return false;
    }
    if(jQuery("#beginTime").val()==""){
        alert("请添加开始时间");
        return false;
    }
    if(jQuery("#endTime").val()==""){
        alert("请添加结束时间");
        return false;
    }
    if(jQuery("#endTime").val() < jQuery("#beginTime").val()){
        alert("结束时间不能小于开始时间");
        return false;
    }
    return true;
}
/**
 * 添加下乡
 */
function addcountrysideFormSubmit() {
    if(!checkInput()){
        return;
    }
    var date = jQuery("#addcountryside").serialize();
    jQuery.ajax({
        url: "/admin/oa/addCountryside.json",
        data: date,
        type: "post",
        dataType: "json",
        async: false,
        success: function (result) {
            if (result.code=="0") {
                alert(result.message);
                window.location.href = "/admin/oa/getCountrysideList.json";
            } else {
                alert(result.message);
            }
        }
    });
}

/**
 * 修改下乡
 */
function updatecountryside() {
    if(!checkInput()){
        return;
    }
    var date = jQuery("#updatecountryside").serialize();
    jQuery.ajax({
        url: "/admin/oa/updateCountryside.json",
        data: date,
        type: "post",
        dataType: "json",
        async: false,
        success: function (result) {
            if (result.code=="0") {
                alert(result.message);
                window.location.href = "/admin/oa/getCountrysideList.json";
            } else {
                alert(result.message);
            }
        }
    });
}

/**
 * 清空下乡表单
 */
function resetData(){
    jQuery(".longinput").val("");
}



/**
 * 执行下乡查询
 */
function searchForm() {
    jQuery("#getCountrysideList").submit();
}
/**
 * 清空下乡搜索条件
 */
function emptyForm() {
    jQuery("input:text").val('');
    jQuery("select").val(0);
}

/**
 * 删除下乡
 */
function delCountryside(countrysideId) {
    if (confirm("删除后将无法恢复，是否继续")) {
        jQuery.ajax({
            url: "/admin/oa/deleteCountryside.json?id="+countrysideId,
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