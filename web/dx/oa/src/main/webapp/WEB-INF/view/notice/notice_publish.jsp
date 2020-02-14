<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>公告发布</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/ztree/css/zTreeStyle.css" type="text/css">
    <%--<link rel="stylesheet" href="${ctx}/static/ztree/css/demo.css" type="text/css"/>--%>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">发布公告</h1>
    </div>
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于公告发布；<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addNotice">
                <input type="hidden" name="notice.id" value="${notice.id}">
                <p>
                    <label>标题</label>
                    <span class="field"><input type="text" name="notice.title" id="title" class="longinput"
                                               value="${notice.title}" readonly="true"/></span>
                </p>
                <div style="border: 1px solid #ddd;  background: #fcfcfc; border-top: 0;position: relative;">
                    <label><em style="color: red;">*</em>发布对象</label>
                     <span class="field">
                         <input id="subjectNameBtn" type="text" readonly="readonly" value=""
                                style="width:120px;height: 40px;padding: 10px;display: none"/>
                        <div id="subjectmenuContent" class="menuContent1">
                            <ul id="subject_ztreedemo" class="ztree" style="margin-top:0; width:160px;"></ul>
                        </div>
                     </span>
                    <div style="clear: both"></div>
                </div>

            </form>
            <p class="stdformbutton" style="text-align: center">
                <button class="submit radius2" onclick="publishNotice(${notice.id});return false;">保 存</button>
            </p>
            <br/>
        </div>
    </div>
</div>

<script charset="utf-8" type="text/javascript" src="${ctx}/static/ztree/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${ctx}/static/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx}/static/ztree/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript" src="${ctx}/static/ztree/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript">

    function resetData() {
        jQuery(".longinput").val("");
    }

    //subject ztree start
    var subject_setting = {
        check: {
            enable: true,
            chkStyle: "checkbox",
            chkboxType: {"Y": "s", "N": "ps"}
        },
        view: {
            showLine: true,
            showIcon: true,
            selectedMulti: false,
            dblClickExpand: false
        },
        data: {
            simpleData: {
                enable: true,
                idKey: 'id',
                pIdKey: 'parentId',
                rootPid: ''
            },
            key: {
                name: 'departmentName',
                title: 'departmentName'
            }
        },
        callback: {}
    };
    var subject_treedata = ${departments};
    var noticeDepartments = ${noticeDepartments};


    jQuery(function () {
        jQuery.fn.zTree.init(jQuery("#subject_ztreedemo"), subject_setting, subject_treedata);
        //专业名称显示
        var treeObj = jQuery.fn.zTree.getZTreeObj("subject_ztreedemo");
        jQuery.each(noticeDepartments, function (index, value) {
            var departmentId = value.departmentId;
            var node = treeObj.getNodeByParam("id", departmentId, null);
            treeObj.checkNode(node, true);
        });
        var cityObj = jQuery("#subjectNameBtn");
        var cityOffset = jQuery("#subjectNameBtn").offset();
        jQuery("#subjectmenuContent").css({
            left: cityOffset.left + "px",
            top: cityOffset.top + cityObj.outerHeight() + "px"
        }).slideDown("fast");
    });

    /**
     * 发布公告 id,公告id，ids部门id的集合
     * @param id
     * @param flag
     */
    function publishNotice(id) {
        var ids = [];
        var treeObj = jQuery.fn.zTree.getZTreeObj("subject_ztreedemo");
        var nodes = treeObj.getCheckedNodes(true);
        jQuery.each(nodes, function (index, value) {
            ids.push(value.id);
        });
        jQuery.ajax({
                    url: "${ctx}/admin/oa/publishNotice.json",
                    data: {
                        "id": id,
                        "ids": ids
                    },
                    type: "post",
                    dataType: "json",
                    cache: false,
                    async: true,
                    success: function (result) {
                        alert(result.message);
                        window.location.href = "/admin/oa/queryAllNotice.json";
                    }
                }
        );
    }
</script>
</body>
</html>