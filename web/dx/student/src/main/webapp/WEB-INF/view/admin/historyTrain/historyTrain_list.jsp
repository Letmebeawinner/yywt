<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>历史培训成绩列表</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
    <script type="text/javascript">
        function searchForm(){
            jQuery("#searchForm").submit();
        }
        function emptyForm(){
            jQuery("#name").val('');
        }


    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">历史培训成绩列表</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示历史培训成绩列表.<br />
					2.可通过名称查询对应的历史培训成绩.<br />
					3.可点击"修改"按钮,修改历史培训成绩.<br />
					4.可点击"删除"按钮,删除历史培训成绩.<br />
                </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/historyTrain/historyTrainList.json" method="get">
                    <%--<div class="disIb ml20 mb10">
                        <span class="vam">姓名&nbsp;</span>
                        <label class="vam">
                            <input id="name" style="width: auto;" name="name" type="text" class="hasDatepicker" value="${partySpirit.name}" placeholder="请输入名称">
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
                    <th class="head0 center">姓名</th>
                    <th class="head0 center">培训班次</th>
                    <th class="head0 center">培训时间</th>
                    <th class="head1 center">毕（结）业证号</th>
                    <th class="head1 center">党性锻炼成绩</th>
                    <th class="head0 center">学习考试成绩</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${historyTrainList!=null&&historyTrainList.size()>0 }">
                    <c:forEach items="${historyTrainList}" var="historyTrain" varStatus="index">
                        <tr>
                            <td>${index.index+1}</td>
                            <td>${historyTrain.name}</td>
                            <td>${historyTrain.className}</td>
                            <td><fmt:formatDate type="both" value="${historyTrain.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                            <td>${historyTrain.graduateNumber}</td>
                            <td>${historyTrain.partySpiritTotal}</td>
                            <td>${historyTrain.studyTestTotal}</td>
                            <td class="center">

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