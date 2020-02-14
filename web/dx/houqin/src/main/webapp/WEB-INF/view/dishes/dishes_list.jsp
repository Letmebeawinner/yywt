<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>菜品列表</title>
    <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script>
    <script type="text/javascript">
        function searchForm() {
            jQuery("#searchForm").submit();
        }

        function toSaveDish() {
            var messIdVal = jQuery("input[name='messId']").val()
            window.location.href = "/admin/houqin/toAddDishes.json?messId=" + messIdVal;
        }


        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        function delDishes(id) {
            var url = window.location.href;
            if (confirm("确定删除这个菜品吗？")) {
                jQuery.ajax({
                    url: "${ctx}/admin/houqin/deleteDishes.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code == "0") {
                            alert(result.message);
                            window.location.href = url;
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
        <h1 class="pagetitle">菜品管理</h1>

    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <label class="vam">
                <a href="javascript:void(0)" onclick="toSaveDish()" class="stdbtn btn_orange">新建菜品</a>
            </label>
        </div>
    <!-- 数据显示列表，开始 -->
    <div class="pr">
        <input type="hidden" value="${mess.id}" name="messId">
        <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
            <thead>
            <tr>
                <th class="head0 center">ID</th>
                <th class="head0 center">早餐</th>
                <th class="head0 center">早餐价格</th>
                <th class="head0 center">午餐</th>
                <th class="head0 center">午餐价格</th>
                <th class="head0 center">晚餐</th>
                <th class="head0 center">晚餐价格</th>
                <th class="head0 center">作用时间</th>
                <th class="head0 center">描述</th>
                <th class="head0 center">农残检测</th>
                <th class="head0 center">菜品检样</th>
                <th class="head0 center">
                    操作
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${dishesList}" var="dishes">
                <tr>
                    <td>${dishes.id}</td>
                    <td>${dishes.breakfast}</td>
                    <td>${dishes.breakprice}</td>
                    <td>${dishes.lunch}</td>
                    <td>${dishes.lunchprice}</td>
                    <td>${dishes.dinner}</td>
                    <td>${dishes.dinnerprice}</td>
                    <td><fmt:formatDate value="${dishes.usetime}" pattern="yyyy-MM-dd"/></td>
                    <td>${dishes.description}</td>
                    <td>${dishes.pesticideResidues}</td>
                    <td>${dishes.retentionSamples}</td>
                    <td class="center">
                        <a href="${ctx}/admin/houqin/toUpdateDishes.json?id=${dishes.id}" class="stdbtn"
                           title="编辑">编辑</a>
                        <a href="javascript:void(0)" class="stdbtn" title="删除"
                           onclick="delDishes('${dishes.id}')">删除</a>
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