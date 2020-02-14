<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>用电区域列表</title>
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

        function delElectricity(id) {
            if (confirm("确定删除吗？")) {
            jQuery.ajax({
                    url: "${ctx}/admin/houqin/delElectricityType.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code==0) {
                            alert(result.message);
                            window.location.href = "/admin/houqin/queryAllElectricityType.json";
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
        <h1 class="pagetitle">用电区域列表</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于用电区域列表查看；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/queryAllElectricityType.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">用电区域名称 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="electricityType.type" type="text" class="hasDatepicker"
                                   value="${electricityType.type}" placeholder="输入用电区域名称">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="${ctx}/admin/houqin/toAddElectricityType.json" class="stdbtn ml10">添 加</a>
                    <a href="${ctx}/admin/houqin/saveEleSecType.json" class="stdbtn ml10">添加二级区域</a>
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
                    <th class="head0 center">ID</th>
                    <th class="head0 center">用电区域</th>
                    <th class="head0 center">录入时间</th>
                    <th class="head1 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody id="mytable">
                <c:forEach items="${typeList}" var="type">
                    <tr>
                        <td>${type.id}</td>
                        <td>${type.type}</td>
                        <td><fmt:formatDate value="${type.createTime}" pattern="yyyy-MM-dd"/></td>
                        <td class="center">
                            <a href="${ctx}/admin/houqin/toUpdateElectricityType.json?id=${type.id}" class="stdbtn" title="编辑">编辑</a>
                            <%--<a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delElectricity('${type.id}')">删除</a>--%>
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