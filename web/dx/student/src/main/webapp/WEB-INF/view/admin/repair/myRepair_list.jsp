<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>报修列表</title>
    <script type="text/javascript">
        function searchForm() {
            jQuery("#searchForm").submit();
        }

        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("#status").val("");
            jQuery("#typeId").val("");
        }

        function delRepair(id) {
            if (confirm("确定删除这个报修吗？")) {
                jQuery.ajax({
                    url: "${ctx}/admin/houqin/delRepair.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code == "0") {
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
        function canclRepair(id, status) {
            if (confirm("确定取消这个报修吗？")) {
                jQuery.ajax({
                    url: "${ctx}/admin/houqin/cancleRepair.json",
                    data: {
                        "repair.id": id,
                        "repair.status": status
                    },
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
        <h1 class="pagetitle">报修列表</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于我的报修物品列表查看；<br>
            2.取消：点击<span style="color:red">取消</span>修改报修状态为取消；<br>
            3.删除：点击<span style="color:red">删除</span>，删除我的报修；<br>
            4.详情：点击<span style="color:red">详情</span>，查看我的报修物品详细信息；<br>
        </div>
    </div>
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/student/repair/queryMyRepairList.json"
                      method="post">

                    <div class="disIb ml20 mb10">
                        <span class="vam">维修分类 &nbsp;</span>
                        <label class="vam">
                            <select name="repair.typeId" style="width: 200px" id="typeId">
                                <option value="">--全部--</option>
                                <c:forEach items="${repairTypeList}" var="repairType">
                                    <option value="${repairType.id}"
                                            <c:if test="${repairType.id==repair.typeId}">selected="selected"</c:if>>${repairType.name}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">状态 &nbsp;</span>
                        <label class="vam">
                            <select name="repair.status" style="width: 150px" id="status">
                                <option value="">全部</option>
                                <option value="0" <c:if test="${repair.status==0}">selected="selected"</c:if>>未处理
                                </option>
                                <option value="1" <c:if test="${repair.status==1}">selected="selected"</c:if>>正维修
                                </option>
                                <option value="2" <c:if test="${repair.status==2}">selected="selected"</c:if>>已维修
                                </option>
                                <option value="3" <c:if test="${repair.status==3}">selected="selected"</c:if>>已取消
                                </option>
                            </select>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="${ctx}/admin/student/repair/toAddRepair.json" class="stdbtn btn_orange">申请报修</a>
                </div>
            </div>
        </div>
        <!-- 搜索条件，结束 -->

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
                    <th>序号</th>
                    <th class="head0 center">报修编号</th>
                    <th class="head0 center">报修物品</th>
                    <th class="head0 center">报修分类</th>
                    <th class="head0 center">报修时间</th>
                    <th class="head0 center">状态</th>
                    <th class="head0 center">处理人</th>
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
                        <td>
                            <c:if test="${repair.status==0}"><font style="color: red">未处理</font></c:if>
                            <c:if test="${repair.status==1}"><font style="color:orange">正维修</font></c:if>
                            <c:if test="${repair.status==2}"><font style="color:green">已维修</font></c:if>
                            <c:if test="${repair.status==3}"><font style="color:#ccc">已取消</font></c:if>
                        </td>
                        <td>${repair.sysUser.userName}</td>
                        <td class="center">
                            <a href="/admin/student/repair/descRepair.json?id=${repair.id}" class="stdbtn"
                               title="详情">详情</a>
                                <%--<c:if test="${repair.status!=3}">
                                    <a href="javascript:void(0)" class="stdbtn" title="编辑" onclick="canclRepair('${repair.id}','3')">取消</a>
                                </c:if>
                                <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delRepair('${repair.id}')">删除</a>--%>
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