<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>消息接收列表</title>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">消息接收列表</h1>
    </div>

    <div id="contentwrapper" class="contentwrapper">
        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <!-- 搜索form表单 -->
                <form class="disIb" id="searchForm" action="${ctx}/admin/sms/info/queryInfoList.json" method="get">
                    <div class="tableoptions disIb mb10">
                        <span class="vam">消息内容&nbsp;</span>
                        <label class="vam" for="infoContent"></label>

                        <input style="width: 200px;" type="text" id="infoContent" class="hasDatepicker" name="userReceive.content" value="${userReceive.content}" placeholder="消息正文">
                    </div>

                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript:" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript:" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="${ctx}/admin/sms/info/toSendInfoAndFile.json" class="stdbtn ml10">发送消息</a>
                </div>
            </div>
        </div>
        <!-- 搜索条件，结束 -->

        <!-- 数据显示列表，开始 -->
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <colgroup>
                    <col class="con0"/>
                    <col class="con0"/>
                    <col class="con0"/>
                    <col class="con0"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">消息内容</th>
                    <th class="head1 center">发送人</th>
                    <th class="head1 center">状态</th>
                    <th class="head1 center">接收时间</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody id="infoSendRecordData">
                <c:forEach items="${infoList}" var="info">
                    <tr id="infoSendRecord_${info.id}">
                        <td style="width: 30%">
                            <c:choose>
                                <c:when test="${info.content.length() > 25}">${info.content.substring(0, 25)}...</c:when>
                                <c:otherwise>${info.content}</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${info.senderId == 0}">系统发送</c:when>
                                <c:otherwise>${senders.get(info.senderId.toString())}</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:if test="${info.status==0}"><font color="red">未读</font></c:if>
                            <c:if test="${info.status==1}"><font color="green">已读</font></c:if>
                        </td>
                        <td>
                            <fmt:formatDate value="${info.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td>
                            <a class="stdbtn" title="查看" href="javascript:" onclick="updateInfoStatus(${info.id})">查看</a>
                            <a class="stdbtn" title="删除" href="javascript:" onclick="deleteInfoReceiveRecord(${info.id})">删除</a>
                        </td>
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
<script charset="utf-8" type="text/javascript" src="${ctx}/static/admin/js/email/email.js"></script>
<script charset="utf-8" type="text/javascript" src="${ctx}/static/admin/js/info/info.js"></script>
<script charset="utf-8" type="text/javascript" src="${ctx}/static/admin/js/email/utils.js"></script>
<script type="text/javascript">
    function updateInfoStatus(id) {
        jQuery.ajax({
            data: {"id":id},
            dataType: 'json',
            url: "/admin/sms/info/updateInfoReceiveRecord.json",
            type: 'post',
            success: function (result) {
                if (result.code == '0') {
                    window.location.href="/admin/sms/info/getInfoReceiveRecord.json?id="+id;
                } else {
                    alert(result.message);
                }
            }
        });
    }
</script>
</body>
</html>