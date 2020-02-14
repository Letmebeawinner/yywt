var zTreeObj = null;

var settings = {
    check: {
        enable: true,
        chkStyle: 'checkbox'
    },
    data: {
        key: {
            checked: 'roleHas',
            name: 'resourceName',
            title: 'resourceDesc'
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
 * 返回上一页
 */
function comeback() {
    window.location.href = document.referrer;
}

/**
 * 保存角色信息
 * 删除角色时roleId可以穿多个角色的id，使用","分割
 * 例如   1, 2, 3
 *
 * @param type 1 普通更新 2 禁用或恢复 3 删除角色
 * @param roleId 操作角色的id
 * @param obj 触发该事件的当前对象
 * @param roleName 角色名
 */
function saveRole(type, roleId, obj, roleName) {
    var url = null;
    var param = null;
    if (type == 3) {
        if (confirm('确定删除[' + roleName + ']吗?')) {
            url = '/admin/base/role/deleteRoles';
            param = {'ids': roleId};
        }
    } else {
        url = '/admin/base/role/addOrUpdateRole';
        if (type == 2) {
            if (confirm('确定' + obj.title + '角色[' + roleName + ']吗?')) {
                param = {'id': roleId, 'isAvailable': '2'};
            } else {
                return false;
            }
        } else {
            param = jQuery('#form').serialize();
        }
    }

    crud(url, param, 'POST', function (data) {
        if (type == 2) {
            var roleStatusOp = document.getElementById('roleStatusOp_' + roleId);
            var roleStatus = document.getElementById('roleStatus_' + roleId);
            if (roleStatusOp.title == '禁用') {
                roleStatusOp.innerHTML = roleStatusOp.title = '恢复';
                roleStatus.innerHTML = '禁用';
            } else {
                roleStatusOp.innerHTML = roleStatusOp.title = '禁用';
                roleStatus.innerHTML = '正常';
            }
        } else {
            window.location.href = basePath + data;
        }
    });
}

/**
 * 修改指定角色的权限
 * confirm : 当confirm = true时表示确定, confirm = false表示取消
 * @param roleName 角色名
 * @param roleId 角色的id
 */
function showRoleResources(roleName, roleId) {
    var url_getRoleResources = '/admin/base/role/getRoleResources';
    crud(url_getRoleResources, {'roleId': roleId}, 'POST', function (data) {
        jQuery.alerts._show(roleName + '权限管理', '<div class="addContBox" id="addContBox"><ul id="resources" class="ztree"></ul></div>', null, 'zTree', function (confirm) {
            if (confirm) {
                var paramValue = '';
                var checkedNodes = zTreeObj.getCheckedNodes(true);
                for (var i = 0; i < checkedNodes.length; i++) {
                    paramValue += checkedNodes[i]['id'];
                    if (i < checkedNodes.length) {
                        paramValue += ',';
                    }
                }
                var url_updateRoleResources = '/admin/base/role/updateRoleResources';
                var param = {'roleId': roleId, 'resourceIds': paramValue};
                crud(url_updateRoleResources, param, 'POST', function () {
                });
            }
        });
        var tree = jQuery('#resources');
        zTreeObj = jQuery.fn.zTree.init(tree, settings, data);
        zTreeObj.getNodesByFilter(function (node) {
            var checkStatus = node.getCheckStatus();
            if (checkStatus.half || checkStatus.checked) {
                zTreeObj.expandNode(node);
            }
        });
        // 修改弹窗的位置。距窗口上边距150px，左边距30%.
        jQuery('#popup_container').css({
            top: 150,
            left: '30%'
        });
    });
}