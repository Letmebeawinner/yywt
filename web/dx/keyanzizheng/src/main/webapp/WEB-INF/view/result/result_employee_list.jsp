<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>课题人员列表</title>
    <script type="text/javascript" src="${ctx}/static/admin/js/taskemployee.js"></script>
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
        
    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab" style="margin-left: 10px">
        <h1 class="pagetitle">
            <c:if test="${result.resultForm==3}">
            课题人员列表</c:if>
            <c:if test="${result.resultForm==2}">
                参编人员列表</c:if>
        </h1>
        <span>
                <span style="color:red">说明</span><br>
            <c:if test="${result.resultForm==3}">
                1. 本页面用来新增、移除课题组成员<br>
                </c:if>
             <c:if test="${result.resultForm==2}">
                 1. 本页面用来新增、移除参编人员<br>
                 </c:if>
            2. 新增成员：点击搜索框中最后的<span style="color:red">添加成员</span>按钮添加成员；<br>
            3. 移除成员：点击操作列中的<span style="color:red">移除</span>按钮移除成员。
        </span>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="getEmployeeList" action="${ctx}/admin/ky/getResultEmployeeList.json" method="post">
                    <input type="hidden" name="employee.resultId" id="resultId" value="${employee.resultId}">
                    <div class="disIb ml20 mb10">
                        <span class="vam">ID &nbsp;</span>
                        <label class="vam">
                            <input type="text" onkeyup='this.value=this.value.replace(/\D/gi,"")' class="hasDatepicker" placeholder="输入用户ID" name="employee.id" value="${employee.id}">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">姓名 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" placeholder="输入用户名称" name="employee.name" value="${employee.name}">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">电话 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" placeholder="输入用户电话" name="employee.mobile" value="${employee.mobile}">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchForm()">搜 索</a>
                    <a href="javascript: void(0)" class="stdbtn ml10" onclick="emptyForm()">重 置</a>
                    <a href="javascript:void(0)" class="stdbtn" title="添加成员" onclick="selectEmployee(${employee.resultId})">添加成员</a>
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
                <c:forEach items="${employeeList}" var="employee">
                    <tr>
                        <td>${employee.id}</td>
                        <td>${employee.name}</td>
                        <td>
                            <c:if test="${employee.sex==1}">男</c:if>
                            <c:if test="${employee.sex==2}">女</c:if>
                        </td>
                        <td>${employee.mobile}</td>
                        <td>${employee.nationality}</td>
                        <td class="center">
                            <a href="javascript:void(0)" class="stdbtn" title="移除" onclick="delEmployee(${employee.id})">移除</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- 分页，开始 -->
            <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
            <!-- 分页，结束 -->
        </div><!-- centercontent -->
    </div>
</div>
</body>
</html>