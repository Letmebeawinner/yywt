<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<script type="text/javascript">
    function searchForm() {
        jQuery("#searchForm").submit();
    }

    function propertyClicks(id) {
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
                <th class="head0 center"><input type="radio"/></th>
                <th class="head0 center">资产名称</th>
                <th class="head0 center">规格型号</th>
                <th class="head0 center">计量单位</th>
                <th class="head0 center">金额</th>
                <th class="head0 center">购入时间</th>
                <th class="head0 center">管理员</th>
                <th class="head0 center">使用期限</th>
                <th class="head0 center">来源</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${propertyMessageDtoList}" var="propertyMessage">
                <tr>
                    <td><input type="radio" name="propertyId" onclick="propertyClicks(${propertyMessage.id})"
                               value="${propertyMessage.id}"
                               class="function"/>${propertyMessage.id}</td>
                    <td>${propertyMessage.name}</td>
                    <td>${propertyMessage.product}</td>
                    <td>${propertyMessage.unit}</td>
                    <td>${propertyMessage.price}</td>
                    <td><fmt:formatDate value="${propertyMessage.buyTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td>${propertyMessage.sysUserName}</td>
                    <td><fmt:formatDate value="${propertyMessage.liftTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td>${propertyMessage.propertySource}</td>
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
