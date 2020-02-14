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
        <h1 class="pagetitle">固定资产入库记录</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于固定资产入库管理列表查看；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/listInsidePM.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">入库单号 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="insidePropertyMessage.insideNumber" type="text"
                                   class="hasDatepicker"
                                   value="${insidePropertyMessage.insideNumber}" placeholder="入库单号">
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
                    <th class="head0 center">库房</th>
                    <th class="head0 center">资产名称</th>
                    <th class="head0 center">资产类型</th>
                    <th class="head0 center">入库人</th>
                    <th class="head0 center">入库单号</th>
                    <th class="head0 center">入库数量</th>
                    <th class="head0 center">入库时间</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${insidePropertyMessageList}" var="opm" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${opm.storageName}</td>
                        <td>${opm.name}</td>
                        <td>${opm.propertyType}</td>
                        <td>${opm.userName}</td>
                        <td>${opm.insideNumber}</td>
                        <td>${opm.amount}</td>
                        <td><fmt:formatDate value="${opm.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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