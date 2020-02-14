<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>菜单列表</title>
    <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script>
    <script type="text/javascript">
        function searchForm() {
            jQuery("#searchForm").submit();
        }

        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        function delMenus(id) {
            if (confirm("确定删除这个菜单吗？")) {
                jQuery.ajax({
                    url: "${ctx}/admin/houqin/deleteMenus.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code =="0") {
                            alert(result.message);
                            window.location.href = "/admin/houqin/queryAllMenus.json";
                        } else {
                            alert(result.message);
                            return;
                        }
                    }
                });
            }
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">菜单列表</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div class="contenttitle2">
            <h3>菜单列表</h3>
        </div><!--contenttitle-->

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/queryAllMenus.json" method="post">

                    <div class="disIb ml20 mb10">
                        <span class="vam">类型名称 &nbsp;</span>
                        <label class="vam">
                            <select style="width: 200px;" name="typeId">
                                <option value="0">请选择</option>
                                <c:forEach var="menus" items="${menuTypeList}">
                                    <option value="${menus.id}" <c:if test="${menus.id==typId}">selected="selected"</c:if>>${menus.name}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="${ctx}/admin/houqin/toAddMenus.json" class="stdbtn ml10">添加菜单</a>
                </div>
            </div>
        </div>
        <!-- 搜索条件，结束 -->

        <!-- 数据显示列表，开始 -->
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <colgroup>
                    <col class="con0"/>
                    <col class="con0"/>
                    <col class="con0"/>
                    <col class="con0"/>
                    <col class="con0"/>
                    <col class="con1"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">ID</th>
                    <th class="head1 center">名称</th>
                    <th class="head1 center">价格</th>
                    <th class="head0 center">分类名称</th>
                    <th class="head1 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${menusList}" var="menus">
                    <tr>
                        <td>${menus.id}</td>
                        <td>${menus.title}</td>
                        <td>${menus.price}</td>
                        <td>${menus.menuTypeName}</td>
                        <td class="center">
                            <a href="${ctx}/admin/houqin/toUpdateMenus/${menus.id}.json" class="stdbtn"
                               title="编辑">编辑</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除"
                               onclick="delMenus('${menus.id}')">删除</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- 分页，开始 -->
            <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
            <!-- 分页，结束 -->
        </div>
        <!-- 数据显示列表，结束 -->
    </div>
</body>
</html>