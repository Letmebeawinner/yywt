<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>班级通讯录</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">班级通讯录</h1>
        <span>
            <span style="color:red">说明</span><br>
            1.本页面展示我的班级通讯录.<br/>
        </span>
    </div>
    
    <div id="contentwrapper" class="contentwrapper">
        <h1 style="text-align:center">${classes.name}（${userList==null?0:userList.size()}人）</h1>
        <div class="overviewhead clearfix mb10" style="font-size: initial">
            <c:if test="${noteUsers != null && noteUsers.size() >0}">
                <table>
                    <tr>
                        <td>班主任</td>
                        <td>:</td>
                        <td>&nbsp;&nbsp;${teacher.name}</td>
                        <td>&nbsp;&nbsp;${teacher.mobile}</td>
                    </tr>
                    <tr>
                        <td>副班主任</td>
                        <td>:</td>
                        <td>&nbsp;&nbsp;${deputyTeacher.name}</td>
                        <td>&nbsp;&nbsp;${deputyTeacher.mobile}</td>
                    </tr>

                    <c:forEach items="${noteUsers}" var="noteUser">
                        <tr>
                            <td>${noteUser.note}</td>
                            <td>:</td>
                            <td>&nbsp;&nbsp;${noteUser.name}</td>
                            <td>&nbsp;&nbsp;${noteUser.mobile}</td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
        </div>
        <!-- 数据显示列表，开始 -->
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <colgroup>
                    <col class="con0"/>
                    <col class="con1"/>
                    <col class="con0"/>
                    <col class="con1"/>
                    <col class="con0"/>
                    <col class="con1"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">序号</th>
                    <th class="head0 center">姓名</th>
                    <th class="head0 center">性别</th>
                    <%--<th class="head0 center">班主任</th>--%>
                    <th class="head0 center">分组</th>
                    <th class="head0 center">政治面貌</th>
                    <th class="head0 center">单位</th>
                    <th class="head0 center">学员单位及职务职称（全称）</th>
                    <th class="head0 center">联系电话</th>
                    <c:if test="${isUser!=true}">
                        <th class="head0 center">身份证号</th>
                    </c:if>
                    <th class="head0 center">备注</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${userList!=null&&userList.size()>0 }">
                    <c:forEach items="${userMap}" var="item">
                        <tr>
                            <td colspan="10" align="center" style="font-size: large;color: black;">
                                <c:if test="${item.key==0}">
                                    <b>未分组(${fn:length(item.value)} 人)</b>
                                </c:if>
                                <c:if test="${item.key != 0}">
                                    <b>第${item.key}组(${fn:length(item.value)} 人)</b>
                                </c:if>
                            </td>
                        </tr>
                        <c:forEach items="${item.value}" var="user"  varStatus="index">
                            <tr>
                                <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${index.index+1}</td>
                                <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.name}</td>
                                <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.sex}</td>
                                <%--<td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.teacherName}</td>--%>
                                <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>
                                    <c:if test="${user.groupId==1}"> 第一组</c:if>
                                    <c:if test="${user.groupId==2}"> 第二组</c:if>
                                    <c:if test="${user.groupId==3}"> 第三组</c:if>
                                    <c:if test="${user.groupId==4}"> 第四组</c:if>
                                </td>
                                <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>
                                    <c:if test="${user.politicalStatus==0}">
                                        中共党员
                                    </c:if>
                                    <c:if test="${user.politicalStatus==1}">
                                        民主党派
                                    </c:if>
                                    <c:if test="${user.politicalStatus==2}">
                                        无党派人士
                                    </c:if>
                                    <c:if test="${user.politicalStatus==3}">
                                        群众
                                    </c:if>
                                    <c:if test="${user.politicalStatus==4}">
                                        其它
                                    </c:if>

                                    <c:if test="${user.politicalStatus==5}">
                                        中共预备党员
                                    </c:if>
                                    <c:if test="${user.politicalStatus==6}">
                                        共青团员
                                    </c:if>
                                    <c:if test="${user.politicalStatus==7}">
                                        民革党员
                                    </c:if>
                                    <c:if test="${user.politicalStatus==8}">
                                        民盟盟员
                                    </c:if>
                                    <c:if test="${user.politicalStatus==9}">
                                        民建会员
                                    </c:if>
                                    <c:if test="${user.politicalStatus==10}">
                                        民进会员
                                    </c:if>
                                    <c:if test="${user.politicalStatus==11}">
                                        农工党党员
                                    </c:if>
                                    <c:if test="${user.politicalStatus==12}">
                                        致公党党员
                                    </c:if>
                                    <c:if test="${user.politicalStatus==13}">
                                        九三学社社员
                                    </c:if>
                                    <c:if test="${user.politicalStatus==14}">
                                        台盟盟员
                                    </c:if>
                                </td>
                                <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.unit}</td>
                                <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.job}</td>
                                <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>${user.mobile}</td>
                                <c:if test="${isUser!=true}">
                                    <td>${user.idNumber}</td>
                                </c:if>
                                <td <c:if test="${user.politicalStatus!=0}">style="color:red;"</c:if>>
                                        ${user.note}
                                </td>
                            </tr>
                        </c:forEach>
                       
                    </c:forEach>
                    
                </c:if>
                </tbody>
            </table>
        </div>
        <!-- 数据显示列表，结束 -->
    </div>
</div>
</body>
</html>