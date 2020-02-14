<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/ztree/css/zTreeStyle/zTreeStyle.css">
    <title>权限资源列表</title>
</head>
<body>

<div class="centercontent tables">
    <!--pageheader-->
    <div class="pageheader notab" style="margin-left: 10px">
        <h1 class="pagetitle">权限资源列表</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面包括权限资源的增加、修改、删除等相关的操作。
                权限资源以树状图的形式展示。
                权限资源只支持三级节点，且父子节点必须属于同一个系统；<br>
                2. 增加权限资源：在资源列表部分右侧的表单用来添加一个新的权限资源。其中，带有红色
                <span style="color: red">*</span>标记的内容为必填部分。
                <span style="color: red">注意：若该表单有上次查看的资源权限的信息时，请先重置表单，再添加数据，否则会是更新上次查看的资源权限</span>；
                <br>
                3. 更新/删除权限资源：将鼠标移至左侧的资源权限上时，在其名称右侧会出现两个图标。
                第一个图标为修改权限资源，第二个为删除权限资源。
                点击第一个图标时该权限资源的信息会在右侧出现，修改完成后保存即可。
                点击第二个图标时会弹窗再次确认是否删除，如继续请点击确定；<br>
                4. 若需要使某个节点成为另一个根节点的子节点时，请拖动其到目标节点上（目标节点会带有蓝色背景）；
                若需要使某个根节点的子节点成为新的根节点时，请拖动其到任意根节点的前或后方，即两个节点的中间位置（不包括其兄弟节点）。
        </span>
    </div>

    <!-- 数据展示开始 -->
    <div class="contentwrapper">
        <!-- 数据展示树状图 -->
        <div style="display: table-cell; width: 30%; padding-left: 10%">
            <ul id="resources" class="ztree mt5"></ul>
        </div>

        <!-- 数据展示详细表单 -->
        <div id="basicform" class="subcontent" style="display:table-cell;width: 60%">
            <form id="form" class="stdform stdform2" action="#">
                <p>
                    <label for="resourceName"><em style="color: red;">* </em>权限资源名</label>
                    <span class="field">
                        <input type="text" name="resource.resourceName" id="resourceName" class="longinput"/>
                    </span>
                </p>
                <p>
                    <label for="resourcePath">权限资源路径</label>
                    <span class="field">
                        <input type="text" name="resource.resourcePath" id="resourcePath" class="longinput"/>
                    </span>
                </p>
                <p>
                    <label for="resourceType"><em style="color: red;">* </em>权限资源类型</label>
                    <span class="field">
                                 <select name="resource.resourceType" id="resourceType">
                                    <option value="0">请选择</option>
                                    <option value="1">菜单类型</option>
                                    <option value="2">功能类型</option>
                                 </select>
                    </span>
                </p>
                <p>
                    <label for="resourceSite"><em style="color: red;">* </em>权限资源所属系统</label>
                    <span class="field">
                                 <select name="resource.resourceSite" id="resourceSite">
                                    <option value="0">请选择</option>
                                     <c:forEach items="${siteArr}" var="site">
                                         <option value="${site.siteKey}">${site}</option>
                                     </c:forEach>
                                     <option value="XY">学员管理系统</option>
                                 </select>
                    </span>
                </p>
                <p>
                    <label for="resourceOrder">权限资源排序(值大者优先)</label>
                    <span class="field">
                        <input value="0" type="text" name="resource.resourceOrder" id="resourceOrder" class="longinput"/>
                    </span>
                </p>
                <p>
                    <label for="resourceStyleName">权限资源样式名</label>
                    <span class="field">
                        <input type="text" name="resource.styleName" id="resourceStyleName" class="longinput"/>
                    </span>
                </p>
                <p>
                    <label for="resourceDesc"><em style="color: red;">* </em>权限资源描述</label>
                    <span class="field">
                        <textarea style="resize: none;" cols="80" rows="5" name="resource.resourceDesc" id="resourceDesc" class="longinput"></textarea>
                    </span>
                </p>
                <p hidden="hidden">
                    <label for="resourceId"><em style="color: red;">* </em>权限资源ID</label>
                    <span class="field">
                        <input type="text" name="resource.id" id="resourceId" class="longinput" value="0"/></span>
                </p>
                <p hidden="hidden">
                    <label for="resourceParentId">权限资源父ID</label>
                    <span class="field">
                        <input type="text" name="resource.parentId" id="resourceParentId" class="longinput" value="0"/></span>
                </p>

                <p class="stdformbutton">
                    <button class="submit radius2" type="button" onclick="saveResource()">保存</button>
                    <input type="reset" class="reset radius2" onclick="resetForm()" value="重置"/>
                </p>
            </form>
        </div>
    </div>
</div>
<script charset="utf-8" type="text/javascript" src="${ctx}/static/ztree/js/jquery-1.4.4.min.js"></script>
<script charset="utf-8" type="text/javascript" src="${ctx}/static/ztree/jquery.ztree.all-3.5.js"></script>
<script charset="utf-8" type="text/javascript" src="${ctx}/static/admin/js/permission/utils.js"></script>
<script charset="utf-8" type="text/javascript" src="${ctx}/static/admin/js/permission/permission.js"></script>
<script type="text/javascript">
    var resource = ${resourceList};
    var zTreeObj;
    jQuery(document).ready(function () {
        zTreeObj = buildTree('#resources', resource);
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

    /**
     * 重置表单
     * @returns {boolean}
     */
    function resetForm() {
        jQuery('#resourceSite').attr('disabled', false);
        return true;
    }
</script>
</body>
</html>