<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>规章制度列表</title>
    <script type="text/javascript" src="${ctx}/static/common/file-download.js"></script>
    <script type="text/javascript">

        jQuery(function(){
            laydate.skin('molv');
            laydate({
                elem: '#startFromDate',
                format:'YYYY-MM-DD hh:mm:ss',
                istime: true
            });
            laydate({
                elem: '#startToDate',
                format:'YYYY-MM-DD hh:mm:ss',
                istime: true
            });
        });

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
            jQuery("select").val("");
        }

        function deleteFile(id) {
            if (confirm("删除后将无法恢复，是否继续")) {
                jQuery.ajax({
                    url: "${ctx}/admin/oa/deleteRule.json",
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
        <h1 class="pagetitle">制度列表</h1>

        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于文件信息列表查看；<br>
            2.查询：输入查询条件，点击<span style="color:red">搜索</span>；<br>
            3.编辑：点击<span style="color:red">编辑</span>，修改制度信息；<br>
            4.删除：点击<span style="color:red">删除</span>，删除制度；<br>
            5.下载：点击<span style="color:red">下载</span>，下载制度；<br>
        </div>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">

        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/oa/queryAllRule.json" method="post">

                    <%--<div class="disIb ml20 mb10">
                        <span class="vam">ID &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="rule.id" type="text" class="hasDatepicker" value="${rule.id}" placeholder="输入id" onkeyup="value=value.replace(/[^\d]/g,'')">
                        </label>
                    </div>--%>

                    <div class="disIb ml20 mb10">
                        <span class="vam">名称 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="queryRule.name" type="text" class="hasDatepicker" value="${queryRule.name}" placeholder="输入名称">
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">类型名称 &nbsp;</span>
                        <label class="vam">
                          <select name="queryRule.typeId">
                              <option value="">--请选择--</option>
                              <c:forEach items="${ruleTypeList}" var="ruleType">
                                  <option value="${ruleType.id}" <c:if test="${queryRule.typeId==ruleType.id}">selected="selected"</c:if>>${ruleType.name}</option>
                              </c:forEach>
                          </select>
                        </label>
                    </div>
                        <div class="disIb ml20 mb10">
                            <span class="vam">创建日期 &nbsp;</span>
                            <label class="vam">

                                <input style="width: auto;" name="queryRule.startFromDate" id = "startFromDate" type="text" class="hasDatepicker" value="<fmt:formatDate value="${queryRule.startFromDate}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>">
                            </label>
                            <span class="vam">- &nbsp;</span>
                            <label class="vam">
                                <input style="width: auto;" name="queryRule.startToDate" id = "startToDate" type="text" class="hasDatepicker" value="<fmt:formatDate value="${queryRule.startToDate}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>">
                            </label>
                        </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchForm()">搜 索</a>
                    <a href="javascript: void(0)" class="stdbtn ml10" onclick="emptyForm()">重 置</a>
                    <a href="${ctx}/admin/oa/toAddRule.json" class="stdbtn ml10">添 加</a>
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
                    <th class="head0 center">名称</th>
                    <th class="head0 center">类型名称</th>
                    <th class="head0 center">来源</th>
                    <th class="head0 center">作者</th>
                    <th class="head0 center">浏览量</th>
                    <th class="head0 center">创建日期</th>
                    <th class="head0 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${RuleList}" var="rule" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${rule.name}</td>
                        <td>${rule.typeName}</td>
                        <td>${rule.source}</td>
                        <td>${rule.author}</td>
                        <td>${rule.views}</td>
                        <td>
                            <fmt:formatDate value="${rule.createTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                        </td>
                        <td class="center">
                            <a href="${ctx}/admin/oa/toUpdateRule.json?id=${rule.id}" class="stdbtn" title="编辑">编辑</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="deleteFile(${rule.id})">删除</a>
                            <a href="${rule.fileUrl}" class="stdbtn" title="下载">下载</a>
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