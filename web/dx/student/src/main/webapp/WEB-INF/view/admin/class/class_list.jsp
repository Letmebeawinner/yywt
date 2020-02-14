<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>班次列表</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">
        jQuery(function () {
            var classTypeId = "${classes.classTypeId}";
            jQuery("#classTypeId option[value='" + classTypeId + "']").attr("selected", true);
            var teacherName = "${classes.teacherName}";
            jQuery("#teacherspan").html(teacherName);
            var classNumber = "${classes.classNumber}";
            jQuery("#classNumber").val(classNumber);
        });

        function searchForm() {
            jQuery("#searchForm").submit();
        }
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
            jQuery("#teacherId").val(0);
            jQuery("#teacherName").val("");
            jQuery("#teacherspan").html("");
        }
        function selectTeacher() {
            window.open('${ctx}/jiaowu/teacher/teacherListForSelect.json', 'newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=800,height=600');
        }
        function addToPeopleId(teacherArray) {
            jQuery("#teacherspan").html(teacherArray[1]);
            jQuery("#teacherId").val(teacherArray[0]);
            jQuery("#teacherName").val(teacherArray[1]);
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">班次列表</h1>
        <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示班次的列表.<br/>
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/class/classList.json" method="get">
                    <input type="hidden" name="teacherId" id="teacherId" value="${classes.teacherId }"/>
                    <input type="hidden" name="teacherName" id="teacherName" value="${classes.teacherName}"/>
                    <div class="tableoptions disIb mb10">
                        <span class="vam">班型 &nbsp;</span>
                        <label class="vam">
                            <select name="classTypeId" class="vam" id="classTypeId">
                                <option value="0">请选择</option>
                                <c:if test="${classTypeList!=null&&classTypeList.size()>0}">
                                    <c:forEach items="${classTypeList }" var="classType">
                                        <option value="${classType.id }">${classType.name}</option>
                                    </c:forEach>
                                </c:if>
                            </select>
                        </label>
                    </div>


                    <div class="disIb ml20 mb10">
                        <span class="vam">讲师 &nbsp;</span>
                        <label class="vam">
                            <span id="teacherspan">${courseArrange.teacherName}</span>
                            <a href="javascript:selectTeacher()" class="stdbtn btn_orange">选择讲师</a>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
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
                            <td><fmt:formatDate type="both" value="${classes.startTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                            <td><fmt:formatDate type="both" value="${classes.endTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                            <c:if test="${unit==null||unit==false}">
                                <td class="center">
                                    <a href="${ctx}/admin/jiaowu/class/toUpdateClassLeaderOfClass.json?id=${classes.id}"
                                       class="stdbtn" title="设置班主任">设置班主任</a>
                                    <a href="${ctx}/admin/jiaowu/twentyoneEvaluate/twentyoneEvaluateList.json?classId=${classes.id}"
                                       class="stdbtn" title="设置班主任">查看测评</a>
                                </td>
                            </c:if>
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