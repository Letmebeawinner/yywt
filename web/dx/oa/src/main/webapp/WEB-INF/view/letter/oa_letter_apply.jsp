<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>公文申请</title>
    <%--<link rel="stylesheet" type="text/css" href="${ctx}/kindeditor/themes/default/default.css"/>--%>
    <%--<script type="text/javascript" src="${ctx}/kindeditor/kindeditor-all.js"></script>--%>
    <style>
        #uploadFile-button .uploadify-button-text {
            color: #fff;
        }
    </style>

    <script type="text/javascript" src="/pageoffice.js" id="po_js_main"></script>
    <script type="text/javascript" charset="utf-8" src="/static/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="/static/ueditor/ueditor.all.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="/static/ueditor/lang/zh-cn/zh-cn.js"></script>
    <link type="text/css" rel="stylesheet" href='${ctx}/static/uploadify/uploadify.css'/>
    <script type="text/javascript" src="${ctx}/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/uploadify/upload-file.js"></script>


    <script type="text/javascript">
        var processDefinitionId = new Date().getTime();

        function addFormSubmit() {
            jQuery("#timeStamp").val(processDefinitionId);
            if (!jQuery("#reason").val()) {
                alert("文件标题不能为空");
                return;
            }
            if (!jQuery("#sendToUnit").val()) {
                alert("发送单位不能为空");
                return;
            }
            if (!jQuery("#context").val()) {
                alert("内容摘要不能为空");
                return;
            }
            var isflg = true;
            jQuery.ajax({
                url: "${ctx}/ajax/judgmentContent.json",
                data: {"processDefinitionId": processDefinitionId},
                type: "post",
                dataType: "json",
                async: false,
                cache: false,
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        isflg = false;
                    }
                }
            });
            if (isflg == false) {
                return false;
            }
            var data = jQuery("#addLetter").serialize();
            jQuery.ajax({
                url: "${ctx}/admin/oa/letter/process/start.json",
                data: data,
                type: "post",
                dataType: "json",
                async: false,
                cache: false,
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

        //        jQuery(function (){//编辑器初始化
        //            initKindEditor_addblog('context', '876px', '400px');
        //        });
        //添加富文页面编辑器
        function initKindEditor_addblog(id, width, height) {
            EditorObject = KindEditor.create('textarea[id=' + id + ']', {
                resizeType: 1,
                filterMode: false,// true时过滤HTML代码，false时允许输入任何代码。
                allowPreviewEmoticons: false,
                allowUpload: true,// 允许上传
                urlType: 'domain',// absolute
                newlineTag: 'br',// 回车换行br|p
                width: width,
                height: height,
                minWidth: '10px',
                minHeight: '10px',
                uploadJson: 'http://127.0.0.1:8083' + '&param=article',// 图片上传路径
                afterBlur: function () {
                    this.sync();
                },
                allowFileManager: false,
                items: ['source', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor',
                    'bold', 'italic', 'underline', 'formatblock', 'lineheight', 'removeformat', '|',
                    'justifyleft', 'justifycenter', 'justifyright',
                    'insertorderedlist', 'insertunorderedlist', '|', 'emoticons',
                    'image', 'link', 'plainpaste']
            });
        }

        function resetData() {
            jQuery(".longinput").val("");
        }

        function openPageOffice() {
            var url = "${ctx}/open/oaWord.json?processDefinitionId=" + processDefinitionId + "&type=1"
            window.location.href = "javascript:POBrowser.openWindow('" + url + "','width=1200px;height=800px;')";

        }

        jQuery(function () {
            jQuery("#pobmodal-dialog").hide()
        })

    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="background: #fff;">
        <h1 class="page-list" style="text-align: center;">
            中共贵阳市委党校拟文稿纸
        </h1>
    </div>
    <div id="contentwrapper" class="contentwrapper user-car">
        <div id="basicform" class="subcontent">
            <div class="testtle-tables">
                <form id="addLetter" method="post">
                    <table border="1">
                        <tr>
                            <td class="pt text_controller_center" style="width:12%;">发文号</td>
                            <td style="width:38%;"><input type="text" name="oaLetter.letterNo"></td>
                            <td class="pt" style="width:5%;">密级</td>
                            <td wstyle="width:45%;"><input type="text" name="oaLetter.secretLevel"></td>
                        </tr>
                        <tr>
                            <td class="pt" colspan="4" style="text-align: left;padding-left: 40px; color:#666;"><tt>
                                <small class="c-red" style="display: inline-block; vertical-align: top">*</small>
                                标题：</tt><span><textarea name="oaLetter.reason" id="reason" style="width:88%;"
                                                          cols="20"
                                                          rows="5"></textarea></span></td>
                        </tr>
                        <tr>
                            <td colspan="2" class="text_controller_center">主办单位：<span>${departmentName}</span></td>
                            <td rowspan="2" colspan="2" class="text_controller_center">时&nbsp;&nbsp;&nbsp;&nbsp;间：<span><fmt:formatDate
                                    value="${nowDate}" pattern="yyyy 年 MM 月dd 日 HH:mm"></fmt:formatDate></span></td>
                        </tr>
                        <tr>
                            <td colspan="2" class="text_controller_center">
                                拟&nbsp;&nbsp;稿&nbsp;&nbsp;人：<span>${applyName}</span></td>
                        </tr>
                        <tr>
                            <td class="pt text_controller_center" colspan="2">部门领导核稿：
                                <textarea style="width:88%;" cols="20" rows="5" readonly></textarea>
                            </td>
                            <td class="pt text_controller_center" colspan="2">分管校领导意见：
                                <textarea  style="width:88%;" cols="20" rows="5" readonly></textarea>
                            </td>
                        </tr>
                        <tr>
                            <td class="pt text_controller_center" colspan="2">会&nbsp;&nbsp;&nbsp;&nbsp;签：
                                <textarea  style="width:95%;" cols="20" rows="5" readonly></textarea>
                            </td>
                            <td class="pt text_controller_center" colspan="2">办公室主任：
                                <textarea  style="width:88%;" cols="20" rows="5" readonly></textarea>
                            </td>
                        </tr>
                        <%--<tr>--%>
                        <%--<td colspan="2" class="text_controller_center">核&nbsp;&nbsp;&nbsp;&nbsp;稿：</td>--%>
                        <%--<td colspan="2" class="text_controller_center">会&nbsp;&nbsp;&nbsp;&nbsp;签：</td>--%>
                        <%--</tr>--%>
                        <tr>
                            <td class="pt" colspan="4" style="text-align: left;padding-left: 40px; color:#666;">
                            <tt>签&nbsp;&nbsp;&nbsp;&nbsp;发：</tt>
                                <textarea  id="" style="width:88%;" cols="20" rows="5" readonly></textarea>
                            </td>
                        </tr>
                        <tr>
                            <td class="pt" colspan="4" style="text-align: left;padding-left: 40px; color:#666;"><tt>
                                <small class="c-red" style="display: inline-block; vertical-align: top">*</small>
                                发送单位：</tt><span><textarea name="oaLetter.sendToUnit" id="sendToUnit" style="width:88%;"
                                                          cols="10" rows="5"></textarea></span></td>
                        </tr>
                        <tr>
                            <td class="pt" colspan="4" style="text-align: left;padding-left: 40px; color:#666;"><tt>
                                <small class="c-red" style="display: inline-block; vertical-align: top">*</small>
                                内容摘要：</tt><span><textarea name="oaLetter.context" id="context" style="width:88%;"
                                                          cols="10" rows="5">${oaLetter.context}</textarea></span></td>
                        </tr>
                        <tr>
                            <td class="pt" colspan="4" style="text-align: left;padding-left: 40px; color:#666;"><tt>
                                <small class="c-red" style="display: inline-block; vertical-align: top">*</small>
                                公文内容(在线Word)：</tt><span>
                                 <div class="text-center m-t-sm">
                                         <a href="javascript:void(0)"
                                            onclick="openPageOffice()" class="ZL-generate">创建公文内容</a>
                                  </div>
                              </span></td>
                        </tr>

                    </table>
                    <input type="hidden" name="processDefinitionId" value="${processDefinition.id}">
                    <input type="hidden" id="timeStamp" name="oaLetter.timeStamp" value="">
                    <input type="hidden" value="<fmt:formatDate value="${nowDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                           name="oaLetter.startTime"/>
                    <input type="hidden" value="1" name="oaLetter.type"/>
                </form>
                <div class="buttons" style="text-align: center;margin-top:20px;margin-bottom: 20px">
                    <a class="submit radius2" onclick="addFormSubmit()" style="cursor: pointer">提交</a>
                </div>
            </div>
            <br/>
        </div>
    </div>
</div>


</body>
</html>