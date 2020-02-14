<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<%-- <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>回复列表</title>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
<%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
        <script type="text/javascript">
        jQuery(function(){
        	var userName="${reply.userName}";
        	jQuery("#userName").val(userName);
        	var content="${reply.content}";
            jQuery("#content").val(content);
        });
        function searchForm(){
            jQuery("#searchForm").submit();
        }
        
        function emptyForm(){
            jQuery("input:text").val('');
            jQuery("select").val(0);
            jQuery("#topicspan").html("");
//             jQuery("#title").val("");
        }
			function deleteReply(id){
				jConfirm('您确定要删除吗?','确认',function(r){
				    if(r){
				    	 jQuery.ajax({
					    		url:'${ctx}/admin/jiaowu/reply/deleteReply.json?id='+id,
					    		type:'post',
					    		dataType:'json',		    		
					    		success:function (result){
					    			if(result.code=="0"){
					    				window.location.reload();
					    			}else{
					    				jAlert(删除失败,'提示',function() {});
					    			}		    			
					    		},
					    		error:function(e){
					    			jAlert('删除失败','提示',function() {});
					    		}
					    	});
				    }
				});
			}
			
			function selectTopic(){
                window.open('${ctx}/jiaowu/topic/topicListForSelect.json','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=1200,height=800');
            }
			
			function addTopic(topicIdAndName){
				jQuery("#topicId").val(topicIdAndName[0]);
				jQuery("#topicTitle").val(topicIdAndName[1]);
				jQuery("#topicspan").html(topicIdAndName[1]);
			}
        </script>
	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">回复列表</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示所有对话题的评论列表.<br>
					2.可通过创建者、内容、话题三个条件查询对应的评论.<br>
					3.可点击"删除"按钮,删除某评论.
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">

                <!-- 搜索条件，开始 -->
                <div class="overviewhead clearfix mb10">
                    <div class="fl mt5">
                        <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/reply/replyList.json" method="get">
                           <input type="hidden" name="topicId" value="${reply.topicId}" id="topicId" />
                           <input type="hidden" name="topicTitle" value="${reply.topicTitle}" id="topicTitle" />
                            <div class="disIb ml20 mb10">
                               <span class="vam">创建者 &nbsp;</span>
                               <label class="vam">
                               <input id="userName" style="width: auto;" name="userName" type="text" class="hasDatepicker" value="" placeholder="请输入创建者">
                               </label>
                           </div>
                           
                            <div class="disIb ml20 mb10">
                               <span class="vam">内容 &nbsp;</span>
                               <label class="vam">
                               <input id="content" style="width: auto;" name="content" type="text" class="hasDatepicker" value="" placeholder="请输入内容">
                               </label>
                           </div>

                           <div class="disIb ml20 mb10">
                               <span class="vam">话题 &nbsp;</span>
                               <label class="vam">
                                 <span id="topicspan">${reply.topicTitle}</span>
                           		 <a href="javascript:selectTopic()" class="stdbtn btn_orange">选择话题</a>
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
                                <th class="head1 center">话题</th>
                                <th class="head0 center">创建者</th>
                                <%--<th class="head0 center">父级回复ID</th>--%>
                                <th class="head1 center">内容</th>
                                <th class="head1 center">点赞数</th>
                                <th class="head1 center">回复数</th>
                                <th class="head0 center">创建时间</th>
                                <th class="head1 center">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<c:if test="${replyList!=null&&replyList.size()>0 }">
                            <c:forEach items="${replyList}" var="reply" varStatus="index">
                            <tr>
                                <td>${index.index+1}</td>
                                <td>${reply.topicTitle}</td> 
                                <td>${reply.userName}</td> 
                                <%--<td>${reply.replyId}</td> --%>
                                <td>${reply.content}</td> 
                                <td>${reply.praiseNum}</td> 
                                <td>${reply.replyNum}</td> 
                                <td><fmt:formatDate type="both" value="${reply.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td class="center">
                                    <a href="javascript:void(0)" onclick="deleteReply(${reply.id})" class="stdbtn" title="删除">删除</a>
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