<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<%-- <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>我的教学质量评估</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
    <script type="text/javascript">
        jQuery(function(){

        });
        function searchForm(){
            jQuery("#searchForm").submit();
        }

        function emptyForm(){
            jQuery("input:text").val('');
            jQuery("select").val(0);
//             jQuery("#title").val("");
        }

        function selectClass(){
            window.open('${ctx}/jiaowu/class/classListForSelect.json','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=1200,height=800');
        }

        function addClass(classArray){
            jQuery("#classspan").html(classArray[1]);
            jQuery("#classId").val(classArray[0]);
            jQuery("#className").val(classArray[1]);
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">我的教学质量评估</h1>
                <span>
                    <span style="color:red">说明</span><br />
					1.本页面展示我的教学质量评估.<br />
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/teachEvaluate/myTeachEvaluateList.json" method="get">
                    <input type="hidden" name="classId" id="classId" value="${teachEvaluate.classId}"/>
                    <input type="hidden" name="className" id="className" value="${className}"/>


                    <%--<div class="disIb ml20 mb10">
                        <span class="vam">班级 &nbsp;</span>
                        <label class="vam">
                            <span id="classspan">${className}</span>
                            <a href="javascript:void(0)" onclick="selectClass()" class="stdbtn btn_orange">选择班次</a>
                        </label>
                    </div>--%>


                </form>
                <%--<div class="disIb ml20 mb10">
                        <a href="${ctx}/admin/jiaowu/courseArrange/courseArrangeListOfCurrentStudent.json" class="stdbtn ml10 btn_orange">填写教学质量评估</a>
                </div>--%>
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
                    <th class="head0 center">用户</th>
                    <th class="head1 center">课程</th>
                    <th class="head0 center">备课认真治学严谨</th>
                    <th class="head0 center">教态端庄为人师表</th>
                    <th class="head1 center">层次分明突出重点</th>
                    <th class="head1 center">联系实际范例恰当</th>
                    <th class="head1 center">语言清晰善用多媒</th>
                    <th class="head1 center">启发教学培养思维</th>
                    <th class="head1 center">教学内容印象深刻</th>
                    <th class="head1 center">个人成长帮助较大</th>
                    <th class="head1 center">总分</th>
                    <th class="head0 center">创建时间</th>
                    <%--<th class="head1 center">操作</th>--%>
                </tr>
                </thead>
                <tbody>
                <c:if test="${teachEvaluateList!=null&&teachEvaluateList.size()>0 }">
                    <c:forEach items="${teachEvaluateList}" var="teachEvaluate">
                        <tr>
                            <td>${teachEvaluate.userName}</td>
                            <td>${teachEvaluate.courseName}</td>
                            <td>${teachEvaluate.index1}</td>
                            <td>${teachEvaluate.index2}</td>
                            <td>${teachEvaluate.index3}</td>
                            <td>${teachEvaluate.index4}</td>
                            <td>${teachEvaluate.index5}</td>
                            <td>${teachEvaluate.index6}</td>
                            <td>${teachEvaluate.index7}</td>
                            <td>${teachEvaluate.index8}</td>
                            <td>${teachEvaluate.total}</td>
                            <td><fmt:formatDate type="both" value="${teachEvaluate.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                           <%-- <td class="center">
                                <a href="${ctx}/admin/jiaowu/teachEvaluate/queryTeachEvaluate.json?id=${teachEvaluate.id}" class="stdbtn" title="查看">查看</a>
                            </td>--%>
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