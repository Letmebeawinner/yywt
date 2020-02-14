<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<%@ include file="/popUpBase.jsp" %>
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
            
			/* var courseId= "${teachingComment.courseId}";
			jQuery("#courseId option[value='"+courseId+"']").attr("selected",true); */
			var startTime= "${teachingComment.startTime}";
			jQuery("#startTime").val(startTime);
            var endTime="${teachingComment.endTime}";
            jQuery("#endTime").val(endTime);
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
		var myArrayMoveStock = new Array();	
		function tijiao(){
			var qstChecked = jQuery(".questionIds:checked");
			if (qstChecked.length == 0) {
				jAlert('请选择教学评价类别','提示',function() {});
				return;
			}
			qstChecked.each(function() {
				var idAndCourseName=jQuery(this).val();
				myArrayMoveStock.push(idAndCourseName.split("-")[0]);
				myArrayMoveStock.push(idAndCourseName.split("-")[1]);
			});
			opener.addTeachingCommentType(myArrayMoveStock);
			window.close();
		}
        </script>
	</head>
	<body>
	    <div class="tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">教学工作评价类别列表</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面用于其它页面获取教学工作评价类别,以弹出框形式展示.<br />
					2.可通过开始时间、结束时间查询对应的教学工作评价类别.<br />
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">

                <!-- 搜索条件，开始 -->
                <div class="overviewhead clearfix mb10">
                    <div class="fl mt5">
                        <form class="disIb" id="searchForm" action="${ctx}/jiaowu/teachingComment/teachingCommentListForSelect.json" method="get">
                            <%-- <div class="tableoptions disIb mb10">
                                <span class="vam">课程&nbsp;</span>
                                <label class="vam">
                                <select name="courseId" class="vam" id="courseId">
                                    <option value="0">请选择</option>
                                    <c:if test="${courseList!=null&&courseList.size()>0}">
                                	<c:forEach items="${courseList }" var="course">
                                    	<option value="${course.id }">${course.name}</option>
                                	</c:forEach>
                                	</c:if>
                                </select>
                                </label>
                            </div> --%>
                           
                            <div class="disIb ml20 mb10">
                               <span class="vam">开始时间 &nbsp;</span>
                               <label class="vam">
                               <input id="startTime" type="text" class="width100 laydate-icon" name="startTime"/>
                               </label>
                           </div>
							<div class="disIb ml20 mb10">
                               <span class="vam">结束时间 &nbsp;</span>
                               <label class="vam">
                               <input id="endTime" type="text" class="width100 laydate-icon" name=endTime"/>
                               </label>
                           </div>
                           
                        </form>
                        <div class="disIb ml20 mb10">
                            <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                            <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                            <a href="javascript: void(0)" onclick="tijiao()" class="stdbtn btn_orange">确定</a>
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
                            	<th class="head0 center">ID</th> 
                                <th class="head0 center">课程</th>                               
                                <th class="head0 center">评价开始时间</th>
                                <th class="head0 center">评价结束时间</th>
<!--                                 <th class="head0 center">备注</th>     -->
<!--                                 <th class="head1 center">操作</th> -->
                            </tr>
                        </thead>
                        <tbody>
                        	<c:if test="${teachingCommentList!=null&&teachingCommentList.size()>0 }">
                            <c:forEach items="${teachingCommentList}" var="teachingComment">
                            <tr>
                            	<td><input name="teachingCommentId" type="radio" value="${teachingComment.id}-${teachingComment.courseName}" class="questionIds" />${teachingComment.id }</td>
                                <td>${teachingComment.courseName}</td>                               
                                <td><fmt:formatDate type="both" value="${teachingComment.startTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td><fmt:formatDate type="both" value="${teachingComment.endTime}" pattern="yyyy-MM-dd HH:mm"/></td>       
<%--                                 <td>${teachingComment.note}</td>                           --%>
                                <%-- <td class="center">
                                    <a href="" onclick="deleteTeachingComment(${teachingComment.id})" class="stdbtn" title="删除">删除</a>
                                </td> --%>
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