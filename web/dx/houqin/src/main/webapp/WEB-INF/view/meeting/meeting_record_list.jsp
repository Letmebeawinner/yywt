<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>会场使用记录</title>
    <script type="text/javascript">
        function searchForm() {
            jQuery("#searchForm").submit();
        }

        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        function cancelMeetingUse(id,meetingId) {
            if (confirm("确定取消这个会场的使用吗？")) {
                jQuery.ajax({
                    url: "${ctx}/admin/houqin/cancelMeetingUse.json",
                    data: {"id": id,"meetingId":meetingId},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code == 0) {
                            alert(result.message);
                            window.location.href = "/admin/houqin/queryMeetingRecord.json?meetingId="+meetingId;
                        } else {
                            alert(result.message);
                            return;
                        }
                    }
                });
            }
        }

    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab">
        <h1 class="pagetitle"><b>${meeting.name}:</b>会场预约记录</h1>
    </div>
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
                    <th class="head0 center">标题</th>
                    <th class="head0 center">使用人</th>
                    <th class="head0 center">所用班级</th>
                    <th class="head0 center">使用开始时间</th>
                    <th class="head0 center">使用结束时间</th>
                    <th class="head0 center">使用状态</th>
                    <th class="head0 center">备注说明</th>
                    <th class="head0 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${meetingRecordList}" var="meet" varStatus="index">
                    <tr>
                        <td>${index.count}</td>
                        <td>${meet.caption}</td>
                        <td>${meet.userName}</td>
                        <td>${meet.classesId}</td>
                        <td><fmt:formatDate value="${meet.useTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td><fmt:formatDate value="${meet.turnTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>
                            <c:if test="${meet.status==0}">正常</c:if>
                            <c:if test="${meet.status==1}">已取消</c:if>
                        </td>
                        <td>${meet.description}</td>
                        <td>
                            <c:if test="${meet.status==0}">
                                <a href="javascript:void(0)" class="stdbtn" title="取消使用" onclick="cancelMeetingUse(${meet.id},${meet.meetingId})">取消使用</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- 分页，开始 -->
            <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
            <!-- 分页，结束 -->
        </div>
        <!-- 数据显示列表，结束 -->
    </div>
</div>
</body>
</html>