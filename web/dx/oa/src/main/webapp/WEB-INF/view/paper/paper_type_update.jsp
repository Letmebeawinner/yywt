<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <c:if test = "${flag == 0}">
        <title>修改封条类型信息</title>
    </c:if>
    <c:if test = "${flag == 1}">
        <title>查看封条类型信息</title>
    </c:if>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <c:if test = "${flag == 0}">
            <h1 class="pagetitle">修改封条类型信息</h1>
        </c:if>
        <c:if test = "${flag == 1}">
            <h1 class="pagetitle">查看封条类型信息</h1>
        </c:if>
    </div><!--pageheader-->
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        <c:if test = "${flag == 0}">
            1.本页面用于封条类型信息修改；<br>
        </c:if>
        <c:if test = "${flag == 1}">
            1.本页面用于封条类型信息查看；<br>
        </c:if>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <%--<div class="contenttitle2">--%>
                <%--<h3>修改封条类型</h3>--%>
            <%--</div><!--contenttitle-->--%>
            <form class="stdform stdform2" method="post" action="" id="updatePaperType">
                <input type = "hidden" name = "paperType.id" value = "${paperType.id}">
                <p>
                    <label><em style="color: red;">*</em>封条类型名称</label>
                    <span class="field"><input type="text" name="paperType.name" id="name" class="longinput" value = "${paperType.name}"/></span>
                </p>
            </form>
            <c:if test = "${flag == 0}">
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit();return false;">保 存</button>
                    <input type="reset" class="reset radius2" value="重 置" onclick="resetData()"/>
                </p>
            </c:if>
            <br/>
        </div><!--subcontent-->
    </div>
</div>
<script type="text/javascript">
    var flag = '${flag}';
    function addFormSubmit() {
        if(!jQuery("#name").val()){
            alert("请添加封条类型名");
            return;
        }
        var data = jQuery("#updatePaperType").serialize();
        jQuery.ajax({
            url: "${ctx}/admin/oa/updatePaperType.json",
            data: data,
            type: "post",
            dataType: "json",
            async: false,
            cache : false,
            success: function (result) {
                if (result.code == "0") {
                    alert(result.message);
                    window.location.href = "${ctx}/admin/oa/queryAllPaperType.json";
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
        if (flag != 0) {
            jQuery(".longinput").attr("disabled", "disabled");
        }
    });
</script>
</body>
</html>