<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>通讯录列表</title>
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

        function deleteTelephone(id) {
            if (confirm("删除后将无法恢复，是否继续")) {
                jQuery.ajax({
                    url: "${ctx}/admin/oa/deleteTelephone.json",
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
        jQuery(function () {
            laydate.skin('molv');
            laydate({
                elem: '#beginTime',
                format: 'YYYY-MM-DD hh:mm:ss'
            });
        });
    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab">
        <h1 class="pagetitle">通讯录列表</h1>

    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">

        <div class="contenttitle2">
            <h3>通讯录列表</h3>
        </div><!--contenttitle-->

        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/oa/queryAllTelephone.json" method="post">

                    <div class="disIb ml20 mb10">
                        <span class="vam">ID &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="telephone.id" type="text" class="hasDatepicker" value="${telephone.id}" placeholder="输入id" onkeyup="value=value.replace(/[^\d]/g,'')">
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">姓名 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="telephone.name" type="text" class="hasDatepicker" value="${telephone.name}" placeholder="输入姓名">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchForm()">搜 索</a>
                    <a href="javascript: void(0)" class="stdbtn ml10" onclick="emptyForm()">重 置</a>
                    <a href="${ctx}/admin/oa/toAddTelephone.json" class="stdbtn ml10" onclick="emptyForm()">添加通讯录</a>
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
                    <th class="head0 center">姓名</th>
                    <th class="head0 center">性别</th>
                    <th class="head0 center">单位名称</th>
                    <th class="head0 center">单位职位</th>
                    <th class="head1">手机</th>
                    <th class="head1">邮箱</th>
                    <th class="head0 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${telephoneList}" var="telephone">
                    <tr>
                        <td>${telephone.id}</td>
                        <td>${telephone.name}</td>
                        <td>
                            <c:if test="${telephone.sex==0}">男</c:if>
                            <c:if test="${telephone.sex==1}">女</c:if>
                        </td>
                        <td>${telephone.unit}</td>
                        <td>${telephone.position}</td>
                        <td>${telephone.telephone}</td>
                        <td>${telephone.email}</td>
                        <td class="center">
                            <a href="${ctx}/admin/oa/toUpdateTelephone.json?id=${telephone.id}" class="stdbtn" title="编辑">编辑</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="deleteTelephone(${telephone.id})">删除</a>
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