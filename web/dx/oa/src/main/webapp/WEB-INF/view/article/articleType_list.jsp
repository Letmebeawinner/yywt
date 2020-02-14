<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>文章类型列表</title>
    <script type="text/javascript" src="${ctx}/static/js/schedule/telephone.js"></script>
    <script type="text/javascript">
        /**
         * 执行查询
         */
        function articleTypeForm() {
            jQuery("#articleTypeForm").submit();
        }

        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        function deleteJournalById(id) {
            if (confirm("删除后将无法恢复，是否继续")) {
                jQuery.ajax({
                    url: "${ctx}/admin/oa/deleteArticleType.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    async: false,
                    success: function (result) {
                        if (result.code == "0") {
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
        <h1 class="pagetitle">文章类型列表</h1>

    </div><!--pageheader-->
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于文章类型信息列表查看；<br>
        2.查询：输入查询条件，点击<span style="color:red">搜索</span>；<br>
        3.编辑：点击<span style="color:red">编辑</span>，修改用文章类型信息；<br>
        4.删除：点击<span style="color:red">删除</span>，删除文章类型信息；<br>
        5.是否完成：点击<span style="color:red">完成</span>，该文章类型就完成，反之点击未完成代表该文章类型未完成；<br>
        6.发送给：点击<span style="color:red">发送给</span>，弹出人员列表，选中对应的人即发送给对应的人，可以多选；<br>
    </div>

    <div id="contentwrapper" class="contentwrapper">

        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="articleTypeForm" action="${ctx}/admin/oa/queryAllArticleType.json" method="post">

                    <div class="disIb ml20 mb10">
                        <span class="vam">ID &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="articleType.id" type="text" class="hasDatepicker"
                                   value="${articleType.id}" placeholder="输入id" onkeyup="value=value.replace(/[^\d]/g,'')">
                        </label>
                    </div>

                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="articleTypeForm()">搜 索</a>
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
                    <th class="head0 center">名称</th>
                    <th class="head0 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${articleTypeList}" var="articleType">
                    <tr>
                        <td>${articleType.id}</td>
                        <td>${articleType.type_name}</td>
                        <td class="center">
                            <a href="${ctx}/admin/oa/toUpdateArticleType.json?id=${articleType.id}" class="stdbtn"
                               title="编辑">编辑</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="deleteJournalById(${articleType.id})">删除</a>
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