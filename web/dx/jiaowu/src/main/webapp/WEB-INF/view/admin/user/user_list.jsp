<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>正式学员列表</title>
	<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
	<script type="text/javascript">
		jQuery(function(){
			jQuery("#classTypeId").change(function(){
				var selectedClassTypeId = jQuery(this).children('option:selected').val();
				jQuery.ajax({
					url:'${ctx}/admin/jiaowu/class/getClassListByClassType.json',
					data:{"classTypeId":selectedClassTypeId
					},
					type:'post',
					dataType:'json',
					success:function (result){
						if(result.code=="0"){
							var list=result.data;
							var classstr="<option value=0>请选择</option>";
							if(list!=null&&list.length>0){

								for(var i=0;i<list.length;i++){
									classstr+="<option value='"+list[i].id+"'>"+list[i].name+"</option>";
								}

							}
							jQuery("#classId").html(classstr);
							jQuery("#classId").val("${user.classId}");
						}else{
							jAlert(result.message,'提示',function() {});
						}
					} ,
					error:function(e){
						jAlert('添加失败','提示',function() {});
					}
				});
			});
			jQuery("#classTypeId").val("${user.classTypeId}");
			jQuery("#classTypeId").trigger("change");

			var studentId="${user.studentId}";
			jQuery("#studentId").val(studentId);
			jQuery("#userId").val("${user.id}");
			jQuery("#name").val("${user.name}");
			jQuery("#idNumber").val("${user.idNumber}");
			jQuery("#email").val("${user.email}");
			jQuery("#mobile").val("${user.mobile}");
			jQuery("#time").val("${time}");
		});
		function searchForm(){
			jQuery("#searchForm").submit();
		}

		function emptyForm(){
			jQuery("input:text").val('');
			jQuery("select").val(0);
		}
		function deleteUser(id){
			jConfirm('您确定要删除吗?','确认',function(r){
				if(r){
					jQuery.ajax({
						url:'${ctx}/admin/jiaowu/user/delUser.json?id='+id,
						type:'post',
						dataType:'json',
						success:function (result){
							if(result.code=="0"){
								window.location.reload();
							}else{
								jAlert(result.message,'提示',function() {});
							}
						},
						error:function(e){
							jAlert('删除失败','提示',function() {});
						}
					});
				}
			});
		}

		function assignMonitor(userId){
			jQuery.ajax({
				url:'${ctx}/admin/jiaowu/user/getMonitor.json',
				data:{"userId":userId
				},
				type:'post',
				dataType:'json',
				success:function (result){
					console.info("success");
					if(result.code=="0"){
						if(result.data.hasMonitor==1){
							jConfirm('该班次原班长为'+result.data.monitorName+',您确定要修改班长吗?','确认',function(r){
								if(r){
									jQuery.ajax({
										url:'${ctx}/admin/jiaowu/user/assignMonitor.json?userId='+userId+"&monitorId="+result.data.monitorId,
										type:'post',
										dataType:'json',
										success:function (result){
											if(result.code=="0"){
												window.location.reload();
											}else{
												jAlert(result.message,'提示',function() {});
											}
										},
										error:function(e){
											jAlert('修改失败','提示',function() {});
										}
									});
								}
							});
						}else{
							jConfirm('您确定要将其设为班长吗?','确认',function(r){
								if(r){
									jQuery.ajax({
										url:'${ctx}/admin/jiaowu/user/assignMonitor.json?userId='+userId,
										type:'post',
										dataType:'json',
										success:function (result){
											if(result.code=="0"){
												window.location.reload();
											}else{
												jAlert(result.message,'提示',function() {});
											}
										},
										error:function(e){
											jAlert('修改失败','提示',function() {});
										}
									});
								}
							});
						}

					}else{
						jAlert(result.message,'提示',function() {});
					}
				},
				error:function(e){
					jAlert('查询失败','提示',function() {});
				}
			});
		}

		function cancelMonitor(userId){
			jQuery.ajax({
				url:'${ctx}/admin/jiaowu/user/cancelMonitor.json?userId='+userId,
				type:'post',
				dataType:'json',
				success:function (result){
					if(result.code=="0"){
						window.location.reload();
					}else{
						jAlert(result.message,'提示',function() {});
					}
				},
				error:function(e){
					jAlert('更新失败','提示',function() {});
				}
			});
		}

		function selectUnit(){
			window.open('${ctx}/jiaowu/user/unitListForSelect.json','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=800,height=600');
		}

		function addUnit(unitIdAndName){
			jQuery("#unitId").val(unitIdAndName[0]);
			jQuery("#unit").val(unitIdAndName[1]);
			jQuery("#unitspan").html(unitIdAndName[1]+'<a href="javascript:delUnit()" class="stdbtn">删除</a>');
		}

		function delUnit(){
			jQuery("#unitId").val("");
			jQuery("#unit").val("");
			jQuery("#unitspan").html("");
		}

		function useExcel(){
			var totalCount="${pagination.totalCount}";
			if(totalCount==""||totalCount=="0"){
				jAlert("暂无数据,不可导出!",'提示',function() {});
				return;
			}
			jQuery("#searchForm").prop("action", "${ctx}/admin/jiaowu/user/exportUserList.json?classId=" + classId);
			jQuery("#searchForm").submit();
			jQuery("#searchForm").prop("action", "${ctx}/admin/jiaowu/user/userList.json");
		}
	</script>
