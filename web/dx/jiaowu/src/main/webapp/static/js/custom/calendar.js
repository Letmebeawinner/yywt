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
    		url:'getCourseArrangeByClassId.json',
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
    						    	url:teachingProgramCourseId1,
    						    	editable:courseArrangeList[i].editable
    						    };
    						console.info("editable:"+existObject.editable);
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
	
		var myresource=[{id: 'a',title: 'Room A'},{id: 'a1',parentId: 'a',title: 'Room A1'}];
		
		/* initialize the calendar */
		jQuery('#calendar').fullCalendar({
            // slotDuration:'00:01:00',

			/*option:{
                slotDuration:'00:01:00',
                snapDuration:'00:01:00'
			},*/
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
				month: '月视图',
				week: '周视图',
				day: '日视图'
			},
            /*slotDuration:'00:01:00',
            snapDuration:'00:01:00',*/
            slotMinutes : 30,
			editable: true,
			droppable: true, // this allows things to be dropped onto the calendar !!!
			drop: function(date, allDay) { // this function is called when something is dropped
				// retrieve the dropped element's stored Event Object
				var originalEventObject = jQuery(this).data('eventObject');
				var teachingProgramCourseId=jQuery(this).data('teachingProgramCourseId');
				var now=new Date();
				if(date<now){
					jAlert('开始时间不能早于当前时间!','提示',function() {});
					return;
				}
				/*console.info("date:"+date);
				console.info("teachingProgramCourseId:"+teachingProgramCourseId);
				console.info("classId:"+classId);*/
				var courseArrangeId=0;
				jQuery.ajax({
		    		url:'addCourseArrange.json',
		    		data:{"courseArrange.teachingProgramCourseId":teachingProgramCourseId,
		    			"courseArrange.classId":classId,
		    			"beginTime":date+""},
		    		type:'post',
		    		dataType:'json',		
		    		success:function (result){
		    			var copiedEventObject = jQuery.extend({}, originalEventObject);
		    			copiedEventObject.start = date;
	    				copiedEventObject.allDay = allDay;
	    				copiedEventObject.url=teachingProgramCourseId+"";
//	    				jQuery(copiedEventObject).data('teachingProgramCourseId',teachingProgramCourseId);	    				
		    			if(result.code=="0"){
		    				courseArrangeId=result.data;		    				
		    				copiedEventObject.id=courseArrangeId;		    				
//		    				alert("courseArrangeId:"+courseArrangeId);
//		    				alert("copiedEventObject.id:"+copiedEventObject.id);
		    				
		    			}else{
//		    				alert("讲师授课时间发生冲突!");
// 		    				jAlert(result.message,'提示',function() {});
		    				copiedEventObject.id=0;
		    				copiedEventObject.color="orange";
                            jAlert(result.message,'提示',function() {});
                            return;
		    			}	
		    			jQuery('#calendar').fullCalendar('renderEvent', copiedEventObject, true);
		    		} ,
		    		error:function(e){
		    			// jAlert('添加失败','提示',function() {});
		    		}
		    	});
				// we need to copy it, so that multiple events don't have a reference to the same object
				
				// assign it the date that was reported
				
				// render the event on the calendar
				// the last `true` argument determines if the event "sticks" (http://arshaw.com/fullcalendar/docs/event_rendering/renderEvent/)
				
				/*jQuery.ajax({
		    		url:'addCourseArrange.json',
		    		data:{"teachingProgramCourseId":teachingProgramCourseId,
		    			"classId":classId,
		    			"startTime":date+""},
		    		type:'post',
		    		dataType:'json',		
		    		success:function (result){
		    			console.info(result.code);	    			
		    		}
		    	});*/
				
				// is the "remove after drop" checkbox checked?
				
//				jQuery(this).remove();//这行会将右侧的选项移除掉
				
			},
			//点击事件
			eventClick: function(calEvent, jsEvent, view) {
//				window.open('../teach/toUpdateClassroom.json?id='+calEvent.id,'newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=300,left=500,width=800,height=400');
		        if(calEvent.id!=0){
//				window.location.href='../teach/toUpdateClassroom.json?id='+calEvent.id;
		        	window.location.href='toUpdateOneCourseArrange.json?id='+calEvent.id;
		        }
		        return false;
				/*alert('Event: ' + calEvent.title);
		        alert('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);
		        alert('View: ' + view.name);*/

		        // change the border color just for fun
//		        $(this).css('border-color', 'green');

				
				/*if(confirm("您是否要移除该课程?")){
					jQuery(this).remove();
					var removeCourseArrangeId=calEvent.id;
					jQuery.ajax({
			    		url:'delCourseArrange.json',
			    		data:{"id":removeCourseArrangeId
			    			},
			    		type:'post',
			    		dataType:'json',		
			    		success:function (result){
			    			if(result.code=="0"){
			    				
			    				
			    			}else{
			    				
			    			}		    			
			    		} ,
			    		error:function(e){
			    			alert("移除失败");
			    		}
			    	});
				}*/
		    },
		    //修改元素的大小
//		    eventResize: function(event, delta, revertFunc) {
		    eventResize: function(event,dayDelta,minuteDelta,allDay,revertFunc) {	
//		    	var teachingProgramCourseId=jQuery(event).data('teachingProgramCourseId');
		    	var now=new Date();
		    	var eventStart=event.start;
				if(eventStart<now){
					// jAlert('开始时间不能早于当前时间!','提示',function() {});
					revertFunc();
					return;
				}
		    	var teachingProgramCourseId=event.url;
		    	
		    	jQuery.ajax({
		    		url:'updateCourseArrangeBeginAndEndTime.json',
		    		data:{"courseArrange.id":event.id,
		    			  "courseArrange.teachingProgramCourseId":teachingProgramCourseId,
		    			  "courseArrange.classId":classId,
		    			  "courseArrange.startTimeForJs":event.start+"",
		    			  "courseArrange.endTimeForJs":event.end+""
		    			},
		    		type:'post',
		    		dataType:'json',		
		    		success:function (result){
		    			if(result.code=="0"){
		    				//会导致修改一次后,出现a is null错误,无法再次修改,应该是某属性没有添加.
//		    				jQuery(this).remove();
//		    				var existObject={
//    						    	id:result.data,
//    						    	title:event.title,
//    						    	start:event.startTimeForJs,
//    						    	end:event.endTimeForJs,
//    						    	allDay:false,
//    						    	url:event.url
//    						    };
//    						jQuery('#calendar').fullCalendar('renderEvent', existObject, true);
		    				
    						
		    				event.id=result.data;
		    				event.color="#DF4C52";
		    				jQuery('#calendar').fullCalendar('renderEvent', event, true);
		    			}else{
		    				// jAlert(result.message,'提示',function() {});
		    				event.color="orange";
		    				jQuery('#calendar').fullCalendar('renderEvent', event, true);
		    			}		    			
		    		} ,
		    		error:function(e){
		    			// jAlert('修改失败','提示',function() {});
		    		}
		    	});
		    },
		    //在日历上拖动
//		    eventDrop: function(event, delta, revertFunc) {
		    eventDrop: function(event,dayDelta,minuteDelta,allDay,revertFunc) {	
//		    	var teachingProgramCourseId=jQuery(this).data('teachingProgramCourseId');
		    	var now=new Date();
		    	var eventStart=event.start;
				if(eventStart<now){
					// jAlert('开始时间不能早于当前时间!','提示',function() {});
					revertFunc();
					return;
				}
				console.info("eventStart:"+eventStart);
				
		    	var teachingProgramCourseId=event.url;
		    	
		    	jQuery.ajax({
		    		url:'updateCourseArrangeBeginAndEndTime.json',
		    		data:{"courseArrange.id":event.id,
		    			  "courseArrange.teachingProgramCourseId":teachingProgramCourseId,
		    			  "courseArrange.classId":classId,
		    			  "courseArrange.startTimeForJs":event.start+"",
		    			  "courseArrange.endTimeForJs":event.end+""
		    			},
		    		type:'post',
		    		dataType:'json',		
		    		success:function (result){
		    			if(result.code=="0"){
		    				//会导致修改一次后,出现a is null错误,无法再次修改,应该是某属性没有添加.
//		    				jQuery(this).remove();
//		    				var existObject={
//    						    	id:result.data,
//    						    	title:event.title,
//    						    	start:event.startTimeForJs,
//    						    	end:event.endTimeForJs,
//    						    	allDay:false,
//    						    	url:event.url
//    						    };
//    						jQuery('#calendar').fullCalendar('renderEvent', existObject, true);

		    				event.id=result.data;
		    				event.color="#DF4C52";
		    				jQuery('#calendar').fullCalendar('renderEvent', event, true);
		    			}else{
		    				// jAlert(result.message,'提示',function() {});
		    				event.id=0;
		    				event.color="orange";
		    				jQuery('#calendar').fullCalendar('renderEvent', event, true);
		    			}		    			
		    		} ,
		    		error:function(e){
		    			// jAlert('修改失败','提示',function() {});
		    		}
		    	});
		    }/*,
		    eventMouseover:function( event, jsEvent, view ) {
		    	if(event.id>0){
		    		jQuery.ajax({
			    		url:'queryCourseArrange.json',
			    		data:{"id":event.id},
			    		type:'post',
			    		dataType:'json',		
			    		success:function (result){
			    			if(result.code=="0"){
			    				console.info("x:"+jsEvent.pageX);
			    				console.info("y:"+jsEvent.pageY);
			    				console.info("id:"+result.data.id);
			    				console.info("courseId:"+result.data.courseId);
			    				console.info("coursename:"+result.data.courseName);
			    				jQuery('#tip').css({
			    					'top' : jsEvent.pageX + 'px',
			    					'left' : jsEvent.pageY + 'px'
//			    					'top' : '429px',
//			    					'left' : '656px'
			    				});
			    				console.info("1");
			    				jQuery("#courseNameP").html(result.data.courseName);
			    				console.info("2");
//			    				jQuery('#tip').fadeIn('slow');
			    				jQuery('#tip').show();
			    				console.info("3");
			    			}else{
			    				
			    			}		    			
			    		}
			    	});
		    	}
		    }*/
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

