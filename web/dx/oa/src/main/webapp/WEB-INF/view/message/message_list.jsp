<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>收件箱</title>
    <script type="text/javascript">
        //删除
        function updateMessage() {

            var checked = false;
            var str = "";
            jQuery(".checkbox").each(function () {
                if (jQuery(this).prop("checked")) {
                    str += this.value + ",";
                    checked = true;
                }
            });
            if (!checked) {
                alert("请至少选择一条信息");
                return;
            }
            str = str.substring(0, str.length - 1);

            if (confirm("删除后将放入到回收站，是否继续")) {
                jQuery.ajax({
                    url: "${ctx}/admin/oa/updateMessage.json",
                    data: {
                        "ids": str
                    },
                    type: "POST",
                    dataType: "JSON",
                    async: false,
                    cache: false,
                    success: function (result) {
                        if (result.code == "0") {
                            window.location.reload();
                        } else {
                            alert(result.message);
                            return;
                        }
                    }
                });
            }
        }

        //彻底删除
        function delMessage() {

            var checked = false;
            var str = "";
            jQuery(".checkbox").each(function () {
                if (jQuery(this).prop("checked")) {
                    str += this.value + ",";
                    checked = true;
                }
            });
            if (!checked) {
                alert("请至少选择一条信息");
                return;
            }
            str = str.substring(0, str.length - 1);

            if (confirm("删除后将无法恢复，是否继续")) {
                jQuery.ajax({
                    url: "${ctx}/admin/oa/delMessage.json",
                    data: {
                        "ids": str
                    },
                    type: "POST",
                    dataType: "JSON",
                    async: false,
                    cache: false,
                    success: function (result) {
                        if (result.code == "0") {
                            window.location.reload();
                        } else {
                            alert(result.message);
                            return;
                        }
                    }
                });
            }
        }


        function huiFu(id) {
            jQuery.ajax({
                url: "${ctx}/admin/oa/recoverInfoInRecycleBin.json",
                data: {
                    "id": id
                },
                type: "POST",
                dataType: "JSON",
                async: false,
                cache: false,
                success: function (result) {
                    if (result.code == "0") {
                        alert("已恢复");
                        window.location.reload();
                    } else {
                        alert(result.message);
                        return;
                    }
                }
            });
        }


        function allCheck(cb) {
            jQuery("input[class='checkbox']").prop('checked', cb.checked);
        }


    </script>
</head>
<body>
<div class="centercontent tables">

    <div class="pageheader notab">
        <c:if test="${status==1}">
             <h1 class="pagetitle">回收站</h1>
        </c:if>
        <c:if test="${status==0}">
            <h1 class="pagetitle">收件箱</h1>
        </c:if>
    </div>
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于收件箱；<br>
    </div>

    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <div class="disIb ml20 mb10">
                    <c:if test="${status==0}">
                        <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="updateMessage()">删除</a>
                    </c:if>
                    <a href="javascript: void(0)" class="stdbtn btn_orange" onclick="delMessage()">彻底删除</a>
                </div>
            </div>
        </div>
        ${info}
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <thead>
                <tr>
                    <th><input type="checkbox" onclick="allCheck(this)"></th>
                    <th class="head0 center">消息内容</th>
                    <th class="head0">发送人</th>
                    <th class="head0">发送时间</th>
                    <th class="head0" style="text-align: center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${messageList}" var="message">
                    <tr>
                        <td><input type="checkbox" value="${message.id}" id="chos" class="checkbox"></td>
                        <td>${message.content}</td>
                        <td>${message.senderName  }</td>
                        <td>${message.createTime}
                        </td>
                        <td style="text-align: center">
                            <c:if test="${status==0}">
                            <a href="${ctx}/admin/oa/findInfoDetail.json?id=${message.id}" class="stdbtn" title="查看详情">查看详情</a>
                            </c:if>
                            <c:if test="${status==1}">
                                <a href="javascript:void(0)" class="stdbtn" title="恢复"
                                   onclick="huiFu(${message.id},'0')">恢复</a>
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
    </div>
</div>
</div>
</body>
</html>