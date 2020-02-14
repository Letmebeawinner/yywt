<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>班次列表</title>
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
			
			function intelligentCourseArrange(classId){
				jConfirm('您确定要进行智能排课吗?','确认',function(r){
				    if(r){
				    	window.location.href="${ctx}/admin/jiaowu/courseArrange/intelligentCourseArrange.json?classId="+classId;
				    }
				});
			}
			
			
			function selectTeacher(){
                window.open('${ctx}/jiaowu/teacher/classLeaderListForSelect.json','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=800,height=600');
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
	         <div class="pageheader notab">
                 <div style="margin-left: 30px">
                <h1 class="pagetitle">班次列表</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示班次的列表.<br />
					2.可通过班型、编号查询对应的班次.<br />
					3.可点击"修改"按钮,修改班次的信息.<br />
					4.可点击"删除"按钮,删除某班次.<br />
					5.可点击"排课"按钮,为某班次排课.<br />
					6.可点击"班次课表"按钮,查看某班次的课表.
                </span>
                 </div>
             </div><!--pageheader-->
            <div id="contentwrapper" class="contentwrapper">

                <!-- 搜索条件，开始 -->
                <div class="overviewhead clearfix mb10">
                    <div class="fl mt5">
                        <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/class/classList.json" method="get">
                            <input type="hidden" name="teacherId" id="teacherId" value="${classes.teacherId }"/>
                            <input type="hidden" name="teacherName" id="teacherName" value="${classes.teacherName}" />
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
                                <span class="vam">讲师 &nbsp;</span>
                                <label class="vam">
                                    <span id="teacherspan">${courseArrange.teacherName}</span>
                                    <a href="javascript:selectTeacher()" class="stdbtn btn_orange">选择讲师</a>
                                </label>
                            </div>

                            <div class="disIb ml20 mb10">
                                <span class="vam">名称 &nbsp;</span>
                                <label class="vam">
                                    <input type="text" name="className" value="${classes.name}">
                                </label>
                            </div>

                        </form>
                        <div class="disIb ml20 mb10">
                            <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                            <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                            <a href="/admin/jiaowu/class/createClass.json" class="stdbtn btn_orange">新 建</a>
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
                            <td class="head0 center">班次id</td>
                            <th class="head0 center">班型</th>
                            <th class="head0 center" width="16%">名称</th>
                            <th class="head1 center">班主任</th>
                            <th class="head0 center">已报到人数</th>
                            <th class="head0 center">开始时间</th>
                            <th class="head0 center">结束时间</th>
                            <th class="head0 center">报名截止时间</th>
                            <th class="head1 center">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${classList!=null&&classList.size()>0 }">
                            <c:forEach items="${classList}" var="classes">
                                <tr>
                                    <td>${classes.id}</td>
                                    <td>${classes.classType}</td>
                                    <td>${classes.name}</td>
                                    <td>${classes.teacherName}</td>
                                    <td>${classes.registration == 2 ? classes.peopleNumber:classes.totalNum}</td>
                                    <td><fmt:formatDate type="both" value="${classes.startTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                                    <td><fmt:formatDate type="both" value="${classes.endTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                                    <td>
                                        <c:if test="${classes.registration == 2}">
                                                    -----------
                                        </c:if>
                                        <c:if test="${classes.registration == 1}">
                                            <fmt:formatDate type="both" value="${classes.signEndTime}" pattern="yyyy-MM-dd HH:mm"/>
                                        </c:if>
                                    </td>
                                    <td class="center">
                                        <a href="${ctx}/admin/jiaowu/class/toUpdateClassLeaderOfClass.json?id=${classes.id}"
                                           class="stdbtn" title="设置班主任">设置班主任</a>
                                        <c:if test="${teacher==null||teacher==false}">
                                        <a href="${ctx}/admin/jiaowu/class/updateClass.json?id=${classes.id}" class="stdbtn" title="修改">修改</a>
                                        <a href="javascript:void(0)" onclick="deleteClass(${classes.id})" class="stdbtn" title="删除">删除</a>
                                        <a href="${ctx}/admin/jiaowu/courseArrange/toUpdateCourseArrange.json?classId=${classes.id}" class="stdbtn" title="修改">排课</a>
                                        </c:if>
                                        <a href="${ctx}/admin/jiaowu/courseArrange/classCourseArrange.json?classId=${classes.id}" class="stdbtn" title="班级课表">班级课表</a>
                                        <a href="${ctx}/admin/jiaowu/exportClassSch.json?id=${classes.id}"
                                           class="stdbtn" title="导出课表">导出课表</a>
                                        <a href="${ctx}/admin/jiaowu/user/newUserListExcel.json?classId=${classes.id}" class="stdbtn" title="导出学员">导出学员</a>
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