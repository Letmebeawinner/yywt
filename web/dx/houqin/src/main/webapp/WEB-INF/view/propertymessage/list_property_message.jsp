<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title> 固定资产出库记录</title>
    <script type="text/javascript">
        function searchForm() {
            jQuery("#searchForm").submit();
        }
        function emptyForm() {
            jQuery("select").val(-1);
            jQuery("input:text").val('');
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">固定资产出库记录</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于固定资产出库管理列表查看；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/listOutPM.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">编号 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="outOfPropertyMessage.serialNo" type="text"
                                   class="hasDatepicker"
                                   value="${outOfPropertyMessage.serialNo}" placeholder="输入出库编号">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">物品名称 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="outOfPropertyMessage.outboundItemName" type="text" class="hasDatepicker"
                                   value="${outOfPropertyMessage.outboundItemName}" placeholder="输入物品名称">
                        </label>
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
                    <th class="head0 center">序号</th>
                    <th class="head0 center">库房名称</th>
                    <th class="head0 center">物品名称</th>
                    <th class="head0 center">出库部门</th>
                    <th class="head0 center">出库数量</th>
                    <th class="head0 center">经办人</th>
                    <th class="head0 center">出库人</th>
                    <th class="head0 center">出库编号</th>
                    <th class="head0 center">出库日期</th>
                    <th class="head0 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${out}" var="opm" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${opm.storageName}</td>
                        <td>${opm.outboundItemName}</td>
                        <td>${opm.departmentName}</td>
                        <td>${opm.outboundNumber}</td>
                        <td>${opm.manager}</td>
                        <td>${opm.outboundPerson}</td>
                        <td>${opm.serialNo}</td>
                        <td><fmt:formatDate value="${opm.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td class="head0 center">
                            <a href="${ctx}/admin/houqin/detailOpm.json?id=${opm.id}" class="stdbtn" title="查看详情">查看详情</a>
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