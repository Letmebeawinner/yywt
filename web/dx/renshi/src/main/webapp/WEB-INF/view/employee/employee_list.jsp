<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>教职工列表</title>
    <script type="text/javascript">
        jQuery(function () {
            jQuery("#employeeType").val('${employee.employeeType}')
        })

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
            jQuery("select").val('');
        }

        function delEmployee(employeeId) {
            if (confirm("删除后将无法恢复，是否继续")) {
                jQuery.ajax({
                    url: "${ctx}/admin/rs/deleteEmployee.json?id=" + employeeId,
                    data: {},
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

        function exportExcel() {
            jQuery("#getEmployeeList").prop("action", "${ctx}/admin/rs/exportEmployeeExcel.json");
            jQuery("#getEmployeeList").submit();
            jQuery("#getEmployeeList").prop("action", "${ctx}/admin/rs/getEmployeeList.json");
        }

    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab" style="margin-left: 10px">
        <h1 class="pagetitle">教职工列表</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来新建、编辑、删除教职工，查看教职工参与过的科研咨政课题<br>
                2. 新建教职工：点击搜索框中最后的<span style="color:red">新建</span>按钮添加教职工；<br>
                3. 编辑教职工：点击操作列中的<span style="color:red">编辑</span>按钮编辑教职工的信息；<br>
                4. 删除教职工：点击操作列中的<span style="color:red">删除</span>按钮删除教职工的信息；<br>
        </span>
    </div>

    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="getEmployeeList" action="${ctx}/admin/rs/getEmployeeList.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">姓名 &nbsp;</span>
                        <label class="vam">
                            <input type="text" style="width: 150px;" class="hasDatepicker" placeholder="输入姓名"
                                   name="employee.name" value="${employee.name}">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">电话 &nbsp;</span>
                        <label class="vam">
                            <input type="text" style="width: 150px;" class="hasDatepicker" placeholder="输入用户电话"
                                   name="employee.mobile" value="${employee.mobile}">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">&nbsp;教职工类别 </span>
                        <label class="vam">
                            <select name="employee.employeeType" id="employeeType" class="longinput"
                                    style="width: 150px;">
                                <option value="">请选择教职工类别</option>
                                <option value="2">县级非领导</option>
                                <option value="3">校领导</option>
                                <option value="4">中层干部</option>
                                <option value="5">一般干部</option>
                                <option value="6">技术工人</option>
                            </select>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchForm()">搜 索</a>
                    <a href="javascript: void(0)" class="stdbtn ml10" onclick="emptyForm()">重 置</a>
                    <a href="${ctx}/admin/rs/toAddEmployee.json" class="stdbtn ml10">新 建</a>
                    <a href="${ctx}/admin/rs/toBatchEmployee.json"  class="stdbtn ml10">批量导入</a>
                    <a href="javascript:void(0)" onclick="exportExcel()" class="stdbtn ml10">导出excel</a>


                    <%--<a href="${ctx}/admin/rs/toBatchEmployeeSynchronization.json"  class="stdbtn ml10">临时批量导入同步信息用</a>--%>
                </div>
            </div>
        </div>

        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <thead>
                <tr>
                    <th class="head1">序号</th>
                    <th class="head1">姓名</th>
                    <th class="head1">教职工类别</th>
                    <th class="head1">性别</th>
                    <th class="head1">民族</th>
                    <th class="head1">入党时间</th>
                    <th class="head1">参加工作时间</th>
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
                            <%--<c:if test="${employee.employeeType==1}">教师</c:if>--%>
                            <c:if test="${employee.employeeType==2}">县级非领导</c:if>
                            <c:if test="${employee.employeeType==3}">校领导</c:if>
                            <c:if test="${employee.employeeType==4}">中层干部</c:if>
                            <c:if test="${employee.employeeType==5}">一般干部</c:if>
                            <c:if test="${employee.employeeType==6}">技术工人</c:if>
                        </td>
                        <td>
                            <c:if test="${employee.sex==0}">男</c:if>
                            <c:if test="${employee.sex==1}">女</c:if>
                        </td>
                        <td>
                            <c:if test="${fn:contains(employee.nationality,'族')}">
                                ${employee.nationality}
                            </c:if>
                            <c:if test="${!fn:contains(employee.nationality,'族')}">
                                ${employee.nationality}族
                            </c:if>
                        </td>
                        <td><fmt:formatDate value="${employee.enterPartyTime}" pattern="yyyy-MM"/></td>
                        <td><fmt:formatDate value="${employee.workTime}" pattern="yyyy-MM"/></td>
                        <td>${employee.mobile}</td>
                        <td class="center">
                            <a href="${ctx}/admin/rs/toUpdateEmployee.json?id=${employee.id}" class="stdbtn" title="编辑">编辑</a>
                            <a href="${ctx}/admin/rs/getEmployeeInfo.json?id=${employee.id}" class="stdbtn" title="查看">查看</a>
                            <%--<a href="${ctx}/admin/rs/employeeFamilyList.json?family.employeeId=${employee.id}"--%>
                               <%--class="stdbtn" title="家庭成员列表">家庭成员</a>--%>
                            <a href="javascript:void(0)" class="stdbtn" title="删除"
                               onclick="delEmployee(${employee.id})">删除</a>
                            <a href="${ctx}/admin/rs/toUpdateEmployeeCardNo.json?id=${employee.id}" class="stdbtn"
                               title="修改一卡通编号">修改一卡通编号</a>
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