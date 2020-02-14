<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>>修改文章</title>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">修改文章</h1>
    </div>
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于文章信息修改；<br>
        2.<em style="color: red;">*</em>代表必填项；<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="updateArticle">
                <p>
                    <label><em style="color: red;">*</em>文章名称</label>
                    <span class="field"><input type="text" name="article.name" id="name" value="${article.name}" class="longinput"/>
                    <input type="hidden" name="article.id" id="id" value="${article.id}" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>文章类别</label>
                    <span class="field">
                        <select name = "article.type_id" id = "articleTypeId">
                            <option value = "">请选择文章类别</option>
                            <c:forEach items = "${articleTypeList}" var = "articleType">
                                <option value = "${articleType.id}" <c:if test="${article.type_id==articleType.id}">selected="selected"</c:if>>${articleType.type_name}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>
                <p>
                    <label>文章内容</label>
                    <span class="field"><textarea cols="80" rows="5" name="article.content"  class="longinput">${article.content}</textarea></span>
                </p>
            </form>
            <p class="stdformbutton" style="text-align: center">
                <button class="submit radius2" onclick="addFormSubmit();return false;">保 存</button>
                <input type="reset" class="reset radius2" value="重 置" onclick="resetData()"/>
            </p>
            <br/>
        </div>
    </div>
</div>
<script type="text/javascript">
    function addFormSubmit() {
        if(!jQuery("#name").val()){
            alert("请添加文章名");
            return;
        }
        if(!jQuery("#articleTypeId").val()){
            alert("请选择文章类别");
            return;
        }
        var data = jQuery("#updateArticle").serialize();
        jQuery.ajax({
            url: "${ctx}/admin/oa/updateArticle.json",
            data: data,
            type: "post",
            dataType: "json",
            async: false,
            cache : false,
            success: function (result) {
                if (result.code == "0") {
                    alert(result.message);
                    window.location.href = "${ctx}/admin/oa/queryAllArticle.json";
                } else {
                    alert(result.message);
                }
            }
        });
    }

    function resetData(){
        jQuery(".longinput").val("");
    }
</script>
</body>
</html>