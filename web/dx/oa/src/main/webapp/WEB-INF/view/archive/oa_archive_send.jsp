<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>档案处推送档案</title>
</head>

<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">档案处推送档案</h1>
    </div>
    <div style="margin-left: 20px;">
        <span style="color:red">说明</span><br>
        1.本页面用于档案处推送档案；<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label><em style="color: red;">*</em>调阅人姓名</label>
                    <span class="field">
                        <input type="text"  value = "${applyName}" class="longinput" readonly/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>主要内容和目的</label>
                    <span class="field">
                        <textarea rows="10" cols="80" class="longinput" id="content" readonly>${oaArchive.content}</textarea>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>档案</label>
                    <span class="field">
                    <a class="stdbtn btn_red" onclick="selectArchive()">选择档案</a>
                    <div id="employees"></div>
                </span>
                </p>
                <p>
                    <label>审核状态</label>
                    <span class="field">
                    <c:if test = "${oaArchive.audit == 1}">
                        审批已通过
                    </c:if>
                    <c:if test = "${oaArchive.audit == 0}">
                        审核中
                    </c:if>
                    <c:if test = "${oaArchive.audit == 2}">
                        已拒绝
                    </c:if>
                </span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addFormSubmit(0);return false;">同意</button>
                    <button class="submit radius2" onclick="addFormSubmit(2);return false;">驳回</button>
                    <button class="submit radius2" onclick="history.go(-1);return false;">返回</button>
                </p>
                <input type="hidden" value="${task.id}" id="taskId" name="taskId">
                <input type="hidden" value="${oaArchive.processInstanceId}" name="processInstanceId">
                <input type="hidden" name="oaArchive.audit" id="audit">
                <input type="hidden"id="archiveIds" name = "archiveIds">
                <input type="hidden" value = "${oaArchive.applyId}" name = "userId">
            </form>
            <br/>
        </div>
    </div>
    <jsp:include page="/WEB-INF/view/common/process_transfer.jsp"></jsp:include>
</div>
<script>
    function addFormSubmit(flag) {
        if (!jQuery("#archiveIds").val()) {
            alert("请选择档案");
            return;
        }
        jQuery("#audit").val(flag);
        var data = jQuery("#addFormSubmit").serialize();
        jQuery.ajax({
            url: "${ctx}/admin/oa/archive/give.json",
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

    /**
     * Created by yzl on 2017-01-18.
     */
    /**
     * 通讯录选择
     */
    var data=data;
    var employeesIds;
    function selectArchive() {
        jQuery.ajax({
            type:"post",
            url:"${ctx}/admin/oa/ajax/queryArchiveList.json?archive.stockFlag=1&flag=0",
            data:{},
            dataType:"text",
            async: false,
            success:function (result) {
                jQuery.alerts._show('选择档案',null, null,'addCont',function (confirm) {
                    if(confirm){
                        if(employeesIds){
                            jQuery("#archiveIds").val(employeesIds);
                            queryEmployee(employeesIds);
                        }else{
                            alert("没有选择！");
                        }
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


    function queryEmployee(ids){
        jQuery.ajax({
            url: "/admin/oa/ajax/getArchiveListByIds.json",
            data: {"ids": ids},
            type: "post",
            dataType: "json",
            success: function (result) {
                if (result.code=="0") {
                    var userList = result.data;
                    if(userList!=null){
                        var html = '';
                        for (var i = 0; i < userList.length; i++) {
                            html += '<p style="margin-left:0px;width: 300px;height: 40px;border: 0px;margin-top: 10px;">' + userList[i].autograph  + '&nbsp;&nbsp;<a onclick="delEmployee(' + userList[i].id  + ')" class="stdbtn btn_red" style="" href="javascript:void(0)"> 删除</a></p>';
                        }
                        jQuery("#employees").html(html);
                    }else{
                        jQuery("#employees").html("");
                    }
                } else {
                    jQuery("#employees").html("");
                }
            }
        });
    }


    /**
     * 得到教职工id串
     */
    function checkClick() {
        employeesIds="";
        jQuery('.telephone:checked').each(function () {
            employeesIds+=jQuery(this).val()+',';
        });
        employeesIds=employeesIds.substring(0,employeesIds.length-1);
    }

    function delEmployee(id) {
        var userId = jQuery("#userId").val();
        var pattern = id + "";
        userId = userId.replace(new RegExp(pattern), "");
        userId = userId.split(",").unique();
        jQuery("#archiveIds").val(userId);
        queryEmployee(jQuery("#archiveIds").val());
    }
</script>
</body>
</html>