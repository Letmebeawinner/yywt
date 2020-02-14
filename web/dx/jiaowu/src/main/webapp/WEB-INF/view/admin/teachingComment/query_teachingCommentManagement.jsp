<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>查看教学工作评价</title>
	</head>
	<body>
	    <div class="centercontent tables">
	         <div class="pageheader notab" style="margin-left: 30px">
                <h1 class="pagetitle">查看教学工作评价</h1>
                <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于查看某教学工作评价的具体内容<br />
				</span>
             </div><!--pageheader-->
             <div id="contentwrapper" class="contentwrapper">
                <div id="updates" class="subcontent">
        		<!-- 主要内容开始 -->
				<div id="validation" class="subcontent">
            	
                    <form id="form1" class="stdform" method="post" action="">                    	                        
                        <p>
                        	<label>课程名称</label>
                            <span class="field"><input type="text" name="classes.classNumber" id="classNumber" class="longinput" value="${teachingCommentManagement.courseName}" readonly="readonly"/></span>
                        </p>
                        <p>
                        	<label>评价人</label>
                            <span class="field"><input type="text" name="classes.classNumber" id="classNumber" class="longinput" value="${teachingCommentManagement.fromPeopleName}" readonly="readonly"/></span>
                        </p>
                        <p>
                        	<label>被评价人</label>
                            <span class="field"><input type="text" name="classes.classNumber" id="classNumber" class="longinput" value="${teachingCommentManagement.toPeopleName}" readonly="readonly"/></span>
                        </p>
                        
                        <p>
                        	<label>类型</label>
                            <span class="field"><input type="text" name="classes.name" id="name" class="longinput"
                                                       value="<c:if test="${teachingCommentManagement.type=='student_to_teacher'}">学生对老师</c:if>
<c:if test="${teachingCommentManagement.type=='leader_to_teacher'}">领导对老师</c:if>"
                                                       readonly="readonly"/></span>
                        </p>
                        
                        <p>
                        	<label>内容</label>
                            <span class="field"><textarea cols="80" rows="5" name="classes.note" class="mediuminput" id="note" readonly="readonly">${teachingCommentManagement.content}</textarea></span> 
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