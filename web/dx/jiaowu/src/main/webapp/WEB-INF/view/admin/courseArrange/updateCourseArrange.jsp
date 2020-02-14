<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>排课</title>
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
        <style type="text/css">
        	div#tip {
				position: absolute;
				width: 100px;
				height: auto;
				border: 1px solid #00CC66;
			}
			.fc-event-time{
				display: none;
			}
        </style>
	</head>
	<body>
		
	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">排课</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面用于为某班次排课.右侧"待选课程"部分为一个班次的所有教学计划课程.日历上方可通过<font style="font-weight: bold">月视图、周视图、日视图</font>三中形式查看.<br>
					2.拖动待选课程到日历上,会为该班次添加一条排课记录.课程开始时间必须晚于当前时间,否则不予添加.<br>
					3.可在日历上拖动、拉长或压缩排课记录,来改变上课的时间和持续时间.<br>
					4.如果排课记录因讲师、教室等原因发生时间上的冲突,冲突的排课记录会显示黄色,表明该排课记录已被删除.<br>
					5.可点击排课记录,修改该排课记录的讲师和教室,也可以选择将该课程分享给其他符合条件的班次.<br>
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper withrightpanel">
        
        	<div id="calendar"></div>
        	<div id="tip" style="display: none">
			<p>课程名称:<span id="courseNameP"></span></p>
			<p></p>
			<p></p>
			<p></p>
			<p></p>
			<p></p>
			<p></p>
			</div>
        </div><!--contentwrapper-->
        
        <div class="rightpanel">
        	<div class="rightpanelinner">
                <div class="widgetbox" style="margin-top: 150px;">
                	<div class="title"><h4>待选课程</h4></div>
                    <div class="widgetcontent">
							<div class="disIb ml20 mb10">
								<label class="vam">
									<form id="courseForm" action="${ctx}/admin/jiaowu/courseArrange/searchTeachingProgramCourse.json">
										<input type="hidden" name="classId" value="${classId}" id="classId"/>
										<%--<input type="text" name="courseName"/>--%>
										<input id="courseName" style="width: auto;" name="courseName" type="text" class="hasDatepicker" value="${courseName}" placeholder="请输入专题名称或授课老师名">
										<input type="button" value="搜索" onclick="searchTeachingProgramCourse()"/>
									</form>

								</label>
							</div>
                    	<div id="external-events">

<!--                             <div class="external-event">My friend's birthday event</div> -->
                            <c:if test="${teachingProgramCourseList!=null&&teachingProgramCourseList.size()>0}">
                            	<c:forEach items="${teachingProgramCourseList}" var="teachingProgramCourse">
                            		<div class="external-event" id="${teachingProgramCourse.id}">${teachingProgramCourse.name }</div>
                            	</c:forEach>
                            </c:if>

                        </div>

                    </div><!--widgetcontent-->
                    <%--<div class="title"><h4>教学任务</h4></div>
                    <div>
            			${teachingTask.content}
            		</div>--%>
                </div><!--widgetbox-->
            </div><!--rightpanelinner-->
            
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
		<script type="text/javascript" src="${ctx}/static/js/custom/calendar.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/plugins/kill-ie6.js"></script>
		<script type="text/javascript">
			function searchTeachingProgramCourse(){
				window.location.href="${ctx}/admin/jiaowu/courseArrange/toUpdateCourseArrange.json?classId="+jQuery("#classId").val()+"&courseName="+jQuery("#courseName").val();
				/*jQuery.ajax({
					url:'${ctx}/admin/jiaowu/courseArrange/searchTeachingProgramCourse.json',
					data:{"classId":jQuery("#classId").val(),"courseName":jQuery("#courseName").val()},
					type:'post',
					dataType:'json',
					success:function (result){
						if(result.code=="0"){
							var teachingProgramCourseList=result.data;
							if(teachingProgramCourseList!=null&&teachingProgramCourseList.length>0){
								var str="";
								for(var i=0;i<teachingProgramCourseList.length;i++){
									str+="<div class='external-event' id='"+teachingProgramCourseList[i].id+"'>"+teachingProgramCourseList[i].courseName+"</div>";
								}
								jQuery("#external-events").html(str);
							}
						}else{
							jAlert(result.message,'提示',function() {});
						}
					} ,
					error:function(e){
						jAlert('查询失败','提示',function() {});
					}
				});*/
			}
		</script>
	</body>
</html>