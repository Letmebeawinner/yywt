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
            var url = '/admin/base/department/addOrUpdateDepartment';
            crud(url, param, 'POST', function () {
                zTreeObj.moveNode(targetNode, treeNodes[0], moveType);
            });
            return false;
        },

        beforeEditName: function (treeId, treeNode) {
            var id = treeNode['id'];
            var url = '/admin/base/department/getDepartmentById.json';
            crud(url, {"id":id}, 'POST', function (data) {
                var children = jQuery('.longinput');
                children.each(function () {
                    if(this.id=='departmentId'){
                       jQuery(this).val(data.department.id);
                   }
                   if(this.id=='departmentName'){
                       jQuery(this).val(data.department.departmentName);
                   }
                    if(this.id=='departmentDesc'){
                        jQuery(this).val(data.department.departmentDesc);
                    }
                    if(this.id=='departmentAvailable'){
                        jQuery(this).val(data.department.departmentAvailable);
                    }
                });
                var roles=data.roles;
                jQuery("#addRole").html("");
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
                var url = '/admin/base/department/deleteDepartment.json';
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
 * 保存权限资源
 * @since 2016-12-13
 */
function saveResource() {
  var departmentId=jQuery("#departmentId").val();
  var arr=jQuery("#form").serialize();
  jQuery.ajax({
      url:'/admin/base/department/addOrUpdateDepartment.json',
      type:'post',
      data:arr,
      dataType:'json',
      success:function(result){
          if(result.code==0){

              if(departmentId==''){
                  zTreeObj.addNodes(null,result.data);
              }else {
                  var targetNode = zTreeObj.getNodeByParam('id', departmentId);
                  if (targetNode != null) {
                      for (var attr in result.data) {
                          targetNode[attr] = result.data[attr];
                      }
                      zTreeObj.updateNode(targetNode);

                  }
              }
              romoveAll();
              jAlert("操作成功","提醒");
          }else{
              romoveAll();
              jAlert(result.message,"提醒")
          }

      },
      error:function () {},
  });
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
