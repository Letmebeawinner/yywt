<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>新建课题模板</title>
    <link type="text/css" rel="stylesheet" href='${ctx}/static/uploadify/uploadify.css'/>
    <script type="text/javascript" src="${ctx}/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/uploadify/upload-file.js"></script>

    <script type="text/javascript">
        jQuery(function () {
            uploadFile("uploadFile", true, "myFile", imagePath, callbackFile);
        });

        function callbackFile(data) {
            data = data.substr(2);
            data = data.substr(0, data.length - 2);
            jQuery("#fileUrl").val(data);
            jQuery("#file").html('已上传：' + jQuery(".fileName").html());
        }

        function save() {
            if (jQuery("input[name='title']").val() === "") {
                alert("请填写标题内容")
                return false
            }
            if (jQuery("input[name='url']").val() === "") {
                alert("请上传课题模板")
                return false
            }
            var param = jQuery("#bill").serialize()
            jQuery.ajax({
                url: '/admin/jiaowu/ky/projectTemplateManagement/doSave.json',
                type: 'post',
                data: param,
                dataType: 'json',
                success: function (result) {
                    alert(result.message)
                    location.href = document.referrer
                }
            });
        }
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">新建课题模板</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来新建课题模板<%--，其中审批文件为保留部门批示笔记的文件（照片等形式）--%><br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="bill">
                <p>
                    <label><em style="color: red;">*</em>标题:</label>
                    <span class="field">
						 <input type="text" name="title" class="longinput"/>
					</span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>课题模板:</label>
                    <span class="field">
						 <input type="hidden" name="url" id="fileUrl"/>
						 <input type="button" id="uploadFile" value="课题模板"/>
						 <center><h4 id="file"></h4></center>
					</span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="save();return false;">添 加</button>
                </p>
            </form>
            <br/>
        </div><!--subcontent-->
    </div><!--contentwrapper-->
</div><!-- centercontent -->
</body>
</html>