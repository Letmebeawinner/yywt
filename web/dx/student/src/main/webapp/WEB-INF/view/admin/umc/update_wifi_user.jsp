<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>修改WIFI密码</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">
        function updWifiPassword() {
            var passwordLength = jQuery("#password").val().length;
            if (passwordLength < 6) {
                jAlert("用户密码长度为6~64位", "提示", function () {})
                return
            }
            var param = jQuery("#saveForm").serialize();
            jQuery.getJSON(
                "${ctx}/admin/jiaowu/wifiuser/doUpdate.json",
                param,
                function (rs) {
                    jAlert(rs.message, "提示", function () {
                        if (rs.code === "0") {
                            window.location.reload()
                        }
                    })

                })
        }

    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">修改WIFI密码</h1>
        <span>
                <span style="color:red">说明</span><br>
                1.本页面用于修改wifi密码<br/>
                2.按要求填写相关信息,点击"提交"按钮,修改wifi密码.<br/>
                3.带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="updates" class="subcontent">
            <!-- 主要内容开始 -->
            <div id="validation" class="subcontent">

                <form id="saveForm" class="stdform stdform2" method="post" action="">
                    <p>
                        <label><em style="color: red;">*</em>账号</label>
                        <span class="field">
                            ${wifiUser.account} &nbsp;
                        </span>
                    </p>

                    <p>
                        <label><em style="color: red;">*</em>密码</label>
                        <span class="field">
                            <input type="text" name="wifiUser.password" id="password" class="longinput"
                                   value="${wifiUser.password}" maxlength="18"/>
                        </span>
                    </p>

                    <input type="hidden" name="wifiUser.id" value="${wifiUser.id}">
                    <input type="hidden" name="wifiUser.account" value="${wifiUser.account}">
                </form>
                <p class="stdformbutton">
                    <button class="radius2" onclick="updWifiPassword();" type="button" id="submitButton"
                            style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">
                        提交
                    </button>
                </p>
            </div>
            <!-- 主要内容结束 -->
            <div class="clear"></div>
        </div><!-- #updates -->
    </div>
</div>
</body>
</html>