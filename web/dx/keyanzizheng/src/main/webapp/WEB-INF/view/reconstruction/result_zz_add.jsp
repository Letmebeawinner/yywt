<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>申报生态文明所课题</title>
    <link type="text/css" rel="stylesheet" href='${ctx}/static/uploadify/uploadify.css'/>
    <script type="text/javascript" src="${ctx}/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/uploadify/upload-file.js"></script>
    <style type="text/css">
        em {
            color: red;
        }

        .uploadify {
            display: inline-block;
            margin-left: 15px;
        }
    </style>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">申报课题</h1>
        <span>
            <span style="color:red">说明</span><br>
                1. 本页面用来申报课题<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。<br>
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addResult">

                <p>
                    <label class="name"><em style="color: red;">*</em>论文名称</label>
                    <span class="field"><input type="text" name="result.name" id="name" class="longinput"/></span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>课题类型</label>
                    <span class="field">
                        <select name="result.yearOrMonthly">
                            <option value="1">年度</option>
                            <option value="2">月度</option>
                        </select>
                    </span>
                </p>

                <p>
                    <label> <em style="color: red;">*</em>
                        所属处室</label>
                    <span class="field">
                        <select name="result.teacherResearch" id="teacherResearch">
                            <option value="0">--请选择--</option>
                             <c:forEach items="${subSectionList}" var="s">
                                 <option value="${s.id}">${s.name}</option>
                             </c:forEach>
                        </select>
                    </span>
                </p>

                <p class="resultSwitch">
                    <label>是否公开</label>
                    <span class="field">
                        <select name="result.resultSwitch" id="resultSwitch" class="longinput">
                            <option value="1">公开</option>
                            <option value="2">不公开</option>
                        </select>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>级别</label>
                    <span class="field">
                        <input type="radio" value="4" name="result.level" checked="checked"/>校级&nbsp;&nbsp;
                    </span>
                </p>

                <p class="reportingTime">
                    <label><em style="color: red;">*</em>开始时间</label>
                    <span class="field"><input type="text" readonly name="result.addTime" id="addTime"
                                               class="longinput"/></span>
                </p>
                <p class="reportingTime">
                    <label>结束时间</label>
                    <span class="field"><input type="text" readonly name="result.endTime" id="endTime"
                                               class="longinput"/></span>
                </p>
                <p>
                    <label class="taskForceMembers">课题组成员</label>
                    <span class="field"><input type="text" name="result.taskForceMembers"
                                               class="longinput" value=""/></span>
                </p>

                <%--由js控制显示课题负责人--%>
                <p>
                    <label class="workName">课题负责人</label>
                    <span class="field"><input type="text" name="result.workName" id="workName"
                                               class="longinput"/></span>
                </p>

                <p>
                    <label><em>*</em>字数</label>
                    <span class="field"><input type="text" name="result.wordsNumber" class="longinput"
                                               id="wordsNumber"
                                               onkeyup="value=this.value.replace(/\D+/g,'')" maxlength="8"
                                               placeholder="请输入数字"/></span>
                </p>

                <p>
                    <label> <em>*</em> 手机号</label>
                    <span class="field"><input type="text" name="result.phoneNumber" class="longinput"
                                               onblur="trim(this)"/></span>
                </p>

                <p>
                    <label> <em>*</em> 邮箱</label>
                    <span class="field"><input type="text" name="result.mailbox" class="longinput" onblur="trim(this)"/></span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>上传申请书</label>
                    <span class="field">
                        <input type="hidden" name="result.fileUrl" id="fileUrl"/>
                        <input type="button" id="uploadFile" value="上传申请书"/>
                        <center><h4 id="file"></h4></center>
                    </span>
                <div class="clear"></div>
                </p>
                <p>
                    <label><em style="color: red;">*</em>上传论证活页</label>
                    <span class="field">
                        <input type="hidden" name="result.fileUrlTheory" id="fileUrlTheory"/>
                        <input type="button" id="uploadFileTheory" value="上传论证活页"/>
                        <center><h4 id="fileTheory"></h4></center>
                    </span>
                <div class="clear"></div>
                </p>

                <p>
                    <label>备注
                        <small></small>
                    </label>
                    <span class="field"><textarea cols="80" rows="5" name="result.remark" id="remark"
                                                  class="longinput"></textarea></span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="doSave();return false;">添 加</button>
                    <input type="reset" class="reset radius2" value="重 置" onclick="resetData()"/>
                </p>
                <%--生态文明所成果--%>
                <input type="hidden" name="result.resultType" value="2">
                <input type="hidden" name="type" id="type" value="1">
                <%--成果形式为课题--%>
                <input type="hidden" id="resultForm" name="result.resultForm" value="3">
            </form>
            <br/>
        </div><!--subcontent-->
    </div><!--contentwrapper-->
</div><!-- centercontent -->
<script type="text/javascript" src="${ctx}/static/admin/js/result.js"></script>
<script type="text/javascript">
    var nowDate = '${nowDate}';
    var resultForm = '${result.resultForm}';
    if(resultForm==null || resultForm == ''){
        jQuery("#type").val(2);
    }
    function trim(obj) {
        obj.value = jQuery.trim(obj.value)
    }

    /**
     * 时间控件
     */
    jQuery(function () {
        laydate.skin('molv');
        laydate({
            elem: '#addTime',
            format: 'YYYY-MM-DD hh:mm:ss',
            istime: true
        });
        laydate({
            elem: '#endTime',
            format: 'YYYY-MM-DD hh:mm:ss',
            istime: true
        });
        uploadFile("uploadFileTheory", true, "myFile", imagePath, callbackFileTheory);

        uploadFile("uploadFile", true, "myFile", imagePath, callbackFile);
    });

    function callbackFile(data) {
        data = data.substr(2);
        data = data.substr(0, data.length - 2);
        jQuery("#fileUrl").val(data);
        jQuery("#file").html('申请书已上传');
    }

    function callbackFileTheory(data) {
        data = data.substr(2);
        data = data.substr(0, data.length - 2);
        jQuery("#fileUrlTheory").val(data);
        jQuery("#fileTheory").html('论证活页已上传');
    }

    function doSave() {
        var patt = /^1[0-9]{10}$/;
        var reg = /^[a-z0-9](\w|\.|-)*@([a-z0-9]+-?[a-z0-9]+\.){1,3}[a-z]{2,4}$/i;

        var phone = jQuery("input[name='result.phoneNumber']").val()
        var mailbox = jQuery("input[name='result.mailbox']").val()
        if (phone.length < 1) {
            alert("请输入手机号")
            return false
        }

        if (!(patt.test(phone))) {
            alert("手机号码有误，请重填")
            return false;
        }

        if (mailbox.length < 1) {
            alert("请输入邮箱")
            return false
        }

        if (!(reg.test(mailbox))) {
            alert("邮箱格式错误，请重填")
            return false;
        }
        addResultFormSubmit()
    }
</script>
</body>
</html>