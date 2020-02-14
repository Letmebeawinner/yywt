/*
 * 	Additional function for calendar.html
 *	Written by ThemePixels	
 *	http://themepixels.com/
 *	
 *	Built for Amanda Premium Responsive Admin Template
 *  http://themeforest.net/category/site-templates/admin-templates
 */


jQuery(document).ready(function() {
		/* initialize the external events */
		jQuery('#external-events div.external-event').each(function() {
		
			// create an Event Object (http://arshaw.com/fullcalendar/docs/event_data/Event_Object/)
			// it doesn't need to have a start or end
			var eventObject = {
				title: jQuery.trim(jQuery(this).text()) // use the element's text as the event title
			};
//			console.info(eventObject.title);
//			console.info(jQuery(this).attr("id"));
			
			// store the Event Object in the DOM element so we can get to it later
			jQuery(this).data('eventObject', eventObject);
			jQuery(this).data('teachingProgramCourseId',jQuery(this).attr("id"));
			
			// make the event draggable using jQuery UI
			jQuery(this).draggable({
				zIndex: 999,
				revert: true,      // will cause the event to go back to its
				revertDuration: 0  //  original position after the drag
			});
			
		});
		
		var classId=jQuery("#classId").val();
	    /*var firstObject={
	    	id:3,
	    	title:'数学课',
	    	start:'2016-12-22 06:00:00',
	    	end:'2016-12-22 09:00:00',
	    	allDay:false
	    };*/
//		var existObjectArray=new Array();
		jQuery.ajax({
    		url:'ajaxClassCourseArrange.json',
    		data:{"classId":classId},
    		type:'post',
    		dataType:'json',		
    		success:function (result){
    			if(result.code=="0"){
    				var courseArrangeList=result.data;
    				if(courseArrangeList!=null&&courseArrangeList.length>0){
    					for(var i=0;i<courseArrangeList.length;i++){
    						var teachingProgramCourseId1=courseArrangeList[i].teachingProgramCourseId+"";
    						var existObject={
    						    	id:courseArrangeList[i].id,
									title:"课程:"+courseArrangeList[i].courseName+
											" 教师:"+courseArrangeList[i].teacherName+
											" 教室:"+courseArrangeList[i].classroomName+
											" 开始时间:" + courseArrangeList[i].startTimeForJs+
											" 结束时间" + courseArrangeList[i].endTimeForJs,
    						    	start:courseArrangeList[i].startTimeForJs,
    						    	end:courseArrangeList[i].endTimeForJs,
    						    	allDay:false,
    						    	url:"javascript:void(0)"
    						    };
//    						existObjectArray.push(existObject);
    						jQuery('#calendar').fullCalendar('renderEvent', existObject, true);
    					}
    				}
    			}else{
    				
    			}		    			
    		} ,
    		error:function(e){
    			// jAlert('获取排课信息失败','提示',function() {});
    		}
    	});
	
		
		/* initialize the calendar */
		jQuery('#calendar').fullCalendar({
			header: {
				left: 'month,agendaWeek,agendaDay',
				center: 'title',
				right: 'today, prev, next'
			},
			buttonText: {
				prev: '&laquo;',
				next: '&raquo;',
				prevYear: '&nbsp;&lt;&lt;&nbsp;',
				nextYear: '&nbsp;&gt;&gt;&nbsp;',
				today: '今天',
				month: '月',
				week: '周',
				day: '天'
			},
			
			editable: false,
			droppable: false, // this allows things to be dropped onto the calendar !!!
			drop: function(date, allDay) { // this function is called when something is dropped
				
				
			}
		});
		
//		jQuery('#calendar').fullCalendar('renderEvent', firstObject, true);
		
		///// SWITCHING LIST FROM 3 COLUMNS TO 2 COLUMN LIST /////
		function reposTitle() {
			console.info("reposTitle");
			if(jQuery(window).width() < 450) {
				if(!jQuery('.fc-header-title').is(':visible')) {
					if(jQuery('h3.calTitle').length == 0) {
						var m = jQuery('.fc-header-title h2').text();
						jQuery('<h3 class="calTitle">'+m+'</h3>').insertBefore('#calendar table.fc-header');
					}
				}
			} else {
				jQuery('h3.calTitle').remove();
			}
		}
		reposTitle();
		
		///// ON RESIZE WINDOW /////
		jQuery(window).resize(function(){
			reposTitle();
		});
		
});
