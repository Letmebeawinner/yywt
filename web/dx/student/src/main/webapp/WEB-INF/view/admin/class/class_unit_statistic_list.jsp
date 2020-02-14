<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>各个班次报名人数</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">
        function searchForm() {
            jQuery("#searchForm").submit();
        }

    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle"><b>${classes.name}</b><br>各个单位报名人数</h1>

    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/class/queryUnitPersonNum.json?classId="
              +${classes.id} method="get">

        </form>
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
                    <th class="head0 center">单位ID</th>
                    <th class="head0 center" width="55%">单位名称</th>
                    <th class="head1 center">班型总人数</th>
                    <th class="head1 center">本次已报名人数</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${unitList}" var="unit" varStatus="index">
                    <tr>
                        <td>${unit.id}</td>
                        <td>${unit.name}</td>
                        <td>
                            <c:if test="${unit.classNum==0}">
                                <font color="red">${unit.classNum}</font>
                            </c:if>
                            <c:if test="${unit.classNum!=0}">
                                ${unit.classNum}
                            </c:if>
                        <td>
                            <c:if test="${unit.num==0}">
                                <font color="red">${unit.num}</font>
                            </c:if>
                            <c:if test="${unit.num!=0}">
                                ${unit.num}
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
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