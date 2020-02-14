<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>物品列表</title>
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

        function toSave() {
            window.location.href = "/admin/houqin/toAddGoods.json"
        }
        
        function toBatchSave() {
            window.location.href = "/admin/houqin/toAddGoodsBatch.json"
        }


        function delGoods(id) {
            if (confirm("确定删除这个物品吗？")) {
            jQuery.ajax({
                    url: "${ctx}/admin/houqin/delGoods.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code==0) {
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
        <h1 class="pagetitle">新录物品管理</h1>
        <div style="margin-left: 20px;">
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/queryAllGoods.json" method="post">

                    <div class="disIb ml20 mb10">
                        <span class="vam">物品名称 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="goods.name" type="text" class="hasDatepicker"
                                   value="${goods.name}" placeholder="输入名称">
                        </label>
                    </div>



                    <div class="disIb ml20 mb10">
                        <span class="vam">物品编号 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="goods.code" type="text" class="hasDatepicker" value="${goods.code}" placeholder="输入编号">
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">物品类型 &nbsp;</span>
                        <label class="vam">
                           <select name="typeId">
                               <option value="">--请选择--</option>
                               <c:forEach items="${goodTypeList}" var="type">
                                     <option value="${type.id}"<c:if test="${type.id==goods.typeId}">selected="selected"</c:if>>${type.typeName}</option>
                               </c:forEach>
                           </select>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="javascript: void(0)" onclick="toSave()" class="stdbtn  ml10">单品入库</a>
                    <a href="javascript: void(0)" onclick="toBatchSave()" class="stdbtn  ml10">批量入库</a>
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
                    <th class="head0 center">库房名称</th>
                    <th class="head0 center">物品名称</th>
                    <th class="head0 center">物品编号</th>
                    <th class="head0 center">物品类型</th>
                    <th class="head0 center">单价</th>
                    <th class="head0 center">数量</th>
                    <th class="head0 center">总金额</th>
                    <th class="head0 center">入库时间</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${goodsList}" var="goods" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${goods.storageName}</td>
                        <td>${goods.name}</td>
                        <td>${goods.code}</td>
                        <td>${goods.typeName}</td>
                        <td>${goods.price}</td>
                        <td>${goods.num}</td>
                        <td>${goods.num*goods.price}</td>
                        <td><fmt:formatDate value="${goods.createTime}" pattern="yyyy-MM-dd"/></td>
                        <td class="center">
                            <a href="${ctx}/admin/houqin/detailGoods/${goods.id}.json" class="stdbtn" title="详情">详情</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delGoods('${goods.id}')">删除</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
            <!-- 分页，开始 -->
            <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
            <!-- 分页，结束 -->
        </div>
        <!-- 数据显示列表，结束 -->
    </div>
</body>
</html>