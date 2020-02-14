<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>生态文明所课题审批</title>
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
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">生态文明所课题审批</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来进行课题信息的审批。<br>
                2. 通过审批的课题可点击<span style="color:red">同意</span>按钮将完成课题审批。<br>
                3. 未通过审批的课题可点击<span style="color:red">拒绝</span>按钮将拒绝课题审批。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addresult">

                <p>
                    <label class="name"> <em>*</em> 课题名称</label>
                    <span class="field">${result.name} &nbsp;</span>
                </p>
                <p style="display: none;">
                    <label>老师所属教研部</label>
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
                    <span class="field" id="ownRoom"></span>
                </p>

                <p>
                    <label>级别</label>
                    <span class="field">
                        校级&nbsp;&nbsp;
                    </span>
                </p>

                <p class="reportingTime">
                    <label><em>*</em> 开始时间</label>
                    <span class="field" id="startTime">
                        <fmt:formatDate value="${result.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;
                    </span>
                </p>
                <p class="reportingTime">
                    <label><em>*</em> 结束时间</label>
                    <span class="field"><input type="text"
                                               value="<fmt:formatDate value="${result.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                                               name="result.endTime" id="endTime" class="longinput"/></span>
                </p>
                <p>
                    <label class="workName"><em>*</em> 课题负责人</label>
                    <span class="field">${result.workName}&nbsp; </span>
                </p>
                <p id="taskForceMembers">
                    <label class="taskForceMembers">课题组成员</label>
                    <span class="field">${result.taskForceMembers}&nbsp;</span>
                </p>
                <p>
                    <label class="wordsNumber"> <em>*</em> 字数</label>
                    <span class="field">${result.wordsNumber}&nbsp;</span>
                </p>

                <p>
                    <label> <em>*</em> 手机号</label>
                    <span class="field">${result.phoneNumber} &nbsp;</span>
                </p>

                <p>
                    <label> <em>*</em> 邮箱</label>
                    <span class="field">${result.mailbox}&nbsp;</span>
                </p>

                <c:if test="${not empty result.fileUrlTheory}">
                    <p>
                        <label><em>*</em> 申请书</label>
                        <span class="field">
                        <a href="${result.fileUrl}" class="stdbtn" title="下载申请书">下载申请书</a>
                    </span>
                    <div class="clear"></div>
                    </p>
                </c:if>
                <c:if test="${not empty result.fileUrlTheory}">
                    <p>
                        <label><em>*</em> 论证活页</label>
                        <span class="field">
                        <a href="${result.fileUrlTheory}" class="stdbtn" title="下载论证活页">下载论证活页</a>
                    </span>
                    <div class="clear"></div>
                    </p>
                </c:if>

                <c:if test="${result.passStatus == 2}">
                    <p>
                        <label>课题结项申请</label>
                        <span class="field">
                            <c:if test="${not empty result.fileUrlDeclaration}">
                                <a href="${result.fileUrlDeclaration}" class="stdbtn" title="下载课题结项申请" download="">下载课题结项申请</a>
                            </c:if>
                            <c:if test="${empty result.fileUrlDeclaration}">
                                未上传课题结项申请
                            </c:if>
                        </span>
                    </p>
                </c:if>
                <p>
                    <label>备注
                        <small></small>
                    </label>
                    <span class="field">${result.remark}&nbsp;</span>
                </p>
                <p class="stdformbutton" style="text-align: center">

                    <button class="submit radius2" onclick="passResult('${result.id}', '2');return false;">同意</button>
                    <button class="submit radius2" onclick="passResult('${result.id}', '3');return false;">拒绝</button>
                    <input type="reset" class="reset radius2" value="返 回" onclick="history.go(-1);"/>
                </p>
            </form>
            <br/>
        </div><!--subcontent-->
    </div><!--contentwrapper-->
</div><!-- centercontent -->
<script type="text/javascript">
    // 课题立项审批
    function passResult(resultId, passStatus) {
        var msg = ""
        var endTime = jQuery("#endTime").val()
        var startTime = jQuery.trim(jQuery("#startTime").html())

        var endTimeDate = new Date(endTime.replace(/-/g, '/'))
        var startTimeDate = new Date(startTime.replace(/&nbsp;/ig, '').replace(/-/g, '/'))

        if (passStatus === "2") {
            if (!endTime) {
                alert("请选择结束时间")
                return false
            }

            if (endTimeDate < startTimeDate) {
                alert("结束时间不能早于开始时间, 请重新选择")
                return false
            }
            msg = "是否同意立项?"
        }

        if (passStatus === "3") {
            msg = "是否拒绝立项?"
        }

        if (confirm(msg)) {
            jQuery.ajax({
                url: "/admin/zz/projectEstablishment/update.json",
                data: {
                    "result.id": resultId,
                    "result.passStatus": passStatus,
                    "result.endTime": endTime
                },
                type: "post",
                dataType: "json",
                async: false,
                success: function (result) {
                    if (result.code === "0") {
                        alert("操作成功！");
                        window.location.href = "${ctx}/admin/zz/projectEstablishment/list.json?passStatus=1";
                    } else {
                        alert(result.message);
                        return false;
                    }
                }
            });
        }
    }

    /**
     * 时间控件
     */
    jQuery(function () {
        laydate.skin('molv');
        laydate({
            elem: '#endTime',
            format: 'YYYY-MM-DD hh:mm:ss',
            istime: true
        });
    });
</script>
</body>
</html>