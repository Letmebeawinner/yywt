<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<%@ include file="/popUpBase.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>课程列表</title>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
        <script type="text/javascript">
        jQuery(function(){
			
			
            var status="${course.status}";
            jQuery("#status option[value='"+status+"']").attr("selected",true);
            var courseTypeId="${course.courseTypeId}";
            jQuery("#courseTypeId option[value='"+courseTypeId+"']").attr("selected",true);
        });
        function searchForm(){
            jQuery("#searchForm").submit();
        }
        
        function emptyForm(){
            jQuery("input:text").val('');
            jQuery("select").val(0);
            jQuery("#teacherId").val(0);
            jQuery("#teacherName").val("");
            jQuery("#teacherspan").html("");
        }
			function deleteCourse(id){
				jConfirm('您确定要删除课程吗?','确认',function(r){
				    if(r){
				    	jQuery.ajax({
				    		url:'${ctx}/admin/jiaowu/course/delCourse.json?id='+id,
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
				});
			}
			

	            var myArrayMoveStock = new Array();	
				function tijiao(){
					var qstChecked = jQuery(".questionIds:checked");
					if (qstChecked.length == 0) {
						jAlert('请选择课程','提示',function() {});
						return;
					}
					qstChecked.each(function() {
						var courseIdAndName=jQuery(this).val();
						myArrayMoveStock.push(courseIdAndName.split("-")[0]);
						myArrayMoveStock.push(courseIdAndName.split("-")[1]);
					});
					opener.addCourse(myArrayMoveStock);
					window.close();
				}
        </script>
	</head>
	<body>
	    <div class="tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">课程列表</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面用于其它页面获取课程,以弹出框形式展示.<br />
					2.可通过课程类别、状态查询对应的课程.<br />
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">

                <!-- 搜索条件，开始 -->
                <div class="overviewhead clearfix mb10">
                    <div class="fl mt5">
                        <form class="disIb" id="searchForm" action="${ctx}/jiaowu/course/courseListForSelect.json" method="get">
                        	<input type="hidden" name="teacherId" id="teacherId" value="${course.teacherId}"/>
                        	<input type="hidden" name="teacherName" id="teacherName" value="${course.teacherName }"/>
                           <div class="disIb ml20 mb10">
                               <span class="vam">课程类别&nbsp;</span>
                               <label class="vam">
                               <select name="courseTypeId" class="vam" id="courseTypeId">
                                    <option value="0">请选择</option>
                                	<c:if test="${courseTypeList!=null&&courseTypeList.size()>0}">
                            		<c:forEach items="${courseTypeList}" var="courseType">
                            			<option value="${courseType.id}">${courseType.name}</option>
                            		</c:forEach>
                            	</c:if>
                                </select>
                               </label>
                           </div>
                           
                            <div class="disIb ml20 mb10">
                               <span class="vam">状态&nbsp;</span>
                               <label class="vam">
                               <select name="status" class="vam" id="status">
                                    <option value="-1">请选择</option>
                                	<option value="1">上架</option>
                                	<option value="0">下架</option>
                                </select>
                               </label>
                           </div>

                           
                        </form>
                        <div class="disIb ml20 mb10">
                            <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                            <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                            <a href="javascript: void(0)" onclick="tijiao()" class="stdbtn ml10">确定</a>
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
                                <th class="head0 center">名称</th>
                                <th class="head0 center">课程类别</th>
                                <th class="head0 center">学时</th>
                                <th class="head1 center">讲师</th>
                                <th class="head0 center">创建时间</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<c:if test="${courseList!=null&&courseList.size()>0}">
                            <c:forEach items="${courseList}" var="course">
                            <tr>
                            	<td><input name="courseId" type="radio" value="${course.id}-${course.name}" class="questionIds" />${course.id }</td>
                                <td>${course.name}</td>
                                 <td>${course.courseTypeName}</td>
                                <td>${course.hour}</td>
                                <td>${course.teacherName}</td>
                                <td><fmt:formatDate type="both" value="${course.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
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