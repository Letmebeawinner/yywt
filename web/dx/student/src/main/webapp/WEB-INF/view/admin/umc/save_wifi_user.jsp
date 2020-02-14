<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>开通WIFI账号</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">

        function saveForm() {
            var phone = jQuery("#phone").val()
            var param = jQuery("#saveForm").serialize()
            var passwordLength = jQuery("#password").val().length;
            if (phone.length < 11) {
                jAlert("请输入正确的手机号", "提示", function () {})
                return
            }

            if (passwordLength < 6) {
                jAlert("用户密码长度为6~64位", "提示", function () {})
                return
            }

            jQuery.getJSON(
                "${ctx}/admin/jiaowu/wifiuser/doSave.json",
                param,
                function (rs) {
                    jAlert(rs.message, "提示", function () {
                        if (rs.code === "0") {
                            window.location.href = "${ctx}/admin/jiaowu/wifiuser/update.json"
                        }
                    })
                }
            )
        }

    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">开通WIFI账号</h1>
        <span>
                <span style="color:red">说明</span><br>
                1.本页面用于开通WIFI账号<br/>
                2.按要求填写相关信息,点击"提交"按钮,开通WIFI账号.<br/>
                3.带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="updates" class="subcontent">
            <!-- 主要内容开始 -->
            <div id="validation" class="subcontent">

                <form id="saveForm" class="stdform stdform2" method="post" action="">
                    <p>
                        <label><em style="color: red;">*</em>手机号</label>
                        <span class="field">
                            <input type="text" name="wifiUser.phone" id="phone" class="longinput"
                                   value="" maxlength="11"/>
                        </span>
                    </p>
                    <p>
                        <label><em style="color: red;">*</em>密码</label>
                        <span class="field">
                             <input type="text" name="wifiUser.password" id="password" class="longinput"
                                    value="" maxlength="18"/>
                        </span>
                    </p>

                    <p class="stdformbutton">
                        <button class="radius2" onclick="saveForm();" type="button" id="submitButton"
                                style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">提交</button>
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