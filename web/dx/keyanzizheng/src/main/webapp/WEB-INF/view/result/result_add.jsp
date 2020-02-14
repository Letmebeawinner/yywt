<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>科研添加成果</title>
    <link type="text/css" rel="stylesheet" href='${ctx}/static/uploadify/uploadify.css'/>
    <script type="text/javascript" src="${ctx}/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/uploadify/upload-file.js"></script>
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
        <h1 class="pagetitle">
            <c:choose>
                <c:when test="${result.resultForm==3}">
                    科研课题申报
                </c:when>
                <c:otherwise>
                    成果登记
                </c:otherwise>
            </c:choose></h1>
        <span>
            <span style="color:red">说明</span><br>
             <c:choose>
                 <c:when test="${result.resultForm==3}">
                     1. 本页面用来申报课题。<br>
                 </c:when>
                 <c:otherwise>
                     1. 本页面用来新建成果。<br>
                 </c:otherwise>
             </c:choose>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addResult">
                <%--隐藏域放在这里会影响样式--%>
                <p>
                    <label class="name"><em style="color: red;">*</em> 论文名称</label>
                    <span class="field"><input type="text" name="result.name" id="name" class="longinput"
                                               maxlength="200"/></span>
                </p>

                <c:choose>
                    <c:when test="${result.resultForm==3}">
                        <p style="display: none">
                            <label><em style="color: red;">*</em>成果形式</label>
                            <span class="field">
                                <select name="result.resultForm" id="resultForm">
                                    <option value="3">课题</option>
                                </select>
                            </span>
                        </p>
                    </c:when>
                    <c:otherwise>
                        <p>
                            <label><em style="color: red;">*</em>成果形式</label>
                            <span class="field">
                                <select name="result.resultForm" id="resultForm"
                                        onchange="chooseResultForm(); secondaryLinkage(this)">
                                    <c:forEach items="${resultFormList}" var="resultForm">
                                        <option value="${resultForm.id}">${resultForm.name}</option>
                                    </c:forEach>
                                </select>
                            </span>
                        </p>
                    </c:otherwise>
                </c:choose>

                <p>
                    <label><em>*</em> 所属处室</label>
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
                    <label> <em>*</em> 是否公开</label>
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
                        <select name="result.journalNature" id="selectJournalNature"></select>
                    </span>
                </p>

                <p>
                    <label> <em>*</em> 级别</label>
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

                <p>
                    <label><em style="color: red;">*</em> 登记时间</label>
                    <span class="field"><input type="text" readonly name="result.regTime" id="regTime"
                                               class="longinput"/></span>
                </p>

                <p class="publishTime1">
                    <label class="publishTime"><em style="color: red;">*</em> 发表时间</label>
                    <span class="field"><input type="text" readonly name="result.publishTime" id="publishTime"
                                               class="longinput"/></span>
                </p>

                <p class="reportingTime">
                    <label><em style="color: red;">*</em> 开始时间</label>
                    <span class="field"><input type="text" readonly name="result.addTime" id="addTime"
                                               class="longinput"/></span>
                </p>

                <p class="reportingTime">
                    <label><em style="color: red;">*</em> 结束时间</label>
                    <span class="field"><input type="text" readonly name="result.endTime" id="endTime"
                                               class="longinput"/></span>
                </p>

                <p>
                    <label class="workName">作者姓名</label>
                    <span class="field"><input type="text" name="result.workName" id="workName"
                                               class="longinput" maxlength="200"/></span>
                </p>

                <p style="display: none" id="taskForceMembers">
                    <label class="taskForceMembers" id="participationGroupMembers">课题组成员</label>
                    <span class="field">
                        <input type="text" name="result.taskForceMembers" id="teacherName" class="longinput"
                               maxlength="200"/>
                    </span>
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
                                               class="longinput" maxlength="8" placeholder="请输入数字"/></span>
                </p>

                <p class="choose2">
                    <label><em style="color: red;">*</em>参编章节信息
                        <small></small>
                    </label>
                    <span class="field"><textarea cols="80" rows="5" name="result.chapter" id="chapter"
                                                  class="longinput"></textarea></span>
                </p>
                    <div class="clear"></div>
                <p>
                    <label>
                        <em style="color: red;">*</em>上传印证材料<br>
                        <span id="isflash"></span>
                    </label>
                    <span class="field">
                        <input type="hidden" name="result.fileUrl" id="fileUrl"/>
                        <input type="button" id="uploadFile" value="上传申请书"/>
                        <center><h4 id="file"></h4></center>
                    </span>
                </p>
                <div class="clear"></div>
                <p id="uploadArgumentTheory" style="display: none">
                    <label> <em>*</em> 上传论证活页</label>
                    <span class="field">
                        <input type="hidden" name="result.fileUrlTheory" id="fileUrlTheory"/>
                        <input type="button" id="uploadFileTheory" value="上传论证活页"/>
                        <center><h4 id="fileTheory"></h4></center>
                    </span>
                </p>
                <div class="clear"></div>

                <p id="awardTitle">
                    <label>获奖列表名称</label>
                    <span class="field">
                        <textarea cols="80" rows="5" name="result.awardTitle"
                                  class="longinput" maxlength="250"></textarea>
                    </span>
                </p>
                <p>
                    <label>备注</label>
                    <span class="field"><textarea cols="80" rows="5" name="result.remark" id="remark"
                                                  class="longinput" maxlength="250"></textarea></span>
                </p>

                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addResultFormSubmit();return false;">添 加</button>
                    <input type="reset" class="reset radius2" value="重 置" onclick="resetData()"/>
                </p>

                <%--隐藏域--%>
                <input type="hidden" name="result.resultType" value="${result.resultType}">
                <input type="hidden" name="result.journalNatureName" id="journalNatureName">
                </p></form>
            <br/>
        </div><!--subcontent-->
    </div><!--contentwrapper-->
</div><!-- centercontent -->
<script type="text/javascript" src="${ctx}/static/admin/js/result.js"></script>
<script type="text/javascript">
    // 二级联动
    function secondaryLinkage(obj) {
        var selectedId = jQuery(obj).find("option:selected").val()
        jQuery.getJSON("/admin/ky/secondaryLinkage.json", {"selectedId": selectedId},
            function (result) {
                var $secondaryType = jQuery("#selectJournalNature");
                $secondaryType.empty()

                if (result.data.length > 0) {
                    jQuery.each(result.data, function () {
                        $secondaryType.append("<option value='" + this.id + "'>" + this.name + "</option>")
                    })
                } else {
                    $secondaryType.append("<option value=''>暂无类型</option>")
                }
            })
    }


    var nowDate = '${nowDate}';
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
        laydate({
            elem: '#regTime',
            format: 'YYYY-MM-DD hh:mm:ss',
            istime: true
        });

        uploadFile("uploadFile", "useless", "myFile", imagePath, callbackFile);
        uploadFile("uploadFileTheory", "useless", "myFile", imagePath, callbackFileTheory);

        jQuery("#resultForm").change()

        //检查flash插件
        if (!testingFlash()) {
            alert("您的浏览器还没有安装flash插件或未允许本站运行，导致上传按钮无法加载，请检查flash插件后重试！");
        } else {
            jQuery("#isflash").html(' <em style="color: red;">包括封面、封底、目录、内容</em><br> <em style="color: red;">请注明原件及复印件</em>')
        }

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
</script>
</body>
</html>