<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<div id="contentwrapper" class="contentwrapper">
    <div class="overviewhead clearfix mb10">
        <div class="fl mt5">
            <form class="disIb" id="getEmployeeList" action="${ctx}/admin/oa/ajax/selectDepartmentList.json" method="get">
                <div class="disIb ml20 mb10">
                    <span class="vam">用户名 &nbsp;</span>
                    <label class="vam">
                        <input type="text" class="hasDatepicker" placeholder="输入用户名称" name="username" id="username" value="${username}">
                    </label>
                </div>
            </form>
            <div class="disIb ml20 mb10">
                <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchEmployeeList();">搜 索</a>
                <a href="javascript: void(0)" class="stdbtn ml10" onclick="emptyForm()">重 置</a>
            </div>
        </div>
    </div>
    <div class="pr">
        <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
            <colgroup>
                <col class="con0" style="width:10%;"/>
                <col class="con1"/>
                <col class="con0" style="width:20%;"/>
            </colgroup>
            <thead>
            <tr>
                <th class="head0 center">ID</th>
                <th class="head0 center">用户名</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${sysUsers}" var="user">
                <tr>
                    <td><input type="checkbox" name="sysUserId" value="${user.id}" onclick="radioClick()">${user.id}</td>
                    <td id="userName${user.id}">${user.userName}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<script type="text/javascript">
    /**
     * 筛选接收人
     */
    function searchEmployeeList() {
        var param = jQuery('#getEmployeeList').serialize();
        jQuery.ajax({
            url: '/admin/oa/ajax/selectDepartmentList.json',
            data: param,
            type: 'POST',
            dataType: 'html',
            success: function (result) {
                jQuery("#popup_message").html(result);
            }
        });
    }

    function emptyForm() {
        jQuery("#username").val("")
    }

</script>
