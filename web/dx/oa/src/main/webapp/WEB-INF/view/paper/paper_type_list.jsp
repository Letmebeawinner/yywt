<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>封条类型列表</title>
    <script type="text/javascript">
        /**
         * 执行查询
         */
        function searchForm() {
            jQuery("#paperTypeForm").submit();
        }
        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        function delPaperType(paperTypeId) {
            if (confirm("删除后将无法恢复，是否继续")) {
                jQuery.ajax({
                    url: "${ctx}/admin/oa/deletePaperType.json?id="+paperTypeId,
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
        <h1 class="pagetitle">封条类型列表</h1>
    </div><!--pageheader-->
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于封条类型信息列表查看；<br>
        2.查询：输入查询条件，点击<span style="color:red">搜索</span>；<br>
        3.编辑：点击<span style="color:red">编辑</span>，修改封条类型信息；<br>
        4.查看：点击<span style="color:red">查看</span>，查看封条类型信息；<br>
        5.删除：点击<span style="color:red">删除</span>，删除封条类型；<br>
    </div>

    <div id="contentwrapper" class="contentwrapper">

        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="paperTypeForm" action="${ctx}/admin/oa/queryAllPaperType.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">ID &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="longinput"  style="width: auto" placeholder="输入封条ID" name="paperType.id" value="${paperType.id}">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">封条类型名称 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="longinput" style="width: auto" placeholder="输入封条类型名称" name="paperType.name" value="${paperType.name}">
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
                    <th class="head0 center">封条类型名称</th>
                    <th class="head0 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${paperTypes}" var="paperType">
                    <tr>
                        <td>${paperType.id}</td>
                        <td>${paperType.name}</td>
                        <td class="center">
                            <a href="${ctx}/admin/oa/toUpdatePaperType.json?id=${paperType.id}&flag=0" class="stdbtn" title="编辑">编辑</a>
                            <a href="${ctx}/admin/oa/toUpdatePaperType.json?id=${paperType.id}&flag=1" class="stdbtn" title="查看">查看</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delPaperType(${paperType.id})">删除</a>
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