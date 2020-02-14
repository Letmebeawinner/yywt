<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>调研报告</title>
<%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
        <script type="text/javascript">
        jQuery(function(){			
            var researchId="${researchReport.researchId}";
            jQuery("#researchId").val(researchId);
            var researchName="${researchReport.researchName}";    
            jQuery("#researchName").val(researchName);
            jQuery("#researchspan").html(researchName);
        });
        function searchForm(){
            jQuery("#searchForm").submit();
        }
        
        function emptyForm(){
            jQuery("input:text").val('');
            jQuery("select").val(0);
            jQuery("#researchspan").html("");
            jQuery("#researchId").val("");
            jQuery("#researchName").val("");
        }
        function selectResearch(){
        	window.open('${ctx}/jiaowu/research/researchListForSelect.json','newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=1200,height=800');
        }
        
        function addResearchId(researchIdAndName){
        	jQuery("#researchId").val(researchIdAndName[0]);
        	jQuery("#researchName").val(researchIdAndName[1]);
        	jQuery("#researchspan").html(researchIdAndName[1]);
        }
			
        function deleteResearchReport(id){
        	jConfirm('您确定要删除吗?','确认',function(r){
			    if(r){
			    	jQuery.ajax({
			    		url:'${ctx}/admin/jiaowu/researchReport/deleteResearchReport.json?id='+id,
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
                <h1 class="pagetitle">调研报告</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示调研报告的列表.<br />
					2.可通过调研报告类型查询对应的调研报告.<br />
					3.可点击"详情"按钮,查询某调研报告的具体内容.<br />
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">

                <!-- 搜索条件，开始 -->
                <div class="overviewhead clearfix mb10">
                    <div class="fl mt5">
                        <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/researchReport/researchReportList.json" method="get">
                        <input type="hidden" id="researchId" name="researchId" />    
                        <input type="hidden" id="researchName" name="researchName" />   
                            <div class="disIb ml20 mb10">
                               <span class="vam">调研报告类型 &nbsp;</span>
                               <label class="vam">
                               <span id="researchspan"></span>
                               <a href="javascript:selectResearch()" class="stdbtn btn_orange">选择调研报告类型</a>
                               </label>
                           </div>

                          <!--  <div class="disIb ml20 mb10">
                               <span class="vam">会议ID &nbsp;</span>
                               <label class="vam">
                               <input id="meetingId" style="width: auto;" name="meetingId" type="text" class="hasDatepicker" value="" placeholder="请输入会议ID">
                               </label>
                           </div> -->
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
                                <th class="head0 center">调研报告类型</th>
                                <th class="head0 center">填写人</th>
                                <th class="head0 center">类型</th>
                                <th class="head0 center">状态</th>
                                <th class="head1 center">添加时间</th>
                                <th class="head1 center">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:if test="${researchReportList!=null&&researchReportList.size()>0 }">
                            <c:forEach items="${researchReportList}" var="researchReport" varStatus="index">
                                <tr>
                                <td>${index.index+1}</td>
                                <td>${researchReport.researchName}</td>
                                <td>${researchReport.peopleName}</td>
                                <td>
                                   <c:if test="${researchReport.type=='student'}">学员</c:if>
                                   <c:if test="${researchReport.type=='teacher'}">老师</c:if>
                                </td>
                                    <td>
                                        <c:if test="${researchReport.audit == 0}">
                                            未审核
                                        </c:if>
                                        <c:if test="${researchReport.audit == 1}">
                                            已审核
                                        </c:if>
                                    </td>
                                    <td><fmt:formatDate type="both" value="${researchReport.createTime}"
                                                        pattern="yyyy-MM-dd HH:mm"/></td>
                                <td class="center">
                                    <a href="${ctx}/admin/jiaowu/researchReport/queryResearchReport.json?id=${researchReport.id}" class="stdbtn" title="详情">详情</a>
                                	<a href="javascript:void(0)" onclick="deleteResearchReport(${researchReport.id})" class="stdbtn" title="删除">删除</a>
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