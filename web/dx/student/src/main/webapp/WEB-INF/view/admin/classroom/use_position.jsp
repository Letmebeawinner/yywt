<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>教室使用情况</title>
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
            
        	var classroomId= "${courseArrange.classroomId}";
			jQuery("#classroomId").val(classroomId);
			 var startTime= "${courseArrange.startTime}";
			jQuery("#startTime").val(startTime);
            var endTime="${courseArrange.endTime}";
            jQuery("#endTime").val(endTime); 
        	
        	 
            
            /* var position= "${classroom.position}";
            jQuery("#position").val(position); */
            
        });
        function searchForm(){
            jQuery("#searchForm").submit();
        }
        
        function emptyForm(){
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }
			
        </script>
	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">教室使用情况</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示某教室的使用情况.<br />
					2.可通过开始时间和结束时间查询某段时间内的讲师使用情况.
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">

                <!-- 搜索条件，开始 -->
                <div class="overviewhead clearfix mb10">
                    <div class="fl mt5">
                        <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/classroom/usePosition.json" method="get">                            
                           <input type="hidden" id="classroomId" name="classroomId" />
                            <!-- div class="disIb ml20 mb10">
                               <span class="vam">位置 &nbsp;</span>
                               <label class="vam">
                               <input id="position" style="width: auto;" name="position" type="text" class="hasDatepicker" value="" placeholder="请输入位置">
                               </label>
                           </div> -->

                            <div class="disIb ml20 mb10">
                               <span class="vam">开始时间 &nbsp;</span>
                               <label class="vam">
                               <input id="startTime" type="text" class="width100 laydate-icon" name="startTime"/>
                               </label>
                           </div>
							<div class="disIb ml20 mb10">
                               <span class="vam">结束时间 &nbsp;</span>
                               <label class="vam">
                               <input id="endTime" type="text" class="width100 laydate-icon" name="endTime" />
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
                                <th class="head0 center">班次</th>
                                <th class="head0 center">开始时间</th>
                                <th class="head0 center">结束时间</th>
                                <th class="head1 center">讲师</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<c:if test="${courseArrangeList!=null&&courseArrangeList.size()>0 }">
                            <c:forEach items="${courseArrangeList}" var="courseArrange">
                            <tr>
                                <td>${courseArrange.className}</td>
                                                             
                                <td><fmt:formatDate type="both" value="${courseArrange.startTime}" pattern="yyyy-MM-dd HH:mm"/></td>    
                                <td><fmt:formatDate type="both" value="${courseArrange.endTime}" pattern="yyyy-MM-dd HH:mm"/></td>                             
                                <td>${courseArrange.teacherName}</td>  
                                <%-- <td class="center">
                                    <a href="${ctx}/admin/jiaowu/class/updateClass.json?id=${classes.id}" class="stdbtn" title="修改">查看使用情况</a>                                    
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