<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>${outType==1 ? "离退休申请" : "转出申请"}</title>
    <script type="text/javascript" src="${ctx}/static/admin/js/employee.js"></script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">${outType==1 ? "新建离退休" : "新建转出"}</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来添加教职工${outType==1 ? "离退休" : "转出"}信息<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent" >
            <form class="stdform stdform2" method="post" action="" id="addretirement">
                <p>
                    <input type="hidden" name="ids" id="employeeId" class="longinput"/>
                    <input type="hidden" name="retirement.outType" id="outType" value="${outType}" class="longinput"/>
                    <label><em style="color: red;">*</em>教职工</label>
                    <span class="field">
                    <a href="javascript:void(0)" class="dialog-btn btn radius50" onclick="selectEmployee()"><span>选择教职工</span></a>
                    <tt id="employeeName"></tt>
                        <a href="javascript:void(0)" style="display: none" id="deleteEmployee" class="radius2" value="" onclick="deleteEmployee()">
                            <input type="reset" class="reset radius2" value="删 除"/>
                        </a>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>${outType==1 ? "离退休" : "转出"}时间</label>
                    <span class="field"><input type="text"  readonly name="retirement.applyTime" id="applyTime" class="longinput"/></span>
                </p>
                <p>
                    <label>${outType==1 ? "享受待遇" : "转出原因"}</label>
                    <span class="field"><input type="text"  name="retirement.treatment" id="treatment" class="longinput"/></span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addretirementFormSubmit();return false;">添 加</button>
                    <input type="reset" class="reset radius2" value="重 置" onclick="resetData()"/>
                </p>
            </form>
            <br/>
        </div><!--subcontent-->
    </div>
</div>

<script type="text/javascript">
    function addretirementFormSubmit() {
        if(jQuery("#employeeId").val()==""){
            alert("请选择教职工");
            return;
        }
        if(jQuery("#applyTime").val()==""){
            alert("请选择${outType==1 ? "离退休" : "转出"}时间");
            return;
        }
        var ids = jQuery("#employeeId").val();
        var date = jQuery("#addretirement").serialize();
        jQuery.ajax({
            url: "${ctx}/admin/rs/addRetirement.json",
            data: date,
            type: "post",
            dataType: "json",
            async: false,
            success: function (result) {
                if (result.code=="0") {
                    alert(result.message);
                    window.location.href = "${ctx}/admin/rs/getRetirementList.json?retirement.outType=${outType}";
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
            elem: '#applyTime',
            format:'YYYY-MM-DD hh:mm:ss'
        });
    });
</script>
</body>
</html>