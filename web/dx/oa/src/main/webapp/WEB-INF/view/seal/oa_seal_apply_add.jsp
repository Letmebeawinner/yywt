<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>印章申请单</title>
    <link type="text/css" rel="stylesheet" href='${ctx}/static/uploadify/uploadify.css'/>
    <link rel="stylesheet" type="text/css" href="${ctx}/kindeditor/themes/default/default.css"/>
    <link type="text/css" rel="stylesheet" href="${basePath}/static/css/plugins/jquery.chosen.css">
    <style>
        #uploadFile-button .uploadify-button-text{
            color: #fff;
        }
    </style>

</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="background: #fff;">
        <h1 class="pagetitle tac">印章使用审批表</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper user-car">
        <div id="basicform" class="subcontent">
            <div class="testtle-tables">
                <form id = "addSealApply" method="post">
                    <table border="1">
                        <tr>
                            <td class="pt text_controller_center" style="width:15%;"><small class="c-red" style = "display: inline-block; vertical-align: top">*</small>用印部门</td>
                            <td class="pt" style="width:15%;"><span style="padding: 0;"><textarea style="width:95%;" cols="20" rows="1">${departmentName}</textarea></span></td>
                            <td class="pt text_controller_center" style="width:15%;"><small class="c-red" style = "display: inline-block; vertical-align: top">*</small>用印人</td>
                            <td class="pt" style="width:15%;"><span style="padding: 0;"><textarea style="width:95%;" cols="20" rows="1">${applyName}</textarea></span></td>
                            <td class="pt text_controller_center" style="width:15%;"><small class="c-red" style = "display: inline-block; vertical-align: top">*</small>用印数量</td>
                            <td class="pt" style="width:15%;"><span style="padding: 0;"><textarea id="useSealNum" name="sealApply.useSealNum" style="width:95%;" cols="20" rows="1"></textarea></span></td>
                        </tr>
                        <tr>
                            <td class="pt text_controller_center"><small class="c-red" style = "display: inline-block; vertical-align: top">*</small>用印事由</td>
                            <td class="pt" colspan="3" style="text-align: left; color:#666;">
                                <span style="padding: 0;"><textarea id="reason" name="sealApply.reason" id="reason" style="width:95%;" cols="20" rows="5"></textarea></span>
                            </td>
                            <td class="pt text_controller_center"><small class="c-red" style = "display: inline-block; vertical-align: top">*</small>印章类别</td>
                            <td class="pt">
                                <div class="input-group">
                                    <select id="sealType" data-placeholder="请选择印章类别（可重复选）" name="sealApply.sealType" class="chosen-select" multiple style="width:350px;" tabindex="4">
                                        <%--<option value="">请选择印章类别</option>--%>
                                        <option value="1" hassubinfo="true">中共贵阳市委党校</option>
                                        <option value="2" hassubinfo="true">法人章</option>
                                        <option value="3" hassubinfo="true">贵阳社会主义学院</option>
                                        <option value="4" hassubinfo="true">贵阳行政学院</option>
                                        <option value="5" hassubinfo="true">中共贵阳市委党校机关委员会</option>
                                        <option value="6" hassubinfo="true">正校长章</option>
                                        <option value="7" hassubinfo="true">中共贵阳市委党校纪律监察委员会</option>
                                        <option value="8" hassubinfo="true">贵阳市县级党校体制改革推荐委员会</option>
                                        <option value="9" hassubinfo="true">贵阳市县级党校体制改革推荐委员会办公室</option>
                                    </select>
                                </div>
                                <%--<span style="padding: 0;"><textarea id="useSealNum" name="sealApply.useSealNum" style="width:95%;" cols="20" rows="5"></textarea></span>--%>
                            </td>
                        </tr>
                        <tr>
                            <td class="pt text_controller_center">部门领导意见</td>
                            <td style="text-align: left; color:#666;" class="pt">
                                <span style="padding: 0;"><textarea name="sealApply.departmentOption" style="width:95%;" cols="20" rows="5" disabled></textarea></span>
                            </td>
                            <td class="pt text_controller_center">分管校领导意见</td>
                            <td class="pt"><span style="padding: 0;"><textarea name="sealApply.schoolOption" style="width:95%;" cols="20" rows="5" disabled></textarea></span></td>
                            <td class="pt text_controller_center">常务副校长/副校长（主持工作）意见</td>
                            <td class="pt"><span style="padding: 0;"><textarea name="sealApply.schoolLeaderOption" style="width:95%;" cols="20" rows="5" disabled></textarea></span></td>
                        </tr>
                        <tr>
                            <td class="pt text_controller_center" rowspan="2">办公室主任意见</td>
                            <td colspan="2" rowspan="2" style="text-align: left; color:#666;" class="pt">
                                <span style="padding: 0;"><textarea name="sealApply.officeLeaderOption" style="width:95%;" cols="20" rows="5" disabled></textarea></span>
                            </td>
                            <td class="pt text_controller_center">盖章人</td>
                            <td colspan="2" class="pt"><span style="padding: 0;"><textarea style="width:95%;" cols="20" rows="5" disabled></textarea></span></td>
                        </tr>
                        <tr>
                            <td class="pt text_controller_center"><small class="c-red" style = "display: inline-block; vertical-align: top">*</small>用印时间</td>
                            <td colspan="2" class="pt"><span style="padding: 0;"><input type="text" name = "sealApply.useSealTime"  style="width:90%;" id = "useSealTime" readonly></span></td>
                        </tr>
                        <tr>
                            <td class="pt"><small class="c-red" style = "display: inline-block; vertical-align: top">*</small>附件上传</td>
                            <td colspan="5">
                                <span class="buttons">
                                     <input type="hidden" name="sealApply.fileUrl"  id="fileUrl" />
                                     <input type="hidden" name="sealApply.fileName"   id="fileName" />
                                     <input type="button" id="uploadFile" value="上传附件"/>
                                     <a onclick="upFile()" href="javascript:void(0)" class="upload_sate submit radius2">确认上传</a>
                                     <center><h4  id="file"></h4></center>
                                </span>
                            </td>
                        </tr>
                    </table>
                    <input type = "hidden" name = "processDefinitionId" value = "${processDefinition.id}">
                    <%--<input type = "hidden" value = "${nowDate}" name = "sealApply.startTime"/>--%>
                </form>
                <div class="buttons" style="text-align: center;margin-top:20px;margin-bottom: 20px">
                    <a class="submit radius2" onclick = "addFormSubmit()" style="cursor: pointer">提交</a>
                </div>
            </div>
            <br/>
        </div>
    </div>
