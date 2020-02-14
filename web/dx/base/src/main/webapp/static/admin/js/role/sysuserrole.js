var zTreeObj = null;

var settings = {
    check: {
        enable: true,
        chkStyle: 'checkbox'
    },
    data: {
        key: {
            checked: 'isHave',
            name: 'roleName',
            title: 'roleDesc'
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
 * 加载角色树形结构
 * @param sysUserId  管理员用户id
 */
function showSysUserRole(sysUserId) {
    var url_getRoleResources = '/admin/base/role/getRoleTree.json';
    crud(url_getRoleResources, {"sysUserId": sysUserId}, 'POST', function (data) {
        jQuery.alerts._show('角色管理', '<div class="addContBox" id="addContBox"><ul id="resources" class="ztree"></ul></div>', null, 'dialog', function (confirm) {
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
                    var url_updateRoleResources = '/admin/base/role/updateUserRole';
                    var param = {'userId': sysUserId, 'roleIds': paramValue};
                    crud(url_updateRoleResources, param, 'POST', function () {
                        alert("操作成功");
                    });
                }
            }
        });
        var tree = jQuery('#resources');
        zTreeObj = jQuery.fn.zTree.init(tree, settings, data);
        zTreeObj.expandAll(true);
        // 修改弹窗的位置。距窗口上边距150px，左边距30%.
        jQuery('#popup_container').css({
            top: 70,
            left: '30%'
        });
    });
}
























