<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>饭卡列表</title>
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

        function delMealCard(id) {
            if (confirm("确定删除这个饭卡吗？")) {
            jQuery.ajax({
                    url: "${ctx}/admin/houqin/delMealCard.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code==0) {
                            alert(result.message);
                            window.location.href = "/admin/houqin/queryAllMealCard.json";
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
        <h1 class="pagetitle">饭卡列表</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div class="contenttitle2">
            <h3>饭卡</h3>
        </div><!--contenttitle-->

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/queryAllRepairType.json" method="post">

                    <%--<div class="disIb ml20 mb10">--%>
                        <%--<span class="vam">ID &nbsp;</span>--%>
                        <%--<label class="vam">--%>
                            <%--<input style="width: auto;" name="typeId" type="text" class="hasDatepicker" value="${id}"--%>
                                   <%--placeholder="输入类型id">--%>
                        <%--</label>--%>
                    <%--</div>--%>

                    <%--<div class="disIb ml20 mb10">--%>
                        <%--<span class="vam">类型名称 &nbsp;</span>--%>
                        <%--<label class="vam">--%>
                            <%--<input style="width: auto;" name="typeName" type="text" class="hasDatepicker"--%>
                                   <%--value="${name}" placeholder="输入类型名称">--%>
                        <%--</label>--%>
                    <%--</div>--%>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="${ctx}/admin/houqin/toAddMealCard.json"  class="stdbtn ml10">添加饭卡</a>
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
                    <th class="head1 center">学生姓名</th>
                    <th class="head0 center">卡号</th>
                    <%--<th class="head0 center">密码</th>--%>
                    <th class="head1 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${mealCardList}" var="mealCard">
                    <tr>
                        <td>${mealCard.id}</td>
                        <td>${mealCard.name}</td>
                        <td>${mealCard.cardNumber}</td>
                        <%--<td>${mealCard.password}</td>--%>
                        <td class="center">
                            <a href="${ctx}/admin/houqin/toUpdateMealCard/${mealCard.id}.json" class="stdbtn" title="编辑">编辑</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delMealCard('${mealCard.id}')">删除</a>
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