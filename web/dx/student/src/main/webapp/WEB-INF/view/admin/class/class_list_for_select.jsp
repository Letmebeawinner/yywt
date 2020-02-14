<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<%@ include file="/popUpBase.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>班次列表</title>
<%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
        <script type="text/javascript">
        jQuery(function(){
			var classTypeId= "${classes.classTypeId}";
			jQuery("#classTypeId option[value='"+classTypeId+"']").attr("selected",true);
			var teacherName= "${classes.teacherName}";
			jQuery("#teacherspan").html(teacherName);
            var classNumber="${classes.classNumber}";
            jQuery("#classNumber").val(classNumber);
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
			function deleteClass(id){
				jConfirm('您确定要删除吗?','确认',function(r){
				    if(r){
				    	jQuery.ajax({
				    		url:'${ctx}/admin/jiaowu/class/deleteClass.json?id='+id,
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
			
			/* function selectTeacher(){
                window.open('${ctx}/admin/jiaowu/teacher/teacherListForSelect.json','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=1200,height=800');
            }

            function addToPeopleId(teacherArray){
                jQuery("#teacherspan").html(teacherArray[1]);
                jQuery("#teacherId").val(teacherArray[0]);
            } */
            
            var myArrayMoveStock = new Array();	
			function tijiao(){
				var qstChecked = jQuery(".questionIds:checked");
				if (qstChecked.length == 0) {
					jAlert('请选择班次','提示',function() {});
					return;
				}
				qstChecked.each(function() {
					var classIdAndName=jQuery(this).val();
					myArrayMoveStock.push(classIdAndName.split("-")[0]);
					myArrayMoveStock.push(classIdAndName.split("-")[1]);
				});
				opener.addClass(myArrayMoveStock);
				window.close();
			}
        </script>
	</head>
	<body>
	    <div class="tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">班次列表</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面用于其它页面获取班次,以弹出框形式展示.<br />
					2.可通过班型、编号查询对应的班次.<br />
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">

                <!-- 搜索条件，开始 -->
                <div class="overviewhead clearfix mb10">
                    <div class="fl mt5">
                        <form class="disIb" id="searchForm" action="${ctx}/jiaowu/class/classListForSelect.json" method="get">
                        <input type="hidden" name="teacherId" id="teacherId" value="${classes.teacherId }"/>
                            <div class="tableoptions disIb mb10">
                                <span class="vam">班型 &nbsp;</span>
                                <label class="vam">
                                <select name="classTypeId" class="vam" id="classTypeId">
                                    <option value="0">请选择</option>
                                    <c:if test="${classTypeList!=null&&classTypeList.size()>0}">
                                	<c:forEach items="${classTypeList }" var="classType">
                                    	<option value="${classType.id }">${classType.name}</option>
                                	</c:forEach>
                                	</c:if>
                                </select>
                                </label>
                            </div>
                           
                            <div class="disIb ml20 mb10">
                               <span class="vam">编号 &nbsp;</span>
                               <label class="vam">
                               <input id="classNumber" style="width: auto;" name="classNumber" type="text" class="hasDatepicker" value="" placeholder="请输入编号">
                               </label>
                           </div>

                           <%-- <div class="disIb ml20 mb10">
                               <span class="vam">讲师ID &nbsp;</span>
                               <label class="vam">
                                 <span id="teacherspan">${courseArrange.teacherName}</span>
                           		 <a href="javascript:selectTeacher()" class="stdbtn btn_orange">选择讲师</a>
                               </label>
                           </div> --%>
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
                                <th class="head0 center">班型</th>
                                <th class="head1 center">编号</th>
                                <th class="head0 center">名称</th>
                                <th class="head1 center">讲师</th>
                                <th class="head0 center">总人数</th>
                                <th class="head0 center">开班时间</th>
                                <th class="head0 center">结束时间</th>
<!--                                 <th class="head1 center">操作</th> -->
                            </tr>
                        </thead>
                        <tbody>
                        	<c:if test="${classList!=null&&classList.size()>0 }">
                            <c:forEach items="${classList}" var="classes">
                            <tr>
                            	<td><input name="classId" type="radio" value="${classes.id}-${classes.name}" class="questionIds" />${classes.id }</td>
                                <td>${classes.classType}</td>
                                <td>${classes.classNumber}</td>
                                <td>${classes.name}</td>
                                <td>${classes.teacherName}</td>
                                <td>${classes.studentTotalNum}</td>
                                <td><fmt:formatDate type="both" value="${classes.startTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td><fmt:formatDate type="both" value="${classes.endTime}" pattern="yyyy-MM-dd HH:mm"/></td>                                
                               <%--  <td class="center">
                                    <a href="${ctx}/admin/jiaowu/class/updateClass.json?id=${classes.id}" class="stdbtn" title="修改">修改</a>
                                    <a href="" onclick="deleteClass(${classes.id})" class="stdbtn" title="删除">删除</a>
                                    <a href="${ctx}/admin/jiaowu/courseArrange/toUpdateCourseArrange.json?classId=${classes.id}" class="stdbtn" title="修改">排课</a>
                                	<a href="${ctx}/admin/jiaowu/class/classActivity.json?classId=${classes.id}" class="stdbtn" title="班级课表">班级课表</a>
									<a href="${ctx}/admin/jiaowu/courseArrange/getCourseArrangeByClassId.json?classId=${classes.id}" class="stdbtn" title="班级课表">班级课表</a>
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