<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>教学评价列表</title>
<%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
        <script type="text/javascript">
        jQuery(function(){
			var teachingCommentId="${teachingCommentManagement.teachingCommentId}";
			jQuery("#teachingCommentId").val(teachingCommentId);
			var courseName="${teachingCommentManagement.courseName}";
			jQuery("#teachingCommentIdp").html(courseName);
        });
        function searchForm(){
            jQuery("#searchForm").submit();
        }
        
        function emptyForm(){
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }
        
        function selectTeachingComment(){
        	window.open('${ctx}/jiaowu/teachingComment/teachingCommentListForSelect.json','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=1200,height=800');
        }
        
		function addTeachingCommentType(teachingCommentIdAndCourseName){
        	jQuery("#teachingCommentId").val(teachingCommentIdAndCourseName[0]);
        	jQuery("#teachingCommentIdp").html(teachingCommentIdAndCourseName[1]);
        }
			
        </script>
	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">教学评价列表</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示教学评价的列表.<br />
					2.可通过教学工作评价类别查询对应的教学工作评价.<br />
					3.可点击"查看"按钮,查看某教学工作评价的具体内容.<br />
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">

                <!-- 搜索条件，开始 -->
                <div class="overviewhead clearfix mb10">
                    <div class="fl mt5">
                        <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/teachingComment/teachingCommentManagementList.json" method="get">
                        <input type="hidden" id="teachingCommentId" name="teachingCommentId" />
                            <div class="tableoptions disIb mb10">
                                <span class="vam">教学工作评价类别 &nbsp;</span>
                                <label class="vam">
                                <span id="teachingCommentIdp"></span>
                            	<a href="javascript:selectTeachingComment()" class="stdbtn btn_orange">选择教学工作评价类别</a>
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
                            <col class="con1" />
                            <col class="con0"/>
                            <col class="con1" />
                            <col class="con0"/>
                            <col class="con1" />
                        </colgroup>
                        <thead>
                            <tr>
                                <th class="head0 center">序号</th>
                                <th class="head0 center">课程名称</th>
                                <th class="head1 center">评价人</th>
                                <th class="head0 center">被评价人</th>                               
                                <th class="head0 center">类型</th>                               
                                <th class="head1 center">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<c:if test="${teachingCommentManagementList!=null&&teachingCommentManagementList.size()>0 }">
                            <c:forEach items="${teachingCommentManagementList}" var="teachingCommentManagement" varStatus="index">
                            <tr>
                                <td>${index.index+1}</td>
                                <td>${teachingCommentManagement.courseName}</td>
                                <td>${teachingCommentManagement.fromPeopleName}</td>
                                <td>${teachingCommentManagement.toPeopleName}</td>
                                <td>${teachingCommentManagement.type}</td>
                                
                                <td class="center">
                                	<c:if test="${teachingCommentManagement.status==1}">
                                		<a href="${ctx}/admin/jiaowu/teachingComment/queryTeachingCommentManagement.json?id=${teachingCommentManagement.id}" class="stdbtn" title="查看">查看</a>
                                	</c:if>
                                	<c:if test="${teachingCommentManagement.status==2}">
                                		<a href="" class="stdbtn" title="督促">督促</a>
                                	</c:if>
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