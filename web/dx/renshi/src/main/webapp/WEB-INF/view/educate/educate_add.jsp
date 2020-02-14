<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>添加培训项目</title>
    <link type="text/css" rel="stylesheet" href='/static/uploadify/uploadify.css'/>

</head>
<body>
<div class="centercontent">
    <div class="pageheader"  style="margin-left: 10px">
        <h1 class="pagetitle">新建培训项目</h1>
         <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来添加新的培训信息<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addEducate">
                <p>
                    <%--<input type="hidden" name="educate.employeeId" id="employeeId" class="longinput"/>--%>
                    <input type="hidden" name="employeeIds" id="employeeId" class="longinput"/>
                    <label><em style="color: red;">*</em>姓名</label>
                    <span class="field">
                    <a href="javascript:void(0)" class="dialog-btn btn "
                       onclick="selectEmployee()"><span>选择教职工</span></a>
                    <tt id="employeeName"></tt>
                        <a href="javascript:void(0)" class="dialog-btn btn" id="deleteEmployee" style="display: none"
                                                   onclick="deleteEmployee()"><span>清空教职工</span></a>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>职级（职称）</label>
                    <span class="field"><input type="text" name="educate.technical" id="technical" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>培训名称</label>
                    <span class="field"><input type="text" name="educate.name" id="name" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>培训开始时间</label>
                    <span class="field"><input type="text"  readonly name="educate.beginTime" id="beginTime" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>培训结束时间</label>
                    <span class="field"><input type="text" readonly  name="educate.endTime" id="endTime" class="longinput"/></span>
                </p>
                <p>
                    <label>总学习日</label>
                    <span class="field"><input type="text" name="educate.studay" id="studay" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>请假天数</label>
                    <span class="field"><input type="text" name="educate.leavedays" id="leavedays" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>实际学习天数</label>
                    <span class="field"><input type="text" name="educate.actstuday" id="actstuday" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>调训单位</label>
                    <span class="field"><input type="text" name="educate.diaoXunUnit" id="diaoXunUnit" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>培训地点</label>
                    <span class="field"><input type="text" name="educate.trainingLocation" id="trainingLocation" class="longinput"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>培训单位</label>
                    <span class="field"><input type="text" name="educate.trainingUnit" id="trainingUnit" class="longinput"/></span>
                </p>
                <p>
                    <label>是否发放毕业证书</label>
                    <span class="field">
                        <input type="radio" value="0" checked name="educate.leavingCertificate">是&nbsp;&nbsp;
                        <input type="radio" value="1" name="educate.leavingCertificate">否&nbsp;
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>培训总结:</label>
                    <span class="field">
						 <input type="hidden" name="educate.summarize"id="fileUrl"/>
						 <input type="button" id="uploadFile" value="上传文件"/><a onclick="upFile()"
                                                                               href="javascript:void(0)">上传</a>
						 <tt id="file" style="color: red"></tt>
					</span>
                </p>
                <p>
                    <label>备注</label>
                    <span class="field"><textarea cols="80" rows="5" name="educate.description" id="description" class="longinput"></textarea></span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addEducateFormSubmit();return false;">添 加</button>
                    <input type="reset" class="reset radius2" value="重 置" onclick="resetData()"/>
                </p>
            </form>
            <br/>
        </div><!--subcontent-->
    </div>
</div>
<script type="text/javascript" src="${ctx}/static/admin/js/educate.js"></script>
<script type="text/javascript" src="/static/uploadify/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="/static/uploadify/upload-file.js"></script>
<script type="text/javascript">
    /**
     * 时间控件
     */
    jQuery(function(){
        laydate.skin('molv');
        laydate({
            elem: '#beginTime',
            format:'YYYY-MM-DD'
        });
        laydate({
            elem: '#endTime',
            format:'YYYY-MM-DD'
        });
    });
    var fileServicePath = '${fileServicePath}';

    function callbackFile(data) {
        data = data.substr(2);
        data = data.substr(0, data.length - 2);
        jQuery("#fileUrl").val(data);
        jQuery("#file").html('已上传：' + jQuery(".fileName").html());
    }

    function upFile() {
        jQuery("#uploadFile").uploadify('upload');
    }

    jQuery(function () {
        uploadFile("uploadFile", false, "myFile", fileServicePath, callbackFile);
    });
</script>
</body>
</html>