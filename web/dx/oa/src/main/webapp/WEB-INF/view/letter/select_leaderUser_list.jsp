<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<div id="contentwrapper" class="contentwrapper">
    <div class="overviewhead clearfix mb10">
        <div class="fl mt5">
            <form class="disIb" id="getEmployeeList" action="${ctx}/admin/oa/ajax/selectSysUserList.json" method="get">
                <div class="tableoptions disIb mb10">
                    <span class="vam">用户名&nbsp;</span>
                    <label class="vam" for="infoContent"></label>
                    <input type = "hidden" name = "pagination.currentPage" value = "${pagination.currentPage}" id = "currentPage">
                    <input type = "hidden" name = "type" value = "${type}" id = "type">
                    <input style="width: auto" id="infoContent" class="hasDatepicker" name="userName" value="${userName}"
                           placeholder="用户名" type="text">
                </div>
                <div class="tableoptions disIb mb10">
                    <span class="vam">查找方式&nbsp;</span>
                    <label class="vam">
                        <select name="userType" class="vam" onchange="jQuery('#currentPage').val(1);searchEmployeeList()">
                            <option value="0">部门职工</option>
                            <option value="1" <c:if test="${userType==1}">selected="selected"</c:if>>部门负责人</option>
                            <option value="2" <c:if test="${userType==2}">selected="selected"</c:if>>中层干部</option>
                            <option value="3" <c:if test="${userType==3}">selected="selected"</c:if>>中心组成员</option>
                        </select>
                    </label>
                </div>
                <c:if test="${userType==null || userType==''}">
                    <div class="tableoptions disIb mb10">
                        <span class="vam">部门&nbsp;</span>
                        <label class="vam">
                            <select name="departmentId" class="vam" onchange="jQuery('#currentPage').val(1);searchEmployeeList()">
                                <option value="">请选择</option>
                                <c:forEach var="dpt" items="${list}">
                                    <c:if test="${dpt.parentId!=0}">
                                        <option value="${dpt.id}" <c:if test="${departmentId==dpt.id}">selected="selected"</c:if>>${dpt.departmentName}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                </c:if>

                <input value="${from}" name="from" hidden="hidden">
            </form>
            <div class="disIb ml20 mb10">
                <a href="javascript:" onclick="jQuery('#currentPage').val(1);searchEmployeeList()" class="stdbtn btn_orange">搜 索</a>
            </div>
            <%--<div class="disIb ml20 mb10">--%>
                <%--<a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchEmployeeList();">搜 索</a>--%>
                <%--<a href="javascript: void(0)" class="stdbtn ml10" onclick="emptyForm()">重 置</a>--%>
            <%--</div>--%>
        </div>
    </div>
    <div class="pr">
        <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
            <colgroup>
                <col class="con0" style="width:10%;"/>
                <col class="con1" style="width:10%;"/>
                <col class="con0" style="width:10%;"/>
            </colgroup>
            <thead>
            <tr>
                <th class="head0 center">ID</th>
                <th class="head0 center">用户名</th>
                <th class="head1 center">手机</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${userList}" var="user">
                <tr>
                    <td><input type="checkbox" name="sysUserId" value="${user.id}" onclick="radioClick()">${user.id}</td>
                    <td id="userName${user.id}">${user.userName}</td>
                    <td>${user.mobile}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:if test="${pagination!=null && pagination.totalCount>0}">
        <div class="dataTables_info" id="dyntable_info">第 ${pagination.currentPage} 页，共 ${pagination.totalPages} 页，共 ${pagination.totalCount} 个,每页最多显示 ${pagination.pageSize} 条记录</div>
        <div class="dataTables_paginate paging_full_numbers" id="dyntable_paginate">
            <c:choose>
                <c:when test="${pagination.currentPage<=1}">
                    <span class="first paginate_button paginate_button_disabled" id="dyntable_first">首页</span>
                    <span class="previous paginate_button paginate_button_disabled" id="dyntable_previous">上一页</span>
                </c:when>
                <c:when test="${pagination.currentPage>1}">
                    <span class="first paginate_button paginate_button_disabled" id="dyntable_first" onclick="goPage(1)">首页</span>
                    <span class="previous paginate_button paginate_button_disabled" id="dyntable_previous"  onclick="goPage(${pagination.currentPage-1})">上一页</span>
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
                    <span class="paginate_button" id="dyntable_next" onclick="goPage(${pagination.currentPage+1})">下一页</span>
                    <span class="last paginate_button" id="dyntable_last" onclick="goPage(${pagination.totalPages})">尾页</span>
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
    </div>
</div>
<script type="text/javascript">
    var currentPage = parseInt('${pagination.currentPage}');
    var totalPageSize = parseInt('${pagination.totalPages}');
    var currentUrl='${pagination.currentUrl}';
    function goPage(pageNo){
        if(pageNo>totalPageSize){
            pageNo = totalPageSize;
        }
        if(pageNo<=0){
            pageNo = 1;
        }
//        if(currentUrl.indexOf('pagination.currentPage=')!=-1){
//            currentUrl = currentUrl.replace(/pagination.currentPage=[0-9]*/g,"pagination.currentPage="+pageNo);
//        }else{
//            currentUrl = currentUrl+"pagination.currentPage="+pageNo;
//        }
        jQuery("#currentPage").val(pageNo);
//        window.location.href=currentUrl;
        searchEmployeeList();
    }

    function goNum(){
        var _pageNo = document.getElementById("go_to_page_no").value;
        var reg = /^[0-9]+$/;
        if(!reg.test(_pageNo)){
            document.getElementById("go_to_page_no").value='';
            return;
        }
        _pageNo = parseInt(_pageNo);
        if(_pageNo<=0){
            _pageNo=1;
        }
        if(_pageNo>totalPageSize){
            _pageNo = totalPageSize;
        }
        goPage(_pageNo);
    }
</script>
<script type="text/javascript">
    /**
     * 筛选接收人
     */
    function searchEmployeeList() {
        var param = jQuery('#getEmployeeList').serialize();
        jQuery.ajax({
            url: '/admin/oa/ajax/selectSysUserList.json',
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

    function changeType(type) {
        if(type==0){
            $("#departmentId").val("");
        }
    }
</script>
