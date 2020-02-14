<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>app更新</title>
    <style type="text/css">
        textarea {
            resize: none;
        }
    </style>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 30px">
        <h1 class="pagetitle">app更新</h1>
        <span>
            <span style="color: red;">说明</span><br>
            本页面用来保存app更新信息<br>
        </span>
    </div>

    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" id="saveAppUpdate">
                <p>
                    <label><em style="color: red;">*</em>app版本号</label>
                    <span class="field">
                        <input class="longinput" id="app.version" name="appUpdate.version"
                               placeholder="${appUpdate.version}" type="text" value="${appUpdate.version}"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>app手机类型</label>
                    <span class="field">
                        <c:choose>
                            <c:when test="${appUpdate.mobileType == null || appUpdate.mobileType == 0}">
                                <select id="app.mobileType" name="appUpdate.mobileType">
                                    <option value="1" selected="selected">Android</option>
                                    <option value="2">IOS</option>
                                </select>
                            </c:when>
                            <c:otherwise>
                                <input name="appUpdate.mobileType" value="${appUpdate.mobileType}" hidden="hidden">
                                <select id="app.mobileType" name="appUpdate.mobileType" disabled="disabled">
                                    <option value="1"
                                            <c:if test="${appUpdate.mobileType == 1}">selected="selected"</c:if>>Android</option>
                                    <option value="2"
                                            <c:if test="${appUpdate.mobileType == 2}">selected="selected"</c:if>>IOS</option>
                                </select>
                            </c:otherwise>
                        </c:choose>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>app下载链接</label>
                    <span class="field">
                        <input class="longinput" id="app.updateUrl" name="appUpdate.updateUrl"
                               placeholder="${appUpdate.updateUrl}" type="text" value="${appUpdate.updateUrl}"/>
                    </span>
                </p>
                <p>
                    <label>app更新说明</label>
                    <span class="field">
                        <textarea class="longinput" cols="80" id="app.updateInfo" name="appUpdate.updateInfo"
                                  placeholder="app更新说明" rows="5"
                                  style="resize: none;">${appUpdate.updateInfo}</textarea>
                    </span>
                </p>
                <p class="stdformbutton">
                    <button type="button" class="submit radius2" onclick="commit()">提交</button>
                    <button class="stdbtn" type="reset" >重置</button>
                </p>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctx}/static/admin/js/app.js"></script>
</body>
</html>