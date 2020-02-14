<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>成果列表</title>
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

            jQuery("#myDate").val('${queryYear}')
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
        function userExcel() {
            var resultList = '${resultList}';
            if (resultList != null) {
                jQuery("#getResultList").prop("action", "${ctx}/admin/ky/getResultFileList/export.json");
                jQuery("#getResultList").submit();
                jQuery("#getResultList").prop("action", "${ctx}/admin/ky/getResultFileList.json");
            } else {
                alert("当前数据为空");
            }
        }

    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab" style="margin-left: 10px">
        <h1 class="pagetitle">成果档案列表</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来展示所有归档成果信息<br/>
                2.点击成果详情, 查看成果内容.<br/>
                3.点击查看档案, 查看归入的档案.<br/>
        </span>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="getResultList" action="${ctx}/admin/ky/getResultFileList.json" method="post">
                    <input type="hidden" name="queryResult.resultType" value="${queryResult.resultType}">
                    <div class="disIb ml20 mb10">
                        <span class="vam" id="resultName">成果名称 &nbsp;</span>
                        <label class="vam">
                            <input type="text" class="hasDatepicker" placeholder="输入成果名称" name="queryResult.name"
                                   value="${queryResult.name}">
                        </label>
                    </div>
                    <div class="tableoptions disIb mb10 ml20">
                        <span class="vam" id="resultTime">入库年份 &nbsp;</span>
                        <label class="vam">
                            <select id="myDate" name="queryYear" >
                                <option value="">---请选择年份---</option>
                                <c:forEach begin="${beginYear}" end="${endYear}" var="year">
                                    <option value="${year}">${year}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                    <div class="tableoptions disIb mb10 ml20">
                        <span class="vam">成果形式&nbsp;</span>
                        <label class="vam">
                            <select name="queryResult.resultForm" style="" id="resultForm"
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
                    <th class="head1" width="5%">序号</th>
                    <th class="head1" width="10%">作者</th>
                    <th class="head1" id="publicationsThesisName" width="30%">论文名称</th>
                    <th class="head1" width="10%">发表刊物</th>
                    <th class="head1" width="10%">刊号</th>
                    <th class="head1" width="10%">发表时间</th>
                    <th class="head1" width="10%">入库时间</th>
                    <td class="head0 center">
                        操作
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${resultList}" var="result" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${result.workName}</td>
                        <td>${result.name}</td>
                        <td>${result.publish}</td>
                        <td>${result.publishNumber}</td>
                        <td><fmt:formatDate value="${result.publishTime}" pattern="yyyy-MM-dd"/></td>
                        <td><fmt:formatDate value="${result.storageTime}" pattern="yyyy-MM-dd"/></td>
                        <td class="center">
                            <a href="${ctx}/admin/ky/rr/detailArchive.json?oaArchiveId=${result.oaArchiveId}"
                               class="stdbtn"
                               title="查看档案">查看档案</a>
                            <a href="${ctx}/admin/ky/mergeResult.json?id=${result.id}" class="stdbtn" title="编辑">编辑</a>
                            <a href="${ctx}/admin/ky/getResultInfo.json?id=${result.id}" class="stdbtn"
                               title="成果详情">成果详情</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable" id="resultForm2">
                <thead>
                <tr>
                    <th class="head1" width="5%">序号</th>
                    <th class="head1" width="20%">著作名称</th>
                    <th class="head1" width="10%">出版社</th>
                    <th class="head1" width="10%">出版时间</th>
                    <th class="head1" width="10%">入库时间</th>
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
                        <td><fmt:formatDate value="${result.storageTime}" pattern="yyyy-MM-dd"/></td>
                        <td>${result.workName}（${result.wordsNumber}字）</td>
                        <td>${result.associateEditor}（${result.associateNumber}字）</td>
                        <td class="center">
                            <a href="${ctx}/admin/ky/rr/detailArchive.json?oaArchiveId=${result.oaArchiveId}"
                               class="stdbtn"
                               title="查看档案">查看档案</a>
                            <a href="${ctx}/admin/ky/mergeResult.json?id=${result.id}" class="stdbtn" title="编辑">编辑</a>
                            <a href="${ctx}/admin/ky/getResultInfo.json?id=${result.id}" class="stdbtn"
                               title="成果详情">成果详情</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable" id="resultForm3">
                <thead>
                <tr>
                    <th class="head1" width="5%">序号</th>
                    <th class="head1" width="20%">课题名称</th>
                    <th class="head1" width="10%">课题负责人</th>
                    <th class="head1" width="10%">字数</th>
                    <th class="head1" width="10%">入库时间</th>
                    <th class="head1" width="10%">开始时间</th>
                    <th class="head1" width="10%">结束时间</th>
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
                        <td>${result.wordsNumber}</td>
                        <td><fmt:formatDate value="${result.storageTime}" pattern="yyyy-MM-dd"/></td>
                        <td><fmt:formatDate value="${result.addTime}" pattern="yyyy-MM-dd"/></td>
                        <td><fmt:formatDate value="${result.endTime}" pattern="yyyy-MM-dd"/></td>
                        <td class="center">
                            <a href="${ctx}/admin/ky/rr/detailArchive.json?oaArchiveId=${result.oaArchiveId}"
                               class="stdbtn"
                               title="查看档案">查看档案</a>
                            <a href="${ctx}/admin/ky/mergeResult.json?id=${result.id}" class="stdbtn" title="编辑">编辑</a>
                            <a href="${ctx}/admin/ky/getResultInfo.json?id=${result.id}" class="stdbtn"
                               title="成果详情">成果详情</a></td>
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