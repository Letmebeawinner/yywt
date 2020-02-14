<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>用电量列表</title>
    <script type="text/javascript">
        //计算用电量
        jQuery(document).ready(function () {
            var totalRow = 0;
            jQuery('#mytable').find("tr").each(function () {
                jQuery(this).find('td:eq(4)').each(function () {
                    totalRow += parseFloat(jQuery(this).text());
                });
            });

            // 改变span的值
            jQuery('#count').html(totalRow);
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

        function delElectricity(id) {
            if (confirm("确定删除吗？")) {
                jQuery.ajax({
                    url: "${ctx}/admin/houqin/delElectricity.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code == 0) {
                            alert(result.message);
                            window.location.href = "/admin/houqin/queryAllElectricity.json";
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
                    url: "${ctx}/admin/houqin/confirmEle.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code == 0) {
                            alert(result.message);
                            window.location.href = "/admin/houqin/queryAllElectricity.json";
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
        <h1 class="pagetitle">用电量列表</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于用电列表查看；<br>
            2.查询：输入查询条件，点击<span style="color:red">搜索</span>；<br>
            3.编辑：点击<span style="color:red">编辑</span>修改用电信息；<br>
            4.删除：点击<span style="color:red">删除</span>，删除用电信息；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/queryAllElectricity.json" method="post">

                    <div class="disIb ml20 mb10">
                        <span class="vam">区域分类 &nbsp;</span>
                        <label class="vam">
                            <select name="electricity.typeId" style="width: 150px">
                                <option value="">未选择</option>
                                <c:forEach items="${typeList}" var="eleType">
                                    <option value="${eleType.id}"
                                            <c:if test="${eleType.id eq electricity.typeId}">selected="selected"</c:if>
                                    >${eleType.type}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam"><p style="width: auto;">统计用电量:
                             <span id="count"></span> 度
                            </p></span>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="${ctx}/admin/houqin/toAddElectricity.json" class="stdbtn ml10">添 加</a>
                    <a href="${ctx}/admin/houqin/import/toElectricityImport.json" class="stdbtn ml10">导入</a>
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
                    <th class="head0 center">录入人</th>
                    <th class="head0 center">用电区域</th>
                    <th class="head0 center">二级用电区域</th>
                    <th class="head0 center">用电度数</th>
                    <th class="head0 center">单价</th>
                    <th class="head0 center">录入时间</th>
                    <th class="head0 center">备注</th>
                    <th class="head1 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody id="mytable">
                <c:forEach items="${electricityList}" var="electricity" varStatus="index">
                    <tr>
                        <td>${index.count}</td>
                        <td>${electricity.userName}</td>
                        <td>
                            <c:forEach items="${typeList}" var="eleType">
                                <c:if test="${eleType.id eq electricity.typeId}">${eleType.type}</c:if>
                            </c:forEach>
                        </td>
                        <td>${electricity.secTypeName}</td>
                        <td>${electricity.degrees}</td>
                        <td>${electricity.price}</td>
                        <td><fmt:formatDate value="${electricity.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>${electricity.context}</td>
                        <td class="center">
                            <c:if test="${flag=='true'&&electricity.affirm==0}">
                                <a href="javascript:void(0)" class="stdbtn" title="确认"
                                   onclick="confirms('${electricity.id}')">确认</a>
                            </c:if>
                            <c:if test="${electricity.affirm==0||roleIds==10}">
                                <a href="${ctx}/admin/houqin/toUpdateElectricity.json?id=${electricity.id}"
                                   class="stdbtn" title="编辑">编辑</a>
                                <a href="javascript:void(0)" class="stdbtn" title="删除"
                                   onclick="delElectricity('${electricity.id}')">删除</a>
                            </c:if>
                            <c:if test="${electricity.affirm==1}">
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