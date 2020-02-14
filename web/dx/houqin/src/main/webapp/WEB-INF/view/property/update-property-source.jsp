<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>修改资产来源</title>
    <script type="text/javascript">
        function addFormSubmit() {
            var name=jQuery("#name").val();
            if(name==null || name==''){
                alert("名称不能为空!");
                return;
            }
            jQuery.ajax({
                url: "${ctx}/admin/houqin/updatePropertySource.json",
                data: jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code=="0") {
                        alert(result.message);
                        window.location.href = "/admin/houqin/propertySourceList.json";
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
        <h1 class="pagetitle">修改资产来源</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于资产来源修改；<br>
            2.修改资产来源：按要求修改相关信息,点击<span style="color:red">提交保存</span>按钮；修改资产来源<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <input type="hidden" name="propertySource.id" value="${propertySource.id}">
                    <label><em style="color: red;">*</em>资产来源名称</label>
                    <span class="field">
                        <input type="text" name="propertySource.name"  class="longinput" value="${propertySource.name}" id="name"/>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>类型排序</label>
                    <span class="field">
                        <input type="text" name="propertySource.sort"  class="longinput" value="${propertySource.sort}"/>
                    </span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit();return false;">提交保存</button>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
</html>