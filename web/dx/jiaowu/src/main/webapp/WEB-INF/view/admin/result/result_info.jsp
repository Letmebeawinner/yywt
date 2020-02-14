<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>课题详情</title>
    <script type="text/javascript" src="${ctx}/static/admin/js/result.js"></script>
    <style type="text/css">
        em {
            color: red;
        }
    </style>
    <script type="text/javascript">
        jQuery(function () {
            var $rsResearch = jQuery("select[name='result.teacherResearch']")
            $rsResearch.val(jQuery("input[name='TCHResearch']").val())
            jQuery("#ownRoom").html($rsResearch.find("option:selected").text())

        })
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">课题详情</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addresult">
                <p>
                    <label class="name"> 论文名称</label>
                    <span class="field">
                        ${result.name}&nbsp;
                    </span>
                </p>

                <p style="display: none;">
                    <label><em style="color: red;">*</em> 所属处室</label>
                    <span class="field">
                        <input type="hidden" name="TCHResearch"
                               value="${result.teacherResearch}"
                               class="longinput"/>
                        <select name="result.teacherResearch" id="teacherResearch" disabled>
                            <option value="0">--未选择--</option>
                            <option value="1">党史党建教研部</option>
                            <option value="2">公共管理教研部</option>
                            <option value="3">经济学教研部</option>
                            <option value="4">法学教研部</option>
                            <option value="5">文化与社会发展教研部</option>
                            <option value="6">马列主义理论教研部</option>
                            <option value="7">统一战线理论教研部</option>
                            <option value="8">校委委员</option>
                            <option value="9">办公室</option>
                            <option value="10">机关党委</option>
                            <option value="11">组织人事处</option>
                            <option value="12">财务处</option>
                            <option value="13">后勤管理处</option>
                            <option value="14">纪检监察室</option>
                            <option value="15">教务处</option>
                            <option value="16">信息资源管理处</option>
                            <option value="17">科研管理处</option>
                            <option value="18">生态文明研究所</option>
                            <option value="19">图书馆（《学报》编辑部、校史办）</option>
                            <option value="20">学员管理处</option>
                        </select>
                    </span>
                </p>

                <p>
                    <label> <em>*</em> 所属处室</label>
                    <span class="field" id="ownRoom"></span>
                </p>

                <p>
                    <label><em style="color: red;">*</em> 级别</label>
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

                <c:if test="${result.resultType == 1}">
                    <p>
                        <label class="publish">课题发布单位</label>
                        <span class="field">${result.publish}&nbsp;</span>
                    </p>
                </c:if>

                <p class="reportingTime">
                    <label><em style="color: red;">*</em> 开始时间</label>
                    <span class="field"><fmt:formatDate value="${result.addTime}"
                                                        pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;</span>
                </p>
                <p class="reportingTime">
                    <label>
                        <c:if test="${result.resultType == 1}">
                            <em style="color: red;">*</em>
                        </c:if>
                        结束时间</label>
                    <span class="field"><fmt:formatDate value="${result.endTime}"
                                                        pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;</span>
                </p>
                <p>
                    <label> <em>*</em> 课题负责人</label>
                    <span class="field">
                        ${result.workName}&nbsp;
                    </span>
                </p>
                <p>
                    <label class="taskForceMembers">课题组成员</label>
                    <span class="field">
                        ${result.taskForceMembers}&nbsp;
                    </span>
                </p>

                <p>
                    <label class="wordsNumber">字数</label>
                    <span class="field">
                        ${result.wordsNumber}&nbsp;
                    </span>
                </p>

                <c:if test="${result.resultType == 2}">
                    <p>
                        <label> <em style="color: red;">*</em> 手机号</label>
                        <span class="field">${result.phoneNumber}&nbsp;</span>
                    </p>

                    <p>
                        <label> <em style="color: red;">*</em> 邮箱</label>
                        <span class="field">${result.mailbox}&nbsp;</span>
                    </p>
                </c:if>

                <p>
                    <label><em style="color: red;">*</em> 下载申请书</label>
                    <span class="field">
                        <c:if test="${not empty result.fileUrl}">
                            <a href="${result.fileUrl}"
                               class="stdbtn" title="下载附件" download="">下载申请书</a>
                        </c:if>
                        <c:if test="${empty result.fileUrl}">
                            未上传申请书 <br>
                        </c:if>
                    </span>
                </p>

                <p>
                    <label><em style="color: red;">*</em> 下载论证活页</label>
                    <span class="field">
                            <c:if test="${not empty result.fileUrlTheory}">
                                <a href="${result.fileUrlTheory}"
                                   class="stdbtn" title="下载论证原理" download="">下载论证活页</a>
                            </c:if>
                            <c:if test="${empty result.fileUrlTheory}">
                                未上传论证原理 <br>
                            </c:if>
                    </span>
                </p>

                <%--通过领导审批, 开始由科研处审批 课题结项申请--%>
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
                <div class="clear"></div>
                </p>

                <p>
                    <label>备注</label>
                    <span class="field">${result.remark} &nbsp;</span>
                </p>


                <p class="stdformbutton" style="text-align: center">
                    <input type="reset" class="reset radius2" value="返 回" onclick="history.go(-1);"/>
                </p>
            </form>
            <br/>
        </div><!--subcontent-->
    </div><!--contentwrapper-->
</div><!-- centercontent -->
</body>
</html>