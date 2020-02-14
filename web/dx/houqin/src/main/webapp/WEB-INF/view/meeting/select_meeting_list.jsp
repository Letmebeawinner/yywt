<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<script type="text/javascript">
    function searchForm() {
        jQuery("#searchForm").submit();
    }

    function meetingClicks(id) {
        functionsIds = id;
    }
</script>
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
                <th class="head0 center">移交时间</th>
                <th class="head0 center">投入使用时间</th>
                <th class="head0 center">状态</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${meetingList}" var="meet">
                <tr>
                    <td><input type="radio" name="propertyId" onclick="meetingClicks(${meet.id})" alue="${meet.id}" class="function"/>${meet.id}</td>
                    <td>${meet.name}</td>
                    <td><fmt:formatDate value="${meet.turnTime}" pattern="yyyy-MM-dd"/></td>
                    <td><fmt:formatDate value="${meet.useTime}" pattern="yyyy-MM-dd"/></td>
                    <td>
                        <c:if test="${meet.status==0}">未使用</c:if>
                        <c:if test="${meet.status==1}"><font style="color: red">维修中</font></c:if>
                        <c:if test="${meet.status==2}">使用中</c:if>
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
