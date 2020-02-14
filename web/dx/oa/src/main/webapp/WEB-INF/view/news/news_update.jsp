<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <c:if test = "${flag == 0}">
        <title>修改新闻</title>
    </c:if>
    <c:if test = "${flag == 1}">
        <title>查看新闻</title>
    </c:if>
    <link rel="stylesheet" type="text/css" href="${ctx}/kindeditor/themes/default/default.css"/>
    <script type="text/javascript" src="${ctx}/kindeditor/kindeditor-all.js"></script>

    <link type="text/css" rel="stylesheet" href='${ctx }/static/uploadify/uploadify.css'/>
    <script type="text/javascript" src="${ctx }/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="${ctx }/static/uploadify/upload-file.js"></script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <c:if test = "${flag == 0}">
            <h1 class="pagetitle">修改新闻信息</h1>
        </c:if>
        <c:if test = "${flag == 1}">
            <h1 class="pagetitle">查看新闻信息</h1>
        </c:if>
    </div><!--pageheader-->
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        <c:if test = "${flag == 0}">
            1.本页面用于新闻信息修改；<br>
            2.<em style="color: red;">*</em>代表必填项；<br>
        </c:if>
        <c:if test = "${flag == 1}">
            1.本页面用于新闻信息查看；<br>
            2.<em style="color: red;">*</em>代表必填项；<br>
        </c:if>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <input type="hidden" name="status" value="${news.status}">
            <input type="hidden" name="canComment" value="${news.canComment}">
            <input type="hidden" name="hot" value="${news.hot}">
            <input type="hidden" name="recommend" value="${news.recommend}">
            <form class="stdform stdform2" method="post" action="" id="updateNews">
                <p>
                    <label><em style="color: red;">*</em>新闻类别</label>
                    <span class="field">
                        <select name = "news.newsTypeId" id = "newsTypeId" class = "longinput">
                              <option value = "">请选择新闻类别</option>
                            <c:forEach items = "${newsTypes}" var = "newsType">
                                <option value = "${newsType.id}" <c:if test = "${news.newsTypeId == newsType.id}">selected = "selected"</c:if>>${newsType.name}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>
                <input type="hidden" name="news.id" value="${news.id}">
                <p>
                    <label><em style="color: red;">*</em>新闻标题</label>
                    <span class="field"><input type="text" name="news.title" id="title" class="longinput"
                                               value="${news.title}"/></span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>副标题</label>
                    <span class="field"><input type="text" name="news.subTitle" id="subTitle" value="${news.subTitle}"
                                               class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>来源</label>
                    <span class="field"><input type="text" name="news.source" id="source" value="${news.source}"
                                               class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>作者</label>
                    <span class="field"><input type="text" name="news.author" id="author" value="${news.author}"
                                               class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>首页图片:</label>
                    <span class="field">
						 <input type="text" name="news.indexImage" value="${news.indexImage}" readonly class="longinput"
                                id="fileUrl" style="display: none"/>
						 <input type="button" id="uploadFile" value="上传文件"/>
						 <a onclick="upFile()" href="javascript:void(0)">上传</a>
						 <center><h4 id="file"></h4></center>
                         <%--<img src="" id="touxiang" style="width: 100px;height: 100px;"/>--%>
					</span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>外部链接</label>
                    <span class="field"><input type="text" name="news.link" id="link" value="${news.link}"
                                               class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>简介</label>
                    <span class="field"><textarea cols="80" rows="5" name="news.brief" class="longinput"
                                                  id="brief">${news.brief}</textarea></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>内容</label>
                    <span class="field"><textarea cols="80" rows="5" name="news.content"  class="longinput" id = "content" >${news.content}</textarea></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>状态</label>
                    <span class="field">
                        <input type="radio" name="news.status" value="2"/>草稿&nbsp;
                        <input type="radio" name="news.status" value="0"/>已删除&nbsp;
                        <input type="radio" name="news.status" value="3"/>归档&nbsp;
                        <input type="radio" name="news.status" value="4"/>待审核&nbsp;
                        <input type="radio" name="news.status" value="1"/>已通过
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>是否可评论</label>
                    <span class="field">
                        <input type="radio" name="news.canComment" value="1"/>&nbsp;可评论
                        <input type="radio" name="news.canComment" value="0"/>&nbsp;不可评论
                    </span>
                </p>
                <p>
                    <label>属性</label>
                    <span class="field">
                        <input type="checkbox" name="news.hot" id="hot" class="longinput" value="1"/>&nbsp;热点&nbsp;
                        <input type="checkbox" name="news.recommend" id="recommend" class="longinput" value="1"/>&nbsp;推荐
                    </span>
                </p>
                <c:if test="${flag == 0}">
                    <p class="stdformbutton" style="text-align: center">
                        <button class="submit radius2" onclick="addFormSubmit();return false;">保 存</button>
                        <input type="reset" class="reset radius2" value="重 置" onclick="resetData()"/>
                    </p>
                </c:if>
            </form>

            <br/>
        </div><!--subcontent-->
    </div>
</div>
<script type="text/javascript">
    var flag = '${flag}';
    jQuery(function (){//编辑器初始化
        initKindEditor_addblog('content', '876px', '400px');
        uploadFile("uploadFile", false, "myFile", 'http://10.100.101.1:6694', callbackFile);

        // 回显状态
        jQuery("input:radio[name='news.status'][value=" + jQuery("input[name='status']").val() + "]").prop("checked", true);
        // 回显评论
        jQuery("input:radio[name='news.canComment'][value=" + jQuery("input[name='canComment']").val() + "]").prop("checked", true);
        // 回显属性
        jQuery("input:checkbox[name='news.hot'][value=" + jQuery("input[name='hot']").val() + "]").prop("checked", true);
        jQuery("input:checkbox[name='news.recommend'][value=" + jQuery("input[name='recommend']").val() + "]").prop("checked", true);
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

    function addFormSubmit() {
        if(!jQuery("#title").val()){
            alert("请添加新闻标题");
            return;
        }
        if(!jQuery("#newsTypeId").val()){
            alert("请选择新闻类别");
            return;
        }
        if(!jQuery("#content").val()){
            alert("请添加新闻内容");
            return;
        }
        var data = jQuery("#updateNews").serialize();
        jQuery.ajax({
            url: "${ctx}/admin/oa/updateNews.json",
            data: data,
            type: "post",
            dataType: "json",
            async: false,
            success: function (result) {
                if (result.code == "0") {
                    alert(result.message);
                    window.location.href = "${ctx}/admin/oa/queryAllNews.json";
                } else {
                    alert(result.message);
                }
            }
        });
    }

    function callbackFile(data) {
        data = data.substr(2);
        data = data.substr(0, data.length - 2);
        jQuery("#fileUrl").val(data).show();
//        jQuery("#touxiang").attr("src",data);

    }

    function upFile() {
        jQuery("#file").html('已上传：' + jQuery(".fileName").html());
        jQuery("#uploadFile").uploadify('upload');
    }
    function resetData(){
        jQuery(".longinput").val("");
    }
    jQuery(function(){
        if (flag != 0) {
            jQuery(".longinput").attr("disabled", "disabled");
        }
    });
</script>
</body>
</html>