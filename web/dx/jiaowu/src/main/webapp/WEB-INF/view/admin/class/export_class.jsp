<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>导出课表</title>
    <script type="text/javascript">

        jQuery(function () {
            laydate.skin('molv');
            laydate({
                elem: '#exportStartTime',
                istoday: false,
                format: 'YYYY-MM-DD'
            });
            laydate({
                elem: '#exportEndTime',
                istoday: false,
                format: 'YYYY-MM-DD'
            });
        })

        function addFormSubmit(classId) {
            var exportStartTime = jQuery("#exportStartTime").val();
            var exportEndTime = jQuery("#exportEndTime").val();
            /*var exportStartTime = jQuery("#exportStartTime").val();
            if(exportStartTime == null || exportStartTime.trim()==''){
                jAlert('请填写开始时间','提示',function() {});
                return;
            }
            var exportEndTime = jQuery("#exportEndTime").val();
            if(exportEndTime == null || exportEndTime.trim()==''){
                jAlert('请填写结束时间','提示',function() {});
                return;
            }
            var startTime = new Date(Date.parse(exportStartTime));
            var endTime = new Date(Date.parse(exportEndTime));
            if(endTime<startTime){
                jAlert("结束时间不能小于开始时间",'提示',function() {});
                return;
            }*/
            /*var contactTeacher = jQuery("#contactTeacher").val();
            if (contactTeacher == "") {
                jAlert('联系老师姓名不能为空', '提示', function () {
                });
                return false
            }

            var patt = /^1[0-9]{10}$/;
            var contactNumber = jQuery("#contactNumber").val();
            if (contactNumber.length < 1) {
                jAlert('请输入手机号', '提示', function () {
                });
                return false
            }
            if (!(patt.test(contactNumber))) {
                jAlert('手机号码有误，请重填', '提示', function () {
                });
                return false;
            }*/
            <%--window.location.href = "${ctx}/admin/jiaowu/courseArrange/courseArrangeExcel.json?classId=" + classId + "&exportStartTime=" + jQuery("#exportStartTime").val() + "&exportEndTime=" + jQuery("#exportEndTime").val() + "&contactTeacher=" + jQuery("#contactTeacher").val() + "&contactNumber=" + jQuery("#contactNumber").val();--%>
            window.location.href = "${ctx}/admin/jiaowu/courseArrange/courseArrangeExcel.json?classId=" + classId + "&exportStartTime="+exportStartTime+"&exportEndTime="+exportEndTime+"&contactTeacher=&contactNumber=";
        }

        //短信通知讲师来指定地址上课
        function sendMobile(classId) {
            jQuery.ajax({
                url     : "${ctx}/admin/jiaowu/class/sendMobile.json",
                type    : 'POST',
                data    : {"classId": classId},
                dataType: "json",
                async   : false,
                success : function(result) {
                    if (result.code == "0") {
                        alert(result.message);
                    } else {
                        jAlert('发送失败，请稍后重试','提示',function() {});
                        return;
                    }
                }
            });
        }

        //查询讲师进行提示
        function queryTeacher(classId) {
            jQuery.ajax({
                url: "${ctx}/admin/jiaowu/class/queryTeacher.json",
                type: "post",
                data: {"classId": classId},
                dataType: "json",
                async: false,
                cache : false,
                success: function (result) {
                    if (result.code == "0") {
                        jConfirm('确定要给：'+result.message+'讲师发送短信通知吗？','确认',function(r){
                            if(r){
                                sendMobile(classId);
                            }
                        });
                    } else {
                        alert(result.message);
                        return ;
                    }
                }
            });
        }
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">导出课表</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于导出课表；<br>
            2.为防止课表信息较多，请选择时间，如不填写则全部导出；<br>
            <%--2.导出课表：按需求选择日期,点击<span style="color:red">提交保存</span>按钮；将导出指定日期之间的课表<br>--%>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label><%--<em style="color: red;">*</em>--%>开始时间</label>
                    <span class="field">
                        <input type="text" class="longinput" id="exportStartTime" readonly/>
                    </span>
                </p>

                <p>
                    <label><%--<em style="color: red;">*</em>--%>结束时间</label>
                    <span class="field">
                         <input type="text" class="longinput" id="exportEndTime" readonly/>
                    </span>
                </p>
                <%--<p>
                    <label><em style="color: red;">*</em>联系老师</label>
                    <span class="field">
                         <input type="text" class="longinput" id="contactTeacher" name="contactTeacher"/>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>联系电话</label>
                    <span class="field">
                         <input type="text" class="longinput" id="contactNumber" name="contactNumber"/>
                    </span>
                </p>--%>

                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="queryTeacher(${classId});return false;">短信通知授课教师</button>
                    <button class="submit radius2" onclick="addFormSubmit(${classId});return false;">导出课表</button>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
</html>