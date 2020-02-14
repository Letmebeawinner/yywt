<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>类型列表</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/ztree/css/zTreeStyle.css" type = "text/css">
    <script charset="utf-8" type="text/javascript" src="${ctx}/static/ztree/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/ztree/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" src="${ctx}/static/ztree/jquery.ztree.exedit-3.5.js"></script>
    <script type="text/javascript" src="${ctx}/static/ztree/jquery.ztree.excheck-3.5.js"></script>
    <script type="text/javascript">
        var data =${formTypeList};
        var setting = {
            check: {
                enable: true,
                chkboxType : { "Y" : "s", "N" : "s" }
            },
            view: {
                showLine: true,
                showIcon: true,
                selectedMulti: false,
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: 'id',
                    pIdKey: 'parentId',
                    rootPid: ''
                },
                key: {
                    name: 'name',
                    title: 'name'
                }
            },
            callback: {
                onClick: treeOnclick
            }
        };

        //切换专业时操作
        function treeOnclick(e, treeId, treeNode) {
            window.location.href="${ctx}/admin/oa/toUpdateFormType.json?id="+treeNode.id;
        }

        //取消全部选中
        function checkNodeFalse() {
            var zTree = jQuery.fn.zTree.getZTreeObj("subject_ztreedemo");
            zTree.checkAllNodes(false);
        }

        jQuery(function() {
            var treeObj = jQuery.fn.zTree.init(jQuery("#subject_ztreedemo"), setting, data);
            treeObj.expandAll(true);
        });


        function addFormType() {
            window.location.href = "${ctx}/admin/oa/toAddFormType.json";
        }


        function delFormType() {
            if (confirm("确定删除选中的分类？")) {
                var zTree = jQuery.fn.zTree.getZTreeObj("subject_ztreedemo"),
                        checkCount = zTree.getCheckedNodes(true).length;
                if(checkCount>0){
                    var checkedNodes=zTree.getCheckedNodes(true);
                    var arrayObj = new Array()
                    jQuery.each(checkedNodes,function(index,val){
                        arrayObj.push(val.id);
                    });
                    jQuery("#ids").val(arrayObj.join(","));
                    jQuery("#updateFormTypeForm").submit();
                }else{
                    alert("未选中分类");
                }
            } else {
                return;
            }
        }
    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab">
        <h1 class="pagetitle">表单类型</h1>
    </div>
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于表单分类树形显示；<br>
        3.新建：点击<span style="color:red">新建</span>，新建表单分类；<br>
        3.删除：点击<span style="color:red">删除</span>，删除选中表单分类；<br>
    </div>

    <div id="contentwrapper" class="contentwrapper">

        <div id="subjectmenuContent" class="menuContent" style="position: inherit">
            <ul id="subject_ztreedemo" class="ztree" style="margin-top:0; width:160px;"></ul>
        </div>
    </div>
</div>

<div class="mt20">
    <form action="${ctx}/admin/oa/deleteFormType.json" method="post" id="updateFormTypeForm">
        <input type="hidden" id="ids" name="ids" value=""/>
        <table width="100%" cellspacing="0" cellpadding="0" border="0" class="commonTab01">
            <thead>

            </thead>
            <tbody>
            <tr>
                <td width="20%" >
                    <div id="ztreedemo" class="ztree"></div>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="button" value="新建分类" onclick="addFormType();"  class="btn btn-danger"/>
                    <input type="button" value="删除选中分类" onclick="delFormType();"  class="btn btn-danger"/>
                    <input type="button" onclick="checkNodeFalse();"  value="清空"  class="btn btn-danger"/>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>

</body>
</html>