<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <c:if test = "${flag == 0}">
        <title>修改新闻类型信息</title>
    </c:if>
    <c:if test = "${flag == 1}">
        <title>查看新闻类型信息</title>
    </c:if>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <c:if test = "${flag == 0}">
            <h1 class="pagetitle">修改新闻类型信息</h1>
           <font style="margin-left: 20px;">1.<em style="color: red;">*</em>代表必填项；</font> <br>
        </c:if>
        <c:if test = "${flag == 1}">
            <h1 class="pagetitle">查看新闻类型信息</h1>
            <font style="margin-left: 20px;">1.<em style="color: red;">*</em>代表必填项；</font> <br>
        </c:if>
    </div><!--pageheader-->
    <div style="margin-left: 20px;">
        <c:if test = "${flag == 0}">
            2.本页面用于新闻信息修改；<br>
        </c:if>
        <c:if test = "${flag == 1}">
            2.本页面用于新闻信息查看；<br>
        </c:if>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="updateNewsType">
                <p>
                    <label><em style="color: red;">*</em>新闻类型名称</label>
                    <span class="field"><input type="text" name="newsType.name" id="name" class="longinput" value = "${newsType.name}"/></span>
                </p>
                <input type="hidden" name="newsType.id" value="${newsType.id}">
                <c:if test="${flag == 0}">
                    <p class="stdformbutton" style="text-align: center">
                        <button class="submit radius2" onclick="addFormSubmit();return false;">保 存</button>
                        <input type="reset" class="reset radius2" value="重 置" onclick="resetData()"/>
                    </p>
                </c:if>
            </form>

            <br/>
        </div><!--subcontent-->
    </div>
</div>
<script type="text/javascript">
    var flag = '${flag}';
    function addFormSubmit() {
        if(!jQuery("#name").val()){
            alert("请添加新闻类型名");
            return;
        }
        var data = jQuery("#updateNewsType").serialize();
        jQuery.ajax({
            url: "${ctx}/admin/oa/updateNewsType.json",
            data: data,
            type: "post",
            dataType: "json",
            async: false,
            success: function (result) {
                if (result.code == "0") {
                    alert(result.message);
                    window.location.href = "${ctx}/admin/oa/queryAllNewsType.json";
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