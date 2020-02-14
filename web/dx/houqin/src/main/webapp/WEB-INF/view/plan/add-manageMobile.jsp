<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>食堂主管电话</title>
    <script type="text/javascript">
            function addFormSubmit() {

                var mobile = jQuery("#mobile").val();
                if (mobile == null || mobile == '') {
                    alert("请填写手机号码");
                    return;
                }
                jQuery.ajax({
                    url: "${ctx}/admin/houqin/addOrUpdateManageMobile.json",
                    data: jQuery("#addFormSubmit").serialize(),
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code == "0") {
                            alert(result.message);
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
        <h1 class="pagetitle">食堂主管电话</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <input type="hidden" name="manageMobile.id" class="longinput" value="${manageMobileList.get(0).id}"/>
                    <label><em style="color: red;">*</em>电话</label>
                    <span class="field">
                        <input type="text" name="manageMobile.mobile" class="longinput"  value="${manageMobileList.get(0).mobile}" id="mobile"/>
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