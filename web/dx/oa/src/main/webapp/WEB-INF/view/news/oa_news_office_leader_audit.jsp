<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>信息管理员审批</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/kindeditor/themes/default/default.css"/>
    <script type="text/javascript" src="${ctx}/kindeditor/kindeditor-all.js"></script>
    <link type="text/css" rel="stylesheet" href='${ctx}/static/uploadify/uploadify.css'/>
    <script type="text/javascript" src="${ctx}/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/uploadify/upload-file.js"></script>
    <script type = "text/javascript" src = "${ctx}/static/js/oa_select_options.js"></script>
    <script type="text/javascript" charset="utf-8" src="/static/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="/static/ueditor/ueditor.all.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="/static/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript">
        jQuery(function (){//编辑器初始化
            initFrontMultiUM("content", "100%", "200");
            laydate.skin('molv');
            laydate({
                elem: '#planPublicTime',
                format: 'YYYY-MM-DD hh:mm:ss',
                istime:true
            });
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

        jQuery(function (){//编辑器初始化

            var pros='${publicWay}';
            for(var i=0;i<pros.length;i++)
            {
                jQuery('input[lang="publicWay"]').each(function(){
                    var str=jQuery(this).val();
                    if(pros[i]==str){
                        jQuery(this).attr("checked","checked");
                    }
                });
            }

            var notAllowReason = '${notAllowReason}';
            for(var i = 0;i < notAllowReason.length; i++)
            {
                jQuery('input[name="oaNews.notAllowReason"]').each(function(){
                    var str=jQuery(this).val();
                    if(notAllowReason[i]==str){
                        jQuery(this).attr("checked","checked");
                    }
                });
            }

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
                uploadJson: imagePath + '&param=article',// 图片上传路径
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

        function addFormSubmit(flag) {
            jQuery("#audit").val(flag);
            jQuery("#comment").val(jQuery("#infoLeaderOption").val());
            if(!jQuery("#infoLeaderOption").val()){
                alert("请填写责任主编意见");
                return;
            }
            var data = jQuery("#oaNewsApply").serialize();
            jQuery.ajax({
                url: "${ctx}/admin/oa/newsApplyAudit.json",
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

        function resetData(){
            jQuery(".longinput").val("");
            jQuery("#content").val("");
        }

        function addPublic() {
            var publicIds="";
            jQuery('.checkinput:checked').each(function () {
                publicIds+=jQuery(this).val()+',';
            });
            publicIds=publicIds.substring(0,publicIds.length-1);
            jQuery("#publicWay").val(publicIds);
        }



        /**
         * 更改选择没公开和不公开理由
         */
        function changeSelect() {
            var allowPublic = jQuery("input[name='oaNews.allowPublic']:checked").val();
            if (allowPublic == 0) {
                jQuery(".notAllowPublicReason").attr("checked", false);
                jQuery("#notAllowPublicReason").val("");
                jQuery("#notAllowAccording").val("");
                jQuery("#agree").removeAttr("disabled")
                jQuery("#notAllowAccording").attr({"disabled":"disabled"})
            }else {
                jQuery("#agree").attr({"disabled":"disabled"})
                jQuery("#notAllowAccording").removeAttr("disabled")
            }
        }

        /**
         * 不允许公开理由
         */
        function notAllowPublic() {
            var allowPublic = jQuery("input[name='oaNews.allowPublic']:checked").val();
            //如果选择，公开，那么所有不公开理由，选中效果取消
            if (allowPublic != 1) {
                jQuery(".notAllowPublicReason").attr("checked", false);
            }
            var notAllowReasonIds = "";
            jQuery(".notAllowPublicReason:checked").each(function() {
                notAllowReasonIds += jQuery(this).val() + ",";
            });
            notAllowReasonIds = notAllowReasonIds.substring(0, notAllowReasonIds.length - 1);
            jQuery("#notAllowPublicReason").val(notAllowReasonIds);
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
                        <td colspan="3"><input type="text" name = "oaNews.title" id="newTitle" value="${oaNews.title}"></td>
                    </tr>
                    <tr>
                        <td class="pt">图片作者</td>
                        <td><input type="text" name="oaNews.imageAuthor" id="imageAuthor" value="${oaNews.imageAuthor}"></td>
                        <td class="pt">图片数量</td>
                        <td><input type="text" name="oaNews.imageNumber" id="imageNumber" value="${oaNews.imageNumber}"></td>
                    </tr>
                    <tr>
                        <td class="pt">信息载体形式</td>
                        <td colspan="3" style="text-align: left;">
                            <%--<span><c:if test="${oaNews.infoWay==0}">&nbsp;公文类&nbsp;</c:if></span>--%>
                            <%--<span><c:if test="${oaNews.infoWay==1}">&nbsp;非公文类</c:if></span>--%>
                            <span> <input type="radio" name="oaNews.infoWay"  class="checkinput1" value="0"  <c:if test="${oaNews.infoWay==0}">checked</c:if>/>&nbsp;公文类&nbsp;</span>
                            <span><input type="radio" name="oaNews.infoWay"  class="checkinput1" value="1" <c:if test="${oaNews.infoWay==1}">checked</c:if>/>&nbsp;非公文类</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="pt">信息拟公开方式</td>
                        <td colspan="3" style="text-align: left;">
                            <span><input type="checkbox" lang = "publicWay" class="checkinput" value="1" onclick="addPublic()" />&nbsp;政府网站&nbsp;</span>
                            <span><input type="checkbox" lang = "publicWay" class="checkinput" value="2" onclick="addPublic()" />&nbsp;报刊</span>
                            <span><input type="checkbox" lang = "publicWay" class="checkinput" value="3" onclick="addPublic()" />&nbsp;广播</span>
                            <span><input type="checkbox" lang = "publicWay" class="checkinput" value="4" onclick="addPublic()" />&nbsp;电视</span>
                            <span><input type="checkbox" lang = "publicWay" class="checkinput" value="5" onclick="addPublic()" />&nbsp;政务公开栏</span>
                            <span><input type="checkbox" lang = "publicWay" class="checkinput" value="6" onclick="addPublic()" />&nbsp;校园网站</span>
                            <span><input type="checkbox" lang = "publicWay" name="publicWay"  class="checkinput publicWay" value="7" onclick="addPublic()" />&nbsp;其他</span>

                        </td>
                    </tr>
                    <tr>
                        <td class="pt">信息拟定公开时间</td>
                        <td colspan="3"><input type="text" name="oaNews.planPublicTime" id="planPublicTime"  value="<fmt:formatDate value="${oaNews.planPublicTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"></td>
                    </tr>
                    <tr>
                        <td class="pt">信息留存</td>
                        <td colspan="3"><textarea rows="4" name="oaNews.summary" id="summary" style="resize:none;">${oaNews.summary}</textarea></td>
                    </tr>
                    <tr>
                        <td class="pt">信息内容</td>
                        <td colspan="3"><textarea name="oaNews.content" id="content" cols="30" rows="10">${oaNews.content}</textarea></td>
                    </tr>
                    <c:if test = "${not empty oaNews.fileUrl}">
                        <tr>
                            <td class="pt"><small class="c-red">*</small>附件</td>
                            <td colspan="3">
                            <span>
                                 <a href="${ctx}/admin/oa/file/load.json?fileUrl=${oaNews.fileUrl}&fileName=${oaNews.fileName}" title="下载附件">${oaNews.fileName}</a>
                            </span>
                            </td>
                        </tr>
                    </c:if>
                    <tr>
                        <td class="pt">拟公开信息机关自审意见<br/>（处室领导意见）</td>
                        <td colspan="3"><textarea name="oaNews.departmentOption" id="departmentOption" cols="30" rows="10">${oaNews.departmentOption}</textarea></td>
                    </tr>
                    <tr>
                        <td class="pt" rowspan="6">信息公开保密审查机构审核意见</td>
                        <td colspan="3" style="text-align: left;">
                            <span><input type="radio" name="oaNews.allowPublic" onclick = "changeSelect()" class="checkinput" value="0" <c:if test = "${oaNews.allowPublic == 0}"> checked</c:if>/>&nbsp;应予公开</span>

                        </td>
                    </tr>
                    <tr>
                        <td style="text-align: left;" rowspan="3">
                            <span><input type="radio" name="oaNews.allowPublic" onclick = "changeSelect()" class="checkinput" value="1" <c:if test = "${oaNews.allowPublic == 1}"> checked</c:if>/>&nbsp;不予公开理由</span>
                        </td>
                        <td colspan="2" style="text-align: left;">
                            <span><input type="checkbox" name="oaNews.notAllowReason"  class="checkinput notAllowPublicReason" value="0" onclick = "notAllowPublic()"/>&nbsp;国家秘密</span>
                            <span><input type="checkbox" name="oaNews.notAllowReason"  class="checkinput notAllowPublicReason" value="1" onclick = "notAllowPublic()"/>&nbsp;商业秘密</span>
                        </td>

                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: left;">
                            <span><input type="checkbox" name="oaNews.notAllowReason"  class="checkinput notAllowPublicReason" value="2" onclick = "notAllowPublic()"/>&nbsp;个人隐私</span>
                        </td>

                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: left;">
                            <span><input type="checkbox" name="oaNews.notAllowReason"  class="checkinput notAllowPublicReason" value="3" onclick = "notAllowPublic()"/>&nbsp;其他不予公开的形式</span>
                        </td>

                    </tr>
                    <tr>
                        <td class="pt" colspan="3" style="text-align: left; border:none;border-right: 1px solid #333;padding-left: 40px; color:#666;">不予公开的依据：
                            <textarea  cols="30" rows="5" name = "oaNews.notAllowAccording"  id = "notAllowAccording">${oaNews.notAllowAccording}</textarea>
                        </td>
                    </tr>
                    <tr>
                        <td class="pt" colspan="3" style="border:none;border-right: 1px solid #333;border-bottom: 1px solid #333; color:#666;">审查人签字：</td>

                    </tr>
                    <tr>
                        <td class="pt">编辑意见</td>
                        <td><input type="text" name="oaNews.infoManagerOption" id="infoManagerOption" value="${oaNews.infoManagerOption}" disabled></td>
                        <td class="pt"><small class="c-red">*</small>责任主编意见</td>
                        <td><input type="text" name="oaNews.infoLeaderOption" id="infoLeaderOption"></td>
                    </tr>
                    <tr>
                        <td class="pt" rowspan="2">主管领导人审批意见</td>

                        <td class="pt" colspan="3" style="border:none;border-right: 1px solid #333; color:#666;"><span></span></td>


                    </tr>
                    <tr>
                        <td class="pt" colspan="3" style="border:none;border-right: 1px solid #333;border-bottom: 1px solid #333; color:#666;">签字：</td>
                    </tr>
                    <tr>
                        <td class="pt" rowspan="1">审批状态</td>
                        <td colspan="3">
                            <span>
                                <c:if test="${oaNews.audit==1}">
                                    <font color="green">审核通过</font>
                                </c:if>
                                <c:if test="${oaNews.audit==0}">
                                    审核中
                                </c:if>
                                <c:if test="${oaNews.audit==2}">
                                    已拒绝
                                </c:if>
                            </span>
                        </td>
                    </tr>
                    <input type="hidden" value="${oaNews.processInstanceId}" name="processInstanceId">
                    <input type="hidden" value="${task.id}" id="taskId" name="taskId">
                    <input type="hidden" name="comment" id="comment">
                    <input type="hidden" name="oaNews.audit" id="audit">
                    <input type="hidden" id="publicWay" name="oaNews.publicWay" value = "${oaNews.publicWay}"/>
                    <input type="hidden" id="notAllowPublicReason" name="oaNews.notAllowReason" value = "${oaNews.notAllowReason}"/>
                    <tr>
                        <td colspan="4">
                            <div class="buttons" style="text-align: center;margin-top:20px;margin-bottom: 20px">
                                <button class="submit radius2" id="agree" onclick="addFormSubmit(1);return false;">同意</button>
                                <button class="submit radius2" onclick="addFormSubmit(2);return false;">驳回</button>
                                <button class="submit radius2" onclick="history.go(-1);return false;">返回</button>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
            <jsp:include page="/WEB-INF/view/common/process_transfer.jsp"></jsp:include>
        </form>
    </div>

</div>
</body>
</html>