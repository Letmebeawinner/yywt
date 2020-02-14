<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<%-- <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>议程列表</title>
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


        function delAgenda(id) {
            jConfirm('您确定要删除吗?', '确认', function (r) {
                if (r) {
                    jQuery.ajax({
                        url: '/admin/oa/conference/agenda/del.json?id=' + id,
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
    <div class="pageheader notab">
        <h1 class="pagetitle">议程列表</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br/>
            1.本页面展示议程。<br/>
            2.可通过地点，主持人查询对应的议程。<br/>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="/admin/oa/conference/agenda/list.json"
                      method="get">
                    <div class="disIb ml20 mb10">
                        <span class="vam">地点 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="agenda.location" type="text"
                                   value="${agenda.location}" placeholder="请输入议程地点">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">主持人 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="agenda.compere" type="text"
                                   value="${agenda.compere}" placeholder="请输入议程主持人">
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
                    <th class="head0 center">议题</th>
                    <th class="head0 center">时间</th>
                    <th class="head0 center">地点</th>
                    <th class="head0 center">主持人</th>
                    <th class="head0 center">出席</th>
                    <th class="head0 center">缺席</th>
                    <th class="head0 center">列席</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${agendas!=null&&agendas.size()>0 }">
                    <c:forEach items="${agendas}" var="agenda" varStatus="status">
                        <tr>
                            <td>${status.count}</td>
                            <td>${agenda.agendaName}</td>
                            <td>
                                <fmt:formatDate type="both" value="${agenda.time}" pattern="yyyy-MM-dd HH:mm:ss"/>
                            </td>
                            <td>${agenda.location}</td>
                            <td>${agenda.compere}</td>
                            <td>${agenda.bePresent}</td>
                            <td>${agenda.absent}</td>
                            <td>${agenda.attend}</td>
                            <td class="center">
                                <a href="/admin/oa/conference/agenda/toUpdate.json?id=${agenda.id}"
                                   class="stdbtn" title="编辑">编辑</a>
                                <a href="javascript:void(0)" onclick="delAgenda('${agenda.id}')"
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
</div>

</body>
</html>