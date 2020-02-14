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

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/holiday/holidayList.json" method="get">
                    <input type="hidden" name="classId" id="classId" value="${holiday.classId}"/>
                    <input type="hidden" name="className" id="className" value="${className}"/>
                    <%--<div class="disIb ml20 mb10">
                        <span class="vam">创建者 &nbsp;</span>
                        <label class="vam">
                            <input id="createUserName" style="width: auto;" name="createUserName" type="text" class="hasDatepicker" value="" placeholder="请输入发起人">
                        </label>
                    </div>--%>
                    <div class="disIb ml20 mb10">
                        <span class="vam">用户名 &nbsp;</span>
                        <label class="vam">
                            <input id="userName" style="width: auto;" name="userName" type="text" class="hasDatepicker"
                                   value="" placeholder="请输入用户名">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">开始时间 &nbsp;</span>
                        <label class="vam">
                            <input id="startTime" type="text" class="hasDatepicker" name="startTime" value="${startTime}" readonly/>
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">结束时间 &nbsp;</span>
                        <label class="vam">
                            <input id="endTime" type="text" class="hasDatepicker" name="endTime" value="${endTime}" readonly/>
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">状态 &nbsp;</span>
                        <label class="vam">
                            <select id="status" name="status">
                                <option value="">请选择</option>
                                <option value="2">未审核</option>
                                <option value="1">批准</option>
                                <option value="0">不批准</option>
                            </select>
                        </label>
                    </div>
                    <c:if test="${isAdministrator==true}">
                        <div class="disIb ml20 mb10">
                            <span class="vam">班级 &nbsp;</span>
                            <label class="vam">
                                <span id="classspan">${className}</span>
                                <a href="javascript:void(0)" onclick="selectClass()" class="stdbtn btn_orange">选择班次</a>
                            </label>
                        </div>
                        <div class="disIb ml20 mb10">
                            <span class="vam">用户类型 &nbsp;</span>
                            <label class="vam">
                                <select id="type" name="type">
                                    <option value="">请选择</option>
                                    <option value="2">讲师</option>
                                    <option value="3">学员</option>
                                </select>
                            </label>
                        </div>
                    </c:if>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="javascript: void(0)" onclick="holidayExport()" class="stdbtn ml10">导出Excel</a>
                </div>
            </div>
        </div>
        <!-- 搜索条件，结束 -->
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
                                <c:if test="${holiday.status==2}">
                                    <span id="operationspan${holiday.id}">
                                    <a href="javascript:void(0)" onclick="updateHoliday(${holiday.id})" class="stdbtn"
                                       title="修改">修改</a>
                                    <a href="javascript:void(0)" onclick="pass(${holiday.id})" class="stdbtn"
                                       title="批准">批准</a>
                                    <a href="javascript:void(0)" onclick="deny(${holiday.id})" class="stdbtn"
                                       title="不批准">不批准</a>
                                         <a href="javascript:void(0)" onclick="delHoliday(${holiday.id})" class="stdbtn"
                                            title="删除">删除</a>
                                    </span>
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