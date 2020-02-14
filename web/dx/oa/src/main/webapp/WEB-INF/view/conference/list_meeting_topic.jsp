<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<%-- <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>议题列表</title>
    <script type="text/javascript" src="${ctx}/static/plugins/jquery.alerts.js"></script>
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
    <div class="pageheader">
        <h1 class="pagetitle">议题列表</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br/>
            1.本页面展示议题。<br/>
            2.可通过议题名称查询对应的议题。<br/>
            3.编辑：点击<span style="color:red">编辑</span>，进入编辑议题页面；<br>
            4.删除：点击<span style="color:red">删除</span>，删除对应的议题；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="/admin/oa/conference/meetingTopic/list.json"
                      method="get">
                    <div class="disIb ml20 mb10">
                        <span class="vam">议题名称 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="name" type="text"
                                   value="${name}" placeholder="请输入议题名称">
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
                    <th class="head0 center">议题名称</th>
                    <th class="head0 center">紧急程度</th>
                    <th class="head0 center">汇报人</th>
                    <th class="head0 center">列席人</th>
                    <th class="head0 center">议题内容</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${meetingTopics!=null&&meetingTopics.size()>0 }">
                    <c:forEach items="${meetingTopics}" var="mt" varStatus="status">
                        <tr>
                            <td>${status.count}</td>
                            <td>${mt.name}</td>
                            <td>${mt.emergencyDegree}</td>
                            <td>${mt.reporter}</td>
                            <td>${mt.attendPeople}</td>
                            <td>${mt.subjectContent}</td>
                            <td class="center">
                                <a href="/admin/oa/conference/meetingTopic/toUpdate.json?id=${mt.id}"
                                   class="stdbtn" title="编辑">编辑</a>
                                <a href="javascript:void(0)" onclick="delMeetingTopic('${mt.id}')"
                                   class="stdbtn" title="删除">删除</a>
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