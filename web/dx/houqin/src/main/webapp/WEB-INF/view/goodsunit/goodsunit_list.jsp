<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>物品单位列表</title>
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

        function toSave() {
            window.location.href = "/admin/houqin/toAddGoodsunit.json"
        }

        function delGoodsunit(id) {
            if (confirm("确定删除这个单位吗？")) {
            jQuery.ajax({
                    url: "${ctx}/admin/houqin/delGoodsunit.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code==0) {
                            alert(result.message);
                            window.location.href = "/admin/houqin/queryAllGoodsUnit.json";
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
        <h1 class="pagetitle">物品单位列表</h1>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/queryAllGoodsUnit.json" method="post">

                    <div class="disIb ml20 mb10">
                        <span class="vam">物品单位名称 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="goodsunit.name" type="text" class="hasDatepicker" value="${goodsunit.name}" placeholder="物品单位名称">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                </div>


                <div class="disIb ml20 mb10">
                    <label class="vam">
                        <a href="javascript: void(0)" onclick="toSave()" class="stdbtn btn_orange">添加物品单位</a>
                    </label>
                </div>
            </div>
        </div>

        <!-- 数据显示列表，开始 -->
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <thead>
                <tr>
                    <th class="head0 center">序号</th>
                    <th class="head0 center">物品单位名称</th>
                    <th class="head1 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${goodsUnitList}" var="goods" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${goods.name}</td>
                        <td class="center">
                            <a href="${ctx}/admin/houqin/toUpdateGoodsunit.json?id=${goods.id}" class="stdbtn" title="编辑">编辑</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delGoodsunit('${goods.id}')">删除</a>
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