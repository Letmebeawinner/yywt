<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>修改红头公文模板</title>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">修改红头公文模板</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于修改红头公文模板；<br>
            2.<em style="color: red;">*</em>代表必填项；<br>
        </div>
    </div><!--pageheader-->

    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="updateLetterModel">
                <p>
                    <label><em style="color: red;">*</em>模板名称</label>
                    <span class="field"><input type="text" value="${letterModel.modelName}" name="letterModel.modelName"
                                               id="modelName" class="longinput"/></span>
                </p>
                <p>
                    <label>排序值</label>
                    <span class="field"><input type="text" value="${letterModel.sort}" name="letterModel.sort"
                                               class="longinput" id="sort"/></span>
                </p>
                <p>
                    <label>编辑文档</label>
                    <span class="field"><a href="javascript:void (0)"
                                           onclick="openPageOffice(${letterModel.id})">编辑文档</a></span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <input type="hidden" value="${letterModel.fileName}" name="letterModel.fileName"/>
                    <input type="hidden" value="${letterModel.fileUrl}" name="letterModel.fileUrl"/>
                    <input type="hidden" value="${letterModel.id}" name="letterModel.id"/>
                    <button class="submit radius2" onclick="addFormSubmit();return false;">保 存</button>
                </p>
            </form>
            <br/>
        </div>
    </div>
</div>
<script type="text/javascript" src="/pageoffice.js" id="po_js_main"></script>
<script type="text/javascript">
    jQuery(function () {
        jQuery(".pobmodal-overlay").hide();
    })

    function openPageOffice(modelId) {
        var url = "${ctx}/open/oaModelWord.json?modelId=" + modelId;
        window.location.href = "javascript:POBrowser.openWindow('" + url + "','width=1200px;height=800px;')";
    }
    function addFormSubmit() {
        if (!jQuery("#modelName").val()) {
            alert("请填写模板名称");
            return;
        }

        var data = jQuery("#updateLetterModel").serialize();
        jQuery.ajax({
            url: "${ctx}/admin/oa/updateLetterModel.json",
            data: data,
            type: "post",
            dataType: "json",
            async: false,
            cache: false,
            success: function (result) {
                if (result.code == "0") {
                    alert(result.message);
                    window.location.href = "${ctx}/admin/oa/letterModelList.json";
                } else {
                    alert(result.message);
                }
            }
        });
    }

</script>
</body>
</html>