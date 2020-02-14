<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<body onload="demo()">
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
        <c:if test="${userType==2}">
        <thead>
        <tr>
            <th class="head0 center">ID</th>
            <th class="head0 center">姓名</th>
            <th class="head0 center">性别</th>
            <th class="head0 center">身份证号</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${teacherStudentList}" var="user">
            <tr >
                <td style="width: 100px"><input  name="admin" type="radio" value="${user.id}">${user.id}</td>
                <td style="width: 100px" id="administratorName_${user.id}">${user.name}</td>
                <c:if test="${user.sex==0}">
                    <td style="width: 100px" >男</td>
                </c:if>
                <c:if test="${user.sex==1}">
                    <td style="width: 100px" >女</td>
                </c:if>
                <td style="width: 250px" >${user.identityCard}</td>
            </tr>
        </c:forEach>
        </tbody>
        </c:if>
        <c:if test="${userType==3}">
            <thead>
            <tr>
                <th class="head0 center">ID</th>
                <th class="head0 center">姓名</th>
                <th class="head0 center">性别</th>
                <th class="head0 center">身份证号</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${teacherStudentList}" var="user">
                <tr >
                    <td style="width: 100px"><input  name="admin" type="radio" value="${user.id}">${user.id}</td>
                    <td style="width: 100px" id="administratorName_${user.id}">${user.name}</td>
                    <td style="width: 100px" >${user.sex}</td>
                    <td style="width: 250px" >${user.idNumber}</td>
                </tr>
            </c:forEach>
            </tbody>
        </c:if>
    </table>

    <%--分页--%>
    <div class="dataTables_paginate paging_full_numbers" id="dyntable_paginate">
        <c:choose>
            <c:when test="${_pagination.currentPage<=1}">
                <span class="first paginate_button paginate_button_disabled" id="dyntable_first">首页</span>
                <span class="previous paginate_button paginate_button_disabled" id="dyntable_previous">上一页</span>
            </c:when>
            <c:when test="${_pagination.currentPage>1}">
                <span class="first paginate_button paginate_button_disabled" id="dyntable_first"
                      onclick="goPage(1)">首页</span>
                <span class="previous paginate_button paginate_button_disabled" id="dyntable_previous"
                      onclick="goPage(${_pagination.currentPage-1})">上一页</span>
            </c:when>
        </c:choose>
        <c:choose>
            <c:when test="${_pagination.currentPage >= _pagination.totalPages}">
                <span class="next paginate_button" id="dyntable_next">下一页</span>
                <span class="last paginate_button" id="dyntable_last">尾页</span>
            </c:when>
            <c:when test="${_pagination.currentPage < _pagination.totalPages}">
                <span class="next paginate_button" id="dyntable_next"
                      onclick="goPage(${_pagination.currentPage+1})">下一页</span>
                <span class="last paginate_button" id="dyntable_last"
                      onclick="goPage(${_pagination.totalPages})">尾页</span>
            </c:when>
        </c:choose>
    </div>
    <%-- 分页结束--%>
</div>
<script type="text/javascript">
    var currentUrl = '${_pagination.currentUrl}';
    var totalPageSize = parseInt(${_pagination.totalPages});
    function goPage(pageNo) {
        if (pageNo > totalPageSize) {
            pageNo = totalPageSize;
        }
        if (pageNo <= 0) {
            pageNo = 1;
        }
        if (currentUrl.indexOf('pagination.currentPage=') != -1) {
            currentUrl = currentUrl.replace(/pagination.currentPage=[0-9]*/g, "pagination.currentPage=" + pageNo);
        } else {
            currentUrl = currentUrl + "pagination.currentPage=" + pageNo;
        }
        jQuery.ajax({
            url: currentUrl,
            type: 'POST',
            dataType: 'html',
            success: function (result) {
                jQuery("#popup_message").html(result);
            }
        });
    }
</script>
</body>
</html>