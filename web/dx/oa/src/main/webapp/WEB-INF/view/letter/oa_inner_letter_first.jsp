<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>公文申请</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/kindeditor/themes/default/default.css">
    <script type="text/javascript" src="/pageoffice.js" id="po_js_main"></script>
    <script type="text/javascript" src="${ctx}/kindeditor/kindeditor-all.js"></script>
    <script type="text/javascript">
        function addFormSubmit(flag) {
            var confirmOption = jQuery("#confirmOption").val();
            if (!confirmOption) {
                alert("核稿信息必填");
                return;
            }
            jQuery("#audit").val(flag);
            jQuery("#comment").val(confirmOption);
            jQuery("#audit").val(flag);
            jQuery("#comment").val(confirmOption);
            if(!jQuery("#reason").val()){
                alert("文件标题不能为空");
                return;
            }
//            if (!jQuery("#sendToUnit").val()) {
//                alert("发送单位不能为空");
//                return;
//            }
//            if (!jQuery("#context").val()) {
//                alert("内容摘要不能为空");
//                return;
//            }
            var data = jQuery("#addLetter").serialize();
            var obj = jQuery("#mobiles").children();
            var userIds = "";
            jQuery.each(obj, function(index , value) {
                userIds += jQuery(value).attr("id") + ",";
            });
            if (userIds) {
                data += "&userIds=" + userIds.substr(0, userIds.length - 1);
            }
            jQuery.ajax({
                url: "${ctx}/admin/oa/inner/letterApplyAudit.json",
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
        function openPageOffice(timeStamp) {
            var url = "${ctx}/open/internalOfficialDocument.json?timeStamp=" + timeStamp
            window.location.href = "javascript:POBrowser.openWindow('" + url + "','width=1200px;height=800px;')";
        }

        jQuery(function(){
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
                    <form id = "addLetter" method="post">
                    <table border="1">
                        <tr>
                            <td class="pt text_controller_center" style="width:12%;">发文号</td>
                            <td style="width:38%;"><span></span></td>
                            <td class="pt" style="width:5%;">密级</td>
                            <td wstyle="width:45%;"><span></span></td>
                        </tr>
                        <tr>
                            <td class="pt" colspan="4" style="text-align: left;padding-left: 40px; color:#666;"><tt><small class="c-red" style = "display: inline-block; vertical-align: top">*</small>文件标题：</tt><span><textarea name="oaLetter.reason" id="reason" style="width:88%;" cols="20" rows="5">${oaLetter.reason}</textarea></span></td>
                        </tr>
                        <tr>
                            <td colspan="2" class="text_controller_center">主办单位：<span>${departmentName}</span></td>
                            <td rowspan="2" colspan="2"  class="text_controller_center">时&nbsp;&nbsp;&nbsp;&nbsp;间：<span><fmt:formatDate value = "${nowDate}" pattern="yyyy 年 MM 月dd 日 HH:mm"></fmt:formatDate></span></td>

                        </tr>
                        <tr>
                            <td colspan="2" class="text_controller_center">拟&nbsp;&nbsp;稿&nbsp;&nbsp;人：<span>${applyName}</span></td>
                        </tr>
                        <tr>
                            <td colspan="2" class="text_controller_center">核&nbsp;&nbsp;&nbsp;&nbsp;稿：<input style="width:85%;height:110px;" type = "text" name = "oaLetter.confirmOption" id = "confirmOption"/></td>
                            <td colspan="2" class="text_controller_center">会&nbsp;&nbsp;&nbsp;&nbsp;签：</td>
                        </tr>
                        <tr>
                           <td class="pt" colspan="4" style="text-align: left;padding-left: 40px; color:#666;"><tt>签发：</tt></td>
                        </tr>
                        <tr>
                           <td class="pt" colspan="4" style="text-align: left;padding-left: 40px; color:#666;"><tt>发送单位：</tt><span><textarea name="oaLetter.sendToUnit" id="sendToUnit" style="width:88%;" cols="10" rows="5">${oaLetter.sendToUnit}</textarea></span></td>
                        </tr>
                        <tr>
                           <td class="pt" colspan="4" style="text-align: left;padding-left: 40px; color:#666;"><tt>内容摘要：</tt><span><textarea name="oaLetter.context" id="context" style="width:88%;" cols="10" rows="5">${oaLetter.context}</textarea></span></td>
                        </tr>
                        <c:if test = "${not empty oaLetter.fileUrl}">
                            <tr>
                                <td class="pt">公文内容(在线Word)</td>
                                <td colspan="3">
                               <span class="field">
                                <a href="javascript:void(0)" onclick="openPageOffice('${oaLetter.timeStamp}')" class="ZL-generate">编辑公文内容</a>
                               </span>
                                </td>
                            </tr>
                        </c:if>
                    </table>
                        <input type = "hidden" name = "taskId" value = "${task.id}" id = "taskId">
                        <input type = "hidden" name = "oaLetter.processInstanceId" value = "${oaLetter.processInstanceId}" id = "processInstanceId">
                        <input type = "hidden" name = "oaLetter.audit" id = "audit">
                        <input type = "hidden" name = "comment" id = "comment">

                    </form>
                    <div class="buttons" style="text-align: center;margin-top:20px;margin-bottom: 20px">
                        <span id = "mobiles"></span>
                        <p class="stdformbutton" style="text-align: center">
                            <button class="submit radius2" onclick="addFormSubmit(0);return false;">同意</button>
                            <button class="submit radius2" onclick="addFormSubmit(2);return false;">驳回</button>
                            <button class="submit radius2" onclick = "chooseUser()" style="cursor: pointer">选择下一步审批人</button>
                            <button class="submit radius2" onclick="history.go(-1);return false;">返回</button>
                        </p>
                    </div>
                </div>
            <br/>
        </div>
    </div>
    <jsp:include page="/WEB-INF/view/common/process_transfer.jsp"></jsp:include>
</div>

<script>
    /**
     * 发送短信页面
     * 点击选择学员
     */
    function chooseUser() {
        var mobiles = jQuery('#mobiles');
        var len = mobiles.children('a').size() - 1;
        jQuery.ajax({
            url: '${ctx}/admin/oa/ajax/queryReceivers.json',
            type: 'POST',
            data: {'from': 2},
            dataType: 'html',
            success: function (result) {
                jQuery.alerts._show("添加用户", result, null, 'dialog', function (data) {
                    if (!data) {
                        if (len < 0) mobiles.children('a').remove();
                        mobiles.children("a:gt(" + len + ")").remove();
                    }
                });
            }
        });
    }

    /**
     * 点击x号  移除联系人
     * @param obj 点击的js对象
     */
    function removeUser(obj) {
        jQuery(obj).parent().next().remove();
        jQuery(obj).parent().remove();
    }


    /**
     * 用户列表  点击多选框
     */
    function checkuser(obj) {
        //判断是否是全部选中了
        var bn = true;
        jQuery(".users").each(function () {
            if (!this.checked) {
                bn = false;
            }
        });
        if (bn) {
            jQuery(":checkbox").attr("checked", true);
        } else {
            jQuery("#checkAll").attr("checked", false);
        }

        var userId = jQuery(obj).val();
        var userName = jQuery("#username_" + userId).text().trim();
        //添加联系人
        if(obj.checked){//选中的添加
            if(!jQuery("#"+userId).text()){
                var element=' <a class="close" id="'+userId+'"> <i>'+userName+'</i><i class="fa fa-w c-999 fa-close" onclick="removeUser(this)"></i> </a>';
                jQuery("#mobiles").append(element);
            }
        } else {//取消选中的移除
            var id = "#" + userId;
            jQuery(id).next().remove();
            jQuery(id).remove();
        }
    }

    function checkAll(obj) {
        var a = obj.checked;
        jQuery(":checkbox").attr("checked", a);
        if (a) {
            jQuery("#chooseText").text("取消全选");
            jQuery(".users").each(function () {
                checkuser(this);
            });
        } else {
            jQuery("#chooseText").text("全部选中");
            jQuery(".users").each(function () {
                checkuser(this);
            });
        }
    }
</script>
</body>
</html>