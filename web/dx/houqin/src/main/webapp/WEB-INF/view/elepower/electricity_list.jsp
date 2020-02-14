<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<%-- <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>供电局抄表数列表</title>
    <script type="text/javascript" src="${ctx}/static/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">

        function searchForm() {
            jQuery("#searchForm").submit();
        }

        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }


        function delMeetingTopic(id) {
            if (confirm("确定删除吗？")) {
                jQuery.ajax({
                    url: "${ctx}/admin/houqin/delElePower.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        alert(result.message);
                        if (result.code === "0") {
                            window.location.href = "/admin/houqin/elePowerList.json";
                        }
                    }
                });
            }
        }


    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">供电局抄表数列表</h1>
        <span>
                    <span style="color:red">说明</span><br/>
					1.本页面展示供电局抄表数列表.<br/>
					2.可通过名称查询对应的供电局抄表数列表.<br/>
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/elePowerList.json"
                      method="get">
                    <div class="disIb ml20 mb10">
                        <span class="vam">区域分类 &nbsp;</span>
                        <label class="vam">
                            <select name="typeId" style="width: 150px">
                                <option value="">未选择</option>
                                <c:forEach items="${typeList}" var="type">
                                    <option value="${type.id}" <c:if test="${type.id == elePower.typeId}">selected="selected"</c:if> >${type.type}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">录入人 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="userName" type="text"
                                   value="${elePower.userName}" placeholder="请输入录入人">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="${ctx}/admin/houqin/addElePower.json" class="stdbtn ml10">添 加</a>
                    <a href="${ctx}/admin/houqin/import/toElePowerImport.json" class="stdbtn ml10">导入</a>
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
                    <th class="head0 center">区域</th>
                    <th class="head0 center">上期读数</th>
                    <th class="head0 center">本期读数</th>
                    <th class="head0 center">用电度数</th>
                    <th class="head0 center">单价</th>
                    <th class="head0 center">倍率</th>
                    <th class="head0 center">电费</th>
                    <th class="head0 center">录入时间</th>
                    <th class="head0 center">录入人</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${powers!=null&&powers.size()>0 }">
                    <c:forEach items="${powers}" var="pow" varStatus="status">
                        <tr>
                            <td>${status.count}</td>
                            <td>
                                <c:forEach items="${typeList}" var="type">
                                    <c:if test="${type.id==pow.typeId}">${type.type}</c:if>
                                </c:forEach>
                            </td>
                            <td>${pow.previousDegrees}</td>
                            <td>${pow.currentDegrees}</td>
                            <td>${pow.degrees}</td>
                            <td>${pow.price}</td>
                            <td>${pow.rate}</td>
                            <td>${pow.eleFee}</td>
                            <td>
                                <fmt:formatDate value="${pow.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                            </td>
                            <td>${pow.userName}</td>
                            <td class="center">
                                <a href="${ctx}/admin/houqin/updateElePower.json?id=${pow.id}"
                                   class="stdbtn" title="编辑">编辑</a>
                                <a href="javascript:void(0)" onclick="delMeetingTopic('${pow.id}')"
                                   class="stdbtn" title="删除">删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
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