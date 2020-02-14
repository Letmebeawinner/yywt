<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>讲师教学评价类别列表</title>
<%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
        <script type="text/javascript">
        jQuery(function(){
        });
        function searchForm(){
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
                <h1 class="pagetitle">讲师教学评价类别列表</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示讲师教学评价类别列表.<br />
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">

                <!-- 搜索条件，开始 -->
               <%--  <div class="overviewhead clearfix mb10">
                    <div class="fl mt5">
                        <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/class/classList.json" method="get">
                        </form>
                        <div class="disIb ml20 mb10">
                            <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                            <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                        </div>
                    </div>
                </div> --%>
                <!-- 搜索条件，结束 -->

                <!-- 数据显示列表，开始 -->
                <div class="pr">
                    <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                        <colgroup>
                            <col class="con0"/>
                            <col class="con1" />
                            <col class="con0"/>
                            <col class="con1" />
                            <col class="con0"/>
                            <col class="con1" />
                        </colgroup>
                        <thead>
                            <tr>
                                <th class="head0 center">序号</th>
                                <th class="head0 center">课程</th>
                                <th class="head1 center">班次</th>
                                <th class="head1 center">评价开始时间</th>
                                <th class="head0 center">评价结束时间</th>
                                <th class="head0 center">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<c:if test="${teachingCommentList!=null&&teachingCommentList.size()>0 }">
                            <c:forEach items="${teachingCommentList}" var="teachingComment" varStatus="index">
                            <tr>
                                <td>${index.index+1}</td>
                                <td>${teachingComment.courseName}</td>
                                <td>${teachingComment.className}</td>
                                <td><fmt:formatDate type="both" value="${teachingComment.startTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td><fmt:formatDate type="both" value="${teachingComment.endTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td>
                                	<a href="${ctx}/admin/jiaowu/teachingComment/teachingCommentManagementListOfOneTeachingComment.json?teachingCommentId=${teachingComment.id}" class="stdbtn" title="查看评价情况">查看评价情况</a>
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