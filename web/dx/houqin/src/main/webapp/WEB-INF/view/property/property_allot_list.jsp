z
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>资产分类列表</title>
    <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script>
    <script type="text/javascript">
        function searchForm() {
            jQuery("#searchForm").submit();
        }

        function cancelAllot(id) {
            jQuery.ajax({
                url: "${ctx}/admin/houqin/cancelAllot.json",
                data: {"id": id},
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == 0) {
                        alert(result.message);
                        window.location.href = "/admin/houqin/propertyAllotList.json";
                    } else {
                        alert(result.message);
                        return;
                    }
                }
            });
        }

        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        function delProperty(id) {
            if (confirm("确定删除这个资产类型吗？")) {
                jQuery.ajax({
                    url: "${ctx}/admin/houqin/delProperty.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code == 0) {
                            alert(result.message);
                            window.location.href = "/admin/houqin/queryAllProperty.json";
                        } else {
                            alert(result.message);
                            return;
                        }
                    }
                });
            }
        }
    </script>
    <style type="text/css">
        .diaobo{background-color: #dd4b39 !important;padding:5px;border-radius:5px;color:#FFF;font-weight: 400}
        .diaobowancheng{background-color: #00B83F !important;padding:5px;border-radius:5px;color:#FFF;font-weight: 400}
    </style>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">资产调拨列表</h1>
    </div>
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/propertyAllotList.json" method="post">


                    <div class="disIb ml20 mb10">
                        <span class="vam">资产类型 &nbsp;</span>
                        <label class="vam">
                            <select name="propertyAllot.propertyTypeId">
                                <option value="">--请选择--</option>
                                <c:forEach items="${propertyList}" var="property">
                                    <option value="${property.id}"
                                            <c:if test="${property.id==propertyAllot.propertyTypeId}">selected</c:if>>${property.typeName}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="${ctx}/admin/houqin/propertyallot.json" class="stdbtn ml10">添加调拨</a>
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
                    <th class="head0 center">状态</th>
                    <th class="head0 center">调入人</th>
                    <th class="head0 center">处理人</th>
                    <th class="head0 center">资产名称</th>
                    <th class="head0 center">资产类型</th>
                    <th class="head0 center">调拨时间</th>
                    <th class="head0 center">处理时间</th>
                    <th class="head1 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${propertyAllotList}" var="property">
                    <tr>
                        <td>${property.id}</td>
                        <td>
                              <c:if test="${property.status==1}"><font class="diaobowancheng">调拨完成</font></c:if>
                              <c:if test="${property.status==0}"><font class="diaobo">调拨已取消</font></c:if>
                              <c:if test="${property.status==2}"><font class="diaobo">调拨已取消</font></c:if>
                        </td>
                        <td>${property.name}</td>
                        <td>${property.userName}</td>
                        <td>${property.propertyName}</td>
                        <td>${property.typeName}</td>
                        <td><fmt:formatDate value="${property.allotTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td><fmt:formatDate value="${property.handleTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td class="center">
                            <c:if test="${property.status==1}">
                                <a href="javascript:void(0)" class="stdbtn" title="取消已调拨"
                                   onclick="cancelAllot(${property.id})">取消调拨</a>
                            </c:if>

                            <c:if test="${property.status==2||property.status==0}">
                                <a href="javascript:void(0)" class="stdbtn btn_orange" title="已取消">已取消</a>
                            </c:if>


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