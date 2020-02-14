<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<%-- <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>心得评价列表</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
    <script type="text/javascript">
        jQuery(function () {

        });

        function searchForm() {
            jQuery("#searchForm").submit();
        }

        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
//             jQuery("#title").val("");
        }

        function toSaveEvaluate(tipsId) {
            window.location.href = "/admin/jiaowu/xinDe/toSaveEvaluate.json?tipsId=" + tipsId;
        }

    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">心得评价列表</h1>
        <span>
                    <span style="color:red">说明</span><br/>
					1.本页面展示心得评价列表.<br/>
					2.可通过标题名称查询对应的心得评价列表.<br/>
					<%--3.可点击"查看"按钮,查看某教学质量评估.<br />--%>
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <%--<form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/teacher/teacherLibraryList.json"
                      method="get">
                    &lt;%&ndash; <input type="hidden" name="classId" id="classId" value="${teachEvaluate.classId}"/>
                     <input type="hidden" name="className" id="className" value="${className}"/>&ndash;%&gt;
                    <div class="disIb ml20 mb10">
                        <span class="vam">标题 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="projectName" type="text"
                                   value="${projectName}" placeholder="请输入标题名称">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                </div>--%>

                <%--<div class="disIb ml20 mb10">
                    &lt;%&ndash;<span class="vam">讲师 &nbsp;</span>&ndash;%&gt;
                    <label class="vam">
                        <span id="classspan">${className}</span>
                        <a href="javascript:void(0)" onclick="selectClass()" class="stdbtn btn_orange">选择讲师</a>
                    </label>
                </div>--%>

                <div class="disIb ml20 mb10">
                    <%--<span class="vam">讲师 &nbsp;</span>--%>
                    <label class="vam">
                        <a href="javascript:void(0)" onclick="toSaveEvaluate(${tipsId})"
                           class="stdbtn btn_orange">新建评价</a>
                    </label>
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
                    <th class="head0 center">评价标题</th>
                    <th class="head0 center">学员名称</th>
                    <th class="head0 center">评价人</th>
                    <th class="head0 center">创建时间</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${evaluates!=null&&evaluates.size()>0 }">
                    <c:forEach items="${evaluates}" var="eva" varStatus="status">
                        <tr>
                            <td>${status.count}</td>
                            <td>${eva.title}</td>
                            <td>${eva.studentName}</td>
                            <td>${eva.evaluatorName}</td>
                            <td><fmt:formatDate type="both" value="${eva.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                            <td class="center">
                                <a href="${ctx}/admin/jiaowu/xinDe/descEvaluate.json?id=${eva.id}"
                                   class="stdbtn" title="详情">详情</a>
                                    <%--<a href="javascript:void(0)" onclick="delTeacherLibrary('${tl.id}')"
                                       class="stdbtn" title="删除">删除</a>--%>
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
</body>
</html>