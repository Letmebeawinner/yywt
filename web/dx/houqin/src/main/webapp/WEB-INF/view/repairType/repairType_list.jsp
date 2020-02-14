<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>维修类型列表</title>
    <link rel="stylesheet" href="${ctximg}/static/css/zTreeStyle.css?" type="text/css"/>
    <script type="text/javascript" src="${ctximg}/static/ztree/js/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" src="${ctximg}/static/ztree/js/jquery.ztree.excheck-3.5.js"></script>
    <script type="text/javascript">

        //ztree start
        var setting = {
            check: {
                enable: true,
                chkboxType: {"Y": "s", "N": "s"}
            },
            view: {
                showLine: true,
                showIcon: true,
                selectedMulti: false
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
        var treedata =${repairTypeList};

        function treeOnclick(e, treeId, treeNode) {
            window.location.href = "${ctx}/admin/houqin/toUpdateRepairType.json?id=" + treeNode.id;
        }

        function treeonCheck(e, treeId, treeNode) {
            var zTree = jQuery.fn.zTree.getZTreeObj("ztreedemo"),
                checkCount = zTree.getCheckedNodes(true).length,
                nocheckCount = zTree.getCheckedNodes(false).length,
                changeCount = zTree.getChangeCheckedNodes().length;
            var getCheckedNodes = zTree.getCheckedNodes(true);
        }

        //取消全部选中
        function checkNodeFalse() {
            var zTree = jQuery.fn.zTree.getZTreeObj("ztreedemo");
            zTree.checkAllNodes(false);
        }

        jQuery().ready(function () {
            jQuery.fn.zTree.init(jQuery("#ztreedemo"), setting, treedata);
            var zTree = jQuery.fn.zTree.getZTreeObj("ztreedemo");
            zTree.expandAll(true);//展开全部

            //$("#checkAllFalse").bind("click", {type:"checkAllFalse"}, checkNodeFalse);
        });

        //ztree end

        //删除组提交
        function del() {
            if (confirm("确认删除该类型？")) {
                var zTree = jQuery.fn.zTree.getZTreeObj("ztreedemo"),
                    checkCount = zTree.getCheckedNodes(true).length;
                if (checkCount > 0) {
                    var checkedNodes = zTree.getCheckedNodes(true);
                    var arrayObj = new Array()
                    jQuery.each(checkedNodes, function (index, val) {
                        arrayObj.push(val.id);
                    });
                    jQuery("#ids").val(arrayObj.join(","));
                    jQuery.ajax({
                        url: '/admin/houqin/delRepairType.json',
                        data: {
                            "ids": jQuery("#ids").val()
                        },
                        type: "post",
                        dataType: "json",
                        async: false,
                        success: function (result) {
                            alert(result.message);
                            window.location.href = "${ctx}/admin/houqin/queryAllRepairType.json";
                        }
                    });
                } else {
                    alert("未选中部门");
                }
            } else {
                return false;
            }
        }

        //跳转到增加部门页面
        function toAdd() {
            window.location.href = "${ctx}/admin/houqin/toAddRepairType.json";
        }
    </script>
    <style type="text/css">
        form button {
            border: 1px solid #f0882c;
            background: #D20009;
            color: #fff;
            cursor: pointer;
            padding: 7px 10px;
            font-weight: bold;
        }
    </style>
</head>
<body>
<div class="centercontent tables">
    <form action="${ctx}/admin/houqin/delRepairType.json" method="post" name="repairTypeForm" id="repairTypeForm">
        <input type="hidden" name="ids" id="ids"/>


        <div class="pageheader notab">
            <h1 class="pagetitle">维修类型列表</h1>
            <div class="ztree" id="ztreedemo" style="margin-left: 50px"></div>
            <div style="width:250px;margin: 0 auto">
                <button onclick="toAdd();return false;" class="submitbutton">新建维修类型</button>
                <button onclick="del();return false;" class="submitbutton">删除选中</button>
                <button onclick="history.go(-1);return false;" value="返回" class="submitbutton">返回</button>
            </div>
        </div>
    </form>
</div>
</div>
</body>
</html>