</div>

<script type="text/javascript" src="${ctx}/kindeditor/kindeditor-all.js"></script>
<script type="text/javascript" charset="utf-8" src="/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="/static/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8" src="/static/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="${ctx}/static/uploadify/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="${ctx}/static/plugins/chosen.jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/uploadify/upload-file.js"></script>
<script type="text/javascript">

    jQuery(function(){
        laydate.skin('molv');
        laydate({
            elem: '#useSealTime',
            format: 'YYYY-MM-DD'
        });

        uploadFile("uploadFile", false, "myFile", imagePath, callbackFile);
    });
    var config = {
        '.chosen-select': {},
        '.chosen-select-deselect': {
            allow_single_deselect: true
        },
        '.chosen-select-no-single': {
            disable_search_threshold: 10
        },
        '.chosen-select-no-results': {
            no_results_text: 'Oops, nothing found!'
        },
        '.chosen-select-width': {
            width: "95%"
        }
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }

    function addFormSubmit() {

        if (!jQuery("#sealType").val()) {
            alert('印章类别不能为空');
            return;
        }
        if (!jQuery("#reason").val()) {
            alert('用印事由不能为空');
            return;
        }
        if (!jQuery("#useSealNum").val()) {
            alert('用印数量不能为空');
            return;
        }
        if (isNaN(jQuery("#useSealNum").val())) {
            alert('用印数量只能填数字');
            return;
        }
        if (!jQuery("#useSealTime").val()) {
            alert('用印时间不能为空');
            return;
        }
        if (!jQuery("#fileUrl").val()) {
            alert('请上传附件');
            return;
        }
        var data = jQuery("#addSealApply").serialize();
        jQuery.ajax({
            url: "${ctx}/admin/oa/startSealApply.json",
            data: data,
            type: "post",
            dataType: "json",
            async: false,
            cache : false,
            success: function (result) {
                if (result.code == "0") {
                    alert(result.message);
                    window.location.href = "${ctx}/admin/oa/task/history/mine.json";
                } else {
                    alert(result.message);
                }
            }
        });
    }

    function resetData(){
        jQuery(".longinput").val("");
    }


    function callbackFile(data){
        data=data.substr(2);
        data=data.substr(0,data.length-2);
        jQuery("#fileUrl").val(data).show();
        jQuery("#file").html('已上传：' + jQuery(".fileName").html());
        jQuery("#fileName").val(jQuery(".fileName").html());

    }

    function upFile(){
        jQuery("#file").html( '已上传：'+jQuery(".fileName").html());
        jQuery("fileName").val(jQuery(".fileName").html());
        jQuery("#uploadFile").uploadify('upload');
    }
    
    
    function clean(string) {
        if(string==null){
            return "";
        }else {
            return string.trim();
        }
    }

</script>
</body>
</html>