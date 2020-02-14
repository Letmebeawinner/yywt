<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>查询调研报告</title>
    <style type="text/css">
        em {
            color: red;
        }
    </style>
    <script type="text/javascript">
        jQuery(function () {


        });

    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">查询调研报告</h1>
        <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于查询某调研报告的具体信息<br/>
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
                    <label><em>*</em> 班型</label>
                    <span class="field">${researchReport.classType}&nbsp;</span>
                </p>
                <p>
                    <label><em>*</em> 班次</label>
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
                <c:if test="${researchReport.approvalDepartment == 2}">
                    <c:if test="${not empty researchReport.firstDraft}">
                        <p>
                            <label> <em style="color: red">*</em>初稿查重率(%)</label>
                            <span class="field">
                        ${researchReport.firstDraft}&nbsp;
                    </span>
                        </p>
                    </c:if>
                    <c:if test="${not empty researchReport.modifiedDraft}">
                    <p>
                        <label> <em style="color: red">*</em>修改稿查重率(%)</label>
                        <span class="field">
                        ${researchReport.modifiedDraft}&nbsp;
                    </span>
                    </p>
                    </c:if>
                </c:if>
                <br/>
            </form>
            <p class="stdformbutton">
                <button class="radius2" onclick="javascript :history.back();" id="submitButton"
                        style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">
                    返回
                </button>
            </p>
            <!-- 主要内容结束 -->
            <div class="clear"></div>
        </div><!-- #updates -->
    </div>
</div>
</body>
</html>