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
            meetingId: jQuery(this).attr("id")
        };
        // store the Event Object in the DOM element so we can get to it later
        jQuery(this).data('eventObject', eventObject);
        // make the event draggable using jQuery UI
        jQuery(this).draggable({
            zIndex: 999,
            revert: true,      // will cause the event to go back to its
            revertDuration: 0  //  original position after the drag
        });

    });

    jQuery.ajax({
        url: '/admin/houqin/ajax/queryMeetingRecord.json?meetingId=' + jQuery("#meetingId").val(),
        data:  null,
        type: 'post',
        dataType: 'json',
        success:function (result){
            if(result.code=="0"){
                var recordMeetingList = result.data;
                if(recordMeetingList != null && recordMeetingList.length>0){
                    jQuery.each(recordMeetingList, function(index, value) {
                        var existObject={
                            id: value.id,
                            title: value.name,
                            start: value.startTimeGmt,
                            end: value.endTimeGmt,
                            allDay: false,
                            editable: false
                        };
                        jQuery('#calendar').fullCalendar('renderEvent', existObject, true);
                    });
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
        slotMinutes : 15,
        editable: false,
        droppable: false, // this allows things to be dropped onto the calendar !!!
        drop: function(date, allDay) {
            return;
            // this function is called when something is dropped
            // retrieve the dropped element's stored Event Object
            var originalEventObject = jQuery(this).data('eventObject');
            var meetingId = originalEventObject.meetingId;
            var now = new Date();
            if(date<now){
                // jAlert('开始时间不能早于当前时间!','提示',function() {});
                return;
            }
            saveMeetingRecord(meetingId, date, originalEventObject, allDay);
        },
        //点击事件
        eventClick: function(calEvent, jsEvent, view) {
            return;
            if(calEvent.id != 0){
                window.location.href = "/admin/houqin/ajax/toUpdateApply.json?id=" + calEvent.id;
            }
            return false;
        },
        //修改元素的大小
//		    eventResize: function(event, delta, revertFunc) {
        eventResize: function(event,dayDelta,minuteDelta,allDay,revertFunc) {
            return false;
//		    	var teachingProgramCourseId=jQuery(event).data('teachingProgramCourseId');
            var now=new Date();
            var eventStart=event.start;
            if(eventStart<now){
                // jAlert('开始时间不能早于当前时间!','提示',function() {});
                revertFunc();
                return;
            }
        },
        //在日历上拖动
//		    eventDrop: function(event, delta, revertFunc) {
        eventDrop: function(event,dayDelta,minuteDelta,allDay,revertFunc) {
            return;
//		    	var teachingProgramCourseId=jQuery(this).data('teachingProgramCourseId');
            var now = new Date();
            var eventStart = event.start;
            if( eventStart < now){
                // jAlert('开始时间不能早于当前时间!','提示',function() {});
                revertFunc();
                return;
            }
            console.info("eventStart:"+eventStart);
        }
    });

//		jQuery('#calendar').fullCalendar('renderEvent', firstObject, true);

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

    function saveMeetingRecord(meetingId, startTime, originalEventObject, allDay) {
        jQuery.ajax({
            url: "/admin/houqin/applyMeeting.json",
            data: {
                "meetingRecord.meetingId": meetingId,
                 "meetingRecord.useTime": jQuery.fullCalendar.formatDate(startTime, "yyyy-MM-dd HH:mm:ss")
            },
            type: 'post',
            dataType: 'json',
            success: function (result){
                var copiedEventObject = jQuery.extend({}, originalEventObject);
                copiedEventObject.start = startTime;
                copiedEventObject.allDay = allDay;
                if(result.code == "0"){
                    copiedEventObject.id = result.data;
                }else{
                    copiedEventObject.id=0;
                    copiedEventObject.color="orange";
                }
                jQuery('#calendar').fullCalendar('renderEvent', copiedEventObject, true);
                window.location.href = "/admin/houqin/ajax/toUpdateApply.json?id=" + result.data;
            }
        });
    }

});

