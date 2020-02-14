<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>调研报告归档</title>
    <style type="text/css">
        em {
            color: red;
        }
    </style>
    <script type="text/javascript">
        jQuery(function () {
            jQuery("select[name='researchReport.assessmentLevel']").val(${researchReport.assessmentLevel})
        });

        function filed(reporyId) {
            // 验证
            var classification = jQuery("input[name='classification']:checked").val();
            var reportClassify = jQuery("select[name='reportClassify']").val();
            if (!classification) {
                alert("档案分类未选择, 请选择后重试")
                return false
            }
            if (!reportClassify) {
                alert("调研报告分类未选择, 请选择后重试")
                return false
            }

            // 提交表单
            if (confirm("是否确定归档")) {
                jQuery.post("${ctx}/admin/ky/zz/doReportFiled.json",
                    {
                        "reportFiled.classification": classification,
                        "reportFiled.reportClassify": reportClassify,
                        "reportId": '${researchReport.id}'
                    },
                    function (data) {
                        alert(data.message)
                        if (data.code === "0") {
                            window.location.href = "${ctx}/admin/ky/rr/researchReportLibrary.json?resultType=2&isArchive=0"
                        }
                    }, "json")
            }

        }
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">调研报告归档</h1>
        <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于归档某调研报告的具体信息<br/>
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
                             <select name="researchReport.assessmentLevel" disabled>
                               <option value="">未选择</option>
                               <option value="1">优</option>
                               <option value="2">良</option>
                               <option value="3">合格</option>
                               <option value="4">不合格</option>
                           </select>
                         </span>
                </p>

                <p>
                    <label> <em style="color: red">*</em>初稿查重率(%)</label>
                    <span class="field">
                    ${researchReport.firstDraft}&nbsp;
                </span>
                </p>

                <p>
                    <label> <em style="color: red">*</em>修改稿查重率(%)</label>
                    <span class="field">
                        ${researchReport.modifiedDraft}&nbsp;
                    </span>
                </p>

                <p>
                    <label> <em style="color: red">*</em>修改稿查重率(%)</label>
                    <span class="field">
                        ${researchReport.modifiedDraft}&nbsp;
                    </span>
                </p>

                <p>
                    <label> <em style="color: red">*</em>档案分类</label>
                    <span class="field">
                        <input type="radio" name="classification" value="1"> 课题成果&nbsp;
                        <input type="radio" name="classification" value="2"> 领导批示成果&nbsp;
                    </span>
                </p>

                <p>
                    <label> <em style="color: red">*</em>调研报告分类</label>
                    <span class="field">
                        <select name="reportClassify">
                            <option value="">未选择</option>
                            <option value="1">专题调研报告</option>
                            <option value="2">贵阳决策咨询报告(已批示)</option>
                            <option value="3">贵阳决策咨询报告(未批示)</option>
                            <option value="4">学院调研报告</option>
                            <option value="5">领导调研报告</option>
                        </select>
                    </span>
                </p>

                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="filed('${researchReport.id}');return false;">归 档</button>
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