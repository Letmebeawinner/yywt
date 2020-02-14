<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>添加维修类型</title>
    <script type="text/javascript">
        function addFormSubmit() {
            jQuery("#addFormSubmit").submit();
        }

    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">添加维修类型</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="${ctx}/admin/houqin/UpdateRepairType.json" id="addFormSubmit">
                <input type="hidden" name="repairType.id" value="${repairType.id}">
                <p>
                    <label><em style="color: red;">*</em>类型名称</label>
                    <span class="field">
                        <input type="text" name="repairType.name"  class="longinput" value="${repairType.name}"/>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>类型排序</label>
                    <span class="field">
                        <input type="text" name="repairType.sort"  class="longinput" value="${repairType.sort}"/>
                    </span>
                </p>
                <p class="stdformbutton">
                    <button class="submit radius2" onclick="addFormSubmit()">提交保存</button>
                    <input type="reset" class="reset radius2" value="重置表单"/>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
</html>