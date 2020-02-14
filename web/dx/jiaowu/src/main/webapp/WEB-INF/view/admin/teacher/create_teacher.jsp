<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>新建师资</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <script type="text/javascript">

        jQuery(function () {
            laydate.skin('molv');
            laydate({
                elem: '#birthDay',
                format: 'YYYY-MM-DD'
            });

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
        });

        function addTeacher() {
            var searchPart = jQuery("#searchPart").val();
            var source = jQuery("input[name='teacher.source']:checked").val()
            var birthDay = jQuery("#birthDay").val()
            var email = jQuery("#email").val()
            var age = jQuery("#age").val()
            var politicalStatus = jQuery("#politicalStatus").val()

            if (source === "1") {
                var employeeNo = jQuery("#employeeNo").val();
                if(employeeNo == null || employeeNo == ''){
                    jAlert("教职工弄不能为空", "提示", function () {
                    });
                    return false;
                }
            }

            if(source==2){
                if (jQuery("#name").val().length === 0) {
                    jAlert("姓名不能为空", "提示", function () {
                    });
                    return false;
                }

//            if (jQuery("#sex").val().length === 0) {
//                jAlert("请选择性别", "提示", function () {
//                });
//                return false;
//            }
                // 政治面貌
//            if (politicalStatus.length === 0) {
//                jAlert("请选择政治面貌", "提示", function () {
//                });
//                return false;
//            }

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
//
//                if (email.length === 0) {
//                    jAlert("请填写邮箱", "提示", function () {
//                    });
//                    return false;
//                }

//                if (age.length === 0) {
//                    jAlert("请填写年龄", "提示", function () {
//                    });
//                    return false;
//                }
                }
            }
            var params = jQuery("#addTeacher").serialize();
            jQuery.ajax({
                url: '${ctx}/admin/jiaowu/teacher/createTeacher.json',
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
                    jAlert('添加失败', '提示', function () {
                    });
                }
            });
        }

        jQuery(function () {
            switchSchool(1);
        });

        function switchSchool(type) {
            if (type == 1) {
                jQuery(".theSource").hide();
                jQuery(".employeeNo").show();
            } else {
                jQuery(".theSource").show();
                jQuery(".employeeNo").hide();
            }
        }

        function selectEmployeeNo() {
            window.open('${ctx}/jiaowu/teacher/selectEmployeeNo.json', 'newwindow', 'toolbar=yes,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=800,height=600');
        }

        function addToPeopleId(teacherIds,teacherNames) {
            jQuery("#teacherspan").html(teacherNames);
            jQuery("#employeeNo").val(teacherIds);
        }
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle">新建师资</h1>
        <span>
                <span style="color:red">说明</span><br>
                1. 本页面用来新建师资<br>
                2. 带有红色<span style="color: red">*</span>标记的内容为必填部分。
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addTeacher">
                <p>
                    <label>类型</label>
                    <span class="field">
                        <input type="radio" name="teacher.source" value="1" checked="checked" onclick="switchSchool('1')"/>&nbsp;本校
                        <input type="radio" name="teacher.source" value="2" onclick="switchSchool('2')"/>&nbsp;外校
                    </span>
                </p>
                <p class="employeeNo" style="display: none">
                    <label><em style="color: red;">*</em>选择教职工</label>
                    <span class="field">
                        <input type="hidden" name="teacher.employeeNo" id="employeeNo"/>
                        <span id="teacherspan"></span>
                        <a href="javascript:selectEmployeeNo()" class="stdbtn btn_orange">选择教职工</a>
                    </span>
                </p>
                <%--<p class="teachingResearchDepartment">
                    <label class="calibration">单位（部门）</label>
                    <span class="field">
                        <select name="teacher.teachingResearchDepartment" id="searchPart">
                            <option value="0">--请选择--</option>
                            <option value="1">党史党建教研部</option>
                            <option value="2">公共管理教研部</option>
                            <option value="3">经济学教研部</option>
                            <option value="4">法学教研部</option>
                            <option value="5">文化与社会发展教研部</option>
                            <option value="6">马列主义理论教研部</option>
                            <option value="7">统一战线理论教研部</option>
                        </select>
                    </span>
                </p>--%>
                <p class="theSource">
                    <label class="calibration">单位（部门）</label>
                    <span class="field"><input type="text" name="teacher.unitDepartment" id="unitDepartment" class="longinput"/></span>
                </p>
                <p class="theSource">
                    <label><em style="color: red;">*</em>姓名</label>
                    <span class="field"><input type="text" name="teacher.name" id="name" class="longinput"/></span>
                </p>
                <p class="theSource">
                    <label class="calibration">出生日期</label>
                    <span class="field">
                        <input id="birthDay" type="text" class="longinput" name="teacher.birthDay" readonly="readonly"/>
                    </span>
                </p>
                <p class="theSource">
                    <label>电话</label>
                    <span class="field"><input type="text" name="userInfo.mobile" id="mobile" class="longinput"/></span>
                </p>
                <p class="theSource">
                    <label class="calibration">邮箱</label>
                    <span class="field"><input type="text" name="userInfo.email" id="email" class="longinput"/></span>
                </p>
                <%--<p class="theSource">
                    <label>密码</label>
                    <span class="field"><input type="password" name="userInfo.password" id="password"
                                               class="longinput"/></span>
                </p>
                <p class="theSource">
                    <label>请再次输入密码</label>
                    <span class="field"><input type="password" name="userInfo.confirmPassword" id="confirmPassword"
                                               class="longinput"/></span>
                </p>--%>
                <p class="theSource">
                    <label class="calibration">年龄</label>
                    <span class="field"><input type="text" name="teacher.age" id="age" class="longinput"
                                               onchange="if(/\D/.test(this.value)){jAlert('只能输入数字','提示',null);this.value='';}"/></span>
                </p>
                <p class="theSource">
                    <label>性别</label>
                    <span class="field">
                        <select name="teacher.sex" id="sex">
                            <option value="">--请选择--</option>
                            <option value="0">男</option>
                            <option value="1">女</option>
                        </select>
                    </span>
                </p>

                <p class="theSource">
                    <label>政治面貌</label>
                    <span class="field">
                        <select name="teacher.politicalStatus" id="politicalStatus">
                            <option value="">--请选择--</option>
                            <option value="0">中共党员</option>
                            <option value="1">民主党派</option>
                            <option value="2">无党派人士</option>
                            <option value="3">群众</option>
                            <option value="4">其他</option>
                            <option value="5">中共预备党员</option>
                            <option value="6">共青团员</option>
                            <option value="7">民革党员</option>
                            <option value="8">民盟盟员</option>
                            <option value="9">民建会员</option>
                            <option value="10">民进会员</option>
                            <option value="11">农工党党员</option>
                            <option value="12">致公党党员</option>
                            <option value="13">九三学社社员</option>
                            <option value="14">台盟盟员</option>
                        </select>
                    </span>
                </p>

                <p class="theSource">
                    <label>民族</label>
                    <span class="field"><input type="text" name="teacher.nationality" id="nationality"
                                               class="longinput"/></span>
                </p>
                <p class="theSource">
                    <label>学历</label>
                    <span class="field"><input type="text" name="teacher.education" id="education"
                                               class="longinput"/></span>
                </p>
                <p class="theSource">
                    <label>专业</label>
                    <span class="field"><input type="text" name="teacher.profession" id="profession"
                                               class="longinput"/></span>
                </p>
                <p class="theSource">
                    <label>职务</label>
                    <span class="field"><input type="text" name="teacher.position" id="position"
                                               class="longinput"/></span>
                </p>
                <p class="stdformbutton" style="text-align: center">
                    <button class="submit radius2" onclick="addTeacher();return false;">添 加</button>
                </p>
            </form>
            <br/>
        </div><!--subcontent-->
    </div><!--contentwrapper-->
</div><!-- centercontent -->
</body>
</html>