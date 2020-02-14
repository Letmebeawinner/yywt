<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>议题草稿箱列表</title>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader">
        <h1 class="pagetitle">草稿箱议题列表</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br/>
            1.本页面展示议题。<br/>
            2.可通过议程名称查询对应的议题。<br/>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="/admin/oa/agenda/process/oaMeetingAgendaDraftList.json"
                      method="get">
                    <div class="disIb ml20 mb10">
                        <span class="vam">议程名称 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" name="oaMeetingAgenda.name" type="text"
                                   value="${oaMeetingAgenda.name}" placeholder="请输入议程名称">
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
                    <th class="head0 center">年次</th>
                    <th class="head0 center">议程时间</th>
                    <th class="head0 center">主持人</th>
                    <th class="head0 center">记录</th>
                    <th class="head0 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${oaMeetingAgendaList !=null && oaMeetingAgendaList.size()>0 }">
                    <c:forEach items="${oaMeetingAgendaList}" var="oaMeetingAgenda" varStatus="status">
                        <tr>
                            <td>${status.count}</td>
                            <td>${oaMeetingAgenda.year}-${oaMeetingAgenda.frequency}</td>
                            <td><fmt:formatDate value="${oaMeetingAgenda.time}" pattern="yyyy-MM-dd"/></td>
                            <td>${oaMeetingAgenda.compere}</td>
                            <td>${oaMeetingAgenda.record}</td>
                            <td class="center">
                                <a href="/admin/oa/agenda/process/toUpdateOaMeetingAgenda.json?id=${oaMeetingAgenda.id}" class="stdbtn" title="修改">修改</a>
                                <a href="javascript:void(0)" onclick="deleteOaMeetingAgendaById(${oaMeetingAgenda.id})" class="stdbtn" title="删除">删除</a>
                                <a href="javascript:void(0)" onclick="releaseOaMeetingAgendaById(${oaMeetingAgenda.id})" class="stdbtn" title="发布议程">发布议程</a>
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
<script type="text/javascript">
    function searchForm() {
        jQuery("#searchForm").submit();
    }

    function emptyForm() {
        jQuery("input:text").val('');
        jQuery("select").val(0);
    }
    //删除
    function deleteOaMeetingAgendaById(id) {
        if(confirm("您确定要删除吗?")){
            jQuery.ajax({
                url: '${ctx}/admin/oa/agenda/process/deleteOaMeetingAgendaById.json',
                data: {"id":id},
                type: 'post',
                dataType: 'json',
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "${ctx}/admin/oa/agenda/process/oaMeetingAgendaDraftList.json";
                    } else {
                        alert(result.message);
                        return;
                    }
                }
            })
        }
    }

    //发布
    function releaseOaMeetingAgendaById(id) {
        if(confirm("您确定要发布吗?")){
            jQuery.ajax({
                url: '${ctx}/admin/oa/agenda/process/startDraftOaMeetingAgendaById.json',
                data: {"id":id},
                type: 'post',
                dataType: 'json',
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "${ctx}/admin/oa/task/to/claim/list.json";
                    } else {
                        alert(result.message);
                        return;
                    }
                }
            })
        }
    }
</script>
</body>
</html>