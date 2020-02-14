<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>考勤统计</title>
    <%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">
        jQuery(function(){
            laydate.skin('molv');
            laydate({
                elem: '#startTime',
                format:'YYYY-MM-DD'
            });
            laydate({
                elem: '#endTime',
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

        });
        function searchForm(){
            jQuery("#searchForm").submit();
        }

        function emptyForm(){
            jQuery("#classTypeId").val('');
            jQuery("#classId").val('');
            jQuery("#userName").val('');
            jQuery("#startTime").val('');
            jQuery("#endTime").val('');
        }

        function exportExcel(){
            var totalCount="${pagination.totalCount}";
            if(totalCount==""||totalCount=="0"){
                jAlert("暂无数据,不可导出!",'提示',function() {});
                return;
            }
            jQuery("#searchForm").prop("action", "${ctx}/admin/jiaowu/attendance/exportWorkDayDataStatistics.json");
            jQuery("#searchForm").submit();
            jQuery("#searchForm").prop("action", "${ctx}/admin/jiaowu/attendance/workDayDataStatistics.json");
        }

    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">考勤统计</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示考勤统计.<br />
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/attendance/workDayDataStatistics.json" method="get">

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
                        <span class="vam">姓名 &nbsp;</span>
                        <label class="vam">
                            <input id="userName" style="width: auto;" name="userCondition.userName" type="text" class="" value="${userCondition.userName}">
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">开始日期 &nbsp;</span>
                        <label class="vam">
                            <input id="startTime" type="text" class="width100 laydate-icon" name="userCondition.startTime" value="<fmt:formatDate type='both' value='${userCondition.startTime}' pattern='yyyy-MM-dd'/>"/>
                        </label>
                    </div>
                        <div class="disIb ml20 mb10">
                            <span class="vam">结束日期 &nbsp;</span>
                            <label class="vam">
                                <input id="endTime" type="text" class="width100 laydate-icon" name="userCondition.endTime" value="<fmt:formatDate type='both' value='${userCondition.endTime}' pattern='yyyy-MM-dd'/>"/>
                        </label>
                        </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="javascript: void(0)" onclick="exportExcel()" class="stdbtn btn_orange">导出</a>
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
                    <th class="head0 center">班型</th>
                    <th class="head0 center">班次</th>
                    <th class="head0 center">姓名</th>
                    <th class="head0 center">正常次数</th>
                    <th class="head0 center">迟到次数</th>
                    <th class="head1 center">早退次数</th>
                    <th class="head1 center">旷工次数</th>
                    <th class="head1 center">请假次数</th>
                    <%--<th class="head1 center">设备编号</th>--%>
                </tr>
                </thead>
                <tbody>
                <c:if test="${statisticsList!=null&&statisticsList.size()>0 }">
                    <c:forEach items="${statisticsList}" var="statistics" varStatus="index">
                        <tr>
                            <td>${index.index+1}</td>
                            <td>${statistics.classTypeName}</td>
                            <td>${statistics.className}</td>
                            <td>${statistics.userName}</td>
                            <td>${statistics.normal}</td>
                            <td>${statistics.late}</td>
                            <td>${statistics.leaveEarly}</td>
                            <td>${statistics.absenteeism}</td>
                            <td>${statistics.holiday}</td>
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