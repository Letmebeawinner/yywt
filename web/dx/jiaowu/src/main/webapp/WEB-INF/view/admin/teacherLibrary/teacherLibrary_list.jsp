<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>讲师专题库</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">
        function searchForm() {
            jQuery("#searchForm").submit();
        }

        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        function addClass(classArray) {
            jQuery("#classspan").html(classArray[1]);
            jQuery("#classId").val(classArray[0]);
            jQuery("#className").val(classArray[1]);
        }


        function toSaveProject() {
            window.location.href = "/admin/jiaowu/teacher/toSaveProject.json";
        }

        function delTeacherLibrary(id) {
            jConfirm('您确定要删除吗?','确认',function(r){
                if(r){
                    jQuery.ajax({
                        url:'${ctx}/admin/jiaowu/teacher/delTeacherLibrary.json?id='+id,
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
        <h1 class="pagetitle">讲师专题库</h1>
        <span>
                    <span style="color:red">说明</span><br/>
					1.本页面展示讲师专题库.<br/>
					2.可通过专题名称查询对应的讲师专题库.<br/>
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/teacher/teacherLibraryList.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">专题名称 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="projectName" type="text" value="${projectName}" placeholder="请输入专题名称">
                        </label>
                    </div>

                    <div class="disIb ml20 mb10">
                        <span class="vam">专题 &nbsp;</span>
                        <label class="vam">
                           <select style="width: auto" name="classification">
                               <option value="">--请选择--</option>
                               <option value="1" <c:if test="${classification==1}">selected</c:if>>马克思主义原著</option>
                               <option value="2" <c:if test="${classification==2}">selected</c:if>>中国特色社会主义理论体系</option>
                               <option value="3" <c:if test="${classification==3}">selected</c:if>>学习贯彻习近平总书记重要讲话精神</option>
                               <option value="4" <c:if test="${classification==4}">selected</c:if>>中国特色社会主义政治、经济、文化、社会、生态文明建设</option>
                               <option value="5" <c:if test="${classification==5}">selected</c:if>>党性教育、党性锻炼与廉政建设</option>
                               <option value="6" <c:if test="${classification==6}">selected</c:if>>贵州、贵阳发展重大问题</option>
                               <option value="7" <c:if test="${classification==7}">selected</c:if>>干部综合素质、能力建设</option>
                           </select>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                </div>

                <div class="disIb ml20 mb10">
                    <label class="vam">
                        <a href="javascript:void(0)" onclick="toSaveProject()" class="stdbtn btn_orange">新建专题</a>
                    </label>
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
                    <th class="head0 center">专题分类</th>
                    <th class="head0 center">专题名称</th>
                    <th class="head0 center">讲师姓名</th>
                    <th class="head0 center">创建时间</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${teacherLibraries!=null&&teacherLibraries.size()>0 }">
                    <c:forEach items="${teacherLibraries}" var="tl" varStatus="status">
                        <tr>
                            <td>${status.count}</td>
                            <td>
                                <c:if test="${tl.classification == 1}">
                                    马克思主义原著
                                </c:if>
                                <c:if test="${tl.classification == 2}">
                                    中国特色社会主义理论体系
                                </c:if>
                                <c:if test="${tl.classification == 3}">
                                    学习贯彻习近平总书记重要讲话精神
                                </c:if>
                                <c:if test="${tl.classification == 4}">
                                    中国特色社会主义政治、经济、文化、社会、生态文明建设
                                </c:if>
                                <c:if test="${tl.classification == 5}">
                                    党性教育、党性锻炼与廉政建设
                                </c:if>
                                <c:if test="${tl.classification == 6}">
                                    贵州、贵阳发展重大问题
                                </c:if>
                                <c:if test="${tl.classification == 7}">
                                    干部综合素质、能力建设
                                </c:if>
                            </td>
                            <td>${tl.projectName}</td>
                            <td>${tl.teacherName}</td>
                            <td><fmt:formatDate type="both" value="${tl.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                            <td class="center">
                                <a href="${ctx}/admin/jiaowu/teacher/toUpdateTeacherLibrary.json?id=${tl.id}"
                                   class="stdbtn" title="编辑">编辑</a>
                                <a href="javascript:void(0)" onclick="delTeacherLibrary('${tl.id}')"
                                   class="stdbtn" title="删除">删除</a>
                                <a href="${ctx}/admin/jiaowu/courseware/coursewareList.json?courseware.teacherLibraryId=${tl.id}" class="stdbtn" title="课件列表">课件列表</a>
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