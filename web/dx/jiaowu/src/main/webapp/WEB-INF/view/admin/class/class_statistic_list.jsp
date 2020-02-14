<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>各个班次报名人数</title>
        <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script> 
        <script type="text/javascript">
        function searchForm(){
            jQuery("#searchForm").submit();
        }
        
        function emptyForm(){
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }
        
			function deleteClassType(id){
				jConfirm('您确定要删除吗?','确认',function(r){
				    if(r){
				    	jQuery.ajax({
				    		url:'${ctx}/admin/jiaowu/classType/delClassType.json?id='+id,
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
                <h1 class="pagetitle">各个班次报名人数</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示班型的列表.<br />
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">

                <%--<!-- 搜索条件，开始 -->--%>
                <%--<div class="overviewhead clearfix mb10">--%>
                    <%--<div class="fl mt5">--%>
                        <%--<form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/classType/classTypeList.json" method="get">--%>
                            <%--<div class="tableoptions disIb mb10">--%>
                                <%--<span class="vam">名称 &nbsp;</span>--%>
                                <%--<label class="vam">--%>
                                <%--<input style="width: auto;" name=name type="text" class="hasDatepicker" value="${classType.name}" placeholder="请输入名称">--%>
                                <%--</label>--%>
                            <%--</div>--%>
                           <%----%>
                            <%--<div class="disIb ml20 mb10">--%>
                               <%--<span class="vam">描述&nbsp;</span>--%>
                               <%--<label class="vam">--%>
                               <%--<input style="width: auto;" name="description" type="text" class="hasDatepicker" value="${classType.description}" placeholder="请输入描述">--%>
                               <%--</label>--%>
                           <%--</div>--%>
                        <%--</form>--%>
                        <%--<div class="disIb ml20 mb10">--%>
                            <%--<a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>--%>
                            <%--<a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<!-- 搜索条件，结束 -->--%>

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
                                <th class="head0 center">班次ID</th>
                                <th class="head0 center" width="55%">班次名称</th>
                                <th class="head1 center">已报名人数</th>
                                <th class="head1 center">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${classesStatisticList}" var="classType" varStatus="index">
                            <tr>
                                <td>${classType.classId}</td>
                                <td>${classType.name}</td>
                                <td>${classType.num}</td>
                                <td class="center">
                                    <a href="${ctx}/admin/jiaowu/class/queryUnitPersonNum.json?classId=${classType.classId}" class="stdbtn btn_orange" title="修改">查看各个单位人数</a>
                                </td>
                            </tr>
                            </c:forEach>
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