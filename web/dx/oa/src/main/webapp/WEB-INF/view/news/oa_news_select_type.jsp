<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>选择信息分类</title>
</head>
<body>

    <div style="display: table-cell; width: 100%; padding-left: 10%">
        <ul id="resources" class="ztree mt5"></ul>
    </div>

<script>
    var settings = {
        callback: {
            onClick: function(event ,treeId, treeNode) {
                if (treeNode) {
                    if(treeNode.children) {
                        alert("只能选择子节点");
                    } else {
                        jQuery("#classId").val(treeNode.id);
                        jQuery("#className").html(treeNode.title);
                    }
                }
            }
        },
        data: {
            key: {
                name: 'title',
                title: 'title'
            },
            simpleData: {
                enable: true,
                idKey: 'id',
                pIdKey: 'parentID',
                rootPid: null
            }
        }
    };

    function getResource() {
        var resource = "";
        jQuery.ajax({
            url: informationPath + "/queryInfoClassList.json",
            type: "get",
            dataType: "json",
            cache: false,
            async: false,
            success: function(result) {
                resource = result.data;
            }
        });
        return resource;
    }
    var zTreeObj;
    jQuery(document).ready(function () {
        var resources = getResource();
        zTreeObj = buildTree('#resources', eval("(" + resources + ")"));
    });

    /**
     * 构建zTree
     * @param id ul元素的id
     * @param data zTree的数据
     */
    function buildTree(id, data) {
        var tree = jQuery(id);
        return jQuery.fn.zTree.init(tree, settings, data);
    }

</script>
</body>
</html>