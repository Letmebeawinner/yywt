<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>用车申请</title>
</head>
<body>
<div class="centercontent user-car">
    <div class="pageheader" style="background: #fff;">
        <%--<h1 class="pagetitle">用车申请</h1>--%>
        <%--<div style="margin-left: 20px;">--%>
            <%--<span style="color:red">说明</span><br>--%>
            <%--1.本页面用于添加用车申请；<br>--%>
            <%--2.<span style="color:red">*</span>标记为必填项；<br>--%>
        <%--</div>--%>
            <h1 class="page-list" style="text-align: center;">
               公务用车审批表
            </h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
                <div class="testtle-tables">
                    <form method="post" action="" id="addCarApply">
                    <table border="1">
                        <caption align="bottom">备注：如需使用公务用车，原则上提前一天填写该表，经同意后方可使用。该审批单由出车驾驶员、办公室各留存一份，办公室每月底对驾驶员出车情况进行汇总统计</caption>
                            <tr>
                                <td class="pt" style="width:20%;">申请人</td>
                                <td class="pt" style="width:15%;"><span>${applyName}</span></td>
                                <td class="pt" style="width:15%;"><small class="c-red">*</small>用车时间</td>
                                <td style="width:50%;"><input type="text" id = "startTime"  class="list-input-controller" name = "carApply.startTime" readonly> 到 <input type="text" id = "endTime"  class="list-input-controller" readonly name = "carApply.endTime" readonly></td>
                            </tr>
                            <tr>
                                <td class="pt" style="padding:30px 0;"><small class="c-red">*</small>用车事由</td>
                                <td colspan="3"><span><input type="text" name = "carApply.reason" id = "reason"/></span></td>
                            </tr>
                            <tr>
                                <td class="pt"><small class="c-red">*</small>线路、公里数</td>
                                <td colspan="3"><span><input type="text" name = "carApply.distance" id = "distance"/></span></td>
                            </tr>
                            <tr>
                                <td class="pt" style="padding:30px 0;">处室领导意见</td>
                                <td colspan="3"><span></span></td>
                            </tr>
                            <tr>
                                <td class="pt" style="padding:30px 0;">办公室管理员意见</td>
                                <td colspan="3"><span></span></td>
                            </tr>
                            <tr>
                                <td class="pt">所用车辆驾驶员</td>
                                <td colspan="3"><span></span></td>
                            </tr>
                            <tr>
                                <td class="pt">所用车辆车牌号</td>
                                <td colspan="3"><span></span></td>
                            </tr>
                    </table>
                        <input type="hidden" name="processDefinitionId" value="${processDefinition.id}">
                    </form>
                </div>
                <div class="buttons" style="text-align: center;margin-top:40px;">
                    <a class="submit radius2" onclick = "addFormSubmit()" style="cursor: pointer">提交</a>
                </div>
        </div><!--subcontent-->

    </div>
</div>

<script src="/static/plugins/jquery-1.7.2.min.js"></script>
<script type="text/javascript">

    function addFormSubmit() {
        var startTimeVal = jQuery("#startTime").val()
        var endTimeVal = jQuery("#endTime").val()

//        if(!jQuery("#peopleNumber").val()){
//            alert("请填写用车人数");
//            return;
//        }
//        if(!jQuery("#startAddress").val()){
//            alert("请填写出车地点");
//            return;
//        }
//        if(!jQuery("#endAddress").val()){
//            alert("请填写目的地点");
//            return;
//        }
        if (startTimeVal.length < 1) {
            alert("请填写开始时间");
            return;
        }
        if (endTimeVal.length < 1) {
            alert("请填写结束时间");
            return;
        }
        // 开始时间不能大于结束时间
        var startTimeDate = new Date(startTimeVal.replace(/-/g, '/'));
        var endTimeDate = new Date(endTimeVal.replace(/-/g, '/'));
        if (endTimeDate <= startTimeDate) {
            alert('结束时间必须大于开始时间');
            return;
        }
        if(!jQuery("#reason").val()){
            alert("请填写用车事由");
            return;
        }

        var data = jQuery("#addCarApply").serialize();
        jQuery.ajax({
            url: "${ctx}/admin/oa/startCarApply.json",
            data: data,
            type: "post",
            dataType: "json",
            async: false,
            cache : false,
            success: function (result) {
                if (result.code == "0") {
                    alert(result.message);
                    window.location.href = "${ctx}/admin/oa/task/history/mine.json";
                } else {
                    alert(result.message);
                }
            }
        });
    }

    function resetData(){
        jQuery(".longinput").val("");
    }
    jQuery(function(){
        laydate.skin('molv');
        laydate({
            elem: '#startTime',
            format:'YYYY-MM-DD hh:mm:ss',
            istime: true
        });
        laydate({
            elem: '#endTime',
            format:'YYYY-MM-DD hh:mm:ss',
            istime: true
        });
    });

</script>
</body>
</html>