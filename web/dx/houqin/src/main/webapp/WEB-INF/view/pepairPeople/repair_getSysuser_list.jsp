<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<script type="text/javascript">
    function functionClicks() {
        functionsIds="";
        jQuery('.function:checked').each(function () {
            functionsIds+=jQuery(this).val()+',';
        });
        functionsIds=functionsIds.substring(0,functionsIds.length-1);
    }
</script>
<div id="contentwrapper" class="contentwrapper">
    <div class="pr">
        <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
            <colgroup>
                <col class="con0" style="width:50%;"/>
                <col class="con1"/>
            </colgroup>
            <thead>
            <tr>
                <th class="head0 center">&nbsp;ID</th>
                <th class="head0 center">用户名</th>
                <th class="head0 center">手机号</th>
            </tr>
            </thead>
            <tbody>

            <c:forEach items="${sysUserList}" var="function">
                <tr>
                    <td>
                        <input type="radio" name="telephoneIds" onclick="functionClicks()" value="${function.userId}" class="function" />${function.userId}
                    </td>
                    <td>${function.userName}</td>
                    <td>${function.mobile}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <!-- 分页，开始 -->
        <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
        <!-- 分页，结束 -->
    </div>
</div>
