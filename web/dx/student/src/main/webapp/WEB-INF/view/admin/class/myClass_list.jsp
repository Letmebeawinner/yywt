<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>班级列表</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">
        jQuery(function () {

        });
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">班级列表</h1>
        <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示班级的列表.<br/>
                </span>
    </div><!--pageheader-->
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
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">班型ID</th>
                    <th class="head0 center">班次ID</th>
                    <th class="head0 center">班型</th>
                    <th class="head0 center" width="12%">名称</th>
                    <th class="head1 center">班主任</th>
                    <th class="head1 center">副班主任</th>
                    <th class="head0 center">已报到人数</th>
                    <th class="head0 center">开始时间</th>
                    <th class="head0 center">结束时间</th>
                    <c:if test="${unit==null||unit==false}">
                        <th class="head1 center">操作</th>
                    </c:if>
                </tr>
                </thead>
                <tbody>
                <c:if test="${classList!=null&&classList.size()>0 }">
                    <c:forEach items="${classList}" var="classes">
                        <tr>
                            <td>${classes.classTypeId}</td>
                            <td>${classes.id }</td>
                            <td>${classes.classType}</td>
                            <td>${classes.name}</td>
                            <td>${classes.teacherName}</td>
                            <td>${classes.deputyTeacherName}</td>
                            <td>${classes.studentTotalNum}</td>
                            <td><fmt:formatDate type="both" value="${classes.startTime}"
                                                pattern="yyyy-MM-dd HH:mm"/></td>
                            <td><fmt:formatDate type="both" value="${classes.endTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                            <td class="center">
                                <a href="${ctx}/admin/jiaowu/user/userListOfCurrentTeacherByClassId.json?classId=${classes.id}"
                                   class="stdbtn" title="查看本班学员">查看本班学员</a>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
            <!-- 分页，开始 -->
            <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
            <!-- 分页，结束 -->
        </div>
        <!-- 数据显示列表，结束 -->
    </div>
</body>
</html>