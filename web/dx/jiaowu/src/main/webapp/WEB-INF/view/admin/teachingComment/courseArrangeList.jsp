<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<%-- <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>班次已上课记录</title>
    <script type="text/javascript">
        function searchForm() {
            jQuery("#searchForm").submit();
        }
        function emptyForm(){
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">班次已上课列表</h1>
        <span>
                    <span style="color:red">说明</span><br/>
					1.本页面展示班次已上课列表.<br/>
					3.可点击"批准"按钮,批准该请假.<br/>
					4.可点击"不批准"按钮,不批准该请假.<br/>
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/teachingComment/courseArrangeList.json"
                  method="get">

                <div class="tableoptions disIb mb10">
                    <span class="vam">班次 &nbsp;</span>
                    <label class="vam">
                        <select name="classTypeId" class="vam" id="classTypeId">
                            <option value="0">请选择</option>
                            <c:forEach items="${classList}" var="classes">
                                <option value="${classes.id}"
                                        <c:if test="${classes.id==classesId}">selected</c:if>>${classes.name}</option>
                            </c:forEach>
                        </select>
                    </label>
                </div>
            </form>
            <div class="disIb ml20 mb10">
                <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
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
                    <th class="head0 center">课程</th>
                    <th class="head1 center">讲师</th>
                    <th class="head0 center">开始时间</th>
                    <th class="head0 center">结束时间</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${courseArrangeList!=null&&courseArrangeList.size()>0 }">
                    <c:forEach items="${courseArrangeList}" var="courseArrange">
                        <tr>
                            <td>${courseArrange.courseName}</td>
                            <td>${courseArrange.teacherName}</td>
                            <td><fmt:formatDate type="both" value="${courseArrange.startTime}"
                                                pattern="yyyy-MM-dd HH:mm"/></td>
                            <td><fmt:formatDate type="both" value="${courseArrange.endTime}"
                                                pattern="yyyy-MM-dd HH:mm"/></td>
                            <td class="center">
                                    <span id="operationspan${holiday.id}">
                                    <a href="${ctx}/admin/jiaowu/teachEvaluate/teachEvaluateListOfOneCourseArrange.json?courseArrangeId=${courseArrange.id}"
                                       class="stdbtn" title="查看教学质量评估结果">查看教学质量评估结果</a>
                                        <a href="${ctx}/admin/jiaowu/teachEvaluate/averageTeachEvaluateOfOneCourseArrange.json?courseArrangeId=${courseArrange.id}"
                                           class="stdbtn" title="查看任课讲师教学质量评估各项平均值">查看任课讲师教学质量评估各项平均值</a>
                                    </span>
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