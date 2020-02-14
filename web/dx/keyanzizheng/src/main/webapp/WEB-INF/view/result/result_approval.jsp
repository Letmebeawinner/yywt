<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>课题审核</title>
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
        <h1 class="pagetitle">课题审批</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来进行课题信息的审批<br>
                2. 通过审批的课题可点击<span style="color:red">同意</span>按钮将完成课题审批。
                3. 未通过审批的课题可点击<span style="color:red">拒绝</span>按钮将拒绝课题审批。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addresult">

                <p>
                    <label class="name"> <em>*</em> 课题名称</label>
                    <span class="field">
                        ${result.name} &nbsp;</span>
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
                    <label> <em>*</em> 级别</label>
                    <span class="field">
                        <c:if test="${result.level==1}">国家级&nbsp;&nbsp; </c:if>
                        <c:if test="${result.level==2}">省部级&nbsp;&nbsp;</c:if>
                        <c:if test="${result.level==3}">市级&nbsp;&nbsp;</c:if>
                        <c:if test="${result.level==4}">校级&nbsp;&nbsp;</c:if>
                    </span>
                  </p>

                  <p class="reportingTime">
                      <label><em style="color: red;">*</em> 开始时间</label>
                      <span class="field">
                          <fmt:formatDate value="${result.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/> &nbsp;
                      </span>
                  </p>
                  <p class="reportingTime">
                      <label><em style="color: red;">*</em> 结束时间</label>
                      <span class="field">
                          <fmt:formatDate value="${result.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/> &nbsp;
                      </span>
                  </p>
                  <p>
                      <label class="workName"> <em>*</em> 课题负责人</label>
                      <span class="field">${result.workName} &nbsp;</span>
                  </p>
                  <p id="taskForceMembers">
                      <label class="taskForceMembers">课题组成员</label>
                      <span class="field">${result.taskForceMembers} &nbsp;</span>
                  </p>

                <p>
                    <label class="wordsNumber"> <em>*</em> 字数</label>
                    <span class="field">${result.wordsNumber} &nbsp;</span>
                  </p>


                  <c:if test="${not empty result.fileUrlTheory}">
                      <p>
                          <label><em style="color: red;">*</em> 上传申请书</label>
                          <span class="field">
                        <a href="${result.fileUrl}" class="stdbtn" title="下载申请书">下载申请书</a>
                    </span>
                      <div class="clear"></div>
                      </p>
                  </c:if>
                  <c:if test="${not empty result.fileUrlTheory}">
                      <p>
                          <label> <em>*</em> 上传论证活页</label>
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
                    <span class="field"><textarea cols="80" rows="5" name="result.remark" id="remark"
                                                  readonly="readonly"   class="longinput">${result.remark}</textarea></span>
                </p>
                <p class="stdformbutton" style="text-align: center">

                    <button class="submit radius2" onclick="intoResultStorage(${result.id});return false;">同意</button>
                    <button class="submit radius2" onclick="notPassResult(${result.id});return false;">拒绝</button>
                    <input type="reset" class="reset radius2" value="返 回" onclick="javascript:history.go(-1);"/>
                </p>
            </form>
            <br/>
        </div><!--subcontent-->
    </div><!--contentwrapper-->
</div><!-- centercontent -->
<script type="text/javascript">
    var passReason = "";
    /**
     * 成果审批未通过
     * @param resultId
     */
    function notPassResult(resultId) {
        jQuery.alerts._show('输入原因', null, null, 'addCont', function (confirm) {
            if (confirm) {
                if (passReason != "" && passReason != null) {
                    jQuery.ajax({
                        url: "/admin/ky/updateResult.json",
                        data: {
                            "result.id": resultId,
                            "result.passStatus": 6
                        },
                        type: "post",
                        dataType: "json",
                        async: false,
                        success: function (result) {
                            if (result.code == "0") {
                                sendInfo(result);
                            } else {
                                alert(result.message);
                                return;
                            }
                        }
                    });
                }else{
                    alert("请输入原因!");
                    passResult(resultId);
                }
            }
        });
        jQuery('#popup_message').html("<textarea cols='120' rows='8' id='passReason' onchange='setPassReason()'></textarea>");
        // 修改弹窗的位置。距窗口上边距150px，左边距30%.
        jQuery('#popup_container').css({
            top: 250,
            left: '30%',
            overflow: 'hidden'
        });
        jQuery('#popup_container').css("max-height", "800px");
        jQuery('#popup_message').css("max-height", "600px");
        jQuery('#popup_message').css('overflow-y', 'scroll');
    }
    /**
     * 发送站内信
     * @param taskId
     */
    function sendInfo(result) {
        jQuery.ajax({
            url: "/admin/ky/sendInfo.json",
            data: {
                "content": "你申报的课题成果‘" + result.data.name + "'未通过科研处领导审批，原因：" + passReason,
                "id": result.data.sysUserId
            },
            type: "post",
            dataType: "json",
            async: false,
            success: function (result) {
                if (result.code == "0") {
                    alert(result.message);
                    location.href = document.referrer
                } else {
                    alert(result.message);
                    return;
                }
            }
        });
    }
    function setPassReason() {
        passReason = jQuery("#passReason").val()
    }
    /**
     * 通过科研处领导审批
     * @param resultId
     */
    function intoResultStorage(resultId) {
        if (confirm("是否通过审批？")) {
            jQuery.ajax({
                url: "/admin/ky/updateResult.json",
                data: {
                    "result.id": resultId,
                    "result.passStatus": 7
                },
                type: "post",
                dataType: "json",
                async: false,
                success: function (result) {
                    if (result.code == "0") {
                        alert("操作成功！");
                        window.location.href = "/admin/ky/toResultApprovalList.json?queryResult.passStatus=" + 4+"&queryResult.resultType="+result.data.resultType;;
                    } else {
                        alert(result.message);
                        return;
                    }
                }
            });
        }
    }

</script>
</body>
</html>