<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>教学材料列表</title>
<%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
        <script type="text/javascript">
        jQuery(function(){
			var title="${teachingMaterial.title}";
			jQuery("#title").val(title);
            var materialTypeId="${teachingMaterial.materialTypeId}";
            jQuery("#materialTypeId").val(materialTypeId);
        });
        function searchForm(){
            jQuery("#searchForm").submit();
        }
        
        function emptyForm(){
            jQuery("input:text").val('');
            jQuery("select").val(0);
           
        }
			function deleteTeachingMaterial(id){
				jConfirm('您确定要删除教学材料吗?','确认',function(r){
				    if(r){
				    	jQuery.ajax({
				    		url:'${ctx}/admin/jiaowu/teachingMaterial/delTeachingMaterial.json?id='+id,
				    		type:'post',
				    		dataType:'json',		    		
				    		success:function (result){
				    			if(result.code=="0"){
				    				window.location.reload();
				    			}else{
				    				jAlert(result.message,'提示',function() {});
				    				alert(result.message);
				    			}		    			
				    		},
				    		error:function(e){
				    			jAlert('删除失败','提示',function() {});
				    		}
				    	});
				    }
				});
			}
			
			function passTeachingMaterial(id){
				jConfirm('您确定要审核通过该教学材料吗?','确认',function(r){
				    if(r){
				    	jQuery.ajax({
				    		url:"${ctx}/admin/jiaowu/teachingMaterial/passTeachingMaterial.json?id="+id,
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
				});
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
                <h1 class="pagetitle">教学材料列表</h1>
                 <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示教学材料的列表.<br />
					2.可通过讲师、教学材料名称查询对应的教学材料.<br />
					3.可点击"下载"按钮,下载该教学材料.<br />
					4.如果某教学材料还未审核通过,可点击"审核通过"按钮,表示已审核通过该教学材料.<br />
					4.可点击"删除"按钮,删除某教学材料.<br />
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">

                <!-- 搜索条件，开始 -->
                <div class="overviewhead clearfix mb10">
                    <div class="fl mt5">
                        <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/teachingMaterial/teachingMaterialList.json" method="get">
                            <input type="hidden" name="classId" id="classId" value="${materialType.classId}"/>
                            <input type="hidden" name="className" id="className" value="${className}"/>
                            <div class="disIb ml20 mb10">
                               <span class="vam">资料类别&nbsp;</span>
                               <label class="vam">
                                   <select id="materialTypeId" name="materialTypeId">
                                       <option value="">请选择</option>
                                       <c:forEach items="${materialTypeList}" var="materialType">
                                           <option value="${materialType.id}">${materialType.name}</option>
                                       </c:forEach>
                                   </select>
                               </label>
                           </div>
                           <div class="disIb ml20 mb10">
                               <span class="vam">教学材料名称&nbsp;</span>
                               <label class="vam">
                               <input id="title" style="width: auto;" name="title" type="text" class="hasDatepicker" value="" placeholder="请输入教学材料名称">
                               </label>
                           </div>
                            <c:if test="${isAdministrator==true}">
                            <div class="disIb ml20 mb10">
                                <span class="vam">班级 &nbsp;</span>
                                <label class="vam">
                                    <span id="classspan">${className}</span>
                                    <a href="javascript:void(0)" onclick="selectClass()" class="stdbtn btn_orange">选择班次</a>
                                </label>
                            </div>
                            </c:if>
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
                                <th class="head0 center">创建人</th>
                                <th class="head1 center">资料类别</th>
                                <th class="head1 center">名称</th>
                                <th class="head0 center">路径</th>                               
                                <th class="head0 center">创建时间</th>
                                <th class="head0 center">更新时间</th>
                                <th class="head1 center">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<c:if test="${teachingMaterialList!=null&&teachingMaterialList.size()>0 }">
                            <c:forEach items="${teachingMaterialList}" var="teachingMaterial" varStatus="index">
                            <tr>
                                <td>${index.index+1}</td>
                                <td>${teachingMaterial.createUserName}</td>
                                <td>${teachingMaterial.materialTypeName}</td>
                                <td>${teachingMaterial.title}</td>
                                <td>${teachingMaterial.path}</td>                               
                                <td><fmt:formatDate type="both" value="${teachingMaterial.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td><fmt:formatDate type="both" value="${teachingMaterial.updateTime}" pattern="yyyy-MM-dd HH:mm"/></td>                                
                                <td class="center">
<%--                                     <a href="${ctx}/admin/jiaowu/teachingMaterial/downloadFile.json?fileUrl=${teachingMaterial.path}" class="stdbtn" title="下载">下载</a> --%>
                                    <a href="http://10.100.101.1:6694/upload/res/downolad/downoladFile.json?fileUrl=${teachingMaterial.path}&fileName=${teachingMaterial.title}" class="stdbtn" title="下载">下载</a>
                                    <c:if test="${teachingMaterial.hasCheck==0}">
                                    <a href="javascript:void(0)" onclick="passTeachingMaterial(${teachingMaterial.id})" class="stdbtn" title="审核通过">审核通过</a>
                                    </c:if>
                                    <a href="javascript:void(0)" onclick="deleteTeachingMaterial(${teachingMaterial.id})" class="stdbtn" title="删除">删除</a>
                                    
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