<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>用户管理</title>
    <script type="text/javascript">
        function searchForm() {
            jQuery("#searchForm").submit();
        }

        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        function delGoodsunit(id) {
            if (confirm("确定删除这个单位吗？")) {
                jQuery.ajax({
                    url: "${ctx}/admin/houqin/delGoodsunit.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code == 0) {
                            alert(result.message);
                            window.location.href = "/admin/houqin/queryAllGoodsUnit.json";
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
        <h1 class="pagetitle">用户管理</h1>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <%--<div class="overviewhead clearfix mb10">--%>
            <%--<div class="fl mt5">--%>
                <%--<form class="disIb" id="searchForm" action="${ctx}/admin/houqin/BedRoomList.json" method="post">--%>
                    <%--<div class="disIb ml20 mb10">--%>
                        <%--<span class="vam">地点 &nbsp;</span>--%>
                        <%--<label class="vam">--%>
                            <%--<select name="searchFloor">--%>
                                <%--<option value="0">--请选择--</option>--%>
                                <%--<option value="1">1号楼</option>--%>
                                <%--<option value="2">2号楼</option>--%>
                                <%--<option value="3">3号楼</option>--%>
                                <%--<option value="5">5号楼</option>--%>
                            <%--</select>--%>
                        <%--</label>--%>
                    <%--</div>--%>
                <%--</form>--%>
                <%--<div class="disIb ml20 mb10">--%>
                    <%--<a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>--%>
                    <%--<a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>

        <!-- 数据显示列表，开始 -->
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <thead>
                <tr>
                    <th class="head0 center">编号</th>
                    <th class="head0 center">姓名</th>
                    <th class="head0 center">证件号</th>
                    <th class="head0 center">主卡号</th>
                    <th class="head0 center">卡状态</th>
                    <th class="head0 center">员工部门编号</th>
                    <th class="head0 center">所属房间</th>
                    <th class="head0 center">用户消费次数</th>
                </tr>
                </thead>
                <tbody>

                <c:forEach items="${userList}" var="user" varStatus="index">
                    <tr>
                        <td>${user.userId}</td>
                        <td>${user.userName}</td>
                        <td>${user.identityNo}</td>
                        <td>${user.cardId}</td>
                        <td>
                            <c:if test="${user.cardStatusId==1}">正常</c:if>
                            <c:if test="${user.cardStatusId==2}">冻结</c:if>
                        </td>
                        <td>${user.EmpDeptId}</td>
                        <td>${user.BedchamberId}</td>
                        <td>${user.ConsumeTotal}</td>
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