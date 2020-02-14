<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>添加奖惩记录</title>
    <link type="text/css" rel="stylesheet" href='/static/uploadify/uploadify.css'/>
    <script type="text/javascript" src="/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="/static/uploadify/upload-file.js"></script>
    <script type="text/javascript">
        /**
         * 时间控件
         */
        jQuery(function () {
            laydate.skin('molv');
            laydate({
                elem: '#certificateTime',
                format: 'YYYY-MM-DD'
            });
        });
    </script>

</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">新建奖惩记录</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来添加教职工奖惩记录<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addinstitution">
                <p>
                    <input type="hidden" name="institution.employeeId" id="employeeId" class="longinput" value="${employee.id}"/>
                    <label><em style="color: red;">*</em>获奖名称</label>
                    <span class="field"><input type="text" name="institution.title" id="title"
                                               class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>颁奖单位</label>
                    <span class="field"><input type="text" name="institution.unit" id="unit" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>是否发放证书</label>
                    <span class="field">
                        <input type="radio" name="institution.isCertificate" checked="checked" value="0"/>是&nbsp;&nbsp;
                        <input type="radio" name="institution.isCertificate" value="1"/>否&nbsp;
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>证书时间</label>
                    <span class="field"><input type="text" name="institution.certificateTime" id="certificateTime"
                                               class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>证书照片:</label>
                    <span class="field">
						 <input type="hidden" name="institution.picture" id="fileUrl"/>
						 <input type="button" id="uploadFile" value="上传文件"/><a onclick="upFile()"
                                                                               href="javascript:void(0)">上传</a>
						 <tt id="file" style="color: red"></tt>
					</span>
                </p>
                <p>
                    <label>备注</label>
                    <span class="field"><textarea cols="80" rows="5" name="institution.explains" id="explains"
                                                  class="longinput"></textarea></span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit();return false;">添 加</button>
                    <input type="reset" class="reset radius2" value="重 置" onclick="resetData()"/>
                </p>
            </form>
            <br/>
        </div>
    </div>
</div>

<script type="text/javascript">
    function addFormSubmit() {

        if (jQuery("#title").val() == "") {
            alert("请填写获奖名称");
            return;
        }
        if (jQuery("#unit").val() == "") {
            alert("请填写颁奖单位");
            return;
        }
        if (jQuery("#certificateTime").val() == "") {
            alert("请填写证书时间");
            return;
        }
        if (jQuery("#reason").val() == "") {
            alert("请添加备注");
            return;
        }
        jQuery.ajax({
            url: "${ctx}/admin/rs/addInstitution.json",
            data: jQuery("#addinstitution").serialize(),
            type: "post",
            dataType: "json",
            async: false,
            success: function (result) {
                if (result.code == "0") {
                    alert(result.message);
                    window.location.href = "${ctx}/admin/rs/getSelfInstitutionList.json";
                } else {
                    alert(result.message);
                }
            }
        });
    }

    function resetData() {
        jQuery(".longinput").val("");
    }
</script>
<script type="text/javascript">
    var fileServicePath = '${fileServicePath}';

    function callbackFile(data) {
        data = data.substr(2);
        data = data.substr(0, data.length - 2);
        jQuery("#fileUrl").val(data);
        jQuery("#file").html('已上传：' + jQuery(".fileName").html());
    }

    function upFile() {
        jQuery("#uploadFile").uploadify('upload');
    }

    jQuery(function () {
        uploadFile("uploadFile", false, "myFile", fileServicePath, callbackFile);
    });
</script>
</body>
</html>