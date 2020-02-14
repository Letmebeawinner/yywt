<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>资产信息详情</title>
</head>
<body>
<div class="centercontent">
    <div class="pageheader notab">
        <h1 class="pagetitle">资产信息详情</h1>
        <div style="margin-left: 20px;">
            <span style="color:red">说明</span><br>
            1.本页面用于查看资产信息；<br>
        </div>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="" id="addFormSubmit">
                <p>
                    <label>资产名称</label>
                    <span class="field">
                        ${propertyMessage.name}&nbsp;
                    </span>
                </p>
                <p>
                    <label>规格型号</label>
                    <span class="field">
                       ${propertyMessage.product}&nbsp;
                    </span>
                </p>

                <p>
                    <label>数量</label>
                    <span class="field">
                        ${propertyMessage.amount}&nbsp;
                    </span>
                </p>

                <p>
                    <label>计量单位</label>
                    <span class="field">
                       ${propertyMessage.unit}&nbsp;
                    </span>
                </p>
                <p>
                    <label>金额</label>
                    <span class="field">
                        ${propertyMessage.price}&nbsp;
                    </span>
                </p>
                <p>
                    <label>购入时间</label>
                    <span class="field">
                        <fmt:formatDate type='both' value='${propertyMessage.buyTime}' pattern='yyyy-MM-dd'/>&nbsp;

                    </span>
                </p>
                <p>
                    <label>使用期限</label>
                    <span class="field">
                       <fmt:formatDate type='both' value='${propertyMessage.liftTime}' pattern='yyyy-MM-dd'/>&nbsp;
                    </span>
                </p>
                <p>
                    <label>来源</label>
                    <span class="field">
                        ${propertyMessage.propertySource}
                    </span>
                </p>
                <p>
                    <label>状态</label>
                     <span class="field">
                        <c:if test="${propertyMessage.status==0}">未使用</c:if>
                        <c:if test="${propertyMessage.status==1}"><font style="color: #00B83F">调拨中</font></c:if>
                        <c:if test="${propertyMessage.status==2}"><font style="color: red">借用中</font></c:if>
                        <c:if test="${propertyMessage.status==3}"><font style="color:blue">领用中</font></c:if>
                     </span>
                </p>
                <p>
                    <label>备注</label>
                    <span class="field">
                      ${propertyMessage.context}&nbsp;
                    </span>
                </p>
            </form>
        </div>
    </div>
</div>
</body>
</html>