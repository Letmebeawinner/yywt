var settings = {
    check: {
        enable: true,
        chkStyle: 'checkbox'
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
    crud(url_getRoleResources, {"sysUserId":sysUserId}, 'POST', function (data) {
        jQuery.alerts._show('部门管理', '<div class="addContBox" id="addContBox"><ul id="resources" class="ztree"></ul></div>', data, 'zTree', function (confirm) {
            if (confirm) {
                var paramValue = '';
                var checkedNodes = zTreeObj.getCheckedNodes(true);
                for (var i = 0; i < checkedNodes.length; i++) {
                    paramValue += checkedNodes[i]['id'];
                    if (i < checkedNodes.length) {
                        paramValue += ',';
                    }
                }
                if(paramValue){
                    var url_updateRoleResources = '/admin/base/sysuser/updateUserDepartment';
                    var param = {'userId': sysUserId, 'departmentIds': paramValue};
                    crud(url_updateRoleResources, param, 'POST', function () {
                        jAlert("操作成功","提醒")
                    });
                }
            }
        });
        var tree = jQuery('#resources');
        zTreeObj = jQuery.fn.zTree.init(tree, settings, data);
        zTreeObj.expandAll(true);
    });
}

/**
 * 修改用户状态
 * @param id
 * @param status
 */
function updateStatus(id,obj){
    if(jQuery(obj).text()=='恢复'){//恢复用户
        jConfirm("确定恢复用户吗？","提醒",function(result){
           if(result){
               crud('/admin/base/sysuser/deleteSysUser.json',{"id":id,"status":0},"POST",function(data){
                      jQuery(obj).text("锁定");
                      var _id="#status_"+id;
                      jQuery(_id).text("正常");
               });
           }
        });
    }else{
        jConfirm("确定锁定用户吗？","提醒",function(result){
            if(result){
                crud('/admin/base/sysuser/deleteSysUser.json',{"id":id,"status":1},"POST",function(data){
                        jQuery(obj).text("恢复");
                        var _id="#status_"+id;
                        jQuery(_id).text("锁定");
                });
            }
        });
    }
}