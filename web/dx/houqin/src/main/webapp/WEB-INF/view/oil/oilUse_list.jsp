<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>油用途列表</title>
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
                    url: "${ctx}/admin/houqin/delOilUse.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code == 0) {
                            alert(result.message);
                            window.location.href = "/admin/houqin/oilUseManage.json";
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
        <h1 class="pagetitle">油用途列表</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用油用途列表查看；<br>
            2.编辑：点击<span style="color:red">编辑</span>修改油用途信息；<br>
            3.删除：点击<span style="color:red">删除</span>，删除油用途信息；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/oilUseManage.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">油用途名称 &nbsp;</span>
                        <label class="vam">
                            <input type="text" value="${oilUse.name}" name="oilUse.name" placeholder="请输入用途名称">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="${ctx}/admin/houqin/toAddOilUser.json" class="stdbtn ml10">添 加</a>
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
                <c:forEach items="${oilUseList}" var="oilUse" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${oilUse.name}</td>
                        <td><fmt:formatDate value="${oilUse.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>${oilUse.sort}</td>
                        <td class="center">
                            <a href="${ctx}/admin/houqin/toUpdateOilUse/${oilUse.id}.json" class="stdbtn"
                               title="编辑">编辑</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除"
                               onclick="delOil('${oilUse.id}')">删除</a>
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