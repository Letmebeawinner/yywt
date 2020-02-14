<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>添加员工</title>
    <link type="text/css" rel="stylesheet" href='/static/uploadify/uploadify.css'/>
    <script type="text/javascript" src="/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="/static/uploadify/upload-file.js"></script>
    <script type="text/javascript" src="${ctx}/static/admin/js/employee.js"></script>
    <style>
        .laydate_table {
            display: none;
        }

        #laydate_hms {
            display: none !important;
        }
    </style>
    <script type="text/javascript">
        /**
         * 时间控件
         */
        jQuery(function(){
            laydate.skin('molv');
            laydate({
                elem: '#birthDay',
                format:'YYYY-MM-DD'
            }); laydate({
                elem: '#enterPartyTime',
                format:'YYYY-MM-DD hh:mm:ss'
            }); laydate({
                elem: '#workTime',
                format:'YYYY-MM-DD hh:mm:ss'
            });

            jQuery("#employeeType").val(${queryEmployee.employeeType});
        });
    </script>
    <script type="text/javascript">
        var fileServicePath='${fileServicePath}';
        function callbackFile(data){
            data=data.substr(2);
            data=data.substr(0,data.length-2);
            jQuery("#fileUrl").val(data);
            jQuery("#file").html( '已上传：'+jQuery(".fileName").html());
        }
        function upFile(){
            jQuery("#uploadFile").uploadify('upload');
        }
        jQuery(function() {
            uploadFile("uploadFile", false, "myFile", fileServicePath, callbackFile);
        });
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">修改教职工</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来添加新的教职工信息<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="updateEmployee">
                <p>
                    <input type="hidden" value="${queryEmployee.id}" name="queryEmployee.id">
                    <label><em style="color: red;">*</em>教职工类别</label>
                    <span class="field">
                        <select name="queryEmployee.employeeType" id="employeeType" class="longinput">
                                <option value="">请选择</option>
                                <%--<option <c:if test="${queryEmployee.employeeType==1}">selected="selected"</c:if> value="1">教师</option>--%>
                                <option <c:if test="${queryEmployee.employeeType==3}">selected="selected"</c:if> value="3">校领导</option>
                                <option <c:if test="${queryEmployee.employeeType==2}">selected="selected"</c:if> value="2">其他县级干部</option>
                                <option <c:if test="${queryEmployee.employeeType==4}">selected="selected"</c:if> value="4">中层干部</option>
                                <option <c:if test="${queryEmployee.employeeType==5}">selected="selected"</c:if> value="5">一般干部</option>
                                <option <c:if test="${queryEmployee.employeeType==6}">selected="selected"</c:if>value="6">技术人员</option>
                        </select>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>职工类别</label>
                    <span class="field">
                        <select name="queryEmployee.category" id="category" class="longinput">
                                <option value="">请选择</option>
                                <option value="参公" <c:if test="${queryEmployee.category.equals('参公')}">selected="selected"</c:if>>参公</option>
                                <option value="事业"<c:if test="${queryEmployee.category.equals('事业')}">selected="selected"</c:if>>事业</option>
                                <option value="工勤"<c:if test="${queryEmployee.category.equals('工勤')}">selected="selected"</c:if>>工勤</option>
                        </select>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>姓名</label>
                    <span class="field"><input type="text" name="queryEmployee.name" id="name" class="longinput" value="${queryEmployee.name}"/></span>
                </p>
                <p>
                    <label>照片:</label>
                    <span class="field">
						 <input type="hidden" name="queryEmployee.picture"   id="fileUrl" value="${queryEmployee.picture}" />
						 <input type="button" id="uploadFile" value="上传文件"/><a onclick="upFile()" href="javascript:void(0)">上传</a>
						 <tt  id="file" style="color: red"></tt>
					</span>
                </p>
                <p>
                    <label>性别</label>
                    <span class="field">
                        <input type="radio" name="queryEmployee.sex" value="0" <c:if test="${queryEmployee.sex==0}">checked</c:if>>男&nbsp;&nbsp;
                        <input type="radio" name="queryEmployee.sex" value="1" <c:if test="${queryEmployee.sex==1}">checked</c:if>>女
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>出生年月</label>
                    <span class="field"><input type="text" readonly name="queryEmployee.birthDay" id="birthDay"  class="longinput"
                                               value="<fmt:formatDate value="${queryEmployee.birthDay}" pattern="yyyy-MM-dd"/>"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>年龄</label>
                    <span class="field"><input type="text" name="queryEmployee.age" id="age"  class="longinput" value="${queryEmployee.age}"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>手机号</label>
                    <span class="field"><input type="text" name="queryEmployee.mobile" id="mobile" class="longinput" value="${queryEmployee.mobile}"/></span>
                </p>
                <p>
                    <label>民族</label>
                    <span class="field"><input type="text" name="queryEmployee.nationality" id="nationality" class="longinput" value="${queryEmployee.nationality}"/></span>
                </p>
                <p>
                    <label>籍贯</label>
                    <span class="field"><input type="text" name="queryEmployee.nativePlace" id="nativePlace" class="longinput" value="${queryEmployee.nativePlace}"/></span>
                </p>
                <p>
                    <label>出生地</label>
                    <span class="field"><input type="text" name="queryEmployee.birthdayPlace" id="birthdayPlace" class="longinput" value="${queryEmployee.birthdayPlace}"/></span>
                </p>
                <p>
                    <label>入党时间</label>
                    <span class="field"><input type="text"  readonly name="queryEmployee.enterPartyTime" id="enterPartyTime" class="longinput"
                    value="<fmt:formatDate value="${queryEmployee.enterPartyTime}" pattern="yyyy-MM-dd"/>"/></span>
                </p>
                <p>
                    <label>参加工作时间</label>
                    <span class="field"><input type="text"  readonly name="queryEmployee.workTime" id="workTime" class="longinput"
                                               value="<fmt:formatDate value="${queryEmployee.workTime}"  pattern="yyyy-MM-dd"/>"/></span>
                </p>
                <p>
                    <label>身份证号</label>
                    <span class="field"><input type="text" name="queryEmployee.identityCard" id="identityCard" class="longinput" value="${queryEmployee.identityCard}"/></span>
                </p>

                <p>
                    <label><em style="color: red;">*</em>专业技术职务</label>
                    <span class="field"><input type="text" name="queryEmployee.profressTelnel" id="profressTelnel" class="longinput" value="${queryEmployee.profressTelnel}"/></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>熟悉专业有何特长</label>
                    <span class="field"><input type="text" name="queryEmployee.speciality" id="speciality" class="longinput" value="${queryEmployee.speciality}"/></span>
                </p>
                <p>
                    <label>全日制学历学位</label>
                    <span class="field"><input type="text" name="queryEmployee.education" id="education" class="longinput" value="${queryEmployee.education}"/></span>
                </p>
                <p>
                    <label>全日制毕业院校及专业</label>
                    <span class="field"><input type="text" name="queryEmployee.profession" id="profession" value="${queryEmployee.profession}"
                                               class="longinput"/></span>
                </p>
                <p>
                    <label>在职教育学历学位</label>
                    <span class="field"><input type="text" name="queryEmployee.jobEducation" id="jobEducation" value="${queryEmployee.jobEducation}"
                                               class="longinput"/></span>
                </p>
                <p>
                    <label>在职教育毕业院校及专业</label>
                    <span class="field"><input type="text" name="queryEmployee.jobProfession" id="jobProfession" value="${queryEmployee.jobProfession}"
                                               class="longinput"/></span>
                </p>
                <p>
                    <label>现任职务</label>
                    <span class="field"><input type="text" name="queryEmployee.presentPost" id="presentPost" value="${queryEmployee.presentPost}"
                                               class="longinput"/></span>
                </p>

                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="updateSelfEmployee();return false;">修 改</button>
                    <input type="reset" class="reset radius2" value="重 置" onclick="resetData()"/>
                </p>
            </form>
            <br/>
        </div>
    </div>
</div>
</body>
</html>