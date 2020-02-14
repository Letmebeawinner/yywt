<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>成果列表</title>
    <link href="/static/layui/lay/modules/theme/default/layer.css?v=3.1.0 ">
    <script src="/static/layui/lay/modules/layer.js"></script>
    <script type="text/javascript">
        jQuery(function () {
            if (jQuery("#resultForm").val() == 1 || jQuery("#resultForm").val() == 4) {
                jQuery("#resultForm1").show();
                jQuery("#resultForm2").hide();
                jQuery("#resultForm3").hide();
                jQuery("#resultName").html("论文名称");
                if (jQuery("#resultForm").val() == 1) {
                    jQuery("#publicationsThesisName").html("论文名称")
                }
                if (jQuery("#resultForm").val() == 4) {
                    jQuery("#publicationsThesisName").html("内刊名称")
                    jQuery("#resultName").html("内刊名称");
                }

            } else if (jQuery("#resultForm").val() == 2) {
                jQuery("#resultForm2").show();
                jQuery("#resultForm1").hide();
                jQuery("#resultForm3").hide();
                jQuery("#resultName").html("著作名称");
            } else {
                jQuery("#resultForm3").show();
                jQuery("#resultForm1").hide();
                jQuery("#resultForm2").hide();
                jQuery("#resultName").html("课题名称");
            }
        });

        /**
         * 执行查询
         */
        function searchForm() {
            jQuery("#getResultList").submit();
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

    <div class="pageheader notab" style="margin-left: 10px">
        <h1 class="pagetitle">成果库</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用与管理员编辑、删除入库成果信息，对成果进行归档；<br>
                2. 编辑成果信息：点击操作列中的<span style="color:red">编辑</span>按钮编辑成果信息；<br>
                3. 删除成果信息：点击操作列中的<span style="color:red">删除</span>按钮删除成果信息；<br>
                4. 成果归档：点击操作列中的<span style="color:red">归档</span>按钮将成果归档。
        </span>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="getResultList" action="${ctx}/admin/ky/getResultStorageList.json" method="post">
                    <input type="hidden" name="queryResult.resultType" value="${queryResult.resultType}">
                    <div class="disIb ml20 mb10">
                        <span class="vam" id="resultName">成果名称 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" placeholder="输入成果名称" name="queryResult.name"
                                   value="${queryResult.name}">
                        </label>
                    </div>
                    <div class="tableoptions disIb mb10">
                        <span class="vam">成果形式&nbsp;</span>
                        <label class="vam">
                            <select name="queryResult.resultForm" id="resultForm"
                                    onchange="searchForm()">
                                <c:forEach items="${resultFormList}" var="resultForm">
                                    <option <c:if test="${queryResult.resultForm==resultForm.id}"> selected</c:if>
                                            value="${resultForm.id}">${resultForm.name}</option>
                                </c:forEach>
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
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable" id="resultForm1">
                <thead>
                <tr>
                    <th class="head0 center" width="5%">序号</th>
                    <th class="head1" id="publicationsThesisName" width="30%">论文名称</th>
                    <th class="head1" width="22%">发表刊物</th>
                    <th class="head1" width="8%">刊号</th>
                    <th class="head1" width="5%">作者</th>
                    <th class="head1" width="5%">发表时间</th>
                    <td class="head0 center" width="25%">
                        操作
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${resultList}" var="result" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${result.name}</td>
                        <td>${result.publish}</td>
                        <td>${result.publishNumber}</td>
                        <td>${result.workName}</td>
                        <td><fmt:formatDate value="${result.publishTime}" pattern="yyyy-MM-dd"/></td>
                        <td class="center">
                            <c:if test="${result.ifFile == 0}">
                            <a href="javascript:void(0)" class="stdbtn" title="归档"
                               onclick="fileResult(${result.id})">归档</a>
                            </c:if>
                            <a href="${ctx}/admin/ky/toUpdateResult.json?id=${result.id}" class="stdbtn"
                               title="编辑">编辑</a>
                            <a href="${ctx}/admin/ky/getResultInfo.json?id=${result.id}" class="stdbtn"
                               title="查看">查看</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除"
                               onclick="delResult(${result.id})">删除</a>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable" id="resultForm2">
                <thead>
                <tr>
                    <th class="head0 center">序号</th>
                    <th class="head1" width="10%">著作名称</th>
                    <th class="head1" width="10%">出版社</th>
                    <th class="head1" width="10%">出版时间</th>
                    <th class="head1" width="10%">主编（字数）</th>
                    <th class="head1" width="10%">副主编（字数）</th>
                    <td class="head0 center">
                        操作
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${resultList}" var="result" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${result.name}</td>
                        <td>${result.publish}</td>
                        <td><fmt:formatDate value="${result.publishTime}" pattern="yyyy-MM-dd"/></td>
                        <td>${result.workName}（${result.wordsNumber}字）</td>
                        <td>${result.associateEditor}（${result.associateNumber}字）</td>
                        <td class="center">
                            <c:if test="${result.ifFile == 0}">
                            <a href="javascript:void(0)" class="stdbtn" title="归档"
                               onclick="fileResult(${result.id})">归档</a>
                            </c:if>
                            <a href="${ctx}/admin/ky/toUpdateResult.json?id=${result.id}" class="stdbtn"
                               title="编辑">编辑</a>
                            <a href="${ctx}/admin/ky/getResultInfo.json?id=${result.id}" class="stdbtn"
                               title="查看">查看</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除"
                               onclick="delResult(${result.id})">删除</a>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable" id="resultForm3">
                <thead>
                <tr>
                    <th class="head0 center">序号</th>
                    <th class="head1" width="10%">课题名称</th>
                    <th class="head1" width="10%">课题负责人</th>
                    <th class="head1" width="10%">结项单位</th>
                    <th class="head1" width="10%">字数</th>
                    <td class="head0 center">
                        操作
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${resultList}" var="result" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${result.name}</td>
                        <td>${result.workName}</td>
                        <td>${result.resultDepartment}</td>
                        <td>${result.wordsNumber}</td>
                        <td class="center">
                            <c:if test="${result.ifFile == 0}">
                            <a href="javascript:void(0)" class="stdbtn" title="归档"
                               onclick="fileResult(${result.id})">归档</a>
                            </c:if>
                            <a href="${ctx}/admin/ky/toUpdateResult.json?id=${result.id}" class="stdbtn"
                               title="编辑">编辑</a>
                            <a href="${ctx}/admin/ky/getResultInfo.json?id=${result.id}" class="stdbtn"
                               title="查看">查看</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除"
                               onclick="delResult(${result.id})">删除</a>
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
<script type="text/javascript" src="${ctx}/static/admin/js/result.js"></script>
</body>
</html>