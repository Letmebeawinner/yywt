<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>${retirement.outType==1 ? "离退休" : "转出"}信息列表</title>
    <script type="text/javascript">
        /**
         * 执行查询
         */
        function searchForm() {
            jQuery("#getRetirementList").submit();
        }
        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }
        /**
         * 删除离退休记录
         */
        function delRetirement(retirementId) {
            if (confirm("删除后将无法恢复，是否继续？")) {
                jQuery.ajax({
                    url: "${ctx}/admin/rs/deleteRetirement.json?id="+retirementId,
                    data: {},
                    type: "post",
                    dataType: "json",
                    async: false,
                    success: function (result) {
                        if (result.code=="0") {
                            window.location.reload();
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

    <div class="pageheader notab" style="margin-left: 10px">
        <h1 class="pagetitle">${retirement.outType==1 ? "离退休" : "转出"}信息列表</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来新建、编辑、删除${retirement.outType==1 ? "离退休" : "转出"}<br>
                2. 新建${retirement.outType==1 ? "离退休" : "转出"}：点击搜索框中最后的<span style="color:red">申请</span>按钮进行新建；<br>
                3. 编辑${retirement.outType==1 ? "离退休" : "转出"}：点击操作列中的<span style="color:red">编辑</span>按钮编辑离退休；<br>
                4. 删除${retirement.outType==1 ? "离退休" : "转出"}：点击操作列中的<span style="color:red">删除</span>按钮删除离退休。
        </span>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="getRetirementList" action="${ctx}/admin/rs/getRetirementList.json?retirement.outType=${retirement.outType}" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">教职工姓名 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" placeholder="输入教职工姓名" name="retirement.name" value="${retirement.name}">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchForm()">搜 索</a>
                    <a href="javascript: void(0)" class="stdbtn ml10" onclick="emptyForm()">重 置</a>
                    <a href="${ctx}/admin/rs/toAddRetirement.json?outType=${retirement.outType}" class="stdbtn ml10">新 建</a>
                    <%--<a href="${ctx}/admin/rs/toImportRetirement.json" class="stdbtn ml10">批量导入</a>--%>
                </div>
            </div>
        </div>

        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <colgroup>
                    <col class="con0" style="width:10%;"/>
                    <col class="con1"/>
                    <col class="con0" style="width:20%;"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">序号</th>
                    <th class="head0 center">姓名</th>
                    <th class="head0 center">出生年月</th>
                    <th class="head0 center">民族</th>
                    <th class="head0 center">参加工作时间</th>
                    <th class="head0 center">学历</th>
                    <th class="head0 center">离退时间</th>
                    <th class="head1">类别</th>
                    <th class="head1">离职前职务</th>
                    <th class="head1">${retirement.outType==1 ? "享受待遇" : "转出原因"}</th>
                    <td class="head0 center">
                        操作
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${retirementList}" var="retirement" varStatus="index">
                    <tr>
                        <td>${retirement.id}</td>
                        <td>${retirement.name}</td>
                        <td><fmt:formatDate value="${retirement.birthday}" pattern="yyyy.MM.dd"/></td>
                        <td>${retirement.nationality}</td>
                        <td><fmt:formatDate value="${retirement.workTime}" pattern="yyyy.MM"/></td>
                        <td>${retirement.education}</td>
                        <td><fmt:formatDate value="${retirement.applyTime}" pattern="yyyy.MM"/> </td>
                        <td>${retirement.category}</td>
                        <td>${retirement.presentPost}</td>
                        <td>${retirement.treatment}</td>
                        <td class="center">
                            <a href="${ctx}/admin/rs/toUpdateRetirement.json?id=${retirement.id}" class="stdbtn" title="编辑">编辑</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delRetirement(${retirement.id})">删除</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- 分页，开始 -->
            <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
            <!-- 分页，结束 -->
        </div>
    </div>
</div>
</body>
</html>