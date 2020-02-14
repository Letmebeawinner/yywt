<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>计生申请列表</title>
    <script type="text/javascript" src="${ctx}/static/admin/js/fertility.js"></script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab" style="margin-left: 10px">
        <h1 class="pagetitle">计生申请列表</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来新建、编辑、删除计生信息，上传、下载合同<br>
                2. 申请计生信息：点击搜索框中最后的<span style="color:red">申请</span>按钮就行计生申请；<br>
                3. 编辑计生信息：点击操作列中的<span style="color:red">编辑</span>按钮编辑计生信息；<br>
                4. 删除计生信息：点击操作列中的<span style="color:red">删除</span>按钮删除计生信息；<br>
                5. 上传合同：通过审核的申请，点击操作列中的<span style="color:red">上传合同</span>按钮上传合同；<br>
                6. 下载合同：已上传合同的，点击操作列中的<span style="color:red">下载合同</span>按钮下载合同。
        </span>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="getFertilityList" action="${ctx}/admin/rs/getFertilityList.json" method="post">
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
                    <a href="${ctx}/admin/rs/toAddFertility.json" class="stdbtn ml10">申 请</a>
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
                    <th class="head0 center">上传人姓名</th>
                    <th class="head0 center">申请人姓名</th>
                    <th class="head1">申请表地址</th>
                    <th class="head1">是否通过审核</th>
                    <th class="head1">申请时间</th>
                    <td class="head0 center">
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
                        <td><a href="${fertility.url}">申请表</a></td>
                        <td>
                            <c:if test="${fertility.ifPass==1}">未审核</c:if>
                            <c:if test="${fertility.ifPass==2}">已审核</c:if>
                            <c:if test="${fertility.ifPass==3}">未通过审核</c:if>
                        </td>
                        <td><fmt:formatDate value="${fertility.createTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                        <td class="center">
                            <a href="${ctx}/admin/rs/toUpdateFertility.json?id=${fertility.id}" class="stdbtn" title="编辑">编辑</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delFertility(${fertility.id})">删除</a>
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