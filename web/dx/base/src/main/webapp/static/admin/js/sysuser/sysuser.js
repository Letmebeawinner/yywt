var settings1 = {
    check: {
        enable: true,
        chkStyle: 'radio',
        radioType: "all",
        chkboxType: {"Y": "s", "N": "s"}
    },
    data: {
        key: {
            checked: 'isHave',
            name: 'departmentName',
            title: 'departmentDesc'
        },
        simpleData: {
            enable: true,
            idKey: 'id',
            pIdKey: 'parentId',
            rootPid: null
        }
    }
};

/**
 * 加载部门树形结构
 * @param sysUserId  管理员用户id
 */
function showSysUserDepartment(sysUserId) {
    var url_getRoleResources = '/admin/base/sysuser/getDepartmentTree.json';
    crud(url_getRoleResources, {"sysUserId": sysUserId}, 'POST', function (data) {
        jQuery.alerts._show('部门管理', '<div class="addContBox" id="addContBox"><ul id="resources" class="ztree"></ul></div>', null, 'dialog', function (confirm) {
            if (confirm) {
                var paramValue = '';
                var checkedNodes = zTreeObj.getCheckedNodes(true);
                for (var i = 0; i < checkedNodes.length; i++) {
                    paramValue += checkedNodes[i]['id'];
                    if (i < checkedNodes.length) {
                        paramValue += ',';
                    }
                }
                if (paramValue) {
                    var url_updateRoleResources = '/admin/base/sysuser/updateUserDepartment';
                    var param = {'userId': sysUserId, 'departmentIds': paramValue};
                    crud(url_updateRoleResources, param, 'POST', function () {
                        alert("操作成功");
                        window.location.reload();
                    });
                }
            }
        });
        var tree = jQuery('#resources');
        zTreeObj = jQuery.fn.zTree.init(tree, settings1, data);
        zTreeObj.expandAll(true);
        // 修改弹窗的位置。距窗口上边距150px，左边距30%.
        jQuery('#popup_container').css({
            top: 70,
            left: '20%'
        });
    });
}

/**
 * 修改用户状态
 * @param id
 * @param status
 */
function updateStatus(id, obj) {
    if (jQuery(obj).text() == '恢复') {//恢复用户
        if (window.confirm("确定恢复用户吗？")) {
            crud('/admin/base/sysuser/deleteSysUser.json', {"id": id, "status": 0}, "POST", function (data) {
                jQuery(obj).text("锁定");
                var _id = "#status_" + id;
                jQuery(_id).text("正常");
            });
        }
    } else {
        if (window.confirm("确定锁定用户吗？")) {
            crud('/admin/base/sysuser/deleteSysUser.json', {"id": id, "status": 1}, "POST", function (data) {
                jQuery(obj).text("恢复");
                var _id = "#status_" + id;
                jQuery(_id).text("锁定");
            });
        }
    }
}

/**
 * 添加系统管理员用户
 */
function save() {
    var sysUser = jQuery("#addUser").serialize();
    jQuery.ajax({
        url: basePath + '/admin/base/sysuser/addSysUser.json',
        type: 'POST',
        data: sysUser,
        dataType: 'json',
        success: function (result) {
            if (result.code == 0) {
                alert(result.message);
                window.location.href = basePath + '/admin/base/sysuser/queryUserList.json';
                jQuery(".reset.radius2").click();
            } else {
                alert(result.message);
            }
        }
    });
}

//----------------------------------------------------------------------------------------
var bind;
jQuery(document).ready(function () {
    bind = jQuery('.bindingpeople').html();
    jQuery('#userType').change(function () {
        //类型 1管理员，2教职工，3学员
        var queryuserType = this.value;
        if (queryuserType == 1 || queryuserType == 0) {
            directAddition();
        } else {
            bindingTeacherStudeng(queryuserType);
        }
    })
});

/**
 * 获取学员和老师列表列表
 */
function getTeacherStudentList(queryType) {
    jQuery.ajax({
        url: "/admin/base/sysuser/queryTeacherStudentList.json",
        data: {"userType": queryType},
        type: "post",
        dataType: "html",
        success: function (result) {
            var title;
            if (queryType == 2) title = '教职工列表';
            else if (queryType == 3) title = '学员列表';
            jQuery.alerts._show(title, result, null, "dialog", function (confirm) {
                var administratorId = jQuery('input[name="admin"]:checked').val();
                if (confirm) {
                    if (administratorId == null || administratorId == "undefined" || administratorId == 0) {
                        alert("请选择用户");
                        getTeacherStudentList(queryType);
                    } else {
                        queryAdministratorName(administratorId, queryType);
                    }
                } else {
                    directAddition();
                }
            });
        }
    });
}

//=======================================================
//获取选取人姓名，linkId，类型，
function queryAdministratorName(id, queryType) {
    jQuery("#addUserName").text(jQuery('#administratorName_' + id).text()).show();
    jQuery("#linkId").val(id);
    jQuery("#queryuserType").val(queryType);
    jQuery("#deletebinding").show();
}

/**
 * 删除绑定的人
 */
function deletequeryAdministrator() {
    directAddition();
    /*//jQuery('select').prop('selectedIndex', 0);*/
}

/**
 * 根据queryType判断弹出按钮
 * @param queryType  2：教职工  3：学员
 */
function bindingTeacherStudeng(queryType) {
    if (queryType == 2) {
        jQuery(".bindingpeople").show();
        jQuery("#teacherList").show();
        jQuery("#studentList").hide();
        jQuery("#deletebinding").hide();
    }
    if (queryType == 3) {
        jQuery(".bindingpeople").show();
        jQuery("#teacherList").hide();
        jQuery("#studentList").show();
        jQuery("#deletebinding").hide();
    }
}

/**
 * 当queryuserType等于1的时候，或删除绑定人时，或取消时用。
 */
function directAddition() {
    jQuery(".bindingpeople").hide();
    jQuery("#addUserName").text("");
    jQuery("#linkId").val("0");
    var ut=jQuery("#userType").val();
    if(ut!="5"){
        jQuery("#userType").val(1);
        jQuery("#queryuserType").val(1);
    }
    
}

/**
 *重置表单
 */
function formResetAddSysuser() {
    jQuery('.bindingpeople').hide();
}

function deleteSysUser(id) {
    if (window.confirm("确认要删除该用户吗？")) {
        jQuery.ajax({
            url: "/admin/base/sysuser/deleteSysUserById.json",
            data: {"id": id},
            type: "post",
            dataType: "json",
            success: function (result) {
                if (result.code==0) {
                    alert(result.message);
                    window.location.href = "/admin/base/sysuser/queryUserList.json";
                } else {
                    alert(result.message);
                    return;
                }
            }
        });
    }
}

