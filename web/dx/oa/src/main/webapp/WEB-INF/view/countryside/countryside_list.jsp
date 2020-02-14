<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>下乡帮扶人员列表</title>
    <script type="text/javascript" src="${ctx}/static/js/countryside.js"></script>
    <script type="text/javascript">

    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab" style="margin-left: 10px">
        <h1 class="pagetitle">下乡帮扶人员列表</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来新建、编辑、删除下乡帮扶人员，查看项目需要参与的人员<br>
        </span>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="getCountrysideList" action="${ctx}/admin/oa/getCountrysideList.json" method="post">
                </form>
                <div class="disIb ml20 mb10">
                    <a href="${ctx}/admin/oa/toAddCountryside.json" class="stdbtn btn_orange" >新建</a>
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
                    <th class="head1">序号</th>
                    <th class="head1">姓名</th>
                    <th class="head1">下乡地点</th>
                    <th class="head1">下乡内容</th>
                    <th class="head1">开始时间</th>
                    <th class="head1">结束时间</th>
                    <%--<th class="head1">是否兑现浮动工资和艰边津贴</th>--%>
                    <th class="head1">预参与人数</th>
                    <th class="head1">已参加人数</th>
                    <td class="head0 center">
                        操作
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${countrysideList}" var="countryside" varStatus="index">
                    <tr>
                        <td>${countryside.id}</td>
                        <td>${countryside.name}</td>
                        <td>${countryside.place}</td>
                        <td>${countryside.content}</td>
                        <td><fmt:formatDate value="${countryside.beginTime}" pattern="yyyy-MM-dd"></fmt:formatDate></td>
                        <td><fmt:formatDate value="${countryside.endTime}" pattern="yyyy-MM-dd"></fmt:formatDate></td>
                        <td>${countryside.number}</td>
                        <td>${countryside.joinNumber}</td>
                        <td class="center">
                            <a href="${ctx}/admin/oa/toUpdateCountryside.json?id=${countryside.id}" class="stdbtn" title="编辑">编辑</a>
                            <a href="${ctx}/admin/oa/getCountrysideEmpList.json?countrysideEmp.countrysideId=${countryside.id}" class="stdbtn" title="参与人员">预参与人员</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delCountryside(${countryside.id})">删除</a>
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