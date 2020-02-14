<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>发件箱</title>
    <script type="text/javascript">

    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab">
        <h1 class="pagetitle">发件箱</h1>
    </div>
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于发件箱；<br>
    </div>
${sendInfo}
    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="sendMessage()">发送消息</a>
                </div>
            </div>
        </div>
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <thead>

                <tr>
                    <th class="head0 center">消息内容</th>
                    <th class="head0">发送人</th>
                    <th class="head0">发送时间</th>
                    <th class="head0" style="text-align: center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${sendInfoList}" var="message">
                    <tr>
                        <td>${message.content}</td>
                        <td>${message.senderName  }</td>
                        <td>${message.sendTime}
                        </td>
                            <td style="text-align: center">
                                <a href="${ctx}/admin/oa/findSendInfoDetail.json?id=${message.id}" class="stdbtn" title="查看详情">查看详情</a>
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
</div>
</body>
</html>