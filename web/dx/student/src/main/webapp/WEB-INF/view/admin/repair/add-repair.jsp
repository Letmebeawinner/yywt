<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>添加报修记录</title>
    <link rel="stylesheet" href="/static/css/zTreeStyle.css" type="text/css"/>
    <script type="text/javascript" src="/static/ztree/js/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" src="/static/ztree/js/jquery.ztree.excheck-3.5.js"></script>
    <script type="text/javascript">
        jQuery(function () {
            laydate.skin('molv');
            laydate({
                elem: '#warnTime',
                format: 'YYYY-MM-DD hh:mm:ss'
            });
        });

        function addFormSubmit() {
            if (jQuery("#name").val() == "") {
                alert("请添加物品名称");
                return;
            }
            if (jQuery("#context").val() == "") {
                alert("请添加故障说明");
                return;
            }
            jQuery.ajax({
                url: "${ctx}/admin/student/repair/saveRepair.json",
                data: jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                async: false,
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        jQuery("#parentName,#name,#context,#warnTime").val("");
//                        window.location.href = "/admin/student/repair/queryMyRepairList.json"
                    } else {
                        alert(result.message);
                    }
                }
            });
        }

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
</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">添加报修申请</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于添加物品报修；<br>
            2.所属分类：选择需要报修物品的类型；<br>
            3.添加报修：按要求填写相关信息,点击<span style="color:red">提交保存</span>按钮；添加报修申请<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" id="addFormSubmit" action="">
                <%--<p>--%>
                <%--<label>所属分类</label>--%>
                <%--<span class="field">--%>
                <%--<select name="repair.typeId">--%>
                <%--<c:forEach var="repair" items="${repairTypeList}">--%>
                <%--<option value="${repair.id}">${repair.name}</option>--%>
                <%--</c:forEach>--%>
                <%--</select>--%>
                <%--</span>--%>
                <%--</p>--%>

                <p>
                    <label><em style="color: red;">*</em>所属分类</label>
                    <span class="field">
                        <input type="hidden" name="repair.typeId" class="longinput" id="parentId"/>
                        <input type="text" name="repairType.parentName" class="" id="parentName" readonly="readonly"
                               onclick="showZtree()"/>
                        <div id="distree" style="display: none;margin-left: 250px;margin-bottom: 20px">
							<div id="ztreedemo" class="ztree"></div>
                            <%--<a class="submitbutton" onclick="selectRoot()">根目录</a>--%>
                            <a class="submitbutton" onclick="closetree()">关闭</a>

						</div>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>报修物品</label>
                    <span class="field">
                        <input type="text" name="repair.name" class="longinput" id="name"/>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>故障说明</label>
                    <span class="field"><textarea cols="80" rows="5" name="repair.context" id="context"
                                                  class="longinput"></textarea></span>
                </p>
                <p>
                    <label>预警时间</label>
                    <span class="field">
                        <input type="text" name="repair.warnTime" class="longinput" id="warnTime"/>
                    </span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button  class="submit radius2" onclick="addFormSubmit();return false;">提交保存</button>
                </p>
            </form>

        </div>
    </div>
</div>
</body>
</html>