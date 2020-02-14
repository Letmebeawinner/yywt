<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>审批</title>
    <link type="text/css" rel="stylesheet" href='${ctx}/static/uploadify/uploadify.css'/>
    <script type="text/javascript" src="${ctx}/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/uploadify/upload-file.js"></script>
    <style type="text/css">
        em {
            color: red;
        }
    </style>
    <script type="text/javascript">
        jQuery(function () {
            jQuery("#ownRoom").html(jQuery("select[name='result.teacherResearch'] option[value="
                    + jQuery("input[name='TCHResearch']").val() + "]").text() + "&nbsp;")
        })

        /**
         * 添加审批
         * @returns {boolean}
         */
        function save(flag, resultId) {
            var msg
            if (flag === 1) {
                msg = "是否拒绝审批"
            }
            if (flag === 2) {
                msg = "是否通过审批"
            }

            if (confirm(msg)) {
                jQuery.ajax({
                    url: "/admin/ky/addApproveBill.json",
                    data: {
                        "approveBill.ifPass": flag,
                        "approveBill.resultId": resultId,
                        "resultType": jQuery("input[name='resultType']").val()
                    },
                    type: "post",
                    dataType: "json",
                    async: false,
                    success: function (result) {
                        if (result.code === "0") {
                            alert(result.message);
                            window.location.href = "/admin/ky/toResultApprovalList.json?queryResult.passStatus=" + 1
                                + "&queryResult.resultType=" + result.data
                        } else {
                            alert(result.message)
                        }
                    }
                });
            }
        }
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">审批</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来部门对课题进行审批<%--，其中审批文件为保留部门批示笔记的文件（照片等形式）--%><br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addapproveBill">
                <p>
                    <label class="name"><em>*</em> 课题名称</label>
                    <span class="field">${result.name}&nbsp;</span>
                </p>

                <p style="display:none;">
                    <label>所属处室</label>
                    <span class="field">
                        <input type="hidden" name="TCHResearch"
                               value="${result.teacherResearch}"
                               class="longinput"/>
                        <select name="result.teacherResearch" id="teacherResearch" disabled="disabled">
                            <option value="0">--请选择--</option>
                            <c:forEach items="${subSectionList}" var="s">
                                <option value="${s.id}">${s.name}</option>
                            </c:forEach>
                        </select>
                    </span>
                </p>

                <p>
                    <label> <em>*</em> 所属处室</label>
                    <span class="field" id="ownRoom">
                    </span>
                </p>

                <p>
                    <label> <em>*</em> 级别</label>
                    <span class="field">
                        <c:if test="${result.level==1}">国家级&nbsp;&nbsp;</c:if>
                        <c:if test="${result.level==2}">省部级&nbsp;&nbsp;</c:if>
                        <c:if test="${result.level==3}">市级&nbsp;&nbsp; </c:if>
                        <c:if test="${result.level==4}">校级&nbsp;&nbsp; </c:if>
                    </span>
                </p>

                <p class="reportingTime">
                    <label><em style="color: red;">*</em> 开始时间</label>
                    <span class="field"><fmt:formatDate value="${result.addTime}"
                                                        pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;</span>
                </p>
                <p class="reportingTime">
                    <label><em style="color: red;">*</em> 结束时间</label>
                    <span class="field"><fmt:formatDate value="${result.endTime}"
                                                        pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;</span>
                </p>
                <p>
                    <label class="workName"> <em>*</em> 课题负责人</label>
                    <span class="field">${result.workName}&nbsp;</span>
                </p>
                <p id="taskForceMembers">
                    <label class="taskForceMembers">课题组成员</label>
                    <span class="field">${result.taskForceMembers}&nbsp;</span>
                </p>
                <p>
                    <label class="wordsNumber"> <em>*</em> 字数</label>
                    <span class="field">${result.wordsNumber}&nbsp;</span>
                </p>

                <c:if test="${not empty result.fileUrlTheory}">
                    <p>
                        <label><em style="color: red;">*</em>上传申请书</label>
                        <span class="field">
                        <a href="${result.fileUrl}" class="stdbtn" title="下载申请书">下载申请书</a>
                    </span>
                    <div class="clear"></div>
                    </p>
                </c:if>
                <c:if test="${not empty result.fileUrlTheory}">
                    <p>
                        <label><em style="color: red;">*</em>上传论证活页</label>
                        <span class="field">
                        <a href="${result.fileUrlTheory}" class="stdbtn" title="下载论证活页">下载论证活页</a>
                    </span>
                    <div class="clear"></div>
                    </p>
                </c:if>
                <p>
                    <label>备注
                        <small></small>
                    </label>
                    <span class="field">${result.remark} &nbsp;</span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="save(2, '${result.id}');return false;">同意</button>
                    <button class="submit radius2" onclick="save(1, '${result.id}');return false;">拒绝</button>
                    <input type="reset" class="reset radius2" value="返 回" onclick="javascript:history.go(-1);"/>
                </p>

                <input type="hidden" value="${resultType}" name="resultType">
            </form>
            <br/>
        </div><!--subcontent-->
    </div><!--contentwrapper-->
</div><!-- centercontent -->
<script type="text/javascript" src="${ctx}/static/admin/js/approveBill.js"></script>
</body>
</html>