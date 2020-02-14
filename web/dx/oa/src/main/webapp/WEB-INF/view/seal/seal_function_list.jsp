<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>印章用途列表</title>
    <script type="text/javascript">
        /**
         * 执行查询
         */
        function searchForm() {
            jQuery("#sealFunctionForm").submit();
        }
        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        function delSealFunction(sealFunctionId) {
            if (confirm("删除后将无法恢复，是否继续")) {
                jQuery.ajax({
                    url: "${ctx}/admin/oa/deleteSealFunction.json?id="+sealFunctionId,
                    data: {},
                    type: "post",
                    dataType: "json",
                    async: false,
                    cache : false,
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

    <div class="pageheader notab">
        <h1 class="pagetitle">印章用途列表</h1>

    </div><!--pageheader-->
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于印章用途列表查看；<br>
        2.查询：输入查询条件，点击<span style="color:red">搜索</span>；<br>
        3.编辑：点击<span style="color:red">编辑</span>，修改用印章用途信息；<br>
        4.查看：点击<span style="color:red">查看</span>，查看用印章用途信息；<br>
        5.删除：点击<span style="color:red">删除</span>，删除印章用途信息；<br>
    </div>

    <div id="contentwrapper" class="contentwrapper">


        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="sealFunctionForm" action="${ctx}/admin/oa/queryAllSealFunction.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">ID &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="longinput" placeholder="输入印章ID" style="width: auto" name="sealFunction.id" value="${sealFunction.id}">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">印章用途名称 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="longinput" placeholder="输入印章用途名称" style="width: auto" name="sealFunction.name" value="${sealFunction.name}">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchForm()">搜 索</a>
                    <a href="javascript: void(0)" class="stdbtn ml10" onclick="emptyForm()">重 置</a>
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
                    <th class="head0 center">ID</th>
                    <th class="head0 center">印章用途名称</th>
                    <th class="head0 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${sealFunctions}" var="sealFunction">
                    <tr>
                        <td>${sealFunction.id}</td>
                        <td>${sealFunction.name}</td>
                        <td class="center">
                            <a href="${ctx}/admin/oa/toUpdateSealFunction.json?id=${sealFunction.id}&flag=0" class="stdbtn" title="编辑">编辑</a>
                            <a href="${ctx}/admin/oa/toUpdateSealFunction.json?id=${sealFunction.id}&flag=1" class="stdbtn" title="查看">查看</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delSealFunction(${sealFunction.id})">删除</a>
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