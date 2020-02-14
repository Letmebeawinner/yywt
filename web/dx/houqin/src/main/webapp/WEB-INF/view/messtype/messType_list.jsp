<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>食堂列表</title>
    <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script>
    <script type="text/javascript">
        function searchForm() {
            jQuery("#searchForm").submit();
        }

        /**
         * 清空搜索条件
         */
        function emptyForm(){
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        function delMenuType(id) {
            if (confirm("确定删除这个分类吗？")) {
            jQuery.ajax({
                    url: "${ctx}/admin/houqin/delMessType.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code==0) {
                            alert(result.message);
                            window.location.href = "/admin/houqin/queryAllMessType.json";
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
        <h1 class="pagetitle">食堂列表</h1>
        <%--<div style="margin-left: 20px;">--%>
            <%--<span style="color:red">说明</span><br>--%>
            <%--1.本页面用于食堂分类列表查看；<br>--%>
            <%--2.查询：输入查询条件，点击<span style="color:red">搜索</span>；<br>--%>
            <%--3.编辑：点击<span style="color:red">编辑</span>修改食堂分类信息；<br>--%>
            <%--4.删除：点击<span style="color:red">删除</span>，删除食堂分类信息；<br>--%>
        <%--</div>--%>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/queryAllMessType.json" method="post">

                    <div class="disIb ml20 mb10">
                        <span class="vam">ID &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="messType.id" type="text" class="hasDatepicker" value="${messType.id}" placeholder="输入分类id">
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">分类名称 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="messType.name" type="text" class="hasDatepicker" value="${messType.name}" placeholder="输入分类名称">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="${ctx}/admin/houqin/toAddMessType.json" class="stdbtn ml10">添 加</a>
                </div>
            </div>
        </div>
        <!-- 搜索条件，结束 -->

        <!-- 数据显示列表，开始 -->
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <colgroup>
                    <col class="con0"/>
                    <col class="con1"/>
                    <col class="con0"/>
                    <col class="con1"/>
                    <col class="con0"/>
                    <col class="con1"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">ID</th>
                    <th class="head0 center">分类名称</th>
                    <th class="head0 center">位置</th>
                    <th class="head1 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${messTypeList}" var="type">
                    <tr>
                        <td>${type.id}</td>
                        <td>${type.name}</td>
                        <td>${type.location}</td>
                        <td class="center">
                            <a href="${ctx}/admin/houqin/toUpdateMessType.json?id=${type.id}" class="stdbtn" title="编辑">编辑</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delMenuType('${type.id}')">删除</a>
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