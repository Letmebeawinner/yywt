<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>会场维护记录</title>
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

        function delMeeting(id) {
            if (confirm("确定删除这个会场吗？")) {
                jQuery.ajax({
                    url: "${ctx}/admin/houqin/delMeeting.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code == 0) {
                            alert(result.message);
                            window.location.href = "/admin/houqin/queryAllMeeting.json";
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
        <h1 class="pagetitle">会场维护记录</h1>
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
                    <th class="head0 center">ID</th>
                    <th class="head0 center">会场名称</th>
                    <th class="head0 center">维护人</th>
                    <th class="head0 center">修改时间</th>
                    <th class="head0 center">设备情况</th>
                    <th class="head0 center">状态</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${meetingRepairRecordList}" var="meet" varStatus="index">
                    <tr>
                        <td>${index.count}</td>
                        <td>${meet.name}</td>
                        <td>${meet.meetingName}</td>
                        <td><fmt:formatDate value="${meet.repairTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>${meet.description}</td>
                        <td>
                            <c:if test="${meet.status==0}">在维护</c:if>
                            <c:if test="${meet.status==1}">已正常</c:if>
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