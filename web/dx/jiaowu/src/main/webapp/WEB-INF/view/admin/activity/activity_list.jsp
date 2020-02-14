<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<%-- <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>活动列表</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
    <script type="text/javascript">
        jQuery(function(){
            /*var createUserName="${activity.createUserName}";
            jQuery("#createUserName").val(createUserName);*/
            var title="${activity.title}";
            jQuery("#title").val(title);
        });
        function searchForm(){
            jQuery("#searchForm").submit();
        }

        function emptyForm(){
            jQuery("input:text").val('');
            jQuery("select").val(0);
//             jQuery("#title").val("");
        }
        function deleteActivity(id){
            jConfirm('您确定要删除活动吗?','确认',function(r){
                if(r){
                    jQuery.ajax({
                        url:'${ctx}/admin/jiaowu/activity/deleteActivity.json?id='+id,
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

        function createAdvice(id){
            jQuery.ajax({
                url:'${ctx}/admin/jiaowu/activity/checkUserCanCreateAdvice.json?id='+id,
                type:'post',
                dataType:'json',
                success:function (result){
                    if(result.code=="0"){
                        window.location.href='${ctx}/admin/jiaowu/activity/toCreateAdvice.json?activityId='+id;
                    }else{
                        jAlert(result.message,'提示',function() {});
                    }
                },
                error:function(e){
                    jAlert('删除失败','提示',function() {});
                }
            });
        }


    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">活动列表</h1>
                <span>
                    <span style="color:red">说明</span><br />
					1.本页面展示活动的列表.<br />
					2.可通过名称、发起人查询对应的活动.<br />
					3.可点击"查看"按钮,查看某活动具体内容和评论.<br />
					4.可点击"删除"按钮,删除某活动.<br />
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/activity/activityList.json" method="get">

                    <%--<div class="disIb ml20 mb10">
                        <span class="vam">创建者 &nbsp;</span>
                        <label class="vam">
                            <input id="createUserName" style="width: auto;" name="createUserName" type="text" class="hasDatepicker" value="" placeholder="请输入发起人">
                        </label>
                    </div>--%>

                    <div class="disIb ml20 mb10">
                        <span class="vam">标题 &nbsp;</span>
                        <label class="vam">
                            <input id="title" style="width: auto;" name="title" type="text" class="hasDatepicker" value="" placeholder="请输入名称">
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
                    <th class="head1 center">内容</th>
                    <th class="head0 center">创建者</th>
                    <th class="head0 center">点击量</th>
                    <th class="head1 center">回复数</th>
                    <th class="head0 center">创建时间</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${activityList!=null&&activityList.size()>0 }">
                    <c:forEach items="${activityList}" var="activity" varStatus="index">
                        <tr>
                            <td>${index.index+1}</td>
                            <td><a href="${ctx}/admin/jiaowu/activity/detailOfOneActivity.json?id=${activity.id}">${activity.title}</a></td>
                            <td>${fn:substring(activity.content,0,40)}...</td>
                            <td>${activity.createUserName}</td>
                            <td>${activity.viewNum}</td>
                            <td>${activity.replyNum}</td>
                            <td><fmt:formatDate type="both" value="${activity.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                            <td class="center">
                                <a href="${ctx}/admin/jiaowu/activity/detailOfOneActivity.json?id=${activity.id}" class="stdbtn" title="查看">查看</a>
                                <c:if test="${user.userType=='1'}">
                                    <a href="${ctx}/admin/jiaowu/activity/toUpdateActivity.json?id=${activity.id}" class="stdbtn" title="修改">修改</a>
                                    <a href="javascript:void(0)" onclick="deleteActivity(${activity.id})" class="stdbtn" title="删除">删除</a>
                                </c:if>
                                <c:if test="${user.userType=='3'}">
                                <a href="javascript:void(0)" onclick="createAdvice(${activity.id})" class="stdbtn" title="建议">建议</a>
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