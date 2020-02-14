<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/ztree/css/zTreeStyle/zTreeStyle.css">
    <title>部门列表</title>
</head>
<body>

<div class="centercontent tables">
    <!--pageheader-->
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">部门列表</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 左侧树形结构为可操作的部门,右侧用于添加、修改部门；<br>
                2. 增加部门：在右侧填写部门相关信息，点击<span style="color:red">保存</span>按钮添加；<br>
                3. 更新部门：点击树形结构部门右侧<span style="color:red">修改部门</span>修改右侧信息，点击保存；<br>
                4. 删除部门：点击树形结构部门右侧<span style="color:red">删除部门</span>删除部门；<br>

        </span>

    </div>

    <!-- 数据展示开始 -->
    <div class="contentwrapper">
        <!-- 数据展示树状图 -->
        <div style="display: table-cell; width: 30%; padding-left: 10%">
            <ul id="department" class="ztree mt5"></ul>
        </div>

        <!-- 数据展示详细表单 -->
        <div id="basicform" class="subcontent" style="display: table-cell; width: 60%">
            <form id="form" class="stdform stdform2" action="#">

                <p>
                    <label for="departmentName"><em style="color: red;">*</em>部门名</label>
                    <span class="field"><input type="text" name="department.departmentName" id="departmentName" class="longinput"/></span>
                </p>
                <p>
                    <label for="departmentDuty">职务</label>
                    <span class="field"><input type="text" name="department.departmentDuty" id="departmentDuty" class="longinput"/></span>
                </p>

                <p hidden="hidden">
                    <label for="departmentId"><em style="color: red;">*</em>部门id</label>
                    <span class="field"><input type="text" name="department.id" id="departmentId" class="longinput"/></span>
                </p>
                <p>
                    <label for="departmentAvailable"><em style="color: red;">*</em>状态</label>
                    <span class="field">
                                 <select name="department.departmentAvailable" id="departmentAvailable" class="longinput">
                                    <option value="0">激活</option>
                                    <option value="1">禁用</option>
                                 </select>
                             </span>
                </p>
                <p>
                    <label for="departmentDesc"><em style="color: red;">*</em>部门描述</label>
                    <span class="field">
                        <textarea style="resize: none;" cols="80" rows="5" name="department.departmentDesc" id="departmentDesc" class="longinput" ></textarea>
                    </span>
                </p>

                <p>
                    <label for="departmentDesc">排序数值</label>
                    <span class="field">
                       <input type="text" name="department.sort" id="sort" class="longinput"/>
                    </span>
                </p>

                <p class="stdformbutton">
                    <button class="submit radius2" type="button" onclick="saveResource()">保存</button>
                    <button type="button" class="reset radius2" onclick="romoveAll()">重置</button>
                </p>
            </form>
        </div>
    </div>
</div>
<script charset="utf-8" type="text/javascript" src="${ctx}/static/ztree/js/jquery-1.4.4.min.js"></script>
<script charset="utf-8" type="text/javascript" src="${ctx}/static/ztree/jquery.ztree.all-3.5.js"></script>
<script charset="utf-8" type="text/javascript" src="${ctx}/static/admin/js/permission/utils.js"></script>
<script charset="utf-8" type="text/javascript" src="${ctx}/static/admin/js/department/department-list.js"></script>
<script type="text/javascript">
    var department = ${department};
    var zTreeObj;
    jQuery(document).ready(function () {
        zTreeObj = buildTree('#department', department);
        var expandFirst = zTreeObj.getNodesByFilter(function(node) {
            if (!node['parentId']) {
                if (undefined != node.children) {
                    return node.children.length != 0;
                }
            }
            return false;
        }, true);
        zTreeObj.expandNode(expandFirst);
        jQuery('input[type=reset]').click();
        jQuery('#resourceSite').attr('disabled', false);
    });


</script>
</body>
</html>