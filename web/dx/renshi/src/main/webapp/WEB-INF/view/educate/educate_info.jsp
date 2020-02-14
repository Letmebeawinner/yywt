<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>培训项目详情</title>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle tac">培训项目详情</h1>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div class="q-l-content tac">
            <ul>
                <li>
                    <span>调&nbsp;训&nbsp;单&nbsp;位：</span>
                    <tt>${educate.diaoXunUnit}</tt>
                </li>
                <li>
                    <span>培&nbsp;训&nbsp;单&nbsp;位：</span>
                    <tt>${educate.trainingUnit}</tt>
                </li>
                <li>
                    <span>培训班名称：</span>
                    <tt>${educate.name}</tt>
                </li>
                <li>
                    <span>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</span>
                    <tt>${educate.employeeName}</tt>
                </li>
                <li>
                    <span>部门及职务：</span>
                    <tt>${educate.presentPost}</tt>
                </li>
            </ul>
        </div>
        <div id="basicform" class="subcontent staff" style="margin-top: 20px;">
            <table width="100%" cellspacing="0" cellpadding="0" border="0">
                <tr>
                    <td align="center" width="10%">姓&nbsp;名</td>
                    <td align="center" width="15%">${educate.employeeName}</td>
                    <td align="center" width="15%">所属部门</td>
                    <td align="center" width="15%"></td>
                    <td align="center" width="10%">职&nbsp;务</td>
                    <td align="center" width="15%">${educate.presentPost}</td>
                </tr>
                <tr>
                    <td align="center" width="10%">性&nbsp;别</td>
                    <td align="center" width="15%">
                        <c:if test="${educate.sex==0}">男</c:if>
                        <c:if test="${educate.sex==1}">女</c:if>
                    </td>
                    <td align="center" width="15%">文化程度</td>
                    <td align="center" width="15%">大学</td>
                    <td align="center" width="10%">职&nbsp;称</td>
                    <td align="center" width="15%">${educate.technical}</td>
                </tr>
                <tr>
                    <td align="center" width="10%">出生年月</td>
                    <td align="center" width="15%"><fmt:formatDate value="${educate.birthday}" type="date" dateStyle="long"/></td>
                    <td align="center" width="10%">参加工作时间</td>
                    <td align="center" width="10%"><fmt:formatDate value="${educate.workTime}" type="date" dateStyle="long"/></td>
                    <td align="center" width="10%">入党时间</td>
                    <td align="center" width="15%"><fmt:formatDate value="${educate.enterPartyTime}" type="date" dateStyle="long"/></td>
                </tr>
                <tr>
                    <td align="center" width="10%">学习时间</td>
                    <td align="center" colspan="5">自<fmt:formatDate value="${educate.beginTime}" type="date" dateStyle="long"/>至<fmt:formatDate value="${educate.endTime}" type="date" dateStyle="long"/></td>
                </tr>
                <tr>
                    <td align="center" width="10%">总学习日</td>
                    <td align="center" width="15%">${educate.studay}天</td>
                    <td align="center" width="15%">实际参加学习日</td>
                    <td align="center" width="15%">${educate.actstuday}天</td>
                    <td align="center" width="10%">缺勤天数</td>
                    <td align="center" width="15%">${educate.leavedays}天</td>
                </tr>
            </table>
        </div>
    </div>
</div>
</body>
</html>