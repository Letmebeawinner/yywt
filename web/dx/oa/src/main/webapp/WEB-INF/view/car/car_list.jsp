<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>车辆列表</title>
    <script type="text/javascript">
        /**
         * 执行查询
         */
        function searchForm() {
            jQuery("#carForm").submit();
        }
        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        function delCar(carId) {
            if (confirm("删除后将无法恢复，是否继续")) {
                jQuery.ajax({
                    url: "/admin/oa/deleteCar.json?id="+carId,
                    data: {},
                    type: "post",
                    dataType: "json",
                    async: false,
                    cache : false,
                    success: function (result) {
                        if (result.code=="0") {
                            window.location.reload();
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
        <h1 class="pagetitle">车辆列表</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于车辆信息列表查看；<br>
            2.查询：输入查询条件，点击<span style="color:red">搜索</span>；<br>
            3.编辑：点击<span style="color:red">编辑</span>，修改用车辆信息；<br>
            4.查看：点击<span style="color:red">查看</span>，查看用车辆信息；<br>
            5.删除：点击<span style="color:red">删除</span>，删除车辆信息；<br>
        </div>
    </div>

    <div id="contentwrapper" class="contentwrapper">

        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="carForm" action="${ctx}/admin/oa/queryAllCar.json" method="post">
                    <%-- <div class="disIb ml20 mb10">
                         <span class="vam">ID &nbsp;</span>
                         <label class="vam">
                             <input style="width: auto;" type="text" class="longinput" placeholder="输入车辆ID" name="car.id" value="${car.id}">
                         </label>
                     </div>--%>
                    <div class="disIb ml20 mb10">
                        <span class="vam">车辆名称 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" type="text" class="longinput" placeholder="输入车辆名称" name="car.name" value="${car.name}"/>
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">车牌号 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" type="text" class="longinput" placeholder="输入车牌号" name="car.carID" value="${car.carID}">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">送保人 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" type="text" class="longinput" placeholder="输入送保人" name="car.sendPeople" value="${car.sendPeople}">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">是否出车 &nbsp;</span>
                        <label class="vam">
                            <select name = "car.inUse">
                                <option value = "">未选择</option>
                                <option value="0" <c:if test = "${car.status == 0}">selected = "selected"</c:if>>未出车</option>
                                <option value="1" <c:if test = "${car.status == 2}">selected = "selected"</c:if>>已出车</option>
                            </select>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchForm()">搜 索</a>
                    <a href="javascript: void(0)" class="stdbtn ml10" onclick="emptyForm()">重 置</a>
                </div>
            </div>
        </div>

        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <colgroup>
                    <col class="con0" style="width:10%;"/>
                    <col class="con1"/>
                    <col class="con0" style="width:20%;"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">序号</th>
                    <th class="head0 center">车辆名称</th>
                    <th class="head0">车牌号</th>
                    <th class="head0">保养日期</th>
                    <th class="head0">保养单位</th>
                    <th class="head0">行车公里数</th>
                    <th class="head0">送保人</th>
                    <%--<th class="head0">车辆状态</th>--%>
                    <th class="head0">是否出车</th>
                    <th class="head0 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${carList}" var="car" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${car.name}</td>
                        <td>${car.carID}</td>
                        <td><fmt:formatDate value="${car.repairDate}" pattern="yyyy-MM-dd"></fmt:formatDate></td>
                        <td>${car.repairCompany}</td>
                        <td>${car.carRunDistance}</td>
                        <td>${car.sendPeople}</td>
                        <td>
                            <c:if test = "${car.status == 0}">
                                正常车辆
                            </c:if>
                            <c:if test = "${car.status == 1}">
                                维修中
                            </c:if>
                            <c:if test = "${car.status == 2}">
                                有人使用中
                            </c:if>
                        </td>
                        <%--<td>--%>
                            <%--<c:if test = "${car.inUse == 0}">--%>
                                <%--未出车--%>
                            <%--</c:if>--%>
                            <%--<c:if test = "${car.inUse == 1}">--%>
                                <%--已出车--%>
                            <%--</c:if>--%>
                        <%--</td>--%>
                        <td class="center">
                            <a href="${ctx}/admin/oa/toUpdateCar.json?id=${car.id}&flag=0" class="stdbtn" title="编辑">编辑</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delCar(${car.id})">删除</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- 分页，开始 -->
            <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
            <!-- 分页，结束 -->
        </div>
    </div>
</div>
</body>
</html>