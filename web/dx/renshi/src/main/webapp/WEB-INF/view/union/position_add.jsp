<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>添加职位</title>
    <script type="text/javascript" src="${ctx}/static/admin/js/employee.js"></script>
    <script type="text/javascript" src="${ctx}/static/admin/js/department.js"></script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">新建职位</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来添加新的公会职位<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent" >
            <form class="stdform stdform2" method="post" action="" id="addposition">
                <p>
                    <label><em style="color: red;">*</em>名称</label>
                    <span class="field"><input type="text" name="position.name" id="name" class="longinput"/></span>
                </p>
                <p>
                    <label>职责</label>
                    <span class="field"><textarea cols="80" rows="5" name="position.duty" id="duty" class="longinput"></textarea></span>
                </p> 
                <p>
                    <label>备注</label>
                    <span class="field"><textarea cols="80" rows="5" name="position.remark" id="remark" class="longinput"></textarea></span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addpositionFormSubmit();return false;">添 加</button>
                    <input type="reset" class="reset radius2" value="重 置" onclick="resetData()"/>
                </p>
            </form>
            <br/>
        </div><!--subcontent-->
    </div>
</div>

<script type="text/javascript">
    function addpositionFormSubmit() {
        if(jQuery("#name").val()==""){
            alert("请添加名称");
            return;
        }
        var date = jQuery("#addposition").serialize();
        jQuery.ajax({
            url: "${ctx}/admin/rs/addPosition.json",
            data: date,
            type: "post",
            dataType: "json",
            async: false,
            success: function (result) {
                if (result.code=="0") {
                    alert(result.message);
                    window.location.href = "${ctx}/admin/rs/getPositionList.json";
                } else {
                    alert(result.message);
                }
            }
        });
    }

    function resetData(){
        jQuery(".longinput").val("");
        jQuery("#employeeName").html('');
        jQuery("#departmentName").html('');
        jQuery("#deleteEmployee").hide();
        jQuery("#deleteDepartment").hide();
    }
</script>
</body>
</html>