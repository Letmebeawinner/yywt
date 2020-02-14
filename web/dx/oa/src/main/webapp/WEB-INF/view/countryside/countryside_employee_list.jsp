<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>下乡人员列表</title>
    <script type="text/javascript" src="${ctx}/static/js/countryside.js"></script>
    <script type="text/javascript">
        /**
         * 执行查询
         */
        function searchForm() {
            jQuery("#getEmployeeList").submit();
        }

        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        /**
         * 移除教职工
         */
        function delEmployee(id, employeeId) {
            if (confirm("确定将该教职工从该活动中移除？")) {
                jQuery.ajax({
                    url: "${ctx}/admin/oa/deleteCountrysideEmployee.json",
                    data: {
                        'countrysideEmp.employeeId': employeeId,
                        'countrysideEmp.countrysideId': id
                    },
                    type: "post",
                    dataType: "json",
                    async: false,
                    success: function (result) {
                        if (result.code == "0") {
                            window.location.reload();
                        } else {
                            alert(result.message);
                            return;
                        }
                    }
                });
            }
        }

        /**
         * 修改参加状态
         */
        function updateEmployee(id,counttysideId) {
            if (confirm("确定该教职工已参加？")) {
                jQuery.ajax({
                    url: "${ctx}/admin/oa/updateCountrysideEmployee.json?",
                    data: {
                        'countrysideEmp.countrysideId': counttysideId,
                        'countrysideEmp.id': id
                    },
                    type: "post",
                    dataType: "json",
                    async: false,
                    success: function (result) {
                        if (result.code == "0") {
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

    <div class="pageheader notab" style="margin-left: 10px">
        <h1 class="pagetitle">下乡员工列表</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来添加、查看、移除下乡帮扶人员，设置人员参与状态<br>
                2. 添加人员：点击搜索框中最后的<span style="color:red">添加人员</span>按钮添加人员；<br>
                3. 查看人员信息：点击操作列中的<span style="color:red">查看</span>按钮查看人员的信息；<br>
                4. 已参加人员设置：已参加而未设置参加的人员可点击操作列中的<span style="color:red">设为已参加</span>按钮将参加状态修改为已参加；<br>
                5. 移除人员：点击操作列中的<span style="color:red">移除</span>按钮移除人员；
        </span>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="getEmployeeList" action="${ctx}/admin/oa/getCountrysideEmpList.json?countrysideEmp.countrysideId=${countrysideEmp.countrysideId}" method="post">
                    <%--<div class="disIb ml20 mb10">--%>
                        <%--<span class="vam">电话 &nbsp;</span>--%>
                        <%--<label class="vam">--%>
                            <%--<input type="text" class="hasDatepicker" placeholder="输入用户电话" name="countrysideEmp.mobile"--%>
                                   <%--value="${countrysideEmp.mobile}">--%>
                        <%--</label>--%>
                    <%--</div>--%>
                </form>
                <div class="disIb ml20 mb10">
                    <%--<a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchForm()">搜 索</a>--%>
                    <%--<a href="javascript: void(0)" class="stdbtn ml10" onclick="emptyForm()">重 置</a>--%>
                    <a href="javascript:void(0)" class="stdbtn ml10" onclick="addEmployee(${countrysideEmp.countrysideId})">添加人员</a>
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
                    <th class="head0 center">姓名</th>
                    <th class="head1">性别</th>
                    <th class="head1">民族</th>
                    <th class="head1">电话</th>
                    <td class="head0 center">
                        操作
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${employeeList}" var="employee" varStatus="index">
                    <tr>
                        <td>${index.count}</td>
                        <td>${employee.name}</td>
                        <td>
                            <c:if test="${employee.sex==0}">男</c:if>
                            <c:if test="${employee.sex==1}">女</c:if>
                        </td>
                        <td>${employee.nationality}</td>
                        <td>${employee.mobile}</td>
                        <td class="center">
                            <a href="${ctx}/admin/oa/getEmployeeInfo.json?id=${employee.employeeId}" class="stdbtn" title="查看">查看</a>
                            <c:if test="${employee.joinStatus==1}">
                                <a href="javascript:void(0)" class="stdbtn" title="设为已参加" onclick="updateEmployee(${employee.id},${employee.countrysideId})">设为已参加</a>
                            </c:if>
                            <c:if test="${employee.joinStatus==2}">
                                <a href="javascript:void(0)" class="stdbtn btn_orange" title="已参加">已参加</a>
                            </c:if>
                            <a href="javascript:void(0)" class="stdbtn" title="移除" onclick="delEmployee('${employee.countrysideId}','${employee.employeeId}')">移除</a>
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