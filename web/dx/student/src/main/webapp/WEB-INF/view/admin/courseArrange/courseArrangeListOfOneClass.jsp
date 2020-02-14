<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<%-- <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>班次已上课记录</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
    <script type="text/javascript">
        jQuery(function(){
            var userName="${holiday.userName}";
            jQuery("#userName").val(userName);
            var status="${holiday.status}";
            jQuery("#status").val(status);
        });
        function searchForm(){
            jQuery("#searchForm").submit();
        }

        function emptyForm(){
            jQuery("input:text").val('');
            jQuery("select").val("");
            jQuery("input:hidden").val("");
            jQuery("#classspan").html("");
        }
        function pass(id){
            jConfirm('您确定要通过该请假吗?','确认',function(r){
                if(r){
                    jQuery.ajax({
                        url:'${ctx}/admin/jiaowu/holiday/pass.json?id='+id,
                        type:'post',
                        dataType:'json',
                        success:function (result){
                            if(result.code=="0"){
                                jQuery("#operationspan"+id).html("批准");
                            }else{
                                jAlert(result.message,'提示',function() {});
                            }
                        },
                        error:function(e){
                            jAlert('操作失败,请您稍后再试.','提示',function() {});
                        }
                    });
                }
            });
        }




    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">班次已上课列表</h1>
                <span>
                    <span style="color:red">说明</span><br />
					1.本页面展示班次已上课列表.<br />
					3.可点击"批准"按钮,批准该请假.<br />
					4.可点击"不批准"按钮,不批准该请假.<br />
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/courseArrange/courseArrangeListOfOneClass.json" method="get">

                </form>
                <%--<div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                </div>--%>
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
                    <th class="head0 center">课程</th>
                    <th class="head1 center">讲师</th>
                    <th class="head0 center">开始时间</th>
                    <th class="head0 center">结束时间</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${courseArrangeList!=null&&courseArrangeList.size()>0 }">
                    <c:forEach items="${courseArrangeList}" var="courseArrange">
                        <tr>
                            <td>${courseArrange.courseName}</td>
                            <td>${courseArrange.teacherName}</td>
                            <td><fmt:formatDate type="both" value="${courseArrange.startTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                            <td><fmt:formatDate type="both" value="${courseArrange.endTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                            <td class="center">
                                    <span id="operationspan${holiday.id}">
                                    <a href="${ctx}/admin/jiaowu/teachEvaluate/teachEvaluateListOfOneCourseArrange.json?courseArrangeId=${courseArrange.id}" class="stdbtn" title="查看教学质量评估结果">查看教学质量评估结果</a>
                                        <a href="${ctx}/admin/jiaowu/teachEvaluate/averageTeachEvaluateOfOneCourseArrange.json?courseArrangeId=${courseArrange.id}" class="stdbtn" title="查看任课讲师教学质量评估各项平均值">查看任课讲师教学质量评估各项平均值</a>
                                    </span>
                            </td>
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