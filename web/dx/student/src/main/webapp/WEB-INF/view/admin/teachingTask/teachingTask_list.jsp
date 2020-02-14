<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>教学任务列表</title>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
<%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
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
			function deleteTeachingTask(id){
				if(confirm("您确定要删除吗?")){
				 jQuery.ajax({
		    		url:'${ctx}/admin/jiaowu/teachingTask/deleteTeachingTask.json?id='+id,
		    		type:'post',
		    		dataType:'json',		    		
		    		success:function (result){
		    			if(result.code=="0"){
		    				windows.location.reload();
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
			
			function selectClass(){
	        	window.open('${ctx}/jiaowu/class/classListForSelect.json','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=1200,height=800');
	        }
	        
	        function addClass(classArray){
	        	jQuery("#classspan").html(classArray[1]);
	            jQuery("#classId").val(classArray[0]);
	            jQuery("#className").val(classArray[1]);
	        }
        </script>
	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">教学任务列表</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示教学任务的列表.<br />
					2.可通过班型、班次查询对应的教学任务.<br />
					3.可点击"查询"按钮,查看某教学任务的具体内容.
					3.可点击"修改"按钮,修改教学任务.<br />
					4.可点击"删除"按钮,删除某教学任务.<br />
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">

                <!-- 搜索条件，开始 -->
                <div class="overviewhead clearfix mb10">
                    <div class="fl mt5">
                        <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/teachingTask/teachingTaskList.json" method="get">
                           	<input type="hidden" id="classId" name="classId" value="${teachingTask.classId}" />
                           	<input type="hidden" id="className" name="className" value="${className}" />
                            <%-- <div class="tableoptions disIb mb10">
                                <span class="vam">班型 &nbsp;</span>
                                <label class="vam">
                                <select name="classTypeId" id="classTypeId">
                            	<option value="">请选择</option>
                                <c:if test="${classTypeList!=null&&classTypeList.size()>0}">
                                	<c:forEach items="${classTypeList}" var="classType">
                                		<option value="${classType.id}">${classType.name }</option>
                                	</c:forEach>
                                </c:if>
                                
                            </select>
                                </label>
                            </div> --%>
                           
                            <div class="disIb ml20 mb10">
                               <span class="vam">班次 &nbsp;</span>
                               <label class="vam">
                               </label>
                            		<span class="field">
                            		<span id="classspan">${className}</span>
                                		<a href="javascript:selectClass()" class="stdbtn btn_orange">选择班次</a>
                            		</span>
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
                                <th class="head1 center">班型</th>
                                <th class="head0 center">班次</th>                               
                                <th class="head1 center">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${teachingTaskList}" var="teachingTask" varStatus="index">
                            <tr>
                                <td>${index.index+1}</td>
                                <td>${teachingTask.classTypeName}</td>
                                <td>${teachingTask.className}</td>                              
                                <td class="center">
                                	<a href="${ctx}/admin/jiaowu/teachingTask/queryTeachingTask.json?id=${teachingTask.id}" class="stdbtn" title="查询">查询</a>
                                    <a href="${ctx}/admin/jiaowu/teachingTask/updateTeachingTask.json?id=${teachingTask.id}" class="stdbtn" title="修改">修改</a>
                                    <a href="" onclick="deleteTeachingTask(${teachingTask.id})" class="stdbtn" title="删除">删除</a>
                                </td>
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
	</body>
</html>