<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>教室列表</title>
    <script type="text/javascript">

        function searchForm() {
            jQuery("#searchForm").submit();
        }

        function emptyForm() {
            jQuery("#name").val('');
        }

    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">教室列表</h1>
        <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示教室的列表.<br/>
					2.可通过名称查询对应的教室.<br/>
					<%--3.可点击"教室课表"按钮,查看某教室的排课情况.<br/>--%>
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/classroom/classroomList.json"
                      method="get">

                    <div class="disIb ml20 mb10">
                        <span class="vam">名称 &nbsp;</span>
                        <label class="vam">
                            <input id="name" style="width: auto;" name="name" type="text" class="hasDatepicker"
                                   value="${name}" placeholder="请输入名称">
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
                    <th class="head0 center">ID</th>
                    <th class="head0 center">会场名称</th>
                    <th class="head0 center">可容纳人数</th>
                    <th class="head1">状态</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${meetingList!=null&&meetingList.size()>0 }">
                    <c:forEach items="${meetingList}" var="meet">
                        <tr>
                            <td><input type="radio" value="${meet.id}-${meet.name}" class="questionIds"
                                       name="teacherId"/>${meet.id }</td>
                            <td>${meet.name}</td>
                            <td>${meet.peopleNo}</td>
                            <td>
                                <c:if test="${meet.status==0}">未使用</c:if>
                                <c:if test="${meet.status==1}"><font style="color: red">维修中</font></c:if>
                                <c:if test="${meet.status==2}">使用中</c:if>
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