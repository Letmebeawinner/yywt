<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>档案列表</title>
    <script type="text/javascript">
        /**
         * 执行查询
         */
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

    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab">
        <h1 class="pagetitle">查询档案记录</h1>

    </div><!--pageheader-->
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于查询档案记录；<br>
    </div>

    <div id="contentwrapper" class="contentwrapper">


        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/oa/searchArchiveList.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">调阅人名称 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="longinput" style="width: auto" placeholder="输入调阅人名称" name="archiveSearch.name">
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
                    <col class="con0"/>
                    <col class="con1"/>
                    <col class="con0"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">ID</th>
                    <td>调阅人姓名</td>
                    <td>调阅人所在部门</td>
                    <td>申请时间</td>
                    <td>主要目的和内容</td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${archiveSearchList}" var="archive">
                    <tr>
                        <td>${archive.id}</td>
                        <td>${archive.name}</td>
                        <td>${archive.departmentName}</td>
                        <td><fmt:formatDate value="${archive.applyDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>${archive.context}</td>
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