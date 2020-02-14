<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>新建${resultTypeName}档案</title>
    <link type="text/css" rel="stylesheet" href='/static/uploadify/uploadify.css'/>
    <script type="text/javascript" src="/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="/static/uploadify/upload-file.js"></script>
    <script type="text/javascript" src="${ctx}/static/admin/js/approveBill.js"></script>
    <style type="text/css">
        em {
            color: red;
        }
    </style>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">新建${resultTypeName}档案</h1>
        <span>
            <span style="color:red">说明</span><br>
                1. 本页面用来新建${resultTypeName}档案<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addResult">
                <%--隐藏域放在这里会影响样式--%>

                <p>
                    <label class="name"><em style="color: red;">*</em>论文名称</label>
                    <span class="field"><input type="text" name="result.name" id="name" class="longinput"
                                               maxlength="200"/></span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>成果形式</label>
                    <span class="field">
                        <select name="result.resultForm" id="resultForm" onchange="chooseResultForm()">
                            <c:forEach items="${resultFormList}" var="resultForm">
                                <option value="${resultForm.id}">${resultForm.name}</option>
                            </c:forEach>
                        </select>
                    </span>

                </p>

                <p>
                    <label><em>*</em> 教师所属教研部</label>
                    <span class="field">
                        <select name="result.teacherResearch" id="teacherResearch">
                            <option value="0">--请选择--</option>
                            <option value="1">党史</option>
                            <option value="2">公管</option>
                            <option value="3">经济学</option>
                            <option value="4">法学</option>
                            <option value="5">文化与社会发展</option>
                            <option value="6">马列</option>
                            <option value="7">统一战线</option>
                            <%--新增--%>
                            <option value="8">校委委员</option>
                            <option value="9">办公室(区县党校工作处)</option>
                            <option value="10">机关党委</option>
                            <option value="11">组织人事处</option>
                            <option value="12">财务处</option>
                            <option value="13">后勤管理处</option>
                            <option value="14">纪检监察室</option>
                            <option value="15">教务处</option>
                            <option value="16">信息资源管理处</option>
                            <option value="17">科研管理处</option>
                            <option value="18">生态文明研究所</option>
                            <option value="19">图书馆</option>
                            <option value="20">学员管理处</option>
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

                <p id="radioKindNature">
                    <label id="kindNature">刊物性质</label>
                    <span class="field" id="publicationNature">
                        <input type="radio" value="1" checked="checked" name="result.journalNature"/><span
                            id="natureOne">市级以上</span>&nbsp;&nbsp;
                        <input type="radio" value="2" name="result.journalNature"/><span id="natureTwo">校级</span>&nbsp;&nbsp;
                        <input type="radio" value="3" name="result.journalNature"/><span id="natureThree">报纸</span>&nbsp;&nbsp;
                        <input type="radio" value="4" name="result.journalNature"/><span id="natureFour">报纸</span>&nbsp;&nbsp;
                    </span>
                </p>

                <p>
                    <label>级别</label>
                    <span class="field">
                        <input type="radio" value="1" checked="checked" name="result.level"/>国家级&nbsp;&nbsp;
                        <input type="radio" value="2" name="result.level"/>省部级&nbsp;&nbsp;
                        <input type="radio" value="3" name="result.level"/>市级&nbsp;&nbsp;
                        <input type="radio" value="4" name="result.level"/>校级&nbsp;&nbsp;
                    </span>
                </p>

                <p class="publish1">
                    <label class="publish">发表刊物</label>
                    <span class="field"><input type="text" name="result.publish" id="publish" class="longinput"
                                               maxlength="200"/></span>
                </p>

                <p class="choose1">
                    <label>刊号</label>
                    <span class="field"><input type="text" name="result.publishNumber" id="publishNumber"
                                               class="longinput" maxlength="200"/></span>
                </p>

                <p class="publishTime1">
                    <label class="publishTime"><em style="color: red;">*</em>发表时间</label>
                    <span class="field"><input type="text" readonly name="result.publishTime" id="publishTime"
                                               class="longinput"/></span>
                </p>

                <p class="reportingTime">
                    <label><em style="color: red;">*</em>开始时间</label>
                    <span class="field"><input type="text" readonly name="result.addTime" id="addTime"
                                               class="longinput"/></span>
                </p>

                <p class="reportingTime">
                    <label><em style="color: red;">*</em>结束时间</label>
                    <span class="field"><input type="text" readonly name="result.endTime" id="endTime"
                                               class="longinput"/></span>
                </p>
                <p style="display: none" id="taskForceMembers">
                    <label class="taskForceMembers" id="participationGroupMembers">课题组成员</label>
                    <span class="field">
                        <input type="text" name="result.taskForceMembers" id="teacherName" class="longinput"
                               maxlength="200"/>
                    </span>
                </p>

                <p>
                    <label class="workName">作者姓名</label>
                    <span class="field"><input type="text" name="result.workName" id="workName"
                                               class="longinput" maxlength="200"/></span>
                </p>

                <p>
                    <label class="wordsNumber"><em style="color: red;">*</em>字数</label>
                    <span class="field"><input type="text" onkeyup="value=this.value.replace(/\D+/g,'')"
                                               maxlength="8" placeholder="请输入数字"
                                               name="result.wordsNumber" id="wordsNumber"
                                               class="longinput"/></span>
                </p>

                <p class="choose2">
                    <label>副主编</label>
                    <span class="field"><input type="text" name="result.associateEditor" id="associateEditor"
                                               class="longinput" maxlength="200"/></span>
                </p>

                <p class="choose2">
                    <label>副主编字数</label>
                    <span class="field"><input type="text" onkeyup='this.value=this.value.replace(/\D/gi,"")'
                                               name="result.associateNumber" id="associateNumber"
                                               class="longinput" maxlength="8"/></span>
                </p>

                <p class="choose2">
                    <label><em style="color: red;">*</em>参编章节信息
                        <small></small>
                    </label>
                    <span class="field"><textarea cols="80" rows="5" name="result.chapter" id="chapter"
                                                  class="longinput"></textarea></span>
                </p>

                <div id="awards">
                    <p>
                        <label>获奖情况</label>
                        <span class="field">
                        <input type="radio" value="1" checked="checked" name="result.awardSituation"/><span id="aw-1">国家级奖</span>&nbsp;&nbsp;
                        <input type="radio" value="2" name="result.awardSituation"/><span id="aw-2">省部级奖</span>&nbsp;&nbsp;
                        <input type="radio" value="3" name="result.awardSituation"/><span id="aw-3">地市级奖</span>&nbsp;&nbsp;
                        <input type="radio" value="4" name="result.awardSituation"/><span id="aw-4">其他</span>&nbsp;&nbsp;
                    </span>
                    </p>
                    <p>
                        <label>获奖情况描述
                            <small></small>
                        </label>
                        <span class="field"><textarea cols="80" rows="5" name="result.digest" id="digest"
                                                      class="longinput"></textarea></span>
                    </p>
                </div>

                <p>
                    <label><em style="color: red;">*</em>上传申请书</label>
                    <span class="field">
                        <input type="hidden" name="result.fileUrl" id="fileUrl"/>
                        <input type="button" id="uploadFile" value="上传申请书"/>
                        <center><h4 id="file"></h4></center>
                    </span>
                <div class="clear"></div>
                </p>

                <p id="uploadArgumentTheory" style="display: none">
                    <label> <em>*</em> 上传论证活页</label>
                    <span class="field">
                        <input type="hidden" name="result.fileUrlTheory" id="fileUrlTheory"/>
                        <input type="button" id="uploadFileTheory" value="上传论证活页"/>
                        <center><h4 id="fileTheory"></h4></center>
                    </span>
                <div class="clear"></div>
                </p>

                <p>
                    <label>上传档案附件</label>
                    <span class="field">
                    <input type="hidden" name="result.fileUrlAttachment" id="fileUrlAttachment"/>
                    <input type="button" id="uploadFileAttachment" value="上传档案附件"/>
                    <center><h4 id="fileAttachment"></h4></center>
                </span>
                <div class="clear"></div>
                </p>

                <p>
                    <label>备注
                        <small></small>
                    </label>
                    <span class="field"><textarea cols="80" rows="5" name="result.remark" id="remark"
                                                  class="longinput" maxlength="250"></textarea></span>
                </p>

                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="doSave(2);return false;">新建长期档案</button>
                    <button class="submit radius2" onclick="doSave(3);return false;">新建短期档案</button>
                    <input type="reset" class="reset radius2" value="重 置" onclick="resetData()"/>
                </p>

                <input type="hidden" name="result.resultType" value="${resultType}">
            </form>
            <br/>
        </div><!--subcontent-->
    </div><!--contentwrapper-->
