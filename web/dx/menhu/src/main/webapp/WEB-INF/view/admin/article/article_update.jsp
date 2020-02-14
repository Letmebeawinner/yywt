<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>修改资讯</title>
		<script type="text/javascript" charset="utf-8" src="/static/ueditor/ueditorConfig.js"></script>
		<script type="text/javascript" charset="utf-8" src="/static/ueditor/ueditor.config.js"></script>
		<script type="text/javascript" charset="utf-8" src="/static/ueditor/ueditor.all.min.js"></script>
		<script type="text/javascript" charset="utf-8" src="/static/ueditor/lang/zh-cn/zh-cn.js"></script>
		<link type="text/css" rel="stylesheet" href='/static/uploadify/uploadify.css?v=${v}'/>
		<script type="text/javascript" src="/static/uploadify/jquery.uploadify.js?v=${v}"></script>
		<script type="text/javascript" src="/static/uploadify/upload-file.js?v=${v}"></script>
		<script type="text/javascript">

			jQuery(function (){//编辑器初始化
                initFrontMultiUM("content", "80%", "300");
				uploadImage("uploadImage",true,"myImage",imagePath,callbackImage);
			});

            function initFrontMultiUM(id, width, height) {
                UE.delEditor(id);
                //实例化编辑器
                var ue = UE.getEditor('' + id, {
                    toolbars: [
                        [
                            'fullscreen', 'source', '|', 'undo', 'redo', '|',
                            'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
                            'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
                            'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
                            'directionalityltr', 'directionalityrtl', 'indent', '|',
                            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
                            'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
                            'simpleupload', 'insertimage', 'emotion', 'scrawl', 'insertvideo', 'music', 'attachment', 'map', 'gmap', 'insertframe', 'insertcode', 'webapp', 'pagebreak', 'template', 'background', '|',
                            'horizontal', 'date', 'time'
                        ]
                    ],
                    enableAutoSave: false,
                    autoHeightEnabled: true,
                    autoFloatEnabled: true,
                    initialFrameWidth: width,
                    initialFrameHeight: height,
                    scaleEnabled: true//滚动条
                });
                return ue;
            }
			function callbackVideo(data){
				jQuery("#pictureVideo").val(data);
			}

			function callbackImage(data){

				var data_=data;
				var picture=jQuery("#picture").val().trim()
				if(picture==""){
					jQuery("#picture").val(data);
				}else{
					data=data.substr(1);
					picture=picture.substr(0,picture.length-1);
					jQuery("#picture").val(picture+","+data);
				}

				data_=data_.substr(2);
				data_=data_.substr(0,data.length-2);
				jQuery("#image").append('<img id="image"  width="200px" height="200px"  class="picture" src="'+data_+'" >');

			}
			function fileClear(){
				jQuery(".picture").remove();
				jQuery("#picture").val('');
				jQuery("#pictureVideo").val('');
				jQuery("#pictureSplit").remove();
			}


			function submit(){

                if(jQuery("#title").val().trim()=="") {
                    alert("请填写标题。");
                    return false
                }
                if(jQuery("#description").val().trim()=="") {
                    alert("请填写简介。");
                    return false
                }
                if(jQuery("#author").val().trim()=="") {
                    alert("请填写作者。");
                    return false
                }
                if(jQuery("#source").val().trim()=="") {
                    alert("请填写来源。");
                    return false
                }
                var content = UE.getEditor('content').getContent();
                if(content=="") {
                    alert("请填写内容。");
                    return false
                }
				jQuery.ajax({
					type: "POST",
					dataType:"json",
					url:ctx+"/admin/menhu/article/updateArticle.json",
					data:jQuery('#articleform').serialize(),
					cache: false,
					async: false,
					error: function(request) {
						alert("网络异常，请稍后再试");
					},
					success: function(result) {
						if(result.code==0){
							window.location.href=ctx+result.data;
						}else{
							alert("网络异常，请稍后再试");
						}
					}
				});
			}
		</script>
	</head>
	<body>
	<div class="centercontent">
		<div class="pageheader notab">
			<h1 class="pagetitle">修改资讯</h1>
		</div><!--pageheader-->
		<div id="contentwrapper" class="contentwrapper">
			<form id="articleform" class="stdform stdform2" method="post" action="">
				<p>
				<input type="hidden" value="${article.id}" name="article.id">
					<label>类型:</label>
					<span class="field">
						<select name="article.typeId">
							<c:forEach items="${articleTypeList}" var="list">
								<option value="${list.id}" <c:if test="${list.id==article.typeId}"> selected="selected" </c:if>>${list.name}</option>
							</c:forEach>
						</select>
					</span>
				</p>
				<p>
					<label>标题:</label>
					<span class="field"><input type="text" id="title" value="${article.title}"  name="article.title"  class="longinput" /></span>
				</p>
				<p>
					<label>简介:</label>
					<span class="field"><input type="text" id="description" value="${article.description}"  name="article.description"  class="longinput" /></span>
				</p>

				<p id="imageTab" >
					<label>图片:</label>
					<span class="field" id="image">
						<input type="button" id="uploadImage" value="上传图片"/>
						<input type="hidden"  name="article.picture" id="picture" value='${article.picture}'  class="longinput" />
						 <a onclick="fileClear()" class="stdbtn"  >清空</a>
							 <c:forEach items="${pictureSplit}" var="picture">
								 <img class="picture" width="200px" height="200px"  src="${picture}" style="margin-left: 100px;" >
							 </c:forEach>
					</span>
				</p>
				<p>
					<label>作者:</label>
					<span class="field"><input type="text" id="author" value="${article.author}"  name="article.author" class="longinput" /></span>
				</p>
				<p>
					<label>来源:</label>
					<span class="field">
						<input type="text" id="source"  value="${article.source}"  name="article.source"  class="longinput" />
					</span>
				</p>
				<p>
					<label><em style="color: red;">*</em>内容:</label>
					<span class="field"  >
						 <textarea cols="5" rows="30" name="article.content" id="content" class="longinput">${article.content}</textarea>
					</span>
				</p>
				<p>
					<label>是否在app首页显示:</label>
					<span class="field">
						<select name="article.isIndex">
							<option <c:if test="${1==article.isIndex}"> selected="selected" </c:if> value="1">否</option>
							<option <c:if test="${2==article.isIndex}"> selected="selected" </c:if> value="2">是</option>
						</select>
					</span>
				</p>
			</form>
			<p class="stdformbutton">
				<center>
					<button onclick="submit()"   class="stdbtn" >保存</button>
					<button onclick="javascript:history.go(-1);"   class="stdbtn" >返 回</button>
				</center>
			</p>
		</div>
	</div>
	</body>
</html>