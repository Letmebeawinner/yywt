<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>${user.name}教学计划完成情况</title>
<%-- 		<script type="text/javascript" src="${ctx}/static/js/custom/elements.js"></script> --%>
		<script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
        <script type="text/javascript">
           
        jQuery(function(){
        	var errorInfo="${errorInfo}";
        	if(errorInfo!=""){
        		jAlert(errorInfo,'提示',function() {});
        	}
        });
        
    </script>

        </script>
	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">${user.name}教学计划完成情况</h1>
                <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于展示某学员教学计划完成情况.<br />
				</span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">
                <div id="updates" class="subcontent">
        		<!-- 主要内容开始 -->
				<div id="validation" class="subcontent">
            	
                    <form id="form1" class="stdform" method="post" action="">
                        <c:if test="${list!=null&&list.size()>0}">
                        	<c:forEach items="${list }" var="courseAndProgress">
                        		<p><label><b>${courseAndProgress.courseName}(${courseAndProgress.progress}%)</b></label></p><br />
                        		<p>
                            		<span class="field"><div class="progress">
                            		<div class="bar2"><div class="value bluebar" style="width: ${courseAndProgress.progress}%;height:10px;"></div></div>
                       				 </div></span>
                       			 </p>
                        	</c:forEach>
                        </c:if>
                        
                        <br />
                        
                        </form>
                    
					
            </div>
            <!-- 主要内容结束 -->
				<div class="clear"></div>
            </div><!-- #updates -->
	    </div>
	    </div>
	</body>
</html>