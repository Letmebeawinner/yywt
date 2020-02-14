
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>邮件发送列表</title>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">邮件发送列表</h1>
        <span>
            <span style="color: red;">说明</span><br>
            1. 本页面用来展示系统或管理员发送过的邮件历史记录；<br>
            2. 本页面包括按条件查询邮件、查看发送的邮件的详情以及删除邮件记录等操作；<br>
            3. 查询邮件：在搜索框中输入邮件标题或内容的一部分文字，点击<span style="color: red">搜索</span>按钮即可查询；<br>
            4. 查看邮件：点击操作列中的<span style="color: red">查看</span>按钮，
                即可查看该邮件的详细信息。包括邮件标题、邮件正文、发送人、接收人、发送时间等；<br>
            5. 删除邮件：点击操作列中的<span style="color: red">删除</span>按钮，
                即可删除指定的邮件。<span style="color: red">删除的邮件不可恢复，请谨慎操作</span> 。
        </span>
    </div>

    <div id="contentwrapper" class="contentwrapper">
        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <!-- 搜索form表单 -->
                <form class="disIb" id="searchForm" action="${ctx}/admin/sms/email/queryEmailSendRecord.json"
                      method="get">
                    <div class="tableoptions disIb mb10">
                        <span class="vam">邮件内容&nbsp;</span>
                        <label class="vam" for="emailContent"></label>
                        <input id="emailContent" style="width: auto;" type="text" class="hasDatepicker"
                               name="email.content" value="${email.content}" placeholder="邮件标题或正文">
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript:" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript:" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="${ctx}/admin/sms/email/toSendEmail.json" class="stdbtn ml10">发送邮件</a>
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
                    <th class="head1 center">邮件标题</th>
                    <th class="head0 center">邮件正文</th>
                    <th class="head1 center">发送人</th>
                    <th class="head1 center">发送时间</th>
                    <th class="head1 center">备注</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody id="emailSendRecordData">
                <c:forEach items="${emailList}" var="email">
                    <tr id="emailSendRecord_${email.id}">
                        <td>${email.subject}</td>
                        <td style="width: 25%">
                            <c:choose>
                                <c:when test="${email.content.length() > 100}">${email.content.substring(0, 100)}...</c:when>
                                <c:otherwise>${email.content}</c:otherwise>
                            </c:choose>
                        <td>
                            <c:if test="${email.sendType == 1}">系统发送</c:if>
                            <c:if test="${email.sendType == 2}">${sysUsers.get(email.userId.toString())}</c:if>
                        </td>
                        <td>
                            <fmt:formatDate value="${email.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td style="width: 25%">
                            <c:choose>
                                <c:when test="${email.remarks.equals('0')}">发送成功</c:when>
                                <c:when test="${email.remarks.length() > 50}">${email.remarks.substring(0, 50)}……</c:when>
                                <c:otherwise>${email.remarks}</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <a class="stdbtn" title="编辑"
                               href="${ctx}/admin/sms/email/getEmailSendRecord.json?recordId=${email.id}">查看</a>
                            <a class="stdbtn" title="删除" href="javascript:"
                               onclick="deleteEmailRecord(${email.id})">删除</a>
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
<script charset="utf-8" type="text/javascript" src="${ctx}/static/admin/js/email/utils.js"></script>
</body>
</html>