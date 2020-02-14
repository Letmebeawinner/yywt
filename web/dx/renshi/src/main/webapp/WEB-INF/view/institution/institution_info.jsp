<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>奖惩记录详情</title>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">奖惩详情</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="updateEmployee">
                <p>
                    <label>姓名</label>
                    <span class="field">${institution.employeeName}&nbsp;</span>
                </p>
                <p>
                    <label>性别</label>
                    <span class="field">
                        <c:if test="${institution.sex==0}">男</c:if>
                        <c:if test="${institution.sex==1}">女</c:if>
                        &nbsp;</span>
                </p>
                <p>
                    <label>部门及职务</label>
                    <span class="field">${institution.presentPost}&nbsp;</span>
                </p>
                <p>
                    <label>获奖名称</label>
                    <span class="field">${institution.title}&nbsp;</span>
                </p>
                <p>
                    <label>颁奖单位</label>
                    <span class="field">${institution.unit}&nbsp;</span>
                </p>
                <p>
                    <label>是否发放证书</label>
                    <span class="field">
                        <c:if test="${institution.isCertificate==0}">是</c:if>
                        <c:if test="${institution.isCertificate==1}">否</c:if>
                        &nbsp;</span>
                </p>
                <p>
                    <label>证书时间</label>
                    <span class="field"><fmt:formatDate value="${institution.certificateTime}" type="date" dateStyle="long"/>&nbsp;</span>
                </p>
                <p>
                    <label>证书照片</label>
                    <span class="field"><img src="${institution.picture}"/>&nbsp;</span>
                </p>
                <p>
                    <label>备注</label>
                    <span class="field">${institution.explains}&nbsp;</span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <input type="reset" class="reset radius2" value="返 回" onclick="javascript:history.go(-1);"/>
                </p>
            </form>
            <br/>
        </div>
    </div>
</div>
</body>
</html>