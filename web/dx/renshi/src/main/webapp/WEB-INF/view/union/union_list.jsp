<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>工会小组列表</title>
    <script type="text/javascript" src="${ctx}/static/admin/js/union.js"></script>
    <script type="text/javascript">
        /**
         * 执行查询
         */
        function searchForm() {
            jQuery("#getUnionList").submit();
        }
        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        function delUnion(UnionId) {
            if (confirm("删除后将无法恢复，是否继续")) {
                jQuery.ajax({
                    url: "${ctx}/admin/rs/deleteUnion.json?id="+UnionId,
                    data: {},
                    type: "post",
                    dataType: "json",
                    async: false,
                    success: function (result) {
                        if (result.code=="0") {
                            window.location.reload();
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

    <div class="pageheader notab" style="margin-left: 10px">
        <h1 class="pagetitle">工会小组列表</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来新建、编辑、删除小组信息及查看小组人员信息<br>
                2. 新建小组：点击搜索框中最后的<span style="color:red">新建</span>按钮进行添加；<br>
                3. 编辑小组：点击操作列中的<span style="color:red">编辑</span>按钮编辑小组信息；<br>
                4. 删除小组：点击操作列中的<span style="color:red">删除</span>按钮删除小组信息；<br>
        </span>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="getUnionList" action="${ctx}/admin/rs/getUnionList.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">工会小组名 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" placeholder="输入工会小组名" name="union.name"
                                   value="${union.name}">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchForm()">搜 索</a>
                    <a href="javascript: void(0)" class="stdbtn ml10" onclick="emptyForm()">重 置</a>
                    <a href="${ctx}/admin/rs/toAddUnion.json" class="stdbtn ml10">新 建</a>
                </div>
            </div>
        </div>

        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable" >
                <colgroup>
                    <col class="con0" style="width:10%;"/>
                    <col class="con1"/>
                    <col class="con0" style="width:20%;"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">序号</th>
                    <th class="head0 center">工会小组名</th>
                    <th class="head1">联系人</th>
                    <th class="head1">联系方式</th>
                    <td class="head0 center">
                        操作
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${unionList}" var="union" varStatus="index">
                    <tr>
                        <td>${index.count}</td>
                        <td>${union.name}</td>
                        <td>${union.contacts}</td>
                        <td>${union.mobile}</td>
                        <td class="center">
                            <a href="${ctx}/admin/rs/toUpdateUnion.json?id=${union.id}" class="stdbtn" title="编辑">编辑</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delUnion(${union.id})">删除</a>
                            <a href="${ctx}/admin/rs/getUnionEmployeeList.json?unionEmployee.unionId=${union.id}" class="stdbtn" title="小组人员">小组人员</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- 分页，开始 -->
            <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
            <!-- 分页，结束 -->
        </div>
    </div>
</div>
</body>
</html>