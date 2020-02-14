<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>一键开通</title>
    <script type="text/javascript">
        function kaiFang() {
            var classesId=jQuery("#classesId").val();
            jQuery.ajax({
                url: "${ctx}/admin/houqin/fenFang.json",
                data:{"classesId":classesId} ,
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code=="0") {
                        alert(result.message);
                    } else {
                        alert(result.message);
                        return;
                    }
                }
            });
        }

    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">一键开通</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.一卡通开通时卡号随机分配，无特殊说明<br>
            2.如遇到卡号不够时，请及时联系相关的工作人员，可先开通现有卡号，未关联的人员随后单独绑定<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/openCardById/${id}.json" method="post">
                        <input type="hidden" value="${id}" id="classesId">
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="kaiFang()" class="stdbtn btn_orange">一键开通</a>
                    <font style="margin-left: 30px;">班级人数：<font style="color:red">${pagination.totalCount}</font>人</font>
                </div>
                <div class="disIb ml20 mb10">
                    <a href="${ctx}/admin/houqin/toBatchUserOpenCard.json"  class="stdbtn ml10">本班批量开通</a>
                </div>
            </div>
        </div>
        <!-- 数据显示列表，开始 -->
        <div class="pr">
            <input type="hidden" value="${mess.id}" name="messId">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <thead>
                <tr>
                    <th class="head1 center">序号</th>
                    <th class="head1 center">姓名</th>
                    <th class="head1 center">班级类型</th>
                    <th class="head1 center">班级名称</th>
                    <th class="head1 center">单位名称</th>
                    <th class="head1 center">性别</th>
                    <th class="head1 center">身份证号</th>
                </tr>
                </thead>
                <tbody id="emailSendRecordData">
                <c:forEach items="${userList}" var="stu" varStatus="index">
                    <tr>
                        <td class="center">${index.count}</td>
                        <td class="center">${stu.name}</td>
                        <td class="center">${stu.classTypeName}</td>
                        <td class="center">${stu.className}</td>
                        <td class="center">${stu.unit}</td>
                        <td class="center">${stu.sex}</td>
                        <td class="center">${stu.idNumber}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
        </div>
        <!-- 分页，结束 -->
    </div>
    <!-- 数据显示列表，结束 -->
</div>
</body>
</html>