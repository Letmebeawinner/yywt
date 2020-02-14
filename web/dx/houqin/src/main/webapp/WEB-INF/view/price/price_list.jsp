<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>单价列表</title>
    <script type="text/javascript">
        /**
         * 执行查询
         */
        function searchForm() {
            jQuery("#priceForm").submit();
        }
        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        function delPrice(priceId) {
            if (confirm("删除后将无法恢复，是否继续")) {
                jQuery.ajax({
                    url: "/admin/houqin/ajax/price/delete.json",
                    data: {
                        "priceId": priceId
                    },
                    type: "post",
                    dataType: "json",
                    async: false,
                    cache : false,
                    success: function (result) {
                        if (result.code == "0") {
                            alert(result.message);
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

    <div class="pageheader notab">
        <h1 class="pagetitle">单价列表</h1>
    </div>
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于单价信息列表查看；<br>
        2.查询：输入查询条件，点击<span style="color:red">搜索</span>；<br>
        3.编辑：点击<span style="color:red">编辑</span>，修改用单价信息；<br>
        5.删除：点击<span style="color:red">删除</span>，删除单价信息；<br>
    </div>

    <div id="contentwrapper" class="contentwrapper">

        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="priceForm" action="${ctx}/admin/houqin/price/list.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">单价名称 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" type="text" class="longinput" placeholder="输入单价名称" name="price.name" value="${price.name}"/>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchForm()">搜 索</a>
                    <a href="javascript: void(0)" class="stdbtn ml10" onclick="emptyForm()">重 置</a>
                    <a href="${ctx}/admin/houqin/price/to/add.json" class="stdbtn ml10" >添 加</a>
                </div>
            </div>
        </div>

        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <colgroup>
                    <col class="con0" style="width:10%;"/>
                    <col class="con1"/>
                    <col class="con0" style="width:20%;"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">ID</th>
                    <th class="head0 center">名称</th>
                    <th class="head0">类型</th>
                    <th class="head0">内部单价</th>
                    <th class="head0">外部单价</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${prices}" var="price" varStatus="index">
                    <tr>
                        <td>${index.index+1}</td>
                        <td>${price.name}</td>
                        <td>
                            <c:if test="${price.type=='WATER'}">水费</c:if>
                            <c:if test="${price.type=='ELECTRICITY'}">电费</c:if>
                            <c:if test="${price.type=='NATURAL'}">天然气费</c:if>
                        </td>
                        <td>${price.inwardPrice}</td>
                        <td>${price.exteriorPrice}</td>
                        <td class="center">
                            <a href="${ctx}/admin/houqin/price/to/update.json?priceId=${price.id}" class="stdbtn" title="编辑">编辑</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delPrice(${price.id})">删除</a>
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