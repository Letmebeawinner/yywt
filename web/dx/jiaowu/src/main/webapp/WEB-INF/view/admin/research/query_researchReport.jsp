<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>查询调研报告</title>
        <script type="text/javascript">
			jQuery(function(){
			
           
        	});
			
        </script>
	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">查询调研报告</h1>
                <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于查询某调研报告的具体信息<br />
				</span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">
                <div id="updates" class="subcontent">
        		<!-- 主要内容开始 -->
				<div id="validation" class="subcontent">
            	
                    <form id="form1" class="stdform" method="post" action="">
                    	
                        
                        <p>
                        	<label>调研报告类型</label>
                            <span class="field"><input type="text" name="classes.classNumber" id="classNumber" class="longinput" value="${researchReport.researchName}" readonly="readonly"/></span>
                        </p>
                        
                        <p>
                        	<label>类型</label>
                        	
                            <span class="field">
                            <c:if test='${researchReport.type=="teacher"}'>
                            <input type="text" name="classes.name" id="name" class="longinput" readonly="readonly" value="讲师"/>
                            </c:if>
                            <c:if test='${researchReport.type=="student"}'>
                            <input type="text" name="classes.name" id="name" class="longinput" readonly="readonly" value="学员"/>
                            </c:if>
                            
                            </span>
                        </p>
                        <p>
                        	<label>姓名</label>
                            <span class="field"><input type="text" name="classes.classNumber" id="classNumber" class="longinput" value="${researchReport.peopleName}" readonly="readonly"/></span>
                        </p>

                        <p>
                        	<label>内容</label>
                            <span class="field"><textarea cols="80" rows="5" name="classes.note" class="mediuminput" id="note" readonly="readonly">${researchReport.content}</textarea></span> 
                        </p>
                        <c:if test="${not empty researchReport.fileUrl}">
                            <p>
                                <label>调研报告附件</label>
                                <span class="field">
                                <a href="${researchReport.fileUrl}"
                                   class="stdbtn" title="下载附件" download="">下载附件</a>
                            </span>
                            </p>
                        </c:if>
                        <p>
                        	<label>备注</label>
                            <span class="field"><textarea cols="80" rows="5" name="classes.note" class="mediuminput" id="note" readonly="readonly">${researchReport.note}</textarea></span> 
                        </p>
                        <br />
                        
                        
                    </form>
					<p class="stdformbutton">
                        	<button class="radius2" onclick="javascript :history.back(-1);" id="submitButton" style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;">返回</button>
                        </p>
            </div>
            <!-- 主要内容结束 -->
				<div class="clear"></div>
            </div><!-- #updates -->
	    </div>
	    </div>
	</body>
</html>