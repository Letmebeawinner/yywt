<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<%@ include file="/popUpBase.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>选择讲师</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">

        function searchForm(){
            jQuery("#searchForm").submit();
        }

        function emptyForm(){
            jQuery("input:checkbox").attr("checked",false);
            jQuery("input:text").val("");
        }


        var teacherIds="";
        var teacherNames="";
        function tijiao(){
            var qstChecked = jQuery(".questionIds:checked");
            if (qstChecked.length == 0) {
                jAlert('请选择讲师','提示',function() {});
                return;
            }
            qstChecked.each(function() {
                var teacherIdAndName=jQuery(this).val();
                teacherIds+=teacherIdAndName.split("-")[0]+",";
                teacherNames+=teacherIdAndName.split("-")[1]+","
            });
            opener.addToPeopleId(teacherIds,teacherNames);
            window.close();
        }
    </script>
</head>
<body>
<div class="tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">讲师列表</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/jiaowu/teacher/teacherListForSelect.json" method="get">
                    <div class="disIb ml20 mb10">
                        <span class="vam">姓名 &nbsp;</span>
                        <label class="vam">
                            <input id="name" style="width: auto;" name="teacher.name" type="text" class="hasDatepicker" value="${teacher.name}" placeholder="请输入姓名">
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="javascript: void(0)" onclick="tijiao()" class="stdbtn ml10">确定</a>
                </div>
            </div>
        </div>
        <!-- 搜索条件，结束 -->

        <!-- 数据显示列表，开始 -->
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <colgroup>
                    <col class="con0"/>
                    <col class="con1" />
                    <col class="con0"/>
                    <col class="con1" />
                    <col class="con0"/>
                    <col class="con1" />
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">ID</th>
                    <th class="head0 center">姓名</th>
                    <th class="head0 center">教研部</th>
                    <th class="head1">性别</th>
                    <th class="head1">电话</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${teacherList!=null&&teacherList.size()>0 }">
                    <c:forEach items="${teacherList}" var="teacher">
                        <tr>
                            <td><input type="checkbox" value="${teacher.id}-${teacher.name}" class="questionIds" name="teacherId"/>${teachingComment.id }</td>
                            <td>${teacher.name}</td>
                            <td>
                                <c:if test="${teacher.teachingResearchDepartment==1}">党史党建教研部</c:if>
                                <c:if test="${teacher.teachingResearchDepartment==2}">公共管理教研部</c:if>
                                <c:if test="${teacher.teachingResearchDepartment==3}">经济学教研部</c:if>
                                <c:if test="${teacher.teachingResearchDepartment==4}">法学教研部</c:if>
                                <c:if test="${teacher.teachingResearchDepartment==5}">文化与社会发展教研部</c:if>
                                <c:if test="${teacher.teachingResearchDepartment==6}">马列主义理论教研部</c:if>
                                <c:if test="${teacher.teachingResearchDepartment==7}">统一战线理论教研部</c:if>
                            </td>
                            <td>
                                <c:if test="${teacher.sex==0}">男</c:if>
                                <c:if test="${teacher.sex==1}">女</c:if>
                            </td>
                            <td>${teacher.mobile}</td>
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