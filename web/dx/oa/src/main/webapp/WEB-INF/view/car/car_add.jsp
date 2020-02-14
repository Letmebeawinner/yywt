<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>添加车辆</title>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">添加车辆</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于添加车辆信息；<br>
            2.<em style="color: red;">*</em>代表必填项；<br>
        </div>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addCar">
                <p>
                    <label><em style="color: red;">*</em>车辆名称</label>
                    <span class="field"><input type="text" name="car.name" id="name" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>车牌号</label>
                    <span class="field"><input type = "text" name="car.carID" class="longinput" id = "carId"/></span>
                </p>
                <p>
                    <label>车辆描述</label>
                    <span class="field"><textarea cols="80" rows="5" name="car.description"  class="longinput"></textarea></span>
                </p>
                <p>
                    <label>保养单位</label>
                    <span class="field"> <input type="text"  name="car.repairCompany" class="longinput" id = "repairCompany"/></span>
                </p>
                <p>
                    <label>保养日期</label>
                    <span class="field"><input type = "text" name="car.repairDate"  class="longinput" id = "repairTime" readonly="readonly"/></span>
                </p>
                <p>
                    <label>行车距离</label>
                    <span class="field"><input type = "text" name="car.carRunDistance" class="longinput" id = "carRunDistance" placeholder="请输入数字" onkeyup="value=value.replace(/[^\d]/g,'')"/></span>
                </p>
                <p>
                    <label>送保人</label>
                    <span class="field"><input type = "text" name="car.sendPeople" id = "sendPeople" class="longinput"/></span>
                </p>
            <p class="stdformbutton" style="text-align: center">
                <button class="submit radius2" onclick="addFormSubmit();return false;">添 加</button>
                <input type="reset" class="reset radius2" value="重 置" onclick="resetData()"/>
            </p>
            </form>
            <br/>
        </div>
    </div>
</div>
<script type="text/javascript">
    function addFormSubmit() {
        if(!jQuery("#name").val()){
            alert("请添加车辆名");
            return;
        }
        if(!jQuery("#carId").val()){
            alert("请添加车牌号");
            return;
        }
//        if(!jQuery("#repairCompany").val()){
//            alert("请添加保养单位");
//            return;
//        }
//        if(!jQuery("#repairTime").val()){
//            alert("请添加保养日期");
//            return;
//        }
//        if(!jQuery("#carRunDistance").val()){
//            alert("请添加行车距离");
//            return;
//        }
        if(isNaN(jQuery("#carRunDistance").val())){
            alert("请输入正确的数字");
            return;
        }

//        if(!jQuery("#sendPeople").val()){
//            alert("请添加送保人");
//            return;
//        }
        var data = jQuery("#addCar").serialize();
        jQuery.ajax({
            url: "${ctx}/admin/oa/addCar.json",
            data: data,
            type: "post",
            dataType: "json",
            async: false,
            cache : false,
            success: function (result) {
                if (result.code == "0") {
                    alert(result.message);
                    window.location.href = "${ctx}/admin/oa/queryAllCar.json";
                } else {
                    alert(result.message);
                }
            }
        });
    }

    function resetData(){
        jQuery(".longinput").val("");
        jQuery(".hasDatepicker").val("");
    }
    jQuery(function(){
        laydate.skin('molv');
        laydate({
            elem: '#repairTime',
            format:'YYYY-MM-DD'
        });
    });
</script>
</body>
</html>