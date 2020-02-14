<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>修改学员</title>
    <style>
        #uploadFile{margin:0 auto;}
        .uploadify-button{margin-top: 15px;}
    </style>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <link type="text/css" rel="stylesheet" href='${ctx }/static/uploadify/uploadify.css'/>
    <style type="text/css">
        .tables-all table input{width:100%;border:1px  solid #d4d4d4;box-shadow: inset 0 1px 3px #ddd;    height: 30px;  padding-left: 10px;  line-height: 30px;}
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
                                    if (classId == list[i].id) {
                                        classstr += "<option value='" + list[i].id + "' selected>" + list[i].name + "</option>";
                                    } else {
                                        classstr += "<option value='" + list[i].id + "'>" + list[i].name + "</option>";
                                    }
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


            uploadFile("uploadFile", false, "myFile", 'http://10.100.101.1:6694', callbackFile);
            var classId = "${classes.id}";
            var id = "${user.id}";
            jQuery("#userId").val(id);
            var name = "${user.name}";
            jQuery("#name").val(name);
            var idNumber = "${user.idNumber}";
            jQuery("#idNumber").val(idNumber);
            var carNumber = "${user.carNumber}";
            jQuery("#carNumber").val(carNumber);
            var mobile = "${mobile}";
            jQuery("#mobile").val(mobile);
            var email = "${email}";
            jQuery("#email").val(email);
            var sex = "${user.sex}";
            jQuery("#sex").val(sex);
            var age = "${user.age}";
            jQuery("#age").val(age);
            var note = "${user.note}";
            jQuery("#note").val(note);
            var business = "${user.business}";
            jQuery("#business").val(business);
            var politicalStatus = "${user.politicalStatus}";
            jQuery("#politicalStatus").val(politicalStatus);
            jQuery("#classTypeId").val("${user.classTypeId}");
            jQuery("#classTypeId").triggerHandler("change");
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
        }


        //获取年龄
        function checkAge() {
            var idNumber = jQuery("#idNumber").val();
            var flag=false;
            jQuery.ajax({
                url: '${ctx}/admin/jiaowu/user/checkAge.json',
                data: {"idNumber": idNumber},
                type: 'post',
                async : false,
                dataType: 'json',
                success: function (result) {
                    if (result.code == "0") {
                        var date = result.data;
                        jQuery("#age").val(date);
                        flag=true;
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
            if(${! empty errorInfo}){
                alert("${errorInfo}");
                return;
            }

            if(!checkAge()){
                return;
            }



            var submittedName = jQuery("#submittedName").val();
            console.log(submittedName);
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

            var params = jQuery("#form1").serialize();
            jQuery.ajax({
                url: '${ctx}/admin/jiaowu/user/updateUser.json',
                data: params,
                type: 'post',
                dataType: 'json',
                success: function (result) {
                    jAlert(result.message, '提示', function () {
                        window.location.href="/admin/jiaowu/user/userList.json";
                    });
                },
                error: function (e) {
                    jAlert('更新失败', '提示', function () {
                    });
                }
            });
        }

        function selectUnit() {
            window.open('${ctx}/jiaowu/user/unitListForSelect.json', 'newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=800,height=600');
        }

        function addUnit(unitIdAndName) {
            jQuery("#unitId").val(unitIdAndName[0]);
            jQuery("#unit").val(unitIdAndName[1]);
            jQuery("#unitName").html(unitIdAndName[1]);
        }
    </script>
</head>
<body>

<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">修改学员</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="updates" class="subcontent">
            <!-- 主要内容开始 -->
            <form id="form1" class="stdform" method="post" action="">
                <input type="hidden" name="user.id" value="${user.id}"/>
                <input type="hidden" name="user.classTypeName" value="${user.classTypeName}"/>
                <input type="hidden" name="user.className" value="${user.className}"/>
                <input type="hidden" name="user.unitId" value="${user.unitId}" id="unitId"/>
                <input type="hidden" name="user.unit" value="${user.unit}" id="unit"/>
                <div id="validation" class="subcontent">
                    <div class="tables-all">
                        <table>
                            <tr>
                                <td rowspan="2" style="width: 100px">
                                    <tt><em style="color: red;">*</em>班型</tt>
                                </td>
                                <td rowspan="2" style="width: 140px">
											<span class="field">
									<select name="user.classTypeId" id="classTypeId" onclick="return false">
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
                                    <tt><em style="color: red;width: 200px;">*</em>班次</tt>

                                </td>
                                <td>
                                    <span class="field" style="width: 240px">
                                        <select name="user.classId" id="classId" onclick="return false">
                                            <option value="${classes.id}">${classes.name}</option>
                                        </select>
                                    </span>
                                </td>
                                <td colspan="2">
                                    <tt><em style="color: red;">*</em>姓名</tt>

                                </td>
                                <td colspan="2">
                                    <span class="field"><input type="text" name="user.name" id="name" class="longinput"
                                                               placeholder="请填写名称"/></span>
                                </td>
                                <td rowspan="3" colspan="2" width="100px" style="margin: 0 auto" >
												 <span class="field" style="margin-left: 10px;text-align: center">
												 <input type="hidden" name="user.path"  id="fileUrl" />
													 <span class="please-upload">请上传2寸照片626*413</span>
													 <span class="field" id="touxiangspan"<c:if test="${user.path==null}"> style="display: none;"</c:if>>
                                                         <img src="${user.path}" id="touxiang" style="width: 120px;height: 120px;"/>
                                                     </span>

													 <input type="button" id="uploadFile" value="上传文件" />
												 <center><h4  id="file"></h4></center>
											</span>
                                </td>

                            </tr>
                            <tr>
                                <td>
                                    <tt><em style="color: red;">*</em>政治面貌</tt>
                                </td>
                                <td>
											<span class="field">
											<select name="user.politicalStatus" id="politicalStatus"
                                                    onclick="return false">
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
												<option value="1">民主党派</option>
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
											<select name="user.sex" id="sex" onclick="return false">
												<option value="">请选择</option>
												<option value="男">男</option>
												<option value="女">女</option>
											</select>
										</span>
                                </td>

                            </tr>
                            <tr>

                                <td>
                                    <tt><em style="color: red;">*</em>学员单位及职务职称（全称）</tt>
                                </td>
                                <td>
											<span class="field">
												<input type="text" name="user.job" id="job" class="longinput"
                                                       placeholder="请填写学员单位及职务职称（全称）" value="${user.job}"/>
											</span>
                                </td>
                                <td>
                                    <tt>学历</tt>

                                </td>
                                <td>
													<span class="field">
										<input type="text" name="user.qualification" id="qualification"
                                               class="longinput" placeholder="请填写学历" value="${user.qualification}"/>
									</span>

                                </td>
                                <td colspan="2">
                                    <tt><em style="color: red;">*</em>民族</tt>

                                </td>
                                <td colspan="2">
                                    <span class="field"><input type="text" name="user.nationality" id="nationality"
                                                               class="longinput" placeholder="请填写民族"
                                                               value="${user.nationality}"/></span>
                                </td>

                            </tr>
                            <tr>
                                <td>
                                    <tt><em style="color: red;">*</em>单位</tt>
                                </td>
                                <td colspan="3">
											<span class="field">
                                                <a href="javascript:selectUnit()" class="stdbtn btn_orange">选择单位</a>
                                                <font id="unitName">${user.unit}</font>
											</span>
                                </td>


                                <td colspan="2">
                                    <tt><em style="color: red;">*</em>级别</tt>
                                </td>

                                <td>
											<span class="field">
										<select name="user.business" id="business" onclick="return false">
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
											</select>
										</span>
                                </td>

                            </tr>
                            <tr>
                                <td>
                                    <tt>身份证号</tt>
                                </td>
                                <td colspan="3">
                                    <span class="field"><input type="text" name="user.idNumber" id="idNumber"
                                                               class="longinput" placeholder="请填写身份证号"
                                                               onblur="checkAge()" value="${user.idNumber}"/></span>
                                </td>
                                <td colspan="2">
                                    <tt><em style="color: red;">*</em>联系电话(手机号)</tt>
                                </td>
                                <td colspan="2">
                                    <span class="field"><input type="text" name="user.mobile" id="mobile"
                                                               class="longinput" placeholder="请填写联系电话(手机号)"
                                                               value="${mobile}" style="width: 120px;"/></span>
                                </td>
                                <td>
                                    <tt>邮箱</tt>

                                </td>
                                <td>
                                    <span class="field"><input type="text" name="user.email" id="email"
                                                               class="longinput" placeholder="请填写邮箱" value="${email}"/></span>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <tt>私人车牌号</tt>

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
                                    <span class="field"><input type="text" value="${user.age}" name="user.age" id="age"
                                                               class="longinput" placeholder="请填写年龄" value=""
                                                               onchange="if(/\D/.test(this.value)){jAlert('只能输入数字','提示',null);this.value='';}"/></span>
                                </td>
                            </tr>
                            <tr>

                                <td>
                                    <tt>备注</tt>
                                </td>
                                <td colspan="9" style="text-align: left;">
                                    <div class="fl">
                                    <span class="field">
                                        <textarea cols="80" rows="5" name="user.note" class="mediuminput" id="note" style="resize: none;width:500px;">${user.note}</textarea>
                                    </span>
                                    </div>
                                    <div class="fl">
                                        <ul>
                                            <li class="fl vam mr10">
                                                <tt style="line-height: 31px;width:100px;display: inline-block;"><em style="color: red;">*</em>报送人姓名</tt>
                                            </li>
                                            <li class="fl vam">
                                                <input type="text" name="user.submittedName" id="submittedName" class="longinput" value="${user.submittedName}" placeholder=""/>
                                            </li>
                                            <div class="clear"></div>

                                        </ul>
                                        <ul class="mt30">
                                            <li class="fl vam mr10">
                                                <tt style="line-height: 31px;width:100px;display: inline-block;"><em style="color: red;">*</em>报送人手机号</tt>
                                            </li>
                                            <li class="fl vam">
                                                <input type="text" name="user.submittedMobile" id="submittedMobile" value="${user.submittedMobile}" class="longinput" placeholder=""/>
                                            </li>
                                            <div class="clear"></div>
                                        </ul>
                                    </div>
                                </td>
                            </tr>
                        </table>

                    </div>

                </div>
            </form>
            <p class="stdformbutton">
                <button class="radius2" onclick="addUser()" id="submitButton" style="background-color: #d20009;border:1px solid #de4204;width:120px;line-height:28px;font-size:14px;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">
                    提交
                </button>
            <!-- 主要内容结束 -->
            <div class="clear"></div>
        </div><!-- #updates -->
    </div>
</div>

</body>
</html>