</head>
<body>
<div class="centercontent tables">
	<div class="pageheader notab" style="margin-left: 30px">
		<h1 class="pagetitle">正式学员列表</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示正式学员列表.<br />
					2.可通过班型、班次和学号查询对应的学员.<br />
                </span>
	</div><!--pageheader-->
	<div id="contentwrapper" class="contentwrapper">

		<!-- 搜索条件，开始 -->
		<div class="overviewhead clearfix mb10">
			<div class="fl mt5">
				<form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/user/userList.json" method="get">
					<input type="hidden" name="unitId" id="unitId" value="${user.unitId}"/>
					<input type="hidden" name="unit" id="unit" value="${user.unit}"/>
					<div class="tableoptions disIb mb10">
						<span class="vam">班型 &nbsp;</span>
						<label class="vam">
							<select name="classTypeId" class="vam" id="classTypeId">
								<option value="0">请选择</option>
								<c:if test="${classTypeList!=null&&classTypeList.size()>0}">
									<c:forEach items="${classTypeList }" var="classType">
										<option value="${classType.id }">${classType.name}</option>
									</c:forEach>
								</c:if>
							</select>
						</label>
					</div>

					<div class="disIb ml20 mb10">
						<span class="vam">班次 &nbsp;</span>
						<label class="vam">
							<select name="classId" class="vam" id="classId" >
								<option value="0">请选择</option>
							</select>
						</label>
					</div>
					<div class="disIb ml20 mb10">
						<span class="vam">姓名&nbsp;</span>
						<label class="vam">
							<input id="name" style="width: auto;" name="name" type="text" class="hasDatepicker" value="" placeholder="请输入名称">
						</label>
					</div>
					<div class="disIb ml20 mb10">
						<span class="vam">身份证号 &nbsp;</span>
						<label class="vam">
							<input id="idNumber" style="width: auto;" name="idNumber" type="text" class="hasDatepicker" value="" placeholder="请输入身份证号">
						</label>
					</div>
					<div class="disIb ml20 mb10">
						<span class="vam">单位 &nbsp;</span>
						<label class="vam">
									<span class="field">
                            		<span id="unitspan">
                            		${user.unit}
									<c:if test="${user.unitId!=null&&user.unitId!=0}">
										<a href="javascript:delUnit()" class="stdbtn">删除</a>
									</c:if>
									</span>
										<a href="javascript:selectUnit()" class="stdbtn btn_orange">选择单位</a>
									</span>
						</label>
					</div>
				</form>
				<div class="disIb ml20 mb10">
					<a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
					<a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
					<a href="javascript: void(0)" onclick="useExcel()" class="stdbtn btn_orange">导出Excel</a>
				</div>
			</div>
		</div>
		<!-- 搜索条件，结束 -->

		<!-- 数据显示列表，开始 -->
		<div class="pr">
			<table cellpadding="0" cellspacing="0" border="0" class="stdtable">
				<colgroup>
					<col class="con0"/>
					<col class="con1" />
					<col class="con0"/>
					<col class="con1" />
					<col class="con0"/>
					<col class="con1" />
				</colgroup>
				<thead>
				<tr>

						<th class="head0 center">序号</th>
						<th class="head0 center">姓名</th>
						<th class="head0 center">性别</th>
						<th class="head0 center">政治面貌</th>
						<th class="head0 center">民族</th>
						<th class="head0 center">单位</th>
						<th class="head0 center">学员单位及职务职称（全称）</th>
						<th class="head0 center">联系电话</th>
						<th class="head1 center">状态</th>
					<th class="head1 center">操作</th>
				</tr>
				</thead>
				<tbody>
				<c:if test="${userList!=null&&userList.size()>0 }">
					<c:forEach items="${userList}" var="user" varStatus="index">
						<tr>
								<td>${index.index+1}</td>
								<td>${user.name}</td>
								<td>${user.sex}</td>
								<td>
									<c:if test="${user.politicalStatus==0}">
										中共党员
									</c:if>
									<c:if test="${user.politicalStatus==1}">
										民主党派
									</c:if>
									<c:if test="${user.politicalStatus==2}">
										无党派人士
									</c:if>
									<c:if test="${user.politicalStatus==3}">
										群众
									</c:if>
									<c:if test="${user.politicalStatus==4}">
										其它
									</c:if>
								</td>
								<td>${user.nationality}</td>
								<td>${user.unit}</td>
								<td>${user.job}</td>
								<td>${user.mobile}</td>
								<td >
									<c:if test="${user.status==1}">
										正常
									</c:if>
									<c:if test="${user.status==7}">
										已结业
									</c:if>
									<c:if test="${user.status==8}">
										在校
									</c:if>
								</td>
							<td class="center">
								<a href="${ctx}/admin/jiaowu/user/detailOfUser.json?id=${user.id}" class="stdbtn" title="详细信息">详细信息</a>
							</td>
						</tr>
					</c:forEach>
				</c:if>
				</tbody>
			</table>
			<!-- 分页，开始 -->
			<jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
			<!-- 分页，结束 -->
		</div>
		<!-- 数据显示列表，结束 -->
	</div>
</body>
</html>