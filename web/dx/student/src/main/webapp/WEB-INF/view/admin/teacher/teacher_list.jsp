<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>教职工列表</title>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
        <script type="text/javascript">

        function searchForm(){
            jQuery("#searchForm").submit();
        }
        
        function emptyForm(){
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }
		function delTeacher(id){
				if(confirm("您确定要删除吗?")){
				 jQuery.ajax({
		    		url:'${ctx}/admin/jiaowu/teacher/delTeacher.json?id='+id,
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
		}

        function updateTeacherType(id){
            jQuery.ajax({
                url:'${ctx}/admin/jiaowu/teacher/updateTeacherType.json?id='+id,
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

        function cancelClassLeader(id){
            jConfirm('您确定要取消班主任吗?','确认',function(r){
                if(r){
                    jQuery.ajax({
                        url: '${ctx}/admin/jiaowu/teacher/cancelClassLeader.json?employeeId=' + id,
                        type: 'post',
                        dataType: 'json',
                        success: function (result) {
                            if (result.code == "0") {
                                window.location.reload();
                            } else {
                                jAlert(result.message, '提示', function () {
                                });
                            }
                        },
                        error: function (e) {
                            jAlert('更新失败，请稍后重试。', '提示', function () {
                            });
                        }
                    });
                }
            });
        }
        </script>
	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader notab">
                <h1 class="pagetitle">教职工列表</h1>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">
                <!-- 搜索条件，开始 -->
                <div class="overviewhead clearfix mb10">
                    <div class="fl mt5">
                        <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/teacher/teacherList.json" method="get">
                           
                            <div class="disIb ml20 mb10">
                               <span class="vam">姓名 &nbsp;</span>
                               <label class="vam">
                               <input id="name" style="width: auto;" name="teacher.name" type="text" class="hasDatepicker" value="${teacher.name}" placeholder="请输入姓名">
                               </label>
                           </div>
                        </form>
                        <div class="disIb ml20 mb10">
                            <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                            <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
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
                                <th class="head1">出生日期</th>
                                <th class="head1">电话</th>
                    			<th class="head1">性别</th>
                    			<th class="head1">民族</th>
                    			<th class="head1">学历</th>
                    			<th class="head1">专业</th>
                    			<th class="head1">职务</th>
                    			<td class="head0 center">操作</td>
                            </tr>
                        </thead>
                        <tbody>
                        	<c:if test="${teacherList!=null&&teacherList.size()>0 }">
                            <c:forEach items="${teacherList}" var="teacher" varStatus="index">
                            <tr>
                                <td>${index.index+1}</td>
                                <td>${teacher.name}</td>
                                <td>${teacher.birthDay}</td>
                        		<td>${teacher.mobile}</td>
                        		<td>
                            		<c:if test="${teacher.sex==0}">男</c:if>
                            		<c:if test="${teacher.sex==1}">女</c:if>
                        		</td>
                        		<td>${teacher.nationality}</td>
                        		<td>${teacher.education}</td>
                        		<td>${teacher.profession}</td>
                        		<td>${teacher.presentPost}</td>
                                <td class="center">
                                    <c:if test="${teacher.type!=1 && teacher.type!=3}">
                                        <a href="javascript:void(0)" onclick="updateTeacherType(${teacher.id})" class="stdbtn">选为班主任</a>
                                    </c:if>
                                    <c:if test="${teacher.type==1 || teacher.type==3}">
                                        <a href="javascript:void(0)" onclick="cancelClassLeader(${teacher.id})" class="stdbtn">取消班主任</a>
                                    </c:if>
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
    </div>
	</body>
</html>