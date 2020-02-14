<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>库房列表</title>
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
            window.location.href = "/admin/houqin/toAddStorage.json"
        }

        function delElectricity(id) {
            if (confirm("确定删除吗？")) {
            jQuery.ajax({
                    url: "${ctx}/admin/houqin/deleteStorage.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        alert(result.message);
                        if (result.code==0) {
                            window.location.reload();
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
        <h1 class="pagetitle">库房列表</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于库房列表查看；<br>
            2.查询：输入查询条件，点击<span style="color:red">搜索</span>；<br>
            3.编辑：点击<span style="color:red">编辑</span>修改库房信息；<br>
            4.删除：点击<span style="color:red">删除</span>，删除库房信息；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/storageList.json" method="post">

                    <div class="disIb ml20 mb10">
                        <span class="vam">名称 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="storage.name" type="text" class="hasDatepicker"
                                   value="${storage.name}" placeholder="输入库房名称">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="javascript: void(0)" onclick="toSave()" class="stdbtn ml10">添 加</a>
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
                    <th class="head0 center">库房ID</th>
                    <th class="head0 center">库房名称</th>
                    <th class="head0 center">库房地址</th>
                    <th class="head0 center">管理员名称</th>
                    <th class="head0 center">管理员电话</th>
                    <th class="head1 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${storageList}" var="storage" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${storage.id}</td>
                        <td>${storage.name}</td>
                        <td>${storage.address}</td>
                        <td>${storage.managerName}</td>
                        <td>${storage.managerPhone}</td>
                        <td class="center">
                            <a href="${ctx}/admin/houqin/toUpdateStorage.json?id=${storage.id}" class="stdbtn" title="编辑">编辑</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delElectricity('${storage.id}')">删除</a>
                            <a href="${ctx}/admin/houqin/queryStorehouse.json?storeHouse.storageId=${storage.id}"
                               class="stdbtn" title="库存列表">库存列表</a>
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