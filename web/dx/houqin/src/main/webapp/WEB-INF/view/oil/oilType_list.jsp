<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>油类型列表</title>
    <script type="text/javascript">
        function searchForm() {
            jQuery("#searchForm").submit();
        }

        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
        }

        function delOil(id) {
            if (confirm("确定删除吗？")) {
                jQuery.ajax({
                    url: "${ctx}/admin/houqin/delOilType.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code == 0) {
                            alert(result.message);
                            window.location.href = "/admin/houqin/oilTypeManage.json";
                        } else {
                            alert(result.message);
                            return;
                        }
                    }
                });
            }
        }

        function confirms(id) {
            if (confirm("确定确认吗？")) {
                jQuery.ajax({
                    url: "${ctx}/admin/houqin/confirmOil.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code == 0) {
                            alert(result.message);
                            window.location.href = "/admin/houqin/queryAllOil.json";
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
        <h1 class="pagetitle">油类型列表</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用油类型列表查看；<br>
            2.编辑：点击<span style="color:red">编辑</span>修改油类型信息；<br>
            3.删除：点击<span style="color:red">删除</span>，删除油类型信息；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/oilTypeManage.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">油类型名称 &nbsp;</span>
                        <label class="vam">
                            <input type="text" value="${oilType.name}" name="oilType.name" placeholder="请输入类型名称">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="${ctx}/admin/houqin/toAddOilType.json" class="stdbtn ml10">添 加</a>
                </div>
            </div>
        </div>
        <!-- 搜索条件，结束 -->
        <!-- 数据显示列表，开始 -->
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <colgroup>
                    <col class="con1"/>
                    <col class="con1"/>
                    <col class="con1"/>
                    <col class="con1"/>
                    <col class="con1"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head1 center">序号</th>
                    <th class="head1 center">名称</th>
                    <th class="head1 center">添加时间</th>
                    <th class="head1 center">排序</th>
                    <th class="head1 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${oilTypeList}" var="oilType" varStatus="status">
                    <tr>
                        <td>${oilType.id}</td>
                        <td>${oilType.name}</td>
                        <td><fmt:formatDate value="${oilType.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>${oilType.sort}</td>
                        <td class="center">
                            <a href="${ctx}/admin/houqin/toUpdateOilType/${oilType.id}.json" class="stdbtn"
                               title="编辑">编辑</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除"
                               onclick="delOil('${oilType.id}')">删除</a>
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