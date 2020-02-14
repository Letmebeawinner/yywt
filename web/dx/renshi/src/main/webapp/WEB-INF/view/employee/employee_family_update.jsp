<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>修改家庭成员</title>
    <script type="text/javascript">
        /**
         * 时间控件
         */
        jQuery(function () {
            laydate.skin('molv');
            laydate({
                elem: '#birthday',
                format: 'YYYY-MM-DD'
            })
        });

        function addFormSubmit() {
            var employeeId=jQuery("#employeeId").val();
            var title=jQuery("#title").val();
            if(title==null||title==""){
                alert("请填写称谓");
                return;
            }
            var name=jQuery("#name").val();
            if(name==null||name==""){
                alert("请填写姓名");
                return;
            }
            var aspect=jQuery("#aspect").val();
            if(aspect==null||aspect==""){
                alert("请填写政治面貌");
                return;
            }
            jQuery.ajax({
                url: "/admin/rs/updateFamily.json",
                data: jQuery("#addFamily").serialize(),
                type: "post",
                dataType: "json",
                async: false,
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "/admin/rs/employeeFamilyList.json?family.employeeId="+employeeId;
                    } else {
                        alert(result.message);
                    }
                }
            });
        }
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">修改家庭成员</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来添加新的家庭成员<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFamily">
                <p>
                    <input type="hidden" id="employeeId" name="family.employeeId" value="${family.employeeId}">
                    <input type="hidden" name="family.id" value="${family.id}">
                    <label><em style="color: red;">*</em>称谓</label>
                    <span class="field"><input type="text" name="family.title" value="${family.title}" id="title" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>姓名</label>
                    <span class="field"><input type="text" name="family.name" id="name" class="longinput" value="${family.name}"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>出生年月</label>
                    <span class="field"><input type="text" readonly name="family.birthday" id="birthday" class="longinput"
                                               value="<fmt:formatDate value="${family.birthday}" pattern="yyyy-MM-dd"/>"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>政治面貌</label>
                    <span class="field">
                      <input type="text" name="family.aspect" id="aspect" class="longinput" value="${family.aspect}"/>
                    </span>
                </p>
                <p>
                    <label>工作单位及职务</label>
                    <span class="field"><input type="text" name="family.unit"  value="${family.unit}" id="unit" class="longinput"/></span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit();return false;">修 改</button>
                    <input type="reset" class="reset radius2" value="重 置" onclick="resetData()"/>
                </p>
            </form>
            <br/>
        </div>
    </div>
</div>
</body>
</html>