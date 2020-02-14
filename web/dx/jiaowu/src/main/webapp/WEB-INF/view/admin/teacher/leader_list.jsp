<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>班主任列表</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">
        jQuery(function () {
            var zhutiban = '${zhutiban}';
            if(zhutiban != null && zhutiban != ''){
                jQuery("#zhutiban").val(zhutiban);
            }
        })

        function searchForm() {
            jQuery("#searchForm").submit();
        }

        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val("");
        }
        function delTeacher(id) {
            if (confirm("您确定要删除吗?")) {
                jQuery.ajax({
                    url: '${ctx}/admin/jiaowu/teacher/delTeacher.json?id=' + id,
                    type: 'post',
                    dataType: 'json',
                    success: function (result) {
                        if (result.code == "0") {
                            window.location.reload();
                        } else {
                            jAlert(result.message, '提示', function () {
                            });
                        }
                    },
                    error: function (e) {
                        jAlert('删除失败', '提示', function () {
                        });
                    }
                });
            }
        }

        function cancelClassLeader(id){
            jConfirm('您确定要取消班主任吗?','确认',function(r){
                if(r){
                    jQuery.ajax({
                        url: '${ctx}/admin/jiaowu/teacher/cancelLeaderType.json?employeeId=' + id,
                        type: 'post',
                        dataType: 'json',
                        success: function (result) {
                            if (result.code == "0") {
                                window.location.reload();
                            } else {
                                jAlert(result.message, '提示', function () {
                                });
                            }
                        },
                        error: function (e) {
                            jAlert('操作失败，请稍后重试。', '提示', function () {
                            });
                        }
                    });
                }
            });
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">班主任列表</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/teacher/leaderList.json" method="get">

                    <div class="disIb ml20 mb10">
                        <span class="vam">姓名 &nbsp;</span>
                        <label class="vam">
                            <input id="name" style="width: auto;" name="teacher.name" type="text" class="hasDatepicker"
                                   value="${teacher.name}" placeholder="请输入姓名">
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">是否主体班 &nbsp;</span>
                        <label class="vam">
                            <select name="zhutiban" id="zhutiban">
                                <option value="">请选择</option>
                                <option value="1">主体班</option>
                                <option value="2">非主体班</option>
                            </select>
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
                    <th class="head0 center">序号</th>
                    <th class="head0 center">姓名</th>
                    <th class="head1">是否主体班</th>
                    <th class="head1">部门</th>
                    <th class="head1">电话</th>
                    <th class="head1">职务</th>
                    <th class="head1">专业</th>
                    <td class="head0 center">操作</td>
                </tr>
                </thead>
                <tbody>
                <c:if test="${teacherList!=null&&teacherList.size()>0 }">
                    <c:forEach items="${teacherList}" var="teacher" varStatus="index">
                        <tr>
                            <td>${index.index+1}</td>
                            <td>${teacher.name}</td>
                            <td>${teacher.isZhuTi=="1"?"主体班":"非主体班"}</td>
                            <td>${teacher.department}</td>
                            <td>${teacher.mobile}</td>
                            <td>${teacher.presentPost}</td>
                            <td>${teacher.profession}</td>
                            <td class="center">
                                <c:if test="${teacher.source==2}">
                                    <a href="${ctx}/admin/jiaowu/teacher/toUpdateTeacher.json?id=${teacher.id}" class="stdbtn" title="编辑">编辑</a>
                                </c:if>
                                <a href="javascript:void(0)" onclick="cancelClassLeader(${teacher.id})" class="stdbtn">取消班主任</a>
                                <%--<a href="javascript:void(0)" class="stdbtn" title="删除" onclick="delTeacher(${teacher.id})">删除</a>--%>
                                <%--<a href="${ctx}/admin/jiaowu/courseArrange/teacherCourseArrange.json?teacherId=${teacher.id}" class="stdbtn" title="讲师课表">班主任课表</a>--%>
                                <%--<c:if test="${teacher.type==1}">
                                    <a href="javascript:void(0)" class="stdbtn">选为班主任</a>
                                </c:if>--%>
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
</div>
</body>

</html>