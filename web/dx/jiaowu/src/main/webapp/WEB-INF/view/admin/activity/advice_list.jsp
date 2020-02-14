<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<%-- <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>建议列表</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
    <script type="text/javascript">
        jQuery(function(){
            /*var createUserName="${activity.createUserName}";
             jQuery("#createUserName").val(createUserName);*/
            /*var title="${activity.title}";
            jQuery("#title").val(title);*/
        });
        function searchForm(){
            jQuery("#searchForm").submit();
        }

        function emptyForm(){
            jQuery("input:text").val('');
            jQuery("select").val(0);
//             jQuery("#title").val("");
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">建议列表</h1>
                <span>
                    <span style="color:red">说明</span><br />
					1.本页面展示建议列表.<br />
					2.可点击"回复"按钮,回复该建议.<br />
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/activity/adviceList.json" method="get">

                    <%--<div class="disIb ml20 mb10">
                        <span class="vam">创建者 &nbsp;</span>
                        <label class="vam">
                            <input id="createUserName" style="width: auto;" name="createUserName" type="text" class="hasDatepicker" value="" placeholder="请输入发起人">
                        </label>
                    </div>--%>

                   <%-- <div class="disIb ml20 mb10">
                        <span class="vam">标题 &nbsp;</span>
                        <label class="vam">
                            <input id="title" style="width: auto;" name="title" type="text" class="hasDatepicker" value="" placeholder="请输入名称">
                        </label>
                    </div>--%>


                </form>
                <%--<div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
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
                    <th class="head0 center">序号</th>
                    <th class="head0 center">活动</th>
                    <th class="head0 center">创建人</th>
                    <th class="head0 center">内容</th>
                    <th class="head0 center">创建时间</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${adviceList!=null&&adviceList.size()>0 }">
                    <c:forEach items="${adviceList}" var="advice" varStatus="index">
                        <tr>
                            <td>${index.index+1}</td>
                            <td>${advice.activityTitle}</td>
                            <td>${advice.createUserName}</td>
                            <td>${advice.content}</td>
                            <td><fmt:formatDate type="both" value="${advice.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                            <td class="center">
                                <c:if test="${advice.hasReply==0}">
                                    <a href="${ctx}/admin/jiaowu/activity/toAddAdviceReply.json?id=${advice.id}" class="stdbtn" title="回复">回复</a>
                                </c:if>
                                <c:if test="${advice.hasReply==1}">
                                已回复
                                </c:if>
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