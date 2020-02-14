<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>配置信息</title>
    <script type="text/javascript" src="/static/js/profile.js"></script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader" style="margin-left: 20px">
        <h1 class="pagetitle">配置信息</h1>
        <span>
            <span style="color:red">说明</span><br>
            1：本页面用于配置信息管理；<br>
            2：添加配置：点击<span style="color: red;">添加配置</span>跳转添加页面添加配置信息；<br>
            3：删除配置：点击<span style="color: red;">删除配置</span>删除该配置；<br>
            4：修改配置：点击<span style="color: red;">修改配置</span>跳转修改页面修改配置信息；<br>
		</span>
    </div>
    <div id="contentwrapper" class="contentwrapper">
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
                    <th class="head1 center">配置名称</th>
                    <th class="head0 center">配置关键字</th>
                    <th class="head1 center">创建时间</th>
                    <th class="head0 center">更新时间</th>
                    <th class="head1 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody id="roleData">
                <c:forEach items="${profileList}" var="profile" begin="0" end="0">
                    <tr id="profile_${profile.id}">
                        <td class="center">${profile.configName}</td>
                        <td class="center">${profile.configKey}</td>
                        <td class="center">${profile.createTime}</td>
                        <td class="center">${profile.updateTime}</td>
                        <td class="center">
                            <a class="stdbtn" title="编辑" href="/admin/sms/profile/toAddOrUpdateProfile.json?id=${profile.id}"><span>编辑</span></a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="deleteProfile(${profile.id});">删除</a>
                        </td>
                    </tr>
                </c:forEach>
                <tr >
                    <%--<td class="center" colspan="5"><a href="${ctx}/admin/sms/profile/toAddOrUpdateProfile.json" class="stdbtn" title="添加配置">添加配置</a></td>--%>
                </tr>
                </tbody>
            </table>
        </div>

        <!-- 数据显示列表，结束 -->
    </div>
</div>
</body>
</html>


