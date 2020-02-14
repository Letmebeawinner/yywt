<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>修改讲师</title>
    <script type="text/javascript"
            src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">
        jQuery(function () {
            laydate.skin('molv');
            laydate({
                elem: '#birthDay',
                format: 'YYYY-MM-DD hh:mm:ss'
            });
            var sex = "${teacher.sex}";
            jQuery("#sex option[value='" + sex + "']").attr("selected", true);
            var politicalStatus = "${teacher.politicalStatus}";
            jQuery("#politicalStatus option[value='" + politicalStatus + "']").attr("selected", true);

            //获取管理类型，只有身份是管理员才能查看身份证
            var uT = ${userType};
            if (uT == 1) {
                jQuery(".identityCardForTeacher").css('display', 'block');
            } else {
                jQuery(".identityCardForTeacher").css('display', 'none');
            }

            // 回显类型和下拉列表
            jQuery("input[name='teacher.source']").bind("change", function () {
                var _source = jQuery(this).val()
                jQuery(".calibration").each(function () {
                    if (_source === "1") {
                        jQuery(this).find("em").show()
                    }
                    if (_source === "2") {
                        jQuery(this).find("em").hide()
                    }
                })
            })
            var source = "${teacher.source}";
            jQuery("input:radio[name='teacher.source'][value=" + source + "]").prop("checked", true).change();

            switchSchool('${teacher.source}');
        });

        function updateTeacher() {
            var searchPart = jQuery("#searchPart").val();
            var source = jQuery("input[name='teacher.source']:checked").val()
            var birthDay = jQuery("#birthDay").val()
            var email = jQuery("#email").val()
            var age = jQuery("#age").val()
            if (jQuery("#name").val().length === 0) {
                jAlert("姓名不能为空", "提示", function () {
                });
                return false;
            }

            // 外校不验证 教研部 出生日期 邮箱 年龄
            if (source === "1") {
                if (searchPart === "0") {
                    jAlert("请选择教研部", "提示", function () {
                    });
                    return false;
                }

                if (birthDay.length === 0) {
                    jAlert("请选择出生日期", "提示", function () {
                    });
                    return false;
                }

//                if (email.length === 0) {
//                    jAlert("请填写邮箱", "提示", function () {
//                    });
//                    return false;
//                }
//
//                if (age.length === 0) {
//                    jAlert("请填写年龄", "提示", function () {
//                    });
//                    return false;
//                }
            }
            var params = jQuery("#form1").serialize();
            jQuery
                .ajax({
                    url: '${ctx}/admin/jiaowu/teacher/updateTeacher.json',
                    data: params,
                    type: 'post',
                    dataType: 'json',
                    success: function (result) {
                        if (result.code == "0") {
                            window.location.href = "${ctx}/admin/jiaowu/teacher/teacherList.json";
                        } else {
                            jAlert(result.message, '提示', function () {
                            });
                        }
                    },
                    error: function (e) {
                        jAlert('更新失败', '提示', function () {
                        });
                    }
                });
        }

        function switchSchool(type) {
            if (type == 1) {
                jQuery(".teachingResearchDepartment").show();
                jQuery(".unitDepartment").hide();
            } else {
                jQuery(".teachingResearchDepartment").hide();
                jQuery(".unitDepartment").show();
            }
        }

        jQuery(function () {
            switchSchool(2);
        });
    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 10px">
        <h1 class="pagetitle">修改讲师</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来修改讲师<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="updates" class="subcontent">
            <!-- 主要内容开始 -->
            <div id="validation" class="subcontent">

                <form id="form1" class="stdform stdform2" method="post" action="">
                    <p>
                        <label><em style="color: red;">*</em>类型</label>
                        <span class="field">
                            <input type="radio" name="teacher.source" value="1" disabled onclick="switchSchool('1')"/>&nbsp;本校
                            <input type="radio" name="teacher.source" value="2" onclick="switchSchool('2')"/>&nbsp;外校
                        </span>
                    </p>
                    <p class="teachingResearchDepartment">
                        <label class="calibration"><em style="color: red;">*</em>单位（部门）</label>
                        <span class="field">
                                <select name="teacher.teachingResearchDepartment">
                                    <option value="0">--请选择--</option>
                                    <option value="1" <c:if test="${teacher.teachingResearchDepartment==1}">selected</c:if>>党史党建教研部</option>
                                    <option value="2" <c:if test="${teacher.teachingResearchDepartment==2}">selected</c:if>>公共管理教研部</option>
                                    <option value="3" <c:if test="${teacher.teachingResearchDepartment==3}">selected</c:if>>经济学教研部</option>
                                    <option value="4" <c:if test="${teacher.teachingResearchDepartment==4}">selected</c:if>>法学教研部</option>
                                    <option value="5" <c:if test="${teacher.teachingResearchDepartment==5}">selected</c:if>>文化与社会发展教研部</option>
                                    <option value="6" <c:if test="${teacher.teachingResearchDepartment==6}">selected</c:if>>马列主义理论教研部</option>
                                    <option value="7" <c:if test="${teacher.teachingResearchDepartment==7}">selected</c:if>>统一战线理论教研部</option>
                                </select>
                            </span>
                    </p>
                    <p class="unitDepartment" style="display: none">
                        <label class="calibration">单位（部门）</label>
                        <span class="field"><input type="text" name="teacher.unitDepartment" id="unitDepartment" class="longinput" value="${teacher.unitDepartment}" /></span>
                    </p>
                    <input type="hidden" value="${teacher.id}" id="teacherId" name="teacher.id"/>
                    <p>
                        <label><em style="color: red;">*</em>姓名</label> <span
                            class="field"><input type="text" name="teacher.name"
                                                 id="name" class="longinput" value="${teacher.name}"/></span>
                    </p>
                    <p>
                        <label class="calibration"><em style="color: red;">*</em>出生日期</label>
                        <span class="field">
                            	<input style="width: 200px;" id="birthDay" type="text" class="longinput" readonly
                                       name="teacher.birthDay" value="${teacher.birthDay}"/>
                            </span>
                    </p>

                    <p>
                        <label>电话</label> <span
                            class="field"><input type="text" name="userInfo.mobile"
                                                 id="mobile" class="longinput" value="${mobile}"/></span>
                    </p>
                    <p>
                        <label class="calibration">邮箱</label> <span
                            class="field"><input type="text" name="userInfo.email"
                                                 id="email" class="longinput" value="${email}"/></span>
                    </p>
                    <p>
                        <label class="calibration">年龄</label> <span
                            class="field"><input type="text" name="teacher.age"
                                                 id="age" class="longinput" value="${teacher.age}"/></span>
                    </p>
                    <p>
                        <label>性别</label> <span class="field"> <select
                            name="teacher.sex" id="sex">
                        <option value="">--请选择--</option>
                        <option value="0" <c:if test="${teacher.sex==0}"></c:if>>男</option>
                        <option value="1" <c:if test="${teacher.sex==1}"></c:if>>女</option>
							</select>
							</span>
                    </p>

                    <p>
                        <label>政治面貌</label>
                        <span class="field">
								<select name="teacher.politicalStatus" id="politicalStatus">
                                    <option value="">--请选择--</option>
									<option value="0" <c:if test="${teacher.politicalStatus==0}"></c:if>>中共党员</option>
									<option value="1" <c:if test="${teacher.politicalStatus==1}"></c:if>>民主党派</option>
									<option value="2" <c:if test="${teacher.politicalStatus==2}"></c:if>>无党派人士</option>
									<option value="3" <c:if test="${teacher.politicalStatus==3}"></c:if>>群众</option>
								</select>
							</span>
                    </p>

                    <p>
                        <label>民族</label> <span class="field"><input type="text"
                                                                     name="teacher.nationality"
                                                                     id="nationality"
                                                                     class="longinput"
                                                                     value="${teacher.nationality}"/></span>
                    </p>
                    <p>
                        <label>学历</label> <span class="field"><input type="text"
                                                                     name="teacher.education"
                                                                     id="education"
                                                                     class="longinput"
                                                                     value="${teacher.education}"/></span>
                    </p>
                    <p>
                        <label>专业</label> <span class="field"><input type="text"
                                                                     name="teacher.profession"
                                                                     id="profession"
                                                                     class="longinput"
                                                                     value="${teacher.profession}"/></span>
                    </p>
                    <p>
                        <label>职务</label> <span
                            class="field"><input type="text" name="teacher.position"
                                                 id="position" class="longinput" value="${teacher.position}"/></span>
                    </p>
                    <br/>
                </form>
                <p class="stdformbutton">
                    <button class="radius2" onclick="updateTeacher()"
                            id="submitButton"
                            style="background-color: #d20009; border-color: #de4204; cursor: pointer; font-weight: bold; padding: 7px 10px; color: #fff; margin-left: 220px;">
                        提交
                    </button>
                </p>
            </div>
            <!-- 主要内容结束 -->
            <div class="clear"></div>
        </div>
        <!-- #updates -->
    </div>
</div>
</body>
</html>