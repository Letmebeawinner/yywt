<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>学员列表</title>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
<%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
        <script type="text/javascript">
        jQuery(function(){
        	jQuery("#classTypeId").change(function(){
        		 var selectedClassTypeId = jQuery(this).children('option:selected').val();
        		jQuery.ajax({
 	    		url:'${ctx}/admin/jiaowu/class/getClassListByClassType.json',
 	    		data:{"classTypeId":selectedClassTypeId
 	    			},
 	    		type:'post',
 	    		dataType:'json',		
 	    		success:function (result){
 	    			if(result.code=="0"){
 	    				var list=result.data;
 	    				var classstr="<option value=0>请选择</option>";
 	    				if(list!=null&&list.length>0){
 	    					
 	    					for(var i=0;i<list.length;i++){
 	    						classstr+="<option value='"+list[i].id+"'>"+list[i].name+"</option>";
 	    					}
 	    					
 	    				}
 	    				jQuery("#classId").html(classstr);
						jQuery("#classId").val("${user.classId}");
 	    			}else{
 	    				jAlert(result.message,'提示',function() {});
 	    			}		    			
 	    		} ,
 	    		error:function(e){
 	    			jAlert('添加失败','提示',function() {});
 	    		}
 	    	});
        	 });
			jQuery("#classTypeId").val("${user.classTypeId}");
			jQuery("#classTypeId").trigger("change");

            var studentId="${user.studentId}";
            jQuery("#studentId").val(studentId);
            /*var baodao="${user.baodao}";
            jQuery("#baodao option[value='"+baodao+"']").attr("selected",true);*/
			jQuery("#userId").val("${user.id}");
			jQuery("#name").val("${user.name}");
			jQuery("#idNumber").val("${user.idNumber}");
			jQuery("#email").val("${user.email}");
			jQuery("#mobile").val("${user.mobile}");
        });
        function searchForm(){
            jQuery("#searchForm").submit();
        }
        
        function emptyForm(){
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }
			function deleteUser(id){
				jConfirm('您确定要删除吗?','确认',function(r){
				    if(r){
				    	jQuery.ajax({
				    		url:'${ctx}/admin/jiaowu/user/delUser.json?id='+id,
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
			
		function assignMonitor(userId){
			jQuery.ajax({
	    		url:'${ctx}/admin/jiaowu/user/getMonitor.json',
	    		data:{"userId":userId
	    			},
	    		type:'post',
	    		dataType:'json',		    		
	    		success:function (result){
	    			console.info("success");
	    			if(result.code=="0"){
	    				if(result.data.hasMonitor==1){
	    					jConfirm('该班次原班长为'+result.data.monitorName+',您确定要修改班长吗?','确认',function(r){
	    					    if(r){
	    					    	jQuery.ajax({
		    				    		url:'${ctx}/admin/jiaowu/user/assignMonitor.json?userId='+userId+"&monitorId="+result.data.monitorId,
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
		    				    			jAlert('修改失败','提示',function() {});
		    				    		}
		    				    	});
	    					    }
	    					});
	    				}else{
	    					jConfirm('您确定要将其设为班长吗?','确认',function(r){
	    					    if(r){
	    					    	jQuery.ajax({
							    		url:'${ctx}/admin/jiaowu/user/assignMonitor.json?userId='+userId,
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
							    			jAlert('修改失败','提示',function() {});
							    		}
							    	});
	    					    }
	    					});
	    				}
	    				
	    			}else{	   
	    				jAlert(result.message,'提示',function() {});
	    			}		    			
	    		},
	    		error:function(e){
	    			jAlert('查询失败','提示',function() {});
	    		}
	    	});
		}
		
		function cancelMonitor(userId){
			jQuery.ajax({
	    		url:'${ctx}/admin/jiaowu/user/cancelMonitor.json?userId='+userId,
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
	    			jAlert('更新失败','提示',function() {});
	    		}
	    	});
		}
        </script>
	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">学员列表</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示学员的基本信息.<br />
					2.可通过班型、班次和学号查询学员.<br />
					3.可点击"修改"按钮,修改学员.<br />
<!-- 					4.可点击"删除"按钮,删除某学员.<br /> -->
				<%--	4.可点击"学员课表"按钮,查看某学员的课表.<br />
					5.如果某位学员是班长,可点击"撤销班长职务"按钮,将该学员班长职务撤销.如果该学员不是班长,可点击"设为班长"按钮,将该学员设为班长.一个班次只能有一个班长.--%>
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">

                <!-- 搜索条件，开始 -->
                <div class="overviewhead clearfix mb10">
                    <div class="fl mt5">
                        <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/user/userInfo.json" method="get">
							<%--<div class="disIb ml20 mb10">
								<span class="vam">ID &nbsp;</span>
								<label class="vam">
									<input id="userId" style="width: auto;" name="userId" type="text" class="hasDatepicker" value="" placeholder="请输入ID">
								</label>
							</div>--%>
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
                               <span class="vam">班次 &nbsp;</span>
                               <label class="vam">
                               <select name="classId" class="vam" id="classId" >
                               <option value="0">请选择</option>
                               </select>
                               </label>
                           </div>
                           
                            <div class="disIb ml20 mb10">
                               <span class="vam">学号 &nbsp;</span>
                               <label class="vam">
                               <input id="studentId" style="width: auto;" name="studentId" type="text" class="hasDatepicker" value="" placeholder="请输入学号">
                               </label>
                           </div>
							<div class="disIb ml20 mb10">
								<span class="vam">名称 &nbsp;</span>
								<label class="vam">
									<input id="name" style="width: auto;" name="name" type="text" class="hasDatepicker" value="" placeholder="请输入名称">
								</label>
							</div>
							<div class="disIb ml20 mb10">
								<span class="vam">身份证号 &nbsp;</span>
								<label class="vam">
									<input id="idNumber" style="width: auto;" name="idNumber" type="text" class="hasDatepicker" value="" placeholder="请输入身份证号">
								</label>
							</div>
							 <%--<div class="disIb ml20 mb10">
                               <span class="vam">邮箱 &nbsp;</span>
                               <label class="vam">
                               <input id="email" style="width: auto;" name="email" type="text" class="hasDatepicker" value="" placeholder="请输入邮箱">
                               </label>
                           </div>
                            <div class="disIb ml20 mb10">
                               <span class="vam">手机号 &nbsp;</span>
                               <label class="vam">
                               <input id="mobile" style="width: auto;" name="mobile" type="text" class="hasDatepicker" value="" placeholder="请输入手机号">
                               </label>
                           </div>--%>
                          <!--  <div class="disIb ml20 mb10">
                               <span class="vam">是否报到 &nbsp;</span>
                               <label class="vam">
                               <select name="baodao" class="vam" id="baodao">
                                    <option value="-1">所有</option>
                                    <option value="0">未报到</option>
                                    <option value="1">已报到</option>
                                </select>
                               </label>
                           </div> -->
                        </form>
                        <div class="disIb ml20 mb10">
                            <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                            <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                        </div>
                       <%--  <div class="disIb ml20 mb10">
                               <span class="vam">总人数: &nbsp;</span>
                               <label class="vam">
                               ${totalNum}人
                               </label>
                           </div> --%>
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
                                <th class="head0 center">班型ID</th>
                                <th class="head0 center">班型</th>
                                <th class="head0 center">班次ID</th>
                                <th class="head0 center">班次</th>
                                <th class="head1 center">学号</th>
                                <th class="head0 center">名称</th>
                                <th class="head1 center">身份证号</th>
                                <th class="head0 center">手机号</th>
                                <th class="head0 center">邮箱</th>
                                <th class="head0 center">性别</th>
                                <th class="head0 center">年龄</th>
                                <th class="head0 center">创建时间</th>
                                <th class="head1 center">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<c:if test="${userList!=null&&userList.size()>0 }">
                            <c:forEach items="${userList}" var="user">
                            <tr>
                                <td>${user.id}</td>
                                <td>${user.classTypeId}</td>
                                <td>${user.classTypeName}</td>
                                <td>${user.classId}</td>
                                <td>${user.className}</td>
                                <td>${user.studentId}</td>
                                <td>${user.name}</td>
                                <td>${user.idNumber}</td>
                                <td>${user.mobile}</td>
                                <td>${user.email}</td>
                                <td>${user.sex}</td>
                                <td>${user.age}</td>
                                <td><fmt:formatDate type="both" value="${user.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td class="center">
                                	
                                	<a href="${ctx}/admin/jiaowu/user/toUpdateUser.json?id=${user.id}" class="stdbtn" title="修改">修改</a>
                                    <%--<a href="javascript:void(0)" onclick="deleteUser(${user.id})" class="stdbtn" title="删除">删除</a>
                                    <a href="${ctx}/admin/jiaowu/courseArrange/studentCourseArrange.json?userId=${user.id}" class="stdbtn" title="学员课表">学员课表</a>
                                    <c:if test="${user.isMonitor==0 }">
                                    <a href="javascript:void(0)" onclick="assignMonitor(${user.id})" class="stdbtn" title="设为班长">设为班长</a>
                                    </c:if>
                                    <c:if test="${user.isMonitor==1}">
                                    <a href="javascript:void(0)" onclick="cancelMonitor(${user.id})" class="stdbtn" title="取消班长职务">撤销班长职务</a>
                                    </c:if>--%>
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