<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<%-- <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>调研报告档案</title>
    <script type="text/javascript">
        jQuery(function () {
            jQuery("select[name='reportFiled.reportClassify']").val('${reportFiled.reportClassify}')
            jQuery("select[name='reportFiled.classification']").val('${reportFiled.classification}')
        });

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
        <h1 class="pagetitle">调研报告档案</h1>
        <span>
                    <span style="color:red">说明</span><br/>
					1.本页面展示调研报告库.<br/>
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
                      action="${ctx}/admin/ky/zz/reportFile.json"
                      method="get">
                    <div class="disIb ml20 mb10">
                        <span class="vam">填写人名称 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="reportFiled.peopleName" type="text"
                                   value="${reportFiled.peopleName}" placeholder="填写人名称" onblur="trim(this)">
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">档案分类 &nbsp;</span>
                        <label class="vam">
                            <select name="reportFiled.classification">
                                <option value="">未选择</option>
                                <option value="1">课题成果</option>
                                <option value="2">领导批示成果</option>
                            </select>
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">调研报告分类 &nbsp;</span>
                        <label class="vam">
                            <select name="reportFiled.reportClassify">
                                <option value="">未选择</option>
                                <option value="1">专题调研报告</option>
                                <option value="2">贵阳决策咨询报告(已批示)</option>
                                <option value="3">贵阳决策咨询报告(未批示)</option>
                                <option value="4">学院调研报告</option>
                                <option value="5">领导调研报告</option>
                            </select>
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
                    <th class="head0 center">联系方式</th>
                    <th class="head0 center" width="7%">填写人</th>
                    <th class="head0 center" width="5%">类型</th>
                    <th class="head0 center">档案分类</th>
                    <th class="head0 center">调研报告分类</th>
                    <th class="head1 center">归档时间</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${not empty reportFiledList}">
                    <c:forEach items="${reportFiledList}" var="rql" varStatus="status">
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
                                <c:if test="${rql.classification ==1}">课题成果</c:if>
                                <c:if test="${rql.classification ==2}">领导批示成果</c:if>
                            </td>
                            <td>
                                <c:if test="${rql.reportClassify ==1}">专题调研报告</c:if>
                                <c:if test="${rql.reportClassify ==2}">贵阳决策咨询报告(已批示)</c:if>
                                <c:if test="${rql.reportClassify ==3}">贵阳决策咨询报告(未批示)</c:if>
                                <c:if test="${rql.reportClassify ==4}">学院调研报告</c:if>
                                <c:if test="${rql.reportClassify ==5}">领导调研报告</c:if>
                            </td>
                            <td>
                                <fmt:formatDate type="both" value="${rql.createTime}" pattern="yyyy-MM-dd"/>
                            </td>
                            <td class="center">
                                <a href="${ctx}/admin/ky/zz/detailFile.json?id=${rql.id}"
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