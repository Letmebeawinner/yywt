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
				title: jQuery.trim(jQuery(this).text()), // use the element's text as the event title
				sysUserId: jQuery.trim(jQuery(this).attr("id")),
                dutyId: ""
			};

			// store the Event Object in the DOM element so we can get to it later
			jQuery(this).data('eventObject', eventObject);
			// jQuery(this).data('teachingProgramCourseId',jQuery(this).attr("id"));
			
			// make the event draggable using jQuery UI
			jQuery(this).draggable({
				zIndex: 999,
				revert: true,      // will cause the event to go back to its
				revertDuration: 0  //  original position after the drag
			});
			
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
				today: 'today',
				month: 'month',
				week: 'week',
				day: 'day'
			},
            events: duties,
			editable: true,
            overlap: false,
			droppable: true, // this allows things to be dropped onto the calendar !!!
			drop: function(date, allDay) { // this function is called when something is dropped
				// retrieve the dropped element's stored Event Object;
				var originalEventObject = jQuery(this).data('eventObject');
                var copiedEventObject = jQuery.extend({}, originalEventObject);
                copiedEventObject.start = date;
                copiedEventObject.allDay = allDay;
                jQuery.ajax({
                    url: "/admin/oa/addDuty.json",
                    data: {
                        "duty.sysUserId": originalEventObject.sysUserId,
                        "beginTime": date + "",
                        },
                    dataType: "json",
                    type: "post",
                    cache: false,
                    async: true,
                    success: function(result) {
                        if (result.code == '0') {
                            //增加成功后将id返回
                            copiedEventObject.dutyId = result.data;
                            jQuery('#calendar').fullCalendar('renderEvent', copiedEventObject, true);
                            // alert(result.message);
                        } else {
                            //如果增加失败就移除掉
                            alert(result.message);
                            jQuery(this).remove();
                        }
                    },
                    error: function() {
                        alert("系统错误，请稍候再试");
                        jQuery(this).remove();
                    }
                });
				// is the "remove after drop" checkbox checked?
				
				// jQuery(this).remove();//这行会将右侧的选项移除掉
				
			},
			//点击事件
			eventClick: function(calEvent, jsEvent, view) {
                if (confirm("您确定要删除该值班人员吗")) {
                    var nowDate = new Date();
                    if (nowDate >= calEvent.start) {
                        alert("不能删除已经值过班的");
                        return;
                    }
                    jQuery.ajax({
                        url: "/admin/oa/deleteDuty.json",
                        data: {
                            "id": calEvent.dutyId,
                        },
                        dataType: "json",
                        type: "post",
                        cache: false,
                        async: true,
                        success: function(result) {
                            console.log(result.code);
                            // 删除成功就移除，失败就不移除
                            if (result.code == '0') {
                                console.log("code = 0" + result.message);
                                alert(result.message);
                                window.location.reload();
                            } else {
                                alert(result.message);
                            }
                        },
                        error: function() {
                            alert("系统错误，请稍候再试");
                        }
                    });
                }
		    },
		    //修改元素的大小
		    eventResize: function(event, delta, revertFunc) {
		    },
		    //在日历上拖动
		    eventDrop: function(event, dayDiff, revertFunc) {
                jQuery('#calendar').fullCalendar('renderEvent', event, true);
                jQuery.ajax({
                    url: "/admin/oa/updateDuty.json",
                    data: {
                         "id": event.dutyId,
                         "beginTime": event.start + "",
                    },
                    dataType: "json",
                    type: "post",
                    cache: false,
                    async: true,
                    success: function(result) {
                        if (result.code != '0') {
                            alert(result.message);
                            revertFunc();
                        }
                    },
                    error: function() {
                        revertFunc();
                    }
                });
		    }
		});

		///// SWITCHING LIST FROM 3 COLUMNS TO 2 COLUMN LIST /////
		function reposTitle() {
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

