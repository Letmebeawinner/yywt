<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>查询出库记录</title>
    <script type="text/javascript">
        function searchForm() {
            jQuery("#searchForm").submit();
        }

        function emptyForm() {
            jQuery("input:text").val('');
        }

        function delOutStock(id) {
            if (confirm("确定删除吗？")) {
                jQuery.ajax({
                    url: "${ctx}/admin/houqin/delOutStock.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code == 0) {
                            alert(result.message);
                            window.location.href = "/admin/houqin/queryOutStock.json";
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
        <h1 class="pagetitle">查询出库记录</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于出库管理列表查看；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/queryOutStock.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">物品编号 &nbsp;</span>
                        <label class="vam">
                            <input name="outStock.code" type="text" class="hasDatepicker" value="${outStock.code}"
                                   placeholder="输入物品编号">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">出库单号 &nbsp;</span>
                        <label class="vam">
                            <input name="outStock.billNum" type="text" class="hasDatepicker" value="${outStock.billNum}"
                                   placeholder="输入出库单号">
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <label>出库类型</label>
                        <span class="field">
                           <select name="outStock.outStockType" id="outStockType">
                               <option value="">--请选择--</option>
                               <option value="0"
                                       <c:if test="${outStock.outStockType==0}">selected="selected"</c:if>>转让</option>
                               <option value="1"
                                       <c:if test="${outStock.outStockType==1}">selected="selected" </c:if>>捐赠</option>
                           </select>
                       </span>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                </div>
            </div>
        </div>
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
                    <th class="head0 center">经办人</th>
                    <th class="head0 center">领用人</th>
                    <th class="head0 center">出库时间</th>
                    <th class="head0 center">出库单号</th>
                    <th class="head0 center">出库仓库</th>
                    <th class="head0 center">物品名称</th>
                    <th class="head0 center">出库数量</th>
                    <th class="head0 center">单位</th>
                    <th class="head0 center">作用于</th>
                    <th class="head0 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${outStockList}" var="outStock">
                    <tr>
                        <td>${outStock.id}</td>
                        <td>${outStock.userName}</td>
                        <td>${outStock.receiver}</td>
                        <td><fmt:formatDate value="${outStock.createTime}" pattern="yyyy-MM-dd"/></td>
                        <td>${outStock.billNum}</td>
                        <td>${outStock.billNum}</td>
                        <td>${outStock.name}</td>
                        <td>${outStock.num}</td>
                        <td>${outStock.unitName}</td>
                        <td>${outStock.source}</td>
                        <td>
                            <a href="${ctx}/admin/houqin/detailOutStock.json?id=${outStock.id}" class="stdbtn" title="详情">详情</a>
                            <a href="${ctx}/admin/houqin/toUpdateOutStock.json?id=${outStock.id}" class="stdbtn" title="编辑">编辑</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delOutStock('${outStock.id}')">删除</a>
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
</div>
</body>
</html>