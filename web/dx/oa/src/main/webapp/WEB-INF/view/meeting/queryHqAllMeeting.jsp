<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>会场列表</title>
</head>
<body>
<!-- 搜索条件，开始 -->
<div class="overviewhead clearfix mb10">
    <div class="fl mt5">
        <form class="disIb" id="searchMeeting" action="${ctx}/admin/oa/ajax/queryHqAllMeeting.json"
              method="post">
            <div class="disIb ml20 mb10">
                <span class="vam">会场名称 &nbsp;</span>
                <label class="vam">
                    <input type = "hidden" name = "pagination.currentPage" value = "${pagination.currentPage}" id = "currentPage">
                    <input style="width: auto;" name="name" type="text" value="${name}" placeholder="请输入会场名称">
                </label>
            </div>
        </form>
        <div class="disIb ml20 mb10">
            <a href="javascript: void(0)" onclick="searchMeeting()" class="stdbtn btn_orange">搜 索</a>
            <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
        </div>
    </div>
</div>
<!-- 搜索条件，结束 -->
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
            <col class="con0"/>
            <col class="con1"/>
        </colgroup>
        <thead>
            <tr>
                <th class="head0 center">选择</th>
                <th class="head0 center">序号</th>
                <th class="head0 center">会场名称</th>
                <th class="head0 center">移交时间</th>
                <th class="head1 center">投入时间</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach items="${meetingList}" var="meeting" varStatus="status">
            <tr>
                <td style="width: 50px">
                    <input type="radio" name="radio" value="${meeting.id}" onclick="checkMeeting(this)" class="topics">
                </td>
                <td style="width: 15%">${status.count}</td>
                <td style="width: 20%" id="meetingName_${meeting.id}">${meeting.name}</td>
                <td style="width: 250px" id="turnTime_${meeting.id}">${meeting.turnTime}</td>
                <td style="width: 250px" id="useTime_${meeting.id}">${meeting.useTime}</td>
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
    </c:if>
    <!-- 分页，结束 -->
    <script type="text/javascript">
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
//        if(currentUrl.indexOf('pagination.currentPage=')!=-1){
//            currentUrl = currentUrl.replace(/pagination.currentPage=[0-9]*/g,"pagination.currentPage="+pageNo);
//        }else{
//            currentUrl = currentUrl+"pagination.currentPage="+pageNo;
//        }
            console.log(pageNo);
            jQuery("#currentPage").val(pageNo);
//        window.location.href=currentUrl;
            searchMeeting();
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

        function searchMeeting() {
            var param = jQuery('#searchMeeting').serialize();
            jQuery.ajax({
                url: '/admin/oa/ajax/queryHqAllMeeting.json',
                data: param,
                type: 'POST',
                dataType: 'html',
                success: function (result) {
                    console.log(result);
                    jQuery("#popup_message").html(result);
                }
            });
        }

    </script>
</body>
</html>