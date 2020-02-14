<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>修改培训项目</title>
    <link type="text/css" rel="stylesheet" href='/static/uploadify/uploadify.css'/>

</head>
<body>
<div class="centercontent">
    <div class="pageheader"  style="margin-left: 10px">
        <h1 class="pagetitle">修改培训项目</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来添加新的培训信息<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="updateEducate">
                <p>
                    <input type="hidden" name="educate.employeeId" id="employeeId" class="longinput" value="${educate.employeeId}"/>
                    <input type="hidden" name="educate.id" id="educationId" class="longinput" value="${educate.id}"/>
                    <label>姓名</label>
                    <span class="field">
                    <tt id="employeeName">${educate.employeeName}</tt>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>职称</label>
                    <span class="field"><input type="text" name="educate.technical" id="technical" class="longinput" value="${educate.technical}"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>培训名称</label>
                    <span class="field"><input type="text" name="educate.name" id="name" class="longinput" value="${educate.name}"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>培训开始时间</label>
                    <span class="field"><input type="text"  readonly name="educate.beginTime" id="beginTime" class="longinput"
                                               value="<fmt:formatDate value="${educate.beginTime}" pattern="yyyy-MM-dd"/>"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>培训结束时间</label>
                    <span class="field"><input type="text" readonly  name="educate.endTime" id="endTime" class="longinput"
                                               value="<fmt:formatDate value="${educate.endTime}" pattern="yyyy-MM-dd"/>"/></span>
                </p>
                <p>
                    <label>总学习日</label>
                    <span class="field"><input type="text" name="educate.studay" id="studay" class="longinput" value="${educate.studay}"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>请假天数</label>
                    <span class="field"><input type="text" name="educate.leavedays" id="leavedays" class="longinput" value="${educate.leavedays}"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>实际学习天数</label>
                    <span class="field"><input type="text" name="educate.actstuday" id="actstuday" class="longinput" value="${educate.actstuday}"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>调训单位</label>
                    <span class="field"><input type="text" name="educate.diaoXunUnit" id="diaoXunUnit" class="longinput" value="${educate.diaoXunUnit}"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>培训地点</label>
                    <span class="field"><input type="text" name="educate.trainingLocation" id="trainingLocation" class="longinput" value="${educate.trainingLocation}"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>培训单位</label>
                    <span class="field"><input type="text" name="educate.trainingUnit" id="trainingUnit" class="longinput" value="${educate.trainingUnit}"/></span>
                </p>
                <p>
                    <label>是否发放毕业证书</label>
                    <span class="field">
                        <input type="radio" value="0"  name="educate.leavingCertificate" <c:if test="${educate.leavingCertificate==0}">checked</c:if>>是&nbsp;&nbsp;
                        <input type="radio" value="1" name="educate.leavingCertificate" <c:if test="${educate.leavingCertificate==1}">checked</c:if>>否&nbsp;
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>培训总结:</label>
                    <span class="field">
						 <input type="hidden" name="educate.summarize"id="fileUrl" value="${educate.summarize}"/>
						 <input type="button" id="uploadFile" value="上传文件"/><a onclick="upFile()"
                                                                               href="javascript:void(0)">上传</a>
						 <tt id="file" style="color: red"></tt>
					</span>
                </p>
                <p>
                    <label>备注</label>
                    <span class="field"><textarea cols="80" rows="5" name="educate.description" id="description" class="longinput">${educate.description}</textarea></span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="updateEducate();return false;">修改</button>
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