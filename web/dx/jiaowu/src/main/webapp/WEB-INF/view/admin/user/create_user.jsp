<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>在线报名</title>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>

		<link type="text/css" rel="stylesheet" href='${ctx }/static/uploadify/uploadify.css'/>
		<script type="text/javascript" src="${ctx }/static/uploadify/jquery.uploadify.min.js"></script>
		<script type="text/javascript" src="${ctx }/static/uploadify/upload-file.js"></script>
<%--		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>--%>
<%-- 		<script type="text/javascript" src="${ctx}/static/js/custom/elements.js"></script> --%>
        <script type="text/javascript">
           
        jQuery(function(){
        	jQuery("#classTypeId").change(function(){
       		 var selectedClassTypeId = jQuery(this).children('option:selected').val();
       		jQuery("#classId").html("");
       		jQuery.ajax({
	    		url:'${ctx}/admin/jiaowu/class/getClassListByClassType.json',
	    		data:{"classTypeId":selectedClassTypeId
	    			},
	    		type:'post',
	    		dataType:'json',		
	    		success:function (result){
	    			if(result.code=="0"){
	    				var list=result.data;
	    				if(list!=null&&list.length>0){
	    					var classstr="";
	    					for(var i=0;i<list.length;i++){
	    						classstr+="<option value='"+list[i].id+"'>"+list[i].name+"</option>";
	    					}
	    					jQuery("#classId").html(classstr);
	    				}
	    			}else{
	    				jAlert(result.message,'提示',function() {});
	    			}		    			
	    		} ,
	    		error:function(e){
	    			jAlert('添加失败','提示',function() {});
	    		}
	    	});
       	 });
        	
        	var info="${info}";
        	if(info!=""){
        		jAlert(info,'提示',function() {});
        	}

/*			//文件上传地址
			uploadFile("uploadFile",false,"myFile",'http://127.0.0.1:8083',callbackFile);*/

			uploadFile("uploadFile",false,"myFile",'http://10.100.101.1:6694',callbackFile);


        });


		//文件下载的回调数据
		function callbackFile(data){
			data=data.substr(2);
			data=data.substr(0,data.length-2);
			jQuery("#fileUrl").val(data);
			jQuery("#touxiang").attr("src",data);
			jQuery("#touxiangspan").show();
		}
		//下载文件
		function upFile(){
			//jQuery("#file").html( '已上传：'+jQuery(".fileName").html());
			jQuery("#uploadFile").uploadify('upload');
		}


		  //获取年龄
			function checkAge() {
			var	idNumber =	jQuery("#idNumber").val();

				jQuery.ajax({
					url:'${ctx}/admin/jiaowu/user/checkAge.json',
					data:{"idNumber":idNumber},
					type:'post',
					dataType:'json',
					success:function (result) {
						if (result.code == "0") {
							var date = result.data;
							jQuery("#age").val(date);
						}else{
							jAlert(result.message,'提示',function() {});
						}
					}
				});
			}


		function clear() {
			console.log(1)
			jQuery(":input").val('');
			jQuery("#classTypeId").find("option:selected").val('');
			jQuery("#classId").find("option:selected").val('');
			jQuery("#sex").find("option:selected").val('');
			jQuery("#politicalStatus").find("option:selected").val('');
			jQuery("#touxiang").attr('src','');
			jQuery("#note").val('');
		}



        function addUser(){
        	var params=jQuery("#form1").serialize();
        	jQuery.ajax({
	    		url:'${ctx}/admin/jiaowu/user/createUser.json',
	    		data:params,
	    		type:'post',
	    		dataType:'json',		
	    		success:function (result){
	    			if(result.code=="0"){
	    				/*window.location.href="${ctx}/admin/jiaowu/user/toCreateUser.json?flag=1";*/
					   var uerNo =	result.data;
						jAlert("恭喜"+uerNo+"报名成功,可以用该帐号登录",'提示',function() {});
						//添加成功后，将所有数据置为空
						//jQuery(":input").val('');
						jQuery("#classTypeId").find("option:selected").val('');
						jQuery("#classId").empty();
						jQuery("#name").val("");
						jQuery("#unit").val("");
						jQuery("#business").val("");
						jQuery("#sex").find("option:selected").val('');
						jQuery("#politicalStatus").find("option:selected").val('');
						jQuery("#touxiang").attr('src','');
						jQuery("#idNumber").val('');
						jQuery("#mobile").val('');
						jQuery("#age").val('');
						jQuery("#note").val('');
						jQuery("#nationality").val("");
						jQuery("#email").val("");
						jQuery("#password").val("");
						jQuery("#confirmpassword").val("");
						jQuery("#carNumber").val("");
	    			}else{
	    				jAlert(result.message,'提示',function() {});
	    			}
	    		} ,
	    		error:function(e){
	    			jAlert('添加失败','提示',function() {});
	    		}
	    	});
        }

		function selectUnit(){
			window.open('${ctx}/jiaowu/user/unitListForSelect.json','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=1200,height=800');
		}

		function addUnit(unitIdAndName){
			/*jQuery("#unitId").val(unitIdAndName[0]);
			jQuery("#unitspan").html(unitIdAndName[1]);
			if(unitIdAndName[0]!=163){
				jQuery("#unitp").hide();
				jQuery("#unitName").val("");
			}else{
				jQuery("#unitp").show();
			}*/
			jQuery("#unit").val(unitIdAndName[1]);
		}





    </script>

       <%-- </script>--%>
	</head>
	<body>

	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">
					<c:if test="${name==null||name==''}">
						在线报名
					</c:if>
					<c:if test="${name!=null&&name!=''}">
						${name}轮巡管理系统
					</c:if>
				</h1>
                <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于在线报名<br />
                    2.按要求填写相关信息,点击"提交"按钮,在线报名.<br />
                    3.带有红色<span style="color: red">*</span>标记的内容为必填部分。
				</span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper"><!--contentwrapper-->
                <div id="updates" class="subcontent">
        		<!-- 主要内容开始 -->
					<form id="form1" class="stdform" method="post" action="">
				<div id="validation" class="subcontent">
            	<%----%>
                    <%--<form id="form1" class="stdform" method="post" action="">--%>
						<%--<input type="hidden" id="unitId" name="user.unitId" />--%>
                    	<!-- <p>
                        	<label><em style="color: red;">*</em>年份</label>
                            <span class="field"><input type="text" name="year" id="year" class="longinput" placeholder="年份需为4位数,如2017"/></span>
                        </p>
                        <p>
                        	<label><em style="color: red;">*</em>月份</label>
                            <span class="field"><input type="text" name="month" id="month" class="longinput" placeholder="月份需为2位数,如05"/></span>
                        </p> -->
						<div class="tables-all">
								<table>
									<tr>
										<td rowspan="2">
												<tt><em style="color: red;">*</em>班型</tt>
										</td>
										<td rowspan="2" >
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
										<td >
											<tt><em style="color: red;">*</em>班次</tt>

										</td>
										<td>
											<span class="field">
												<select name="user.classId" id="classId">

												</select>
											</span>
										</td>
										<td colspan="2">
												<tt><em style="color: red;">*</em>姓名</tt>

										</td>
										<td colspan="2">
											<span class="field"><input type="text" name="user.name" id="name" class="longinput" placeholder="请填写名称"/></span>
										</td>
										<td rowspan="3" colspan="2" width="100px" >
												 <span class="field">
												 <input type="hidden" name="user.path"  id="fileUrl" />
												 <em style="color: red;">* 请按照2寸照片格式,上传图片</em>
													 <input type="button" id="uploadFile" value="上传文件"/>
												 <a onclick="upFile()" href="javascript:void(0)">上传</a>
												 <center><h4  id="file"></h4></center>
													 <span class="field" id="touxiangspan" style="display: none"><img src="" id="touxiang" style="width: 100px;height: 100px;"/></span>
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
												<option value="1" >民主党派</option>
												<option value="2" >无党派人士</option>
												<option value="3" >群众</option>
												<option value="4" >其它</option>
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
											<tt><em style="color: red;">*</em>单位</tt>
										</td>
										<td >
											<span class="field">
												<%--<span id="unitspan"></span>--%>
												<a href="javascript:selectUnit()" class="stdbtn btn_orange">选择单位</a>
											</span>
										</td>

										<td colspan="2">
											<tt><em style="color: red;">*</em>其它单位</tt>

										</td>
										<td colspan="2">
											<span class="field">
												<input type="text" name="user.unit" id="unit" class="longinput" placeholder="请填写其它单位" />
											</span>
										</td>
										<td>
											<tt><em style="color: red;">*</em>级别</tt>
										</td>
										<td>
											<span class="field">
										<%--<input type="text" name="user.business" id="business" class="longinput" placeholder="请填写职务/职称"/>--%>
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
											</select>
										</span>
										</td>

									</tr>
									<tr>
										<%--<td>
												<tt><em style="color: red;">*</em>其它单位</tt>

										</td>
										<td colspan="3">
											<span class="field">
												<input type="text" name="user.unit" id="unit" class="longinput" placeholder="请填写其它单位" />
											</span>
										</td>--%>
											<td>
												<tt><em style="color: red;">*</em>学员单位及职务职称（全称）</tt>
											</td>
											<td>
											<span class="field">
												<input type="text" name="user.job" id="job" class="longinput" placeholder="请填写学员单位及职务职称（全称）" />
											</span>
											</td>
										<td>
											<tt><em style="color: red;">*</em>学历</tt>

										</td>
										<td>
													<span class="field">
										<input type="text" name="user.qualification" id="qualification" class="longinput" placeholder="请填写学历" />
									</span>

										</td>
										<td colspan="2">
												<tt><em style="color: red;">*</em>民族</tt>

										</td>
										<td colspan="2">
											<span class="field"><input type="text" name="user.nationality" id="nationality" class="longinput" placeholder="请填写民族"/></span>
										</td>

									</tr>


									<tr>
										<td >
											<tt><em style="color: red;">*</em>身份证号</tt>
										</td>
										<td colspan="3">
											<span class="field"><input type="text" name="user.idNumber" id="idNumber" class="longinput" placeholder="请填写身份证号" onblur="checkAge()"/></span>
										</td>
										<td colspan="2">
												<tt><em style="color: red;">*</em>联系电话(手机号)</tt>
										</td>
										<td colspan="2">
											<span class="field"><input type="text" name="user.mobile" id="mobile" class="longinput" placeholder="请填写联系电话(手机号)"/></span>
										</td>
										<td>
											<tt>邮箱</tt>

										</td>
										<td>
											<span class="field"><input type="text" name="user.email" id="email" class="longinput" placeholder="请填写邮箱"/></span>
										</td>
									</tr>
									<tr style="display: none">
										<td >
											<tt><em style="color: red;">*</em>平台登录密码</tt>

										</td>
										<td colspan="9" style="text-align: left;">
											<span class="field"><input type="password" name="user.password" id="password" class="longinput" placeholder="请填写密码" value="111111"/></span>
										</td>
									</tr>
									<tr style="display: none">
										<td>
											<tt><em style="color: red;">*</em>确认密码</tt>
										</td>
										<td colspan="9" style="text-align: left;">
											<span class="field"><input type="password" name="user.confirmPassword" id="confirmpassword" class="longinput" placeholder="请再次填写密码" value="111111"/></span>
										</td>
									</tr>
									<tr>
										<td>
											<tt><%--<em style="color: red;">*</em>--%>私人车牌号</tt>

										</td>
										<td colspan="9" style="text-align: left;">
											<span class="field"><input type="text" name="user.carNumber" id="carNumber" class="longinput" placeholder="请填写车辆编号" /></span>
										</td>
									</tr>
									<tr>
										<td>
											<tt><em style="color: red;">*</em>年龄</tt>
										</td>
										<td colspan="9" style="text-align: left;">
											<span class="field"><input type="text" name="user.age" id="age" class="longinput" placeholder="请填写年龄" readonly="readonly"  value="" onchange="if(/\D/.test(this.value)){jAlert('只能输入数字','提示',null);this.value='';}"/></span>
										</td>
									</tr>
									<tr>

										<td>
											<tt>备注</tt>
										</td>
										<td colspan="9" style="text-align: left;">
											<span class="field"><textarea cols="80" rows="5" name="user.note" class="mediuminput" id="note" style="resize: none;"></textarea></span>
										</td>
									</tr>
									<%--<tr>
										<td>
											<tt></tt>
										</td>
										<td colspan="9" style="text-align: left;">
											<span class="field"><img src="" id="touxiang"/></span>
										</td>
									</tr>--%>

								</table>

						</div>

                    
					
            </div>
						</form>
					<p class="stdformbutton">
						<button class="radius2" onclick="addUser()" id="submitButton" style="background-color: #d20009;border:1px solid #de4204;width:120px;line-height:28px;font-size:14px;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">
							提交
						</button>
						<button class="radius2" onclick="clear()" id="submitButton1" style="background-color: #d20009;border:1px solid #de4204;width:120px;line-height:28px;font-size:14px;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 20px;">
							清空
						</button>
					</p>
            <!-- 主要内容结束 -->
				<div class="clear"></div>
            </div><!-- #updates -->
	    </div><!--contentwrapper-->
	    </div>

	</body>
</html>