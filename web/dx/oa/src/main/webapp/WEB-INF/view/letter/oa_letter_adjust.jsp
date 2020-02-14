<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>公文申请调整</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/kindeditor/themes/default/default.css"/>
    <script type="text/javascript" src="${ctx}/kindeditor/kindeditor-all.js"></script>
    <script type="text/javascript">
        function addFormSubmit(flag) {
            jQuery("#audit").val(flag);
            if(!jQuery("#name").val()){
                alert("请添加公文名");
                return;
            }
            var data = jQuery("#addLetter").serialize();
            jQuery.ajax({
                url: "${ctx}/admin/oa/letterApplyAudit.json",
                data: data,
                type: "post",
                dataType: "json",
                async: false,
                cache : false,
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "${ctx}/admin/oa/task/to/claim/list.json";
                    } else {
                        alert(result.message);
                    }
                }
            });
        }

        jQuery(function (){//编辑器初始化
            initKindEditor_addblog('context', '876px', '400px');
        });
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
                items: ['source','fontname', 'fontsize', '|', 'forecolor', 'hilitecolor',
                    'bold', 'italic', 'underline', 'formatblock', 'lineheight', 'removeformat', '|',
                    'justifyleft', 'justifycenter', 'justifyright',
                    'insertorderedlist', 'insertunorderedlist', '|', 'emoticons',
                    'image', 'link', 'plainpaste']
            });
        }

        function resetData(){
            jQuery(".longinput").val("");
        }
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">公文申请</h1>
    </div>
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于公文申请调整；<br>
        2.<em style="color: red;">*</em>代表必填项；<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">

            <form class="stdform stdform2" method="post" action="" id="addLetter">
                <p>
                    <label>来文单位</label>
                    <span class="field">
                        <input type = "text" name = "oaLetter.applyCompany" class="longinput">
                    </span>
                </p>
                <p>
                    <label>文号</label>
                    <span class="field">
                        <input type = "text" name = "oaLetter.letterNo" class="longinput">
                    </span>
                </p>
                <p>
                    <label>序号</label>
                    <span class="field">
                        <input type = "text" name = "oaLetter.orderNo" class="longinput">
                    </span>
                </p>
                <p>
                    <label>密级</label>
                    <span class="field">
                        <input type = "text" name = "oaLetter.secretLevel" class="longinput">
                    </span>
                </p>
                <p>
                    <label>紧急程度</label>
                    <span class="field">
                        <input type = "text" name = "oaLetter.urgentLevel" class="longinput">
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>公文名称</label>
                    <span class="field"><input type="text" name="oaLetter.title" id="name" class="longinput"/></span>
                </p>
                <p>
                    <label>公文内容</label>
                    <span class="field">
                        <textarea cols="10" rows="50" id="context" name="oaLetter.context"></textarea>
                    </span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit(0);return false;">同意</button>
                    <button class="submit radius2" onclick="addFormSubmit(2);return false;">驳回</button>
                    <button class="submit radius2" onclick="history.go(-1);return false;">返回</button>
                </p>

                <input type = "hidden" name = "taskId" value = "${task.id}" id = "taskId">
                <input type = "hidden" name = "oaLetter.processInstanceId" value = "${oaLetter.processInstanceId}" id = "processInstanceId">
                <input type = "hidden" name = "oaLetter.audit" id = "audit">
            </form>
            <br/>
        </div>
    </div>
    <jsp:include page="/WEB-INF/view/common/process_transfer.jsp"></jsp:include>
</div>


</body>
</html>