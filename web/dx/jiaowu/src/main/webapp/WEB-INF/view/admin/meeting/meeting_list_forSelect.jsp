<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<%@ include file="/popUpBase.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>会议列表</title>
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
            
			var name= "${meeting.name}";
			jQuery("#name").val(name);
			var startTime= "${meeting.startTime}";
			jQuery("#startTime").val(startTime);
            var endTime="${meeting.endTime}";
            jQuery("#endTime").val(endTime);
        });
        function searchForm(){
            jQuery("#searchForm").submit();
        }
        
        function emptyForm(){
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }
        
        function tijiao(){
        	var qstChecked = jQuery(".questionIds:checked");
			if (qstChecked.length == 0) {
				jAlert('请选择会议','提示',function() {});
				return;
			}
			var teacherIdAndName="";
			qstChecked.each(function() {
				teacherIdAndName=jQuery(this).val();
			});
			opener.addMeetingIdAndName(teacherIdAndName);
			window.close();
        }
			
        </script>
	</head>
	<body>
	    <div class="tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">会议列表</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面用于其它页面获取会议,以弹出框形式展示.<br />
					2.可通过名称、开始时间和结束时间查询对应的会议.<br />
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">

                <!-- 搜索条件，开始 -->
                <div class="overviewhead clearfix mb10">
                    <div class="fl mt5">
                        <form class="disIb" id="searchForm" action="${ctx}/jiaowu/meeting/meetingListForSelect.json" method="get">
                            
                           
                            <div class="disIb ml20 mb10">
                               <span class="vam">名称 &nbsp;</span>
                               <label class="vam">
                               <input id="name" style="width: auto;" name="name" type="text" class="hasDatepicker" value="" placeholder="请输入名称">
                               </label>
                           </div>

                           <div class="disIb ml20 mb10">
                               <span class="vam">开始时间 &nbsp;</span>
                               <label class="vam">
                               <input id="startTime" style="width: auto;height:35px;" name="startTime" type="text" class="hasDatepicker laydate-icon" value="" placeholder="请输入开始时间">
                               </label>
                           </div>
                           <div class="disIb ml20 mb10">
                               <span class="vam">结束时间 &nbsp;</span>
                               <label class="vam">
                               <input id="endTime" style="width: auto;height:35px;" name="endTime" type="text" class="hasDatepicker laydate-icon" value="" placeholder="请输入结束时间">
                               </label>
                           </div>
                        </form>
                        <div class="disIb ml20 mb10">
                            <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                            <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                            <a href="javascript: void(0)" onclick="tijiao()" class="stdbtn btn_orange">确定</a>
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
                                <th class="head0 center">名称</th>                                
                                <th class="head0 center">开始时间</th>
                                <th class="head0 center">结束时间</th>
                                <th class="head0 center">备注</th>
<!--                                 <th class="head1 center">操作</th> -->
                            </tr>
                        </thead>
                        <tbody>
                        	<c:if test="${meetingList!=null&&meetingList.size()>0 }">
                            <c:forEach items="${meetingList}" var="meeting">
                            <tr>
                            	<td><input name="meetingId" type="radio" value="${meeting.id}-${meeting.name}" class="questionIds" />${meeting.id }</td>
                                <td>${meeting.name}</td>                              
                                <td><fmt:formatDate type="both" value="${meeting.startTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td><fmt:formatDate type="both" value="${meeting.endTime}" pattern="yyyy-MM-dd HH:mm"/></td>                                
                                <td>${meeting.note}</td>       
<!--                                 <td class="center"> -->
<%--                                     <a href="${ctx}/admin/jiaowu/class/updateClass.json?id=${classes.id}" class="stdbtn" title="修改">修改</a> --%>
<!--                                 </td> -->
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