<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>报修详情</title>
</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">报修详情</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于报修物品详情查看；<br>
        </div>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">

        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">

                <p>
                    <label>报修编号</label>
                    <span class="field">
                        ${repairs.number}&nbsp;
                    </span>
                </p>

                <p>
                    <label>报修物品</label>
                    <span class="field">
                        ${repairs.name}&nbsp;
                    </span>
                </p>

                <p>
                    <label>故障说明</label>
                    <span class="field">
                        ${repairs.context}&nbsp;
                    </span>
                </p>

                <p>
                    <label>报修人</label>
                    <span class="field">
                        ${repairs.userName}&nbsp;
                    </span>
                </p>

                <p>
                    <label>报修时间</label>
                    <span class="field">
                      <fmt:formatDate value="${repairs.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;
                    </span>
                </p>
                <p>
                    <label>预警时间</label>
                    <span class="field">
                              &nbsp;<fmt:formatDate value="${repairs.warnTime}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;
                    </span>
                </p>

                <p>
                    <label>处理结果
                        <small></small>
                    </label>
                    <span class="field">
                        <textarea cols="80" rows="5" name="repair.result" class="longinput" id="result"
                                  disabled="disabled">${repairs.result}&nbsp;</textarea></span>
                </p>

                <p>
                    <label>处理状态</label>
                    <span class="field">
                    <c:if test="${repairs.status==0}">未处理</c:if>
                    <c:if test="${repairs.status==1}">正维修</c:if>
                    <c:if test="${repairs.status==2}">已维修</c:if>
                    <c:if test="${repairs.status==3}">已取消</c:if>&nbsp;
                   </span>
                </p>
                <p>
                    <label>处理时间</label>
                    <span class="field">
                        <c:choose>
                            <c:when test="${repairs.repairTime==null}">
                                &nbsp;&nbsp;
                            </c:when>
                            <c:otherwise>
                                <fmt:formatDate type='both' value='${repairs.repairTime}'
                                                pattern='yyyy-MM-dd HH:mm:ss'/>&nbsp;
                            </c:otherwise>
                        </c:choose>
                    </span>
                </p>
                <p>
                    <label>处理人</label>
                    <span class="field">
                        ${repairs.sysUser.userName}&nbsp;
                    </span>
                </p>
            </form>

        </div>
    </div>
</div>
</body>
</html>