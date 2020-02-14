<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>修改会议室</title>
    <script type="text/javascript">
        function addFormSubmit() {
            var id=jQuery("#telephoneId").val();
            var name=jQuery("#name").val();
            if (name == "" || name == null) {
                alert("请添加姓名");
                return;
            }
            var telephone = jQuery("#telephone").val();
            if (telephone == "" || telephone == null) {
                alert("请添加电话号码");
                return;
            }
            var unit = jQuery("#unit").val();
            var email = jQuery("#email").val();
            var position = jQuery("#position").val();
            var sex = jQuery('input[name="telephone.sex"]:checked').val();
            jQuery.ajax({
                url: "${ctx}/admin/oa/updateTelephone.json",
                data: {
                    "telephone.id": id,
                    "telephone.name": name,
                    "telephone.telephone": telephone,
                    "telephone.unit": unit,
                    "telephone.position": position,
                    "telephone.email": email,
                    "telephone.sex": sex
                },
                type: "post",
                dataType: "json",
                async: false,
                success: function (result) {
                    if (result.code=="0") {
                        alert(result.message);
                        window.location.href = "${ctx}/admin/oa/queryAllTelephone.json";
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
    <div class="pageheader">
        <h1 class="pagetitle">修改通讯录</h1>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <div class="contenttitle2">
                <h3>修改通讯录</h3>
            </div><!--contenttitle-->
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <input type="hidden" name="telephone.id" value="${telephone.id}" id="telephoneId">
                <p>
                    <label>姓名</label>
                    <span class="field"><input type="text" name="telephone.name" id="name" class="longinput" value="${telephone.name}"/></span>
                </p>
                <p>
                    <label>性别</label>
                    <span class="field">
                        <input type="radio" name="telephone.sex" value="0" <c:if test="${telephone.sex==0}">checked</c:if>/>男
                        <input type="radio" name="telephone.sex" value="1" <c:if test="${telephone.sex==1}">checked</c:if>/> 女
                    </span>
                </p>
                <p>
                    <label>电话</label>
                    <span class="field"><input type="text" name="telephone.telephone" id="telephone" class="longinput" value="${telephone.telephone}"/></span>
                </p>
                <p>
                    <label>单位名称</label>
                    <span class="field"><input type="text" name="telephone.unit" id="unit" class="longinput" value="${telephone.unit}"/></span>
                </p>
                <p>
                    <label>单位职位</label>
                    <span class="field"><input type="text" name="telephone.position" id="position" class="longinput" value="${telephone.position}"/></span>
                </p>
                <p>
                    <label>邮箱</label>
                    <span class="field"><input type="text" name="telephone.email" id="email" class="longinput" value="${telephone.email}"/></span>
                </p>
            </form>
            <p class="stdformbutton" style="text-align: center">
                <button class="submit radius2" onclick="addFormSubmit()">提交保存</button>
            </p>
            <br/>
        </div>
    </div>
</div>


</body>
</html>