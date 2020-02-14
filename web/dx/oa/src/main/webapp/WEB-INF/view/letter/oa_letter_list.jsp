<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>公文列表</title>
    <script type="text/javascript">
        /**
         * 执行查询
         */
        function searchForm() {
            jQuery("#formSubmit").submit();
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
        <h1 class="pagetitle">公文列表</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于查看公文列表；<br>
            2.编辑:点击<span style="color:red">编辑</span>，进入修改公文信息；<br>
        </div>
    </div>

    <div id="contentwrapper" class="contentwrapper">

        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="formSubmit" action="${ctx}/admin/oa/letter/list.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">公文名称 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="longinput" style="width: auto"  name="oaLetter.title" value="${oaLetter.title}">
                        </label>
                        <span class="vam">文号 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="longinput" style="width: auto"  name="oaLetter.letterNo" value="${oaLetter.letterNo}">
                        </label>
                        <span class="vam">申请人 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="longinput" style="width: auto"  name="applyName" value="${applyName}">
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
                    <th class="head0 center">序号</th>
                    <th class="head0 center">公文名称</th>
                    <th class="head0 center">文号</th>
                    <th class="head0 center">密级</th>
                    <th class="head0 center">紧急程度</th>
                    <th class="head0 center">申请人</th>
                    <th class="head0 center">申请时间</th>
                    <th class="head0 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${oaLetters}" var="letter" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${letter.title}</td>
                        <td>${letter.letterNo}</td>
                        <td>${letter.secretLevel}</td>
                        <td>${letter.urgentLevel}</td>
                        <td>${letter.applyName}</td>
                        <td><fmt:formatDate value="${letter.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- 分页，开始 -->
            <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
            <!-- 分页，结束 -->
        </div><!-- centercontent -->
    </div>
</div>
</body>
</html>