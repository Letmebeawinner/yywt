<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>小组人员列表</title>
    <script type="text/javascript" src="${ctx}/static/admin/js/position.js"></script>
    <script type="text/javascript" src="${ctx}/static/admin/js/union.js"></script>
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
        function delEmployee(id) {
            if (confirm("确定将该教职工从该组中移除？")) {
                jQuery.ajax({
                    url: "${ctx}/admin/rs/deleteUnionEmployee.json?",
                    data: {
                        'unionEmployee.unionId': jQuery("#unionId").val(),
                        'unionEmployee.employeeId': id
                    },
                    type: "post",
                    dataType: "json",
                    async: false,
                    success: function (result) {
                        if (result.code == "0") {
                            alert(result.message);
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
        <h1 class="pagetitle">小组人员列表</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来添加、移除除人员人员及设置人员职位<br>
                2. 添加人员：点击搜索框中最后的<span style="color:red">添加成员</span>按钮进行添加；<br>
                3. 移除人员：点击操作列中的<span style="color:red">删除</span>按钮删除人员信息；<br>
                4. 设置职位：点击操作列中的<span style="color:red">设置职位</span>按钮设置人员职位。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="getEmployeeList" action="${ctx}/admin/rs/getUnionEmployeeList.json"
                      method="post">
                    <input type="hidden" name="unionEmployee.unionId" id="unionId" value="${unionEmployee.unionId}">
                    <div class="disIb ml20 mb10">
                        <span class="vam">ID &nbsp;</span>
                        <label class="vam">
                            <input type="text" onkeyup='this.value=this.value.replace(/\D/gi,"")' class="hasDatepicker"
                                   placeholder="输入用户ID" name="unionEmployee.id" value="${unionEmployee.id}">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchForm()">搜 索</a>
                    <a href="javascript: void(0)" class="stdbtn ml10" onclick="emptyForm()">重 置</a>
                    <a href="javascript:void(0)" class="stdbtn" title="添加成员" onclick="addEmployee(${unionEmployee.unionId})">添加成员</a>
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
                    <th class="head0 center">id</th>
                    <th class="head0 center">姓名</th>
                    <th class="head1">性别</th>
                    <th class="head1">电话</th>
                    <th class="head1">民族</th>
                    <td class="head0 center">
                        操作
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${unionEmployeeList}" var="employee">
                    <tr>
                        <td>${employee.id}</td>
                        <td>${employee.employeeName}</td>
                        <td>
                            <c:if test="${employee.sex==0}">男</c:if>
                            <c:if test="${employee.sex==1}">女</c:if>
                        </td>
                        <td>${employee.mobile}</td>
                        <td>${employee.nationality}</td>
                        <td class="center">
                            <a href="${ctx}/admin/rs/getEmployeeInfo.json?id=${employee.employeeId}" class="stdbtn" title="查看">查看</a>
                            <a href="javascript:void(0)" class="stdbtn" title="移除" onclick="delEmployee(${employee.employeeId})">移除</a>
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