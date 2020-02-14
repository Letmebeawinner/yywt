<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>油用量列表</title>
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

        function delOil(id) {
            if (confirm("确定删除吗？")) {
                jQuery.ajax({
                    url: "${ctx}/admin/houqin/delOil.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code == 0) {
                            alert(result.message);
                            window.location.href = "/admin/houqin/queryAllOil.json";
                        } else {
                            alert(result.message);
                            return;
                        }
                    }
                });
            }
        }

        function confirms(id) {
            if (confirm("确定确认吗？")) {
                jQuery.ajax({
                    url: "${ctx}/admin/houqin/confirmOil.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code == 0) {
                            alert(result.message);
                            window.location.href = "/admin/houqin/queryAllOil.json";
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
        <h1 class="pagetitle">油用量列表</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用油用量列表查看；<br>
            2.查询：输入查询条件，点击<span style="color:red">搜索</span>；<br>
            3.编辑：点击<span style="color:red">编辑</span>修改油用信息；<br>
            4.删除：点击<span style="color:red">删除</span>，删除油用量信息；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/queryAllOil.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">油类型 &nbsp;</span>
                        <label class="vam">
                            <select name="oil.oilType">
                                <option value="">请选择</option>
                                <c:forEach items="${oilTypeList}" var="type">
                                    <option value="${type.id}"
                                            <c:if test="${oil.oilType==type.id}">selected</c:if>>${type.name}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">油用途： &nbsp;</span>
                        <label class="vam">
                            <select name="oil.purpose">
                                <option value="">请选择</option>
                                <c:forEach items="${oilUseList}" var="use">
                                    <option value="${use.id}"
                                            <c:if test="${oil.purpose==use.id}">selected</c:if>>${use.name}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="${ctx}/admin/houqin/toAddOil.json" class="stdbtn ml10">添 加</a>
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
                    <th class="head0 center">序号</th>
                    <th class="head0 center">油用量(升)</th>
                    <th class="head0 center">油类型</th>
                    <th class="head0 center">油单价(升)</th>
                    <th class="head0 center">外界用能类型</th>
                    <th class="head0 center">录入人</th>
                    <th class="head0 center">录入时间</th>
                    <th class="head1 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${oilDtoList}" var="oilDto" varStatus="status">
                    <tr>
                        <td>${oilDto.id}</td>
                        <td>${oilDto.litre}</td>
                        <td>
                            <c:forEach items="${oilTypeList}" var="oilType">
                                <c:if test="${oilType.id==oilDto.oilType}">${oilType.name}</c:if>
                            </c:forEach>
                        </td>
                        <td>${oilDto.price}</td>
                        <td>
                            <c:if test="${'IRON' eq oilDto.type}">铁塔公司</c:if>
                            <c:if test="${'BUFFET' eq oilDto.type}">小卖部</c:if>
                            <c:if test="${'PARK' eq oilDto.type}">森林公园</c:if>
                            <c:if test="${'OTHER' eq oilDto.type}">其他</c:if>
                        </td>
                        <td>${oilDto.userName}</td>
                        <td><fmt:formatDate value="${oilDto.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td class="center">
                            <c:if test="${flag=='true'&&oilDto.affirm==0}">
                                <a href="javascript:void(0)" class="stdbtn" title="确认"
                                   onclick="confirms('${oilDto.id}')">确认</a>
                            </c:if>

                            <c:if test="${oilDto.affirm==0||roleIds==10}">
                                <a href="${ctx}/admin/houqin/toUpdateOil.json?id=${oilDto.id}" class="stdbtn"
                                   title="编辑">编辑</a>
                                <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delOil('${oilDto.id}')">删除</a>
                            </c:if>
                            <c:if test="${oilDto.affirm==1}">
                                <a href="javascript:void(0)" class="stdbtn btn_orange" title="已确认">已确认</a>
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
</div>
</body>
</html>