</div><!-- centercontent -->
<script type="text/javascript" src="${ctx}/static/admin/js/result.js"></script>
<script type="text/javascript">
    /**
     * 时间控件
     */
    jQuery(function () {
        laydate.skin('molv');
        laydate({
            elem: '#publishTime',
            format: 'YYYY-MM-DD hh:mm:ss',
            istime: true
        });
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
        uploadFile("uploadFile", "useless", "myFile", imagePath, callbackFile);
        uploadFile("uploadFileTheory", "useless", "myFile", imagePath, callbackFileTheory);
        uploadFile("uploadFileAttachment", "useless", "myFile", imagePath, callbackFileAttachment);

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

    function callbackFileAttachment(data) {
        data = data.substr(2);
        data = data.substr(0, data.length - 2);
        jQuery("#fileUrlAttachment").val(data);
        jQuery("#fileAttachment").html('档案附件已上传');
    }

    function doSave(flag) {
        if (!checkInput()) {
            return false;
        }

        var params = jQuery("#addResult").serialize()
        jQuery.post("${ctx}/admin/ky/doSaveFile.json?flag=" + flag, params,
            function (result) {
                alert(result.message);
                if (result.code === "0") {
                    window.location.href = "${ctx}/admin/ky/getResultFileList.json?queryResult.resultType=" + result.data;
                }
            }, "json"
        )
    }
</script>
</body>
</html>