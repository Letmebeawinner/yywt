<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>食堂库存列表</title>
    <script type="text/javascript">
        function searchForm() {
            jQuery("#searchForm").submit();
        }

        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val("");
        }

        function delGoodstype(id) {
            if (confirm("确定删除这个物品吗？")) {
                jQuery.ajax({
                    url: "${ctx}/admin/houqin/delGoods.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code == 0) {
                            alert(result.message);
                            window.location.href = "/admin/houqin/queryAllGoods.json";
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
        <h1 class="pagetitle">食堂库存列表</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于食堂库存管理列表查看；<br>
            2.查询：输入查询条件，点击<span style="color:red">搜索</span>；<br>
            3.出库：点击<span style="color:red">出库</span>修改食堂库存物品出库信息；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/queryAllMessStock.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">物品名称 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="messStock.name" type="text" class="hasDatepicker"
                                   value="${messStock.name}" placeholder="输入名称">
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">进货名称 &nbsp;</span>
                        <label class="vam">
                            <select name="messStock.foodTypeContent">
                                <option value="">请选择</option>
                                <c:forEach items="${foodTypeList}" var="fl">
                                    <option value="${fl.content}"
                                            <c:if test="${messStock.foodTypeContent eq fl.content}"> selected="selected" </c:if>
                                    >${fl.content}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="${ctx}/admin/houqin/toAddMessStock.json"  class="stdbtn ml10">添 加</a>
                    <a href="${ctx}/admin/houqin/toBatchMessStock.json"  class="stdbtn ml10">批量导入</a>
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
                    <col class="con0"/>
                    <col class="con1"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">ID</th>
                    <th class="head0 center">名称</th>
                    <th class="head0 center">物品单位</th>
                    <th class="head0 center">数量</th>
                    <th class="head0 center">总金额</th>
                    <th class="head0 center">进货名称</th>
                    <th class="head0 center">过期时间</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${messStockList}" var="messStock">
                    <tr>
                        <td>${messStock.id}</td>
                        <td>${messStock.name}</td>
                        <td>${messStock.units}</td>
                        <td>${messStock.count}</td>
                        <td>${messStock.price}</td>
                        <td>${messStock.foodTypeContent}</td>
                        <td>
                            <fmt:formatDate value="${messStock.expirationTime}" pattern="yyyy-MM-dd"/>
                        </td>
                        <td class="center">
                            <a href="${ctx}/admin/houqin/toUpdateMessStock/${messStock.id}.json" class="stdbtn" title="编辑">编辑</a>
                            <a href="${ctx}/admin/houqin/lot/list/${messStock.id}.json" class="stdbtn" title="批次管理">批次管理</a>
                            <a href="${ctx}/admin/houqin/lot/priceCurve/${messStock.id}.json"  class="stdbtn" title="批次价格曲线">批次价格曲线</a>
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