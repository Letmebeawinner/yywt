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
            jQuery("#paperFunctionForm").submit();
        }
        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        function delLetter(id) {
            if (confirm("删除后将无法恢复，是否继续")) {
                jQuery.ajax({
                    url: "${ctx}/admin/oa/deleteLetter.json?id="+id,
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
                <form class="disIb" id="paperFunctionForm" action="${ctx}/admin/oa/queryAllLetter.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">公文名称 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="longinput" style="width: auto"  name="letter.title" value="${letter.title}">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">公文类型 &nbsp;</span>
                        <label class="vam">
                           <select name="letter.typeId">
                               <option value="">--请选择类型--</option>
                               <option value="1"  <c:if test="${letter.typeId==1}">selected</c:if>>中共贵阳市委党校文件</option>
                               <option value="2"  <c:if test="${letter.typeId==2}">selected</c:if>>贵阳社会主义学院</option>
                               <option value="3"  <c:if test="${letter.typeId==3}">selected</c:if>>校委会议纪要</option>
                               <option value="4"  <c:if test="${letter.typeId==4}">selected</c:if>>工作简报</option>
                           </select>
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
                    <th class="head0 center">公文类型</th>
                    <th class="head0 center">添加时间</th>
                    <th class="head0 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${letterList}" var="letter" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${letter.title}</td>
                        <td>
                            <c:if test="${letter.templateTypeId==1}">中共贵阳市委党校文件</c:if>
                            <c:if test="${letter.templateTypeId==2}">贵阳社会主义学院</c:if>
                            <c:if test="${letter.templateTypeId==3}">校委会议纪要</c:if>
                            <c:if test="${letter.templateTypeId==4}">工作简报</c:if>
                        </td>
                        <td><fmt:formatDate value="${letter.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td class="center">
                            <a href="${ctx}/admin/oa/toUpdateLetter.json?id=${letter.id}" class="stdbtn" title="编辑">编辑</a>
                            <%--<a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delLetter(${letter.id})">删除</a>--%>
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