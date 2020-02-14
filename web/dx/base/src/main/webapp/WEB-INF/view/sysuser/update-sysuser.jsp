<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>修改管理员用户</title>
    <script type="text/javascript" src="${ctx}/static/admin/js/sysuser/sysuser.js"></script>
    <script type="text/javascript">
        $(function(){
            jQuery("#userType").change(function(){
                var ut=jQuery(this).val();
                if(ut=="5"){
                    jQuery("#unitIdp").show();
                }else{
                    jQuery("#unitIdp").hide();
                    jQuery("#unitId").val("");
                }
            });
            var beforeUserType="${_sysUser.userType}";
            if(beforeUserType=="5"){
                jQuery("#unitIdp").show();
            }
        });
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 30px">
        <h1 class="pagetitle">修改管理员用户</h1>
        <span>
                    <span style="color:red">说明</span><br>
                    1. 本页面用于修改用户；<br>
                    2. 修改信息：修改用户信息，点击<span style="color:red">提交保存</span>按钮修改；点击<span style="color:red">重置表单</span>按钮，将重新显示用户修改前的信息；<br>
                    3. 修改密码：添加新密码并确认，点击<span style="color:red">提交保存</span>按钮，点击<span style="color:red">重置表单</span>按钮，将清空新密码以及确认密码
		</span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="${ctx}/admin/base/sysuser/updateSysUser.json"
                  onsubmit="return false;" id="updateUser">
                <p>
                    <label><em style="color: red;">*</em>用户名</label>
                    <span class="field"><input type="text" name="userName" id="userName2" class="longinput"
                                               value="${_sysUser.userName}"/><em style="color: red;"
                                                                                 id="usna"></em></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>邮箱</label>
                    <span class="field"><input type="text" name="email" id="email2" class="longinput"
                                               value="${_sysUser.email}"/><em style="color: red;" id="mail"></em></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>手机号</label>
                    <span class="field"><input type="text" name="mobile" id="mobile2" class="longinput"
                                               value="${_sysUser.mobile}"/><em style="color: red;" id="mob"></em></span>
                </p>

                <p class="bindingpeople"
                   <c:if test="${_sysUser.userType==1||_sysUser.userType==0 || _sysUser.userType == 4}">style="display: none;"</c:if> >
                    <label><em style="color: red;">*</em>绑定人</label>
                    <span class="field">
                        <a id="deletebinding" onclick="deletequeryAdministrator()">
						    <span id="addUserName">${teacherStudengMap.name}</span>&nbsp;&nbsp;<i
                                class="fa fa-w c-999 fa-close"></i>
					    </a>
                    </span>
                    <span class="field">
                        <button id="teacherList" class="stdbtn btn_red"
                                onclick="getTeacherStudentList(2)">选择教职工</button>
                        <button id="studentList" class="stdbtn btn_red" onclick="getTeacherStudentList(3)">选择学员</button>
					</span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>用户类型</label>
                    <span class="field">
						<select class="query" id="userType">
							    <option value="1" <c:if test="${_sysUser.userType==1||_sysUser.userType==0}">selected="selected"</c:if> >管理员</option>
							    <option value="2" <c:if test="${_sysUser.userType==2}">selected="selected"</c:if>>教职工</option>
							    <option value="3" <c:if test="${_sysUser.userType==3}">selected="selected"</c:if>>学员</option>
                                <option value="4" <c:if test="${_sysUser.userType==4}">selected="selected"</c:if>>驾驶员</option>
                                <option value="5" <c:if test="${_sysUser.userType==5}">selected="selected"</c:if>>单位报名</option>
                                <option value="6" <c:if test="${_sysUser.userType==6}">selected="selected"</c:if>>物业公司</option>
						</select>
					</span>
                </p>
                <p id="unitIdp" style="display: none">
                    <label><em style="color: red;">*</em>单位ID</label>
                    <span class="field"><input type="text" name="unitId" id="unitId" class="longinput" placeholder="" value="${_sysUser.unitId}" onkeyup="if(/\D/.test(this.value)){alert('只能输入数字');this.value='';}"/><span style="color: red;">您可以到学员系统-字典管理-单位列表查看单位ID</span></span>
                </p>
                <p style="display: none">
                    <label><em style="color: red;">*</em>用户id</label>
                    <span class="field"><input type="text" name="id" id="id2" class="longinput" value="${_sysUser.id}"/><em
                            style="display:none;color: red;">用户名已存在</em></span>
                </p>
                <input type="hidden" name="linkId" id="linkId" value="${_sysUser.linkId}"/>
                <input type="hidden" name="userType" id="queryuserType" value="${_sysUser.userType}"/>
                <p class="stdformbutton">
                    <button class="submit radius2" onclick="update()">提交保存</button>
                    <input type="reset" class="reset radius2" value="重置表单" onclick="formReset()"/>
                </p>
            </form>
            <form action="${ctx}/admin/base/sysuser/updateSysUserPsw.json" method="post" class="stdform stdform2"
                  id="updatePsw" onsubmit="return false;">
                <div class="pageheader notab">
                    <h1 class="pagetitle">修改管理员用户密码</h1>
                </div><!--pageheader-->
                <p style="display: none">
                    <label><em style="color: red;">*</em>用户id</label>
                    <span class="field"><input type="text" name="id" id="id" class="longinput"
                                               value="${_sysUser.id}"/><em style="display:none;color: red;" id="usn">用户名已存在</em></span>
                </p>
                <%-- <p>
                     <label><em style="color: red;">*</em>旧密码</label>
                     <span class="field"><input type="password" name="oldPassword" id="oldPassword2" class="longinput"/><em style="color: red;" id="oldPsw"></em></span>
                 </p>--%>
                <p>
                    <label><em style="color: red;">*</em>新密码</label>
                    <span class="field"><input type="password" name="newPassword" id="newPassword2" class="longinput"
                                               placeholder="新密码不能为空"/><em style="color: red;" id="newPsw"></em></span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>确认密码</label>
                    <span class="field"><input type="password" class="longinput" id="rpassword" name="rnewPassword"
                                               placeholder="两次输入密码必须相同"/><em style="color: red;"
                                                                             id="rnewPsw"></em></span>
                </p>
                <p class="stdformbutton">
                    <button class="submit radius2" onclick="savePsw()">提交保存</button>
                    <input type="reset" class="reset radius2" value="重置表单"/>
                </p>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    jQuery(document).ready(function () {
        judgeSelection();
    });

    /**
     * 修改基本信息
     */
    function update() {
        var arr = jQuery('#updateUser').serialize();
        jQuery.ajax({
            url: '${ctx}/admin/base/sysuser/updateSysUser.json',
            type: 'post',
            data: arr,
            dataType: 'json',
            async: false,
            success: function (result) {
                if (result.code == 0) {
                    if (window.confirm("修改成功，是否返回列表页面？")) {
                        window.location = "/admin/base/sysuser/queryUserList.json"
                    }
                } else {
                    alert(result.message);
                }
            }
        })
    }
    /**
     * 修改密码
     */
    function savePsw() {
        var str = jQuery('#updatePsw').serialize();
        jQuery.ajax({
            url: '${ctx}/admin/base/sysuser/updateSysUserPsw.json',
            type: 'post',
            data: str,
            dataType: 'json',
            async: false,
            success: function (result) {
                if (result.code == 0) {
                    if (window.confirm("修改成功，是否返回列表页面？")) {
                        window.location = "/admin/base/sysuser/queryUserList.json"
                    }
                } else {
                    alert(result.message);
                }
            }
        })
    }
    /**
     *
     * 判断显示教职工学员按钮
     * */
    var getusertype;
    function judgeSelection() {
        getusertype = jQuery('#userType').val();
        if (getusertype == 2) {
            jQuery("#teacherList").show();
            jQuery("#studentList").hide();
        }
        if (getusertype == 3) {
            jQuery("#teacherList").hide();
            jQuery("#studentList").show();
        }
    }
    /**
     * 重置表单
     */
    var condition =${_sysUser.userType};
    function formReset() {
        jQuery('.bindingpeople').html(bind).show();
        if (condition == 1 || condition == 0) {
            directAddition();
        }
        if (condition == 2) {
            jQuery(".bindingpeople").show();
            jQuery("#teacherList").show();
            jQuery("#studentList").hide();
            jQuery("#deletebinding").show();
        }
        if (condition == 3) {
            jQuery(".bindingpeople").show();
            jQuery("#teacherList").hide();
            jQuery("#studentList").show();
            jQuery("#deletebinding").show();
        }
    }
</script>
</body>
</html>