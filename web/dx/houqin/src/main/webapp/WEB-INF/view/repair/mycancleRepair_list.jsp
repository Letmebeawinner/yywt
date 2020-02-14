<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>我处理的报修</title>
    <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script>
    <script type="text/javascript">
        function searchForm() {
            jQuery("#searchForm").submit();
        }

        function delRepair(id) {
            if (confirm("确定删除这个报修吗？")) {
                jQuery.ajax({
                    url: "${ctx}/admin/houqin/delRepair.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    cache: false,
                    success: function (result) {
                        if (result.code == 0) {
                            alert(result.message);
                            window.location.href = "/admin/houqin/cancelRepairList.json.json";
                        } else {
                            alert(result.message);
                            return;
                        }
                    }
                });
            }
        }
        function canclRepair(id,status) {
            if (confirm("确定取消这个报修吗？")) {
                jQuery.ajax({
                    url: "${ctx}/admin/houqin/cancleRepair.json",
                    data: {"repair.id": id,
                        "repair.status":status},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code == 0) {
                            alert(result.message);
                            window.location.href = "/admin/houqin/queryMyRepairList.json";
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
        <h1 class="pagetitle">我处理的报修</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于查看我处理的报修；<br>
            2.删除：点击<span style="color:red">删除</span>，删除我的报修处理<br>
            2.处理：点击<span style="color:red">处理</span>，修改我的报修处理状态<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 数据显示列表，开始 -->
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <colgroup>
                    <col class="con0"/>
                    <col class="con1"/>
                    <col class="con0"/>
                    <col class="con1"/>
                    <col class="con0"/>
                    <col class="con1"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">序号</th>
                    <th class="head0 center">报修编号</th>
                    <th class="head0 center">报修物品</th>
                    <th class="head0 center">报修分类</th>
                    <th class="head0 center">报修时间</th>
                    <th class="head0 center">预警时间</th>
                    <th class="head0 center">状态</th>
                    <th class="head1 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${repairList}" var="repair" varStatus="state">
                    <tr>
                        <td>${state.index+1}</td>
                        <td>${repair.number}</td>
                        <td>${repair.name}</td>
                        <td>${repair.typeName}</td>
                        <td><fmt:formatDate value="${repair.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td><fmt:formatDate value="${repair.warnTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>
                            <c:if test="${repair.status==0}"><font style="color: red">未处理</font></c:if>
                            <c:if test="${repair.status==1}"><font style="color:orange">正维修</font></c:if>
                            <c:if test="${repair.status==2}"><font style="color:green">已维修</font></c:if>
                            <c:if test="${repair.status==3}"><font style="color:#ccc">已取消</font></c:if>
                        </td>
                        <td class="center">
                            <c:if test="${repair.status!=3}">
                                <a href="${ctx}/admin/houqin/toUpdateRepairStatus.json?id=${repair.id}" class="stdbtn" title="处理">处理</a>
                            </c:if>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delRepair('${repair.id}')">删除</a>
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