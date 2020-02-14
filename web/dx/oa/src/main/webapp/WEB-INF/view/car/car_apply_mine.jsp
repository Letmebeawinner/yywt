<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>我的车辆申请情况</title>
    <script type="text/javascript">
        /**
         * 执行查询
         */
        function searchForm() {
            jQuery("#carApplyForm").submit();
        }
        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        function delCarApply(carApplyId) {
            if (confirm("删除后将无法恢复，是否继续")) {
                jQuery.ajax({
                    url: "/admin/oa/deleteCarApply.json?id="+carApplyId,
                    data: {},
                    type: "post",
                    dataType: "json",
                    async: false,
                    cache : false,
                    success: function (result) {
                        if (result.code=="0") {
                            window.location.reload();
                        } else {
                            alert(result.message);
                            return;
                        }
                    }
                });
            }
        }
    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab">
        <h1 class="pagetitle">我的车辆申请情况列表</h1>

    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">

        <div class="contenttitle2">
            <h3>我的车辆申请情况列表</h3>
        </div><!--contenttitle-->

        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="carApplyForm" action="${ctx}/admin/oa/queryAllCarApply.json" method="post">
                    <div class="disIb ml20 mb10">
                        <span class="vam">出车地点 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" type="text" class="longinput" placeholder="请输入出车地点" name="carApply.id" value="${carApply.id}">
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <span class="vam">目的地点 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" type="text" class="longinput" placeholder="输入目的地点" name="carApply.name" value="${carApply.name}"/>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="searchForm()">搜 索</a>
                    <a href="javascript: void(0)" class="stdbtn ml10" onclick="emptyForm()">重 置</a>
                    <a href="${ctx}/admin/oa/toAddCarApply.json" class="stdbtn ml10">增 加</a>
                </div>
            </div>
        </div>

        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <colgroup>
                    <col class="con0" style="width:10%;"/>
                    <col class="con1"/>
                    <col class="con0" style="width:20%;"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">ID</th>
                    <th class="head0 center">申请人</th>
                    <th class="head1">人数</th>
                    <th class="head1">出车时间</th>
                    <th class="head1">结束时间</th>
                    <th class="head1">出车地址</th>
                    <th class="head1">结束地址</th>
                    <th class="head1">事由</th>
                    <th class="head1">申请时间</th>
                    <th class="head0 center">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${carApplies}" var="carApply">
                    <tr>
                        <td>${carApply.id}</td>
                        <td>${carApply.userName}</td>
                        <td>${carApply.peopleNumber}</td>
                        <td><fmt:formatDate value="${carApply.startTime}" pattern="yyyy-MM-dd"></fmt:formatDate></td>
                        <td><fmt:formatDate value="${carApply.endTime}" pattern="yyyy-MM-dd"></fmt:formatDate></td>
                        <td>${carApply.startAddress}</td>
                        <td>${carApply.endAddress}</td>
                        <td>${carApply.reason}</td>
                        <td><fmt:formatDate value="${carApply.applyTime}" pattern="yyyy-MM-dd"></fmt:formatDate></td>
                        <td class="center">
                            <a href="${ctx}/admin/oa/myCarProcessInfo.json?processInstanceId=${carApply.processInstanceId}" class="stdbtn" title="查看">查看详情</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- 分页，开始 -->
            <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
            <!-- 分页，结束 -->
        </div>
    </div>
</div>
</body>
</html>