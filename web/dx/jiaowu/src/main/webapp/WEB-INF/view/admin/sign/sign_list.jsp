<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>报名列表</title>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
<%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
        <script type="text/javascript">
        jQuery(function(){
			
            /* var classNumber="${classes.classNumber}";
            jQuery("#classNumber").val(classNumber); */
        });
        function searchForm(){
            jQuery("#searchForm").submit();
        }
        
        function emptyForm(){
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }
			function deleteSign(id){
				jConfirm('您确定要删除该报名信息吗?','确认',function(r){
				    if(r){
				    	jQuery.ajax({
				    		url:'${ctx}/admin/jiaowu/sign/delSign.json?id='+id,
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
        </script>
	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">报名列表</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示在线报名的列表.<br />
					2.可点击"删除"按钮,删除某在线报名记录.<br />
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">

                <!-- 搜索条件，开始 -->
                <div class="overviewhead clearfix mb10">
                    <div class="fl mt5">
                        <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/class/classList.json" method="get">
                            
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
                                <th class="head0 center">姓名</th>
                                <th class="head1 center">性别</th>
                                <th class="head0 center">单位名称</th>
                                <th class="head1 center">电话</th>
                                <th class="head0 center">科目信息</th>
                                <th class="head1 center">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<c:if test="${signList!=null&&signList.size()>0 }">
                            <c:forEach items="${signList}" var="sign">
                            <tr>
                                <td>${sign.name}</td>
                                <td>${sign.sex}</td>
                                <td>${sign.companyName}</td>
                                <td>${sign.mobile}</td>
                                <td>${sign.info}</td>
                                <td class="center">
                                    <a href="" onclick="deleteSign(${sign.id})" class="stdbtn" title="删除">删除</a>
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