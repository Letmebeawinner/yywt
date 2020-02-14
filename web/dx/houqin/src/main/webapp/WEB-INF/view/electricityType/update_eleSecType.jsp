<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>修改二级用电区域</title>
    <script type="text/javascript">
        function addFormSubmit() {
            var eleTypeId=jQuery("#eleTypeId").val();
            var typeName=jQuery("#typeName").val();

            if(!jQuery.trim(eleTypeId)){
                alert("区域不能为空!");
                return;
            }

            if(!jQuery.trim(typeName)){
                alert("二级区域不能为空!");
                return;
            }

            jQuery.ajax({
                url: "${ctx}/admin/houqin/updEleSecTypeService.json",
                data: jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code=="0") {
                        alert(result.message);
                        window.location.href = "/admin/houqin/eleTypeList.json";
                    } else {
                        alert(result.message);
                        return;
                    }
                }
            });
        }

    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">修改用电区域</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于更新用电区域；<br>
            2.更新用电区域信息：按要求修改相关信息,点击<span style="color:red">提交保存</span>按钮；更新用电区域信息<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label><em style="color: red;">*</em>用电区域</label>
                    <span class="field">
                        <select name="eleTypeId" id="eleTypeId">
                        <option value="">未选择</option>
                         <c:forEach items="${electricityTypeList}" var="et">
                             <option value="${et.id}">${et.type}</option>
                         </c:forEach>
                        </select>
                    </span>
                    <input type="hidden" value="${eleSecType.eleTypeId}" id="oldEleTypeId">
                    <script>
                        jQuery("#eleTypeId").val(jQuery("#oldEleTypeId").val())
                    </script>
                </p>

                <p>
                    <label><em style="color: red;">*</em>二级用电区域</label>
                    <span class="field">
                        <input type="text" name="typeName"  class="longinput"
                               value="${eleSecType.typeName}" id="typeName"/>
                    </span>
                </p>

                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit();return false;">提交保存</button>
                </p>

                <input type="hidden" name="id" value="${eleSecType.id}">
            </form>
        </div>
    </div>
</div>
</body>
</html>