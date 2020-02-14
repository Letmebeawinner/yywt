<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>心得列表</title>
<%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
        <script type="text/javascript">
        jQuery(function(){			
            var studentId="${xinDe.studentId}";
            jQuery("#studentId").val(studentId);
            var meetingId="${xinDe.meetingId}";
            jQuery("#meetingId").val(meetingId);
        });
        function searchForm(){
            jQuery("#searchForm").submit();
        }
        
        function emptyForm(){
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }
			
        function useExcel(){
        	var totalCount="${pagination.totalCount}";
        	if(totalCount==""||totalCount=="0"){
        		jAlert("暂无数据,不可导出!",'提示',function() {});
        		return;
        	}
        	jQuery("#searchForm").prop("action", "${ctx}/admin/jiaowu/xinDe/exportExcel.json");
        	jQuery("#searchForm").submit();
        	jQuery("#searchForm").prop("action", "${ctx}/admin/jiaowu/xinDe/xinDeList.json");
        }

        function deleteXinDe(id){
        	jConfirm('您确定要删除吗?','确认',function(r){
			    if(r){
			    	jQuery.ajax({
			    		url:'${ctx}/admin/jiaowu/xinDe/delXinDe.json?id='+id,
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
                <h1 class="pagetitle">心得列表</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示心得的列表.<br />
					2.可通过学员ID、会议ID查询对应的心得.<br />
					3.可点击"查看"按钮,查看某心得具体的内容.<br />
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">

                <!-- 搜索条件，开始 -->
                <div class="overviewhead clearfix mb10">
                    <div class="fl mt5">
                        <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/xinDe/xinDeList.json" method="get">

                            <div class="disIb ml20 mb10">
                               <span class="vam">学员ID &nbsp;</span>
                               <label class="vam">
                               <input id="studentId" style="width: auto;" name="studentId" type="text" class="hasDatepicker" value="" placeholder="请输入学员ID">
                               </label>
                           </div>

                           <div class="disIb ml20 mb10">
                               <span class="vam">会议ID &nbsp;</span>
                               <label class="vam">
                               <input id="meetingId" style="width: auto;" name="meetingId" type="text" class="hasDatepicker" value="" placeholder="请输入会议ID">
                               </label>
                           </div>
                           
                           <div class="disIb ml20 mb10">
                               <span class="vam">班次ID &nbsp;</span>
                               <label class="vam">
                               <input id="classId" style="width: auto;" name="classId" type="text" class="hasDatepicker" value="${xinDe.classId}" placeholder="请输入班次ID">
                               </label>
                           </div>
                        </form>
                        <div class="disIb ml20 mb10">
                            <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                            <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                            <a href="javascript: void(0)" onclick="useExcel()" class="stdbtn btn_orange">导出Excel</a>
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
                            	<%--<th class="head0 center">班次ID</th>--%>
                            	<%--<th class="head0 center">学员ID</th>--%>
                                <th class="head0 center">学员</th>
                                <th class="head1 center">标题</th>
                                <th class="head1 center">类型</th>
                                <%--<th class="head0 center">会议ID</th> --%>
                                <th class="head0 center">会议名称</th>                                
                                <th class="head0 center">创建时间</th>
                                <th class="head1 center">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<c:if test="${xinDeList!=null&&xinDeList.size()>0 }">
                            <c:forEach items="${xinDeList}" var="xinDe" varStatus="index">
                            <tr>
                                <td>${index.index+1}</td>
                            	<%--<td>${xinDe.classId}</td>--%>
                            	<%--<td>${xinDe.studentId}</td>--%>
                                <td>${xinDe.studentName}</td>
                                <td>${xinDe.title}</td>
                                <td>
                                	${xinDe.type}
                                </td>
                                <%--<td>${xinDe.meetingId}</td>--%>
                                <td>${xinDe.meetingName}</td>                              
                                <td><fmt:formatDate type="both" value="${xinDe.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td class="center">
                                    <a href="${ctx}/admin/jiaowu/xinDe/queryXinDe.json?id=${xinDe.id}" class="stdbtn"
                                       title="查看">查看</a>
                                    <a href="javascript:void(0)" onclick="deleteXinDe(${xinDe.id})" class="stdbtn" title="删除">删除</a>
                                    <a href="${ctx}/admin/jiaowu/xinDe/evaluateList.json?tipsId=${xinDe.id}"
                                       class="stdbtn" title="评价">评价</a>
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