<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>信息公开保密审查审批表</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/kindeditor/themes/default/default.css"/>
    <script type="text/javascript" src="${ctx}/kindeditor/kindeditor-all.js"></script>
    <link type="text/css" rel="stylesheet" href='${ctx}/static/uploadify/uploadify.css'/>
    <link rel="stylesheet" type="text/css" href="${basePath}/static/ztree/css/zTreeStyle/zTreeStyle.css">
    <script charset="utf-8" type="text/javascript" src="${basePath}/static/ztree/jquery.ztree.all-3.5.js"></script>
    <script type="text/javascript" src="${ctx}/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/uploadify/upload-file.js"></script>
    <script type = "text/javascript" src = "${ctx}/static/js/oa_select_options.js"></script>
    <script type="text/javascript" charset="utf-8" src="/static/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="/static/ueditor/ueditor.all.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="/static/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript">

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
            initFrontMultiUM("content", "100%", "200");

            var pros='${publicWay}';
            for(var i=0;i<pros.length;i++)
            {
                jQuery('input[name="publicWay"]').each(function(){
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

        function sendInformationToOuter() {
            var title = jQuery("#title").html();
            var content = UE.getEditor("content").getContent();
            jQuery.ajax({
                url: informationPath + "/addInfoArticle.json",
                data: {
                    "Content": content+'<br/><a href="'+jQuery("#fileUrl").val()+'">点击下载附件内容</a>',
                    "Title": title,
                    "CopyFrom": jQuery("#departmentName").html(),
                    "KeyWord": jQuery("#applyName").html(),
                    "ClassID": jQuery("#classId").val(),
                    "FilePath": jQuery("#fileUrl").val()
                },
                type: "post",
                cache: false,
                dataType: "json",
                async: false,
                success: function(result) {
                    if (result.code == '0') {
                        jQuery("#sendToOuter").html("取消发布(外网)");
                        jQuery("#sendToOuter").attr("onclick", "cancelPublish('OUTER')");
                        changeSendStatus(result.data.id);
                        alert(result.message);
                    }
                }
            });
        }

        function sendInformationToInner(id) {
            var title = jQuery("#title").html();
            var content = UE.getEditor("content").getContent();
            jQuery.ajax({
                url: contextPath + "/admin/oa/ajax/send/inner.json",
                data: {
                    "newsId": jQuery("#newsId").val(),
                    "content": content,
                    "typeId": id,
                    "author": jQuery("#applyName").html(),
                    "title": title,
                    "description": "",
                    "fileUrl": jQuery("#fileUrl").val()
                },
                type: "post",
                cache: false,
                dataType: "json",
                async: false,
                success: function(result) {
                    if (result.code == '0') {
                        jQuery("#sendToInner").html("取消发布(内网)");
                        jQuery("#sendToInner").attr("onclick", "cancelPublish('INNER')");
                        alert(result.message);
                        jQuery.ajax({
                            url: "${ctx}/admin/oa/ajax/updateNews.json",
                            data: {
                                "id": jQuery("#newsId").val(),
                                "content": content
                            },
                            type: "post",
                            cache: false,
                            dataType: "json",
                            async: false,
                            success: function(result) {
                                window.location.reload();
                            }
                        });
                    }
                }
            });
        }

        /**
         * 改为已发送状态, 同时记录外网对应的id;
         */
        function changeSendStatus(outerNetId) {
            jQuery.ajax({
                url: "${ctx}/admin/oa/sendNews.json",
                data: {
                    "id": jQuery("#newsId").val(),
                    "outNetId": outerNetId
                },
                type: "post",
                cache: false,
                dataType: "json",
                async: false,
                success: function(result) {
                }
            });
        }

        function selectNewsType() {
            jQuery.ajax({
                type:"post",
                url:"${ctx}/admin/oa/ajax/news/type.json",
                data:{},
                dataType:"text",
                async: false,
                success:function (result) {
                    jQuery.alerts._show('选择分类',null, null,'addCont',function (confirm) {
                        if (confirm) {
                            sendInformationToOuter();
                        }

                    });
                    jQuery('#popup_message').html(result);
                    // 修改弹窗的位置。距窗口上边距150px，左边距30%.
                    jQuery('#popup_container').css({
                        top: 50,
                        left: '30%',
                        overflow:'hidden'
                    });
                    jQuery('#popup_container').css("max-height","800px");
                    jQuery('#popup_message').css("max-height","600px");
                    jQuery('#popup_message').css('overflow-y','scroll');
                }
            });
        }

        function selectNewsTypeToInner() {
            jQuery.ajax({
                type:"post",
                url:"${ctx}/admin/oa/ajax/news/inner/type.json",
                data:{},
                dataType:"text",
                async: false,
                success:function (result) {
                    jQuery.alerts._show('选择分类',null, null,'addCont',function (confirm) {
                        if (confirm) {
                            var id = jQuery("#typeId").val();
                            sendInformationToInner(id);
                        }

                    });
                    jQuery('#popup_message').html(result);
                    // 修改弹窗的位置。距窗口上边距150px，左边距30%.
                    jQuery('#popup_container').css({
                        top: 50,
                        left: '30%',
                        overflow:'hidden'
                    });
                    jQuery('#popup_container').css("max-height","800px");
                    jQuery('#popup_message').css("max-height","600px");
                    jQuery('#popup_message').css('overflow-y','scroll');
                }
            });
        }

        function getTypeId() {
            var id = jQuery("input[name='typeId']:checked").val();
            jQuery("#typeId").val(id);
        }

        /**
         * 取消发布
         */
        function cancelPublish(type) {
            if (!confirm("确定要取消发布")) {
                return;
            }
            jQuery.ajax({
                url: "${ctx}/admin/oa/ajax/article/del.json",
                data: {
                    "newsId": jQuery("#newsId").val(),
                    "type": type
                },
                type: "post",
                cache: false,
                dataType: "json",
                async: false,
                success: function(result) {
                    if (result.code == '0') {
                        alert("操作成功");
                        if (type == "INNER") {
                            jQuery("#sendToInner").html("发布到内网");
                            jQuery("#sendToInner").attr("onclick", "javascript:selectNewsTypeToInner()");
                        } else {
                            jQuery("#sendToOuter").html("发布到外网");
                            jQuery("#sendToOuter").attr("onclick", "javascript:selectNewsType()");
                        }
                        window.location.reload();
                    }

                }
            });
        }

        function printDocument() {
            jQuery("#print").hide();
            jQuery(".printHide").hide();
            jQuery(".header").hide();
            jQuery(".iconmenu").hide();
            jQuery(".centercontent").css("marginLeft","0");
            jQuery("textarea").css("min-height","102px");
            jQuery("td").css("border","1px solid #999");


            // return;
            window.print();
            jQuery("#print").show();
            jQuery(".printHide").show();
            jQuery(".header").show();
            jQuery(".iconmenu").show();
            jQuery(".centercontent").css("marginLeft","181px");
            jQuery("td").css("border","1px solid #ddd");
            jQuery("textarea").css("height", "100%");

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
    <div id="print">
        <button type = "button" onclick = "printDocument()" style = "margin-left: 21px;cursor: pointer" class = "printHide">打印</button>
    </div>
    <div id="contentwrapper" class="contentwrapper users-car">
            <div class="testtle-tables">
                <input type="hidden" name="processDefinitionId" value="${processDefinition.id}">
                <input type = "hidden" id = "typeId">
                <table border="1">
                    <tr>
                        <td class="pt">部门</td>
                        <td><span id = "departmentName">${departmentName}</span></td>
                        <td class="pt">申请人</td>
                        <td><span id = "applyName">${applyName}</span></td>
                    </tr>
                    <tr>
                        <td class="pt">信息名称</td>
                        <td colspan="3"><span id = "title">${oaNews.title}</span></td>
                    </tr>
                    <tr>
                        <td class="pt">图片作者</td>
                        <td><span id = "imageAuthor">${oaNews.imageAuthor}</span></td>
                        <td class="pt">图片数量</td>
                        <td><span id = "imageNumber">${oaNews.imageNumber}</span></td>
                    </tr>
                    <tr>
                        <td class="pt">信息载体形式</td>
                        <td colspan="3" style="text-align: left;">
                            <span><c:if test="${oaNews.infoWay==0}">&nbsp;公文类&nbsp;</c:if></span>
                            <span><c:if test="${oaNews.infoWay==1}">&nbsp;非公文类</c:if></span>
                        </td>
                    </tr>
                    <tr>
                        <td class="pt">信息拟公开方式</td>
                        <td colspan="3" style="text-align: left;">
                            <span><input type="checkbox" name="publicWay"  class="checkinput" value="1" onclick="addPublic()" disabled/>&nbsp;政府网站&nbsp;</span>
                            <span><input type="checkbox" name="publicWay"  class="checkinput" value="2" onclick="addPublic()" disabled/>&nbsp;报刊</span>
                            <span><input type="checkbox" name="publicWay"  class="checkinput" value="3" onclick="addPublic()" disabled/>&nbsp;广播</span>
                            <span><input type="checkbox" name="publicWay"  class="checkinput" value="4" onclick="addPublic()" disabled/>&nbsp;电视</span>
                            <span><input type="checkbox" name="publicWay"  class="checkinput" value="5" onclick="addPublic()" disabled/>&nbsp;政务公开栏</span>
                            <span><input type="checkbox" name="publicWay"  class="checkinput" value="6" onclick="addPublic()" disabled/>&nbsp;校园网站</span>
                            <span><input type="checkbox" name="publicWay"  class="checkinput" value="7" onclick="addPublic()" disabled/>&nbsp;其他</span>

                        </td>
                    </tr>
                    <tr>
                        <td class="pt">信息拟定公开时间</td>
                        <td colspan="3"><input type="text" name="oaNews.planPublicTime" id="planPublicTime"  disabled value="<fmt:formatDate value="${oaNews.planPublicTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"></td>
                    </tr>
                    <tr>
                        <td class="pt">信息留存</td>
                        <td colspan="3">${oaNews.summary}</td>
                    </tr>
                    <tr  class = "printHide">
                        <td class="pt">信息内容</td>
                        <c:if test="${oaNews.sendStatusInner == 0 && oaNews.allowPublic==0}">
                            <td colspan="3"><textarea name="oaNews.content" id="content" cols="30" rows="10">${oaNews.content}</textarea></td>
                        </c:if>
                        <c:if test="${oaNews.sendStatusInner != 0 || oaNews.allowPublic != 0}">
                            <td colspan="3">${oaNews.content}</td>
                        </c:if>
                    </tr>
                    <input type="hidden" id="fileUrl" value="${oaNews.fileUrl}">
                    <c:if test = "${not empty oaNews.fileUrl}">
                        <tr>
                            <td class="pt">附件</td>
                            <td colspan="3">
                            <span>
                                 <a href="${ctx}/admin/oa/file/load.json?fileUrl=${oaNews.fileUrl}&fileName=${oaNews.fileName}" title="下载附件">${oaNews.fileName}</a>
                            </span>
                            </td>
                        </tr>
                    </c:if>
                    <tr>
                        <td class="pt">拟公开信息机关自审意见<br/>（处室领导意见）</td>
                        <td colspan="3"><span>${oaNews.departmentOption}</span></td>
                    </tr>
                    <tr>
                        <td class="pt" rowspan="6">信息公开保密审查机构审核意见</td>
                        <td colspan="3" style="text-align: left;">
                            <span ><input type="radio"  onclick = "changeSelect()" name="oaNews.allowPublic"  class="checkinput" value="0"  <c:if test = "${oaNews.allowPublic == 0}"> checked</c:if> disabled/>&nbsp;应予公开</span>

                        </td>
                    </tr>
                    <tr>
                        <td style="text-align: left;" rowspan="3">
                            <span><input type="radio" onclick = "changeSelect()" name="oaNews.allowPublic"  class="checkinput" value="1"  <c:if test = "${oaNews.allowPublic == 1}"> checked</c:if> disabled/>&nbsp;不予公开理由</span>
                        </td>
                        <td colspan="2" style="text-align: left;">
                            <span><input type="checkbox" name="oaNews.notAllowReason"  class="checkinput notAllowPublicReason" value="0" onclick = "notAllowPublic()" disabled/>&nbsp;国家秘密</span>
                            <span><input type="checkbox" name="oaNews.notAllowReason"  class="checkinput notAllowPublicReason" value="1" onclick = "notAllowPublic()" disabled/>&nbsp;商业秘密</span>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: left;">
                            <span><input type="checkbox" name="oaNews.notAllowReason"  class="checkinput notAllowPublicReason" value="2" onclick = "notAllowPublic()" disabled/>&nbsp;个人隐私</span>
                        </td>

                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: left;">
                            <span><input type="checkbox" name="oaNews.notAllowReason"  class="checkinput notAllowPublicReason" value="3" onclick = "notAllowPublic()" disabled/>&nbsp;其他不予公开的形式</span>
                        </td>

                    </tr>
                    <tr>
                        <td class="pt" colspan="3" style="text-align: left; border:none;border-right: 1px solid #333;padding-left: 40px; color:#666;">不予公开的依据：
                            <textarea  cols="30" rows="5" name = "oaNews.notAllowAccording"  id = "notAllowAccording" disabled>${oaNews.notAllowAccording}</textarea>
                        </td>

                    </tr>
                    <tr>
                        <td class="pt" colspan="3" style="border:none;">审查人签字：</td>

                    </tr>
                    <tr>
                        <td class="pt">编辑意见</td>
                        <td><input type="text" name="oaNews.infoManagerOption" id="infoManagerOption" value="${oaNews.infoManagerOption}" disabled></td>
                        <td class="pt">责任主编意见</td>
                        <td><span>${oaNews.infoLeaderOption}</span></td>
                    </tr>
                    <tr>
                        <td class="pt" rowspan="2">主管领导人审批意见</td>

                        <td class="pt" colspan="3" style="border:none;"><span></span></td>


                    </tr>
                    <tr>
                        <td class="pt" colspan="3" style="border:none;">签字：</td>
                    </tr>
                    <tr class = "printHide">
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
                    <tr class = "printHide">
                        <td colspan="4">
                            <div class="buttons" style="text-align: center;margin-top:20px;margin-bottom: 20px">
                                <c:if test = "${flag == 1 and oaNews.audit ==1}">
                                    <%--<c:if test = "${oaNews.sendStatus == 0}">
                                        <button class="submit radius2" onclick="selectNewsType(); return false" id = "sendToOuter">发布到外网</button>
                                    </c:if>--%>
                                    <c:if test = "${oaNews.sendStatusInner == 0 && oaNews.allowPublic==0}">
                                        <button class="submit radius2" onclick="selectNewsTypeToInner(); return false" id = "sendToInner">发布到内网</button>
                                    </c:if>

                                    <%--<c:if test = "${oaNews.sendStatus == 1}">
                                        <button class="submit radius2" onclick="cancelPublish('OUTER')" id = "sendToOuter">取消发布(外网)</button>
                                    </c:if>--%>
                                    <c:if test = "${oaNews.sendStatusInner == 1 && oaNews.allowPublic==0}">
                                        <button class="submit radius2" onclick="cancelPublish('INNER')" id = "sendToInner">取消发布(内网)</button>
                                    </c:if>
                                </c:if>
                                <button class="submit radius2 printHide" onclick="history.go(-1)">返回</button>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
            <div id="employees"></div>
            <input type = "hidden" value = "${oaNews.id}" id = "newsId">
            <jsp:include page="/WEB-INF/view/common/process_transfer.jsp"></jsp:include>
        <input type = "hidden" id = "classId">
    </div>

<script>
</script>
</div>
</body>
</html>