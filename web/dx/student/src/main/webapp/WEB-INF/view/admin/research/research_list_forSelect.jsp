<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<%@ include file="/popUpBase.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>调研报告类型列表</title>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
<%--         <script type="text/javascript" src="${ctx}/static/admin/js/resource-list.js"></script> --%>
        <script type="text/javascript">
        jQuery(function(){			
            
        });
        function searchForm(){
            jQuery("#searchForm").submit();
        }
        
        function emptyForm(){
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }
		
        var myArrayMoveStock = new Array();	
		function tijiao(){
			var qstChecked = jQuery(".questionIds:checked");
			if (qstChecked.length == 0) {
				jAlert('请选择调研报告类别','提示',function() {});
				return;
			}
			qstChecked.each(function() {
				var researchIdAndName=jQuery(this).val();
				myArrayMoveStock.push(researchIdAndName.split("-")[0]);
				myArrayMoveStock.push(researchIdAndName.split("-")[1]);
			});
			opener.addResearchId(myArrayMoveStock);
			window.close();
		}
        </script>
	</head>
	<body>
	    <div class="tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">调研报告类型列表</h1>
                <span>
                    <span style="color:red">说明</span><br>
					1.本页面用于其它页面获取调研报告类型,以弹出框形式展示.<br />
					2.可通过标题查询对应的调研报告类型.<br />
                </span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">

                <!-- 搜索条件，开始 -->
                <div class="overviewhead clearfix mb10">
                    <div class="fl mt5">
                        <form class="disIb" id="searchForm" action="${ctx}/jiaowu/research/researchListForSelect.json" method="get">
                            
                           
                           <div class="disIb ml20 mb10">
                               <span class="vam">标题 &nbsp;</span>
                               <label class="vam">
                               <input id="title" style="width: auto;" name="title" type="text" class="hasDatepicker" value="${research.title }" placeholder="请输入调研标题">
                               </label>
                           </div>                           
                        </form>
                        <div class="disIb ml20 mb10">
                            <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                            <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                            <a href="javascript: void(0)" onclick="tijiao()" class="stdbtn ml10 btn_orange">确定</a>
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
                            	<th class="head0 center"></th>
                                <th class="head0 center">名称</th>                               
                                <th class="head1 center">开始时间</th>
                                <th class="head0 center">结束时间</th>                                
                                <th class="head0 center">创建时间 </th>
<!--                                 <th class="head1 center">操作</th> -->
                            </tr>
                        </thead>
                        <tbody>
                        	<c:if test="${researchList!=null&&researchList.size()>0 }">
                            <c:forEach items="${researchList}" var="research">
                            <tr>
                            	<td><input type="radio" name="researchId" value="${research.id}-${research.title}" class="questionIds" />${research.id }</td>
                                <td>${research.title}</td>                                
                                <td><fmt:formatDate type="both" value="${research.startTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td><fmt:formatDate type="both" value="${research.endTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <td><fmt:formatDate type="both" value="${research.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>                          
                                <!-- <td class="center">                                   
                                                                        
                                </td> -->
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