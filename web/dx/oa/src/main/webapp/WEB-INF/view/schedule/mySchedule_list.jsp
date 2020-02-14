<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>我的日程列表</title>
    <script type="text/javascript" src="${ctx}/static/js/schedule/telephone.js"></script>
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
            jQuery("select").val(0);
        }

        function delSchedule(id) {
            if (confirm("删除后将无法恢复，是否继续")) {
                jQuery.ajax({
                    url: "${ctx}/admin/oa/deleteSchedule.json",
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

        function cancelSchedule(id,type) {
                jQuery.ajax({
                    url: "${ctx}/admin/oa/cancelSchedule.json",
                    data: {"schedule.id": id,
                           "schedule.status": type },
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
    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab">
        <h1 class="pagetitle">我的日程</h1>
    </div>
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于我安排的日程列表查看；<br>
        2.查询：输入查询条件，点击<span style="color:red">搜索</span>；<br>
        3.编辑：点击<span style="color:red">编辑</span>，修改我安排的日程信息；<br>
        4.删除：点击<span style="color:red">删除</span>，删除我安排的日程信息；<br>
        5.完成：点击<span style="color:red">完成</span>，代表已完成该日程，按钮变成未完成，点击未完成，代表还未完成，同时按钮变成完成；<br>
    </div>

    <div id="contentwrapper" class="contentwrapper">

        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/oa/queryMySchedule.json" method="post">

                    <div class="disIb ml20 mb10">
                        <span class="vam">ID &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="schedule.id" type="text" class="hasDatepicker" value="${schedule.id}" placeholder="输入id" onkeyup="value=value.replace(/[^\d]/g,'')">
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
                    <th class="head0 center">ID</th>
                    <th class="head0 center">开始时间</th>
                    <th class="head0 center">日程类型</th>
                    <th class="head0 center">内容</th>
                    <th class="head0 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${scheduleList}" var="schedule">
                    <tr>
                        <td>${schedule.id}</td>
                        <td><fmt:formatDate value="${schedule.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>
                            <c:if test="${schedule.type==0}">工作事务</c:if>
                            <c:if test="${schedule.type==1}">个人事务</c:if>
                        </td>
                        <td>${schedule.context}</td>
                        <td class="center">
                            <a href="${ctx}/admin/oa/toUpdateSchedule.json?id=${schedule.id}" class="stdbtn" title="编辑">编辑</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delSchedule(${schedule.id})">删除</a>
                            <c:if test="${schedule.status==0}">
                                <a href="javascript:void(0)" class="stdbtn" title="完成" onclick="cancelSchedule(${schedule.id},'1')">完成</a>
                            </c:if>
                            <c:if test="${schedule.status==1}">
                                <a href="javascript:void(0)" class="stdbtn" title="未完成" onclick="cancelSchedule(${schedule.id},'0')">未完成</a>
                            </c:if>
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