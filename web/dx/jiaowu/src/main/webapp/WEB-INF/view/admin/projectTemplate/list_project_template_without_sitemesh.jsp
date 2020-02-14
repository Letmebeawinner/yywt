<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<%@ include file="/popUpBase.jsp" %>
<%-- <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>课题模板列表</title>
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
                        url: '/admin/jiaowu/ky/projectTemplateManagement/del.json?id=' + id,
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
<div class=" tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">课题模板</h1>
        <span>
                    <span style="color:red">说明</span><br/>
					1.本页面展示课题模板.<br/>
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <%--<div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                &lt;%&ndash;<form class="disIb" id="searchForm" action="/admin/oa/conference/meetingTopic/list.json"
                      method="get">
                    <div class="disIb ml20 mb10">
                        <span class="vam">议题名称 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="topicName" type="text"
                                   value="${topicName}" placeholder="请输入议题名称">
                        </label>
                    </div>
                </form>&ndash;%&gt;
                &lt;%&ndash;<div class="disIb ml20 mb10">
                    <a href="/jiaowu/ky/projectTemplateManagement/save.json" class="stdbtn btn_orange">添加模板</a>
                </div>&ndash;%&gt;
            </div>
        </div>--%>
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
                    <th class="head0 center">模板标题</th>
                    <th class="head0 center">创建时间</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${not empty projectTemplates }">
                    <c:forEach items="${projectTemplates}" var="projectTemplate" varStatus="status">
                        <tr>
                            <td>${status.count}</td>
                            <td>${projectTemplate.title}</td>
                            <td>${projectTemplate.createTime}</td>
                            <td class="center">
                                <a href="${projectTemplate.url}"
                                   class="stdbtn" title="下载模板" download="">下载模板</a>
                                <a href="javascript:void(0)" onclick="delMeetingTopic('${projectTemplate.id}')"
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