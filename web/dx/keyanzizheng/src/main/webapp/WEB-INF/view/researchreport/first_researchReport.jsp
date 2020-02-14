<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>初审调研报告</title>
    <style type="text/css">
        em {
            color: red;
        }
    </style>
    <script type="text/javascript">
        jQuery(function () {

        });

        function audit(id, audit) {
            var msg = "";
            if (audit === 1) {
                msg = "是否通过审批?"
            }
            if (audit === 2) {
                msg = "是否拒绝审批?"
            }
            if (confirm(msg)) {
                jQuery.ajax({
                    url: '/admin/ky/rr/audit.json',
                    data: {
                        "researchReport.id": id,
                        "researchReport.audit": audit
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
        <h1 class="pagetitle">初审调研报告</h1>
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

                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="audit('${researchReport.id}', 1);return false;">同 意</button>
                    <button class="submit radius2" onclick="audit('${researchReport.id}', 2);return false;">拒 绝</button>
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