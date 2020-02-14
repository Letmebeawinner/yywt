<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>党性分析材料列表</title>
        <script type="text/javascript">
        jQuery(function(){			
            var studentId="${materialAnalysis.studentId}";
            jQuery("#studentId").val(studentId);
            var meetingId="${materialAnalysis.meetingId}";
            jQuery("#meetingId").val(meetingId);


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
                            jQuery("#classId").val("${classId}");
                        }else{
                            jAlert(result.message,'提示',function() {});
                        }
                    } ,
                    error:function(e){
                        jAlert('添加失败','提示',function() {});
                    }
                });
            });
            jQuery("#classTypeId").val("${classTypeId}");
            jQuery("#classTypeId").trigger("change");
        });
        function searchForm(){
            jQuery("#searchForm").submit();
        }
        
        function emptyForm(){
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }
			
        function useExcel(){
        	jQuery("#searchForm").prop("action", "${ctx}/admin/jiaowu/materialAnalysis/exportExcel.json");
        	jQuery("#searchForm").submit();
        	jQuery("#searchForm").prop("action", "${ctx}/admin/jiaowu/materialAnalysis/materialAnalysisList.json");
        }
        
        function deleteMaterialAnalysis(id){
        	jConfirm('您确定要删除吗?','确认',function(r){
			    if(r){
			    	jQuery.ajax({
			    		url:'${ctx}/admin/jiaowu/materialAnalysis/deleteMaterialAnalysis.json?id='+id,
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
                <h1 class="pagetitle">党性分析材料列表</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面展示党性分析材料的列表.<br />
					2.可通过学员ID、会议ID查询对应的党性分析材料.<br />
					3.可点击"导出Excel"按钮,将党性分析材料导出为Excel文件.<br />
					4.可点击"查看"按钮,查看某党性分析材料的具体内容.<br />
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">

                <!-- 搜索条件，开始 -->
                <div class="overviewhead clearfix mb10">
                    <div class="fl mt5">
                        <form class="disIb" id="searchForm" action="${ctx}/admin/jiaowu/materialAnalysis/materialAnalysisList.json" method="get">
                            
                           
                            <%--<div class="disIb ml20 mb10">
                               <span class="vam">学员ID &nbsp;</span>
                               <label class="vam">
                               <input id="studentId" style="width: auto;" name="studentId" type="text" class="hasDatepicker" value="" placeholder="请输入学员ID">
                               </label>
                           </div>--%>
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
                               <span class="vam">会议ID &nbsp;</span>
                               <label class="vam">
                               <input id="meetingId" style="width: auto;" name="meetingId" type="text" class="hasDatepicker" value="" placeholder="请输入会议ID">
                               </label>
                           </div>
                        </form>
                        <div class="disIb ml20 mb10">
                            <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                            <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                            <a href="javascript: void(0)" onclick="useExcel()" class="stdbtn btn_orange">导出Excel</a>
                            <a href="${ctx}/admin/jiaowu/materialAnalysis/toCreateMaterialAnalysis.json" class="stdbtn btn_orange">添加</a>
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
                                <th class="head0 center">班次</th>
                            	<%--<th class="head0 center">学员ID</th>--%>
                                <th class="head0 center">学员</th>
                                <th class="head1 center">类型</th>
                                <%--<th class="head0 center">会议ID</th>--%>
                                <th class="head0 center">会议名称</th>                                
                                <th class="head0 center">创建时间</th>
                                <th class="head1 center">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<c:if test="${materialAnalysisList!=null&&materialAnalysisList.size()>0 }">
                            <c:forEach items="${materialAnalysisList}" var="materialAnalysis" varStatus="index">
                            <tr>
                                <td>${index.index+1}</td>
                                <td>${materialAnalysis.classTypeName}</td>
                                <td>${materialAnalysis.className}</td>
                            	<%--<td>${materialAnalysis.studentId}</td>--%>
                                <td>${materialAnalysis.studentName}</td>
                                <td>
                                <c:if test="${materialAnalysis.type=='train'}">
                                		培训党性分析材料
                                </c:if>
                                <c:if test="${materialAnalysis.type=='meeting'}">
                                	会议党性分析材料
                                </c:if>
                                </td>
                                <%--<td>${materialAnalysis.meetingId}</td>--%>
                                <td>${materialAnalysis.meetingName}</td>                              
                                <td><fmt:formatDate type="both" value="${materialAnalysis.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td class="center">
                                    <a href="${ctx}/admin/jiaowu/materialAnalysis/queryMaterialAnalysis.json?id=${materialAnalysis.id}" class="stdbtn" title="修改">查看</a>
                                	<a href="javascript:void(0)" onclick="deleteMaterialAnalysis(${materialAnalysis.id})" class="stdbtn" title="删除">删除</a>
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