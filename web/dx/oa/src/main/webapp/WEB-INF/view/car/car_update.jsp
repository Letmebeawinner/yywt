<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <c:if test = "${flag == 0}">
        <title>修改车辆信息</title>
    </c:if>
    <c:if test = "${flag == 1}">
        <title>查看车辆基本信息</title>
    </c:if>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <c:if test = "${flag == 0}">
            <h1 class="pagetitle">修改车辆信息</h1>
        </c:if>
        <c:if test = "${flag == 1}">
            <h1 class="pagetitle">查看车辆信息</h1>
        </c:if>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            <c:if test = "${flag == 0}">
                1.本页面用于修改车辆信息；<br>
                2.<em style="color: red;">*</em>代表必填项；<br>
            </c:if>
            <c:if test = "${flag == 1}">
                1.本页面用于查看车辆信息；<br>
                2.<em style="color: red;">*</em>代表必填项；<br>
            </c:if>
        </div>
    </div><!--contenttitle-->

    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="updateCar">
                <p>
                <input type="hidden" name="car.id" value = "${car.id}"/>
                    <label><em style="color: red;">*</em>车辆名称</label>
                    <span class="field"><input type="text" name="car.name" value = "${car.name}" id="name" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>车牌号</label>
                    <span class="field"><input type = "text" name="car.carID" class="longinput" id = "carId" value = "${car.carID}"/></span>
                </p>
                <p>
                    <label>车辆描述</label>
                    <span class="field"><textarea cols="80" rows="5" name="car.description"  class="longinput">${car.description}</textarea></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>保养单位</label>
                    <span class="field"> <input type="text"  name="car.repairCompany" class="longinput" value = "${car.repairCompany}" id = "repairCompany"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>保养日期</label>
                    <span class="field"><input type = "text" name="car.repairDate"  class="longinput" id = "repairTime" readonly="readonly" value = "<fmt:formatDate value="${car.repairDate}" pattern="yyyy-MM-dd"></fmt:formatDate>"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>行车距离</label>
                    <span class="field"><input type = "text" name="car.carRunDistance" class="longinput" value = "${car.carRunDistance}" id = "carRunDistance" onkeyup="value=value.replace(/[^\d]/g,'')" placeholder="请输入数字"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>送保人</label>
                    <span class="field"><input type = "text" name="car.sendPeople" id = "sendPeople" class="longinput" value = "${car.sendPeople}"/></span>
                </p>
            <c:if test = "${flag == 0}">
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="updateFormSubmit();return false;">保 存</button>
                    <input type="reset" class="reset radius2" value="重 置" onclick="resetData()"/>
                </p>
            </c:if>
            </form>
            <br/>
        </div><!--subcontent-->
    </div>
</div>
<script type="text/javascript">
    var flag = '${flag}';
    function updateFormSubmit() {
        if(!jQuery("#name").val()){
            alert("请添加车辆名");
            return;
        }
        if(!jQuery("#carId").val()){
            alert("请添加车牌号");
            return;
        }
        if(!jQuery("#repairCompany").val()){
            alert("请添加保养单位");
            return;
        }
        if(!jQuery("#repairTime").val()){
            alert("请添加保养日期");
            return;
        }
        if(!jQuery("#carRunDistance").val()){
            alert("请添加行车距离");
            return;
        }
        if(isNaN(jQuery("#carRunDistance").val())){
            alert("请输入正确的数字");
            return;
        }
        if(!jQuery("#sendPeople").val()){
            alert("请添加送保人");
            return;
        }
        var data = jQuery("#updateCar").serialize();
        jQuery.ajax({
            url: "${ctx}/admin/oa/updateCar.json",
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
    }
    jQuery(function(){
        if (flag != 0) {
            jQuery("input, textarea").attr("disabled", "disabled");
        }
        laydate.skin('molv');
        laydate({
            elem: '#repairTime',
            format:'YYYY-MM-DD'
        });
    });
</script>
</body>
</html>