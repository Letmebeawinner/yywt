<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>封条列表</title>
    <script type="text/javascript">
        /**
         * 执行查询
         */
        function searchForm() {
            jQuery("#paperForm").submit();
        }
        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        function delPaper(paperId) {
            if (confirm("删除后将无法恢复，是否继续")) {
                jQuery.ajax({
                    url: "${ctx}/admin/oa/deletePaper.json",
                    data: {
                        "id" : paperId
                    },
                    type: "POST",
                    dataType: "JSON",
                    async : false,
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
        <h1 class="pagetitle">封条列表</h1>

    </div><!--pageheader-->
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于封条信息列表查看；<br>
        2.查询：输入查询条件，点击<span style="color:red">搜索</span>；<br>
        3.编辑：点击<span style="color:red">编辑</span>，修改封条信息；<br>
        4.查看：点击<span style="color:red">查看</span>，查看封条信息；<br>
        5.删除：点击<span style="color:red">删除</span>，删除封条；<br>
    </div>

    <div id="contentwrapper" class="contentwrapper">

        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="paperForm" action="${ctx}/admin/oa/queryAllPaper.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">ID &nbsp;</span>
                        <label class="vam">
                            <input type="text" style="width: auto;" class="longinput" placeholder="输入封条ID" name="paper.id" value="${paper.id}">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">封条名称 &nbsp;</span>
                        <label class="vam">
                            <input type="text" style="width: auto;" class="longinput" placeholder="输入封条名称" name="paper.name" value="${paper.name}">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">封条类别 &nbsp;</span>
                        <label class="vam">
                            <select name = "paper.paperTypeId">
                                <option value = "">未选择</option>
                                <c:forEach items = "${paperTypes}" var = "paperType">
                                    <option value = "${paperType.id}" <c:if test = "${paper.paperTypeId == paperType.id}">selected = "selected"</c:if>>${paperType.name}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">封条用途 &nbsp;</span>
                        <label class="vam">
                            <select name = "paper.paperFunctionId">
                                <option value = "">未选择</option>
                                <c:forEach items = "${paperFunctions}" var = "paperFunction">
                                    <option value = "${paperFunction.id}" <c:if test = "${paper.paperFunctionId == paperFunction.id}">selected = "selected"</c:if>>${paperFunction.name}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">版本号 &nbsp;</span>
                        <label class="vam">
                            <input type="text" style="width: auto;" class="longinput" placeholder="备注" name="paper.version" value="${paper.version}">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">描述 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="longinput" style="width: auto;" placeholder="备注" name="paper.description" value="${paper.description}">
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
                    <th class="head0 center">封条名称</th>
                    <th class="head0">封条类别</th>
                    <th class="head0">封条用途</th>
                    <th class="head0">发行部门</th>
                    <th class="head0">版本号</th>
                    <th class="head0">描述</th>
                    <th class="head0 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${paperDtos}" var="paperDto">
                    <tr>
                        <td>${paperDto.id}</td>
                        <td>${paperDto.name}</td>
                        <td>${paperDto.paperTypeName}</td>
                        <td>${paperDto.paperFunctionName}</td>
                        <td>${paperDto.departmentName}</td>
                        <td>${paperDto.version}</td>
                        <td>${paperDto.description}</td>
                        <td class="center">
                            <a href="${ctx}/admin/oa/toUpdatePaper.json?id=${paperDto.id}&flag=0" class="stdbtn" title="编辑">编辑</a>
                            <a href="${ctx}/admin/oa/toUpdatePaper.json?id=${paperDto.id}&flag=1" class="stdbtn" title="查看">查看</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delPaper(${paperDto.id})">删除</a>
                            <a href="${ctx}/admin/oa/downloadFile.json?fileUrl=${paperDto.fileUrl}" class="stdbtn" title="下载">下载</a>
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