<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>在线报名</title>
    <style>
        #uploadFile {
            margin: 0 auto;
        }

        .uploadify-button {
            margin-top: 15px;
        }
    </style>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>

    <link type="text/css" rel="stylesheet" href='${ctx }/static/uploadify/uploadify.css'/>
    <style type="text/css">
        .tables-all table input {
            width: 100%;
            border: 1px solid #d4d4d4;
            box-shadow: inset 0 1px 3px #ddd;
            height: 30px;
            padding-left: 10px;
            line-height: 30px;
        }
    </style>
    <script type="text/javascript" src="${ctx }/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="${ctx }/static/uploadify/upload-file.js"></script>
    <script type="text/javascript">

        jQuery(function () {
            jQuery("#classTypeId").change(function () {
                var selectedClassTypeId = jQuery(this).children('option:selected').val();
                jQuery("#classId").html("");
                jQuery.ajax({
                    url: '${ctx}/admin/jiaowu/class/getClassListByClassType.json',
                    data: {
                        "classTypeId": selectedClassTypeId
                    },
                    type: 'post',
                    dataType: 'json',
                    success: function (result) {
                        if (result.code == "0") {
                            var list = result.data;
                            if (list != null && list.length > 0) {
                                var classstr = "";
                                for (var i = 0; i < list.length; i++) {
                                    classstr += "<option value='" + list[i].id + "'>" + list[i].name + "</option>";
                                }
                                jQuery("#classId").html(classstr);

                            }
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
            });

            var info = "${info}";
            if (info != "") {
                jAlert(info, '提示', function () {
                });
            }
            uploadFile("uploadFile", false, "myFile", 'http://10.100.101.1:6694', callbackFile);

        });


        //文件下载的回调数据
        function callbackFile(data) {
            data = data.substr(2);
            data = data.substr(0, data.length - 2);
            jQuery("#fileUrl").val(data);
            jQuery("#touxiang").attr("src", data);
            jQuery("#touxiangspan").show();
        }

        //下载文件
        function upFile() {
            jQuery("#uploadFile").uploadify('upload');
            $(".please-upload").hide();
        }


        //获取年龄
        function checkAge() {
            var idNumber = jQuery("#idNumber").val();
            var flag = false;
            jQuery.ajax({
                url: '${ctx}/admin/jiaowu/user/checkAge.json',
                data: {"idNumber": idNumber},
                type: 'post',
                async: false,
                dataType: 'json',
                success: function (result) {
                    if (result.code == "0") {
                        var date = result.data;
                        jQuery("#age").val(date);
                        flag = true;
                    } else {
                        jAlert(result.message, '提示', function () {
                        });
                    }
                }
            });
            return flag;
        }


        function clear() {
            console.log(1)
            jQuery(":input").val('');
            jQuery("#classTypeId").find("option:selected").val('');
            jQuery("#classId").find("option:selected").val('');
            jQuery("#sex").find("option:selected").val('');
            jQuery("#politicalStatus").find("option:selected").val('');
            jQuery("#touxiang").attr('src', '');
            jQuery("#note").val('');
        }


        function addUser() {
            if (!checkAge()) {
                return;
            }


            var submittedName = jQuery("#submittedName").val();
            if (submittedName == "") {
                jAlert('报送人姓名不能为空', '提示', function () {
                });
                return false
            }

            var patt = /^1[0-9]{10}$/;
            var phone = jQuery("#submittedMobile").val();
            if (phone.length < 1) {
                jAlert('请输入手机号', '提示', function () {
                });
                return false
            }
            if (!(patt.test(phone))) {
                jAlert('手机号码有误，请重填', '提示', function () {
                });
                return false;
            }
            var $submitButton = jQuery("#submitButton");
            //  控制按钮为禁用：
            if (confirm("是否提交")) {
                $submitButton.attr({"disabled": "disabled"});
                var params = jQuery("#form1").serialize();
                jQuery.ajax({
                    url: '${ctx}/admin/jiaowu/user/createUser.json',
                    data: params,
                    type: 'post',
                    dataType: 'json',
                    success: function (result) {
                        //将按钮可用
                        $submitButton.removeAttr("disabled");

                        if (result.code == "0") {
                            var uerNo = result.data;


                            //添加成功后，将所有数据置为空
                            //jQuery(":input").val('');
                            jQuery("#classTypeId").find("option:selected").val('');
                            jQuery("#classId").empty();
                            jQuery("#name").val("");
                            jQuery("#unit").val("");
                            jQuery("#business").val("");
                            jQuery("#sex").find("option:selected").val('');
                            jQuery("#politicalStatus").find("option:selected").val('');
                            jQuery("#touxiang").attr('src', '');
                            jQuery("#idNumber").val('');
                            jQuery("#mobile").val('');
                            jQuery("#age").val('');
                            jQuery("#note").val('');
                            jQuery("#nationality").val("");
                            jQuery("#email").val("");
                            jQuery("#password").val("");
                            jQuery("#confirmpassword").val("");
                            jQuery("#carNumber").val("");
                            jQuery("#job").val("");
                            jAlert("恭喜" + uerNo + "报名成功,核实信息请到学员管理-本单位预审列表,如继续添加，请确认后继续添加。", '提示', function () {
                                window.location.reload();
                            });

                        } else {
                            jAlert(result.message, '提示', function () {
                            });
                        }
                    },
                    error: function (e) {
                        //将按钮可用
                        $submitButton.removeAttr("disabled");
                        jAlert('添加失败', '提示', function () {
                        });
                    }
                });

            }
        }

        function selectUnit() {
            window.open('${ctx}/jiaowu/user/unitListForSelect.json', 'newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=800,height=600');
        }

        function addUnit(unitIdAndName) {
            jQuery("#unitId").val(unitIdAndName[0]);
            jQuery("#unit").val(unitIdAndName[1]);
            jQuery("#unitspan").html(unitIdAndName[1]);
        }
    </script>

</head>
<body>

<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">
            在线报名
        </h1>
        <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于在线报名<br/>
                    2.按要求填写相关信息,点击"提交"按钮,在线报名.<br/>
                    3.带有红色<span style="color: red">*</span>标记的内容为必填部分。
				</span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="updates" class="subcontent">
            <!-- 主要内容开始 -->
            <form id="form1" class="stdform" method="post" action="">
                <input type="hidden" id="unitId" name="user.unitId" value="${unit.id}"/>
                <input type="hidden" name="user.unit" id="unit" value="${unit.name}"/>
                <div id="validation" class="subcontent">
                    <div class="tables-all">
                        <table>
                            <tr>
                                <td rowspan="2" style="width: 100px">
                                    <tt><em style="color: red;">*</em>班型</tt>
                                </td>
                                <td rowspan="2" style="width: 140px">
											<span class="field">
									<select name="user.classTypeId" id="classTypeId">
										<option value="">请选择</option>
										<c:if test="${classTypeList!=null&&classTypeList.size()>0}">
                                            <c:forEach items="${classTypeList }" var="classType">
                                                <option value="${classType.id }">${classType.name}</option>
                                            </c:forEach>
                                        </c:if>

									</select>
									</span>
                                </td>
                                <td>
                                    <tt><em style="color: red;">*</em>班次</tt>

                                </td>
                                <td>
											<span class="field" style="width: 240px">
												<select name="user.classId" id="classId">

												</select>
											</span>
                                </td>
                                <td colspan="2" style="width: 0px;">
                                    <tt><em style="color: red;">*</em>姓名</tt>

                                </td>
                                <td colspan="2">
                                    <span class="field"><input type="text" name="user.name" id="name" class="longinput"
                                                               placeholder="请填写姓名"/></span>
                                </td>
                                <td rowspan="3" colspan="2" width="100px" style="margin: 0 auto">
												 <span class="field" style="margin-left: 10px;text-align: center">
												 <input type="hidden" name="user.path" id="fileUrl"/>
													 <span class="please-upload">请上传2寸照片626*413</span>
													 <span class="field" id="touxiangspan" style="display: none"><img
                                                             src="" id="touxiang" style="width: 120px;height: 120px;"/></span>

													 <input type="button" id="uploadFile" value="上传文件"/>
												 <center><h4 id="file"></h4></center>
											</span>
                                </td>

                            </tr>
                            <tr>
                                <td>
                                    <tt><em style="color: red;">*</em>政治面貌</tt>
                                </td>
                                <td>
											<span class="field">
											<select name="user.politicalStatus" id="politicalStatus">
												<option value="">请选择</option>
												<option value="0">中共党员</option>
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
												<%--<option value="1" >民主党派</option>--%>
												<option value="2">无党派人士</option>
												<option value="3">群众</option>
												<option value="4">其它</option>
											</select>
										</span>
                                </td>
                                <td colspan="2">
                                    <tt><em style="color: red;">*</em>性别</tt>
                                </td>
                                <td colspan="2">
											<span class="field">
											<select name="user.sex" id="sex">
												<option value="">请选择</option>
												<option value="男">男</option>
												<option value="女">女</option>
											</select>
										</span>
                                </td>

                            </tr>
                            <tr>
                                <td>
                                    <tt><em style="color: red;">*</em>报名单位</tt>
                                </td>
                                <td>
											<span class="field">
												<span id="unitspan">${unit.name}</span>
												<c:if test="${unit==null}">
                                                    <a href="javascript:selectUnit()" class="stdbtn btn_orange">选择单位</a>
                                                </c:if>
											</span>
                                </td>
                                <td>
                                    <tt><em style="color: red;">*</em>级别</tt>
                                </td>
                                <td>
											<span class="field">
										<select name="user.business" id="business">
												<option value="">请选择</option>
												<option value="1">正厅</option>
												<option value="2">巡视员</option>
												<option value="3">副厅</option>
												<option value="4">副巡视员</option>
												<option value="5">正县</option>
												<option value="6">副县</option>
												<option value="7">调研员</option>
												<option value="8">副调研员</option>
												<option value="9">正科</option>
												<option value="10">副科</option>
												<option value="11">工作人员</option>
											</select>
										</span>
                                </td>
                                <td style="" colspan="2">
                                    <tt><em style="color: red;">*</em>学历</tt>

                                </td>
                                <td>
                                    <span class="field">
                                    <input type="text" name="user.qualification" id="qualification"
                                           class="longinput" placeholder="请填写学历"/>
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <tt><em style="color: red;">*</em>学员单位及职务职称（全称）</tt>
                                </td>
                                <td colspan="3">
											<span class="field">
												<input type="text" name="user.job" id="job" class="longinput"
                                                       placeholder="请填写学员单位及职务职称（全称）"/>
											</span>
                                </td>

                                <td colspan="2">
                                    <tt><em style="color: red;">*</em>民族</tt>

                                </td>
                                <td colspan="2">
                                    <span class="field"><input type="text" name="user.nationality" id="nationality"
                                                               class="longinput" placeholder="请填写民族"/></span>
                                </td>

                            </tr>


                            <tr>
                                <td>
                                    <tt><em style="color: red;">*</em>身份证号</tt>
                                </td>
                                <td colspan="3">
                                    <span class="field"><input type="text" name="user.idNumber" id="idNumber"
                                                               class="longinput" placeholder="请填写身份证号"
                                                               onblur="checkAge()"/></span>
                                </td>
                                <td colspan="2">
                                    <tt><em style="color: red;">*</em>手机号</tt>
                                </td>
                                <td colspan="2">
                                    <span class="field"><input type="text" name="user.mobile" id="mobile"
                                                               class="longinput" placeholder="请填写联系电话(手机号)"/></span>
                                </td>
                                <td>
                                    <tt>邮箱</tt>

                                </td>
                                <td>
                                    <span class="field"><input type="text" name="user.email" id="email"
                                                               class="longinput" placeholder="请填写邮箱"/></span>
                                </td>
                            </tr>
                            <tr style="display: none">
                                <td>
                                    <tt><em style="color: red;">*</em>平台登录密码</tt>

                                </td>
                                <td colspan="9" style="text-align: left;">
                                    <span class="field"><input type="password" name="user.password" id="password"
                                                               class="longinput" placeholder="请填写密码"
                                                               value="111111"/></span>
                                </td>
                            </tr>
                            <tr style="display: none">
                                <td>
                                    <tt><em style="color: red;">*</em>确认密码</tt>
                                </td>
                                <td colspan="9" style="text-align: left;">
                                    <span class="field"><input type="password" name="user.confirmPassword"
                                                               id="confirmpassword" class="longinput"
                                                               placeholder="请再次填写密码" value="111111"/></span>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <tt><%--<em style="color: red;">*</em>--%>个人车号牌</tt>

                                </td>
                                <td colspan="9" style="text-align: left;">
                                    <span class="field"><input type="text" name="user.carNumber" id="carNumber"
                                                               class="longinput" placeholder="请填写车辆编号"/></span>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <tt>年龄</tt>
                                </td>
                                <td colspan="9" style="text-align: left;">
                                    <span class="field"><input type="text" name="user.age" id="age" class="longinput"
                                                               placeholder="请填写年龄" readonly="readonly" value=""
                                                               onchange="if(/\D/.test(this.value)){jAlert('只能输入数字','提示',null);this.value='';}"/></span>
                                </td>
                            </tr>
                            <tr>

                                <td>
                                    <tt>备注</tt>
                                </td>
                                <td colspan="9" style="text-align: left;">
                                    <div class="fl">
                                        <span class="field ">
                                            <textarea cols="80" rows="5" name="user.note" class="mediuminput" id="note"
                                                      style="resize: none;width:500px;"></textarea>
                                        </span>
                                    </div>
                                    <div class="fl">
                                        <ul>
                                            <li class="fl vam mr10">
                                                <tt style="line-height: 31px;width:100px;display: inline-block;"><em
                                                        style="color: red;">*</em>报送人姓名</tt>
                                            </li>
                                            <li class="fl vam">
                                                <input type="text" name="user.submittedName" id="submittedName"
                                                       class="longinput" placeholder=""/>
                                            </li>
                                            <div class="clear"></div>

                                        </ul>
                                        <ul class="mt30">
                                            <li class="fl vam mr10">
                                                <tt style="line-height: 31px;width:100px;display: inline-block;"><em
                                                        style="color: red;">*</em>报送人手机号</tt>
                                            </li>
                                            <li class="fl vam">
                                                <input type="text" name="user.submittedMobile" id="submittedMobile"
                                                       class="longinput" placeholder=""/>
                                            </li>
                                            <div class="clear"></div>

                                        </ul>

                                    </div>
                                    <div class="clear"></div>


                                </td>
                            </tr>
                        </table>

                    </div>


                </div>
            </form>
            <p class="stdformbutton">
                <button class="radius2" onclick="addUser()" id="submitButton"
                        style="background-color: #d20009;border:1px solid #de4204;width:120px;line-height:28px;font-size:14px;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">
                    提交
                </button>
                <button class="radius2" onclick="clear()" id="submitButton1"
                        style="background-color: #d20009;border:1px solid #de4204;width:120px;line-height:28px;font-size:14px;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 20px;">
                    清空
                </button>
            </p>
            <!-- 主要内容结束 -->
            <div class="clear"></div>
        </div><!-- #updates -->
    </div>
</div>

</body>
</html>