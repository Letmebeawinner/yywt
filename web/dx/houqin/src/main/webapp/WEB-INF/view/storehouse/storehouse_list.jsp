<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>查询库存列表</title>
    <script type="text/javascript">
        function searchForm() {
            jQuery("#searchForm").submit();
        }   

        function trim(obj) {
            obj.value = jQuery.trim(obj.value)
        }

        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        function exportExcel() {
            jQuery("#searchForm").prop("action", "${ctx}/admin/houqin/storeHouseExportExcel.json");
            jQuery("#searchForm").submit();
            jQuery("#searchForm").prop("action", "${ctx}/admin/houqin/queryStorehouse.json");
        }

        /**
         * 批量出库
         */
        function batchOutStore() {
            var checked = false;
            var str = "";
            jQuery(".checkbox").each(function(){
                if(jQuery(this).prop("checked")){
                    str+=this.value+",";
                    checked=true;
                }
            });
            if (!checked) {
                alert("请至少选择一条记录");
                return;
            }
            str=str.substring(0,str.length-1);
            window.location.href="${ctx}/admin/houqin/toAddOutStockBatch.json?ids=" + str;
        }
        
        
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">查询库存列表</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于库存管理列表查看；<br>
            2.查询：输入查询条件，点击<span style="color:red">搜索</span>；<br>
            3.出库：点击<span style="color:red">出库</span>修改库存物品出库信息；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/queryStorehouse.json" method="post">

                    <div class="disIb ml20 mb10">
                        <span class="vam">物品类型 &nbsp;</span>
                        <label class="vam">
                            <select name="storeHouse.typeId">
                                <option value="">--请选择--</option>
                                <c:forEach items="${goodTypeList}" var="type">
                                    <option value="${type.id}"
                                            <c:if test="${type.id==storeHouse.typeId}">selected="selected"</c:if>>${type.typeName}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">库房 &nbsp;</span>
                        <label class="vam">
                            <select name="storeHouse.storageId">
                                <option value="">--请选择--</option>
                                <c:forEach items="${storageList}" var="storage">
                                    <option value="${storage.id}"
                                            <c:if test="${storage.id==storeHouse.storageId}">selected="selected"</c:if>>${storage.name}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">物品名称 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="storeHouse.name" type="text" class="hasDatepicker" value="${storeHouse.name}" placeholder="输入名称">
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">物品编号 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="storeHouse.code" type="text" class="hasDatepicker" value="${storeHouse.code}" placeholder="输入编号">
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">入库单号 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="storeHouse.storageNum" type="text"
                                   onblur="trim(this)"
                                   value="${storeHouse.storageNum}" placeholder="输入单号">
                        </label>
                    </div>

                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="javascript: void(0)" onclick="exportExcel()" class="stdbtn ml10 btn_orange">导出Excel</a>
                    <a href="javascript: void(0)" onclick="batchOutStore()" class="stdbtn ml10 btn_orange">批量出库</a>
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
                    <th class="head0 center">入库仓库</th>
                    <th class="head0 center">入库单号</th>
                    <th class="head0 center">物品名称</th>
                    <th class="head0 center">剩余数量</th>
                    <th class="head0 center">物品单位</th>
                    <th class="head0 center">物品编号</th>
                    <th class="head0 center">物品类型</th>
                    <th class="head0 center">规格型号</th>
                    <th class="head0 center">经办人</th>
                    <th class="head0 center">入库时间</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${storeList}" var="store" varStatus="status">
                    <tr>
                        <td>
                        <input type="checkbox" class="checkbox" value="${store.id}"/>&nbsp;${status.count}
                        </td>
                        <td>${store.storageName}</td>
                        <td>${store.storageNum}</td>
                        <td>${store.goodsName}</td>
                        <td <c:if test="${store.num==0}">style="color: red" </c:if>>${store.num}</td>
                        <td>${store.unitName}</td>
                        <td>${store.code}</td>
                        <td>${store.typeName}</td>
                        <td>
                            <c:if test="${store.model==null}">— —</c:if>
                            <c:if test="${store.model!=null}">${store.model}</c:if>
                        </td>
                        <td>${store.userName}</td>
                        <td><fmt:formatDate value="${store.createTime}" pattern="yyyy-MM-dd"/> </td>
                        <td class="center">
                            <a href="${ctx}/admin/houqin/toUpdateStorage/${store.id}.json" class="stdbtn" title="入库">入库</a>
                            <a href="${ctx}/admin/houqin/toAddOutStock/${store.id}.json" class="stdbtn" title="出库">出库</a>
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