<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>评审调研报告</title>
    <style type="text/css">
        em {
            color: red;
        }
    </style>
    <script type="text/javascript">
        jQuery(function () {

        });

        function audit(id, audit) {
            var msg = ""
            var assessmentLevel = jQuery("select[name='researchReport.assessmentLevel']").val()
            var modifiedDraft = jQuery("input[name='modifiedDraft']").val()

            if (audit === 3) {
                if (assessmentLevel.length < 1) {
                    alert("请选择评审等级")
                    return false
                }

                if (!modifiedDraft) {
                    alert("修改稿查重率为空, 请填写")
                    jQuery("input[name='modifiedDraft']").focus()
                    return false
                }
                msg = "是否通过审批?"
            }
            if (audit === 4) {
                msg = "是否拒绝审批?"
                assessmentLevel = 0
            }
            if (confirm(msg)) {
                jQuery.ajax({
                    url: '/admin/ky/rr/audit.json',
                    data: {
                        "researchReport.id": id,
                        "researchReport.audit": audit,
                        "researchReport.assessmentLevel": assessmentLevel,
                        "researchReport.modifiedDraft": modifiedDraft
                    },
                    type: 'post',
                    dataType: 'json',
                    success: function (result) {
                        alert(result.message);
                        if (result.code === '0') {
                            location.href = document.referrer
                        }
                    },
                    error: function () {
                        alert('操作失败, 请稍后再试');
                    }
                });
            }
        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">评审调研报告</h1>
        <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于审批某调研报告的具体信息<br/>
				</span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <!-- 主要内容开始 -->

            <form id="form1" class="stdform stdform2" method="post" action="">
                <p>
                    <label> <em>*</em> 调研报告类型</label>
                    <span class="field">
                           ${researchReport.researchName}&nbsp;
                        </span>
                </p>

                <p>
                    <label> <em>*</em> 类型</label>

                    <span class="field">
                            <c:if test='${researchReport.type=="teacher"}'>
                                讲师 &nbsp;
                            </c:if>
                            <c:if test='${researchReport.type=="student"}'>
                                学员 &nbsp;
                            </c:if>
                        </span>
                </p>
                <p>
                    <label> <em>*</em> 姓名</label>
                    <span class="field"> ${researchReport.peopleName}&nbsp;</span>
                </p>
                <p>
                    <label> <em>*</em> 班型</label>
                    <span class="field">${researchReport.classType}&nbsp;</span>
                </p>
                <p>
                    <label> <em>*</em> 班次</label>
                    <span class="field">${researchReport.classStr}&nbsp;</span>
                </p>
                <p>
                    <label> <em>*</em> 调研组</label>
                    <span class="field">${researchReport.agroup}&nbsp;</span>
                </p>
                <p>
                    <label> <em>*</em> 指导老师</label>
                    <span class="field">${researchReport.teacher}&nbsp;</span>
                </p>
                <p>
                    <label> <em>*</em> 第一执笔人</label>
                    <span class="field">${researchReport.zbr}&nbsp;</span>
                </p>
                <p>
                    <label> <em>*</em> 课题参与人员</label>
                    <span class="field">${researchReport.participant}&nbsp;</span>
                </p>
                <p>
                    <label> <em>*</em> 联系方式</label>
                    <span class="field">${researchReport.contact} &nbsp;</span>
                </p>

                <p>
                    <label> <em>*</em> 内容</label>
                    <span class="field">
                                <textarea cols="80" rows="5" name="classes.note" class="mediuminput"
                                          disabled>${researchReport.content}</textarea>
                            </span>
                </p>

                <p>
                    <label> <em>*</em> 调研报告附件</label>
                    <span class="field">
                                     <c:if test="${not empty researchReport.fileUrl}">
                                <a href="${researchReport.fileUrl}"
                                   class="stdbtn" title="下载附件" download="">下载附件</a>
                                     </c:if>
                                    <c:if test="${empty researchReport.fileUrl}">
                                        未上传附件
                                    </c:if>
                            </span>
                </p>

                <p>
                    <label>备注</label>
                    <span class="field"><textarea cols="80" rows="5" name="classes.note" class="mediuminput"
                                                  id="note"
                                                  disabled>${researchReport.note}</textarea></span>
                </p>

                <p>
                    <label> <em style="color: red">*</em>评审等级</label>
                    <span class="field">
                             <select name="researchReport.assessmentLevel">
                               <option value="">未选择</option>
                               <option value="1">优</option>
                               <option value="2">良</option>
                               <option value="3">合格</option>
                               <option value="4">不合格</option>
                           </select>
                         </span>
                </p>

                <p>
                    <label> <em style="color: red">*</em>修改稿查重率(%)</label>
                    <span class="field">
                        <input type="text" value="" class="longinput" name="modifiedDraft" onblur="trim(this)"
                               maxlength="100">
                    </span>
                </p>

                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="audit('${researchReport.id}', 3);return false;">同 意</button>
                    <button class="submit radius2" onclick="audit('${researchReport.id}', 4);return false;">拒 绝</button>
                    <input type="reset" class="reset radius2" value="返 回" onclick="history.back();return false;"/>
                </p>
            </form>
            <!-- 主要内容结束 -->
            <div class="clear"></div>
        </div><!-- #updates -->
    </div>
</div>
</body>
</html>