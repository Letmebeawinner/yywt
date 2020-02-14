<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>资产信息列表</title>
    <script type="text/javascript">
        function searchForm() {
            jQuery("#searchForm").submit();
        }

        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val('');
        }

        function delPropertyMessage(id) {
            if (confirm("确定删除这条资产吗？")) {
                jQuery.ajax({
                    url: "${ctx}/admin/houqin/deletePropertyMessage.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code == "0") {
                            alert(result.message);
                            window.location.href = "/admin/houqin/queryAllPropertyMessage.json";
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
        <h1 class="pagetitle">资产信息列表</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于资产信息列表查看；<br>
            2.查询：输入查询条件，点击<span style="color:red">搜索</span>；<br>
            3.编辑：点击<span style="color:red">编辑</span>修改资产信息；<br>
            4.删除：点击<span style="color:red">删除</span>，删除资产信息；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/queryAllPropertyMessage.json"
                      method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">资产信息名称 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="propertyMessage.name" type="text" class="hasDatepicker"
                                   value="${propertyMessage.name}" placeholder="资产信息名称">
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">库房名称 &nbsp;</span>
                        <label class="vam">
                            <select style="width: 150px;" name="propertyMessage.storageId">
                                <option value="">请选择</option>
                                <c:forEach var="wareHouse" items="${wareHouseList}">
                                    <option value="${wareHouse.id}"
                                            <c:if test="${wareHouse.id==propertyMessage.storageId}">selected="selected"</c:if>>${wareHouse.name}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">资产类型 &nbsp;</span>
                        <label class="vam">
                            <select style="width: 150px;" name="propertyMessage.propertyId">
                                <option value="">请选择</option>
                                <c:forEach var="property" items="${propertyList}">
                                    <option value="${property.id}"
                                            <c:if test="${property.id==propertyMessage.propertyId}">selected="selected"</c:if>>${property.typeName}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">使用状态 &nbsp;</span>
                        <label class="vam">
                            <select style="width: 100px;" name="propertyMessage.status">
                                <option value="">请选择</option>
                                <option value="1" <c:if test="${propertyMessage.status==1}">selected</c:if>>调拨中</option>
                                <option value="2" <c:if test="${propertyMessage.status==2}">selected</c:if>>借用中</option>
                                <option value="3" <c:if test="${propertyMessage.status==3}">selected</c:if>>领用中</option>
                            </select>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="${ctx}/admin/houqin/toAddPropertyMessage.json"  class="stdbtn ml10">添 加</a>
                    <a href="${ctx}/admin/houqin/toBatchImportPropertyMessage.json" class="stdbtn ml10">批量导入</a>
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
                    <col class="con0"/>
                    <col class="con1"/>
                    <col class="con0"/>
                    <col class="con1"/>
                    <col class="con0"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">序号</th>
                    <th class="head0 center">资产名称</th>
                    <th class="head0 center">库房</th>
                    <th class="head0 center">剩余数量</th>
                    <th class="head0 center">金额</th>
                    <th class="head0 center">管理员</th>
                    <th class="head0 center">购入时间</th>
                    <th class="head0 center">使用期限</th>
                    <th class="head0 center">状态</th>
                    <th class="head1 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${propertyMessageDtoList}" var="propertyMessage" varStatus="index">
                    <tr>
                        <td>${index.count}</td>
                        <td>${propertyMessage.name}</td>
                        <td>${propertyMessage.wareHouseName}</td>
                        <td>${propertyMessage.amount}</td>
                        <td>${propertyMessage.price}</td>
                        <td>${propertyMessage.sysUserName}</td>
                        <td><fmt:formatDate value="${propertyMessage.buyTime}" pattern="yyyy-MM-dd"/></td>
                        <td><fmt:formatDate value="${propertyMessage.liftTime}" pattern="yyyy-MM-dd"/></td>
                        <td>
                            <c:if test="${propertyMessage.status==0}">未使用</c:if>
                            <c:if test="${propertyMessage.status==1}"><font style="color: #00B83F">调拨中</font></c:if>
                            <c:if test="${propertyMessage.status==2}"><font style="color: red">借用中</font></c:if>
                            <c:if test="${propertyMessage.status==3}"><font style="color:blue">领用中</font></c:if>
                        </td>
                        <td class="center">
                            <a href="${ctx}/admin/houqin/toAgainPropertyMessage.json?id=${propertyMessage.id}" class="stdbtn" title="入库">入库</a>
                            <c:if test="${propertyMessage.status==0}">
                                <%--未使用时才可以出库--%>
                                <a href="${ctx}/admin/houqin/outOfPropertyMessage.json?id=${propertyMessage.id}" class="stdbtn" title="出库">出库</a>
                            </c:if>
                            <a href="${ctx}/admin/houqin/detailPropertyMessage.json?id=${propertyMessage.id}" class="stdbtn" title="查看详情">查看详情</a>
                            <a href="${ctx}/admin/houqin/toUpdatePropertyMessage.json?id=${propertyMessage.id}" class="stdbtn" title="编辑">编辑</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delPropertyMessage('${propertyMessage.id}')">删除</a>

                            <a href="${ctx}/admin/houqin/queryForTransferList.json?id=${propertyMessage.id}"
                               class="stdbtn" title="调拨走向">调拨走向</a>
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