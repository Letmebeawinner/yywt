<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>教职工详情</title>
    <script type="text/javascript">
        jQuery(function () {
            jQuery("#employeeType").val('${employee.employeeType}');
        });
    </script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 10px">
        <h1 class="pagetitle tac">教职工基本信息表</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent staff">
            <table width="100%" cellspacing="0" cellpadding="0" border="0">
                <tr>
                    <td align="center" width="10%">姓&nbsp;名</td>
                    <td align="center" width="10%">${employee.name}</td>
                    <td align="center" width="10%">性&nbsp;别</td>
                    <td align="center" width="10%">
                        <c:if test="${employee.sex==0}">男</c:if>
                        <c:if test="${employee.sex==1}">女</c:if>
                    </td>
                    <td align="center" width="10%">出生年月（&nbsp;岁）</td>
                    <td align="center" width="15%"><fmt:formatDate value="${employee.birthDay}" pattern="yyyy.MM"/> &nbsp;（${employee.age}岁）</td>
                    <td align="center" width="20%" rowspan="4">
                        <c:if test="${!empty employee.picture}">
                            <img src="${employee.picture}" style="width: 144px;height: 179px">
                        </c:if>
                        <c:if test="${empty employee.picture}">
                            <img src="/static/images/more2.jpg" id="touxiang" style="width: 144px;height: 179px"/>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td align="center" width="10%">民&nbsp;族</td>
                    <td align="center" width="10%">${employee.nationality}</td>
                    <td align="center" width="10%">籍&nbsp;贯</td>
                    <td align="center" width="10%">${employee.nativePlace}</td>
                    <td align="center" width="10%">出&nbsp;生&nbsp;地</td>
                    <td align="center" width="15%">${employee.birthdayPlace}</td>
                </tr>
                <tr>
                    <td align="center" width="10%">入党时间</td>
                    <td align="center" width="10%"><fmt:formatDate value="${employee.enterPartyTime}" pattern="yyyy.MM"/></td>
                    <td align="center" width="10%">参加工作时间</td>
                    <td align="center" width="10%">${employee.workTime}</td>
                    <td align="center" width="10%">身份证号</td>
                    <td align="center" width="15%">${employee.identityCard}</td>
                </tr>
                <tr>
                    <td align="center" width="10%">专业技术职务</td>
                    <td align="center" colspan="2">${employee.profressTelnel}</td>
                    <td align="center" width="10%">熟悉专业有何专长</td>
                    <td align="center" colspan="2">${employee.speciality}</td>
                </tr>
                <tr>
                    <td align="center" width="10%" rowspan="2">学历学位</td>
                    <td align="center" width="10%">全日制教育</td>
                    <td align="center" colspan="2">${employee.education}</td>
                    <td align="center" width="10%">毕业院校系及专业</td>
                    <td align="center" colspan="2">${employee.profession}</td>
                </tr>
                <tr>
                    <td align="center" width="10%">在职教育</td>
                    <td align="center" colspan="2">${employee.jobEducation}</td>
                    <td align="center" width="10%">毕业院校系及专业</td>
                    <td align="center" colspan="2">${employee.jobProfession}</td>
                </tr>
                <tr>
                    <td align="center" colspan="2">现&nbsp;任&nbsp;职&nbsp;务</td>
                    <td align="left" colspan="5">${employee.presentPost}</td>
                </tr>
                <%--<tr>--%>
                    <%--<td align="center" width="6%" rowspan="${familyList.size()+1}">家庭主要成员及重要社会关系</td>--%>
                    <%--<td align="center" width="10%">称&nbsp;谓</td>--%>
                    <%--<td align="center" width="10%">姓&nbsp;名</td>--%>
                    <%--<td align="center" width="10%">出生年月</td>--%>
                    <%--<td align="center" width="10%">政治面貌</td>--%>
                    <%--<td align="center" colspan="2">工作单位及职务</td>--%>
                <%--</tr>--%>
                <%--<c:forEach items="${familyList}" var="family">--%>
                <%--<tr>--%>
                    <%--<td align="center" width="10%">${family.title}</td>--%>
                    <%--<td align="center" width="10%">${family.name}</td>--%>
                    <%--<td align="center" width="10%"><fmt:formatDate value="${family.birthday}" pattern="yyyy-MM-dd"/> </td>--%>
                    <%--<td align="center" width="10%">${family.aspect}</td>--%>
                    <%--<td align="center" colspan="2">${family.unit}</td>--%>
                <%--</tr>--%>
                <%--</c:forEach>--%>
            </table>
        </div>
    </div>
</div>
</body>
</html>