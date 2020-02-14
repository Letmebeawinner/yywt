<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>各个班型报名人数</title>
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
                <h1 class="pagetitle">各个班型报名人数</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示班型的列表.<br />
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">
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
                                <th class="head0 center">班型id</th>
                                <th class="head0 center">班型名称</th>
                                <th class="head1 center">已报名人数</th>
                                <th class="head1 center">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${classTypeStatisticList}" var="classType" varStatus="index">
                            <tr>
                                <td>${classType.classTypeId}</td>
                                <td>${classType.className}</td>
                                <td>${classType.num}</td>
                                <td class="center">
                                    <a href="${ctx}/admin/jiaowu/class/queryClassPersonNum.json?type=${classType.classTypeId}" class="stdbtn btn_orange" title="修改">查看各个班次人数</a>
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