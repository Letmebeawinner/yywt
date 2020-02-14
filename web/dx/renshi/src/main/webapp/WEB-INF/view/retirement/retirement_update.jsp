<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>修改${retirement.outType==1 ? "离退休" : "转出"}申请</title>
    <script type="text/javascript" src="${ctx}/static/admin/js/employee.js"></script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">${retirement.outType==1 ? "离退休" : "转出"}信息修改</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来修改教职工${retirement.outType==1 ? "离退休" : "转出"}信息<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent" >
            <form class="stdform stdform2" method="post" action="" id="updateRetirement">
                <p>
                    <input type="hidden" name="retirement.id" value="${retirement.id}">
                    <input type="hidden" name="retirement.employeeId" value="${retirement.employeeId}" id="employeeId" class="longinput"/>
                    <label><em style="color: red;">*</em>教职工</label>
                    <span class="field">
                    <a href="javascript:void(0)" class="dialog-btn btn radius50" onclick="selectEmployee()"><span>选择教职工</span></a>
                    <tt id="employeeName">${retirement.name}</tt>
                        <a href="javascript:void(0)" id="deleteEmployee" class="radius2" value="" onclick="deleteEmployee()">
                            <input type="reset" class="reset radius2" value="删 除"/>
                        </a>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>离退休时间</label>
                    <span class="field"><input type="text" readonly value="<fmt:formatDate value="${retirement.applyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" name="retirement.applyTime" id="applyTime" class="longinput"/></span>
                </p>
                <p>
                    <label>${retirement.outType==1 ? "享受待遇" : "转出原因"}</label>
                    <span class="field"><input type="text"  name="retirement.treatment" value="${retirement.treatment}" id="treatment" class="longinput"/></span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="updateRetirement();return false;">修改</button>
                    <input type="reset" class="reset radius2" value="返 回" onclick="javascript:history.go(-1);"/>
                </p>
            </form>
            <br/>
        </div><!--subcontent-->
    </div>
</div>

<script type="text/javascript">
    function updateRetirement() {
        if(jQuery("#applyTime").val()==""){
            alert("请选择${retirement.outType==1 ? "离退休" : "转出"}时间");
            return;
        }
        var date = jQuery("#updateRetirement").serialize();
        jQuery.ajax({
            url: "${ctx}/admin/rs/updateRetirement.json",
            data: date,
            type: "post",
            dataType: "json",
            async: false,
            success: function (result) {
                if (result.code=="0") {
                    alert(result.message);
                    window.location.href = "${ctx}/admin/rs/getRetirementList.json?retirement.outType=${retirement.outType}";
                } else {
                    alert(result.message);
                }
            }
        });
    }

    jQuery(function(){
        laydate.skin('molv');
        laydate({
            elem: '#applyTime',
            format:'YYYY-MM-DD hh:mm:ss'
        });
    });
</script>
</body>
</html>