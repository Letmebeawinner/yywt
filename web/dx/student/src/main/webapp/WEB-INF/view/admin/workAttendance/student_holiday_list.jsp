<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<%-- <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>请假列表</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
    <script type="text/javascript">
        jQuery(function () {
            var userName = "${holiday.userName}";
            jQuery("#userName").val(userName);
            var status = "${holiday.status}";
            jQuery("#status").val(status);
            jQuery("#type").val("${holiday.type}");
        });

        jQuery(function () {
            laydate.skin('molv');
            laydate({
                elem: '#startTime',
                format: 'YYYY-MM-DD'
                // istime: true
            });
            laydate({
                elem: '#endTime',
                format: 'YYYY-MM-DD'
                // istime: true
            });
        });

        function searchForm() {
            jQuery("#searchForm").submit();
        }

        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val("");
            jQuery("input:hidden").val("");
            jQuery("#classspan").html("");
        }

        function pass(id) {
            jConfirm('您确定要通过该请假吗?', '确认', function (r) {
                if (r) {
                    jQuery.ajax({
                        url: '${ctx}/admin/jiaowu/holiday/pass.json?id=' + id,
                        type: 'post',
                        dataType: 'json',
                        success: function (result) {
                            if (result.code == "0") {
                                jQuery("#operationspan" + id).html("批准");
                            } else {
                                jAlert(result.message, '提示', function () {
                                });
                            }
                        },
                        error: function (e) {
                            jAlert('操作失败,请您稍后再试.', '提示', function () {
                            });
                        }
                    });
                }
            });
        }

        function deny(id) {
            jConfirm('您确定不通过该请假吗?', '确认', function (r) {
                if (r) {
                    jQuery.ajax({
                        url: '${ctx}/admin/jiaowu/holiday/deny.json?id=' + id,
                        type: 'post',
                        dataType: 'json',
                        success: function (result) {
                            if (result.code == "0") {
                                jQuery("#operationspan" + id).html("不批准");
                            } else {
                                jAlert(result.message, '提示', function () {
                                });
                            }
                        },
                        error: function (e) {
                            jAlert('操作失败,请您稍后再试.', '提示', function () {
                            });
                        }
                    });
                }
            });
        }

        function delHoliday(id) {
            jConfirm('您确定删除该请假吗?', '确认', function (r) {
                if (r) {
                    jQuery.ajax({
                        url: '${ctx}/admin/jiaowu/holiday/delHoliday.json?id=' + id,
                        type: 'post',
                        dataType: 'json',
                        success: function (result) {
                            if (result.code == "0") {
                                jAlert(result.message, '提示', function () {
                                    window.location.reload();
                                });
                            } else {
                                jAlert(result.message, '提示', function () {
                                });
                            }
                        },
                        error: function (e) {
                            jAlert('操作失败,请您稍后再试.', '提示', function () {
                            });
                        }
                    });
                }
            });
        }

        function updateHoliday(id) {
            jConfirm('您确定修改该请假吗?', '确认', function (r) {
                if (r) {
                    window.location.href = '${ctx}/admin/jiaowu/holiday/toUpdateHoliday.json?id=' + id;
                }
            });
        }

        function selectClass() {
            window.open('${ctx}/jiaowu/class/classListForSelect.json', 'newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=1200,height=800');
        }

        function addClass(classArray) {
            jQuery("#classspan").html(classArray[1]);
            jQuery("#classId").val(classArray[0]);
            jQuery("#className").val(classArray[1]);
        }

        function holidayExport() {
            jQuery("#searchForm").prop("action", "/admin/jiaowu/holiday/holidayExport.json");
            jQuery("#searchForm").submit();
            jQuery("#searchForm").prop("action", "/admin/jiaowu/holiday/holidayList.json");
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">请假列表</h1>
        <span>
                    <span style="color:red">说明</span><br/>
					1.本页面展示请假的列表.<br/>
					2.可通过名称、状态查询对应的请假.<br/>
					3.可点击"批准"按钮,批准该请假.<br/>
					4.可点击"不批准"按钮,不批准该请假.<br/>
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <!-- 数据显示列表，开始 -->
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <colgroup>
                    <col class="con0"/>
                    <col class="con1"/>
                    <col class="con0"/>
                    <col class="con1"/>
                    <col class="con0"/>
                    <col class="con1"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">序号</th>
                    <th class="head0 center">用户类型</th>
                    <th class="head0 center">班次</th>
                    <th class="head0 center">用户</th>
                    <th class="head1 center">开始时间</th>
                    <th class="head0 center">结束时间</th>
                    <th class="head0 center">请假时长</th>
                    <th class="head0 center">请假类型</th>
                    <th class="head1 center">原因</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${holidayList!=null&&holidayList.size()>0 }">
                    <c:forEach items="${holidayList}" var="holiday" varStatus="index">
                        <tr>
                            <td>${index.index+1}</td>
                            <td>
                                <c:if test="${holiday.type==2}">
                                    讲师
                                </c:if>
                                <c:if test="${holiday.type==3}">
                                    学员
                                </c:if>
                            </td>
                            <td>${holiday.className}</td>
                            <td>${holiday.userName}</td>
                            <td><fmt:formatDate type="both" value="${holiday.beginTime}" pattern="yyyy-MM-dd"/></td>
                            <td><fmt:formatDate type="both" value="${holiday.endTime}" pattern="yyyy-MM-dd"/></td>
                            <td>${holiday.length}</td>
                            <td>${holiday.leaType}</td>
                            <td>${holiday.reason}</td>
                            <td class="center">
                                <c:if test="${holiday.status==0}">
                                    不批准
                                </c:if>
                                <c:if test="${holiday.status==1}">
                                    批准
                                </c:if>
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