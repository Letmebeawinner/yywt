<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>物品分类列表</title>
    <script type="text/javascript">
        function searchForm() {
            jQuery("#searchForm").submit();
        }


        function toSave() {
            window.location.href = "/admin/houqin/toAddGoodstype.json"
        }


        /**
         * 清空搜索条件
         */
        function emptyForm(){
            jQuery("input:text").val('');
        }

        function delGoodstype(id) {
            if (confirm("确定删除这个类型吗？")) {
            jQuery.ajax({
                    url: "${ctx}/admin/houqin/delGoodstype.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code==0) {
                            alert(result.message);
                            window.location.href = "/admin/houqin/queryAllGoodstype.json";
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
        <h1 class="pagetitle">物品分类列表</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于物品分类列表查看；<br>
            2.查询：输入查询条件，点击<span style="color:red">搜索</span>；<br>
            3.删除：点击<span style="color:red">删除</span>删除物品分类；<br>
            4.编辑：点击<span style="color:red">编辑</span>修改物品分类名称；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/queryAllGoodstype.json" method="post">

                    <div class="disIb ml20 mb10">
                        <span class="vam">物品类型名称 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="goodstype.typeName" type="text" class="hasDatepicker" value="${goodstype.typeName}" placeholder="物品类型名称">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                </div>


                <div class="disIb ml20 mb10">
                    <label class="vam">
                        <a href="javascript: void(0)" onclick="toSave()" class="stdbtn btn_orange">添加物品分类</a>
                    </label>
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
                    <th class="head0 center">序号</th>
                    <th class="head0 center">物品类型ID</th>
                    <th class="head0 center">物品类型名称</th>
                    <th class="head0 center">添加时间</th>
                    <%--<th class="head0 center">排序</th>--%>
                    <th class="head1 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${goodstypeList}" var="goods" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${goods.id}</td>
                        <td>${goods.typeName}</td>
                        <td><fmt:formatDate value="${goods.createTime}" pattern="yyyy-MM-dd"/></td>
                        <%--<td>${goods.sort}</td>--%>
                        <td class="center">
                            <a href="${ctx}/admin/houqin/toUpdateGoodstype.json?id=${goods.id}" class="stdbtn" title="编辑">编辑</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delGoodstype('${goods.id}')">删除</a>
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