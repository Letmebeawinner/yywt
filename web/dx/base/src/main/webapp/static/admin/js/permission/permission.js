/**
 * 执行查询
 */
function searchForm() {
    jQuery("#searchForm").submit();
}

/**
 * 清空搜索条件
 */
function emptyForm() {
    jQuery("input:text").val('');
    jQuery("select").val(1);
}
var nodeLevels = 0;
//递归节点，计算其级数
function recursionNodes(node) {
    nodeLevels++;
    var children = node.children;
    if (children && children != null && children.length > 0) {
        recursionNodes(children[0]);
    }
}
/*
 zTree的设置项
 */
var settings = {
    callback: {
        beforeDrop: function (treeId, treeNodes, targetNode, moveType) {
            var level = targetNode.level + 1;
            if (level >= 3) {
                alert('权限树最多只支持三级！');
                return false;
            }
            recursionNodes(treeNodes[0]);
            var _levels = nodeLevels + level;
            nodeLevels = 0;
            if (_levels > 3) {
                alert('权限树最多只支持三级！');
                return false;
            }
            //不同系统类型判断提示
            if (targetNode.resourceSite != treeNodes[0].resourceSite) {
                if (!confirm("移动的节点和目标节点的所属系统类型不一致，确认要移动？")) {
                    return false;
                }
            }
            var url = '/admin/base/permission/updateResourceParent';
            var parentId = 0;
            if (moveType == 'inner') {
                parentId = targetNode['id'];
            } else {
                parentId = (targetNode['parentId'] == null ? 0 : targetNode['parentId']);
            }
            var isok = false;
            var param = {
                'id': treeNodes[0]['id'],
                'parentId': parentId,
                'parentResourceSite': targetNode['resourceSite']
            };
            crud(url, param, 'POST', function () {
                isok = true;
                //得到权限树，将移动的节点(treeNodes)更新到其父节点(targetNode)下
                var treeObj = jQuery.fn.zTree.getZTreeObj("resources");
                for (var i = 0; i < treeNodes.length; i++) {
                    treeObj.moveNode(targetNode, treeNodes[i], moveType, true);
                }
            });
            return isok;
        },
        beforeEditName: function (treeId, treeNode) {
            var id = treeNode['id'];
            var url = '/admin/base/permission/getResource';
            crud(url, {'resourceId': id}, 'POST', function (data) {
                var children = jQuery('#form').find('[id^=resource]');
                children.each(function (index) {
                    var input = children[index];
                    if (input.id == 'resourceId') {
                        jQuery(input).val(data['id']);
                    } else if (input.id == 'resourceParentId') {
                        jQuery(input).val(data['parentId']);
                        if (data['parentId'] == 0) {
                            jQuery('#resourceSite').attr('disabled', false);
                        } else {
                            jQuery('#resourceSite').attr('disabled', true);
                        }
                    } else if (input.id == 'resourceStyleName') {
                        jQuery(input).val(data['styleName']);
                    } else {
                        jQuery(input).val(data[input.id]);
                    }
                });
            });
            return false;
        },
        beforeRemove: function (treeId, treeNode) {
            var message = '确定删除"' + treeNode['resourceName'] + '"及其子节点吗?';
            if (confirm(message)) {
                var id = treeNode['id'];
                var url = '/admin/base/permission/deleteResource';
                crud(url, {'resourceId': id}, 'POST', function () {
                    zTreeObj.removeNode(treeNode);
                });
            }
            return false;
        }
    },
    data: {
        key: {
            name: 'resourceName',
            title: 'resourceDesc',
            url: 'url'
        },
        simpleData: {
            enable: true,
            idKey: 'id',
            pIdKey: 'parentId',
            rootPid: null
        }
    },
    edit: {
        drag: {
            enable: true,
            prev: true,
            next: true
        },
        enable: true,
        renameTitle: '修改权限资源',
        removeTitle: '删除权限资源',
        showRenameBtn: true,
        showRemoveBtn: function (treeId, treeNode) {
            return !((treeNode.parentId == null) || (treeNode.parentId == 0));
        }
    },
    view: {
        fontCss: function (treeId, treeNode) {
            if (treeNode.resourceType == 2) {
                return {color: 'red'};
            } else {
                return {};
            }
        }
    }
};


/**
 * 构建zTree
 * @param id ul元素的id
 * @param data zTree的数据
 */
function buildTree(id, data) {
    var tree = jQuery(id);
    return jQuery.fn.zTree.init(tree, settings, data);
}

/**
 * 保存权限资源
 * @since 2016-12-13
 */
function saveResource() {
    var isInteger = /^[0-9]+$/;
    if (!isInteger.test(jQuery('#resourceOrder').val())) {
        alert('权限资源排序为≥0的整数');
        return;
    }
    var form = jQuery('#form');
    var url = '/admin/base/permission/addOrUpdateResource';
    var param = form.serialize();
    param += '&resourceSite=' + jQuery('#resourceSite').val();
    crud(url, param, 'POST', function (data) {
        var targetNode = zTreeObj.getNodeByParam('id', data['id']);
        if (targetNode != null) {
            for (var attr in data) {
                targetNode[attr] = data[attr];
            }
            zTreeObj.updateNode(targetNode);
        } else {
            zTreeObj.addNodes(null, data);
        }
        jQuery('input[type=reset]').click();
    });
}