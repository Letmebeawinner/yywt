<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>班次教学计划列表</title>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
<%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
        <script type="text/javascript">
        jQuery(function(){			
            var classNumber="${classes.classNumber}";
            jQuery("#classNumber").val(classNumber);
        });
        function searchForm(){
            jQuery("#searchForm").submit();
        }
        
        function emptyForm(){
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }
			function confirmTeachingProgram(id,confirmTeachingProgram){				
				 jQuery.ajax({
		    		url:'${ctx}/admin/jiaowu/teach/confirmTeachingProgram.json',
		    		data:{"id":id,
		    			  "confirmTeachingProgram":confirmTeachingProgram		    			 
		    			},		
		    		type:'post',
		    		dataType:'json',		    		
		    		success:function (result){
		    			if(result.code=="0"){
// 		    				window.location.reload();
		    				if(confirmTeachingProgram==1){
		    					jQuery("#confirm_true_"+id).hide();
		    					jQuery("#confirm_false_"+id).show();
		    				}else{
		    					jQuery("#confirm_true_"+id).show();
		    					jQuery("#confirm_false_"+id).hide();
		    				}
		    			}else{
		    				jAlert(result.message,'提示',function() {});
		    				alert(result.message);
		    			}		    			
		    		}
		    	});
				
			}
        </script>
	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">班次教学计划列表</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面用于对所有班次进行教学计划相关的操作.<br />
					2.可通过编号查询对应的班次.<br />
					3.可点击"查看详情"按钮,查看某班次的教学计划详情.<br />
					4.如果某班次的教学计划未确认,可点击"确认"按钮,对某班次的教学计划进行确认,确认后,该教学计划不可修改.<br />
					5.如果某班次的教学计划已确认,可点击"取消确认"按钮,取消对某班次的教学计划的确认.<br />
					6.可点击"导出"按钮,将某班次的教学计划已Excel的形式导出.
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">

                <!-- 搜索条件，开始 -->
                <div class="overviewhead clearfix mb10">
                    <div class="fl mt5">
                        <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/teach/confirmTeachingProgramList.json" method="get">
                           
                           
                            <div class="disIb ml20 mb10">
                               <span class="vam">编号 &nbsp;</span>
                               <label class="vam">
                               <input id="classNumber" style="width: auto;" name="classNumber" type="text" class="hasDatepicker" value="" placeholder="请输入编号">
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
                                <th class="head0 center">班型</th>
                                <th class="head1 center">编号</th>
                                <th class="head0 center">名称</th>
                                <th class="head1 center">讲师</th>
                                <th class="head0 center">总人数</th>
                                <th class="head0 center">开班时间</th>
                                <th class="head0 center">结束时间</th>
                                <th class="head1 center">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<c:if test="${classList!=null&&classList.size()>0 }">
                            <c:forEach items="${classList}" var="classes" varStatus="index">
                            <tr>
                                <td>${index.index+1}</td>
                                <td>${classes.classType}</td>
                                <td>${classes.classNumber}</td>
                                <td>${classes.name}</td>
                                <td>${classes.teacherName}</td>
                                <td>${classes.studentTotalNum}</td>
                                <td><fmt:formatDate type="both" value="${classes.startTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td><fmt:formatDate type="both" value="${classes.endTime}" pattern="yyyy-MM-dd HH:mm"/></td>                                
                                <td class="center">
                                    <a href="${ctx}/admin/jiaowu/teach/detailTeachingProgramOfOneClass.json?classId=${classes.id}" class="stdbtn" title="查看详情">查看详情</a>
                                    <c:if test="${classes.confirmTeachingProgram==0}">
                                    <a href="javascript:void(0);" onclick="confirmTeachingProgram(${classes.id},1)" class="stdbtn" title="确认" id="confirm_true_${classes.id}">确认</a>
                                    <a href="javascript:void(0);" onclick="confirmTeachingProgram(${classes.id},0)" class="stdbtn" title="取消确认" id="confirm_false_${classes.id}" style="display: none;">取消确认</a>
                                    </c:if>
                                    <c:if test="${classes.confirmTeachingProgram==1}">
                                    <a href="javascript:void(0);" onclick="confirmTeachingProgram(${classes.id},1)" class="stdbtn" title="确认" id="confirm_true_${classes.id}" style="display: none;">确认</a>
                                    <a href="javascript:void(0);" onclick="confirmTeachingProgram(${classes.id},0)" class="stdbtn" title="取消确认" id="confirm_false_${classes.id}">取消确认</a>
                                	</c:if>
                                	<a href="${ctx}/admin/jiaowu/teach/exportExcel.json?classId=${classes.id}" class="stdbtn" title="导出">导出</a>
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