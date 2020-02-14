<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>课件列表</title>
    <%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">
        jQuery(function(){
            jQuery("#teacherLibraryId").val("${courseware.teacherLibraryId}");
        });
        function searchForm(){
            jQuery("#searchForm").submit();
        }

        function emptyForm(){
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }


        function deleteCourseware(id){
            jConfirm('您确定要删除吗?','确认',function(r){
                if(r){
                    jQuery.ajax({
                        url:'${ctx}/admin/jiaowu/courseware/delCourseware.json?id='+id,
                        type:'post',
                        dataType:'json',
                        success:function (result){
                            if(result.code=="0"){
                                window.location.reload();
                            }else{
                                jAlert(result.message,'提示',function() {});
                            }
                        },
                        error:function(e){
                            jAlert('删除失败','提示',function() {});
                        }
                    });
                }
            });
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">课件列表</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示课件的列表.<br />
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/courseware/coursewareList.json" method="get">
                    <div class="disIb ml20 mb10">
                        <span class="vam">讲师专题库 &nbsp;</span>
                        <label class="vam">
                            <select name="courseware.teacherLibraryId" id="teacherLibraryId">
                                <c:if test="${teacherLibraryList!=null&&teacherLibraryList.size()>0}">
                                    <c:forEach items="${teacherLibraryList}" var="teacherLibrary">
                                        <option value="${teacherLibrary.id}">${teacherLibrary.projectName}</option>
                                    </c:forEach>
                                </c:if>
                            </select>
                        </label>
                    </div>
                </form>
                    <%--<div class="disIb ml20 mb10">
                        <span class="vam">学员ID &nbsp;</span>
                        <label class="vam">
                            <input id="studentId" style="width: auto;" name="studentId" type="text" class="hasDatepicker" value="" placeholder="请输入学员ID">
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">会议ID &nbsp;</span>
                        <label class="vam">
                            <input id="meetingId" style="width: auto;" name="meetingId" type="text" class="hasDatepicker" value="" placeholder="请输入会议ID">
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">班次ID &nbsp;</span>
                        <label class="vam">
                            <input id="classId" style="width: auto;" name="classId" type="text" class="hasDatepicker" value="${xinDe.classId}" placeholder="请输入班次ID">
                        </label>
                    </div>
                </form>--%>
                <div class="disIb ml20 mb10">
                   <%-- <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="javascript: void(0)" onclick="useExcel()" class="stdbtn btn_orange">导出Excel</a>--%>
                       <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                       <%--<a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>--%>
                    <a href="${ctx}/admin/jiaowu/courseware/toCreateCourseware.json" class="stdbtn btn_orange">新 建</a>
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
                    <th class="head0 center">序号</th>
                    <th class="head0 center">标题</th>
                    <th class="head1 center">讲师</th>
                    <th class="head0 center">地址</th>
                    <th class="head0 center">创建时间</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${coursewareList!=null&&coursewareList.size()>0 }">
                    <c:forEach items="${coursewareList}" var="courseware" varStatus="index">
                        <tr>
                            <td>${index.index+1}</td>
                            <td>${courseware.title}</td>
                            <td>${courseware.teacherName}</td>
                            <td>${courseware.url}</td>
                            <td><fmt:formatDate type="both" value="${courseware.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                            <td class="center">
                                <a href="javascript:void(0)" onclick="deleteCourseware(${courseware.id})" class="stdbtn" title="删除">删除</a>
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