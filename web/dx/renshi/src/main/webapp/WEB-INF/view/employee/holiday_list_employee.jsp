<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<%-- <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>请假列表</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">请假列表</h1>
        <span>
                    <span style="color:red">说明</span><br/>
					1.本页面展示请假的列表.<br/>
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <%--<div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/holiday/holidayList.json" method="get">
                    <input type="hidden" name="classId" id="classId" value="${holiday.classId}"/>
                    <input type="hidden" name="className" id="className" value="${className}"/>
                    &lt;%&ndash;<div class="disIb ml20 mb10">
                        <span class="vam">创建者 &nbsp;</span>
                        <label class="vam">
                            <input id="createUserName" style="width: auto;" name="createUserName" type="text" class="hasDatepicker" value="" placeholder="请输入发起人">
                        </label>
                    </div>&ndash;%&gt;

                    <div class="disIb ml20 mb10">
                        <span class="vam">用户名 &nbsp;</span>
                        <label class="vam">
                            <input id="userName" style="width: auto;" name="userName" type="text" class="hasDatepicker" value="" placeholder="请输入用户名">
                        </label>
                    </div>


                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                </div>
            </div>
        </div>--%>
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
                    <th class="head0 center">用户类型</th>
                    <th class="head0 center">用户</th>
                    <th class="head1 center">开始时间</th>
                    <th class="head0 center">结束时间</th>
                    <th class="head0 center">请假时长</th>
                    <th class="head1 center">原因</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${holidayList!=null&&holidayList.size()>0 }">
                    <c:forEach items="${holidayList}" var="holiday" varStatus="index">
                        <tr>
                            <td>${index.index+1}</td>
                            <td>
                                <c:if test="${holiday.type==2}">
                                    教师
                                </c:if>
                                <c:if test="${holiday.type==3}">
                                    学员
                                </c:if>
                            </td>
                            <td>${holiday.userName}</td>
                            <td><fmt:formatDate type="both" value="${holiday.beginTime}"
                                                pattern="yyyy-MM-dd HH:mm"/></td>
                            <td><fmt:formatDate type="both" value="${holiday.endTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                            <td>${holiday.length}</td>
                            <td>${holiday.reason}</td>
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