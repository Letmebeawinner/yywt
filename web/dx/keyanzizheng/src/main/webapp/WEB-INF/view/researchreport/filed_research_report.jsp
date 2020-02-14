<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>归入档案</title>
    <link type="text/css" rel="stylesheet" href='${ctx}/static/uploadify/uploadify.css'/>
    <script type="text/javascript" src="${ctx}/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/uploadify/upload-file.js"></script>
    <script type="text/javascript">
        /**
         * 时间控件
         */
        jQuery(function () {
            laydate.skin('molv');
            laydate({
                elem: '#archivedate',
                format: 'YYYY-MM-DD hh:mm:ss',
                istime: true
            });
            uploadFile("uploadFile", false, "myFile", imagePath, callbackFile);
        });


        function callbackFile(data) {
            data = data.substr(2);
            data = data.substr(0, data.length - 2);
            jQuery("#fileUrl").val(data);
            jQuery("#file").html('附件已上传');
        }

        function addFormSubmit() {
            var typeId = jQuery("#typeId").val();
            var danghao = jQuery("#danghao").val();
            var jianhao = jQuery("#jianhao").val();
            var autograph = jQuery("#autograph").val();
            var wenhao = jQuery("#wenhao").val()
            var archivedate = jQuery("#archivedate").val();
            var pages = jQuery("#pages").val();
            var orginzation = jQuery("#orginzation").val();

            if (typeId == 0 || typeId == null) {
                alert("请选择分类");
                return;
            }
            if (danghao == "" || danghao == null) {
                alert("请添加档号");
                return;
            }
            if (jianhao == "" || jianhao == null) {
                alert("请添加件号");
                return;
            }
            if (autograph == "" || autograph == null) {
                alert("请添加题名");
                return;
            }

            if (wenhao == "" || wenhao == null) {
                alert("请添加文号");
                return;
            }

            if (archivedate == "" || archivedate == null) {
                alert("请添加日期");
                return;
            }
            if (pages == "" || pages == null) {
                alert("请添加页数");
                return;
            }
            if (orginzation == "" || orginzation == null) {
                alert("请添加机构或问题");
                return;
            }
            if (!jQuery("#fileUrl").val()) {
                alert("附件不能为空");
                return;
            }
            jQuery.ajax({
                url: "${ctx}/admin/ky/rr/doSaveArchive.json",
                data: jQuery('#addFormSubmit').serialize(),
                type: "post",
                dataType: "json",
                async: false,
                success: function (result) {
                    alert(result.message);
                    if (result.code === "0") {
                        window.location.href = "${ctx}/admin/ky/rr/researchReportLibrary.json?resultType=1&isArchive=1";
                    }
                }
            });
        }


    </script>
</head>

<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">归入档案</h1>
    </div>
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于归入档案；<br>
        2.<em style="color: red;">*</em>代表必填项；<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">

                <p>
                    <label><em style="color: red;">*</em>档案分类</label>
                    <span class="field">
                        <select name="archive.typeId" id="typeId" class="longinput">
                            <option value="0">--请选择--</option>
                            <c:forEach items="${linkedHashMapList}" var="arch">
                                <option value="${arch.id}">${arch.name}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>档号</label>
                    <span class="field"><input type="text" name="archive.danghao" id="danghao"
                                               class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>档案件号</label>
                    <span class="field"><input type="text" name="archive.jianhao" id="jianhao"
                                               class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>责任者</label>
                    <span class="field"><input type="text" name="archive.author" id="author" value="贵阳市委党校"
                                               class="longinput" readonly/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>题名</label>
                    <span class="field"><input type="text" name="archive.autograph" id="autograph"
                                               class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>文号</label>
                    <span class="field"><input type="text" name="archive.wenhao" id="wenhao" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>时间</label>
                    <span class="field"><input type="text" name="archive.archivedate" id="archivedate" class="longinput"
                                               readonly/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>页数</label>
                    <span class="field"><input type="text" name="archive.pages" id="pages" class="longinput"
                                               placeholder="请输入数字" maxlength="8"
                                               onkeyup="value=this.value.replace(/\D+/g,'')"
                                               style="ime-mode:Disabled"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>机构或问题</label>
                    <span class="field"><input type="text" name="archive.orginzation" id="orginzation"
                                               class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>附件:</label>
                    <span class="field">
						 <input type="hidden" name="archive.file" id="fileUrl"/>
						 <input type="hidden" name="archive.fileName" id="fileName"/>
						 <input type="button" id="uploadFile" value="上传附件"/>
						 <center><h4 id="file"></h4></center>
					</span>
                </p>
                <p>
                    <label>备注</label>
                    <span class="field">
                        <textarea rows="10" cols="80" class="longinput" name="archive.description"></textarea>
                    </span>
                </p>

                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit();return false;">提交保存</button>
                </p>

                <input type="hidden" name="rqId" value="${rqId}">
            </form>
            <br/>
        </div>
    </div>
</div>

</body>
</html>