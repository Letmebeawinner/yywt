<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>添加维修类型</title>
    <link rel="stylesheet" href="${ctximg}/static/css/zTreeStyle.css" type="text/css"/>
    <script type="text/javascript" src="${ctximg}/static/ztree/js/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" src="${ctximg}/static/ztree/js/jquery.ztree.excheck-3.5.js"></script>
    <script type="text/javascript">

        //ztree start
        var setting = {
            check: {
                enable: false,
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
            jQuery("#parentId").val(treeNode.id);
            jQuery("#parentName").val(treeNode.name);
            jQuery("#distree").hide();
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
        });

        function selectRoot() {
            jQuery("#parentId").val(0);
            jQuery("#parentName").val("根目录");
            jQuery("#distree").hide();
        }

        function showZtree() {
            jQuery("#distree").show();
        }

        function closetree() {
            jQuery("#distree").hide();
        }

    </script>
    <script type="text/javascript">
        function addFormSubmit() {
            var name = jQuery("#name").val();
            var parentId = jQuery("#parentId").val();
            var sort = jQuery("#sort").val();

            if (name == null || name == '') {
                alert("类型名称不能为空");
                return;
            }
            var functionType = jQuery("input[name='repairType.functionType']:checked").val();
            if (parentId == null || parentId == '') {
                alert("请选择类型");
                return;
            }

            jQuery.ajax({
                url: '/admin/houqin/addSaveRepairType.json',
                data: {
                    "repairType.name": name,
                    "repairType.parentId": parentId,
                    "repairType.functionType": functionType,
                    "repairType.sort": sort
                },
                type: "post",
                dataType: "json",
                async: false,
                success: function (result) {
                    alert(result.message);
                }
            });
        }
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">添加维修类型</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于添加维修类型；<br>
            2.添加维修类型：按要求填写相关信息,点击<span style="color:red">提交保存</span>按钮；添加维修类型<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" id="addFormSubmit">
                <p>
                    <label><em style="color: red;">*</em>父级节点</label>
                    <span class="field">
                        <input type="hidden" name="repairType.parentId" class="longinput" id="parentId"/>
                        <input type="text" name="repairType.parentName" class="longinput" id="parentName"
                               readonly="readonly"
                               onclick="showZtree()"/>
                        <div id="distree" style="display: none;margin-left: 250px;margin-bottom: 20px">
							<div id="ztreedemo" class="ztree"></div>
                            <a class="submitbutton" onclick="selectRoot()">根目录</a>
                            <a class="submitbutton" onclick="closetree()">关闭</a>
						</div>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>维修类型名称</label>
                    <span class="field">
                        <input type="text" name="repairType.name" class="longinput" id="name"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>作用于部门</label>
                    <span class="field">
                        <input type="radio" name="repairType.functionType" checked="checked" value="0"/>后勤处&nbsp;&nbsp;
                        <input type="radio" name="repairType.functionType" value="1"/>信息处
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>类型排序</label>
                    <span class="field">
                        <input type="text" name="repairType.sort" id="sort" class="longinput" value="0"/>
                    </span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit();return false;">提交保存</button>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
</html>