<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>修改公文</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/kindeditor/themes/default/default.css"/>
    <script type="text/javascript" src="${ctx}/kindeditor/kindeditor-all.js"></script>
    <script type="text/javascript">
        var flag = '${flag}';
        function addFormSubmit() {
            if (!jQuery("#name").val()) {
                alert("请添加公文名称");
                return;
            }
            if (!jQuery("#typeId").val()&&jQuery("#typeId").val()!=0) {
                alert("请选择公文类型");
                return;
            }

            var editor = KindEditor.create("textarea[name='letter.context']")
            editor.sync()
            if (editor.html().trim().length < 1) {
                alert("请输入公文内容")
                return false
            }

            var data = jQuery("#updateLetter").serialize();
            jQuery.ajax({
                url: "${ctx}/admin/oa/updateLetter.json",
                data: data,
                type: "post",
                dataType: "json",
                async: false,
                cache: false,
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.message);
                        window.location.href = "${ctx}/admin/oa/queryAllLetter.json";
                    } else {
                        alert(result.message);
                    }
                }
            });
        }

        function resetData() {
            jQuery(".longinput").val("");
        }
        jQuery(function () {
            if (flag != 0) {
                jQuery(".longinput").attr("disabled", "disabled");
            }
        });


        jQuery(function () {//编辑器初始化
            initKindEditor_addblog('content', '876px', '400px');
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
                items: ['source', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor',
                    'bold', 'italic', 'underline', 'formatblock', 'lineheight', 'removeformat', '|',
                    'justifyleft', 'justifycenter', 'justifyright',
                    'insertorderedlist', 'insertunorderedlist', '|', 'emoticons',
                    'image', 'link', 'plainpaste']
            });
        }

    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">修改公文信息</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于修改公文信息；<br>
            2.<em style="color: red;">*</em>代表必填项；<br>
        </div>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="updateLetter">
                <p>
                    <input type="hidden" name="letter.id" value="${letter.id}">
                    <label><em style="color: red;">*</em>公文名称</label>
                    <span class="field"><input type="text" name="letter.title" id="name" class="longinput"
                                               value="${letter.title}"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>公文模版类型</label>
                    <span class="field">
                        <select name="letter.templateTypeId" id="typeId">
                            <option value="1" <c:if test="${letter.templateTypeId==1}">selected</c:if>>中共贵阳市委党校文件</option>
                            <option value="2" <c:if test="${letter.templateTypeId==2}">selected</c:if>>贵阳社会主义学院</option>
                            <option value="3" <c:if test="${letter.templateTypeId==3}">selected</c:if>>校委会议纪要</option>
                            <option value="4" <c:if test="${letter.templateTypeId==4}">selected</c:if>>工作简报</option>
                        </select>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>公文内容</label>
                    <span class="field">
                        <textarea cols="10" rows="50" id="content" name="letter.context">
                            ${letter.context}
                        </textarea>
                    </span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit();return false;">保 存</button>
                </p>
            </form>
            <br/>
        </div>
    </div>
</div>

</body>
</html>