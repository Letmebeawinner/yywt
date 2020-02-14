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
                    <label>维修地点</label>
                    <span class="field">
                        ${repairs.repairSite}&nbsp;
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
                    <label>报修人电话</label>
                    <span class="field">
                      ${repairs.telephone}&nbsp;
                    </span>
                </p>
                <p>
                    <label>报修时间</label>
                    <span class="field">
                      <fmt:formatDate value="${repairs.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;
                    </span>
                </p>
                <p>
                    <label>处理截至时间</label>
                    <span class="field">
                              &nbsp;<fmt:formatDate value="${repairs.warnTime}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;
                    </span>
                </p>
                <p>
                    <label>损耗品</label>
                    <span class="field">
                        ${repairs.lossGoods}&nbsp;
                    </span>
                </p>
                <p>
                    <label>处理结果</label>
                    <span class="field">
                        ${repairs.result}&nbsp;
                    </span>
                </p>

                <p>
                    <label>处理状态</label>
                    <span class="field">
                    <c:if test="${repairs.status==0}">未处理</c:if>
                    <c:if test="${repairs.status==1}">正维修</c:if>
                    <c:if test="${repairs.status==2}">已维修</c:if>
                    <c:if test="${repairs.status==3}">已取消</c:if>
                   </span>
                </p>
                <p>
                    <label>处理人</label>
                    <span class="field">
                        ${repairs.sysUser.userName}&nbsp;
                    </span>
                </p>
                <c:if test="${repairs.status==2}">
                    <p>
                        <label>响应时间</label>
                        <span class="field">
                        <input type="radio" name="repair.responseTime" value="1" disabled
                        <c:if test="${not empty repairs.responseTime and repairs.responseTime == 1}">
                               checked="checked" </c:if>
                        > 满意
                        <input type="radio" name="repair.responseTime" value="0" disabled
                        <c:if test="${not empty repairs.responseTime and repairs.responseTime == 0}">
                               checked="checked" </c:if>
                        > 不满意
                    </span>
                    </p>

                    <p>
                        <label>维修质量</label>
                        <span class="field">
                          <input type="radio" name="repair.quality" value="1" disabled
                          <c:if test="${not empty repairs.quality and repairs.quality == 1}"> checked="checked" </c:if>
                          > 满意
                         <input type="radio" name="repair.quality" value="0" disabled
                         <c:if test="${not empty repairs.quality and repairs.quality == 0}"> checked="checked" </c:if>
                         > 不满意
                    </span>
                    </p>
                    <p>
                        <label>人员态度</label>
                        <span class="field">
                         <input type="radio" name="repair.attitude" value="1" disabled
                         <c:if test="${not empty repairs.attitude and repairs.attitude == 1}"> checked="checked" </c:if>
                         > 满意
                         <input type="radio" name="repair.attitude" value="0" disabled
                         <c:if test="${not empty repairs.attitude and repairs.attitude == 0}"> checked="checked" </c:if>
                         > 不满意
                    </span>
                    </p>
                    <p>
                        <label>意见栏</label>
                        <span class="field">
                         <input type="text" name="repair.commentsBelow" value="${repairs.commentsBelow}" disabled
                                class="longinput"/>
                    </span>
                    </p>
                </c:if>
            </form>

        </div>
    </div>
</div>
</body>
</html>