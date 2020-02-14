<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<body>
<div class="pr">
    <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
        <colgroup>
            <col class="con0"/>
            <col class="con1"/>
            <col class="con0"/>
            <col class="con1"/>
            <col class="con0"/>
            <col class="con1"/>
            <col class="con0"/>
            <col class="con1"/>
        </colgroup>
        <thead>
        <tr>
            <th class="head0 center"></th>
            <th class="head0 center">用户名</th>
            <th class="head0 center">邮箱</th>
            <th class="head1 center">手机</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${userList}" var="user">
            <tr>
                <td style="width: 50px">
                    <input type="radio" value="${user.id}" name="user" class="users">
                </td>
                <td style="width: 20%" id="username_${user.id}">${user.userName}</td>
                <td style="width: 250px" id="email_${user.id}}">
                    <c:choose>
                        <c:when test="${user.email == null || user.email == ''}">未绑定</c:when>
                        <c:otherwise>${user.email}</c:otherwise>
                    </c:choose>
                </td>
                <td style="width: 250px" id="mobile_${user.id}">
                    <c:choose>
                        <c:when test="${user.mobile == null || user.mobile == ''}">未绑定</c:when>
                        <c:otherwise>${user.mobile}</c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<script type="text/javascript">
</script>
</body>
</html>