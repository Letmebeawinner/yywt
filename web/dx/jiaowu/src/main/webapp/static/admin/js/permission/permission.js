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

/*
 zTree的设置项
 */
var settings = {
    callback: {
        beforeDrop: function (treeId, treeNodes, targetNode, moveType) {
            var url = '/admin/base/permission/addOrUpdateResource';
            var parentId;
            if (moveType == 'inner') {
                parentId = targetNode['id'];
            } else {
                parentId = (targetNode['parentId'] == null ? 0 : targetNode['parentId']);
            }
            var param = {'resource.id': treeNodes[0]['id'], 'resource.parentId': parentId, 'isMove': '2'};
            crud(url, param, 'POST', function () {
                zTreeObj.moveNode(targetNode, treeNodes[0], moveType);
            });
            return false;
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
        showRemoveBtn: true
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