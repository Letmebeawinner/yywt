<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>计生申请审核</title>
    <script type="text/javascript" src="${ctx}/static/admin/js/fertility.js"></script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab" style="margin-left: 10px">
        <h1 class="pagetitle">计生申请审核</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来下载申请表及进行计生申请的审<br>
                2. 下载申请表：点击操作列中的<span style="color:red">下载申请表</span>按钮下载申请表；<br>
                3. 审核：查看申请表信息若申请通过，点击操作列中的<span style="color:red">审核</span>按钮通过申请审核。
        </span>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="getFertilityList" action="${ctx}/admin/rs/toApprovalFertilityList.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">申请人姓名 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" placeholder="输入申请人姓名" name="queryFertility.name" value="${queryFertility.name}">
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
                    <th class="head0 center">id</th>
                    <th class="head0 center" width="10%">上传人姓名</th>
                    <th class="head0 center">申请人姓名</th>
                    <%--<th class="head1">申请表地址</th>--%>
                    <th class="head1">申请时间</th>
                    <td class="head0 center" width="25%">
                        操作
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${fertilityList}" var="fertility" varStatus="index">
                    <tr>
                        <td>${index.count}</td>
                        <td>${fertility.employeeName}</td>
                        <td>${fertility.name}</td>
                        <%--<td>${fertility.url}</td>--%>
                        <td><fmt:formatDate value="${fertility.createTime}" pattern="yyyy-MM-dd"></fmt:formatDate></td>
                        <td class="center">
                                <a href="${fertility.url}" class="stdbtn" title="下载申请表">下载申请表</a>
                                <a href="javascript:void(0)" class="stdbtn" title="通过审核" onclick="passFertility(${fertility.id})">通过审核</a>
                                <a href="javascript:void(0)" class="stdbtn" title="拒绝反馈" onclick="notPassFertility(${fertility.id})">拒绝反馈</a>
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