<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>设置分类</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/ztree/css/zTreeStyle.css" type = "text/css">
    <link rel="stylesheet" href="${ctx}/static/ztree/css/newTree.css" type="text/css"/>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">设置分类</h1>
    </div><!--pageheader-->
    <div style="margin-left: 20px;">
        <span style="color:red">设置分类</span><br>
            1.本页面用于流程设置分类；<br>
            2.<em style="color: red;">*</em>代表必填项；<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="${ctx}/admin/oa/category/set.json" id="processCategory">
                <input type = "hidden" name = "processDefinitionId" value = "${processDefinitionId}">
                <input type = "hidden" name = "category" value = "${category}" id = "subjectId">
                <p>
                    <label><em style="color: red;">*</em>名称</label>
                    <span class="field"><input type="text" name="name" id="name" class="longinput" value = "${processDefinitionName}" readonly = "true"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>分类</label>
                    <span class="field">
                             <input id="subjectNameBtn" type="text" readonly="readonly"  style="width:120px;height:40px;;padding: 10px" value = "${categoryName}" class = "longinput"
                                    onclick="showSubjectMenu()"/>
                            <div id="subjectmenuContent" class="menuContent" style="display:none;">
                                <ul id="subject_ztreedemo" class="ztree" style="margin-top:0; width:160px;"></ul>
                            </div>
                     </span>
                </p>
            </form>
            <p class="stdformbutton" style="text-align: center">
                <button class="submit radius2" onclick="addFormSubmit();return false;">保 存</button>
                <input type="reset" class="reset radius2" value="重 置" onclick="resetData()"/>
            </p>
            <br/>
        </div><!--subcontent-->
    </div>
</div>
<script charset="utf-8" type="text/javascript" src="${ctx}/static/ztree/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${ctx}/static/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx}/static/ztree/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript" src="${ctx}/static/ztree/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="${ctx}/static/js/process/process.js"></script>
<script type="text/javascript">
    var subject_treedata = ${flowTypes};
    jQuery(function(){
        jQuery.fn.zTree.init(jQuery("#subject_ztreedemo"), subject_setting, subject_treedata);
    });
</script>
</body>
</html>