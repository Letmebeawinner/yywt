<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>奖惩记录列表</title>
    <script type="text/javascript">
        /**
         * 执行查询
         */
        function searchForm() {
            jQuery("#getInstitutionList").submit();
        }
        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        function delInstitution(institutionId) {
            if (confirm("删除后将无法恢复，是否继续")) {
                jQuery.ajax({
                    url: "${ctx}/admin/rs/deleteInstitution.json?id="+institutionId,
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
        
        
        function exportExcel() {
            jQuery("#getInstitutionList").prop("action", "${ctx}/admin/rs/exportInstitutionExcel.json");
            jQuery("#getInstitutionList").submit();
            jQuery("#getInstitutionList").prop("action", "${ctx}/admin/rs/getInstitutionList.json");
        }
    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab" style="margin-left: 10px">
        <h1 class="pagetitle">奖惩记录列表</h1>
         <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来新建、编辑、删除奖惩记录<br>
                2. 新建奖惩记录：点击搜索框中最后的<span style="color:red">新建</span>按钮新建记录；<br>
                3. 编辑奖惩记录：点击操作列中的<span style="color:red">编辑</span>按钮编辑奖惩记录；<br>
                4. 删除奖惩记录：点击操作列中的<span style="color:red">删除</span>按钮删除奖惩记录。
        </span>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="getInstitutionList" action="${ctx}/admin/rs/getInstitutionList.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">获奖名称 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" placeholder="输入获奖名称" name="institution.title" value="${institution.title}">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchForm()">搜 索</a>
                    <a href="javascript: void(0)" class="stdbtn ml10" onclick="emptyForm()">重 置</a>
                    <a href="${ctx}/admin/rs/toAddInstitution.json" class="stdbtn ml10">新 建</a>
                    <a href="javascript:void(0)" onclick="exportExcel()" class="stdbtn ml10">导出Excel</a>
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
                    <th class="head0 center">id</th>
                    <th class="head0 center">教职工姓名</th>
                    <th class="head1">获奖名称</th>
                    <th class="head1">颁奖单位</th>
                    <th class="head1">是否发放证书</th>
                    <th class="head1">证书时间</th>
                    <td class="head0 center">
                        操作
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${institutionList}" var="institution" varStatus="index">
                    <tr>
                        <td>${index.count}</td>
                        <td>${institution.employeeName}</td>
                        <td>${institution.title}</td>
                        <td>${institution.unit}</td>
                        <td>
                            <c:if test="${institution.isCertificate==0}">是</c:if>
                            <c:if test="${institution.isCertificate==1}">否</c:if>
                        </td>
                        <td>
                            <c:if test="${institution.isCertificate==0}">
                                <fmt:formatDate value="${institution.certificateTime}" type="date" dateStyle="long"/>
                            </c:if>
                            <c:if test="${institution.isCertificate==1}">
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -&nbsp;&nbsp;-
                            </c:if>
                        </td>
                        <td class="center">
                            <a href="${ctx}/admin/rs/toUpdateInstitution.json?id=${institution.id}" class="stdbtn" title="编辑">编辑</a>
                            <a href="${ctx}/admin/rs/getInstitutionInfo.json?id=${institution.id}" class="stdbtn" title="查看">查看</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delInstitution(${institution.id})">删除</a>
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