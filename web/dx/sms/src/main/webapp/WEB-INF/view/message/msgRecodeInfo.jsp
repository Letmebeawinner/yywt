<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>查看短信</title>
    <script type="text/javascript" src="${ctx}/static/js/message.js"></script>
    <script charset="utf-8" type="text/javascript" src="${ctx}/static/admin/js/email/utils.js"></script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 20px">
        <h1 class="pagetitle">查看短信详情</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" onsubmit="return false;" id="addUser">
                <p>
                    <label>接收人</label>
                    <span class="field">${smssendrecord.mobiles}&nbsp;</span>
                </p>
                <p>
                    <label>短信内容</label>
                    <span class="field">${smssendrecord.context}&nbsp;</span>
                </p>
                <p>
                    <label>发送类型</label>
                    <span class="field">
                        <c:if test="${smssendrecord.sendType==1}">
                            系统发送
                        </c:if>
                        <c:if test="${smssendrecord.sendType==2}">
                            管理员发送
                        </c:if>
                        <c:if test="${smssendrecord.sendType==3}">
                            用户对发
                        </c:if>
                        &nbsp;
                    </span>
                </p>
                <p>
                    <label>发送人</label>
                    <span class="field">${map.get(smssendrecord.userId.toString())}&nbsp;</span>
                </p>
                <p>
                    <label>发送时间</label>
                    <span class="field"><fmt:formatDate value="${smssendrecord.createTime}" pattern="yyyy-MM-dd"/>&nbsp;</span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <a  class="stdbtn ml10" href="javascript:comeback();">返回</a>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
</html>