<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>新建资讯</title>
		<script type="text/javascript" charset="utf-8" src="/static/ueditor/ueditorConfig.js"></script>
		<script type="text/javascript" charset="utf-8" src="/static/ueditor/ueditor.config.js"></script>
		<script type="text/javascript" charset="utf-8" src="/static/ueditor/ueditor.all.min.js"></script>
		<script type="text/javascript" charset="utf-8" src="/static/ueditor/lang/zh-cn/zh-cn.js"></script>
		<link type="text/css" rel="stylesheet" href='/static/uploadify/uploadify.css?v=${v}'/>
		<script type="text/javascript" src="/static/uploadify/jquery.uploadify.js?v=${v}"></script>
		<script type="text/javascript" src="/static/uploadify/upload-file.js?v=${v}"></script>
		<script type="text/javascript">

			jQuery(function (){//编辑器初始化
				uploadImage("uploadImage",true,"myImage",imagePath,callbackImage);
                initFrontMultiUM("content", "80%", "300");
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
				jQuery(".uploadify-queue-item").remove();
				jQuery(".picture").remove();
				jQuery("#picture").val('');
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
					cache: true,
					type: "POST",
					dataType:"json",
					url:ctx+"/admin/menhu/article/addArticle.json",
					data:jQuery('#articleform').serialize(),
					async: false,
					error: function(request) {
						alert("网络异常，请稍后再试！");
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
			<h1 class="pagetitle">新建资讯</h1>
		</div><!--pageheader-->
		<div id="contentwrapper" class="contentwrapper">
			<form id="articleform" class="stdform stdform2" method="post" action="">
				<p>
					<label><em style="color: red;">*</em>资讯类型:</label>
					<span class="field">
						<select name="article.typeId">
							<c:forEach items="${articleTypeList}" var="list">
								<option value="${list.id}">${list.name}</option>
							</c:forEach>
						</select>
					</span>
				</p>

				<p>
					<label><em style="color: red;">*</em>标题:</label>
					<span class="field"><input type="text" name="article.title" id="title"  class="longinput" /></span>
				</p>
				<p>
					<label><em style="color: red;">*</em>简介:</label>
					<span class="field"><input type="text" name="article.description" id="description"  class="longinput" /></span>
				</p>
				<p>
					<label>图片:</label>
					<span class="field" id="image">
						<input type="button" id="uploadImage" value="上传图片"/>
						<input type="hidden"  name="article.picture" id="picture"  class="longinput" />
						<a onclick="fileClear()" class="stdbtn"  >清空</a>
						<br>
						<br>
					</span>
				</p>

				<p>
					<label><em style="color: red;">*</em>作者:</label>
					<span class="field"><input type="text" name="article.author" id="author"  class="longinput" /></span>
				</p>
				<p>
					<label><em style="color: red;">*</em>来源:</label>
					<span class="field"><input type="text" name="article.source" id="source"  class="longinput" /></span>
				</p>
				<p>
					<label><em style="color: red;">*</em>内容:</label>
					 <span class="field"  >
						 <textarea cols="5" rows="30" name="article.content" id="content" class="longinput"></textarea>
					</span>
				</p>
				<p>
					<label><em style="color: red;">*</em>是否首页轮播显示:</label>
					<span class="field">
						<select name="article.isIndex">
							<option value="1">否</option>
							<option value="2">是</option>
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