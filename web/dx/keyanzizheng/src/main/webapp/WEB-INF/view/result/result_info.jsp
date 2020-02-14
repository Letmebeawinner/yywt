<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>成果详情</title>
    <script type="text/javascript" src="${ctx}/static/admin/js/result.js"></script>
    <style type="text/css">
        em {
            color: red;
        }
    </style>
    <script type="text/javascript">
        jQuery(function () {
            jQuery("#ownRoom").html(jQuery("select[name='result.teacherResearch'] option[value="
                    + jQuery("input[name='TCHResearch']").val() + "]").text() + "&nbsp;")

            jQuery("#showFormName").html(jQuery("#resultForm").find("option:selected").text() + "&nbsp;")
        })
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">成果详情</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addresult">

                <p>
                    <label> <em>*</em> 成果形式</label>
                    <span class="field" id="showFormName"></span>
                </p>
                <p style="display: none">
                    <label>成果形式</label>
                    <span class="field">
                        <select name="result.resultForm" id="resultForm" disabled onchange="chooseResultForm()">
                           <c:forEach items="${resultFormList}" var="resultForm">
                               <option <c:if test="${result.resultForm==resultForm.id}"> selected</c:if>
                                       value="${resultForm.id}">${resultForm.name}</option>
                           </c:forEach>
                        </select>
                    </span>
                </p>

                <p>
                    <label class="name">论文名称</label>
                    <span class="field">${result.name}&nbsp;</span>
                </p>

                <p style="display: none">
                    <label>所属处室</label>
                    <span class="field">
                        <input type="hidden" name="TCHResearch"
                               value="${result.teacherResearch}"
                               class="longinput"/>
                        <select name="result.teacherResearch" id="teacherResearch" disabled="disabled">
                            <option value="0">--请选择--</option>
                            <c:forEach var="s" items="${subSectionList}">
                                <option value="${s.id}">${s.name}</option>
                                &nbsp;
                            </c:forEach>
                        </select>
                    </span>
                </p>

                <p>
                    <label> <em>*</em> 所属处室</label>
                    <span class="field" id="ownRoom"></span>
                </p>

                <p class="resultSwitch">
                    <label> <em>*</em> 是否公开</label>
                    <span class="field">
                        <c:if test="${result.resultSwitch==1}"> 公开 </c:if>
                        <c:if test="${result.resultSwitch==2}"> 不公开 </c:if>
                        &nbsp;
                    </span>
                </p>

                <p id="radioKindNature">
                    <label id="kindNature">刊物性质</label>
                    <span class="field" id="publicationNature">
                        ${result.journalNatureName}&nbsp;
                    </span>
                </p>

                <p>
                    <label>
                        <em>*</em>
                        级别
                    </label>
                    <span class="field">
                    <c:if test="${result.resultType == 1}">
                        <c:if test="${result.level==1}">国家级&nbsp;&nbsp;</c:if>
                        <c:if test="${result.level==2}">省部级&nbsp;&nbsp;</c:if>
                        <c:if test="${result.level==3}">市级&nbsp;&nbsp;</c:if>
                    </c:if>
                               <c:if test="${result.level==4}">校级&nbsp;&nbsp;</c:if>
                        &nbsp;&nbsp;
                    </span>
                </p>


                <c:if test="${result.resultType != 2}">
                    <p class="publish1">
                        <label class="publish">发表刊物</label>
                        <span class="field">
                        ${result.publish}&nbsp;
                    </span>
                    </p>
                </c:if>
                <p class="choose1">
                    <label>刊号</label>
                    <span class="field">${result.publishNumber}&nbsp;</span>
                </p>


                <p>
                    <label><em style="color: red;">*</em> 登记时间</label>
                    <span class="field">
                         <fmt:formatDate value="${result.regTime}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;
                    </span>
                </p>

                <p class="publishTime1">
                    <label class="publishTime">发表时间</label>
                    <span class="field">
                        <fmt:formatDate value="${result.publishTime}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;
                    </span>
                </p>
                <p class="reportingTime">
                    <label> <em>*</em> 开始时间</label>
                    <span class="field"><fmt:formatDate value="${result.addTime}"
                                                        pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;</span>
                </p>
                <p class="reportingTime">
                    <label>
                        <c:if test="${result.resultType == 1}">
                            <em>*</em>
                        </c:if>
                        结束时间</label>
                    <span class="field"><fmt:formatDate value="${result.endTime}"
                                                        pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;</span>
                </p>
                <p>
                    <label class="workName">作者姓名</label>
                    <span class="field">
                        ${result.workName}&nbsp;
                    </span>
                </p>
                <p style="display: none" id="taskForceMembers">
                    <label class="taskForceMembers" id="participationGroupMembers">课题组成员</label>
                    <span class="field">${result.taskForceMembers}&nbsp;</span>
                </p>

                <p>
                    <label class="wordsNumber">字数</label>
                    <span class="field">
                        ${result.wordsNumber}&nbsp;
                    </span>
                </p>
                <p class="choose2">
                    <label>副主编</label>
                    <span class="field">
                        ${result.associateEditor}&nbsp;
                    </span>
                </p>
                <p class="choose2">
                    <label>副主编字数</label>
                    <span class="field">
                        ${result.associateNumber}&nbsp;
                    </span>
                </p>
                <p class="choose2">
                    <label>参编章节信息
                        <small></small>
                    </label>
                    <span class="field">${result.chapter}&nbsp;</span>
                </p>

                <c:if test="${result.resultType == 2}">
                    <p>
                        <label> <em>*</em> 手机号</label>
                        <span class="field">${result.phoneNumber}&nbsp;</span>
                    </p>

                    <p>
                        <label> <em>*</em> 邮箱</label>
                        <span class="field">${result.mailbox}&nbsp;</span>
                    </p>
                </c:if>

                <p>
                    <label><em>* </em>下载申请书</label>
                    <span class="field">
                              <c:if test="${not empty result.fileUrl}">
                                  <a href="${result.fileUrl}" class="stdbtn" title="下载申请书" download="">下载申请书</a>
                              </c:if>
                              <c:if test="${empty result.fileUrl}">
                                  未上传申请书
                              </c:if>
                    </span>
                </p>
                <p id="uploadArgumentTheory" style="display: none">
                    <label><em>*</em>下载论证活页</label>
                    <span class="field">
                            <c:if test="${not empty result.fileUrlTheory}">
                                <a href="${result.fileUrlTheory}" class="stdbtn" title="下载论证活页" download="">下载论证活页</a>
                            </c:if>
                            <c:if test="${empty result.fileUrlTheory}">
                                未上传论证活页
                            </c:if>
                        </span>
                </p>

                <%--通过领导审批, 开始由科研处审批 课题结项申请--%>
                <c:if test="${result.passStatus > 7 and result.resultForm==3 and result.resultType == 1}">
                    <p>
                        <label>课题结项申请</label>
                        <span class="field">
                            <c:if test="${not empty result.fileUrlDeclaration}">
                                <a href="${result.fileUrlDeclaration}" class="stdbtn"
                                   title="下载课题结项申请" download="">下载课题结项申请</a>
                            </c:if>
                            <c:if test="${empty result.fileUrlDeclaration}">
                                未上传课题结项申请
                            </c:if>
                        </span>
                    </p>
                </c:if>

                <c:if test="${result.ifFile ==2}">
                    <p>
                        <label>档案附件</label>
                        <span class="field">
                            <c:if test="${not empty result.fileUrlAttachment}">
                                <a href="${result.fileUrlAttachment}" class="stdbtn" title="下载档案附件"
                                   download="">下载档案附件</a>
                            </c:if>
                            <c:if test="${empty result.fileUrlAttachment}">
                                未上传档案附件
                            </c:if>
                        </span>
                    </p>
                </c:if>

                <c:if test="${result.resultType == 2 and result.passStatus == 4}">
                    <p>
                        <label>课题结项申请</label>
                        <span class="field">
                                <c:if test="${not empty result.fileUrlDeclaration}">
                                    <a href="${result.fileUrlDeclaration}"
                                       class="stdbtn" title="下载课题结项申请" download="">下载课题结项申请</a>
                                </c:if>
                                <c:if test="${empty result.fileUrlDeclaration}">
                                    未上传课题结项申请
                                </c:if>
                            </span>
                    </p>

                    <p>
                        <label> <em style="color: red">*</em>评审等级</label>
                        <span class="field">
                            <c:if test="${result.assessmentLevel == 1}">优</c:if>
                            <c:if test="${result.assessmentLevel == 2}">良</c:if>
                            <c:if test="${result.assessmentLevel == 3}">合格</c:if>
                            <c:if test="${result.assessmentLevel == 4}">不合格</c:if>
                         </span>
                    </p>
                </c:if>

                <p id="awardTitle">
                    <label>获奖列表名称</label>
                    <span class="field">
                       ${result.awardTitle} &nbsp;
                    </span>
                </p>

                <p>
                    <label>备注
                        <small></small>
                    </label>
                    <span class="field">${result.remark}&nbsp;</span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <input type="reset" class="reset radius2" value="返 回" onclick="history.go(-1);"/>
                </p>
            </form>
            <br/>
        </div>
    </div>
</div>
</body>
</html>