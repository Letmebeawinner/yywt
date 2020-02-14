<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>讲师课表</title>
		<style type="text/css">
			.fc-event-time{
				display: none;
			}
		</style>
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
                <h1 class="pagetitle">讲师课表</h1>
                 <span>
                    <span style="color:red">说明</span><br>
					1.本页面用来展示某个讲师的排课记录,不可修改.<br>
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">
        
        	<div id="calendar"></div>
        
        </div><!--contentwrapper-->
        
       
	    </div>
	    <form method="post" action="">
	    	<input type="hidden" id="teacherId" value="${teacherId}"/>
	    </form>
	    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
	    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery-ui-1.8.16.custom.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.cookie.js"></script>
		<script type='text/javascript' src='${ctx}/static/js/plugins/fullcalendar.min.js'></script>
		<script type="text/javascript" src="${ctx}/static/js/custom/general.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/custom/teacherCourseArrange.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/plugins/kill-ie6.js"></script>
	</body>
</html>