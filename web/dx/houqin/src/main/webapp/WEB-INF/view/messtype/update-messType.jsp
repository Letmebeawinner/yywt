<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>修改食堂分类</title>
    <script type="text/javascript">
        function addFormSubmit() {
            var name=jQuery("#name").val();
            if(name==null || name==''){
                alert("名称不能为空!");
                return;
            }
            jQuery.ajax({
                url: "${ctx}/admin/houqin/updateMessType.json",
                data:jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code=="0") {
                        alert(result.message);
                        window.location.href = "/admin/houqin/queryAllMessType.json";
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
        <h1 class="pagetitle">修改食堂分类</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于修改食堂分类信息；<br>
            2.修改食堂分类信息：按要求修改相关信息,点击<span style="color:red">提交保存</span>按钮；修改食堂信息<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <input type="hidden" name="messType.id" value="${messType.id}">
                    <label><em style="color: red;">*</em>名称</label>
                    <span class="field">
                        <input type="text" name="messType.name"  class="longinput" value="${messType.name}" id="name"/>
                    </span>
                </p>

                <p>
                    <label>位置</label>
                    <span class="field">
                        <input type="text" name="messType.location"  class="longinput" value="${messType.location}" />
                    </span>
                </p>

                <p>
                    <label>备注</label>
                    <span class="field">
                         <textarea cols="80" rows="5" name="messType.context" id="context" class="longinput">${messType.context}</textarea>
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