<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<%@ include file="/popUpBase.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>教室列表</title>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
<%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
        <script type="text/javascript">
        jQuery(function(){
        	/* laydate.skin('molv');
            laydate({
                elem: '#startTime',
                format:'YYYY-MM-DD hh:mm:ss'
            });
            laydate({
                elem: '#endTime',
                format:'YYYY-MM-DD hh:mm:ss'
            }); */
            
            var position= "${classroom.position}";
            jQuery("#position").val(position);
			/* var startTime= "${startTime}";
			jQuery("#startTime").val(startTime);
            var endTime="${endTime}";
            jQuery("#endTime").val(endTime); */
        });
        function searchForm(){
            jQuery("#searchForm").submit();
        }
        
        function emptyForm(){
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }
			
        function tijiao(){
        	var qstChecked = jQuery(".questionIds:checked");
			if (qstChecked.length == 0) {
				alert("请选择教室");
				return;
			}
			var classroomIdAndPosition="";
			qstChecked.each(function() {
				classroomIdAndPosition=jQuery(this).val();
			});
			opener.addClassroomIdAndPosition(classroomIdAndPosition);
			window.close();
        }
        </script>
	</head>
	<body>
	    <div class="tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">教室列表</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面用于其它页面获取教室,以弹出框形式展示.<br />
					2.可通过位置查询对应的班次.<br />
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">

                <!-- 搜索条件，开始 -->
                <div class="overviewhead clearfix mb10">
                    <div class="fl mt5">
                        <form class="disIb" id="searchForm" action="${ctx}/jiaowu/classroom/classroomListForSelect.json" method="get">                            
                           
                            <div class="disIb ml20 mb10">
                               <span class="vam">位置 &nbsp;</span>
                               <label class="vam">
                               <input id="position" style="width: auto;" name="position" type="text" class="hasDatepicker" value="" placeholder="请输入位置">
                               </label>
                           </div>

                           <!-- <div class="disIb ml20 mb10">
                               <span class="vam">开始时间 &nbsp;</span>
                               <label class="vam">
                               <input id="startTime" type="text" class="width100 laydate-icon" name="startTime"/>
                               </label>
                           </div>
							<div class="disIb ml20 mb10">
                               <span class="vam">结束时间 &nbsp;</span>
                               <label class="vam">
                               <input id="endTime" type="text" class="width100 laydate-icon" name=endTime"/>
                               </label>
                           </div> -->
                        </form>
                        <div class="disIb ml20 mb10">
                            <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                            <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                            <a href="javascript: void(0)" onclick="tijiao()" class="stdbtn btn_orange">确定</a>
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
                                <th class="head0 center">ID</th>
                                <th class="head0 center">位置</th>
                                <th class="head0 center">座位数</th>
                                <th class="head0 center">创建时间</th>
                                <th class="head1 center">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<c:if test="${classroomList!=null&&classroomList.size()>0 }">
                            <c:forEach items="${classroomList}" var="classroom">
                            <tr>
                                <td><input name="classroomId" type="radio" value="${classroom.id}-${classroom.position}" class="questionIds" />${classroom.id }</td>
                                <td>${classroom.position}</td>  
                                <td>${classroom.num}</td>                               
                                <td><fmt:formatDate type="both" value="${classroom.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>                                
                                <td class="center">
                                    <a href="${ctx}/admin/jiaowu/class/updateClass.json?id=${classes.id}" class="stdbtn" title="修改">查看使用情况</a>                                    
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