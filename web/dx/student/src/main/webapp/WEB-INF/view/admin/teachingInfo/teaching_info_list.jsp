<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>教学动态列表</title>
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
            
			
			/* var startTime= "${startTime}";
			jQuery("#startTime").val(startTime);
            var endTime="${endTime}";
            jQuery("#endTime").val(endTime); */
            var title="${teachingInfo.title}";
            jQuery("#title").val(title);
        });
        function searchForm(){
            jQuery("#searchForm").submit();
        }
        
        function emptyForm(){
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }
			function deleteTeachingInfo(id){
				jConfirm('您确定要删除该教学动态吗?','确认',function(r){
				    if(r){
				    	jQuery.ajax({
				    		url:'${ctx}/admin/jiaowu/teachingInfo/delTeachingInfo.json?id='+id,
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
			
			
        </script>
	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">教学动态列表</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示教学动态的列表.<br />
					2.可通过标题、开始时间和结束时间查询对应的教学动态.<br />
					3.可点击"删除"按钮,删除某教学动态.<br />
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">

                <!-- 搜索条件，开始 -->
                <div class="overviewhead clearfix mb10">
                    <div class="fl mt5">
                        <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/teachingInfo/teachingInfoList.json" method="get">
                            
                           <div class="disIb ml20 mb10">
                               <span class="vam">标题 &nbsp;</span>
                               <label class="vam">
                               <input id="title" type="text" class="width100" name="title"/>
                               </label>
                           </div>
                            <div class="disIb ml20 mb10">
                               <span class="vam">开始时间 &nbsp;</span>
                               <label class="vam">
                               <input id="startTime" type="text" class="width100 laydate-icon" name="startTime" value="<fmt:formatDate type='both' value='${startTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
                               </label>
                           </div>
							<div class="disIb ml20 mb10">
                               <span class="vam">结束时间 &nbsp;</span>
                               <label class="vam">
                               <input id="endTime" type="text" class="width100 laydate-icon" name="endTime" value="<fmt:formatDate type='both' value='${endTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
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
                                <th class="head0 center">类型</th>                             
                                <th class="head0 center">标题</th>
                                <th class="head0 center" width="70%;">内容</th>
                                <th class="head0 center">创建时间</th>   
                                <th class="head1 center">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<c:if test="${teachingInfoList!=null&&teachingInfoList.size()>0 }">
                            <c:forEach items="${teachingInfoList}" var="teachingInfo" varStatus="index">
                            <tr>
                                <td>${index.index+1}</td>
                                <td>
                                <c:if test="${teachingInfo.type==1}">
                                	排课信息
                                </c:if>
                                <c:if test="${teachingInfo.type==2}">
                                	教学计划信息
                                </c:if>
                                </td>  
                                <td>${teachingInfo.title}</td>  
                                <td>${teachingInfo.content}</td>                               
                                <td><fmt:formatDate type="both" value="${teachingInfo.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td class="center">
                                    <a href="javascript:void(0)" onclick="deleteTeachingInfo(${teachingInfo.id})" class="stdbtn" title="删除">删除</a>
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