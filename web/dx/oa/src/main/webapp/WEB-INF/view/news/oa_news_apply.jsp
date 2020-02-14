<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>新闻发布申请</title>
    <script type="text/javascript" charset="utf-8" src="/static/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="/static/ueditor/ueditor.all.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="/static/ueditor/lang/zh-cn/zh-cn.js"></script>
    <link type="text/css" rel="stylesheet" href='${ctx}/static/uploadify/uploadify.css'/>
    <script type="text/javascript" src="${ctx}/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/uploadify/upload-file.js"></script>
    <script type="text/javascript">

        jQuery(function (){//编辑器初始化
            initFrontMultiUM("content", "100%", "200");
//            var editor = KindEditor.create("textarea[name='oaNews.content']")
//            editor.sync()
            uploadFile("uploadFile", false, "myFile", imagePath, callbackFile);
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


        function addFormSubmit() {
            if (!jQuery("#newTitle").val()) {
                alert("请填写信息名称");
                return;
            }
//            if(!jQuery("#imageAuthor").val()){
//                alert("请填写图片作者");
//                return;
//            }
//            if(!jQuery("#imageNumber").val()){
//                alert("请填写图片数量");
//                return;
//            }

            if (!jQuery("#summary").val()) {
                alert("请填写信息留存");
                return;
            }

            var content = UE.getEditor("content").getContent();
            if (!content) {
                alert("请填写信息内容");
                return;
            }

            var data = jQuery("#oaNewsApply").serialize();
            jQuery.ajax({
                url: "${ctx}/admin/oa/oaNews/process/start.json",
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
            jQuery("#content").val("");
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

    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="background: #fff;">
            <h1 class="page-list" style="text-align: center;">
               信息公开保密审查审批表
            </h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper users-car">
            <form method="post" action="" id="oaNewsApply">
            <div class="testtle-tables">
                <input type="hidden" name="processDefinitionId" value="${processDefinition.id}">
                <table border="1">
                    <tr>
                        <td class="pt"><small class="c-red">*</small>部门</td>
                        <td><span>${departmentName}</span></td>
                        <td class="pt"><small class="c-red">*</small>申请人</td>
                        <td><span>${applyName}</span></td>
                    </tr>
                    <tr>
                        <td class="pt"><small class="c-red">*</small>信息名称</td>
                        <td colspan="3"><span><input type="text" name = "oaNews.title" id="newTitle"></span></td>
                    </tr>
                    <tr>
                        <td class="pt">图片作者</td>
                        <td><span><input type="text" name="oaNews.imageAuthor" id="imageAuthor"></span></td>
                        <td class="pt">图片数量</td>
                        <td><span><input type="text" name="oaNews.imageNumber" id="imageNumber" onkeyup="value=value.replace(/[^\d]/g,'')"></span></td>
                    </tr>
                    <tr>
                        <td class="pt">信息载体形式</td>
                        <td colspan="3" style="text-align: left;">
                            <span> <input type="checkbox" name="oaNews.infoWay"  class="checkinput" value="0" disabled/>&nbsp;公文类&nbsp;</span>
                            <span><input type="checkbox" name="oaNews.infoWay"  class="checkinput" value="1" disabled/>&nbsp;非公文类</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="pt">信息拟公开方式</td>
                        <td colspan="3" style="text-align: left;">
                            <span> <input type="checkbox" name="oaNews.recommend"  class="checkinput" value="1" disabled/>&nbsp;政府网站&nbsp;</span>
                            <span><input type="checkbox" name="oaNews.recommend"  class="checkinput" value="2" disabled/>&nbsp;报刊</span>
                            <span><input type="checkbox" name="oaNews.recommend"  class="checkinput" value="3" disabled/>&nbsp;广播</span>
                            <span><input type="checkbox" name="oaNews.recommend" class="checkinput" value="4" disabled/>&nbsp;电视</span>
                            <span><input type="checkbox" name="oaNews.recommend"  class="checkinput" value="5" disabled/>&nbsp;政务公开栏</span>
                            <span><input type="checkbox" name="oaNews.recommend"  class="checkinput" value="6" disabled/>&nbsp;校园网站</span>
                            <span><input type="checkbox" name="oaNews.recommend"  class="checkinput" value="7" disabled/>&nbsp;其他</span>

                        </td>
                    </tr>
                    <tr>
                        <td class="pt">信息拟定公开时间</td>
                        <td colspan="3"><span></span></td>
                    </tr>
                    <tr>
                        <td class="pt"><small class="c-red">*</small>信息留存</td>
                        <td colspan="3"><textarea rows="4" name="oaNews.summary" id="summary" style="resize:none;"></textarea></td>
                    </tr>
                    <tr>
                        <td class="pt"><small class="c-red">*</small>信息内容</td>
                        <td colspan="3"><textarea name="oaNews.content" id="content" style="resize:none;"></textarea></td>
                    </tr>
                    <tr>
                        <td class="pt"><small class="c-red">*</small>附件上传</td>
                        <td colspan="3">
                        <span class="buttons">
                             <input type="hidden" name="oaNews.fileUrl"  id="fileUrl" />
                             <input type="hidden" name="oaNews.fileName"   id="fileName" />
                             <input type="button" id="uploadFile" value="上传附件"/>
                             <a onclick="upFile()" href="javascript:void(0)" class="upload_sate submit radius2">确认上传</a>
                             <center><h4  id="file"></h4></center>
					    </span>
                        </td>
                    </tr>
                    <tr>
                        <td class="pt">拟公开信息机关自审意见<br/>（处室领导意见）</td>
                        <td colspan="3"><span></span></td>
                    </tr>
                    <tr>
                        <td class="pt" rowspan="6">信息公开保密审查机构审核意见</td>
                        <td colspan="3" style="text-align: left;">
                            <span><input type="checkbox" name="oaNews.recommend"  class="checkinput" value="1" disabled/>&nbsp;应予公开</span>

                        </td>
                    </tr>
                    <tr>
                        <td style="text-align: left;" rowspan="3">
                            <span><input type="checkbox" name="oaNews.recommend"  class="checkinput" value="1" disabled/>&nbsp;不予公开理由</span>
                        </td>
                        <td colspan="2" style="text-align: left;">
                            <span><input type="checkbox" name="oaNews.recommend"  class="checkinput" value="0" disabled />&nbsp;国家秘密</span>
                            <span><input type="checkbox" name="oaNews.recommend"  class="checkinput" value="1" disabled/>&nbsp;商业秘密</span>
                        </td>

                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: left;">
                            <span><input type="checkbox" name="oaNews.recommend"  class="checkinput" value="2" disabled/>&nbsp;个人隐私</span>
                        </td>

                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: left;">
                            <span><input type="checkbox" name="oaNews.recommend"  class="checkinput" value="3" disabled/>&nbsp;其他不予公开的形式</span>
                        </td>

                    </tr>
                    <tr>
                        <td class="pt" colspan="3" style="text-align: left; border:none;border-right: 1px solid #ddd;padding-left: 40px; color:#666;">不予公开的依据：<textarea name="" id="" style="width:88%;" disabled></textarea></td>

                    </tr>
                    <tr>
                        <td class="pt" colspan="3" style="border:none;border-right: 1px solid #ddd;border-bottom: 1px solid #ddd; color:#666;">审查人签字：</td>

                    </tr>
                    <tr>
                        <td class="pt">编辑意见</td>
                        <td></td>
                        <td class="pt">责任主编意见</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="pt" rowspan="2">主管领导人审批意见</td>

                        <td class="pt" colspan="3" style="border:none;border-right: 1px solid #ddd; color:#666;"></td>


                    </tr>
                    <tr>
                        <td class="pt" colspan="3" style="border:none;border-right: 1px solid #ddd;border-bottom: 1px solid #ddd; color:#666;">签字：</td>
                    </tr>
                    <tr>
                        <td colspan="4">
                            <div class="buttons" style="text-align: center;margin-top:20px;margin-bottom: 20px">
                                <a class="submit radius2" onclick = "addFormSubmit()" style="cursor: pointer">提交</a>
                            </div>
                        </td>
                    </tr>
</table>
</div>
            </form>
</div>


</div>
</body>
</html>