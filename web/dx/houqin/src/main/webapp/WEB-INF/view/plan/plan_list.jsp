<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>就餐计划列表</title>
    <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script>
    <script type="text/javascript">
        function searchForm() {
            jQuery("#searchForm").submit();
        }

        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        function delMenuType(id) {
            if (confirm("确定删除吗？")) {
                jQuery.ajax({
                    url: "${ctx}/admin/houqin/delPlan.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code == 0) {
                            alert(result.message);
                            window.location.href = "${ctx}/admin/houqin/selectAllPlan.json";
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
        <h1 class="pagetitle">就餐计划列表</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/selectAllPlan.json" method="post">

                    <div class="disIb ml20 mb10">
                        <span class="vam">ID &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="plan.id" type="text" class="hasDatepicker"
                                   value="${plan.id}" placeholder="输入id">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="${ctx}/admin/houqin/toAddPlan.json"  class="stdbtn ml10">添 加</a>
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
                    <th class="head0 center">ID</th>
                    <th class="head0 center">就餐要求</th>
                    <th class="head0 center">就餐时间</th>
                    <th class="head0 center" width="20%">班次</th>
                    <th class="head0 center">开班时间</th>
                    <th class="head0 center">结束时间</th>
                    <th class="head0 center">班主任姓名</th>
                    <th class="head0 center">班主任电话</th>
                    <th class="head0 center">人数</th>
                    <th class="head0 center">标准</th>
                    <th class="head0 center">食堂</th>
                    <th class="head0 center">就餐区域</th>
                    <th class="head1 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${planList}" var="plan">
                    <tr>
                        <td>${plan.id}</td>
                        <td>${plan.requ}</td>
                        <td><fmt:formatDate value="${plan.planTime}" pattern="yyyy-MM-dd"/></td>
                        <td>
                            <c:forEach items="${classTypeMap}" var="cls">
                                <c:if test="${cls.key==plan.classType}">
                                    ${cls.value}
                                </c:if>
                            </c:forEach>
                                的
                            <c:forEach items="${classesList}" var="cl">
                                <c:if test="${cl.id == plan.classId}">
                                    ${cl.name}
                                </c:if>
                            </c:forEach>
                        </td>
                        <td>
                            <c:forEach items="${classesList}" var="cl">
                                <c:if test="${cl.id == plan.classId}">
                                    ${fn:replace(cl.startTime,"00:00:00","")}
                                </c:if>
                            </c:forEach>
                        </td>
                        <td>
                            <c:forEach items="${classesList}" var="cl">
                                <c:if test="${cl.id == plan.classId}">
                                    ${fn:replace(cl.endTime,"00:00:00","")}
                                </c:if>
                            </c:forEach>
                        </td>
                        <td>
                            <c:forEach items="${classesList}" var="cl">
                                <c:if test="${cl.id == plan.classId}">
                                    ${cl.teacherName}
                                </c:if>
                            </c:forEach>
                        </td>
                        <td>
                            <c:forEach items="${classesList}" var="cl">
                                <c:if test="${cl.id == plan.classId}">
                                    ${cl.teacherMobile}
                                </c:if>
                            </c:forEach>
                        </td>
                        <td>${plan.people}</td>
                        <td>${plan.standard}</td>
                        <td>
                            <c:forEach items="${messes}" var="ms">
                                <c:if test="${ms.id == plan.messId}">
                                    ${ms.name}
                                </c:if>
                            </c:forEach>
                        </td>
                        <td>
                            <c:forEach items="${messAreas}" var="ms">
                                <c:if test="${ms.id == plan.areaId}">
                                    ${ms.name}
                                </c:if>
                            </c:forEach>
                        </td>
                        <td class="center">
                            <a href="${ctx}/admin/houqin/lunchManagement.json?id=${plan.id}" class="stdbtn"
                               title="早中餐管理">早中餐管理</a>
                            <a href="${ctx}/admin/houqin/toUpdatePlan.json?id=${plan.id}" class="stdbtn"
                               title="编辑">编辑</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除"
                               onclick="delMenuType('${plan.id}')">删除</a>
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
</div>
</body>
</html>