<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<%-- <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>获奖申报</title>
    <script type="text/javascript">
        jQuery(function () {

        });

        function searchForm() {
            jQuery("#searchForm").submit();
        }

        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }


        function delMeetingTopic(id) {
            jConfirm('您确定要删除吗?', '确认', function (r) {
                if (r) {
                    jQuery.ajax({
                        url: '/admin/oa/conference/meetingTopic/del.json?id=' + id,
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
                        error: function () {
                            jAlert('删除失败', '提示', function () {
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
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">获奖申报</h1>
        <span>
                    <span style="color:red">说明</span><br/>
					1.本页面展示获奖申报.<br/>
					2.点击下载附件, 可查看获奖申报附件.<br/>
					3.点击查看详情, 可查看获奖申报内容.<br/>
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <%-- <!-- 搜索条件，开始 -->
         <div class="overviewhead clearfix mb10">
             <div class="fl mt5">
                 <form class="disIb" id="searchForm" action="/admin/oa/conference/meetingTopic/list.json"
                       method="get">
                     <div class="disIb ml20 mb10">
                         <span class="vam">议题名称 &nbsp;</span>
                         <label class="vam">
                             <input style="width: auto;" name="topicName" type="text"
                                    value="${topicName}" placeholder="请输入议题名称">
                         </label>
                     </div>
                 </form>
                 <div class="disIb ml20 mb10">
                     <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                     <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                 </div>
             </div>
         </div>
         <!-- 搜索条件，结束 -->--%>

        <!-- 数据显示列表，开始 -->
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <colgroup>
                    <col class="con0"/>
                    <col class="con1"/>
                    <col class="con0"/>
                    <col class="con1"/>
                    <col class="con0"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">序号</th>
                    <th class="head0 center">申报人</th>
                    <th class="head0 center">获奖申报标题</th>
                    <th class="head0 center">状态</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${not empty awards}">
                    <c:forEach items="${awards}" var="award" varStatus="status">
                        <tr>
                            <td>${status.count}</td>
                            <td>${award.userName}</td>
                            <td>${award.title}</td>
                            <td>
                                <c:if test="${award.status == 0}">
                                    未审批
                                </c:if>
                                <c:if test="${award.status == 1}">
                                    已通过审批
                                </c:if>
                                <c:if test="${award.status == 2}">
                                    审批被拒绝
                                </c:if>
                            </td>
                            <td class="center">
                                <c:if test="${not empty award.url}">
                                    <a href="${award.url}"
                                       class="stdbtn" title="下载附件" download="">下载附件</a>
                                </c:if>
                                <a href="/admin/jiaowu/ky/awardInfo.json?id=${award.id}"
                                   class="stdbtn" title="查看详情">查看详情</a>
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