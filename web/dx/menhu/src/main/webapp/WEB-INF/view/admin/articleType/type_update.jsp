<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>资讯类型修改</title>
    <script type="text/javascript">
        function addFormSubmit(){
            var name = jQuery("#name").val();
            if(name == null || name == undefined || name ==''){
                alert("请填写类型名称");
                return;
            }

            jQuery.ajax({
                type: "POST",
                dataType:"json",
                url:ctx+"/admin/menhu/articleType/updateArticleType.json",
                data:jQuery('#archivesTypeForm').serialize(),
                cache: false,
                async: false,
                error: function(request) {
                    alert("网络异常，请稍后再试");
                },
                success: function(result) {
                    if(result.code==0){
                        window.location.href=ctx+result.data;
                    }else{
                        alert("网络异常，请稍后再试");
                    }
                }
            });
        }
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">资讯类型修改</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <form id="archivesTypeForm" class="stdform stdform2"  method="post" action="">

            <p>
            <input type="hidden" value="${articleType.id}" name="articleType.id">
                <label>名称:</label>
                <span class="field"><input type="text" id="name" name="articleType.name" value="${articleType.name}"  class="longinput" /></span>
            </p>
            <p>
                <label>排序:</label>
                <span class="field"><input type="text" id="sort" name="articleType.sort"  onkeyup="value=value.replace(/[^\d]/g,'')"  value="${articleType.sort}" class="longinput" /></span>
            </p>
            <p style="text-align: center">
                <button onclick="addFormSubmit();return false" class="stdbtn">保存</button>
            </p>
        </form>
    </div>
</div>
</body>
</html>
