<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>申报科研课题</title>
    <link type="text/css" rel="stylesheet" href='/static/uploadify/uploadify.css'/>
    <script type="text/javascript" src="/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="/static/uploadify/upload-file.js"></script>
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
        <h1 class="pagetitle">申报科研课题</h1>
        <span>
            <span style="color:red">说明</span><br>
                1. 本页面用来申报课题<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。<br>
                3. 点击<a href="${ctx}/admin/ky/projectTemplateManagement/list.json"><span
                style="color:red">这里下载申请模板</span></a>。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addResult">

                <p>
                    <label class="name"><em style="color: red;">*</em>论文名称</label>
                    <span class="field"><input type="text" name="result.name" id="name" class="longinput"
                                               maxlength="200"/></span>
                </p>

                <p>
                    <label> <em style="color: red;">*</em>
                        所属处室</label>
                    <span class="field">
                        <select name="result.teacherResearch" id="teacherResearch">
                            <option value="0">--未选择--</option>
                            <option value="1">党史党建教研部</option>
                            <option value="2">公共管理教研部</option>
                            <option value="3">经济学教研部</option>
                            <option value="4">法学教研部</option>
                            <option value="5">文化与社会发展教研部</option>
                            <option value="6">马列主义理论教研部</option>
                            <option value="7">统一战线理论教研部</option>
                            <option value="8">校委委员</option>
                            <option value="9">办公室</option>
                            <option value="10">机关党委</option>
                            <option value="11">组织人事处</option>
                            <option value="12">财务处</option>
                            <option value="13">后勤管理处</option>
                            <option value="14">纪检监察室</option>
                            <option value="15">教务处</option>
                            <option value="16">信息资源管理处</option>
                            <option value="17">科研管理处</option>
                            <option value="18">生态文明研究所</option>
                            <option value="19">图书馆（《学报》编辑部、校史办）</option>
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
                <p>
                    <label><em style="color: red;">*</em>级别</label>
                    <span class="field">
                        <input type="radio" value="1" checked="checked" name="result.level"/>国家级&nbsp;&nbsp;
                        <input type="radio" value="2" name="result.level"/>省部级&nbsp;&nbsp;
                        <input type="radio" value="3" name="result.level"/>市级&nbsp;&nbsp;
                        <input type="radio" value="4" name="result.level"/>校级&nbsp;&nbsp;
                    </span>
                </p>

                <p>
                    <label class="publish">课题发布单位</label>
                    <span class="field"><input type="text" name="result.publish" id="publish" class="longinput"
                                               maxlength="200"/></span>
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
                <%--由js控制显示课题负责人--%>
                <p>
                    <label> <em>*</em> 课题负责人</label>
                    <span class="field"><input type="text" name="result.workName" id="workName"
                                               class="longinput" maxlength="200"/></span>
                </p>
                <p>
                    <label class="taskForceMembers">课题组成员</label>
                    <span class="field"><input type="text" name="result.taskForceMembers"
                                               class="longinput" value="" maxlength="200"/></span>
                </p>

                <p>
                    <label><em>*</em>字数</label>
                    <span class="field"><input type="text" name="result.wordsNumber" class="longinput"
                                               id="wordsNumber"
                                               onkeyup="value=this.value.replace(/\D+/g,'')" maxlength="8"
                                               placeholder="请输入数字"/></span>
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
                                                  class="longinput" maxlength="200"></textarea></span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addResultFormSubmit();return false;">添 加</button>
                    <input type="reset" class="reset radius2" value="重 置" onclick="resetData()"/>
                </p>
                <input type="hidden" name="result.resultType" value="${result.resultType}">
                <input type="hidden" id="resultForm" name="result.resultForm" value="${result.resultForm}">
            </form>
            <br/>
        </div><!--subcontent-->
    </div><!--contentwrapper-->
</div><!-- centercontent -->
<script type="text/javascript" src="${ctx}/static/admin/js/result.js"></script>
<script type="text/javascript">
    var nowDate = '${nowDate}';
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

    function doSave() {
        var addTime = jQuery("#addTime").val()
        var endTime = jQuery("#endTime").val()
        var _addTime = new Date(addTime.replace(/-/g, '/'));
        var _endTime = new Date(endTime.replace(/-/g, '/'));
        if (_addTime > _endTime) {
            alert('审报开始时间不能大于结束时间');
            return false;
        }
        addResultFormSubmit()
    }


    function callbackFileTheory(data) {
        data = data.substr(2);
        data = data.substr(0, data.length - 2);
        jQuery("#fileUrlTheory").val(data);
        jQuery("#fileTheory").html('论证活页已上传');
    }
</script>
</body>
</html>