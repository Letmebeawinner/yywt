<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>教学工作评价类别列表</title>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
<%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
        <script type="text/javascript">
        jQuery(function(){
        	laydate.skin('molv');
            laydate({
                elem: '#startTime',
                format:'YYYY-MM-DD hh:mm:ss'
            });
            laydate({
                elem: '#endTime',
                format:'YYYY-MM-DD hh:mm:ss'
            });
            
			 var courseId= "${teachingComment.courseId}";
			jQuery("#courseId").val(courseId);
			var courseName="${teachingComment.courseName}";
			jQuery("#courseName").val(courseName);
			jQuery("#coursespan").html(courseName);
			/* var startTime= "${teachingComment.startTime}";
			jQuery("#startTime").val(startTime);
            var endTime="${teachingComment.endTime}";
            jQuery("#endTime").val(endTime); */
        });
        function searchForm(){
            jQuery("#searchForm").submit();
        }
        
        function emptyForm(){
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }
			function deleteTeachingComment(id){
				if(confirm("您确定要删除吗?")){
				 jQuery.ajax({
		    		url:'${ctx}/admin/jiaowu/teachingComment/delTeachingComment.json?id='+id,
		    		type:'post',
		    		dataType:'json',		    		
		    		success:function (result){
		    			if(result.code=="0"){
		    				window.location.reload();
		    			}else{
		    				jAlert(result.message,'提示',function() {});
		    			}		    			
		    		},
		    		error:function(e){
		    			jAlert('删除失败','提示',function() {});
		    		}
		    	});
				}
			}
			
			function selectCourse(){
	            window.open('${ctx}/jiaowu/course/courseListForSelect.json','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=1200,height=800');
	        }

	        function addCourse(courseArray){
	            jQuery("#coursespan").html(courseArray[1]);
	            jQuery("#courseId").val(courseArray[0]);
	            jQuery("#courseName").val(courseArray[1]);
	        }
        </script>
	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">教学工作评价类别列表</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示教学工作评价类别的列表.<br />
					2.可通过课程、开始时间和结束时间查询对应的教学工作评价类别.<br />
					3.可点击"教学工作评价列表"按钮,查看该教学工作评价类别下的教学工作评价.<br />
					4.可点击"删除"按钮,删除某教学工作评价类别.<br />
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">

                <!-- 搜索条件，开始 -->
                <div class="overviewhead clearfix mb10">
                    <div class="fl mt5">
                        <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/teachingComment/teachingCommentList.json" method="get">
                            <input type="hidden" name="courseId" id="courseId" />
                            <input type="hidden" name="courseName" id="courseName" />
                             <div class="tableoptions disIb mb10">
                                <span class="vam">课程&nbsp;</span>
                                <label class="vam">
                                	<span id="coursespan">${teachingProgramCourse.courseName}</span>
                                	<a href="javascript:selectCourse()" class="stdbtn btn_orange">选择课程</a>
                                </label>
                            </div> 
                           
                            <div class="disIb ml20 mb10">
                               <span class="vam">开始时间 &nbsp;</span>
                               <label class="vam">
                               <input id="startTime" type="text" class="width100 laydate-icon" name="startTime" value="<fmt:formatDate type='both' value='${teachingComment.startTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
                               </label>
                           </div>
							<div class="disIb ml20 mb10">
                               <span class="vam">结束时间 &nbsp;</span>
                               <label class="vam">
                               <input id="endTime" type="text" class="width100 laydate-icon" name="endTime" value="<fmt:formatDate type='both' value='${teachingComment.endTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
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
                                <th class="head0 center">课程</th>         
                                <th class="head0 center">班次</th>     
                                <th class="head0 center">评价开始时间</th>
                                <th class="head0 center">评价结束时间</th>
                                <th class="head1 center">操作</th>
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
                                <td class="center">
                                	<a href="${ctx}/admin/jiaowu/teachingComment/teachingCommentManagementList.json?teachingCommentId=${teachingComment.id}" class="stdbtn" title="教学工作评价列表">教学工作评价列表</a>
                                    <a href="${ctx}/admin/jiaowu/teachingComment/toUpdateTeachingComment.json?id=${teachingComment.id}" class="stdbtn" title="修改">修改</a>
                                    <a href="javascript:void(0)" onclick="deleteTeachingComment(${teachingComment.id})" class="stdbtn" title="删除">删除</a>
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