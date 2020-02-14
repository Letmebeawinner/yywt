<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
            var functionType = jQuery("input[name='repairType.functionType']:checked").val();
            var parentId = jQuery("#parentId").val();
            var sort = jQuery("#sort").val();
            var id = jQuery("#id").val();

            if (name == null || name == '') {
                alert("类型名称不能为空!");
                return;
            }
            if (parentId == null || parentId == '') {
                alert("请选择类型");
                return;
            }
            jQuery.ajax({
                url: "${ctx}/admin/houqin/UpdateRepairType.json",
                data: {
                    "repairType.name": name,
                    "repairType.functionType": functionType,
                    "repairType.parentId": parentId,
                    "repairType.sort": sort,
                    "repairType.id": id
                },
                type: "post",
                dataType: "json",
                async: false,
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "/admin/houqin/queryAllRepairType.json";
                    } else {
                        alert(result.message);
                        return;
                    }
                }
            });
        }

    </script>
    <style type="text/css">
        .submitbutton {
            background-color: #d20009;
            border-color: #de4204;
            color: #fff;
            cursor: pointer;
            font-weight: bold;
            padding: 7px 10px;
        }
    </style>
</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">修改维修类型</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于修改维修类型；<br>
            2.按要求修改维修类型相关信息；<br>
            3.提交保存：点击<span style="color:red">提交保存</span>，保存维修类型；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <input type="hidden" name="repairType.id" id="id" value="${repairTypeDto.id}">
                    <label><em style="color: red;">*</em>父级节点</label>
                    <span class="field">
                        <input type="hidden" name="repairType.parentId" class="longinput"
                               value="${repairTypeDto.parentId}" id="parentId"/>
                        <input type="text" name="repairType.parentName" class="" value="${repairTypeDto.parentName}"
                               id="parentName" readonly="readonly" onclick="showZtree()"/>
                        <div id="distree" style="display: none;margin-left: 250px;margin-bottom: 20px;">
							<div id="ztreedemo" class="ztree"></div>
                            <a class="submitbutton" onclick="selectRoot()">根目录</a>
							<a class="submitbutton" onclick="closetree()">关闭</a>
						</div>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>类型名称</label>
                    <span class="field">
                        <input type="text" name="repairType.name" class="longinput" value="${repairTypeDto.name}"
                               id="name"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>作用于部门</label>
                    <span class="field">
                        <input type="radio" name="repairType.functionType" value="0" <c:if test="${repairTypeDto.functionType==0}">checked</c:if>/>后勤处&nbsp;&nbsp;
                        <input type="radio" name="repairType.functionType" value="1" <c:if test="${repairTypeDto.functionType==1}">checked</c:if>/>信息处
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>类型排序</label>
                    <span class="field">
                        <input type="text" name="repairType.sort" class="longinput" id="sort"
                               value="${repairTypeDto.sort}"/>
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