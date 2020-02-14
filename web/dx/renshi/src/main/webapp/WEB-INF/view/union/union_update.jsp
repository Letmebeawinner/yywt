<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>修改工会</title>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">处室信息修改</h1>
         <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来修改工会处室信息<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="updateunion">
                <input type="hidden" name="union.id" id="id" value="${union.id}"/>
                <p>
                    <label><em style="color: red;">*</em>处室名称</label>
                    <span class="field"><input type="text" value="${union.name}" name="union.name" id="name" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>联系人</label>
                    <span class="field"><input type="text" value="${union.contacts}" name="union.contacts" id="contacts" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>联系方式</label>
                    <span class="field"><input type="text"value="${union.mobile}"  name="union.mobile" id="mobile" class="longinput"/></span>
                </p>
                <p>
                    <label>简介</label>
                    <span class="field"><textarea cols="80" rows="5" name="union.info" id="info" class="longinput">${union.info}</textarea></span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="updateunion();return false;">修 改</button>
                    <input type="reset" class="reset radius2" value="返 回" onclick="javascript:history.go(-1);"/>
                </p>
            </form>
            <br/>
        </div><!--subcontent-->
    </div>
</div>
<script type="text/javascript">
    function updateunion() {
        if(jQuery("#name").val()==""){
            alert("请添加工会名");
            return;
        }
        if(jQuery("#contacts").val()==""){
            alert("请添加联系人");
            return;
        }
        var date = jQuery("#updateunion").serialize();
        jQuery.ajax({
            url: "${ctx}/admin/rs/updateUnion.json",
            data: date,
            type: "post",
            dataType: "json",
            async: false,
            success: function (result) {
                if (result.code=="0") {
                    alert(result.message);
                    window.location.href = "${ctx}/admin/rs/getUnionList.json";
                } else {
                    alert(result.message);
                }
            }
        });
    }
</script>
</body>
</html>