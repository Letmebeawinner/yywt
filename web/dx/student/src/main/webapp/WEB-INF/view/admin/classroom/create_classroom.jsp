<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>新建教室</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">

        function addClassroom() {
            var reg = new RegExp("^[0-9]*$");
            var num = jQuery("#num").val();
            if (num == "") {
                jAlert("座位数不能为空", '提示', function () {
                });
                return;
            }
            if (!reg.test(num)) {
                jAlert("座位数必须为数字", '提示', function () {
                });
                return;
            }
            var params = jQuery("#form1").serialize();
            jQuery.ajax({
                url: '${ctx}/admin/jiaowu/classroom/createClassroom.json',
                data: params,
                type: 'post',
                dataType: 'json',
                success: function (result) {
                    if (result.code == "0") {
                        window.location.href = "${ctx}/admin/jiaowu/classroom/classroomList.json";
                    } else {
                        jAlert(result.message, '提示', function () {
                        });
                    }
                },
                error: function (e) {
                    jAlert('添加失败', '提示', function () {
                    });
                }
            });
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">新建教室</h1>
        <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于新建教室<br/>
                    2.按要求填写相关信息,点击"提交"按钮,新建教室.<br/>
                    3.带有红色<span style="color: red">*</span>标记的内容为必填部分。
				</span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="updates" class="subcontent">
            <!-- 主要内容开始 -->
            <div id="validation" class="subcontent">

                <form id="form1" class="stdform" method="post" action="">
                    <p>
                        <label><em style="color: red;">*</em>位置</label>
                        <span class="field"><input type="text" name="classroom.position" id="position" class="longinput"/></span>
                    </p>
                    <p>
                        <label><em style="color: red;">*</em>座位数</label>
                        <span class="field"><input type="text" name="classroom.num" id="num" class="longinput"/></span>
                    </p>
                    <p>
                        <label>备注</label>
                        <span class="field"><textarea cols="80" rows="5" name="classroom.note" class="mediuminput" id="note"></textarea></span>
                    </p>
                    <p class="stdformbutton" style="text-align: center">
                        <button class="radius2" onclick="addClassroom();return false" id="submitButton">提交</button>
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