<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>学员基本信息</title>
</head>
<body>

<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">
            学员基本信息
        </h1>
        <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于展示学员信息<br/>
				</span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="updates" class="subcontent">
            <!-- 主要内容开始 -->
            <form id="form1" class="stdform" method="post" action="">
                <div id="validation" class="subcontent">
                    <div class="tables-all">
                        <table>
                            <tr>
                                <td rowspan="2" style="width: 100px">
                                    <tt>班型</tt>
                                </td>
                                <td rowspan="2" style="width: 120px">
											<span class="field">
                                                ${user.classTypeName}
                                            </span>
                                </td>
                                <td>
                                    <tt>班次</tt>

                                </td>
                                <td>
											<span class="field">
                                                ${classes.name}
                                            </span>
                                </td>
                                <td colspan="2">
                                    <tt>姓名</tt>

                                </td>
                                <td colspan="2">
                                    <span class="field">${user.name}</span>
                                </td>
                                <td rowspan="3" colspan="2" width="100px">
												   <span class="field" style="margin-left: 10px;text-align: left">
												    <center><h4 id="file"></h4></center>
                                                     <span class="field" id="touxiangspan">
                                                         <c:if test="${!empty user.path}">
                                                             <img src="${user.path}" id="touxiang" style="width: 150px;height: 185px;"/>
                                                         </c:if>
                                                         <c:if test="${empty user.path}">
                                                             <img src="/static/images/more2.jpg" id="touxiang" style="width: 150px;height: 185px;border: 1px solid #f2f2f2"/>
                                                         </c:if>

                                                     </span>
											</span>
                                </td>

                            </tr>
                            <tr>
                                <td>
                                    <tt>政治面貌</tt>
                                </td>
                                <td>
											<span class="field">
                                              <c:if test="${user.politicalStatus==0}">中共党员</c:if>
                                              <c:if test="${user.politicalStatus==1}">民主党派</c:if>
                                              <c:if test="${user.politicalStatus==2}">无党派人士</c:if>
                                              <c:if test="${user.politicalStatus==3}">群众</c:if>
                                              <c:if test="${user.politicalStatus==4}">其它</c:if>
										</span>
                                </td>
                                <td colspan="2">
                                    <tt>性别</tt>
                                </td>
                                <td colspan="2">
                                    <span class="field">
                                        ${user.sex}
                                    </span>
                                </td>

                            </tr>
                            <tr>
                                <td>
                                    <tt>单位</tt>
                                </td>
                                <td>
                                </td>

                                <td colspan="3">
											<span class="field">
                                                ${user.unit}
                                            </span>
                                </td>
                                <td>
                                    <tt>级别</tt>
                                </td>
                                <td>
                                    <span class="field">
                                            <c:if test="${user.business==1}">正厅</c:if>
                                            <c:if test="${user.business==2}">巡视员</c:if>
                                            <c:if test="${user.business==3}">副厅</c:if>
                                            <c:if test="${user.business==4}">副巡视员</c:if>
                                            <c:if test="${user.business==5}">正县</c:if>
                                            <c:if test="${user.business==6}">副县</c:if>
                                            <c:if test="${user.business==7}">调研员</c:if>
                                            <c:if test="${user.business==8}">副调研员</c:if>
                                            <c:if test="${user.business==9}">正科</c:if>
                                            <c:if test="${user.business==10}">副科</c:if>
                                            <c:if test="${user.business==11}">工作人员</c:if>
                                    </span>
                                </td>

                            </tr>
                            <tr>

                                <td>
                                    <tt>学员单位及职务职称（全称）</tt>
                                </td>
                                <td>
											<span class="field">
                                                ${user.job}
                                            </span>
                                </td>
                                <td style="width: 100px;">
                                    <tt>学历</tt>

                                </td>
                                <td>
                                    <span class="field">
                                        ${user.qualification}
                                    </span>

                                </td>
                                <td colspan="2">
                                    <tt>民族</tt>

                                </td>
                                <td colspan="2">
                                    <span class="field">${user.nationality}</span>
                                </td>

                            </tr>


                            <tr>
                                <td>
                                    <tt>身份证号</tt>
                                </td>
                                <td colspan="3">
                                    <span class="field">${user.idNumber}</span>
                                </td>
                                <td colspan="2" style="width: 100px;">
                                    <tt>手机号</tt>
                                </td>
                                <td colspan="2">
                                    <span class="field">${mobile}</span>
                                </td>
                                <td>
                                    <tt>邮箱</tt>

                                </td>
                                <td>
                                    <span class="field">${email}</span>
                                </td>
                            </tr>


                            <tr>
                                <td>
                                    <tt>个人车号牌</tt>
                                </td>
                                <td colspan="9" style="text-align: left;">
                                    <span class="field">${user.carNumber}</span>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <tt>年龄</tt>
                                </td>
                                <td colspan="9" style="text-align: left;">
                                    <span class="field">${user.age}岁</span>
                                </td>
                            </tr>
                            <tr>

                                <td>
                                    <tt>备注</tt>
                                </td>
                                <td colspan="9" style="text-align: left;">
                                    <span class="field">${user.note}</span>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </form>
            <div class="clear"></div>
        </div><!-- #updates -->
    </div>
</div>

</body>
</html>