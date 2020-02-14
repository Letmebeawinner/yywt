<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>水表列表</title>
    <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script>
    <script type="text/javascript">

        jQuery(function () {
            laydate.skin('molv');
            laydate({
                elem: '#auditStartTime',
                format:'YYYY-MM-DD hh:mm:ss',
                istime: true
            });
            laydate({
                elem: '#auditEndTime',
                format:'YYYY-MM-DD hh:mm:ss',
                istime: true
            });
        });

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

        function delWater(id) {
            if (confirm("确定删除吗？")) {
                jQuery.ajax({
                    url: "${ctx}/admin/houqin/delWater.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        alert(result.message);
                        if (result.code === "0") {
                            window.location.href = "/admin/houqin/queryAllWater.json";
                        }
                    }
                });
            }
        }

        function confirms(id) {
            if (confirm("确定确认吗？")) {
                jQuery.ajax({
                    url: "${ctx}/admin/houqin/confirmWater.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code == 0) {
                            alert(result.message);
                            window.location.href = "/admin/houqin/queryAllWater.json";
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
        <h1 class="pagetitle">用水量列表</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于用水量列表查看；<br>
            2.编辑：点击<span style="color:red">编辑</span>修改用水量信息；<br>
            3.删除：点击<span style="color:red">删除</span>，删除用水量信息；<br>
            4.输入录入时间, 将根据录入时间查询记录；<br>
            5.只输入录入时间第一项,将查询此时间之后的录入记录.；<br>
            6.只输入录入时间第二项,将查询此时间之前的录入记录.；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/queryAllWater.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">区域分类 &nbsp;</span>
                        <label class="vam">
                            <select name="water.waterType" style="width: 150px">
                                <option value="">未选择</option>
                                <c:forEach items="${waterTypeList}" var="waterType">
                                    <option value="${waterType.id}" <c:if test="${waterType.id==water.waterType}">selected</c:if>>${waterType.type}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">录入时间 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" type="text"  readonly  name="previousTime"
                                   value="${previousTime}"
                                   id = "auditStartTime">
                        </label>
                        <span class="vam"> &nbsp; - &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" type="text" readonly name="afterTime"
                                   value="${afterTime}"
                                   id = "auditEndTime">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="${ctx}/admin/houqin/toAddWater.json" class="stdbtn ml10">添 加</a>
                    <a href="${ctx}/admin/houqin/import/toWaterImport.json" class="stdbtn ml10">导入</a>
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
                    <th class="head0 center">用水区域</th>
                    <th class="head0 center">录入人</th>
                    <th class="head0 center">用水吨数</th>
                    <th class="head0 center">单价</th>
                    <th class="head0 center">年月</th>
                    <th class="head0 center">录入时间</th>
                    <th class="head1 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${waterList}" var="water" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${water.waterTypeName}</td>
                        <td>${water.userName}</td>
                        <td>${water.tunnage}</td>
                        <td>${water.price}</td>
                        <td>${water.monthTime}</td>
                        <td><fmt:formatDate value="${water.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td class="center">
                            <c:if test="${water.affirm==0&&flag=='true'}">
                                <a href="javascript:void(0)" class="stdbtn" title="确认"
                                   onclick="confirms('${water.id}')">确认</a>
                            </c:if>
                            <c:if test="${water.affirm==0||roleIds==10}">
                                <a href="${ctx}/admin/houqin/toUpdateWater.json?id=${water.id}" class="stdbtn"
                                   title="编辑">编辑</a>
                                <a href="javascript:void(0)" class="stdbtn" title="删除"
                                   onclick="delWater('${water.id}')">删除</a>
                            </c:if>
                            <c:if test="${water.affirm==1}">
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