<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>专题列表</title>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
        <script type="text/javascript">
        jQuery(function(){
            var year="${course.year}";
            if(year != null && year !=''){
                jQuery("#year").val(year);
            }
            var season="${course.season}";
            if(season != null && season !=''){
                jQuery("#season").val(season);
            }
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
            jQuery("#season").val("");
            jQuery("#year").val("");
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
			
			 function selectTeacher(){
	                window.open('${ctx}/jiaowu/teacher/teacherListForSelect.json','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=1200,height=800');
	            }

	            function addToPeopleId(teacherArray){
	                jQuery("#teacherspan").html(teacherArray[1]);
	                jQuery("#teacherId").val(teacherArray[0]);
	                jQuery("#teacherName").val(teacherArray[1]);
	            }
        </script>
	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">专题列表</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示专题的列表.<br />
					2.可通过讲师、专题类别、状态查询对应的专题.<br />
					3.可点击"修改"按钮,修改某专题.<br />
					4.可点击"删除"按钮,删除某专题.<br />
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">

                <!-- 搜索条件，开始 -->
                <div class="overviewhead clearfix mb10">
                    <div class="fl mt5">
                        <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/course/courseList.json" method="get">
                        	<input type="hidden" name="teacherId" id="teacherId" value="${course.teacherId}"/>
                        	<input type="hidden" name="teacherName" id="teacherName" value="${course.teacherName }"/>
                        	
                            <div class="tableoptions disIb mb10">
                                <span class="vam">讲师 &nbsp;</span>
                                <label class="vam">
                                	<span id="teacherspan">${course.teacherName}</span>
                            		<a href="javascript:selectTeacher()" class="stdbtn btn_orange">选择讲师</a>
                                </label>
                            </div>
                           
                           <div class="disIb ml20 mb10">
                               <span class="vam">专题类别&nbsp;</span>
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
                                <span class="vam">专题类别&nbsp;</span>
                                <label class="vam">
                                    <input style="width: 200px;" type="text" name="keyWord" value="${keyWord}" placeholder="输入名称、类别、讲师姓名、备注">
                                </label>
                            </div>
                            <div class="disIb ml20 mb10">
                                <span class="vam">年份&nbsp;</span>
                                <label class="vam">
                                    <select name="year" class="vam" id="year">
                                        <option value="">请选择</option>
                                        <c:forEach begin="2018" end="2030" var="year">
                                            <option value="${year}">${year}</option>
                                        </c:forEach>
                                    </select>
                                </label>
                            </div>
                            <div class="disIb ml20 mb10">
                                <span class="vam">季节&nbsp;</span>
                                <label class="vam">
                                    <select name="season" class="vam" id="season">
                                        <option value="">请选择</option>
                                        <option value="1">春季</option>
                                        <option value="2">秋季</option>
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
                                <th class="head0 center">专题类别</th>
                                <th class="head1 center">讲师</th>
                                <th class="head0 center">备注</th>
                                <th class="head0 center">年份</th>
                                <th class="head0 center">季节</th>
                                <th class="head0 center">创建时间</th>
                                <th class="head1 center">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<c:if test="${courseList!=null&&courseList.size()>0}">
                            <c:forEach items="${courseList}" var="course" varStatus="index">
                            <tr>
                                <td>${index.count}</td>
                                <td>${course.name}</td>
                                <td>${course.courseTypeName}</td>
                                <td>${course.teacherName}</td>
                                <td>${course.note}</td>
                                <td>${course.year}</td>
                                <td>
                                    <c:if test="${course.season==1}">
                                        春季
                                    </c:if>
                                    <c:if test="${course.season==2}">
                                        秋季
                                    </c:if>
                                </td>
                                <td><fmt:formatDate type="both" value="${course.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td class="center">
                                    <a href="${ctx}/admin/jiaowu/course/toUpdateCourse.json?id=${course.id}" class="stdbtn" title="修改">修改</a>
                                    <a href="javascript:void(0)" onclick="deleteCourse(${course.id})" class="stdbtn" title="删除">删除</a>
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