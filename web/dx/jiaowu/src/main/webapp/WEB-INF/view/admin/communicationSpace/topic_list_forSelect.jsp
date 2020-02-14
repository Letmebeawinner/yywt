<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<%@ include file="/popUpBase.jsp" %>
<%-- <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>话题列表</title>
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
			function deleteTopic(id){
				jConfirm('您确定要删除话题吗?','确认',function(r){
				    if(r){
				    	jQuery.ajax({
				    		url:'${ctx}/admin/jiaowu/topic/deleteTopic.json?id='+id,
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
			
			function tijiao(){
				var topicIdAndName=jQuery('input[name="topicId"]:checked').val();
				var array=new Array();
				array=topicIdAndName.split("-");
				opener.addTopic(array);
				window.close();
			}
        </script>
	</head>
	<body>
	    <div class="tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">话题列表</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面用于其它页面获取话题,以弹出框形式展示.<br />
					2.可通过创建者、名称查询对应的话题.<br />
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">

                <!-- 搜索条件，开始 -->
                <div class="overviewhead clearfix mb10">
                    <div class="fl mt5">
                        <form class="disIb" id="searchForm" action="${ctx}/jiaowu/topic/topicListForSelect.json" method="get">
                           
                           <div class="disIb ml20 mb10">
                               <span class="vam">创建者 &nbsp;</span>
                               <label class="vam">
                               <input id="createUserName" style="width: auto;" name="createUserName" type="text" class="hasDatepicker" value="${topic.createUserName}" placeholder="请输入创建者">
                               </label>
                           </div>
                           
                            <div class="disIb ml20 mb10">
                               <span class="vam">名称 &nbsp;</span>
                               <label class="vam">
                               <input id="title" style="width: auto;" name="title" type="text" class="hasDatepicker" value="${topic.title}" placeholder="请输入名称">
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
                                <th class="head0 center">标题</th>
                                <th class="head1 center">内容</th>
                                <th class="head0 center">创建者</th>
                                <th class="head0 center">点击量</th>
                                <th class="head1 center">回复数</th>
                                <th class="head0 center">创建时间</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<c:if test="${topicList!=null&&topicList.size()>0 }">
                            <c:forEach items="${topicList}" var="topic">
                            <tr>
                            	<td><input type="radio" value="${topic.id}-${topic.title}" class="questionIds" name="topicId"/>${topic.id }</td>
                                <td>${topic.title}</td>
                                <td>${fn:substring(topic.content,0,40)}...</td>
                                <td>${topic.createUserName}</td>
                                <td>${topic.viewNum}</td>
                                <td>${topic.replyNum}</td>
                                <td><fmt:formatDate type="both" value="${topic.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
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