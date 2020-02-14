<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>器材列表</title>
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
            jQuery("#status").val(-1);
        }

        function delEquipment(id) {
            if (confirm("确定删除这个器材吗？")) {
                jQuery.ajax({
                    url: "${ctx}/admin/houqin/delEquipment.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code == 0) {
                            alert(result.message);
                            window.location.href = "/admin/houqin/queryAllEquipment.json";
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
        <h1 class="pagetitle">消防器材列表</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于消防器材列表查看；<br>
            2.查询：输入查询条件，点击<span style="color:red">搜索</span>；<br>
            3.编辑：点击<span style="color:red">编辑</span>修改消防器材信息；<br>
            4.删除：点击<span style="color:red">删除</span>，删除消防器材信息；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/queryAllEquipment.json" method="post">

                    <div class="disIb ml20 mb10">
                        <span class="vam">器材名称 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="name" type="text" class="hasDatepicker" value="${equipment.name}" placeholder="器材名称">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">使用情况 &nbsp;</span>
                        <label class="vam">
                            <select name="status" style="width: 200px;" id="status">
                                <option value="-1">请选择</option>
                                <option value="0" <c:if test="${status==0}">selected="selected"</c:if>>未使用</option>
                                <option value="1" <c:if test="${status==1}">selected="selected"</c:if>>已使用</option>
                            </select>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="${ctx}/admin/houqin/toAddEquipment.json"  class="stdbtn ml10">添 加</a>
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
                    <th class="head0 center">器材名称</th>
                    <th class="head0 center">消防器材位置</th>
                    <th class="head0 center">器材维护信息</th>
                    <th class="head0 center">数量</th>
                    <th class="head0 center">单位</th>
                    <th class="head0 center">使用情况</th>
                    <th class="head0 center">添加时间</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${equipmentList}" var="equipment">
                    <tr>
                        <td>${equipment.name}</td>
                        <td>${equipment.location}</td>
                        <td>${equipment.context}</td>
                        <td>${equipment.num}</td>
                        <td>${equipment.unitName}</td>
                        <td>
                            <c:if test="${equipment.status==0}"><font style="color: green">未使用</font> </c:if>
                            <c:if test="${equipment.status==1}"><font style="color: red">已使用</font></c:if>
                        </td>
                        <td><fmt:formatDate value="${equipment.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td class="center">
                            <a href="${ctx}/admin/houqin/toUpdateEquipment.json?id=${equipment.id}" class="stdbtn" title="编辑">编辑</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delEquipment('${equipment.id}')">删除</a>
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