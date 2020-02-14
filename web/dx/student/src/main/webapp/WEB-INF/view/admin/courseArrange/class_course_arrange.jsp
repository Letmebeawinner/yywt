<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>班级课表</title>
<%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
        <script type="text/javascript">
			$(function(){
				$(".userinfo").on("click",function(){
					if(!$(this).hasClass('active')) {
						$('.userinfodrop').show();
						$(this).addClass('active');
					} else {
						$('.userinfodrop').hide();
						$(this).removeClass('active');
					}
					$('.notification').removeClass('active');
					$('.noticontent').remove();

					return false;
				})
			})

        </script>
	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">班级课表</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面用来展示某个班次的排课记录,不可修改.<br>
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper withrightpanel">
        
        	<div id="calendar"></div>
        
        </div>
        
       
	    </div>
	    <form method="post" action="">
	    	<input type="hidden" id="classId" value="${classId}"/>
	    </form>
	    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
	    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery-ui-1.8.16.custom.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.cookie.js"></script>
		<script type='text/javascript' src='${ctx}/static/js/plugins/fullcalendar.min.js'></script>
		<script type="text/javascript" src="${ctx}/static/js/custom/general.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/custom/classCourseArrange.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/plugins/kill-ie6.js"></script>
	</body>
</html>