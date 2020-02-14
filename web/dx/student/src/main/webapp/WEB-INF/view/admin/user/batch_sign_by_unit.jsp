<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>批量再次报名</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">
        function batchSignByUnit() {

            jQuery.ajax({
                url: '${ctx}/admin/jiaowu/user/batchSignByUnit.json',
                data: {"classId": jQuery("#classId").val(), "userIds": jQuery("#userIds").val()},
                type: 'post',
                dataType: 'json',
                success: function (result) {
                    jAlert(result.message, '提示', function () {
                    });
                    if (result.code == "0") {
                        jQuery("#userIds").val("");
                        jQuery("#comperespan").html("");
                    } else {

                    }
                },
                error: function (e) {
                    jAlert('添加失败', '提示', function () {
                    });
                }
            });
        }

        function selectUser() {
            window.open('${ctx}/jiaowu/user/userListForMultiSelect.json?unitId=${unitId}', 'newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=800,height=600');
        }

        function addUsers(ids, names) {
            jQuery("#userIds").val(ids);
            jQuery("#comperespan").html(names);
        }
    </script>

</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">批量再次报名</h1>

    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="updates" class="subcontent">
            <!-- 主要内容开始 -->
            <div id="validation" class="subcontent">

                <form id="form1" class="stdform stdform2" method="post" action="">
                    <p>
                        <input type="hidden" name="userIds" id="userIds"/>
                        <input type="hidden" name="classId" id="classId" value="${classId}"/>
                        <label><em style="color: red;">*</em>历史报名人员</label>
                        <span class="field">
                                <span id="comperespan"></span>
                                <a href="javascript:selectUser()" class="stdbtn btn_orange">选择历史报名人员</a>
                            </span>
                    </p>
                    <p class="stdformbutton" style="text-align: center">
                        <button class="radius2" onclick="batchSignByUnit()" id="submitButton" type="button">提交</button>
                    </p>
                </form>
            </div>
            <!-- 主要内容结束 -->
            <div class="clear"></div>
        </div><!-- #updates -->
    </div>
</div>
</body>
</html>