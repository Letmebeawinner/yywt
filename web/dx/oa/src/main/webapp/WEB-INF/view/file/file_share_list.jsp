<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>分享的文件</title>
    <script type="text/javascript" src="${ctx}/static/common/file-download.js"></script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab">
        <h1 class="pagetitle">分享的文件</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于分享的文件列表查看；<br>
        </div>
    </div>

    <div id="contentwrapper" class="contentwrapper">
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <colgroup>
                    <col class="con0" style="width:10%;"/>
                    <col class="con1"/>
                    <col class="con0" style="width:20%;"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">排序</th>
                    <th class="head0 center">文件名称</th>
                    <th class="head0 center">分享时间</th>
                    <th class="head0 center">分享人</th>
                    <th class="head0 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${fileList}" var="file" varStatus="state">
                    <tr>
                        <td>${state.index+1}</td>
                        <td>${file.title}</td>
                        <td><fmt:formatDate value="${file.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>${file.sysUserName}</td>
                        <td class="center">
                            <a href="${file.fileUrl}" class="stdbtn" title="下载">下载</a>
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