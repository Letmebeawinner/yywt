<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<%-- <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>调研报告库</title>
    <script type="text/javascript">

        function searchForm() {
            jQuery("#searchForm").submit();
        }

        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        function trim(obj) {
            obj.value = jQuery.trim(obj.value)
        }

        function storage(rpId) {
            if (confirm("是否确认入库")) {
                jQuery.post("${ctx}/admin/ky/rr/doStorage.json", {"rpId": rpId}, function (result) {
                    alert(result.message)
                    if (result.code === "0") {
                        window.location.reload()
                    }
                }, "json")
            }
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">调研报告</h1>
        <span>
                    <span style="color:red">说明</span><br/>
					1.本页面展示调研报告.<br/>
					2.点击详情, 查看调研报告内容.<br/>
					3.点击归档, 归档调研报告.<br/>
					4.点击查看档案, 查看归入的档案.<br/>
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm"
                      action="${ctx}/admin/ky/rr/researchReportLibrary.json?resultType=${resultType}&isArchive=0"
                      method="get">

                    <input type="hidden" name="resultType" value="${resultType}">
                    <input type="hidden" name="isArchive" value="${isArchive}">
                    <div class="disIb ml20 mb10">
                        <span class="vam">填写人名称 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="name" type="text"
                                   value="${name}" placeholder="填写人名称" onblur="trim(this)">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                </div>
            </div>
        </div>
        <!-- 搜索条件，结束 -->

        <!-- 数据显示列表，开始 -->
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <colgroup>
                    <col class="con0"/>
                    <col class="con1"/>
                    <col class="con0"/>
                    <col class="con1"/>
                    <col class="con0"/>
                    <col class="con1"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">序号</th>
                    <th class="head0 center">调研报告类型</th>
                    <th class="head0 center" width="20%">班级</th>
                    <th class="head0 center" width="10%">联系方式</th>
                    <th class="head0 center">填写人</th>
                    <th class="head0 center">类型</th>
                    <th class="head0 center">状态</th>
                    <th class="head1 center">添加时间</th>
                    <th class="head1 center">更新时间</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${not empty rqList}">
                    <c:forEach items="${rqList}" var="rql" varStatus="status">
                        <tr>
                            <td>${status.count}</td>
                            <td>${rql.researchName}</td>
                            <td>${rql.classStr}</td>
                            <td>${rql.contact}</td>
                            <td>${rql.peopleName}</td>
                            <td>
                                <c:if test="${rql.type eq 'student'}">学员</c:if>
                                <c:if test="${rql.type eq 'teacher'}">老师</c:if>
                            </td>
                            <td>
                                <c:if test="${rql.archive == 0}">
                                    未归档
                                </c:if>
                                <c:if test="${rql.archive == 1}">
                                    已归档
                                </c:if>
                            </td>
                            <td>
                                <fmt:formatDate type="both" value="${rql.createTime}" pattern="yyyy-MM-dd"/>
                            </td>
                            <td>
                                <fmt:formatDate type="both" value="${rql.updateTime}" pattern="yyyy-MM-dd"/>
                            </td>
                            <td class="center">
                                    <a href="${ctx}/admin/ky/zz/researchReportFiled.json?id=${rql.id}" class="stdbtn"
                                       title="归档">归档</a>
                                <a href="${ctx}/admin/ky/rr/detailResearchReport.json?id=${rql.id}"
                                   class="stdbtn" title="详情">详情</a>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
            <!-- 分页，开始 -->
            <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
            <!-- 分页，结束 -->
        </div>
        <!-- 数据显示列表，结束 -->
    </div>
</div>
</body>
</html>