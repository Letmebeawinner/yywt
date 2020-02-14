<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<div id="contentwrapper" class="contentwrapper">
    <!-- 搜索form表单 -->
    <form class="disIb" id="searchReceivers" action="" method="get">
        <div class="tableoptions disIb mb10">
            <span class="vam">用户名&nbsp;</span>
            <label class="vam" for="infoContent"></label>
            <input type="hidden" name="pagination.currentPage" value="${pagination.currentPage}" id="currentPage">
            <input style="width: auto" id="infoContent" class="hasDatepicker" name="userName" value="${userName}" placeholder="用户名" type="text">
        </div>
        <div class="tableoptions disIb mb10">
            <span class="vam">用户类型&nbsp;</span>
            <label class="vam">
                <select name="userType" class="vam">
                    <option value="2">教职工</option>
                    <option value="6">物业公司</option>
                </select>
            </label>
            <script>
                var userType = "${userType}"
                if (userType !== "") {
                    jQuery("select[name='userType']").val(userType)
                }
            </script>
        </div>

        <div class="tableoptions disIb mb10">
            <span class="vam">角色类型&nbsp;</span>
            <label class="vam">
                <select name="roleId" class="vam">
                    <option value="">未选择</option>
                    <option value="92">工程主管</option>
                    <option value="32">维修人员</option>
                </select>
            </label>
            <script>
                var roleId = "${roleId}"
                if (roleId !== "") {
                    jQuery("select[name='roleId']").val(roleId)
                }
            </script>
        </div>

        <input value="${from}" name="from" hidden="hidden">
    </form>
    <div class="disIb ml20 mb10">
        <a href="javascript:" onclick="searchReceivers()" class="stdbtn btn_orange">搜 索</a>
    </div>
    <div class="fl mt5">

    </div>
    <div class="pr">
        <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
            <thead>
            <tr>
                <th class="head0 center">&nbsp;ID</th>
                <th class="head0 center">用户名</th>
                <th class="head0 center">手机号</th>
                <th class="head0 center">用户类型</th>
                <th class="head0 center">角色类型</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${userList}" var="function">
                <tr>
                    <td>
                        <input type="checkbox" name="telephoneIds" onclick="functionClicks()" value="${function.id}"
                                <c:forEach items="${pepairPeopleList}" var="pepairPeople">
                                    <c:if test="${function.id==pepairPeople.userId}">
                                        disabled="disabled"
                                    </c:if>
                                </c:forEach>
                               class="function"/>${function.id}
                    </td>
                    <td>${function.userName}</td>
                    <td>${function.mobile}</td>
                    <td>
                        <c:if test="${function.userType == 2}">
                            教职工
                        </c:if>
                        <c:if test="${function.userType == 6}">
                            物业公司
                        </c:if>
                    </td>
                    <td>${function.roleName}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <!-- 分页，开始 -->
        <c:if test="${pagination!=null && pagination.totalCount>0}">
        <div class="dataTables_info" id="dyntable_info">第 ${pagination.currentPage} 页，共 ${pagination.totalPages}
            页，共 ${pagination.totalCount} 个,每页最多显示 ${pagination.pageSize} 条记录
        </div>
        <div class="dataTables_paginate paging_full_numbers" id="dyntable_paginate">
            <c:choose>
                <c:when test="${pagination.currentPage<=1}">
                    <span class="first paginate_button paginate_button_disabled" id="dyntable_first">首页</span>
                    <span class="previous paginate_button paginate_button_disabled" id="dyntable_previous">上一页</span>
                </c:when>
                <c:when test="${pagination.currentPage>1}">
                    <span class="first paginate_button paginate_button_disabled" id="dyntable_first"
                          onclick="goPage(1)">首页</span>
                    <span class="previous paginate_button paginate_button_disabled" id="dyntable_previous"
                          onclick="goPage(${pagination.currentPage-1})">上一页</span>
                </c:when>
            </c:choose>

            <span>
                <c:forEach begin="${pagination.begin}" end="${pagination.end}" var="pageNo">
                    <c:if test="${pagination.currentPage==pageNo}">
                        <span class="paginate_active">${pageNo}</span>
                    </c:if>
                    <c:if test="${pagination.currentPage!=pageNo}">
                        <span class="paginate_button" onclick="goPage(${pageNo})">${pageNo}</span>
                    </c:if>
                </c:forEach>
            </span>
            <c:choose>
                <c:when test="${pagination.currentPage>0 && pagination.currentPage < pagination.totalPages}">
                    <span class="paginate_button" id="dyntable_next"
                          onclick="goPage(${pagination.currentPage+1})">下一页</span>
                    <span class="last paginate_button" id="dyntable_last"
                          onclick="goPage(${pagination.totalPages})">尾页</span>
                </c:when>
                <c:otherwise>
                    <span class="paginate_button" id="dyntable_next">下一页</span>
                    <span class="last paginate_button" id="dyntable_last">尾页</span>
                </c:otherwise>
            </c:choose>

            <span class="disIb ml20 left_box">
               <label class="vam" style="padding: 4px 5px;">
                <input type="text" placeholder="" id="go_to_page_no" class="hasDatepicker" style="width: 40px;">
               </label>
               <span class="last paginate_button" onclick="goNum()" id="dyntable_last">确定</span>
           </span>
        </div>
    </div>
    </c:if>
    <!-- 分页，结束 -->
</div>
<script type="text/javascript">

    function functionClicks() {
        functionsIds = "";
        jQuery('.function:checked').each(function () {
            functionsIds += jQuery(this).val() + ',';
        });
        functionsIds = functionsIds.substring(0, functionsIds.length - 1);
    }
    var currentPage = parseInt('${pagination.currentPage}');
    var totalPageSize = parseInt('${pagination.totalPages}');
    var currentUrl = '${pagination.currentUrl}';

    function goPage(pageNo) {
        if (pageNo > totalPageSize) {
            pageNo = totalPageSize;
        }
        if (pageNo <= 0) {
            pageNo = 1;
        }
        jQuery("#currentPage").val(pageNo);
        searchReceivers();
    }

    function goNum() {
        var _pageNo = document.getElementById("go_to_page_no").value;
        var reg = /^[0-9]+$/;
        if (!reg.test(_pageNo)) {
            document.getElementById("go_to_page_no").value = '';
            return;
        }
        _pageNo = parseInt(_pageNo);
        if (_pageNo <= 0) {
            _pageNo = 1;
        }
        if (_pageNo > totalPageSize) {
            _pageNo = totalPageSize;
        }
        goPage(_pageNo);
    }

    /**
     * 筛选接收人
     */
    function searchReceivers() {
        var param = jQuery('#searchReceivers').serialize();
        jQuery.ajax({
            url: '/admin/houqin/getSystemUserList.json',
            data: param,
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
