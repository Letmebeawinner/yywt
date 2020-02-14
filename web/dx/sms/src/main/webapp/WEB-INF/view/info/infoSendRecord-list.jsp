<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>消息发送列表</title>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">消息发送列表</h1>
        <span>
            <span style="color: red;">说明</span><br>
            1. 本页面用来展示系统或管理员发送过的消息历史记录；<br>
            2. 本页面包括按条件查询消息、查看发送的消息的详情以及删除消息记录等操作；<br>
            3. 查询消息：在搜索框中输入消息内容的部分文字，点击<span style="color: red">搜索</span>按钮即可查询；<br>
            4. 查看消息：点击操作列中的<span style="color: red">查看</span>按钮，
                即可查看该消息的详细信息。包括消息正文、发送人、接收人、发送时间等；<br>
            5. 删除消息：点击操作列中的<span style="color: red">删除</span>按钮，
                即可删除指定的消息。<span style="color: red">删除的消息不可恢复，请谨慎操作</span> 。
        </span>
    </div>

    <div id="contentwrapper" class="contentwrapper">
        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <!-- 搜索form表单 -->
                <form class="disIb" id="searchForm" action="${ctx}/admin/sms/info/queryInfoSendRecord.json"
                      method="get">
                    <div class="tableoptions disIb mb10">
                        <span class="vam">消息内容&nbsp;</span>
                        <label class="vam" for="infoContent"></label>
                        <input style="width: 300px" type="text" id="infoContent" class="hasDatepicker"
                               name="infoRecord.content" value="${infoRecord.content}" placeholder="消息内容">
                    </div>
                    <c:if test="${from==1}">
                        <div class="tableoptions disIb mb10">
                            <span class="vam">消息类型&nbsp;</span>
                            <label class="vam">
                                <select name="infoRecord.infoType" class="vam" id="infoType">
                                    <option value="-1">全部</option>
                                    <option value="0" <c:if test="${infoRecord.infoType == 0}"> selected="selected"</c:if>>
                                        系统发送部分人
                                    </option>
                                    <option value="1" <c:if test="${infoRecord.infoType == 1}"> selected="selected"</c:if>>
                                        系统发送所有人
                                    </option>
                                    <option value="2" <c:if test="${infoRecord.infoType == 2}"> selected="selected"</c:if>>
                                        管理发送部分人
                                    </option>
                                    <option value="3" <c:if test="${infoRecord.infoType == 3}"> selected="selected"</c:if>>
                                        管理发送所有人
                                    </option>
                                        <%--<option value="4" <c:if test="${infoRecord.infoType == 4}"> selected="selected"</c:if>>--%>
                                        <%--用户发送部分人--%>
                                        <%--</option>--%>
                                </select>
                            </label>
                        </div>
                    </c:if>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript:" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript:" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <c:if test="${from==1}"><a href="${ctx}/admin/sms/info/toSendInfo.json" class="stdbtn ml10">发送消息</a></c:if>
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
                    <th class="head0 center">序号</th>
                    <th class="head0 center">消息内容</th>
                    <c:if test="${from==1}">
                        <th class="head1 center">发送人</th>
                        <%--<th class="head1 center">发送状态</th>--%>
                        <th class="head1 center">消息类型</th>
                    </c:if>
                    <th class="head1 center">发送时间</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody id="infoSendRecordData">
                <c:forEach items="${infoRecordList}" var="infoRecord" varStatus="index">
                    <tr id="infoSendRecord_${infoRecord.id}">
                        <td>${index.count}</td>
                        <td style="width: 30%">
                            <c:choose>
                                <c:when test="${infoRecord.content.length() > 100}">${infoRecord.content.substring(0, 100)}</c:when>
                                <c:otherwise>${infoRecord.content}</c:otherwise>
                            </c:choose>
                        </td>
                        <c:if test="${from==1}">
                            <td>
                                <c:choose>
                                    <c:when test="${infoRecord.senderId == 0}">系统发送</c:when>
                                    <c:otherwise>${senders.get(infoRecord.senderId.toString())}</c:otherwise>
                                </c:choose>
                            </td>
                        <%--<td>--%>
                            <%--<c:if test="${infoRecord.status==0}"><font color="red">未读</font></c:if>--%>
                            <%--<c:if test="${infoRecord.status==1}"><font color="green">已读</font></c:if>--%>
                        <%--</td>--%>
                            <td>
                                <c:choose>
                                    <c:when test="${infoRecord.infoType == 0}">系统发送部分人</c:when>
                                    <c:when test="${infoRecord.infoType == 1}">系统发送所有人</c:when>
                                    <c:when test="${infoRecord.infoType == 2}">管理发送部分人</c:when>
                                    <c:otherwise>管理发送所有人</c:otherwise>
                                </c:choose>
                            </td>
                        </c:if>
                        <td>
                            <fmt:formatDate value="${infoRecord.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td>
                            <a class="stdbtn" title="查看"
                               href="${ctx}/admin/sms/info/getInfoSendRecordById.json?recordId=${infoRecord.id}">查看</a>
                            <a class="stdbtn" title="删除" href="javascript:"
                               onclick="deleteInfoRecord(${infoRecord.id})">删除</a>
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
</body>
</html>