<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>我的获奖申报</title>
    <link type="text/css" rel="stylesheet" href='/static/uploadify/uploadify.css'/>
    <script type="text/javascript" src="/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="/static/uploadify/upload-file.js"></script>
    <script type="text/javascript">
        jQuery(function () {
            uploadFile("uploadFile", true, "myFile", imagePath, callbackFile);

            jQuery("input:radio[name='awardSituation'][value=" + ${map.awardSituation} +"]").prop("checked", true);
            jQuery("select[name='resultForm'] option[value='${map.resultForm}']").attr("selected", "selected");
        });

        function callbackFile(data) {
            data = data.substr(2);
            data = data.substr(0, data.length - 2);
            jQuery("#fileUrl").val(data);
            jQuery("#file").html('已上传：' + jQuery(".fileName").html());
        }

        function problemStatementDeclaration() {
            if (jQuery("input[name='awardTitle']").val() == "") {
                alert("请填写标题内容")
                return false
            }
            if (jQuery("input[name='fileUrlAward']").val() == "") {
                alert("请上传附件")
                return false
            }
            var param = jQuery("#bill").serialize()
            jQuery.ajax({
                url: '/admin/ky/awardDeclaration/add.json',
                type: 'post',
                data: param,
                dataType: 'json',
                success: function (result) {
                    alert(result.message)
                    location.href = document.referrer
                }
            });
        }

        function chgRadio(obj) {
            if (jQuery(obj).val() === "3") {
                jQuery("#aw-1").html("党校系统级")
                jQuery("#aw-2").html("哲学社会成果奖（国家级、省级、市级）")
                jQuery("#aw-3").html("其他")
                jQuery("#aw-4").hide().prev().hide()
            } else {
                jQuery("#aw-1").html("国家级")
                jQuery("#aw-2").html("省级")
                jQuery("#aw-3").html("市级")
                jQuery("#aw-4").show().prev().show()
            }
        }
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">获奖申报</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来部门获奖申报<%--，其中审批文件为保留部门批示笔记的文件（照片等形式）--%><br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="bill">
                <p>
                    <label><em style="color: red;">*</em>标题:</label>
                    <span class="field">
                        ${map.title} &nbsp;
					</span>
                </p>

                <%--新增字段开始--%>
                <p>
                    <label><em style="color: red;">*</em>成果形式</label>
                    <span class="field">
                        <select name="resultForm" id="resultForm" onchange="chgRadio(this)" disabled>
                                <option value="1">论文</option>
                                <option value="2">著作</option>
                                <option value="3">课题</option>
                                <option value="4">内刊</option>
                                <option value="5">调研报告</option>
                                <option value="6">其他</option>
                        </select>
                    </span>
                </p>
                <p>
                    <label>获奖情况</label>
                    <span class="field">
                        <input type="radio" value="1" checked="checked" name="awardSituation" disabled/><span id="aw-1">国家级奖</span>&nbsp;&nbsp;
                        <input type="radio" value="2" name="awardSituation" disabled/><span id="aw-2">省部级奖</span>&nbsp;&nbsp;
                        <input type="radio" value="3" name="awardSituation" disabled/><span id="aw-3">地市级奖</span>&nbsp;&nbsp;
                        <input type="radio" value="4" name="awardSituation" disabled/><span id="aw-4">其他</span>&nbsp;&nbsp;
                    </span>
                </p>
                <p>
                    <label>获奖情况描述
                        <small></small>
                    </label>
                    <span class="field"><textarea cols="80" rows="5" name="digest" id="digest" disabled
                                                  class="longinput">${map.digest}</textarea></span>
                </p>
                <%--新增字段结束--%>
                <p>
                    <label><em style="color: red;">*</em>附件:</label>
                    <span class="field">
						 <a href="${map.url}" class="stdbtn" title="下载申请书" download="">下载申请书</a>
					</span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <input type="reset" class="reset radius2" value="返 回" onclick="javascript:history.back();"/>
                </p>
            </form>
            <br/>
        </div><!--subcontent-->
    </div><!--contentwrapper-->
</div><!-- centercontent -->
</body>
</html>