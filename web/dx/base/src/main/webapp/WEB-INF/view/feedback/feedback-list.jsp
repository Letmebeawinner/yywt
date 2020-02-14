<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>意见反馈列表</title>
	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader" style="margin-left: 30px">
                <h1 class="pagetitle">意见反馈列表</h1>
                 <span>
                    <span style="color:red">说明</span><br>
                    1. 本页面用于意见反馈列表；<br>
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">



                <!-- 数据显示列表，开始 -->
                <div class="pr">
                    <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                        <colgroup>
                            <col class="con0"/>
                            <col class="con1" />
                            <col class="con0"/>
                        </colgroup>
                        <thead>
                            <tr>
                                <th class="head0 center">ID</th>
                                <th class="head0 center">用户名称</th>
                                <th class="head1 center">反馈内容</th>
                                <th class="head1 center">反馈时间</th>
                                <th class="head0 center">联系方式</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${feedbackList}" var="feedback">
                            <tr>
                                <td>${feedback.id}</td>
                                <td>${feedback.sysUserName}</td>
                                <td>${feedback.context}</td>
                                <td><fmt:formatDate value="${feedback.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                <td>${feedback.connect}</td>
                            </tr>
                            </c:forEach>
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