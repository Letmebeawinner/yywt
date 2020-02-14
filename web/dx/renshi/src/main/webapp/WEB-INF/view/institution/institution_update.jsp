<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>添加奖惩记录</title>
    <script type="text/javascript" src="${ctx}/static/admin/js/employee.js"></script>
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
                    <input type="hidden" name="institution.id" value="${institution.id}" id="instituId"
                           class="longinput"/>
                    <input type="hidden" name="institution.employeeId" value="${institution.employeeId}" id="employeeId"
                           class="longinput"/>
                    <label><em style="color: red;">*</em>姓名</label>
                    <span class="field">
                    <a href="javascript:void(0)" class="dialog-btn btn "
                       onclick="selectEmployee()"><span>选择教职工</span></a>
                    <tt id="employeeName"></tt>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>获奖名称</label>
                    <span class="field"><input type="text" name="institution.title" id="title"
                                               value="${institution.title}"
                                               class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>颁奖单位</label>
                    <span class="field"><input type="text" name="institution.unit" id="unit" class="longinput"
                                               value="${institution.unit}"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>是否发放证书</label>
                    <span class="field">
                        <input type="radio" name="institution.isCertificate" onclick="isCertificate('0')" value="0"
                               <c:if test="${institution.isCertificate==0}">checked</c:if>/>是&nbsp;&nbsp;
                        <input type="radio" name="institution.isCertificate" onclick="isCertificate('1')" value="1"
                               <c:if test="${institution.isCertificate==1}">checked</c:if>/>否&nbsp;
                    </span>
                </p>
                <p class="certificateTime">
                    <label><em style="color: red;">*</em>证书时间</label>
                    <span class="field"><input type="text" name="institution.certificateTime" id="certificateTime"
                                               value="<fmt:formatDate value="${institution.certificateTime}" pattern="yyyy-MM-dd"/>"
                                               class="longinput"/></span>
                </p>
                <p class="picture">
                    <label><em style="color: red;">*</em>证书照片:</label>
                    <span class="field">
						 <input type="hidden" name="institution.picture" id="fileUrl" value="${institution.picture}"/>
						 <input type="button" id="uploadFile" value="上传文件"/><a onclick="upFile()"
                                                                               href="javascript:void(0)">上传</a>
						 <tt id="file" style="color: red"></tt>
					</span>
                </p>
                <p>
                    <label>备注</label>
                    <span class="field"><textarea cols="80" rows="5" name="institution.explains" id="explains"
                                                  class="longinput">${institution.explains}</textarea></span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit();return false;">修改</button>
                    <input type="reset" class="reset radius2" value="重 置" onclick="resetData()"/>
                </p>
            </form>
            <br/>
        </div>
    </div>
</div>

<script type="text/javascript">

    function isCertificate(type) {
        if (type == 1) {
            jQuery(".certificateTime").hide();
            jQuery(".picture").hide();
        } else {
            jQuery(".certificateTime").show();
            jQuery(".picture").show();
        }
    }

    function addFormSubmit() {
        if (jQuery("#employeeId").val() == "") {
            alert("请选择教职工");
            return false;
        }
        if (jQuery("#title").val() == "") {
            alert("请填写获奖名称");
            return;
        }
        if (jQuery("#unit").val() == "") {
            alert("请填写颁奖单位");
            return;
        }
        var checked = false;
        jQuery("[name='institution.isCertificate']").each(function () {
            if (jQuery(this).is(':checked')) {
                if (jQuery(this).val() == 0) {
                    if (jQuery("#certificateTime").val() == "") {
                        alert("请填写证书时间");
                        checked = true;
                        return false;
                    }

                    if (jQuery("#fileUrl").val() == "") {
                        alert("请上传证书照片");
                        checked = true;
                        return false;
                    }
                }
            }
        });

        if (checked) {
            return false;
        }

        // if (jQuery("#certificateTime").val() == "") {
        //     alert("请填写证书时间");
        //     return;
        // }
        if (jQuery("#reason").val() == "") {
            alert("请添加备注");
            return;
        }
        jQuery.ajax({
            url: "${ctx}/admin/rs/updateInstitution.json",
            data: jQuery("#addinstitution").serialize(),
            type: "post",
            dataType: "json",
            async: false,
            success: function (result) {
                if (result.code == "0") {
                    alert(result.message);
                    window.location.href = "${ctx}/admin/rs/getInstitutionList.json";
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

    isCertificate('${institution.isCertificate}');

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