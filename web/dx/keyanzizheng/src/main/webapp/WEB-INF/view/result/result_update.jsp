<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>修改成果</title>
    <link type="text/css" rel="stylesheet" href='${ctx}/static/uploadify/uploadify.css'/>
    <script type="text/javascript" src="${ctx}/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/uploadify/upload-file.js"></script>
    <script type="text/javascript" src="${ctx}/static/admin/js/approveBill.js"></script>
    <script type="text/javascript" src="${ctx}/static/admin/js/result.js"></script>
    <script type="text/javascript">
        var nowDate = '${nowDate}';
        /**
         * 时间控件
         */
        jQuery(function () {
            laydate.skin('molv');
            laydate({
                elem: '#publishTime',
                format: 'YYYY-MM-DD hh:mm:ss',
            });
            laydate({
                elem: '#addTime',
                format: 'YYYY-MM-DD hh:mm:ss',
            });
            laydate({
                elem: '#endTime',
                format: 'YYYY-MM-DD hh:mm:ss',
            });
            laydate({
                elem: '#regTime',
                format: 'YYYY-MM-DD hh:mm:ss',
                istime: true
            });

            uploadFile("uploadFileTheory", false, "myFile", imagePath, callbackFileTheory);

            // 回显下拉列表
            jQuery("select[name='result.teacherResearch']").val(jQuery("input[name='TCHResearch']").val())
        });

        function callbackFileTheory(data) {
            data = data.substr(2);
            data = data.substr(0, data.length - 2);
            jQuery("#fileUrlTheory").val(data);
            jQuery("#file").html('已上传：' + jQuery(".fileName").html());
        }
    </script>
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
        <h1 class="pagetitle"><c:choose>
            <c:when test="${result.resultForm==3}">
                课题修改
            </c:when>
            <c:otherwise>
                成果修改
            </c:otherwise>
        </c:choose></h1>
        <span>
                <span style="color:red">说明</span><br>
                <c:choose>
                    <c:when test="${result.resultForm==3}">
                        1. 本页面用来修改课题<br>
                    </c:when>
                    <c:otherwise>
                        1. 本页面用来修改成果<br>
                    </c:otherwise>
                </c:choose>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="updateResult">

                <p>
                    <label class="name"><em style="color: red;">*</em> 论文名称</label>
                    <span class="field"><input type="text" value="${result.name}" name="result.name" id="name"
                                               class="longinput"/></span>
                </p>

                <p>
                    <label> <em>*</em> 所属处室</label>
                    <span class="field">
                        <input type="hidden" name="TCHResearch" value="${result.teacherResearch}" class="longinput"/>
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
                            <option value="1" <c:if test="${result.resultSwitch==1}"> selected </c:if>>公开</option>
                            <option value="2" <c:if test="${result.resultSwitch==2}"> selected </c:if>>不公开</option>
                        </select>
                    </span>
                </p>
                <p id="radioKindNature">
                    <label id="kindNature">刊物性质</label>
                    <span class="field" id="publicationNature">
                        <select name="result.journalNature" id="selectJournalNature">
                              <c:if test="${empty result.journalNature}">
                                  <option value=''>暂无类型</option>
                              </c:if>
                            <c:forEach items="${categoryList}" var="c" varStatus="">
                                <%--<option value="">未选择</option>--%>
                                <option value="${c.id}"
                                        <c:if test="${result.journalNature == c.id}">selected="selected"</c:if>
                                >${c.name}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>

                <p>
                    <label>
                        <em>*</em>
                        级别
                    </label>
                    <span class="field">
                    <c:if test="${result.resultType == 1}">
                        <input type="radio" value="1"
                               <c:if test="${result.level==1}">checked="checked"</c:if>
                               name="result.level"/>国家级&nbsp;&nbsp;
                        <input type="radio" value="2"
                               <c:if test="${result.level==2}">checked="checked"</c:if>
                               name="result.level"/>省部级&nbsp;&nbsp;
                        <input type="radio" value="3"
                               <c:if test="${result.level==3}">checked="checked"</c:if>
                               name="result.level"/>市级&nbsp;&nbsp;
                    </c:if>
                        <input type="radio" value="4"
                               <c:if test="${result.level==4}">checked="checked"</c:if>
                               name="result.level"/>校级&nbsp;&nbsp;
                    </span>
                </p>

                <c:if test="${result.resultType == 1}">
                    <p class="publish1">
                        <label class="publish">发表刊物</label>
                        <span class="field"><input type="text" value="${result.publish}" name="result.publish" id="publish"
                                                   class="longinput"/></span>
                    </p>
                </c:if>

                <p class="choose1">
                    <label>刊号</label>
                    <span class="field"><input type="text" value="${result.publishNumber}" name="result.publishNumber"
                                               id="publishNumber" class="longinput"/></span>
                </p>

                <p>
                    <label><em style="color: red;">*</em> 登记时间</label>
                    <span class="field"><input type="text" readonly name="result.regTime" id="regTime"
                                               value="<fmt:formatDate value="${result.regTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                                               class="longinput"/></span>
                </p>

                <p class="publishTime1">
                    <label class="publishTime"><em style="color: red;">*</em>发表时间</label>
                    <span class="field"><input type="text" readonly
                                               value="<fmt:formatDate value="${result.publishTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                                               name="result.publishTime" id="publishTime" class="longinput"/></span>
                </p>
                <p class="reportingTime">
                    <label><em style="color: red;">*</em> 开始时间</label>
                    <span class="field"><input type="text" readonly
                                               value="<fmt:formatDate value="${result.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                                               name="result.addTime" id="addTime" class="longinput"/></span>
                </p>
                <p class="reportingTime">
                    <label><em style="color: red;">*</em> 结束时间</label>
                    <span class="field"><input type="text" readonly
                                               value="<fmt:formatDate value="${result.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                                               name="result.endTime" id="endTime" class="longinput"/></span>
                </p>

                <p>
                    <label class="workName">作者姓名</label>
                    <span class="field"><input type="text" value="${result.workName}" name="result.workName"
                                               id="workName"
                                               class="longinput"/></span>
                </p>

                <p style="display: none" id="taskForceMembers">
                    <label class="taskForceMembers">课题组成员</label>
                    <span class="field"><input type="text" name="result.taskForceMembers"
                                               class="longinput" value="${result.taskForceMembers}"/></span>
                </p>

                <p>
                    <label class="wordsNumber"><em style="color: red;">*</em>字数</label>
                    <span class="field"><input type="text" onkeyup='this.value=this.value.replace(/\D/gi,"")'
                                               value="${result.wordsNumber}" name="result.wordsNumber" id="wordsNumber"
                                               class="longinput" maxlength="8"/></span>
                </p>
                <p class="choose2">
                    <label>副主编</label>
                    <span class="field"><input type="text" value="${result.associateEditor}"
                                               name="result.associateEditor" id="associateEditor"
                                               class="longinput"/></span>
                </p>
                <p class="choose2">
                    <label>副主编字数</label>
                    <span class="field"><input type="text" onkeyup='this.value=this.value.replace(/\D/gi,"")'
                                               value="${result.associateNumber}" name="result.associateNumber"
                                               id="associateNumber"
                                               class="longinput"/></span>
                </p>
                <p class="choose2">
                    <label><em style="color: red;">*</em>参编章节信息
                        <small></small>
                    </label>
                    <span class="field"><textarea cols="80" rows="5" name="result.chapter" id="chapter"
                                                  class="longinput">${result.chapter}</textarea></span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>上传申请书</label>
                    <span class="field">
                        <input type="hidden" name="result.fileUrl" value="2222222222222222" id="fileUrl"/>
                        <input type="button" id="uploadFile" value="上传申请书"/>
                        <center><h4 id="file"></h4></center>
                    </span>
                <div class="clear"></div>
                </p>

                <p id="uploadArgumentTheory" style="display: none">
                    <label> <em>*</em> 上传论证活页</label>
                    <span class="field">
                        <input type="hidden" name="result.fileUrlTheory" value="${result.fileUrlTheory}"
                               id="fileUrlTheory"/>
                        <input type="button" id="uploadFileTheory" value="上传论证活页"/>
                    </span>
                <div class="clear"></div>
                </p>

                <p>
                    <label>备注
                        <small></small>
                    </label>
                    <span class="field"><textarea cols="80" rows="5" name="result.remark" id="remark"
                                                  class="longinput">${result.remark}</textarea></span>
                </p>


                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="updateResult();return false;">修 改</button>
                    <input type="reset" class="reset radius2" value="返 回" onclick="javascript:history.go(-1);"/>
                </p>

                <input type="hidden" name="result.id" id="id" value="${result.id}"/>
                <input type="hidden" name="result.resultForm" id="resultForm" value="${result.resultForm}"/>
                <input type="hidden" name="result.journalNatureName" id="journalNatureName"/>

            </form>
            <br/>
        </div><!--subcontent-->
    </div><!--contentwrapper-->
</div><!-- centercontent -->

</body>
</html>