<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>考勤列表</title>
    <%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">
        jQuery(function(){
            laydate.skin('molv');
            laydate({
                elem: '#workDate',
                format:'YYYY-MM-DD'
            });
            jQuery("#classTypeId").change(function(){
                var selectedClassTypeId = jQuery(this).children('option:selected').val();
                jQuery.ajax({
                    url:'${ctx}/admin/jiaowu/class/getClassListByClassType.json',
                    data:{"classTypeId":selectedClassTypeId
                    },
                    type:'post',
                    dataType:'json',
                    success:function (result){
                        if(result.code=="0"){
                            var list=result.data;
                            var classstr="<option value=0>请选择</option>";
                            if(list!=null&&list.length>0){

                                for(var i=0;i<list.length;i++){
                                    classstr+="<option value='"+list[i].id+"'>"+list[i].name+"</option>";
                                }

                            }
                            jQuery("#classId").html(classstr);
                            jQuery("#classId").val("${userCondition.classId}");
                        }else{
                            jAlert(result.message,'提示',function() {});
                        }
                    } ,
                    error:function(e){
                        jAlert('添加失败','提示',function() {});
                    }
                });
            });
            jQuery("#classTypeId").val("${userCondition.classTypeId}");
            jQuery("#classTypeId").trigger("change");
            jQuery("#morningAttendanceStatus").val("${userCondition.morningAttendanceStatus}");
            jQuery("#afternoonAttendanceStatus").val("${userCondition.afternoonAttendanceStatus}");

        });
        function searchForm(){
            jQuery("#searchForm").submit();
        }

        function emptyForm(){
            jQuery("#classTypeId").val('');
            jQuery("#classId").val('');
            jQuery("#userName").val('');
            jQuery("#morningAttendanceStatus").val('');
            jQuery("#afternoonAttendanceStatus").val('');
        }

    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">考勤列表</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示考勤列表.<br />
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/attendance/workDayDataList.json" method="get">

                    <%--<div class="disIb ml20 mb10">
                        <span class="vam">员工编号 &nbsp;</span>
                        <label class="vam">
                            <input id="perId" style="width: auto;" name="userCondition.perId" type="text" class="" value="${userCondition.perId}">
                        </label>
                    </div>--%>
                    <div class="disIb ml20 mb10">
                        <span class="vam">班型 &nbsp;</span>
                        <label class="vam">
                            <select name="userCondition.classTypeId" class="vam" id="classTypeId">
                                <option value="0">请选择</option>
                                <c:if test="${classTypeList!=null&&classTypeList.size()>0}">
                                    <c:forEach items="${classTypeList }" var="classType">
                                        <option value="${classType.id }">${classType.name}</option>
                                    </c:forEach>
                                </c:if>
                            </select>
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">班次 &nbsp;</span>
                        <label class="vam">
                            <select name="userCondition.classId" class="vam" id="classId" >
                                <option value="0">请选择</option>
                            </select>
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">名称 &nbsp;</span>
                        <label class="vam">
                            <input id="userName" style="width: auto;" name="userCondition.userName" type="text" class="" value="${userCondition.userName}">
                        </label>
                    </div>

                        <div class="disIb ml20 mb10">
                            <span class="vam">第一时间段 &nbsp;</span>
                            <label class="vam">
                                <select id="morningAttendanceStatus" class="vam" name="userCondition.morningAttendanceStatus">
                                    <option value=""></option>
                                    <option value="1">正常</option>
                                    <option value="2">迟到</option>
                                    <option value="3">早退</option>
                                    <option value="4">旷工</option>
                                </select>
                            </label>
                        </div>

                        <div class="disIb ml20 mb10">
                            <span class="vam">第二时间段 &nbsp;</span>
                            <label class="vam">
                                <select id="afternoonAttendanceStatus" name="userCondition.afternoonAttendanceStatus">
                                    <option value=""></option>
                                    <option value="1">正常</option>
                                    <option value="2">迟到</option>
                                    <option value="3">早退</option>
                                    <option value="4">旷工</option>
                                </select>
                            </label>
                        </div>
                       <div class="disIb ml20 mb10">
                            <span class="vam">日期 &nbsp;</span>
                            <label class="vam">
                                <input id="workDate" type="text" class="width100 laydate-icon" name="userCondition.workDate" value="${userCondition.workDate}"/>
                            </label>
                        </div>
                    <%--<div class="disIb ml20 mb10">
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
                    </div>--%>
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
                    <th class="head0 center">序号</th>
                    <th class="head0 center">员工编号</th>
                    <th class="head0 center">班型</th>
                    <th class="head0 center">班次</th>
                    <th class="head0 center">姓名</th>
                    <th class="head0 center">第一时间段考勤状态</th>
                    <th class="head0 center">第二时间段考勤状态</th>
                    <%--<th class="head0 center">流水号</th>--%>
                    <th class="head1 center">日期</th>
                    <%--<th class="head1 center">设备编号</th>--%>
                </tr>
                </thead>
                <tbody>
                <c:if test="${userWorkDayDataList!=null&&userWorkDayDataList.size()>0 }">
                    <c:forEach items="${userWorkDayDataList}" var="userWorkDayData" varStatus="index">
                        <tr>
                            <td>${index.index+1}</td>
                            <td>${userWorkDayData.perId}</td>
                            <td>${userWorkDayData.classTypeName}</td>
                            <td>${userWorkDayData.className}</td>
                            <td>${userWorkDayData.userName}</td>
                            <td>
                            <c:if test="${userWorkDayData.morningAttendanceStatus==1}">
                                正常
                            </c:if>
                            <c:if test="${userWorkDayData.morningAttendanceStatus==2}">
                                迟到
                            </c:if>
                            <c:if test="${userWorkDayData.morningAttendanceStatus==3}">
                                早退
                            </c:if>
                            <c:if test="${userWorkDayData.morningAttendanceStatus==4}">
                                旷工
                            </c:if>
                        </td>
                            <td>
                                <c:if test="${userWorkDayData.afternoonAttendanceStatus==1}">
                                    正常
                                </c:if>
                                <c:if test="${userWorkDayData.afternoonAttendanceStatus==2}">
                                    迟到
                                </c:if>
                                <c:if test="${userWorkDayData.afternoonAttendanceStatus==3}">
                                    早退
                                </c:if>
                                <c:if test="${userWorkDayData.afternoonAttendanceStatus==4}">
                                    旷工
                                </c:if>
                            </td>
                            <td>${userWorkDayData.workDate}</td>
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