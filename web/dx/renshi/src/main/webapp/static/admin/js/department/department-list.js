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
    jQuery("select").val(0);
}

/*
 zTree的设置项
 */
var settings = {
    callback: {
        beforeDrop : function(treeId, treeNodes, targetNode,moveType) {
            if(moveType=='inner'){
                var param = {'department.id': treeNodes[0]['id'], 'department.parentId': targetNode['id'], 'isMove': '2'};
            }else{
                if(targetNode['parentId']==null){
                    targetNode['parentId']=0;
                }
                var param = {'department.id': treeNodes[0]['id'], 'department.parentId': targetNode['parentId'], 'isMove': '2'};
            }
            var url = '/admin/rs/addOrUpdateDepartment.json';
            crud(url, param, 'POST', function () {
                zTreeObj.moveNode(targetNode, treeNodes[0], moveType);
            });
            return false;
        },

        beforeEditName: function (treeId, treeNode) {
            var id = treeNode['id'];
            var url = '/admin/rs/getDepartmentById.json';
            crud(url, {"id":id}, 'POST', function (data) {
                var children = jQuery('.longinput');
                children.each(function () {
                    if(this.id=='departmentId'){
                       jQuery(this).val(data.department.id);
                   }
                   if(this.id=='departmentName'){
                       jQuery(this).val(data.department.departmentName);
                   }
                    if(this.id=='departmentDuty'){
                        jQuery(this).val(data.department.departmentDuty);
                    }
                    if(this.id=='departmentDesc'){
                        jQuery(this).val(data.department.departmentDesc);
                    }
                    if(this.id=='departmentAvailable'){
                        jQuery(this).val(data.department.departmentAvailable);
                    }
                    if(this.id=='sort'){
                        jQuery(this).val(data.department.sort);
                    }
                });
                var roles=data.roles;
                jQuery("#select").html("");
                if(roles!=null){
                    for(var i=0;i<roles.length;i++){
                        if(roles[i].ishave==1){
                            jQuery("#addRole").append("<option value='"+roles[i].id+"'>"+roles[i].roleName+"</option>")
                        }else{
                            jQuery("#select").append("<option value='"+roles[i].id+"'>"+roles[i].roleName+"</option>");
                        }
                    }
                }
            });
            return false;
        },


        beforeRemove: function (treeId, treeNode) {
            var message = '确定删除"' + treeNode['departmentName'] + '"及其子节点吗?';
            if (confirm(message)) {
                var id = treeNode['id'];
                var url = '/admin/rs/deleteDepartment.json';
                crud(url, {"id":id}, 'POST', function () {
                    zTreeObj.removeNode(treeNode);
                    romoveAll();
                });
            }
            return false;
        }
    },
    data: {
        key: {
            name: 'departmentName',
            title: 'departmentName',
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
            prev:true,
            next:true

        },
        enable: true,
        renameTitle: '修改部门',
        removeTitle: '删除部门',
        showRenameBtn: true,
        showRemoveBtn: true
    },

    view:{
        selectedMulti:true
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
 * 添加角色
 */
function add(){
   $("#addRole").append($("#select :selected"));
}
/**
 * 移除角色
 */
function romove(){
    $("#select").append($("#addRole :selected"));
}

/**
 * 清空
 */
function romoveAll(){
    jQuery(".longinput").val('');
    jQuery("select").val(0);
    $("#select").append($("#addRole option"));
}
