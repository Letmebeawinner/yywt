<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>会议室申请列表</title>
    <script type="text/javascript">
        /**
         * 执行查询
         */
        function searchForm() {
            jQuery("#searchForm").submit();
        }

        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(-1);
        }

        function delMeeting(id) {
            if (confirm("删除后将无法恢复，是否继续")) {
                jQuery.ajax({
                    url: "${ctx}/admin/oa/deleteMeetApply.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    async: false,
                    success: function (result) {
                        if (result.code=="0") {
                            window.location.reload();
                        } else {
                            alert(result.message);
                            return;
                        }
                    }
                });
            }
        }

        function checkApplyMeeting(id) {
                jQuery.ajax({
                    url: "${ctx}/admin/oa/checkMeetApply.json",
                    data: {"id": id},
                    type: "post",
                    dataType: "json",
                    async: false,
                    success: function (result) {
                        if (result.code=="0") {
                            alert("审核成功");
                            window.location.reload();
                        } else {
                            alert(result.message);
                            return;
                        }
                    }
                });
        }


    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab">
        <h1 class="pagetitle">会议室申请列表</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于显示会议申请列表；<br>
            2.查询：输入查询条件，点击<span style="color:red">搜索</span>；<br>
            3.编辑：点击<span style="color:red">编辑</span>，修改用会议申请信息；<br>
            4.删除：点击<span style="color:red">删除</span>，删除会议申请信息；<br>
            5.审核：点击<span style="color:red">审核</span>，审核会议申请信息；<br>
        </div>
    </div>

    <div id="contentwrapper" class="contentwrapper">

        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/oa/queryAllMeetApply.json" method="post">

                    <%--<div class="disIb ml20 mb10">
                        <span class="vam">ID &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="meetApply.id" type="text" class="hasDatepicker" value="${meetApply.id}" placeholder="输入id" onkeyup="value=value.replace(/[^\d]/g,'')">
                        </label>
                    </div>--%>

                    <div class="disIb ml20 mb10">
                        <span class="vam">会议名称 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="meetApply.name" type="text" class="hasDatepicker" value="${meetApply.name}" placeholder="输入名称">
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">会议状态 &nbsp;</span>
                        <label class="vam">
                           <select name="meetApply.status" style="width: 150px;" id="status">
                               <option value="">--请选择状态--</option>
                               <option value="0" <c:if test="${meetApply.status==0}">selected="selected"</c:if>>未审核</option>
                               <option value="1" <c:if test="${meetApply.status==1}">selected="selected"</c:if>>已审核</option>
                           </select>
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">会议室名称 &nbsp;</span>
                        <label class="vam">
                            <select name="meetApply.meetId" style="width: 150px;" id="name">
                                <option value="">--请选择会议室--</option>
                                <c:forEach items="${meetingList}" var="meet">
                                    <option value="${meet.id}" <c:if test="${meet.id==meetApply.meetId}">selected</c:if>>${meet.name}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchForm()">搜 索</a>
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
                    <th class="head0 center">序号</th>
                    <th class="head0 center">会议名称</th>
                    <th class="head0 center">会议室名称</th>
                    <th class="head1">开始时间</th>
                    <th class="head1">结束时间</th>
                    <th class="head1">审核状态</th>
                    <th class="head1">申请理由</th>
                    <th class="head0 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${meetingApplyList}" var="meetApply" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${meetApply.name}</td>
                        <td>${meetApply.meetingName}</td>
                        <td><fmt:formatDate value="${meetApply.applyStartDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td><fmt:formatDate value="${meetApply.applyendDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>
                            <c:if test="${meetApply.status==0}"><font style="color: red">未审核</font></c:if>
                            <c:if test="${meetApply.status==1}"><font style="color: green">已审核</font></c:if>
                        </td>
                        <td>${meetApply.context}</td>
                        <td class="center">
                            <a href="${ctx}/admin/oa/toUpdateMeetApply.json?id=${meetApply.id}" class="stdbtn" title="编辑">编辑</a>
                            <c:if test="${meetApply.status==0}">
                            <a href="javascript:void(0)" class="stdbtn" title="审核" onclick="checkApplyMeeting(${meetApply.id})">审核</a>
                            </c:if>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delMeeting(${meetApply.id})">删除</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- 分页，开始 -->
            <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
            <!-- 分页，结束 -->
        </div>
    </div>
</div>
</body>
</html>