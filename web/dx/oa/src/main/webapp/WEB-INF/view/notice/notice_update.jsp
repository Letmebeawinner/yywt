<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>修改公告</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/kindeditor/themes/default/default.css"/>
    <script type="text/javascript" src="${ctx}/kindeditor/kindeditor-all.js"></script>
    <script type="text/javascript">
        function addFormSubmit() {
            var title = jQuery("#title").val();
            if (title == "" || title == null) {
                alert("请添加名称");
                return;
            }
            jQuery.ajax({
                url: "${ctx}/admin/oa/updateNotice.json",
                data:jQuery("#addFormSubmit").serialize(),
                type: "post",
                dataType: "json",
                async: false,
                success: function (result) {
                    if (result.code=="0") {
                        alert(result.message);
                        window.location.href = "${ctx}/admin/oa/queryAllNotice.json";
                    } else {
                        alert(result.message);
                    }
                }
            });
        }



        jQuery(function (){//编辑器初始化
            initKindEditor_addblog('ArticleContent', '876px', '400px');
        });
        //添加博文页面编辑器
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

    </script>
</head>

<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">修改公告</h1>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label>分类</label>
                    <span class="field">
                        <select name="notice.typeId">
                            <c:forEach items="${noticeTypeList}" var="noticeType">
                                <option value="${noticeType.id}" <c:if test="${noticeType.id==notice.id}">selected</c:if>>${noticeType.name}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>
                <input type="hidden" name="notice.id" value="${notice.id}">
                <p>
                    <label><em style="color: red;">*</em>标题</label>
                    <span class="field"><input type="text" name="notice.title" id="title" class="longinput" value="${notice.title}"/></span>
                </p>
                <p>
                    <label>关键字</label>
                    <span class="field"><input type="text" name="notice.keyword" class="longinput" value="${notice.keyword}"/></span>
                </p>
                <p>
                    <label>编辑器</label>
                    <span class="field"><textarea cols="80" rows="5" name="notice.context" class="longinput" id="ArticleContent">${notice.context}</textarea></span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit(); return false">提交保存</button>
                </p>
            </form>
            <br/>
        </div>
    </div>
</div>


</body>
</html>